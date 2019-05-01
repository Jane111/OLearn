package com.bigdata.olearn.repository;


import com.bigdata.olearn.neo.JobPreviousRelationship;
import com.bigdata.olearn.neo.UserPreviousRelationship;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobPreviousRelationshipRepository extends Neo4jRepository<JobPreviousRelationship,Long> {

    /**
     * 查询知识图谱，根据岗位Id
     * @param jpId -- 岗位Id
     * @return
     */
    @Query("match p = (n:jobpoint{jpId:{0}})-[r:JOBPREVIOUS]->(b) return p")
    List<JobPreviousRelationship> getRelationshipByUIdAndArea(Long jpId);


    /**
     * 为两个已经存在的知识点添加关系
     * @param startNodePName -- 起始知识点名称
     * @param endNodePName   -- 终止知识点名称
     * @return
     */
    @Query("match(a:jobpoint),(b:jobpoint) where a.pName={0} and b.pName = {1}"
            + " create p = (a)-[r:JOBPREVIOUS]->(b) return p ")
    JobPreviousRelationship createJobPrevious(String startNodePName, String endNodePName);

}
