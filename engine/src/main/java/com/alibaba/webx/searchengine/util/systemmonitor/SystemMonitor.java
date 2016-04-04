package com.alibaba.webx.searchengine.util.systemmonitor;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.webx.common.util.date.MyDateUtil;
import com.alibaba.webx.common.util.system.CPUs;
import com.alibaba.webx.common.util.system.Disks;
import com.alibaba.webx.common.util.system.Memory;
import com.alibaba.webx.common.util.system.Net;
import com.alibaba.webx.common.util.system.Nets;
import com.alibaba.webx.common.util.system.SystemInfoUtil;
import com.alibaba.webx.searchengine.factory.mail.MailFactory;
import com.alibaba.webx.searchengine.factory.mail.MailSenderUtil;
import com.alibaba.webx.searchengine.util.log.LoggerUtils;
import com.alibaba.webx.searchengine.util.switchs.MySwitchUtil;

/**
 * 系统监控报警器
 * 
 * 功能：监听系统CPU、内存、磁盘、网速，当达到配置的阀值时报警.
 * 
 * 报警使用的是本项目集成的MailUtil，使用默认的邮箱发送。
 * 
 * @author xiaoMzjm
 *
 */
public class SystemMonitor {
	
	private Logger log = LoggerFactory.getLogger(SystemMonitor.class);
	private static SystemInfoUtil systemInfoUtil = new SystemInfoUtil();
	
	@Autowired
	private MailFactory mailFactory;
	
	@Autowired
	private MySwitchUtil mySwitchUtil;
	
	@Autowired
	private LoggerUtils loggerUtils;
	
	// 邮件发送对象
	private static MailSenderUtil mailSender;
	
	// 接收者
	private List<String> acceptorList;
	
	// 标题
	private String emailTitle;
	
	// 发送错误邮件的频率，单位秒
	private int sendEmailRate;
	
	// 线程池
	private static ScheduledThreadPoolExecutor stpe;
	
	// 错误队列
	public static Queue<SystemMonitorBean> queue = new ConcurrentLinkedQueue<SystemMonitorBean>();
	
	private static float cpuAlarmPercent;		// 单位：百分比	意思：cpu使用率报警阀值	
	private static int cpuAlarmLastTime;		// 单位：秒		意思：cpu持续超过报警阀值多久才报警。
	private static int cpuHasAlarmTime;			// 记录cpu已超过报警阀值的持续时间
	
	private static float memoryAlarmPercent;	// 单位：百分比	意思：内存使用率报警阀值
	private static int memoryAlarmLastTime;		// 单位：秒		意思：内存持续超过报警阀值多久才报警
	private static int memoryHasAlarmTime;		// 记录内存已超过报警阀值的持续时间
	
	private static float diskAlarmPercent;		// 单位：百分比	意思：磁盘使用率报警阀值
	private static int diskAlarmLastTime;		// 单位：秒		意思：磁盘持续超过报警阀值多久才报警
	private static int diskHasAlarmTime;		// 记录磁盘已超过报警阀值的持续时间
	
	private static String ipAddress;			// 要监听网速的网卡IP
	
	private static float netReceiveAlarmSpeed;	// 单位：K/s		意思：网络接收速度报警阀值
	private static int netReceiveAlarmLastTime; // 单位：秒		意思：网络接收速度持续超过报警阀值多久才报警
	private static int netReceiveHasAlarmTime;	// 记录网络接收速度已超过报警阀值的持续时间
	
	private static float netSendAlarmSpeed;		// 单位：K/s		意思：网络发送速度报警阀值
	private static int netSendAlarmLastTime;	// 单位：秒		意思：网络发送速度持续超过报警阀值多久才报警
	private static int netSendHasAlarmTime;		// 记录网络发送速度已超过报警阀值的持续时间

	/**
	 * 初始化函数
	 */
	public void init(){
		
		// 开始监听系统信息
		Thread t = new Thread(new SystemMonitorListenThread());
		t.start();
		
		// 消费错误消息
		try {
			stpe = new ScheduledThreadPoolExecutor(1);
			mailSender = mailFactory.getDefaultMailSender();
			SystemMonitorThread systemMonitorThread = new SystemMonitorThread(mailSender,acceptorList,emailTitle);
			stpe.scheduleAtFixedRate(systemMonitorThread , 10, sendEmailRate , TimeUnit.SECONDS);
		} catch (Exception e) {
			log.error("ERROR:",e);
			loggerUtils.emailError(e);
		}
	}
	
	class SystemMonitorListenThread implements Runnable {

		public void run() {
			// CPU使用率
			CPUs cpus = null;
			double cpuUseRate = 0;	
			
			// 内存利用率
			long memoryUsed = 0;	
			long memoryTotal = 0;
			double memoryUseRate = 0;
			Memory memory = null;
			
			//磁盘使用量
			Disks disks = null;
			long disksTotal = 0;
			long disksUsed = 0;
			double disksUsedRate = 0;
			
			// 每秒接收 & 发送速度
			Nets nets = null;
			List<Net> netList = null;
			long netReceivePerBytes = 0;
			long netSendPerBytes = 0;
			float netReceiveSpeed = 0;
			float netSendSpeed = 0;
			
			try {
				while(true) {
					if(mySwitchUtil.isEMAIL_SYSTEM_MONITOR_SWITCH()){
						// 获取最新CPU
						cpus = systemInfoUtil.getOneCpuPerc();
						cpuUseRate = cpus.getCpuTolalUseRate();
						
						// 获取最新内存
						memory = systemInfoUtil.getMemory();
						memoryUsed = memory.getMemoryUsed();
						memoryTotal = memory.getMemoryTotal();
						
						// 获取最新磁盘
						disks = systemInfoUtil.getDisks();
						disksUsed = disks.getDisksUsed();
						disksTotal = disks.getDisksTotal();
						
						// 获取最新网络
						nets = systemInfoUtil.getNets();
						netList = nets.getNetList();
						for(Net net : netList) {
							if(net.getNetIpAddress().equals(ipAddress)) {
								netReceivePerBytes = net.getNetReceivePerBytes();
								netSendPerBytes = net.getNetSendPerBytes();
							}
						}
						
						// 计算利用率等
						cpuUseRate *=100;										// cpu利用率
						memoryUseRate = ((double)memoryUsed/memoryTotal)*100;	// 内存利用率
						disksUsedRate = (double)disksUsed/disksTotal*100;		// 磁盘使用率
						netReceiveSpeed = netReceivePerBytes / 1024;			// 网络接收速度
						netSendSpeed = netSendPerBytes / 1024;					// 网络发送速度
						
						// 打印测试 区
//						System.out.println();
//						System.out.println("cpu总使用率："+cpuUseRate+"%");
//						System.out.println("内存利用率："+memoryUseRate+"%");
//						System.out.println("磁盘总利用率："+disksUsedRate+"%");
//						System.out.println("每秒接收速度："+netReceiveSpeed+"K/s");
//						System.out.println("每秒发送速度："+netSendSpeed+" K/s");
						
						// 统计区
						cpuHasAlarmTime = cpuUseRate >= cpuAlarmPercent ? cpuHasAlarmTime + 1 : 0;
						memoryHasAlarmTime = memoryUseRate >= memoryAlarmPercent ? memoryHasAlarmTime + 1 : 0;
						diskHasAlarmTime = disksUsedRate >= diskAlarmPercent ? diskHasAlarmTime + 1 : 0;
						netReceiveHasAlarmTime = netReceiveSpeed >= netReceiveAlarmSpeed ? netReceiveHasAlarmTime + 1 : 0;
						netSendHasAlarmTime = netSendSpeed >= netSendAlarmSpeed ? netSendHasAlarmTime + 1 : 0;
						
						// 报警区
						if(cpuHasAlarmTime >= cpuAlarmLastTime) {
							SystemMonitorBean SystemMonitorBean = new SystemMonitorBean(MyDateUtil.getNowStringDate(),"警告！cpu使用率在连续 "+cpuAlarmLastTime+"s 内都超过阀值 "+cpuAlarmPercent+"% 。");
							queue.add(SystemMonitorBean);
//							System.out.println("cpu报警！！！");
							cpuHasAlarmTime = 0;
						}
						if(memoryHasAlarmTime >= memoryAlarmLastTime) {
							SystemMonitorBean SystemMonitorBean = new SystemMonitorBean(MyDateUtil.getNowStringDate(),"警告！内存使用率在连续 "+memoryAlarmLastTime+"s 内都超过阀值' "+memoryAlarmPercent+"%' 。");
							queue.add(SystemMonitorBean);
//							System.out.println("内存报警！！！");
							memoryHasAlarmTime = 0;
						}
						if(diskHasAlarmTime >= diskAlarmLastTime) {
							SystemMonitorBean SystemMonitorBean = new SystemMonitorBean(MyDateUtil.getNowStringDate(),"警告！磁盘总使用率在连续 "+diskAlarmLastTime+"s 内都超过阀值' "+diskAlarmPercent+"%' 。");
							queue.add(SystemMonitorBean);
//							System.out.println("磁盘报警！！！");
							diskHasAlarmTime = 0;
						}
						if(netReceiveHasAlarmTime >= netReceiveAlarmLastTime) {
							SystemMonitorBean SystemMonitorBean = new SystemMonitorBean(MyDateUtil.getNowStringDate(),"警告！接收网速在连续 "+netReceiveAlarmLastTime+"s 内都超过阀值 '"+netReceiveSpeed+"K/s' 。");
							queue.add(SystemMonitorBean);
//							System.out.println("接收网速报警！！！");
							netReceiveHasAlarmTime = 0;
						}
						if(netSendHasAlarmTime >= netSendAlarmLastTime) {
							SystemMonitorBean SystemMonitorBean = new SystemMonitorBean(MyDateUtil.getNowStringDate(),"警告！发送网速在连续 "+netSendAlarmLastTime+"s 内都超过阀值' "+netSendAlarmSpeed+"K/s' 。");
							queue.add(SystemMonitorBean);
//							System.out.println("发送网速报警！！！");
							netSendHasAlarmTime = 0;
						}
					}
					
					Thread.sleep(1000);
				}
			} catch (Exception e) {
				log.error("ERROR:",e);
				loggerUtils.emailError(e);
			}
		}
	}
	
	// get & set
	public static float getCpuAlarmPercent() {
		return cpuAlarmPercent;
	}

	public static void setCpuAlarmPercent(float cpuAlarmPercent) {
		SystemMonitor.cpuAlarmPercent = cpuAlarmPercent;
	}

	public static int getCpuHasAlarmTime() {
		return cpuHasAlarmTime;
	}

	public static void setCpuHasAlarmTime(int cpuHasAlarmTime) {
		SystemMonitor.cpuHasAlarmTime = cpuHasAlarmTime;
	}

	public static float getMemoryAlarmPercent() {
		return memoryAlarmPercent;
	}

	public static void setMemoryAlarmPercent(float memoryAlarmPercent) {
		SystemMonitor.memoryAlarmPercent = memoryAlarmPercent;
	}

	public static int getMemoryHasAlarmTime() {
		return memoryHasAlarmTime;
	}

	public static void setMemoryHasAlarmTime(int memoryHasAlarmTime) {
		SystemMonitor.memoryHasAlarmTime = memoryHasAlarmTime;
	}

	public static float getDiskAlarmPercent() {
		return diskAlarmPercent;
	}

	public static void setDiskAlarmPercent(float diskAlarmPercent) {
		SystemMonitor.diskAlarmPercent = diskAlarmPercent;
	}

	public static int getDiskHasAlarmTime() {
		return diskHasAlarmTime;
	}

	public static void setDiskHasAlarmTime(int diskHasAlarmTime) {
		SystemMonitor.diskHasAlarmTime = diskHasAlarmTime;
	}

	public static float getNetReceiveAlarmSpeed() {
		return netReceiveAlarmSpeed;
	}

	public static void setNetReceiveAlarmSpeed(float netReceiveAlarmSpeed) {
		SystemMonitor.netReceiveAlarmSpeed = netReceiveAlarmSpeed;
	}

	public static int getNetReceiveHasAlarmTime() {
		return netReceiveHasAlarmTime;
	}

	public static void setNetReceiveHasAlarmTime(int netReceiveHasAlarmTime) {
		SystemMonitor.netReceiveHasAlarmTime = netReceiveHasAlarmTime;
	}

	public static float getNetSendAlarmSpeed() {
		return netSendAlarmSpeed;
	}

	public static void setNetSendAlarmSpeed(float netSendAlarmSpeed) {
		SystemMonitor.netSendAlarmSpeed = netSendAlarmSpeed;
	}

	public static int getNetSendHasAlarmTime() {
		return netSendHasAlarmTime;
	}

	public static void setNetSendHasAlarmTime(int netSendHasAlarmTime) {
		SystemMonitor.netSendHasAlarmTime = netSendHasAlarmTime;
	}

	public static String getIpAddress() {
		return ipAddress;
	}

	public static void setIpAddress(String ipAddress) {
		SystemMonitor.ipAddress = ipAddress;
	}

	public static int getCpuAlarmLastTime() {
		return cpuAlarmLastTime;
	}

	public static void setCpuAlarmLastTime(int cpuAlarmLastTime) {
		SystemMonitor.cpuAlarmLastTime = cpuAlarmLastTime;
	}

	public static int getMemoryAlarmLastTime() {
		return memoryAlarmLastTime;
	}

	public static void setMemoryAlarmLastTime(int memoryAlarmLastTime) {
		SystemMonitor.memoryAlarmLastTime = memoryAlarmLastTime;
	}

	public static int getDiskAlarmLastTime() {
		return diskAlarmLastTime;
	}

	public static void setDiskAlarmLastTime(int diskAlarmLastTime) {
		SystemMonitor.diskAlarmLastTime = diskAlarmLastTime;
	}

	public static int getNetReceiveAlarmLastTime() {
		return netReceiveAlarmLastTime;
	}

	public static void setNetReceiveAlarmLastTime(int netReceiveAlarmLastTime) {
		SystemMonitor.netReceiveAlarmLastTime = netReceiveAlarmLastTime;
	}

	public static int getNetSendAlarmLastTime() {
		return netSendAlarmLastTime;
	}

	public static void setNetSendAlarmLastTime(int netSendAlarmLastTime) {
		SystemMonitor.netSendAlarmLastTime = netSendAlarmLastTime;
	}

	public List<String> getAcceptorList() {
		return acceptorList;
	}

	public void setAcceptorList(List<String> acceptorList) {
		this.acceptorList = acceptorList;
	}

	public String getEmailTitle() {
		return emailTitle;
	}

	public void setEmailTitle(String emailTitle) {
		this.emailTitle = emailTitle;
	}

	public int getSendEmailRate() {
		return sendEmailRate;
	}

	public void setSendEmailRate(int sendEmailRate) {
		this.sendEmailRate = sendEmailRate;
	}
	
	
}
