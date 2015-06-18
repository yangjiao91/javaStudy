package com.http.test ; 

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mock.test.HttpRequest;

public class TestCreateOrderAndPay{
//	gateway_code=ALIPAY_WAP_TRADE
	HttpRequest http = new HttpRequest();
	String 	url = "http://172.16.12.199:8080/bestpay-service/api";
	ParasParamUtil parasParamUtil = new ParasParamUtil();
	Map<String, String> map = new HashMap<String, String>();
	JSONObject result = null;
	String biz_trade_no = "";
			
	@Before
	public void testCreateOrderAndPay() throws Exception{

		map.put("account_id", "777777");
		map.put("account_type", "funshion");
		map.put("api_service_code", "create_order_and_pay");
		map.put("biz_trade_no", "1111111");
		map.put("callback_url", "http://172.16.13.5/callBack.php?");
		map.put("client_code", "game");
		map.put("consume_fee", "1");
		map.put("description", "百视通TV-观看电影<<速度与激情6>>");
		map.put("gateway_code", "WEIXIN");
		map.put("item_name", "wo*de");
		map.put("nonce_str", "aaaaaaaaaa");
		map.put("pay_scene", "qr_code");
		map.put("payment_fee", "1");
		map.put("return_url", "http://www.baidu.com");
		map.put("type", "recharge_consume");
		map.put("v", "1.0");		
		biz_trade_no = parasParamUtil.getRandomString(64);
		map.put("biz_trade_no", biz_trade_no);
	}
	

	public JSONObject createOrderAndPay() throws Exception{
		testCreateOrderAndPay();
		result = parasParamUtil.testUtil(url,map);
		return  result;
	}
	
	@Test
	public void testFeeCheck() throws Exception{
		map.put("type", "recharge_consume");
		map.put("consume_fee", "1");
		map.put("payment_fee", "1");
		result = parasParamUtil.testUtil(url,map);
		Assert.assertEquals("支付金额等于消费金额", "SUCCESS", result.get("return_code"));
		
		biz_trade_no = parasParamUtil.getRandomString(64);
		map.put("biz_trade_no", biz_trade_no);
		map.put("consume_fee", "1");
		map.put("payment_fee", "2");
		result = parasParamUtil.testUtil(url,map);
		Assert.assertEquals("支付金额大于消费金额", "SUCCESS", result.get("return_code"));
		
		biz_trade_no = parasParamUtil.getRandomString(64);
		map.put("biz_trade_no", biz_trade_no);
		map.put("consume_fee", "2");
		map.put("payment_fee", "1");
		result = parasParamUtil.testUtil(url,map);
		System.out.println(result);
		Assert.assertEquals("支付金额小于消费金额", "PARAM_ERROR", result.get("return_code"));
	
	}
	
	@Test
	public void testOrderType() throws Exception{
		map.put("type", "recharge_consume");
		map.put("consume_fee", "1");
		map.put("payment_fee", "1");
		result = parasParamUtil.testUtil(url,map);
		Assert.assertEquals("充消订单", "SUCCESS", result.get("return_code"));
		
		
		biz_trade_no = parasParamUtil.getRandomString(64);
		map.put("biz_trade_no", biz_trade_no);
		map.put("type", "recharge");
		map.put("consume_fee", "0");
		map.put("payment_fee", "1");
		result = parasParamUtil.testUtil(url,map);
		Assert.assertEquals("纯支付订单", "SUCCESS", result.get("return_code"));
		map.put("consume_fee", "1");
		result = parasParamUtil.testUtil(url,map);
		Assert.assertEquals("充值订单的消费金额应该为0", "CONSUME_MUST_ZERO", result.get("return_code"));
		
		biz_trade_no = parasParamUtil.getRandomString(64);
		map.put("biz_trade_no", biz_trade_no);
		map.put("type", "consume");
		result = parasParamUtil.testUtil(url,map);
		Assert.assertEquals("不支持consume订单", "CONSUME_NOT_SUPPORT", result.get("return_code"));
	}
	
	@Test
	public void testWeixinPay() throws Exception{
		
		map.put("gateway_code", "WEIXIN");
		map.put("pay_scene", "qr_code");
		result = parasParamUtil.testUtil(url,map);
		System.out.println(result);
		Assert.assertEquals("微信网关支付", "SUCCESS",result.get("return_code"));
		
		map.put("gateway_code", "WEIXIN");
		map.put("pay_scene", "url");
		result = parasParamUtil.testUtil(url,map);
		Assert.assertEquals("微信支付网关不支持url场景", "PAY_SCENE_NOT_SUPPORT", result.get("return_code"));

		map.put("gateway_code", "weixin");
		map.put("pay_scene", "qr_code");
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
		Assert.assertEquals("支付宝网关支付", "SUCCESS",result.get("return_code"));
	
		map.put("pay_scene", "qr_code");
		result = parasParamUtil.testUtil(url,map);
		Assert.assertEquals("支付宝网关支付", "PAY_SCENE_NOT_SUPPORT",result.get("return_code"));
	
	}
}