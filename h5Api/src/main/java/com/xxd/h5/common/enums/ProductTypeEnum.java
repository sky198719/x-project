package com.xxd.h5.common.enums;

/**
 * 限定产品类型枚举字典.
 */
public enum ProductTypeEnum {

    /**
     * _.
     */
    PRODUCT_RRY(1, "_"),

    /**
     * 步步高升.
     */
    PRODUCT_BBGS(2, "步步高升"),

    /**
     * 七天大胜.
     */
    PRODUCT_QTDS(3, "七天大胜"),
    /**
     * _.
     */
    PRODUCT_YJDJ(4, "_"),

    /**
     * _.
     */
    PRODUCT_XYB(5, "_"),

    /**
     * _.
     */
    PRODUCT_YYP(6, "_"),

    /**
     * 散标.
     */
    PRODUCT_BID(7, "散标"),

    /**
     * 债券.
     */
    PRODUCT_ZQ(8, "债券"),


    /**
     * 消费贷.
     */
    PRODUCT_XFD(9, "消费贷"),

    /**
     * 债券.
     */
    PRODUCT_XS30T(10, "新手产品30天"),

    /**
     * 消费贷.
     */
    PRODUCT_XSB(16, "新手标"),


    ;


    private int type;

    private String name;

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    ProductTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String getNameByType(int type) {
        for (ProductTypeEnum productTypeEnum : ProductTypeEnum.values()) {
            if (type == productTypeEnum.getType()) {
                return productTypeEnum.getName();
            }
        }
        return "";
    }
}
