package com.warehouse.data.info.inventory;

import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

/**
 * @Description:
 * @Author: gaojian@doctorwork.com
 * @Date: 2021/11/18 21:33
 */
@Data
@Builder
public class InventorySearchInfo implements Serializable {
    private static final long serialVersionUID = -5233069476461989872L;
    private Long id;
    private Long productId;
    private String productName;
    private Long skuId;
    private String skuName;
    private String unit;
    private Integer totalRemaining;
    private Integer outBoundTimes;
    private Integer outBound;
    private Integer inBoundTimes;
    private Integer inBound;
    private Byte isDelete;
    private Date gmtCreate;
    private Date gmtModified;
}
