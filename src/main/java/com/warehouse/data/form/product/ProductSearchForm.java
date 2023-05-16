package com.warehouse.data.form.product;

import com.warehouse.data.form.BaseSearchForm;

import lombok.Builder;
import lombok.Data;

/**
 * @Description:
 * @Author: gaojian
 * @Date: 2021/11/08 23:03
 */
@Data
@Builder
public class ProductSearchForm extends BaseSearchForm {
    private static final long serialVersionUID = -7796556415976010534L;
    private String productName;
    private String skuName;

}
