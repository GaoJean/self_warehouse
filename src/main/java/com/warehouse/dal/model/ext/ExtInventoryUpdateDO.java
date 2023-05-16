package com.warehouse.dal.model.ext;

import lombok.Builder;
import lombok.Data;

/**
 * @Description:
 * @Author: gaojian
 * @Date: 2021/11/21 14:05
 */
@Data
@Builder
public class ExtInventoryUpdateDO {
    private Long productId;
    private Long skuId;
    private Integer totalRemaining;
    private Integer outBoundTimes;
    private Integer outBound;
    private Integer inBoundTimes;
    private Integer inBound;
    private String unit;
}
