package com.warehouse.common.exception;

/**
 * @Description:
 * @Author: gaojian
 * @Date: 2021/11/26 16:22
 */
@FunctionalInterface
public interface ThrowExceptionFunction {
    void throwMessage(String message) throws HouseException;
}
