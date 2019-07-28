package com.bigdata.olearn.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

import java.math.BigInteger;

public abstract class BaseUserLinkWork<M extends BaseUserLinkWork<M>> extends Model<M> implements IBean {

    public void setId(BigInteger id) {
        set("id", id);
    }

    public BigInteger getId() {
        return get("id");
    }

    public void setUserId(BigInteger userId) { set("user_id", userId); }

    public BigInteger getUserId() {
        return get("user_id");
    }

    public void setWorkClusterId(BigInteger workClusterId) { set("work_cluster_id", workClusterId); }

    public BigInteger getWorkClusterId() {
        return get("work_cluster_id");
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
