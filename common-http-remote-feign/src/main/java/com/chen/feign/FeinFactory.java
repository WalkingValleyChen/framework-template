package com.chen.feign;

import com.chen.feign.coder.Coder;
import com.chen.feign.contract.FormContract;
import com.chen.feign.encoder.FormEncoder;
import com.chen.feign.log.CustomFeignLoger;
import com.chen.feign.target.RoundRobinTarget;
import feign.Feign;
import feign.Logger;
import feign.Target;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;

/**
 * @author Chenwl
 * @version 1.0.0
 * @date 2017/4/20
 */
public class FeinFactory {

    /**
     * 生产form表单提交客户端
     * @param target
     * @param <T>
     * @return
     */
    public static <T> T newFormClient(Target<T> target) {
        return Feign
                .builder()
                .logger(new CustomFeignLoger()).logLevel(Logger.Level.FULL)
                .client(new OkHttpClient()).contract(new FormContract())
                .encoder(new FormEncoder(new GsonEncoder(Coder.GSON)))
                .decoder(new GsonDecoder(Coder.GSON))
                .target(target);
    }

    /**
     * 生产客户端
     * @param target
     * @param <T>
     * @return
     */
    public static <T> T newClient(Target<T> target) {
        return Feign
                .builder()
                .logger(new CustomFeignLoger()).logLevel(Logger.Level.FULL)
                .client(new OkHttpClient())
                .encoder(new GsonEncoder(Coder.GSON))
                .decoder(new GsonDecoder(Coder.GSON))
                .target(target);
    }
}
