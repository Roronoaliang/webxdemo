package com.alibaba.webx.searchengine.factory.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Test;

/**
 * 【纯手工无mybatis连接数据库，使用用例】
 * 
 * @author xiaoMzjm
 *
 */
public class UseCase {
	
	@Test
	public void select() throws SQLException{
		Connection connection = MysqlFactory.getConnection();
		String sql = "select * from xxx";
		PreparedStatement stat = (PreparedStatement) connection.prepareStatement(sql);
		stat.executeQuery();
		connection.close();
	}
}
