package com.funshion.bestpay.test.testcase ; 

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.funshion.bestpay.test.method.HttpRequestUtil;

import net.sf.json.JSONObject;

public class TestBalanceQuery{
	String 	url = "http://172.16.12.199:8080/bestpay-service/api";
	HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
	Map<String, String> map = new HashMap<String, String>();
	JSONObject result = null;
	
	@Before
	public void testBalanceQuery() throws Exception{
		map.put("api_service_code", "balance_query");
		map.put("account_type", "funshion");
		map.put("account_id", "777777");
		map.put("nonce_str", "aaaaaaaaa");
		map.put("client_code", "game");
		map.put("v", "1.0");
	}
	
	@Test
	public void testAccountIdCheck() throws Exception{
		
		map.put("account_id", "777777");
		result = httpRequestUtil.testUtil(url,map);
		Assert.assertEquals("数据库中存在账号", "SUCCESS",result.get("return_code"));
		Assert.assertEquals("数据库中存在账号", 0,result.get("balance"));
		
		map.put("account_id", "1");
		result = httpRequestUtil.testUtil(url,map);
		Assert.assertEquals("数据库中不存在账号", "SUCCESS",result.get("return_code"));
		Assert.assertEquals("数据库中不存在账号", 0,result.get("balance"));
		
	}
	

}