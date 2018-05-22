/**
 * Copyright (c) 2016, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.activity.constants;


import java.util.HashMap;
import java.util.Map;

public class OlympicConstant {

    //活动代码
    public static final String OLYMPIC_ACTIVITY_CODE = "web-olympic";
    //五环名称
    public static final Map<Integer, String> RINGNAMES = new HashMap<Integer, String>();
    //奥运活动奖品名称
    public static final Map<Integer, String> PRIMENAMES = new HashMap<Integer, String>();

    static {
        //五环名称
        RINGNAMES.put(1, "欧洲环");
        RINGNAMES.put(2, "非洲环");
        RINGNAMES.put(3, "美洲环");
        RINGNAMES.put(4, "亚洲环");
        RINGNAMES.put(5, "大洋洲环");
        //奖品名称
        PRIMENAMES.put(7, "310个新新币");
        PRIMENAMES.put(8, "10元话费");
        PRIMENAMES.put(9, "50元京东E卡");
        PRIMENAMES.put(10, "200元哈根达斯券");
        PRIMENAMES.put(11, "1000元耐克礼品卡");
    }

    public OlympicConstant() {
    }

}
