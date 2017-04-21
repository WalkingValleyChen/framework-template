package com.chen.service;


import com.chen.feign.annotation.Form;
import com.chen.model.Demo;
import com.chen.response.BaseResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * @author Chenwl
 * @version 1.0.0
 * @create 2017-01-20
 */
public interface DemoRemoteService {


    @RequestLine("POST /get/{id}")
    public Demo get(@Param("id") int id) ;

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @RequestLine("POST /add")
    public BaseResponse add(@Form Demo demo, @Param("aaa") int aaa);
}
