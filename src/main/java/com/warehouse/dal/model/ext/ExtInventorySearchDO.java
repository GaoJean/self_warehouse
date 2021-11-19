package com.warehouse.dal.model.ext;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: gaojian@doctorwork.com
 * @Date: 2021/11/18 21:23
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExtInventorySearchDO {
    private Collection<Long> skuIds;
    private Collection<Long> productIds;
}
