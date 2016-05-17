package com.alibaba.webx.service.authority.impl;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.webx.common.po.authority.User;
import com.alibaba.webx.searchengine.dao.authority.UserMapper;
import com.alibaba.webx.searchengine.util.log.LoggerUtils;
import com.alibaba.webx.service.authority.UserService;
import com.alibaba.webx.service.base.impl.BaseServiceImpl;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{
	
	private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private LoggerUtils loggerUtils;
	
	@Resource
	private UserMapper userMapper;
	
	@Resource
	public void setBaseDao(UserMapper userMapper){
		super.setBaseDao(userMapper);
	}
	
	private static final Integer userCache_refreshAfterWrite	= 5;
	private static final Integer userCache_expireAfterWrite		= 5;
	private static final Integer userCache_expireAfterAccess	= 5;
	private static final Integer userCache_maximumSize			= 10000;

	// Demo对象的缓存
	private static LoadingCache<String, User> userCache;

	@PostConstruct
	public void init() {
		userCache = CacheBuilder.newBuilder().recordStats(). // 统计信息，有API可以统计命中率等
				refreshAfterWrite(userCache_refreshAfterWrite, TimeUnit.MINUTES). // 给定时间内没有被读/写访问，则回收该对象
				expireAfterWrite(userCache_expireAfterWrite, TimeUnit.MINUTES). // 给定时间内没有写访问，则回收该对象
				expireAfterAccess(userCache_expireAfterAccess, TimeUnit.MINUTES). // 缓存过期时间。经过该时间后，清空全部缓存
				maximumSize(userCache_maximumSize). // 设置缓存个数N。N代表可以缓存N个对象
				build(new CacheLoader<String, User>() {
					@Override
					// 当本地缓存命没有中时，调用load方法
					public User load(String userName) throws Exception {
						// 没有命中时，去数据库查
						List<User> list = selectByOneParameter("userName", userName, 0, 1);
						if(CollectionUtils.isNotEmpty(list)) {
							User user = list.get(0);
							return user;
						}
						return null;
					}
				});
	}

	// 从缓存查询该对象
	public User selectByNameFromCache(String userName) {
		User user = null;
		try {
			user = userCache.get(userName);
		} catch (ExecutionException e) {
			log.error("[UserServiceImpl-selectByNameFromCache-异常]",e);
			loggerUtils.emailError(e);
		}
		return user;
	}
}
