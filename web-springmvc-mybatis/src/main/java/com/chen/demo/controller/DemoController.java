package com.chen.demo.controller;

import com.chen.demo.entity.Demo;
import com.chen.demo.service.DemoService;
import com.chen.demo.service.Tester;
import com.chen.response.BaseResponse;
import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author Chenwl
 * @version 1.0.0
 * @create 2017-01-20
 */
@Controller
public class DemoController {

    @Autowired
    private DemoService demoService;

    @ResponseBody
    @RequestMapping(value = "/get/{id}")
    @ApiOperation(value = "demo获取", httpMethod = "GET", response = Demo.class, notes = "get demo")
    public Demo get(@PathVariable("id") int id, HttpSession session) {
        session.setAttribute("test",demoService.test());
        return demoService.get(id);
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "demoAPI添加", httpMethod = "POST", response = BaseResponse.class, notes = "add demo")
    public BaseResponse add(@Valid Demo demo) {
        demoService.insert(demo);
        return BaseResponse.getSuccessResponse("插入成功");
    }
}
