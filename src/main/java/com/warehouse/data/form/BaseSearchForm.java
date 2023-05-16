package com.warehouse.data.form;

import java.io.Serializable;
import java.util.Objects;

import com.warehouse.common.exception.HouseException;
import com.warehouse.common.util.ExceptionUtil;

import lombok.Data;

/**
 * @Description:
 * @Author: gaojian
 * @Date: 2021/11/18 21:30
 */
@Data
public class BaseSearchForm implements Serializable {
    private static final long serialVersionUID = 3256660675002893121L;
    private Integer pageNum;
    private Integer pageSize;

    public void check() throws HouseException {
        ExceptionUtil.paramIsTrue(Objects.isNull(pageSize)).throwMessage("pageSize 不能为空");
        ExceptionUtil.paramIsTrue(Objects.isNull(pageNum)).throwMessage("pageNum 不能为空");
    }

}
