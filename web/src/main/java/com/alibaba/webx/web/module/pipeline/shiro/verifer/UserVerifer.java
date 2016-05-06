package com.alibaba.webx.web.module.pipeline.shiro.verifer;

import com.alibaba.webx.common.po.authority.User;

/**
 * 用户名&密码验证器。
 * 
 * 根据项目密码的加密方式，自己可以自定义其他实现。
 * 
 * @author xiaoMzjm
 *
 */
public interface UserVerifer {

	/**
	 * 根据用户名和密码获取用户对象
	 * @param userName
	 * @param password
	 * @return
	 */
	public User getUser(String userName , String password);
}
