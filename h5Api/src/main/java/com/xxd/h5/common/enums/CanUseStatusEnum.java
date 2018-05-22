package com.xxd.h5.common.enums;

public enum CanUseStatusEnum {

    CAN_USE(1, "可用"),

    USED(0, "不可用");

    private int status;

    private String desc;

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

    CanUseStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

}
