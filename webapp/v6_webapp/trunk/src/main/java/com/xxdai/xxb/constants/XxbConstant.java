/**
 * Copyright (c) 2015, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.xxb.constants;

import java.util.HashMap;

public class XxbConstant {
    /**兑换新新币图片验证码错误*/
    public static int XXB_EXCHANGE_RANDCODE_ERROR = 399;
    /**兑换新新币支付密码错误*/
    public static int XXB_EXCHANGE_PWD_ERROR = 299;
    /**兑换新新币金额错误*/
    public static int XXB_EXCHANGE_AMOUNT_ERROR = 199;


    public static HashMap<Integer,String> XxbExchangeMsg = new HashMap<Integer, String>();

    static {

        XxbExchangeMsg.put(0,"兑换成功");
        XxbExchangeMsg.put(-1,"参数为空");
        XxbExchangeMsg.put(-2,"缺少参数");
        XxbExchangeMsg.put(-3,"支付密码不对");
        XxbExchangeMsg.put(-4,"兑换金额不能超过1000元");
        XxbExchangeMsg.put(-5,"图片验证码不对");
        XxbExchangeMsg.put(-6,"兑换新新比的个数不能为0");
        XxbExchangeMsg.put(-7,"当前新新比不足");
    }
}
