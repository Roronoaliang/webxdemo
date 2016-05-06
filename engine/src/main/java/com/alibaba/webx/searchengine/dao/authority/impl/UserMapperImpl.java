package com.alibaba.webx.searchengine.dao.authority.impl;

import org.springframework.stereotype.Repository;

import com.alibaba.webx.common.po.authority.User;
import com.alibaba.webx.searchengine.dao.authority.UserMapper;
import com.alibaba.webx.searchengine.dao.base.impl.BaseDaoImpl;

@Repository
public class UserMapperImpl extends BaseDaoImpl<User> implements UserMapper{
}
