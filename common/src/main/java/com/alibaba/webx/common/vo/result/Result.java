package com.alibaba.webx.common.vo.result;

import java.io.Serializable;

/**
 * 返回给前端/客户端的统一格式的结果
 * 
 * @author xiaoMzjm
 *
 */
public class Result implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int status;			// 状态码
	private String message;		// 错误信息
	private Object data;		// 数据
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}