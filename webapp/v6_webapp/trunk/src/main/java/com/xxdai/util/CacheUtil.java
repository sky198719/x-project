/**
 * Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.util;

import com.alibaba.fastjson.JSONObject;
import com.xxdai.client.CXF_Factory;
import com.xxdai.constant.AppConsts;
import com.xxdai.core.util.http.WSResponse;
import com.xxdai.core.util.lang.StringUtil;
import com.xxdai.system.bo.SysConfig;
import com.xxdai.system.ws.SysConfigCXFService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存获取工具
 *
 * @version $Id: CacheUtil.java 2014/11/23 15:04 $
 * @since jdk1.6
 */
public class CacheUtil {
    /**
      * 日志记录器
      */
    static Logger log = LoggerFactory.getLogger(CacheUtil.class);

    static SysConfigCXFService sysConfigCXFService = (SysConfigCXFService) CXF_Factory.getFactory(SysConfigCXFService.class, Configuration.getInstance().getValue("webService_url") + "/sysConfigWebService").create();

    /**
     * 从缓存中获取内容
     * @param key
     * @return
     */
    public static synchronized String getCacheValue(String key){
        log.info("get cache key = " + key);
        String resultValue = null;
        try {
            JSONObject inputObj=new JSONObject();
            inputObj.put("syskey", key);
            String sysConfigStr = sysConfigCXFService.querySysConfigByKey(inputObj.toJSONString());
            if(sysConfigStr!=null && StringUtil.isNotEmpty(sysConfigStr)){
                JSONObject resObject = JSONObject.parseObject(sysConfigStr);
                if(resObject!=null && resObject.size()>0){
                    WSResponse response = resObject.getObject("response", WSResponse.class);
                    if(response!=null && response.getResultCode() == AppConsts.WS_RETURN_SUCC){
                        SysConfig sysConfig = resObject.getObject("sysConfig", SysConfig.class);
                        String value = sysConfig.getSysKeyValue();
                        resultValue = value;
                        log.info("value：" + resultValue);
                    }
                }
            }
        } catch (Exception e){
            log.error(" 从缓存中获取内容失败，未知异常：" + e.getMessage(),e);
        }
        return resultValue;
    }
}
