package com.alibaba.webx.common.util.system;



/**
 * 系统信息类
 * 
 * @author xiaoMzjm
 *
 */
public class SystemInfo {

	private CPUs cpus;							// cpu
	private Memory memory;						// 内存 
	private OperationSystem operationSystem;	// 操作系统
	private Disks disks;						// 磁盘
	private Nets nets;							// 网卡
	
	
	public CPUs getCpus() {
		return cpus;
	}

	public void setCpus(CPUs cpus) {
		this.cpus = cpus;
	}

	public Memory getMemory() {
		return memory;
	}

	public void setMemory(Memory memory) {
		this.memory = memory;
	}

	public OperationSystem getOperationSystem() {
		return operationSystem;
	}

	public void setOperationSystem(OperationSystem operationSystem) {
		this.operationSystem = operationSystem;
	}

	public Disks getDisks() {
		return disks;
	}

	public void setDisks(Disks disks) {
		this.disks = disks;
	}

	public Nets getNets() {
		return nets;
	}

	public void setNets(Nets nets) {
		this.nets = nets;
	}

	@Override
	public String toString() {
		return "SystemInfo [cpus=" + cpus + ", memory=" + memory
				+ ", operationSystem=" + operationSystem + ", disks=" + disks
				+ ", nets=" + nets + "]";
	}
	
	
}
