package com.chen;

import com.chen.feign.FeinFactory;
import com.chen.feign.coder.Coder;
import com.chen.feign.target.RoundRobinTarget;
import com.chen.model.Demo;
import com.chen.response.BaseResponse;
import com.chen.service.DemoRemoteService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Chenwl
 * @version 1.0.0
 * @date 2017/4/19
 */
public class Remote {

    /**
     * url->权重 map
     */
    private static Map<String, Integer> urls = new HashMap() {{
        put("http://localhost:8080/demo", 2);
        put("http://127.0.0.1:8080/demo", 1);
    }};

    /**获取目标接口的代理对象*/
    private static DemoRemoteService target = FeinFactory.newFormClient(new RoundRobinTarget<DemoRemoteService>(DemoRemoteService.class, urls));

    public static void main(String[] args) {
        //运行需启动web-springmvc-mybatis项目的http接口
//        Demo demo = target.get(1);
//        System.out.println("get:" + Coder.GSON.toJson(demo));
//
//        demo = target.get(1);
//        demo = target.get(1);
//        demo = target.get(1);
//        demo = target.get(1);
//        demo = target.get(1);

        Demo add = new Demo();
        add.setName("feign add");
        BaseResponse addResult = target.add(add,1);
        System.out.println("add:" + Coder.GSON.toJson(addResult));
    }
}
