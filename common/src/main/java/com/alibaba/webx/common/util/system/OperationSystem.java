package com.alibaba.webx.common.util.system;

import java.util.List;

/**
 * 操作系统信息
 * 
 * @author xiaoMzjm
 *
 */
public class OperationSystem {

	private String systemUserName;		// 计算机名称			如：zjm
	private String systemArchitecture;	// 系统架构			如：x86、x64
	private String systemCpuEndian;		// 大小端			如：little
	private String systemDigits;		// 系统位数			如：64
	private String systemDescribe;		// 操作系统描述		如：Microsoft Windows 7
	private String systemSeller;		// 系统厂商			如：Microsoft
	private String systemVersion;		// 操作系统版本号		如：6.2
	private List<OperationSystemUser> operationSystemUserList;	// 系统用户
	
	
	public String getSystemUserName() {
		return systemUserName;
	}
	public void setSystemUserName(String systemUserName) {
		this.systemUserName = systemUserName;
	}
	public String getSystemArchitecture() {
		return systemArchitecture;
	}
	public void setSystemArchitecture(String systemArchitecture) {
		this.systemArchitecture = systemArchitecture;
	}
	public String getSystemCpuEndian() {
		return systemCpuEndian;
	}
	public void setSystemCpuEndian(String systemCpuEndian) {
		this.systemCpuEndian = systemCpuEndian;
	}
	public String getSystemDigits() {
		return systemDigits;
	}
	public void setSystemDigits(String systemDigits) {
		this.systemDigits = systemDigits;
	}
	public String getSystemDescribe() {
		return systemDescribe;
	}
	public void setSystemDescribe(String systemDescribe) {
		this.systemDescribe = systemDescribe;
	}
	public String getSystemSeller() {
		return systemSeller;
	}
	public void setSystemSeller(String systemSeller) {
		this.systemSeller = systemSeller;
	}
	public String getSystemVersion() {
		return systemVersion;
	}
	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}
	public List<OperationSystemUser> getOperationSystemUserList() {
		return operationSystemUserList;
	}
	public void setOperationSystemUserList(
			List<OperationSystemUser> operationSystemUserList) {
		this.operationSystemUserList = operationSystemUserList;
	}
	@Override
	public String toString() {
		return "OperationSystem [systemUserName=" + systemUserName
				+ ", systemArchitecture=" + systemArchitecture
				+ ", systemCpuEndian=" + systemCpuEndian + ", systemDigits="
				+ systemDigits + ", systemDescribe=" + systemDescribe
				+ ", systemSeller=" + systemSeller + ", systemVersion="
				+ systemVersion + ", operationSystemUserList="
				+ operationSystemUserList + "]";
	}
	
	
}
