package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;

/**
 * 内容分类Controller
 * @author yeasue
 * 调用服务，需要pom先依赖taotao-manager-interface
 * 	然后在springmvc.xml引用com.taotao.content.service.ContentCategoryService,
 * 	最后注入
 */
@Controller
public class ContentCategoryController {
	
	@Autowired
	private ContentCategoryService contentCategoryService;//内容分类服务
	
	/**
	 * 初次点击url后面没有参数id的：http://localhost:8081/content/category/list
	 *   所以需要给该函数的参数parentId一个id的默认值，让第一次请求时赋值0：@RequestParam(name或value = "id",defaultValue = "0")
	 * @param parentId 从前端传回来的id值；
	 * @return
	 */
	@RequestMapping("/content/category/list")	//来自taotao-manager-web的content-category.jsp
	@ResponseBody
	public List<EasyUITreeNode> getContentCategoryList(
			@RequestParam(value = "id",defaultValue = "0")Long parentId){
		return contentCategoryService.getContentCategoryList(parentId);
		
	}
	/**
	 * /content/category/create来自content-category.jsp的onAfterEdit
	 * 参数对应content-category.jsp的onAfterEdit的Ajax请求参数：{parentId:node.parentId,name:node.text}
	 * @param parentId 
	 * @param name
	 * @return
	 */
	@RequestMapping("/content/category/create")
	@ResponseBody
	public TaotaoResult addContentCategory(Long parentId, String name) {
		return contentCategoryService.addContentCategory(parentId, name);
	}
	
	@RequestMapping("/content/category/update")
	@ResponseBody
	public TaotaoResult updateContentCategory(Long id, String name) {
		return contentCategoryService.updateContentCategory(id, name);
	}
	
	@RequestMapping("/content/category/delete/")
	@ResponseBody
	public TaotaoResult deleteContentCategory(Long id) {
		return contentCategoryService.deleteContentCategory(id);
	}
	
	@RequestMapping("/content/category/hasChild")
	@ResponseBody
	public TaotaoResult hasChildCategory(Long id) {
		boolean b = contentCategoryService.hasChildCategory(id);
		if (b) {
			return TaotaoResult.ok();
		}
		return TaotaoResult.build(999, "是叶子节点");
	}
}
