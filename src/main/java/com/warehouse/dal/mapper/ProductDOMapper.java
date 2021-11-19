package com.warehouse.dal.mapper;

import com.warehouse.dal.model.ProductDO;
import java.util.List;

public interface ProductDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product
     *
     * @mbg.generated
     */
    int insert(ProductDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product
     *
     * @mbg.generated
     */
    ProductDO selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product
     *
     * @mbg.generated
     */
    List<ProductDO> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ProductDO record);
}