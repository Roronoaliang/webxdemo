package com.alibaba.webx.test.engine.systemmonitor;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.junit.Before;
import org.junit.Test;

import com.alibaba.webx.searchengine.util.systemmonitor.SystemMonitor;
import com.alibaba.webx.test.engine.base.EngineBaseTest;

/**
 * 【系统信息工具 使用用例】
 * 
 * @author xiaoMzjm
 *
 */
public class UseCase extends EngineBaseTest<UseCase,SystemMonitor>{
	
	@Before
	public void before(){
		initTarget("systemMonitor");
	}


	/**
	 * 测试报警系统是否会报警————测试通过
	 * @throws InterruptedException 
	 */
	@Test
	public void testMonitor() throws InterruptedException {
		// 设置CPU的阀值很低，然后试一下
		// 设置内存的阀值很低，然后试一下
		// 设置磁盘阀值很低，然后试一下
		// 设置网速阀值很低，然后试一下
		Thread.sleep(50000);
	}
	
	/**
	 * 测试获取本地网卡IP————测试通过
	 * @throws SocketException
	 */
	@Test
	public void testIpAddress() throws SocketException {
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

}
