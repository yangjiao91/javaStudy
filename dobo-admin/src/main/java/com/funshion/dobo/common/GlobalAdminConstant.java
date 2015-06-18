package com.funshion.dobo.common;




/**
 * 全局常量
 * @author lirui
 *
 */
public final class GlobalAdminConstant {
	
	/** 空格 */
	public static final String BLANK = " ";
	/** 空串 */
	public static final String EMPTY = "";
	
	/** 排序方向 */
	public static final class OrderDirection {
		/** 升序 */
		public static final String ASC = "asc";
		/** 降序 */
		public static final String DESC = "desc";
	}
	
	/** session 里属性的 key 值 */
	public static final class SessionKey {
		/** 登录用户信息在 session 里的 key 值 */
		public static final String LOGIN_USER = "login_user";
		/** 登录用户信所拥有的权限 */
		public static final String LOGIN_USER_AUTHS = "login_user_auths";
		/** 记录当前环境是不是公网 */
		public static final String ENV_IS_PUB = "env_is_pub";
		/** 记录防止二次提交的 token */
		public static final String AVOID_DUP_SUBMIT_TOKENS = "avoidDupSubmitTokens";
	}
	
	/** Ajax 返回的状态码 */
	public static final class AjaxResponseStatusCode {
		/** 成功 */
		public static final int SUCCESS = 200;
		/** 失败 */
		public static final int FAIL = 300;
		/** 登录失效 */
		public static final int TIMEOUT = 301;
	}
	
	/** Avro 数据类型 */
	public static final String[] AvroFieldType = {"string", "int", "long", "float", "double", "boolean"};
	
	/** avro schema 字段 */
	public static final class SchemaField {
		/** 固定字段timestamp */
		public static final String TIMESTAMP = "time";
		/** 固定字段schemaId */
		public static final String SCHEMA_ID = "schemaId";
	}
	
	public static final class AuditStatus {
		/** 待审核 */
		public static final int PENDING = 0;
		/** 已审核 */
		public static final int CHECKED = 1;
	}
	
	/** 冗余topic后缀名 */
	public static final class TopicSuffix {
		/** 校验失败的数据发送给error topic */
		public static final String ERROR = "_error";
		/** 有实时性消费要求的topic 发送给tail */
		public static final String TAIL = "_tail";
		
	}
	
}
