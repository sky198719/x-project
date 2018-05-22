package com.xxd.h5.common.enums;

/**
 * 菜单类型对照.
 */
public enum MenuCodeEnum {


    MENU_CODE_ZCZL("ZCZL", "资产总览"),
    MENU_CODE_ZHGL("ZHGL", "账户管理"),
    MENU_CODE_YECZ("YECZ", "余额充值"),
    MENU_CODE_DQTZ("DQTZ", "当前投资"),
    MENU_CODE_HKJH("HKJH", "回款计划"),
    MENU_CODE_LSTZ("LSTZ", "历史投资"),
    MENU_CODE_JYJL("JYJL", "交易记录"),
    MENU_CODE_WDHB("WDHB", "我的红包"),
    MENU_CODE_WDXXB("WDXXB", "我的新新币"),
    MENU_CODE_ZJTX("ZJTX", "资金提现"),
    MENU_CODE_WDTQ("WDTQ", "我的特权"),
    MENU_CODE_AQZX("AQZX", "安全中心");


    /**
     * 菜单code.
     */
    private String code;

    /**
     * 对类型的描述性信息.
     */
    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    MenuCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    MenuCodeEnum() {
    }
}
