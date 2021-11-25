package com.warehouse.web.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 * @Description:
 * @Author: gaojian@doctorwork.com
 * @Date: 2021/11/25 21:31
 */
public class ResponseLog implements Serializable {
    private static final long serialVersionUID = 8466469593502872710L;
    @JsonAlias(value = "contentType")
    private String contentType;
    @JsonAlias(value = "url")
    private String url;
    @JsonAlias(value = "addr")
    private String addr;
    @JsonAlias(value = "response")
    private Object response;
    @JsonAlias(value = "setCookie")
    private String setCookie;
    @JsonAlias(value = "cost")
    private String cost;

    public ResponseLog() {
    }

    public String getContentType() {
        return this.contentType;
    }

    public ResponseLog setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public String getAddr() {
        return this.addr;
    }

    public ResponseLog setAddr(String addr) {
        this.addr = addr;
        return this;
    }

    public Object getResponse() {
        return this.response;
    }

    public ResponseLog setResponse(Object response) {
        this.response = response;
        return this;
    }

    public String getSetCookie() {
        return this.setCookie;
    }

    public ResponseLog setSetCookie(String setCookie) {
        this.setCookie = setCookie;
        return this;
    }

    public String getCost() {
        return this.cost;
    }

    public ResponseLog setCost(String cost) {
        this.cost = cost;
        return this;
    }

    public String getUrl() {
        return this.url;
    }

    public ResponseLog setUrl(String url) {
        this.url = url;
        return this;
    }

    public String toString() {
        return "ResponseLog [contentType=" + this.contentType + ", url=" + this.url + ", addr=" + this.addr
            + ", response=" + this.response + ", setCookie=" + this.setCookie + ", cost=" + this.cost + "]";
    }
}
