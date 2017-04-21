package com.chen.feign.convertor;

import com.chen.feign.coder.Coder;
import com.chen.util.JsonUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

/**
 * @author Chenwl
 * @version 1.0.0
 * @date 2017/4/19
 */
public class GsonObjectConvertor implements ObjectConvertor {

    private static GsonObjectConvertor instatnce=new GsonObjectConvertor();

    private GsonObjectConvertor() {
    }

    public static ObjectConvertor getInstance() {
        return instatnce;
    }

    @Override
    public Map<String, Object> toMap(Object o) throws ConvertException {
        if (o == null)
            return null;
        Gson gson = Coder.GSON;
        String json = gson.toJson(o);
        if (!JsonUtil.isArray(json)) {
            return gson.fromJson(json, new TypeToken<Map<String, Object>>() {
            }.getType());
        }else {
            throw new ConvertException();
        }
    }
}
