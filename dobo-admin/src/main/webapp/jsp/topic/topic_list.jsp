<%@page import="com.funshion.dobo.common.GlobalAdminConstant"%>
<%@page import="com.funshion.dobo.common.GlobalAdminConstant.AuditStatus"%>
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
    <form id="pagerForm" data-toggle="ajaxsearch" action="${ctx}/topic/search" method="post">
        <input type="hidden" name="pageSize" value="${pagerForm.pageSize}">
        <input type="hidden" name="pageCurrent" value="${pagerForm.pageCurrent}">
        <input type="hidden" name="orderField" value="${pagerForm.orderField}">
        <input type="hidden" name="orderDirection" value="${pagerForm.orderDirection}">
        <div class="bjui-searchBar">
            <label>上报主题：</label><input type="text" value="${pagerForm.topicName}" name="topicName" class="form-control" size="10">&nbsp;
            <label>上报方：</label><input type="text" value="${pagerForm.owner}" name="version" class="form-control" size="8">&nbsp;
            <button type="submit" class="btn-default" data-icon="search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" onclick="$(this).navtab('reloadForm', true);" data-icon="undo">清空查询</a>
            <div class="pull-right">
		        <div class="btn-group">
		            <button type="button" class="btn-green" data-icon="plus" data-toggle="dialog" data-width="550" data-height="700" data-id="add_topic_dialog" data-mask="true" data-url="${ctx}/topic/add">添加</button>
		        </div>
		    </div>
        </div>
    </form>
    
</div>
<div class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%" data-nowrap="true">
        <thead>
            <tr> 
                <th data-order-field="path">路径</th>
                <th data-order-field="name">topic</th>
                <th data-order-field="latestVersion">schema最新版本</th>
                <th data-order-field="status" width="80">备案状态</th>
                <th data-order-field="owner">上报方</th>
                <th data-order-field="hasTail">是否实时</th>
                <th data-order-field="remark">备注</th>
                <th data-order-direction="asc" data-order-field="createtime">创建时间</th>
                <th width="150">操作</th>
            </tr>
        </thead>
        <tbody>
        	<c:forEach var="item" items="${list}" >
        		<tr data-id="${item.id}">
	                <td>${item.path}</td>
	                <td>${item.name}</td>
	                <td>
	                	<c:choose>
	                		<c:when  test="${item.latestVersion == 0}">
	                			无已备案的schema
	                		</c:when>
	                		<c:otherwise>
		                		${item.latestVersion}
		                	</c:otherwise>
	                	</c:choose>
	                	
	                </td>
	                <td> 
	                	<c:if test="${item.status == 1 && item.newSchemaStatus == 1}">
	                		<i class="center fa fa-check-square-o" style="font-size:20px;color:#43A102"  data-toggle="tooltip" title="已通过"></i>
	                	</c:if>
	                	<c:if test="${item.status == 1 && item.newSchemaStatus == 0}">
	                		<i class="center fa fa-warning" style="font-size:20px;color:#e87e38"  data-toggle="tooltip" title="有schema未审核"></i>
	                	</c:if>
	                	<c:if test="${item.status == 0 }">
	                		<i class="center fa fa-times-circle-o" style="font-size:20px;color:#A73800"  data-toggle="tooltip" title="未审核"></i>
	                	</c:if>
	                </td>
	                <td>${item.owner}</td>
	                <td align="center">
	                	<c:if test="${item.hasTail == true }">
	                		是
	                	</c:if>
	                	<c:if test="${item.hasTail == false }">
	                		否
	                	</c:if>
	                </td>
	                <td>${item.remark}</td>
	                <td><fmt:formatDate value="${item.createTime}" pattern='yyyy-MM-dd HH:mm:ss'/></td>
	                <td>
	                	<c:if test="${item.status == 1 && item.newSchemaStatus == 1}">
	                		<button type="button" class="btn-blue" data-icon="arrow-circle-o-up" data-toggle="tooltip" title="升级schema" onclick="showUpdateSchema(${item.id})"></button>
	                	</c:if>
	                	
	                	<c:if test="${item.status == 1 && item.newSchemaStatus == 0}">
	                		<button class="btn btn-green" data-toggle="tooltip" title="审核schema" data-icon="check" onclick="showAuditDialog(${item.id})"></button>
	                	</c:if>
	                	
	                	<c:if test="${item.status == 0 }">
	                		<button class="btn btn-green" data-toggle="tooltip" title="审核topic" data-icon="check" onclick="showAuditDialog(${item.id})"></button>
	                    	<button class="btn btn-blue" data-toggle="tooltip" title="修改" data-icon="edit" onclick="showEditDialog(${item.id})"></button>
	                    	<button class="btn btn-red" data-toggle="tooltip" title="删除" data-icon="times" onclick="delTopic('${item.path}', ${item.id})"></button>
	                	</c:if>
						<button type="button" class="btn-default" data-icon="search" data-toggle="tooltip" title="查看schema" onclick="showSchemas('${item.path}')"></button>
	                </td>
	            </tr>
        	</c:forEach>
            
        </tbody>
    </table>
</div>
<%@ include file="/jsp/common/pagination.jsp"%>

<script>
function delTopic(topicPath, topicId) {
	$(this).alertmsg('confirm', "确认删除 " + topicPath + " 吗！(包括该topic未审核的schema也一并删除)", {
		okCall : function() {
			var options = {
				url : "${ctx}/topic/del/" + topicId,
				callback : function(ret) {
					$(this).navtab("refresh");
				}
			};
			$(this).bjuiajax('doAjax', options);
		}
	});
}

function auditTopic(topicPath, topicName) {
	$(this).alertmsg('confirm', "确认审核通过 " + topicPath + " 吗！", {
		okCall : function() {
			var options = {
				url : "${ctx}/topic/audit/" + topicName,
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

function showSchemas(topicPath) {
	// 此处不能用$(this)不知道为什么
	var options = {
		id : "schema_list_link",
		url : "${ctx}/schema/search",
		title : "查看schema",
		type : "POST",
		fresh : true,//需要加此参数,否则每次打开的dialog url都是一样的
		data : {
			topicPath : topicPath,
			time : new Date().getTime()
		}
	};
	$(this).navtab(options);
}

function showUpdateSchema(topicId) {
	$(this).dialog(
		{
			id : 'updateSchema',
			url : '${ctx}/schema/update/' + topicId,
			title : '升级Schema',
			width : 600,
			height : 500,
			mask : true,
			fresh : true//需要加此参数,否则每次打开的dialog url都是一样的
		}
	);
}

function showAuditDialog(topicId) {
	$(this).dialog(
		{
			id : 'auditSchema',
			url : '${ctx}/topic/audit/' + topicId,
			title : '审核Topic Schema',
			width : 550,
			height : 700,
			mask : true,
			fresh : true//需要加此参数,否则每次打开的dialog url都是一样的
		}
	);
}

function showEditDialog(topicId) {
	$(this).dialog(
		{
			id : 'editTopic',
			url : '${ctx}/topic/edit/' + topicId,
			title : '编辑Topic Schema',
			width : 550,
			height : 700,
			mask : true,
			fresh : true//需要加此参数,否则每次打开的dialog url都是一样的
		}
	);
}
</script>