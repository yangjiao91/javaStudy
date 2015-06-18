package com.getForm;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/api")
public class BestPaySignGen{
	Map<String, String> map = new HashMap<String, String>();
	ParasParamUtil parasParamUtil = new ParasParamUtil();
	@RequestMapping(value = "/createOrder",method = RequestMethod.POST)
	public void createOrderMethod(HttpServletRequest request,HttpServletResponse response) throws Exception{
		map.put("account_id", request.getParameter("account_id"));
		map.put("account_type", request.getParameter("account_type"));
		map.put("api_service_code", request.getParameter("api_service_code"));
		map.put("biz_trade_no", request.getParameter("biz_trade_no"));
		map.put("callback_url", request.getParameter("callback_url"));
		map.put("client_code", request.getParameter("client_code"));
		map.put("consume_fee", request.getParameter("consume_fee"));
		map.put("description", request.getParameter("description"));
		map.put("item_name", request.getParameter("item_name"));
		map.put("nonce_str", request.getParameter("nonce_str"));
		map.put("return_url", request.getParameter("return_url"));
		map.put("type", request.getParameter("type"));
		map.put("v", request.getParameter("v"));	
		String url= request.getParameter("url");
		String res = parasParamUtil.requestUtil(url, map);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(res);
		response.getWriter().flush();
		response.getWriter().close();

	}
	
	@RequestMapping(value = "/createPay",method = RequestMethod.POST)
	public void createPayMethod(HttpServletRequest request,HttpServletResponse response) throws Exception{
		map.put("api_service_code", request.getParameter("api_service_code"));
		map.put("client_code",request.getParameter("client_code"));
		map.put("gateway_code", request.getParameter("gateway_code"));
		map.put("nonce_str", request.getParameter("nonce_str"));
		map.put("order_id", request.getParameter("order_id"));
		map.put("pay_scene", request.getParameter("pay_scene"));
		map.put("payment_fee", request.getParameter("payment_fee"));
		map.put("v", request.getParameter("v"));
		String url= request.getParameter("url");
			
		String res = parasParamUtil.requestUtil(url, map);

		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(res);
		response.getWriter().flush();
		response.getWriter().close();

	}
	
	@RequestMapping(value = "/createOrderAndPay",method = RequestMethod.POST)
	public void createOrderAndPayMethod(HttpServletRequest request,HttpServletResponse response) throws Exception{
		map.put("account_id", request.getParameter("account_id"));
		map.put("account_type", request.getParameter("account_type"));
		map.put("api_service_code", request.getParameter("api_service_code"));
		map.put("biz_trade_no", request.getParameter("biz_trade_no"));
		map.put("callback_url", request.getParameter("callback_url"));
		map.put("client_code", request.getParameter("client_code"));
		map.put("consume_fee", request.getParameter("consume_fee"));
		map.put("description", request.getParameter("description"));
		map.put("gateway_code", request.getParameter("gateway_code"));
		map.put("item_name", request.getParameter("item_name"));
		map.put("nonce_str", request.getParameter("nonce_str"));
		map.put("pay_scene", request.getParameter("pay_scene"));
		map.put("payment_fee", request.getParameter("payment_fee"));
		map.put("return_url", request.getParameter("return_url"));
		map.put("type", request.getParameter("type"));
		map.put("v",request.getParameter("v"));		
		String url= request.getParameter("url");
			
		String res = parasParamUtil.requestUtil(url, map);

		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(res);
		response.getWriter().flush();
		response.getWriter().close();

	}
}
