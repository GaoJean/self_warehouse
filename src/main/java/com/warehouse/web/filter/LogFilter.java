package com.warehouse.web.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.warehouse.common.util.IpUtil;
import com.warehouse.web.filter.wrapper.CachingHttpServletRequestWrapper;
import com.warehouse.web.filter.wrapper.CachingHttpServletResponseWrapper;
import com.warehouse.web.model.RequestLog;
import com.warehouse.web.model.ResponseLog;

/**
 * @Description:
 * @Author: gaojian@doctorwork.com
 * @Date: 2021/11/25 21:28
 */
@SuppressWarnings("checkstyle:MagicNumber")
public class LogFilter extends OncePerRequestFilter {
    private final Logger logger = LoggerFactory.getLogger("LogFilter");
    private boolean shouldLog = true;
    private int maxLogLength = 2048;
    private String sessionName = "dwu";
    private boolean logHealthCheck;
    private String healthCheckUri = "/health";
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private final ObjectMapper mapper = new ObjectMapper();

    public LogFilter() {
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        boolean shouldNotFilter = super.shouldNotFilter(request);
        if (!shouldNotFilter) {
            String requestUri = request.getRequestURI();
            shouldNotFilter = !this.shouldLog || this.isHealthCheckUri(requestUri) && !this.logHealthCheck;
        }

        return shouldNotFilter;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        boolean isFirstRequest = !this.isAsyncDispatch(request);
        long beginTime = System.nanoTime();
        CachingHttpServletResponseWrapper responseWrapper = new CachingHttpServletResponseWrapper(response);
        HttpServletRequest requestToUse;
        if (this.isJsonContentRequest(request) && isFirstRequest
            && !(request instanceof CachingHttpServletRequestWrapper)) {
            requestToUse = new CachingHttpServletRequestWrapper(request);
        } else {
            requestToUse = new ContentCachingRequestWrapper(request);
        }

        boolean var13 = false;

        try {
            var13 = true;
            if (isFirstRequest) {
                RequestLog requestLog = this.buildRequestLog(requestToUse);
                this.logger.info("[HttpRequest] {}", this.toJSONString(requestLog, false));
            }

            filterChain.doFilter(requestToUse, response);
            var13 = false;
        } finally {
            if (var13) {
                if (!this.isAsyncStarted(requestToUse)) {
                    ResponseLog responseLog = this.buildResponseLog(request, responseWrapper, beginTime);
                    this.logger.info("[HttpResponse] {}", this.toJSONString(responseLog, true));
                }

            }
        }

        if (!this.isAsyncStarted(requestToUse)) {
            ResponseLog responseLog = this.buildResponseLog(request, responseWrapper, beginTime);
            this.logger.info("[HttpResponse] {}", this.toJSONString(responseLog, true));
        }

    }

    private RequestLog buildRequestLog(HttpServletRequest request) throws IOException {
        RequestLog log = new RequestLog();
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> requestParams = this.generatorRequestParamMap(parameterMap);
        String contentType = request.getContentType();
        String sessionId;
        if (contentType != null && contentType.toLowerCase().contains("application/json")) {
            sessionId = this.filterRequest(request.getInputStream());
            log.setRequestBody(this.parseObject(sessionId));
        }

        log.setUrl(new String(request.getRequestURL())).setAddr(IpUtil.getClientAddr(request))
            .setReferer(request.getHeader("Referer")).setAccept(request.getHeader("Accept"))
            .setAgent(request.getHeader("User-Agent")).setContentType(request.getContentType())
            .setParameters(requestParams).setMethod(request.getMethod());
        return log;
    }

    private Map<String, Object> generatorRequestParamMap(Map<String, String[]> parameterMap) {
        Map<String, Object> requestParams = new TreeMap();
        if (parameterMap != null && !parameterMap.isEmpty()) {
            parameterMap.forEach((key, val) -> {
                if (val != null && val.length > 0) {
                    if (val.length > 1) {
                        requestParams.put(key, val);
                    } else {
                        requestParams.put(key, val[0]);
                    }
                }

            });
        }

        return requestParams;
    }

    private String getCookieSessionId(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String sessionId = null;
        if (cookies != null && cookies.length > 0) {
            for (int i = 0; i < cookies.length; ++i) {
                Cookie cookie = cookies[i];
                if (this.sessionName.equals(cookie.getName())) {
                    sessionId = cookie.getValue();
                    break;
                }
            }
        }

        return sessionId;
    }

    private ResponseLog buildResponseLog(HttpServletRequest request, ContentCachingResponseWrapper response,
        long beginTime) throws IOException {
        ResponseLog responseLog = new ResponseLog();
        String contentType = response.getContentType();
        if (StringUtils.isEmpty(contentType)) {
            contentType = response.getHeader("Content-Type");
        }

        Collection<String> headers = response.getHeaderNames();
        Iterator var8 = headers.iterator();

        while (var8.hasNext()) {
            String string = (String) var8.next();
            String value = response.getHeader(string);
            this.logger.debug("Response Header:[{}], Value:[{}]", string, value);
        }

        responseLog.setContentType(contentType).setUrl(request.getRequestURL().toString())
            .setAddr(IpUtil.getClientAddr(request)).setSetCookie(response.getHeader("Set-Cookie"));
        responseLog.setResponse(this.parseObject(this.filterResponse(response)));
        long cost = System.nanoTime() - beginTime;
        responseLog.setCost(cost / 1000000L + "ms");
        return responseLog;
    }

    private String filterRequest(InputStream inputStream) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            StringBuilder builder = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            String body = builder.toString();
            this.logger.debug(body);
            return body;
        } catch (Exception e) {
            this.logger.error("", e);
            return null;
        }
    }

    private String filterResponse(ContentCachingResponseWrapper wrapper) {
        try {
            String charsetEncoding = wrapper.getCharacterEncoding();
            wrapper.copyBodyToResponse();
            this.logger.debug(charsetEncoding);
            byte[] body = StreamUtils.copyToByteArray(wrapper.getContentInputStream());
            String outStr = new String(body, charsetEncoding);
            this.logger.debug("Response OutStr={}", outStr);
            return outStr;
        } catch (Exception e) {
            this.logger.error("", e);
            return null;
        }
    }

    protected boolean isJsonContentRequest(HttpServletRequest request) {
        String contentType = request.getContentType();
        return contentType != null && contentType.toLowerCase().contains("application/json");
    }

    private String toJSONString(Object object, boolean lenghtLimit) throws JsonProcessingException {

        String json = mapper.writeValueAsString(object);
        int jsonLength = json.length() > this.maxLogLength && lenghtLimit ? this.maxLogLength : json.length();
        String jsonStr = json.substring(0, jsonLength);
        return jsonStr.replace("\\\"", "\"");
    }

    private Object parseObject(String jsonStr) {
        try {
            if (jsonStr != null && !jsonStr.trim().isEmpty()) {
                return mapper.readValue(jsonStr, Object.class);
            }
        } catch (Exception e) {
            this.logger.error("", e);
        }

        return jsonStr;
    }

    public void setShouldLog(boolean shouldLog) {
        this.shouldLog = shouldLog;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public void setMaxLogLength(int maxLogLength) {
        this.maxLogLength = maxLogLength;
    }

    public void setLogHealthCheck(boolean logHealthCheck) {
        this.logHealthCheck = logHealthCheck;
    }

    public void setHealthCheckUri(String healthCheckUri) {
        if (StringUtils.isEmpty(healthCheckUri)) {
            throw new IllegalArgumentException("health check uri can't be null or empty.");
        } else {
            this.healthCheckUri = healthCheckUri;
        }
    }

    private boolean isHealthCheckUri(String requestUri) {
        return this.pathMatcher.match(this.healthCheckUri, requestUri);
    }
}
