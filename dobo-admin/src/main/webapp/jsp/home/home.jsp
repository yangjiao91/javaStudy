<%@ page contentType="text/html;charset=UTF-8"%>

<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<%@ include file="/jsp/common/meta.jsp"%>
		<%@ include file="/jsp/common/taglibs.jsp"%>
		<%@ include file="/jsp/common/bjui.jsp"%>
		
		<!-- init -->
		<script type="text/javascript">
		//菜单-事件
		function MainMenuClick(event, treeId, treeNode) {
		    event.preventDefault()
		    
		    if (treeNode.isParent) {
		        var zTree = $.fn.zTree.getZTreeObj(treeId)
		        zTree.expandNode(treeNode)
		        return
		    }
		    
		    if (treeNode.target && treeNode.target == 'dialog')
		        $(event.target).dialog({id:treeNode.tabid, url:treeNode.url, title:treeNode.name})
		    else
		        $(event.target).navtab({id:treeNode.tabid, url:treeNode.url, title:treeNode.name, fresh:treeNode.fresh, external:treeNode.external})
		}
		</script>
	</head>
	
	
	<%@ include file="/jsp/common/header.jsp"%>
	
	<div id="bjui-container" class="clearfix">
		<div id="bjui-leftside">
		    <div id="bjui-sidebar-s">
		        <div class="collapse"></div>
		    </div>
		    <div id="bjui-sidebar">
		        <div class="toggleCollapse"><h2><i class="fa fa-bars"></i> 导航栏 <i class="fa fa-bars"></i></h2><a href="javascript:;" class="lock"><i class="fa fa-lock"></i></a></div>
		        <div class="panel-group panel-main" data-toggle="accordion" id="bjui-accordionmenu" data-heightbox="#bjui-sidebar" data-offsety="26">
		            <div class="panel panel-default">
		                <div class="panel-heading panelContent">
		                    <h4 class="panel-title"><a data-toggle="collapse" data-parent="#bjui-accordionmenu" href="#bjui-collapse0" class="active"><i class="fa fa-caret-square-o-down"></i>&nbsp;confluent管理</a></h4>
		                </div>
		                <div id="bjui-collapse0" class="panel-collapse panelContent collapse in">
		                    <div class="panel-body" >
		                        <ul id="bjui-tree" class="ztree ztree_main" data-toggle="ztree" data-on-click="MainMenuClick" data-expand-all="true">
		                            <li data-id="1" data-pid="0" data-faicon="th-large">topic schema管理</li>
		                            <li data-id="10" data-pid="1" data-url="${ctx}/topic/list" data-tabid="topic_list" data-faicon="hand-o-up">有格式topic</li>
		                            <li data-id="11" data-pid="1" data-url="${ctx}/schema/list" data-tabid="schema_list" data-faicon="hand-o-up">schema</li>
		                            <li data-id="12" data-pid="1" data-url="${ctx}/topic/simple/list" data-tabid="topic_simple_list" data-faicon="hand-o-up">无格式topic</li>
		                        </ul>
		                        <ul id="bjui-tree-es" class="ztree ztree_es" data-toggle="ztree" data-on-click="MainMenuClick" data-expand-all="true">
		                            <li data-id="1" data-pid="0" data-faicon="th-large">elasticsearch</li>
		                            <li data-id="10" data-pid="1" data-url="${ctx}/elasticsearch/list" data-tabid="elasticsearch_list" data-faicon="hand-o-up">elasticsearch查询</li>
		                        </ul>
		                    </div>
		                </div>
		                <div class="panelFooter"><div class="panelFooterContent"></div></div>
		            </div>
		        </div>
		    </div>
		</div>
		<div id="bjui-navtab" class="tabsPage">
			<!-- 工作区(navtab) -->
			<div class="tabsPageHeader">
                <div class="tabsPageHeaderContent">
                    <ul class="navtab-tab nav nav-tabs">
                        <li ><a href="javascript:;"><span><i class="fa fa-home"></i>Welcome</span></a></li>
                    </ul>
                </div>
                <div class="tabsLeft"><i class="fa fa-angle-double-left"></i></div>
                <div class="tabsRight"><i class="fa fa-angle-double-right"></i></div>
                <div class="tabsMore"><i class="fa fa-angle-double-down"></i></div>
            </div>
            <!-- tabsMoreList 最右端tab下拉框中的内容#tabname#和 tabsPageHeaderContent.nav-tabs中的tabname要一致-->
            <ul class="tabsMoreList">
                <li><a href="javascript:;">#maintab#</a></li>
            </ul>
            <!-- 没有tab的时候就显示以下内容 -->
            <div class="navtab-panel tabsPageContent">
                <div class="navtabPage unitBox">
                    <div class="bjui-pageHeader" style="background:#FFF;">
                        <div style="padding: 0 15px;">
                            <!-- <h4>
                                <li data-url="index_layout.html"><a href="javascript:;"><span><i class="fa fa-home"></i> #maintab#</span></a></li>
                            </h4> -->
                            <div class="row">
                                <div class="col-md-4">
                                    <div class="alert alert-success" role="alert" style="margin:0 0 5px; padding:5px 15px;">
                                        <strong>风信子团队欢迎你!</strong>
                                        <br><span class="label label-default">开发：</span> lirui
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
		</div>
	</div>
	<footer id="bjui-footer">
		<!-- 页脚 -->
		Copyright &copy; 2013 - 2015　<a href="http://www.fun.tv/" target="_blank">风信子</a>
	</footer>

</html>

