/**
 * Copyright (c) 2014, www.xinxindai.com All Rights Reserved.
 */
package com.xxdai.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 *
 * @author aiden at 2017-3-7 19:44:19
 *
 * @since jdk1.6
 */
public class PatternUtil {


    public PatternUtil() {
    }

    //验证手机号码正则表达式
    public boolean checkPhoneNOPattern(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

}
