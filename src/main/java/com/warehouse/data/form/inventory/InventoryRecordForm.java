package com.warehouse.data.form.inventory;

import java.io.Serializable;
import java.util.Objects;

import com.warehouse.common.enmus.ManagementTypeEnum;
import com.warehouse.common.exception.HouseException;
import com.warehouse.common.util.ExceptionUtil;

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
        ExceptionUtil.paramIsTrue(Objects.isNull(productId)).throwMessage("productId 不能为空");
        ExceptionUtil.paramIsTrue(Objects.isNull(skuId)).throwMessage("skuId 不能为空");
        ExceptionUtil.paramIsTrue(Objects.isNull(managementType)).throwMessage("managementType 不能为空");
//        ExceptionUtil.paramIsTrue(Objects.isNull(remaining)).throwMessage("remaining 不能为空");
        ExceptionUtil.paramIsTrue(Objects.isNull(quantity)).throwMessage("quantity 不能为空");
    }
}
