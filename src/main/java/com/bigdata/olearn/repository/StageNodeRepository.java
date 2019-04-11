package com.bigdata.olearn.repository;

import com.bigdata.olearn.neo.StageNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StageNodeRepository extends Neo4jRepository<StageNode,Long> {
}
