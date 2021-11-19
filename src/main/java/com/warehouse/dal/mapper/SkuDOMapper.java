package com.warehouse.dal.mapper;

import com.warehouse.dal.model.SkuDO;
import java.util.List;

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
    SkuDO selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku
     *
     * @mbg.generated
     */
    List<SkuDO> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SkuDO record);
}