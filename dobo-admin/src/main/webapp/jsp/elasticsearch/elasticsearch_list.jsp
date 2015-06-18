<%@page import="com.funshion.dobo.common.GlobalAdminConstant"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/jsp/common/taglibs.jsp"%>

<style>
	.center {
		width: auto;
		display: table;
		margin-left: auto;
		margin-right: auto;
	}
</style>

<div class="bjui-pageHeader">
    <form id="pagerForm" action="${ctx}/elasticsearch/search" onsubmit="return false;" method="post">
        <div class="bjui-searchBar">
            <label>关键字：</label><input id="keyword" name="keyword" value="" class="form-control" size="10"/>&nbsp;
            <button class="btn-default" data-icon="search" onclick="formatRes()">查询</button>&nbsp;
            <button class="btn-orange"  onclick="clearForm()" data-icon="undo">清空查询</button>&nbsp;
        </div>
    </form>
</div>

<div id="js-result" class="bjui-pageContent tableContent">
    <table id="js-table" data-toggle="tablefixed" data-width="100%" data-nowrap="true">
        <thead>
            <tr> 
                <th data-order-field="no" width="5%">条数</th>
                <th data-order-field="result" width="95%">搜索结果</th>
            </tr>
        </thead>
        <tbody id="js-tbody">          
        </tbody>
    </table>
</div>

<script type="text/javascript" >

function formatRes() { 
	var keyword = document.getElementById("keyword").value;
	$("#js-tbody").empty();
	var options = {
		callback : function(ret) {
// 			key = key.replace(keyword, '<span style="color:red">'+keyword+'</span>');
// 			for(var i = 0 ; i < ret.length ; i++){
// 				var newTr = jsResult.insertRow(-1);
// 				var newTd = newTr.insertCell();
// 				var newTd1 = newTr.insertCell();
// 				tdList+=
// 				newTd.innerHTML = i ;
// 				newTd1.innerHTML = JSON.stringify(ret[i]).replace(keyword, '<span style="background-color:yellow" >'+keyword+'</span>');
// 				console.log(JSON.stringify(ret[i]));
// 			}
	if(ret.length>0){
			for(var i=0;i<ret.length;i++){
		        var tr=document.createElement("tr");
		        var td1=document.createElement("td");
		        td1.innerHTML= i ;
		        tr.appendChild(td1);
		        var td2=document.createElement("td");
		        td2.innerHTML = JSON.stringify(ret[i]).replace(keyword, '<span style="background-color:yellow" >'+keyword+'</span>');
		        tr.appendChild(td2);
		        document.getElementById("js-tbody").appendChild(tr);
			}
	}else{}
		}
	};
    $("#pagerForm").bjuiajax('ajaxForm', options);
//  	$(this).ajaxSubmit({
// 		success : function(ret){
// 			//str = str.replace(keyword, '<span style="color:red">'+keyword+'</span>');
// 			for(var i = 0 ; i < ret.length ; i++){
// 				var newTr = jsResult.insertRow(-1);
// 				var newTd = newTr.insertCell();
// 				var newTd1 = newTr.insertCell();
// 				newTd.innerHTML = i ;
// 				newTd1.innerHTML = JSON.stringify(ret[i]);
// 				console.log(JSON.stringify(ret[i]));
// 			}
// 		}
// 		});
	}

function clearForm(){
	$("#pagerForm")[0].reset() ;
	$("#js-tbody").empty();
}
</script>
<%@ include file="/jsp/common/pagination.jsp"%>
	
