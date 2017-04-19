package com.chen.feign.convertor;

import java.util.Map;

/**
 * object to map转换器
 * 在FormEncoder中会将object自动转为为form表单使用
 * @author Chenwl
 * @version 1.0.0
 * @date 2017/4/19
 */
public interface ObjectConvertor {

    public Map<String,Object> toMap(Object o) throws ConvertException;
}
