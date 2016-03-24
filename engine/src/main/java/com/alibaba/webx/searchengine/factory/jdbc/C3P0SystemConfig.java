package com.alibaba.webx.searchengine.factory.jdbc;

import java.util.ResourceBundle;

/** 
 * 读取配置文件 
 */  
public class C3P0SystemConfig {  
      
    static String configFile = "c3p0/c3p0jdbc";//根据具体配置文件名称配置  
      
    public static String getConfigInfomation(String itemIndex) {  
           try {  
               ResourceBundle resource = ResourceBundle.getBundle(configFile);  
               return resource.getString(itemIndex);  
           } catch (Exception e) {  
               return"";  
           }  
        }  
}
