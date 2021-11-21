package com.warehouse.dal.mapper.ext;

import java.util.List;

import com.warehouse.dal.model.ext.ExtInventoryUpdateDO;
import org.apache.ibatis.annotations.Param;

import com.warehouse.dal.model.InventoryDO;
import com.warehouse.dal.model.ext.ExtInventorySearchDO;

public interface ExtInventoryDOMapper {

    List<InventoryDO> selectAdminList(ExtInventorySearchDO searchDO);

    InventoryDO selectBySku(@Param("skuId") Long skuId, @Param("productId") Long productId);

    int updateInventory(ExtInventoryUpdateDO updateDO);
}
