package com.techwells.wumei.domain;

import java.util.Date;

public class Aaa {
	private Integer aaaId;
	private String aaaName;
	private String aaaIcon;
	private Date createdDate;
	private Date updatedDate;

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Integer getAaaId() {
		return aaaId;
	}

	public void setAaaId(Integer aaaId) {
		this.aaaId = aaaId;
	}

	public String getAaaName() {
		return aaaName;
	}

	public void setAaaName(String aaaName) {
		this.aaaName = aaaName;
	}

	public String getAaaIcon() {
		return aaaIcon;
	}

	public void setAaaIcon(String aaaIcon) {
		this.aaaIcon = aaaIcon;
	}

}
