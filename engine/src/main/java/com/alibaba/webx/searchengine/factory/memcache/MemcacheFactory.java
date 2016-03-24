package com.alibaba.webx.searchengine.factory.memcache;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.webx.common.factory.log.LoggerFactory;
import com.alibaba.webx.searchengine.util.log.LoggerUtils;
import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

/**
 * memcacahed类
 * 
 * 【设计该类的目的】：
 * 
 * 封装memcached相关操作，让使用起来更方便
 * 
 * @author xiaoMzjm
 *
 */
public class MemcacheFactory {

	// 普通日志
	private static Logger log = LoggerFactory.getLogger(MemcacheFactory.class);
	
	// 邮件日志
	@Autowired
	private static LoggerUtils loggerUtils;
	
	// 池
	private static SockIOPool pool;
	
	private String[] serverList;		// IP数组
	private int initConnectionNum;	// 初始连接数
	private boolean ifAliveCheck;	// 连接前书否检查连接是否可用
	private int maxWaitTime;			// 最大等待连接时间
	private int maxConnectionNum;	// 最大连接数
	private int maxWaitConnectionNum;// 最大等待连接数
	
	// 初始化
	public void init(){
		try {
			pool = SockIOPool.getInstance("SP"); 
			pool.setServers(serverList); 
			pool.setInitConn(initConnectionNum);		
			pool.setAliveCheck(ifAliveCheck);	
			pool.setMaxBusyTime(maxWaitTime);	
			pool.setMaxConn(maxConnectionNum);
			pool.setMaxIdle(maxWaitConnectionNum);
			pool.initialize();
		} catch (Exception e) {
			log.error("ERROR:",e);
			loggerUtils.emailError(e);
		}
	}
	
	/**
	 * 获取一个Memcache实例
	 * @return
	 */
	public static MemCachedClient getDefaultMemCachedClient() {
		MemCachedClient mc = new MemCachedClient("SP");  
		return mc;
	}
	

	public String[] getServerList() {
		return serverList;
	}

	public void setServerList(String[] serverList) {
		this.serverList = serverList;
	}

	public int getInitConnectionNum() {
		return initConnectionNum;
	}

	public void setInitConnectionNum(int initConnectionNum) {
		this.initConnectionNum = initConnectionNum;
	}

	public boolean isIfAliveCheck() {
		return ifAliveCheck;
	}

	public void setIfAliveCheck(boolean ifAliveCheck) {
		this.ifAliveCheck = ifAliveCheck;
	}

	public int getMaxWaitTime() {
		return maxWaitTime;
	}

	public void setMaxWaitTime(int maxWaitTime) {
		this.maxWaitTime = maxWaitTime;
	}

	public int getMaxConnectionNum() {
		return maxConnectionNum;
	}

	public void setMaxConnectionNum(int maxConnectionNum) {
		this.maxConnectionNum = maxConnectionNum;
	}

	public int getMaxWaitConnectionNum() {
		return maxWaitConnectionNum;
	}

	public void setMaxWaitConnectionNum(int maxWaitConnectionNum) {
		this.maxWaitConnectionNum = maxWaitConnectionNum;
	}
}
