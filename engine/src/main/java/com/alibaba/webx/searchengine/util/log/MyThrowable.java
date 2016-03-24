package com.alibaba.webx.searchengine.util.log;

public class MyThrowable {

	// 日期
	private String data;
	
	// 具体错误
	private Throwable throwable;

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
