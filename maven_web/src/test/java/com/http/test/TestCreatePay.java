package com.http.test ; 

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mock.test.HttpRequest;

import net.sf.json.JSONObject;

public class TestCreatePay{
	HttpRequest http = new HttpRequest();
	String 	url = "http://172.16.12.199:8080/bestpay-service/api";
	ParasParamUtil parasParamUtil = new ParasParamUtil();
	TestCreateOrder co = new TestCreateOrder();
	Map<String, String> map = new HashMap<String, String>();
	JSONObject result = null;
	String orderId = "" ;
	
	@Before
	public void testCreatePay() throws Exception{
		map.put("api_service_code", "create_pay");
		map.put("client_code", "game");
		map.put("gateway_code", "WEIXIN");
		map.put("nonce_str", "aaaaaaaaa");
		map.put("order_id", "19LTDL4IB45C7777");
		map.put("pay_scene", "qr_code");
		map.put("payment_fee", "1");

		orderId = co.createOrder();
		if (orderId == null || orderId == "")
			return ;
		map.put("order_id", orderId);
	}
	
	
	@Test
	public void testWeixinPay() throws Exception{
		map.put("gateway_code", "WEIXIN");
		map.put("pay_scene", "qr_code");
		map.put("payment_fee", "9999");
		result = parasParamUtil.testUtil(url,map);
		Assert.assertEquals("微信支付", "SUCCESS", result.get("return_code"));
		Assert.assertNotNull("微信二维码支付链接不能为空", result.getJSONObject("data").get("qr_code"));
		result = parasParamUtil.testUtil(url,map);
		Assert.assertEquals("同一个未支付订单重复支付", "SUCCESS", result.get("return_code"));
		
		map.put("gateway_code", "WEIXIN");
		map.put("pay_scene", "url");
		map.put("payment_fee", "9999");
		result = parasParamUtil.testUtil(url,map);
		Assert.assertEquals("微信支付网关不支持url场景", "PAY_SCENE_NOT_SUPPORT", result.get("return_code"));

		map.put("gateway_code", "weixin");
		map.put("pay_scene", "qr_code");
		map.put("payment_fee", "9999");
		result = parasParamUtil.testUtil(url,map);
		Assert.assertEquals("微信支付网关名称区分大小写", "PARAM_ERROR", result.get("return_code"));

		
//		map.put("gateway_code", "WEIXIN");
//		map.put("pay_scene", "app_sdk");
//		map.put("payment_fee", "9999");
//		result = parasParamUtil.testUtil(url,map);
//		System.out.println(result);
//		Assert.assertEquals("微信支付网关支持app_sdk场景", "SUCCESS", result.get("return_code"));


//		map.put("gateway_code", "WEIXIN");
//		map.put("pay_scene", "js_sdk");
//		map.put("payment_fee", "9999");
//		result = parasParamUtil.testUtil(url,map);
//		Assert.assertEquals("微信支付网关js_sdk场景必须要有open_id", "NEED_OPEN_ID", result.get("return_code"));
//		map.put("open_id", "11111");
//		result = parasParamUtil.testUtil(url,map);
//		Assert.assertEquals("微信支付网关支持js_sdk场景", "SUCCESS", result.get("return_code"));
//		System.out.println(result);
	}
	
	@Test
	public void testAliPay() throws Exception{
		map.put("gateway_code", "ALIPAY_WAP_TRADE");
		map.put("pay_scene", "url");
		result = parasParamUtil.testUtil(url,map);
		Assert.assertEquals("支付宝支付", "SUCCESS", result.get("return_code"));
		Assert.assertNotNull("微信二维码支付链接不能为空", result.getJSONObject("data").get("url"));
		result = parasParamUtil.testUtil(url,map);
		Assert.assertEquals("同一个未支付订单重复支付", "SUCCESS", result.get("return_code"));
		
		orderId = co.createRechargeOrder();
		map.put("order_id", orderId);
		result = parasParamUtil.testUtil(url,map);
		Assert.assertEquals("纯支付订单支付", "SUCCESS",result.get("return_code"));
		
//		map.put("gateway_code", "ALIPAY_WAP_TRADE");
//		map.put("pay_scene", "qr_code");
//		map.put("payment_fee", "9999");
//		result = parasParamUtil.testUtil(url,map);
//		Assert.assertEquals("支付宝支付网关不支持qr_code", "PAY_SCENE_NOT_SUPPORT", result.get("return_code"));
	}
	
	@Test
	public void testPaymentFeeTest() throws Exception{
		map.put("gateway_code", "ALIPAY_WAP_TRADE");
		map.put("pay_scene", "url");
		map.put("payment_fee", "0");
		result = parasParamUtil.testUtil(url,map);
		Assert.assertEquals("支付金额必须大于0", "PARAM_ERROR", result.get("return_code"));
		
		map.put("gateway_code", "ALIPAY_WAP_TRADE");
		map.put("pay_scene", "url");
		map.put("payment_fee", "-1");
		result = parasParamUtil.testUtil(url,map);
		Assert.assertEquals("支付金额必须大于0", "PARAM_ERROR", result.get("return_code"));
		
		map.put("gateway_code", "ALIPAY_WAP_TRADE");
		map.put("pay_scene", "url");
		map.put("payment_fee", "9999.99");
		result = parasParamUtil.testUtil(url,map);
		Assert.assertEquals("支付金额不能有小数点", "PARAM_ERROR", result.get("return_code"));
		
		map.put("gateway_code", "ALIPAY_WAP_TRADE");
		map.put("pay_scene", "url");
		map.put("payment_fee", "9999");
		result = parasParamUtil.testUtil(url,map);
		System.out.println(result);
		Assert.assertEquals("订单支付", "SUCCESS", result.get("return_code"));
		
		orderId = co.createRechargeAndConsumeOrder();
		map.put("order_id",orderId);
		map.put("payment_fee", "2");
		result = parasParamUtil.testUtil(url,map);
		Assert.assertEquals("充消订单支付金额等于消费金额", "SUCCESS", result.get("return_code"));
		
		orderId = co.createRechargeAndConsumeOrder();
		map.put("payment_fee", "3");
		result = parasParamUtil.testUtil(url,map);
		Assert.assertEquals("充消订单支付金额大于消费金额", "SUCCESS", result.get("return_code"));
	
		orderId = co.createRechargeAndConsumeOrder();
		map.put("order_id",orderId);
		map.put("payment_fee", "1");
		result = parasParamUtil.testUtil(url,map);
		Assert.assertEquals("充消订单支付金额小于消费金额", "NOT_ENOUGH_PAYMENT_FEE", result.get("return_code"));
	}
	
	@Test
	public void testIncorrectOrderId() throws Exception{
		map.put("gateway_code", "ALIPAY_WAP_TRADE");
		map.put("pay_scene", "url");
		map.put("payment_fee", "1");
		map.put("order_id", "a") ;
		result = parasParamUtil.testUtil(url,map);
		Assert.assertEquals("不存在的order_id", "INVALID_ORDER_ID", result.get("return_code"));

		orderId = co.createConsumeOrder();
		map.put("order_id",orderId);
		map.put("gateway_code", "ALIPAY_WAP_TRADE");
		map.put("pay_scene", "url");
		map.put("payment_fee", "1");
		result = parasParamUtil.testUtil(url,map);
		Assert.assertEquals("消费订单不允许支付", "ORDER_OVER", result.get("return_code"));
		
	}
	
}