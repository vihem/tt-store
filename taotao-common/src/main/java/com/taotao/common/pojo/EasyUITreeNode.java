package com.taotao.common.pojo;

import java.io.Serializable;

/**
 * 树形节点的EasyUI bean
 * 用于manager、content的内容管理
 * @author EAsue
 *
 */
public class EasyUITreeNode implements Serializable{
	private static final long serialVersionUID = 6197855129514531882L;
	
	private long id;
	private String text;
	private String state;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

}
