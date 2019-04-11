package com.bigdata.olearn.repository;


import com.bigdata.olearn.neo.BelongRelationship;
import com.bigdata.olearn.neo.PointNode;
import com.bigdata.olearn.neo.PreviousRelationship;
import com.bigdata.olearn.neo.StageNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BelongRelationshipRepository extends Neo4jRepository<PreviousRelationship,Long> {

    /**
     * 根据point的pname查询stage
     * @param pName -- 知识点的名称
     * @return
     */
    @Query("match (a:stage)-[r:BELONG]->(b:point{pName:{0}}) return a")
    List<StageNode> getStageByPName(String pName);


    /**
     * 为已经存在的stage和point添加关系
     * @param startNodeSName -- 起始stage名称
     * @param endNodePName   -- 终止point名称
     * @return
     */
    @Query("match(a:stage),(b:point) where a.sName={0} and b.pName = {1}"
            + " create p = (a)-[r:BELONG]->(b) return p ")
    List<BelongRelationship> createBelong(String startNodeSName, String endNodePName);


//    /**
//     * 为两个已经存在的节点添加关系
//     * @param startNodeID -- 起始节点
//     * @param endNodeID   -- 终止节点
//     * @param rID         -- 关系的ID
//     * @param year        -- 关系的开始年限
//     * @param reason	  -- 关系产生的原因
//     * @return
//     */
//    @Query("match(a),(b) where a.uid={0} and b.uid = {1}"
//            + " create p = (a)-[r:Like{relationID:{2},since:{3},reason:{4}}]->(b) return p ")
//    List<LikeRelation> createLikes(Long startNodeID,Long endNodeID,
//                                   Integer rID,Integer year,String reason);
}
