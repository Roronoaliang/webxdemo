package com.alibaba.webx.test.engine.system;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.alibaba.webx.common.util.system.CPUs;
import com.alibaba.webx.common.util.system.Disks;
import com.alibaba.webx.common.util.system.Memory;
import com.alibaba.webx.common.util.system.Nets;
import com.alibaba.webx.common.util.system.OperationSystem;
import com.alibaba.webx.common.util.system.SystemInfo;
import com.alibaba.webx.common.util.system.SystemInfoUtil;
import com.alibaba.webx.searchengine.util.systemmonitor.SystemMonitor;

/**
 * 【系统信息工具 使用用例】
 * 
 * @author xiaoMzjm
 *
 */
public class UseCase {

	private static SystemInfoUtil systemInfoUtil = null;

	private static FileSystemXmlApplicationContext fsxac;

	private static SystemMonitor systemMonitor;

	public static void main(String[] args) throws Exception {
		Enumeration<NetworkInterface> interfaces = NetworkInterface
				.getNetworkInterfaces();
		while (interfaces.hasMoreElements()) {
			NetworkInterface ni = interfaces.nextElement();
			// 排除回环网卡、虚拟网卡、没启动的网卡
			if (ni.isLoopback() || ni.isVirtual() || !ni.isUp()) {
				continue;
			}
			Enumeration<InetAddress> e = ni.getInetAddresses();
			if (e.hasMoreElements()) {
				InetAddress inetAddress = e.nextElement();
				String hostName = inetAddress.getHostName();
				String address = inetAddress.getHostAddress();
				System.out.println(ni + " , hostName=" + hostName
						+ " , address=" + address);
			}
			System.out.println();
		}

	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		systemInfoUtil = new SystemInfoUtil();
		if (systemMonitor == null) {
			String[] strs = new String[] { "src/main/webapp/WEB-INF/biz/*.xml" };
			fsxac = new FileSystemXmlApplicationContext(strs);
			systemMonitor = (SystemMonitor) fsxac.getBean("systemMonitor");
		}

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

	/**
	 * 测试报警系统是否会报警
	 */
	@Test
	public void testMonitor() {
		// 设置CPU的阀值很低，然后试一下
		// 设置内存的阀值很低，然后试一下
		// 设置磁盘阀值很低，然后试一下
		// 设置网速阀值很低，然后试一下
	}

}
