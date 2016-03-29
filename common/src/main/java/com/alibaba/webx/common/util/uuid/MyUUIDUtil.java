package com.alibaba.webx.common.util.uuid;

import java.util.UUID;

public class MyUUIDUtil {

	public static String getUUID() {
		return String.valueOf(UUID.randomUUID()).replaceAll("-", "");
	}
}
