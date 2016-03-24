package com.alibaba.webx.searchengine.factory.mybatis;

/**
 * 简介：
 * 		和数据源类型选择相关的类
 * 
 * 工作原理：
 * 		本类被DynamicDataSource引用，DynamicDataSource将会调用本类的getDbType()方法获得用户对数据源的选择。
 * 		用户可以调用setDbType()方法设置数据源。
 * 		数据源类型暂时写死两个，一个是写数据源类型为DB_TYPE_RW，一个数读数据源类型为DB_TYPE_R。
 * 
 * @author xiaoMzjm
 *
 */
public class DBContextHolder {
	
	// 线程ThreadLocal，用于保存跟本线程相关的数据源类型的选择的变量，默认为DB_TYPE_RW
    private static ThreadLocal<String> threadLocal = new ThreadLocal<String>(){
    	@Override
    	protected String initialValue(){
    		return DB_TYPE_RW;
    	}
    };  
  
    public static final String DB_TYPE_RW = "dataSourceKeyRW";  	// 写数据源
    public static final String DB_TYPE_R = "dataSourceKeyR";  		// 读数据源
  
    /**
     * 获取数据源类型
     * @return 数据源类型字符串标志
     */
    public static String getDbType() {  
        String db = threadLocal.get();  
        if (db == null) {  
            db = DB_TYPE_RW;// 默认是写库  
        }  
        return db;  
    }  
  
    /** 
     * 设置本线程的数据源类型
     * @param str 数据源类型字符串标志
     */  
    public static void setDbType(String str) {  
    	threadLocal.set(str);  
    }  
  
    /**
     * 清楚本线程的数据源类型标志，防止内存泄露使用 
     */
    public static void clearDBType() {  
    	threadLocal.remove();  
    }  
}