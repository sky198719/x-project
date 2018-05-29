/**
 * Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.plan.constants;

import java.math.BigDecimal;

/**
 * 优选理财计划常量定义类
 *
 * @version $Id: PlanConstant.java 12011 2014-12-06 02:40:26Z zhangyi $
 * @since jdk1.6
 */
public class PlanConstant {

    /**
     * 优选理财计划状态--------------0，待发布期
     */
    public static final int PREFER_STATUS_RELEASE = 0;

    /**
     * 优选理财计划状态--------------1，预定期
     */
    public static final int PREFER_STATUS_RESERVE = 1;

    /**
     * 优选理财计划状态--------------2，支付期
     */
    public static final int PREFER_STATUS_PAY = 2;

    /**
     * 优选理财计划状态--------------3，开放期
     */
    public static final int PREFER_STATUS_OPENJOIN = 3;

    /**
     * 优选理财计划状态--------------4，锁定期
     */
    public static final int PREFER_STATUS_LOCK = 4;

    /**
     * 优选理财计划状态--------------5，结束
     */
    public static final int PREFER_STATUS_QUIT = 5;

    /**
     * 优选理财计划状态--------------6，撤销
     */
    public static final int PREFER_STATUS_REVOCATION = 6;

    /**
     * 优选理财计划状态--------------7，等待进去开放加入期
     */
    public static final int  PREFER_STATUS_WAIT_OPENJOIN = 7;

    /**
     * 优选理财计划，平台最搞年收益率，目前定义为18
     */
    public static final BigDecimal PREFER_APR_MAX = new BigDecimal(18);


    /**
     * _类型，1：_季季盈
     */
    public static final int SCHEME_TYPE_ONE = 1;
    public static final String SCHEME_TYPE_NAME_ONE = "_季季盈";

    /**
     * _类型，2：_双季盈
     */
    public static final int SCHEME_TYPE_TWO = 2;
    public static final String SCHEME_TYPE_NAME_TWO = "_双季盈";

    /**
     * _类型，3：_年年盈
     */
    public static final int SCHEME_TYPE_THREE = 3;
    public static final String SCHEME_TYPE_NAME_THREE = "_年年盈";
}
