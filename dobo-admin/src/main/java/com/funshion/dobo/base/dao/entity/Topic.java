package com.funshion.dobo.base.dao.entity;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class Topic {
	
	private long id;
	private String name;
	private String path;
	private String owner;
	private String remark;
	private Date createTime;
	private int status;
	/** 是否创建用于flume以tail方式发送数据的topic */
	private boolean hasTail;
	/** 已备案的最新的schema版本 */
	private int latestVersion;
	private boolean hasSchema = true;
	/** 最新schema的状态(已备案+尚未备案) 
	 * 0 未审核
	 * 1 已审核
	 * 10 没有schema
	 * */
	private int newSchemaStatus = 10;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public boolean getHasTail() {
		return hasTail;
	}
	public void setHasTail(boolean hasTail) {
		this.hasTail = hasTail;
	}
	public int getLatestVersion() {
		return latestVersion;
	}
	public void setLatestVersion(int latestVersion) {
		this.latestVersion = latestVersion;
	}
	
	public int getNewSchemaStatus() {
		return newSchemaStatus;
	}
	public void setNewSchemaStatus(int newSchemaStatus) {
		this.newSchemaStatus = newSchemaStatus;
	}
	
	public boolean getHasSchema() {
		return hasSchema;
	}
	public void setHasSchema(boolean hasSchema) {
		this.hasSchema = hasSchema;
	}
	/** 根据path生成topic名称, 因为topic名字不允许 "/" */
	public void genNameFromPath() {
		if (StringUtils.isNotBlank(path)) {
			name = path.replace('/', '_');
		}
	}
	
	

}
