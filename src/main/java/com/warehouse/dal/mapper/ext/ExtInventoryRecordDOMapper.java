package com.warehouse.dal.mapper.ext;

import java.util.List;

import com.warehouse.dal.model.InventoryRecordDO;
import com.warehouse.dal.model.ext.ExtInventoryRecordSearchDO;

public interface ExtInventoryRecordDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table inventory_record
     *
     * @mbg.generated
     */
    int insert(InventoryRecordDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table inventory_record
     *
     * @mbg.generated
     */
    InventoryRecordDO selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table inventory_record
     *
     * @mbg.generated
     */
    List<InventoryRecordDO> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table inventory_record
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(InventoryRecordDO record);

    List<InventoryRecordDO> selectList(ExtInventoryRecordSearchDO extInventoryRecordSearchDO);
}