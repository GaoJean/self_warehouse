package com.warehouse.data.form.inventory;

import com.warehouse.data.form.BaseSearchForm;

import lombok.Builder;
import lombok.Data;

/**
 * @Description:
 * @Author: gaojian
 * @Date: 2021/11/19 15:50
 */
@Data
@Builder
public class InventoryRecordSearchForm extends BaseSearchForm {
    private static final long serialVersionUID = 2750914944663885199L;
    private String productName;
    private String skuName;
    private Integer managementType;
    private Long startTime;
    private Long endTime;
}
