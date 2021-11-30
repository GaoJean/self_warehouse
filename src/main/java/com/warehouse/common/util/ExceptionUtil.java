package com.warehouse.common.util;

import com.warehouse.common.error.ApiError;
import com.warehouse.common.exception.HouseException;
import com.warehouse.common.exception.ThrowExceptionFunction;

/**
 * @Description:
 * @Author: gaojian@doctorwork.com
 * @Date: 2021/11/26 16:23
 */
public class ExceptionUtil {

    /**
     * 验证参数错误
     */
    public static ThrowExceptionFunction paramIsTrue(boolean result) {
        return (message) -> {
            if (result) {
                throw new HouseException(ApiError.BASE_BAD_PARAMS.getCode(), message);
            }
        };
    }
}
