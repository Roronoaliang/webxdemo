package com.alibaba.webx.searchengine.dao.authority;

import java.util.List;

import com.alibaba.webx.common.po.authority.Roles;
import com.alibaba.webx.searchengine.dao.base.BaseDao;

public interface RolesMapper extends BaseDao<Roles>{
	
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
