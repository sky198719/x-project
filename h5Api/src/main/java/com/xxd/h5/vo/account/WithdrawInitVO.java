package com.xxd.h5.vo.account;

import lombok.Data;

/**
 * 提现初始化参数VO.
 */
@Data
public class WithdrawInitVO {

    // GET /user/bank/userCheckingBankCardInfo
    // 银行卡状态 0--不存在正在审核的银行卡 1--存在正在审核的银行卡
    private String cardStatus = "0";

    // 卡号
    private String cardNo;

    // 银行卡对应的银行code
    private String bankCode;

    // tips 提示信息
    private String tips;

    // 可用余额
    private String balanceAmount;

    // 可提现金额
    private String withdrawAmount;

    // 最小提现金额
    private String minAmount;

    // 免费提现次数
    private String freeWithdrawCount;

    // 剩余提现次数
    private String userWithdrawCount;

    // 系统配置本月最大可提现次数
    private String configWithdrawCount;

    // 是否白名单（1白名单 0 非白名单）
    private int isWhiteCash;

    // h5提现说明页面.
    private String tipH5url;
}
