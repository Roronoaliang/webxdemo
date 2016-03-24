package com.alibaba.webx.service.demo;

import com.alibaba.webx.common.po.demo.Demo;

public interface ServiceDemo {

	public void add(Demo demo);
	
	public void delete(Demo demo);
	
	public void find(Demo demo);
	
	public void update(Demo demo);
}
