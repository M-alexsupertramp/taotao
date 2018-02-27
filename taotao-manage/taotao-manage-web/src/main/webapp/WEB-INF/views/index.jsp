<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>淘淘商城后台管理系统</title>
<jsp:include page="/commons/common-js.jsp"></jsp:include>
<style type="text/css">
	.content {
		padding: 10px 10px 10px 10px;
	}
</style>
</head>
<body>
	<div id="master-layout">
		<div data-options="region:'north',border:false,bodyCls:'theme-header-layout'">
        	<div class="theme-navigate">
                <div class="left">
                </div>
                <div class="right">
                    <a href="#" class="easyui-menubutton theme-navigate-more-button" data-options="menu:'#more',hasDownArrow:false"></a>
                    <div id="more" class="theme-navigate-more-panel">
                    	<div>联系管理员</div>
                        <div>安全退出</div>
                        <div>关于</div>
                    </div>
                </div>
            </div>
        </div>
		 <div data-options="region:'west',title:'菜单',split:true,border:false" style="width:200px; padding:10px 10px;">
	    	<ul id="menu" class="easyui-tree" style="margin-top: 10px;margin-left: 5px;">
	         	<li>
	         		<span>商品管理</span>
	         		<ul>
		         		<li data-options="attributes:{'url':'/rest/page/item-add'}">新增商品</li>
		         		<li data-options="attributes:{'url':'/rest/page/item-list'}">查询商品</li>
		         		<li data-options="attributes:{'url':'/rest/page/item-param-list'}">规格参数</li>
		         	</ul>
	         	</li>
	         	<li>
	         		<span>网站内容管理</span>
	         		<ul>
		         		<li data-options="attributes:{'url':'/rest/page/content-category'}">内容分类管理</li>
		         		<li data-options="attributes:{'url':'/rest/page/content'}">内容管理</li>
		         	</ul>
	         	</li>
	         </ul>
	    </div>
	    
	    <div data-options="region:'center',title:'',border:false">
	    	<div id="tabs" class="easyui-tabs" style="width:100%;height:100%">
			    <div title="首页" style="padding:20px;" data-options="fit:false,border:false,href:'/js/EasyUI_of_InsdepTheme-1.5.1-1.0.0-rc2/info.html'">
			        	
			    </div>
			</div>
	    </div>
	</div>
   
    
    <!--第三方插件加载-->
    <script src="/js/EasyUI_of_InsdepTheme-1.5.1-1.0.0-rc2/plugin/justgage-1.2.2/raphael-2.1.4.min.js"></script>
    <script src="/js/EasyUI_of_InsdepTheme-1.5.1-1.0.0-rc2/plugin/justgage-1.2.2/justgage.js"></script>

    <script src="/js/EasyUI_of_InsdepTheme-1.5.1-1.0.0-rc2/plugin/Highcharts-5.0.0/js/highcharts.js"></script>
    <!--第三方插件加载结束-->
    
    
<script type="text/javascript">
$(function(){
	/*布局部分*/
	$('#master-layout').layout({
		fit:true/*布局框架全屏*/
	});   
	
	$('#menu').tree({
		onClick: function(node){
			/* 
				判断指定的节点是否是叶子节点,参数是很多事件的回调函数都包含'node'参数，其具备如下属性：
				id：绑定节点的标识值。
				text：显示的节点文本。
				iconCls：显示的节点图标CSS类ID。
				checked：该节点是否被选中。
				state：节点状态，'open' 或 'closed'。
				attributes：绑定该节点的自定义属性。
				target：目标DOM对象。 
			*/
			if($('#menu').tree("isLeaf",node.target)){
				var tabs = $("#tabs");
				//getTab which 获取指定选项卡面板，'which'参数可以是选项卡面板的标题或者索引。 
				var tab = tabs.tabs("getTab",node.text);
				if(tab){
					//select which 选择一个选项卡面板，'which'参数可以是选项卡面板的标题或者索引。 
					tabs.tabs("select",node.text);
				}else{
					/*
					添加一个新选项卡面板，选项参数是一个配置对象，查看选项卡面板属性的更多细节。在添加一个新选项卡面板的时候它将变成可选的。
					添加一个非选中状态的选项卡面板时，记得设置'selected'属性为false。
					*/
					tabs.tabs('add',{
					    title:node.text,//标签标题
					    href: node.attributes.url,//新标签页要加载的地址  attributes：绑定该节点的自定义属性。
					    closable:true,
					    bodyCls:"content"
					});
				}
			}
		}
	});
});
</script>
</body>
</html>