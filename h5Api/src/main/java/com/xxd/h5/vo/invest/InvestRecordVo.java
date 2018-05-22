package com.xxd.h5.vo.invest;

import lombok.Data;

import java.util.List;

/**
 * @author zhangshengwen
 * @date 2018/1/19
 */
@Data
public class InvestRecordVo {


    private List<Item> items;


    @Data
    public static class Item {


        private String apr;

        private String productId;

        private String endDate;

        private String investmentAmount;

        private String channel;

        private String closeterm;

        private String floatApr;

        private String opensalebegin;

        private String periodName;

        private String productStatus;

        private String joinId;

        private String opensaleend;

        private String type;

        private String addDate;

        private boolean matching;

        private String interest;

        private String name;

        private String account;

        private String productType;

        private String status;

        private String remaCapital;

        private String holdingDays;

        private String stepApr;

        private String maxApr;

        private String minApr;

        private String outDate;

        private String actualPrincipalAndInterest;

    }
}
