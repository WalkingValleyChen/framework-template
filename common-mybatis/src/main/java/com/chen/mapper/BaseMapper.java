package com.chen.mapper;

public interface BaseMapper<T> {
	public T selectByPrimaryKey(int id);

	public int insert(T t);

	public int insertSelective(T t);

	public int deleteByPrimaryKey(int id);

	public int updateByPrimaryKeySelective(T t);

	public int updateByPrimaryKey(T t);
}