package com.warehouse.data.convert;

import java.util.function.Function;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.warehouse.dal.model.ProductDO;
import com.warehouse.data.info.product.ProductSearchInfo;

/**
 * @Description:
 * @Author: gaojian@doctorwork.com
 * @Date: 2021/11/08 23:24
 */
@Mapper
public interface ProductInfoConvert {
    ProductInfoConvert INSTANCE = Mappers.getMapper(ProductInfoConvert.class);

    ProductSearchInfo infoConvert(ProductDO productDO);

}
