package com.warehouse.web.filter;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: gaojian
 * @Date: 2021/12/05 18:05
 */
@Component
public class CorsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        res.addHeader("Access-Control-Allow-Credentials", "true");
        res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
        res.addHeader("Access-Control-Allow-Headers",
            "Content-Type,X-CAF-Authorization-Token,sessionToken,X-TOKEN,Access-Token,X-Requested-With,authorization");
        if ("OPTIONS".equals(((HttpServletRequest) request).getMethod())) {
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
