package com.taotao.solrj;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class TestSolrJ {
	private String url = "http://192.168.1.136:8080/solr/collection1";
	
	@Test
	public void addDocument() throws Exception{
		// 1. 创建一个solrServer对象。创建HttpSolrServer对象，需要指定solr服务的url http://192.168.1.136:8080/solr/collection1	
		SolrServer solrServer = new HttpSolrServer(url);
		// 2. 创建一个文档对象 SolrInputDocumnet
		SolrInputDocument solrInputDocument = new SolrInputDocument();
		// 3. 向文档中添加域，域的名称必须在schema.xml中定义（已定义），且必须有id域
		solrInputDocument.addField("id", "test001");
		solrInputDocument.addField("item_title", "测试标题");
		solrInputDocument.addField("item_price", 1003);
		// 4. 把文档对象写入索引库
		solrServer.add(solrInputDocument);
		// 5. 提交
		solrServer.commit();
	}

	@Test
	public void deleteDocumentById() throws Exception{
		SolrServer server = new HttpSolrServer(url);
		server.deleteById("test001");
		server.commit();
	}
	
	@Test
	public void deleteDocumentByQuery() throws Exception{
		SolrServer server = new HttpSolrServer(url);
		server.deleteByQuery("id:test001");
		server.commit();
	}
	
	@Test
	public void searchDocument() throws Exception{
		// 1. 创建一个SolrServer对象
		SolrServer server = new HttpSolrServer(url);
		// 2. 创建SolrQuery对象
		SolrQuery solrQuery = new SolrQuery();
		// 3. 设置查询条件、过滤条件、分页条件、排序条件、高亮条件
//		solrQuery.set("q", "*:*");//q=查询条件
//		solrQuery.setQuery("*:*");//查询所有
		solrQuery.setQuery("手机");
		solrQuery.setStart(0);//从第一条开始
		solrQuery.setRows(2);//每页20条
		solrQuery.setHighlight(true);//设置高亮
		solrQuery.addHighlightField("item_title");// 设置高亮显示的域
		solrQuery.setHighlightSimplePre("<em>");// 设置高亮显示的域。<em>用<div>也行
		solrQuery.setHighlightSimplePost("</em>");// 设置高亮显示的域
		solrQuery.set("df","item_keywords");// 设置默认搜索域
		// 4. 执行查询，得到一个Response对象
		QueryResponse response = server.query(solrQuery);
		// 5. 获取查询结果
		SolrDocumentList solrDocumentList = response.getResults();
		//		取查询结果总记录数
		System.out.println("查询结果总记录数：" + solrDocumentList.getNumFound());
		
		// 6. 分析所有的查询结果，对单条记录进行处理
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			// 取itemTitle里面的 高亮显示
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String itemTitle = (String) solrDocument.get("item_title");
			if (list != null && list.size()>0) {
				itemTitle = list.get(0);
			}
			System.out.println(itemTitle);
			System.out.println(solrDocument.get("item_sell_point"));
			System.out.println(solrDocument.get("item_price"));
			System.out.println(solrDocument.get("item_image"));
			System.out.println(solrDocument.get("item_category_name"));
			System.out.println(solrDocument.get("item_desc"));
			System.out.println("===============================================");
		}
	}
}
