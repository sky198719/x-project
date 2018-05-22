/**
 * Copyright (c) 2014, www.xinxindai.com All Rights Reserved.
 */
package com.xxdai.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.xxdai.appro.bo.MobileAppro;
import com.xxdai.common.BaseController;
import com.xxdai.constant.AppConsts;
import com.xxdai.constant.SmsConsts;
import com.xxdai.core.util.http.WSResponse;
import com.xxdai.external.sms.ws.SMSCXFService;
import com.xxdai.external.user.ws.User;

import com.xxdai.external.user.ws.UserCXFService;
import com.xxdai.user.model.UserResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.xxdai.client.CXF_Factory;
import com.xxdai.core.util.http.HttpUtil;
import com.xxdai.core.util.http.WSMapResponse;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.person.model.PersonResponse;
import com.xxdai.person.ws.accountov.AccountOVQueryCXFService;
import com.xxdai.util.Configuration;

/**
 * 用户消息配置Controller
 *
 * @version $Id: UserNoticeConfigController.java 18463 2015-07-02 03:24:32Z pufei
 *          $
 * @since jdk1.6
 */
@Controller
@RequestMapping(value = "/voiceSms")
public class VoiceCodeMsgController extends BaseController {

    private static final Log logger = LogFactory
            .getLog(VoiceCodeMsgController.class);
    /**
     * 非登陆状态下发送语音验证码
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/sendVoiceSMSLogout", produces = {"application/json;charset=UTF-8"})
    private
    @ResponseBody
    String sendVoiceSMSLogout(HttpServletRequest request, HttpServletResponse response) {
        JSONObject resultJson = new JSONObject();

        // 获取用户手机号
        String mobileNo = request.getParameter("mobileNo");
        if (StringUtils.isBlank(mobileNo)) {
            resultJson
                    .put("msg",
                            "手机号为空，无法发送语音验证码！");
            resultJson.put("resultCode", -1);
            return resultJson.toString();
        }
        // 判断手机号码是否已经注册过
//        JSONArray jsonArray = new JSONArray();
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("mobile", mobileNo);
//        jsonArray.add(jsonObject);
//        logger.info("check mobile request param = "  + jsonArray.toJSONString());
//        String resultString = approCXFService.checkMobileIsExisting(jsonArray.toString());
//        logger.info("check mobile response = " + resultString);
//        WSResponse wsResponse = JsonUtil.jsonToBean(resultString, WSResponse.class);
//        if (wsResponse.getResultCode() == -11) {
//            resultJson.put("resultCode", -4);
//            resultJson.put("msg", "此号码已被注册,请更换后再尝试");
//            return resultJson.toString();
//        }
        //生成验证码
        int randCode = (int) Math.round(Math.random() * 8999) + 1000;
        logger.info("语音验证码："+randCode);
        //获取功能编号
        String busiCode =SmsConsts.BUSICODE_REGISTER_VOICE;

        JSONObject obj = new JSONObject();
        String msg = "";
        int resultCode = 0;
        try{
            obj.put("verifyCode", randCode);
            obj.put("phone", mobileNo);
            obj.put("sendIp", HttpUtil.getRealIpAddr(request));
            obj.put("userId", 0);
            obj.put("busiCode", busiCode);
            String str = smsCXFService.sendVoiceSMS(obj.toJSONString());
            WSMapResponse ws = JsonUtil.jsonToBean(str, WSMapResponse.class);
            resultCode = ws.getResultCode();
            msg = ws.getDesc();
            if (ws.getResultCode() == AppConsts.WS_RETURN_SUCC) {
                request.getSession().setAttribute("smsCode", randCode);
                logger.info("语音验证码发送成功...");
            } else {
                resultCode = -2;
                msg = ws.getDesc();
                logger.info("语音验证码发送失败："+msg);
            }
        }catch (Exception e){
            msg = "获取语音验证码失败，请刷新重试";
            resultCode = -3;
            logger.error("sendVoiceSMSLogout ERROR:" + e.getMessage(),e);
        }
        resultJson.put("msg", msg);
        resultJson.put("resultCode", resultCode);
        return resultJson.toString();
    }

    /**
     * 登陆状态下发送语音验证码
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/sendVoiceSMSLogin", produces = {"application/json;charset=UTF-8"})
    private
    @ResponseBody
    String sendVoiceSMSLogin(HttpServletRequest request, HttpServletResponse response) {
        JSONObject resultJson = new JSONObject();

        // session中的用户对象
        Object userObj = request.getSession().getAttribute("loginUser");
        if (null == userObj) {
            resultJson.put("resultCode", 400);
            resultJson.put("msg", "会话失效，请重新登录");
            return resultJson.toString();
        }

        User user = (User) userObj;

        // 获取用户认证手机号
        String mobileNo = request.getParameter("mobileNo");
        JSONObject obj = new JSONObject();
        if (StringUtils.isBlank(mobileNo)) {
            resultJson
                    .put("msg",
                            "手机号为空，无法发送语音验证码！");
            resultJson.put("resultCode", -1);
            return resultJson.toString();
        }
        int randCode = (int) Math.round(Math.random() * 8999) + 1000;

        // 功能编号
        String busiCode = SmsConsts.BUSICODE_MOBILE_APPRO;
        String msg ="";
        int resultCode = 0;
        try{
            obj.put("verifyCode", randCode);
            obj.put("phone", mobileNo);
            obj.put("sendIp", HttpUtil.getRealIpAddr(request));
            obj.put("userId", user.getUserId());
            obj.put("busiCode", busiCode);
            String str = smsCXFService.sendVoiceSMS(obj.toJSONString());
            WSMapResponse ws = JsonUtil.jsonToBean(str, WSMapResponse.class);
            resultCode = ws.getResultCode();
            msg = ws.getDesc();
            if (ws.getResultCode() == AppConsts.WS_RETURN_SUCC) {
                request.getSession().setAttribute("sendVoice", randCode);
            } else {
                resultCode = -2;
                msg = ws.getDesc();
            }
        }catch (Exception e){
            msg = "获取语音验证码失败，请刷新重试";
            resultCode = -3;
            logger.error("sendVoiceSMSLogin ERROR:" + e.getMessage(),e);
        }
        resultJson.put("msg", msg);
        resultJson.put("resultCode", resultCode);
        return resultJson.toString();
    }
}
