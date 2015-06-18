<%@ page contentType="text/html;charset=UTF-8"%>

<div class="bjui-pageFooter">
    <div class="pages">
        <span>每页&nbsp;</span>
        <div class="selectPagesize">
            <select data-toggle="selectpicker" data-toggle-change="changepagesize">
                <option value="20">20</option>
                <option value="50">50</option>
                <option value="100">100</option>
                <option value="200">200</option>
            </select>
        </div>
        <span>&nbsp;条，共 ${pagerForm.totalCount} 条</span>
    </div>
    <div class="pagination-box" data-toggle="pagination" data-total="${pagerForm.totalCount}" data-page-size="${pagerForm.pageSize}" data-page-current="${pagerForm.pageCurrent}">
    </div>
</div>
