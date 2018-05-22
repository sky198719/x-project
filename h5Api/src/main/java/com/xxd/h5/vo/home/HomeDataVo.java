package com.xxd.h5.vo.home;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 *
 * @author zhangshengwen
 * @date 2017/11/17
 */
@Data
public class HomeDataVo {

    private String investStatus = "0";
    private int unreadAllNum;
    private int unreadLastType;
    private int unreadNoticeNum;
    private int unreadMessageNum;
    private List<BannerVo> banners = Lists.newArrayList();
    private HomeDataVo.WonderfulProduct wonderfulProduct = new HomeDataVo.WonderfulProduct();
    private BannerVo bottomBanner;
    private long currentTime = System.currentTimeMillis();
    private String qtdsH5url;
    private String redPackageH5url;





    @Data
    public static class WonderfulProduct {
        /**
         * 产品
         */
        private String productId;
        /**
         * 产品名称
         */
        private String name;
        /**
         * 最小投资期限
         */
        private int leastPeriod;
        /**
         * 最小投资期限的单位
         */
        private String leastPeriodUnit;
        /**
         * 活动开始时间
         */
        private long activitedStartDate;
        /**
         * 累计加入人数
         */
        private int accumulatedInvestors;
        /**
         * 计划收益率
         */
        private String plannedAnnualRate;
        private boolean increaseInterest;
        private String floatingRate;
        private String plannedAnnualRateFrom;
        private String plannedAnnualRateTo;
        /**
         * 剩余可投金额
         */
        private String leftAmount;
        /**
         * 计划可投金额
         */
        private String plannedAmount;
        /**
         * 活动结束时间
         */
        private long activitedEndDate;
        private String leastInvestAmount;
        /**
         * 产品类型
         */
        private int productType;
        /**
         * 产品提示标签
         */
        private String tipAction;
        /**
         * 产品介绍
         */
        private String introduction;
        /**
         * 状态
         */
        private String status;
        /**
         * 活动类型
         */
        private String activitedType;
        /**
         * 活动标签内容
         */
        private String activityRemarks;

    }

    @Data
    public static class ProfitProduct {

        /**
         * 产品加入id
         */
        private String id;

        /**
         * 产品名称
         */
        private String name;

        /**
         * 产品类型
         */
        private int productType;

        /**
         * 产品期数
         */
        private String periodName;

        /**
         * 产品收益
         */
        private String income;

        /**
         * 持有天数(步步高升)
          */

        private String holdingDays;
    }


}
