package com.taotao.search.service;

import com.taotao.common.pojo.SearchResult;

public interface SearchService {
	
	/**
	 * 查询索引库
	 * @param queryStr 查询条件
	 * @param page 页数 - 从第 1页开始
	 * @param rows 行数 - 每页显示数
	 * @return SearchResult
	 * @throws Exception
	 */
	SearchResult search(String queryStr, int page, int rows) throws Exception;
}
