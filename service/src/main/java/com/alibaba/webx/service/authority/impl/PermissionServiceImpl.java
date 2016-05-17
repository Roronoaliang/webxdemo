package com.alibaba.webx.service.authority.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.webx.common.po.authority.Permission;
import com.alibaba.webx.searchengine.dao.authority.PermissionMapper;
import com.alibaba.webx.searchengine.util.log.LoggerUtils;
import com.alibaba.webx.service.authority.PermissionService;
import com.alibaba.webx.service.base.impl.BaseServiceImpl;

@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService{
	
	private static Logger log = LoggerFactory.getLogger(PermissionServiceImpl.class);
	
	@Autowired
	private LoggerUtils loggerUtils;

	@Resource
	private PermissionMapper permissionMapper;
	
	@Resource
	public void setBaseDao(PermissionMapper permissionMapper){
		super.setBaseDao(permissionMapper);
	}

	@Override
	public List<Permission> selectByRolesId(String id) {
		try {
			return permissionMapper.selectByRolesId(id);
		} catch (Exception e) {
			log.error("[PermissionServiceImpl-selectByRolesId-异常]",e);
			loggerUtils.emailError(e);
		}
		return null;
	}
}
