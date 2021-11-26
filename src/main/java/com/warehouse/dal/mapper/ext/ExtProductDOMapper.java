package com.warehouse.dal.mapper.ext;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.warehouse.dal.model.ProductDO;
import com.warehouse.dal.model.ext.ExtProductSearchDO;

public interface ExtProductDOMapper {

    List<ProductDO> selectProductList(@Param("extSearchDO") ExtProductSearchDO extProductSearchDO);

    List<ProductDO> selectByIds(@Param("ids") Collection<Long> ids);

    List<ProductDO> selectByName(@Param("productName") String productName);

}