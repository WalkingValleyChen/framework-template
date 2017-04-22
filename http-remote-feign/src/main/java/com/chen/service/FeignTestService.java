package com.chen.service;

import com.chen.feign.annotation.FormBean;
import com.chen.model.Demo;
import com.chen.request.Page;
import com.chen.response.BaseResponse;
import feign.Headers;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;

import java.util.Map;

/**
 * @author ValleyChen
 * @version 1.0.0
 * @time 2017/4/22
 */
public interface FeignTestService {

    @Headers("Content-Type: application/json")
    @RequestLine("POST /test1")
    public BaseResponse test1(Demo demo);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @RequestLine("POST /test2")
    public BaseResponse test2(@Param("id") int id,@Param("name")String name);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @RequestLine("POST /test2")
    public BaseResponse test3(@QueryMap Map<String,Object> param);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @RequestLine("POST /test2")
    public BaseResponse test4(@FormBean Demo demo);
}
