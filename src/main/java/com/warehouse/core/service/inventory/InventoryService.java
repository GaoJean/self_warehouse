package com.warehouse.core.service.inventory;

import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.warehouse.core.service.product.ProductService;
import com.warehouse.core.service.product.SkuService;
import com.warehouse.dal.mapper.ext.ExtInventoryDOMapper;
import com.warehouse.dal.model.InventoryDO;
import com.warehouse.dal.model.ProductDO;
import com.warehouse.dal.model.SkuDO;
import com.warehouse.dal.model.ext.ExtInventorySearchDO;
import com.warehouse.data.PageResult;
import com.warehouse.data.form.inventory.InventorySearchForm;
import com.warehouse.data.info.inventory.InventorySearchInfo;

/**
 * @Description:
 * @Author: gaojian@doctorwork.com
 * @Date: 2021/11/08 22:54
 */
@Service
public class InventoryService {
    private final Logger logger = LoggerFactory.getLogger(ProductService.class);
    @Autowired
    private ExtInventoryDOMapper extInventoryMapper;
    @Autowired
    private ProductService productService;
    @Autowired
    private SkuService skuService;

    public PageResult<InventorySearchInfo> list(InventorySearchForm searchForm) {
        ExtInventorySearchDO extInventorySearch = ExtInventorySearchDO.builder().build();
        Integer pageNum = searchForm.getPageNum();
        Integer pageSize = searchForm.getPageSize();
        Page<InventoryDO> page = PageHelper.startPage(pageNum, pageSize).doSelectPage(() -> {
            extInventoryMapper.selectAdminList(extInventorySearch);
        });
        if (Objects.isNull(page) || CollectionUtils.isEmpty(page.getResult())) {
            return new PageResult<>(0, 0, pageNum, pageSize, Collections.emptyList());
        }
        List<InventoryDO> pageResult = page.getResult();
        Set<Long> productIdSet = pageResult.stream().map(InventoryDO::getProductId).collect(Collectors.toSet());
        Set<Long> skuIdSet = pageResult.stream().map(InventoryDO::getSkuId).collect(Collectors.toSet());
        Map<Long, ProductDO> productMap = productService.queryMapIds(productIdSet);
        Map<Long, SkuDO> skuMap = skuService.queryMapIds(skuIdSet);
        List<InventorySearchInfo> result = pageResult.stream().map(this::convert).collect(Collectors.toList());
        for (InventorySearchInfo inventoryDO : result) {
            SkuDO skuDO = skuMap.get(inventoryDO.getSkuId());
            if (Objects.nonNull(skuDO)) {
                inventoryDO.setSkuName(skuDO.getSkuName());
            }
            ProductDO productDO = productMap.get(inventoryDO.getProductId());
            if (Objects.nonNull(productDO)) {
                inventoryDO.setProductName(productDO.getProductName());
            }
        }

        return new PageResult<>(page.getTotal(), page.getPages(), pageNum, pageSize, result);
    }

    private InventorySearchInfo convert(InventoryDO inventoryDO) {
        return InventorySearchInfo.builder().id(inventoryDO.getId()).productId(inventoryDO.getProductId())
            .skuId(inventoryDO.getSkuId()).unit(inventoryDO.getUnit()).totalRemaining(inventoryDO.getTotalRemaining())
            .outBoundTimes(inventoryDO.getOutBoundTimes()).outBound(inventoryDO.getOutBound())
            .inBoundTimes(inventoryDO.getInBoundTimes()).inBound(inventoryDO.getInBound())
            .isDelete(inventoryDO.getIsDelete()).gmtCreate(inventoryDO.getGmtCreate())
            .gmtModified(inventoryDO.getGmtModified()).build();
    }
}
