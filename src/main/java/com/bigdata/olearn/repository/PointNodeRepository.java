package com.bigdata.olearn.repository;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bigdata.olearn.neo.PointNode;
import com.bigdata.olearn.neo.StageNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointNodeRepository extends Neo4jRepository<PointNode,Long> {

    //根据pArea找到对应的point
    List<PointNode> findPointNodesByPAreaIs(String pArea);

    //根据pAreaId找到对应的point
    List<PointNode> findPointNodesByPAreaIdIs(Long pAreaId);

    //根据pName找到对应的point
    PointNode findPointNodeByPNameIs(String pName);

    //根据pNameId找到对应的point
    PointNode findPointNodeByPNameIdIs(Long pNameId);
    /**
     * 根据point的pname得到其所有的前导课程
     * @param pName -- 知识点的名称
     * @return
     */
    @Query("match (a:point{pName:{0}})-[r*]->(b:point) return b")
    List<PointNode> getPreviousPointByPName(String pName);

    /**
     * 根据point的pnameId得到其所有的前导课程
     * @param pNameId -- 知识点的名称Id
     * @return
     */
    @Query("match (a:point{pNameId:{0}})-[r*]->(b:point) return b")
    List<PointNode> getPreviousPointByPNameId(Long pNameId);


    /**
     * 根据point的pname得到其所有的前导课程和其本身
     * @param pName -- 知识点的名称
     * @return
     */
    @Query("match (a:point{pName:{0}})-[r*]->(b:point) return a,b")
    List<PointNode> getPreviousPointAndPointByPName(String pName);




}
