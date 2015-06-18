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
    <form id="pagerForm" data-toggle="ajaxsearch" action="${ctx}/topic/simple/search" method="post">
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
		            <button type="button" class="btn-green" data-icon="plus" data-toggle="dialog" data-width="500" data-height="300" data-id="add_simple_topic_dialog" data-mask="true" data-url="${ctx}/topic/simple/add">添加</button>
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
                <th data-order-field="status" width="80">备案状态</th>
                <th data-order-field="owner">上报方</th>
                <th data-order-field="hasTail">是否实时</th>
                <th data-order-field="remark">备注</th>
                <th data-order-direction="asc" data-order-field="createtime">创建时间</th>
                <th width="100">操作</th>
            </tr>
        </thead>
        <tbody>
        	<c:forEach var="item" items="${list}" >
        		<tr data-id="${item.id}">
	                <td>${item.path}</td>
	                <td>${item.name}</td>
	                <td> 
	                	<c:if test="${item.status == 1 }">
	                		<i class="center fa fa-check-square-o" style="font-size:20px;color:#43A102"  data-toggle="tooltip" title="已通过"></i>
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
	                	<c:if test="${item.status == 0 }">
	                		<button class="btn btn-green" data-toggle="tooltip" title="审核topic" data-icon="check" onclick="showAuditDialogSimple(${item.id})"></button>
	                    	<button class="btn btn-red" data-toggle="tooltip" title="删除" data-icon="times" onclick="delTopicSimple('${item.path}', ${item.id})"></button>
	                    	<button class="btn btn-blue" data-toggle="tooltip" title="修改" data-icon="edit" onclick="showEditDialogSimple(${item.id})"></button>
	                	</c:if>
	                </td>
	            </tr>
        	</c:forEach>
            
        </tbody>
    </table>
</div>
<%@ include file="/jsp/common/pagination.jsp"%>

<script>
function delTopicSimple(topicPath, topicId) {
	$(this).alertmsg('confirm', "确认删除 " + topicPath + " 吗！", {
		okCall : function() {
			var options = {
				url : "${ctx}/topic/simple/del/" + topicId,
				callback : function(ret) {
					$(this).navtab("refresh");
				}
			};
			$(this).bjuiajax('doAjax', options);
		}
	});
}

function auditTopicSimple(topicPath, topicName) {
	$(this).alertmsg('confirm', "确认审核通过 " + topicPath + " 吗！", {
		okCall : function() {
			var options = {
				url : "${ctx}/topic/simple/audit/" + topicName,
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

function showAuditDialogSimple(topicId) {
	$(this).dialog(
		{
			id : 'auditSchema',
			url : '${ctx}/topic/simple/audit/' + topicId,
			title : '审核Topic',
			width : 500,
			height : 300,
			mask : true,
			fresh : true//需要加此参数,否则每次打开的dialog url都是一样的
		}
	);
}

function showEditDialogSimple(topicId) {
	$(this).dialog(
		{
			id : 'editTopic',
			url : '${ctx}/topic/simple/edit/' + topicId,
			title : '编辑Topic',
			width : 500,
			height : 300,
			mask : true,
			fresh : true//需要加此参数,否则每次打开的dialog url都是一样的
		}
	);
}
</script>