package com.alibaba.webx.common.util.system;

import java.util.List;

/**
 * 网卡集合
 * 
 * @author xiaoMzjm
 *
 */
public class Nets {
	private String netDomian;		// 域名
	private String netIpAddress;	// ip地址
	private String netMAC;			// MAC地址
	private List<Net> netList;		// 网卡
	public String getNetDomian() {
		return netDomian;
	}
	public void setNetDomian(String netDomian) {
		this.netDomian = netDomian;
	}
	public String getNetIpAddress() {
		return netIpAddress;
	}
	public void setNetIpAddress(String netIpAddress) {
		this.netIpAddress = netIpAddress;
	}
	public String getNetMAC() {
		return netMAC;
	}
	public void setNetMAC(String netMAC) {
		this.netMAC = netMAC;
	}
	public List<Net> getNetList() {
		return netList;
	}
	public void setNetList(List<Net> netList) {
		this.netList = netList;
	}
	@Override
	public String toString() {
		return "Nets [netDomian=" + netDomian + ", netIpAddress="
				+ netIpAddress + ", netMAC=" + netMAC + ", netList=" + netList
				+ "]";
	}
	
	
}
