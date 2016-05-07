package com.alibaba.webx.test.engine.base;

import java.lang.reflect.ParameterizedType;

import org.slf4j.Logger;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.alibaba.webx.common.factory.log.LoggerFactory;

public class EngineBaseTest<T1,T2> {

	protected Logger log = LoggerFactory.getLogger(getEntityClass());
	
	private static FileSystemXmlApplicationContext fsxac;
	
	public T2 target;
	
	
	@SuppressWarnings("unchecked")
	public void initTarget(String beanId) {
		if(target == null) {
			String[] strs = new String[]{"src/main/webapp/WEB-INF/biz/*.xml"};
	    	fsxac = new FileSystemXmlApplicationContext(strs);
	    	target = (T2) fsxac.getBean(beanId);
		}
    }
	
	@SuppressWarnings("unchecked")
	private Class<T1> getEntityClass() {
		return (Class<T1>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
}
