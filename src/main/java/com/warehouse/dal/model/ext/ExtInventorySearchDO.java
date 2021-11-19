package com.warehouse.dal.model.ext;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Author: gaojian@doctorwork.com
 * @Date: 2021/11/18 21:23
 */
public class ExtInventorySearchDO {
    private String productName;
    private String skuName;
    private List<Integer> skuIds;
    private List<Integer> productIds;

    public ExtInventorySearchDO() {
    }

    private ExtInventorySearchDO(Builder builder) {
        setProductName(builder.productName);
        setSkuName(builder.skuName);
        setSkuIds(builder.skuIds);
        setProductIds(builder.productIds);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(ExtInventorySearchDO copy) {
        Builder builder = new Builder();
        builder.productName = copy.getProductName();
        builder.skuName = copy.getSkuName();
        builder.skuIds = copy.getSkuIds();
        builder.productIds = copy.getProductIds();
        return builder;
    }

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

    public List<Integer> getSkuIds() {
        return skuIds;
    }

    public void setSkuIds(List<Integer> skuIds) {
        this.skuIds = skuIds;
    }

    public List<Integer> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Integer> productIds) {
        this.productIds = productIds;
    }

    public static final class Builder {
        private String productName;
        private String skuName;
        private List<Integer> skuIds;
        private List<Integer> productIds;

        private Builder() {
        }

        public Builder productName(String val) {
            productName = val;
            return this;
        }

        public Builder skuName(String val) {
            skuName = val;
            return this;
        }

        public Builder skuIds(List<Integer> val) {
            skuIds = val;
            return this;
        }

        public Builder productIds(List<Integer> val) {
            productIds = val;
            return this;
        }

        public ExtInventorySearchDO build() {
            return new ExtInventorySearchDO(this);
        }
    }
}
