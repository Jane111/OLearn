package com.bigdata.olearn.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

import java.math.BigInteger;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseMooc<M extends BaseMooc<M>> extends Model<M> implements IBean {

	public void setMoocId(BigInteger moocId) {
		set("mooc_id", moocId);
	}

	public BigInteger getMoocId() {
		return get("mooc_id");
	}

	public void setTitle(String title) {
		set("title", title);
	}

	public String getTitle() {
		return get("title");
	}

	public void setTitleUrl(String titleUrl) {
		set("title_url", titleUrl);
	}

	public String getTitleUrl() {
		return get("title_url");
	}

	public void setPicture(String picture) {
		set("picture", picture);
	}

	public String getPicture() {
		return get("picture");
	}

	public void setInstitute(String institute) {
		set("institute", institute);
	}

	public String getInstitute() {
		return get("institute");
	}

	public void setPeople(String people) {
		set("people", people);
	}

	public String getPeople() {
		return get("people");
	}

	public void setProfessor(String professor) {
		set("professor", professor);
	}

	public String getProfessor() {
		return get("professor");
	}

	public void setIntroduce(String introduce) {
		set("introduce", introduce);
	}

	public String getIntroduce() {
		return get("introduce");
	}

	public void setIdeal(String ideal) {
		set("ideal", ideal);
	}

	public String getIdeal() {
		return get("ideal");
	}

	public void setMenu(String menu) {
		set("menu", menu);
	}

	public String getMenu() {
		return get("menu");
	}

	public void setPrestudy(String prestudy) {
		set("prestudy", prestudy);
	}

	public String getPrestudy() {
		return get("prestudy");
	}

	public void setReference(String reference) {
		set("reference", reference);
	}

	public String getReference() {
		return get("reference");
	}

	public void setQuto(String quto) {
		set("quto", quto);
	}

	public String getQuto() {
		return get("quto");
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
