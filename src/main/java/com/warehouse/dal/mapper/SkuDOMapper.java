package com.warehouse.dal.mapper;

import com.warehouse.dal.model.SkuDO;

public interface SkuDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku
     *
     * @mbg.generated
     */
    int insert(SkuDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku
     *
     * @mbg.generated
     */
    int insertSelective(SkuDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku
     *
     * @mbg.generated
     */
    SkuDO selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(SkuDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SkuDO record);
}