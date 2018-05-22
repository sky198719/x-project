package com.xxdai.product.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;

public class StepUtils {
    public static final int LOCK_DAY = 30;
    /**
     * 计算两个日期间隔天数，不考虑时间部分
     *
     * @param d1
     * @param d2
     * @return d2-d1天数
     */
    public static int diffDays(Date d1, Date d2) {
        Date date1 = emptyTime(d1);
        Date date2 = emptyTime(d2);
        return (int) ((date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24));
    }

    /**
     * 将Date的时间部分置为0
     *
     * @param date
     * @return
     */
    public static Date emptyTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 计算当前退出时利率
     *
     * @param minApr
     * @param step
     * @param maxApr
     * @param day
     * @return
     */
    public static BigDecimal calcCurrentApr(BigDecimal minApr, BigDecimal step, BigDecimal maxApr, int day) {
        if(day<30){
            return BigDecimal.ZERO;
        }
        BigDecimal apr = minApr.add(new BigDecimal((day/ 30)-1).multiply(step));
        if (apr.compareTo(maxApr) > 0) {
            apr = maxApr;
        }
        return apr;
    }

    /**
     * 计算利息
     *
     * @param amount
     * @param day
     * @param apr
     * @return
     */
    public static BigDecimal calcInterest(BigDecimal amount, int day, BigDecimal apr) {
        return amount.multiply(apr).multiply(new BigDecimal(day)).divide(new BigDecimal("36000"), 2, RoundingMode.DOWN);
    }


    /**
     * 判断两个日期年份和月份是否相同
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameYearMonth(Date date1, Date date2){
        Calendar quitCal = Calendar.getInstance();
        quitCal.setTime(date1);

        Calendar nowCal = Calendar.getInstance();
        nowCal.setTime(date2);

        if(quitCal.get(Calendar.YEAR)==nowCal.get(Calendar.YEAR) && quitCal.get(Calendar.MONTH)==nowCal.get(Calendar.MONTH)){
            return true;
        }
        return false;
    }

    /**
     * 判断是否处于锁定期
     * @param day
     * @return
     */
    public static boolean isInLocked(int day){
        return day < LOCK_DAY;
    }
}