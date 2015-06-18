<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<meta charset="UTF-8">
<title>createOrder</title>
<script type="text/javascript" src="../js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="../js/jquery.form.js"></script>
<style>
p span{display:inline-block; width:150px; font-family:Arial,"微软雅黑",sans-serif;}
p input{
		width:400px;
        border-color:grey;   
        border-top-width:0px;   
        border-right-width:0px;   
        border-bottom-width:3px;   
        border-left-width:0px;  
    }  
</style>
</head>
<body>

<form action="/bestpay-api/api/createOrderAndPay" method="post" id="js-form" onsubmit="return false;">
	<p><span>url : </span><input type="text" name="url" value="http://172.16.12.199:8080/bestpay-service/api"/></p>
	<p><span>account_id : </span><input  name="account_id" value="100049499"/></p>
	<p><span>account_type :</span><input name="account_type" value="funshion"/></p>
	<p><span>api_service_code :</span><input name="api_service_code" value="create_order_and_pay" /></p>
	<p><span>biz_trade_no : </span><input name="biz_trade_no" value="业务方订单号"/></p>
	<p><span>callback_url : </span><input name="callback_url" value="http://"/></p>
	<p><span>client_code : </span><input name="client_code" value="game"/></p>
	<p><span>consume_fee : </span><input name="consume_fee" value="0"/></p>
	<p><span>description : </span><input name="description" value="支付描述" /></p>
	<p><span>gateway_code : </span>
		<select name="gateway_code">  
			<option name="微信" value ="WEIXIN">WEIXIN</option>
			<option name="支付宝" value ="ALIPAY_WAP_TRADE">ALIPAY_WAP_TRADE</option>
		</select>  
	</p>
	<p><span>item_name : </span><input name="item_name" value="商品名称"/></p>
	<p><span>nonce_str : </span><input name="nonce_str" value="default"/></p>
	<p><span>pay_scene : </span>
		<select name="pay_scene">  
			<option name="二维码" value ="qr_code">qr_code</option>
			<option name="url支付" value ="url">url</option>
			<option name="app_sdk" value ="app_sdk">app_sdk</option>
			<option name="js_sdk" value ="js_sdk">js_sdk</option>
		</select>  
	</p>
	<p><span>payment_fee : </span><input name="payment_fee" value="0"/></p>
	<p><span>return_url : </span><input name="return_url" value="http://"/></p>
	<p><span>type : </span>
		<select name="type">  
			<option name="recharge" value ="recharge">recharge</option>  
			<option name="consume" value ="consume">consume</option>  
			<option name="recharge_consume" value="recharge_consume">recharge_consume</option>  
		</select>  
	</p>
	<p><span>v : </span><input name="v" value="1.0" "/></p>
	<button id="createorderandpay" name="createorderandpay" onclick="createOrderAndPay()">post</button>
</form>

<div class="result">
	<dl>
		<dt>完整的url:</dt>
			<dd id="resURL" value="url"></dd>
		<dt>响应结果 :</dt>
			<dd id="response" value="response"></dd>
	</dl>
</div>

<script>

function createOrderAndPay(){
	$("#js-form").ajaxSubmit({
		success : function(res){
			var res = $.parseJSON(res);
			document.getElementById("resURL").innerHTML = res.resURL ;
			document.getElementById("response").innerHTML = res.result ;
		}
	});
}
</script>
</body>
</html>