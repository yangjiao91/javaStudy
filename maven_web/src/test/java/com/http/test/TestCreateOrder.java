package com.http.test ; 

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mock.test.HttpRequest;

public class TestCreateOrder{
//	gateway_code=ALIPAY_WAP_TRADE
	HttpRequest http = new HttpRequest();
	String 	url = "http://172.16.12.199:8080/bestpay-service/api";
	ParasParamUtil parasParamUtil = new ParasParamUtil();
	Map<String, String> map = new HashMap<String, String>();
	JSONObject result = null;
	
	@Before
	public void testOrderCreate() throws Exception{

		map.put("account_id", "100049499");
		map.put("account_type", "funshion");
		map.put("api_service_code", "create_order");
		map.put("biz_trade_no", "1111111");
		map.put("callback_url", "http://172.16.13.5/callBack.php?");
		map.put("client_code", "game");
		map.put("consume_fee", "1");
		map.put("description", "百视通TV-观看电影<<速度与激情6>>");
		map.put("item_name", "wo*de");
		map.put("nonce_str", "aaaaaaaaaa");
		map.put("return_url", "http://www.baidu.com");
		map.put("type", "recharge_consume");
		map.put("v", "1.0");		
		String biz_trade_no = parasParamUtil.getRandomString(64);
		map.put("biz_trade_no", biz_trade_no);
	}
	
	public String createOrder() throws Exception{
		testOrderCreate();
		result = parasParamUtil.testUtil(url,map);
		return (String) result.get("order_id");
		
	}
	
	public String createConsumeOrder() throws Exception{
		testOrderCreate();
		map.put("type", "consume");
		map.put("consume_fee", "1");
		result = parasParamUtil.testUtil(url,map);
		return (String) result.get("order_id");
	}
	
	public String createRechargeOrder() throws Exception{
		testOrderCreate();
		map.put("type", "recharge");
		map.put("consume_fee", "0");
		result = parasParamUtil.testUtil(url,map);
		return (String) result.get("order_id");
	}
	
	
	public String createRechargeAndConsumeOrder() throws Exception{
		testOrderCreate();
		map.put("type", "recharge_consume");
		map.put("consume_fee", "2");
		result = parasParamUtil.testUtil(url,map);
		return (String) result.get("order_id"); 
	}
	
	@Test
	public void testRechargeTypeOrder() throws Exception{
		//type取值 recharge、consume、recharge_consume
		map.put("type", "recharge");
		map.put("consume_fee", "0");
		result = parasParamUtil.testUtil(url,map);
		Assert.assertEquals("充值订单下单失败", "SUCCESS", result.get("return_code"));
		Assert.assertNotNull("充值订单成功下单返回的订单号不应该为空", result.get("order_id"));
		
		map.put("consume_fee", "1");
		result = parasParamUtil.testUtil(url,map);
		Assert.assertEquals("充值订单的消费金额应该为0", "CONSUME_MUST_ZERO", result.get("return_code"));
	
		map.put("type", "RECHARGE");
		result = parasParamUtil.testUtil(url,map);
		Assert.assertEquals("充值订单type区分大小写", "PARAM_ERROR", result.get("return_code"));
		
	}
	@Test
	public void testConsumeTypeOrder() throws Exception{
	
		map.put("type", "consume");
		map.put("consume_fee", "0");
		result = parasParamUtil.testUtil(url,map);
		Assert.assertEquals("消费订单的消费金额应该大于0", "CONSUME_MUST_GT_ZERO", result.get("return_code"));
		
		map.put("consume_fee", "1");
		map.put("account_id","00000001");
		result = parasParamUtil.testUtil(url,map);
		System.out.println(result);
		Assert.assertEquals("消费订单余额不足", "CONSUME_ERROR", result.get("return_code"));
		
		map.put("consume_fee", "1");
		map.put("account_id","100049499");
		result = parasParamUtil.testUtil(url,map);
		System.out.println(result);
		Assert.assertEquals("消费成功", "SUCCESS", result.get("return_code"));
	}
	@Test
	public void testRechargeAndConsumeTypeOrder() throws Exception{
		
		map.put("type", "recharge_consume");
		map.put("consume_fee", "0");
		result = parasParamUtil.testUtil(url,map);
		System.out.println(result);
		Assert.assertEquals("充消订单的消费金额应该大于0", "CONSUME_MUST_GT_ZERO", result.get("return_code"));
		
		map.put("consume_fee", "1");
		result = parasParamUtil.testUtil(url,map);
		Assert.assertEquals("充消订单下单失败", "SUCCESS", result.get("return_code"));
		Assert.assertNotNull("充消订单成功下单返回的订单号不应该为空", result.get("order_id"));
		
		map.put("type", "RECHARGE_CONSUME");
		result = parasParamUtil.testUtil(url,map);
		Assert.assertEquals("充消订单type区分大小写", "PARAM_ERROR", result.get("return_code"));
		
		map.put("type", "recharge_ consume");
		result = parasParamUtil.testUtil(url,map);
		Assert.assertEquals("充消订单type应正确填写", "PARAM_ERROR", result.get("return_code"));
	}
	@Test
	public void testCreateRepeatOrder() throws Exception{
		map.put("biz_trade_no", "aaaaaa");
		map.put("type", "recharge");
		map.put("consume_fee", "0");
		result = parasParamUtil.testUtil(url,map);
		result = parasParamUtil.testUtil(url,map);
		Assert.assertEquals("不允许重复下单", "DUPLICATE_BIZ_ORDER", result.get("return_code"));
	}
	@Test
	public void testUrlFormCheck() throws Exception{
		map.put("type", "recharge");
		map.put("consume_fee", "0");
		map.put("return_url", "http//://www.baidu.com");
		result = parasParamUtil.testUtil(url,map);
		Assert.assertEquals("return_url参数错误", "PARAM_ERROR", result.get("return_code"));
		
		map.put("callback_url", "http//://www.baidu.com");
		System.out.println(result);
		Assert.assertEquals("callback_url参数错误", "PARAM_ERROR", result.get("return_code"));
		
	}
	
}