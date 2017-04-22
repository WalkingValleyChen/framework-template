package com.chen.mapper;

/**
 * 通用mybatis mapper，使用mybatis generator生成的mapper.xml会有这些方法
 * @param <T>
 */
public interface BaseMapper<T> {
	public T selectByPrimaryKey(int id);

	public int insert(T t);

	public int insertSelective(T t);

	public int deleteByPrimaryKey(int id);

	public int updateByPrimaryKeySelective(T t);

	public int updateByPrimaryKey(T t);
}