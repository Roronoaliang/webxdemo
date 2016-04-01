package com.alibaba.webx.searchengine.factory.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.webx.common.factory.log.LoggerFactory;
import com.alibaba.webx.searchengine.util.log.LoggerUtils;

/**
 * 【mybatis 工厂】
 * 
 * @author xiaoMzjm
 *
 */
public class MyBatisFactory {
	
	@Autowired
	private SqlSessionFactoryBean sqlSessionFactoryBean;
	
	@Autowired
	private LoggerUtils loggerUtils;

	public static SqlSessionFactory sqlsessionfactory;
	
	private static Logger log = LoggerFactory.getLogger(MyBatisFactory.class);
	
	public void init(){
		sqlsessionfactory = getSqlSessionFactory();
	}

	/**
	 * 获取工厂SqlSessionFactory
	 */
	private SqlSessionFactory getSqlSessionFactory() {
		try {
			return sqlSessionFactoryBean.getObject();
		} catch (Exception e) {
			loggerUtils.emailError(e);
			log.error("ERROR:", e);
		}
		return null;
	}
	
	/**
	 * 获取写数据源的SqlSession
	 */
	public SqlSession getWriteSqlSession() {
		DBContextHolder.setDbType(DBContextHolder.DB_TYPE_RW);
		return sqlsessionfactory.openSession();
	}
	
	/**
	 * 获取读数据源的SqlSession
	 */
	public SqlSession getReadSqlSession(){
		DBContextHolder.setDbType(DBContextHolder.DB_TYPE_R);
		return sqlsessionfactory.openSession();
	}
}