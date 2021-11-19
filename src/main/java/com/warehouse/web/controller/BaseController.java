package com.warehouse.web.controller;

import com.warehouse.common.error.ErrorCode;
import com.warehouse.web.model.HttpResult;

/**
 * @Description:
 * @Author: gaojian@doctorwork.com
 * @Date: 2021/11/18 21:55
 */
public class BaseController {

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
