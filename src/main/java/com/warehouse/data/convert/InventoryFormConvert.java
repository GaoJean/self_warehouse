package com.warehouse.data.convert;

import com.warehouse.dal.model.ext.ExtInventoryRecordSearchDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.warehouse.dal.model.ext.ExtInventorySearchDO;
import com.warehouse.data.form.inventory.InventoryRecordSearchForm;
import com.warehouse.data.form.inventory.InventorySearchForm;

/**
 * @Description:
 * @Author: gaojian@doctorwork.com
 * @Date: 2021/11/18 21:43
 */
@Mapper
public interface InventoryFormConvert {
    InventoryFormConvert INSTANCE = Mappers.getMapper(InventoryFormConvert.class);

    ExtInventorySearchDO inventoryFromToDO(InventorySearchForm searchForm);

    ExtInventoryRecordSearchDO inventoryRecordFromToDO(InventoryRecordSearchForm searchForm);

}
