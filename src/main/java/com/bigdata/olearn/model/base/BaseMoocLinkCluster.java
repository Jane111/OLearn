package com.bigdata.olearn.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

import java.math.BigInteger;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseMoocLinkCluster<M extends BaseMoocLinkCluster<M>> extends Model<M> implements IBean {

	public void setId(BigInteger id) {
		set("id", id);
	}

	public BigInteger getId() {
		return get("id");
	}

	public void setMoocId(Long moocId) {
		set("mooc_id", moocId);
	}

	public Long getMoocId() {
		return get("mooc_id");
	}

	public void setTitle(String title) {
		set("title", title);
	}

	public String getTitle() {
		return get("title");
	}

	public void setIntroduce(String introduce) {
		set("introduce", introduce);
	}

	public String getIntroduce() {
		return get("introduce");
	}

	public void setRank(Integer rank) {
		set("rank", rank);
	}

	public Integer getRank() {
		return get("rank");
	}

	public void setClusterId(Long clusterId) {
		set("cluster_id", clusterId);
	}

	public Long getClusterId() {
		return get("cluster_id");
	}

	public void setClassname(String classname) {
		set("classname", classname);
	}

	public String getClassname() {
		return get("classname");
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
