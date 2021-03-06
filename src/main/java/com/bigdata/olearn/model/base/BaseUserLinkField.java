package com.bigdata.olearn.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

import java.math.BigInteger;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseUserLinkField<M extends BaseUserLinkField<M>> extends Model<M> implements IBean {

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

    public void setNickname(String nickname) {
        set("nickname", nickname);
    }

    public String getNickname() {
        return get("nickname");
    }

    public void setFieldId(BigInteger fieldId) {
        set("field_id", fieldId);
    }

    public BigInteger getFieldId() {
        return get("field_id");
    }

    public void setFieldname(String fieldname) {
        set("fieldname", fieldname);
    }

    public String getFieldname() {
        return get("fieldname");
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
