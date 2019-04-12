package com.bigdata.olearn.service;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class MoocServiceW {
    //得到热点知识点
    public List<Record> getKnowledge(){return Db.find("SELECT * FROM mooc_cluster");}

    public List<Record> getCourseByKnowledge(BigInteger clusterId){
        return Db.find("SELECT mooc.mooc_id,mooc.title,mooc.introduce,mooc.people,mooc.professor,picture " +
                "FROM mooc_link_cluster LEFT JOIN mooc on mooc_link_cluster.mooc_id=mooc.mooc_id WHERE cluster_id=?",clusterId);
    }

    //搜索课程数据
    public List<Record> searchMooc(String keyword){
        return Db.find("SELECT mooc_id,title,picture,institute,people,professor FROM mooc WHERE title like '%"+keyword+"%' OR institute like '%"+keyword+"%'");
    }

    //查看mooc详情
    public Record moocDetail(BigInteger moocId){
        return Db.findFirst("SELECT * FROM mooc WHERE mooc_id=?",moocId);
    }

    //查看mooc菜单
    public List<Record> moocMenu(BigInteger moocId){
        return Db.find("SELECT mooc_id,lesson,sequence FROM mooc_menu WHERE mooc_id=? ORDER BY sequence",moocId);
    }

    //显示领域标签
    public List<Record> getLabel(){
        return Db.find("SELECT field_id,fieldname FROM field");
    }

    //显示某个领域下的知识图谱
    public List<Record> getKnowledgeGraph(BigInteger fieldId){
        return Db.find("SELECT mooc_id,title,introduce,rank FROM mooc_link_cluster WHERE cluster_id=?",fieldId);
    }
    //显示某个知识点的课程
    public List<Record> getCourse(BigInteger clusterId){
        return Db.find("SELECT mooc_id,title,introduce,rank FROM mooc_link_cluster WHERE cluster_id=?",clusterId);
    }
}
