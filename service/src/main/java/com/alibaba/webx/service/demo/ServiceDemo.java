package com.alibaba.webx.service.demo;

import java.util.Map;

import com.alibaba.webx.common.po.demo.Demo;

public interface ServiceDemo {

	public void add(Demo demo);
	
	public void testTransactional(Demo demo);
	
	public void delete(String id);
	
	public void find(Demo demo);
	
	public void update(Map<String,String> map);
}
