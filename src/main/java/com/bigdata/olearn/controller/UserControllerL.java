package com.bigdata.olearn.controller;

import com.bigdata.olearn.model.MoocCluster;
import com.bigdata.olearn.model.UserLinkField;
import com.bigdata.olearn.model.WorkCluster;
import com.bigdata.olearn.neo.PointNode;
import com.bigdata.olearn.neo.PreviousRelationship;
import com.bigdata.olearn.repository.PointNodeRepository;
import com.bigdata.olearn.repository.PreviousRelationshipRepository;
import com.bigdata.olearn.service.Neo4jServiceL;
import com.bigdata.olearn.service.UserServiceL;
import com.bigdata.olearn.util.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserControllerL {
    @Autowired
    BaseResponse br;

    @Autowired
    Neo4jServiceL neo4jServiceL;

    @GetMapping("getMapByArea")//得到某个领域的知识图谱
    public Map<String, Object> getMapByArea(
            @RequestParam("areaId") BigInteger areaId
    ){
        return neo4jServiceL.getMapByArea(areaId);
    }
    //在添加知识点之前首先提示用户，是否学完其前导课程
    @GetMapping("/getPreviousPoint")
    public String getPreviousPoint(
            @RequestParam("pNameId") Long pNameId
    )
    {
        //todo 如果没有用户知识图谱时加入知识点，询问所有的前导节点；建议在用户进入领域知识图谱页面时得到一个状态status
        //todo 如果在有用户图谱时加入知识点，则提示是否已经选取该节点以及前导节点的状态
         List<PointNode> nodesList = neo4jServiceL.getPreviousPoint(pNameId);
         String previousHint = "";
         for(PointNode pn:nodesList)
         {
             previousHint+=pn.getpName()+",";
         }
         return previousHint.substring(0,previousHint.length()-1);
    }

    @GetMapping("/addPointToUserMap")
    public void addPointToUserMap(
            @RequestParam("uId") BigInteger uId,//用户id
            @RequestParam("pNameId") BigInteger pNameId//知识点Id
    ) {
        BigInteger areaId = MoocCluster.dao.findById(pNameId).getFieldId();//由知识点得到对应的领域
        UserLinkField userLinkField = UserLinkField.dao.findFirst("select * from user_link_field " +
                "where field_id=?",areaId);
        if(userLinkField==null)//如果第一次接触该领域，创建该领域对应的图谱
        {
            //todo 在关系型数据库中加入相关记录
            neo4jServiceL.createMapForUser(uId,pNameId);
        }
        else
        {
            //todo
            List<PointNode> nodesList = neo4jServiceL.getPreviousPoint(pNameId.longValue());

        }

    }

    @GetMapping("/getMapByAreaUser")//得到某个用户某个领域的知识图谱
    public Map<String, Object> getMapByAreaUser(
            @RequestParam("areaId") Long areaId,
            @RequestParam("uId") Long uId

    ){
        return neo4jServiceL.getMapByAreaUser(areaId,uId);
    }


    @GetMapping("/getMapByJob")//得到某个岗位对应的知识图谱
    public Map<String, Object> getMapByJob(
            @RequestParam("jId") BigInteger jId//岗位Id
    ){
        String requestList = WorkCluster.dao.findById(jId).getRequest();
        requestList = requestList.substring(1,requestList.length()-1);
        String[] jobPointList = requestList.split(",");//初始岗位知识点
        return neo4jServiceL.getJobMap(jobPointList);
    }


}
