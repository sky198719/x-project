package com.xxd.h5.common.enums;

/**
 * 活动状态枚举类.
 */
public enum  ActivityStatusEnum {

    /**
     * 进行中.
     */
    ACTIVITY_STATUS_ING(1, "进行中"),

    /**
     * 预热.
     */
    ACTIVITY_STATUS_WARMUP(2, "预热"),

    /**
     * 结束.
     */
    ACTIVITY_STATUS_END(3, "结束"),

    /**
     * 未知.
     */
    ACTIVITY_STATUS_UNKNOWN(4, "未知");


    private int status;

    private String desc;

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

    ActivityStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
