package com.warehouse.dal.mapper.ext;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.warehouse.dal.model.ext.ExtProductSkuJoinDO;

public interface ExtProductSkuDOMapper {

    List<ExtProductSkuJoinDO> selectJoinProductSku(@Param("productName") String productName,
        @Param("skuName") String skuName);

}
