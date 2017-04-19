package com.chen.feign.log;

import com.chen.utils.LoggerUtil;
import feign.Logger;

/**
 * @author Chenwl
 * @version 1.0.0
 * @date 2017/4/19
 */
public class CustomFeignLoger extends Logger {
    @Override
    protected void log(String configKey, String format, Object... args) {
        LoggerUtil.log(String.format(format,args));
    }
}
