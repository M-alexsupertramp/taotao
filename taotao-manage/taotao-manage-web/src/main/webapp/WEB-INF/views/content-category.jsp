<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div>
	 <ul id="contentCategory" class="easyui-tree"> </ul>
</div>
<div id="contentCategoryMenu" class="easyui-menu" style="width:120px;" data-options="onClick:menuHandler">
    <div data-options="iconCls:'icon-add',name:'add'">添加</div>
    <div data-options="iconCls:'icon-remove',name:'rename'">重命名</div>
    <div class="menu-sep"></div>
    <div data-options="iconCls:'icon-remove',name:'delete'">删除</div>
</div>
<script type="text/javascript">
$(function(){
	$("#contentCategory").tree({
		url : '/rest/content/category',
		animate: true,
		method : "GET",
		onContextMenu: function(e,node){
            e.preventDefault(); //阻止默认事件执行
            $(this).tree('select',node.target); //选中执行右键事件的行
            $('#contentCategoryMenu').menu('show',{
                left: e.pageX,
                top: e.pageY
            });
        },
        onAfterEdit : function(node){
        	var _tree = $(this);
        	if(node.id == 0){
        		// 新增节点
        		$.post("/rest/content/category",{parentId:node.parentId,name:node.text},function(data){
        			_tree.tree("update",{
        				target : node.target,
        				id : data.id
        			});
        		});
        	}else{
        		$.ajax({
        			   type: "PUT",
        			   url: "/rest/content/category",
        			   data: {id:node.id,name:node.text},
        			   success: function(msg){
        				   //$.messager.alert('提示','新增商品成功!');
        			   },
        			   error: function(){
        				   $.messager.alert('提示','重命名失败!');
        			   }
        			});
        	}
        }
	});
});
function menuHandler(item){
	//获取树对象
	var tree = $("#contentCategory");
	//获取树中被选中的节点
	var node = tree.tree("getSelected");
	//-判断是添加,删除,编辑中的哪一个
	if(item.name === "add"){
		//如果是添加,调用append,新增节点
		tree.tree('append', {
			/*
			parent：DOM对象，将要被追加子节点的父节点，如果未指定，子节点将被追加至根节点。
			data：数组，节点数据。
			*/
			//指定新增节点的父节点为当前选择节点
            parent: (node?node.target:null),   //打开或关闭节点的触发器，target参数是一个节点DOM对象。
            data: [{
                text: '新建分类',//新增节点的名称
                id : 0, //新增节点的默认id,用0是为了不跟其它节点冲突
                parentId : node.id  //新增节点的父节点id
            }]
        }); 
		//拿到新增的这个节点
		var _node = tree.tree('find',0);
		//打开编辑框,开始编辑新增节点名称  beginEdit-->开始编辑一个节点
		tree.tree("select",_node.target).tree('beginEdit',_node.target);
	}else if(item.name === "rename"){
		//打开编辑框,开始编辑选中的节点
		tree.tree('beginEdit',node.target);
	}else if(item.name === "delete"){
		//确定是否删除
		$.messager.confirm('确认','确定删除名为 '+node.text+' 的分类吗？',function(r){
			if(r){
				//发起请求,删除指定的节点
				$.ajax({
     			   type: "POST",
     			   url: "/rest/content/category",
     			   data : {parentId:node.parentId,id:node.id,"_method":"DELETE"},
     			   success: function(msg){
     				   //$.messager.alert('提示','新增商品成功!');
     				  tree.tree("remove",node.target);
     			   },
     			   error: function(){
     				   $.messager.alert('提示','删除失败!');
     			   }
     			});
			}
		});
	}
}
</script>