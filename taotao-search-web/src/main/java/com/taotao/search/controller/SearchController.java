package com.taotao.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.pojo.SearchResult;
import com.taotao.search.service.SearchService;

import java.nio.charset.StandardCharsets;

/**
 * 搜索服务的Controller
 */
@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;
	
	@Value("${SEARCH_RESULT_ROWS}")
	private Integer SEARCH_RESULT_ROWS;//每页显示记录数
	
	@RequestMapping("/search")
	public String search(@RequestParam("queryCondition") String queryCondition, 
			@RequestParam(defaultValue = "1")Integer page, Model model) {

		try {
			// 解决乱码问题 手机= ææº
			queryCondition = new String(queryCondition.getBytes("iso8859-1"), StandardCharsets.UTF_8);
			
			// 调用service
			SearchResult searchResult = searchService.search(queryCondition, page, SEARCH_RESULT_ROWS);
			//把结果传递给页面
			model.addAttribute("query", queryCondition);
			model.addAttribute("totalPages", searchResult.getTotalPages());
			model.addAttribute("itemList", searchResult.getItemList());
			model.addAttribute("page", page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 返回逻辑视图
		return "search";
	}
}
