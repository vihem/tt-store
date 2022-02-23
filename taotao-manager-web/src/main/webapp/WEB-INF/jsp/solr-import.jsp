<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div>
    <a class="easyui-linkbutton" onclick="importSorl()">一键导入商品数据到索引库</a>
</div>
<script type="text/javascript">
function importSorl() {
	$.post("/solr/import", function(data){
		if(data.status == 200){
			$.messager.alert('提示','导入索引库成功!');
		} else {
			$.messager.alert('提示','导入失败!');
		}
	});
}
</script>