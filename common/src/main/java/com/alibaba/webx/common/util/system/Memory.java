package com.alibaba.webx.common.util.system;

/**
 * 内存
 * 
 * @author xiaoMzjm
 *
 */
public class Memory {

	private long memoryTotal;		// 内存总量		单位：字节
	private long memoryUsed;		// 内存使用量		单位：字节
	private long memoryFree;		// 内存剩余量		单位：字节
	private long swapMemoryTotal;	// 交换区总量		单位：字节
	private long swapMemoryUsed;	// 交换区使用量	单位：字节
	private long swapMemoryFree;	// 交换区剩余量	单位：字节
	public long getMemoryTotal() {
		return memoryTotal;
	}
	public void setMemoryTotal(long memoryTotal) {
		this.memoryTotal = memoryTotal;
	}
	public long getMemoryUsed() {
		return memoryUsed;
	}
	public void setMemoryUsed(long memoryUsed) {
		this.memoryUsed = memoryUsed;
	}
	public long getMemoryFree() {
		return memoryFree;
	}
	public void setMemoryFree(long memoryFree) {
		this.memoryFree = memoryFree;
	}
	public long getSwapMemoryTotal() {
		return swapMemoryTotal;
	}
	public void setSwapMemoryTotal(long swapMemoryTotal) {
		this.swapMemoryTotal = swapMemoryTotal;
	}
	public long getSwapMemoryUsed() {
		return swapMemoryUsed;
	}
	public void setSwapMemoryUsed(long swapMemoryUsed) {
		this.swapMemoryUsed = swapMemoryUsed;
	}
	public long getSwapMemoryFree() {
		return swapMemoryFree;
	}
	public void setSwapMemoryFree(long swapMemoryFree) {
		this.swapMemoryFree = swapMemoryFree;
	}
	@Override
	public String toString() {
		return "Memory [memoryTotal=" + memoryTotal + ", memoryUsed="
				+ memoryUsed + ", memoryFree=" + memoryFree
				+ ", swapMemoryTotal=" + swapMemoryTotal + ", swapMemoryUsed="
				+ swapMemoryUsed + ", swapMemoryFree=" + swapMemoryFree + "]";
	}
	
}
