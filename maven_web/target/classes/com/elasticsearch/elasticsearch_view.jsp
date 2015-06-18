<%@page import="com.funshion.dobo.base.dao.entity.ElasticSearchDetail"%>
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

<div class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%" data-nowrap="true">
        <thead>
            <tr> 
            	<!-- <th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th> -->
                <th data-order-field="index">index名称</th>
                <th data-order-field="docs">type名称</th>
                <th data-order-field="size">id</th>
                <th data-order-field="source" width="100">source</th>
            </tr>
        </thead>
        <tbody>
        	<c:forEach var="item" items="${detail}" >
        		<tr data-id="${item.id}">
					<td>${item.index}</td>
	                <td>${item.type}</td>
	                <td>${item.id}</td>
	                <td>${item.source}</td>
	            </tr>
        	</c:forEach>
            
        </tbody>
    </table>
</div>
<%@ include file="/jsp/common/pagination.jsp"%>

	
