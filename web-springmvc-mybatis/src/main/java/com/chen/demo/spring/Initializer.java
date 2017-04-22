package com.chen.demo.spring;

import com.chen.demo.config.SpringSessionConfig;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

/**
 * 初始化redission +spring session 配置
 * @author Chenwl
 * @version 1.0.0
 * @date 2017/4/18
 */
public class Initializer extends AbstractHttpSessionApplicationInitializer {

    public Initializer() {
        super(SpringSessionConfig.class);
    }
}