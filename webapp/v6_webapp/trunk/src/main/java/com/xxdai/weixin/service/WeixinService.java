/**
 * Copyright (c) 2017, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.weixin.service;

import com.alibaba.fastjson.JSONObject;
import com.xxdai.client.CXF_Factory;
import com.xxdai.common.BaseService;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.external.weixin.ws.WeixinCXFService;
import com.xxdai.person.model.PersonResponse;
import com.xxdai.util.Configuration;
import com.xxdai.weixin.api.WxServiceImpl;
import com.xxdai.weixin.bean.WeixinAccount;
import com.xxdai.weixin.bean.result.WxUser;
import com.xxdai.weixin.constants.WeixinConstants;
import com.xxdai.weixin.util.http.SimpleGetRequestExecutor;
import com.xxdai.ws.util.WSModelResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述
 *
 * @version $Id: WeixinService.java 2017/3/28 20:22 $
 * @since jdk1.6
 */
@Service
public class WeixinService extends BaseService {

    private Log log = LogFactory.getLog(WeixinService.class);
    private WeixinCXFService weixinCXFService = (WeixinCXFService) CXF_Factory.getFactory(WeixinCXFService.class, Configuration.getInstance().getValue("webService_url") + "/weixinWebService").create();

    public WSModelResponse getWeixinUserBind(String openId) {
        WSModelResponse resp = null;
        try {
            //检查当前用户是否已绑定微信用户
            JSONObject userBindParam = new JSONObject();
            userBindParam.put("openId", openId);
            userBindParam.put("selectType", "openId");
           // log.info("getWeixinUserBind req = " + userBindParam.toJSONString());
            String getwxUserBindStr = weixinCXFService.getWeixinUserBind(userBindParam.toJSONString());
            //log.info("getWeixinUserBind resp = " + getwxUserBindStr);
            resp = JsonUtil.jsonToBean(getwxUserBindStr, WSModelResponse.class);
        } catch (Exception e) {
            log.error(e);
        } finally {
            return resp;
        }
    }

    public WSModelResponse getWeixinUserBindByUserId(Long userId) {
           WSModelResponse resp = null;
           try {
               //检查当前用户是否已绑定微信用户
               JSONObject userBindParam = new JSONObject();
               userBindParam.put("userId", userId);
               userBindParam.put("selectType", "userId");
              // log.info("getWeixinUserBind req = " + userBindParam.toJSONString());
               String getwxUserBindStr = weixinCXFService.getWeixinUserBind(userBindParam.toJSONString());
               //log.info("getWeixinUserBind resp = " + getwxUserBindStr);
               resp = JsonUtil.jsonToBean(getwxUserBindStr, WSModelResponse.class);
           } catch (Exception e) {
               log.error(e);
           } finally {
               return resp;
           }
       }

    public PersonResponse getUser(Long userId){
        PersonResponse resp = null;
        try {
            String str = userQueryCXFService.queryById(String.valueOf(userId));
            resp = JsonUtil.jsonToBean(str, PersonResponse.class);
        } catch (Exception e) {
              log.error(e);
        } finally {
             return resp;
        }
    }

    public WSModelResponse getWeixinUser(String openId) {
        WSModelResponse wxUserResp = null;
        try {
            //获取微信用户信息
            JSONObject wxUserParam = new JSONObject();
            wxUserParam.put("openId", openId);
            //log.info("getWeixinUser req = " + wxUserParam.toJSONString());
            String wxUserStr = weixinCXFService.getWeixinUser(wxUserParam.toJSONString());
            //log.info("getWeixinUser resp = " + wxUserStr);
            wxUserResp = JsonUtil.jsonToBean(wxUserStr, WSModelResponse.class);
        } catch (Exception e) {
            log.error(e);
        } finally {
            return wxUserResp;
        }
    }

    public WSModelResponse weixinUserBind(String openId, Long userId) {
        WSModelResponse wxUserBindResp = null;
        try {
            JSONObject userBindParam = new JSONObject();
            userBindParam.put("openId", openId);
            userBindParam.put("selectType", "openId");
            userBindParam.put("bindType", "reg");
            userBindParam.put("userId", userId);
           // log.info("weixinUserBind req = " + userBindParam.toJSONString());
            String wxUserBindStr = weixinCXFService.weixinUserBind(userBindParam.toJSONString());
           // log.info("weixinUserBind req = " + wxUserBindStr);
            wxUserBindResp = JsonUtil.jsonToBean(wxUserBindStr, WSModelResponse.class);
        } catch (Exception e) {
            log.error(e);
        } finally {
            return wxUserBindResp;
        }
    }

    public boolean saveWeixinUser(String openId){
        boolean bool = false;
        try {
            log.info("发送请求到微信服务器，查询微信用户信息");
            String uri = "https://api.weixin.qq.com/cgi-bin/user/info";
            String data = "openid="+openId+"&lang=zh_CN";
            String responseContext = WxServiceImpl.getInstance().execute(new SimpleGetRequestExecutor(), uri, data);
            log.info("远程请求内容：\n"+responseContext);

            //responseContext = new String(responseContext.getBytes("GBK"), "UTF-8");

            WxUser jsonUser = WxUser.fromJson(responseContext);
            log.info("=============================================");

            String nickName = jsonUser.getNickname();
            if(nickName != null && !"".equals(nickName)){
                Pattern pattern = Pattern.compile("[^\u4e00-\u9fa5\\w\\x00-\\x7F]*");
                Matcher matcher = pattern.matcher(nickName);
                //替换第一个符合正则的数据
                jsonUser.setNickname(matcher.replaceAll(""));
            }

            String weixinServiceCode = com.xxdai.weixin.util.CacheUtil.getCacheValue(WeixinConstants.WEIXIN_SERVICE_CODE);
            JSONObject paramJson = new JSONObject();
            paramJson.put("accountCode",weixinServiceCode);
            String weixinAccountResp = weixinCXFService.getWeixinAccoiunt(paramJson.toJSONString());
            WSModelResponse resp = JsonUtil.jsonToBean(weixinAccountResp, WSModelResponse.class);
            WeixinAccount weixinAccount = JSONObject.parseObject(resp.getData().toString(), WeixinAccount.class);

            JSONObject userJson = new JSONObject();
            userJson.put("wxAccountId",weixinAccount.getId());
            userJson.put("city",jsonUser.getCity());
            userJson.put("country",jsonUser.getCountry());
            userJson.put("headimgurl",jsonUser.getHeadimgurl());
            userJson.put("language",jsonUser.getLanguage());
            userJson.put("nickName",jsonUser.getNickname());
            userJson.put("openId",openId);
            userJson.put("province",jsonUser.getProvince());
            userJson.put("sex",jsonUser.getSex());
            userJson.put("subscribeTime",new Date());
            userJson.put("unionId",jsonUser.getUnionid());
            userJson.put("status",WeixinConstants.WX_USER_STATUS_SUBSCRIBE);
            log.info("saveWeixinUser req = "+userJson.toJSONString());
            String userResp = weixinCXFService.saveWeixinUser(userJson.toJSONString());
            log.info("saveWeixinUser resp = "+userResp);
            WSModelResponse userReps = JsonUtil.jsonToBean(userResp,WSModelResponse.class);
            if(userReps.getResultCode() == 0){
                bool = true;
            }
        } catch (Exception e) {
            log.error("saveWeixinUser error",e);
        } finally {
            return bool;
        }
    }

}
