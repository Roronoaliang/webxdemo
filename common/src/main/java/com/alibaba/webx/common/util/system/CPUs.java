package com.alibaba.webx.common.util.system;

import java.util.List;

/**
 * 系统cpu集合
 * 
 * @author xiaoMzjm
 *
 */
public class CPUs {
	
	private int cpuCount;			// 系统cpu个数
	private double cpuTolalUseRate;	// cpu总使用率
	private double cpuFree;			// cpu空闲率
	private List<CPU> cpuList;		// cpu集合
	
	
	public double getCpuTolalUseRate() {
		return cpuTolalUseRate;
	}
	public void setCpuTolalUseRate(double cpuTolalUseRate) {
		this.cpuTolalUseRate = cpuTolalUseRate;
	}
	public int getCpuCount() {
		return cpuCount;
	}
	public void setCpuCount(int cpuCount) {
		this.cpuCount = cpuCount;
	}
	
	public List<CPU> getCpuList() {
		return cpuList;
	}
	public void setCpuList(List<CPU> cpuList) {
		this.cpuList = cpuList;
	}
	
	public double getCpuFree() {
		return cpuFree;
	}
	public void setCpuFree(double cpuFree) {
		this.cpuFree = cpuFree;
	}
	@Override
	public String toString() {
		return "CPUs [cpuCount=" + cpuCount + ", cpuTolalUseRate="
				+ cpuTolalUseRate + ", cpuFree=" + cpuFree + ", cpuList="
				+ cpuList + "]";
	}
}
