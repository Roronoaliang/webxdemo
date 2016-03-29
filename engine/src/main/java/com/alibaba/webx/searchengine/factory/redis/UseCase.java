package com.alibaba.webx.searchengine.factory.redis;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;

/**
 * 【redis组件 使用用例】
 * 
 * @author xiaoMzjm
 */
public class UseCase {
	
	@Autowired
	private RedisFactory redisFactory;

	@Test
	public void test() {
		Jedis jedis = null;
		try {
			jedis = redisFactory.getJedis();
			
			// 操作jedis
			// ...
			// ...
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
	}
}