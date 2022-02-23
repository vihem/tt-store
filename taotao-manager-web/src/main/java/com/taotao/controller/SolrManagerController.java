package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.service.SearchItemService;

/**
 * solr索引库维护Controller
 * @author yeasue
 */
@Controller
@RequestMapping("/solr")
public class SolrManagerController {
	@Autowired
	private SearchItemService searchItemService;
	
	@RequestMapping("/import")
	@ResponseBody
	public TaotaoResult importItemsToIndex() {
		return searchItemService.importItemsToIndex();
	}
}
