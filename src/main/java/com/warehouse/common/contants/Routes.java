package com.warehouse.common.contants;

/**
 * @Description:
 * @Author: gaojian@doctorwork.com
 * @Date: 2021/11/06 16:17
 */
public class Routes {
    /** 商品 **/
    public static final String BASE_PRODUCT_ROUTE = "/product";
    public static final String BASE_PRODUCT_SKU_ROUTE = BASE_PRODUCT_ROUTE + "/sku";
    public static final String PRODUCT_LIST = BASE_PRODUCT_ROUTE + "/list";
    public static final String PRODUCT_ADD = BASE_PRODUCT_ROUTE + "/add";
    public static final String PRODUCT_UPDATE = BASE_PRODUCT_ROUTE + "/update";
    public static final String PRODUCT_VIEW = BASE_PRODUCT_ROUTE + "/view";
    public static final String PRODUCT_UPDATE_STATUS = BASE_PRODUCT_ROUTE + "/update/status";

    public static final String PRODUCT_SKU_LIST = BASE_PRODUCT_SKU_ROUTE + "/list";
    public static final String PRODUCT_SKU_ADD = BASE_PRODUCT_SKU_ROUTE + "/add";
    public static final String PRODUCT_SKU_UPDATE = BASE_PRODUCT_SKU_ROUTE + "/update";
    public static final String PRODUCT_SKU_VIEW = BASE_PRODUCT_SKU_ROUTE + "/view";
    public static final String PRODUCT_SKU_UPDATE_STATUS = BASE_PRODUCT_SKU_ROUTE + "/update/status";

    /** 库存 **/
    public static final String BASE_INVENTORY_ROUTE = "/inventory";
    public static final String INVENTORY_LIST = BASE_INVENTORY_ROUTE + "/list";
    public static final String INVENTORY_RECORD = BASE_INVENTORY_ROUTE + "/record";
    public static final String INVENTORY_RECORD_LIST = BASE_INVENTORY_ROUTE + "/record/list";

}
