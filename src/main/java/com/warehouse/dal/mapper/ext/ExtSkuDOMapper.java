package com.warehouse.dal.mapper.ext;

import java.util.Collection;
import java.util.List;

import com.warehouse.dal.model.SkuDO;
import org.apache.ibatis.annotations.Param;

public interface ExtSkuDOMapper {
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

    List<SkuDO> selectByIds(@Param("skuIds") Collection<Long> skuIds);
}