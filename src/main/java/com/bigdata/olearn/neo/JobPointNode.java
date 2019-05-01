package com.bigdata.olearn.neo;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

import java.io.Serializable;

/*
* 岗位子图中的知识点，岗位能力素养图的生成策略：包含知识点的所有子图
* */
@NodeEntity(label = "jobpoint")
public class JobPointNode implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String pName;//知识点名称,如C语言
    private String pArea;//知识点所在的领域，如“计算机”
    private Long pNameId;//知识点对应Id（对应MYSQL）
    private Long pAreaId;//知识点所在领域对应的Id（对应MYSQL）
    private String jpJName;//岗位名称
    private Long jpJId;//岗位Id

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getpName() {
        return pName;
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

    public String getJpJName() {
        return jpJName;
    }

    public void setJpJName(String jpJName) {
        this.jpJName = jpJName;
    }

    public Long getJpJId() {
        return jpJId;
    }

    public void setJpJId(Long jpJId) {
        this.jpJId = jpJId;
    }
}
