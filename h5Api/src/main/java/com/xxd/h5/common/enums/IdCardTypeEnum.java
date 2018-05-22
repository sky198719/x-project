package com.xxd.h5.common.enums;

/**
 * 证件类型.
 */
public enum IdCardTypeEnum {

    ID_CARD_1(1, "身份证"),

    ID_CARD_2(2, "澳门特别行政区护照"),

    ID_CARD_3(3, "台湾居民往来大陆通行证"),

    ID_CARD_4(4, "澳门特别行政区护照");

    private int type;

    private String desc;

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    IdCardTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
