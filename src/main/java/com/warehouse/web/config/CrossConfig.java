package com.warehouse.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description:
 * @Author: gaojian
 * @Date: 2021/12/05 18:23
 */
@Configuration
public class CrossConfig implements WebMvcConfigurer {

    public static final int MAX_AGE = 3600;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOriginPatterns("*").allowCredentials(true).maxAge(MAX_AGE)
            .allowedHeaders("*");
    }
}
