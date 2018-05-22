/**
 * Copyright (c) 2015, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.fund.model;

import java.math.BigDecimal;

/**
 * 描述
 *
 * @version $Id: FundTotalVO.java 2015/11/2 16:08 $
 * @since jdk1.6
 */
public class FundTotalVO extends FundTotal {
    private BigDecimal maxAvailableRansom;
    public BigDecimal getMaxAvailableRansom() {
        return maxAvailableRansom;
    }

    public void setMaxAvailableRansom(BigDecimal maxAvailableRansom) {
        this.maxAvailableRansom = maxAvailableRansom;
    }

}
