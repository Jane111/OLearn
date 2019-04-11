package com.bigdata.olearn.neo;

import org.neo4j.ogm.annotation.*;

import java.io.Serializable;

@RelationshipEntity(type = "BELONG")
public class BelongRelationship implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private StageNode startNode;

    @EndNode
    private PointNode endNode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StageNode getStartNode() {
        return startNode;
    }

    public void setStartNode(StageNode startNode) {
        this.startNode = startNode;
    }

    public PointNode getEndNode() {
        return endNode;
    }

    public void setEndNode(PointNode endNode) {
        this.endNode = endNode;
    }
}
