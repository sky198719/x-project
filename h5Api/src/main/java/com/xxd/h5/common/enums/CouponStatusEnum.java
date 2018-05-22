package com.xxd.h5.common.enums;

/**
 * 优惠券状态
 */
public enum CouponStatusEnum {

    CAN_USE(0, "可以使用"),

    USED(1, "已使用"),

    OVERDUE(2, "已过期");

    private int status;

    private String desc;

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

    CouponStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
