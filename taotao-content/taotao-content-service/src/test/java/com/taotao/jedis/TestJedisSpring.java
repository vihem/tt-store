package com.taotao.jedis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestJedisSpring {

	public void testJedisClient() throws Exception {
		//1. 初始化spring容器
		ApplicationContext applicationContext = 
				new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
		//2. 从容器中获取JedisClient对象,由于只加载一个applicationContext-redis.xml，没有打开注解，所以需要打开注解
		JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
		//3. 使用对象操作redis
		jedisClient.set("clusterSpring", "testSpring");
		String res = jedisClient.get("testSpring");
		System.out.println(res);
	}
	public static void main(String[] args) throws Exception {
		TestJedisSpring testJedisSpring = new TestJedisSpring();
		testJedisSpring.testJedisClient();
	}
}
