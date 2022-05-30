package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.service.ItemCatService;

/**
 * The controller of the management of item category 
 * 商品分类管理controller
 */
@Controller
public class ItemCatController {
	
	/*
	 * 需要在springmvc.xml中引用服务com.taotao.service.ItemCatService。 (dubbo)
	 * 再注入服务
	 */
	@Autowired
	private ItemCatService itemCatService;
	
	/**
	 * /item/cat/list来自common.js里面的initItemCat函数
	 * @param parentId RequestParam(name = "id",defaultValue = "0") Long parentId  把url获取的参数id映射为parentId，并给默认值为0
	 */
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<EasyUITreeNode> getItemCatList(@RequestParam(name = "id",defaultValue = "0") Long parentId){
		return itemCatService.getItemCatList(parentId);
	}
}
