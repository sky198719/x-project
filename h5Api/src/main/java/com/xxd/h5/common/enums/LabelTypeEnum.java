package com.xxd.h5.common.enums;

/**
 * 债券类型.
 */
public enum LabelTypeEnum {

    /**
     * 新商贷.
     */
    LABEL_TYPE_XSD("ic-xsd", "新商贷"),

    /**
     * 车主贷.
     */
    LABEL_TYPE_XCD("ic-xcd", "车主贷"),

    /**
     * 房主贷.
     */
    LABEL_TYPE_XFD("ic-xfd", "房主贷"),

    /**
     * 消费金融消金贷
     */
    LABEL_TYPE_XFJRXJD("ic-xfjrxjd", "消费金融消金贷");

    private String label;

    private String desc;

    public String getLabel() {
        return label;
    }

    public String getDesc() {
        return desc;
    }

    LabelTypeEnum(String label, String desc) {
        this.label = label;
        this.desc = desc;
    }

}
