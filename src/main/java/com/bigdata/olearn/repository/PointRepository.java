package com.bigdata.olearn.repository;

import com.bigdata.olearn.neo.PointNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends Neo4jRepository<PointNode,Long> {
}
