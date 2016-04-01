package com.alibaba.webx.test.engine.logutils;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.alibaba.webx.searchengine.util.log.LoggerUtils;

/**
 * 【日志工具组件 使用用例】
 * 
 * @author xiaoMzjm
 */
public class UseCase {
	
	private static LoggerUtils loggerUtils;
	private static FileSystemXmlApplicationContext fsxac;
	
	@BeforeClass
    public static void setUpBeforeClass() throws Exception {
		if(loggerUtils == null) {
			String[] strs = new String[]{"src/main/webapp/WEB-INF/biz/*.xml"};
	    	fsxac = new FileSystemXmlApplicationContext(strs);
	    	loggerUtils = (LoggerUtils) fsxac.getBean("loggerUtils");
		}
    }
	
	// 发生error这种严重的错误时，及时把错误发到指定邮箱。
	@Test
	public void test(){
		System.out.println(1);
		try {
			int num = 5 / 0;
			System.out.println(num);
		} catch (Exception e) {
			loggerUtils.emailError(e);
		}
		System.out.println(2);
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
