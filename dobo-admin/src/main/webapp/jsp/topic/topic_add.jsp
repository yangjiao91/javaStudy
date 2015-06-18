<%@page import="com.funshion.dobo.common.GlobalAdminConstant"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/jsp/common/taglibs.jsp"%>
<style>
.panel-body {
	padding : 0
}
</style>
<div class="bjui-pageContent">
	<form action="${ctx}/topic/add" method="post" data-toggle="validate" data-alertmsg="false">
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
		                        <input type="text" name="path" id="path" value="" data-rule="required; fieldname; length[3~128] " data-rule-fieldname="[/^\/\w+[/\w]*$/, '只允许 字母、数字、下划线,首字符必须是/']" size="16">
		                    </td>
		                </tr>
		                <tr>
		                	<td>
		                        <label for="owner" class="control-label x85">上报方：</label>
		                        <input type="text" name="owner" id="owner" value="" data-rule="required" size="16">
		                    </td>
		                </tr>
		                <tr>
		                	<td>
		                        <label for="hasTail" class="control-label x85">是否实时：</label>
		                        <input type="checkbox" id="hasTailBox" data-toggle="icheck">
		                        <input id="hasTail" name="hasTail" value="false" type="hidden">
		                    </td>
		                </tr>
						<tr>
							<td>
		                        <label for="remark" class="control-label x85">备注：</label>
		                        <textarea name="remark" id="remark" data-toggle="autoheight" cols="30" rows="1"></textarea>
		                    </td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		 
		<div class="panel panel-primary">
			<div class="panel-heading">
				<div class="panel-title">
					<div class="pull-right" >
						<i class="fa fa-info-circle"></i> timestamp和schemaId都是固定字段, <font color="red">注册时不需要关心</font><br>
						<i class="fa fa-info-circle"></i> timestamp不要上报，schemaId必须上报<br>
						<i class="fa fa-info-circle"></i> 添加字段点击表格右上角的加号<br>
					</div>
					<h3>SCHEMA</h3>
					<button id="addRowBtn" type="button" class="btn-green"
								data-toggle="tableditadd" data-target="#addSchemaTable"
								data-num="1" data-icon="plus">添加字段</button>
				</div>
			</div>
	
			<div class="panel-body">
				<table id="addSchemaTable" class="table table-bordered table-hover table-striped table-top" 
					data-toggle="tabledit" data-initnum="0" data-single-noindex="true">
					<thead>
						<!-- fieldList[#index#]表示 此表的数据都属于fieldList 当前行是index  此列是id -->
						<tr data-idname="fieldList[#index#].id">
							<th title="字段名称" width="20%">
								<input type="text" name="fieldList[#index#].fieldName" data-rule="required; fieldname; length[2~] " data-rule-fieldname="[/^[a-zA-Z_]\w*$/, '只允许 字母、数字、下划线,首字符不能是数字']" size="3">
							</th>
							<th title="字段类型" width="20%">
								<select name="fieldList[#index#].fieldType" data-toggle="selectpicker" data-rule="required" data-width="100">
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
							<th title="备注" width="30%">
								<textarea name="fieldList[#index#].remark" data-toggle="autoheight" cols="5" rows="1"></textarea>
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
								<td data-val="${field.remark}" data-readonly="true"></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</form>
</div>

<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close">关闭</button></li>
        <li><button type="submit" class="btn-default">保存</button></li>
    </ul>
</div>

<script>
$("#hasTailBox").on("ifChanged", function(event){
	// true/false
	$("#hasTail").val(event.target.checked);
});
setTimeout(function() {
	// 初始增加一个空行
	$("#addRowBtn").click();
}, 100);
</script>