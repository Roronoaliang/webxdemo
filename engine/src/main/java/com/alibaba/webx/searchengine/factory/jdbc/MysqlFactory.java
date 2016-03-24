package com.alibaba.webx.searchengine.factory.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.webx.common.factory.log.LoggerFactory;
import com.alibaba.webx.searchengine.util.log.LoggerUtils;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * MYSQL连接工厂
 * 
 * 采用c3p0连接池
 * 
 * @author xiaoMzjm
 */
public class MysqlFactory {

	// 日志
	private static Logger log = LoggerFactory.getLogger(MysqlFactory.class);

	// 连接池
	private static ComboPooledDataSource cpds = null;
	
	@Autowired
	private static LoggerUtils loggetUtils;
	
	private String diverName;			// 驱动器名称
	private String databaseUrl;			// 数据库连接URL
	private String databaseUser;		// 数据库用户名
	private String databasePssword;		// 数据库密码
	private int minConnectionNuml;		// 最小连接数
	private int maxConnectionNuml;		// 最大连接数
	private int acquireIncreamentNum;	// 获取增量
	private int testConnectionPeriod;	// 每隔几秒测试连接是否可用
	private int initConnectionNuml;		// 初始连接数
	private boolean validate;		

	/**
	 * 初始化
	 */
	public void init() {
		try {
			cpds = new ComboPooledDataSource();
			cpds.setDriverClass(diverName); // 驱动器
			cpds.setJdbcUrl(databaseUrl); // 数据库url
			cpds.setUser(databaseUser); // 用户名
			cpds.setPassword(databasePssword); // 密码
			cpds.setInitialPoolSize(initConnectionNuml); // 初始化连接池大小
			cpds.setMinPoolSize(minConnectionNuml); // 最少连接数
			cpds.setMaxPoolSize(maxConnectionNuml); // 最大连接数
			cpds.setAcquireIncrement(acquireIncreamentNum); // 连接数的增量
			cpds.setIdleConnectionTestPeriod(testConnectionPeriod); // 测连接有效的时间间隔
			cpds.setTestConnectionOnCheckout(validate); // 每次连接验证连接是否可用
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 取得链接
	 * 
	 * @return
	 */
	public static Connection getConnection() {
		Connection connection = null;
		try {
			connection = cpds.getConnection();
		} catch (SQLException ex) {
			log.error("ERROR:", ex);
			loggetUtils.emailError(ex);
		}
		return connection;
	}

	/**
	 * 释放连接
	 */
	public static void release() {
		try {
			if (cpds != null) {
				cpds.close();
			}
		} catch (Exception ex) {
			log.error("ERROR:", ex);
			loggetUtils.emailError(ex);
		}
	}
	
	public String getDiverName() {
		return diverName;
	}

	public void setDiverName(String diverName) {
		this.diverName = diverName;
	}

	public String getDatabaseUrl() {
		return databaseUrl;
	}

	public void setDatabaseUrl(String databaseUrl) {
		this.databaseUrl = databaseUrl;
	}

	public String getDatabaseUser() {
		return databaseUser;
	}

	public void setDatabaseUser(String databaseUser) {
		this.databaseUser = databaseUser;
	}

	public String getDatabasePssword() {
		return databasePssword;
	}

	public void setDatabasePssword(String databasePssword) {
		this.databasePssword = databasePssword;
	}

	public int getMinConnectionNuml() {
		return minConnectionNuml;
	}

	public void setMinConnectionNuml(int minConnectionNuml) {
		this.minConnectionNuml = minConnectionNuml;
	}

	public int getMaxConnectionNuml() {
		return maxConnectionNuml;
	}

	public void setMaxConnectionNuml(int maxConnectionNuml) {
		this.maxConnectionNuml = maxConnectionNuml;
	}

	public int getAcquireIncreamentNum() {
		return acquireIncreamentNum;
	}

	public void setAcquireIncreamentNum(int acquireIncreamentNum) {
		this.acquireIncreamentNum = acquireIncreamentNum;
	}

	public int getTestConnectionPeriod() {
		return testConnectionPeriod;
	}

	public void setTestConnectionPeriod(int testConnectionPeriod) {
		this.testConnectionPeriod = testConnectionPeriod;
	}

	public int getInitConnectionNuml() {
		return initConnectionNuml;
	}

	public void setInitConnectionNuml(int initConnectionNuml) {
		this.initConnectionNuml = initConnectionNuml;
	}

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}
}