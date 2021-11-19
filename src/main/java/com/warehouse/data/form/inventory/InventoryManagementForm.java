package com.warehouse.data.form.inventory;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: gaojian@doctorwork.com
 * @Date: 2021/11/19 15:50
 */
@Data
public class InventoryManagementForm implements Serializable {
    private Long productId;
    private Long skuId;
    private Byte managementType;
    private Integer remaining;
    private Integer quantity;
    private String unit;
    private String comment;
}
