package com.alibaba.webx.searchengine.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alibaba.webx.common.po.demo.Demo;
import com.alibaba.webx.searchengine.dao.DaoDemo;
import com.alibaba.webx.searchengine.factory.mybatis.MyBatisFactory;
import com.alibaba.webx.searchengine.factory.mybatis.MySqlSessionTemplate;

@Repository
public class DaoDempImpl implements DaoDemo{
	
	@Autowired
	private MyBatisFactory myBatisFactory;
	
	@Autowired
	private MySqlSessionTemplate sqlSessionWriteTemplate;
	
	@Autowired
	private MySqlSessionTemplate sqlSessionReadTemplate;

	@Override
	public void add(Demo demo) {
		System.out.println("database add method.");
		sqlSessionWriteTemplate.insert("DemoMapper.insert", demo);
	}

	@Override
	public void delete(String id) {
		System.out.println("database delete method.");
		sqlSessionWriteTemplate.delete("DemoMapper.delete", id);
	}

	@Override
	public List<Demo> find() {
		System.out.println("database find method.");
		return sqlSessionReadTemplate.selectList("DemoMapper.get");
	}

	@Override
	public void update(Map<String,String> map) {
		System.out.println("database update method.");
		sqlSessionWriteTemplate.update("DemoMapper.update", map);
	}
}