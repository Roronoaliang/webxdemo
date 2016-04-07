package com.alibaba.webx.searchengine.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alibaba.webx.searchengine.dao.DaoDemo;
import com.alibaba.webx.searchengine.factory.mybatis.MyBatisFactory;

@Repository
public class DaoDempImpl implements DaoDemo{
	
	@Autowired
	private MyBatisFactory myBatisFactory;

	@Override
	public void add() {
		System.out.println("database add method.");
	}

	@Override
	public void delete() {
		System.out.println("database delete method.");
	}

	@Override
	public void find() {
		System.out.println("database find method.");
	}

	@Override
	public void update() {
		System.out.println("database update method.");
	}
}