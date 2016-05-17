package com.alibaba.webx.test.engine.logutils;

import org.junit.Before;
import org.junit.Test;

import com.alibaba.webx.searchengine.util.log.LoggerUtils;
import com.alibaba.webx.test.engine.base.EngineBaseTest;

/**
 * 【日志工具组件 使用用例】
 * 
 * @author xiaoMzjm
 */
public class UseCase extends EngineBaseTest<UseCase,LoggerUtils>{
	
	@Before
	public void before(){
		initTarget("loggerUtils");
	}
	
	// 发生error这种严重的错误时，及时把错误发到指定邮箱。————测试通过
	@Test
	public void testSendEmailError(){
		System.out.println(1);
		try {
			int num = 5 / 0;
			System.out.println(num);
		} catch (Exception e) {
			target.emailError(e);
		}
		System.out.println(2);
		try {
			int num = 5 / 0;
			System.out.println(num);
		} catch (Exception e) {
			target.emailError(e);
		}
		System.out.println(3);
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
