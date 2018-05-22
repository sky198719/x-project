package com.xxdai.person.constants;


/**
 * 绑卡返回状态码
 *
 */
public class BankConsts {

	/***************************绑卡状态******************************/
	
	//支付密码错误
	public static int BIND_BANK_CASH_PASSWORDE_WRONG_CODE = -4;
	
	//未设置支付密码
	public static int BIND_BANK_UNSET_CASH_PASSWORD_CODE = -5;
	
	//支付密码与登录密码一样
	public static int BIND_BANK_SAME_CASH_PASSWORD_CODE = -6;
	
	
	
	/******************************取现时 银行卡校验状态***************************/
	
	//该银行已被删除，请重新选择银行卡
	public static int CASH_BANK_DELETED_CODE = 500;
	
	//当前提现银行卡未设置支行信息
	public static int CASH_BANK_EMPTY_BRANCH_CODE = 501;
	
	//当前取现账户与绑定的卡不匹配
	public static int CASH_BANK_UNMATCH_CODE = 502; 
	
	//当前提现银行卡未设置开户行城市信息
	public static int CASH_BANK_EMPTY_CITY_CODE = 503;
}
