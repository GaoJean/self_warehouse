package com.warehouse.data.form;

import java.io.Serializable;
import java.util.Objects;

import com.warehouse.common.error.ApiError;
import com.warehouse.common.exception.HouseException;

import lombok.Data;

/**
 * @Description:
 * @Author: gaojian@doctorwork.com
 * @Date: 2021/11/18 21:30
 */
@Data
public class BaseSearchForm implements Serializable {
    private static final long serialVersionUID = 3256660675002893121L;
    private Integer pageNum;
    private Integer pageSize;

    public void check() throws HouseException {
        if (Objects.isNull(pageSize)) {
            throw new HouseException(ApiError.BASE_BAD_PARAMS, "pageSize 不能为空");
        }
        if (Objects.isNull(pageNum)) {
            throw new HouseException(ApiError.BASE_BAD_PARAMS, "pageNum 不能为空");
        }
    }

}
