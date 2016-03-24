package com.alibaba.webx.searchengine.factory.mybatis;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 简介：
 * 		动态数据源
 * 
 * 工作原理：
 * 		本类继承	AbstractRoutingDataSource，重写其对数据源类型的选择的方法，从我们自己写的DBContextHolder获取用户选择的数据源
 * 		SqlSessionFactoryBean类将会从AbstractRoutingDataSource的相关接口determineCurrentLookupKey（）获取到数据源的类型，从而确定数据源	
 * 
 * @author xiaoMzjm
 */
public class DynamicDataSource extends AbstractRoutingDataSource{
  
	/**
	 * 返回数据源的类型
	 */
    @Override  
    protected Object determineCurrentLookupKey() {  
        return DBContextHolder.getDbType();  
    } 
}
