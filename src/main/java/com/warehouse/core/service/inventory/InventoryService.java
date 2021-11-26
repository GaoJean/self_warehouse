package com.warehouse.core.service.inventory;

import static com.warehouse.common.error.ApiError.MANAGEMENT_TYPE_ERROR;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.warehouse.common.enmus.ManagementTypeEnum;
import com.warehouse.common.exception.HouseException;
import com.warehouse.core.service.product.ProductService;
import com.warehouse.core.service.product.SkuService;
import com.warehouse.dal.mapper.InventoryDOMapper;
import com.warehouse.dal.mapper.InventoryRecordDOMapper;
import com.warehouse.dal.mapper.ext.ExtInventoryDOMapper;
import com.warehouse.dal.mapper.ext.ExtInventoryRecordDOMapper;
import com.warehouse.dal.model.InventoryDO;
import com.warehouse.dal.model.InventoryRecordDO;
import com.warehouse.dal.model.ProductDO;
import com.warehouse.dal.model.SkuDO;
import com.warehouse.dal.model.ext.ExtInventoryRecordSearchDO;
import com.warehouse.dal.model.ext.ExtInventorySearchDO;
import com.warehouse.dal.model.ext.ExtInventoryUpdateDO;
import com.warehouse.data.PageResult;
import com.warehouse.data.convert.InventoryFormConvert;
import com.warehouse.data.form.inventory.InventoryRecordForm;
import com.warehouse.data.form.inventory.InventoryRecordSearchForm;
import com.warehouse.data.form.inventory.InventorySearchForm;
import com.warehouse.data.info.inventory.InventoryRecordSearchInfo;
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
    private InventoryDOMapper inventoryMapper;
    @Autowired
    private InventoryRecordDOMapper inventoryRecordMapper;
    @Autowired
    private ExtInventoryRecordDOMapper extInventoryRecordMapper;
    @Autowired
    private ProductService productService;
    @Autowired
    private SkuService skuService;

    public PageResult<InventorySearchInfo> list(InventorySearchForm searchForm) {
        Integer pageNum = searchForm.getPageNum();
        Integer pageSize = searchForm.getPageSize();
        Optional<ExtInventorySearchDO> extInventorySearch = toInventorySearchCondition(searchForm);
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

    private Optional<ExtInventorySearchDO> toInventorySearchCondition(InventorySearchForm searchForm) {
        ExtInventorySearchDO extInventorySearch = InventoryFormConvert.INSTANCE.inventoryFromToDO(searchForm);
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

    private InventoryRecordSearchInfo recordConvert(InventoryRecordDO inventoryDO) {
        return InventoryRecordSearchInfo.builder().id(inventoryDO.getId()).productId(inventoryDO.getProductId())
            .skuId(inventoryDO.getSkuId()).unit(inventoryDO.getUnit()).managementType(inventoryDO.getManagementType())
            .comment(inventoryDO.getComment()).quantity(inventoryDO.getQuantity()).remaining(inventoryDO.getRemaining())
            .gmtCreate(inventoryDO.getGmtCreate()).build();
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean record(InventoryRecordForm inventoryRecordForm) throws HouseException {
        insertRecord(inventoryRecordForm);
        updateInventory(inventoryRecordForm);
        return true;
    }

    private void updateInventory(InventoryRecordForm inventoryRecordForm) throws HouseException {
        Long skuId = inventoryRecordForm.getSkuId();
        Long productId = inventoryRecordForm.getProductId();
        ManagementTypeEnum managementTypeEnum = inventoryRecordForm.managementTypeEnum();
        if (Objects.isNull(managementTypeEnum)) {
            throw new HouseException(MANAGEMENT_TYPE_ERROR);
        }
        InventoryDO inventoryDO = extInventoryMapper.selectBySku(skuId, productId);
        if (Objects.isNull(inventoryDO)) {
            initInventory(inventoryRecordForm, skuId, productId, managementTypeEnum);
        } else {
            int totalRemaining = inventoryDO.getTotalRemaining();
            ExtInventoryUpdateDO updateDO = ExtInventoryUpdateDO.builder().productId(productId).skuId(skuId).build();
            if (managementTypeEnum.compareTo(ManagementTypeEnum.IN) == 0) {
                totalRemaining = totalRemaining + inventoryRecordForm.getQuantity();
                updateDO.setInBound(inventoryDO.getInBound() + inventoryRecordForm.getQuantity());
                updateDO.setInBoundTimes(inventoryDO.getInBoundTimes() + 1);
            } else {
                totalRemaining = totalRemaining - inventoryRecordForm.getQuantity();
                updateDO.setOutBound(inventoryDO.getOutBound() + inventoryRecordForm.getQuantity());
                updateDO.setOutBoundTimes(inventoryDO.getOutBoundTimes() + 1);
            }
            updateDO.setTotalRemaining(totalRemaining);
            extInventoryMapper.updateInventory(updateDO);
        }
    }

    private void initInventory(InventoryRecordForm managementForm, Long skuId, Long productId,
        ManagementTypeEnum managementTypeEnum) {
        InventoryDO inventoryDO;
        inventoryDO = new InventoryDO();
        inventoryDO.setProductId(productId);
        inventoryDO.setSkuId(skuId);
        inventoryDO.setTotalRemaining(managementForm.getQuantity());
        inventoryDO.setUnit(managementForm.getUnit());
        if (managementTypeEnum.compareTo(ManagementTypeEnum.IN) == 0) {
            inventoryDO.setInBound(managementForm.getQuantity());
            inventoryDO.setInBoundTimes(1);
        } else {
            inventoryDO.setOutBound(managementForm.getQuantity());
            inventoryDO.setOutBoundTimes(1);
        }
        inventoryMapper.insertSelective(inventoryDO);
    }

    private void insertRecord(InventoryRecordForm inventoryRecordForm) {
        InventoryRecordDO inventoryRecordDO = new InventoryRecordDO();
        inventoryRecordDO.setManagementType(inventoryRecordForm.getManagementType());
        inventoryRecordDO.setProductId(inventoryRecordForm.getProductId());
        inventoryRecordDO.setSkuId(inventoryRecordForm.getSkuId());
        inventoryRecordDO.setUnit(inventoryRecordForm.getUnit());
        inventoryRecordDO.setRemaining(inventoryRecordForm.getRemaining());
        inventoryRecordDO.setQuantity(inventoryRecordForm.getQuantity());
        inventoryRecordDO.setComment(inventoryRecordForm.getComment());
        inventoryRecordMapper.insertSelective(inventoryRecordDO);
    }

    public PageResult<InventoryRecordSearchInfo> recordList(InventoryRecordSearchForm recordSearchForm) {
        Integer pageNum = recordSearchForm.getPageNum();
        Integer pageSize = recordSearchForm.getPageSize();
        Optional<ExtInventoryRecordSearchDO> extInventoryRecordSearchDO =
            toInventoryRecordSearchCondition(recordSearchForm);
        if (extInventoryRecordSearchDO.isEmpty()) {
            return new PageResult<>(0, 0, pageNum, pageSize, Collections.emptyList());
        }
        Page<InventoryRecordDO> page = PageHelper.startPage(pageNum, pageSize)
            .doSelectPage(() -> extInventoryRecordMapper.selectList(extInventoryRecordSearchDO.get()));
        if (Objects.isNull(page) || CollectionUtils.isEmpty(page.getResult())) {
            return new PageResult<>(0, 0, pageNum, pageSize, Collections.emptyList());
        }
        List<InventoryRecordDO> pageResult = page.getResult();
        Set<Long> productIdSet = pageResult.stream().map(InventoryRecordDO::getProductId).collect(Collectors.toSet());
        Set<Long> skuIdSet = pageResult.stream().map(InventoryRecordDO::getSkuId).collect(Collectors.toSet());
        Map<Long, ProductDO> productMap = productService.queryMapIds(productIdSet);
        Map<Long, SkuDO> skuMap = skuService.queryMapIds(skuIdSet);
        List<InventoryRecordSearchInfo> result =
            pageResult.stream().map(this::recordConvert).collect(Collectors.toList());
        for (InventoryRecordSearchInfo inventoryDO : result) {
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

    private Optional<ExtInventoryRecordSearchDO> toInventoryRecordSearchCondition(
        InventoryRecordSearchForm recordSearchForm) {
        ExtInventoryRecordSearchDO extInventoryRecordSearchDO =
            InventoryFormConvert.INSTANCE.inventoryRecordFromToDO(recordSearchForm);
        if (StringUtils.isNotBlank(recordSearchForm.getProductName())) {
            Optional<List<ProductDO>> productList = productService.queryByName(recordSearchForm.getProductName());
            if (productList.isEmpty() || CollectionUtils.isEmpty(productList.get())) {
                return Optional.empty();
            }
            productList.ifPresent(products -> extInventoryRecordSearchDO
                .setProductIds(products.stream().map(ProductDO::getId).collect(Collectors.toSet())));
        }
        if (StringUtils.isNotBlank(recordSearchForm.getSkuName())) {
            Optional<List<SkuDO>> skuList = skuService.queryByName(recordSearchForm.getSkuName());
            if (skuList.isEmpty() || CollectionUtils.isEmpty(skuList.get())) {
                return Optional.empty();
            }
            skuList.ifPresent(skus -> extInventoryRecordSearchDO
                .setSkuIds(skus.stream().map(SkuDO::getId).collect(Collectors.toSet())));
        }
        if (Objects.nonNull(recordSearchForm.getStartTime())) {
            extInventoryRecordSearchDO.setStartTime(new Date(recordSearchForm.getStartTime()));
        }
        if (Objects.nonNull(recordSearchForm.getEndTime())) {
            extInventoryRecordSearchDO.setEndTime(new Date(recordSearchForm.getEndTime()));
        }
        if (Objects.nonNull(recordSearchForm.getManagementType())) {
            extInventoryRecordSearchDO.setManagementType(recordSearchForm.getManagementType());
        }
        return Optional.of(extInventoryRecordSearchDO);

    }
}
