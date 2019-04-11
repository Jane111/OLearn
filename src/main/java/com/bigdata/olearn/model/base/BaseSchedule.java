package com.bigdata.olearn.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseSchedule<M extends BaseSchedule<M>> extends Model<M> implements IBean {

	public void setId(java.math.BigInteger id) {
		set("id", id);
	}

	public java.math.BigInteger getId() {
		return get("id");
	}

	public void setUserId(Long userId) {
		set("user_id", userId);
	}

	public Long getUserId() {
		return get("user_id");
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

	public void setMoocId(Long moocId) {
		set("mooc_id", moocId);
	}

	public Long getMoocId() {
		return get("mooc_id");
	}

	public void setMoocName(String moocName) {
		set("mooc_name", moocName);
	}

	public String getMoocName() {
		return get("mooc_name");
	}

	public void setRank(Integer rank) {
		set("rank", rank);
	}

	public Integer getRank() {
		return get("rank");
	}

	public void setMenuId(Long menuId) {
		set("menu_id", menuId);
	}

	public Long getMenuId() {
		return get("menu_id");
	}

	public void setLesson(String lesson) {
		set("lesson", lesson);
	}

	public String getLesson() {
		return get("lesson");
	}

	public void setSequence(Integer sequence) {
		set("sequence", sequence);
	}

	public Integer getSequence() {
		return get("sequence");
	}

	public void setStatus(Integer status) {
		set("status", status);
	}

	public Integer getStatus() {
		return get("status");
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
