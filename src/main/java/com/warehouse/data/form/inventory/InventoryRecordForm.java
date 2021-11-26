package com.warehouse.data.form.inventory;

import java.io.Serializable;
import java.util.Objects;

import com.warehouse.common.enmus.ManagementTypeEnum;
import com.warehouse.common.error.ApiError;
import com.warehouse.common.exception.HouseException;

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

    public void check() throws HouseException {
        if (Objects.isNull(productId)) {
            throw new HouseException(ApiError.BASE_BAD_PARAMS, "productId 不能为空");
        }
        if (Objects.isNull(skuId)) {
            throw new HouseException(ApiError.BASE_BAD_PARAMS, "skuId 不能为空");
        }
        if (Objects.isNull(managementType)) {
            throw new HouseException(ApiError.BASE_BAD_PARAMS, "managementType 不能为空");
        }
        if (Objects.isNull(remaining)) {
            throw new HouseException(ApiError.BASE_BAD_PARAMS, "remaining 不能为空");
        }
        if (Objects.isNull(quantity)) {
            throw new HouseException(ApiError.BASE_BAD_PARAMS, "quantity 不能为空");
        }
    }
}
