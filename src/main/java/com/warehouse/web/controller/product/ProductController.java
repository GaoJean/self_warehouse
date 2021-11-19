package com.warehouse.web.controller.product;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.warehouse.common.contants.Routes;
import com.warehouse.core.service.product.ProductService;
import com.warehouse.data.form.product.ProductSearchForm;
import com.warehouse.web.controller.BaseController;
import com.warehouse.web.model.HttpResult;

/**
 * @Description:
 * @Author: gaojian@doctorwork.com
 * @Date: 2021/11/06 16:12
 */
@RestController
@RequestMapping(produces = APPLICATION_JSON_VALUE)
public class ProductController extends BaseController {
    @Autowired
    private ProductService productService;

    @GetMapping(value = Routes.PRODUCT_LIST)
    public HttpResult list(@RequestBody ProductSearchForm productSearchForm) {
        return success(productService.productSearch(productSearchForm));
    }
}
