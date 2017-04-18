package com.chen.demo.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.redisson.spring.session.config.EnableRedissonHttpSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Chenwl
 * @version 1.0.0
 * @date 2017/4/18
 */
@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    CacheManager cacheManager(RedissonClient redissonClient) {
        Map<String, org.redisson.spring.cache.CacheConfig> config = new HashMap<String, org.redisson.spring.cache.CacheConfig>();
        // 创建一个名称为"testMap"的缓存，过期时间ttl，同时最长空闲时maxIdleTime。
        config.put("testMap", new org.redisson.spring.cache.CacheConfig(10 * 60 * 1000, 5 * 60 * 1000));
        return new RedissonSpringCacheManager(redissonClient, config);
    }

}
