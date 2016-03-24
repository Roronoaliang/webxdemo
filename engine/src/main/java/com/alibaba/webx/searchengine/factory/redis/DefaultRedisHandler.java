package com.alibaba.webx.searchengine.factory.redis;

import org.apache.commons.lang3.StringUtils;

import redis.clients.jedis.Jedis;

public class DefaultRedisHandler {
	
	private Jedis jedis = null;
	
	/**
	 * 对于jedis来说，它不是一层不变的，它可能根据业务的不同而调用不同的数据库，所以把jedis抽出来，交给上一层决定。
	 * @param jedis
	 */
	public DefaultRedisHandler(Jedis jedis) {
		this.jedis = jedis ;
	}
	
	/**
	 * 归还连接，
	 * 调用该方法后，需重新new DefaultRedisHandler功能才能再次正常使用。
	 */
	public void returnResource() {
		RedisFactory.returnResource(jedis);
	}

	/**
	 * 根据key和value，新增键值对。
	 * @param key
	 * @param value
	 * @return
	 */
	public String set(String key, Object value) {
		if(StringUtils.isNotBlank(key) && StringUtils.isNotBlank(key)){
			return jedis.set(key, String.valueOf(value));
		}
		else {
			throw new IllegalArgumentException("ERROR : key or value is excepted.");
		}
	}
	
	/**
	 * 根据key和value，新增键值对。设定超时时间，单位秒。
	 * @param key
	 * @param value
	 * @param seconds 
	 * @return
	 */
	public String set(String key, Object value,int seconds) {
		if(StringUtils.isNotBlank(key) && StringUtils.isNotBlank(key)){
			return jedis.setex(key, seconds ,String.valueOf(value));
		}
		else {
			throw new IllegalArgumentException("ERROR : key or value is excepted.");
		}
	}

	/**
	 * 根据key，删除某个键。
	 * @param key
	 * @return
	 */
	public Long del(String key) {
		if(StringUtils.isNotBlank(key)) {
			return jedis.del(key);
		}
		else {
			throw new IllegalArgumentException("ERROR : key is excepted.");
		}
	}

	/**
	 * 根据key，获取value
	 * @param key
	 * @return
	 */
	public String get(String key) {
		if(StringUtils.isNotBlank(key)) {
			return jedis.get(key);
		}
		else {
			throw new IllegalArgumentException("ERROR : key is excepted.");
		}
	}

	/**
	 * 把key的值替换为新的value
	 * @param key
	 * @param value
	 * @return
	 */
	public String update(String key, Object value) {
		if(StringUtils.isNotBlank(key) && value != null) {
			return set(key,value);
		}
		return null;
	}

	/**
	 * 自增1操作
	 * @param key
	 * @return
	 */
	public Long incr(String key) {
		if(StringUtils.isNotBlank(key)) {
			return jedis.incr(key);
		}
		return null;
	}
}
