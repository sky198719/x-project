/**
 * Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.weixin.util;

import com.alibaba.fastjson.JSONObject;
import com.xxdai.client.CXF_Factory;
import com.xxdai.constant.AppConsts;
import com.xxdai.core.util.http.WSResponse;
import com.xxdai.core.util.lang.StringUtil;
import com.xxdai.system.bo.SysConfig;
import com.xxdai.system.ws.SysConfigCXFService;
import com.xxdai.util.Configuration;
import com.xxdai.weixin.constants.WeixinConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        if(WeixinConstants.WEIXIN_APPID.equalsIgnoreCase(key)) {
            return "wx28896fab8e9c19cb";
        }

        if(WeixinConstants.WEIXIN_APPSECRET.equalsIgnoreCase(key)) {
            return "921777b4ff7a15939988e837b37a479f";
        }

        if(WeixinConstants.REDIRECT_URI.equalsIgnoreCase(key)) {
            return "http://www.xinxindai.com/m/weixin/auth.jsp";
        }

        if(WeixinConstants.WEIXIN_SERVICE_CODE.equalsIgnoreCase(key)){
            return "gh_ac7087b55177";
        }
        log.info("从缓存中去获取key = " + key);
        String resultValue = "";
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
                        log.info("从缓存中获取内容：" + resultValue);
                    }
                }
            }
        } catch (Exception e){
            log.error(" 从缓存中获取内容失败，未知异常：" + e.getMessage(),e);
        }
        return resultValue;
    }
}
