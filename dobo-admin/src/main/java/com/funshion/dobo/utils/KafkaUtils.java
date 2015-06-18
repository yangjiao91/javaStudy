package com.funshion.dobo.utils;

import io.confluent.kafka.schemaregistry.client.rest.entities.requests.RegisterSchemaRequest;
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;
import io.confluent.kafka.schemaregistry.client.rest.utils.RestUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kafka.admin.TopicCommand;
import kafka.admin.TopicCommand.TopicCommandOptions;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.apache.avro.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.funshion.dobo.base.exception.DoboAdminException;
import com.funshion.lego.utils.JsonUtil;
import com.funshion.lego.utils.PropertyUtil;
import com.funshion.lego.utils.PropertyUtil.Propertiez;


public class KafkaUtils {

	final static Logger logger = LoggerFactory.getLogger(KafkaUtils.class);
	
	static Propertiez kafkaProps = PropertyUtil.getInstance("application.properties");
	/** kafka zk connections */
	final static String ZK_URL = kafkaProps.getProperty("kafka.zk.url");
	final static int DEFAULT_PARTITIONS = kafkaProps.getInt("kafka.default.partitions");
	final static int DEFAULT_REPLICA_FACTOR = kafkaProps.getInt("kafka.default.replica.factor");
	final static int ZK_TIMEOUT = kafkaProps.getInt("kafka.zk.timeout");
	final static String SCHEMA_REGISTRY_BASE_URL = kafkaProps.getProperty("schema.registry.baseUrl");
	
	final static int SCHEMA_CODE_SUBJECT_NOT_EXISTS = 40401;
	final static int SCHEMA_CODE_OTHER = 500;
	
	private static ZkClient zkClient;
	
	public static void initConfig() {
		// zk有的时候连接慢，异步加载
		new Thread() {
			@Override
			public void run() {
				zkClient = new ZkClient(ZK_URL, ZK_TIMEOUT, ZK_TIMEOUT,  new MyZkStringSerializer());
			}
		}.start();
		
	}
	
	public static void releaseResources() {
		try {
			zkClient.close();
		} catch (Exception e) {
			logger.error("fail to release KafkaUtil resource", e);
		}
	}
	
	public static void createTopic(String topicName) {
		String [] args = {"--topic", topicName, "--create", "--zookeeper", ZK_URL, "--partitions", String.valueOf(DEFAULT_PARTITIONS), "--replication-factor", String.valueOf(DEFAULT_REPLICA_FACTOR)};
		TopicCommandOptions options = new TopicCommandOptions(args);
		options.checkArgs();
		
		TopicCommand.createTopic(zkClient, options);
	}
	
	/** 检查schema兼容性 是否可以注册 */
	public static boolean testCompatibility(String topic, Schema schema) {
	    RegisterSchemaRequest request = new RegisterSchemaRequest();
	    request.setSchema(schema.toString());
	    String version = "latest";
	    try {
	    	return RestUtils.testCompatibility(
		    		SCHEMA_REGISTRY_BASE_URL, RestUtils.DEFAULT_REQUEST_PROPERTIES, request, topic, version);
		} catch (RestClientException e) {
			if (e.getErrorCode() == SCHEMA_CODE_SUBJECT_NOT_EXISTS) {
				throw new DoboAdminException("topic <" + topic + "> 不存在");
			} else {
				throw new DoboAdminException("schema不合理，请检测字段类型、默认值等是否匹配");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new DoboAdminException("无法连接schema registry服务");
		}
	}
	
	/** kafka admin 接口需要一个ZkSerializer, 这里实现最简单的UFT8格式化Serializer */
	static class MyZkStringSerializer implements ZkSerializer {

		public byte[] serialize(Object data) throws ZkMarshallingError {
			try {
				return ((String)data).getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return null;
			}
		}

		public Object deserialize(byte[] bytes) throws ZkMarshallingError {
			if (bytes == null) {
				return null;
			} else {
				try {
					return new String(bytes, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		
	}
	
	public static void main(String[] args) throws Exception{
		String topic = "_ott_45";
		List<Object> fields = new ArrayList<Object>();
		Map<String, Object> fieldJson = new HashMap<String, Object>();
		fieldJson.put("name", "f1");
		fieldJson.put("type", "string");
		fields.add(fieldJson);
		fieldJson = new HashMap<String, Object>();
		fieldJson.put("name", "f2");
		fieldJson.put("type", "string");
		fieldJson.put("default", 10);
		fields.add(fieldJson);
		fieldJson = new HashMap<String, Object>();
		fieldJson.put("name", "timestamp");
		fieldJson.put("type", "string");
		fields.add(fieldJson);
		fieldJson = new HashMap<String, Object>();
		fieldJson.put("name", "schemaId");
		fieldJson.put("type", "string");
		fields.add(fieldJson);
		
		Map<String, Object> schema = new HashMap<String, Object>();
		schema.put("type", "record");
		schema.put("name", topic);
		schema.put("fields", fields);
		
		System.out.println(testCompatibility(topic, AvroUtils.toSchema(JsonUtil.toJsonString(schema))));
	}
}
