package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItemExample.Criteria;
import com.taotao.service.ItemService;

/**
 * 商品管理Service
 */
@Service
public class ItemServiceImpl implements ItemService{
	
	//注入
	@Autowired
	public TbItemMapper itemMapper;
	@Autowired
	public TbItemDescMapper itemDescMapper;
	@Autowired
	public TbItemParamItemMapper itemParamItemMapper;
	
	@Override
	public TbItem getItemById(long itemId) {
		return itemMapper.selectByPrimaryKey(itemId);
	}
	
	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {
		// 设置分页信息
		PageHelper.startPage(page, rows);
		// 执行查询
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example); //list是一个Page<> -- Page继承ArrayList
		// 得到查询结果
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		result.setTotal(pageInfo.getTotal());
		// 返回结果
		return result;
		// 发布服务到dubbo，让web引用
	}
	
	@Override
	public TaotaoResult addItem(TbItem item, String desc) {
		// 生成商品ID
		long itemId = IDUtils.genItemId();
		// 补全item的属性
		item.setId(itemId);
		// 商品状态，1.正常，2.下架，3.删除
		item.setStatus((byte) 1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		// 向Tb_Item表、Tb_item_desc表插入相关数据
		itemMapper.insert(item);
		// 创建商品描述表对应的pojo
		TbItemDesc itemDesc = new TbItemDesc();
		// 补全pojo数据，向Tb_item_desc表插入相关数据
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(new Date());
		itemDesc.setUpdated(new Date());
		itemDescMapper.insert(itemDesc);
		// 返回结果
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteItem(List<Long> itemIds) {
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(itemIds);
		itemMapper.deleteByExample(example);
		// 返回结果
		return TaotaoResult.ok();
	}

	/**
	 * 根据商品id查询商品描述
	 */
	@Override
	public TbItemDesc getItemDescById(long itemId){
		TbItemDescExample example = new TbItemDescExample();
		TbItemDescExample.Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		List<TbItemDesc> list = itemDescMapper.selectByExample(example);
		if (list.size() <= 0){
			return null;
		}
		return list.get(0);
	}

	@Override
	public TbItemParamItem getItemParamByItemId(long itemId) {
		TbItemParamItemExample example = new TbItemParamItemExample();
		TbItemParamItemExample.Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		List<TbItemParamItem> list = itemParamItemMapper.selectByExample(example);
		if (list.size() <= 0) {
			return null;
		}
		return list.get(0);
	}
}
