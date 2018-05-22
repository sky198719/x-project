/**
* <p>Title: TradeConstants.java</p>
* <p>Description: </p>
* <p>Copyright (c) 2014, www.xinxindai.com All Rights Reserved. </p>
* <p>Company: www.xinxindai.com</p>
* @author huna
* @date 2015年2月11日
* @version 1.0
*/
package com.xxdai.trade.constants;

/**
 * @author huna
 * @since jdk1.6
 * @version 上午10:21:40
 */
public class TradeConstants {
	
	//债权转让发起方式
	/** 1 用户发起 */
	public static Integer TRADE_TRANSFER_METHOD_USER = 1;
	/** 2 系统发起 */
	public static Integer TRADE_TRANSFER_METHOD_SYSTEM = 2;
	
	//债权转让费率字典类型
	/** TRADE_RATE 普通债权 */
	public static String TRADE_RATE_NOMAL = "TRADE_RATE";
	/** TRADE_RATE_OP 新元宝债权 */
	public static String TRADE_RATE_OP = "TRADE_RATE_OP";
	
	//债权转让申请状态
	/** 1  转让中 */
	public static Integer TRADE_STATUS_TRANSFERING = 1;
	/** 2 转让成功 */
	public static Integer TRADE_STATUS_SUCCESS = 2;	
	/** 3 转让失败 */
	public static Integer TRADE_STATUS_FAILURE = 3;
	/** 4 撤消 */
	public static Integer TRADE_STATUS_ROLLBACK = 4;
	/** 5 过期 */
	public static Integer TRADE_STATUS_OVERDUE = 5;
	/** 6  支付中 */
	public static Integer TRADE_STATUS_PAYING = 6;

	//我的债权页面tab页标记
	/** 1 持有的债权列表页面 */
	public static String TRADE_PAGE_TAB_IS_HOLD = "1";
	/** 2 转让中的债权列表页面 */
	public static String TRADE_PAGE_TAB_TRANSFERING = "2";
	/** 3 已转出的债权列表页面 */
	public static String TRADE_PAGE_TAB_TRANSFERED_OUT = "3";
	/** 4 已转入的债权列表页面 */
	public static String TRADE_PAGE_TAB_TRANSFERED_IN = "4";
	
	//债权转让模块错误代码
	/** -1 */
	public static Integer ERROR_CODE_OTHER = -1;
	/** -2 */
	public static Integer ERROR_CODE_OVERDUE = -2; //债权标的已逾期,无法转让该债权！
	/** -3 */
	public static Integer ERROR_CODE_IS_REPAYDAY = -3; //当前日期为还款日或还款日第二日,无法转让该债权！
	/** -5 */
	public static Integer ERROR_CODE_NOT_INCLUDE = -5; //您当前持有的可转让债权中不包含此债权,无法转让该债权！
	/** -6 */
	public static Integer ERROR_CODE_IS_REPAY_ADVANCE = -6; //债权标的已提前还款,无法转让该债权！
	/** -7 */
	public static Integer ERROR_CODE_IS_REMAINNUMBER_ZERO = -7; //债权剩余期数为0,无法转让该债权！
	/** -9 */
	public static Integer ERROR_CODE_ROLLBACK_ERROR = -9; //撤消转让申请失败错误
	/** -10 */
	public static Integer ERROR_CODE_NOT_TRANSFERING = -10; //债权已不在转让中，无法购买!
	/** -11 */
	public static Integer ERROR_CODE_IS_SELF_TRADE = -11; //不能购买自己发布的债权转让标！
	/** -12 */
	public static Integer ERROR_CODE_STATUS_ILLEGAL = -12; //该债权标或已被购买、被撤消、已过期，无法继续购买！
	/** -13 */
	public static Integer ERROR_CODE_NOT_ENOUGH_MONEY = -13; //用户账户资金不足，无法购买！
	/** -20 */
	public static Integer ERROR_CODE_NOT_EXISTS = -20; //该债权转让申请不存在或已被删除！;
	/** -21 */
	public static Integer ERROR_CODE_WAS_TRANSFERED = -21; //该债权已转让成功，无法撤消！
	/** -22 */
	public static Integer ERROR_CODE_WAS_ROLLBACKED = -22; //该债权转让申请已被撤消，无法再做撤消操作！
	/** -23 */
	public static Integer ERROR_CODE_REQUEST_OVERDUE = -23; //该债权转让申请已过期，无法撤消！
	/** -99 */
	public static Integer ERROR_CODE_NOT_LOGIN = -99; //用户未登陆
	/** -901 */
	public static Integer ERROR_CODE_QUERY_ERROR = -901; //查询债权出错
	/** -902 */
	public static Integer ERROR_CODE_CHECK_ERROR = -902; //校验债权失败
	
	
	//债权转让模块错误提示信息
	/** 其它未知错误 */
	public static String ERROR_MSG_OTHER = "其它未知错误";
	/** 操作失败 */
	public static String ERROR_MSG_OTHER1 = "操作失败";
	/** 债权标的已逾期 */
	public static String ERROR_MSG_OVERDUE = "债权标的已逾期,无法转让该债权！";
	/** 当前日期为还款日或还款日第二日,无法转让该债权！ */
	public static String ERROR_MSG_IS_REPAYDAY = "当前日期为还款日或还款日第二日,无法转让该债权！";
	/** 标的还款日及还款日第二日不允许购买债权转让标！ */
	public static String ERROR_MSG_IS_REPAYDAY2 = "标的还款日及还款日第二日不允许购买债权转让标！";
	/** 您当前持有的债权中不包含此债权,无法转让该债权！ */
	public static String ERROR_MSG_NOT_INCLUDE = "您当前持有的可转让债权中不包含此债权,无法转让该债权！";
	/** 债权标的已提前还款,无法转让该债权！ */
	public static String ERROR_MSG_IS_REPAY_ADVANCE  = "债权标的已提前还款,无法转让该债权！";
	/** 债权剩余期数为0,无法转让该债权！ */
	public static String ERROR_MSG_IS_REMAINNUMBER_ZERO = "债权剩余期数为0,无法转让该债权！";
	/** 该债权标剩余期数为0，无法购买！ */
	public static String ERROR_MSG_IS_REMAINNUMBER_ZERO2 = "该债权标剩余期数为0，无法购买！";
	/** 撤消转让申请失败 */
	public static String ERROR_MSG_ROLLBACK = "撤消转让申请失败";
	/** 债权已不在转让中，无法购买! */
	public static String ERROR_MSG_NOT_TRANSFERING = "债权已不在转让中，无法购买!";
	/** 不能购买自己发布的债权转让标！ */
	public static String ERROR_MSG_IS_SELF_TRADE ="不能购买自己发布的债权转让标！";
	/** 该债权标或已被购买、被撤消、已过期，无法继续购买！ */
	public static String ERROR_MSG_STATUS_ILLEGAL = "该债权标或已被购买、被撤消、已过期，无法继续购买！";
	/** 用户账户资金不足，无法购买！ */
	public static String ERROR_MSG_NOT_ENOUGH_MONEY = "用户账户资金不足，无法购买！";
	/** 该债权转让申请不存在或已被删除！ */
	public static String ERROR_MSG_NOT_EXISTS = "该债权转让申请不存在或已被删除！";
	/** 该债权已转让成功，无法撤消！ */
	public static String ERROR_MSG_WAS_TRANSFERED = "该债权已转让成功，无法撤消！";
	/** 该债权转让申请已被撤消，无法再做撤消操作！ */
	public static String ERROR_MSG_WAS_ROLLBACKED = "该债权转让申请已被撤消，无法再做撤消操作！";
	/** 该债权转让申请已过期，无法撤消！ */
	public static String ERROR_MSG_REQUEST_OVERDUE = "该债权转让申请已过期，无法撤消！";
	/** Session过期，请重新登陆! */
	public static String ERROR_MSG_NOT_LOGIN = "您的会话已过期，请重新登陆!";
	/** 查询持有的债权列表出错！ */
	public static String ERROR_MSG_QUERY_ERROR1 = "查询持有的债权列表出错！";
	/** 查询转让中的债权出错！ */
	public static String ERROR_MSG_QUERY_ERROR2 = "查询转让中的债权出错！";
	/** 查询已转出的债权出错！ */
	public static String ERROR_MSG_QUERY_ERROR3 = "查询已转出的债权出错！";
	/** 债权转让/撤消校验出错！ */
	public static String ERROR_MSG_CHECK_ERROR = "债权转让/撤消校验出错！";
}
