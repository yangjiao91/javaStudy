<%@page import="com.funshion.dobo.common.GlobalAdminConstant"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/jsp/common/taglibs.jsp"%>
<style>
.panel-body {
	padding : 0
}
</style>
<div class="bjui-pageContent">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title">TOPIC</h3>
		</div>

		<div class="panel-body">
			<table class="table table-condensed table-hover" width="100%">
				<tbody>
					<tr>
						<td>
	                        <label for="name" class="control-label x85">上报路径：</label>
	                        <input type="text" name="path" id="path" value="${topic.path}" size="16" readonly>
	                    </td>
	                </tr>
					<tr>
						<td>
	                        <label for="name" class="control-label x85">主题：</label>
	                        <input type="text" name="path" id="path" value="${topic.name}" size="16" readonly>
	                    </td>
	                </tr>
	                <tr>
	                	<td>
	                        <label for="owner" class="control-label x85">上报方：</label>
	                        <input type="text" name="owner" id="owner" value="${topic.owner}"size="16" readonly>
	                    </td>
	                </tr>
	                <tr>
	                	<td>
	                        <label for="hasTail" class="control-label x85">是否实时：</label>
	                        <input type="checkbox" id="hasTailBox" data-toggle="icheck" <c:if test="${topic.hasTail == true }" >checked</c:if> disabled>
	                    </td>
	                </tr>
					<tr>
						<td>
	                        <label for="remark" class="control-label x85">备注：</label>
	                        <textarea name="remark" id="remark" data-toggle="autoheight" cols="30" rows="1" value="${topic.remark}" readonly></textarea>
	                    </td>
					</tr>
					<tr>
						<td>
	                        <label for="name" class="control-label x85">备案状态：</label>
	                        <c:if test="${topic.status == 1 }">
		                		<i class="fa fa-check-square-o" style="font-size:20px;color:#43A102"  data-toggle="tooltip" title="已通过"></i>
		                	</c:if>
		                	<c:if test="${topic.status == 0 }">
		                		<i class="fa fa-times-circle-o" style="font-size:20px;color:#A73800"  data-toggle="tooltip" title="未审核"></i>
		                	</c:if>
	                    </td>
	                </tr>
				</tbody>
			</table>
		</div>
	</div>
	
	<div class="panel panel-primary">
		<div class="panel-heading">
			<div class="pull-right">
				版本 : ${schema.version} &nbsp;&nbsp;
				备案状态：
	            <c:if test="${schema.status == 1 }">
	           		<i class="fa fa-check-square-o" style="font-size:20px;color:#43A102"  data-toggle="tooltip" title="已通过"></i>
	           	</c:if>
	           	<c:if test="${schema.status == 0 }">
	           		<i class="fa fa-times-circle-o" style="font-size:20px;color:#A73800"  data-toggle="tooltip" title="未审核"></i>
	           	</c:if>
			</div>
			<h3 class="panel-title">SCHEMA</h3>
		</div>

		<div class="panel-body">
			<table data-toggle="tablefixed" data-width="100%" >
				<thead>
					<tr>
						<th  width="10%">顺序</th>
						<th  width="20%">字段名称</th>
						<th  width="20%">字段类型</th>
						<th  width="20%">默认值</th>
						<th  width="30%">备注</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="field" items="${schema.fieldList}" >
		        		<tr>
			                <td>${field.position}</td>
			                <td>${field.fieldName}</td>
			                <td>${field.fieldType}</td>
			                <td>${field.defaultValue}</td>
			                <td>${field.remark}</td>
			            </tr>
		        	</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>

<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close">关闭</button></li>
        <li><button type="button" class="btn-green" data-icon="check" onclick="doAudit(${topic.id})">确认审核</button></li>
    </ul>
</div>

<script>
function doAudit(topicId) {
	$(this).alertmsg('confirm', "确认审核吗！", {
		okCall : function() {
			var options = {
				url : "${ctx}/topic/audit/" + topicId,
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
