package com.alibaba.webx.common.util.system;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * 【系统信息工具 使用用例】
 * @author xiaoMzjm
 *
 */
public class UseCase {

	private static  SystemInfoUtil systemInfoUtil = null;
	
	@Before
	public void befor(){
		systemInfoUtil = new SystemInfoUtil();
	}
	
	/**
	 * 测试各种信息的获取是否正常————测试通过
	 */
	@Test
	public void test(){
		try {
			// 获取 cpu信息
			CPUs cpus = systemInfoUtil.getCPUs();
			System.out.println(cpus);
			
			// 获取内存信息
			Memory memory = systemInfoUtil.getMemory();
			System.out.println(memory);
			
			// 获取操作系统信息
			OperationSystem operationSystem = systemInfoUtil.getOperationSystem();
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
	 * 测试获取监听必要信息是否正常————测试通过
	 */
	@Test
	public void test2(){
		
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
			e.printStackTrace();
		}
	}
	
	/**
	 * 测试报警系统是否会报警
	 */
	@Test
	public void testMonitor(){
		SystemMonitor systemMonitor = new SystemMonitor();
		systemMonitor.init();
	}
	
	
	/**
	 * 一个会让cpu飙升的程序，哇哈哈
	 */
	public void cpuUp(){
		CPUTestThread cpuTestThread = new CPUTestThread();  
        for (int i = 0; i < 1000; i++) {  
            Thread cpuTest = new Thread(cpuTestThread);  
            cpuTest.start();  
        }  
          
        //Windows Task Manager shows  
        try {  
            Runtime.getRuntime().exec("taskmgr");  
        } catch (IOException e1) {  
            e1.printStackTrace();  
        } 
	}
	class CPUTestThread implements Runnable {  
	    @Override  
	    public void run() {  
	        int busyTime = 10;  
	        int idleTime = busyTime;  
	        long startTime = 0;  
	        while (true) {  
	            startTime = System.currentTimeMillis();  
	            System.out.println(System.currentTimeMillis()+","+startTime+","+(System.currentTimeMillis() - startTime));  
	  
	            // busy loop  
	            while ((System.currentTimeMillis() - startTime) <= busyTime)  
	                ;  
	            // idle loop  
	            try {  
	                Thread.sleep(idleTime);  
	            } catch (InterruptedException e) {  
	                System.out.println(e);  
	            }  
	        }  
	  
	    }
	}
}
