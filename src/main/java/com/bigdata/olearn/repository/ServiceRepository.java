package com.bigdata.olearn.repository;



import com.bigdata.olearn.neo.ServiceNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends Neo4jRepository<ServiceNode,Long> {

}
