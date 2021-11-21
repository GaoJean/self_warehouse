package com.warehouse.data.info.product;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: gaojian@doctorwork.com
 * @Date: 2021/11/08 23:24
 */
@Data
@Builder
public class ProductSearchInfo implements Serializable {
    private static final long serialVersionUID = 3121279729838525530L;
    private Long id;

    private String productName;

    private Byte status;

    private Byte isDelete;

    private Date gmtCreate;

    private Date gmtModified;

    private List<SkuInfo> skuInfos;
}
