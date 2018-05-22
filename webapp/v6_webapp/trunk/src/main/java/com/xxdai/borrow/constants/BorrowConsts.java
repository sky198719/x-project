/**
 * Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.borrow.constants;

/**
 * 描述
 *
 * @version $Id: BorrowConsts.java 18529 2015-04-30 03:55:00Z chenzhiheng $
 * @since jdk1.6
 */
public class BorrowConsts {
    //信用标
    public final static int BORROW_CREDIT_TYPE = 1;
    //推荐标
    public final static int BORROW_RECOMMEND_TYPE = 2;
    //净值标
    public final static int BORROW_NETWORTH_TYPE = 3;
    //抵押标
    public final static int BORROW_MORTGAGE_TYPE = 6;
    //新新宝
    public final static int BORROW_XXBAO_TYPE = 7;
    //新生贷
    public final static int BORROW_STUDENT_TYPE = 8;
    //新商贷
    public final static int BORROW_BUSINESS_TYPE = 9;
    //新房贷
    public final static int BORROW_HOUSE_TYPE = 10;
    //菁英贷
    public final static int BORROW_ELITE_TYPE = 11;
    //新网贷
    public final static int BORROW_NET_TYPE = 12;
    //票小宝
    public final static int BORROW_BILL_TYPE = 13;
    //新车贷
    public final static int BORROW_CAR_TYPE = 14;
    
    //分期贷
    public final static int BORROW_NEWMALL_TYPE = 15;
    
    //新手专享
    public final static int BORROW_XSB_TYPE=16;

    public static final String BORROW_DOC_VERSION_20160921 = "20160921";// 车房贷费率调整
    
    public final static String XXB_BORROW_XXB_TYPE="1";
    public final static String XXB_BORROW_XSB_TYPE="2";

    /**
     * *************************************************奖励类型**********************************
     */

    //无奖励
    public final static int BORROW_AWARD_NO_TYPE = 0;

    //金额奖励
    public final static int BORROW_AWARD_FUNDS_TYPE = 1;

    //百分比金额奖励
    public final static int BORROW_AWARD_PERCENT_TYPE = 2;


    /**
     * *************************************************还款方式**********************************
     */

    //等额本息
    public final static int BORROW_ACPI_PAYMENT_METHOD = 1;

    //付息还本
    public final static int BORROW_RCWI_PAYMENT_METHOD = 2;

    //还本付息
    public final static int BORROW_RCAI_PAYMENT_METHOD = 4;

    //其他还款方式
    public final static int BORROW_OTHER_PAYMENT_METHOD = 3;

    //按月付息，到期通过债权转让退出
    public final static int BORROW_RCWI_MONTHLY_PAYMENT_METHOD = 5;

    //按季付息，到期通过债权转让退出
    public final static int BORROW_RCWI_SEASON_PAYMENT_METHOD = 6;

}
