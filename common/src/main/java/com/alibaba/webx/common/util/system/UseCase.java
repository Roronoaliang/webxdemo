package com.alibaba.webx.common.util.system;

import org.junit.Before;
import org.junit.Test;

/**
 * 系统信息工具 测试用例
 * 
 * @author xiaoMzjm
 *
 */
public class UseCase {
	
	private static SystemInfoUtil systemInfoUtil = null;
	
	@Before
	public void before(){
		systemInfoUtil = new SystemInfoUtil();
	}

	/**
	 * 测试各种信息的获取是否正常————测试通过
	 */
	@Test
	public void test() {
		try {
			// 获取 cpu信息
			CPUs cpus = systemInfoUtil.getCPUs();
			System.out.println(cpus);

			// 获取内存信息
			Memory memory = systemInfoUtil.getMemory();
			System.out.println(memory);

			// 获取操作系统信息
			OperationSystem operationSystem = systemInfoUtil
					.getOperationSystem();
			System.out.println(operationSystem);

			// 获取磁盘信息
			Disks disks = systemInfoUtil.getDisks();
			System.out.println(disks);

			// 获取网卡信息
			Nets nets = systemInfoUtil.getNets();
			System.out.println(nets);

			// 获取总信息，集成上面5种信息
			SystemInfo si = systemInfoUtil.getSystemInfo();
			System.out.println(si);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
