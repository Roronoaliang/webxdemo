package com.alibaba.webx.web.module.pipeline.shiro.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.webx.common.factory.log.LoggerFactory;
import com.alibaba.webx.common.vo.result.Result;

/**
 * 
 * @author xiaoMzjm
 *
 */
public class BaseFilter {
	
	private static Logger log = LoggerFactory.getLogger(BaseFilter.class);

	/**
     * 获取客户端IP
     * @param request
     * @return
     */
	public static String getIpAddr(HttpServletRequest request) {
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
    	return ip;
	}
	
	/**
	 * 根据Object输出JSON，返回null
	 * @param jsonMap 需要以json形式输出的Map<String,Object>对象
	 * @return null
	 */
	public static void ajax200json(HttpServletRequest request , HttpServletResponse response , Object data){
		Result result = new Result();
		result.setStatus(200);
		result.setData(data);
		ajax(request , response,JSONObject.toJSONString(result), "text/html"); 
	}
	
	/**
	 * 登录成功
	 * @param response
	 * @param message
	 */
	public static void ajax250json(HttpServletRequest request , HttpServletResponse response) {
		Result result = new Result();
		result.setStatus(250);
		ajax(request , response,JSONObject.toJSONString(result), "text/html"); 
	}
	
	/**
	 * 登出成功
	 * @param response
	 * @param message
	 */
	public static void ajax251json(HttpServletRequest request , HttpServletResponse response) {
		Result result = new Result();
		result.setStatus(251);
		ajax(request , response,JSONObject.toJSONString(result), "text/html"); 
	}
	
	/**
	 * 权限不足
	 * @param message
	 * @return
	 */
	public static void ajax401json(HttpServletRequest request , HttpServletResponse response , String message) {
		Result result = new Result();
		result.setStatus(401);
		result.setMessage(message);
		ajax(request , response,JSONObject.toJSONString(result), "text/html"); 
	}
	
	/**
	 * 账号或密码错误
	 * @param response
	 * @param message
	 */
	public static void ajax403json(HttpServletRequest request , HttpServletResponse response , String message) {
		Result result = new Result();
		result.setStatus(403);
		result.setMessage(message);
		ajax(request , response,JSONObject.toJSONString(result), "text/html"); 
	}
	
	/**
	 * 未登录
	 * @param response
	 * @param message
	 */
	public static void ajax450json(HttpServletRequest request , HttpServletResponse response , String message) {
		Result result = new Result();
		result.setStatus(450);
		result.setMessage(message);
		ajax(request , response,JSONObject.toJSONString(result), "text/html"); 
	}
	
	/**
	 * 验证码错误
	 * @param response
	 * @param message
	 */
	public static void ajax451json(HttpServletRequest request , HttpServletResponse response , String message) {
		Result result = new Result();
		result.setStatus(451);
		result.setMessage(message);
		ajax(request , response,JSONObject.toJSONString(result), "text/html"); 
	}
	
	/**
	 * 告诉请求者需要验证码验证
	 * @param response
	 * @param message
	 */
	public static void ajax452json(HttpServletRequest request , HttpServletResponse response , String message) {
		Result result = new Result();
		result.setStatus(452);
		result.setMessage(message);
		ajax(request , response,JSONObject.toJSONString(result), "text/html"); 
	}
	
	/**
	 * 根据message输出JSON，返回null
	 * @param message
	 * @return
	 */
	public static void ajax500json(HttpServletRequest request , HttpServletResponse response , String message) {
		Result result = new Result();
		result.setStatus(500);
		result.setMessage(message);
		ajax(request,response,JSONObject.toJSONString(result), "text/html"); 
	}
	
	
	/**
	 *  AJAX输出，返回null
	 * @param content 需要传出去的内容
	 * @param type Ajax输出类型 ： 1、"text/plain"(输出文本)	<br> 
	 * 							 2、text/html(输出HTML)		<br> 
	 * 							 3、text/xml(输出XML)
	 * @return null
	 */
	private static void ajax(HttpServletRequest request , HttpServletResponse response , String content, String type) {
		try {
			response.setContentType(type + ";charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			crossDomain(request,response);
			response.setDateHeader("Expires", 0);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			log.error("[Ajax信息输出]异常信息 ： ", e);
		}
	}
	
	/**
	 * 跨域请求设置
	 */
	public static void crossDomain(HttpServletRequest request , HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		response.setHeader("Access-Control-Allow-Credentials", "true");
		if(request.getMethod().equalsIgnoreCase("OPTIONS")) {
			response.setHeader("Access-Control-Allow-Methods", request.getHeader("Access-Control-Request-Method"));
			response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
        } else {
        	response.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,PATCH,DELETE");
        	response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        }
	}
}
