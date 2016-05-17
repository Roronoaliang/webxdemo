package com.alibaba.webx.searchengine.dao.authority.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.alibaba.webx.common.po.authority.Permission;
import com.alibaba.webx.searchengine.dao.authority.PermissionMapper;
import com.alibaba.webx.searchengine.dao.base.impl.BaseDaoImpl;

@Repository
public class PermissionMapperImpl extends BaseDaoImpl<Permission> implements PermissionMapper{

	private static final String spaceName = "com.alibaba.webx.common.po.authority.PermissionMapper";
	
	@Override
	public List<Permission> selectByRolesId(String id) {
		return sqlSessionReadTemplate.selectList(spaceName+".selectByRolesId",id);
	}
}
