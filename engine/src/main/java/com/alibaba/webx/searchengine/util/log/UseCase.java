package com.alibaba.webx.searchengine.util.log;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 【日志工具组件 使用用例】
 * 
 * @author xiaoMzjm
 */
public class UseCase {

	@Autowired
	private LoggerUtils loggerUtils;
	
	// 发生error这种严重的错误时，及时把错误发到指定邮箱。
	@Test
	public void test(){
		try {
			int num = 5 / 0;
		} catch (Exception e) {
			loggerUtils.emailError(e);
		}
	}
}