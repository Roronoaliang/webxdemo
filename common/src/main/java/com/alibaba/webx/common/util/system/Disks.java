package com.alibaba.webx.common.util.system;

import java.util.List;

/**
 * 系统磁盘集合
 * 
 * @author xiaoMzjm
 *
 */
public class Disks {

	private List<Disk> diskList;

	public List<Disk> getDiskList() {
		return diskList;
	}

	public void setDiskList(List<Disk> diskList) {
		this.diskList = diskList;
	}

	@Override
	public String toString() {
		return "Disks [diskList=" + diskList + "]";
	}
	
	
}
