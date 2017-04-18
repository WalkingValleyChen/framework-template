package com.chen.demo.spring;

import com.chen.demo.config.SpringSessionConfig;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

/**
 * @author Chenwl
 * @version 1.0.0
 * @date 2017/4/18
 */
public class Initializer extends AbstractHttpSessionApplicationInitializer {

    public Initializer() {
        super(SpringSessionConfig.class);
    }
}