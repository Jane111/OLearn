package com.bigdata.olearn.neo;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

import java.io.Serializable;

@NodeEntity(label = "stage")
public class StageNode implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String sName;//阶段名称,如C语言初阶

    public Long getId() {
        return id;
    }

    public String getsName() {
        return sName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }
}
