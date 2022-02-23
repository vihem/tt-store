package com.taotao.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 查询商品响应的json数据格式bean。
 */
//网络传输使用implements Serializable
public class EasyUIDataGridResult implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long total;//总记录数
	private List<?> rows;//数据集
	
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	@Override
	public String toString() {
		return "EasyUIDataGridResult [total=" + total + ", rows=" + rows + ", getTotal()=" + getTotal() + ", getRows()="
				+ getRows() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
}
