package cn.ea.generator;

import java.io.File;
//import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
//import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

public class GeneratorSqlmapPlus {
	public void generator(String filename) throws Exception {
		// 指定逆向工程配置文件
		File configFile = new File(filename);
		List<String> warnings = new ArrayList<>();
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(configFile);
		DefaultShellCallback callback = new DefaultShellCallback(true);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);
	}

	public static void main(String[] args) throws Exception {
		try {
			GeneratorSqlmapPlus generatorSqlmap = new GeneratorSqlmapPlus();
			// 需要使用绝对路径
			// 改了 运行配置的 Working directory 为 $MODULE_DIR$ 可以用相对路径
			generatorSqlmap.generator("generatorConfig.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
