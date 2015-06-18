<%@ page contentType="text/html;charset=UTF-8"%>
<!-- bootstrap - css -->
<link href="${ctx}/resources/BJUI/themes/css/bootstrap.css" rel="stylesheet">
<!-- core - css -->
<link href="${ctx}/resources/BJUI/themes/css/style.css" rel="stylesheet">
<link href="${ctx}/resources/BJUI/themes/blue/core.css" id="bjui-link-theme" rel="stylesheet">
<!-- plug - css -->
<link href="${ctx}/resources/BJUI/plugins/kindeditor_4.1.10/themes/default/default.css" rel="stylesheet">
<link href="${ctx}/resources/BJUI/plugins/colorpicker/css/bootstrap-colorpicker.min.css" rel="stylesheet">
<link href="${ctx}/resources/BJUI/plugins/niceValidator/jquery.validator.css" rel="stylesheet">
<link href="${ctx}/resources/BJUI/plugins/bootstrapSelect/bootstrap-select.css" rel="stylesheet">
<link href="${ctx}/resources/BJUI/themes/css/FA/css/font-awesome.min.css" rel="stylesheet">
<!--[if lte IE 7]>
<link href="${ctx}/resources/BJUI/themes/css/ie7.css" rel="stylesheet">
<![endif]-->
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lte IE 9]>
    <script src="${ctx}/resources/BJUI/other/html5shiv.min.js"></script>
    <script src="${ctx}/resources/BJUI/other/respond.min.js"></script>
<![endif]-->
<!-- jquery -->
<script src="${ctx}/resources/BJUI/js/jquery-1.7.2.min.js"></script>
<script src="${ctx}/resources/BJUI/js/jquery.cookie.js"></script>
<!--[if lte IE 9]>
<script src="${ctx}/resources/BJUI/other/jquery.iframe-transport.js"></script>    
<![endif]-->
<!-- ${ctx}/resources/BJUI.all 分模块压缩版 -->
<script src="${ctx}/resources/BJUI/js/bjui-all.js"></script>
<!-- 以下是B-JUI的分模块未压缩版，建议开发调试阶段使用下面的版本 -->
<!--
<script src="${ctx}/resources/BJUI/js/bjui-core.js"></script>
<script src="${ctx}/resources/BJUI/js/bjui-regional.zh-CN.js"></script>
<script src="${ctx}/resources/BJUI/js/bjui-frag.js"></script>
<script src="${ctx}/resources/BJUI/js/bjui-extends.js"></script>
<script src="${ctx}/resources/BJUI/js/bjui-basedrag.js"></script>
<script src="${ctx}/resources/BJUI/js/bjui-slidebar.js"></script>
<script src="${ctx}/resources/BJUI/js/bjui-contextmenu.js"></script>
<script src="${ctx}/resources/BJUI/js/bjui-navtab.js"></script>
<script src="${ctx}/resources/BJUI/js/bjui-dialog.js"></script>
<script src="${ctx}/resources/BJUI/js/bjui-taskbar.js"></script>
<script src="${ctx}/resources/BJUI/js/bjui-ajax.js"></script>
<script src="${ctx}/resources/BJUI/js/bjui-alertmsg.js"></script>
<script src="${ctx}/resources/BJUI/js/bjui-pagination.js"></script>
<script src="${ctx}/resources/BJUI/js/bjui-util.date.js"></script>
<script src="${ctx}/resources/BJUI/js/bjui-datepicker.js"></script>
<script src="${ctx}/resources/BJUI/js/bjui-ajaxtab.js"></script>
<script src="${ctx}/resources/BJUI/js/bjui-datagrid.js"></script>
<script src="${ctx}/resources/BJUI/js/bjui-tablefixed.js"></script>
<script src="${ctx}/resources/BJUI/js/bjui-tabledit.js"></script>
<script src="${ctx}/resources/BJUI/js/bjui-spinner.js"></script>
<script src="${ctx}/resources/BJUI/js/bjui-lookup.js"></script>
<script src="${ctx}/resources/BJUI/js/bjui-tags.js"></script>
<script src="${ctx}/resources/BJUI/js/bjui-upload.js"></script>
<script src="${ctx}/resources/BJUI/js/bjui-theme.js"></script>
<script src="${ctx}/resources/BJUI/js/bjui-initui.js"></script>
<script src="${ctx}/resources/BJUI/js/bjui-plugins.js"></script>
-->
<!-- plugins -->
<!-- swfupload for uploadify && kindeditor -->
<script src="${ctx}/resources/BJUI/plugins/swfupload/swfupload.js"></script>
<!-- kindeditor -->
<script src="${ctx}/resources/BJUI/plugins/kindeditor_4.1.10/kindeditor-all.min.js"></script>
<script src="${ctx}/resources/BJUI/plugins/kindeditor_4.1.10/lang/zh_CN.js"></script>
<!-- colorpicker -->
<script src="${ctx}/resources/BJUI/plugins/colorpicker/js/bootstrap-colorpicker.min.js"></script>
<!-- ztree -->
<script src="${ctx}/resources/BJUI/plugins/ztree/jquery.ztree.all-3.5.js"></script>
<!-- nice validate -->
<script src="${ctx}/resources/BJUI/plugins/niceValidator/jquery.validator.js"></script>
<script src="${ctx}/resources/BJUI/plugins/niceValidator/jquery.validator.themes.js"></script>
<!-- bootstrap plugins -->
<script src="${ctx}/resources/BJUI/plugins/bootstrap.min.js"></script>
<script src="${ctx}/resources/BJUI/plugins/bootstrapSelect/bootstrap-select.min.js"></script>
<script src="${ctx}/resources/BJUI/plugins/bootstrapSelect/defaults-zh_CN.min.js"></script>
<!-- icheck -->
<script src="${ctx}/resources/BJUI/plugins/icheck/icheck.min.js"></script>
<!-- dragsort -->
<script src="${ctx}/resources/BJUI/plugins/dragsort/jquery.dragsort-0.5.1.min.js"></script>
<!-- HighCharts -->
<script src="${ctx}/resources/BJUI/plugins/highcharts/highcharts.js"></script>
<script src="${ctx}/resources/BJUI/plugins/highcharts/highcharts-3d.js"></script>
<script src="${ctx}/resources/BJUI/plugins/highcharts/themes/gray.js"></script>
<!-- ECharts -->
<script src="${ctx}/resources/BJUI/plugins/echarts/echarts.js"></script>
<!-- other plugins -->
<script src="${ctx}/resources/BJUI/plugins/other/jquery.autosize.js"></script>
<link href="${ctx}/resources/BJUI/plugins/uploadify/css/uploadify.css" rel="stylesheet">
<script src="${ctx}/resources/BJUI/plugins/uploadify/scripts/jquery.uploadify.min.js"></script>
<script src="${ctx}/resources/BJUI/plugins/download/jquery.fileDownload.js"></script>

<script>
/**
 * init bjui
 */
$(function() {
    BJUI.init({
        JSPATH       : 'BJUI/',         //[可选]框架路径
        PLUGINPATH   : 'BJUI/plugins/', //[可选]插件路径
        loginInfo    : {url:'login_timeout.html', title:'登录', width:400, height:200}, // 会话超时后弹出登录对话框
        statusCode   : {ok:200, error:300, timeout:301}, //[可选]
        ajaxTimeout  : 50000, //[可选]全局Ajax请求超时时间(毫秒)
        pageInfo     : {total:'total', pageCurrent:'pageCurrent', pageSize:'pageSize', orderField:'orderField', orderDirection:'orderDirection'}, //[可选]分页参数
        alertMsg     : {displayPosition:'topcenter', displayMode:'slide', alertTimeout:3000}, //[可选]信息提示的显示位置，显隐方式，及[info/correct]方式时自动关闭延时(毫秒)
        keys         : {statusCode:'statusCode', message:'message'}, //[可选]
        ui           : {
                         showSlidebar     : true, //[可选]左侧导航栏锁定/隐藏
                         clientPaging     : true, //[可选]是否在客户端响应分页及排序参数
                         overwriteHomeTab : false //[可选]当打开一个未定义id的navtab时，是否可以覆盖主navtab(我的主页)
                       },
        debug        : true,    // [可选]调试模式 [true|false，默认false]
        theme        : 'sky' // 若有Cookie['bjui_theme'],优先选择Cookie['bjui_theme']。皮肤[五种皮肤:default, orange, purple, blue, red, green]
    });
 	// main - menu
    $('#bjui-accordionmenu')
        .collapse()
        .on('hidden.bs.collapse', function(e) {
            $(this).find('> .panel > .panel-heading').each(function() {
                var $heading = $(this), $a = $heading.find('> h4 > a')
                
                if ($a.hasClass('collapsed')) $heading.removeClass('active')
            })
        })
        .on('shown.bs.collapse', function (e) {
            $(this).find('> .panel > .panel-heading').each(function() {
                var $heading = $(this), $a = $heading.find('> h4 > a')
                
                if (!$a.hasClass('collapsed')) $heading.addClass('active')
            })
        })
        
    $(document).on('click', 'ul.menu-items li > a', function(e) {
        var $a = $(this), $li = $a.parent(), options = $a.data('options').toObj(), $children = $li.find('> .menu-items-children')
        var onClose = function() {
            $li.removeClass('active')
        }
        var onSwitch = function() {
            $('#bjui-accordionmenu').find('ul.menu-items li').removeClass('switch')
            $li.addClass('switch')
        }
        
        $li.addClass('active')
        if (options) {
            options.url      = $a.attr('href')
            options.onClose  = onClose
            options.onSwitch = onSwitch
            if (!options.title) options.title = $a.text()
            
            if (!options.target)
                $a.navtab(options)
            else
                $a.dialog(options)
        }
        if ($children.length) {
            $li.toggleClass('open')
        }
        
        e.preventDefault()
    })
    
    //时钟
    var today = new Date(), time = today.getTime()
    $('#bjui-date').html(today.formatDate('yyyy/MM/dd'))
    setInterval(function() {
        today = new Date(today.setSeconds(today.getSeconds() + 1))
        $('#bjui-clock').html(today.formatDate('HH:mm:ss'))
    }, 1000)
})

//菜单-事件
function MainMenuClick(event, treeId, treeNode) {
    event.preventDefault()
    
    if (treeNode.isParent) {
        var zTree = $.fn.zTree.getZTreeObj(treeId)
        
        zTree.expandNode(treeNode, !treeNode.open, false, true, true)
        return
    }
    
    if (treeNode.target && treeNode.target == 'dialog')
        $(event.target).dialog({id:treeNode.tabid, url:treeNode.url, title:treeNode.name})
    else
        $(event.target).navtab({id:treeNode.tabid, url:treeNode.url, title:treeNode.name, fresh:treeNode.fresh, external:treeNode.external})
}
</script>