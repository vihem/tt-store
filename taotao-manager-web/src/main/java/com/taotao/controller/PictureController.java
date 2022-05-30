package com.taotao.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.utils.FastDFSClient;
import com.taotao.common.utils.JsonUtils;

/**
 *	图片上传Controller
 * @author yeasue
 */
//@Controller
public class PictureController {

	/*
	 * common.js的kingEditorParams:
	    // 编辑器参数--来自kingEditor官方文档http://kindeditor.net/docs/upload.html
		kingEditorParams : {
			//指定上传文件参数名称，在Controller里面需要相同的uploadFile。---<input type="file" name="uploadFile" />
			filePostName  : "uploadFile",
			//指定上传文件请求的url。
			uploadJson : '/pic/upload',
			//上传类型，分别为image、flash、media、file
			dir : "image"
		},
	 *	响应一个json，使用@ResponseBody
	 */
	
	//从resource.properties文件中获取IMAGE_SERVER_URL
	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;
	
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public String picUpload(MultipartFile uploadFile) {
		try {
			// 接收上传的文件
			
			//获取文件的原来的文件名
			String originalFilename = uploadFile.getOriginalFilename();
			// 获取扩展名
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
			// 上传到图片服务器
			FastDFSClient fastDFSClient = new FastDFSClient("classpath:resource/client.conf");
			String url = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
			//先从springmvc.xml加载文件resource.properties
			url = IMAGE_SERVER_URL + url;
			// 响应上传图片的url(根据官方文档http://kindeditor.net/docs/upload.html)
			Map<Object, Object> result = new HashMap<>();
			result.put("error",	0);//error:成功为0，失败为1
			result.put("url", url);
			return JsonUtils.objectToJson(result);
		} catch (Exception e) {
			e.printStackTrace();
			Map<Object, Object> result = new HashMap<>();
			result.put("error",	1);//error:成功为0，失败为1
			result.put("message", "图片上传失败");
			return JsonUtils.objectToJson(result);
		}
		
	}
}
