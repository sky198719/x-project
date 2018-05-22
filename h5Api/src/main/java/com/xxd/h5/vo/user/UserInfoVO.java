package com.xxd.h5.vo.user;

import com.google.common.collect.Lists;
import com.xxd.h5.vo.config.MenuVO;
import lombok.Data;

import java.util.List;

/**
 * 用户信息DTO.
 *
 * @author zhangshengwen
 */
@Data
public class UserInfoVO {

    private String token;

    /**
     * 用户id
     */

    private String userId;

    /**
     * 用户名
     */

    private String userName;

    private String mobile;

    /**
     * 是否设置支付密码
     */

    private String hasPayPassword = "0";

    /**
     * 用户头像
     */

    private String iconUrl;

    /**
     * 用户注册时间
     */

    private long registDate;

    /**
     * 用户资料完整度
     */

    private String infoPercent;

    /**
     * 用户投资金额
     */

    private String investmentAmount;

    /**
     * 用户余额
     */

    private String availableBalance;

    /**
     * 总资产
     */

    private String totalAssets;

    /**
     * 用户累计收益
     */

    private String accumulatedIncome;

    /**
     * 用户待收益
     */

    private String dueInIncome;

    /**
     * 最后登录时间
     */

    private long lastLoginTime;

    /**
     * 绑定理财人员的编号
     */

    private String vipCode;

    /**
     * 用户邮箱
     */

    private String email;

    /**
     * 0-未开户,1-已开户
     */

    private String openAccountStatus = "0";

    /**
     * 0-未风险评测,1-已风险评测
     */

    private String riskExamStatus = "0";

    /**
     * 0-未实名认证,1-已实名认证
     */

    private String isRealName = "0";

    /**
     * 0 待认证 5第三方无法认证 1认证通过 2认证失败 3认证取消
     */

    private String realNameStatus = "";

    /**
     * 富有开户手机号
     */

    private String loginId;

    /**
     * 新新币的数量
     */

    private String xxbNum;

    /**
     * 优惠券可用数量
     */
    private Integer couponCanUseNum;

    /**
     * 用户评测次数
     */
    private String testCount;
    /**
     * 用户评测类型
     */
    private String typeName;
    /**
     * 评测限额
     */
    private String quota;
    /**
     * 用户剩余评测次数
     */
    private String count;
    /**
     * 重新评测时间
     */
    private String nextTestTime;
    /**
     * 平台可投额度
     */
    private String surplusAmount;
    /**
     * 用户总评测次数
     */
    private String sumCount;

    private List<MenuVO.Button> menus = Lists.newArrayList();

}
