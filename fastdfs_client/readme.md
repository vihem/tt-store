图片上传

图片服务器FastDFS

使用于商品添加功能
----------
maven中央仓库中没有fastdfs的jar包。本工程是有客户端源代码更改，更改为maven工程，这样只需要install一下就能生成包到response中，然后再给需要的地方注入。

把下面内容加到manager-web的pom中，添加注入
<groupId>fastdfs_client</groupId>
<artifactId>fastdfs_client</artifactId>
<version>1.25</version>