package com.xxd.h5.common.enums;

/**
 * 运营报告类型.
 */
public enum ReportTypeEnum {

    /**
     * 年度报告.
     */
    REPORT_TYPE_YEAR(1, "年度报告"),

    /**
     * 季度报告.
     */
    REPORT_TYPE_QUARTER(2, "季度报告"),

    /**
     * 月度报告.
     */
    REPORT_TYPE_MONTH(3, "月度报告");

    private int type;

    private String name;

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    ReportTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

}
