package com.alibaba.webx.service.authority;

import java.util.List;

import com.alibaba.webx.common.po.authority.Permission;
import com.alibaba.webx.service.base.BaseService;

public interface PermissionService extends BaseService<Permission> {

	/**
	 * 根据角色id获取对应的权限集合
	 * @param id
	 * @return
	 */
	public List<Permission> selectByRolesId(String id);
}
