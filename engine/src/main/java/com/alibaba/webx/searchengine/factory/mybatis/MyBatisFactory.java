package com.alibaba.webx.searchengine.factory.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.webx.common.factory.log.LoggerFactory;
import com.alibaba.webx.searchengine.util.log.LoggerUtils;

/**
 * 
 * @author xiaoMzjm
 *
 */
public class MyBatisFactory {
	
//	@Autowired
//	private SqlSessionFactoryBean sqlSessionFactoryBean;
	
	@Autowired
	private static LoggerUtils loggetUtils;

	@Autowired
	public static SqlSessionFactory sqlsessionfactory;
	
	private static Logger log = LoggerFactory.getLogger(MyBatisFactory.class);
	
	public void init(){
//		sqlsessionfactory = getSqlSessionFactory();
	}

	/**
	 * 获取工厂SqlSessionFactory
	 */
//	private SqlSessionFactory getSqlSessionFactory() {
//		try {
//			return sqlSessionFactoryBean.getObject();
//		} catch (Exception e) {
//			log.error("ERROR:", e);
//			loggetUtils.emailError(e);
//		}
//		return null;
//	}
	
	/**
	 * 获取写数据源的SqlSession
	 */
	public static SqlSession getWriteSqlSession() {
		DBContextHolder.setDbType(DBContextHolder.DB_TYPE_RW);
		return sqlsessionfactory.openSession();
	}
	
	/**
	 * 获取读数据源的SqlSession
	 */
	public static SqlSession getReadSqlSession(){
		DBContextHolder.setDbType(DBContextHolder.DB_TYPE_R);
		return sqlsessionfactory.openSession();
	}
}