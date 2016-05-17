package com.alibaba.webx.service.authority;

import com.alibaba.webx.common.po.authority.User;
import com.alibaba.webx.service.base.BaseService;

public interface UserService extends BaseService<User> {
	
	public User selectByNameFromCache(String userName);

}
