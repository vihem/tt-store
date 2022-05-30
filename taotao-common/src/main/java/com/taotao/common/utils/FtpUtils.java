package com.taotao.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 * ftp上传下载工具类
 */
public class FtpUtils {

	/** 
	 * Description: 向FTP服务器上传文件 
	 * @param host FTP服务器hostname 
	 * @param port FTP服务器端口 
	 * @param username FTP登录账号 
	 * @param password FTP登录密码 
	 * @param basePath FTP服务器基础目录：/home/ftpuser/www/images
	 * @param filePath FTP服务器文件存放路径。例如分日期存放：/2015/01/01。文件的路径为basePath+filePath
	 * @param filename 上传到FTP服务器上的文件名 
	 * @param inputStream 输入流 
	 * @return 成功返回true，否则返回false 
	 */  
	public static boolean uploadFile(String host, int port, String username, String password, String basePath,
			String filePath, String filename, InputStream inputStream) {
		boolean result = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			// 1. 连接FTP服务器
			ftp.connect(host, port);// 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
			// 2. 登录
			ftp.login(username, password);
			reply = ftp.getReplyCode();//返回FTP应答的整数值：检查连接是否成功
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();//关闭与FTP服务器的连接，并将连接参数恢复为默认值
				return result;
			}
			// 3. 修改FTP会话的当前工作目录（上传保存目录）
			if (!ftp.changeWorkingDirectory(basePath+filePath)) {
				//如果目录不存在创建目录
				String[] dirs = filePath.split("/");
				String tempPath = basePath;
				for (String dir : dirs) {
					if (null == dir || "".equals(dir)) continue;
					tempPath += "/" + dir;
					if (!ftp.changeWorkingDirectory(tempPath)) {
						if (!ftp.makeDirectory(tempPath)) {
							return result;
						} else {
							ftp.changeWorkingDirectory(tempPath);
						}
					}
				}
			}
			// 4. 设置传输模式为 被动
			ftp.enterLocalPassiveMode();
			// 5. 设置上传文件的类型为二进制类型
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			// 6. 上传文件
			if (!ftp.storeFile(filename, inputStream)) {
				return result;
			}
			inputStream.close();
			ftp.logout();
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return result;
	}
	
	/** 
	 * Description: 从FTP服务器下载文件 
	 * @param host FTP服务器hostname 
	 * @param port FTP服务器端口 
	 * @param username FTP登录账号 
	 * @param password FTP登录密码 
	 * @param remotePath FTP服务器上的相对路径 
	 * @param fileName 要下载的文件名 
	 * @param localPath 下载后保存到本地的路径 
	 * @return 
	 */  
	public static boolean downloadFile(String host, int port, String username, String password, String remotePath,
			String fileName, String localPath) {
		boolean result = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(host, port);
			// 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return result;
			}
			ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
			FTPFile[] fs = ftp.listFiles();
			for (FTPFile ff : fs) {
				if (ff.getName().equals(fileName)) {
					File localFile = new File(localPath + "/" + ff.getName());

					OutputStream is = new FileOutputStream(localFile);
					ftp.retrieveFile(ff.getName(), is);
					is.close();
				}
			}

			ftp.logout();
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		try {  
	        FileInputStream in=new FileInputStream(new File("D:/3.jpg"));  
	        boolean flag = uploadFile("192.168.1.133", 21, "ftpuser", "ftpuser", "/home/ftpuser/www/images","", "3.jpg", in);  
	        System.out.println(flag);
	        
	        boolean downloadFile = downloadFile("192.168.1.133", 21, "ftpuser", "ftpuser", "/home/ftpuser/www/images", "3.jpg", "D:/images");
	        System.out.println(downloadFile);
	    } catch (FileNotFoundException e) {  
	        e.printStackTrace();  
	    }  
	}
}
