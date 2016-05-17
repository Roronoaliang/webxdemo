package com.alibaba.webx.web.module.screen.login;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.webx.common.po.authority.User;
import com.alibaba.webx.service.authority.UserService;
import com.alibaba.webx.web.module.screen.base.BaseScreen;

public class Login extends BaseScreen{
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private UserService userServiceImpl;

	/**
	 * 登录成功
	 * 
	 * shiro和webx各自维护自己的session，shiro的session在webx中获取不到，webx的session在shiro中也获取不到，
	 * 假如我们在shrio中登录后把用户对象放在session中的话，去到webx层是拿不到的，
	 * 一个解决的方法是在webx层再一次根据用户名重新查询数据库获取用户对象，放进session。
	 */
	public void doLogin() {
		String userName = request.getParameter("username");
		List<User> userList = userServiceImpl.selectByOneParameter("userName", userName, 0, 1);
		if(CollectionUtils.isNotEmpty(userList)) {
			request.getSession().setAttribute("user", userList.get(0));
			ajax250json();
		}
		else {
			ajax403json("账号或密码错误");
		}
	}
	
	/**
	 * 登出
	 */
	public void doLogout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		ajax251json();
	}
}
