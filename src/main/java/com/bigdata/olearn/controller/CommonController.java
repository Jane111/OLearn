package com.bigdata.olearn.controller;

import com.bigdata.olearn.service.CommonService;
import com.bigdata.olearn.util.BaseResponse;
import com.bigdata.olearn.util.GetKeyWords;
import com.bigdata.olearn.util.ResultCodeEnum;
import com.hankcs.hanlp.mining.cluster.ClusterAnalyzer;
import com.jfinal.plugin.activerecord.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/common")
public class CommonController {
    @Autowired
    CommonService commonService;

    //课程聚类
    @RequestMapping(value="setMoocCluster")
    public BaseResponse setMoocCluster(){
        BaseResponse br=new BaseResponse();
        List<Record> data=commonService.getMooc();
        ClusterAnalyzer<String> analyzer = new ClusterAnalyzer<String>();
        for(Record dRecord:data) {
            analyzer.addDocument(dRecord.getStr("title"),dRecord.getStr("title"));
        }
        //得到了聚类的集合
        List<Set<String>> result=analyzer.kmeans(20);
        //将聚类中的名称进行抽取关键词
        for(Set<String> reSet:result)  {

            List<String> list=new GetKeyWords().getWords(reSet.toString());
            //统计关键词词频
            Map<String, Long> map = commonService.countWords(list);
            //得到最高词频的关键词
            String key=commonService.getDiskMax(map);
            if(key==null||key=="") {
                key="职业素养";
            }
            //将课程与聚类关联起来
            commonService.moocLinkCluster(key,reSet);
        }
        br.setResult(ResultCodeEnum.SUCCESS);
        return br;
    }
    public static void main(String[] args) {
        System.out.println(new GetKeyWords()
                .getWords("工作职责:1、负责公司各类数据的处理、大数据平台框架的研发设计工作；2、使用Spark、MapReduce、Storm、Kafka等组件进行数据处理；3、新技术框架和解决方案预研与落地，以提高处理和分析大数据的效率和速度。任职资格:1、熟悉Hadoop以及Hadoop生态圈中的多个组件，如HBase、Hive、Kafka、Storm、Impala等；2、精通JAVA编程语言，熟悉Linux操作，可以编写代码编程使用Hadoop生态中的组件和基于组件开发的大数据处理；3、熟悉开源组件源码者优先。"));

    }
    //抽取课程预备知识
    @RequestMapping(value="setPreStudyLabel")
    public BaseResponse setPreStudyLabel(){
        BaseResponse br=new BaseResponse();
        List<Record> clusterList=commonService.getMoocCluster();
        for(Record cluster:clusterList) {
            String content=commonService.getClusterPre(cluster);
            commonService.setClusterPreLabel(content,cluster);
        }
        br.setResult(ResultCodeEnum.SUCCESS);
        return br;
    }
    //岗位聚类
    @RequestMapping(value="setWorkCluster")
    public BaseResponse setWorkCluster(){
        BaseResponse br=new BaseResponse();
        List<Record> data=commonService.getWork();
        ClusterAnalyzer<String> analyzer = new ClusterAnalyzer<String>();
        for(Record dRecord:data) {
            String title=dRecord.getStr("workname")+"###"+dRecord.getStr("company");
            analyzer.addDocument(title,dRecord.getStr("introduce"));
        }
        //得到了聚类的集合
        List<Set<String>> result=analyzer.kmeans(20);
        //将聚类中的名称进行抽取关键词
        for(Set<String> reSet:result)  {

            List<String> list=new GetKeyWords().getWords(reSet.toString());
            //统计关键词词频
            Map<String, Long> map = commonService.countWords(list);
            //得到最高词频的关键词
            String key=commonService.getDiskMax(map);
            if(key==null||key=="") {
                key="其它相关岗位";
            }
            //将课程与聚类关联起来
            commonService.workLinkCluster(key,reSet);
        }
        br.setResult(ResultCodeEnum.SUCCESS);
        return br;
    }
    //抽取岗位能力需求
    @RequestMapping(value="setWorkRequest")
    public BaseResponse setWorkRequest(){
        BaseResponse br=new BaseResponse();
        List<Record> clusterList=commonService.getWorkCluster();
        for(Record cluster:clusterList) {
            String content=commonService.getClusterRequest(cluster);
            commonService.setClusterRequest(content,cluster);
        }
        br.setResult(ResultCodeEnum.SUCCESS);
        return br;
    }

    //todo：课程推荐

    //todo：搜索课程（人数，课程名）
    //todo：爬一部分其它领域的课程
    //推荐岗位，返回岗位信息列表
    //课程详情，图，简介，内容，
}
