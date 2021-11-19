package com.warehouse.data.form.inventory;

import com.warehouse.data.form.BaseSearchForm;

/**
 * @Description:
 * @Author: gaojian@doctorwork.com
 * @Date: 2021/11/18 21:30
 */
public class InventorySearchForm extends BaseSearchForm {
    private static final long serialVersionUID = -1030702607398265364L;
    private String productName;
    private String skuName;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }
}
