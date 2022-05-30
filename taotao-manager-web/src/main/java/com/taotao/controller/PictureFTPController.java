package com.taotao.controller;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.utils.FtpUtils;
import com.taotao.common.utils.IDUtils;
import com.taotao.common.utils.JsonUtils;

@Controller
public class PictureFTPController {
	//先从springmvc.xml加载文件resource.properties
	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL; //图片服务器 基础URL
	@Value("${FTP_HOST}")
	private String FTP_HOST;
	@Value("${FTP_PORT}")
	private Integer FTP_PORT;
	@Value("${FTP_USERNAME}")
	private String FTP_USERNAME;
	@Value("${FTP_PASSWORD}")
	private String FTP_PASSWORD;
	@Value("${FTP_BASE_PATH}")
	private String FTP_BASE_PATH;
	
	/*
	 *	考虑浏览器兼容性问题，把picUpload的返回类型由Map改为String
	 * @ResponseBody：直接通过response响应浏览器，响应有多种数据类型：
	 * 	浏览器可以直接解析字符串，可以把String类型响应给浏览器
	 * 	相当于使用response.write
	 * 	响应对象，springmvc默认行为，使用Jackson把对象转为json字符串，再响应。
	 */
	
	/**
	 * picUpload 图片上传
	 * @param uploadFile MultipartFile
	 * @return
	 */
	@RequestMapping("/pic/upload")
	@ResponseBody
	public String picUpload(MultipartFile uploadFile) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			// 接收上传的文件
			// 获取文件的原来的文件名
			String originalFilename = uploadFile.getOriginalFilename();
			// 获取扩展名： .jpg
			String extName = originalFilename.substring(originalFilename.lastIndexOf("."));
			// 生成文件名
			String filename = IDUtils.genImageName() + extName;
			String imagePath = new DateTime().toString("/yyyy/MM/dd/");
			// 上传到图片服务器
			boolean res = FtpUtils.uploadFile(FTP_HOST, FTP_PORT, FTP_USERNAME, FTP_PASSWORD, 
					FTP_BASE_PATH, imagePath, filename, uploadFile.getInputStream());
			String url = IMAGE_SERVER_URL + "images" + imagePath + filename;
			System.out.println("Upload picture successful, imageUrl = " + url);
			// 响应上传图片的url(根据官方文档http://kindeditor.net/docs/upload.html)
			if (!res) {
				result.put("error",	1);//error:成功为0，失败为1
				result.put("message", "图片上传失败");
			}
			result.put("error",	0);//error:成功为0，失败为1
			result.put("url", url);
			return JsonUtils.objectToJson(result);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("error",	1);//error:成功为0，失败为1
			result.put("message", "图片上传失败");
			return JsonUtils.objectToJson(result);
		}
	}
}
