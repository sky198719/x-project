package com.xxd.h5.vo.finance;

import lombok.Data;

/**
 * 标的加入征信信息
 *
 * @author zhangshengwen
 * @date 2018/1/23
 */
@Data
public class BidInfoVo {


    /**
     * "userId":9527,
     * "overdueLoanNum":5,
     * "overdueLoanMonth":12,
     * "overdueLoanAccount":1,
     * "overdueLoanCardMonth":6,
     * "operationStatus":"良好",
     * "repaymentCapacity":"暂无变化",
     * "punish":"无",
     * "overdueLoanInfo":"未逾期",
     * "updateTime":"2018-01-11 14:01:40.0",
     * "income":"6000-8000元",
     * "liabilities":"4000-6000元"
     */

    private LoanInfo loanInfo;

    private BidInfo bidInfo;

    private int loanStatus;


    @Data
    public static class LoanInfo {

        private String userId;

        private String overdueLoanNum;

        private String overdueLoanMonth;

        private String overdueLoanAccount;

        private String overdueLoanCardMonth;

        private String operationStatus;

        private String repaymentCapacity;

        private String punish;

        private String overdueLoanInfo;

        private String updateTime;

        private String income;

        private String liabilities;

    }


    /**
     * "age": "26",
     * "  companyIndustry": "无",
     * "  occupation": "个体商户",
     * "  source": "1"     4:峰融的
     */
    @Data
    public static class BidInfo {

        private String age;

        private String companyIndustry;

        private String occupation;

        private String source;

    }

}
