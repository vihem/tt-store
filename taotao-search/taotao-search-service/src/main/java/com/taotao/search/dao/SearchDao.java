package com.taotao.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.SearchResult;

/**
 * 查询索引库商品dao
 */
@Repository
public class SearchDao {
	private static final String String = null;
	@Autowired	//在applicationContext-solr.xml中配置了该对象
	private SolrServer solrServer;
	
	/**
	 * 查询索引库
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public SearchResult search(SolrQuery query) throws Exception{
		SearchResult result = new SearchResult();
		
		// 1. 根据query对象进行查询
		QueryResponse response = solrServer.query(query);
		
		// 2. 取查询结果
		SolrDocumentList solrDocumentList = response.getResults();
		
		// 3. 封装查询结果到SearchItem中
		List<SearchItem> itemList = new ArrayList<SearchItem>();
		for (SolrDocument solrDocument : solrDocumentList) {
			SearchItem item = new SearchItem();
			item.setId((String) solrDocument.get("id"));
			item.setCategory_name((String) solrDocument.get("item_category_name"));
			// 修改取 第一张图片
			String image = (String) solrDocument.get("item_image");
			if (StringUtils.isNotBlank(image)) {
				image = image.split(",")[0];
			}
			item.setImage(image);
			item.setPrice((long) solrDocument.get("item_price"));
			item.setSell_point((String) solrDocument.get("item_sell_point"));
			// 取高亮
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			
			String itemTitle = "";
			if (list != null && list.size() > 0) {
				itemTitle = list.get(0);
			} else {
				itemTitle = (String) solrDocument.get("item_title");
			}
			item.setTitle(itemTitle);
			itemList.add(item);
		}
		//		设置 查询结果总记录数
		result.setRecordCount(solrDocumentList.getNumFound());
		// 4. 结果添加到SearchResult中
		result.setItemList(itemList);
		return result;
	}
}
