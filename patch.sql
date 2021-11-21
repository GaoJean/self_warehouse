/** 迭代的数据库表变更(包括结构和数据), 每次版本的变更采用追加方式, 利用git自带的版本管理来追踪, 而不是一次patch对应一个文件 */
CREATE DATABASE inventory;
USE `inventory`;
/** 迭代的数据库表变更(包括结构和数据), 每次版本的变更采用追加方式, 利用git自带的版本管理来追踪, 而不是一次patch对应一个文件 */
CREATE TABLE `product` (
	`id` BIGINT ( 20 ) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '序号',
	`product_name` VARCHAR ( 32 ) NOT NULL COMMENT '商品名称',
	`status` TINYINT ( 4 ) NOT NULL DEFAULT '0' COMMENT 'SPU状态 - 0:上架, 1:下架',
	`is_delete` TINYINT ( 4 ) NOT NULL DEFAULT '0',
	`gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
	PRIMARY KEY ( `id` )
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COMMENT = '商品基础信息表';

CREATE TABLE `sku` (
	`id` BIGINT ( 20 ) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '序号',
	`sku_name` VARCHAR ( 64 ) NOT NULL DEFAULT '' COMMENT 'sku名称',
	`cost_price` INT ( 11 ) UNSIGNED NOT NULL DEFAULT '0' COMMENT '价格(分)',
	`status` TINYINT ( 4 ) NOT NULL DEFAULT '0' COMMENT 'SKU状态 0: 上架, 1:下架',
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



/**init sql*/
INSERT INTO `inventory`.`product` ( `product_name` ) VALUES ( '丝接弯头' );
INSERT INTO `inventory`.`product` ( `product_name` ) VALUES ( '丝接三通' );
INSERT INTO `inventory`.`product` ( `product_name` ) VALUES ( '中大三通' );
INSERT INTO `inventory`.`product` ( `product_name` ) VALUES ( '丝接大小头' );
INSERT INTO `inventory`.`product` ( `product_name` ) VALUES ( '外丝直接' );
INSERT INTO `inventory`.`product` ( `product_name` ) VALUES ( '内丝直接' );
INSERT INTO `inventory`.`product` ( `product_name` ) VALUES ( '丝接大小头' );
INSERT INTO `inventory`.`product` ( `product_name` ) VALUES ( '丝接堵头' );
INSERT INTO `inventory`.`product` ( `product_name` ) VALUES ( '液体生料带' );
INSERT INTO `inventory`.`product` ( `product_name` ) VALUES ( '青麻' );
INSERT INTO `inventory`.`product` ( `product_name` ) VALUES ( 'U型卡' );
INSERT INTO `inventory`.`product` ( `product_name` ) VALUES ( '消火栓头' );
INSERT INTO `inventory`.`product` ( `product_name` ) VALUES ( '消火栓箱' );
INSERT INTO `inventory`.`product` ( `product_name` ) VALUES ( '68度下喷' );
INSERT INTO `inventory`.`product` ( `product_name` ) VALUES ( '68度上喷' );
INSERT INTO `inventory`.`product` ( `product_name` ) VALUES ( '生料带' );


INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN15','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN20','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN25','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN32','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN40','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN50','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN65','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN25','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN32','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN40','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN50','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN32*25*25','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN40*32*25','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN50*40*32','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN65*50*40','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN32*25*32','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN40*25*40','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN50*25*50','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN65*25*65','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN25*32*25','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN32*50*32','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN32*65*32','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN25*15','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN32*25','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN40*32','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN50*40','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN25','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN32','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN40','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN50','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN25','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN32','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN40','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN50','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN40*25','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN50*25','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN50*32','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN25','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN32','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN40','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN50','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN65','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( '大件','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'kg','kg');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN25','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN32','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN40','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN50','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN65','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN80','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN100','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN150','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN65-普通栓','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN65-减压栓','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( '650*240*700','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN15','个');
INSERT INTO `inventory`.`sku` ( `sku_name`,`unit` ) VALUES ( 'DN15','个');



INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '1','1');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '1','2');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '1','3');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '1','4');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '1','5');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '1','6');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '1','7');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '2','8');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '2','9');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '2','10');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '2','11');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '2','12');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '2','13');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '2','14');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '2','15');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '2','16');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '2','17');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '2','18');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '2','19');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '3','20');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '3','21');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '3','22');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '4','23');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '4','24');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '4','25');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '4','26');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '5','27');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '5','28');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '5','29');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '5','30');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '6','31');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '6','32');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '6','33');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '6','34');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '7','35');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '7','36');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '7','37');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '8','38');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '8','39');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '8','40');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '8','41');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '8','42');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '9','43');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '10','44');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '11','45');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '11','46');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '11','47');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '11','48');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '11','49');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '11','50');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '11','51');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '11','52');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '12','53');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '12','54');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '13','55');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '14','56');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '15','57');
INSERT INTO `inventory`.`product_sku` ( `product_id`,`sku_id` ) VALUES ( '16','58');