package com.warehouse.dal.mapper;

import com.warehouse.dal.model.AudienceDO;
import java.util.List;

public interface AudienceDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table audience
     *
     * @mbg.generated
     */
    int insert(AudienceDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table audience
     *
     * @mbg.generated
     */
    AudienceDO selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table audience
     *
     * @mbg.generated
     */
    List<AudienceDO> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table audience
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(AudienceDO record);
}