package com.alibaba.webx.common.util.system;

/**
 * 某块磁盘信息
 * 
 * @author xiaoMzjm
 *
 */
public class Disk {

	private String diskName;			// 磁盘名称			如：C:\
	private String diskFileSystemType;	// 文件系统名称		如：NRFS
	private String diskTypeName;		// 文件系统类型		如：本地硬盘local、光驱、网络文件系统等
	private int diskTypeNum;			// 文件系统类型编号	如：0、1、2
	private long diskToltal;			// 文件系统总大小		如：116023572	单位：KB
	private long diskFree;				// 文件系统剩余容量	如：50716268		单位：KB
	private long diskAvail;				// 文件系统可用容量	如：50716268		单位：KB
	private long diskUsed;				// 文件系统已用容量	如：65307304		单位：KB
	private double diskUseRate;			// 文件系统利用率		如：56.99999		单位：百分比
	private long diskReadsNum;			// 磁盘读取次数		如：94592		单位：次
	private	long diskWritesNum;			// 磁盘写入次数		如：16771		单位：次
	public String getDiskName() {
		return diskName;
	}
	public void setDiskName(String diskName) {
		this.diskName = diskName;
	}
	
	public String getDiskFileSystemType() {
		return diskFileSystemType;
	}
	public void setDiskFileSystemType(String diskFileSystemType) {
		this.diskFileSystemType = diskFileSystemType;
	}
	public String getDiskTypeName() {
		return diskTypeName;
	}
	public void setDiskTypeName(String diskTypeName) {
		this.diskTypeName = diskTypeName;
	}
	public int getDiskTypeNum() {
		return diskTypeNum;
	}
	public void setDiskTypeNum(int diskTypeNum) {
		this.diskTypeNum = diskTypeNum;
	}
	public long getDiskToltal() {
		return diskToltal;
	}
	public void setDiskToltal(long diskToltal) {
		this.diskToltal = diskToltal;
	}
	public long getDiskFree() {
		return diskFree;
	}
	public void setDiskFree(long diskFree) {
		this.diskFree = diskFree;
	}
	public long getDiskAvail() {
		return diskAvail;
	}
	public void setDiskAvail(long diskAvail) {
		this.diskAvail = diskAvail;
	}
	public long getDiskUsed() {
		return diskUsed;
	}
	public void setDiskUsed(long diskUsed) {
		this.diskUsed = diskUsed;
	}
	public double getDiskUseRate() {
		return diskUseRate;
	}
	public void setDiskUseRate(double diskUseRate) {
		this.diskUseRate = diskUseRate;
	}
	public long getDiskReadsNum() {
		return diskReadsNum;
	}
	public void setDiskReadsNum(long diskReadsNum) {
		this.diskReadsNum = diskReadsNum;
	}
	public long getDiskWritesNum() {
		return diskWritesNum;
	}
	public void setDiskWritesNum(long diskWritesNum) {
		this.diskWritesNum = diskWritesNum;
	}
	@Override
	public String toString() {
		return "Disk [diskName=" + diskName + ", diskFileSystemType="
				+ diskFileSystemType + ", diskTypeName=" + diskTypeName
				+ ", diskTypeNum=" + diskTypeNum + ", diskToltal=" + diskToltal
				+ ", diskFree=" + diskFree + ", diskAvail=" + diskAvail
				+ ", diskUsed=" + diskUsed + ", diskUseRate=" + diskUseRate
				+ ", diskReadsNum=" + diskReadsNum + ", diskWritesNum="
				+ diskWritesNum + "]";
	}
	
	
}
