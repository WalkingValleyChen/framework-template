package com.chen.interceptor;

import com.alibaba.fastjson.JSON;
import com.chen.utils.LoggerUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 记录请求的参数和响应
 * @author Chenwl
 * @version 1.0.0
 * @date 2017/4/18
 */
public class RequestInfoInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LoggerUtil.log("--------------------request info------------------------");
        LoggerUtil.log("uri" + request.getRequestURI());
        LoggerUtil.log("parameter json:" + JSON.toJSONString(request.getParameterMap()));
        LoggerUtil.log("--------------------------------------------------------");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            LoggerUtil.log("--------------------response info------------------------");
            LoggerUtil.log("response view:" + modelAndView.getView());
            LoggerUtil.log("response model:" + JSON.toJSONString(modelAndView.getModel()));
            LoggerUtil.log("--------------------------------------------------------");
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
