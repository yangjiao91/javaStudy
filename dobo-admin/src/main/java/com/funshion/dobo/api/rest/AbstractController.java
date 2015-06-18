package com.funshion.dobo.api.rest;

import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  Controller 的抽象父类
 * @author lirui
 *
 */
public abstract class AbstractController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 得到临时目录位置，例如 /tmp/
	 */
	protected String getTmpPath(){
		String tmpPath = System.getProperty("java.io.tmpdir");
		if (!tmpPath.endsWith(File.separator)) {
			tmpPath += File.separatorChar;
		}
		return tmpPath;
	}
	
	/** 从 request 中取出参数 map */
	protected Map<String, String> getRequestParameters(HttpServletRequest request) {
		Map<String, String> paramMapRef = new HashMap<String, String>();
		Enumeration<String> requestParamNames = request.getParameterNames();
		while(requestParamNames.hasMoreElements()){
			String key = requestParamNames.nextElement();
			paramMapRef.put(key, request.getParameter(key));
		}
		return paramMapRef;
	}
	
}
