<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="Nested Panel" data-options="width:'100%',minHeight:500,noheader:true,border:false" style="padding:10px;">
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'west',split:false" style="width:250px;padding:5px">
            <ul id="contentCategoryTree" class="easyui-tree" data-options="url:'/content/category/list',animate: true,method : 'GET'">
            </ul>
        </div>
        <div data-options="region:'center'" style="padding:5px">
            <table class="easyui-datagrid" id="contentList" data-options="toolbar:contentListToolbar,singleSelect:false,collapsible:true,pagination:true,method:'get',pageSize:20,url:'/content/query/list',queryParams:{categoryId:0}">
		    <thead>
		        <tr>
		            <th data-options="field:'id',width:30">ID</th>
		            <th data-options="field:'title',width:120">内容标题</th>
		            <th data-options="field:'subTitle',width:100">内容子标题</th>
		            <th data-options="field:'titleDesc',width:120">内容描述</th>
		            <th data-options="field:'url',width:120,align:'center',formatter:TAOTAO.formatUrl">内容链接</th>
		            <th data-options="field:'pic',width:50,align:'center',formatter:TAOTAO.formatUrl">图片</th>
		            <th data-options="field:'pic2',width:50,align:'center',formatter:TAOTAO.formatUrl">图片2</th>
		            <th data-options="field:'created',width:130,align:'center',formatter:TAOTAO.formatDateTime">创建日期</th>
		            <th data-options="field:'updated',width:130,align:'center',formatter:TAOTAO.formatDateTime">更新日期</th>
		        </tr>
		    </thead>
		</table>
        </div>
    </div>
</div>
<script type="text/javascript">
var categoryId;
$(function(){
	var tree = $("#contentCategoryTree");
	var datagrid = $("#contentList");
	tree.tree({
		onClick : function(node){
			if(tree.tree("isLeaf",node.target)){
				categoryId = node.id;
				datagrid.datagrid('reload', {
					categoryId :node.id
		        });
			}
		},
		onDblClick : function(node) {
	        $(this).tree(node.state === 'closed' ? 'expand' : 'collapse', node.target);
	        node.state = node.state === 'closed' ? 'open' : 'closed';
	    }
	});
});
var contentListToolbar = [{
    text:'新增',
    iconCls:'icon-add',
    handler:function(){	//处理的方法
    	var node = $("#contentCategoryTree").tree("getSelected");
    	if(!node || !$("#contentCategoryTree").tree("isLeaf",node.target)){
    		$.messager.alert('喂喂喂','新增内容必须选择一个内容分类哦!');
    		return ;
    	}
    	//见common.js
    	TT.createWindow({
    		//content-add.jsp
			url : "/content-add"
		}); 
    }
},{
    text:'编辑',
    iconCls:'icon-edit',
    handler:function(){
    	var ids = TT.getSelectionsIds("#contentList");//获取id=contentList的那个table的被鼠标选中的数据
    	if(ids.length == 0){
    		$.messager.alert('别这样','必须选择一个内容才能编辑啊!');
    		return ;
    	}
    	if(ids.indexOf(',') > 0){
    		$.messager.alert('别这样','选择太多了啊，只能选择一个内容哦!');
    		return ;
    	}
		TT.createWindow({
			url : "/content-edit",
			onLoad : function(){
				var data = $("#contentList").datagrid("getSelections")[0];//获取contentList中被选中的那一行的数据
				console.log(data);
				$("#contentEditForm").form("load",data);	//来自content-edit.jsp里面的form，选中它，进行加载，把数据加载到content-edit中
				
				// 显示图片
				if(data.pic){
					$("#contentEditForm [name=pic]").after("<a href='"+data.pic+"' target='_blank'><img src='"+data.pic+"' width='80' height='50'/></a>");	
				}
				if(data.pic2){
					$("#contentEditForm [name=pic2]").after("<a href='"+data.pic2+"' target='_blank'><img src='"+data.pic2+"' width='80' height='50'/></a>");					
				}
				
				contentEditEditor.html(data.content);//初始化富文本编辑器
			}
		});    	
    }
},{
    text:'删除',
    iconCls:'icon-cancel',
    handler:function(){
    	var ids = TT.getSelectionsIds("#contentList");
    	var tree = $("#contentCategoryTree");
    	if(ids.length == 0){
    		$.messager.alert('嘿','未选中商品!');
    		return ;
    	}
    	$.messager.confirm('喂喂喂','真的要删除ID为 '+ids+' 的内容吗？',function(r){
    	    if (r){
    	    	var params = {"ids":ids,"categoryId":categoryId};
            	$.post("/content/delete",params, function(data){
        			if(data.status == 200){
        				$.messager.alert('好吧','删除内容成功哦!',undefined,function(){
        					$("#contentList").datagrid("reload");
        				});
        			} else {
        				$.messager.alert('抱歉','删除商品失败!');
        			}
        		});
    	    }
    	});
    }
}];
</script>