package com.xxd.h5.common.enums;

/**
 * 密码类型.
 */
public enum PasswordTypeEnum {

    /**
     * 登录密码.
     */
    PASSWORD_TYPE_LOGIN(2, "登陆密码"),

    /**
     * 支付密码.
     */
    PASSWORD_TYPE_PAY(4, "支付密码");

    private int type;

    private String desc;

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    PasswordTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
