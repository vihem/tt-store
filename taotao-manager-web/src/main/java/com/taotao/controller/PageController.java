package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面展示
 * @author Administrator
 *
 */
@Controller
public class PageController {
	@RequestMapping("/")
	public String showIndex() {
		return "index";
	}
	
	/**
	 * 页面中“菜单”选项的请求
	 * http://127.0.0.1:8081/parameter
	 * @param parameter	页面名字
	 * @return
	 */
	@RequestMapping("/{parameter}")
	public String showPage(@PathVariable String parameter) {
		/*
		 * eg: http://127.0.0.1:8081/item-add?_=1606653100304，则返回item-add.jsp页面到index.jsp页面的布局中
		 */
		return parameter;
	}

}
