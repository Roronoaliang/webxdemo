package com.alibaba.webx.common.util.md5;

import java.security.MessageDigest;

import org.slf4j.Logger;

import com.alibaba.webx.common.factory.log.LoggerFactory;

/**
 * MD5加密算法
 * 
 * @author xiaoMzjm
 *
 */
public final class MD5Encryption {

	/**
	 * 将字符串转成MD5
	 * @param str
	 * @return
	 */
	public static String getMD5(String str) {
		Logger log = LoggerFactory.getLogger(MD5Encryption.class);
		String reStr = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte ss[] = md.digest();
			reStr = bytes2String(ss);
		} catch (Exception e) {
			log.error("[MD5加密失败]异常信息 ：", e);
		}
		return reStr;
	}
	
	private static String bytes2String(byte[] aa) {
		String hash = "";
		for(int i = 0; i < aa.length; i++) {
			int temp;
			temp = aa[i] < 0 ? aa[i]+256 : aa[i];
			if(temp < 16) hash += "0";
			hash += Integer.toString(temp, 16);
		}
		hash = hash.toUpperCase();
		return hash;
	}
}
