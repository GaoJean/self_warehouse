package com.warehouse.core.service.inventory;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
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
import com.warehouse.data.convert.InventoryFormConvert;
import com.warehouse.data.form.inventory.InventoryManagementForm;
import com.warehouse.data.form.inventory.InventoryManagementSearchForm;
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
        Integer pageNum = searchForm.getPageNum();
        Integer pageSize = searchForm.getPageSize();
        Optional<ExtInventorySearchDO> extInventorySearch = toSearchCondition(searchForm);
        if (extInventorySearch.isEmpty()) {
            return new PageResult<>(0, 0, pageNum, pageSize, Collections.emptyList());
        }
        Page<InventoryDO> page = PageHelper.startPage(pageNum, pageSize).doSelectPage(() -> {
            extInventoryMapper.selectAdminList(extInventorySearch.get());
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

    private Optional<ExtInventorySearchDO> toSearchCondition(InventorySearchForm searchForm) {
        ExtInventorySearchDO extInventorySearch = InventoryFormConvert.INSTANCE.fromToDO(searchForm);
        if (StringUtils.isNotBlank(searchForm.getProductName())) {
            Optional<List<ProductDO>> productList = productService.queryByName(searchForm.getProductName());
            if (productList.isEmpty() || CollectionUtils.isEmpty(productList.get())) {
                return Optional.empty();
            }
            productList.ifPresent(products -> extInventorySearch
                .setProductIds(products.stream().map(ProductDO::getId).collect(Collectors.toSet())));
        }
        if (StringUtils.isNotBlank(searchForm.getSkuName())) {
            Optional<List<SkuDO>> skuList = skuService.queryByName(searchForm.getSkuName());
            if (skuList.isEmpty() || CollectionUtils.isEmpty(skuList.get())) {
                return Optional.empty();
            }
            skuList.ifPresent(
                skus -> extInventorySearch.setSkuIds(skus.stream().map(SkuDO::getId).collect(Collectors.toSet())));
        }
        return Optional.of(extInventorySearch);
    }

    private InventorySearchInfo convert(InventoryDO inventoryDO) {
        return InventorySearchInfo.builder().id(inventoryDO.getId()).productId(inventoryDO.getProductId())
            .skuId(inventoryDO.getSkuId()).unit(inventoryDO.getUnit()).totalRemaining(inventoryDO.getTotalRemaining())
            .outBoundTimes(inventoryDO.getOutBoundTimes()).outBound(inventoryDO.getOutBound())
            .inBoundTimes(inventoryDO.getInBoundTimes()).inBound(inventoryDO.getInBound())
            .isDelete(inventoryDO.getIsDelete()).gmtCreate(inventoryDO.getGmtCreate())
            .gmtModified(inventoryDO.getGmtModified()).build();
    }

    public Boolean management(InventoryManagementForm managementForm) {
        return false;
    }

    public boolean managementList(InventoryManagementSearchForm managementSearchForm) {
        return false;
    }
}
