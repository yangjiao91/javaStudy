package com.funshion.bestpay.test.testcase ; 

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.funshion.bestpay.test.method.HttpRequestUtil;
import net.sf.json.JSONObject;

public class TestOrderQuery{
	String 	url = "http://172.16.12.199:8080/bestpay-service/api";
	HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
	TestCreateOrder co = new TestCreateOrder();
	Map<String, String> map = new HashMap<String, String>();
	JSONObject result = null;
	String orderId = "" ;
	
	@Before
	public void testOrderQuery() throws Exception{
		map.put("api_service_code", "order_query");
		map.put("order_id", "19LTDL4IB45C7777");
		map.put("nonce_str", "aaaaaaaaa");
		map.put("client_code", "game");
		map.put("v", "1.0");
	}
	
	@Test
	public void testOrderIdCheck() throws Exception{
		
		orderId  = co.createOrder() ;
		map.put("order_id", orderId);
		result = httpRequestUtil.testUtil(url,map);
		Assert.assertEquals("order_id存在", "SUCCESS",result.get("return_code"));
		
		map.put("order_id", "1");
		result = httpRequestUtil.testUtil(url,map);
		Assert.assertEquals("order_id不存在", "INVALID_ORDER_ID",result.get("return_code"));
		
	}
	

}