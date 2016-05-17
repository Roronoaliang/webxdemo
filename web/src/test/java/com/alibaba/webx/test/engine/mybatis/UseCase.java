package com.alibaba.webx.test.engine.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.alibaba.webx.common.po.authority.Permission;
import com.alibaba.webx.common.po.authority.Roles;
import com.alibaba.webx.common.po.authority.User;
import com.alibaba.webx.common.po.demo.Demo;
import com.alibaba.webx.searchengine.factory.mybatis.MyBatisFactory;
import com.alibaba.webx.searchengine.factory.mybatis.MySqlSessionTemplate;
import com.alibaba.webx.service.authority.PermissionService;
import com.alibaba.webx.service.authority.RolesService;
import com.alibaba.webx.service.authority.UserService;

/**
 * 【Mybatis组件 使用例子】
 * 
 * @author xiaoMzjm
 */
public class UseCase {
	
	private static MyBatisFactory myBatisFactory;
	
	private static MySqlSessionTemplate sqlSessionWriteTemplate;
	
	private static MySqlSessionTemplate sqlSessionReadTemplate;
	
	private static FileSystemXmlApplicationContext fsxac;
	
	private static RolesService rolesServiceImpl;
	private static UserService userServiceImpl;
	private static PermissionService permissionServiceImpl;
	
	@BeforeClass
    public static void setUpBeforeClass() throws Exception {
		if(myBatisFactory == null) {
			String[] strs = new String[]{"src/main/webapp/WEB-INF/biz/*.xml"};
	    	fsxac = new FileSystemXmlApplicationContext(strs);
	    	myBatisFactory = (MyBatisFactory) fsxac.getBean("myBatisFactory");
	    	if(sqlSessionWriteTemplate == null) {
	    		sqlSessionWriteTemplate = (MySqlSessionTemplate) fsxac.getBean("sqlSessionWriteTemplate");
	    	}
	    	if(sqlSessionReadTemplate == null) {
	    		sqlSessionReadTemplate = (MySqlSessionTemplate) fsxac.getBean("sqlSessionReadTemplate");
	    	}
	    	if(rolesServiceImpl ==null) {
	    		rolesServiceImpl = (RolesService) fsxac.getBean("rolesService");
	    	}
	    	if(userServiceImpl == null) {
	    		userServiceImpl = (UserService) fsxac.getBean("userService");
	    	}
	    	if(permissionServiceImpl == null) {
	    		permissionServiceImpl = (PermissionService) fsxac.getBean("permissionService");
	    	}
		}
    }
	
	// 增————测试通过
	@Test
	public void insert(){
		SqlSession sqlSession = null ;
		try {
			Demo demo = new Demo();
			demo.setId("1340");
			// 注意，增删改用的是getWriteSqlSession();
			// 查用的是getReadSqlSession();
			sqlSession = myBatisFactory.getWriteSqlSession();
			int result = sqlSession.insert("DemoMapper.insert", demo);
			System.out.println("插入结果："+result);
			// 记得手动commit
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 记得把session关了
			sqlSession.close();
		}
	}
	
	// 增————测试通过
	@Test
	public void insertWithTemplate(){
		Demo demo = new Demo();
		demo.setId("1349");
		sqlSessionWriteTemplate.insert("DemoMapper.insert", demo);
	}
	
	// 删————测试通过
	@Test
	public void delete(){
		SqlSession sqlSession = null;
		try {
			sqlSession = myBatisFactory.getWriteSqlSession();
			int result = sqlSession.delete("DemoMapper.delete", "123");
			System.out.println("删除结果："+result);
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
	}
	
	// 删————测试通过
	@Test
	public void deleteWithTemplate(){
		sqlSessionWriteTemplate.delete("DemoMapper.delete", "1344");
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
	
	// 查————测试通过
	@Test
	public void findWithTemplate(){
		List<Demo> demoList = sqlSessionReadTemplate.selectList("DemoMapper.get");
		if(demoList != null) {
			for(Demo demo : demoList) {
				System.out.println(demo);
			}
		}
		else {
			System.out.println("demoList is null!");
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
			int result = sqlSession.update("DemoMapper.update", map);
			System.out.println("修改结果："+result);
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
	}
	
	// 改————测试通过
	@Test
	public void updateWithTemplate(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("oldId" , "1344");
		map.put("newId" , "1352");
		sqlSessionWriteTemplate.update("DemoMapper.update", map);
	}
	
	// 测试rolesServiceImpl
	@Test
	public void testRolesServiceImpl(){
		List<Roles> rolesList = rolesServiceImpl.selectByUserName("zhang");
		if(rolesList != null) {
			System.out.println();
			for(Roles r : rolesList) {
				System.out.println(r);
			}
		}
		else {
			System.out.println("空");
		}
	}
	
	// 测试UserServiceImpl
	@Test
	public void testUserServiceImpl(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userName", "zhang");
		map.put("password", "123456");
		List<User> userList = userServiceImpl.selectByParameters(map,0,1);
		if(userList != null) {
			System.out.println();
			for(User u : userList) {
				System.out.println(u);
			}
		}
		else {
			System.out.println("空");
		}
	}
	
	@Test
	public void testPermissionServiceImpl(){
		List<Permission> permissionList = permissionServiceImpl.selectByRolesId("d8eeea88fe4c4edcb45aeb41eecc8454");
		if(permissionList != null) {
			System.out.println();
			for(Permission p : permissionList) {
				System.out.println(p);
			}
		}
		else {
			System.out.println("空");
		}
	}
}
