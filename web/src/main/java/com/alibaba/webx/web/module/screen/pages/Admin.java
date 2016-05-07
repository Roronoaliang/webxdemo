package com.alibaba.webx.web.module.screen.pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.webx.searchengine.util.log.LoggerUtils;
import com.alibaba.webx.web.module.screen.base.BaseScreen;

/**
 * 页面资源权限验证
 * 
 * @author xiaoMzjm
 *
 */
public class Admin extends BaseScreen{

	@Autowired
	private LoggerUtils loggerUtils;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private HttpServletResponse response;
	
	/**
	 * 验证http://xxx/xxx/admin/admin.html这个资源是否可以被访问
	 * 
	 * 访问地址：http://localhost:8080/topview/pages/admin/admin.do
	 * 
	 * 返回true则代表有权限
	 * 
	 * 返回false则代表无权限
	 */
	public void doAdmin(){
		System.out.println("=======Admin--doAdmin()");
		ajax200json(true);
	}
}
