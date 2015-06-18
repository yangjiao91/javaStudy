package com.funshion.dobo.base.dao.entity;

import java.util.Date;
import java.util.List;

/**
 * @author lirui
 *
 */
public class TopicSchema {
	
	private long id;
	/** schema registry 返回的id */
	private int schemaId;
	private long topicId;
	private String topicName;
	private String topicPath;
	private int version;
	private Date createTime;
	private int status;
	/** 辅助VO字段 */
	private List<Field> fieldList;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getSchemaId() {
		return schemaId;
	}
	public void setSchemaId(int schemaId) {
		this.schemaId = schemaId;
	}
	public long getTopicId() {
		return topicId;
	}
	public void setTopicId(long topicId) {
		this.topicId = topicId;
	}
	public String getTopicName() {
		return topicName;
	}
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public List<Field> getFieldList() {
		return fieldList;
	}
	public void setFieldList(List<Field> fieldList) {
		this.fieldList = fieldList;
	}
	public String getTopicPath() {
		return topicPath;
	}
	public void setTopicPath(String topicPath) {
		this.topicPath = topicPath;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	

}
