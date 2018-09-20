package com.classteacher.common.model;

import java.io.Serializable;
import java.util.Date;

public class SubjectTopicModule implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1532961976673023415L;
	public static final int STATUS_ACTIVE = 1;
	public static final int STATUS_INACTIVE = 0;
	private int id;
	private int topic_module_id;
	private long module_id;
	private int topic_id;
	private String url_in_cms;
	private String content_type;
	private String module_grading;
	private String description;
	private int user_id;
	private Date created_date;
	private Date last_updated_date;
	
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTopic_module_id() {
		return topic_module_id;
	}
	public void setTopic_module_id(int topic_module_id) {
		this.topic_module_id = topic_module_id;
	}
	public long getModule_id() {
		return module_id;
	}
	public void setModule_id(long module_id) {
		this.module_id = module_id;
	}
	public int getTopic_id() {
		return topic_id;
	}
	public void setTopic_id(int topic_id) {
		this.topic_id = topic_id;
	}
	public String getUrl_in_cms() {
		return url_in_cms;
	}
	public void setUrl_in_cms(String url_in_cms) {
		this.url_in_cms = url_in_cms;
	}
	public String getContent_type() {
		return content_type;
	}
	public void setContent_type(String content_type) {
		this.content_type = content_type;
	}
	public String getModule_grading() {
		return module_grading;
	}
	public void setModule_grading(String module_grading) {
		this.module_grading = module_grading;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreated_date() {
		return created_date;
	}
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}
	public Date getLast_updated_date() {
		return last_updated_date;
	}
	public void setLast_updated_date(Date last_updated_date) {
		this.last_updated_date = last_updated_date;
	}
	public static int getStatusActive() {
		return STATUS_ACTIVE;
	}
	public static int getStatusInactive() {
		return STATUS_INACTIVE;
	}



}
