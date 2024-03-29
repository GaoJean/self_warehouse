package com.warehouse.dal.model.ext;

import java.util.Collection;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: gaojian
 * @Date: 2021/11/21 14:57
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExtInventoryRecordSearchDO {
    private Collection<Long> productIds;
    private Collection<Long> skuIds;
    private Integer managementType;
    private Date startTime;
    private Date endTime;
}
