package com.taotao.jedis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class TestJedis {
	
	@Test
	public void testJedis() throws Exception{
		// 创建一个Jedis对象，指定redis服务器的ip和端口号
		Jedis jedis = new Jedis("192.168.1.136", 6379);
		// 直接操作数据库jedis.set("key", "value");
		jedis.set("jedis-key", "testJedis");
		String res = jedis.get("jedis-key");
		System.out.println(res);
		// 关闭  jedis是方法级对象，需要关闭
		jedis.close();
	}
	/**
	 * 单机版连接redis
	 * @throws Exception
	 */
	@Test
	public void testJedisPool() throws Exception{
		// 创建连接池对象（单例），指定redis服务器的ip和端口号
		JedisPool jedisPool = new JedisPool("192.168.1.136", 6379);
		// 从连接池种获得连接
		Jedis jedis = jedisPool.getResource();
		// 操作Jedis数据库（方法级别使用）
		jedis.set("jedis-key", "testJedisPool");
		String res = jedis.get("jedis-key");
		System.out.println(res);
		// 关闭jedis连接
		jedis.close();
		// 系统关闭前关闭连接池
		jedisPool.close();
	}
	
	/**
	 * redis集群
	 * @throws Exception
	 */
	@Test
	public void testJedisCluster() throws Exception{
		//创建一个JedisCluster对象，构造函数是一个Set类型，集合中每个元素都是HostAndPort类型
		Set<HostAndPort> hostAndPorts = new HashSet<HostAndPort>();
		//向集合中添加Redis服务器
		hostAndPorts.add(new HostAndPort("192.168.1.136", 7001));
		hostAndPorts.add(new HostAndPort("192.168.1.136", 7002));
		hostAndPorts.add(new HostAndPort("192.168.1.136", 7003));
		hostAndPorts.add(new HostAndPort("192.168.1.136", 7004));
		hostAndPorts.add(new HostAndPort("192.168.1.136", 7005));
		hostAndPorts.add(new HostAndPort("192.168.1.136", 7006));
		JedisCluster jedisCluster = new JedisCluster(hostAndPorts);
		//使用JedisCluster操作redis，自带连接池。JedisCluster对象可以是一个单例的
		jedisCluster.set("cluster-test", "hello cluster");
		String res = jedisCluster.get("cluster-test");
		System.out.println(res);
		//系统关闭前关闭JedisCluster
		jedisCluster.close();
	}
}
