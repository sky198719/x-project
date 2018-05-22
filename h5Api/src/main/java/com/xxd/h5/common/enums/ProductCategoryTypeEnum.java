package com.xxd.h5.common.enums;

/**
 * 理财产品大分类.
 * @author zhangshengwen
 */
public enum ProductCategoryTypeEnum {

    PRODUCT_CATEGORY_TYPE_PRODUCT(0, "封装化产品"),


    PRODUCT_CATEGORY_TYPE_BID(1, "散标"),

    PRODUCT_CATEGORY_TYPE_ZQ(2, "债券转让");

    private int type;

    private String name;

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    ProductCategoryTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }
}
