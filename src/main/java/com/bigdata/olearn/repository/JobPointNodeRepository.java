package com.bigdata.olearn.repository;

import com.bigdata.olearn.neo.JobPointNode;
import com.bigdata.olearn.neo.UserPointNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobPointNodeRepository extends Neo4jRepository<JobPointNode,Long> {
   /*
    * 根据jpJId得到对应的jobpointnode
    * */
    List<JobPointNode> findJobPointNodeByJpJIdIs(Long jpId);
}
