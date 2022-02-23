package com.taotao.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.mapper.SearchItemMapper;
import com.taotao.search.service.SearchItemService;

/**
 * 商品数据导入到索引库
 * 在taotao-manager-web中调用该服务
 * @author yeasue
 *
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {

	// 若需要注入这个，需在applicationContext-dao.xml的包扫描加入该Mapper的包地址com.taotao.search.mapper
	@Autowired
	private SearchItemMapper searchItemMapper;//操作数据库的对象
	
	// 注入SolrServer需要spring容器中有这个对象，所以需要配置。配置文件：applicationContext-solr.xml
	@Autowired
	private SolrServer solrServer;
	
	/**
	 * 获取商品数据列表，导入到solr库
	 */
	@Override
	public TaotaoResult importItemsToIndex() {
		try {
			// 1. 先从数据库中查询所有商品数据
			List<SearchItem> items = searchItemMapper.getItemList();
			// 2. 遍历商品数据，添加到索引库
			for (SearchItem searchItem : items) {
				// 创建文档对象
				SolrInputDocument document = new SolrInputDocument();
				// 向文档添加域
				document.addField("id", searchItem.getId());
				document.addField("item_title", searchItem.getTitle());
				document.addField("item_sell_point", searchItem.getSell_point());
				document.addField("item_price", searchItem.getPrice());
				document.addField("item_image", searchItem.getImage());
				document.addField("item_category_name", searchItem.getCategory_name());
				document.addField("item_desc", searchItem.getItem_desc());
				// 写入索引库，需要使用solrServer
				solrServer.add(document);
			}
			// 3. 提交
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, "数据导入到solr失败");
		}
		// 4. 返回添加成功
		return TaotaoResult.ok();
	}

	

}
