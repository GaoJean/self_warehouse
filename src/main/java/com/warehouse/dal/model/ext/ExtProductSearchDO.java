package com.warehouse.dal.model.ext;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: gaojian
 * @Date: 2021/11/08 23:14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExtProductSearchDO {
    private String productName;
}
