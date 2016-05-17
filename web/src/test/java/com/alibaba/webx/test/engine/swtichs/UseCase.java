package com.alibaba.webx.test.engine.swtichs;

import org.junit.Before;
import org.junit.Test;

import com.alibaba.webx.searchengine.util.switchs.MySwitchUtil;
import com.alibaba.webx.test.engine.base.EngineBaseTest;

/**
 * 【降级开关 使用用例】
 * 
 * @author xiaoMzjm
 *
 */
public class UseCase extends EngineBaseTest<UseCase,MySwitchUtil>{
	
	@Before
	public void before(){
		initTarget("mySwitchUtil");
	}

	
	// ————测试通过
	//
	// 测试用例如下：
	// 1、redis关闭，只配置spring
	// 2、redis打开，同时配置spring,让两者配置不一样，看会去读哪一个
	// 3、先打开redis，同时配置spring，然后突然关闭redis，看看功能正常不
	// 4、redis数据库打开，但没有对应的key-value对的情况下，功能正常不
	@Test
	public void test() throws InterruptedException{
		System.out.println(target.isEMAIL_LOG_SWITCH());
		Thread.sleep(30000);
	}
	
	
}
