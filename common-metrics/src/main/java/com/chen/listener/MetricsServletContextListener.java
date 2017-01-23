package com.chen.listener;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.SharedMetricRegistries;
import com.codahale.metrics.servlets.MetricsServlet;

/**
 * @author Chenwl
 * @version 1.0.0
 * @create 2017-01-23
 */
public class MetricsServletContextListener extends MetricsServlet.ContextListener {

    @Override
    protected MetricRegistry getMetricRegistry() {
        return SharedMetricRegistries.getOrCreate("metrics");
    }
}
