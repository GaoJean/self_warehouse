package com.warehouse.data.convert;

import com.warehouse.dal.model.ext.ExtProductSearchDO;
import com.warehouse.data.form.product.ProductSearchForm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Description:
 * @Author: gaojian@doctorwork.com
 * @Date: 2021/11/08 23:10
 */
@Mapper
public interface ProductFormConvert {
    ProductFormConvert INSTANCE = Mappers.getMapper(ProductFormConvert.class);

    ExtProductSearchDO fromToDO(ProductSearchForm searchForm);

}
