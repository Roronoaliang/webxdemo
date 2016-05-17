package com.alibaba.webx.common.po.demo;

import java.io.Serializable;

public class Demo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	
	public Demo() {
	}
	
	public Demo(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Demo [id=" + id + ", name=" + name + "]";
	}

}