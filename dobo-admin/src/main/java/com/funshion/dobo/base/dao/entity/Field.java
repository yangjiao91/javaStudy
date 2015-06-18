package com.funshion.dobo.base.dao.entity;

public class Field {
	
	private long id;
	private long schemaId;
	private String fieldName;
	private String fieldType;
	private int position;
	private String defaultValue;
	private String remark;
	
	public Field() {}
	
	public Field(String fieldName, String fieldType, String defaultValue, String remark) {
		super();
		this.fieldName = fieldName;
		this.fieldType = fieldType;
		this.defaultValue = defaultValue;
		this.remark = remark;
	}


	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getSchemaId() {
		return schemaId;
	}
	public void setSchemaId(long schemaId) {
		this.schemaId = schemaId;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
