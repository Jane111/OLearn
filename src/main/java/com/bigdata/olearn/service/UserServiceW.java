package com.bigdata.olearn.service;

import com.bigdata.olearn.model.*;
import com.bigdata.olearn.util.ResultCodeEnum;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class UserServiceW {
    //将课程加入学习路线
    public void addCourseToPath(BigInteger userId,BigInteger[] moocIds,BigInteger clusterId,Integer theMinRank){
       for(BigInteger moocId : moocIds){
           //构造shcedule的Bean
           MoocLinkCluster moocLinkCluster=MoocLinkCluster.dao.findFirst("SELECT * FROM mooc_link_cluster WHERE mooc_id=?",moocId );
           List<MoocMenu> moocMenus=MoocMenu.dao.find("SELECT * FROM mooc_menu WHERE mooc_id=?",moocId);
           for(MoocMenu moocMenu:moocMenus){
               Schedule schedule=new Schedule();
               schedule.setUserId(userId);
               schedule.setMoocId(moocId);
               schedule.setMoocName(moocLinkCluster.getTitle());
               schedule.setRank(moocLinkCluster.getRank());
               schedule.setClusterId(moocLinkCluster.getClusterId());
               schedule.setClassname(moocLinkCluster.getClassname());
               schedule.setMenuId(moocMenu.getMoocId());
               schedule.setSequence(moocMenu.getSequence());
               schedule.setLesson(moocMenu.getLesson());
               schedule.setStatus(0);
               schedule.save();
           }
       }
        if(checkKowledgeLabel(userId,clusterId)){
           //能力已经存在
            updateKnowledgeLabel(userId,clusterId,theMinRank);
        }else {
           //能力不存在
            addKnowledgeLabelByMooc(userId,clusterId,theMinRank);
            //在userLinkField里面添加一条记录，记录用户是否已经生成这个领域的个人图谱
            setMyField(userId,clusterId);
        }
    }

    //检查该能力是否已经存在
    public boolean checkKowledgeLabel(BigInteger userId,BigInteger clusterId){

        Record result=Db.findFirst("SELECT * FROM ability WHERE user_id=? AND cluster_id=?",userId,clusterId);
        if(result==null){
            return  false;
        }else {
            return  true;
        }
    }
    //todo:添加用户的能力标签
    public void addKnowledgeLabelByMooc(BigInteger userId,BigInteger clusterId ,Integer theMinRank){
            //MoocLinkCluster moocLinkCluster=MoocLinkCluster.dao.findFirst("SELECT * FROM mooc_link_cluster WHERE mooc_id=?",moocId);
                MoocCluster moocCluster=MoocCluster.dao.findById(clusterId);
                Ability ability=new Ability();
                ability.setUserId(userId);
                ability.setClusterId(moocCluster.getId());
                ability.setClassname(moocCluster.getClassname());
                ability.setRank(theMinRank);//0代表啥都没有，1代表完成了1
                ability.save();

    }

    //添加用户的能力标签,与addKnowledgeLabelByMooc稍有区别
    public void addKnowledgeLabel(BigInteger userId,BigInteger clusterId){

        MoocCluster moocCluster=MoocCluster.dao.findFirst("SELECT * FROM mooc_cluster WHERE id=?",clusterId);
        Ability ability=new Ability();
        ability.setUserId(userId);
        ability.setClusterId(moocCluster.getId());
        ability.setClassname(moocCluster.getClassname());
        ability.setRank(0);
        ability.save();

        //在userLinkField里面添加一条记录，记录用户是否已经生成这个领域的个人图谱
        setMyField(userId,clusterId);
    }

    //在field表里面添加用户是否加入某个领域学习的记录
    public void setMyField(BigInteger userId,BigInteger clusterId){
        MoocCluster moocCluster=MoocCluster.dao.findById(clusterId);
        Record result=Db.findFirst("SELECT * FROM user_link_field WHERE user_id=? AND field_id=?",userId,moocCluster.getFieldId());
        if(result==null){
            UserLinkField userLinkField=new UserLinkField();
            userLinkField.setFieldId(BigInteger.valueOf(result.getLong("field_id")));
            userLinkField.setFieldname(result.getStr("fieldname"));
            userLinkField.setUserId(userId);
            userLinkField.save();
        }
    }
    //修改用户的能力标签
    public void updateKnowledgeLabel(BigInteger userId,BigInteger clusterId,Integer theMinRank){
       //todo:假如用户先只选择了初级课程，此时theMinRank为0，若下一次又选了一个高级课程，则theMinRank变成了2，但是初级课程可能并没有学完，所以在下面必须进行判断
        Ability ability=Ability.dao.findFirst("SELECT * FROM ability WHERE user_id=? AND cluster_id=?",userId,clusterId);
        if(ability.getRank()<theMinRank){
            return;
        }
       Db.update("UPDATE ability SET rank=? WHERE cluster_id=? AND user_id=?",theMinRank+1,clusterId,userId);

    }

    //进入详细知识点查看知识点的学习情况
    public List<Record> showKnowledgeLearnSituaton(BigInteger userId,BigInteger clusterId){
        return Db.find("SELECT DISTINCT mooc_id ,mooc_name,rank FROM schedule WHERE user_id=? AND cluster_id=?",userId,clusterId);
    }
    //查看某个用户的某一课程的进度，即目录的点亮情况
    public List<Record> showMySchedule(BigInteger userId,BigInteger clusterId,Integer rank){
        return Db.find("SELECT * FROM schedule WHERE rank=? AND user_id=? AND cluster_id=? ORDER BY sequence",rank,userId,clusterId);
    }
    //点亮课程进度
    public void setMyMoocPlan(BigInteger userId,BigInteger moocId,Integer sequence){
        Db.update("UPDATE schedule SET status=1 WHERE sequence<=? AND mooc_id=? AND user_id=?",sequence,moocId,userId);
        //检查一下三个级别的进度，从大到小，大的点满了就直接搞定了
        //先得到clusterId
        MoocLinkCluster moocLinkCluster=MoocLinkCluster.dao.findFirst("SELECT * FROM mooc_link_cluster WHERE mooc_id=?",moocId);
        //获得某个用户在此知识点下选的最高级别的课程，rank
        Schedule sc=Schedule.dao.findFirst("SELECT * FROM schedule WHERE cluster_id=? AND user_id=? ORDER BY rank DESC",moocLinkCluster.getClusterId(),userId);
        for(int i=sc.getRank();i>=0;i--){
            //从最高rank开始依次判断用户的能力级别
            Schedule schedule=Schedule.dao.findFirst("SELECT * FROM schedule WHERE cluster_id=? AND rank=? AND user_id=? ORDER BY sequence DESC",moocLinkCluster.getClusterId(),i,userId);
            if(schedule!=null){
                if(schedule.getStatus()==1){
                    //该用户已经达到此层级，可以修改用户此能力级别
                    Db.update("UPDATE ability SET rank=? WHERE user_id=? AND cluster_id=?",schedule.getRank()+1,userId,moocLinkCluster.getClusterId());
                    break;
                }
            }
        }


    }

    //显示某个用户的能力标签
    public List<Record> showMyLabel(BigInteger userId){
        return Db.find("SELECT * FROM ability WHERE user_id=?",userId);
    }

    public ResultCodeEnum register(String nickname,String mail,String password){
        User user=User.dao.findFirst("SELECT * FROM user WHERE mail=? AND password=?",mail,password);
        if(user==null){
            return  ResultCodeEnum.EXIST_USER;
        }else {
            Db.update("INSERT INTO user(nickname,mail,password) VALUES (?,?,?)",nickname,mail,password);
            return ResultCodeEnum.SUCCESS;
        }
    }

    public User login(String mail,String password){
        User user=User.dao.findFirst("SELECT * FROM user WHERE mail=? AND password=?",mail,password);
        return user;
    }
}
