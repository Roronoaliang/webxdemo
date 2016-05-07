package com.alibaba.webx.searchengine.dao.authority;

import java.util.List;

import com.alibaba.webx.common.po.authority.Permission;
import com.alibaba.webx.searchengine.dao.base.BaseDao;

public interface PermissionMapper extends BaseDao<Permission>{
	
	/**
	 * 根据角色id获取对应的权限集合
	 * @param id
	 * @return
	 */
	public List<Permission> selectByRolesId(String id);
}
