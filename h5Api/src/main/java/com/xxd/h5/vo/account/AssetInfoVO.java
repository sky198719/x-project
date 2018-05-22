package com.xxd.h5.vo.account;

import lombok.Data;

/**
 * 资产总览VO.
 */
@Data
public class AssetInfoVO {

    private int status;

    private String userImg;

    private String userName;

    private String userLevel;

    private String totalAssets;

    private String availableBalance;

    private String dueInIncome;

    private String accumulatedRechargeAmount;

    private String accumulatedWithdrawAmount;

    private String investmentAmount;

    private String accumulatedIncome;

    private String frozenAmount;

}
