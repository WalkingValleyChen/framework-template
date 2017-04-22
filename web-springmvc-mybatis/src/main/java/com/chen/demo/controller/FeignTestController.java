package com.chen.demo.controller;

import com.chen.demo.entity.Demo;
import com.chen.request.Page;
import com.chen.response.BaseResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ValleyChen
 * @version 1.0.0
 * @time 2017/4/22
 */
@Controller
public class FeignTestController {

    @ResponseBody
    @RequestMapping(value = "/test1", method = RequestMethod.POST)
    public BaseResponse test1(@RequestBody Demo demo) {
        return BaseResponse.SUCCESS_RESPONSE;
    }

    @ResponseBody
    @RequestMapping(value = "/test2", method = RequestMethod.POST)
    public BaseResponse test2(Demo demo) {
        return BaseResponse.SUCCESS_RESPONSE;
    }
}
