package com.alibaba.webx.service.base.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.webx.searchengine.dao.base.BaseDao;
import com.alibaba.webx.searchengine.util.log.LoggerUtils;
import com.alibaba.webx.service.base.BaseService;

/**
 * 
 * @author xiaoMzjm
 *
 * @param <T>
 */
@Service
public class BaseServiceImpl<T> implements BaseService<T>{
	
	private static Logger log = LoggerFactory.getLogger(BaseServiceImpl.class);
	
	@Autowired
	private LoggerUtils loggerUtils;
	
	private BaseDao<T> baseDao;
	
	public void setBaseDao(BaseDao<T> baseDao){
		this.baseDao = baseDao;
	}
	
	@Override
	public int insert(T t) {
		try {
			return baseDao.insert(t);
		} catch (Exception e) {
			log.error("【BaseServiceImpl.insert】",e);
			loggerUtils.emailError(e);
		}
		return -1;
	}


	@Override
	public int deleteById(String id) {
		try {
			return baseDao.deleteById(id);
		} catch (Exception e) {
			log.error("【BaseServiceImpl.deleteById】",e);
			loggerUtils.emailError(e);
		}
		return -1;
	}


	@Override
	public List<T> selectAll(int offset, int limit) {
		try {
			return baseDao.selectAll(offset, limit);
		} catch (Exception e) {
			log.error("【BaseServiceImpl.selectAll】",e);
			loggerUtils.emailError(e);
		}
		return null;
	}


	@Override
	public List<T> selectByOneParameter(String name, Object value, int offset, int limit) {
		try {
			return baseDao.selectByOneParameter(name, value, offset, limit);
		} catch (Exception e) {
			log.error("【BaseServiceImpl.selectByOneParameter】",e);
			loggerUtils.emailError(e);
		}
		return null;
	}


	@Override
	public List<T> selectByParameters(Map<String, Object> ma, int offset, int limit) {
		try {
			return baseDao.selectByParameters(ma, offset, limit);
		} catch (Exception e) {
			log.error("【BaseServiceImpl.selectByParameters】",e);
			loggerUtils.emailError(e);
		}
		return null;
	}


	@Override
	public T selectById(String id) {
		try {
			return baseDao.selectById(id);
		} catch (Exception e) {
			log.error("【BaseServiceImpl.selectById】",e);
			loggerUtils.emailError(e);
		}
		return null;
	}


	@Override
	public int updateById(T t) {
		try {
			return baseDao.updateById(t);
		} catch (Exception e) {
			log.error("【BaseServiceImpl.updateById】",e);
			loggerUtils.emailError(e);
		}
		return 0;
	}


	
}