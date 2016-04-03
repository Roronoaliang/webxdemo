package com.alibaba.webx.searchengine.util.log;

public class MyThrowable {

	
	private String data;			// 日期
	private Throwable throwable;	// 具体错误

	public MyThrowable(String data, Throwable throwable) {
		super();
		this.data = data;
		this.throwable = throwable;
	}

	public String getData() {
		return "【"+data+"】";
	}

	public void setData(String data) {
		this.data = data;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}
}
