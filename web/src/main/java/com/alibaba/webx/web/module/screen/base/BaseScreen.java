package com.alibaba.webx.web.module.screen.base;

import javax.servlet.http.HttpServletRequest;

/**
 * Screen常用方法封装
 * 
 * @author xiaoMzjm
 */
public class BaseScreen {
    
    /**
     * 获取客户端IP
     * @param request
     * @return
     */
	public String getIpAddr(HttpServletRequest request) {
    	String ip = request.getHeader("x-forwarded-for");
    	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
    	ip = request.getHeader("Proxy-Client-IP");
    	}
    	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
    	ip = request.getHeader("WL-Proxy-Client-IP");
    	}
    	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
    	ip = request.getRemoteAddr();
    	}
//    	System.out.println("BaseScreen.java ip="+ip);
    	return ip;
	}
}
