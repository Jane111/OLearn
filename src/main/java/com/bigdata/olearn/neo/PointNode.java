package com.bigdata.olearn.neo;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

import java.io.Serializable;

@NodeEntity()
public class PointNode implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String pName;//知识点名称,如C语言

    public Long getId() {
        return id;
    }

    public String getpName() {
        return pName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }
}
