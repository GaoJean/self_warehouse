package com.warehouse.web.controller;

import com.warehouse.common.contants.Routes;
import com.warehouse.core.service.product.ProductService;
import com.warehouse.data.info.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @Description:
 * @Author: gaojian@doctorwork.com
 * @Date: 2021/11/06 16:12
 */
@RestController
@RequestMapping(produces = APPLICATION_JSON_VALUE)
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping(value = Routes.PRODUCT_LIST)
    public HttpResult<Object> list() {
        return HttpResult.ok(productService.one());
    }
}
