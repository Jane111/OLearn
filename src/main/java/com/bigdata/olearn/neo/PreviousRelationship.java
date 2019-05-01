package com.bigdata.olearn.neo;

import org.neo4j.ogm.annotation.*;

import java.io.Serializable;

@RelationshipEntity(type = "PREVIOUS")
public class PreviousRelationship implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private PointNode startNode;

    @EndNode
    private PointNode endNode;

    public Long getId() {
        return id;
    }

    public PointNode getStartNode() {
        return startNode;
    }

    public PointNode getEndNode() {
        return endNode;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStartNode(PointNode startNode) {
        this.startNode = startNode;
    }

    public void setEndNode(PointNode endNode) {
        this.endNode = endNode;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        PreviousRelationship pn = (PreviousRelationship) o;
//
//        return id.equals(pn.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return id.hashCode();
//    }
}
