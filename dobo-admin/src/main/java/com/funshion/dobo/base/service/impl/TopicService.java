package com.funshion.dobo.base.service.impl;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kafka.common.TopicExistsException;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.funshion.dobo.api.rest.topic.TopicPagerForm;
import com.funshion.dobo.base.dao.entity.Field;
import com.funshion.dobo.base.dao.entity.Topic;
import com.funshion.dobo.base.dao.entity.TopicSchema;
import com.funshion.dobo.base.dao.mapper.TopicMapper;
import com.funshion.dobo.base.exception.DoboAdminException;
import com.funshion.dobo.base.service.AbstractService;
import com.funshion.dobo.common.GlobalAdminConstant;
import com.funshion.dobo.common.GlobalAdminConstant.AuditStatus;
import com.funshion.dobo.utils.DoboJsonUtils;
import com.funshion.dobo.utils.KafkaUtils;
import com.funshion.lego.utils.ExceptionUtil;

@Service
public class TopicService extends AbstractService {
	
	@Autowired
	private TopicMapper topicMapper;
	@Autowired
	private SchemaService schemaService;
	
	private static final String FIXED_FIELDS_JSON_PATH = "/fixed_fields.json";

	public List<Topic> findTopicListAutoPaging(TopicPagerForm pagerForm) {
		return topicMapper.findTopicListAutoPaging(pagerForm);
	}

	public List<Topic> findSimpleTopicListAutoPaging(TopicPagerForm pagerForm) {
		return topicMapper.findSimpleTopicListAutoPaging(pagerForm);
	}
	
	public void addTopic(Topic topic) {
		topic.genNameFromPath();
		topic.setCreateTime(new Date());
		checkDuplicate(topic);
		topicMapper.addTopic(topic);
		//TODO add send email here
	}

	/** 检测是否名称重复(include add and update) */
	private void checkDuplicate(Topic topic) {
		Topic existTopic = getTopic(topic.getName());
		if (existTopic != null && existTopic.getId() != topic.getId()) {
			throw new DoboAdminException("topic名称重复:" + topic.getPath());
		}
	}

	public Topic getTopic(String name) {
		return topicMapper.getTopicByName(name);
	}
	

	public Topic getTopic(long topicId) {
		return topicMapper.getTopicById(topicId);
	}

	public List<Topic> getAllTopics() {
		return topicMapper.getAllTopics();
	}

	/** 获取没有创建schema的topic, 用于添加schema */
	public List<Topic> getNoSchemaTopics() {
		return topicMapper.getNoSchemaTopics();
	}

	/** 删除topic和其未审核的schema */
	public void delTopic(long topicId) {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		try {
			topicMapper.delTopic(topicId);
			topicMapper.delSchema(topicId);
			transactionManager.commit(status);
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw new DoboAdminException(e.getMessage());
			
		}
		
		
	}

	/** 
	 * 审核通过topic及其schema
	 * */
	public void auditTopic(long topicId) {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		Topic topic = getTopic(topicId);
		try {
			if (topic == null) {
				throw new DoboAdminException("topic不存在");
			}
			// 先审核最新的schema审核， 有可能schema不合法，就不能create topic
			TopicSchema schema = schemaService.getPendingSchema(topicId);
			if (schema != null) {
				schemaService.auditSchema(schema);
			}
						
			// topic审核
			if (topic.getStatus() == GlobalAdminConstant.AuditStatus.PENDING) {
				KafkaUtils.createTopic(topic.getName());
				
				String errorTopicName = topic.getName() + GlobalAdminConstant.TopicSuffix.ERROR;
				KafkaUtils.createTopic(errorTopicName);
				
				if (topic.getHasTail()) {
					String tailTopicName = topic.getName() + GlobalAdminConstant.TopicSuffix.TAIL;
					KafkaUtils.createTopic(tailTopicName);
				}
				
				topicMapper.updateTopicStatus(topic.getName(), AuditStatus.CHECKED);
			}
			
			
			topicMapper.updateLatestVersion(topic.getId(), topic.getLatestVersion() + 1);
			
			transactionManager.commit(status);
		} catch (Exception e) {
			transactionManager.rollback(status);
			Exception ex = ExceptionUtil.seekSpecificException(e, TopicExistsException.class);
			if (ex != null && e.getMessage().contains("already exists")) {
				throw new DoboAdminException("topic <" + topic.getName() + "> 已经存在，审核失败");
			} else {
				logger.error("audit topic <" + topic.getName() + ">fail:", e);
				throw new DoboAdminException(e.getMessage());
			}
		}
	}

	/** 从配置中读取固定字段, 没有则返回空List */
	public List<Field> getFixedFields() {
		try {
			String fixedFieldsConfig = FileUtils.readFileToString(new File(getClass().getResource(FIXED_FIELDS_JSON_PATH).toURI()), Charset.forName("UTF-8"));
			return DoboJsonUtils.loadObjectFromJsonString(fixedFieldsConfig, new TypeReference<List<Field>>() {});
		} catch (Exception e) {
			logger.error("load " + FIXED_FIELDS_JSON_PATH + "error", e);
		}
		return new ArrayList<Field>();
	}

	/** 新建Topic并且注册其schema */
	public void addTopicAndSchema(Topic topic, TopicSchema topicSchema) {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		
		try {
			addTopic(topic);
			
			topicSchema.setTopicId(topic.getId());
			topicSchema.setTopicName(topic.getName());
			schemaService.addSchema(topicSchema, false);
			
			transactionManager.commit(status);
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw new DoboAdminException(e.getMessage());
		}
		
	}

	public void setSchemaStatus(Topic topic) {
		TopicSchema schema = schemaService.getLatestSchema(topic.getId());
		if (schema != null) {
			topic.setNewSchemaStatus(schema.getStatus());
		}
		
	}

	// 更新数据库
	public void update(Topic topic, TopicSchema schema) {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		
		try {
			update(topic);
			schemaService.updateSchema(schema);
			transactionManager.commit(status);
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw new DoboAdminException(e.getMessage());
		}
		
	}

	public void update(Topic topic) {
		topic.genNameFromPath();
		checkDuplicate(topic);
		topicMapper.update(topic);
	}


}
