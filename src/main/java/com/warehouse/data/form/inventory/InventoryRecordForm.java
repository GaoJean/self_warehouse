package com.warehouse.data.form.inventory;

import java.io.Serializable;

import com.warehouse.common.enmus.ManagementTypeEnum;

import lombok.Data;

/**
 * @Description:
 * @Author: gaojian@doctorwork.com
 * @Date: 2021/11/19 15:50
 */
@Data
public class InventoryRecordForm implements Serializable {
    private static final long serialVersionUID = 8424664858665263146L;
    private Long productId;
    private Long skuId;
    private Byte managementType;
    private Integer remaining;
    private Integer quantity;
    private String unit;
    private String comment;

    public ManagementTypeEnum managementTypeEnum() {
        return ManagementTypeEnum.findByCode(managementType);
    }
}
