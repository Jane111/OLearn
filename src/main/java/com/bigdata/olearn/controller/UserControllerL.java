package com.bigdata.olearn.controller;

import com.bigdata.olearn.model.MoocCluster;
import com.bigdata.olearn.model.UserLinkField;
import com.bigdata.olearn.model.WorkCluster;
import com.bigdata.olearn.neo.PointNode;
import com.bigdata.olearn.neo.PreviousRelationship;
import com.bigdata.olearn.neo.UserPointNode;
import com.bigdata.olearn.repository.PointNodeRepository;
import com.bigdata.olearn.repository.PreviousRelationshipRepository;
import com.bigdata.olearn.repository.UserPointNodeRepository;
import com.bigdata.olearn.service.Neo4jServiceL;
import com.bigdata.olearn.service.UserServiceL;
import com.bigdata.olearn.util.BaseResponse;
import com.bigdata.olearn.util.Constant;
import com.bigdata.olearn.util.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.annotation.Query;
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

    @Autowired
    UserPointNodeRepository upnp;

    @GetMapping("getMapByArea")//得到某个领域的知识图谱
    public Map<String, Object> getMapByArea(
            @RequestParam("areaId") BigInteger areaId
    ){
        return neo4jServiceL.getMapByArea(areaId);
    }
    //用户是否拥有该领域的知识图谱
    @GetMapping("/userHasAreaMap")
    public BaseResponse userHasAreaMap(
            @RequestParam("areaId") BigInteger areaId,
            @RequestParam("uId") BigInteger uId
    )
    {
        UserLinkField userLinkField = UserLinkField.dao.findFirst("select * from user_link_field " +
                "where field_id=? AND user_id=?",areaId,uId);
        if(userLinkField==null)
        {
            br.setData("false");
        }else
        {
            br.setData("true");
        }
        return br;
    }
    //todo 用户是否添加为目标岗位
    public BaseResponse userHasSetJob()
    {
        return br;
    }

    //在添加知识点之前首先提示用户，是否学完其前导课程
    //如果没有用户知识图谱时加入知识点，询问所有的前导节点；建议在用户进入领域知识图谱页面时得到一个状态status
    @GetMapping("/getPreviousPoint")
    public String getPreviousPoint(
            @RequestParam("pNameId") Long pNameId
    )
    {
         List<PointNode> nodesList = neo4jServiceL.getPreviousPoint(pNameId);
         String previousHint = "";
         for(PointNode pn:nodesList)
         {
             previousHint+=pn.getpName()+",";
         }
         return previousHint.substring(0,previousHint.length()-1);
    }

    //在添加知识点之前首先提示用户，是否学完其前导课程
    //如果在有用户图谱时加入知识点，则提示是否已经选取该节点以及前导节点的状态
    @GetMapping("/getUserPreviousPoint")
    public String getUserPreviousPoint(
            @RequestParam("pNameId") Long pNameId
    )
    {
        return neo4jServiceL.getUserPreviousPoint(pNameId);
    }


    //用户将某个岗位设为目标岗位
    @GetMapping("/addJobPointToUserMap")
    public void addJobPointToUserMap(
            @RequestParam("uId") BigInteger uId,//用户id
            @RequestParam("pNameIdList") String pNameIdList,//知识点Id,因为岗位对应的知识点的获取比较麻烦，直接由前端传过来
            @RequestParam("areaId") BigInteger areaId//领域Id
    ){
        UserLinkField userLinkField = UserLinkField.dao.findFirst("select * from user_link_field " +
                "where field_id=? AND user_id=?",areaId,uId);
        if(userLinkField==null)//如果第一次接触该领域，创建该领域对应的图谱
        {
            //在关系型数据库中加入用户-领域记录
            UserLinkField newUserLinkField=new UserLinkField();
            newUserLinkField.setFieldId(areaId);
            newUserLinkField.setUserId(uId);
            newUserLinkField.save();
            //todo 在关系型数据库中加入用户-岗位记录,目标岗位不可以再添加，限制“加入目标岗位”按钮的使用
            //在图数据库中生成用户图谱
            neo4jServiceL.createMapForUser(uId,areaId);
        }
       //岗位节点集合中的开始节点
        List<Long> beginNodePNameId = neo4jServiceL.getBeginNodeByPNameIdList(pNameIdList);
        for(Long pNameId:beginNodePNameId)
        {
            //将没有前导知识点的知识加入学习
            UserPointNode userPointNode = upnp.findUserPointNodeByPNameIdAndUIdIs(pNameId,uId.longValue());
            if(userPointNode.getUpStatus()!=0)//用户知识点的状态，0学习完成/1正在学习/2推荐学习/3未学习
            //判断状态，已经完成的学习不改变
            {
                //将该知识点的状态设为正在学习
                neo4jServiceL.setPointLearning(pNameId);
            }

        }
    }

    @GetMapping("/addPointToUserMap")
    public BaseResponse addPointToUserMap(
            @RequestParam("uId") BigInteger uId,//用户id
            @RequestParam("pNameId") BigInteger pNameId//知识点Id
    ) {
        BigInteger areaId = MoocCluster.dao.findById(pNameId).getFieldId();//由知识点得到对应的领域
        UserLinkField userLinkField = UserLinkField.dao.findFirst("select * from user_link_field " +
                "where field_id=? AND user_id",areaId,uId);
        if(userLinkField==null)//如果第一次接触该领域，创建该领域对应的图谱
        {
            //在关系型数据库中加入相关记录
            UserLinkField newUserLinkField=new UserLinkField();
            newUserLinkField.setFieldId(areaId);
            newUserLinkField.setUserId(uId);
            newUserLinkField.save();
            //在图数据库中生成用户图谱
            neo4jServiceL.createMapForUser(uId,areaId);
        }
        List<PointNode> nodeList = neo4jServiceL.getPreviousPoint(pNameId.longValue());
        //将前导知识点的状态改为已经学习
        neo4jServiceL.setPointFinish(nodeList);
        //将该知识点的状态设为正在学习
        neo4jServiceL.setPointLearning(pNameId.longValue());
        br.setResult(ResultCodeEnum.SUCCESS);
        return br;
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
