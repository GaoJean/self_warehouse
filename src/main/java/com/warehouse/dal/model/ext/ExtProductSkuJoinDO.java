package com.warehouse.dal.model.ext;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:
 * @Author: gaojian@doctorwork.com
 * @Date: 2021/11/25 20:53
 */
public class ExtProductSkuJoinDO implements Serializable {
    private static final long serialVersionUID = -5707558440859378656L;

    private Long id;
    private Long productId;
    private Long skuId;
    private String productName;
    private String skuName;
    private Byte isDelete;
    private Date gmtCreate;
    private Date gmtModified;

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

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
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

    @Override
    public String toString() {
        return "ExtProductSkuJoinDO{" + "id=" + id + ", productId=" + productId + ", skuId=" + skuId + ", productName='"
            + productName + '\'' + ", skuName='" + skuName + '\'' + ", isDelete=" + isDelete + ", gmtCreate="
            + gmtCreate + ", gmtModified=" + gmtModified + '}';
    }
}
