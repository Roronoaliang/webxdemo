package com.alibaba.webx.common.util.system;

import java.util.List;

/**
 * 系统磁盘集合
 * 
 * @author xiaoMzjm
 *
 */
public class Disks {

	private long disksTotal;			// 磁盘总大小
	private long disksFree;				// 磁盘总剩余量
	private long disksUsed;				// 磁盘总已用量
	private List<Disk> diskList;		// 每块磁盘

	public long getDisksTotal() {
		return disksTotal;
	}

	public void setDisksTotal(long disksTotal) {
		this.disksTotal = disksTotal;
	}

	public long getDisksFree() {
		return disksFree;
	}

	public void setDisksFree(long disksFree) {
		this.disksFree = disksFree;
	}

	public long getDisksUsed() {
		return disksUsed;
	}

	public void setDisksUsed(long disksUsed) {
		this.disksUsed = disksUsed;
	}

	public List<Disk> getDiskList() {
		return diskList;
	}

	public void setDiskList(List<Disk> diskList) {
		this.diskList = diskList;
	}

	@Override
	public String toString() {
		return "Disks [disksTotal=" + disksTotal + ", disksFree=" + disksFree
				+ ", disksUsed=" + disksUsed + ", diskList=" + diskList + "]";
	}

}
