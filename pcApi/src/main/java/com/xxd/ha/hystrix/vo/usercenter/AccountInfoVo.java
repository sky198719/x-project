package com.xxd.ha.hystrix.vo.usercenter;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * http://dev.xxd.com/tradeCenter/swagger-ui.html#!/AssetStatistics/getAssetStatisticsByTokenUsingGET
 * @author zhangshengwen
 * @date 2018/1/9
 */
@Data
public class AccountInfoVo {

    private MapAccount mapAccount = new MapAccount();

    private MapItem qitiandashengMap = new MapItem();

    private MapItem sanbiaoMap = new MapItem();

    private MapItem stepMap = new MapItem();

    private MapItem xfdMap = new MapItem();

    private MapItem xscp30tMap = new MapItem();

    private MapItem xinshoubiaoMap = new MapItem();

    private MapItem xinyuanbaoMap = new MapItem();

    private MapItem yuejindoujinMap = new MapItem();

    private MapItem yypMap = new MapItem();

    private MapItem rryMap = new MapItem();

    private MapItem other = new MapItem();

    /**
     * 各个产品投资占比金额.
     */
    private List<Percent> percents = Lists.newArrayList();

    @Data
    public static class MapAccount {
        /**
         * 累计活动奖励
         */
        private Float activityRecharge = 0F;
        /**
         * 累计推广奖励充值
         */
        private Float awardRecharge = 0F;
        /**
         * 累计现金充值
         */
        private Float cashRecharge = 0F;
        /**
         * 累计新新币兑换
         */
        private Float cashingRecharge = 0F;
        /**
         * 累计提现
         */
        private Float drawTotal = 0F;
        /**
         * 累计待收收益
         */
        private Float dueInInterestSumTotal = 0F;
        /**
         * 冻结金额
         */
        private Float frozen = 0F;
        /**
         * 借款账户_累计借款
         */
        private Float loanSum = 0F;
        /**
         * 借款账户_待还总额
         */
        private Float repaymentSum = 0F;
        /**
         * 本月待收收益
         */
        private Float sumCollectionThisMonth = 0F;
        /**
         * 本月累计赚取
         */
        private Float sumEarnedThisMonth = 0F;
        /**
         * 今年累计赚取
         */
        private Float sumEarnedThisYear = 0F;
        /**
         * 七天大胜
         */
        private Float sumQiTianDaSheng = 0F;
        /**
         * 散标
         */
        private Float sumSanBiao = 0F;
        /**
         * 步步高升
         */
        private Float sumStep = 0F;

        /**
         * _
         */
        private Float sumRry = 0F;

        /**
         * 消费贷
         */
        private Float sumXFD = 0F;
        /**
         * 新手标
         */
        private Float sumXinShouBiao = 0F;
        /**
         * _
         */
        private Float sumXinYuanBao = 0F;

        private Float sumYueJinDouJin = 0F;

        private Float sumYyp = 0F;

        /**
         * 新手专享.
         */
        private Float sumXSCP30T = 0F;

        /**
         * 资产总额
         */
        private Double sumTotal = 0D;

        /**
         * 可用余额
         */
        private Float usable = 0F;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MapItem {

        /**
         * 累计收益.
         */
        @JSONField(name = "COLLECTEDINTEREST")
        private Float collectedInterest = 0F;

        /**
         * 待收收益.
         */
        @JSONField(name = "COLLECTINTEREST")
        private Float collectInterest  = 0F;

        /**
         * 累计投资.
         */
        @JSONField(name = "EFFECTIVEMONEY")
        private Float effectiveMoney = 0F;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Percent {

        private String name;

        private Float sum = 0F;
    }
}
