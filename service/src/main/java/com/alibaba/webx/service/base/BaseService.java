package com.alibaba.webx.service.base;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author xiaoMzjm
 *
 * @param <T>
 */
public interface BaseService<T> {

	/**
	 * 插入一个对象
	 * @param t	要插入的对象
	 * @return
	 */
	public int insert(T t);
	
	/**
	 * 根据主键删除某行记录
	 * @param id
	 * @return
	 */
	public int deleteById(Object id);
	
	/**
	 * 返回表中所有记录
	 * @param pageNum	第几页			>=1
	 * @param count		每页显示几天记录	>0
	 * @return
	 */
	public List<T> selectAll(int offset, int limit);
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	public T selectById(Object id);
	
	/**
	 * 根据某个属性参数记录
	 * @param name		属性名
	 * @param value		属性值
	 * @param pageNum	第几页			>=1
	 * @param count		每页显示几天记录	>0
	 * @return
	 */
	public List<T> selectByOneParameter(String name , Object value , int offset, int limit);
	
	/**
	 * 根据某些属性做and查询
	 * @param map		属性集合
	 * @param pageNum	第几页			>=1
	 * @param count		每页显示几天记录	>0
	 * @return
	 */
	public List<T> selectByParameters(Map<String,Object> ma , int offset, int limit);
	
	/**
	 * 根据id删修改某行记录
	 * @param t
	 * @return
	 */
	public int updateById(T t);
}
