/**
 * Copyright (c) 2016, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.activity.controller;

import com.alibaba.fastjson.JSONObject;
import com.xxdai.activity.constants.OlympicConstant;
import com.xxdai.activity.model.Activity;
import com.xxdai.client.CXF_Factory;
import com.xxdai.constant.AppConsts;
import com.xxdai.core.util.http.HttpUtil;
import com.xxdai.core.util.http.WSResponse;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.core.util.lang.DateUtil;
import com.xxdai.external.activityUser.ws.ActivityUserCXFService;
import com.xxdai.external.user.ws.User;
import com.xxdai.util.Configuration;
import com.xxdai.util.PageUtils;
import com.xxdai.ws.util.WSModelResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.*;

/**
 * 描述
 *
 * @version $Id: aoyunController.java 2016/8/11 20:06 $
 * @since jdk1.6
 */
@Controller
@RequestMapping(value = "/aoyun")
public class AoyunController {
    Logger log = Logger.getLogger(AoyunController.class);
    private ActivityUserCXFService activityUserCXFService = (ActivityUserCXFService) CXF_Factory.getFactory(ActivityUserCXFService.class, Configuration.getInstance().getValue("webService_url") + "/activityUser").create();


    @RequestMapping(value = "/queryExchangeInfo", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String queryExchangeInfo(HttpServletRequest request) {
        JSONObject resultJson = new JSONObject();
        try {
            //验证是否登录状态
            Object userObj = request.getSession().getAttribute("loginUser");
            if (null == userObj) {
                log.info("user no login");
                resultJson.put("resultCode", 100);
                resultJson.put("resultDesc", "您当前是未登录状态");
                return resultJson.toJSONString();
            }

            User user = (User) userObj;

            //获取用户兑奖信息 && 奖品剩余信息
            String userExchangeInfoStr = activityUserCXFService.queryExchangeInfo(String.valueOf(user.getUserId()));
            WSModelResponse userExchangeInfoRespon = JsonUtil.jsonToBean(userExchangeInfoStr, WSModelResponse.class);
            if (userExchangeInfoRespon.getResultCode() == AppConsts.WS_RETURN_SUCC && null != userExchangeInfoRespon.getData()) {
                JSONObject exchangeInfoJsonObj = JsonUtil.jsonToBean(String.valueOf(userExchangeInfoRespon.getData()), JSONObject.class);
                Map<String, Integer> restMap = JsonUtil.jsonToBean(String.valueOf(exchangeInfoJsonObj.get("restMap")), Map.class);
                Integer desPrizeId = (Integer) exchangeInfoJsonObj.get("desPrizeId");
                Integer actPrizeId = (Integer) exchangeInfoJsonObj.get("actPrizeId");
                Map<String, String> prizePromptMap = getPrizePrompt(restMap, actPrizeId, desPrizeId);
                resultJson.put("actPrizeId", actPrizeId);
                resultJson.put("prizePromptMap", prizePromptMap);
                resultJson.put("prizeName", OlympicConstant.PRIMENAMES.get(actPrizeId));
                resultJson.put("resultCode", 0);
            }
        } catch (Exception e) {
            log.error("queryExchangeInfo exception:", e);
            resultJson.put("resultCode", -1);
            resultJson.put("resultDesc", "获取数据异常，请稍后重试");
        }
        return resultJson.toJSONString();
    }

    private Map<String, String> getPrizePrompt(Map<String, Integer> resourceMap, Integer actPrizeId, Integer desPrizeId) {
        Map<String, String> resultMap = new HashMap<String, String>();
        Iterator<String> setIter = resourceMap.keySet().iterator();
        while (setIter.hasNext()) {
            String key = setIter.next();
            if (key == "7") {//310个新新币
                resultMap.put("PRIZEID" + key, "999+");
            } else if (Integer.valueOf(key) > desPrizeId) {
                resultMap.put("PRIZEID" + key, "五环种类不足");
            } else if (desPrizeId >= Integer.valueOf(key) && Integer.valueOf(key) > actPrizeId) {
                ;
                resultMap.put("PRIZEID" + key, "已兑完");
            } else {
                resultMap.put("PRIZEID" + key, MessageFormat.format("剩余{0}个", resourceMap.get(key)));
            }
        }
        return resultMap;
    }

    @RequestMapping(value = "/getOlympicLast14List", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getOlympicLast14List(HttpServletRequest request) {
        JSONObject resultJson = new JSONObject();
        try {
            //获得最新14条活动信息
            String activityLast14List = activityUserCXFService.getOlympicLast14List();
            WSModelResponse last14ListResponse = JsonUtil.jsonToBean(activityLast14List, WSModelResponse.class);
            if (last14ListResponse.getResultCode() == AppConsts.WS_RETURN_SUCC && null != last14ListResponse.getData()) {
                PageUtils page = JsonUtil.jsonToBean(String.valueOf(last14ListResponse.getData()), PageUtils.class);
                resultJson.put("last14lists", page.getResultList());
                resultJson.put("resultCode", 0);
            }
        } catch (Exception e) {
            log.error("getOlympicLast14List exception:", e);
            resultJson.put("resultCode", -1);
            resultJson.put("resultDesc", "获取数据异常，请稍后重试");
        }
        return resultJson.toJSONString();
    }

    @RequestMapping(value = "/getActivityInfo", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getActivityInfo(HttpServletRequest request) {
        JSONObject resultJson = new JSONObject();
        try {
            //获取活动信息
            Activity activity = null;
            String activityStr = activityUserCXFService.queryActivityInfo(OlympicConstant.OLYMPIC_ACTIVITY_CODE);
            WSModelResponse response = JsonUtil.jsonToBean(activityStr, WSModelResponse.class);
            if (response.getResultCode() == AppConsts.WS_RETURN_SUCC && null != response.getData()) {
                activity = JsonUtil.jsonToBean(String.valueOf(response.getData()), Activity.class);
                resultJson.put("resultCode", 0);
                resultJson.put("activity", activity);
                resultJson.put("currentDate", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            }
        } catch (Exception e) {
            log.error("getActivityInfo exception:", e);
            resultJson.put("resultCode", -1);
            resultJson.put("resultDesc", "获取数据异常，请稍后重试");
        }
        return resultJson.toJSONString();
    }

    @RequestMapping(value = "/getUserActivityInfo", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getUserActivityInfo(HttpServletRequest request) {
        JSONObject resultJson = new JSONObject();
        try {
            //验证是否登录状态
            Object userObj = request.getSession().getAttribute("loginUser");
            String userId = "-1";
            if (null == userObj) {
                log.info("user no login");
                resultJson.put("resultCode", 100);
                resultJson.put("resultDesc", "您当前是未登录状态");
                return resultJson.toJSONString();
            }

            //获取活动信息
            Activity activity = null;
            String activityStr = activityUserCXFService.queryActivityInfo(OlympicConstant.OLYMPIC_ACTIVITY_CODE);
            WSModelResponse response = JsonUtil.jsonToBean(activityStr, WSModelResponse.class);
            if (response.getResultCode() == AppConsts.WS_RETURN_SUCC && null != response.getData()) {
                activity = JsonUtil.jsonToBean(String.valueOf(response.getData()), Activity.class);
            }

            User user = (User) userObj;
            userId = user.getUserId().toString();
            if (null != user) {
                //获取用户已获得的五环的信息
                String userRingInfoStr = activityUserCXFService.query5RingsInfoByUserId(String.valueOf(user.getUserId()));
                WSModelResponse userRingInfoRespon = JsonUtil.jsonToBean(userRingInfoStr, WSModelResponse.class);
                if (userRingInfoRespon.getResultCode() == AppConsts.WS_RETURN_SUCC && null != userRingInfoRespon.getData()) {
                    List<Map<String, BigDecimal>> userRingInfoMap = JsonUtil.jsonToBean(String.valueOf(userRingInfoRespon.getData()), List.class);
                    //处理用户已经获得5环的信息
                    resultJson.put("userRingInfoMap", covertMapListToMap(userRingInfoMap));
                }
                //获取用户已兑奖品记录
                String userExchRecStr = activityUserCXFService.queryExchangePrizeRecord(String.valueOf(user.getUserId()));
                WSModelResponse userExchRecRespon = JsonUtil.jsonToBean(userExchRecStr, WSModelResponse.class);
                Map<String, Object> userExchRecMap = JsonUtil.jsonToBean(String.valueOf(userExchRecRespon.getData()), Map.class);
                resultJson.put("userExchRecMap", userExchRecMap);

                int userActivityPhase = getUserActivityPhase(activity, (Map<String, BigDecimal>) resultJson.get("userRingInfoMap"), user, resultJson.get("userExchRecMap"));
                resultJson.put("userActivityPhase", userActivityPhase);
            }

            //查询活动信息&用户个人活动投资信息&总体活动投资信息
            log.info("queryUserActivityInfo param userid= " + userId);
            String jsonStr = activityUserCXFService.queryUserActivityInfo(userId);
            resultJson.put("resultCode", 0);
            resultJson.put("data", jsonStr);
        } catch (Exception e) {
            log.error("getUserActivityInfo exception:", e);
            resultJson.put("resultCode", -1);
            resultJson.put("resultDesc", "获取数据异常，请稍后重试");
        }
        return resultJson.toJSONString();
    }

    /*--============================================ PRIVATE MECHOD ==================================================--*/

    /**
     * 计算用户当前所处的状态
     */
    private int getUserActivityPhase(Activity activity, Map<String, BigDecimal> ringMap, User user, Object exchangeInfo) {
        if (null != activity) {
            Date nowDay = new Date();
            boolean activityOver = false;
            try {
                if (nowDay.after(DateUtil.parseDate(activity.getEndtime(), DateUtil.LongDateFormat))) {
                    activityOver = true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (nowDay.before(activity.getStarttime())) {
                return 0; //活动未开始
            }

            if (!activityOver && null == user) {
                return 1;//活动期间用户未登录
            }

            if (!activityOver && (null == ringMap || ringMap.size() == 0)) {
                return 2;//活动期间用户未投资
            }

            if (!activityOver && (null != ringMap && ringMap.size() > 0) && null == exchangeInfo) {
                return 3;//活动期间用户未兑奖
            }

            if (!activityOver && (null != ringMap && ringMap.size() > 0) && null != exchangeInfo) {
                return 4;//活动期间用户已兑奖
            }

            if (activityOver && null == user) {
                return 5;//活动结束未登录
            }

            if (activityOver && (null == ringMap || ringMap.size() == 0)) {
                return 6;//活动结束用户未投资
            }

            if (activityOver && (null != ringMap && ringMap.size() > 0) && null == exchangeInfo) {
                return 7;//活动结束用户未兑奖
            }

            if (!activityOver && (null != ringMap && ringMap.size() > 0) && null != exchangeInfo) {
                return 8;//活动结束用户已兑奖
            }

            return -1;//用户状态异常
        }
        return -2;//数据异常：活动不存在
    }

    private Map<String, BigDecimal> covertMapListToMap(List<Map<String, BigDecimal>> resourceList) {
        Map<String, BigDecimal> resultMap = new HashMap<String, BigDecimal>();
        Iterator<Map<String, BigDecimal>> iterList = resourceList.iterator();
        while (iterList.hasNext()) {
            Map<String, BigDecimal> itemMap = iterList.next();
            Iterator<String> itemIter = itemMap.keySet().iterator();
            String firstKey = itemIter.next();
            String secondKey = itemIter.next();
            resultMap.put("NUM" + String.valueOf(itemMap.get(firstKey)), itemMap.get(secondKey));
        }
        return resultMap;
    }

       // 兑奖
    @RequestMapping("/exchange")
    @ResponseBody
    public String exchange(HttpSession session, HttpServletRequest request) throws Exception {
        JSONObject resultJson = new JSONObject();

        try {
            Object userObj = session.getAttribute("loginUser");
            if (userObj == null) {
                resultJson.put("resultCode",100);
                resultJson.put("msg", "您还没有登录，请登录后参加活动！");
                return resultJson.toJSONString();
            }
            User user = (User)userObj;
            JSONObject params = new JSONObject();
            params.put("userId", user.getUserId());
            params.put("terminalver", request.getHeader("user-agent"));
            params.put("ip", HttpUtil.getRealIpAddr(request));
            log.info("aoyun exchange req " + params.toJSONString());
            String str = activityUserCXFService.exchangePrize(JsonUtil.beanToJson(params));
            log.info("aoyun exchange resp " + str);
            WSResponse wsResponse = JsonUtil.jsonToBean(str, WSResponse.class);             ;
            resultJson.put("resultCode",wsResponse.getResultCode());
            resultJson.put("msg",wsResponse.getDesc());
        } catch (Exception e) {
            log.error("aoyun exchange error",e);
            resultJson.put("resultCode",-1);
            resultJson.put("msg", "兑换异常，请重新尝试");
        }
        return resultJson.toJSONString();
    }
}
