/**
 * Copyright (c) 2015, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.borrow.constants;

/**
 * 红包记录常量
 *
 * @version $Id: RedEnvelopeRecordConsts.java 2015/3/25 13:34 $
 * @since jdk1.6
 */
public class RedEnvelopeLimitTypeConsts {

    /**
     * ********************************标的类型 ********************************************
     */

    public final static int INGNORE_BORROW_TYPE = 0;//0表示不考虑标的类型的情况


    /**
     * ********************************产品类型1散标2债权转染3_ ********************************************
     */

    public final static int TENDER_USE_PRODUCT_TYPE = 1;//散标

    public final static int TRADE_TENSFER_USE_PRODUCT_TYPE = 2; //债权转让

    public final static int XPLAN_USED_PRODUCT_TYPE = 3;//_
    
    public final static int STEP_USED_PRODUCT_TYPE = 4;//步步高升


    /**
     * ********************************是否可用0不可用1可用********************************************
     */


    public final static int COULD_NOT_USE_IS_SELECT = 0; //不可用

    public final static int COULD_USE_IS_SELECT = 1;//可用


}
