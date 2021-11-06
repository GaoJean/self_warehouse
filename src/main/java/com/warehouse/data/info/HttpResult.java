package com.warehouse.data.info;

import com.warehouse.common.error.ApiError;
import com.warehouse.common.error.ErrorCode;
import com.warehouse.common.exception.HouseException;

import java.util.Optional;

import static com.warehouse.common.error.ApiError.BASE_NOT_FOUND;


/**
 * Created by wk on 2017/7/4.
 */
public class HttpResult<T> {
    private boolean success;
    private Optional<T> data = Optional.empty();
    private Optional<Integer> errCode = Optional.empty();
    private Optional<String> errMsg = Optional.empty();

    public static <T> T checkAndExtract(HttpResult<T> result) throws HouseException {
        if (result.isSuccess() && result.getData().isPresent()) {
            return result.getData().get();
        } else {
            throw new HouseException(BASE_NOT_FOUND);
        }
    }

    public static <T> Optional<T> checkAndExtractOptional(HttpResult<T> result) throws HouseException {
        if (result.isSuccess()) {
            return result.getData();
        } else {
            throw new HouseException(BASE_NOT_FOUND);
        }
    }

    public static <T> HttpResult<T> ok() {
        HttpResult<T> result = new HttpResult();
        result.setSuccess(true);
        return result;
    }

    public static <T> HttpResult<T> ok(T data) {
        HttpResult<T> result = new HttpResult();
        result.setSuccess(true);
        result.setData(Optional.of(data));
        return result;
    }

    public static <T> HttpResult<T> ok(Optional<T> data) {
        HttpResult<T> result = new HttpResult();
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    public static <T> HttpResult<T> error() {
        return error(ApiError.SERVER_ERROR);
    }

    public static <T> HttpResult<T> error(String errorMessage) {
        return error(ApiError.SERVER_ERROR.getCode(), errorMessage);
    }

    public static <T> HttpResult<T> error(ErrorCode error) {
        return error(error.getCode(), error.getMessage());
    }

    public static <T> HttpResult<T> error(Integer errorCode, String errorMessage) {
        HttpResult<T> result = new HttpResult();
        result.setErrCode(Optional.of(errorCode));
        result.setErrMsg(Optional.ofNullable(errorMessage));
        return result;
    }

    public HttpResult(boolean success, Integer errCode, String errMsg, T data) {
        this.success = success;
        this.errCode = Optional.of(errCode);
        this.errMsg = Optional.of(errMsg);
        this.data = Optional.of(data);
    }

    public HttpResult(boolean success, Optional<Integer> errCode, Optional<String> errMsg, Optional<T> data) {
        this.success = success;
        this.errCode = errCode;
        this.errMsg = errMsg;
        this.data = data;
    }

    public HttpResult() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Optional<T> getData() {
        return data;
    }

    public void setData(Optional<T> data) {
        this.data = data;
    }

    public Optional<Integer> getErrCode() {
        return errCode;
    }

    public void setErrCode(Optional<Integer> errCode) {
        this.errCode = errCode;
    }

    public Optional<String> getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(Optional<String> errMsg) {
        this.errMsg = errMsg;
    }
}
