## 一、配置服务环境
### 1. 192.168.1.133-FtpImageServer
### 2. 192.168.1.136-mysql/solr/dubbo/redis

> 环境信息 \
> Linux: CentOS 7 64位 \
> Jdk: 1.8.0_231 \
> Tomcat: 9.0.19 \
> Solr: solr-4.10.3 \
> MySQL: mysql-5.7.24 \
> redis: 3.0.0
---

#### 1. 启动mysql
[MySQL 之 Linux 安装MySQL-5.7.24](https://blog.csdn.net/vihem/article/details/123171414)
```
chkconfig mysql on #已设置开机自启
ps -ef | grep mysql
service mysql start
```

#### 2. 启动dubbo
```
/usr/local/zookeeper/bin/zkServer.sh start
/usr/local/zookeeper/bin/zkServer.sh status
/usr/local/zookeeper/bin/zkServer.sh stop
```

#### 3. 启动solr
[Solr 之 Linux 安装 solr-4.10.3](https://blog.csdn.net/vihem/article/details/121332502)
```
/usr/local/tomcat9/bin/startup.sh   #启动tomcat(solr在这里)
tailf  /usr/local/tomcat9/logs/catalina.out #查看日志
/usr/local/tomcat9/bin/shutdown.sh  #关闭tomcat
```

#### 4. 启动redis
```
/usr/local/redis/bin/redis-server /usr/local/redis/bin/redis.conf
ps -ef | grep redis
```

#### 5. 一键启动
```
/usr/local/zookeeper/bin/zkServer.sh start
/usr/local/tomcat9/bin/startup.sh
/usr/local/redis/bin/redis-server /usr/local/redis/bin/redis.conf
```

## 二、启动代码
1. 使用maven安装服务： common、pagehelper、manager、content、search
   clean install -Dmaven.test.skip=true

2. 启动 manager、common、search、manager-web、portal-web、search-web
   tomcat7:run

> 端口 \
> manager-8080 \
> common-8083 \
> search-8084 \
> manager-web-8081 \
> portal-web-8082 \
> search-web-8085
---