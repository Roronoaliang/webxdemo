package com.alibaba.webx.common.util.system;

/**
 * 某块网卡信息
 * 
 * @author xiaoMzjm
 *
 */
public class Net {

	private String netName;					// 网卡名称
	private String netIpAddress;			// 该网卡绑定的IP地址
	private String netMask;					// 掩码
	private String netMac;					// 该网卡绑定的MAC地址
	private String netBroadcase;			// 该网卡绑定的广播地址
	private String netDescription;			// 该网卡的描述信息
	
	private long netReceiveTotalPackage;	// 接收的总包裹数
	private long netSendTotalPackage;		// 发送的总包裹数
	private long netReceiveTotalBytes;		// 接收的总字节数
	private long netReceivePerBytes;		// 每秒的接收字节数
	private long netSendTotalBytes;			// 发送的总字节数
	private long netSendPerBytes;			// 每秒发送的字节数
	private long netReceiveErrorPackage;	// 接收到的错误包数
	private long netSendErrorPackage;		// 发送的错误包数
	private long netReceiveThrowAwayPackage;// 接收时丢弃的包数
	private long netSendThrowAwayPackage;	// 发送时丢弃的包数
	private long netMtu;					// 该网卡的最大传输单元，单位：字节
	
	public String getNetName() {
		return netName;
	}
	public void setNetName(String netName) {
		this.netName = netName;
	}
	public String getNetMask() {
		return netMask;
	}
	public void setNetMask(String netMask) {
		this.netMask = netMask;
	}
	public long getNetReceiveTotalPackage() {
		return netReceiveTotalPackage;
	}
	public void setNetReceiveTotalPackage(long netReceiveTotalPackage) {
		this.netReceiveTotalPackage = netReceiveTotalPackage;
	}
	public long getNetSendTotalPackage() {
		return netSendTotalPackage;
	}
	public void setNetSendTotalPackage(long netSendTotalPackage) {
		this.netSendTotalPackage = netSendTotalPackage;
	}
	public long getNetReceiveTotalBytes() {
		return netReceiveTotalBytes;
	}
	public void setNetReceiveTotalBytes(long netReceiveTotalBytes) {
		this.netReceiveTotalBytes = netReceiveTotalBytes;
	}
	public long getNetSendTotalBytes() {
		return netSendTotalBytes;
	}
	public void setNetSendTotalBytes(long netSendTotalBytes) {
		this.netSendTotalBytes = netSendTotalBytes;
	}
	public long getNetReceiveErrorPackage() {
		return netReceiveErrorPackage;
	}
	public void setNetReceiveErrorPackage(long netReceiveErrorPackage) {
		this.netReceiveErrorPackage = netReceiveErrorPackage;
	}
	public long getNetSendErrorPackage() {
		return netSendErrorPackage;
	}
	public void setNetSendErrorPackage(long netSendErrorPackage) {
		this.netSendErrorPackage = netSendErrorPackage;
	}
	public long getNetReceiveThrowAwayPackage() {
		return netReceiveThrowAwayPackage;
	}
	public void setNetReceiveThrowAwayPackage(long netReceiveThrowAwayPackage) {
		this.netReceiveThrowAwayPackage = netReceiveThrowAwayPackage;
	}
	public long getNetSendThrowAwayPackage() {
		return netSendThrowAwayPackage;
	}
	public void setNetSendThrowAwayPackage(long netSendThrowAwayPackage) {
		this.netSendThrowAwayPackage = netSendThrowAwayPackage;
	}
	public String getNetIpAddress() {
		return netIpAddress;
	}
	public void setNetIpAddress(String netIpAddress) {
		this.netIpAddress = netIpAddress;
	}
	public String getNetBroadcase() {
		return netBroadcase;
	}
	public void setNetBroadcase(String netBroadcase) {
		this.netBroadcase = netBroadcase;
	}
	public String getNetMac() {
		return netMac;
	}
	public void setNetMac(String netMac) {
		this.netMac = netMac;
	}
	public String getNetDescription() {
		return netDescription;
	}
	public void setNetDescription(String netDescription) {
		this.netDescription = netDescription;
	}
	public long getNetMtu() {
		return netMtu;
	}
	public void setNetMtu(long netMtu) {
		this.netMtu = netMtu;
	}
	
	
	public long getNetReceivePerBytes() {
		return netReceivePerBytes;
	}
	public void setNetReceivePerBytes(long netReceivePerBytes) {
		this.netReceivePerBytes = netReceivePerBytes;
	}
	public long getNetSendPerBytes() {
		return netSendPerBytes;
	}
	public void setNetSendPerBytes(long netSendPerBytes) {
		this.netSendPerBytes = netSendPerBytes;
	}
	@Override
	public String toString() {
		return "Net [netName=" + netName + ", netIpAddress=" + netIpAddress
				+ ", netMask=" + netMask + ", netMac=" + netMac
				+ ", netBroadcase=" + netBroadcase + ", netDescription="
				+ netDescription + ", netReceiveTotalPackage="
				+ netReceiveTotalPackage + ", netSendTotalPackage="
				+ netSendTotalPackage + ", netReceiveTotalBytes="
				+ netReceiveTotalBytes + ", netReceivePerBytes="
				+ netReceivePerBytes + ", netSendTotalBytes="
				+ netSendTotalBytes + ", netSendPerBytes=" + netSendPerBytes
				+ ", netReceiveErrorPackage=" + netReceiveErrorPackage
				+ ", netSendErrorPackage=" + netSendErrorPackage
				+ ", netReceiveThrowAwayPackage=" + netReceiveThrowAwayPackage
				+ ", netSendThrowAwayPackage=" + netSendThrowAwayPackage
				+ ", netMtu=" + netMtu + "]";
	}
}