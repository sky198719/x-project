package com.xxd.h5.vo.redpackage;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * 新手红包
 * created by xiguoding on 2018/4/27 下午7:50
 */
@Data
public class RedEnvelopes {
    /* 总条数 */
    private int totalCount;
    private List<RedEnvelope> items;

    @Data
    public static class RedEnvelope {
        /* 金额 */
        private int amount;
        /* 满多少金额可使用 */
        private int amountLimit;
        /* 使用产品 */
        private String productRange;
        /* 新手红包code */
        private String redCode;
        /* 新手红包id */
        private String redEnvelopId;
        /* 可使用开始日期 */
        private long startDate;
        /* 状态 */
        private int status;
        /* 名称 */
        private String title;
        /* 可使用截止日期 */
        private long validDate;
        /* 新手红包状态 */
        private String statusDescribe;
        /* 使用平台 */
        private String[] platform = new String[]{"WAP", "APP", "PC"};
    }
}
