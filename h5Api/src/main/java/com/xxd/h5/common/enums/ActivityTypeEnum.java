package com.xxd.h5.common.enums;

/**
 * 活动类型.
 */
public enum ActivityTypeEnum {

    /**
     * 活动.
     */
    ACTIVITY_TYPE_1(1, "普通"),

    /**
     * 专题.
     */
    ACTIVITY_TYPE_2(2, "活动");

    private int type;

    private String desc;

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    ActivityTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
