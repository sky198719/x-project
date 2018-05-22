/**
 * Copyright (c) 2015, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.fund.util;

import java.math.BigDecimal;

/**
 * 描述
 *
 * @version $Id: FundUtil.java 2015/11/6 11:47 $
 * @since jdk1.6
 */
public class FundUtil {
    public static BigDecimal calculationIncome(BigDecimal money, BigDecimal apr, int dayNum) {
        BigDecimal interest = new BigDecimal(0);
        BigDecimal interestSum = new BigDecimal(0);
        apr = apr.divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN);
        for (int i = 0; i < dayNum; i++) {
            interest = apr.multiply(money).divide(new BigDecimal(360), 2, BigDecimal.ROUND_DOWN);
            interestSum = interestSum.add(interest).setScale(2, BigDecimal.ROUND_DOWN);
            //System.out.println(String.format("%s,本金=%s,收益=%s,累计收益=%s", (i + 1), money, interest, interestSum));
            money = money.add(interest);
        }
        return interestSum;
    }
}
