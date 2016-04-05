package com.alibaba.webx.test.engine.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.alibaba.webx.searchengine.factory.jdbc.MysqlFactory;

/**
 * 【纯手工无mybatis连接数据库，使用用例】
 * 
 * @author xiaoMzjm
 *
 */
public class UseCase {
	
	private static MysqlFactory mysqlFactory;
	
	private static FileSystemXmlApplicationContext fsxac;
	
	@BeforeClass
    public static void setUpBeforeClass() throws Exception {
		if(mysqlFactory == null) {
			String[] strs = new String[]{"src/main/webapp/WEB-INF/biz/*.xml"};
	    	fsxac = new FileSystemXmlApplicationContext(strs);
	    	mysqlFactory = (MysqlFactory) fsxac.getBean("mysqlFactory");
		}
    }
	
	// 测试连接性————测试通过
	@Test
	public void select(){
		Connection connection = null;
		try {
			connection = mysqlFactory.getConnection();
			System.out.println("connectionId="+connection.hashCode());
			String sql = "select user from mysql.user";
			PreparedStatement stat = (PreparedStatement) connection.prepareStatement(sql);
			ResultSet resultSet = stat.executeQuery();
			while(resultSet.next()){
				String user = resultSet.getString("user");
				System.out.println(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 记得关闭连接
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
