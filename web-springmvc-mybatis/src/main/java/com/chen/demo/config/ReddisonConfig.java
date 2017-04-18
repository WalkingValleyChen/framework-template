package com.chen.demo.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
public class ReddisonConfig {
    @Bean(name = "redissonConfig")
    public Config config(@Qualifier("redisProp") Properties redisProp) {
        Config config = new Config();
        config.useSingleServer()
                .setAddress(redisProp.getProperty("address"))
                .setDatabase(Integer.valueOf(redisProp.getProperty("database")))
                .setConnectionMinimumIdleSize(Integer.valueOf(redisProp.getProperty("connectionMinimumIdleSize")))
                .setConnectionPoolSize(Integer.valueOf(redisProp.getProperty("connectionPoolSize")))
                .setIdleConnectionTimeout(Integer.valueOf(redisProp.getProperty("idleConnectionTimeout")))
                .setConnectTimeout(Integer.valueOf(redisProp.getProperty("connectTimeout")))
                .setTimeout(Integer.valueOf(redisProp.getProperty("timeout")))
                .setReconnectionTimeout(Integer.valueOf(redisProp.getProperty("reconnectionTimeout")));
        return config;
    }

    @Bean(destroyMethod="shutdown")
    RedissonClient redisson(@Qualifier("redissonConfig") Config config) {
        return Redisson.create(config);
    }

    @Bean
    CacheManager cacheManager(RedissonClient redissonClient) {
        Map<String, CacheConfig> config = new HashMap<String, CacheConfig>();
        // 创建一个名称为"testMap"的缓存，过期时间ttl，同时最长空闲时maxIdleTime。
        config.put("testMap", new CacheConfig());
        return new RedissonSpringCacheManager(redissonClient, config);
    }
}
