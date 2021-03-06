package com.bigdata.olearn.neo;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

import java.io.Serializable;

@NodeEntity(label = "point")
public class PointNode implements Serializable {

    @Id @GeneratedValue
    private Long id;
    private String pName;//知识点名称,如C语言
    private String pArea;//知识点所在的领域，如“计算机”
    private Long pNameId;//知识点对应Id（对应MYSQL）
    private Long pAreaId;//知识点所在领域对应的Id（对应MYSQL）

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

    public String getpArea() {
        return pArea;
    }

    public void setpArea(String pArea) {
        this.pArea = pArea;
    }

    public Long getpNameId() {
        return pNameId;
    }

    public void setpNameId(Long pNameId) {
        this.pNameId = pNameId;
    }

    public Long getpAreaId() {
        return pAreaId;
    }

    public void setpAreaId(Long pAreaId) {
        this.pAreaId = pAreaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PointNode pn = (PointNode) o;

        return id.equals(pn.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
