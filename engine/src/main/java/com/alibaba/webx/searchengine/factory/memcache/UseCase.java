package com.alibaba.webx.searchengine.factory.memcache;

import org.junit.Test;

import com.danga.MemCached.MemCachedClient;

/**
 * 【Memcache组件 使用用例】
 * 
 * @author xiaoMzjm
 *
 */
public class UseCase {

	@Test
	public void test() {
		MemCachedClient mc = MemcacheFactory.getDefaultMemCachedClient();
		mc.set("key", "value");  
		mc.incr("key", 1, 5);
		mc.get("key");
	}
}
