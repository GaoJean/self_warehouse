package com.warehouse.data.form;

import lombok.Data;

import java.io.Serializable;

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

}
