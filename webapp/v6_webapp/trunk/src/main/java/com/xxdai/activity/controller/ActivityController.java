/**
 * Copyright (c) 2014, www.xxdai.com All Rights Reserved. 
 */
package com.xxdai.activity.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ctc.wstx.util.StringUtil;
import com.xxdai.activity.constants.ActivityConstant;
import com.xxdai.appro.bo.RealNameAppro;
import com.xxdai.client.CXF_Factory;
import com.xxdai.client.ClientUtil;
import com.xxdai.common.BaseController;
import com.xxdai.constant.AppConsts;
import com.xxdai.core.util.http.HttpUtil;
import com.xxdai.core.util.http.WSMapResponse;
import com.xxdai.core.util.http.WSResponse;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.core.util.lang.DateUtil;
import com.xxdai.external.activity.ws.ActivityCXFService;
import com.xxdai.external.activityUser.ws.ActivityUserCXFService;
import com.xxdai.external.partner.ws.PartnerPromotionCXFService;
import com.xxdai.external.question.ws.QuestionCXFService;
import com.xxdai.external.user.ws.User;
import com.xxdai.person.model.PersonResponse;
import com.xxdai.util.Configuration;
import com.xxdai.util.MD5Utils;
import com.xxdai.util.TokenUtil;
import com.xxdai.ws.util.WSModelResponse;
import com.xxdai.ws.util.WSPageResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

/**
 * ActivityController
 *
 * @since jdk1.6
 */
@Controller
@RequestMapping(value = "/activity")
public class ActivityController extends BaseController {

    private static final Logger log = Logger.getLogger(ActivityController.class);
    private PartnerPromotionCXFService partnerPromotionCXFService = (PartnerPromotionCXFService) CXF_Factory.getFactory(PartnerPromotionCXFService.class, Configuration.getInstance().getValue("webService_url") + "/partnerPromotionWebService").create();
    private final ActivityCXFService activityCXFService = (ActivityCXFService) ClientUtil.getWebService(ActivityCXFService.class, "activityWebService");
    private final static ActivityUserCXFService activityUserCXFService = (ActivityUserCXFService) ClientUtil.getWebService(ActivityUserCXFService.class, "activityUser");
    /**
     * 问卷信息查询接口
     */
    private QuestionCXFService questionCXFService = (QuestionCXFService) CXF_Factory.getFactory(QuestionCXFService.class,Configuration.getInstance().getValue("webService_url")+"/questionWebService").create();

    /**
     * 分店推广总数查询
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/store/getSumData", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getSumData(HttpServletRequest request, HttpServletRequest response) {
        JSONObject resultJson = new JSONObject();
        try {
            //webservice查询参数
            JSONObject paramJson = new JSONObject();
            String pCode = request.getParameter("pCode");
            paramJson.put("pCode", pCode);
          //  log.info("store getSumData req params:" + paramJson.toString());

            //webservice返回结果
            String resultStr = partnerPromotionCXFService.queryPartnerStorePromotionTotal(paramJson.toString());
           // log.info("store getSumData resp:" + resultStr);

            //返回页面
            return resultStr;

        } catch (Exception e) {
            log.error("store getSumData arise exception:", e);
            //返回页面
            resultJson.put("resultCode", -1);
            resultJson.put("resultDesc", "操作失败，请稍后重试...");
            return resultJson.toJSONString();
        }
    }

    /**
     * 分店注册用户查询
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/store/getStoreListData", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getStoreListData(HttpServletRequest request, HttpServletRequest response) {
        JSONObject resultJson = new JSONObject();
        try {
            //webservice查询参数
            JSONObject paramJson = new JSONObject();
            String pCode = request.getParameter("pCode");
            String beginDate = request.getParameter("beginDate");
            String endDate = request.getParameter("endDate");
            String phoneNo = request.getParameter("phoneNo");
            String currentPage = request.getParameter("currentPage");
            String pageSize = request.getParameter("pageSize");
            if (beginDate != null && !"".equals(beginDate)) {
                beginDate += " 00:00:00";
            }

            if (endDate != null && !"".equals(endDate)) {
                endDate += " 23:59:59";
            }

            paramJson.put("pCode", pCode);
            if (!"".equalsIgnoreCase(beginDate)) {
                paramJson.put("beginDate", beginDate);
            }

            if (!"".equalsIgnoreCase(endDate)) {
                paramJson.put("endDate", endDate);
            }
            paramJson.put("phone", phoneNo);
            paramJson.put("currentPage", currentPage);
            paramJson.put("pageSize", pageSize);
            log.info("getStoreListData req params:" + paramJson.toString());

            //webservice返回结果
            String resultStr = partnerPromotionCXFService.queryPartnerStorePromotionInfo(paramJson.toString());
          //  log.info("getStoreListData resp:" + resultStr);
            resultJson = JSON.parseObject(resultStr);
            resultJson.put("currentPage", currentPage);
            resultJson.put("resultCode", 0);
        } catch (Exception e) {
            log.error("getStoreListData arise exception:", e);
            //返回页面
            resultJson.put("resultCode", -1);
            resultJson.put("resultDesc", "操作失败，请稍后重试...");
        }
        //返回页面
        return resultJson.toJSONString();
    }

    /**
     * 兑换
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/store/exchange", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String exchange(HttpServletRequest request, HttpServletRequest response) {
        JSONObject resultJson = new JSONObject();
        try {
            //webservice查询参数
            JSONObject paramJson = new JSONObject();
            String pCode = request.getParameter("pCode");
            String phoneNo = request.getParameter("phoneNo");
            paramJson.put("pCode", pCode);
            paramJson.put("phone", phoneNo);
           // log.info("store exchange req params:" + paramJson.toString());

            //webservice返回结果
            String resultStr = partnerPromotionCXFService.delUserExchange(paramJson.toString());
           // log.info("store exchange resp:" + resultStr);

            //返回页面
            return resultStr;

        } catch (Exception e) {
            log.error("store exchange arise exception:", e);
            //返回页面
            resultJson.put("resultCode", -1);
            resultJson.put("resultDesc", "操作失败，请稍后重试...");
            return resultJson.toJSONString();
        }
    }

    /**
     * 商户分店查询
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/partner/getStoreList", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getStoreList(HttpServletRequest request, HttpServletRequest response) {
        JSONObject resultJson = new JSONObject();
        try {
            //webservice查询参数
            JSONObject paramJson = new JSONObject();
            String pCode = request.getParameter("pCode");

            paramJson.put("pCode", pCode);
           // log.info("getStoreList req params:" + paramJson.toString());

            //webservice返回结果
            String resultStr = partnerPromotionCXFService.queryPartnerStoreList(paramJson.toString());
           // log.info("getStoreList resp:" + resultStr);

            //返回页面
            resultJson = JSON.parseObject(resultStr);

        } catch (Exception e) {
            log.error("getStoreList arise exception:", e);
            //返回页面
            resultJson.put("resultCode", -1);
            resultJson.put("resultDesc", "操作失败，请稍后重试...");
        }
        return resultJson.toJSONString();
    }

    /**
     * 商户总计查询
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/partner/getPartnerSumData", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getPartnerSumData(HttpServletRequest request, HttpServletRequest response) {
        JSONObject resultJson = new JSONObject();
        try {
            //webservice查询参数
            JSONObject paramJson = new JSONObject();
            String pCode = request.getParameter("pCode");

            paramJson.put("pCode", pCode);
          //  log.info("getPartnerSumData req parm:" + paramJson.toString());

            //webservice返回结果
            String resultStr = partnerPromotionCXFService.queryPartnerPromotionStatisticsInfo(paramJson.toString());
          //  log.info("getPartnerSumData resp :" + resultStr);
            resultJson = JSON.parseObject(resultStr);

        } catch (Exception e) {
            log.error("getPartnerSumData arise exception:", e);
            //返回页面
            resultJson.put("resultCode", -1);
            resultJson.put("resultDesc", "操作失败，请稍后重试...");
        }
        return resultJson.toJSONString();
    }

    /**
     * 商户详情查询
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/partner/getPartnerListData", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getPartnerListData(HttpServletRequest request, HttpServletRequest response) {
        JSONObject resultJson = new JSONObject();
        try {
            //webservice查询参数
            JSONObject paramJson = new JSONObject();
            String pCode = request.getParameter("pCode");
            String lowerlevelCode = request.getParameter("lowerlevelCode");
            String beginDate = request.getParameter("beginDate");
            String endDate = request.getParameter("endDate");
            String phoneNo = request.getParameter("phoneNo");
            String currentPage = request.getParameter("currentPage");
            String pageSize = request.getParameter("pageSize");

            log.info(String.format("getPartnerListData param[pCode=%s,lowerlevelCode=%s,beginDate=%s,endDate=%s,phoneNo=%s,currentPage=%s,pageSize=%s",
                    pCode, lowerlevelCode, beginDate, endDate, phoneNo, currentPage, pageSize));


            if (beginDate != null && !"".equals(beginDate)) {
                beginDate += " 00:00:00";
            }

            if (endDate != null && !"".equals(endDate)) {
                endDate += " 23:59:59";
            }


            List<String> lowerlevelCodes = new ArrayList<String>();
            String[] storeCodes = lowerlevelCode.split(",");
            for (int i = 0; i < storeCodes.length; i++) {
                String storeCode = storeCodes[i].trim();
                if (!StringUtils.isEmpty(storeCode)) {
                    lowerlevelCodes.add(storeCode);
                }
            }

            paramJson.put("pCode", pCode);
            paramJson.put("lowerlevelCode", lowerlevelCodes.toArray());
            paramJson.put("beginDate", beginDate);
            paramJson.put("endDate", endDate);
            paramJson.put("phone", phoneNo);
            paramJson.put("currentPage", currentPage);
            paramJson.put("pageSize", pageSize);
            log.info("getPartnerListData req params:" + paramJson.toString());

            //webservice返回结果
            String resultStr = partnerPromotionCXFService.queryPartnerStoreDetail(paramJson.toString());
            log.info("getPartnerListData resp:" + resultStr);

            //返回页面
            resultJson = JSON.parseObject(resultStr);

        } catch (Exception e) {
            log.error("getPartnerListData arise exception:", e);
            //返回页面
            resultJson.put("resultCode", -1);
            resultJson.put("resultDesc", "操作失败，请稍后重试...");
        }
        return resultJson.toJSONString();
    }

    @RequestMapping(value = "/checkActivity", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String checkActivity(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            String code = request.getParameter("code");
            JSONObject obj = new JSONObject();
            obj.put("activityCode", code);
            String activityType = request.getParameter("activityType");
            if(activityType != null && !"".equals(activityType)) {
                obj.put("activityType",activityType);
            }
           // log.info("ActivityController checkActivity ----> params:" + obj.toJSONString());
            String resp = activityCXFService.getActivityInfoByCode(obj.toJSONString());
           // log.info("ActivityController checkActivity ----> return:" + resp);
            WSModelResponse response = JsonUtil.jsonToBean(resp, WSModelResponse.class);
            if (response.getResultCode() == 0) {
                JSONObject data = JSONObject.parseObject(response.getData().toString());
                String starttime = data.getString("starttime");
                String endtime = data.getString("endtime");
                if (new Date().compareTo(DateUtil.parseDate(starttime, "yyyy-MM-dd HH:mm:ss")) >= 0 && new Date().compareTo(DateUtil.parseDate(endtime, "yyyy-MM-dd HH:mm:ss")) <= 0) {
                    result.put("resultCode", 0);
                    result.put("msg", "活动中");
                } else if (new Date().compareTo(DateUtil.parseDate(starttime, "yyyy-MM-dd HH:mm:ss")) < 0) {
                    result.put("resultCode", 1);
                    result.put("msg", "活动未开始");
                } else {
                    result.put("resultCode", 2);
                    result.put("msg", "活动已结束");
                }
            } else {
                result.put("resultCode", 3);
                result.put("msg", response.getDesc());
            }
        } catch (Exception e) {
            log.error(e);
            result.put("resultCode", 4);
            result.put("msg", "查询活动状态异常");
        }finally {
          //  log.info("ActivityController checkActivity ----> return to page:" + result.toJSONString());
            return TokenUtil.jsonpCallback(request, result.toJSONString());
        }
    }

    //根据活动编码查询活动详情&添加活动当前所处的状态  @author aiden at 2016-12-7 19:43:49
    @RequestMapping(value = "/getActivityInfoByCode", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getActivityInfoByCode(HttpServletRequest request) {
        JSONObject returnJson = new JSONObject();

        //参数必输校验
        String code = request.getParameter("code");
        if(StringUtils.isBlank(code)){
            returnJson.put("code", "-1");
            returnJson.put("info", "参数（活动编号：code）不能为空！");
            return TokenUtil.jsonpCallback(request, returnJson.toJSONString());
        }

        try {
            JSONObject obj = new JSONObject();
            obj.put("activityCode", code);
          //  log.info("ActivityController getActivityInfoByCode ----> params:" + obj.toJSONString());
            String resp = activityCXFService.getActivityInfoByCode(obj.toJSONString());
          //  log.info("ActivityController getActivityInfoByCode ----> return:" + resp);
            WSModelResponse response = JsonUtil.jsonToBean(resp, WSModelResponse.class);
            if (response.getResultCode() == 0 && response.getData() != null) {
                JSONObject jsonData = new JSONObject();
                //添加活动所处阶段
                JSONObject data = JSONObject.parseObject(response.getData().toString());
                String starttime = data.getString("starttime");
                String endtime = data.getString("endtime");
                if (new Date().compareTo(DateUtil.parseDate(starttime, "yyyy-MM-dd HH:mm:ss")) >= 0 && new Date().compareTo(DateUtil.parseDate(endtime, "yyyy-MM-dd HH:mm:ss")) <= 0) {
                    jsonData.put("period", 1);
                } else if (new Date().compareTo(DateUtil.parseDate(starttime, "yyyy-MM-dd HH:mm:ss")) < 0) {
                    jsonData.put("period", 2);
                } else {
                    jsonData.put("period", 3);
                }
                //添加活动详情属性
                jsonData.put("id", data.getIntValue("id"));
                jsonData.put("activitycode", data.getString("activitycode"));
                jsonData.put("activityname", data.getString("activityname"));
                jsonData.put("starttime", data.getString("starttime"));
                jsonData.put("endtime", data.getString("endtime"));
                jsonData.put("addtime", data.getString("addtime"));

                String onlinestarttime = data.getString("onlinestarttime");
                String onlineendtime =  data.getString("onlineendtime");
                if (new Date().compareTo(DateUtil.parseDate(onlinestarttime, "yyyy-MM-dd HH:mm:ss")) >= 0 && new Date().compareTo(DateUtil.parseDate(onlineendtime, "yyyy-MM-dd HH:mm:ss")) <= 0) {
                     jsonData.put("onlineStatus",1);
                }else if(new Date().compareTo(DateUtil.parseDate(onlineendtime, "yyyy-MM-dd HH:mm:ss")) < 0){
                     jsonData.put("onlineStatus",2);
                }else{
                     jsonData.put("onlineStatus",3);
                }

                returnJson.put("data", jsonData);
                returnJson.put("resultCode", 0);
                returnJson.put("msg", "操作成功！");
            } else {
                returnJson.put("resultCode", -2);
                returnJson.put("msg", response.getDesc());
            }
        } catch (Exception e) {
            log.error(e);
            returnJson.put("resultCode", -3);
            returnJson.put("msg", "查询活动详情异常");
        }
       // log.info("ActivityController checkActivity ----> return to page:" + returnJson.toJSONString());
        return TokenUtil.jsonpCallback(request, returnJson.toJSONString());
    }

    public static JSONObject checkLoginStatus(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            String uid = request.getParameter("uid");
            String sign = request.getParameter("sign");
            //app登录
            if (StringUtils.isNotBlank(sign)) {
                log.info("ActivityController checkLoginStatus ----> user is using native app...");
                if (StringUtils.isBlank(uid)) {
                    result.put("resultCode", 7);
                    result.put("msg", "uid为空");
                    log.info("ActivityController checkLoginStatus app ----> uid is null...");
                    return result;
                }

                JSONObject param = new JSONObject();
                param.put("userId", uid);
            //    log.info("ActivityController checkLoginStatus app ----> params:" + param.toJSONString());
                String resp = activityUserCXFService.getSignByUserId(param.toJSONString());
             //   log.info("ActivityController checkLoginStatus app ----> return:" + resp);
                WSModelResponse response = JsonUtil.jsonToBean(resp, WSModelResponse.class);
                if (response.getResultCode() != 0) {
                    result.put("resultCode", 2);
                    result.put("msg", "获取签名失败");
                    return result;
                }

                if (response.getData() == null || "".equals(response.getData())) {
                    result.put("resultCode", 4);
                    result.put("msg", "获取签名为空");
                    return result;
                }
                String tempSign = MD5Utils.MD5Encoder(uid + response.getData().toString(), "UTF-8");
                log.info("ActivityController checkLoginStatus app ----> tempSign=" + tempSign + ",sign=" + sign);
                if (tempSign.equalsIgnoreCase(sign)) {
                    result.put("resultCode", 0);
                    result.put("userId", uid);
                    result.put("msg", "签名合法");
                } else {
                    result.put("resultCode", 3);
                    result.put("msg", "签名非法");
                }

            }
            //webapp登录
            else {
                log.info("ActivityController checkLoginStatus ----> user is using webapp...");
                Object obj = request.getSession().getAttribute("loginUser");
                if (obj == null) {
                    result.put("resultCode", 5);
                    result.put("msg", "未登录");
                    log.info("ActivityController checkLoginStatus webapp----> loginUser is null...");
                } else {
                    User user = (User) obj;
                    result.put("resultCode", 0);
                    result.put("userId", user.getUserId());
                    result.put("msg", "已登录");
                }
            }

        } catch (Exception e) {
            log.error(e);
            result.put("resultCode", 1);
            result.put("msg", "获取登录状态异常");
        }
      //  log.info("ActivityController checkLoginStatus ----> return to method:" + result.toJSONString());
        return result;
    }

    @RequestMapping(value = "/genLoginSignInRedis", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String genLoginSignInRedis(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            JSONObject param = new JSONObject();
            String userId = request.getParameter("userId");
            param.put("userId", userId);
            log.info("ActivityController genLoginSignInRedis ----> params:" + param.toJSONString());
            String resp = activityUserCXFService.genLoginSignInRedis(param.toJSONString());
            log.info("ActivityController genLoginSignInRedis ----> return:" + resp);
            JSONObject resJson = JSON.parseObject(resp);

            result.put("resultCode", 0);
            result.put("resp", MD5Utils.MD5Encoder(userId + resJson.getString("data"), "UTF-8"));
            result.put("userId", userId);
            result.put("msg", "生成签名成功");
        } catch (Exception e) {
            log.error(e);
            result.put("resultCode", -1);
            result.put("msg", "获取登录状态异常");
        }
        log.info("ActivityController genLoginSignInRedis ----> return to method:" + result.toJSONString());
        return result.toJSONString();
    }


    /**
     * 用户参与热跑活动报名
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/rePaoJoinActivity", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String rePaoJoinActivity(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            // 验证是否登录状态
            Object userObj = request.getSession().getAttribute("loginUser");
            if (null == userObj) {
                result.put("resultCode", "-1");
                result.put("msg", "请先登录再参与");
                return result.toString();
            }
            User user = (User)userObj;
            JSONObject param = new JSONObject();
            param.put("userId", user.getUserId());
            param.put("activityCode", ActivityConstant.ACTIVITY_HOT_RUN);
            log.info("rePaoJoinActivity ->joinActivity req = " + param.toJSONString());
            String strwes = activityUserCXFService.joinActivity2(param.toJSONString());
            log.info("rePaoJoinActivity->joinActivity resp = " + strwes);
            JSONObject joinResult = JsonUtil.jsonToBean(strwes,JSONObject.class);
            if(joinResult.getIntValue("resultCode") == 0) {
                result.put("resultCode", "0");
                result.put("msg", "参与活动成功");
            } else {
                result.put("resultCode", joinResult.get("resultCode"));
                result.put("msg", joinResult.getString("resultDesc"));
            }
        } catch (Exception e) {
            log.error(e);
            result.put("resultCode", -1);
            result.put("msg", "参与活动异常，请重新尝试");
        }
        log.info("rePaoJoinActivity result = " + result.toJSONString());
        return result.toJSONString();
    }

    @RequestMapping(value = "/checkSinglesdayActivity",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String checkSinglesdayActivity(){
        JSONObject result = new JSONObject();
         try {
             Date begin = DateUtil.parseDate("2016-11-11 00:00:00",DateUtil.LongDateFormat);
             Date end = DateUtil.parseDate("2016-11-11 23:59:59",DateUtil.LongDateFormat);
             Date current = new Date();
             if(current.compareTo(begin) == 1 && current.compareTo(end) == -1) {
                 result.put("status",0);
             }  else {
                 result.put("status",1);
             }
             result.put("resultCode",0);
         } catch (Exception e) {
               log.error(e);
         } finally {
              return result.toJSONString();
         }
    }


    /**
     * 查询风险测评结果
     * @return
     */
    @RequestMapping(value = "/getRiskResult",produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String getRiskResult(HttpServletRequest request){
        JSONObject result = new JSONObject();
        try {
            // 验证是否登录状态
            Object userObj = request.getSession().getAttribute("loginUser");
            if (null == userObj) {
                result.put("resultCode", "-1");
                result.put("msg", "请先登录再参与");
                return result.toString();
            }
            User user = (User)userObj;
            //查询用户参与所有问卷的评测结果
            JSONObject paramsObj= new JSONObject();
            paramsObj.put("userId", user.getUserId());
            String resStr = questionCXFService.queryQuestionNaire(paramsObj.toJSONString());
            JSONObject resObj = JSONObject.parseObject(resStr);
            if(resObj == null || resObj.size() <= 0){
                return null;
            }
            WSResponse response = resObj.getObject("response", WSResponse.class);
            if(response == null || response.getResultCode() != AppConsts.WS_RETURN_SUCC){
                return null;
            }
            Map	resMap = resObj.getObject("resMap", Map.class);
            if(resMap ==null || resMap.size() <= 0){
                return null;
            }
            List<Map<String,Object>> testResultList = (List)resMap.get("userRestultShowInfos");
            if(testResultList == null || testResultList.size() <= 0){
                return null;
            }

            //获取风险评估问卷信息
            Map riskTermInfo = this.getRiskTermInfo();
            if(riskTermInfo == null){
                return null;
            }

            //获取用户风险测评问卷结果
            boolean resultStatus = false;//评测状态：默认false未评测
            Map<String,Object> resultInfo = null;//评测结果：总得分、风险类型、类型说明
            for (Map<String,Object> testResult : testResultList) {
                if(testResult != null && testResult.get("termId") != null && riskTermInfo.get("termId") != null
                        && riskTermInfo.get("termId").toString().equals(testResult.get("termId").toString())){
                    resultInfo = testResult;
                    resultStatus = true;
                    break;
                }
            }
            result.put("status",resultStatus);//评估状态：未评估flase、已评估true
            result.put("resultInfo",resultInfo == null ? "" : resultInfo);//评估结果
            result.put("resultCode", 0);
            result.put("activity_url",Configuration.getInstance().getValue("activity_url"));
        }catch (Exception e) {
            result.put("resultCode",400);
            log.error("getRiskResult error",e);
        }
        return result.toJSONString();
    }
    /***
     * 测试 答案提交
     */
    @RequestMapping(value = "/commitRiskExam", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String commitRiskExam(HttpServletRequest request){
        JSONObject result = new JSONObject();
        try {
            // 验证是否登录状态
            Object userObj = request.getSession().getAttribute("loginUser");
            if (null == userObj) {
                result.put("resultCode", "-1");
                result.put("msg", "请先登录再参与");
                return result.toString();
            }
            User user = (User)userObj;
            //查询风险评测问卷信息
            Map riskTermInfo = this.getRiskTermInfo();
            if(riskTermInfo == null){
                result.put("resultCode",405);
                return result.toJSONString();
            }

            String termId = riskTermInfo.get("termId") == null ? null : riskTermInfo.get("termId").toString();
            String ipStr = HttpUtil.getRealIpAddr(request);

            String totalScores = request.getParameter("totalScores");
            //封装问卷用户信息对象
            JSONObject questionUser = new JSONObject();
            questionUser.put("addIp",ipStr);
            questionUser.put("userId",user.getUserId());
            questionUser.put("totalScore",totalScores);
            questionUser.put("termId",Long.valueOf(termId));

            JSONObject inputObj=new JSONObject();
            inputObj.put("questionUser", questionUser);
            inputObj.put("terminal","mobile");//终端标识
            String resString=questionCXFService.saveUseQuestionAnswer(inputObj.toJSONString());
            WSResponse resWs = JsonUtil.jsonToBean(resString, WSResponse.class);
            if(resWs.getResultCode() == AppConsts.WS_RETURN_SUCC){
                result.put("resultCode", AppConsts.WS_RETURN_SUCC);
                result.put("msg", "提交成功");
            }else{
                result.put("resultCode", "-1");
                result.put("msg", "提交失败");
                result.put("data", "");
            }
        }catch (Exception e) {
            result.put("resultCode", 400);
            log.error("commitRiskExam error",e);
        }
        return result.toJSONString();
    }

    /**
     * 查询风险评测问卷信息
     * @return
     */
    private Map getRiskTermInfo(){
        JSONObject paramsObject = new JSONObject();
        paramsObject.put("termCode", "riskEva20160117");//风险评测问卷标识
        String termStr = questionCXFService.queryNaireByCode(paramsObject.toJSONString());
        WSModelResponse termWR = JsonUtil.jsonToBean(termStr, WSModelResponse.class);
        if(termWR == null || termWR.getResultCode() != AppConsts.WS_RETURN_SUCC || termWR.getData() == null){
            return null;
        }else{
            List<Map<String,Object>> termList = (List)termWR.getData();
            if(termList == null || termList.size() <= 0){
                return null;
            }else{
                return termList.get(0);
            }
        }
    }

}
