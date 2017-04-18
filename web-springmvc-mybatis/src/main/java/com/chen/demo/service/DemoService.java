package com.chen.demo.service;

import com.chen.demo.entity.Demo;
import com.chen.service.BaseService;
import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author Chenwl
 * @version 1.0.0
 * @create 2017-01-20
 */
@Service
public class DemoService extends BaseService<Demo>{

    @Cacheable(value = "testMap")
    public int test(){
        System.out.println("get One");
        return 1;
    }
}
