package com.bigdata.olearn.neo;

import org.neo4j.ogm.annotation.*;

import java.io.Serializable;

@RelationshipEntity(type = "USERPREVIOUS")
public class UserPreviousRelationship implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private UserPointNode startNode;

    @EndNode
    private UserPointNode endNode;

    public Long getId() {
        return id;
    }

    public UserPointNode getStartNode() {
        return startNode;
    }

    public UserPointNode getEndNode() {
        return endNode;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStartNode(UserPointNode startNode) {
        this.startNode = startNode;
    }

    public void setEndNode(UserPointNode endNode) {
        this.endNode = endNode;
    }
}
