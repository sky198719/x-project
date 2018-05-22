package com.xxd.h5.vo.account;

import lombok.Data;

/**
 * 用户开户信息.
 * @author zhangshengwen
 */
@Data
public class AccountRealNameVO {


    private String nickName;


    private String phoneNum;

    // 用户类型
    private String userType;

    // 姓名
    private String userName;

    // 身份证号码
    private String userIdCard;

    // 银行卡号码
    private String bankCardNumber;

    // 银行名称
    private String bankCardName;

    // 银行代码
    private String bankCardCode;

    // 用户协议H5页面Url
    private String userAgreementUrl = "";

    // 用户正面照片url
    private String cardFrontUrl = "";

    // 用户反面照片url
    private String cardBackUrl = "";

    // 银行卡状态 0-审核中
    private String bankState;

    // 开户方式：系统批量开户--0  用户自主开户--1
    private String openAccountType;

    // 系统自动进行开户的用户是否进行过提醒：0--未提醒  1--已提醒
    private String hasInitRemind="";
}
