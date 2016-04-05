package com.alibaba.webx.common.util.uuid;

import java.util.UUID;

/**
 * UUID 工具
 * 
 * @author xiaoMzjm
 *
 */
public class UUIDUtil {

	public static String getUUID() {
		return String.valueOf(UUID.randomUUID()).replaceAll("-", "");
	}
}
