package com.bigdata.olearn.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

import java.math.BigInteger;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseAbility<M extends BaseAbility<M>> extends Model<M> implements IBean {
    public void setId(BigInteger id) {
        set("id", id);
    }

    public BigInteger getId() {
        return get("id");
    }
    public void setUserId(BigInteger userId) {
        set("user_id", userId);
    }

    public BigInteger getUserId() {
        return get("user_id");
    }
    public void setClusterId(BigInteger clusterId) {
        set("cluster_id", clusterId);
    }

    public BigInteger getClusterId() {
        return get("cluster_id");
    }

    public void setRank(Integer rank) {
        set("rank", rank);
    }
    public void setClassname(String classname) {
        set("classname", classname);
    }

    public String getClassname() {
        return get("classname");
    }

    public Integer getRank() {
        return get("rank");
    }

    public void setCreatetime(java.util.Date createtime) {
        set("createtime", createtime);
    }

    public java.util.Date getCreatetime() {
        return get("createtime");
    }

    public void setModifytime(java.util.Date modifytime) {
        set("modifytime", modifytime);
    }

    public java.util.Date getModifytime() {
        return get("modifytime");
    }
}
