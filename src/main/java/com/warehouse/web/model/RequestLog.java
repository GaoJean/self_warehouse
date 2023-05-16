package com.warehouse.web.model;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 * @Description:
 * @Author: gaojian
 * @Date: 2021/11/25 21:31
 */
public class RequestLog {
    @JsonAlias(value = "url")
    private String url;
    @JsonAlias(value = "referer")
    private String referer;
    @JsonAlias(value = "addr")
    private String addr;
    @JsonAlias(value = "contentType")
    private String contentType;
    @JsonAlias(value = "accept")
    private String accept;
    @JsonAlias(value = "agent")
    private String agent;
    @JsonAlias(value = "token")
    private String sessionId;
    @JsonAlias(value = "parameters")
    private Object parameters;
    @JsonAlias(value = "requestBody")
    private Object requestBody;
    @JsonAlias(value = "method")
    private String method;

    public RequestLog() {
    }

    public String getUrl() {
        return this.url;
    }

    public RequestLog setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getReferer() {
        return this.referer;
    }

    public RequestLog setReferer(String referer) {
        this.referer = referer;
        return this;
    }

    public String getAddr() {
        return this.addr;
    }

    public RequestLog setAddr(String addr) {
        this.addr = addr;
        return this;
    }

    public String getContentType() {
        return this.contentType;
    }

    public RequestLog setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public String getAccept() {
        return this.accept;
    }

    public RequestLog setAccept(String accept) {
        this.accept = accept;
        return this;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public RequestLog setSessionId(String sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public Object getParameters() {
        return this.parameters;
    }

    public RequestLog setParameters(Object parameters) {
        this.parameters = parameters;
        return this;
    }

    public String getAgent() {
        return this.agent;
    }

    public RequestLog setAgent(String agent) {
        this.agent = agent;
        return this;
    }

    public Object getRequestBody() {
        return this.requestBody;
    }

    public RequestLog setRequestBody(Object requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String toString() {
        return "RequestLog [url=" + this.url + ", referer=" + this.referer + ", addr=" + this.addr + ", contentType="
            + this.contentType + ", accept=" + this.accept + ", agent=" + this.agent + ", sessionId=" + this.sessionId
            + ", parameters=" + this.parameters + ", requestBody=" + this.requestBody + ", method=" + this.method + "]";
    }
}
