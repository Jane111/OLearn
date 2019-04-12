package com.bigdata.olearn.service;

import com.bigdata.olearn.model.Ability;
import com.bigdata.olearn.model.Work;
import com.bigdata.olearn.model.WorkCluster;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class WorkServiceW {

    public List<Record> getLabel(){
        return Db.find("SELECT id,classname FROM work_cluster");
    }

    public List<WorkCluster> workRecommend(BigInteger userId){
        //得到用户的能力标签
        List<Ability> abilities=Ability.dao.find("SELECT * FROM ability WHERE user_id=?",userId);
        List<String> myAbilities=new ArrayList<>();
        for(Ability ability:abilities){
            myAbilities.add(ability.getClassname());
        }
        //得到所有的岗位聚类
        List<WorkCluster> workClusters=WorkCluster.dao.find("SELECT * FROM work_cluster");
        List<WorkCluster> canDo=new ArrayList<>();
        OUTER:
        for(WorkCluster workCluster:workClusters){
            String [] requests=workCluster.getRequest().substring(1,workCluster.getRequest().length()-1).split(",");
            for(String request:requests){
                if(!myAbilities.contains((String)request)){
                    break OUTER;
                }
            }
            canDo.add(workCluster);
        }
        return  canDo;
    }

    public List<Work> getWorkRecommend(List<WorkCluster> data){
        List<Work> works=new ArrayList<>();
        for(WorkCluster workCluster :data){
            List<Work> workList=Work.dao.find("SELECT * FROM work WHERE cluster_id=? LIMIT 0,5",workCluster.getId());
            works.addAll(workList);
        }
        return  works;
    }
}
