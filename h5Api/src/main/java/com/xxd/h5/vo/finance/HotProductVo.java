package com.xxd.h5.vo.finance;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * 热门理财 包含:步步高升、七天大胜、月进斗金、新元宝、月月派)
 * 七天大胜 3 明星产品 限购一次
 * 步步高升 2 逐月增息 100元起投 定活两变 存取灵活
 * 月进斗金 4 定期 每日10点、20点限时抢购
 * 新元宝 5 定期 投资次日起息，到期还本付息
 * 月月派 6 定期 投资次日起息，先息后本
 *
 * @author zhangshengwen
 */
@Data
public class HotProductVo {

    private Boolean isHaveValue;

    private String productId;

    private String name;
    /**
     * // 预计年化利率
     */
    private String plannedAnnualRate;

    private String floatingRate;

    private String plannedAnnualRateFrom;

    private String plannedAnnualRateTo;

    private String leastPeriod;

    private String leastPeriodUnit;
    /**
     * // 开始时间
     */
    private long activitedStartDate;
    /**
     * // 结束时间
     */
    private long activitedEndDate;
    /**
     * // 购买人数
     */
    private long accumulatedInvestors;
    /**
     * // 至少投资额度
     */
    private String leastInvestAmount;
    /**
     * // 计划额度
     */
    private String plannedAmount;
    /**
     * // 剩余额度
     */
    private String leftAmount;
    /**
     * // 状态：200-待发售 201-可购买 202	-已售完
     */
    private String status;

    private long currentTime = System.currentTimeMillis();
    /**
     * // 产品类型
     */
    private int productType;
    /**
     * // 专享属性
     */
    private String tipAction;
    /**
     * // 介绍
     */
    private String introduction;
    /**
     * // 是否活动标
     */
    private String activitedType;
    /**
     * // 活动备注
     */

    private String activityRemarks;

    private List<Item> multipleDeadline = Lists.newArrayList();

    @Data
    public static class Item {

        private String productId;

        private String name;
        /**
         * 预计年化利率
         */
        private String plannedAnnualRate;

        private String floatingRate;

        private String plannedAnnualRateFrom;

        private String plannedAnnualRateTo;

        private String leastPeriod;

        private String leastPeriodUnit;
        /**
         * 开始时间
         */

        private long activitedStartDate;
        /**
         * 结束时间
         */

        private long activitedEndDate;
        /**
         *购买人数
         */

        private long accumulatedInvestors;
        /**
         * 至少投资额度
         *
         */



        private String leastInvestAmount;
        /**
         *  计划额度
         *
         */

        private String plannedAmount;
        /**
         *  剩余额度
         */

        private String leftAmount;
        /**
         * 状态
         */
        private String status;

        private long currentTime = System.currentTimeMillis();
        /**
         * 产品类型
         */
        private int productType;

        private String tipAction;

        private String introduction;
        /**
         * 是否活动标
         */
        private String activitedType;
        /**
         * 是否加息
         */
        private boolean increaseInterest;
        /**
         * 期数
         */
        private String periodName;

        /**
         * 活动标签内容
         */
        private String activityRemarks;
    }
}
