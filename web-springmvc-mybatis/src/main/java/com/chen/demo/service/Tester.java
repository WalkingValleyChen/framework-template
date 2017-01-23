package com.chen.demo.service;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import org.springframework.stereotype.Component;

/**
 * @author Chenwl
 * @version 1.0.0
 * @create 2017-01-23
 */
@Component
public class Tester {

    @Timed
    public int  test(){
        return 1;
    }
}
