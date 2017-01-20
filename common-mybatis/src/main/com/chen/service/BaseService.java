package com.chen.service;

import com.chen.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseService<T> {

    @Autowired
    private BaseMapper<T> baseMapper;

    public T get(int id) {
        return baseMapper.selectByPrimaryKey(id);
    }

    public int insert(T t) {
        return baseMapper.insert(t);
    }

    public int insertSelective(T t) {
        return baseMapper.insertSelective(t);
    }

    public int delete(int id) {
        return baseMapper.deleteByPrimaryKey(id);
    }

    public int updateSelective(T t) {
        return baseMapper.updateByPrimaryKeySelective(t);
    }

    public int update(T t) {
        return baseMapper.updateByPrimaryKey(t);
    }
}