package com.chen.config;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.SharedMetricRegistries;
import com.codahale.metrics.Slf4jReporter;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.health.SharedHealthCheckRegistries;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author Chenwl
 * @version 1.0.0
 * @create 2017-01-22
 */
@Configuration
@EnableMetrics(proxyTargetClass = true,exposeProxy = true)
public class SpringMetricsConfig extends MetricsConfigurerAdapter {

    @Override
    public void configureReporters(MetricRegistry metricRegistry) {
        // registerReporter allows the MetricsConfigurerAdapter to
        // shut down the reporter when the Spring context is closed
        registerReporter(ConsoleReporter
                .forRegistry(metricRegistry)
                .build())
                .start(1, TimeUnit.HOURS);
    }

    public MetricRegistry getMetricRegistry() {
        MetricRegistry metrics = SharedMetricRegistries.getOrCreate("metrics");
        return metrics;
    }

    @Override
    public HealthCheckRegistry getHealthCheckRegistry() {
        return SharedHealthCheckRegistries.getOrCreate("healthMetrics");
    }


}
