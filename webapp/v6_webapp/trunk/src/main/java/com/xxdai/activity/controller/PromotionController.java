/**
 * Copyright (c) 2016, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.activity.controller;

import com.alibaba.fastjson.JSONObject;
import com.xxdai.client.CXF_Factory;
import com.xxdai.common.BaseController;
import com.xxdai.core.util.lang.StringUtil;
import com.xxdai.external.activityUser.ws.ActivityUserCXFService;
import com.xxdai.external.user.ws.User;
import com.xxdai.util.Configuration;
import com.xxdai.util.HttpTookit;
import com.xxdai.util.TokenUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述
 *
 * @version $Id: PromotionController.java 2016/11/14 17:05 $
 * @since jdk1.6
 */
@Controller
@RequestMapping(value = "/promotion")
public class PromotionController extends BaseController {
    private static final Logger logger = Logger.getLogger(PromotionController.class);
    private ActivityUserCXFService activityUserCXFService = (ActivityUserCXFService) CXF_Factory.getFactory(ActivityUserCXFService.class, Configuration.getInstance().getValue("webService_url") + "/activityUser").create();

    /**
     * *
     * 感恩节活动落地页初始化数据
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/initThanksGivingDayPage", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String initThanksGivingDayPage(HttpServletRequest request) {
        JSONObject resultObj = new JSONObject();
        try {
            Object userObj = request.getSession().getAttribute("loginUser");
            JSONObject paramsObj = new JSONObject();
            paramsObj.put("userId", null != userObj ? ((User) userObj).getUserId() : "");

            logger.info("queryThanksGivindDayInfo req = " + paramsObj.toJSONString());
            String resultStr = activityUserCXFService.queryThanksGivindDayInfo(paramsObj.toJSONString());
            logger.info("queryThanksGivindDayInfo resp = " + resultStr);
            resultObj = JSONObject.parseObject(resultStr);
            if (userObj != null) {
                resultObj.put("isLogin", "1");
            }
        } catch (Exception e) {
            logger.error("initThanksGivingDayPage error", e);
            resultObj.put("resultCode", 400);
        } finally {
            return TokenUtil.jsonpCallback(request, resultObj.toJSONString());
        }
    }

    /**
     * *
     * 感恩节活动--投骰子
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/joinTanksGivingDayActivity", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String joinTanksGivingDayActivity(HttpServletRequest request) {
        JSONObject resultObj = new JSONObject();
        try {
            //webapp登录判断 or app登录判断&sign校验
            resultObj = ActivityController.checkLoginStatus(request);
            if (resultObj.getIntValue("resultCode") != 0) {
                return TokenUtil.jsonpCallback(request, resultObj.toJSONString());
            }
            String userId = String.valueOf(resultObj.get("userId"));

            JSONObject paramsObj = new JSONObject();
            paramsObj.put("userId", userId);
            paramsObj.put("terminal", "WEBAPP");
            paramsObj.put("id", HttpTookit.getRealIpAddr(request));
            logger.info("joinTanksGivingDayActivity req = " + paramsObj.toJSONString());
            String resultStr = activityUserCXFService.joinThanksGivingDayActivity(paramsObj.toJSONString());
            logger.info("joinTanksGivingDayActivity resp = " + resultStr);
            resultObj = JSONObject.parseObject(resultStr);
        } catch (Exception e) {
            logger.error("joinTanksGivingDayActivity error", e);
            resultObj.put("resultCode", 400);
        } finally {
            return TokenUtil.jsonpCallback(request, resultObj.toJSONString());
        }
    }


    /**
     * *
     * 感恩节活动--我的奖品
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/myPrize", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String myPrize(HttpServletRequest request) {
        JSONObject resultObj = new JSONObject();
        try {
            //webapp登录判断 or app登录判断&sign校验
            resultObj = ActivityController.checkLoginStatus(request);
            if (resultObj.getIntValue("resultCode") != 0) {
                return TokenUtil.jsonpCallback(request, resultObj.toJSONString());
            }
            String userId = String.valueOf(resultObj.get("userId"));


            JSONObject paramsObj = new JSONObject();
            paramsObj.put("userId", userId);
            String resultStr = activityUserCXFService.queryMyPrizeList(paramsObj.toJSONString());
            resultObj = JSONObject.parseObject(resultStr);
        } catch (Exception e) {
            logger.error("myPrize error", e);
            resultObj.put("resultCode", 400);
        } finally {
            return TokenUtil.jsonpCallback(request, resultObj.toJSONString());
        }
    }

    /**
     * *
     * 网贷之家-双旦活动
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/initActivityPage", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String initActivityPage(HttpServletRequest request) {
        JSONObject resultObj = new JSONObject();
        JSONObject paramsObj = new JSONObject();

        String activityCode = request.getParameter("activityCode");
        if (StringUtil.isBlank(activityCode)) {
            resultObj.put("resultCode", -1);
            resultObj.put("msg", "活动参数为空");
            return TokenUtil.jsonpCallback(request, resultObj.toJSONString());
        }

        paramsObj.put("activityCode", activityCode);
        String activityStr = activityCXFService.isInActivityTime(paramsObj.toJSONString());
        resultObj = JSONObject.parseObject(activityStr);


        try {
            User user = (User) request.getSession().getAttribute("loginUser");
            if (null == user) {
                resultObj.put("resultCode", -1);
                resultObj.put("msg", "请先登录");
                return TokenUtil.jsonpCallback(request, resultObj.toJSONString());
            }

            paramsObj.put("userId", null != user ? user.getUserId() : 0);

            JSONObject dataObj = new JSONObject();
            //查询用户推广码
            String inviteStr = popularizeCFXService.queryUserInviteCode(paramsObj.toJSONString());
            JSONObject inviteObj = JSONObject.parseObject(inviteStr);
            if (null != inviteObj && inviteObj.getIntValue("resultCode") >= 0) {
                JSONObject data = inviteObj.getJSONObject("resultData");
                dataObj.put("nickName", data.getString("NICKNAME"));
                dataObj.put("iCode", data.getString("UUID"));
            }

            //查询用户投资情况
            String resultStr = activityCXFService.queryTenderInfoByActTime(paramsObj.toJSONString());
            JSONObject userActObj = JSONObject.parseObject(resultStr);
            if (null != userActObj && userActObj.getIntValue("resultCode") >= 0) {
                dataObj.put("qualifiedUser", userActObj.get("qualifiedUser"));//新老用户
                dataObj.put("qualifiedInvester", userActObj.get("qualifiedInvester"));//是否投资
            }

            resultObj.put("resultCode", 0);
            resultObj.put("msg", "查询成功");
            resultObj.put("data", dataObj);
        } catch (Exception e) {
            logger.error("initActivityPage Exception: " + e.getMessage(), e);
            resultObj.put("resultCode", -1);
            resultObj.put("msg", "查询失败");
        }
        return TokenUtil.jsonpCallback(request, resultObj.toJSONString());
    }
}
