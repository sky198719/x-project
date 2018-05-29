/**
 * Copyright (c) 2015, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.user.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxdai.common.BaseService;
import com.xxdai.common.DataResponse;
import com.xxdai.constant.*;
import com.xxdai.core.util.http.WSResponse;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.core.util.lang.StringUtil;
import com.xxdai.encrypt.EscapeCode;
import com.xxdai.exception.ServiceException;
import com.xxdai.external.user.ws.User;
import com.xxdai.http.*;
import com.xxdai.person.bo.Employee;
import com.xxdai.person.model.PersonResponse;
import com.xxdai.util.Configuration;
import com.xxdai.util.MD5Utils;
import com.xxdai.util.RandCodeUtils;
import com.xxdai.ws.util.WSModelResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户
 *
 * @version $Id: UserService.java 2015/12/9 11:26 $
 * @since jdk1.6
 */
@Service
public class UserService extends BaseService {
    private Log log = LogFactory.getLog(UserService.class);

    @Autowired
    private ApiUtil apiUtil;
    /**
     * V3注册
     *
     * @param param
     * @return
     * @throws ServiceException
     */
    public JSONObject regV3Handler(JSONObject param) {
        log.info(String.format("regV3 param = %s", param));
        JSONObject result = new JSONObject();
        try {
            //注册
            JSONObject regResult = registV3(param);
            result.put("regResultCode", regResult.getString("regResultCode"));
            result.put("regDesc", regResult.getString("regDesc"));

            if (regResult.get("user") != null) {
                User user = (User) regResult.get("user");
                result.put("user", user);
                param.put("user", user);
            }

            //手机认证
            JSONObject mobileResult = mobileAppro(param);
            int mobileResultCode = 1;
            if (mobileResult != null) {
                mobileResultCode = mobileResult.getIntValue("mobileResultCode");
                result.put("mobileResultCode", mobileResultCode);
                result.put("mobileDesc", mobileResult.getString("mobileDesc"));
            }

            //新新推广
            JSONObject popularizeResult = popularize(param, mobileResultCode == 0 ? true : false);
            if (popularizeResult != null) {
                result.put("popularizeResultCode", popularizeResult.getString("popularizeResultCode"));
                result.put("popularizeDesc", popularizeResult.getString("popularizeDesc"));
            }

            //处理商户用户信息
            JSONObject partnerResult = partnerHandler(param);
            if (partnerResult != null) {
                result.put("partnerResultCode", partnerResult.getString("partnerResultCode"));
                result.put("partnerDesc", partnerResult.getString("partnerDesc"));
            }

            //实名认证
            JSONObject realNameResult = realName(param);
            if (realNameResult != null) {
                result.put("realNameResultCode", realNameResult.getString("realNameResultCode"));
                result.put("realNameDesc", realNameResult.getString("realNameDesc"));
            }

            //vip认证
            JSONObject vipResult = vipAppro(param);
            if (vipResult != null) {
                result.put("vipResultCode", vipResult.getString("vipResultCode"));
                result.put("vipDesc", vipResult.getString("vipDesc"));
            }
        } catch (ServiceException se) {
            result.put("resultCode", se.getErrorCode());
            result.put("msg", se.getMessage());
        } catch (Exception e) {
            result.put("resultCode", 201);
            result.put("msg", "注册失败");
            log.error("regV3 fail.", e);
        }
        return result;
    }

    //新业贷注册逻辑（包含官网注册&手机认证） @author aiden at 2016-10-28 11:00:10
    public JSONObject register4xyd(JSONObject param){
        JSONObject resultJson = new JSONObject();
        String phone = param.getString("phone");
        String ip = param.getString("ip");
        String channel = param.getString("channel");
        String terminal = param.getString("terminal");
        String siteFrom = param.getString("siteFrom");

        //注册新业贷用户（channel：15 --新业贷）
        log.info(MessageFormat.format("invoke webservice borrowApplyCXFService.saveBorrowApplyXyd,params:{0}", param.toJSONString()));
        String resultString = borrowApplyCXFService.saveBorrowApplyXyd(param.toJSONString());
        log.info(MessageFormat.format("borrowApplyCXFService.saveBorrowApplyXyd,response:{0}", resultString));
        WSModelResponse wsModelResponse = JsonUtil.jsonToBean(resultString, WSModelResponse.class);
        if(wsModelResponse != null){
            resultJson.put("resultCode",wsModelResponse.getResultCode());
            resultJson.put("msg",wsModelResponse.getDesc());
            if(wsModelResponse.getData() != null){
                resultJson.put("data",wsModelResponse.getData());
            }
            if(wsModelResponse.getResultCode() == 0){
                try{
                    //注册成为官网用户
                    String pwd = RandCodeUtils.getRandomPwd(8);//随机生成8位密码
                    param.put("password", MD5Utils.MD5Encoder(MD5Utils.MD5Encoder(pwd, "UTF-8"), "UTF-8"));
                    param.put("regsource", AccountConsts.XINYEDAI_RESOURCE);
                    JSONObject regResultJson = registV3(param);
                    if(!"0".equals(regResultJson.getString("regResultCode"))){
                        log.error(MessageFormat.format("新业贷注册用户，自动注册官网用户失败！phone:{0},",phone));
                        resultJson.put("regResultCode", -201);
                        resultJson.put("regMsg", "自动注册官网用户失败！");
                    }else{
                        log.info(MessageFormat.format("新业贷注册用户，自动注册官网用户成功！phone:{0},",phone));
                        resultJson.put("regResultCode", 201);
                        resultJson.put("regMsg", "自动注册官网用户成功！");
                    }

                    User user = (User) regResultJson.get("user");
                    if(user != null){
                        //手机认证
                        param.put("user", user);
                        JSONObject mobileResultJson = mobileAppro(param);
                        if(!"0".equals(mobileResultJson.getString("mobileResultCode"))){
                            log.error(MessageFormat.format("新业贷注册用户，自动手机认证失败！phone:{0},",phone));
                            resultJson.put("mobileApproResultCode", -301);
                            resultJson.put("mobileApproMsg", "自动手机认证失败！");
                        }else{
                            log.info(MessageFormat.format("新业贷注册用户，自动手机认证成功！phone:{0},",phone));
                            resultJson.put("mobileApproResultCode", 301);
                            resultJson.put("mobileApproMsg", "自动手机认证成功！");
                        }

                        //短信通知
                        JSONObject smsJson = new JSONObject();
                        smsJson.put("mobile", phone);
                        smsJson.put("sendIp", ip);
                        smsJson.put("randCode", pwd);
                        smsJson.put("userId", user.getUserId());
                        smsJson.put("smsTemplateCode", SmsConsts.AUTO_REG_PROMOTION_FROM_XYD);
                        JSONObject smsResultJson = sendSmsAfterRegister(smsJson);
                        if(!"0".equals(smsResultJson.getString("smsResultCode"))){
                            log.error(MessageFormat.format("新业贷注册用户，短信通知密码失败！phone:{0},",phone));
                            resultJson.put("smsResultCode", -401);
                            resultJson.put("smsMsg", "短信通知密码失败！");
                        }else{
                            log.info(MessageFormat.format("新业贷注册用户，短信通知密码成功！phone:{0},",phone));
                            resultJson.put("smsResultCode", 401);
                            resultJson.put("smsMsg", "短信通知密码成功！");
                        }
                    }

                }catch(Exception e){
                    log.error(MessageFormat.format("新业贷注册用户，自动注册成为官网用户 || 手机认证 || 短信发送失败！phone:{0}, ip:{1}, terminal:{2}, siteFrom:{3}, channel:{4}", phone, ip, terminal, siteFrom, channel));
                }
            }
        }

        return resultJson;
    }

    //注册成功后，发送短信告知用户密码 @author aiden at 2016-10-28 15:39:15
    public JSONObject sendSmsAfterRegister(JSONObject paramJson){
        JSONObject resultJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("mobile", paramJson.getString("mobile"));
        jsonObject2.put("sendIp", paramJson.getString("sendIp"));
        jsonObject2.put("randCode", paramJson.getString("randCode"));
        jsonObject2.put("userId", paramJson.getString("userId"));
        jsonObject2.put("smsTemplateCode", paramJson.getString("smsTemplateCode"));
        jsonArray.add(jsonObject2);
        log.info(MessageFormat.format("invoke sendSMSWebservice,params:{0}", jsonArray.toJSONString()));
        String resultString2 = approCXFService.sendSmsMsgFromReg(jsonArray.toString());
        log.info(MessageFormat.format("sendSMSWebservice,response:{0}", resultString2));
        WSResponse wsResponse2 = JsonUtil.jsonToBean(resultString2, WSResponse.class);
        int resultCode = wsResponse2.getResultCode();
        if (0 == resultCode) {
            resultJson.put("smsResultCode", resultCode);
            resultJson.put("msg", "发送成功！");
        } else if (-101 == resultCode) {
            resultJson.put("smsResultCode", -30);
            resultJson.put("msg", "您短信发送太频繁，如果无法收到短信请联系客服！");
        } else {
            resultJson.put("smsResultCode", resultCode);
            resultJson.put("msg", "系统繁忙，请稍后再试。");
        }
        log.info(MessageFormat.format("sendSMS end,time:{0}", System.currentTimeMillis()));
        return resultJson;
    }

    /**
     * 注册
     *
     * @param param
     * @throws ServiceException
     */
    public JSONObject registV3(JSONObject param) throws ServiceException {
        JSONObject result = new JSONObject();
        try {
            String userName = param.getString("userName");
            String phone = param.getString("phone");
            String ip = param.getString("ip");
            String password = param.getString("password");
            String referer = param.getString("siteFrom");
            String landingPage = param.getString("landingPage");
            String terminal = param.getString("terminal");
            String fundActivityCode = param.getString("fundActivityCode");
            //对手机号，密码，真实姓名，身份证号进行简单的校验
            if (StringUtils.isBlank(phone)) {
                throw new ServiceException("您还未输入手机号码", 101);
            }
            if (password == null || "".equals(password)) {
                throw new ServiceException("您还未输入密码", 102);
            }
            User user = new User();
            userName = userName != null && !"".equalsIgnoreCase(userName) ? userName : phone;
            user.setUserName(userName);
            user.setEmail(null);
            user.setStatus("1");
            user.setAddIp(ip);
            user.setPassword(EscapeCode.Encryption(password));
            user.setHeadImg(Configuration.getInstance().getValue("headimg"));

            // 用户来源
            String regsource = param.getString("regsource");
            regsource = regsource == null || "".equals(regsource) ? "7" : regsource;

            //注册时访问来源,与v6_front保持一致
            String defaultUrl = "http://www.xinxindai.com/m";
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
            String occupationState = param.getString("job");
            if (StringUtils.isNotBlank(occupationState) && occupationState.equalsIgnoreCase("student")) {
                occupationState = OccupationConsts.OCCUPATION_STATE_UNDERGRADUATE;
            }
            user.setOccupation(occupationState);

            JSONObject regJson = new JSONObject();
            regJson.put("user", user);
            regJson.put("ip", ip);
            regJson.put("terminalVer", terminal);

            //_活动标识
            if (fundActivityCode != null && !"".equalsIgnoreCase(fundActivityCode)) {
                regJson.put("activityCode", fundActivityCode);
            }

            //===注册
            log.info("register request param : " + regJson.toJSONString());
            log.info(String.format("begin time = %s", System.currentTimeMillis()));
            String userStr = userCXFService.registV3(regJson.toJSONString());
            log.info(String.format("end time = %s", System.currentTimeMillis()));
            log.info("register response :" + userStr);
            DataResponse dataResponse = JsonUtil.jsonToBean(userStr, DataResponse.class);
            if (dataResponse.getResultCode() < 0) {
                throw new ServiceException(dataResponse.getDesc(), 103);
            }
            user = JsonUtil.jsonToBean(dataResponse.getData().toString(), User.class);
            result.put("user", user);

            //添加账户
            param.put("user", user);
            createAccount(param);
            result.put("regResultCode", 0);
            result.put("regDesc", "注册成功");
            log.info("registV3 resp = " + result.toJSONString());
        } catch (ServiceException se) {
            log.error(String.format("[%s]%s", se.getErrorCode(), se.getMessage()));
            result.put("regResultCode", se.getErrorCode());
            result.put("regDesc", se.getMessage());
            //throw se;
        } catch (Exception e) {
            log.error("registV3 error.", e);
            result.put("regResultCode", 104);
            result.put("regDesc", "注册失败");
        }
        return result;
    }

    /**
     * vip认证
     *
     * @param param
     * @throws ServiceException
     */
    public JSONObject vipAppro(JSONObject param) {
        if (param.get("user") == null) {
            return null;
        }
        JSONObject result = new JSONObject();
        try {
            String vipCode = param.getString("vipCode");
            if (vipCode == null || "".equalsIgnoreCase(vipCode)) {
                log.info("vipCode is null");
                throw new ServiceException("VIP编码为空", 100);
            }

            User user = (User) param.get("user");
            String ip = param.getString("ip");

            String jsonstr = approQueryService.queryVipApproEmployeeNum(JsonUtil.beanToJson(vipCode));
            PersonResponse personResponse = JsonUtil.jsonToBean(jsonstr, PersonResponse.class);
            Employee employee = null;
            if (personResponse.getData() != null) {
                String dataStr = String.valueOf(personResponse.getData());
                employee = (Employee) JsonUtil.jsonToBean(dataStr, Employee.class);
            }

            if (null == employee) {
                throw new ServiceException("客服编号不存在", 404);
            }

            JSONArray vipJsonA = new JSONArray();
            JSONObject vipJsonB = new JSONObject();
            vipJsonB.put("userId", user.getUserId());
            vipJsonB.put("serviceNum", vipCode);
            vipJsonB.put("sendIp", ip);
            vipJsonA.add(vipJsonB);
            log.info("vip Apply req param=" + vipJsonA.toJSONString());
            log.info(String.format("begin time = %s", System.currentTimeMillis()));
            //String json = approCXFService.checkVip(vipJsonA.toString());
            String json = approCXFService.applyAndAuditVip(vipJsonA.toString());
            log.info(String.format("end time = %s", System.currentTimeMillis()));
            log.info("vip Apply resp = " + json);
            WSResponse wsResponse = JsonUtil.jsonToBean(json, WSResponse.class);
            if (wsResponse.getResultCode() == 1) {
                result.put("vipResultCode", 0);
                result.put("vipDesc", "VIP认证成功");
            } else {
                result.put("vipResultCode", wsResponse.getResultCode());
                result.put("vipDesc", wsResponse.getDesc());
            }
        } catch (ServiceException se) {
            log.error(String.format("[%s]%s", se.getErrorCode(), se.getMessage()));
            result.put("vipResultCode", se.getErrorCode());
            result.put("vipDesc", se.getMessage());
        } catch (Exception e) {
            log.error("vipApply,error = ", e);
            result.put("vipResultCode", 400);
            result.put("vipDesc", "VIP认证异常");
        }
        log.info("vipAppro result:" + result.toJSONString());
        return result;
    }

    /**
     * 实名认证
     *
     * @param param
     * @throws ServiceException
     */
    public JSONObject realName(JSONObject param) {
        if (param.get("user") == null) {
            return null;
        }
        JSONObject result = new JSONObject();
        try {
            String realName = param.getString("realname");
            String idCardNo = param.getString("idCardNo");

            if (StringUtils.isBlank(realName)) {
                throw new ServiceException("您还未输入真实姓名", 103);
            }
            if (StringUtils.isBlank(idCardNo)) {
                throw new ServiceException("您还未输入真实身份证号", 104);
            }
            /**调用身份认证接口 start**/
            String picUp = param.getString("picUp");
            String picDown = param.getString("picDown");
            JSONArray jsonArray = new JSONArray();
            JSONObject idJson = new JSONObject();
            User user = (User) param.get("user");
            String ip = param.getString("ip");
            idJson.put("userId", user.getUserId());
            idJson.put("realname", realName);
            idJson.put("idCardNo", idCardNo);
            idJson.put("idCardType", 1);
            idJson.put("picUp", picUp);
            idJson.put("picDown", picDown);
            idJson.put("sendIp", ip);
            jsonArray.add(idJson);
            log.info("checkRealName req param = " + jsonArray.toJSONString());
            log.info(String.format("begin time = %s", System.currentTimeMillis()));
            String jsonstr = approCXFService.checkRealName(jsonArray.toString());
            log.info(String.format("end time = %s", System.currentTimeMillis()));
            log.info("checkRealName resp= " + jsonstr);
            /**调用身份认证接口 over**/
            /**接口返回值处理 start*/
            WSResponse wsr = JsonUtil.jsonToBean(jsonstr, WSResponse.class);
            int resultCode = wsr.getResultCode();
            String msg = "";
            //认证已通过
            if (resultCode == 1) {
                result.put("realNameResultCode", 0);
                result.put("realNameDesc", "实名认证通过");
            } else {
                result.put("realNameResultCode", resultCode);
            }

            switch (resultCode) {
                case -6:
                    msg = "用户不存在，请重新登录";
                    break;
                case -7:
                    msg = "您当前使用的IP地址不合法，请勿攻击";
                    break;
                case -19:
                    msg = "已经认证通过，无需再次提交";
                    break;
                case -20:
                    msg = "身份证号码填写有误，请重新填写";
                    break;
                case -21:
                    msg = "身份证号码填写有误，请重新填写";
                    break;
                case -22:
                    msg = "身份证号码填写有误，请重新填写";
                    break;
                case -23:
                    msg = "证件号码已存在，请重新填写或与客服联系";
                    break;
                case -99:
                    msg = "系统繁忙，请与客服联系";
                    break;
                case 0:
                    msg = "已提交认证信息,请等待审核";
                    break;
                case 2:
                    msg = "认证不通过：身份证号码与姓名不一致，请重新输入";
                    break;
                case 3:
                    msg = "您的身份证信息审核未通过，请重新提交资料认证";
                    break;
                default:
                    msg = "系统繁忙，请与客服联系";
                    break;
            }

            result.put("realNameDesc", msg);
        } catch (ServiceException se) {
            log.info(String.format("[%s]%s", se.getErrorCode(), se.getMessage()));
            result.put("realNameResultCode", se.getErrorCode());
            result.put("realNameDesc", se.getMessage());
        } catch (Exception e) {
            log.info("实名认证申请异常：" + e.getMessage(), e);
            result.put("realNameResultCode", 401);
            result.put("realNameDesc", "实名认证申请异常");
        }
        log.info("realName result:" + result.toJSONString());
        return result;
    }

    /**
     * 处理商户用户信息
     *
     * @throws ServiceException
     */
    public JSONObject partnerHandler(JSONObject param) {
        if (param.get("user") == null) {
            return null;
        }
        JSONObject result = new JSONObject();
        try {
            String pCode = param.getString("pCode");
            String ip = param.getString("ip");
            if (!StringUtil.isNotBlank(pCode)) {
                log.info("pCode is null");
                throw new ServiceException("商户编码为空", 400);
            }
            JSONObject vipJsonB = new JSONObject();
            User user = (User) param.get("user");
            vipJsonB.put("userId", user.getUserId());
            vipJsonB.put("pCode", pCode);
            vipJsonB.put("ip", ip);
            log.info("add PartnerAndUserRelate req param=" + vipJsonB.toJSONString());
            log.info(String.format("begin time = %s", System.currentTimeMillis()));
            String json = partnerPromotionCXFService.addPartnerAndUserRelate(vipJsonB.toJSONString());
            log.info(String.format("end time = %s", System.currentTimeMillis()));
            log.info("add PartnerAndUserRelate resp = " + json);
            WSResponse wsResponse = JsonUtil.jsonToBean(json, WSResponse.class);
            result.put("partnerResultCode", wsResponse.getResultCode());
            result.put("partnerDesc", wsResponse.getDesc());
        } catch (ServiceException se) {
            log.error(String.format("[%s]%s", se.getErrorCode(), se.getMessage()));
            result.put("partnerResultCode", se.getErrorCode());
            result.put("partnerDesc", se.getMessage());
        } catch (Exception e) {
            log.error("addPartnerAndUserRelate,error = ", e);
            result.put("partnerResultCode", 401);
            result.put("partnerDesc", "绑定商户异常");
        }
        return result;
    }


    /**
     * 新新推广
     *
     * @param paramReg
     * @param isMobileAppro
     * @throws ServiceException
     */
    public JSONObject popularize(JSONObject paramReg, boolean isMobileAppro) {
        if (paramReg.get("user") == null) {
            return null;
        }
        JSONObject result = new JSONObject();
        try {
            String uuid = paramReg.getString("uuid");
            log.info("推广码：" + uuid);
            if (uuid == null || "".equalsIgnoreCase(uuid)) {
                throw new ServiceException("推广码为空", 300);
            }

            User user = (User) paramReg.get("user");
            String ip = paramReg.getString("ip");

            //推广注册，执行活动动作
            JSONObject param = new JSONObject();
            param.put("uuId", uuid);
            param.put("userId", user.getUserId());
            String actCode = paramReg.getString("actCode");
            actCode = actCode == null || "".equalsIgnoreCase(actCode) ? PopularizeConstant.ACTIVITY_CONSORTIUM_EXTENSION :actCode;
            param.put("activityCode", actCode);
            param.put("ip", ip);
            log.info("推广注册，请求参数：" + param.toJSONString());
            log.info(String.format("begin time = %s", System.currentTimeMillis()));
            String popularizeStr = popularizeCFXService.popularizeRegistration(param.toJSONString());
            log.info(String.format("end time = %s", System.currentTimeMillis()));
            log.info("推广注册，响应内容：" + popularizeStr);
            WSModelResponse resp = JsonUtil.jsonToBean(popularizeStr, WSModelResponse.class);
            if (resp.getResultCode() != AppConsts.WS_RETURN_SUCC) {
                log.info("推广注册失败，失败原因：" + resp.getDesc() + "，结果码：" + resp.getResultCode());
                throw new ServiceException("推广绑定失败", 301);
            }

            log.info("推广注册成功");
            result.put("popularizeResultCode", 0);
            result.put("popularizeDesc", "推广绑定成功");

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
        } catch (ServiceException se) {
            log.error(String.format("[%s]%s", se.getErrorCode(), se.getMessage()));
            result.put("popularizeResultCode", se.getErrorCode());
            result.put("popularizeDesc", se.getMessage());
        } catch (Exception e) {
            log.error("popularize Registration fail.", e);
            result.put("popularizeResultCode", 302);
            result.put("popularizeDesc", "推广绑定异常");
        }
        return result;
    }

    /**
     * 添加账户
     *
     * @param param
     * @throws ServiceException
     */
    public void createAccount(JSONObject param) throws ServiceException {
        try {
            String ip = param.getString("ip");
            User user = (User) param.get("user");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", user.getUserId());
            jsonObject.put("ip", ip);
            log.info("register init user account request param = " + jsonObject.toJSONString());
            log.info(String.format("begin time = %s", System.currentTimeMillis()));
            String str = accountTradeCXFService.initialAccount(jsonObject.toJSONString());
            log.info(String.format("end time = %s", System.currentTimeMillis()));
            log.info("register init user account response = " + str);
            WSResponse wsResponse = JsonUtil.jsonToBean(str, WSResponse.class);

            if (wsResponse.getResultCode() != 0) {
                log.error("添加账户失败：" + wsResponse.getDesc());
                throw new ServiceException("初始化账户失败", 150);
            } else {
                log.info("添加账户成功：" + user.getUserName());
            }
        } catch (Exception e) {
            log.error("添加账户失败：" + e.getMessage(), e);
            throw new ServiceException("初始化账户异常", 151);
        }
    }

    /**
     * 手机认证
     *
     * @param param
     * @return
     * @throws ServiceException
     */
    public JSONObject mobileAppro(JSONObject param) {
        if (param.get("user") == null) {
            return null;
        }
        JSONObject result = new JSONObject();
        try {
            String smsCode = param.getString("smsCode");
            String phone = param.getString("phone");
            String ip = param.getString("ip");
            User user = (User) param.get("user");
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("userId", user.getUserId());
            jsonObject1.put("mobile", phone);
            jsonObject1.put("randCode", smsCode);
            jsonObject1.put("clientIp", ip);
            log.info("register appro mobile request param = " + jsonObject1.toJSONString());
            log.info(String.format("begin time = %s", System.currentTimeMillis()));
            String resultStr4 = approCXFService.checkMobileCodeForApp(jsonObject1.toString());
            log.info(String.format("end time = %s", System.currentTimeMillis()));
            log.info("register appro mobile response = " + resultStr4);
            WSResponse resultRes4 = JsonUtil.jsonToBean(resultStr4, WSResponse.class);
            if (resultRes4.getResultCode() != 2) {
                log.error("user mobile appro fail：" + resultRes4.getDesc());
                result.put("mobileResultCode", 200);
                result.put("mobileDesc", "手机认证失败");
            } else {
                log.info("user mobile appro ok：" + phone);
                result.put("mobileResultCode", 0);
                result.put("mobileDesc", "手机认证通过");
            }
        } catch (Exception e) {
            log.error("user mobile appro fail：" + e.getMessage(), e);
            result.put("mobileResultCode", 201);
            result.put("mobileDesc", "手机认证异常");
        }
        log.info("mobileAppro result:" + result.toJSONString());
        return result;
    }

    public JSONObject loginV7(String userName,String password,String userAgent) {
        JSONObject result = new JSONObject();
        try {
            if(userName == null || "".equalsIgnoreCase(userName)) {
                result.put("code",500);
                result.put("message","用户名不能为空");
                return result;
            }

            if(password == null || "".equalsIgnoreCase(password)) {
                result.put("code",501);
                result.put("message","密码不能为空");
                return result;
            }

            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", Constant.CLIENTID)
                    .addHeader("clientTime", System.currentTimeMillis())
                    .addHeader("User-Agent",userAgent);
            log.info("loginV7 useragent = " + userAgent);
            String url = Configuration.getInstance().getValue("userCenter") + "/user/login";
            FormUrlEncoded fe = FormUrlEncoded.create();
            JSONObject reqData = new JSONObject();
            reqData.put("userName",userName);
            reqData.put("password",password);
            JSONObject json = new JSONObject();
            json.put("data",reqData);
            fe.setBody(json.toJSONString());
            log.info("loginv7 req " + json.toJSONString());
            Message msg = apiUtil.post(url,headers,fe);
            log.info("loginv7 resp " + JSONObject.toJSONString(msg));
            if(msg != null && msg.getCode() == 200000) {
                result = (JSONObject)msg.getData();
            } else {
                result.put("code",msg.getCode());
                String message = msg.getMessage();
                message = message == null ? "登录失败，请重新尝试或者联系客服" : msg.getMessage();
                result.put("message",message);
            }

        } catch (Exception e) {
            log.error("userCenter user/login error ",e);
            result.put("code",400);
            result.put("message","登录异常");
        } finally {
            log.info("user/login " + result.toJSONString());
            return result;
        }
    }

    public Message loginWeixin(String openId,String userAgent) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", Constant.CLIENTID)
                    .addHeader("clientTime", System.currentTimeMillis())
                    .addHeader("User-Agent",userAgent);

            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("userCenter"));
            url.append("/user/loginWeixin");

            QueryStrings qs = new QueryStrings();
            qs.addParameter("openId", openId);

            return apiUtil.get(url.toString(), headers, qs);
        }catch (Exception e) {
            log.error("loginWeixin error", e);
        }
        return null;
    }

    public JSONObject resetPassword(String phone,String pwd,String pswType,String smsCode) {
        JSONObject result = new JSONObject();
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", "XXD_INTEGRATION_PLATFORM")
                    .addHeader("clientTime", System.currentTimeMillis());

            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("userCenter"));
            url.append("/user/resetPassword");

            FormUrlEncoded fe = FormUrlEncoded.create();
            JSONObject reqData = new JSONObject();
            reqData.put("phone",phone);
            reqData.put("password",pwd);
            reqData.put("pswType",pswType);
            reqData.put("smsCode",smsCode);
            JSONObject json = new JSONObject();
            json.put("data",reqData);
            fe.setBody(json.toJSONString());

            Message msg = apiUtil.post(url.toString(),headers,fe);
            if(msg != null) {
                result.put("code",msg.getCode());
                result.put("info",msg.getMessage());
                result.put("data",msg.getData());
            } else {
                result.put("code",404);
                result.put("info","修改密码失败");
            }

        }catch (Exception e) {
            log.error("resetPassword error",e);
            result.put("code",400);
            result.put("info","设置密码异常，请重新尝试");
        } finally {
            return result;
        }
    }

    public JSONObject validatePayPwdByToken(String token,String password,String userAgent){
        JSONObject result = new JSONObject();
        try {

            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", "XXD_FRONT_END")
                    .addHeader("clientTime", System.currentTimeMillis())
                    .addHeader("token",token)
                    .addHeader("User-Agent",userAgent);

            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("userCenter"));
            url.append("/user/validatePayPwdByToken");

            FormUrlEncoded fe = FormUrlEncoded.create();
            JSONObject reqData = new JSONObject();
            reqData.put("payPassword",password);
            JSONObject json = new JSONObject();
            json.put("data",reqData);
            fe.setBody(json.toJSONString());

            Message msg = apiUtil.post(url.toString(),headers,fe);

            if(msg != null) {
                result.put("code",msg.getCode());
                result.put("msg",msg.getMessage());
                result.put("data",msg.getData());
            } else {
                result.put("code",404);
                result.put("msg","查询不到");
            }

        }catch (Exception e) {
            log.error("validatePayPwdByToken error",e);
            result.put("code",400);
            result.put("msg","验证码密码异常，请重新尝试");
        } finally {
            return result;
        }
    }

    public Message userDetailInfo(String userId){
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", Constant.XXD_INTEGRATION_PLATFORM)
                    .addHeader("clientTime", System.currentTimeMillis());
            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("userCenter"));
            url.append("/user/userDetailInfo");

            QueryStrings qs = new QueryStrings();
            qs.addParameter("userId",userId);
            return apiUtil.get(url.toString(),headers,qs);
        }catch (Exception e) {
            log.error("userDetailInfo error",e);
            return new ErrorMessage("未知异常");
        }
    }

    public User getUserInfo(String token,String userAgent) {
        User user = null;
       try {
           if(token == null || "".equals(token)){
               return user;
           }

           Headers headers = Headers.createHeaders()
                   .addHeader("clientId", "XXD_V6_WEBAPP")
                   .addHeader("clientTime", System.currentTimeMillis())
                   .addHeader("token",token)
                   .addHeader("User-Agent",userAgent);

           StringBuffer url = new StringBuffer();
           url.append(Configuration.getInstance().getValue("userCenter"));
           url.append("/user/userInfoByToken");

           Message msg = apiUtil.get(url.toString(),headers);

           //log.info("userCenter user/userInfoByToken resp = " + JSONObject.toJSONString(msg));

           if (200000 == msg.getCode()) {
               JSONObject data = (JSONObject) msg.getData();
               int code = data.getInteger("code");  // 登录状态

               if (code == 0) {
                   JSONObject userObj = data.getJSONObject("data");
                   String userId = userObj.getString("userid");
                   JSONObject param = new JSONObject();
                   param.put("userId", userId);
                  // log.info("getUserById req " + param);
                   String userStr = userCXFService.getUserById(param.toJSONString());
                  // log.info("getUserById resp " + userStr);
                   DataResponse dataResponse = JsonUtil.jsonToBean(userStr, DataResponse.class);
                   if (dataResponse.getData() != null) {
                       user = JsonUtil.jsonToBean(dataResponse.getData().toString(), User.class);
                   }
               }
           }
       } catch (Exception e) {
            log.error("getUser error ",e);
       } finally {
           return user;
       }
    }

    public Message isOpenOnAccount(String token,String userAgent,Long userId){
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", "XXD_INTEGRATION_PLATFORM")
                    .addHeader("clientTime", System.currentTimeMillis())
                    .addHeader("token",token)
                    .addHeader("User-Agent",userAgent);

            String userCenter = Configuration.getInstance().getValue("userCenter");
            StringBuffer url = new StringBuffer(userCenter);
            url.append("/user/userInfo?userId=").append(userId);

            return apiUtil.get(url.toString(),headers);

        }catch (Exception e) {
            log.error("isOpenOnAccount error",e);
        }
        return null;
    }

    public Message confirmInitRemindByToken(String token,String userAgent) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", "XXD_INTEGRATION_PLATFORM")
                    .addHeader("clientTime", System.currentTimeMillis())
                    .addHeader("token",token)
                    .addHeader("User-Agent",userAgent);

            String userCenter = Configuration.getInstance().getValue("userCenter");
            StringBuffer url = new StringBuffer(userCenter);
            url.append("/user/confirmInitRemindByToken");

            return apiUtil.post(url.toString(),headers);
        }catch (Exception e) {
            log.error("confirmInitRemindByToken error",e);
        }
        return null;
    }

    public Message beInLogging(String token,String userAgent) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", "XXD_INTEGRATION_PLATFORM")
                    .addHeader("clientTime", System.currentTimeMillis())
                    .addHeader("token",token)
                    .addHeader("User-Agent",userAgent);

            String userCenter = Configuration.getInstance().getValue("userCenter");
            StringBuffer url = new StringBuffer(userCenter);
            url.append("/user/beInLogging");

            return apiUtil.get(url.toString(),headers);
        }catch (Exception e) {
            log.error("beInLogging error",e);
        }
        return null;
    }
}
