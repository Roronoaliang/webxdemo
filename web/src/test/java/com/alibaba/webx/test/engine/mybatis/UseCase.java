package com.alibaba.webx.test.engine.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.alibaba.webx.common.po.demo.Demo;
import com.alibaba.webx.searchengine.factory.mybatis.MyBatisFactory;

/**
 * 【Mybatis组件 使用例子】
 * 
 * @author xiaoMzjm
 */
public class UseCase {
	
	private static MyBatisFactory myBatisFactory;
	
	private static FileSystemXmlApplicationContext fsxac;
	
	@BeforeClass
    public static void setUpBeforeClass() throws Exception {
		if(myBatisFactory == null) {
			String[] strs = new String[]{"src/main/webapp/WEB-INF/biz/*.xml"};
	    	fsxac = new FileSystemXmlApplicationContext(strs);
	    	myBatisFactory = (MyBatisFactory) fsxac.getBean("myBatisFactory");
		}
    }
	
	// 增————测试通过
	@Test
	public void insert(){
		SqlSession sqlSession = null ;
		try {
			Demo demo = new Demo();
			demo.setId("123");
			sqlSession = myBatisFactory.getWriteSqlSession();
			sqlSession.insert("DemoMapper.insert", demo);
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
	}
	
	// 删————测试通过
	@Test
	public void delete(){
		SqlSession sqlSession = null;
		try {
			sqlSession = myBatisFactory.getWriteSqlSession();
			sqlSession.delete("DemoMapper.delete", "123");
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
	}
	
	// 查————测试通过
	@Test
	public void find(){
		SqlSession sqlSession = null ;
		try {
			sqlSession = myBatisFactory.getReadSqlSession();
			List<Demo> demoList = sqlSession.selectList("DemoMapper.get");
			if(demoList != null) {
				for(Demo demo : demoList) {
					System.out.println(demo);
				}
			}
			else {
				System.out.println("demoList is null!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
	}
	
	// 改————测试通过
	@Test
	public void update(){
		SqlSession sqlSession = null ;
		try {
			sqlSession = myBatisFactory.getWriteSqlSession();
			Map<String,String> map = new HashMap<String,String>();
			map.put("oldId" , "123");
			map.put("newId" , "798");
			sqlSession.update("DemoMapper.update", map);
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		
		
	}
}
