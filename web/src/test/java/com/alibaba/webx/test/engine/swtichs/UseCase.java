package com.alibaba.webx.test.engine.swtichs;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.alibaba.webx.searchengine.util.switchs.MySwitchUtil;

/**
 * 【降级开关 使用用例】
 * 
 * @author xiaoMzjm
 *
 */
public class UseCase {

	private static MySwitchUtil mySwitchUtil;
	private static FileSystemXmlApplicationContext fsxac;
	
	@BeforeClass
    public static void setUpBeforeClass() throws Exception {
		if(mySwitchUtil == null) {
			String[] strs = new String[]{"src/main/webapp/WEB-INF/biz/*.xml"};
	    	fsxac = new FileSystemXmlApplicationContext(strs);
	    	mySwitchUtil = (MySwitchUtil) fsxac.getBean("mySwitchUtil");
		}
    }
	
	// ————测试通过
	@Test
	public void test(){
		System.out.println(mySwitchUtil.isEMAIL_LOG_SWITCH());
	}
	
	
}
