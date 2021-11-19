/** 迭代的数据库表变更(包括结构和数据), 每次版本的变更采用追加方式, 利用git自带的版本管理来追踪, 而不是一次patch对应一个文件 */
CREATE DATABASE inventory;
USE `inventory`;
/** 迭代的数据库表变更(包括结构和数据), 每次版本的变更采用追加方式, 利用git自带的版本管理来追踪, 而不是一次patch对应一个文件 */ CREATE TABLE `product` (
	`id` BIGINT ( 20 ) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '序号',
	`product_name` VARCHAR ( 32 ) NOT NULL COMMENT '商品名称',
	`status` TINYINT ( 4 ) NOT NULL COMMENT 'SKU状态 - 0: 编辑中, 1:上架, 2:下架',
	`is_delete` TINYINT ( 4 ) NOT NULL DEFAULT '0',
	`gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
	PRIMARY KEY ( `id` )
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COMMENT = '商品基础信息表';

CREATE TABLE `sku` (
	`id` BIGINT ( 20 ) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '序号',
	`sku_name` VARCHAR ( 64 ) NOT NULL DEFAULT '' COMMENT 'sku名称',
	`cost_price` INT ( 11 ) UNSIGNED NOT NULL DEFAULT '0' COMMENT '价格(分)',
	`status` TINYINT ( 4 ) NOT NULL COMMENT 'SKU状态 0: 上架, 1:下架',
	`unit` CHAR ( 3 ) NOT NULL DEFAULT '' COMMENT '数量单位',
	`is_delete` TINYINT ( 4 ) NOT NULL DEFAULT '0',
	`gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
	PRIMARY KEY ( `id` )
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COMMENT = 'sku基础信息表';

CREATE TABLE `product_sku` (
	`id` BIGINT ( 20 ) NOT NULL AUTO_INCREMENT,
	`product_id` BIGINT ( 20 ) NOT NULL,
	`sku_id` BIGINT ( 20 ) NOT NULL,
	`is_delete` TINYINT ( 4 ) NOT NULL DEFAULT '0',
	`gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
	PRIMARY KEY ( `id` ),
	UNIQUE KEY `unqProductIdSkuId` ( `product_id`, `sku_id` )
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COMMENT = 'SPU和SKU关联表';

CREATE TABLE `inventory` (
	`id` BIGINT ( 20 ) NOT NULL AUTO_INCREMENT,
	`product_id` BIGINT ( 20 ) NOT NULL COMMENT '商品ID',
	`sku_id` BIGINT ( 20 ) NOT NULL COMMENT '库存单元ID',
	`unit` CHAR ( 3 ) NOT NULL DEFAULT '' COMMENT '数量单位',
	`total_remaining` INT ( 11 ) NOT NULL COMMENT '总剩余数量',
	`out_bound_times` INT ( 11 ) NOT NULL DEFAULT '0' COMMENT '已出库次数',
	`out_bound` INT ( 11 ) NOT NULL DEFAULT '0' COMMENT '已出库数量',
	`in_bound_times` INT ( 11 ) NOT NULL DEFAULT '0' COMMENT '已入库次数',
	`in_bound` INT ( 11 ) NOT NULL DEFAULT '0' COMMENT '已入库数量',
	`is_delete` TINYINT ( 4 ) NOT NULL DEFAULT '0',
	`gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
	PRIMARY KEY ( `id` ),
	UNIQUE KEY `unqProductIdIdAndSkuId` ( `product_id`, `sku_id` )
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COMMENT = '仓库库存';

CREATE TABLE `inventory_record` (
	`id` BIGINT ( 20 ) NOT NULL AUTO_INCREMENT,
	`product_id` BIGINT ( 20 ) NOT NULL COMMENT '商品ID',
	`sku_id` BIGINT ( 20 ) NOT NULL COMMENT '库存单元ID',
	`management_type` TINYINT ( 4 ) NOT NULL DEFAULT '0' COMMENT '库存类型。0：入库 1：出库',
	`remaining` INT ( 11 ) NOT NULL DEFAULT '0' COMMENT '当时库存数',
	`quantity` INT ( 11 ) NOT NULL DEFAULT '0' COMMENT '数量',
	`unit` CHAR ( 3 ) NOT NULL DEFAULT '' COMMENT '数量单位',
	`comment` VARCHAR ( 100 ) DEFAULT NULL COMMENT '备注',
	`is_delete` TINYINT ( 4 ) NOT NULL DEFAULT '0',
	`gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
	PRIMARY KEY ( `id` ),
KEY `idxProductIdSkuId` ( `product_id`, `sku_id` )
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COMMENT = '出入库记录表';