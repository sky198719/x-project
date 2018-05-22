package com.xxd.h5.vo.redpackage;

import lombok.Data;

import java.util.List;

import static com.xxdai.tools.CodeEnumCollection.DiscountCoupon;

/**
 * 红包、加息券
 * created by xiguoding on 2018/4/28 下午3:22
 */
@Data
public class Coupons {
    /* 当前页数 */
    private int pageIndex;
    /* 当前页条数 */
    private int pageSize;
    /* 总页数 */
    private int totalPages;
    /* 总条数 */
    private int totalSize;
    private List<Coupon> couponList;

    @Data
    public static class Coupon {
        /* 金额 */
        private int amount;
        /* 兑换码 */
        private String code;
        /* 红包、加息券id */
        private String couponId;
        /* 有效使用结束时间 */
        private long effectiveEndTime;
        /* 有效使用开始时间 */
        private long effectiveStartTime;
        /* 是否有效 */
        private String isEffective;
        /* 是否活动标除外 */
        private String isEvent;
        /* 是否使用 */
        private String isUsed;
        /* 加息券名称 */
        private String name;
        /* 最大投资限额（加息券特有） */
        private int maxInvest;
        /* 最小投资限额（加息券特有） */
        private int minInvest;
        /* 加息券加息天数（加息券特有） */
        private int numRaiseDays;
        /* 使用平台 */
        private String[] platform;
        /* 使用范围 */
        private List<ProductScope> productScope;
        /* 抵用金额（红包特有） */
        private int quota;
        /* 加息利率 */
        private int raiseInterestRate;
        /* 状态描述 */
        private String statusDescribe;
        /* 类型 */
        private DiscountCoupon type;

        @Data
        public static class ProductScope {
            /* 可使用产品类型 */
            private int productType;
            /* 可使用产品期次 */
            private int[] termsList;
        }
    }
}
