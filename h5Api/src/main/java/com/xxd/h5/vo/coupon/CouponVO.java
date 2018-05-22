package com.xxd.h5.vo.coupon;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.LinkedList;

/**
 * @author zhangshengwen
 */
@Data
public class CouponVO {

    /**
     *  总条目
     */
    private int totalCount;

    private LinkedList<Item> items = Lists.newLinkedList();

    private String hasCanUse = "0";

    @Data
    public static class Item {

        /**
         *  类型(1.优惠券 2.红包)
         */
        private int type;

        /**
         * 红包 优惠券 Id
         */
        private String id;

        /**
         *  红包优惠券的code码
         */
        private String code;
        /**
         * 红包标题
         */
        private String title;
        /**
         *  红包状态(0:可以使用 1:已使用 2:已过期)
         */

        private int status;
        /**
         *  适用范围
         */

        private String productRange;
        /**
         *  有效期开始时间
         */

        private long startDate;
        /**
         *  有效期结束时间
         */

        private long validDate;
        /**
         *  红包金额
         */

        private double amount;
        /**
         *  使用金额限制
         */

        private double amountLimit;
        /**
         *  平台限制
         */

        private String platform;
        /**
         * 是否可用(1-可用, 0-不可用)
         */

        private int canUse = 1;
    }
}
