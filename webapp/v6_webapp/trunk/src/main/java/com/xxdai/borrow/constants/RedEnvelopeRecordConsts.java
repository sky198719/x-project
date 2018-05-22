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
public class RedEnvelopeRecordConsts {

    /**
     * ********************************红包状态:状态0待领取1待使用2已使用3已过期********************************************
     */

    public final static int WAIT_TO_RECEIVE_STATUS = 0;//待领取

    public final static int USEABLE_STATUS = 1; //待使用

    public final static int HAS_USED_STATUS = 2;//已使用

    public final static int HAS_UNUSABLE_STATUS = 3; //已过期

    public final static int HAS_FROZEN_STATUS = 4; //已冻结


    /**
     * ********************************使用平台:1web2app9全部********************************************
     */

    public final static int WEB_PLATFORM = 1; //WEB平台

    public final static int APP_PLATFORM = 2;//APP平台

    public final static int ALL_PLATFORM = 9;//所有平台

    /**
     * ********************************逻辑错误********************************************
     */

    public final static int ILLEGAL_TERMINALVER_TYPE_ERROR = 10;

    public final static String ILLEGAL_TERMINALVER_TYPE_ERROR_MSG = "非法终端信息类型";


    public final static int DUPLICATE_COUPON_CODES_ERROR = 20;

    public final static String DUPLICATE_COUPON_CODES_ERROR_MSG = "传入的红包编码重复";

    public final static int USE_COUPON_ERROR = 30;

    public final static String USE_COUPON_ERROR_MSG = "红包使用异常，请刷新并检查红包是否可用";

    public final static int COUPON_FROZEN_OPERATE_ERROE = 40;

    public final static String COUPON_FROZEN_OPERATE_ERROE_MSG = "冻结红包异常，请检测礼券是否存在或者已使用";

    public final static int COUPON_NOT_REACH_QUOTA_ERROE = 50;

    public final static String COUPON_NOT_REACH_QUOTA_ERROE_MSG = "红包未到达使用限额";

}
