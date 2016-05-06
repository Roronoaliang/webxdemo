package com.alibaba.webx.web.module.pipeline.shiro.verifer.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
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
@Component("Md5UserVeriferImpl")
public class Md5UserVeriferImpl implements UserVerifer{

	@Autowired
	private UserService userService;
	
	@Override
	public User getUser(String userName, String password) {
		User user = userService.selectByNameFromCache(userName);
		if(user != null) {
			String md5Password = new Md5Hash(password,user.getSalt(),2).toString();
			if(md5Password.equals(user.getPassword())) {
				return user;
			}
			else {
				return null;
			}
		}
		return null;
	}

}
