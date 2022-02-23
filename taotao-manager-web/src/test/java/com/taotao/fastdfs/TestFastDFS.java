package com.taotao.fastdfs;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

/**
 *	上传文件测试，FastDFS
 * @author yeasue
 *
 */
public class TestFastDFS {
	@Test
	public void uploadFile() throws Exception {
		/*
		 * 1. 向工程中加jar包
		 * 		添加maven工程：fastdfs_client
		 * 		run as maven build
		 * 		添加pom
		 * 2. 创建配置文件。配置tracker服务器地址
		 * 		/taotao-manager-web/src/main/resources/resource/client.conf
		 * 		tracker_server=192.168.25.133:22122
		 * 3. 加载配置文件
		 * 4. 创建一个TrackerClient对象
		 * 5. 使用TrackerClient对象获得trackerserver对象
		 * 6. 创建StorageServer的引用，null就可以了
		 * 7. 创建StorageClient对象。trackerserver、StorageServer两个参数
		 * 8. 使用StorageClient对象上传文件
		 */
		//3.加载配置文件 
		ClientGlobal.init("D:/work-eclipse-tt/taotao-manager-web/src/main/resources/resource/client.conf");
		//4、new一个TrackerClient对象。
		TrackerClient trackerClient = new TrackerClient();
		//5、获取trackerserver对象。
		TrackerServer trackerServer = trackerClient.getConnection();
		//6、创建一个StorageServer引用
		StorageServer storageServer = null;
		//7、new一个StorageClient对象
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		//8.使用StorageClient对象上传文件
		String[] strings = storageClient.upload_file("D:/1.jpg","jpg" , null);//最后面的null是备注信息
		for (String string : strings) {
			System.out.println(string);
		}
	}
}
