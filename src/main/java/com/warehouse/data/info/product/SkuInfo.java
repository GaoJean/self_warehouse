package com.warehouse.data.info.product;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: gaojian@doctorwork.com
 * @Date: 2021/11/21 16:48
 */
@Data
@Builder
public class SkuInfo implements Serializable {

    private static final long serialVersionUID = -8747664567595871167L;

    private Long id;

    private String skuName;

    private Integer costPrice;

    private Byte status;

    private String unit;
}
