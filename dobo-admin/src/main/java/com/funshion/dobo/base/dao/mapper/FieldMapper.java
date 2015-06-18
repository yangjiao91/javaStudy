package com.funshion.dobo.base.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import com.funshion.dobo.base.dao.entity.Field;

public interface FieldMapper {

	public void addField(@Param(value="field") Field field);

	public List<Field> getFieldsBySchemaId(@Param(value="schemaId") long schemaId);

	/** 删除schema下所有field */
	@Delete("delete from field where schema_id = #{schemdId}")
	public void delField(@Param(value="schemdId")long schemdId);

}
