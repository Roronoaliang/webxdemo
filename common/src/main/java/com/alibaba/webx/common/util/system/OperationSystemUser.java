package com.alibaba.webx.common.util.system;

/**
 * 系统用户
 * 
 * @author xiaoMzjm
 *
 */
public class OperationSystemUser {
	
	private String device;		// 用户使用的设备		如：console
	private String hostName;	// 计算机名			如：ZJM
	private String userName;	// 用户名			如：xiaoMzjm，在计算机管理-本地用户和组-用户中可以查倒
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Override
	public String toString() {
		return "OperationSystemUser [device=" + device + ", hostName="
				+ hostName + ", userName=" + userName + "]";
	}
	
	
}
