myBatis的逆向工程  生成sql

逆向工程生成的代码有3类：
1. pojo。根据数据库中的字段生成pojo
2. mapper映射文件 --- xml文件
3. mapper接口

pojo放在一个包下（cn.ea.pojo）
mapper文件和接口放在同一个目录下（cn.ea.mapper）

需要使用绝对路径
需修改 cn.ea.generator.GeneratorSqlmap 的main函数下的路径 D:\work-idea-tt\generator-sqlmap\generatorConfig.xml

启动：
运行 cn.ea.generator.GeneratorSqlmap.main

生成文件 cn.ea.pojo 和 cn.ea.mapper，
分别放在/taotao-manager-pojo和/taotao-manager-dao下