package com.xxd.h5.common.enums;

/**
 * 充值通道.
 */
public enum RechargeChannelEnum {

    RECHARGE_CHANNEL_QUCIK_RECHARGE(14, "快捷充值"),

    RECHARGE_CHANNEL_ONLINE_BANING_RECHARGE(13, "网银充值");

    private int type;

    private String desc;

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    RechargeChannelEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
