package com.alibaba.webx.common.util.md5;

import org.junit.Test;

public class UseCase {

	@Test
	public void test(){
		System.out.println("将字符串转成MD5 = "+MD5Encryption.getMD5("poiuytrewq"));
	}
}
