package com.bigdata.olearn.service;

import com.bigdata.olearn.model.Field;
import com.bigdata.olearn.model.MoocCluster;
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

import java.math.BigInteger;
import java.util.*;

@Service
public class Neo4jServiceL {
    @Autowired
    PointNodeRepository pointNodeRepository;
    @Autowired
    PreviousRelationshipRepository prp;
    @Autowired
    UserPointNodeRepository userPointNodeRepository;
    @Autowired
    UserPreviousRelationshipRepository uprp;

    //1、得到某个领域的知识图谱，包括离散点
    public Map<String, Object> getMapByArea(BigInteger areaId)
    {
        Map<String, Object> retMap = new HashMap<>();
        Set<Map<String ,Object>> edgeList = new HashSet<>();
        Set<Map<String ,Object>> nodeList = new HashSet<>();//因为是set类型，可以自动去掉重复
        //得到有关系的点
        List<PreviousRelationship> list = prp.getRelationshipByPAreaId(areaId.longValue());
        for (PreviousRelationship r : list) {
            /*关系*/
            Map<String, Object> map = new HashMap<>();
            map.put("edgeId", r.getId());//关系的id
            map.put("edgeFrom", r.getStartNode().getpNameId());//开始节点的id
            map.put("edgeTo", r.getEndNode().getpNameId());//结束节点的Id
            edgeList.add(map);

            /*节点*/
            Map<String, Object> startNode = new HashMap<>();
            //开始节点的名称
            startNode.put("nodeName", r.getStartNode().getpName());
            startNode.put("nodeId", r.getStartNode().getId());
            startNode.put("pNameId", r.getStartNode().getpNameId());
            startNode.put("pAreaId", r.getStartNode().getpAreaId());
            nodeList.add(startNode);

            Map<String, Object> endNode = new HashMap<>();
            //结束节点的名称
            endNode.put("nodeName", r.getEndNode().getpName());
            endNode.put("nodeId", r.getEndNode().getId());
            endNode.put("pNameId", r.getEndNode().getpNameId());
            endNode.put("pAreaId", r.getEndNode().getpAreaId());
            nodeList.add(endNode);
        }

        //得到离群的点
        List<PointNode> points = pointNodeRepository.findPointNodesByPAreaIdIs(areaId.longValue());
        for(PointNode p:points)
        {
            Map<String, Object> pNode = new HashMap<>();
            //节点的名称
            pNode.put("nodeName",p.getpName());
            pNode.put("nodeId", p.getId());
            pNode.put("pNameId", p.getpNameId());
            pNode.put("pAreaId", p.getpAreaId());
            nodeList.add(pNode);
        }

        retMap.put("nodeList",nodeList);
        retMap.put("edgeList",edgeList);
        return retMap;
    }
    //2、查询某个用户某个领域的知识图谱
    public Map<String, Object> getMapByAreaUser(Long areaId,Long uId)
    {
        Map<String, Object> retMap = new HashMap<>();
        Set<Map<String ,Object>> edgeList = new HashSet<>();
        Set<Map<String ,Object>> nodeList = new HashSet<>();//因为是set类型，可以自动去掉重复
        //得到有关系的点
        List<UserPreviousRelationship> list = uprp.getRelationshipByUIdAndAreaId(uId, areaId.longValue());
        for (UserPreviousRelationship r : list) {
            /*关系*/
            Map<String, Object> map = new HashMap<>();
            map.put("edgeId", r.getId());//关系的id
            map.put("edgeFrom", r.getStartNode().getpNameId());//开始节点的id
            map.put("edgeTo", r.getEndNode().getpNameId());//结束节点的Id
            edgeList.add(map);

            /*节点*/
            Map<String, Object> startNode = new HashMap<>();
            //开始节点的名称
            startNode.put("nodeName", r.getStartNode().getpName());
            startNode.put("nodeStatus", r.getStartNode().getUpStatus());//用户知识点的学习状态
            startNode.put("nodeId", r.getStartNode().getId());
            startNode.put("pNameId", r.getStartNode().getpNameId());
            startNode.put("pAreaId", r.getStartNode().getpAreaId());
            nodeList.add(startNode);

            Map<String, Object> endNode = new HashMap<>();
            //结束节点的名称
            endNode.put("nodeName", r.getEndNode().getpName());
            endNode.put("nodeStatus", r.getEndNode().getUpStatus());//用户知识点的学习状态
            endNode.put("nodeId", r.getEndNode().getId());
            endNode.put("pNameId", r.getEndNode().getpNameId());
            endNode.put("pAreaId", r.getEndNode().getpAreaId());
            nodeList.add(endNode);
        }

        //得到离群的点
        List<UserPointNode> points = userPointNodeRepository.findUserPointNodesByPAreaIdIsAndUIdIs(areaId.longValue(),uId);
        for(UserPointNode p:points)
        {
            Map<String, Object> pNode = new HashMap<>();
            //节点的名称
            pNode.put("nodeName",p.getpName());
            pNode.put("nodeStatus", p.getUpStatus());//用户知识点的学习状态
            pNode.put("nodeId", p.getId());
            pNode.put("pNameId", p.getpNameId());
            pNode.put("pAreaId", p.getpAreaId());
            nodeList.add(pNode);
        }

        retMap.put("nodeList",nodeList);
        retMap.put("edgeList",edgeList);
        return retMap;
    }

    //3、创建属于某个用户的领域知识图谱
    public void createMapForUser(BigInteger uId,BigInteger areaId)
    {
        //参数：用户账号，领域名称

        //1、首先存储userpointnode，根据领域得到所有的Point
        List<PointNode> pointNodeList = pointNodeRepository.findPointNodesByPAreaIdIs(areaId.longValue());
        for(PointNode pn:pointNodeList)
        {
            UserPointNode userPointNode = new UserPointNode();
            userPointNode.setpArea(pn.getpArea());
            userPointNode.setpName(pn.getpName());
            userPointNode.setpAreaId(pn.getpAreaId());
            userPointNode.setpNameId(pn.getpNameId());
            userPointNode.setuId(uId.longValue());//用户Id
            userPointNode.setUpStatus(3);//默认均为3未学习
            UserPointNode save = userPointNodeRepository.save(userPointNode);
        }
        //得到所有的point的Relationship,将其作为userpointRelationship进行处理
        List<PreviousRelationship> previousRelationshipList = prp.getRelationshipByPArea("计算机");
        for(PreviousRelationship pr:previousRelationshipList)
        {
            UserPreviousRelationship saveUserPrevious = uprp.createUserPrevious(pr.getStartNode().getpName(),pr.getEndNode().getpName());
        }
    }

    /*
    * 岗位点在图数据库中不真实存在，现场读取
    * */
    /*
    * 1、得到岗位对应的知识点
    * 2、得到知识点所对应的所有前置节点集合（保证用户可以从某个点开始学习）
    * 3、将集合内数据进行拼接,岗位所在的领域
    * */
    public Map<String, Object> getJobMap(String[] jobPointList)
    {

        Map<String, Object> retMap = new HashMap<>();
        Set<Map<String ,Object>> edgeList = new HashSet<>();
        Set<Map<String ,Object>> nodeList = new HashSet<>();//因为是set类型，可以自动去掉重复


        Set<PointNode> jobPointSet = new HashSet<>();//知识点集合
        for(String jobPoint:jobPointList)
        {
            List<PointNode> pointNodes = pointNodeRepository.getPreviousPointAndPointByPName(jobPoint);
            if(pointNodes.size()==0)//通过关系得不到的点
            {
                jobPointSet.add(pointNodeRepository.findPointNodeByPNameIs(jobPoint));
            }
            for(PointNode pn:pointNodes)
            {
                jobPointSet.add(pn);
            }
        }

        for(PointNode p:jobPointSet)
        {
            Map<String, Object> pNode = new HashMap<>();
            //节点的名称
            pNode.put("nodeName",p.getpName());
            pNode.put("pNameId",p.getpNameId());//知识点Id
            pNode.put("pAreaId",p.getpAreaId());//领域Id
            pNode.put("nodeId", p.getId());
            nodeList.add(pNode);
        }

        //得到所有已知识点为起始节点的关系
        Set<PreviousRelationship> previousRelationshipSet = new HashSet<>();//知识点集合
        for(PointNode pn:jobPointSet)
        {
            List<PreviousRelationship> previousRelationships = prp.getPreviousRelationshipByPName(pn.getpName());
            for(PreviousRelationship pr:previousRelationships)
            {
                previousRelationshipSet.add(pr);
            }
        }

        for (PreviousRelationship r : previousRelationshipSet) {
            /*关系*/
            Map<String, Object> map = new HashMap<>();
            map.put("edgeId", r.getId());//关系的id
            map.put("edgeFrom", r.getStartNode().getpNameId());//开始节点的id
            map.put("edgeTo", r.getEndNode().getpNameId());//结束节点的Id
            edgeList.add(map);

            /*节点*/
            Map<String, Object> startNode = new HashMap<>();
            //开始节点的名称
            startNode.put("nodeName", r.getStartNode().getpName());
            startNode.put("nodeId", r.getStartNode().getId());
            startNode.put("pNameId",r.getStartNode().getpNameId());//知识点Id
            startNode.put("pAreaId",r.getStartNode().getpAreaId());//领域Id
            nodeList.add(startNode);

            Map<String, Object> endNode = new HashMap<>();
            //结束节点的名称
            endNode.put("nodeName", r.getEndNode().getpName());
            endNode.put("nodeId", r.getEndNode().getId());
            endNode.put("pNameId",r.getEndNode().getpNameId());//知识点Id
            endNode.put("pAreaId",r.getEndNode().getpAreaId());//领域Id
            nodeList.add(endNode);
        }

        retMap.put("nodeList",nodeList);
        retMap.put("edgeList",edgeList);
        return retMap;
    }

    //在最初的时候调用，生成初始领域图谱
    public void createAreaMap()
    {
        List<MoocCluster> clusterList = MoocCluster.dao.find("select * from mooc_cluster");
        for(MoocCluster mc:clusterList)//创建节点
        {
            PointNode pointNode = new PointNode();
            pointNode.setpName(mc.getClassname());
            pointNode.setpArea(Field.dao.findById(mc.getFieldId()).getFieldname());
            pointNode.setpNameId(mc.getId().longValue());//知识点Id
            pointNode.setpAreaId(mc.getFieldId().longValue());//领域Id
            PointNode save = pointNodeRepository.save(pointNode);
        }
        for(MoocCluster mc:clusterList)//创建关系
        {
            String previous = mc.getPrelabel();
            previous = previous.substring(1,previous.length()-1);
            String[] previousList = previous.split(",");
            for(int i=0;i<previousList.length;i++)
            {
                PreviousRelationship save = prp.createPrevious(mc.getClassname(),previousList[i]);
            }
        }
    }
    //2、得到一个知识点的所有先导知识点，用于提示。
    public List<PointNode> getPreviousPoint(Long pNameId)
    {
        return pointNodeRepository.getPreviousPointByPNameId(pNameId);
    }


}
