package com.itheima.utils;

import java.io.IOException;
import java.io.InputStream;

import com.sun.xml.internal.fastinfoset.sax.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * JedisPoolUtils的工具类
 * @author:XueYi
 * @time:2017年7月18日 下午3:14:50
 * @version:1.0
 * @company:songbai
 */
public class JedisPoolUtils {

	private static JedisPool pool = null;

	static{
		//加载配置文件
		InputStream inputStream = JedisPoolUtils.class.getClassLoader().getResourceAsStream("redis.properties");
		//properties属性文件对应的文件输入流中，加载属性列表到Properties类对象。
		java.util.Properties properties = new java.util.Properties();
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//1:创建redis的连接池的配置对象
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		//2:最大的闲置个数
		poolConfig.setMaxIdle(Integer.parseInt(properties.get("redis.maxIdle").toString()));
		//3:最小的闲置个数
		poolConfig.setMinIdle(Integer.parseInt(properties.get("redis.minIdle").toString()));
		//4:最大连接数
		poolConfig.setMaxTotal(Integer.parseInt(properties.get("redis.maxTotal").toString()));
		pool = new JedisPool(poolConfig, properties.getProperty("redis.url"), Integer.parseInt(properties.get("redis.port").toString()));
	}
	
	
	//5:获得jedis连接的的方法
	public static Jedis getJedis(){
		//6:获取redis的一个连接
		Jedis jedis = pool.getResource();
		return jedis;
	}
	
	//6:释放jedis的连接的方法
	public static void closeJedis(){
		pool.close();
	}
}
