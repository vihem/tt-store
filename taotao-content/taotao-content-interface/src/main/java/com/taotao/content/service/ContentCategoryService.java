package com.taotao.content.service;

import java.util.List;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContentCategory;

public interface ContentCategoryService {

	List<EasyUITreeNode> getContentCategoryList(long parentId);//获取内容分类的列表
	TaotaoResult addContentCategory(long parentId, String name);//添加内容分类
	TaotaoResult updateContentCategory(long id, String name);	//重命名内容分类
	TaotaoResult deleteContentCategory(long currentId);	//删除内容分类
	TbContentCategory getParentByCurrentCategory(long id);	//获取当前结点的爸爸
	List<TbContentCategory> getChildByCurrentCategory(long id);	//获取当前节点的孩子
	boolean hasChildCategory(long id);//判断是否有孩子
}
