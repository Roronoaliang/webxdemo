package com.alibaba.webx.common.util.system;

/**
 * 某块cpu
 * 
 * @author xiaoMzjm
 *
 */
public class CPU {

	private int cpuTotalHz;			// 该cpu总赫兹
	private String cpuSeller;		// 该cpu厂商
	private String cpuModel;		// 该cpu类别
	private long cpuCacheSize;		// 该cpu缓冲器数量
	private double cpuUserUseRate;	// 该cpu用户的使用率
	private double cpuSystemUseRate;// 该cpu系统的使用率
	private double cpuWaitRate;		// 该cpu等待率
	private double cpuFreeRate;		// 该cpu空闲率
	private double cpuTolalUseRate;	// 该cpu总使用率
	
	
	public int getCpuTotalHz() {
		return cpuTotalHz;
	}
	public void setCpuTotalHz(int cpuTotalHz) {
		this.cpuTotalHz = cpuTotalHz;
	}
	public String getCpuSeller() {
		return cpuSeller;
	}
	public void setCpuSeller(String cpuSeller) {
		this.cpuSeller = cpuSeller;
	}
	public String getCpuModel() {
		return cpuModel;
	}
	public void setCpuModel(String cpuModel) {
		this.cpuModel = cpuModel;
	}
	
	public long getCpuCacheSize() {
		return cpuCacheSize;
	}
	public void setCpuCacheSize(long cpuCacheSize) {
		this.cpuCacheSize = cpuCacheSize;
	}
	public double getCpuUserUseRate() {
		return cpuUserUseRate;
	}
	public void setCpuUserUseRate(double cpuUserUseRate) {
		this.cpuUserUseRate = cpuUserUseRate;
	}
	public double getCpuSystemUseRate() {
		return cpuSystemUseRate;
	}
	public void setCpuSystemUseRate(double cpuSystemUseRate) {
		this.cpuSystemUseRate = cpuSystemUseRate;
	}
	
	public double getCpuWaitRate() {
		return cpuWaitRate;
	}
	public void setCpuWaitRate(double cpuWaitRate) {
		this.cpuWaitRate = cpuWaitRate;
	}
	public double getCpuFreeRate() {
		return cpuFreeRate;
	}
	public void setCpuFreeRate(double cpuFreeRate) {
		this.cpuFreeRate = cpuFreeRate;
	}
	public double getCpuTolalUseRate() {
		return cpuTolalUseRate;
	}
	public void setCpuTolalUseRate(double cpuTolalUseRate) {
		this.cpuTolalUseRate = cpuTolalUseRate;
	}
	@Override
	public String toString() {
		return "CPU [cpuTotalHz=" + cpuTotalHz + ", cpuSeller=" + cpuSeller
				+ ", cpuModel=" + cpuModel + ", cpuCacheSize=" + cpuCacheSize
				+ ", cpuUserUseRate=" + cpuUserUseRate + ", cpuSystemUseRate="
				+ cpuSystemUseRate + ", cpuWaitRate=" + cpuWaitRate
				+ ", cpuFreeRate=" + cpuFreeRate + ", cpuTolalUseRate="
				+ cpuTolalUseRate + "]";
	}
	
	
}
