package com.alibaba.webx.web.module.pipeline.shiro.verifer.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.webx.common.po.authority.User;
import com.alibaba.webx.service.authority.UserService;
import com.alibaba.webx.web.module.pipeline.shiro.verifer.UserVerifer;

/**
 * 
 * @author xiaoMzjm
 *
 */
@Component("Sha256UserVeriferImpl")
public class Sha256UserVeriferImpl implements UserVerifer{

	@Autowired
	private UserService userService;
	
	@Override
	public User getUser(String userName, String password) {
		List<User> userList = userService.selectByOneParameter("userName", userName, 0, 1);
		if(CollectionUtils.isNotEmpty(userList)) {
			User user = userList.get(0);
			String sha256Password = new Sha256Hash(password,user.getSalt(),2).toString();
			if(sha256Password.equals(user.getPassword())) {
				return user;
			}
			else {
				return null;
			}
		}
		return null;
	}

}
