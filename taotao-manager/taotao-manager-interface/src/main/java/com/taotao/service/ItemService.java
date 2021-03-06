package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;

public interface ItemService {
	/**
	 * 根据id获取商品
	 * @param itemId long---数据库中类型是bigint
	 * @return TbItem bean
	 */
	TbItem getItemById(long itemId);
	/**
	 * 获取商品列表
	 * @param page 页码
	 * @param rows 行数
	 * @return EasyUIDataGridResult  json格式
	 */
	EasyUIDataGridResult getItemList(int page, int rows);
	/**
	 * 添加商品
	 * @param item 商品bean
	 * @param desc 商品描述
	 * @return TaotaoResult
	 */
	TaotaoResult addItem(TbItem item,String desc);
	
	/**
	 * 删除商品
	 * @param itemIds 商品id列表
	 * @return
	 * 2021年11月14日
	 */
	TaotaoResult deleteItem(List<Long> itemIds);

	/**
	 * 根据商品id查询商品描述
	 */
	TbItemDesc getItemDescById(long itemId);

	/**
	 * 根据商品id查询 商品规格
	 */
	TbItemParamItem getItemParamByItemId(long itemId);
}
