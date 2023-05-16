package com.warehouse.common.error;

/**
 * @Description:
 * @Author: gaojian
 * @Date: 2021/06/03 18:30
 */
public interface ErrorCode {
    int getCode();

    String getMessage();

    default String getI18nMessage() {
        return "";
    }

}
