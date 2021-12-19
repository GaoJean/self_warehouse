package com.warehouse.common.error;

/**
 * @Description:
 * @Author: gaojian@doctorwork.com
 * @Date: 2021/06/03 18:29
 */
public enum ApiError implements ErrorCode {
    /**
     * 服务异常
     */
    SERVER_ERROR(1, "服务异常"),
    /**
     * 资源不存在
     */
    BASE_NOT_FOUND(2, "资源不存在"),
    /**
     * 时间格式错误
     */
    TIME_ERROR(3, "时间格式错误"),
    /**
     * 拒绝访问
     */
    BASE_FORBIDDEN(4, "拒绝访问"),
    /**
     * 请求参数错误
     */
    BASE_BAD_PARAMS(5, "请求参数错误"),
    /**
     * 操作类型错误
     */
    MANAGEMENT_TYPE_ERROR(6, "操作类型错误");

    private int code;

    private String message;

    ApiError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
