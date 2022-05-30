表现层，web
打包方式：war

在service初始化spring
在manager-web可以配置前端控制器

-----------
spring父容器：manager-service/web.xml（ContextLoaderListener初始化）
在父容器中配置dao、service(aop增强)
	spring子容器：（DispatcherServlet初始化）
	子容器中配置manager-web/web.tml（Controller（action））
	在Controller可以注入一个Service
	
访问规则：子容器可以访问父容器的对象，父容器不可以访问子容器的对象

事务在父容器中配置，所以子容器中 不要 扫描到父容器的service包，
							      要 直接注入。

自己的容器有，不会在父容器中找

DispatcherServlet是表现层框架，所以才会有父子容器的存在

使用dubbo实现系统之间的通信、调用.
zookeeper注册中心

---------
在web页面中：除index.jsp是一个完整的页面外，其他的jsp文件都是div模块
在index.jsp中引用的js在其他jsp文件也能使用
---------
图片上传 FastDFS服务器
client.conf配置文件：tracker服务器地址
tracker_server=192.168.1.133:22122
---------
本项目使用FTP图片服务器，使用配置文件resource.properties
1. 在common中添加了相关依赖：Apache工具组件
2. 测试：com.taotao.ftp.FTPTest
3. 可使用FileZilla Client链接服务器
4. 前端上传配置：
common.js：

	var TT = TAOTAO = {
		// 编辑器参数--来自kingEditor官方文档http://kindeditor.net/docs/upload.html
		kingEditorParams : {
			//指定上传文件参数名称，在Controller里面需要相同的uploadFile --- <input type="file" name="uploadFile" />
			filePostName  : "uploadFile",
			//指定上传文件请求的url。
			uploadJson : '/pic/upload',
			//上传类型，分别为image、flash、media、file
			dir : "image"
		}
		...
	}
上传成功返回格式：{"error" : 0, "url" : "http://www.example.com/path/to/file.ext"}

多图片上传控件：js：kindeditor-all.js 的7866行

批量图片上传组件需要用到flash，所以采用单图片 onePicUpload -- TAOTAO.initOnePicUpload();