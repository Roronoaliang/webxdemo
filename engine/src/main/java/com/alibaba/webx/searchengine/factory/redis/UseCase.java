package com.alibaba.webx.searchengine.factory.redis;

import org.junit.Test;

/**
 * 【redis组件 使用用例】
 * 
 * @author xiaoMzjm
 */
public class UseCase {

	@Test
	public void test() throws Exception{
		DefaultRedisHandler  d = RedisFactory.getDefaultRedisHandler();
		// 增
		d.add("key", "value");
		// 删
		d.delete("key");
		// 查
		d.get("key");
		// 改
		d.update("key", "newValue");
		// 原子加1，用来计数
		d.incr("key");
		// 计时增，用来保存cookie等有寿命的对象，单位：秒。10秒后该对象自动被删除。
		d.add("key", "value", 10);
	}
}
