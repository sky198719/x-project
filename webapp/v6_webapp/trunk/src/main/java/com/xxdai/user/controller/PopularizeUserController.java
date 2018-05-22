/**
 * Copyright (c) 2014, www.xinxindai.com All Rights Reserved.
 */
package com.xxdai.user.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxdai.client.CXF_Factory;
import com.xxdai.common.BaseController;
import com.xxdai.common.DataResponse;
import com.xxdai.constant.AccountConsts;
import com.xxdai.constant.AppConsts;
import com.xxdai.constant.Constant;
import com.xxdai.constant.SmsConsts;
import com.xxdai.core.util.cipher.CipherException;
import com.xxdai.core.util.cipher.DigestUtil;
import com.xxdai.core.util.http.HttpUtil;
import com.xxdai.core.util.http.WSMapResponse;
import com.xxdai.core.util.http.WSResponse;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.core.util.lang.DateUtil;
import com.xxdai.encrypt.EscapeCode;
import com.xxdai.exception.ServiceException;
import com.xxdai.external.user.ws.User;
import com.xxdai.external.user.ws.UserCXFService;
import com.xxdai.person.bo.BaseInfo;
import com.xxdai.person.model.Appro;
import com.xxdai.person.model.PersonResponse;
import com.xxdai.user.model.UserResponse;
import com.xxdai.user.service.UserService;
import com.xxdai.util.*;
import com.xxdai.ws.util.WSModelResponse;
import org.apache.commons.lang3.StringUtils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @version @author aiden at 2017-3-7 19:47:21
 * @since jdk1.6
 */
@SuppressWarnings("unchecked")
@Controller
@RequestMapping(value = "/populariseUser")
public class PopularizeUserController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(PopularizeUserController.class);

    private UserCXFService userCXFService = (UserCXFService) CXF_Factory.getFactory(UserCXFService.class, Configuration.getInstance().getValue("webService_url") + "/userWebService").create();

    @Autowired
    UserService userService;

    /**
     * 推广注册
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/popularizeUserReg", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String popularizeUserReg(HttpServletRequest request, HttpSession session) {
        logger.info("popularizeUserReg, params:{}", JSONObject.toJSONString(request.getParameterMap()));

        JSONObject resultJson = new JSONObject();
        resultJson.put("resultCode", 0);
        resultJson.put("msg", "操作成功！");

        //转化参数
        String realName = request.getParameter("realName");
        String idCode = request.getParameter("idCode");
        String groupType = request.getParameter("groupType");
        String mobilePhone = request.getParameter("mobilePhone");
        String mobileCode = request.getParameter("mobileCode");
        String password = request.getParameter("passWord");

        //验证身份证号码格式
        if (!IdcardValidator.isCardValidate(idCode)) {
            resultJson.put("resultCode", -1);
            resultJson.put("msg", "身份证格式错误！");
            return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
        }

        //验证手机号码格式
/*        if (!new PatternUtil().checkPhoneNOPattern(mobilePhone)) {
            resultJson.put("resultCode", -2);
            resultJson.put("msg", "手机格式不正确！");
            return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
        }*/

        //验证短信验证码
        Object smsCodeSession = request.getSession().getAttribute("smsCode");
        if (smsCodeSession == null || mobileCode == null) {
            resultJson.put("resultCode", -3);
            resultJson.put("msg", "获取不到您的注册验证码，请返回重新尝试");
            return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
        }
        String smsCodeSessionStr = String.valueOf(smsCodeSession);
        if (!RandCodeUtils.checkPhoneCode(mobileCode, smsCodeSessionStr)) {
            resultJson.put("resultCode", -4);
            resultJson.put("msg", "短信验证码不匹配！");
            return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
        }


        try {
            UserResponse res;
            //注册用户&账户
            JSONObject paramsJson = new JSONObject();
//            paramsJson.put("password", idCode.substring(idCode.length() - 6, idCode.length()));//默认身份证后六位
            paramsJson.put("password", password);//默认身份证后六位--前端传来
            paramsJson.put("phone", mobilePhone);
            paramsJson.put("smsCode", mobileCode);
            paramsJson.put("ip", HttpUtil.getRealIpAddr(request));
            paramsJson.put("terminal", HttpTookit.getRequestTerminal(request));
            logger.info(MessageFormat.format("合作注册--官网注册用户&账户参数，params:{0},", paramsJson.toJSONString()));
            JSONObject regResultJson = userService.registV3(paramsJson);
            logger.info(MessageFormat.format("合作注册--官网注册用户&账户返回，regResultJson:{0},", regResultJson.toJSONString()));
            if ("0".equals(regResultJson.getString("regResultCode"))) {
                logger.info("合作注册--官网注册用户&账户成功！");

                //手机认证
                logger.info(MessageFormat.format("合作注册--官网手机认证参数，params:{0},", paramsJson.toJSONString()));
                JSONObject mobileResultJson = userService.mobileAppro(paramsJson);
                logger.info(MessageFormat.format("合作注册--官网手机认证返回，mobileResultJson:{0},", mobileResultJson.toJSONString()));
                if (!"0".equals(mobileResultJson.getString("mobileResultCode"))) {
                    logger.error("合作注册--官网手机认证失败！");
                } else {
                    logger.info("合作注册--官网手机认证成功！");
                }

                //保存扩展信息
                if (regResultJson.get("user") != null) {
                    User user = (User) regResultJson.get("user");
                    JSONObject paramJson = new JSONObject();
                    paramJson.put("userId", user.getUserId());
                    JSONArray jsonArray = new JSONArray();

                    JSONObject attr1 = new JSONObject();
                    attr1.put("key", "project");
                    attr1.put("value", "全民户外运动嘉年华");
                    jsonArray.add(attr1);

                    JSONObject attr2 = new JSONObject();
                    attr2.put("key", "groupType");
                    attr2.put("value", groupType);
                    jsonArray.add(attr2);

                    JSONObject attr3 = new JSONObject();
                    attr3.put("key", "realName");
                    attr3.put("value", realName);
                    jsonArray.add(attr3);

                    JSONObject attr4 = new JSONObject();
                    attr4.put("key", "idCode");
                    attr4.put("value", idCode);
                    jsonArray.add(attr4);

                    paramJson.put("attriList", jsonArray);
                    logger.info(MessageFormat.format("合作注册--保存扩展信息参数，params:{0},", paramJson.toJSONString()));
                    String resultAccountStr = userCXFService.insertOrUpdateUserInfoExt(paramJson.toJSONString());
                    logger.info(MessageFormat.format("合作注册--保存扩展信息返回，resultAccountStr:{0},", resultAccountStr));
                    WSModelResponse accountResponse = JsonUtil.jsonToBean(resultAccountStr, WSModelResponse.class);
                    if (accountResponse.getResultCode() == 0) {
                        logger.info("合作注册--保存扩展信息成功！");
                    }
                }
            }else{
                logger.info("合作注册--官网注册失败！");
                resultJson.put("resultCode", -6);
                resultJson.put("msg", regResultJson.getString("regDesc"));
                return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
            }

            //返回
            logger.info("合作注册--官网注册成功！");
            resultJson.put("resultCode", 0);
            resultJson.put("msg", "合作注册--官网注册成功！");
            return TokenUtil.jsonpCallback(request, resultJson.toJSONString());

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("合作注册--官网注册发生异常，params:{0}", JSONObject.toJSONString(request.getParameterMap()));
            resultJson.put("resultCode", -5);
            resultJson.put("msg", "合作注册--官网注册失败！");
            return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
        }

    }

    /**
     * 推广注册-用户更改密码
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/updatePwd", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String updatePwd(HttpServletRequest request, HttpSession session) throws CipherException {
        logger.info("updatePwd， params： {}", JSONObject.toJSONString(request.getParameterMap()));

        JSONObject resultJson = new JSONObject();

        //转化参数
        String userName = request.getParameter("userName");
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");

        //用户登录验证
        JSONObject object = new JSONObject();
        object.put("username", userName);
        object.put("password", EscapeCode.Encryption(oldPassword));
        object.put("lastIp", HttpUtil.getRealIpAddr(request));
        String borwser = HttpTookit.getRequestTerminal(request);
        //可恶啊，数据库字段长度居然不够。
        borwser = borwser.length() > 200 ? borwser.substring(0, 200) : borwser;
        object.put("browser", borwser);

        logger.info("login request param:" + object.toJSONString());
        String userStr = userCXFService.login(object.toJSONString());
        logger.info("login response string : " + userStr);

        DataResponse userResponse = JsonUtil.jsonToBean(userStr, DataResponse.class);
        if (userResponse.getResultCode() != 0) {
            logger.info("login response faile");
            resultJson.put("resultCode", -1);
            resultJson.put("msg", "用户名或密码错误");
            return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
        }

        //查询用户信息
        User user;
        logger.info("queryUserByName request param:" + userName);
        String userJson = userCXFService.queryUserByName(userName);
        logger.info("queryUserByName response string:" + userJson);
        UserResponse res = JsonUtil.jsonToBean(userJson, UserResponse.class);
        if (res.getData() == null) {
            logger.info("合作注册--用户不存在！");
            resultJson.put("resultCode", -2);
            resultJson.put("msg", "用户不存在！");
            return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
        } else {
            user = (User)JsonUtil.jsonToBean(userResponse.getData().toString(), User.class);
            //更改密码
            JSONObject paraJson = new JSONObject();
            paraJson.put("userId", DigestUtil.md5ToHex(String.valueOf(user.getUserId())));
            paraJson.put("password", EscapeCode.Encryption(newPassword));
            paraJson.put("pswtype", 1);//登录密码
            paraJson.put("ip", HttpUtil.getRealIpAddr(request));

            logger.info("queryUserByName request param:{}", paraJson.toString());
            String str = userCXFService.changPassword(paraJson.toJSONString());
            logger.info("queryUserByName response string:{}", str);
            UserResponse response = JsonUtil.jsonToBean(str, UserResponse.class);

            if (response.getResultCode() == 0) {
                logger.info("alter pwd success");
            } else {
                logger.info("合作注册--更改用户密码失败！");
                resultJson.put("resultCode", -3);
                resultJson.put("msg", "更改用户密码失败！");
                return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
            }

            logger.info("合作注册--更改用户密码成功！");
            resultJson.put("resultCode", 0);
            resultJson.put("msg", "操作成功！");
            return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
        }
    }

}
