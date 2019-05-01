package com.bigdata.olearn.service;

import com.bigdata.olearn.model.Ability;
import com.bigdata.olearn.model.MoocCluster;
import com.bigdata.olearn.model.MoocLinkCluster;
import com.bigdata.olearn.model.MoocMenu;
import com.bigdata.olearn.model.Schedule;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class UserServiceW {
    //将课程加入学习路线
    public void addCourseToPath(Long userId,Long[] moocIds,Long clusterId){
       for(Long moocId : moocIds){
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
               schedule.setMenuId(moocMenu.getMoocId());
               schedule.setSequence(moocMenu.getSequence());
               schedule.setLesson(moocMenu.getLesson());
               schedule.save();
           }
       }
        if(checkKowledgeLabel(userId,clusterId)){
           //能力已经存在
            updateKnowledgeLabel(userId,clusterId);
        }else {
           //能力不存在
            addKnowledgeLabelByMooc(userId,moocIds,clusterId);
        }
    }

    //检查该能力是否已经存在
    public boolean checkKowledgeLabel(Long userId,Long clusterId){

        Record result=Db.findFirst("SELECT * FROM ability WHERE user_id=? AND cluster_id=?",userId,clusterId);
        if(result==null){
            return  false;
        }else {
            return  true;
        }
    }
    //todo:添加用户的能力标签,Ability的实体没有生成
    public void addKnowledgeLabelByMooc(Long userId,Long[] moocId,Long clusterId){

            MoocLinkCluster moocLinkCluster=MoocLinkCluster.dao.findFirst("SELECT * FROM mooc_link_cluster WHERE mooc_id=?",moocId);
                Ability ability=new Ability();
                ability.setUserId(userId);
                ability.setClusterId(moocLinkCluster.getClusterId());
                ability.setClassname(moocLinkCluster.getClassname());
                ability.setRank(moocLinkCluster.getRank());//0代表啥都没有，1代表完成了1
                ability.save();
//            for(int i=0;i<3;i++){
//                Ability ability=new Ability();
//                ability.setUserId(userId);
//                ability.setClusterId(moocLinkCluster.getClusterId());
//                ability.setClassname(moocLinkCluster.getClassname());
//                ability.setRank(i);
//                if(i==moocLinkCluster.getRank()){
//                    ability.setMoocId(moocId);
//                    ability.setMoocName(moocLinkCluster.getTitle());
//                }
//                ability.save();
//            }

    }

    //添加用户的能力标签,与addKnowledgeLabelByMooc稍有区别
    public void addKnowledgeLabel(Long userId,Long clusterId){

        MoocCluster moocCluster=MoocCluster.dao.findFirst("SELECT * FROM mooc_cluster WHERE id=?",clusterId);
        Ability ability=new Ability();
        ability.setUserId(userId);
        ability.setClusterId(moocCluster.getId());
        ability.setClassname(moocCluster.getClassname());
        ability.setRank(0);
        ability.save();
//            for(int i=0;i<3;i++){
//                Ability ability=new Ability();
//                ability.setUserId(userId);
//                ability.setClusterId(moocLinkCluster.getClusterId());
//                ability.setClassname(moocLinkCluster.getClassname());
//                ability.setRank(i);
//                if(i==moocLinkCluster.getRank()){
//                    ability.setMoocId(moocId);
//                    ability.setMoocName(moocLinkCluster.getTitle());
//                }
//                ability.save();
//            }

    }
    //修改用户的能力标签
    public void updateKnowledgeLabel(Long userId,Long moocId){

        /*
        List<Record> clusterList=Db.find("SELECT DISTINCT cluster_id FROM schedule WHERE user_id=?",userId);
        for(Record cluster:clusterList){
            Record rank1=Db.findFirst("SELECT * FROM schedule WHERE user_id=? AND rank=1 AND cluster_id=? ORDER BY sequence DESC"
                    ,userId,cluster.getStr("cluster_id"));
            Record rank2=Db.findFirst("SELECT * FROM schedule WHERE user_id=? AND rank=2 AND cluster_id=? ORDER BY sequence DESC"
                    ,userId,cluster.getStr("cluster_id"));
            Record rank3=Db.findFirst("SELECT * FROM schedule WHERE user_id=? AND rank=3 AND cluster_id=? ORDER BY sequence DESC"
                    ,userId,cluster.getStr("cluster_id"));
            //ability对应的课程更新，如果之前是将整个知识点加入到学习路线，那么ability中的课程相关的字段是空，此时更新课程字段
            Db.update()
            //ability的状态更新
            if(rank3!=null){

            }
            if(Integer.parseInt(rank3.getStr("status"))==1){
                //高阶已完成
                Db.update("UPDATE ability SET status=1 WHERE user_id=? AND cluster_id=?",userId,cluster.getStr("cluster_id"));
            }else {
                if(Integer.parseInt(rank2.getStr("status"))==1){
                    //中阶已完成
                    Db.update("UPDATE ability SET status=1 WHERE user_id=? AND cluster_id=? AND rank IN(0,1)",userId,cluster.getStr("cluster_id"));
                }else {
                    if(Integer.parseInt(rank1.getStr("status"))==1){
                        //初阶已完成
                        Db.update("UPDATE ability SET status=1 WHERE user_id=? AND cluster_id=? AND rank=0",userId,cluster.getStr("cluster_id"));
                    }else{
                        //都未完成
                        return;
                    }
                }
            }

        }
    }*/
    }

    //进入详细知识点查看知识点的学习情况
    public List<Record> showKnowledgeLearnSituaton(Long userId,Long clusterId){
        return Db.find("SELECT DISTINCT mooc_id ,mooc_name,rank FROM schedule WHERE user_id=? AND cluster_id=?",userId,clusterId);
    }
    //查看某个用户的某一课程的进度，即目录的点亮情况
    public List<Record> showMySchedule(Long userId,Long moocId){
        return Db.find("SELECT * FROM schedule WHERE user_id=? AND mooc_id=? ORDER BY sequence",userId,moocId);
    }
    //点亮课程进度
    public void setMyMoocPlan(Long userId,Long moocId,Integer sequence){
        Db.update("UPDATE schedule SET status=1 WHERE sequence<=? AND mooc_id=? AND user_id=?",sequence,moocId,userId);
    }

    //显示某个用户的能力标签
    public List<Record> showMyLabel(Long userId){
        return Db.find("SELECT * FROM ability WHERE user_id=?",userId);
    }
}
