package com.warehouse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.warehouse.dal.mapper") // 扫描的mapper
public class SelfWarehouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SelfWarehouseApplication.class, args);
    }

}
