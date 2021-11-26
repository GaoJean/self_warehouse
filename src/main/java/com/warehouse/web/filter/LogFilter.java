package com.warehouse.web.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
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
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

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
    public static final String CONTENT_TYPE_JSON = "application/json";

    /**
     * 是否应该打印日志
     */
    private boolean shouldLog = true;

    /**
     * 用于记录Session的CookieName
     */
    private String sessionName = "self_house";

    /**
     * 最大日志显示长度
     */
    private int maxLogLength = 2048;

    /**
     * 是否打印健康检查日志
     */
    private boolean logHealthCheck;

    /**
     * 健康检查默认路径
     */
    private String healthCheckUri = "/health";

    private static AntPathMatcher pathMatcher = new AntPathMatcher();
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        boolean shouldNotFilter = super.shouldNotFilter(request);
        if (!shouldNotFilter) {
            String requestUri = request.getRequestURI();
            shouldNotFilter = !shouldLog ? true : isHealthCheckUri(requestUri) && !logHealthCheck ? true : false;
        }
        return shouldNotFilter;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        boolean isFirstRequest = !isAsyncDispatch(request);
        long beginTime = System.nanoTime();
        HttpServletRequest requestToUse = request;
        // doFilter和记录日志需要为同一个数据流
        response = new CachingHttpServletResponseWrapper(response);

        if (isJsonContentRequest(requestToUse) && isFirstRequest
            && !(requestToUse instanceof CachingHttpServletRequestWrapper)) {
            requestToUse = new CachingHttpServletRequestWrapper(request);
        } else {
            requestToUse = new ContentCachingRequestWrapper(request);
        }

        try {
            if (isFirstRequest) {
                RequestLog requestLog = buildRequestLog(requestToUse);
                logger.info("[HttpRequest] {}", toJSONString(requestLog, false));
            }
            filterChain.doFilter(requestToUse, response);
        } finally {
            if (!isAsyncStarted(requestToUse)) {
                ResponseLog responseLog =
                    buildResponseLog(request, (CachingHttpServletResponseWrapper) response, beginTime);
                logger.info("[HttpResponse] {}", toJSONString(responseLog, true));
            }
        }
    }

    /**
     * Request Log
     *
     * @param request
     * @return
     * @throws IOException
     */
    private RequestLog buildRequestLog(HttpServletRequest request) throws IOException {
        RequestLog log = new RequestLog();
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> requestParams = generatorRequestParamMap(parameterMap);
        String contentType = request.getContentType();
        if (contentType != null && contentType.toLowerCase().contains("application/json")) {
            String body = filterRequest(request.getInputStream());
            log.setRequestBody(parseObject(body));
        }
        log.setUrl(new String(request.getRequestURL())).setAddr(IpUtil.getClientAddr(request))
            .setReferer(request.getHeader("Referer")).setAccept(request.getHeader("Accept"))
            .setAgent(request.getHeader("User-Agent")).setContentType(request.getContentType())
            .setParameters(requestParams).setMethod(request.getMethod());
        return log;
    }

    /**
     * 请求参数Map
     *
     * @param parameterMap
     * @return
     */
    private Map<String, Object> generatorRequestParamMap(Map<String, String[]> parameterMap) {
        Map<String, Object> requestParams = new TreeMap<>();
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

    /**
     * 1.0 基于Cookie的分布式SessionKey
     *
     * @param request
     * @return
     */
    private String getCookieSessionId(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String sessionId = null;
        if (cookies != null && cookies.length > 0) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if (sessionName.equals(cookie.getName())) {
                    sessionId = cookie.getValue();
                    break;
                }
            }
        }
        return sessionId;
    }

    private ResponseLog buildResponseLog(HttpServletRequest request, CachingHttpServletResponseWrapper response,
        long beginTime) throws IOException {
        ResponseLog responseLog = new ResponseLog();
        String contentType = response.getContentType();
        if (StringUtils.isEmpty(contentType)) {
            contentType = response.getHeader("Content-Type");
        }
        Collection<String> headers = response.getHeaderNames();
        for (String string : headers) {
            String head = string;
            String value = response.getHeader(head);
            logger.debug("Response Header:[{}], Value:[{}]", head, value);
        }
        responseLog.setContentType(contentType).setUrl(request.getRequestURL().toString()).setAddr(IpUtil.getLocalIp())
            .setSetCookie(response.getHeader("Set-Cookie"));
        responseLog.setResponse(parseObject(filterResponse(response)));
        long cost = System.nanoTime() - beginTime;
        responseLog.setCost(cost / 1000000 + "ms");
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
            logger.debug(body);
            return body;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    private String filterResponse(CachingHttpServletResponseWrapper wrapper) {
        try {
            String charsetEncoding = wrapper.getCharacterEncoding();
            logger.debug(charsetEncoding);
            String outStr = new String(wrapper.toByteArray(), wrapper.getCharacterEncoding());
            logger.debug("Response OutStr={}", outStr);
            return outStr;
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    /**
     * 是否为 application/json
     *
     * @param request
     * @return
     */
    protected boolean isJsonContentRequest(HttpServletRequest request) {
        String contentType = request.getContentType();
        if (contentType != null && contentType.toLowerCase().contains(CONTENT_TYPE_JSON)) {
            return true;
        }
        return false;
    }

    /**
     * 转成JSON string
     *
     * @param object
     * @param lenghtLimit 是否限制打印日志长度
     * @return
     */
    private String toJSONString(Object object, boolean lenghtLimit) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(object);
        int jsonLength = json.length() > maxLogLength && lenghtLimit ? maxLogLength : json.length();
        String jsonStr = json.substring(0, jsonLength);
        return jsonStr.replace("\\\"", "\"");
    }

    private Object parseObject(String jsonStr) {
        try {
            if (jsonStr != null && !jsonStr.trim().isEmpty()) {
                return objectMapper.readValue(jsonStr, Object.class);
            }
        } catch (Exception e) {
            logger.error("", e);
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
        }
        this.healthCheckUri = healthCheckUri;
    }

    private boolean isHealthCheckUri(String requestUri) {
        return pathMatcher.match(healthCheckUri, requestUri);
    }
}
