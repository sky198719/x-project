/**
 * Copyright (c) 2014, www.xinxindai.com All Rights Reserved.
 */
package com.xxdai.user.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxdai.common.BaseController;
import com.xxdai.common.DataResponse;
import com.xxdai.constant.AccountConsts;
import com.xxdai.constant.AppConsts;
import com.xxdai.constant.Constant;
import com.xxdai.constant.SmsConsts;
import com.xxdai.core.util.http.HttpUtil;
import com.xxdai.core.util.http.WSMapResponse;
import com.xxdai.core.util.http.WSResponse;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.core.util.lang.DateUtil;
import com.xxdai.encrypt.EscapeCode;
import com.xxdai.external.user.ws.User;
import com.xxdai.http.Message;
import com.xxdai.person.bo.BaseInfo;
import com.xxdai.person.model.Appro;
import com.xxdai.person.model.PersonResponse;
import com.xxdai.user.model.UserResponse;
import com.xxdai.user.service.UserService;
import com.xxdai.util.*;
import com.xxdai.weixin.constants.WeixinConstants;
import com.xxdai.ws.util.WSModelResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 用户Controller
 *
 * @version $Id: UserController.java 28129 2016-11-02 02:43:28Z zhuzongwei $
 * @since jdk1.6
 */
@SuppressWarnings("unchecked")
@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {

    private static final Log log = LogFactory.getLog(UserController.class);


    @Autowired
    private UserService userService;


    /**
     * 登录
     */
    @RequestMapping(value = "/loginActive", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String login(HttpServletRequest request,HttpServletResponse response,HttpSession session) {
        JSONObject result = new JSONObject();
        try {
            String username = request.getParameter("username");
            String md5pwd = request.getParameter("password");

            JSONObject loginResult = userService.loginV7(username,md5pwd,HttpTookit.getUserAgent(request));
            int code = loginResult.getInteger("code");
            if(code == 0) {
                String userToken = loginResult.getString("data");
                RedisUtil redisUtil = (RedisUtil) SpringApplicationUtil.getBean("redisUtil");
                redisUtil.setCookieToken(request,response,userToken);
                result.put(Constant.USERTOKEN, userToken);

                User user = userService.getUserInfo(userToken,HttpTookit.getUserAgent(request));
                if(user == null) {
                    result.put("resultCode", 101);
                    result.put("msg", "登录失败，请重新尝试或者联系客服");
                    return result.toJSONString();
                }

                session.setAttribute(Constant.LOGINUSER,user);

                //注册时间
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
                String regDateStr = this.getDaySub(dateFormat.format(user.getAddTime()),dateFormat.format(new Date()));
                result.put("regAddtime", regDateStr);

                /*Map<String, String> growingiobm = this.getGrowingIoBM(user);
                if (growingiobm.size() > 0) {
                    //是否实名认证
                    result.put("ISrealnameAppro", growingiobm.get("ISrealnameAppro"));
                    //是否有可用余额
                    result.put("ISusable", growingiobm.get("ISusable"));
                    //年龄
                    result.put("age", growingiobm.get("age"));
                    //投资次数
                    result.put("ISInvesterCount", growingiobm.get("ISInvesterCount"));
                    //是否持有产品
                    result.put("isProduct", growingiobm.get("isProduct"));
                    //购买金额
                    result.put("price", growingiobm.get("price"));

                }*/
                result.put("user", user);
                result.put("front_url", Configuration.getInstance().getValue("front_url"));
                result.put("resultCode", 0);
                result.put("msg", "登录成功");
            } else {
                result.put("resultCode", code);
                result.put("msg", loginResult.getString("message"));
            }
        } catch (Exception e) {
            log.error("登录失败", e);
            result.put("resultCode", 100);
            result.put("msg", "登录失败，请重新尝试或者联系客服");
        }
        return TokenUtil.jsonpCallback(request, result.toJSONString());
    }


    /**
     * 登录
     */
    @RequestMapping(value = "/login", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String loginV2(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
        JSONObject result = new JSONObject();
        try {
            //图片验证码
            String imgCode = request.getParameter("imgCode");
            if (imgCode == null || "".equals(imgCode)) {
                result.put("resultCode", -100);
                result.put("msg", "图片验证码为空");
                return result.toJSONString();
            }

            Object sessionImgCodeObj = session.getAttribute("imgCode");
            if (sessionImgCodeObj == null) {
                result.put("resultCode", -101);
                result.put("msg", "图片验证码失效，请重新刷新");
                return result.toJSONString();
            }

            String sessionImgCode = sessionImgCodeObj.toString();
            if (!imgCode.equalsIgnoreCase(sessionImgCode)) {
                log.info("checkSendSMSCount Not by");
                result.put("resultCode", -40);
                result.put("msg", "您的图片验证码不正确");
                return result.toJSONString();
            }

            String username = request.getParameter("username");
            String md5pwd = request.getParameter("password");

            JSONObject loginResult = userService.loginV7(username,md5pwd,HttpTookit.getUserAgent(request));
            int code = loginResult.getInteger("code");
            if(code != 0) {
                result.put("resultCode", code);
                result.put("msg", loginResult.getString("message"));
                return result.toJSONString();
            }

            String userToken = loginResult.getString("data");
            User user = userService.getUserInfo(userToken,HttpTookit.getUserAgent(request));
            if(user == null) {
                result.put("resultCode", 101);
                result.put("msg", "登录失败，请重新尝试或者联系客服");
                return result.toJSONString();
            }

            session.setAttribute(Constant.LOGINUSER, user);

            RedisUtil redisUtil = (RedisUtil) SpringApplicationUtil.getBean("redisUtil");
           // String userToken = redisUtil.putUsertoCache(request,users,response);
            redisUtil.setCookieToken(request,response,userToken);
            result.put(Constant.USERTOKEN, userToken);

            //注册时间
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
            String regDateStr = this.getDaySub(dateFormat.format(user.getAddTime()),dateFormat.format(new Date()));
            result.put("regAddtime", regDateStr);

            /*Map<String, String> growingiobm = this.getGrowingIoBM(user);
            if (growingiobm.size() > 0) {
                //是否实名认证
                result.put("ISrealnameAppro", growingiobm.get("ISrealnameAppro"));
                //是否有可用余额
                result.put("ISusable", growingiobm.get("ISusable"));
                //年龄
                result.put("age", growingiobm.get("age"));
                //投资次数
                result.put("ISInvesterCount", growingiobm.get("ISInvesterCount"));
                //是否持有产品
                result.put("isProduct", growingiobm.get("isProduct"));
               //购买金额
                result.put("price", growingiobm.get("price"));

            }*/

            result.put("user", user);
            result.put("front_url", Configuration.getInstance().getValue("front_url"));
            result.put("resultCode", 0);
            result.put("msg", "登录成功");
        } catch (Exception e) {
            log.error("登录失败", e);
            result.put("resultCode", 100);
            result.put("msg", "登录失败，请重新尝试或者联系客服");
        } finally {
            return TokenUtil.jsonpCallback(request, result.toJSONString());
        }
    }


    /**
     * 登出
     */
    @RequestMapping(value = "/logout", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String logout(HttpServletRequest request,HttpServletResponse response,HttpSession session) {
        try {
            Object isWeixinAutoLogin = session.getAttribute(Constant.IS_WEIXIN_AUTOLOGIN);
            if(!(isWeixinAutoLogin != null && "true".equals(isWeixinAutoLogin.toString()))){
                Object user = session.getAttribute(Constant.LOGINUSER);
                if(user != null) {
                    session.removeAttribute(Constant.LOGINUSER);
                    //删除redis中的用户信息，且删除cookie
                    RedisUtil redisUtil = (RedisUtil) SpringApplicationUtil.getBean("redisUtil");
                    redisUtil.removeUserFromRedis(request,response,(User)user);
                }

                Object openid = session.getAttribute(WeixinConstants.WEIXIN_USER_SESSION_OPENID);

                HttpTookit.invalidateSession(session);
                if(openid != null) {
                    session.setAttribute(WeixinConstants.WEIXIN_USER_SESSION_OPENID,openid);
                }
            }
        }catch (Exception e) {
            log.error("logout error",e);
        }
        return "0";
    }

    /**
     * 检查用户名
     */
    @RequestMapping(value = "/checkUserName")
    public
    @ResponseBody
    String uniqueUserName(HttpServletRequest request) {
        String result = "0"; // 1可以注册，0不能注册
        String username = request.getParameter("userName");
        if (username != null && !"".equals(username)) {
            int x = userCXFService.uniqueUserName(username);
            log.info("check username " + username + " response = " + x);
            result = String.valueOf(x);

        }
        return result;
    }

    /**
     * 验证手机号
     */
    @RequestMapping(value = "/checkMobile")
    public
    @ResponseBody
    String checkMobile(HttpServletRequest request) {
        log.info(String.format("checkMobile begin time %s", System.currentTimeMillis()));
        String result = "0";
        String mobile = request.getParameter("mobile");
        if (mobile != null && !"".equals(mobile)) {
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("mobile", mobile);
            jsonArray.add(jsonObject);
            log.info("check mobile request param = " + jsonArray.toJSONString());
            String resultString = approCXFService.checkMobileIsExisting(jsonArray.toString());
            log.info("check mobile response = " + resultString);
            WSResponse wsResponse = JsonUtil.jsonToBean(resultString, WSResponse.class);
            if (wsResponse.getResultCode() < 0)
                result = "1";
        }
        log.info(String.format("checkMobile end time %s", System.currentTimeMillis()));
        return TokenUtil.jsonpCallback(request, result);
    }

    //注册新业贷用户(跨域) @author aiden at 2016-10-27 21:15:56
    @RequestMapping(value = "/reg4xyd", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    String reg4xyd(@RequestParam(value = "mobile", required = true) String mobile, @RequestParam(value = "messageCode", required = true) String messageCode, @RequestParam(value = "channel", required = true) String channel, HttpServletRequest request, HttpSession session) {
        log.info(MessageFormat.format("reg4xyd begin ... ...,time = {0}", System.currentTimeMillis()));
        JSONObject resultJson = new JSONObject();
        resultJson.put("resultCode", -500);
        resultJson.put("msg", "注册失败，未知错误");

        //校验短信验证码
        String busiCode = SmsConsts.BUSICODE_REGISTER_SMS;
        String smsCodeInSession = String.valueOf(request.getSession().getAttribute(busiCode + mobile));
        if(!RandCodeUtils.checkPhoneCode(messageCode, smsCodeInSession)){
            resultJson.put("resultCode", -304);
            resultJson.put("msg", "短信验证码错误！");
            return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
        }

        //新业贷用户注册（包含自动注册为官网用户&手机认证）
        JSONObject param = new JSONObject();
        param.put("phone", mobile);
        param.put("smsCode", smsCodeInSession);
        param.put("channel", channel);
        param.put("ip", HttpUtil.getRealIpAddr(request));
        param.put("terminal", HttpTookit.getRequestTerminal(request));
        param.put("siteFrom","http://www.xinxindai.com/m#!/static/html/activity/xinyedai.html?channel=" + channel);
        resultJson = userService.register4xyd(param);
        //session中保存手机号
        request.getSession().setAttribute("xydRegistedMobile", mobile);
        return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
    }

    //验证短信验证码（跨域） @author aiden at 2016-10-27 17:54:50
    @RequestMapping(value = "/checkSMS4xyd", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    String checkSMS4xyd(@RequestParam(value = "mobile", required = true) String mobile, @RequestParam(value = "messageCode", required = true) String messageCode, HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        String busiCode = SmsConsts.BUSICODE_REGISTER_SMS;
        String smsCodeInSession = request.getSession().getAttribute(busiCode + mobile).toString();
        if(RandCodeUtils.checkPhoneCode(messageCode, smsCodeInSession)){
            jsonObject.put("resultCode", 0 );
            jsonObject.put("msg", "短信验证码正确！");
        }else{
            jsonObject.put("resultCode", 1);
            jsonObject.put("msg", "短信验证码错误！");
        }
        return TokenUtil.jsonpCallback(request, jsonObject.toJSONString());
    }

    //发送短信（跨域） @author aiden at 2016-10-27 17:54:50
    @RequestMapping(value = "/sendSMS4xyd", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    String sendSMS4xyd(@RequestParam(value = "mobile", required = true) String mobile, HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        log.info(MessageFormat.format("sendSMS begin ... ...,time:{0}, phoneNumber:{1}", System.currentTimeMillis(), mobile));
        JSONObject resultJson = new JSONObject();

        //获取功能编号
        String busiCode = SmsConsts.BUSICODE_REGISTER_SMS;

        //验证图片验证码
        try {
            String sessionCode = String.valueOf(request.getSession().getAttribute("imgCode"));
            String paramCode = request.getParameter("messageCode");
            if (!RandCodeUtils.checkVerifyCode(paramCode, sessionCode)){
                resultJson.put("resultCode", -1);
                resultJson.put("msg", "图片验证码错误！");
                return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
            }
        } catch (Exception e) {
            log.error("验证图片验证码失败", e);
            resultJson.put("resultCode", -2);
            resultJson.put("msg", "服务器内部发生未知错误！");
            return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
        }

        //生成短信验证码
        int randCode = (int) Math.round(Math.random() * 8999) + 1000;

        //发送短信
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("mobile", mobile);
        jsonObject2.put("sendIp", HttpUtil.getRealIpAddr(request));
        jsonObject2.put("randCode", randCode);
        jsonObject2.put("smsTemplateCode", "MOBILE_REGISTER_CODE");
        jsonArray.add(jsonObject2);
        log.info(MessageFormat.format("invoke sendSMSWebservice,params:{0}", jsonArray.toJSONString()));
        String resultString2 = approCXFService.sendSmsMsgFromReg(jsonArray.toString());
        log.info(MessageFormat.format("sendSMSWebservice,response:{0}", resultString2));
        WSResponse wsResponse2 = JsonUtil.jsonToBean(resultString2, WSResponse.class);
        int resultCode = wsResponse2.getResultCode();
        if (0 == resultCode) {
            resultJson.put("resultCode", resultCode);
            resultJson.put("msg", "发送成功！");
            //session中添加 phone--randCode
            request.getSession().setAttribute(busiCode + mobile, randCode);
        } else if (-101 == resultCode) {
            resultJson.put("resultCode", -30);
            resultJson.put("msg", "您短信发送太频繁，如果无法收到短信请联系客服！4000-169-521");
        } else {
            resultJson.put("resultCode", resultCode);
            resultJson.put("msg", "系统繁忙，请稍后再试。");
        }
        log.info(MessageFormat.format("sendSMS end,time:{0}", System.currentTimeMillis()));
        return TokenUtil.jsonpCallback(request, resultJson.toString());
    }

    /**
     * 发送短信
     */
    @RequestMapping(value = "/sendSMS", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String sendSMS(@RequestParam(value = "phone", required = true) String phone,
                   @RequestParam(value = "type", required = true) int type, // type=1注册type=2忘记登录密码
                   HttpServletRequest request,
                   HttpSession session, HttpServletResponse response) {

        JSONObject resultJson = new JSONObject();

        //图片验证码
        String imgCode = request.getParameter("imgCode");
        log.info(String.format("sendSMS begin time = %s,phone=%s,imgCode=%s", System.currentTimeMillis(), phone,imgCode));
        if(imgCode == null || "".equals(imgCode)) {
            resultJson.put("resultCode", -101);
            resultJson.put("msg", "请填写图片验证码，请重新尝试");
            return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
        }

        Object sessionImgCodeObj = session.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        if(sessionImgCodeObj == null || "".equals(sessionImgCodeObj)) {
            resultJson.put("resultCode", -250);
            resultJson.put("msg", "图片验证码失效，请重新输入");
            return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
        }

        //获取功能编号
        String busiCode = SmsConsts.BUSICODE_REGISTER_SMS;

        session.setAttribute("phone", phone);
        //校验短信验证码发送次数限制
//        if (!MessageUtils.checkSendSMSCount(SmsConsts.BUSICODE_REGISTER_SMS + phone, request, response)) {
//            log.info("checkSendSMSCount Not by");
//            resultJson.put("resultCode", -30);
//            resultJson.put("msg", "您短信发送太频繁，如果无法收到短信请联系客服或使用语音验证码！");
//            return resultJson.toString();
//        }
        switch (MessageUtils.checkVerifyCodeTimeLimit(SmsConsts.BUSICODE_REGISTER_SMS + phone, MessageUtils.SENDTYPE_SMS, request, response)) {
            case 0:
                break;
            case 1:
                resultJson.put("msg", "一小时内最多可获取" + MessageUtils.SENDLIMIT_SMS_TIMES_1HOUR + "次短信验证码！");
                resultJson.put("resultCode", -31);
                return TokenUtil.jsonpCallback(request,resultJson.toString());
            case 2:
                resultJson.put("msg", "一天内最多可获取" + MessageUtils.SENDLIMIT_SMS_TIMES_24HOUR + "次短信验证码！");
                resultJson.put("resultCode", -31);
                return TokenUtil.jsonpCallback(request,resultJson.toString());
            default:
                break;
        }

        //session.removeAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        //Object sessionImgCodeObj = session.getAttribute("imgCode");


        if (imgCode != null && !"".equals(imgCode) && sessionImgCodeObj != null) {
            String sessionImgCode = sessionImgCodeObj.toString();
            if (!imgCode.equalsIgnoreCase(sessionImgCode)) {
                log.info("checkSendSMSCount Not by");
                resultJson.put("resultCode", -40);
                resultJson.put("msg", "您的图片验证码不正确");
                return TokenUtil.jsonpCallback(request,resultJson.toString());
            }
        }

        session.removeAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);

        int randCode = (int) Math.round(Math.random() * 8999) + 1000;
        session.setAttribute("smsCode", randCode);

        JSONArray jsonArray2 = new JSONArray();
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("mobile", phone);
        jsonObject2.put("sendIp", HttpUtil.getRealIpAddr(request));
        jsonObject2.put("randCode", randCode);
        jsonArray2.add(jsonObject2);

        log.info("send SMS request param ：" + jsonArray2.toJSONString());
        String resultString2 = approCXFService.sendSmsMsgFromReg(jsonArray2.toString());
        log.info("send SMS response ：" + resultString2);
        WSResponse wsResponse2 = JsonUtil.jsonToBean(resultString2, WSResponse.class);
//        if (wsResponse2.getResultCode() < 0) {
//            return "-2";
//        }
//        return "0";
        String msg = wsResponse2.getDesc();
        int resultCode = wsResponse2.getResultCode();
        if (0 == resultCode) {
            resultJson.put("resultCode", resultCode);
            resultJson.put("msg", "发送成功！");
            request.getSession().setAttribute(busiCode + phone, randCode);
        } else if (-101 == resultCode) {
            resultJson.put("resultCode", -30);
            resultJson.put("msg", "您短信发送太频繁，如果无法收到短信请联系客服或使用语音验证码！");
        } else {
            resultJson.put("resultCode", resultCode);
            resultJson.put("msg", "系统繁忙，请稍后再试。");
        }
        log.info(String.format("sendSMS end time = %s", System.currentTimeMillis()));
        return TokenUtil.jsonpCallback(request,resultJson.toString());
    }

    /**
     * 发送短信(根据指定短信模板发送短信) @author aiden at 2017-3-10 15:36:12
     */
    @RequestMapping(value = "/sendSMSTemplate", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String sendSMSTemplate(@RequestParam(value = "phone", required = true) String phone, HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        log.info("sendSMSTemplate params:" + JSONObject.toJSONString(request.getParameterMap()));
        JSONObject resultJson = new JSONObject();

        //校验：短信发送次数验证
        switch (MessageUtils.checkVerifyCodeTimeLimit(SmsConsts.BUSICODE_REGISTER_SMS + phone, MessageUtils.SENDTYPE_SMS, request, response)) {
            case 0:
                break;
            case 1:
                resultJson.put("msg", "一小时内最多可获取" + MessageUtils.SENDLIMIT_SMS_TIMES_1HOUR + "次短信验证码！");
                resultJson.put("resultCode", -31);
                return TokenUtil.jsonpCallback(request,resultJson.toString());
            case 2:
                resultJson.put("msg", "一天内最多可获取" + MessageUtils.SENDLIMIT_SMS_TIMES_24HOUR + "次短信验证码！");
                resultJson.put("resultCode", -31);
                return TokenUtil.jsonpCallback(request,resultJson.toString());
            default:
                break;
        }


        //获取参数
        String templateCode = request.getParameter("templateCode") == null ? SmsConsts.BUSICODE_SMS_TEMPLATE_CARNIVAL : request.getParameter("templateCode");//获取短信模板，无则取默认短信模板：BUSICODE_SMS_TEMPLATE_CARNIVAL
        String imgCode = request.getParameter("imgCode");//前端短信验证码
        Object sessionImgCodeObj = session.getAttribute("imgCode");//session中短信验证码

        //校验：短信验证码
        if(!RandCodeUtils.checkPhoneCode(imgCode, String.valueOf(sessionImgCodeObj))){
            resultJson.put("resultCode", -40);
            resultJson.put("msg", "短信验证码错误！");
            return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
        }

        //session中存储短信验证码
        int randCode = (int) Math.round(Math.random() * 8999) + 1000;
        session.setAttribute("phone", phone);
        session.setAttribute("smsCode", randCode);

        //短信通知
        JSONObject smsJson = new JSONObject();
        smsJson.put("mobile", phone);
        smsJson.put("sendIp", HttpUtil.getRealIpAddr(request));
        smsJson.put("randCode", randCode);
        smsJson.put("smsTemplateCode", templateCode);
        log.info(MessageFormat.format("根据短信模板发送短信参数,params:{0},",smsJson.toJSONString()));
        JSONObject smsResultJson = userService.sendSmsAfterRegister(smsJson);
        log.info(MessageFormat.format("根据短信模板发送短信返回,smsResultJson:{0},",smsResultJson.toJSONString()));
        if(!"0".equals(smsResultJson.getString("smsResultCode"))){
            log.error(MessageFormat.format("根据短信模板发送短信失败！phone:{0},",phone));
            resultJson.put("resultCode", -41);
            resultJson.put("msg", "短信通知密码失败！");
        }else{
            log.info(MessageFormat.format("根据短信模板发送短信失败！phone:{0},",phone));
            resultJson.put("resultCode", 0);
            resultJson.put("msg", "短信发送成功！");
        }

        return TokenUtil.jsonpCallback(request,resultJson.toString());
    }

    /**
     * 发送短信(忘记密码)
     */
    @RequestMapping(value = "/sendSMSForFindPwd", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String sendSMSForFindPwd(@RequestParam(value = "phone", required = true) String phone,
                             @RequestParam(value = "type", required = true) int type, // type=1注册type=2忘记登录密码
                             HttpServletRequest request,
                             HttpSession session, HttpServletResponse response) {

        //获取功能编号
        String busiCode = SmsConsts.BUSICODE_RETRIEVE_PASSWORD;

        JSONObject resultJson = new JSONObject();
//        String phone = request.getParameter("phone");
        Object userObj = request.getSession().getAttribute("loginUser");
        User userCheckMobile = null;
        if (userObj != null) {
            userCheckMobile = (User) userObj;
            if (!userCheckMobile.getMobile().equals(phone)) {
                resultJson.put("msg", "请输入当前登录用户绑定的手机号!");
                resultJson.put("resultCode ", -3);
                return resultJson.toString();
            }
        }
        String userStr = userCXFService.getUserByMobileNo(phone);
        UserResponse userResponse = JsonUtil.jsonToBean(userStr, UserResponse.class);
        if (userResponse.getResultCode() != AppConsts.WS_RETURN_SUCC) {
            resultJson.put("msg", "未找到与手机号匹配的用户账号，请重新输入!");
            resultJson.put("resultCode ", -3);
            return resultJson.toString();
        } else {
            //校验短信验证码发送次数限制
            if (!MessageUtils.checkSendSMSCount(busiCode + phone, request, response)) {
                resultJson.put("msg", "您短信发送太频繁，如果无法收到短信请联系客服或使用语音短信！");
                resultJson.put("resultCode", -30);
                return resultJson.toString();
            }

            User user = JsonUtil.jsonToBean(userResponse.getData().toString(), User.class);
            int randCode = (int) Math.round(Math.random() * 8999) + 1000;
            String content = "尊敬的用户：你正在通过手机重置密码，验证码是：" + randCode + "，工作人员不会索取，请勿泄漏，若有问题可致电客服：4000 169 521";
            log.info(phone + "找回密码短信内容：" + content);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("content", content);
            jsonObject.put("userId", user.getUserId());
            jsonObject.put("phone", user.getMobile());
            jsonObject.put("sendIp", HttpUtil.getRealIpAddr(request));
            String jsonstr = smsCXFService.sendSMS(jsonObject.toJSONString());
            WSResponse wsr = JsonUtil.jsonToBean(jsonstr, WSResponse.class);
            String msg = wsr.getDesc();
            //session保存手机的手机验证码
            if (wsr.getResultCode() == 0) {
                session.setAttribute("smsCode", randCode);
                session.setAttribute("phone", user.getMobile());
                request.getSession().setAttribute(busiCode + phone, randCode);
                msg = "验证码已发送到您尾号为" + user.getMobile().substring(7, 11) + "的手机号，";
                log.info(msg);
            }
            resultJson.put("msg", msg);
            resultJson.put("resultCode", wsr.getResultCode());
        }
        return resultJson.toString();
    }

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
        log.info(String.format("sendVoiceSMSLogout begin time %s", System.currentTimeMillis()));
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

        //获取功能编号
        String busiCode = request.getParameter("busiCode");
        if (SmsConsts.BUSICODE_REGISTER_VOICE.equals(busiCode)) {
            switch (MessageUtils.checkVerifyCodeTimeLimit(busiCode + mobileNo, MessageUtils.SENDTYPE_VOICE, request, response)) {
                case 0:
                    break;
                case 1:
                    resultJson.put("msg", "一小时内最多可获取" + MessageUtils.SENDLIMIT_VOICE_TIMES_1HOUR + "次语音验证码！");
                    resultJson.put("resultCode", -31);
                    return resultJson.toString();
                case 2:
                    resultJson.put("msg", "一天内最多可获取" + MessageUtils.SENDLIMIT_VOICE_TIMES_24HOUR + "次语音验证码！");
                    resultJson.put("resultCode", -31);
                    return resultJson.toString();
                default:
                    break;
            }
        } else {
            if (!MessageUtils.checkSendSMSCount(busiCode + "_VOICE" + mobileNo, request, response)) {
                resultJson.put("msg", "您语音发送太频繁，请稍后重试！");
                resultJson.put("resultCode", -3);
                return resultJson.toString();
            }
        }

        //图片验证码
        String imgCode = request.getParameter("imgCode");
        Object sessionImgCodeObj = request.getSession().getAttribute("imgCode");
        if (imgCode != null && !"".equals(imgCode) && sessionImgCodeObj != null) {
            String sessionImgCode = sessionImgCodeObj.toString();
            if (!imgCode.equalsIgnoreCase(sessionImgCode)) {
                log.info("checkSendSMSCount Not by");
                resultJson.put("resultCode", -40);
                resultJson.put("msg", "您的图片验证码不正确");
                return resultJson.toString();
            }
        }

        //生成验证码
        int randCode = (int) Math.round(Math.random() * 8999) + 1000;

        JSONObject obj = new JSONObject();
        String msg = "";
        int resultCode = 0;
        try {
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
                request.getSession().setAttribute(busiCode + mobileNo, randCode);
                request.getSession().setAttribute("smsCode", randCode);
                request.getSession().setAttribute("phone", mobileNo);
                log.info("语音验证码发送成功...");
                log.info("语音验证码：" + randCode);
            } else {
                resultCode = -2;
                msg = ws.getDesc();
                log.info("语音验证码发送失败：" + msg);
            }
        } catch (Exception e) {
            msg = "获取语音验证码失败，请刷新重试";
            resultCode = -3;
            log.error("sendVoiceSMSLogout ERROR:" + e.getMessage(), e);
        }
        resultJson.put("msg", msg);
        resultJson.put("resultCode", resultCode);

        log.info(String.format("sendVoiceSMSLogout end time %s", System.currentTimeMillis()));
        return resultJson.toString();
    }

    /**
     * 验证短信
     */
    @RequestMapping(value = "/checkSMS")
    public
    @ResponseBody
    String checkSMS(HttpServletRequest request) {
        String result = "1";
        try {
            String code = request.getParameter("code");
            String mobileNo = request.getParameter("mobileNo");
            String busiCode = request.getParameter("busiCode");
            String smsCode = String.valueOf(request.getSession().getAttribute(busiCode + mobileNo));
            log.info(String.format("checkSms session-smsCode[%s], req-smsCode[%s]", smsCode, code));
            if (smsCode != null && smsCode.equalsIgnoreCase(code)) {
                result = "0";
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 注册
     */
    @RequestMapping(value = "/reg", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String reg(HttpServletRequest request, HttpSession session, User user) {
        log.info("now enter UserController reg.");
        JSONObject resultJson = new JSONObject();
        resultJson.put("resultCode", 404);
        resultJson.put("msg", "该注册地址已失效");
        return resultJson.toJSONString();
       /* //判断当前token是否有效
        if (!TokenUtil.validToken(request)) {
            resultJson.put("resultCode", Constant.TOKEN_INVALID_ERROR);
            resultJson.put("msg", "页面已过期，请重新尝试");
            return resultJson.toString();
        }
        // 销毁token
        TokenUtil.removeToken(request);
        resultJson.put("resultCode", 0);
        resultJson.put("msg", "注册成功");
        if (user.getUserName() == null || "".equals(user.getUserName())) {
            resultJson.put("resultCode", 100);
            resultJson.put("msg", "您还未输入用户名");
            return resultJson.toJSONString();
        }

        if (!Pattern.compile("^[a-z|A-Z][\\u4E00-\\u9FA5\\uf900-\\ufa2d\\w@.]{2,16}$").matcher(user.getUserName()).find()) {
            resultJson.put("resultCode", 101);
            resultJson.put("msg", "您输入的用户名格式不正确");
            return resultJson.toJSONString();
        }

        if (userCXFService.uniqueUserName(user.getUserName()) == 1) {
            resultJson.put("resultCode", 102);
            resultJson.put("msg", "您输入的用户名已被注册");
            return resultJson.toJSONString();
        }

        if (user.getPassword() == null || "".equals(user.getPassword())) {
            resultJson.put("resultCode", 103);
            resultJson.put("msg", "您还未输入密码");
            return resultJson.toJSONString();
        }
        user.setEmail(null);
        user.setStatus("1");
        user.setAddIp(HttpUtil.getRealIpAddr(request));
        user.setPassword(EscapeCode.Encryption(user.getPassword()));
        user.setHeadImg(Configuration.getInstance().getValue("headimg"));

        // 用户来源
        String regsource = request.getParameter("regsource");
        regsource = regsource == null || "".equals(regsource) ? "7" : regsource;

        //注册时访问来源,与v6_front保持一致
        String defaultUrl = "http://www.xinxindai.com/m";
        String referer = (String) request.getSession().getAttribute("siteFrom");
        String landingPage = (String) request.getSession().getAttribute("landingPage");
        if (StringUtils.isBlank(referer)) {
            referer = defaultUrl;
        }
        if (StringUtils.isBlank(landingPage)) {
            landingPage = defaultUrl;
        }
        referer += "|" + landingPage;
        referer = referer.length() > 2000 ? referer.substring(0, 1999) : referer;

        user.setRegsource(regsource);
        user.setReferer(referer);

        //职业状态
        String occupationState = request.getParameter("job");
        if (StringUtils.isNotBlank(occupationState) && occupationState.equalsIgnoreCase("student")) {
            occupationState = OccupationConsts.OCCUPATION_STATE_UNDERGRADUATE;
        }

        user.setOccupation(occupationState);
        String clientIP = HttpUtil.getRealIpAddr(request);

        JSONObject regJson = new JSONObject();
        regJson.put("user", user);
        regJson.put("ip", clientIP);
        regJson.put("terminalVer", HttpTookit.getRequestTerminal(request));

        //_活动标识
        String fundActivityCode = request.getParameter("fundActivityCode");
        if (fundActivityCode != null && !"".equalsIgnoreCase(fundActivityCode)) {
            regJson.put("activityCode", fundActivityCode);
        }

        log.info("register request param : " + regJson.toJSONString());
        String userStr = userCXFService.registV2(regJson.toJSONString());
        log.info("register response :" + userStr);
        DataResponse dataResponse = JsonUtil.jsonToBean(userStr, DataResponse.class);
        if (dataResponse.getResultCode() < 0) {
            resultJson.put("resultCode", 104);
            resultJson.put("msg", "注册失败，请重新尝试或者联系客服");
            return resultJson.toJSONString();
        }
        user = JsonUtil.jsonToBean(dataResponse.getData().toString(), User.class);

        session.setAttribute("loginUser", user);

        boolean isMobileAppro = false;
        try {
            // 手机认证
            String phone = String.valueOf(request.getSession().getAttribute("phone"));
            String smsCode = String.valueOf(request.getSession().getAttribute("smsCode"));

            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("userId", user.getUserId());
            jsonObject1.put("mobile", phone);
            jsonObject1.put("randCode", smsCode);
            jsonObject1.put("clientIp", HttpUtil.getRealIpAddr(request));
            log.info("register appro mobile request param = " + jsonObject1.toJSONString());
            String resultStr4 = approCXFService.checkMobileCodeForApp(jsonObject1.toString());
            log.info("register appro mobile response = " + resultStr4);
            WSResponse resultRes4 = JsonUtil.jsonToBean(resultStr4, WSResponse.class);
            if (resultRes4.getResultCode() != 2) {
                log.error("user mobile appro fail：" + resultRes4.getDesc());
            } else {
                log.info("user mobile appro ok：" + phone);
                isMobileAppro = true;
            }
        } catch (Exception e) {
            log.error("user mobile appro fail：" + e.getMessage(), e);
        }

        try {
            // 添加账户
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", user.getUserId());
            jsonObject.put("ip", HttpUtil.getRealIpAddr(request));
            log.info("register init user account request param = " + jsonObject.toJSONString());
            String str = accountTradeCXFService.initialAccount(jsonObject.toJSONString());
            log.info("register init user account response = " + str);
            WSResponse wsResponse = JsonUtil.jsonToBean(str, WSResponse.class);

            if (wsResponse.getResultCode() != 0) {
                log.error("添加账户失败：" + wsResponse.getDesc());
            } else {
                log.info("添加账户成功：" + user.getUserName());

                String uuid = request.getParameter("uuid");
                if (uuid != null && !"".equalsIgnoreCase(uuid)) {
                    log.info("用户【" + user.getUserId() + "】注册，存在推广码：" + uuid);
                    //推广注册，执行活动动作
                    JSONObject param = new JSONObject();
                    param.put("uuId", uuid);
                    param.put("userId", user.getUserId());
                    param.put("activityCode", PopularizeConstant.ACTIVITY_CONSORTIUM_EXTENSION);
                    param.put("ip", HttpUtil.getRealIpAddr(request));
                    log.info("推广注册，请求参数：" + param.toJSONString());
                    String popularizeStr = popularizeCFXService.popularizeRegistration(param.toJSONString());
                    log.info("推广注册，响应内容：" + popularizeStr);
                    WSModelResponse resp = JsonUtil.jsonToBean(popularizeStr, WSModelResponse.class);
                    if (resp.getResultCode() == AppConsts.WS_RETURN_SUCC) {
                        log.info("推广注册成功");

                        if (isMobileAppro) {
                            Long redisMsgSeq = redisMsgCXFService.selectRedisMsgSeq();
                            JSONObject redisJson = new JSONObject();
                            redisJson.put("redisMsgId", redisMsgSeq);
                            redisJson.put("triggerName", PopularizeConstant.MOBILE_APPROVE_TRIGGER_NAME);
                            redisJson.put("userId", user.getUserId());
                            log.info("ACTIVITY_REWARD redis json " + redisJson.toJSONString());
                            redisMsgCXFService.saveAccountTradeMsg(redisMsgSeq,
                                    RedisConstant.ACTIVITY_REWARD_TOPIC,
                                    redisJson.toJSONString());

                            //发送通知
                            redisMsgCXFService.sendMessage(RedisConstant.ACTIVITY_REWARD_TOPIC, redisJson.toJSONString());
                        }
                    } else {
                        log.info("推广注册失败，失败原因：" + resp.getDesc() + "，结果码：" + resp.getResultCode());
                    }
                }
            }
        } catch (Exception e) {
            log.error("添加账户失败：" + e.getMessage(), e);
        }
        resultJson.put("userid", user.getUserId());
        resultJson.put("front_url", Configuration.getInstance().getValue("front_url"));
        resultJson.put("userName", user.getUserName());
        return resultJson.toJSONString();      */
    }

    /**
     * 注册V3
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/regV3", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String regV3(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
        log.info(String.format("reg begin time = %s", System.currentTimeMillis()));
        JSONObject resultJson = new JSONObject();
        resultJson.put("resultCode", 1);
        resultJson.put("msg", "注册失败，未知错误");
        try {
            //手机号码
            JSONObject param = new JSONObject();
            String phone = request.getParameter("phone");
            param.put("phone", phone);
            log.info(String.format("regV3 phone %s", phone));

            String tokenName = request.getParameter("tokenName");
            if (StringUtils.isNotBlank(tokenName)) {
                //判断当前token是否有效
                if (!TokenUtil.validToken(request)) {
                    JSONObject json = new JSONObject();
                    json.put("resultCode", Constant.TOKEN_INVALID_ERROR);
                    json.put("msg", "页面已过期，请重新尝试");
                    return TokenUtil.jsonpCallback(request,json.toJSONString());

                }
                log.info("reg token check passed...");
            }

            //短信验证码
            Object smsCodeObj = request.getSession().getAttribute("smsCode");
            String smsCodeReq = request.getParameter("smsCode");
            if (smsCodeObj == null || smsCodeReq == null) {
                resultJson.put("resultCode", 1024);
                resultJson.put("msg", "获取不到您的注册验证码，请返回重新尝试");
                return TokenUtil.jsonpCallback(request,resultJson.toJSONString());
            }
            String smsCode = String.valueOf(smsCodeObj);
            if (!smsCodeReq.equalsIgnoreCase(smsCode)) {
                resultJson.put("resultCode", 1025);
                resultJson.put("msg", "您的注册验证码错误，请返回重新尝试");
                return TokenUtil.jsonpCallback(request,resultJson.toJSONString());
            }

            param.put("smsCode", smsCode);

            //用户名
            String userName = request.getParameter("userName");
            param.put("userName", userName);


            //用户操作IP
            String ip = HttpUtil.getRealIpAddr(request);
            param.put("ip", ip);

            String referer = (String) request.getSession().getAttribute("siteFrom");
            param.put("referer", referer);
            String landingPage = (String) request.getSession().getAttribute("landingPage");
            param.put("landingPage", landingPage);

            //请求来源
            String terminal = HttpTookit.getRequestTerminal(request);
            param.put("terminal", terminal);

            //新新推广码
            String uuid = request.getParameter("uuid");
            param.put("uuid", uuid);

            String actCode = request.getParameter("actCode");
            param.put("actCode",actCode);

            //商户用户注册，商户编码
            String pCode = request.getParameter("pCode");
            param.put("pCode", pCode);

            //实名认证，上传身份证正反面图片地址
            String picUp = request.getParameter("picUp");
            param.put("picUp", picUp);
            String picDown = request.getParameter("picDown");
            param.put("picDown", picDown);

            //实名认证，真实姓名
            String realName = request.getParameter("realname");
            param.put("realname", realName);
            //实名认证，身份证号码
            String idCardNo = request.getParameter("idCardNo");
            param.put("idCardNo", idCardNo);

            //VIP认证，员工编号
            String vipCode = request.getParameter("vipCode");
            param.put("vipCode", vipCode);

            //密码
            String password = request.getParameter("password");
            param.put("password", password);

            //_体验金标识
            String fundActivityCode = request.getParameter("fundActivityCode");
            param.put("fundActivityCode", fundActivityCode);

            //注册
            resultJson = userService.regV3Handler(param);
            log.info(String.format("regV3Handler result %s ", resultJson));

            if (resultJson.get("user") != null) {
                User user = (User) resultJson.get("user");
                session.setAttribute(Constant.LOGINUSER, user);

                RedisUtil redisUtil = (RedisUtil) SpringApplicationUtil.getBean("redisUtil");
               // String userToken = redisUtil.putUsertoCache(request,user,response);
                JSONObject loginResult = userService.loginV7(user.getUserName(),password,HttpTookit.getUserAgent(request));
                int code = loginResult.getInteger("code");
                if(code == 0) {
                    String userToken = loginResult.getString("data");
                    redisUtil.setCookieToken(request,response,userToken);
                    resultJson.put(Constant.USERTOKEN, userToken);
                }


                resultJson.put("userid", user.getUserId());
                resultJson.put("userName", user.getUserName());
                /*************************用户维度star***********************************/
                /******注册时间******/

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String regDateStr = this.getDaySub(dateFormat.format(user.getAddTime()),dateFormat.format(new Date()));
                resultJson.put("regAddtime", regDateStr); //注册时间

                /******是否认证******/
                //获取用户认证信息
                Map<String, String> approMap = getUserApproInfo(user.getUserId());
                resultJson.put("ISrealnameAppro", "1".equals(approMap.get("realnameAppro"))?"已认证":"未认证");
            }

            resultJson.put("front_url", Configuration.getInstance().getValue("front_url"));

            log.info(String.format("reg end time = %s", System.currentTimeMillis()));

            resultJson.put("resultCode", 0);
            resultJson.put("msg", "注册成功");

            if (resultJson.get("regResultCode") != null && resultJson.getIntValue("regResultCode") == 0) {
                request.getSession().removeAttribute("smsCode");
            }

        } catch (Exception e) {
            log.error("regV3 error", e);
            resultJson.put("resultCode", 2);
            resultJson.put("msg", "注册失败");
        } finally {
            // 销毁token
            TokenUtil.removeToken(request);
            log.info("regV3 result:" + resultJson.toJSONString());
            return TokenUtil.jsonpCallback(request,resultJson.toJSONString());
        }
    }

    /**
     * 用户信息认证
     * @param userId
     * @return
     */
    private Map<String,String> getUserApproInfo(Long userId) {
        String mobileAppro = "0";
        String realnameAppro = "0";
        String vipAppro = "0";
        String emailAppro = "0";

        // 认证信息
        JSONObject  obj = new JSONObject();
        obj.put("userId", userId);
        String str = borrowQueryService.queryAppro(obj.toString());
        PersonResponse res =JsonUtil.jsonToBean(str, PersonResponse.class);

        if(res.getData()!=null){
            String dataStr = String.valueOf(res.getData());
            Appro appro =JsonUtil.jsonToBean(dataStr, Appro.class);
            if(appro!=null){
                mobileAppro = appro.getMobile();
                realnameAppro = appro.getRealName();
                vipAppro = appro.getVip();
                emailAppro = appro.getEmail();
            }
        }

        Map<String, String> approMap = new HashMap<String, String>();
        approMap.put("mobileAppro", mobileAppro);
        approMap.put("realnameAppro", realnameAppro);
        approMap.put("vipAppro", vipAppro);
        approMap.put("emailAppro", emailAppro);

        return approMap;
    }


    /**
     * 判断用户是否登陆
     */
    @RequestMapping(value = "/isLogin", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String isLogin(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        boolean islogin = false;
        /*Object isWeixinAutoLogin = request.getSession().getAttribute(Constant.IS_WEIXIN_AUTOLOGIN);
        if(isWeixinAutoLogin != null && "true".equals(isWeixinAutoLogin.toString())) {
            if(request.getSession().getAttribute(Constant.LOGINUSER) != null) {
                islogin = true;
            }
        } else {
            String token = HttpTookit.getCookieValue(request, Constant.USERTOKEN);
            User user = userService.getUserInfo(token, HttpTookit.getUserAgent(request));
            if(user != null) {
                request.getSession().setAttribute(Constant.LOGINUSER,user);
                islogin = true;
            }
        }*/

        String token = HttpTookit.getCookieValue(request, Constant.USERTOKEN);
        User user = userService.getUserInfo(token, HttpTookit.getUserAgent(request));
        if(user != null) {
            request.getSession().setAttribute(Constant.LOGINUSER,user);
            islogin = true;
        }

        jsonObject.put("isLogin", islogin);
        return jsonObject.toJSONString();
    }

    /**
     * 获得当前登录用户信息
     */
    @RequestMapping(value = "/getCurrentUser", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String queryCurrentUser(HttpServletRequest request) {
        JSONObject returnJson = new JSONObject();

        Object userObj = request.getSession().getAttribute("loginUser");
        if (userObj == null) {
            returnJson.put("code", "-1");
            returnJson.put("data", "");
            returnJson.put("msg", "您的会话失效");
            return returnJson.toString();
        }

        User user = (User) userObj;
        JSONObject userJson = new JSONObject();
        userJson.put("user", user);
        userJson.put("currentDate", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        userJson.put("front_url", Configuration.getInstance().getValue("front_url"));

        returnJson.put("code", "0");
        returnJson.put("data", userJson);
        returnJson.put("msg", "");
        return returnJson.toString();
    }

    /**
     * 根据手机号码获得用户信息
     */
    @RequestMapping(value = "/queryUserByMobile", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String queryUserByMobile(HttpServletRequest reqeust) {
        JSONObject returnJson = new JSONObject();
        String mobile = reqeust.getParameter("mobile");
        String str = userCXFService.getUserByMobileNo(mobile);
        UserResponse res = JsonUtil.jsonToBean(str, UserResponse.class);
        User user = null;
        if (res.getData() != null) {
            String dataStr = String.valueOf(res.getData());
            user = JsonUtil.jsonToBean(dataStr, User.class);
        }
        if (user == null) {
            returnJson.put("code", "-2");
            returnJson.put("data", "");
            returnJson.put("info", "用户不存在");
            return returnJson.toString();
        } else {
            JSONObject userJson = new JSONObject();
            userJson.put("userName", user.getUserName());

            returnJson.put("code", "0");
            returnJson.put("data", userJson);
            returnJson.put("info", "");
            return returnJson.toString();
        }
    }

    /**
     * 部码煞笔产品需求GrowingIO
     *
     * @param users
     * @return
     */
    public Map<String, String> getGrowingIoBM(User users) {
        Map<String, String> result = new HashMap<String, String>();
        JSONObject object = new JSONObject();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        try {
            // 认证信息
            object.clear();
            object.put("userId", users.getUserId());
            String realnameStr = borrowQueryService.queryAppro(object.toString());
            log.info(this.getClass() + "......认证信息" + realnameStr);
            PersonResponse pre = JsonUtil.jsonToBean(realnameStr, PersonResponse.class);
            String namestatus = "0";
            if (pre.getData() != null) {
                String dataStr = String.valueOf(pre.getData());
                Appro appro = JsonUtil.jsonToBean(dataStr, Appro.class);
                if (appro != null) {
                    namestatus = appro.getRealName();
                }
            }
            result.put("ISrealnameAppro", namestatus.equals("1") ? "已认证" : "未认证");

            //获取用户是否持有可用余额
            object.clear();
            int usable = 0;
            object.put("useId", users.getUserId());
            object.put("accountType", AccountConsts.ACCOUNT_ONLINE_DEFAULT_TYPE_ACCOUNT);
            log.info("StepUpwardController getUserAccountInfo ----> params:" + object.toJSONString());
            String resultAccountStr = accountQueryCXFService.selectAccountByIdAndType(object.toJSONString());
            log.info("StepUpwardController getUserAccountInfo ----> return:" + resultAccountStr);
            WSModelResponse accountResponse = JsonUtil.jsonToBean(resultAccountStr, WSModelResponse.class);
            if (accountResponse.getData() != null) {
                JSONObject defaultAccount = JSONObject.parseObject(accountResponse.getData().toString());
//                usable = new BigDecimal(defaultAccount.get("accountTotal").toString()).compareTo(BigDecimal.ZERO);
                usable = new BigDecimal(defaultAccount.get("usable").toString()).compareTo(BigDecimal.ZERO);
            }
            result.put("ISusable", usable == 1 ? "有" : "无");//余额状态

            //获取用户年龄
            String userAgeStr = userQueryCXFService.queryBaseInfo(JsonUtil.beanToJson(users.getUserId()));
            UserResponse ageRes = JsonUtil.jsonToBean(userAgeStr, UserResponse.class);
            if (ageRes.getData() != null) {
                String ageStr = String.valueOf(ageRes.getData());
                BaseInfo baseInfo = JsonUtil.jsonToBean(ageStr, BaseInfo.class);
                String birthDay = "";
                if (baseInfo.getBirthday() != null) {
                    birthDay = dateFormat.format(baseInfo.getBirthday());
                }
                String age = this.getAgeSub(baseInfo.getIdCardType(), baseInfo.getIdCardNo(), birthDay);
                log.info("获取用户年龄:" + age);
                result.put("age", age);
            }

            //获取用户是否财务扫标、是否投资过、是否持有产品
            object.clear();
            object.put("userId", users.getUserId());
            String strCash = accountCashprohibitCXFService.selectByUserIdIsFS(object.toJSONString());
            UserResponse cashResponse = JsonUtil.jsonToBean(strCash, UserResponse.class);
            if (cashResponse.getData() != null) {
                Map map = (Map) cashResponse.getData();
                //是否财务扫标
                int isFS = Integer.parseInt(map.get("isFS").toString());
                //是否投资过
                String IsInvester = Integer.parseInt(map.get("cot").toString()) > 0 ? "已投资用户" : "未投资用户";
                result.put("IsInvester", IsInvester);

                //投资次数
                String borrowCountStr = "";
                int borrowCount = Integer.parseInt(map.get("cot").toString());
                if(borrowCount > 0 && borrowCount <= 3){
                    borrowCountStr = borrowCount + "次";
                }else if(borrowCount > 3){
                    borrowCountStr = "3次以上";
                }
                result.put("ISInvesterCount", borrowCountStr);

                //是否持有产品
                String isProduct = Integer.parseInt(map.get("isProduct").toString()) > 0 ? "已持有" : "未持有";
                result.put("isProduct", isProduct);

                //购买金额
                BigDecimal totalMoney = BigDecimal.ZERO;
                if(null != map && map.get("totalMoney") != null && BigDecimal.ZERO.compareTo(new BigDecimal(map.get("totalMoney").toString())) != 0){
                    totalMoney = new BigDecimal(map.get("totalMoney").toString());
                }
                String totalMoneyStr = "";
                if(new BigDecimal("10000.00").compareTo(totalMoney) > 0){
                    //totalMoney < 10000
                    totalMoneyStr ="1万以下";
                }else if(new BigDecimal("10000.00").compareTo(totalMoney) <=0 && new BigDecimal("50000.00").compareTo(totalMoney) >= 0){
                    //10000<=totalMoney<= 50000
                    totalMoneyStr ="1-5万";
                }else if(new BigDecimal("50000.00").compareTo(totalMoney) < 0 && new BigDecimal("300000.00").compareTo(totalMoney) >= 0){
                    //50000<totalMoney<= 300000
                    totalMoneyStr ="5万以上-30万";
                }else if(new BigDecimal("300000.00").compareTo(totalMoney) < 0 && new BigDecimal("1000000.00").compareTo(totalMoney) >= 0){
                    //300000<totalMoney<= 1000000
                    totalMoneyStr ="30万以上-100万";
                }else if(new BigDecimal("1000000.00").compareTo(totalMoney) < 0 ){
                    //1000000>totalMoney
                    totalMoneyStr ="100万以上";
                }

                result.put("price", totalMoneyStr);

            }
        } catch (Exception e) {
            log.info("error.......获取growingIo布码异常!!!!!");
        }
        return result;
    }

    /**
     * 获取年龄
     *
     * @param idcardNoType
     * @param idcardNo
     * @param birthday
     * @return
     * @throws ParseException
     */
    public String getAgeSub(String idcardNoType, String idcardNo, String birthday) {
        String ageStr = "";
        String date = "";
        try {
            boolean flag = idcardNoType != null && !"".equals(idcardNoType) && !"null".equalsIgnoreCase(idcardNoType) && idcardNoType.equals("1");
            boolean idcardNoFlag = StringUtils.isNotEmpty(idcardNo) && !StringUtils.isEmpty(idcardNo) && !"null".equalsIgnoreCase(idcardNo);
            boolean idcardNoFlag1 = flag && idcardNoFlag && idcardNo.length() == 15;
            boolean idcardNoFlag2 = flag && idcardNoFlag && idcardNo.length() > 15;
            boolean birthdayFlag = StringUtils.isNotEmpty(birthday) && !StringUtils.isEmpty(birthday) && !"null".equalsIgnoreCase(birthday);
            boolean birthdayFlag1 = flag && idcardNoFlag == false && birthdayFlag;
            boolean birthdayFlag2 = !flag && birthdayFlag;
            if (idcardNoFlag1) {
                date = "19" + idcardNo.substring(6, 8);
                ageStr = this.getAge(date);
            } else if (idcardNoFlag2) {
                date = idcardNo.substring(6, 10);
                ageStr = this.getAge(date);
            } else if (birthdayFlag1) {
                date = birthday.substring(0, 4);
                ageStr = this.getAge(date);
            } else if (birthdayFlag2) {
                date = birthday.substring(0, 4);
                ageStr = this.getAge(date);
            } else {
                ageStr = "";
            }
        } catch (Exception e) {
            ageStr = "";
        }

        return ageStr;
    }

    /**
     * 年龄 = 当前时间-出生日期
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public String getAge(String date) {
        String ageStr = "";
        int age = 0;
        try {
            Integer ages = Integer.parseInt(date);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            Integer dates = Integer.parseInt(sdf.format(new Date()));
            age = dates - ages;
        } catch (Exception e) {
            age = 0;
        } finally {
            if(age <=18){
                ageStr = "18岁及以下";
            }else if(age >=19 && age<=25 ){
                ageStr = "19-25岁";
            }else if(age >=26 && age<=35 ){
                ageStr = "26-35岁";
            }else if(age >=36 && age<=49 ){
                ageStr = "36-49岁";
            }else if(age >=50 ){
                ageStr = "50岁及以上";
            }
        }
        return ageStr;
    }

    /**
     * 得到注册天数
     * @param beginDateStr
     * @param endDateStr
     */
    private  String getDaySub(String beginDateStr,String endDateStr){
        String regDateStr = "";
        long day=0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate;
        Date endDate;
        try{
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
            day = (endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
        } catch (ParseException e){
            log.info("得到注册天数，请求参数：开始时间："+beginDateStr+"结束世间："+endDateStr);
        }finally{
            if(day < 7){
                regDateStr = "注册时间小于7天";
            }else if(day>=7 && day<=30){
                regDateStr = "注册时间7-30天";
            }else if(day>=31 && day<=90){
                regDateStr = "注册时间31-90天";
            }else if(day >=91 && day <= 365){
                regDateStr = "注册时间91-365天";
            }else if(day>365){
                regDateStr = "注册时间大于365天";
            }
        }

        return regDateStr;
    }


    @RequestMapping(value = "/userDetailInfo", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    String userDetailInfo(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            Object userObj = request.getSession().getAttribute("loginUser");
            if(userObj == null) {
                result.put("code",402);
                result.put("msg","请先登录");
                return result.toJSONString();
            }

            User user = (User)userObj;

            Message msg = userService.userDetailInfo(String.valueOf(user.getUserId()));
            if(msg != null) {
                result.put("code",msg.getCode());
                result.put("msg",msg.getMessage());
                result.put("data",msg.getData());
            } else {
                result.put("code",404);
                result.put("msg","查询不到");
            }
        }catch (Exception e) {
            log.error("userDetailInfo error",e);
            result.put("code",500);
            result.put("msg","未知异常");
        }finally {
            return result.toJSONString();
        }
    }

    @RequestMapping(value = "/confirmInitRemindByToken", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String confirmInitRemindByToken(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            String token = HttpTookit.getCookieValue(request,Constant.USERTOKEN);
            Message msg = userService.confirmInitRemindByToken(token,HttpTookit.getUserAgent(request));
            if(msg != null) {
                result = JSONObject.parseObject(JSONObject.toJSONString(msg));
            } else {
                result.put("code",404);
                result.put("msg","查询不到");
            }
        }catch (Exception e) {
            log.error("confirmInitRemindByToken error",e);
            result.put("code",500);
            result.put("msg","未知异常");
        }finally {
            return result.toJSONString();
        }
    }

}
