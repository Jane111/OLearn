package com.bigdata.olearn.controller;

import com.bigdata.olearn.neo.PointNode;
import com.bigdata.olearn.neo.PreviousRelationship;
import com.bigdata.olearn.repository.PointNodeRepository;
import com.bigdata.olearn.repository.PreviousRelationshipRepository;
import com.bigdata.olearn.service.Neo4jServiceL;
import com.bigdata.olearn.service.UserServiceL;
import com.bigdata.olearn.util.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/user")
public class UserControllerL {
    @Autowired
    UserServiceL userServiceL;

    @Autowired
    BaseResponse br;

    @Autowired
    Neo4jServiceL neo4jServiceL;

    @GetMapping("getMapByArea")//得到某个领域的知识图谱
    public Map<String, Object> getMapByArea(){
        return userServiceL.getMapByArea("计算机");
    }
    @GetMapping("getMapByAreaUser")//得到某个领域的知识图谱
    public Map<String, Object> getMapByAreaUser(){
        return userServiceL.getMapByAreaUser("计算机",1l);
    }


    @GetMapping("getMapByJob")//得到某个领域的知识图谱
    public Map<String, Object> getMapByJob(){

        List<String> jobPointList = new ArrayList<>();//初始岗位知识点
        jobPointList.add("算法与数据结构");
        jobPointList.add("计算机基础数学");
        jobPointList.add("可计算理论");
        jobPointList.add("人工智能");

        return neo4jServiceL.getJobMap(jobPointList);
    }


}
