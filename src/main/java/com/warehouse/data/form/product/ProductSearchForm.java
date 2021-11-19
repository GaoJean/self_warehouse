package com.warehouse.data.form.product;

import com.warehouse.data.form.BaseSearchForm;

/**
 * @Description:
 * @Author: gaojian@doctorwork.com
 * @Date: 2021/11/08 23:03
 */
public class ProductSearchForm extends BaseSearchForm {
    private static final long serialVersionUID = -7796556415976010534L;
    private String startTime;
    private String endTime;
    private String productName;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
