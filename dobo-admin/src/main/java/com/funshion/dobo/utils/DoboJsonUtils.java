package com.funshion.dobo.utils;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;


/** lego.JsonUtil缺少方法,暂时放在这里 */
public class DoboJsonUtils {
	private static final ObjectMapper mapper = new ObjectMapper();
	/**
	 * 从json字符串组装对象
	 */
	public static <T> T loadObjectFromJsonString(String jsonStr, TypeReference<T> valueTypeRef) {
		try {
			if (StringUtils.isBlank(jsonStr)) {
				return null;
			}
			return mapper.readValue(jsonStr, valueTypeRef);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
