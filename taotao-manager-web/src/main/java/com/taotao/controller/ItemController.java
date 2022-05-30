package com.taotao.controller;

import java.util.ArrayList;
import java.util.List;

import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;

/**
 * 商品管理controller    web
 */
//可被springmvc.xml中的扫描包扫到
@Controller
public class ItemController {
	//注入service进行调用
	@Autowired
	private ItemService itemService;
	
	/* 注解：RequestMapping
	 * 用来处理请求地址映射的注解，可用于类或方法上。
	 * 用于类上，表示类中的所有响应请求的方法都是以该地址作为父路径。
	 * url的参数
	 *
	 * 注解：ResponseBody
	 * 将controller的方法返回的对象通过适当的转换器转换为指定的格式之后，
	 * 写入到response对象的body区，通常用来返回JSON数据或者是XML.
	 * 若有406错误，缺少jackson包，来自common包依赖
	 */
	/**
	 * Get item info by itemId.
	 * 根据Id获取商品信息
	 * @param itemId	PathVariable赋予请求url中的动态参数,即:将请求URL中的模板变量映射到接口方法的参数上
	 * @return
	 */
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long itemId) {
		return itemService.getItemById(itemId);
	}
	
	/**
	 * 来自/taotao-manager-web/src/main/webapp/WEB-INF/jsp/item-list.jsp里面的url:'/item/list'
	 * @param page 页数
	 * @param rows 行数
	 * @return
	 */
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		return itemService.getItemList(page, rows);
	}

	/**
	 *	添加商品
	 * @param item
	 * @param desc
	 * @return
	 */
	//设置链接和post请求
	@RequestMapping(value = "/item/save", method = RequestMethod.POST) 
	@ResponseBody	//响应json数据
	public TaotaoResult addItem(TbItem item, String desc) {
		return itemService.addItem(item, desc);
	}

	@RequestMapping("/rest/item/delete") 
	@ResponseBody	//响应json数据
	public TaotaoResult deleteItem(String ids) {
		System.out.println(" 即将删除该商品，id为：" + ids);
		String[] idList = ids.split(",");
		List<Long> itemIds = new ArrayList<Long>();
		for (int i = 0; i < idList.length; i++) {
			itemIds.add(Long.parseLong(idList[i]));
		}
		return itemService.deleteItem(itemIds);
	}

	/**
	 * 根据商品id查询商品描述
	 */
	@RequestMapping("/itemDesc/{itemId}")
	@ResponseBody
	public TbItemDesc getItemDescById(@PathVariable Long itemId) {
		return itemService.getItemDescById(itemId);
	}

	/**
	 * 根据商品id查询 商品规格
	 */
	@RequestMapping("/itemParam/{itemId}")
	@ResponseBody
	public TbItemParamItem getItemParamByItemId(@PathVariable Long itemId) {
		System.out.println("根据商品id查询 商品规格 " + itemId);
		return itemService.getItemParamByItemId(itemId);
	}
}
