package com.chen.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Chenwl
 * @version 1.0.0
 * @date 2017/4/19
 */
public class JsonUtil {

    public static  boolean isArray(String json){
        if(StringUtils.isEmpty(json))
            return false;
        String first = json.substring(0, 1);
        if(first.equals("["))
            return true;
        return false;
    }
}
