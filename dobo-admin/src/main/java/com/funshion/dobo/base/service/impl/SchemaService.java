package com.funshion.dobo.base.service.impl;

import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.funshion.dobo.api.rest.schema.SchemaPagerForm;
import com.funshion.dobo.base.dao.entity.Field;
import com.funshion.dobo.base.dao.entity.Topic;
import com.funshion.dobo.base.dao.entity.TopicSchema;
import com.funshion.dobo.base.dao.mapper.FieldMapper;
import com.funshion.dobo.base.dao.mapper.SchemaMapper;
import com.funshion.dobo.base.dao.mapper.TopicMapper;
import com.funshion.dobo.base.exception.DoboAdminException;
import com.funshion.dobo.base.service.AbstractService;
import com.funshion.dobo.common.GlobalAdminConstant;
import com.funshion.dobo.utils.AvroUtils;
import com.funshion.dobo.utils.KafkaUtils;
import com.funshion.lego.utils.JsonUtil;

@Service
public class SchemaService extends AbstractService {
	
	@Autowired
	private SchemaMapper schemaMapper;
	@Autowired
	private TopicMapper topicMapper;
	@Autowired
	private FieldMapper fieldMapper;
	@Autowired
	private SchemaRegistryClient schemaRegistryClient;

	public List<TopicSchema> findSchemaListAutoPaging(SchemaPagerForm pagerForm) {
		return schemaMapper.findSchemaListAutoPaging(pagerForm);
	}

	
	/**
	 * @param topicSchema 入库对象
	 * @param forUpdate 是否升级
	 */
	public void addSchema(TopicSchema topicSchema, boolean forUpdate) {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		
		try {
			if (topicSchema.getTopicName() == null) {
				Topic topic = topicMapper.getTopicById(topicSchema.getTopicId());
				topicSchema.setTopicName(topic.getName());
			}
			int version = 1;
			/** 首次增加schema，只写入数据库，待审核, 不做兼容性检查也不注册registry */
			if (forUpdate) {
				if (!KafkaUtils.testCompatibility(topicSchema.getTopicName(), AvroUtils.toSchema(assembleSchemaJson(topicSchema)))) {
					throw new DoboAdminException("与最新版本不兼容");
				}
				// topicSchema自带的是最新版本的version
				version = topicSchema.getVersion() + 1;
			}
			topicSchema.setVersion(version);
			topicSchema.setCreateTime(new Date());
			schemaMapper.addSchema(topicSchema);
			addField(topicSchema);
			
			// TODO: add email send
			transactionManager.commit(status);
		} catch (Exception e) {
			transactionManager.rollback(status);
			logger.error("fail to add schema and fields", e);
			// schema不兼容
			if (e.getMessage().contains("incompatible")) {
				throw new DoboAdminException("schema已存在且不兼容");
			} else if (e.getMessage().contains("uni_field_name")) {
				throw new DoboAdminException("请删除重复字段");
			} else if (e.getMessage().contains("uni_version")) {
				throw new DoboAdminException("该topic有尚未审核的schema");
			} else {
				throw new DoboAdminException(e.getMessage());
			}
		}
		
		
	}

	/** 拼装注册schema使用的json字符串 */
	private String assembleSchemaJson(TopicSchema topicSchema) {
		
		List<Map<String, Object>> fieldsJson = new ArrayList<Map<String, Object>>();
		for (Field field : topicSchema.getFieldList()) {
			fieldsJson.add(assemleFieldJsonMap(field.getFieldName(), field.getFieldType(), field.getDefaultValue()));
		}
		
		Map<String, Object> schemaJson = new HashMap<String, Object>();
		schemaJson.put("type", "record");
		schemaJson.put("name", topicSchema.getTopicName());
		schemaJson.put("fields", fieldsJson);
		
		return JsonUtil.toJsonString(schemaJson);
	}

	private Map<String, Object> assemleFieldJsonMap(String fieldName, String fieldType, String defaultValue) {
		Map<String, Object> fieldJson = new HashMap<String, Object>();
		fieldJson.put("name", fieldName);
		fieldJson.put("type", fieldType);
		if (StringUtils.isNotBlank(defaultValue)) {
			Object realDefaultValue = defaultValue;
			if (fieldType.equals("int") || fieldType.equals("long")) {
				realDefaultValue = Long.parseLong(defaultValue);
			} else if (fieldType.equals("float") || fieldType.equals("double")) {
				realDefaultValue = Double.parseDouble(defaultValue);
			} else if (fieldType.equals("boolean")) {
				realDefaultValue = Boolean.parseBoolean(defaultValue);
			}
			fieldJson.put("default", realDefaultValue);
		}
		return fieldJson;
	}

	public TopicSchema getSchema(long id) {
		return schemaMapper.getSchemaById(id);
	}

	/** 
	 * 审核通过
	 * 1 先注册该schema
	 * 2 拿到id 然后回写DB， 修改状态
	 *  */
	public void auditSchema(TopicSchema schema) {
		// TODO Auto-generated method stub
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		try {
			int schemaId = schemaRegistryClient.register(schema.getTopicName(), AvroUtils.toSchema(assembleSchemaJson(schema)));
			TopicSchema latestSchema = getLatestCheckedSchema(schema.getTopicId());
			if (latestSchema != null && latestSchema.getSchemaId() == schemaId) {
				// 升级没有任何变化,不允许保存此schema
				throw new DoboAdminException("该schema在最新版本基础上没有更改， 不允许保留，请删除");
			}
			schema.setSchemaId(schemaId);
			schema.setStatus(GlobalAdminConstant.AuditStatus.CHECKED);
			schemaMapper.update(schema);
			transactionManager.commit(status);
		} catch (Exception e) {
			transactionManager.rollback(status);
			if (e.getMessage().contains("incompatible")) {
				throw new DoboAdminException("与topic <" + schema.getTopicName() + "> 最新版本不兼容");
			} else if (e instanceof IOException) {
				throw new DoboAdminException("无法连接schema registry服务");
			} else {
				throw new DoboAdminException(e.getMessage());
			}
			
		}
		
	}
	
	/** 获取该topic的最新的且已审核的schema */
	public TopicSchema getLatestCheckedSchema(long topicId) {
		TopicSchema schema =  schemaMapper.getLatestCheckedSchema(topicId);
		if (schema != null) {
			schema.setFieldList(fieldMapper.getFieldsBySchemaId(schema.getId()));
		}
		return schema;
	}


	public void delSchema(long id) {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		try {
			schemaMapper.delSchema(id);
			fieldMapper.delField(id);
			transactionManager.commit(status);
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw new DoboAdminException(e.getMessage());
			
		}
		
	}

	/** 获取最新添加的尚未审核的schema */
	public TopicSchema getPendingSchema(long topicId) {
		TopicSchema schema =  schemaMapper.getPendingSchema(topicId);
		if (schema != null) {
			schema.setFieldList(fieldMapper.getFieldsBySchemaId(schema.getId()));
		}
		return schema;
	}

	public TopicSchema getLatestSchema(long topicId) {
		TopicSchema schema =  schemaMapper.getLatestSchema(topicId);
		if (schema != null) {
			schema.setFieldList(fieldMapper.getFieldsBySchemaId(schema.getId()));
		}
		return schema;
	}

	/** 更新schema, 先删除所有字段 然后再添加更新后的所有字段 */
	public void updateSchema(TopicSchema schema) {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		try {
			fieldMapper.delField(schema.getId());
			addField(schema);
			transactionManager.commit(status);
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw new DoboAdminException(e.getMessage());
			
		}
		
	}

	/** 添加schema字段到数据库 */
	private void addField(TopicSchema schema) {
		// 检查default_value和field_type是否匹配
		checkFieldValid(schema.getFieldList());
		for (int i = 0 ; i < schema.getFieldList().size(); i++) {
			Field field = schema.getFieldList().get(i);
			field.setSchemaId(schema.getId());
			field.setPosition(i + 1);
			fieldMapper.addField(field);
		}
		
	}

	/** 检查field是否合法， 不合法抛出异常 */
	private void checkFieldValid(List<Field> fieldList) {
		if (fieldList != null) {
			for (Field field : fieldList) {
				// 检查类型与default_value是否匹配
				if (StringUtils.isNotBlank(field.getDefaultValue())) {
					if (field.getFieldType().equals("double") || field.getFieldType().equals("float")) {
						try {
							Double.parseDouble(field.getDefaultValue());
						} catch (NumberFormatException e) {
							throw new DoboAdminException("默认值必须是数字");
						}
					}
					if (field.getFieldType().equals("int") || field.getFieldType().equals("long")) {
						try {
							Long.parseLong(field.getDefaultValue());
						} catch (NumberFormatException e) {
							throw new DoboAdminException("默认值必须是整数");
						}
						
					}
					if (field.getFieldType().equals("boolean")) {
						if (!field.getDefaultValue().equals("true") && !field.getDefaultValue().equals("false")) {
							throw new DoboAdminException("默认值必须true或false");
						}
					}
				}
			}
		}
	}

}
