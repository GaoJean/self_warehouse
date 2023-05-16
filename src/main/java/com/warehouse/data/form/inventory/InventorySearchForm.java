package com.warehouse.data.form.inventory;

import com.warehouse.data.form.BaseSearchForm;
import lombok.Builder;
import lombok.Data;

/**
 * @Description:
 * @Author: gaojian
 * @Date: 2021/11/18 21:30
 */
@Data
@Builder
public class InventorySearchForm extends BaseSearchForm {
    private static final long serialVersionUID = -1030702607398265364L;
    private String productName;
    private String skuName;

}
