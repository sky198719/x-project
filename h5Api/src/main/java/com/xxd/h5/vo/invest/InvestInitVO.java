package com.xxd.h5.vo.invest;

import lombok.Data;

/**
 * 购买产品初始化的参数.
 * @author zhangshengwen
 */
@Data
public class InvestInitVO {

    private String productName;

    /**
     *  产品类型
     */
    private String productType;

    /**
     * 可用余额
     */
    private String availableBalance;

    /**
     * 年化收益
     */
    private String plannedAnnualRate;

    /**
     * 待收本息
     */
    private String dueInPrincipalAndInterest;

    /**
     * 起投期
     */
    private String leastPeriod;

    /**
     * 起投期限单位
     */
    private String leastPeriodUnit;

    /**
     * 产品剩余金额
     */
    private String leftAmount;

    /**
     * 下一个还款日
     */
    private String nextRepaymentDay;

    /**
     * 折让价格
     */
    private String allowanceAmount;

    /**
     * 债权转让编号
     */
    private String productNo;

    /**
     * 转让价格
     */
    private String transferPrice;

    /**
     * 用户剩余可投金额
     */
    private String investableAmount;

    /**
     * 资金出借风险提示
     */
    private String dangerTipH5url;

    /**
     * 产品服务协议
     */
    private String productProtocolH5url;

    /**
     *  产品QA
     */
    private String productQAH5url;

    /**
     * 期数
     */
    private String periodNo;

    /**
     *  期号
     */
    private String periodName;

    /**
     * 散标、债券标记类型
     */
    private String label;

    /**
     *  散标、债券标记类型名称
     */
    private String labelName;

    /**
     *  起投金额
     */
    private String leastInvestAmount;

    /**
     *  剩余期限
     */
    private String remainingPeriod;

    /**
     *  加入上限
     */
    private String mostInvestAmount;

    /**
     *  自动配标委托书
     */
    private String proxyUrl;

    /**
     *  浮动利率
     */
    private String floatingRate;

    /**
     *  是否可以购买七天大胜
     */
    private String allowBuy;

    /**
     *  步步高升起息
     */
    private String plannedAnnualRateFrom;

    /**
     *  步步高升结息
     */
    private String plannedAnnualRateTo;

    /**
     *  是否活动标
     */
    private String activitedType;

    /**
     *  活动备注
     */
    private String activityRemarks;
}
