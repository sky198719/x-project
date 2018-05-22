package com.xxd.h5.common.enums;

/**
 * 产品状态.
 */
public enum ProductStatusEnum {

    TO_BE_RELEASED("235", "待发布"),
    RELEASED("236", "已发布"),
    OPEN("237", "销售中"),
    LOCKED("238", "已锁定"),
    QUIT("239", "退出"),
    REVOKED("240", "撤销");

    /**
     * 产品状态.
     */
    private String status;

    /**
     * 产品状态描述性信息.
     */
    private String desc;

    public String getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

    ProductStatusEnum(String status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    ProductStatusEnum() {
    }

}
