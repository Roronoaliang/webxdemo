package com.alibaba.webx.searchengine.dao;

import java.util.List;
import java.util.Map;

import com.alibaba.webx.common.po.demo.Demo;

public interface DaoDemo {

	public void add(Demo demo);
	
	public void delete(String id);
	
	public List<Demo> find();
	
	public void update(Map<String,String> map);
}
