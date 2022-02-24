package com.taotao.content.service;

import java.util.List;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {
	/**
	 * 后台 获取内容列表
	 * 根据分类id获取content列表
	 * @param categoryId 分类id
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGridResult getContentList(Long categoryId, int page, int rows);
	/**
	 * 后台 添加内容
	 * 如果纠结返回类型，可以使用TaotaoResult
	 * @param content
	 * @return
	 */
	TaotaoResult addContent(TbContent content);
	/**
	 * 后台 修改内容
	 * @param content
	 * @return
	 */
	TaotaoResult editContent(TbContent content);
	/**
	 * 后台 根据内容id删除内容
	 * @param ids
	 * @param categoryId
	 * @return
	 */
	TaotaoResult deleteContent(List<Long> ids, long categoryId);
	
	/**
	 * 前台 根据分类id获取contents
	 * @param cid 数据库tb_content表的category_id
	 * @return
	 */
	List<TbContent> getContentsByCid(long cid);
	
	List<TbContent> getSwiper();
}
