package com.bigdata.olearn.repository;

import com.bigdata.olearn.neo.PointNode;
import com.bigdata.olearn.neo.UserPointNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPointNodeRepository extends Neo4jRepository<UserPointNode,Long> {
    List<UserPointNode> findUserPointNodesByPAreaIsAndUIdIs(String pArea,Long uId);//根据pArea and uId找到对应的point

}
