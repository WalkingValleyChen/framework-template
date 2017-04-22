package com.chen;

import com.chen.feign.FeinFactory;
import com.chen.feign.coder.Coder;
import com.chen.feign.target.RoundRobinTarget;
import com.chen.model.Demo;
import com.chen.request.Page;
import com.chen.response.BaseResponse;
import com.chen.service.DemoRemoteService;
import com.chen.service.FeignTestService;
import feign.Target;

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
    private static FeignTestService target = FeinFactory.newFormClient(new Target.HardCodedTarget<>(FeignTestService.class,"http://localhost:8080/demo"));

    public static void main(String[] args) {
        Demo chen = new Demo();
        chen.setName("chen");

        Page page = new Page();
        page.setPageNum(1);
        page.setPageSize(10);

//        target.test1(chen);

//        target.test2(1,"chen");

        HashMap<String, Object> map = new HashMap<>();
        map.put("name","chen");
//        target.test3(map);

        target.test4(chen);
//
//        target.test3(chen,page);
//
//        target.test4(chen,page);
    }
}
