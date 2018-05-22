/**
 * Copyright (c) 2015, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.plan.model;


import com.xxdai.plan.vo.Scheme;

import java.math.BigDecimal;

/**
 * 描述
 *
 * @version $Id: SchemeVO.java 2015/1/4 18:38 $
 * @since jdk1.6
 */
public class SchemeVO extends Scheme {

    private boolean isHappyHour;

    private BigDecimal webapp;

    public BigDecimal getWebapp() {
        return webapp;
    }

    public void setWebapp(BigDecimal webapp) {
        this.webapp = webapp;
    }


    public boolean getIsHappyHour() {
        return isHappyHour;
    }

    public void setIsHappyHour(boolean isHappyHour) {
        this.isHappyHour = isHappyHour;
    }
}
