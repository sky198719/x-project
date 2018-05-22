package com.xxd.h5.vo.finance;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 产品详情VO.
 *
 * @author zhangshengwen
 */
@Data
public class ProductDetailVO {

    /**
     * 产品加入记录
     */
    //private List<JoinRecord> joinRecords = Lists.newArrayList();

    private ProductDetail productDetail;

    private String accumulatedInvestors;

    private String totalCount;


    @Data
    public static class ProductDetail {

        private String productId;

        private String joinId;

        private String name;

        private String use;

        private String productNo;

        /**
         * 期数名称
         */
        private String periodName;

        private double plannedAnnualRate;

        private double floatingRate;

        private double plannedAnnualRateFrom;

        private double plannedAnnualRateTo;

        private double plannedAnnualStepRate;

        private String leastPeriod;

        private String leastPeriodUnit;

        private long activitedStartDate;

        private String accumulatedInvestors;

        private long activitedEndDate;

        private String leastInvestAmount;

        private String plannedAmount;

        private String leftAmount;

        private String status;

        private long currentTime = System.currentTimeMillis();

        private int productType;

        private String tipAction;

        private String introduction;
        /**
         * 散标债券合同链接
         */
        private String productContractH5url;

        /**
         * 产品服务协议
         */
        private String productProtocolH5url;
        /**
         * 产品介绍
         */
        private String productH5url;
        /**
         * 产品QA
         */
        private String productQAH5url;
        /**
         * 风险提示
         */
        private String dangerTipH5url;
        /**
         * 散标债券借款详情
         */
        private String borrowDetailH5url;
        /**
         * 还款保障措施
         */
        private String repaymentGuaranteeH5url;

        private String haveRedPacket = "0";

        private String description;

        private String selectedLabel;

        /**
         * 违约金百分比
         */
        private double forfeitPercent;

        private String selectedPeriod;

        private String investableAmount;

        private String holdingAmount;

        private String transferPrice;

        private String label;

        private String labelName;
        /**
         * 还款方式
         */
        private String repaymentMethod;
        /**
         * 投标奖励
         */
        private String investmentAward;

        /**
         * 起息时间
         */

        private String startCalInterestDate;
        /**
         * // 待收本息
         */

        private String dueInPrincipalAndInterest;
        /**
         * // 是否可以购买七天大胜
         */
        private String allowBuy;
        /**
         * // 已折让的价格
         */
        private String allowanceAmount;

        private List<Item> items = Lists.newArrayList();

        private JSONObject periods = new JSONObject();
        /**
         * // 债券回款计划
         */
        private List<Repayment> expctedCollections = Lists.newArrayList();
        /**
         *  // 标的状态
         */
        private String borrowStatus;
        /**
         * // 最大可投资金额
         */
        private String mostInvestAmount;
        /**
         * // 下一个还款日
         */
        private long nextRepaymentDay;
        /**
         * // 转让截止日期
         */
        private long endTime;

        /**
         * 风险等级
         */
        private String riskGrade;
        /**
         * // 产品适用
         */
        private String productApplyDesc;
        /**
         * // 是否活动标
         */
        private String activitedType;
        /**
         * // 活动备注
         */
        private String activityRemarks;
        /**
         *  // 步步高升退出时的步长.
         */
        private double quitsteps;
        /**
         * // 距离转让截止天数
         */
        private long remainingDays;
        /**
         * // 产品分享url.
         */
        private String shareUrl;
        /**
         * // 借款详情id
         */
        private String borrowId;
        /**
         * // 标的版本标识 0老版本 1新版本
         */
        private String versionSign;
    }


    /**
     * 月月派下的期数.
     */
    @Data
    public static class Item {
        private String periodName;
        private String productId;
        private String leftAmount;
        private int step;
        private String plannedAmount;
        private int status;
    }

    /**
     * 新元宝期数.
     */
    @Data
    public static class Period {
        private int period;
        private String productId;
        private String periodName;
        private String leftAmount;
        private int step;
        private String plannedAmount;
        private int status;
    }

    public static class JoinRecord {

        private String investor;

        private String amount;

        public String getInvestor() {
            return investor;
        }

        public void setInvestor(String investor) {
            this.investor = investor;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            BigDecimal bd = new BigDecimal(amount);
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            this.amount = bd.toString();
        }
    }

    /**
     * 债券回款计划
     */
    @Data
    public static class Repayment {
        /**
         * // 回款时间
         */

        private String paymentDate;

        /**
         * // 待收本金
         */
        private String repayCapital;

        /**
         * // 待收利息
         */
        private String repayInterest;

        /**
         * // 0-待回款 1-已经回款
         */
        private String status;
    }
}
