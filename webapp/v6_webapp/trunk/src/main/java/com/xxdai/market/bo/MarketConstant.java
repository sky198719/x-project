/** 
* Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
*/
package com.xxdai.market.bo;

/** 
 * 新新商城常量类
 *
 * @since jdk1.6
 * @version $Id: MarketConstant.java 18938 2015-05-11 03:25:32Z jinzhiguang $
 */
public class MarketConstant { 
	
	/************************商城商品类型********************************/
	 
	public static int XXD_MARKET_YIRTUAL_GOODS = 0;//虚拟物品
	
	public static int XXD_MARKET_ORDINARY_GOODS = 1;//普通物品
 
	public static int XXD_MARKET_FRAGILE_GOODS = 2;//易碎物品
 
	public static int XXD_MARKET_VALUABLES_GOODS = 3;//贵重物品
	
	/************************商城订单类型********************************/
	 
	public static int XXD_MARKET_MATERIAL_ORDER_TYPE = 0;//实物类型
	 
	public static int XXD_MARKET_CREDIT_CARD_ORDER_TYPE = 1;//信用卡类型
	
	public static int XXD_MARKET_PHONE_RECHARGE_ORDER_TYPE = 2;//手机号码充值
	
	public static int XXD_MARKET_Q_COIN_ORDER_TYPE = 3;//Q币充值

    public static int XXD_MARKET_NEWMALL_ORDER_TYPE = 5;//分期购
    
    public static int XXD_MARKET_PRIZE_ORDER_TYPE = 6;//分期购抽奖
	
	/************************商城订单支付类型********************************/
	public static int XXD_MARKET_XINXIN_COIN_PAY_TYPE = 0;//新新币支付
	 
	public static int XXD_MARKET_ACCOUNT_PAY_TYPE = 1;//余额支付
	
	
	/************************商城订单支付状态********************************/
	public static int XXD_MARKET_ALREADLY_NOPAY_ORDER_STATUS = -1;//待付款(首付)
	
	public static int XXD_MARKET_ALREADLY_PAY_ORDER_STATUS = 1;//已支付/待确认
	
	public static int XXD_MARKET_ALREADLY_AFFIRM_ORDER_STATUS = 2;//已确认
	
	public static int XXD_MARKET_ALREADLY_REJECT_ORDER_STATUS = -2;//订单驳回
	
	public static int XXD_MARKET_ALREADLY_CUSTOMER_AFFIRM_ORDER_STATUS = 3;//客户确认
	 
	public static int XXD_MARKET_ALREADLY_AFFIRM_SEND_STATUS = 4;//确认发货
	
	public static int XXD_NEWMALL_WAIT_SEND_GOODS_STATUS = 5;//分期购待发货
	
	/************************商城订单错误CODE********************************/
	public static int XXD_MARKET_ILLEGAL_OPERATION_ERROR   = -1000;//非法支付方式
	
	public static int XXD_MARKET_NOT_ALREADLY_AFFIRM_STATUS_ERROR   = -1500;//订单处于非确认状态错误
	
	public static int XXD_ACCOUNT_FREEZE_ERROR = -2000;//用户账户冻结错误
	
	public static int XXD_COULD_NOT_WITHDRAWALS_ERROR = -2500;//用户账户禁止提现错误
	
	public static int XXD_BLACK_LIST_ERROR = -3000;//用户处于黑名单错误
	
	public static int XXD_ILLEGA_PRICE_ERROR = -3500;//价格计算异常错误
	
	public static int XXD_NOT_ENOUGH_XINXIN_COINS_ERROR = -4000;//新新币不足错误
	
	public static int XXD_NOT_ENOUGH_ACCOUNT_ERROR = -4500;//余额不足错误
	
	public static int XXD_NOT_ENOUGH_NEWMALL_PRIZE_ERROR = 0;//余额不足错误
	
	
	/************************商城订单错误信息********************************/ 
	public static String XXD_MARKET_ILLEGAL_OPERATION_ERROR_MSG   = "支付类型错误，非法操作，请重试";
	
	public static String XXD_MARKET_NOT_ALREADLY_AFFIRM_STATUS_ERROR_MSG   = "只有确认状态的订单才能进行确认发货操作";
	
	public static String XXD_ACCOUNT_FREEZE_ERROR_MSG = "账户已被冻结，无法购买,请联系客服";//用户账户冻结错误信息
	
	public static String XXD_COULD_NOT_WITHDRAWALS_ERROR_MSG = "您的账户一年内禁止提现,如有疑问请联系客服!";//用户账户禁止提现错误信息
	
	public static String XXD_BLACK_LIST_ERROR_MSG = "您的账户存在异常,暂时被冻结,请联系客服!";//用户处于黑名单错误信息
	
	public static String XXD_ILLEGA_PRICE_ERROR_MSG = "商品计算价格异常，请重试或联系客服";//价格计算异常错误信息
	
	public static String XXD_NOT_ENOUGH_XINXIN_COINS_ERROR_MSG = "您的新新币不足!";//新新币不足错误
	
	public static String XXD_NOT_ENOUGH_ACCOUNT_ERROR_MSG = "您的账户余额不足!";//余额不足错误
	
	public static String NEW_BASE_DAO_CONCURRENCY_ERROR_MSG= "商品数量不够或页面信息过期,请重试或联系客服";//WebService操作失败
	
	public static String NEW_BASE_DAO_CONCURRENCY_ERROR_MSG2= "并发错误,请重试或联系客服";
	
	public static String NEW_BASE_DAO_NEWMALL_PRIZE_ERROR_MSG2= "";
	/************************商城订单管理权限********************************/
	
	public static String XXD_MARKET_DEPARTMENT_ROLE_STR = ",2,4,5,";//部门权限 客户，财务
	
	/************************商品管理权限*******************************/
	public static String MARKET_DEPARTMENT_STR=",2,4,";//部门权限，IT，客服
	
	/************************商城banner类型********************************/
	public static String MARKET_NEWMALL_BANNER_TYPE = "5";//分期购banner
	
}
