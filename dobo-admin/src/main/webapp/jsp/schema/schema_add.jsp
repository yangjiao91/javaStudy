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
			timestamp和schemaId都是固定字段, <font color="red">注册时不需要关心</font><br>
			<i class="fa fa-info-circle"></i> timestamp不要上报，schemaId必须上报
		</div>
		<label for="topicId" class="control-label x85">上报路径：</label> 
		<select data-toggle="selectpicker" id="topicSelect">
			<option value="">请选择</option>
			<c:forEach var="item" items="${topicList}">
				<option value="${item.id}">${item.path}</option>
			</c:forEach>
		</select>

		<div class="pull-right">
			<div class="btn-group">
				<button id="addRowBtn" type="button" class="btn-green"
					data-toggle="tableditadd" data-target="#addSchemaTable"
					data-num="1" data-icon="plus">添加字段</button>
			</div>
		</div>
	</div>
</div>

<div class="bjui-pageContent tableContent">
	<form action="${ctx}/schema/add" method="post" data-toggle="validate" data-alertmsg="false" id="addSchemaForm">
		<input name="topicId" data-rule="required" type="hidden" data-msg-required="请选择topic"/> 
		<table id="addSchemaTable" class="table table-bordered table-hover table-striped table-top" 
			data-toggle="tabledit" data-initnum="0" data-single-noindex="true">
			<thead>
				<!-- fieldList[#index#]表示 此表的数据都属于fieldList 当前行是index  此列是id -->
				<tr data-idname="fieldList[#index#].id">
					<th title="字段名称" width="20%">
						<input type="text" name="fieldList[#index#].fieldName" data-rule="required; fieldname; length[2~] " data-rule-fieldname="[/^[a-zA-Z_]\w*$/, '只允许 字母、数字、下划线,首字符不能是数字']" size="3">
					</th>
					<th title="字段类型" width="50%">
						<select name="fieldList[#index#].fieldType" data-toggle="selectpicker" data-rule="required" data-width="150">
							<%
							for (String type : GlobalAdminConstant.AvroFieldType) {
								%>
								<option value="<%=type%>"><%=type%></option>
								<%
							}
							%>
						</select>
					</th>
					<th title="默认值" width="20%">
						<input type="text" name="fieldList[#index#].defaultValue" size="2">
					</th>
					<th title="" data-addtool="true" width="100">
						<a href="#" class="btn btn-red row-del" data-toggle="tooltip" title="删除字段" data-icon="times"  data-confirm-msg="确定要删除该行信息吗？"></a>
					</th>
					
				</tr>
			</thead>
			<tbody>
				<c:forEach var="field" items="${fixedFields}" >
					<tr>
						<td data-val="${field.fieldName}" data-readonly="true"></td>
						<!-- select的disabled通过data- 传不过去,data-notread也没用 -->
						<td data-val="${field.fieldType}"></td>
						<td data-val="${field.defaultValue}" data-readonly="true"></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
</div>

<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close">关闭</button></li>
        <li><button type="submit" class="btn-default">保存</button></li>
    </ul>
</div>

<script>
setTimeout(function() {
	// 初始增加一个空行
	$("#addRowBtn").click();
}, 100);
// 选择topic 赋值topicId
$("#topicSelect").on("change", function() {
	$("#addSchemaForm [name=topicId]").val($(this).val());
	
});
</script>