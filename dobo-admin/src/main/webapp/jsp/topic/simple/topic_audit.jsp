<%@page import="com.funshion.dobo.common.GlobalAdminConstant"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/jsp/common/taglibs.jsp"%>
<style>
.panel-body {
	padding: 0
}
</style>
<div class="bjui-pageContent">
	<table class="table table-condensed table-hover" width="100%">
		<tbody>
			<tr>
				<td><label for="name" class="control-label x85">上报路径：</label> <input
					type="text" name="path" id="path" value="${topic.path}" size="16"
					readonly></td>
			</tr>
			<tr>
				<td><label for="name" class="control-label x85">主题：</label> <input
					type="text" name="path" id="path" value="${topic.name}" size="16"
					readonly></td>
			</tr>
			<tr>
				<td><label for="owner" class="control-label x85">上报方：</label> <input
					type="text" name="owner" id="owner" value="${topic.owner}"
					size="16" readonly></td>
			</tr>
			<tr>
				<td><label for="hasTail" class="control-label x85">是否实时：</label>
					<input type="checkbox" id="hasTailBox" data-toggle="icheck"
					<c:if test="${topic.hasTail == true }" >checked</c:if> disabled>
				</td>
			</tr>
			<tr>
				<td><label for="remark" class="control-label x85">备注：</label> <textarea
						name="remark" id="remark" data-toggle="autoheight" cols="30"
						rows="1" value="${topic.remark}" readonly></textarea></td>
			</tr>
		</tbody>
	</table>
</div>

<div class="bjui-pageFooter">
	<ul>
		<li>
			<button type="button" class="btn-close">关闭</button>
		</li>
		<li>
			<button type="button" class="btn-green" data-icon="check" onclick="doAuditSimple(${topic.id})">确认审核</button>
		</li>
	</ul>
</div>

<script>
function doAuditSimple(topicId) {
	$(this).alertmsg('confirm', "确认审核吗！", {
		okCall : function() {
			var options = {
				url : "${ctx}/topic/simple/audit/" + topicId,
				loadingmask : true,
				callback : function(ret) {
					if (ret.statusCode == "<%=GlobalAdminConstant.AjaxResponseStatusCode.FAIL%>") {
						$(this).alertmsg("error", ret.message);
					} else {
						$(this).dialog("closeCurrent");
						$(this).navtab("refresh");
					}
				}
			};
			$(this).bjuiajax('doAjax', options);
		}
	});
}
</script>
