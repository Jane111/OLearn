package com.bigdata.olearn.repository;


import com.bigdata.olearn.neo.CalledRelationship;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalledRelationshipRepository extends Neo4jRepository<CalledRelationship,Long> {

}
