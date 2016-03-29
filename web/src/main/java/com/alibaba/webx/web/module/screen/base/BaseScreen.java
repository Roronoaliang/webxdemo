package com.alibaba.webx.web.module.screen.base;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.webx.common.util.md5.MD5Encryption;
import com.alibaba.webx.searchengine.factory.redis.DefaultRedisHandler;
import com.alibaba.webx.searchengine.factory.redis.RedisFactory;

/**
 * Screen常用方法封装
 * 
 * @author xiaoMzjm
 */
public class BaseScreen {

	/**
     * 保存session信息到redis中
     * @param session
     */
    public void saveSession(HttpSession session , int expire ){
    	StringBuilder sb = new StringBuilder();
    	Enumeration<String> enumeration = session.getAttributeNames();
    	while(enumeration.hasMoreElements()) {
    		String name = enumeration.nextElement();
    		String value = (String) session.getAttribute(name);
    		if(!"token".equals(name)){
    			sb.append(name+"$%^&="+value+"$%^&;");
    		}
    	}
    	String str = sb.toString();
    	String sessionId = MD5Encryption.getMD5(session.getId());
    	try {
    		DefaultRedisHandler  d = RedisFactory.getDefaultRedisHandler();
    		d.add(sessionId, str , expire);
    		d.returnResource();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
   
    /**
     * 从redis中获取session
     * @param sessionId
     * @return
     */
    public HttpSession getSessionBySessionIdFromRedis(HttpServletRequest request , String sessionId){
    	try {
    		DefaultRedisHandler d = RedisFactory.getDefaultRedisHandler();
			String sessionInfo = d.get(sessionId);
			d.returnResource();
			if(StringUtils.isNotBlank(sessionInfo)) {
				HttpSession session = request.getSession();
				String[] strs = sessionInfo.split("\\$%\\^&;");
				for(String str : strs) {
					String[] ss = str.split("\\$%\\^&=");
					if(ss != null && ss.length == 2)  {
						String name = ss[0];
						String value = ss[1];
//						System.out.println("session.setAttribute : "+ "name="+name+" , value="+value);
						session.setAttribute(name, value);
					}
				}
				return session;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /**
     * 从redis中删除session
     * @param sessionId
     */
    public void deleteSession(String sessionId) {
    	if(StringUtils.isNotBlank(sessionId)){
    		try {
    			DefaultRedisHandler d = RedisFactory.getDefaultRedisHandler();
    			d.delete(sessionId);
    			d.returnResource();
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    }
    
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
