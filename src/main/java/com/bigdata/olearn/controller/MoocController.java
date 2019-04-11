package com.bigdata.olearn.controller;

import com.bigdata.olearn.service.MoocServiceW;
import com.bigdata.olearn.util.BaseResponse;
import com.bigdata.olearn.util.ResultCodeEnum;
import com.jfinal.plugin.activerecord.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/mooc")
public class MoocController {
    @Autowired
    MoocServiceW moocServiceW;
    @Autowired
    BaseResponse br;

    //课程搜索
    @RequestMapping(value="/moocSearch")
    public BaseResponse moocSearch(
            @RequestParam(value = "keyword",required = false)String keyword
    ){
        List<Record> data=moocServiceW.searchMooc(keyword);
        br.setResult(ResultCodeEnum.SUCCESS);
        br.setData(data);
        return br;
    }
    //显示课程详情
    @RequestMapping(value="/moocDetail")
    public BaseResponse moocDetail(Long moocId){
        Record data=moocServiceW.moocDetail(moocId);
        br.setResult(ResultCodeEnum.SUCCESS);
        br.setData(data);
        return br;
    }
    //显示领域标签
    @RequestMapping(value="/showLabel")
    public BaseResponse showLabel(){
        List<Record> data=moocServiceW.getLabel();
        br.setResult(ResultCodeEnum.SUCCESS);
        br.setData(data);
        return br;
    }
    //todo:显示某个领域下的知识图谱——图实现
    @RequestMapping(value="/showCourse")
    public BaseResponse showKnowledgeGraph(
            @RequestParam(value = "fieldId")Long fieldId
    ){
        List<Record>data=moocServiceW.getCourse(fieldId);
        br.setData(data);
        br.setResult(ResultCodeEnum.SUCCESS);
        return  br;
    }
    //显示某个知识点下的课程
    @RequestMapping(value="/showCourse")
    public BaseResponse showCourse(
            @RequestParam(value = "clusterId")Long clusterId
    ){
        List<Record>data=moocServiceW.getCourse(clusterId);
        br.setData(data);
        br.setResult(ResultCodeEnum.SUCCESS);
        return  br;
    }
    //todo：显示系统收录课程资源数量
    //todo：课程来源平台占比
    //todo：热门课程top10
    //todo：课程检索热度占比

}