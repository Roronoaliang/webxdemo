package com.alibaba.webx.service.demo.impl;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;

import com.alibaba.webx.common.factory.log.LoggerFactory;
import com.alibaba.webx.common.po.demo.Demo;
import com.alibaba.webx.service.demo.ServiceDemo;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class ServiceDemoImpl implements ServiceDemo{
	
	private static Logger log = LoggerFactory.getLogger(ServiceDemoImpl.class);
	
	private static Integer demoCache_refreshAfterWrite;
	private static Integer demoCache_expireAfterWrite;
	private static Integer demoCache_expireAfterAccess;
	private static Integer demoCache_maximumSize;
	
	// Demo对象的缓存
	private static LoadingCache<String,Demo> demoCache;
	
	// 由spring来启动init方法
	public void init() {
		demoCache = 
			CacheBuilder.newBuilder().
			recordStats().													// 统计信息，有API可以统计命中率等
			refreshAfterWrite(demoCache_refreshAfterWrite,TimeUnit.DAYS).	// 给定时间内没有被读/写访问，则回收该对象
			expireAfterWrite(demoCache_expireAfterWrite, TimeUnit.DAYS).	// 给定时间内没有写访问，则回收该对象
			expireAfterAccess(demoCache_expireAfterAccess, TimeUnit.DAYS).	// 缓存过期时间。经过该时间后，清空全部缓存
			maximumSize(demoCache_maximumSize).								// 设置缓存个数N。N代表可以缓存N个对象
			build(new CacheLoader<String,Demo>(){
				@Override
				// 当本地缓存命没有中时，调用load方法
				public Demo load(String demoId) throws Exception {
					// 没有命中时，去数据库查
					return getDemoByIdFromDatabase(demoId);
				}
			});
	}

	public void add(Demo demo){
		System.out.println();
		log.info("this is add method.");
	}
	
	public void delete(Demo demo){
		log.info("this is delete method.");
	}
	
	public void find(Demo demo){
		log.info("this is find method.");
	}
	
	// 从缓存查询该对象
	public Demo getDemoByIdFromCache(String id){
		Demo demo = null;
		try {
			demo = demoCache.get(id);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return demo;
	}
	
	// 从数据库查询该对象
	public Demo getDemoByIdFromDatabase(String id){
		log.info("getDemoByIdFromDatabase");
		return null;
	}
	
	public void update(Demo demo){
		log.info("this is update method.");
	}
}