package com.chen.demo.controller;

import com.chen.demo.entity.Demo;
import com.chen.demo.service.DemoService;
import com.chen.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public Demo get(@PathVariable("id") int id){
        return demoService.get(id);
    }

    @ResponseBody
    @RequestMapping(value = "/add")
    public BaseResponse add(@Valid Demo demo){
        demoService.insert(demo);
        return BaseResponse.getSuccessResponse("插入成功");
    }
}
