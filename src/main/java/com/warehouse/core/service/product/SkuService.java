package com.warehouse.core.service.product;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.warehouse.dal.mapper.ext.ExtSkuDOMapper;
import com.warehouse.dal.model.SkuDO;

/**
 * @Description:
 * @Author: gaojian@doctorwork.com
 * @Date: 2021/11/18 21:57
 */
@Service
public class SkuService {
    @Autowired
    private ExtSkuDOMapper extSkuMapper;

    public List<SkuDO> queryByIds(Collection<Long> ids) {
        return extSkuMapper.selectByIds(ids);
    }

    public Map<Long, SkuDO> queryMapIds(Collection<Long> ids) {
        List<SkuDO> skuList = queryByIds(ids);
        return skuList.stream().collect(Collectors.toMap(SkuDO::getId, Function.identity()));
    }
}
