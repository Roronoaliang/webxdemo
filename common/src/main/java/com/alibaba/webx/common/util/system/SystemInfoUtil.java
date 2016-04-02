package com.alibaba.webx.common.util.system;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Swap;

/**
 * 【系统信息 工具】
 * 
 * @see sigar api： http://cpansearch.perl.org/src/DOUGM/hyperic-sigar-1.6.3-src/docs/javadoc/org/hyperic/sigar/Sigar.html
 * 
 * @author xiaoMzjm
 *
 */
public class SystemInfoUtil {
	
	private static Sigar sigar = new Sigar();
	private static OperatingSystem OS = OperatingSystem.getInstance();
	
	private static SystemInfo systemInfo = new SystemInfo();				// 系统信息总汇
	
	private static CPUs cpus = new CPUs();									// cpu信息
	private static Memory memory = new Memory();							// 内存信息
	private static OperationSystem operationSystem = new OperationSystem();	// 操作系统信息
	private static Disks disks = new Disks();								// 磁盘信息
	private static Nets nets = new Nets();									// 网卡
	
	
	/*=========================public 1==========================*/
	
	/**
	 * 获取系统最新整体信息
	 * @return
	 * @throws SigarException
	 */
	public SystemInfo getSystemInfo() throws SigarException{
		// 1、cpu
		systemInfo.setCpus(getCPUs());
		
		// 2、内存
		systemInfo.setMemory(getMemory());
		
		// 3、操作系统
		systemInfo.setOperationSystem(getOperationSystem());
		
		// 4、磁盘
		systemInfo.setDisks(getDisks());
		
		// 5、网卡
		systemInfo.setNets(getNets());
		
		return systemInfo;
	}
	
	/**
	 * 获取系统最新CPU信息
	 * @return
	 * @throws SigarException
	 */
	public CPUs getCPUs() throws SigarException{
		
		// a)CPU数量（单位：个）
		getCpuCount();
		
		// b)CPU的总量（单位：HZ）及CPU的相关信息
		getCpuMessage();
		
		// c)获取单块CPU（总的CPU）的百分比
		getOneCpuPerc();
		
		// d) 获取每块CPU的百分比
		getEveryCpuPerc();
		
		return cpus;
	}
	
	/**
	 * 获取最新操作系统信息
	 * @return
	 * @throws SigarException 
	 */
	public OperationSystem getOperationSystem() throws SigarException{
		
		// a)取到当前操作系统的名称：
		getPlatformName();
		
		// b)取当前操作系统的信息
		getOSInfo();
		
		// c)取当前系统进程表中的用户信息
		getSystemUser();
		
		// d)获取当前系统的磁盘信息
		getDisks();
		
		return operationSystem;
	}
	
	/**
	 * 获取最新内存信息
	 * @return
	 * @throws SigarException
	 */
	public Memory getMemory() throws SigarException{
		// a)物理内存信息
		getPhysicalMemory();
		// b)系统页面文件交换区信息
		getSwapMemory();
		return memory;
	}
	
	/**
	 * 获取文件系统（磁盘）信息
	 * @return
	 * @throws Exception
	 */
	public Disks getDisks() throws SigarException{
		// a)取硬盘已有的分区及其详细信息
		getFileSystemInfo();
		return disks;
	}
	
	/**
	 * 获取最新网卡信息
	 * @return
	 */
	public Nets getNets() throws SigarException{
		// a)当前机器的正式域名
		getDomian();
		// b)取到当前机器的IP地址
		getDefaultIpAddress();
		// c)取到当前机器的MAC地址
		getMAC();
		// d)获取网络流量等信息
		getNetIfList();
		return nets;
	}
	
	
	
	
	/*=========================public 2==========================*/
	
	
	
	
	/*========================================================*/
	/* 1.CPU资源信息											  */
	/*========================================================*/
	
	/**
	 * a)获取CPU数量（单位：个）
	 * @return
	 * @throws SigarException
	 */
	public CPUs getCpuCount() throws SigarException {
		cpus.setCpuCount(sigar.getCpuInfoList().length);	// CPU数量
		return cpus;
	}
	
	/**
	 * b)获取每个CPU的总量（单位：HZ）及CPU的相关信息
	 * @return
	 * @throws SigarException
	 */
	public CPUs getCpuMessage() throws SigarException {
		CpuInfo[] infos;
		infos = sigar.getCpuInfoList();
		List<CPU> cpuList = null;
		if(CollectionUtils.isEmpty(cpus.getCpuList()) || (CollectionUtils.isNotEmpty(cpus.getCpuList())&& cpus.getCpuList().size() != cpus.getCpuCount())) {
			cpuList = new ArrayList<CPU>();
			for(int i = 0 ; i < cpus.getCpuCount() ; i ++) {
				CPU cpu = new CPU();
				cpuList.add(cpu);
			}
			cpus.setCpuList(cpuList);
		}
		else {
			cpuList = cpus.getCpuList();
		}
		for (int i = 0; i < infos.length; i++) {// 不管是单块CPU还是多CPU都适用
			CpuInfo info = infos[i];
			cpuList.get(i).setCpuTotalHz(info.getMhz());			// cpu总赫兹
			cpuList.get(i).setCpuSeller(info.getVendor());			// 获得CPU的卖主，如：Intel
			cpuList.get(i).setCpuModel(info.getModel()); 			// 获得CPU的类别，如：Celeron
			cpuList.get(i).setCpuCacheSize(info.getCacheSize());	// 缓冲存储器数量
		}
		return cpus;
	}

	/**
	 * c)获取单块CPU（总的CPU）的百分比
	 * @return
	 * @throws SigarException
	 */
	public CPUs getOneCpuPerc() throws SigarException {
		CpuPerc oneCpuPerc = sigar.getCpuPerc();
		cpus.setCpuFree(oneCpuPerc.getIdle());					// CPU空闲率
		cpus.setCpuTolalUseRate(oneCpuPerc.getCombined());		// CPU使用率
		return cpus;
	}
	
	/**
	 * d) 获取每块CPU的百分比
	 * @throws SigarException
	 */
	public CPUs getEveryCpuPerc() throws SigarException {
		CpuPerc[] cpuPercList = null;
		CpuPerc cpuPerc = null;
		cpuPercList = sigar.getCpuPercList();
		List<CPU> cpuList = null;
		if(CollectionUtils.isEmpty(cpus.getCpuList()) || (CollectionUtils.isNotEmpty(cpus.getCpuList())&& cpus.getCpuList().size() != cpus.getCpuCount())) {
			cpuList = new ArrayList<CPU>();
			for(int i = 0 ; i < cpus.getCpuCount() ; i ++) {
				CPU cpu = new CPU();
				cpuList.add(cpu);
			}
			cpus.setCpuList(cpuList);
		}
		else {
			cpuList = cpus.getCpuList();
		}
		for (int i = 0; i < cpuPercList.length; i++) {
			cpuPerc = cpuPercList[i];
			cpuList.get(i).setCpuUserUseRate(cpuPerc.getUser());   		// 用户使用率
			cpuList.get(i).setCpuSystemUseRate(cpuPerc.getSys());		// 系统使用率
			cpuList.get(i).setCpuWaitRate(cpuPerc.getWait()); 			// 当前等待率
			cpuList.get(i).setCpuFreeRate(cpuPerc.getIdle()); 			// 当前空闲率
			cpuList.get(i).setCpuTolalUseRate(cpuPerc.getCombined()); 	// 总的使用率
		}
		return cpus;
	}

	
	
	
	
	/*========================================================*/
	/* 2.内存资源信息									 		  */
	/*========================================================*/

	/**
	 * a) 获取物理内存信息
	 * @return
	 * @throws SigarException
	 */
	public Memory getPhysicalMemory() throws SigarException {
		Mem mem = sigar.getMem();
		memory.setMemoryTotal(mem.getTotal());	// 内存总量
		memory.setMemoryUsed(mem.getUsed());	// 当前内存使用量
		memory.setMemoryFree(mem.getFree());	// 当前内存剩余量
		return memory;
	}
	
	/**
	 * b) 获取系统页面文件交换区信息
	 * @throws SigarException
	 */
	private Memory getSwapMemory() throws SigarException{
		Swap swap = sigar.getSwap();
		memory.setSwapMemoryTotal(swap.getTotal());	// 交换区总量
		memory.setSwapMemoryUsed(swap.getUsed());	// 当前交换区使用量
		memory.setSwapMemoryFree(swap.getFree());	// 当前交换区剩余量
		return memory;
	}

	
	
	
	
	
	/*========================================================*/
	/* 3.操作系统信息									 		  */
	/*========================================================*/

	/**
	 * a)取到当前操作系统的名称：
	 * @return
	 */
	public OperationSystem getPlatformName() {
		String hostname = "";
		try {
			hostname = InetAddress.getLocalHost().getHostName();
		} catch (Exception exc) {
			Sigar sigar = new Sigar();
			try {
				hostname = sigar.getNetInfo().getHostName();
			} catch (SigarException e) {
				hostname = "localhost.unknown";
			} finally {
				sigar.close();
			}
		}
		operationSystem.setSystemUserName(hostname);		// 操作系统名称
		return operationSystem;
	}
	
	/**
	 * b)取当前操作系统的信息
	 * @return
	 */
	public OperationSystem getOSInfo() {
		// 操作系统内核类型如： 386、486、586等x86
		operationSystem.setSystemArchitecture(OS.getArch());
		operationSystem.setSystemCpuEndian(OS.getCpuEndian());
		operationSystem.setSystemDigits(OS.getDataModel());
		operationSystem.setSystemDescribe(OS.getDescription());		// 系统描述
		operationSystem.setSystemSeller(OS.getVendor());			// 操作系统的卖主
		operationSystem.setSystemVersion(OS.getVersion());			// 操作系统的版本号
		return operationSystem;
	}

	/**
	 * c)取当前系统进程表中的用户信息
	 * @return
	 * @throws SigarException
	 */
	public OperationSystem getSystemUser() throws SigarException {
		org.hyperic.sigar.Who[] whos = sigar.getWhoList();
		List<OperationSystemUser> userList = new ArrayList<OperationSystemUser>();
		if (whos != null && whos.length > 0) {
			for (int i = 0; i < whos.length; i++) {
				org.hyperic.sigar.Who who = whos[i];
				OperationSystemUser user = new OperationSystemUser();
				user.setDevice(who.getDevice());		// 用户使用的设备，如console
				user.setHostName(who.getHost());		// 计算机名
				user.setUserName(who.getUser());		// 用户名
				userList.add(user);
			}
			operationSystem.setOperationSystemUserList(userList);
		}
		return operationSystem;
	}

	
	
	
	
	/*========================================================*/
	/* 4.资源信息（主要是硬盘）								 	  */
	/*========================================================*/

	/**
	 * a)取得每一块硬盘已有的分区及其详细信息
	 * @return
	 * @throws SigarException
	 */
	public Disks getFileSystemInfo() throws SigarException {
		FileSystem fslist[] = sigar.getFileSystemList();
		List<Disk> diskList = null;
		if(CollectionUtils.isEmpty(disks.getDiskList())) {
			diskList = new ArrayList<Disk>();
			int disksLength = fslist.length;
			for(int i = 0 ; i < disksLength ; i++) {
				Disk disk = new Disk();
				diskList.add(disk);
			}
			disks.setDiskList(diskList);
		}
		else {
			diskList = disks.getDiskList();
		}
		for (int i = 0; i < fslist.length; i++) {
			FileSystem fs = fslist[i];
			Disk disk = diskList.get(i);
			
			disk.setDiskName(fs.getDevName());					// 分区的盘符名称
			disk.setDiskFileSystemType(fs.getSysTypeName());	// 文件系统类型，比如 FAT32、NTFS
			disk.setDiskTypeName(fs.getTypeName());				// 文件系统类型名，比如本地硬盘、光驱、网络文件系统等
			disk.setDiskTypeNum(fs.getType());					// 文件系统类型编号
			
			FileSystemUsage usage = null;
			try {
				usage = sigar.getFileSystemUsage(fs.getDirName());
			} catch (SigarException e) {
				if (fs.getType() == 2)
					throw e;
				continue;
			}
			switch (fs.getType()) {
			case 0: // TYPE_UNKNOWN ：未知
				break;
			case 1: // TYPE_NONE
				break;
			case 2: // TYPE_LOCAL_DISK : 本地硬盘
				disk.setDiskToltal(usage.getTotal());		// 文件系统总大小
				disk.setDiskFree(usage.getFree());			// 文件系统剩余大小
				disk.setDiskAvail(usage.getAvail());		// 文件系统可用大小
				disk.setDiskUsed(usage.getUsed());			// 文件系统已经使用量
				disk.setDiskUseRate(usage.getUsePercent());	// 文件系统资源的利用率
				break;
			case 3:// TYPE_NETWORK ：网络
				break;
			case 4:// TYPE_RAM_DISK ：闪存
				break;
			case 5:// TYPE_CDROM ：光驱
				break;
			case 6:// TYPE_SWAP ：页面交换
				break;
			}
			disk.setDiskReadsNum(usage.getDiskReads());
			disk.setDiskWritesNum(usage.getDiskWrites());
		}
		return disks;
	}

	
	
	
	
	/*========================================================*/
	/* 5.网络信息											 	  */
	/*========================================================*/
	/**
	 * a)当前机器的正式域名
	 * @return
	 * @throws SigarException
	 */
	public Nets getDomian() throws SigarException {
		try {
			nets.setNetDomian(InetAddress.getLocalHost().getCanonicalHostName());	// 获取域名
		} catch (UnknownHostException e) {
			nets.setNetDomian(sigar.getFQDN());
		}
		return nets;
	}

	/**
	 * b)取到当前机器的IP地址
	 * @return
	 */
	public Nets getDefaultIpAddress() {
		String address = null;
		try {
			address = InetAddress.getLocalHost().getHostAddress();
			// 没有出现异常而正常当取到的IP时，如果取到的不是网卡循回地址时就返回
			// 否则再通过Sigar工具包中的方法来获取
			if (!NetFlags.LOOPBACK_ADDRESS.equals(address)) {
				nets.setNetIpAddress(address);
				return nets;
			}
		} catch (UnknownHostException e) {
			// hostname not in DNS or /etc/hosts
		}
		try {
			address = sigar.getNetInterfaceConfig().getAddress();
		} catch (SigarException e) {
			address = NetFlags.LOOPBACK_ADDRESS;
		} 
		nets.setNetIpAddress(address);
		return nets;
	}

	// c)取到当前机器的MAC地址
	public Nets getMAC() throws SigarException {
		String[] ifaces = sigar.getNetInterfaceList();
		String hwaddr = null;
		for (int i = 0; i < ifaces.length; i++) {
			NetInterfaceConfig cfg = sigar.getNetInterfaceConfig(ifaces[i]);
			if (NetFlags.LOOPBACK_ADDRESS.equals(cfg.getAddress())
					|| (cfg.getFlags() & NetFlags.IFF_LOOPBACK) != 0
					|| NetFlags.NULL_HWADDR.equals(cfg.getHwaddr())) {
				continue;
			}
			/*
			 * 如果存在多张网卡包括虚拟机的网卡，默认只取第一张网卡的MAC地址，如果要返回所有的网卡（包括物理的和虚拟的）
			 * 则可以修改方法的返回类型为数组或Collection ，通过在for循环里取到的多个MAC地址。
			 */
			hwaddr = cfg.getHwaddr();
			break;
		}
		nets.setNetMAC(hwaddr != null ? hwaddr : null);
		return nets;
	}
	

	// d)获取每块网卡网络流量等信息
	public Nets getNetIfList() throws SigarException {
		String ifNames[] = sigar.getNetInterfaceList();
		List<Net> netList = null;
		if(CollectionUtils.isEmpty(nets.getNetList())) {
			netList = new ArrayList<Net>();
			for(int i = 0 ; i < ifNames.length ; i++) {
				Net net = new Net();
				netList.add(net);
			}
			nets.setNetList(netList);
		}
		else {
			netList = nets.getNetList();
		}
		for (int i = 0; i < ifNames.length; i++) {
			Net net = netList.get(i);
			
			String name = ifNames[i];
			NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(name);
			net.setNetName(name);
			net.setNetIpAddress(ifconfig.getAddress());
			net.setNetMask(ifconfig.getNetmask());
			net.setNetMtu(ifconfig.getMtu());
			net.setNetBroadcase(ifconfig.getBroadcast());
			net.setNetDescription(ifconfig.getDescription());
			net.setNetMac(ifconfig.getHwaddr());
			if ((ifconfig.getFlags() & 1L) <= 0L) {
				continue;
			}
			NetInterfaceStat ifstat = sigar.getNetInterfaceStat(name);
			// 计算出每秒接收字节数
			if(net.getNetReceiveTotalBytes() != 0) {
				net.setNetReceivePerBytes(ifstat.getRxBytes() - net.getNetReceiveTotalBytes());
			}
			// 计算出每秒发送字节数
			if(net.getNetSendTotalBytes() != 0) {
				net.setNetSendPerBytes(ifstat.getTxBytes() - net.getNetSendTotalBytes());
			}
			net.setNetReceiveTotalPackage(ifstat.getRxPackets());
			net.setNetSendTotalPackage(ifstat.getTxPackets());
			net.setNetReceiveTotalBytes(ifstat.getRxBytes());
			net.setNetSendTotalBytes(ifstat.getTxBytes());
			net.setNetReceiveErrorPackage(ifstat.getRxErrors());
			net.setNetSendErrorPackage(ifstat.getTxErrors());
			net.setNetReceiveThrowAwayPackage(ifstat.getRxDropped());
			net.setNetSendThrowAwayPackage(ifstat.getTxDropped());
		}
		return nets;
	}
}