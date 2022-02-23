package com.taotao.portal.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.utils.JsonUtils;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.ADNode;

/**
 * 商城首页展示Controller
 * @author yeasue
 */
@Controller
public class IndexController {
	
	//轮播图/大广告位
	@Value("${AD1_CATEGORY_ID}")
	private Long AD1_CATEGORY_ID;
	@Value("${AD1_WIDTH}")
	private Integer AD1_WIDTH;
	@Value("${AD1_HEIGHT}")
	private Integer AD1_HEIGHT;
	@Value("${AD1_WIDTH_B}")
	private Integer AD1_WIDTH_B;
	@Value("${AD1_HEIGHT_B}")
	private Integer AD1_HEIGHT_B;
	
	//右上角广告
	@Value("${UPPER_RIGHT_AD_CATEGORY_ID}")
	private Long UPPER_RIGHT_AD_CATEGORY_ID;
	@Value("${UPPER_RIGHT_AD_WIDTH}")
	private Integer UPPER_RIGHT_AD_WIDTH;
	@Value("${UPPER_RIGHT_AD_HEIGHT}")
	private Integer UPPER_RIGHT_AD_HEIGHT;
	@Value("${UPPER_RIGHT_AD_WIDTH_B}")
	private Integer UPPER_RIGHT_AD_WIDTH_B;
	@Value("${UPPER_RIGHT_AD_HEIGHT_B}")
	private Integer UPPER_RIGHT_AD_HEIGHT_B;

	
	@Autowired
	private ContentService contentService;
	
	
	/**
	 * 前端请求url:http://localhost:8082/
	 * web.xml中有一个欢迎页，设置的url是index.html，
	 * web.xml配置的拦截器是*.html，springmvc会找到@RequestMapping("/index")，调用该函数
	 * @return
	 */
	@RequestMapping("/index")
	public String showIndex(Model model) {
		String slideAD = getSlideAD();
		// 把Json数据传到页面
		model.addAttribute("ad1", slideAD); //index.jsp中的var data = ${ad1};
		model.addAttribute("upper_right", getUpperRightAD());//index.jsp中的var data = ${upper_right};
		
		return "index";	//返回一个逻辑视图
	}
	
	@ResponseBody
	@RequestMapping(value = "/swiper",method = RequestMethod.GET)
	public List<TbContent> swiper() {
		System.out.println("=====swiper");
		List<TbContent>  asssContents = contentService.getSwiper();
		System.out.println("=====swiper"+asssContents.toString());
		return asssContents;
	}
	
	
	/**
	 * 轮播图(AD1)
	 */
	private String getSlideAD() {
		// 根据category_id查询轮播图(AD1)列表
		List<TbContent> contents = contentService.getContentsByCid(AD1_CATEGORY_ID);
		// 把列表转为AD1Node列表
		List<ADNode> nodes = new ArrayList<ADNode>();
		for (TbContent content : contents) {
			ADNode node = new ADNode();
			node.setAlt(content.getTitle());
			node.setHeight(AD1_HEIGHT);
			node.setHeightB(AD1_HEIGHT_B);
			node.setWidth(AD1_WIDTH);
			node.setWidthB(AD1_WIDTH_B);
			node.setSrc(content.getPic());
			node.setSrcB(content.getPic2());
			node.setHref(content.getUrl());
			
			nodes.add(node);
		}
		// 把List转换为JSON数据格式字符串
		return JsonUtils.objectToJson(nodes);
	}
	
	//右上角广告位
	private String getUpperRightAD() {
		List<TbContent> contents = contentService.getContentsByCid(UPPER_RIGHT_AD_CATEGORY_ID);
		List<ADNode> nodes = new ArrayList<ADNode>();
		for (TbContent content : contents) {
			ADNode node = new ADNode();
			node.setAlt(content.getTitle());
			node.setHeight(UPPER_RIGHT_AD_HEIGHT);
			node.setHeightB(UPPER_RIGHT_AD_HEIGHT_B);
			node.setWidth(UPPER_RIGHT_AD_WIDTH);
			node.setWidthB(UPPER_RIGHT_AD_WIDTH_B);
			node.setSrc(content.getPic());
			node.setSrcB(content.getPic2());
			node.setHref(content.getUrl());
			
			nodes.add(node);
		}
		return JsonUtils.objectToJson(nodes);
	}
}
