package com.xxd.h5.common.enums;

/**
 * 投资状态.
 */
public enum InvestStatusEnum {

    /**
     * 有过投资记录.
     */
    INVEST_STATUS_YES(Boolean.TRUE, "有过投资记录"),

    /**
     * 无投资记录.
     */
    INVEST_STATUS_NO(Boolean.FALSE, "无投资记录");

    private boolean status;

    private String desc;

    public boolean getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

    InvestStatusEnum(boolean status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
