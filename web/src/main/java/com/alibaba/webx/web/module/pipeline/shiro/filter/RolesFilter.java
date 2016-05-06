package com.alibaba.webx.web.module.pipeline.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

/**
 * 角色过滤器
 * 
 * @author xiaoMzjm
 *
 */
public class RolesFilter extends AccessControlFilter {
	
	/**
	 * 验证是否有角色
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request,ServletResponse response, Object mappedValue) throws Exception {
		String[] roles = (String[]) mappedValue;
		
		// 该资源不需要角色限制
		if (roles == null) {
			return true;
		}
		
		// 该资源需要特定角色才能访问
		for (String role : roles) {
			
			// 该用户有该角色
			if (getSubject(request, response).hasRole(role)) {
				return true;
			}
			
			// 该用户没有该角色
			else {
				return false;
			}
		}
		return false;
	}

	/**
	 * 没角色的处理逻辑
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		
		Subject subject = getSubject(request, response);
		
		// 没有登录
		if (subject.getPrincipal() == null) {
			BaseFilter.ajax450json((HttpServletRequest)request , (HttpServletResponse)response, "请先登录账号");
		} 
		
		// 已登录
		else {
			BaseFilter.ajax401json((HttpServletRequest)request , (HttpServletResponse)response, "权限不足");
		}
		return false;
	}
}
