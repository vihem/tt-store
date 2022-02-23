package com.taotao.common.pojo;
import java.io.Serializable;
import java.util.List;

/**
 * 查询结果集
 */
public class SearchResult implements Serializable{
	private long totalPages;	//总页数
	private long recordCount;	//总记录数 - NumFound
	private List<SearchItem> itemList;	//每页数据
	
	public long getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}
	public long getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}
	public List<SearchItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<SearchItem> itemList) {
		this.itemList = itemList;
	}
}
