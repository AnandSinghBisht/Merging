package com.classteacher.common.model;

import java.io.Serializable;

public class SubjectTopic implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int STATUS_ACTIVE = 1;
	public static final int STATUS_INACTIVE = 0;
	private int id;
	private int topic_id;
	private boolean isActive;
	
	private String description;
	
	private int parent_topic_id;
	private String topic_name;
	private int board_class_id;
	private int topic_level;
	private String module_name;
	private String module_description;
	private String url_in_cms;
	private String content_type;
	private String module_grading;
	private int subject_id;
	private int user_id;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTopic_id() {
		return topic_id;
	}
	public void setTopic_id(int topic_id) {
		this.topic_id = topic_id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getParent_topic_id() {
		return parent_topic_id;
	}
	public void setParent_topic_id(int parent_topic_id) {
		this.parent_topic_id = parent_topic_id;
	}
	public String getTopic_name() {
		return topic_name;
	}
	public void setTopic_name(String topic_name) {
		this.topic_name = topic_name;
	}
	public int getBoard_class_id() {
		return board_class_id;
	}
	public void setBoard_class_id(int board_class_id) {
		this.board_class_id = board_class_id;
	}
	public int getTopic_level() {
		return topic_level;
	}
	public void setTopic_level(int topic_level) {
		this.topic_level = topic_level;
	}
	public String getModule_name() {
		return module_name;
	}
	public void setModule_name(String module_name) {
		this.module_name = module_name;
	}
	public String getModule_description() {
		return module_description;
	}
	public void setModule_description(String module_description) {
		this.module_description = module_description;
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
	public int getSubject_id() {
		return subject_id;
	}
	public void setSubject_id(int subject_id) {
		this.subject_id = subject_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
 	
	
	

}
