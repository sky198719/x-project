package com.xxd.h5.vo.account;

import lombok.Data;

/**
 * 充值初始化参数VO.
 * @author zhangshengwen
 */
@Data
public class RechargeInitVO {

    // GET /user/bank/userCheckingBankCardInfo
    /**
     * 银行卡状态 0--不存在正在审核的银行卡 1--存在正在审核的银行卡
      */
    private String cardStatus = "0";

    /**
     *  卡号
     */
    private String cardNo;

    /**
     * 银行卡对应的银行code
     */
    private String bankCode;

    /**
     * 单笔限额
     */
    private String singleLimit;

    /**
     * 单日限额
     */
    private String dailyLimit;

    /**
     * 单月限额
     */
    private String monthlyLimit;

    /**
     *  tips 提示信息
     */
    private String tips;

}
