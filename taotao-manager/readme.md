taotao-manager：
这是服务层工程、聚合工程、打包方式pom；
有四个继承 模块Maven Model：
	打包方式为jar：
		dao（data access object，使用了逆向工程）
		pojo（Plain Old Java Object，即JavaBeans）
		interface（用于）
	打包方式为war：
		service（把服务层聚合为一块）
		service依赖依赖dao
		dao依赖pojo
	运行时直接运行聚合工程manager
	
mybatis和spring的整合放在manager

mabatis配置文件：
1. 放在dao也行，但是dao会打包成jar包，
这样配置文件也会打包成jar包，其他的配置文件访问该jar包里面的配置文件会比较麻烦
一般放在最终运行的工程里面service（war包）。
2. 结果在：
/taotao-manager/taotao-manager-service/src/main/resources/mybatis/SqlMapConfig.xml

------------
manager-pojo下com.taotao.pojo下的bean
为所有的bean添加Serializable序列化接口
------------
Error：
如果启动没有成功，在manager-service的resources包中添加log4j.properties文件