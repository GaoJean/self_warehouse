package com.warehouse.data.info.inventory;

import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

/**
 * @Description:
 * @Author: gaojian@doctorwork.com
 * @Date: 2021/11/18 21:33
 */
public class InventorySearchInfo implements Serializable {
    private static final long serialVersionUID = -5233069476461989872L;
    private Long id;
    private Long productId;
    private String productName;
    private Long skuId;
    private String skuName;
    private String unit;
    private Integer totalRemaining;
    private Integer outBoundTimes;
    private Integer outBound;
    private Integer inBoundTimes;
    private Integer inBound;
    private Byte isDelete;
    private Date gmtCreate;
    private Date gmtModified;

    public InventorySearchInfo() {
    }

    private InventorySearchInfo(Builder builder) {
        setId(builder.id);
        setProductId(builder.productId);
        setProductName(builder.productName);
        setSkuId(builder.skuId);
        setSkuName(builder.skuName);
        setUnit(builder.unit);
        setTotalRemaining(builder.totalRemaining);
        setOutBoundTimes(builder.outBoundTimes);
        setOutBound(builder.outBound);
        setInBoundTimes(builder.inBoundTimes);
        setInBound(builder.inBound);
        setIsDelete(builder.isDelete);
        setGmtCreate(builder.gmtCreate);
        setGmtModified(builder.gmtModified);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(InventorySearchInfo copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.productId = copy.getProductId();
        builder.productName = copy.getProductName();
        builder.skuId = copy.getSkuId();
        builder.skuName = copy.getSkuName();
        builder.unit = copy.getUnit();
        builder.totalRemaining = copy.getTotalRemaining();
        builder.outBoundTimes = copy.getOutBoundTimes();
        builder.outBound = copy.getOutBound();
        builder.inBoundTimes = copy.getInBoundTimes();
        builder.inBound = copy.getInBound();
        builder.isDelete = copy.getIsDelete();
        builder.gmtCreate = copy.getGmtCreate();
        builder.gmtModified = copy.getGmtModified();
        return builder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getTotalRemaining() {
        return totalRemaining;
    }

    public void setTotalRemaining(Integer totalRemaining) {
        this.totalRemaining = totalRemaining;
    }

    public Integer getOutBoundTimes() {
        return outBoundTimes;
    }

    public void setOutBoundTimes(Integer outBoundTimes) {
        this.outBoundTimes = outBoundTimes;
    }

    public Integer getOutBound() {
        return outBound;
    }

    public void setOutBound(Integer outBound) {
        this.outBound = outBound;
    }

    public Integer getInBoundTimes() {
        return inBoundTimes;
    }

    public void setInBoundTimes(Integer inBoundTimes) {
        this.inBoundTimes = inBoundTimes;
    }

    public Integer getInBound() {
        return inBound;
    }

    public void setInBound(Integer inBound) {
        this.inBound = inBound;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public static final class Builder {
        private Long id;
        private Long productId;
        private String productName;
        private Long skuId;
        private String skuName;
        private String unit;
        private Integer totalRemaining;
        private Integer outBoundTimes;
        private Integer outBound;
        private Integer inBoundTimes;
        private Integer inBound;
        private Byte isDelete;
        private Date gmtCreate;
        private Date gmtModified;

        private Builder() {
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder productId(Long val) {
            productId = val;
            return this;
        }

        public Builder productName(String val) {
            productName = val;
            return this;
        }

        public Builder skuId(Long val) {
            skuId = val;
            return this;
        }

        public Builder skuName(String val) {
            skuName = val;
            return this;
        }

        public Builder unit(String val) {
            unit = val;
            return this;
        }

        public Builder totalRemaining(Integer val) {
            totalRemaining = val;
            return this;
        }

        public Builder outBoundTimes(Integer val) {
            outBoundTimes = val;
            return this;
        }

        public Builder outBound(Integer val) {
            outBound = val;
            return this;
        }

        public Builder inBoundTimes(Integer val) {
            inBoundTimes = val;
            return this;
        }

        public Builder inBound(Integer val) {
            inBound = val;
            return this;
        }

        public Builder isDelete(Byte val) {
            isDelete = val;
            return this;
        }

        public Builder gmtCreate(Date val) {
            gmtCreate = val;
            return this;
        }

        public Builder gmtModified(Date val) {
            gmtModified = val;
            return this;
        }

        public InventorySearchInfo build() {
            return new InventorySearchInfo(this);
        }
    }
}
