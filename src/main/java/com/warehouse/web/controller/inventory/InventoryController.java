package com.warehouse.web.controller.inventory;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.warehouse.common.contants.Routes;
import com.warehouse.core.service.inventory.InventoryService;
import com.warehouse.data.form.inventory.InventorySearchForm;
import com.warehouse.web.controller.BaseController;
import com.warehouse.web.model.HttpResult;

/**
 * @Description:
 * @Author: gaojian@doctorwork.com
 * @Date: 2021/11/12 21:32
 */
@RestController
@RequestMapping(produces = APPLICATION_JSON_VALUE)
public class InventoryController extends BaseController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping(Routes.INVENTORY_LIST)
    public HttpResult list(InventorySearchForm searchForm) {
        return success(inventoryService.list(searchForm));
    }
}
