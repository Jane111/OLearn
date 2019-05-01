package com.bigdata.olearn.service;


import com.bigdata.olearn.neo.PointNode;
import com.bigdata.olearn.neo.PreviousRelationship;
import com.bigdata.olearn.neo.UserPointNode;
import com.bigdata.olearn.neo.UserPreviousRelationship;
import com.bigdata.olearn.repository.PointNodeRepository;
import com.bigdata.olearn.repository.PreviousRelationshipRepository;
import com.bigdata.olearn.repository.UserPointNodeRepository;
import com.bigdata.olearn.repository.UserPreviousRelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceL {
    @Autowired
    PointNodeRepository pointNodeRepository;
    @Autowired
    PreviousRelationshipRepository prp;
    @Autowired
    UserPointNodeRepository userpointNodeRepository;
    @Autowired
    UserPreviousRelationshipRepository uprp;


    //1、得到某个领域的知识图谱，包括离散点
    public Map<String, Object> getMapByArea(String area)
    {
        Map<String, Object> retMap = new HashMap<>();
        Set<Map<String ,Object>> edgeList = new HashSet<>();
        Set<Map<String ,Object>> nodeList = new HashSet<>();//因为是set类型，可以自动去掉重复
        //得到有关系的点
        List<PreviousRelationship> list = prp.getRelationshipByPArea(area);
        for (PreviousRelationship r : list) {
            /*关系*/
            Map<String, Object> map = new HashMap<>();
            map.put("edgeId", r.getId());//关系的id
            map.put("edgeFrom", r.getStartNode().getId());//开始节点的id
            map.put("edgeTo", r.getEndNode().getId());//结束节点的Id
            edgeList.add(map);

            /*节点*/
            Map<String, Object> startNode = new HashMap<>();
            //开始节点的名称
            startNode.put("nodeName", r.getStartNode().getpName());
            startNode.put("nodeId", r.getStartNode().getId());
            nodeList.add(startNode);

            Map<String, Object> endNode = new HashMap<>();
            //结束节点的名称
            endNode.put("nodeName", r.getEndNode().getpName());
            endNode.put("nodeId", r.getEndNode().getId());
            nodeList.add(endNode);
        }

        //得到离群的点
        List<PointNode> points = pointNodeRepository.findPointNodesByPAreaIs(area);
        for(PointNode p:points)
        {
            Map<String, Object> pNode = new HashMap<>();
            //节点的名称
            pNode.put("nodeName",p.getpName());
            pNode.put("nodeId", p.getId());
            nodeList.add(pNode);
        }

        retMap.put("nodeList",nodeList);
        retMap.put("edgeList",edgeList);
        return retMap;
    }
    //2、查询某个用户某个领域的知识图谱
    public Map<String, Object> getMapByAreaUser(String area,Long uId)
    {
        Map<String, Object> retMap = new HashMap<>();
        Set<Map<String ,Object>> edgeList = new HashSet<>();
        Set<Map<String ,Object>> nodeList = new HashSet<>();//因为是set类型，可以自动去掉重复
        //得到有关系的点
        List<UserPreviousRelationship> list = uprp.getRelationshipByUIdAndArea(uId,area);
        for (UserPreviousRelationship r : list) {
            /*关系*/
            Map<String, Object> map = new HashMap<>();
            map.put("edgeId", r.getId());//关系的id
            map.put("edgeFrom", r.getStartNode().getId());//开始节点的id
            map.put("edgeTo", r.getEndNode().getId());//结束节点的Id
            edgeList.add(map);

            /*节点*/
            Map<String, Object> startNode = new HashMap<>();
            //开始节点的名称
            startNode.put("nodeName", r.getStartNode().getpName());
            startNode.put("nodeStatus", r.getStartNode().getUpStatus());//用户知识点的学习状态
            startNode.put("nodeId", r.getStartNode().getId());
            nodeList.add(startNode);

            Map<String, Object> endNode = new HashMap<>();
            //结束节点的名称
            endNode.put("nodeName", r.getEndNode().getpName());
            endNode.put("nodeStatus", r.getEndNode().getUpStatus());//用户知识点的学习状态
            endNode.put("nodeId", r.getEndNode().getId());
            nodeList.add(endNode);
        }

        //得到离群的点
        List<UserPointNode> points = userpointNodeRepository.findUserPointNodesByPAreaIsAndUIdIs(area,uId);
        for(UserPointNode p:points)
        {
            Map<String, Object> pNode = new HashMap<>();
            //节点的名称
            pNode.put("nodeName",p.getpName());
            pNode.put("nodeStatus", p.getUpStatus());//用户知识点的学习状态
            pNode.put("nodeId", p.getId());
            nodeList.add(pNode);
        }

        retMap.put("nodeList",nodeList);
        retMap.put("edgeList",edgeList);
        return retMap;
    }



}
