/**
 * Copyright (c) 2015, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.fund.constants;

/**
 * 描述
 *
 * @version $Id: FundConstant.java 2015/9/5 17:17 $
 * @since jdk1.6
 */
public class FundConstant {
	
	/**基金交易类型，1：申购 2：赎回**/
    public static final int FUND_TRADE_TYPE_PURCHASE = 1;//基金申购
    
    /**基金交易发起人，1：用户  2：系统**/
    public static final int FUND_TRADE_INITIATOR_USER = 1;//用户发起
    
    public static final int FUND_TRADE_INITIATOR_SYSTEM = 2;//系统发起
	
    /**日日盈交易发起人类型，1用户*/
    public static int FUND_USERTRADE_INITIATOR_USER = 1;
    /**日日盈交易发起人类型，2系统*/
    public static int FUND_USERTRADE_INITIATOR_SYS = 2;
    /**日日盈交易Token失效错误*/
    public static int FUND_TRADE_TOKEN_ERROR = 301;

    /**日日盈申购开关*/
    public static String FUND_PURCHASE_SWITCH = "FUND_PURCHASE_SWITCH";
    /**日日盈赎回开关*/
    public static String FUND_RANSOM_SWITCH = "FUND_RANSOM_SWITCH";
}
