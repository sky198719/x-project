/**
 * Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.constant;

/**
 * 资金账户常量
 *
 * @version $Id: AccountConsts.java 11851 2014-12-05 05:28:43Z huna $
 * @since jdk1.6
 */
public class AccountConsts {


    /**
     * ***********************************账户类型常量************************************************
     */

    //线上默认账户
    public final static String ACCOUNT_ONLINE_DEFAULT_TYPE_ACCOUNT = "1001";


    //体验金账户
    public final static String ACCOUNT_EXPERIENCE_FUND_TYPE_ACCOUNT = "1003";

    //优选理财账户
    public final static String ACCOUNT_X_PLAN_ACCOUNT = "1004";
    
    //_账户
    public final static String ACCOUNT_FUND_TYPE_ACCOUNT = "1005";


    /**
     * ***********************************充值记录充值类型常量************************************************
     */

    //现在充值
    public final static String ACCOUNT_RECHARGE_FUND_TYPE = "1";

    //体验金充值
    public final static String ACCOUNT_RECHARGE_EXPERIENCE_FUND_TYPE = "2";

    //奖励
    public final static String ACCOUNT_RECHARGE_REWARD_TYPE = "3";


    /**
     * ***********************************充值渠道常量************************************************
     */


    //易宝
    public final static String ACCOUNT_RECHARGE_YEE_PAY_CHANNEL_ = "yp";

    //富友
    public final static String ACCOUNT_RECHARGE_FU_YOU_CHANNEL_ = "fy";


    /**
     * ***********************************资金冻结记录状态常量************************************************
     */

    public final static String ACCOUNT_FROZEN_FUND_FROZEN_STATUS = "1";

    public final static String ACCOUNT_FROZEN_FUND_UNFROZEN_STATUS = "2";


    /**
     * ***********************************资金日志操作类型常量************************************************
     */

    //资金冻结
    public final static String ACCOUNT_LOG_FUND_FROZEN_OPERATE = "account_log_fund_frozen_operate";


    /**
     * ***********************************资金日志操作资金类型常量************************************************
     */

    //资金冻结
    public final static int ACCOUNT_LOG_FUND_FROZEN_MONEY_TYPE = 1;


    /**
     * ***********************************逻辑错误常量************************************************
     */


    //资金可用余额不足错误code
    public final static int ACCOUNT_NO_ENOUGH_USER_MONEY_ERROR = 10;

    //资金可用余额不足错误消息
    public final static String ACCOUNT_NO_ENOUGH_USER_MONEY_ERROR_MSG = "资金可用余额不足";

    //非法参数错误code
    public final static int ACCOUNT_ILLEGAL_PARAMS_ERROR = 20;

    //非法参数错误信息
    public final static String ACCOUNT_ILLEGAL_PARAMS_ERROR_MSG = "非法参数错误信息";

    //没有匹配资金账户信息错误code
    public final static int ACCOUNT_NO_MATCH_ACCOUNT_INFO_ERROR = 30;

    //没有匹配资金账户信息错误信息
    public final static String ACCOUNT_NO_MATCH_ACCOUNT_INFO_ERROR_MSG = "非法资金账户";

    //非法IP错误
    public final static int ACCOUNT_ILLEGAL_IP_ERROR = 40;
    //非法IP错误信息
    public final static String ACCOUNT_ILLEGAL_IP_ERROR_MSG = "非法参数错误信息";


    //资金操作失败code
    public final static int ACCOUNT_OPERATE_FAILD_ERROE = 50;

    //资金操作失败code
    public final static String ACCOUNT_OPERATE_FAILD_ERROE_MSG = "账户资金操作失败";


    public final static int ACCOUNT_NO_MATCH_ACCOUNT_FROZEN_WITH_BUSIID_ERROR = 50;

    public final static String ACCOUNT_NO_MATCH_ACCOUNT_FROZEN_WITH_BUSIID_ERROR_MSG = "业务号没有匹配的资金冻结记录";

    public final static int ACCOUNT_NO_MATCH_ACCOUNT_FROZEN_WITH_USERID_ERROR = 60;

    public final static String ACCOUNT_NO_MATCH_ACCOUNT_FROZEN_WITH_USERID_ERROR_MSG = "用户与业务资金冻结记录不匹配";

    //新业贷注册来源
    public final static int XINYEDAI_RESOURCE = 11;

    /**
     * 提现次数
     */
    public final static String ACCOUNT_CASH_FER = "ACCOUNT_CASH_FER";

    /**
     * 提现费率
     */
    public final static String ACCOUNT_CASH_RATE = "ACCOUNT_CASH_RATE";

    /**
     * 提现最低金额
     */
	public static final String ACCOUNT_CASH_MINFEE = "ACCOUNT_CASH_MINFEE";
    /**
    * 提现最高金额
    */
    public static final String ACCOUNT_CASH_MAXFEE = "ACCOUNT_CASH_MAXFEE";
}
