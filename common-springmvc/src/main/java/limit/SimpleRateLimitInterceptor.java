package limit;

import com.chen.utils.LoggerUtil;
import com.google.common.util.concurrent.RateLimiter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Chenwl
 * @version 1.0.0
 * @date 2017/2/20
 */
public class SimpleRateLimitInterceptor implements HandlerInterceptor {

    private RateLimiter globalRateLimiter;

    public SimpleRateLimitInterceptor(int rate) {
        if (rate > 0)
            globalRateLimiter = RateLimiter.create(rate);
        else
            throw new RuntimeException("rate must greater than zero");
    }

    public void setRate(int rate){
        globalRateLimiter.setRate(rate);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!globalRateLimiter.tryAcquire()) {
            LoggerUtil.log(request.getRequestURI()+"请求超过限流器速率");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
