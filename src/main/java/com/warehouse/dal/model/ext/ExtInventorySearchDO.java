package com.warehouse.dal.model.ext;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Author: gaojian@doctorwork.com
 * @Date: 2021/11/18 21:23
 */
@Data
@Builder
public class ExtInventorySearchDO {
    private String productName;
    private String skuName;
    private List<Integer> skuIds;
    private List<Integer> productIds;
}
