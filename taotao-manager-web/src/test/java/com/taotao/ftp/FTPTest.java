package com.taotao.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import com.taotao.common.utils.FtpUtils;

/**
 * FTP默认传输是文本
 */
public class FTPTest {
	
	@Test
	public void testFtpUtil() throws Exception {
		FileInputStream inputStream = new FileInputStream(new File("D:/111.wav"));
		FtpUtils.uploadFile("192.168.25.133", 21, "ftpuser", "ftpuser", "/home/ftpuser/www/images", "2021/04/20", "111.wav", inputStream);
	}
	
	@Test
	public void testFTPClient() throws Exception{
		//创建一个FTPClient对象--类似于创建FileZilla
		FTPClient ftpClient = new FTPClient();
		//创建ftp连接，默认端口号port=21
		ftpClient.connect("192.168.25.133", 21);
		//登录ftp服务器，使用用户名密码
		ftpClient.login("ftpuser", "ftpuser");
		
		//读取本地文件
		FileInputStream localFile = new FileInputStream(new File("D:/1.jpg"));
		//设置上传文件保存路径
		ftpClient.changeWorkingDirectory("/home/ftpuser/www/images");
		//将传输模式设置成被动
		ftpClient.enterLocalPassiveMode();
		//修改上传文件的格式为二进制类型（解决图片变质问题）
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		//上传文件：remote：服务端文档名，local：上传文档的本地文件流InputStream
		ftpClient.storeFile("1.jpg", localFile);
		//关闭连接
		ftpClient.logout();
	}
}
