package com.xxd.h5.common.enums;

/**
 * Banner的枚举类型.
 */
public enum  BannerTypeEnum {
    /**
     * 启动页banner.
     */
    BANNER_TYPE_START(1, "启动页banner"),

    /**
     * 首页Banner.
     */
    BANNER_TYPE_HOME(2, "首页banner"),

    /**
     * 理财列表页广告.
     */
    BANNER_TYPE_FINANCIAL(3, "理财列表banner"),

    /**
     * 奖励页banner.
     */
    BANNER_TYPE_PRIZE(4, "奖励页banner");

    private int type;

    private String desc;

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    BannerTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
