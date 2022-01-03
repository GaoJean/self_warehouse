package com.warehouse.data.info.product;

import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

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
    private Long productId;
    private Long skuId;
    private String productName;
    private String skuName;
    private String unit;
    private Byte isDelete;
    private Date gmtCreate;
    private Date gmtModified;

}
