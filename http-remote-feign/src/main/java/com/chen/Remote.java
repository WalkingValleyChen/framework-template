package com.chen;

import com.chen.feign.encoder.FormEncoder;
import com.chen.feign.coder.Coder;
import com.chen.feign.log.CustomFeignLoger;
import com.chen.model.Demo;
import com.chen.response.BaseResponse;
import com.chen.service.DemoRemoteService;
import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;

/**
 * @author Chenwl
 * @version 1.0.0
 * @date 2017/4/19
 */
public class Remote {
    private  static DemoRemoteService target = Feign
            .builder()
            .logger(new CustomFeignLoger())
            .client(new OkHttpClient())
            .encoder(new FormEncoder(new GsonEncoder(Coder.GSON)))
            .decoder(new GsonDecoder(Coder.GSON))
            .target(DemoRemoteService.class, "http://localhost:8080/demo");

    public static void main(String[] args) {
        Demo demo = target.get(1);
        System.out.println("get:"+Coder.GSON.toJson(demo));

        Demo add = new Demo();
        add.setName("feign add");
        BaseResponse addResult = target.add(add);
        System.out.println("add:"+Coder.GSON.toJson(addResult));
    }
}
