package com.warehouse.dal.mapper;

import com.warehouse.dal.model.InventoryDO;

public interface InventoryDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table inventory
     *
     * @mbg.generated
     */
    int insert(InventoryDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table inventory
     *
     * @mbg.generated
     */
    int insertSelective(InventoryDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table inventory
     *
     * @mbg.generated
     */
    InventoryDO selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table inventory
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(InventoryDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table inventory
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(InventoryDO record);
}