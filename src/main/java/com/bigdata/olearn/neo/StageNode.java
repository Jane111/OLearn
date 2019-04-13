package com.bigdata.olearn.neo;

import org.neo4j.ogm.annotation.Relationship;

public class StageNode {

    private String pName;//阶段名称,如C语言初阶

    @Relationship(type = "Belong", direction = Relationship.OUTGOING)
    public PointNode point;

}
