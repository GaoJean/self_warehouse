package com.warehouse.web;

import javax.servlet.http.HttpServletResponse;

import com.warehouse.common.error.ApiError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.warehouse.common.error.ErrorCode;
import com.warehouse.common.exception.HouseException;
import com.warehouse.web.model.HttpResult;

/**
 * Web统一异常处理
 */
@ControllerAdvice
@SuppressWarnings("deprecation")
public class WebExceptionHandler {
    /**
     * 日志对象.
     */
    protected final Logger logger = LoggerFactory.getLogger("ExceptionInterceptor");

    @ExceptionHandler
    @ResponseBody
    public HttpResult exceptionHandler(Throwable e, HttpServletResponse response) {
        // 2017-12-06 根据Exception级别不同，来确定是否打印exception stack trace
        if (e instanceof HouseException) {
            HouseException ex = (HouseException) e;
            logger.error("[HouseException], code:[{}], message:[{}]", ex.getCode(), ex.getMessage());
            return build(ex.getCode(), ex.getMessage());
        } else {
            logger.error("", e);
            return build(ApiError.SERVER_ERROR);
        }
    }

    private HttpResult build(Integer errcode, String errmsg) {
        return new HttpResult(errcode, errmsg, null);
    }

    private HttpResult build(ErrorCode errorCode) {
        return new HttpResult(errorCode.getCode(), errorCode.getMessage(), null);
    }
}
