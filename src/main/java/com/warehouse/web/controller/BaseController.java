package com.warehouse.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.warehouse.common.error.ErrorCode;
import com.warehouse.web.model.HttpResult;

/**
 * @Description:
 * @Author: gaojian
 * @Date: 2021/11/18 21:55
 */
public class BaseController {

    public static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    public BaseController() {
    }

    protected HttpResult success() {
        return new HttpResult(0, "success", (Object) null);
    }

    protected HttpResult success(Object content) {
        return new HttpResult(0, "success", content);
    }

    protected HttpResult error(Integer errcode, String message) {
        return new HttpResult(errcode, message, (Object) null);
    }

    protected HttpResult error(ErrorCode errorCode) {
        return new HttpResult(errorCode.getCode(), errorCode.getMessage(), (Object) null);
    }
}
