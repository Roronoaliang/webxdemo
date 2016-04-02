package com.alibaba.webx.common.util.system;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 系统监控
 * 
 * @author xiaoMzjm
 *
 */
public class SystemMonitor {
	
	private Logger log = LoggerFactory.getLogger(SystemMonitor.class);
	private static SystemInfoUtil systemInfoUtil = new SystemInfoUtil();

	public void init(){
		
		// CPU使用率
		CPUs cpus = null;
		double cpuUseRate = 0.0;	
		
		// 剩余内存量
		long memoryFree = 0;		
		Memory memory = null;
		
		// 剩余硬存
		Disks disks = null;
		List<Disk> diskList = null;
		
		// 每秒接收 & 发送速度，单位字节
		Nets nets = null;
		List<Net> netList = null;
		long netRecivePerBytes = 0;
		long netSendPerBytes = 0;
		
		try {
			while(true) {
				// CPU使用率
				cpus = systemInfoUtil.getOneCpuPerc();
				cpuUseRate = cpus.getCpuTolalUseRate();
				
				// 内存剩余量
				memory = systemInfoUtil.getMemory();
				memoryFree = memory.getMemoryFree();
				
				// 硬存
				disks = systemInfoUtil.getDisks();
				diskList = disks.getDiskList();
				
				// 网络
				nets = systemInfoUtil.getNets();
				netList = nets.getNetList();
				for(Net net : netList) {
					if(net.getNetIpAddress().equals("192.168.1.103")) {
						netRecivePerBytes = net.getNetReceivePerBytes();
						netSendPerBytes = net.getNetSendPerBytes();
					}
				}
				
				// 打印区
				System.out.println();
				System.out.println("cpu总使用率："+cpuUseRate * 100 +"%");
				System.out.println("剩余内存："+memoryFree/1024/1024+"M");
				for(Disk disk : diskList) {
					System.out.println(disk.getDiskName() + " : " + disk.getDiskFree()/1024/1024 +"G");
				}
				System.out.println("每秒接收速度："+netRecivePerBytes / 1024 +"K/s");
				System.out.println("每秒发送速度："+netSendPerBytes / 1024+" K/s");
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			log.error("ERROR:",e);
		}
		
	}
}
