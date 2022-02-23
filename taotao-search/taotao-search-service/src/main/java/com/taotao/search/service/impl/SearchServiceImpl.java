package com.taotao.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.SearchResult;
import com.taotao.search.dao.SearchDao;
import com.taotao.search.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService{

	@Autowired
	private SearchDao searchDao;
	
	@Override
	public SearchResult search(String queryStr, int page, int rows) throws Exception{
		// 1. 根据查询条件拼装查询对象
		//		创建SolrQuery对象
		SolrQuery query = new SolrQuery();
		//		设置查询条件
		query.setQuery(queryStr);
		//		设置分页条件
		if (page < 1) page = 1; //page 从 1开始
		query.setStart((page - 1) * rows);//起始记录
		if (rows < 1) rows = 10;
		query.setRows(rows);//行数
		//		设置默认搜索域
		query.set("df", "item_title");//或 item_keywords
		//		设置高亮
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<font color='red'>");
		query.setHighlightSimplePost("</font>");
		
		// 2. 调用dao执行查询
		SearchResult result = searchDao.search(query);
		
		// 3. 计算查询结果的总页数
		long recordCount = result.getRecordCount();//查询结果总记录数
		long pages = recordCount / rows; //总页数=查询结果总记录数/行数
		if (recordCount % rows > 0) {
			page++;
		}
		result.setTotalPages(pages);
		
		// 4. 返回
		return result;
	}

}
