package com.alibaba.webx.searchengine.factory.redis;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;

import com.alibaba.webx.common.po.demo.Demo;

/**
 * 【redis组件 使用用例】
 * 
 * API	:	http://tool.oschina.net/apidocs/apidoc?api=jedis-2.1.0
 * 
 * 入门	：	http://www.tuicool.com/articles/vaqABb
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
			
			// 普通的操作直接看官网API，太多了，下面演示下如何存储java对象，利用到本包下的序列化工具类

			Demo demo = new Demo();
			
			// 存储java对象
			jedis.set("key".getBytes(), SerializeUtil.serialize(demo));
			
			// 获取java对象
			Demo d = (Demo) SerializeUtil.unserialize(jedis.get("key".getBytes()));
			
			System.out.println(d);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 记得关闭喔！
			jedis.close();
		}
	}
}