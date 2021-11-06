package com.warehouse.core.service.product;

import com.warehouse.dal.mapper.AudienceDOMapper;
import com.warehouse.dal.model.AudienceDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: gaojian@doctorwork.com
 * @Date: 2021/11/06 16:30
 */
@Service
public class ProductService {
    @Autowired
    private AudienceDOMapper audienceDOMapper;

    public List<AudienceDO> list(){
        List<AudienceDO> audienceDOS = audienceDOMapper.selectAll();
        return audienceDOS;
    }

    public AudienceDO one(){
        AudienceDO audienceDOS = audienceDOMapper.selectByPrimaryKey(1L);
        return audienceDOS;
    }



}
