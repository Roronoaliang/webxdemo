package com.alibaba.webx.web.module.screen.base;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.webx.searchengine.util.log.LoggerUtils;
import com.alibaba.webx.web.module.pipeline.shiro.filter.BaseFilter;

/**
 * Screen常用方法封装
 * 
 * @author xiaoMzjm
 */
public class BaseScreen {
	
	private static Logger log = LoggerFactory.getLogger(BaseScreen.class);
	
	@Autowired
	private LoggerUtils loggerUtils;
	
	@Autowired
	private HttpServletRequest request;

	@Autowired
	private HttpServletResponse response;
    
	/**
	 * 文件下载
	 * @param fileName 需要下载的文件的名字（需要先转成ISO8859-1编码，防止乱码问题出现）
	 * @param fileInputStream
	 * @return
	 */
	public String ajaxdownLoad(String fileName, InputStream fileInputStream) {
		try {
			response.setContentType( "application/octet-stream;charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);  
			BaseFilter.crossDomain(request, response);
			response.setDateHeader("Expires", 0);
			//得到响应的输出流  即向客户端输出信息的输出流。  
			ServletOutputStream servletOutputStream = response.getOutputStream();  
			byte[] b = new byte[1024];  
			int len;  
			
			while((len=fileInputStream.read(b)) >0) {
				servletOutputStream.write(b,0,len);
			}
			response.setStatus(HttpServletResponse.SC_OK );  
			response.flushBuffer();  
			servletOutputStream.close();  
			fileInputStream.close();  
		} catch (IOException e) {
			log.error("[文件下载]异常信息 ：", e);
			loggerUtils.emailError(e);
		}
		return null;
	}
	
	/**
	 * 设置页面不缓存
	 */
	public void setResponseNoCache() {
		response.setHeader("progma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0);
	}
	
	/**
	 * 根据Object输出JSON，返回null
	 * @param jsonMap 需要以json形式输出的Map<String,Object>对象
	 */
	public void ajax200json(Object data){
		BaseFilter.ajax200json(request, response, data);
	}
	
	/**
	 * 登录成功
	 * @param response
	 * @param message
	 */
	public void ajax250json() {
		BaseFilter.ajax250json(request, response);
	}
	
	/**
	 * 登出成功
	 * @param response
	 * @param message
	 */
	public void ajax251json() {
		BaseFilter.ajax251json(request, response);
	}
	
	/**
	 * 权限不足
	 * @param message
	 * @return
	 */
	public void ajax401json(String message) {
		BaseFilter.ajax401json(request, response,message);
	}
	
	/**
	 * 账号或密码错误
	 * @param message
	 */
	public void ajax403json(String message) {
		BaseFilter.ajax403json(request, response, message);
	}
	
	/**
	 * 服务器出错
	 * @param message
	 * @return
	 */
	public void ajax500json(String message) {
		BaseFilter.ajax500json(request, response,message);
	}
}