package com.warehouse.common.enmus;

/**
 * @Description:
 * @Author: gaojian@doctorwork.com
 * @Date: 2021/11/21 14:13
 */
public enum ManagementTypeEnum {
    /**
     * 入库
     */
    IN(0, "入库"),
    /**
     * 出库
     */
    OUT(1, "出库");

    ManagementTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private int code;
    private String desc;

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static ManagementTypeEnum findByCode(int code) {
        for (ManagementTypeEnum value : ManagementTypeEnum.values()) {
            if (value.code == code) {
                return value;
            }
        }
        return null;
    }

}
