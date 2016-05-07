package com.alibaba.webx.service.authority.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.webx.common.po.authority.Roles;
import com.alibaba.webx.searchengine.dao.authority.RolesMapper;
import com.alibaba.webx.searchengine.util.log.LoggerUtils;
import com.alibaba.webx.service.authority.RolesService;
import com.alibaba.webx.service.base.impl.BaseServiceImpl;

@Service
public class RolesServiceImpl extends BaseServiceImpl<Roles> implements RolesService{

	private static Logger log = LoggerFactory.getLogger(RolesServiceImpl.class);
	
	@Autowired
	private LoggerUtils loggerUtils;
	
	@Resource
	private RolesMapper rolesMapper;
	
	@Resource
	public void setBaseDao(RolesMapper rolesMapper){
		super.setBaseDao(rolesMapper);
	}

	public List<Roles> selectByUserId(String id) {
		return rolesMapper.selectByUserId(id);
	}

	@Override
	public List<Roles> selectByUserName(String userName) {
		try {
			return rolesMapper.selectByUserName(userName);
		} catch (Exception e) {
			log.error("[RolesServiceImpl-selectByUserName-异常]",e);
			loggerUtils.emailError(e);
		}
		return null;
	}
}
