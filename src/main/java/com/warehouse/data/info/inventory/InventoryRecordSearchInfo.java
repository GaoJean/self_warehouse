package com.warehouse.data.info.inventory;

import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

/**
 * @Description:
 * @Author: gaojian@doctorwork.com
 * @Date: 2021/11/21 14:52
 */
@Data
@Builder
public class InventoryRecordSearchInfo implements Serializable {
    private static final long serialVersionUID = -8086400506376144609L;
    private long id ;
    private long productId;
    private long skuId;
    private String productName;
    private String skuName;
    private Byte managementType;
    private Integer remaining;
    private Integer quantity;
    private String unit;
    private String comment;
    private Date gmtCreate;
}
