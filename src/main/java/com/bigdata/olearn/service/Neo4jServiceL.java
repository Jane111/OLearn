package com.bigdata.olearn.service;

import com.bigdata.olearn.model.MoocCluster;
import com.bigdata.olearn.neo.PointNode;
import com.bigdata.olearn.neo.PreviousRelationship;
import com.bigdata.olearn.repository.PointNodeRepository;
import com.bigdata.olearn.repository.PreviousRelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class Neo4jServiceL {
    @Autowired
    PointNodeRepository pointNodeRepository;
    @Autowired
    PreviousRelationshipRepository prp;

    public Map<String, Object> getJobMap(List<String> jobPointList)
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

        retMap.put("nodeList",nodeList);
        retMap.put("edgeList",edgeList);
        return retMap;


    }


}
