package com.alibaba.webx.searchengine.factory.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

/**
 * 【Mybatis组件 使用例子】
 * 
 * @author xiaoMzjm
 */
public class UseCase {

	// 增
	@Test
	public void insert(){
		SqlSession sqlSession = MyBatisFactory.getWriteSqlSession();
		sqlSession.insert("xxxMapping.insert", "xxx");
		sqlSession.commit();
		sqlSession.close();
	}
	
	// 删
	@Test
	public void delete(){
		SqlSession sqlSession = MyBatisFactory.getWriteSqlSession();
		sqlSession.delete("xxxMapping.delete", "xxx");
		sqlSession.commit();
		sqlSession.close();
	}
	
	// 查
	@Test
	public void find(){
		SqlSession sqlSession = MyBatisFactory.getWriteSqlSession();
		sqlSession.selectOne("xxxMapping.find", "xxx");
		sqlSession.close();
	}
	
	// 改
	@Test
	public void update(){
		SqlSession sqlSession = MyBatisFactory.getWriteSqlSession();
		sqlSession.update("xxxMapping.update", "xxx");
		sqlSession.commit();
		sqlSession.close();
	}
}
