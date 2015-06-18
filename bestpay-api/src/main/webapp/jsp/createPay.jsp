<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<meta charset="UTF-8">
<title>createOrder</title>
<script type="text/javascript" src="../js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="../js/jquery.form.js"></script>
</head>
<body>

<form action="/bestpay-api/createOrder/list" method="post" id="js-form" onsubmit="return false;">
	<p>url : <input type="text" name="url" value="http://172.16.12.199:8080/bestpay-service/api"/></p>
	<p>api_service_code :<input name="api_service_code" value="create_pay" /></p>
	<p>client_code : <input name="client_code" value="game"/></p>
	<p>gateway_code :
		<select name="gateway_code">  
			<option name="微信" value ="WEIXIN">WEIXIN</option>
			<option name="支付宝" value ="ALIPAY_WAP_TRADE">ALIPAY_WAP_TRADE</option>
		</select>  
	</p>
	<p>nonce_str : <input name="nonce_str" value="default"/></p>
	<p>order_id : <input name="order_id" value="111111"/></p>
	<p>pay_scene :
		<select name="pay_scene">  
			<option name="二维码" value ="qr_code">qr_code</option>
			<option name="url支付" value ="url">url</option>
			<option name="app_sdk" value ="app_sdk">app_sdk</option>
			<option name="js_sdk" value ="js_sdk">js_sdk</option>
		</select>  
	</p>
	<p>payment_fee : <input name="payment_fee" value="0"/></p>
	<p>v : <input name="v" value="1.0" "/></p>
	<button id="createpay" name="createpay" onclick="createPay()">click</button>
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

function createPay(){
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