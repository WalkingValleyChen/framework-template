package com.chen.demo.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.redisson.spring.session.config.EnableRedissonHttpSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * redisson spring session在AbstractHttpSessionApplicationInitializer
 * 中创建独立的上下文AnnotationConfigWebApplicationContext加载本配置
 * 所以redis的配置不是注入的，而是读取classpath下的redis.yaml文件
 *
 * @author Chenwl
 * @version 1.0.0
 * @date 2017/4/18
 */
@EnableRedissonHttpSession
public class SpringSessionConfig {

    /**
     *
     * @return
     * @throws IOException
     */
    @Bean(destroyMethod = "shutdown")
    RedissonClient redisson() throws IOException {
        return Redisson.create(
                Config.fromYAML(new ClassPathResource("redis.yaml").getInputStream())
        );
    }

}
