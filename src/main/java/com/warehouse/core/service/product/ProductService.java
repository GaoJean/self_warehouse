package com.warehouse.core.service.product;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.warehouse.common.util.Converters;
import com.warehouse.dal.mapper.ext.ExtProductDOMapper;
import com.warehouse.dal.model.ProductDO;
import com.warehouse.dal.model.ext.ExtProductSearchDO;
import com.warehouse.data.PageResult;
import com.warehouse.data.convert.ProductFormConvert;
import com.warehouse.data.form.product.ProductSearchForm;
import com.warehouse.data.info.product.ProductSearchInfo;

/**
 * @Description:
 * @Author: gaojian@doctorwork.com
 * @Date: 2021/11/06 16:30
 */
@Service
public class ProductService {

    private final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ExtProductDOMapper extProductMapper;

    public PageResult<ProductSearchInfo> productSearch(ProductSearchForm productSearchForm) {
        ExtProductSearchDO extProductSearchDO = ProductFormConvert.INSTANCE.fromToDO(productSearchForm);
        Integer pageNum = productSearchForm.getPageNum();
        Integer pageSize = productSearchForm.getPageSize();
        Page<ProductDO> page = PageHelper.startPage(pageNum, pageSize).doSelectPage(() -> {
            extProductMapper.selectProductList(extProductSearchDO);
        });
        return Converters.pageConverter(pageNum, pageSize, this::productConvert).apply(page);
    }

    public ProductSearchInfo productConvert(ProductDO productDO) {
        return new ProductSearchInfo();
    }

    public List<ProductDO> queryByIds(Collection<Long> ids) {
        return extProductMapper.selectByIds(ids);
    }

    public Map<Long, ProductDO> queryMapIds(Collection<Long> ids) {
        List<ProductDO> productList = queryByIds(ids);
        return productList.stream().collect(Collectors.toMap(ProductDO::getId, Function.identity()));
    }
}
