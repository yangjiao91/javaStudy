<%@page import="com.funshion.dobo.common.GlobalAdminConstant"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/jsp/common/taglibs.jsp"%>

<div class="bjui-pageHeader">
	<div class="bjui-searchBar">
		<div class="alert alert-info ">
			<i class="fa fa-info-circle"></i> 
			timestamp和schemaId都是固定字段, <font color="red">升级时不需要关心</font><br>
			<i class="fa fa-info-circle"></i> timestamp不要上报，schemaId必须上报<br>
			<i class="fa fa-info-circle"></i> 下面展示的是最新版本，请在最新版本基础上做修改
		</div>
		<label for="topicId" class="control-label x85">上报路径：</label> 
		<input type="text" readonly value="${schema.topicPath}" size="20"/>
		
		&nbsp;
		<label for="version" class="control-label x80">最新版本：</label> 
		<input type="text" readonly value="${schema.version}" size="10"/>
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
	<form action="${ctx}/schema/update" method="post" data-toggle="validate" data-alertmsg="false" id="addSchemaForm">
		<input type="hidden" value="${schema.topicId}" name="topicId"/>
		<input type="hidden" name="version" readonly value="${schema.version}"/>
		
		<table id="addSchemaTable" class="table table-bordered table-hover table-striped table-top" 
			data-toggle="tabledit" data-initnum="0" data-single-noindex="true">
			<thead>
				<!-- fieldList[#index#]表示 此表的数据都属于fieldList 当前行是index  此列是id -->
				<tr data-idname="fieldList[#index#].id">
					<th title="字段名称" width="20%">
						<input type="text" name="fieldList[#index#].fieldName" data-rule="required; fieldname; length[2~] " data-rule-fieldname="[/^[a-zA-Z_]\w*$/, '只允许 字母、数字、下划线,首字符不能是数字']" size="3">
					</th>
					<th title="字段类型" width="20%">
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
					<th title="备注" width="30%">
						<textarea name="fieldList[#index#].remark" data-toggle="autoheight" cols="5" rows="1"></textarea>
					</th>
					<th title="" data-addtool="true" width="100">
						<a href="#" class="btn btn-red row-del" data-toggle="tooltip" title="删除字段" data-icon="times"  data-confirm-msg="确定要删除该行信息吗？"></a>
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="field" items="${schema.fieldList}" >
					<tr>
						<td data-val="${field.fieldName}" data-readonly="true"></td>
						<!-- select的disabled通过data- 传不过去,data-notread也没用 -->
						<td data-val="${field.fieldType}" data-disabled="true"></td>
						<td data-val="${field.defaultValue}" data-readonly="true"></td>
						<td data-val="${field.remark}" data-readonly="true"></td>
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
