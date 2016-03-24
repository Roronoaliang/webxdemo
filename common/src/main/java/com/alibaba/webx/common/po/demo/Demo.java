package com.alibaba.webx.common.po.demo;

import java.io.Serializable;

public class Demo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Demo [id=" + id + "]";
	}
}