package com.alibaba.webx.common.factory.log;

import org.junit.Test;
import org.slf4j.Logger;

/**
 * 【日志工厂 使用用例】
 * 
 * @author xiaoMzjm
 */
public class UseCase {
	
	private static Logger log = LoggerFactory.getLogger(UseCase.class);

	@Test
	public void test(){
		log.info("xxx");
	}
}
