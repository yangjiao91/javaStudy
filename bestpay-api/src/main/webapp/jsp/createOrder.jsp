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

<form action="/bestpay-api/api/createOrder" method="post" id="js-form" onsubmit="return false;">
	<p>url : <input type="text" name="url" value="http://172.16.12.199:8080/bestpay-service/api"/></p>
	<p>account_id : <input  name="account_id" value="100049499"/></p>
	<p>account_type :<input name="account_type" value="funshion"/></p>
	<p>api_service_code :<input name="api_service_code" value="create_order" /></p>
	<p>biz_trade_no : <input name="biz_trade_no" value="业务方订单号"/></p>
	<p>callback_url : <input name="callback_url" value="http://"/></p>
	<p>client_code : <input name="client_code" value="game"/></p>
	<p>consume_fee : <input name="consume_fee" value="0"/></p>
	<p>description : <input name="description" value="支付描述" /></p>
	<p>item_name : <input name="item_name" value="Itemname"/></p>
	<p>nonce_str : <input name="nonce_str" value="default"/></p>
	<p>return_url : <input name="return_url" value="http://"/></p>
	<p>type :
		<select name="type">  
			<option name="recharge" value ="recharge">recharge</option>  
			<option name="consume" value ="consume">consume</option>  
			<option name="recharge_consume" value="recharge_consume">recharge_consume</option>  
		</select>  
	</p>
	<p>v : <input name="v" value="1.0" "/></p>
	<button id="createorder" name="createorder" onclick="createOrder()">post</button>
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

function createOrder(){
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