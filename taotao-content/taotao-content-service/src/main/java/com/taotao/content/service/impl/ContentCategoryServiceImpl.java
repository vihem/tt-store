package com.taotao.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;

/**
 * 内容分类管理Service
 * @author yeasue
 *
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;//注入mapper
	
	@Override
	public List<EasyUITreeNode> getContentCategoryList(long parentId) {
		/*
		 * 根据parentId查询子节点列表
		 */
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);//设置parentId
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		List<EasyUITreeNode> resultList = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
			resultList.add(node);	//添加到列表
		}
		return resultList;//最后还要发布服务
	}
	/**
	 * 添加内容分类
	 */
	@Override
	public TaotaoResult addContentCategory(long parentId, String name) {
		//1.创建pojo对象
		TbContentCategory contentCategory = new TbContentCategory();
		//2.补全对象属性
		contentCategory.setParentId(parentId);//父类目id
		contentCategory.setName(name);//分类名称
		contentCategory.setStatus(1);//状态status：1.正常，2.删除
		contentCategory.setSortOrder(1);//排列序号，默认为1
		contentCategory.setIsParent(false);//新加的节点都是叶子节点
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		//3.插入数据库,在插入之后会执行SELECT LAST_INSERT_ID()，把返回的id赋给contentCategory的id属性
		contentCategoryMapper.insert(contentCategory);
		//4.判断它的父类目原来是否是父节点
		TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
		if (!parent.getIsParent()) {
			// 不是父节点，即为叶子节点，改为父节点
			parent.setIsParent(true);
			// 更新该父节点
			contentCategoryMapper.updateByPrimaryKey(parent);
		}
		//5.返回结果
		return TaotaoResult.ok(contentCategory);
	}
	/**
	 * 内容分类的重命名
	 */
	@Override
	public TaotaoResult updateContentCategory(long id, String name) {
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setId(id);
		contentCategory.setName(name);
		contentCategory.setUpdated(new Date());
		contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
		return TaotaoResult.ok(contentCategory);
	}
	/**
	 * 删除当前节点以及其所有子节点
	 */
	@Override
	public TaotaoResult deleteContentCategory(long id) {
		// 根据当前节点id获取当前的节点
		TbContentCategory currentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		// 查询当前节点的子节点
		List<TbContentCategory> childrenCategories = getChildByCurrentCategory(id);
		// 判断子节点是否存在
		if (childrenCategories.size() > 0) {
			for (TbContentCategory childCategory : childrenCategories) {
				// 存在子节点，调用deleteContentCategory函数
				deleteContentCategory(childCategory.getId());
			}
		}
		// 此时当前节点是为叶子节点了，删除当前节点
		contentCategoryMapper.deleteByPrimaryKey(id);
		/*
		 * 根据当前节点(数据库中可能已经删除)的parentId，得到父节点，再获取该父节点的子节点，
		 * 		如果没有子节点了，更新该父节点的is_parent字段为0
		 */
		TbContentCategory parent = getParentByCurrentCategory(currentCategory.getParentId());
		List<TbContentCategory> childrenWithParent = getChildByCurrentCategory(parent.getId());
		if (childrenWithParent.size() == 0) {
			parent.setIsParent(false);
			contentCategoryMapper.updateByPrimaryKey(parent);
		}
		
		
//		// 判断该节点是否为父节点
//		if ( !currentCategory.getIsParent()) {
//			/*
//			 *  不是父节点，即为叶子节点，直接修改status=2，即逻辑删除，或者直接调用delete：物理删除(这里采用当前方法)
//			 *  1. 根据当前节点的parentId查询它的爸爸，修改其is_parent属性值为0(false)
//			 *  2. 删除当前节点
//			 */
//			TbContentCategory parent = getParentByCurrentCategory(currentCategory);
//			parent.setIsParent(false);
//			contentCategoryMapper.updateByPrimaryKey(parent);
//			contentCategoryMapper.deleteByPrimaryKey(id);//删掉当前的节点
//		} else {
//			// 是父节点，即不是叶子节点，需要查到当前结点的所有孩子
//			List<TbContentCategory> childrenCategories = getChildByCurrentCategory(currentCategory);
//			for (TbContentCategory childCategory : childrenCategories) {
//				deleteContentCategory(childCategory.getId());
//			}
//		}
		return TaotaoResult.ok(currentCategory);
	}
	/**
	 * 获取爸爸节点
	 */
	@Override
	public TbContentCategory getParentByCurrentCategory(long id) {
		return contentCategoryMapper.selectByPrimaryKey(id);
	}
	/**
	 * 获取所有儿子
	 */
	@Override
	public List<TbContentCategory> getChildByCurrentCategory(long parentId) {
		//根据parentId查询当前节点的儿子
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);//currentCategory的id就是它的子节点的父节点
		return contentCategoryMapper.selectByExample(example);
	}
	/**
	 * 判断是否拥有孩子
	 */
	@Override
	public boolean hasChildCategory(long id) {
		List<TbContentCategory> childrenCategories = getChildByCurrentCategory(id);
		return childrenCategories.size() != 0;
	}

}
