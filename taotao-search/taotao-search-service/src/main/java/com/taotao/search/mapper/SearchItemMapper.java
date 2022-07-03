package com.taotao.search.mapper;

import java.util.List;

import com.taotao.common.pojo.SearchItem;

/**
 * Search独特的DAO、Mapper，不需要为此定义一个新的DAO模块
 * 定义模块是为了可以提供其他项目模块使用
 */
public interface SearchItemMapper {
	/**
	 * 查询tb_item、tb_item_cat、tb_item_desc关联的数据
	 */
	List<SearchItem> getItemList();
}
