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
    <form id="pagerForm" data-toggle="ajaxsearch" action="${ctx}/elasticsearch/search" method="post">
        <div class="bjui-searchBar">
            <label>topic名称：</label><input value="" name="indexName"  size="10">&nbsp;
            <label>需要查询的内容：</label><input value="" name="content"  size="8">&nbsp;
            <input type="button" name="search" value="查询" class="btn-default" onclick="esSearchRes()" data-icon="search"></button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" onclick="clear();" data-icon="undo">清空查询</a>
        </div>
    </form>
</div>

<div class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%" data-nowrap="true">
        <thead>
            <tr> 
                <th data-order-field="es">搜索结果</th>
            </tr>
        </thead>
        <tbody>
	        <tr>
	        <td id="result">
	        <textarea id="js-result" disabled style="width:100%;height:600px"></textarea></td>
	        </tr>
            
        </tbody>
    </table>
</div>

<script type="text/javascript" >

function esSearchRes() {
	var res ;
	var keyword = "score";
	var options = {
		url : "${ctx}/elasticsearch/search",
		callback : function(ret) {
			var str = JSON.stringify(ret);
			//str = str.replace(keyword, '<span style="color:red">'+keyword+'</span>');
			str = format(str);
			console.log(str);
			//console.log(JSON.stringify(ret));
			//res = $.parseJSON(ret);
			$("#js-result").html(str);
		}
	};
	$(this).bjuiajax('doAjax', options);
}


function format(txt,compress/*是否为压缩模式*/){/* 格式化JSON源码(对象转换为JSON文本) */  
        var indentChar = '    ';   
        if(/^\s*$/.test(txt)){   
            alert('数据为空,无法格式化! ');   
            return;   
        }   
        try{var data=eval('('+txt+')');}   
        catch(e){   
            alert('数据源语法错误,格式化失败! 错误信息: '+e.description,'err');   
            return;   
        };   
        var draw=[],last=false,This=this,line=compress?'':'\n',nodeCount=0,maxDepth=0;   
           
        var notify=function(name,value,isLast,indent/*缩进*/,formObj){   
            nodeCount++;/*节点计数*/  
            for (var i=0,tab='';i<indent;i++ )tab+=indentChar;/* 缩进HTML */  
            tab=compress?'':tab;/*压缩模式忽略缩进*/  
            maxDepth=++indent;/*缩进递增并记录*/  
            if(value&&value.constructor==Array){/*处理数组*/  
                draw.push(tab+(formObj?('"'+name+'":'):'')+'['+line);/*缩进'[' 然后换行*/  
                for (var i=0;i<value.length;i++)   
                    notify(i,value[i],i==value.length-1,indent,false);   
                draw.push(tab+']'+(isLast?line:(','+line)));/*缩进']'换行,若非尾元素则添加逗号*/  
            }else   if(value&&typeof value=='object'){/*处理对象*/  
                    draw.push(tab+(formObj?('"'+name+'":'):'')+'{'+line);/*缩进'{' 然后换行*/  
                    var len=0,i=0;   
                    for(var key in value)len++;   
                    for(var key in value)notify(key,value[key],++i==len,indent,true);   
                    draw.push(tab+'}'+(isLast?line:(','+line)));/*缩进'}'换行,若非尾元素则添加逗号*/  
                }else{   
                        if(typeof value=='string')value='"'+value+'"';   
                        draw.push(tab+(formObj?('"'+name+'":'):'')+value+(isLast?'':',')+line);   
                };   
        };   
        var isLast=true,indent=0;   
        notify('',data,isLast,indent,false);   
        return draw.join('');   
    }  

function clear(){
	document.getElementByName("indexName").innerHTML = "" ;
	document.getElementByName("content").innerHTML = "" ;
}
</script>
<%@ include file="/jsp/common/pagination.jsp"%>
	
