package com.alibaba.webx.searchengine.factory.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.webx.searchengine.util.log.LoggerUtils;

/**
 * 【mybatis 工厂】
 * 
 * @author xiaoMzjm
 *
 */
public class MyBatisFactory {
	
	
	@Autowired
	private LoggerUtils loggerUtils;

	private SqlSessionFactory sqlsessionfactory;
	
	public MyBatisFactory(SqlSessionFactory sqlsessionfactory){
		this.sqlsessionfactory = sqlsessionfactory;
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