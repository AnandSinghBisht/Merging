package com.classteacher.common.model;

import java.io.Serializable;
import java.util.Date;


public class Module implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -239329146946139263L;
	public static final int STATUS_ACTIVE = 1;
	public static final int STATUS_INACTIVE = 0;
		private int module_id;
		private Date created_date;
		private Date last_modified_date;

		private String module_name;
		private String module_descr;
		private int resource_type_id;
		private String tags;
		private int user_id;
		private String Board;
		private String is_active;
		private String content_url;
		private String thumbnail_url;
		private int content_id;
		private int thumbnail_id;
		private int resource_grade_id;
		private int resource_animation_id;
		private int resource_platform_id;
		private int topicId;
		private String topicName;
		private int subjectId;
		private String subjectName;
		private Integer topicLevelId;
		
		
		private Long fileId;
		
		
		
		public int getSubjectId() {
			return subjectId;
		}
		public void setSubjectId(int subjectId) {
			this.subjectId = subjectId;
		}
		public String getSubjectName() {
			return subjectName;
		}
		public void setSubjectName(String subjectName) {
			this.subjectName = subjectName;
		}
		public String getTopicName() {
			return topicName;
		}
		public void setTopicName(String topicName) {
			this.topicName = topicName;
		}
		public int getModule_id() {
			return module_id;
		}
		public void setModule_id(int module_id) {
			this.module_id = module_id;
		}
		public Date getCreated_date() {
			return created_date;
		}
		public void setCreated_date(Date created_date) {
			this.created_date = created_date;
		}
		public Date getLast_modified_date() {
			return last_modified_date;
		}
		public void setLast_modified_date(Date last_modified_date) {
			this.last_modified_date = last_modified_date;
		}
		public String getModule_name() {
			return module_name;
		}
		public void setModule_name(String module_name) {
			this.module_name = module_name;
		}
		public String getModule_descr() {
			return module_descr;
		}
		public void setModule_descr(String module_descr) {
			this.module_descr = module_descr;
		}
		public int getResource_type_id() {
			return resource_type_id;
		}
		public void setResource_type_id(int resource_type_id) {
			this.resource_type_id = resource_type_id;
		}
		public String getTags() {
			return tags;
		}
		public void setTags(String tags) {
			this.tags = tags;
		}
		public int getUser_id() {
			return user_id;
		}
		public void setUser_id(int user_id) {
			this.user_id = user_id;
		}
		public String getBoard() {
			return Board;
		}
		public void setBoard(String board) {
			Board = board;
		}
		public String getIs_active() {
			return is_active;
		}
		public void setIs_active(String is_active) {
			this.is_active = is_active;
		}
		public String getContent_url() {
			return content_url;
		}
		public void setContent_url(String content_url) {
			this.content_url = content_url;
		}
		public String getThumbnail_url() {
			return thumbnail_url;
		}
		public void setThumbnail_url(String thumbnail_url) {
			this.thumbnail_url = thumbnail_url;
		}
		public int getContent_id() {
			return content_id;
		}
		public void setContent_id(int content_id) {
			this.content_id = content_id;
		}
		public int getThumbnail_id() {
			return thumbnail_id;
		}
		public void setThumbnail_id(int thumbnail_id) {
			this.thumbnail_id = thumbnail_id;
		}
		public int getResource_grade_id() {
			return resource_grade_id;
		}
		public void setResource_grade_id(int resource_grade_id) {
			this.resource_grade_id = resource_grade_id;
		}
		public int getResource_animation_id() {
			return resource_animation_id;
		}
		public void setResource_animation_id(int resource_animation_id) {
			this.resource_animation_id = resource_animation_id;
		}
		public int getResource_platform_id() {
			return resource_platform_id;
		}
		public void setResource_platform_id(int resource_platform_id) {
			this.resource_platform_id = resource_platform_id;
		}
		public Long getFileId() {
			return fileId;
		}
		public void setFileId(Long fileId) {
			this.fileId = fileId;
		}
		public int getTopicId() {
			return topicId;
		}
		public void setTopicId(int topicId) {
			this.topicId = topicId;
		}
		public Integer getTopicLevelId() {
			return topicLevelId;
		}
		public void setTopicLevelId(Integer topicLevelId) {
			this.topicLevelId = topicLevelId;
		}

		












}
