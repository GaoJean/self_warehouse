package com.warehouse.common.error;

/**
 * @Description:
 * @Author: gaojian@doctorwork.com
 * @Date: 2021/06/03 18:29
 */
public enum ApiError implements ErrorCode {
    SERVER_ERROR(1, "服务异常"), BASE_NOT_FOUND(2, "资源不存在"), BASE_UNAUTHORIZED(3, "未授权"), BASE_FORBIDDEN(4,
            "拒绝访问"), BASE_BAD_PARAMS(5, "请求参数错误");

    private int code;

    private String message;

    ApiError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
