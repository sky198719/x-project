package com.xxd.h5.common.enums;

/**
 * 优惠券类型.
 */
public enum CouponTypeEnum {

    COUPON(1, "优惠券"),

    REDPACKAGE(2, "新手红包");

    private int type;

    private String desc;

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    CouponTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

}
