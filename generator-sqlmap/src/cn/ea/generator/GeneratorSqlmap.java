package cn.ea.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GeneratorSqlmap {
    public static void main(String[] args) {
        try {
            // 这里使用 绝对路径
            // 改了 运行配置的 Working directory 为 $MODULE_DIR$ 可以用相对路径
            GeneratorSqlmap.generator("generatorConfig.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }        
    }

    public static void generator(String filepath) throws Exception {
        List<String> warnings = new ArrayList<String>();
        File configFile = new File(filepath);
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }
}
