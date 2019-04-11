package com.bigdata.olearn.neo;

import org.neo4j.ogm.annotation.*;

import java.io.Serializable;

@RelationshipEntity(type = "JOBPREVIOUS")
public class JobPreviousRelationship implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private JobPointNode startNode;

    @EndNode
    private JobPointNode endNode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JobPointNode getStartNode() {
        return startNode;
    }

    public void setStartNode(JobPointNode startNode) {
        this.startNode = startNode;
    }

    public JobPointNode getEndNode() {
        return endNode;
    }

    public void setEndNode(JobPointNode endNode) {
        this.endNode = endNode;
    }
}
