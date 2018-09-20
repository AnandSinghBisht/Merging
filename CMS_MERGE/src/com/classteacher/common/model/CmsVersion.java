package com.classteacher.common.model;

import java.io.Serializable;
import java.util.Date;

public class CmsVersion implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5163287268961533820L;
	
	private Integer id;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private String versionName;
	
	private String description;
	
	private Date startDate;
	
	private Date endDate;
	
	

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	

}
