package com.warehouse.dal.mapper.ext;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.warehouse.dal.model.SkuDO;

public interface ExtSkuDOMapper {
    List<SkuDO> selectByIds(@Param("skuIds") Collection<Long> skuIds);

    List<SkuDO> selectByName(@Param("skuName") String skuName);
}