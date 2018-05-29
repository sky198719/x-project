/**
 * Copyright (c) 2015, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.constant;

/**
 * 描述
 *
 * @version $Id: Constant.java 2015/7/21 17:03 $
 * @since jdk1.6
 */
public class Constant {

    //返回结果：0成功，-1失败
    public static int RETURN_SUCC = 0;

    public static int RETURN_ERROR = -1;
    /**
     * 新新推广成功返回值
     */
    public static int XXTG_RESULT_SUCCESS = 1;
    /**
     * 新新推广失败返回值
     */
    public static int XXTG_RESULT_FAIL = -1;
    /**Token失效错误*/
    public static int TOKEN_INVALID_ERROR = 301;
    
	//短期理财产品类型 _
	public static final String MONTH_GOLD_PRODUCT_TYPE = "monthgold";
	
	//短期理财产品类型 七天大胜
	public static final String SEVEN_GOLD_PRODUCT_TYPE = "sevengold";

    //省市字典，顶级节点编码
    public static final String TOP_NODE_CODE_PRO_CITY_DIC = "000000";
    public static final Integer XINYEDAI_DIC_SPECIALAREA_CODE = 15;

    public static final String USERTOKEN = "userToken";

    public static final String CLIENTID = "XXD_V6_WEBAPP";

    public static final String XXD_FRONT_END_H5 = "XXD_FRONT_END_H5";

    public static final String XXD_INTEGRATION_PLATFORM = "XXD_INTEGRATION_PLATFORM";

    public static final String XXD_ACTIVITY_H5_PAGE = "XXD_ACTIVITY_H5_PAGE";

    public static final Long SUCCESS = 200000L;

    public static final String STATIC_VERSION = "12";

    public static final String IS_WEIXIN_AUTOLOGIN = "is_weixin_autoLogin";

    public static final String LOGINUSER = "loginUser";

    public static enum ProductType {
        PRODUCT_TYPE_XYB("XYB",101),
        PRODUCT_TYPE_BBGS("BBGS",103),
        PRODUCT_TYPE_YYP("YYP",104),
        PRODUCT_TYPE_QTDS("DTDS",105),
        PRODUCT_TYPE_YJDJ("YJDJ",106);
        private String message;
        private int code;
        private ProductType(String message,int code) {
            this.message = message;
            this.code = code;
        }

        public int getCode(){
            return this.code;
        }
        public String getMessage(){
            return this.message;
        }
    }

}
