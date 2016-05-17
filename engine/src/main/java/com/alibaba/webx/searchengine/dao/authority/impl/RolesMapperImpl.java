package com.alibaba.webx.searchengine.dao.authority.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.alibaba.webx.common.po.authority.Roles;
import com.alibaba.webx.searchengine.dao.authority.RolesMapper;
import com.alibaba.webx.searchengine.dao.base.impl.BaseDaoImpl;

@Repository
public class RolesMapperImpl extends BaseDaoImpl<Roles> implements RolesMapper{

	private static final String spaceName = "com.alibaba.webx.common.po.authority.RolesMapper";
	
	@Override
	public List<Roles> selectByUserId(String id) {
		return sqlSessionReadTemplate.selectList(spaceName+".selectByUserId",id);
	}

	@Override
	public List<Roles> selectByUserName(String userName) {
		return sqlSessionReadTemplate.selectList(spaceName+".selectByUserName",userName);
	}
}
