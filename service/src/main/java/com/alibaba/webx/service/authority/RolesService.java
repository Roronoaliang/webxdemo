package com.alibaba.webx.service.authority;

import java.util.List;

import com.alibaba.webx.common.po.authority.Roles;
import com.alibaba.webx.service.base.BaseService;

public interface RolesService extends BaseService<Roles> {

	/**
	 * 查，根据用户id
	 * @param id
	 * @return
	 */
	public List<Roles> selectByUserId(String id);
	
	/**
	 * 查，根据用户名
	 * @param id
	 * @return
	 */
	public List<Roles> selectByUserName(String userName);
}
