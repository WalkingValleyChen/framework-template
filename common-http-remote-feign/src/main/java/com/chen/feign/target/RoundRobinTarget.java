package com.chen.feign.target;

import feign.Request;
import feign.RequestTemplate;
import feign.Target;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static feign.Util.checkNotNull;
import static feign.Util.emptyToNull;

/**
 * feign轮询负载均衡target
 * 创建
 * @author Chenwl
 * @version 1.0.0
 * @date 2017/4/19
 */
public class RoundRobinTarget<T> implements Target<T> {

    private final Class<T> type;
    private final String name;
    private final Map<String, Integer> urls;
    private final List<String> urlList;
    private AtomicInteger count;

    public RoundRobinTarget(Class<T> type, Map<String, Integer> urls) {
        this(type, type.getName(), urls);
    }

    public RoundRobinTarget(Class<T> type, String name, Map<String, Integer> urls) {
        this.type = checkNotNull(type, "type");
        this.name = checkNotNull(emptyToNull(name), "name");
        this.urls = checkNotNull(urls, "urls");
        urlList = new CopyOnWriteArrayList();
        refresh(urls);
        count=new AtomicInteger(0);
    }

    private void refresh(Map<String, Integer> urls) {
        urlList.clear();
        for (String url : urls.keySet()) {
            Integer wight = urls.get(url);
            for (Integer i = 0; i < wight; i++) {
                urlList.add(url);
            }
        }
    }

    @Override
    public Class<T> type() {
        return type;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String url() {
        return urlList.get(count.getAndIncrement()%urlList.size());
    }

    @Override
    public Request apply(RequestTemplate input) {
        if (input.url().indexOf("http") != 0) {
            input.insert(0, url());
        }
        return input.request();
    }

}
