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
    <form id="pagerForm" data-toggle="ajaxsearch" action="${ctx}/schema/search" method="post" >
        <input type="hidden" name="pageSize" value="${pagerForm.pageSize}">
        <input type="hidden" name="pageCurrent" value="${pagerForm.pageCurrent}">
        <input type="hidden" name="orderField" value="${pagerForm.orderField}">
        <input type="hidden" name="orderDirection" value="${pagerForm.orderDirection}">
        <div class="bjui-searchBar">
            <label>上报路径：</label><input type="text" value="${pagerForm.topicPath}" name="topicPath" class="form-control" size="10">&nbsp;
            <label>版本：</label><input type="text" value="${pagerForm.version}" name="version" class="form-control" size="8">&nbsp;
            <button type="submit" class="btn-default" data-icon="search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" onclick="$(this).navtab('reloadForm', true);" data-icon="undo">清空查询</a>
            <div class="pull-right">
		        <div class="btn-group">
		        	<!-- 添加都放在topic页面中 -->
		            <%-- <button type="button" class="btn-green" data-icon="plus" data-toggle="dialog" data-width="500" data-height="600" data-id="add_schema_dialog" data-mask="true" data-url="${ctx}/schema/add">添加schema</button> --%>
		        </div>
		    </div>
        </div>
    </form>
</div>
<div class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%" data-nowrap="true">
        <thead>
            <tr> 
            	<!-- <th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th> -->
                <th data-order-field="schemaId">schema ID</th>
                <th data-order-field="topicPath">上报路径</th>
                <th data-order-field="topicName">topic</th>
                <th data-order-field="version">版本</th>
                <th data-order-field="createtime">登记时间</th>
                <th data-order-field="status" width="80">备案状态</th>
                <th width="100">操作</th>
            </tr>
        </thead>
        <tbody>
        	<c:forEach var="item" items="${list}" >
        		<tr data-id="${item.id}">
	            	<%-- <td><input type="checkbox" name="ids" data-toggle="icheck" value="${item.id}"></td> --%>
	                <td>
	                	<c:if test="${item.schemaId == 0 }">
	                		无
	                	</c:if>
	                	<c:if test="${item.schemaId != 0 }">
	                		${item.schemaId}
	                	</c:if>
	                </td>
	                <td>${item.topicPath}</td>
	                <td>${item.topicName}</td>
	                <td>${item.version}</td>
	                <td><fmt:formatDate value="${item.createTime}" pattern='yyyy-MM-dd HH:mm:ss'/></td>
	                <td> 
	                	<c:if test="${item.status == 1 }">
	                		<i class="center fa fa-check-square-o" style="font-size:20px;color:#43A102"  data-toggle="tooltip" title="已通过"></i>
	                	</c:if>
	                	<c:if test="${item.status == 0 }">
	                		<i class="center fa fa-times-circle-o" style="font-size:20px;color:#A73800"  data-toggle="tooltip" title="未审核"></i>
	                	</c:if>
	                </td>
	                <td>
	                	<c:if test="${item.status == 0 }">
	                		<!-- 通过放在topic页面中 -->
	                		<%-- <button class="btn btn-green" data-toggle="tooltip" title="通过" data-icon="check" onclick="auditSchema('${item.topicName}', '${item.id}')"></button> --%>
	                		<button class="btn btn-blue" data-toggle="tooltip" title="修改" data-icon="edit" onclick="showEditSchema(${item.id})"></button>
	                    	<button class="btn btn-red" data-toggle="tooltip" title="删除" data-icon="times" onclick="delSchema('${item.id}')"></button>
	                	</c:if>
	                    <button class="btn btn-green"  title="查看" data-icon="file-text" data-toggle="dialog" data-width="500" data-height="600" data-id="edit_schema_dialog" data-mask="true" data-url="${ctx}/schema/view/${item.id}"></button>
	                </td>
	            </tr>
        	</c:forEach>
            
        </tbody>
    </table>
</div>
<%@ include file="/jsp/common/pagination.jsp"%>

<script>
function delSchema(schemaId) {
	$(this).alertmsg('confirm', "确认删除该schema吗！", {
		okCall : function() {
			var options = {
				url : "${ctx}/schema/del/" + schemaId,
				callback : function(ret) {
					$(this).navtab("refresh");
				}
			};
			$(this).bjuiajax('doAjax', options);
		}
	});
}

function auditSchema(topicName, schemaId) {
	$(this).alertmsg('confirm', "确认审核通过该主题为 " + topicName + " 的schema吗！", {
		okCall : function() {
			var options = {
				url : "${ctx}/schema/audit/" + schemaId,
				loadingmask : true,
				callback : function(ret) {
					if (ret.statusCode == "<%=GlobalAdminConstant.AjaxResponseStatusCode.FAIL%>") {
						$(this).alertmsg("error", ret.message);
					} else {
						$(this).navtab("refresh");
					}
					
				}
			};
			$(this).bjuiajax('doAjax', options);
		}
	});
}
function showEditSchema(schemaId) {
	$(this).dialog(
		{
			id : 'editSchema',
			url : '${ctx}/schema/edit/' + schemaId,
			title : '编辑Schema',
			width : 500,
			height : 600,
			mask : true,
			fresh : true//需要加此参数,否则每次打开的dialog url都是一样的
		}
	);
}
</script>
	
