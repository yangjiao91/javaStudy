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
	<div class="bjui-searchBar">
		<div class="alert alert-info ">
			<i class="fa fa-info-circle"></i> 
			timestamp和schemaId都是固定字段<br>
			<i class="fa fa-info-circle"></i> timestamp不要上报，schemaId必须上报
		</div>
		<label for="topicId" class="control-label x80">上报路径：</label> 
		<input type="text" readonly value="${schema.topicPath}" size="10"/>
		&nbsp;
		<label for="topicId" class="control-label x80">版本：</label> 
		<input type="text" readonly value="${schema.version}" size="10"/>
		
		<br>
		<span class="pull-right" style="margin-right:20px;margin-top:10px;">
			<c:if test="${schema.status == 1 }">
	      		<i class="fa fa-check-square-o" style="font-size:20px;color:#43A102"  data-toggle="tooltip" title="已通过"></i>已通过
	      	</c:if>
	      	<c:if test="${schema.status == 0 }">
	      		<i class="fa fa-times-circle-o" style="font-size:20px;color:#A73800"  data-toggle="tooltip" title="未审核"></i>未审核
	      		&nbsp;&nbsp;
	      		<!-- 查看页面先不加操作 -->
	      		<%-- <button class="btn btn-green" data-toggle="tooltip" title="通过" data-icon="check" onclick="auditSchema('${schema.topicName}', '${schema.id}')"></button>
	            <button class="btn btn-red" data-toggle="tooltip" title="删除" data-icon="times" onclick="delSchema('${schema.id}')"></button> --%>
	      	</c:if>
		</span>
	</div>
</div>

<div class="bjui-pageContent tableContent">
	<table data-toggle="tablefixed" data-width="100%">
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
