package com.alibaba.webx.searchengine.util.systemmonitor;

/**
 * 
 * 邮件内容实体类
 * 
 * @author xiaoMzjm
 *
 */
public class SystemMonitorBean {

	private String date;			// 日期
	private String message;			// 信息
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "SystemMonitorBean [date=" + date + ", message=" + message + "]";
	}
	public SystemMonitorBean(String date, String message) {
		super();
		this.date = date;
		this.message = message;
	}
	
	
}
