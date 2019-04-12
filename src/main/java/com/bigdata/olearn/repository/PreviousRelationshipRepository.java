package com.bigdata.olearn.repository;


import com.bigdata.olearn.neo.PointNode;
import com.bigdata.olearn.neo.PreviousRelationship;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreviousRelationshipRepository extends Neo4jRepository<PreviousRelationship,Long> {

    /**
     * 根据领域，查询知识图谱的关系和有关系的节点
     * @param pArea -- 领域名称
     * @return
     */
    @Query("match p = (a:point{pArea:{0}})-[r:PREVIOUS]->(b) return p")
    List<PreviousRelationship> getRelationshipByPArea(String pArea);

    /**
     * 根据领域，查询知识图谱的关系和有关系的节点
     * @param pAreaId -- 领域Id
     * @return
     */
    @Query("match p = (a:point{pAreaId:{0}})-[r:PREVIOUS]->(b) return p")
    List<PreviousRelationship> getRelationshipByPAreaId(Long pAreaId);


    /**
     * 为两个已经存在的知识点添加关系
     * @param startNodePName -- 起始知识点名称
     * @param endNodePName   -- 终止知识点名称
     * @return
     */
    @Query("match(a:point),(b:point) where a.pName={0} and b.pName = {1}"
            + " create p = (a)-[r:PREVIOUS]->(b) return p ")
    PreviousRelationship createPrevious(String startNodePName,String endNodePName);

    /**
     * 根据point的pname得到其所有的前导关系
     * @param startNodePName -- 起始知识点的名称
     * @return
     */
    @Query("match p = (a:point{pName:{0}})-[r:PREVIOUS]->(b:point) return p")
    List<PreviousRelationship> getPreviousRelationshipByPName(String startNodePName);

    /**
     * 根据point的pnameId得到其所有的前导关系
     * @param startNodePNameId -- 起始知识点的名称
     * @return
     */
    @Query("match p = (a:point{pNameId:{0}})-[r:PREVIOUS]->(b:point) return p")
    List<PreviousRelationship> getPreviousRelationshipByPNameId(Long startNodePNameId);


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
