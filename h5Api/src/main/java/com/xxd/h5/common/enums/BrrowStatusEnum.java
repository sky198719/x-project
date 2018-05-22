package com.xxd.h5.common.enums;

/**
 * 标的状态.
 */
public enum  BrrowStatusEnum {


    /**
     * 0: 暂存
     * 1: 审核中
     * 2: 招标中
     * 3: 满标复审
     * 4: 还款中
     * 5: 还款结束
     * -1: 流标
     * -2: 撤销
     * -3: 审核失败
     * -4: 签约失败
     */

    OPENING(229, "可购买"),
    SETTLE(208, "已结清"),
    TRANSFERED(209, "已转让"),
    SATISFIED_BID(203, "等待满标"),
    ABANDONED_BID(204, "流标"),
    CAN_TRANSFER(229, "可转让"),
    CANNOT_TRANSFER(228, "不可转让"),
    NO_ALLOW_TRANSFER(230, "不符合转让条件"),
    TRANSFERING(231, "转让中"),
    SATISFIED_REVIEW_BID(241, "满标复审");


    private int status;

    private String desc;

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

    BrrowStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
