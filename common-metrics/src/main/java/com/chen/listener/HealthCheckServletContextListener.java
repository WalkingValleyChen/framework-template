package com.chen.listener;

import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.health.SharedHealthCheckRegistries;
import com.codahale.metrics.servlets.HealthCheckServlet;

/**
 * @author Chenwl
 * @version 1.0.0
 * @create 2017-01-23
 */
public class HealthCheckServletContextListener extends HealthCheckServlet.ContextListener {
    @Override
    protected HealthCheckRegistry getHealthCheckRegistry() {
        return SharedHealthCheckRegistries.getOrCreate("healthMetrics");
    }
}
