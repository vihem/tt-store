package com.taotao.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;

/**
 * 内容管理Controller
 * @author yeasue
 */
@Controller
public class ContentController {
	@Autowired
	private ContentService contentService;
	/**
	 * 获取内容列表
	 * @param page	页码数
	 * @param rows	显示的行数
	 */
	@RequestMapping("/content/query/list")
	@ResponseBody
	public EasyUIDataGridResult getContentList(Long categoryId, Integer page, Integer rows) {
		return contentService.getContentList(categoryId, page, rows);
	}
	
	/**
	 * 添加内容 -- post请求
	 * /content/save来自content-add.jsp的submitForm()
	 * @param content
	 */
	@RequestMapping("/content/save")
	@ResponseBody
	public TaotaoResult addContent(TbContent content) {
		return contentService.addContent(content);
	}
	
	@RequestMapping("/content/edit")
	@ResponseBody
	public TaotaoResult editContent(TbContent content) {
		return contentService.editContent(content);
		
	}
	
	@RequestMapping("/content/delete")
	@ResponseBody
	public TaotaoResult deleteContent(String ids, Long categoryId) {
//		List<Long> idList = Arrays.asList(ids.split(","))
//				.stream()
//				.map(s -> Long.parseLong(s.trim()))
//				.collect(Collectors.toList());
		String[] splitIds = ids.split(",");
		List<Long> idList = new ArrayList<>();
		for (String splitId : splitIds) {
			idList.add(Long.parseLong(splitId));
		}
		return contentService.deleteContent(idList,categoryId);
	}
}
