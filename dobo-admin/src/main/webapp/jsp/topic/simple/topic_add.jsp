<%@page import="com.funshion.dobo.common.GlobalAdminConstant"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/jsp/common/taglibs.jsp"%>
<style>
.panel-body {
	padding : 0
}
</style>
<div class="bjui-pageContent">
	<form action="${ctx}/topic/simple/add" method="post" data-toggle="validate" data-alertmsg="false">
		<input id="hasSchema" name="hasSchema" value="false" type="hidden">
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
</script>