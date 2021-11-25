package com.warehouse.web.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.warehouse.web.filter.LogFilter;

/**
 * @Description:
 * @Author: gaojian@doctorwork.com
 * @Date: 2021/11/25 21:48
 */
@Configuration
public class WebFilterAutoConfigure {

    @Bean
    @ConditionalOnMissingBean(name = "logFilter", value = {LogFilter.class})
    public FilterRegistrationBean<LogFilter> registerLogFilter(LogFilter logFilter) {
        FilterRegistrationBean<LogFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(logFilter);
        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("logFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    @ConditionalOnMissingBean(name = "logFilter", value = {LogFilter.class})
    public LogFilter logFilter() {
        return new LogFilter();
    }

}
