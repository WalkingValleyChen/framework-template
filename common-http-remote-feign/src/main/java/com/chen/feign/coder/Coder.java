package com.chen.feign.coder;

import com.chen.feign.coder.gson.TimestampTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Timestamp;

/**
 *
 * @author Chenwl
 * @version 1.0.0
 * @date 2017/4/19
 */
public class Coder {

    public static Gson GSON;

    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd hh:mm:ss");
        gsonBuilder.registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter());
        GSON = gsonBuilder.create();
    }
}
