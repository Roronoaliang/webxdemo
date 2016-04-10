package com.alibaba.webx.searchengine.dao.base.impl;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alibaba.webx.searchengine.dao.base.BaseDao;
import com.alibaba.webx.searchengine.factory.mybatis.MySqlSessionTemplate;

@Repository
public class BaseDaoImpl<T> implements BaseDao<T>{
	
	@Autowired
	private MySqlSessionTemplate sqlSessionWriteTemplate;	// 写
	
	@Autowired
	private MySqlSessionTemplate sqlSessionReadTemplate;	// 读
	
	@Override
	public int insert(T t) {
		return sqlSessionWriteTemplate.insert(getStatement("insert"),t);
	}

	@Override
	public int deleteById(String id) {
		return sqlSessionWriteTemplate.delete(getStatement("deleteById"),id);
	}

	@Override
	public List<T> selectAll(int offset, int limit) {
		return sqlSessionReadTemplate.selectList(getStatement("selectAll"), null, new RowBounds(offset, limit));
	}

	@Override
	public List<T> selectByOneParameter(String name, Object value, int offset, int limit) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("aaa", name);
		map.put("bbb",value);
		return sqlSessionReadTemplate.selectList(getStatement("selectByOneParameter"), map, new RowBounds(offset, limit));
	}

	@Override
	public List<T> selectByParameters(Map<String, Object> map, int offset, int limit) {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String, Object>();
        params.put("relationMap", map);
		return sqlSessionReadTemplate.selectList(getStatement("selectByParameters"), params, new RowBounds(offset, limit));
	}

	@Override
	public T selectById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateById(T t) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private String getStatement(String id){
		return getEntityClass().getName()+"Mapper"+"."+id;
	}
	
	@SuppressWarnings("unchecked")
	private Class<T> getEntityClass() {
		return (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
}