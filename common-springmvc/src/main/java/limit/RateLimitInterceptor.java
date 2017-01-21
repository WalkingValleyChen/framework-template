package limit;

import com.chen.utils.LoggerUtil;
import com.google.common.base.Joiner;
import com.google.common.util.concurrent.RateLimiter;
import limit.model.URLLimitMapping;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * spring请求限流器
 * 可对全局请求或url表达式请求进行限流
 * 内部使用spring DispatcherServlet的匹配器PatternsRequestCondition
 * 进行url的匹配。
 *
 * @author ValleyChen
 * @version 1.0.0
 * @time 2017/1/21
 */
public class RateLimitInterceptor implements HandlerInterceptor {

    private RateLimiter globalRateLimiter;

    private Properties urlProperties;

    private Map<PatternsRequestCondition, RateLimiter> urlRateMap;

    private UrlPathHelper urlPathHelper;

    private int globalRate;

    private List<URLLimitMapping> limitMappings;

    public RateLimitInterceptor() {
        this(0);
    }

    /**
     * @param globalRate 全局请求限制qps
     */
    public RateLimitInterceptor(int globalRate) {
        urlPathHelper = new UrlPathHelper();
        this.globalRate = globalRate;
        if (globalRate > 0)
            globalRateLimiter = RateLimiter.create(globalRate);
    }

    /**
     * @param globalRate 全局请求限制qps
     */
    public void setGlobalRate(int globalRate) {
        this.globalRate = globalRate;
        if (globalRate > 0)
            globalRateLimiter = RateLimiter.create(globalRate);
    }

    /**
     * url限流
     *
     * @param urlProperties url表达式->qps propertis
     */
    public void setUrlProperties(Properties urlProperties) {
        if (urlRateMap == null)
            urlRateMap = new ConcurrentHashMap();
        fillRateMap(urlProperties, urlRateMap);
        this.urlProperties = urlProperties;
    }

    /**
     * url限流
     *
     * @param limitMappings url表达式 array->qps mapping
     */
    public void setLimitMappings(List<URLLimitMapping> limitMappings) {
        if (urlRateMap == null)
            urlRateMap = new ConcurrentHashMap();
        fillRateMap(limitMappings, urlRateMap);
        this.limitMappings = limitMappings;
    }

    /**
     * 将url表达转换为PatternsRequestCondition，并生成对应RateLimiter
     * 保存
     *
     * @param controllerProperties
     * @param map
     */
    private void fillRateMap(Properties controllerProperties, Map<PatternsRequestCondition, RateLimiter> map) {
        if (controllerProperties != null) {
            for (String key : controllerProperties.stringPropertyNames()) {
                String value = controllerProperties.getProperty(key);
                if (value.matches("[0-9]*[1-9][0-9]*")) {
                    map.put(new PatternsRequestCondition(key), RateLimiter.create(Double.valueOf(value)));
                } else {
                    LoggerUtil.log(key + " 的值" + value + " 不是一个合法的限制");
                }
            }
        }
    }

    /**
     * 将url表达转换为PatternsRequestCondition，并生成对应RateLimiter
     * 保存
     *
     * @param limitMappings
     * @param map
     */
    private void fillRateMap(List<URLLimitMapping> limitMappings, Map<PatternsRequestCondition, RateLimiter> map) {
        if (limitMappings != null) {
            for (URLLimitMapping mapping : limitMappings) {
                map.put(new PatternsRequestCondition(mapping.getUrls()), RateLimiter.create(Double.valueOf(mapping.getRate())));
            }
        }
    }




    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        if (urlRateMap != null) {
            String lookupPath = urlPathHelper.getLookupPathForRequest(request);
            for (PatternsRequestCondition patternsRequestCondition : urlRateMap.keySet()) {
                //使用spring DispatcherServlet的匹配器PatternsRequestCondition进行匹配
                List<String> matches = patternsRequestCondition.getMatchingPatterns(lookupPath);
                if (!matches.isEmpty()) {
                    if (urlRateMap.get(patternsRequestCondition).tryAcquire()) {
                        LoggerUtil.log(lookupPath + " 请求匹配到" + Joiner.on(",").join(patternsRequestCondition.getPatterns()) + "限流器");
                    } else {
                        LoggerUtil.log(lookupPath + " 请求超过" + Joiner.on(",").join(patternsRequestCondition.getPatterns()) + "限流器速率");
                        return false;
                    }

                }
            }
        }

        //全局限流
        if (globalRateLimiter != null) {
            if (!globalRateLimiter.tryAcquire()) {
                return false;
            }

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
