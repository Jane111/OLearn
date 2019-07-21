package com.bigdata.olearn.neo;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

import java.io.Serializable;

@NodeEntity(label = "userpoint")
public class UserPointNode implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String pName;//知识点名称,如C语言
    private Long pNameId;
    private String pArea;//知识点所在的领域，如“计算机”
    private Long pAreaId;
    private Long uId;//用户Id
    private Integer upStatus;//用户知识点的状态，0学习完成/1正在学习/2推荐学习/3未学习
//推荐学习的规则，正在学习的相对高阶课程学完推荐下一个知识点，孤立知识点均为未学习
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

    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

    public Integer getUpStatus() {
        return upStatus;
    }

    public void setUpStatus(Integer upStatus) {
        this.upStatus = upStatus;
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

        UserPointNode upn = (UserPointNode) o;

        return id.equals(upn.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
