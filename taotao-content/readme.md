content 内容管理系统

	主页的各种广告位，轮播图内容

对于dao和pojo，只需要依赖manager的dao和pojo

taotao-content-interface 接口层（依赖manager-pojo）

taotao-content-service 服务层（依赖manager-dao）打包方式war
	
	注册中心的地址，dubbo的端口号需要修改（20881），因为manager会占用一个20880
	
关于web.xml，服务层只需要初始化spring容器

理论上，jar包是不能包含jar包，所以打包成war包，直接部署到tomcat，方便部署。

内容分类管理：

在manager-dao中的 TbContentCategoryMapper 的 id="insert"和id="insertSelective" 中添加下面代码：

	<!-- SELECT LAST_INSERT_ID()：可以查到最后插入的id， 
		keyProperty="id"：来自com.taotao.pojo.TbContentCategory的id属性，把查询结果赋给该id属性，也就是tb_content_category表的主键
		resultType="long"：SELECT LAST_INSERT_ID()执行完成返回类型为（TbContentCategory中）Long id;
		order="AFTER"：在插入语句执行之后进行该查询
	-->
	<selectKey keyProperty="id" resultType="long" order="AFTER">
		SELECT LAST_INSERT_ID()
	</selectKey>

添加内容分类之后可以看F12开发者模式-Network-Header-Form Data.
--------
主页使用redis缓存

	1. 编译：redis-3.0.0.tar.gz 是一个c语言开发的源代码，需要进行编译。进入源码目录，有Makefile文件，所以在当前目录下执行make命令进行编译
	2. 安装：make install PREFIX=/usr/local/reids  -- PREFIX=安装目录
	3. 前端启动模式：进入/usr/local/reids/bin目录   ./redis-server
	4. 后端启动模式：复制配置文件：cp ~/redis-3.0.0/redis.conf /usr/local/reids/bin 并修改：daemonize yes。当前目录下启动：./redis-server redis.conf
	5. 查看是否已经开启：ps aux|grep redis
	6. 连接redis服务：/usr/local/reids/bin #./redis-cli #ping 
		# ./redis-cli -h 192.168.1.136 -p 6379
	
	redis 存放的都是字符串，常是键值对
	
redis持久化

两种方案：可同时开启
	
	RDB(快照)形式（默认）：定期更新，存放在内存中，保存为快照。dump.rdb文件（快照文件）。
	AOF形式（保存的是对数据库的命令），开启后性能有一定下降，因为他会频繁操作磁盘，数据完整性较高。

redis集群

1. 创建6个redis环境节点：

	port 7001~7006
	cluster-enabled yes开启集群，代表当前是一个集群模式
	#cd /usr/local/redis-cluster/
	添加可执行权限： chmod +x start-all.sh
	启动#./start-all.sh
	查看是否启动：ps aux|grep redis

2. 安装ruby
	
	yum install ruby
	gem install redis-3.0.0.gem 安装ruby运行依赖包
	cp /root/redis-3.0.0/src/redis-trib.rb /usr/local/redis-cluster
	
3. 搭建集群：

	# cd /usr/local/redis-cluster
	# ./redis-trib.rb create --replicas 1 192.168.1.136:7001 192.168.1.136:7002 192.168.1.136:7003 192.168.1.136:7004 192.168.1.136:7005 192.168.1.136:7006
	--replicas 1 ：1其实代表的是一个比例，就是主节点数/从节点数的比例。那么想一想，在创建集群的时候，哪些节点是主节点呢？哪些节点是从节点呢？答案是将按照命令中IP:PORT的顺序，先是3个主节点，然后是3个从节点。
	连接集群：redis01/redis-cli -p 7006 -c 
	192.168.1.136:7006> cluster info
	
	
	