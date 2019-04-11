package com.bigdata.olearn.service;

import com.bigdata.olearn.util.GetKeyWords;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommonService {
    //得到课程数据
    public List<Record> getMooc(){
        return Db.find("SELECT mooc_id,title,introduce,menu FROM mooc");
    }
    //统计词频
    public Map<String, Long> countWords(List<String> list){
        return list.stream().collect(Collectors.groupingBy(p -> p,Collectors.counting()));
    }
    //获得词频最高的词
    public String getDiskMax(Map<String, Long> map) {
        List<Double> list = new ArrayList<Double>();
        for (String temp : map.keySet()) {
            double value = map.get(temp);
            list.add(value);
        }
        double max = 0;
        for (int i = 0; i < list.size(); i++) {
            double size = list.get(i);
            max = (max>size)?max:size;
        }
        for (String key : map.keySet()) {
            if (max == map.get(key)) {
                return key;
            }
        }
        return null;
    }

    //将课程聚类与课程建立关联
    public void moocLinkCluster(String key,Set<String> reSet){
        //key就是聚类的名称
        Record cluster=Db.findFirst("SELECT id,classname FROM mooc_cluster WHERE classname=?",key);
        if(cluster==null) {
            Db.update("INSERT INTO mooc_cluster(classname) values(?)",key);
            Record cluster2=Db.findFirst("SELECT id,classname FROM mooc_cluster WHERE classname=?",key);
            //将课程与聚类建立关联
            doMoocLinkCluster(reSet,cluster2);
        }else {
            //将课程与聚类建立关联
            doMoocLinkCluster(reSet,cluster);
        }
    }

    //将课程与聚类建立关联
    public void doMoocLinkCluster(Set<String> set,Record cluster) {
        for(String str:set) {
            Record mooc=Db.findFirst("SELECT mooc_id,title,introduce FROM mooc WHERE title=?",str);
            Db.update("INSERT INTO mooc_link_cluster(mooc_id,title,introduce,cluster_id,classname) VALUES(?,?,?,?,?)"
                    ,mooc.getStr("mooc_id"),mooc.getStr("title"),mooc.getStr("introduce"),cluster.getStr("id"),cluster.getStr("classname"));
        }
    }
    //获得所有的聚类
    public List<Record> getMoocCluster(){
        return  Db.find("SELECT * FROM mooc_cluster");
    }
    //得到某个聚类中所有课程的预备知识
    public String getClusterPre(Record cluster){
            //得到某个大类的课程
            List<Record> moocLinkClusterList=Db.find("SELECT * FROM mooc_link_cluster WHERE cluster_id=?",cluster.getStr("id"));
            StringBuilder content=new StringBuilder();
            for(Record moocLinkCluster:moocLinkClusterList) {
                Record mooc=Db.findFirst("SELECT prestudy FROM mooc WHERE mooc_id=?",moocLinkCluster.getStr("mooc_id"));
                content.append(mooc.getStr("prestudy"));
            }
            return content.toString();
    }

    //获得某个聚类的预备知识关键词标签
    public void setClusterPreLabel(String content,Record cluster){
        //根据聚类中所有课程预备知识获得关键词
        List<String> list=new GetKeyWords().getWords(content);
        //统计词频选择top5
        Set<String>key=new HashSet<>();
        for(String str:list) {
            key.add(str);
        }
        if(key.size()<5) {
            Db.update("UPDATE mooc_cluster SET prelabel=? WHERE id=?",key.toString(),cluster.getStr("id"));
        }else {
            Map<String, Long> map = list.stream().collect(Collectors.groupingBy(p -> p,Collectors.counting()));
            StringBuilder label=new StringBuilder();
            label.append("[");
            for(int i=0;i<5;i++) {
                String mapKey=getDiskMax(map);
                label.append(mapKey+",");
                map.remove(mapKey);
            }
            Integer index=label.lastIndexOf(",");
            label.deleteCharAt(index);
            label.append("]");
            Db.update("UPDATE mooc_cluster SET prelabel=? WHERE id=?",label.toString(),cluster.getStr("id"));
        }
    }

    /***************************************************岗位数据的操作*****************************************
     ********************************************************************************************************/

    //得到岗位数据
    public List<Record> getWork(){
        return Db.find("SELECT work_id,workname,introduce,company FROM work");
    }

    //将课程聚类与课程建立关联
    public void workLinkCluster(String key,Set<String> reSet){
        //key就是聚类的名称
        Record cluster=Db.findFirst("SELECT id,classname FROM work_cluster WHERE classname=?",key);
        if(cluster==null) {
            Db.update("INSERT INTO work_cluster(classname) values(?)",key);
            Record cluster2=Db.findFirst("SELECT id,classname FROM work_cluster WHERE classname=?",key);
            //将课程与聚类建立关联
            doWorkLinkCluster(reSet,cluster2);
        }else {
            //将课程与聚类建立关联
            doWorkLinkCluster(reSet,cluster);
        }
    }

    //将岗位与聚类建立关联
    public void doWorkLinkCluster(Set<String> set,Record cluster) {
        for(String str:set) {
            String[] work=str.split("###");
            Db.update("UPDATE work SET cluster_id=?,classname=? WHERE workname=? AND company=?"
                    ,cluster.getStr("id"),cluster.getStr("classname"),work[0],work[1]);
        }
    }

    //获得所有岗位的聚类
    public List<Record> getWorkCluster(){
        return  Db.find("SELECT * FROM work_cluster");
    }

    //todo：得到某个聚类中所有岗位的能力要求
    public String getClusterRequest(Record cluster){
        //得到某个大类的课程
        List<Record> workLinkClusterList=Db.find("SELECT * FROM work WHERE cluster_id=?",cluster.getStr("id"));
        StringBuilder content=new StringBuilder();
        for(Record workLinkCluster:workLinkClusterList) {
            content.append(workLinkCluster.getStr("introduce"));
        }
        return content.toString();
    }

    //todo：获得某个聚类的预备知识关键词标签
    public void setClusterRequest(String content,Record cluster){
        //根据聚类中所有课程预备知识获得关键词
        List<String> list=new GetKeyWords().getWords(content);
        //统计词频选择top5
        Set<String>key=new HashSet<>();
        for(String str:list) {
            key.add(str);
        }
        if(key.size()<5) {
            Db.update("UPDATE work_cluster SET request=? WHERE id=?",key.toString(),cluster.getStr("id"));
        }else {
            Map<String, Long> map = list.stream().collect(Collectors.groupingBy(p -> p,Collectors.counting()));
            StringBuilder label=new StringBuilder();
            label.append("[");
            for(int i=0;i<5;i++) {
                String mapKey=getDiskMax(map);
                label.append(mapKey+",");
                map.remove(mapKey);
            }
            Integer index=label.lastIndexOf(",");
            label.deleteCharAt(index);
            label.append("]");
            Db.update("UPDATE work_cluster SET request=? WHERE id=?",label.toString(),cluster.getStr("id"));
        }
    }
}
