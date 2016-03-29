package com.alibaba.webx.searchengine.factory.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.alibaba.webx.searchengine.util.log.LoggerUtils;

public class RedisFactory {

	// 本地的jedisPool
	private static JedisPool localJedisPool = null;
	
	// 日志
	private static Logger log = LoggerFactory.getLogger(RedisFactory.class);
	
	// 以下参数值由spring注入
	private int poolMaxIdel;			// 控制一个pool最多有多少个状态为空闲的的jedis实例，默认值也是8。
	private int poolMaxWaitMillis;		// 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
	private boolean poolTestOnBorrow;	// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	private String poolIp;				// redis服务器IP地址
	private int poolPort;				// redis服务的端口
	private int poolConnectTimeOut;		// 连接超时时间，单位毫秒
	private String poolPassword;		// redis服务的密码
	
	@Autowired
	private static LoggerUtils loggetUtils;
	
	// 初始化Jedis池
	public void init(){
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxIdle(poolMaxIdel); 
			config.setMaxWaitMillis(poolMaxWaitMillis); 
			config.setTestOnBorrow(poolTestOnBorrow); 
			localJedisPool = new JedisPool(config, poolIp, poolPort, poolConnectTimeOut,
					poolPassword);
		} catch (Exception e) {
			log.error("ERROR:",e);
			loggetUtils.emailError(e);
		}
	}

	/**
	 * 获取本地Jedis实例
	 * @return
	 */
	public synchronized static Jedis getLocalJedis() throws Exception{
		if (localJedisPool != null) {
			Jedis resource = localJedisPool.getResource();
			return resource;
		} else {
			return null;
		}
	}
	
	/**
	 * 获取默认的redis操作者
	 * @return
	 * @throws Exception
	 */
	public static RedisUtil getDefaultRedisHandler() throws Exception {
		Jedis jedis = getLocalJedis();
		if(jedis != null) {
			return new RedisUtil(jedis);
		}
		return null;
	}

	/**
	 * 释放jedis资源
	 * @param jedis
	 */
	@SuppressWarnings("deprecation")
	public static void returnResource(final Jedis jedis) {
		if (jedis != null) {
			localJedisPool.returnResource(jedis);
		}
	}
	
	public int getPoolMaxIdel() {
		return poolMaxIdel;
	}

	public void setPoolMaxIdel(int poolMaxIdel) {
		this.poolMaxIdel = poolMaxIdel;
	}

	public int getPoolMaxWaitMillis() {
		return poolMaxWaitMillis;
	}

	public void setPoolMaxWaitMillis(int poolMaxWaitMillis) {
		this.poolMaxWaitMillis = poolMaxWaitMillis;
	}

	public boolean isPoolTestOnBorrow() {
		return poolTestOnBorrow;
	}

	public void setPoolTestOnBorrow(boolean poolTestOnBorrow) {
		this.poolTestOnBorrow = poolTestOnBorrow;
	}

	public String getPoolIp() {
		return poolIp;
	}

	public void setPoolIp(String poolIp) {
		this.poolIp = poolIp;
	}

	public int getPoolPort() {
		return poolPort;
	}

	public void setPoolPort(int poolPort) {
		this.poolPort = poolPort;
	}

	public int getPoolConnectTimeOut() {
		return poolConnectTimeOut;
	}

	public void setPoolConnectTimeOut(int poolConnectTimeOut) {
		this.poolConnectTimeOut = poolConnectTimeOut;
	}

	public String getPoolPassword() {
		return poolPassword;
	}

	public void setPoolPassword(String poolPassword) {
		this.poolPassword = poolPassword;
	}
}