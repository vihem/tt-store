<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div>
	<input type="button" data-options="onClick:expandAll"/>
	 <ul id="contentCategory" class="easyui-tree">
    </ul>
</div>
<!-- 下面是实现页面上，鼠标右键相应的分类选项，跳出菜单控件，onContextMenu -->
<div id="contentCategoryMenu" class="easyui-menu" style="width:120px;" data-options="onClick:menuHandler">
    <div data-options="iconCls:'icon-add',name:'add'">添加</div>
    <div data-options="iconCls:'icon-remove',name:'rename'">重命名</div>
    <div class="menu-sep"></div>
    <div data-options="iconCls:'icon-remove',name:'delete'">删除</div>
</div>
<script type="text/javascript">
//$()页面初始化之后调用该function
$(function(){
	//找到id=contentCategory的控件，创建tree
	$("#contentCategory").tree({
		url : '/content/category/list',
		animate: true,	//动画效果打开
		method : "GET",
		//dnd: true,//拖放
		//实现双击打开
		onDblClick: function(node) {
            $(this).tree(node.state === 'closed' ? 'expand' : 'collapse', node.target);
            node.state = node.state === 'closed' ? 'open' : 'closed';
        },
		//右键触发事件
		onContextMenu: function(e,node){
            e.preventDefault();
            $(this).tree('select',node.target);	//选中当前节点(背景变黄了)--改变选中状态
            //显示右键菜单的坐标
            $('#contentCategoryMenu').menu('show',{
                left: e.pageX,
                top: e.pageY
            });
        },
        //编辑结束之后触发该函数
        onAfterEdit : function(node){
        	var _tree = $(this);
        	if(node.id == 0){
        		// 新增节点，调用controller，提交请求 -- 提交时 parentId=node.parentId&name=node.text
        		$.post("/content/category/create",{parentId:node.parentId,name:node.text},function(data){
        			//Ajax返回数据json。使用TaotaoResult：data={status,msg,data}
        			if(data.status == 200){
        				//更新当前结点信息，把id改为后端新生成的该节点id，是为真正的id
        				_tree.tree("update",{
            				target : node.target,
            				id : data.data.id	//TaotaoResult里面偶遇一个data属性:data={id}，更新一个新的id，使不再为0
            			});
        			}else{
        				$.messager.alert('不好意思啊','创建 '+node.text+' 分类失败!');
        			}
        		});
        	}else{
        		//id !== 0 ：重命名
        		$.post("/content/category/update",{id:node.id,name:node.text}, function(data){
        			if(data.status == 200){
        				$.messager.alert('恭喜你了咯', node.text+' 重命名成功!');
        			}else{
        				$.messager.alert('真糟糕', node.id + " " + node.text+' 重命名失败!');
        			}
        			
        		});
        	}
        }
	});
});
//右键操作调用函数
function menuHandler(item){
	var tree = $("#contentCategory");//获取tree对象
	var node = tree.tree("getSelected");//得到当前节点
	//判断选择的菜单
	if(item.name === "add"){
		//当前结点添加一个子节点
		tree.tree('append', {
            parent: (node?node.target:null),//得到爸爸状态
            data: [{
                text: '新建分类',
                id : 0,
                parentId : node.id//把当前节点的id设为添加节点的parentId
            }]
        }); 
		var _node = tree.tree('find',0);//找到tree中节点id为0的节点
		tree.tree("select",_node.target).tree('beginEdit',_node.target);//选中该节点，并变为可编辑状态
	}else if(item.name === "rename"){
		tree.tree('beginEdit',node.target);
	}else if(item.name === "delete"){
		// 判断是否是叶子节点
		$.post("/content/category/hasChild",{id:node.id},function(data){
			if(data.status == 200){
				$.messager.confirm('确认','当前分类 '+node.text+' 有子分类，是否全部删除？',function(r){
					if(r){
						$.post("/content/category/delete/",{/* parentId:node.parentId, */id:node.id},function(data){
							if(data.status == 200){
								tree.tree("remove",node.target);
								$.messager.alert('哎呀', node.text+' 删除成功了哦!');
							}
						});
					}
				});
			} else {
				$.messager.confirm('确认','确定删除名为 '+node.text+' 的分类吗？',function(r){
					if(r){
						$.post("/content/category/delete/",{/* 从数据库查询出的是没有这个的parentId:node.parentId, */id:node.id},function(data){
							if(data.status == 200){
								tree.tree("remove",node.target);
								$.messager.alert('哎呀', node.text+' 删除成功啦!');
							}
						});
					}
				});
			}
		});
	}
}
</script>