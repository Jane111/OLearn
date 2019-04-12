package com.bigdata.olearn.repository;


import com.bigdata.olearn.neo.PreviousRelationship;
import com.bigdata.olearn.neo.UserPreviousRelationship;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface UserPreviousRelationshipRepository extends Neo4jRepository<UserPreviousRelationship,Long> {

    /**
     * 查询知识图谱，根据用户Id
     * @param uId -- 用户Id
     * @param area -- 领域名称
     * @return
     */
    @Query("match p = (n:userpoint{uId:{0},pArea:{1}})-[r:USERPREVIOUS]->(b) return p")
    List<UserPreviousRelationship> getRelationshipByUIdAndArea(Long uId,String area);

    /**
     * 查询知识图谱，根据用户Id
     * @param uId -- 用户Id
     * @param areaId -- 领域Id
     * @return
     */
    @Query("match p = (n:userpoint{uId:{0},pAreaId:{1}})-[r:USERPREVIOUS]->(b) return p")
    List<UserPreviousRelationship> getRelationshipByUIdAndAreaId(Long uId,Long areaId);



    /**
     * 为两个已经存在的知识点添加关系
     * @param startNodePName -- 起始知识点名称
     * @param endNodePName   -- 终止知识点名称
     * @return
     */
    @Query("match(a:userpoint),(b:userpoint) where a.pName={0} and b.pName = {1}"
            + " create p = (a)-[r:USERPREVIOUS]->(b) return p ")
    UserPreviousRelationship createUserPrevious(String startNodePName, String endNodePName);

}
