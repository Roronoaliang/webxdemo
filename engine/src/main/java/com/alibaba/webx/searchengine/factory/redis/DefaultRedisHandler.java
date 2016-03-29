package com.alibaba.webx.searchengine.factory.redis;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import redis.clients.jedis.Jedis;

/**
 * redis 操作处理类
 * 
 * 数据结构 与 对应的使用场景：
 * 
 * 1、key-value	：当数据的缓存使用
 * 2、List		：特点为合先进先出，可当消息队列使用
 * 3、Set		：特点为Set中的元素不能一样，可用来统计有多少个IP访问我们的应用	
 * 4、Sort Set	：特点为有序集合，可用来做游戏排名
 * 5、Hash		：特点为
 * 
 * @author xiaoMzjm
 *
 */
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
	
	
	/*-------------------------------------------------*/
	/* key-value操作                                                                                                            */
	/*-------------------------------------------------*/
	
	/**
	 * 根据key和value，新增键值对。
	 * @param key
	 * @param value
	 * @return
	 */
	public String add(String key, Object value) {
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
	public String add(String key, Object value,int seconds) {
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
	public Long delete(String key) {
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
			return add(key,value);
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
	
	/**
	 * 自减1操作
	 * @param key
	 * @return
	 */
	public Long decr(String key) {
		if(StringUtils.isNotBlank(key)) {
			return jedis.decr(key);
		}
		return null;
	}
	
	/*-------------------------------------------------*/
	/* List操作                                                                                                                          */
	/*-------------------------------------------------*/
	
	/**
	 * 往名为listName的队列尾插入一个值value
	 * @param listName
	 * @param value
	 * @return
	 */
	public Long addList(String listName , String value) {
		if(StringUtils.isNotBlank(listName) && StringUtils.isNotBlank(value)) {
			return jedis.rpush(listName, value);
		}
		else {
			throw new IllegalArgumentException("ERROR : listName or value is excepted.");
		}
	}
	
	/**
	 * 在名为listName的队列头拿出一个数据，拿出来后，该数据将会被删除
	 * @param listName
	 * @return
	 */
	public String popList(String listName) {
		if(StringUtils.isNotBlank(listName)) {
			return jedis.lpop(listName);
		}
		else {
			throw new IllegalArgumentException("ERROR : listName is excepted.");
		}
	}
	
	/**
	 * 在名为listName的队列中，拿出第first到第last个数据，第一个数据为0，最后一个数据为-1
	 * @param listName
	 * @param first
	 * @param last
	 * @return
	 */
	public List<String> getRangeList(String listName , int first , int last) {
		if(StringUtils.isNotBlank(listName)) {
			return jedis.lrange(listName , first , last);
		}
		else {
			throw new IllegalArgumentException("ERROR : listName is excepted.");
		}
	}
	
	/**
	 * 更新名为listName的队列的第location个位置的值为newValue
	 * @param listName
	 * @param location
	 * @param newValue
	 */
	public String updateList(String listName , int location , String newValue) {
		if(StringUtils.isNotBlank(listName) || StringUtils.isNotBlank(newValue)) {
			return jedis.lset(listName , location , newValue);
		}
		else {
			throw new IllegalArgumentException("ERROR : listName or newValue is excepted.");
		}
	}
	
	/**
	 * 返回名为listName的队列的长度
	 * @param listName
	 * @return
	 */
	public Long getListSize(String listName){
		if(StringUtils.isNotBlank(listName)) {
			return jedis.llen(listName);
		}
		else {
			throw new IllegalArgumentException("ERROR : listName is excepted.");
		}
	}
	
	/*--------------------------------------------------*/
	/* Set操作                                                                                                                               */
	/*--------------------------------------------------*/
	public void addSet(String setName , String value){
		
	}
}
