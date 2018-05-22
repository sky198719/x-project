package com.xxd.h5.common.enums;

/**
 * 菜单分类枚举类.
 */
public enum  MenuTypeStatus {

    /**
     * 自定义菜单
     */
    MENU_TYPE_CUSTOM(0 ,"自定义菜单"),

    /**
     * 资产相关菜单.
     */
    MENU_TYPE_ASSET(1 ,"资产菜单"),

    /**
     * 账户相关菜单.
     */
    MENU_TYPE_ACCOUNT(2 ,"账户菜单");

    private int type;

    private String desc;

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    MenuTypeStatus(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

}
