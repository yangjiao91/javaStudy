package com.funshion.dobo.base.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import com.funshion.dobo.api.rest.schema.SchemaPagerForm;
import com.funshion.dobo.base.dao.entity.TopicSchema;

public interface SchemaMapper {

	public List<TopicSchema> findSchemaListAutoPaging(@Param(value="pagerForm") SchemaPagerForm pagerForm);

	public void addSchema(@Param(value="schema") TopicSchema schema);

	public TopicSchema getSchemaById(@Param(value="id") long id);

	public void update(@Param(value="schema") TopicSchema schema);

	public TopicSchema getLatestCheckedSchema(long topicId);

	@Delete("delete from topic_schema where id = #{id}")
	public void delSchema(@Param(value="id") long id);

	public TopicSchema getPendingSchema(long topicId);

	public TopicSchema getLatestSchema(long topicId);

}
