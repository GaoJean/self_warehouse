package com.warehouse.web.filter.wrapper;

import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:
 * @Author: gaojian@doctorwork.com
 * @Date: 2021/11/25 21:29
 */
public class CachingHttpServletResponseWrapper  extends ContentCachingResponseWrapper {
    public CachingHttpServletResponseWrapper(HttpServletResponse response) throws IOException {
        super(response);
    }
}
