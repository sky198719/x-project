package com.xxdai.activity.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxdai.client.CXF_Factory;
import com.xxdai.client.ClientUtil;
import com.xxdai.core.util.http.HttpUtil;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.core.util.lang.DateUtil;
import com.xxdai.core.util.lang.StringUtil;
import com.xxdai.external.activity.ws.ActivityCXFService;
import com.xxdai.external.activityUser.ws.ActivityUserCXFService;
import com.xxdai.external.popularize.ws.PopularizeCFXService;
import com.xxdai.util.Configuration;
import com.xxdai.util.TokenUtil;
import com.xxdai.ws.util.WSModelResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Date;

/**
 * 圣诞节活动 [2016-12-23 00:00:00 - 2016-12-28 23:59:59 ]
 */
@Controller
@RequestMapping(value = "/honor5th")
public class Honor5Controller {
    private static final Logger logger = LoggerFactory.getLogger(Honor5Controller.class);

    private final ActivityUserCXFService activityUserCXFService = (ActivityUserCXFService) ClientUtil.getWebService(ActivityUserCXFService.class, "activityUser");
    protected PopularizeCFXService popularizeCFXService = (PopularizeCFXService) CXF_Factory.getFactory(PopularizeCFXService.class, Configuration.getInstance().getValue("webService_url") + "/popularizeCFXService").create();
    private final ActivityCXFService activityCXFService = (ActivityCXFService) ClientUtil.getWebService(ActivityCXFService.class, "activityWebService");


    /**
     * 领取登录奖品
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getLoginPrize", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getLoginPrize(HttpServletRequest request, HttpServletResponse response) {
        JSONObject returnJson = new JSONObject();

        try {
            //webapp登录判断 or app登录判断&sign校验
            returnJson = ActivityController.checkLoginStatus(request);
            if (returnJson.getIntValue("resultCode") != 0) {
                return TokenUtil.jsonpCallback(request, returnJson.toJSONString());
            }

            String userId = String.valueOf(returnJson.get("userId"));
            JSONObject param = new JSONObject();
            param.put("userId", userId);
            param.put("ip", HttpUtil.getRealIpAddr(request));
            logger.info("Honor5Controller getLoginPrize ----> params:" + param.toJSONString());
            String resultStr = activityUserCXFService.saveLoginPrizeRec4Honor5(param.toJSONString());
            logger.info("Honor5Controller getLoginPrize ----> return:" + resultStr);

            JSONObject result = JSON.parseObject(resultStr);
            returnJson.put("resultCode", String.valueOf(result.get("resultCode")));
            returnJson.put("msg", result.getString("desc"));
            if (result.get("data") != null) {
                returnJson.put("data", result.get("data"));
            }

        } catch (Exception e) {
            logger.error("Honor5Controller getLoginPrize ----> encounter exception:");
            e.printStackTrace();
            returnJson.clear();
            returnJson.put("resultCode", -3);
            returnJson.put("msg", "领取登录奖品发生异常");
        }

        logger.info("Honor5Controller getLoginPrize ----> return to page:" + returnJson.toJSONString());
        return TokenUtil.jsonpCallback(request, returnJson.toJSONString());
    }

    /**
     * 最后一天抽奖
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/lotteryPrize", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String lotteryPrize(HttpServletRequest request, HttpServletResponse response) {
        JSONObject returnJson = new JSONObject();

        try {
            //webapp登录判断 or app登录判断&sign校验
            returnJson = ActivityController.checkLoginStatus(request);
            if (returnJson.getIntValue("resultCode") != 0) {
                return TokenUtil.jsonpCallback(request, returnJson.toJSONString());
            }

            String userId = String.valueOf(returnJson.get("userId"));
            JSONObject param = new JSONObject();
            param.put("userId", userId);
            param.put("ip", HttpUtil.getRealIpAddr(request));
            logger.info("Honor5Controller lotteryPrize ----> params:" + param.toJSONString());
            String resultStr = activityUserCXFService.savePrizeRec4Honor5(param.toJSONString());
            logger.info("Honor5Controller lotteryPrize ----> return:" + resultStr);

            JSONObject result = JSON.parseObject(resultStr);
            returnJson.put("resultCode", String.valueOf(result.get("resultCode")));
            returnJson.put("msg", result.getString("desc"));
            if (result.get("data") != null) {
                returnJson.put("data", result.get("data"));
            }

        } catch (Exception e) {
            logger.error("Honor5Controller lotteryPrize ----> encounter exception:");
            e.printStackTrace();
            returnJson.clear();
            returnJson.put("resultCode", -3);
            returnJson.put("msg", "五周年活动最后一天抽奖发生异常");
        }

        logger.info("Honor5Controller lotteryPrize ----> return to page:" + returnJson.toJSONString());
        return TokenUtil.jsonpCallback(request, returnJson.toJSONString());
    }


    /**
     * 初始化信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/initInfo", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String initInfo(HttpServletRequest request, HttpServletResponse response) {
        JSONObject returnJson = new JSONObject();

        try {
            //webapp登录判断 or app登录判断&sign校验
            returnJson = ActivityController.checkLoginStatus(request);
            //加入活动状态
            JSONObject activityJson = this.getActivityStatus();
            returnJson.put("activityCode", activityJson.get("period"));
            if (returnJson.getIntValue("resultCode") != 0) {
                return TokenUtil.jsonpCallback(request, returnJson.toJSONString());
            }

            String userId = String.valueOf(returnJson.get("userId"));
            JSONObject param = new JSONObject();
            param.put("userId", userId);
            param.put("ip", HttpUtil.getRealIpAddr(request));
            logger.info("Honor5Controller initInfo ----> params:" + param.toJSONString());
            String resultStr = activityUserCXFService.queryInitInfo4Honor5Anniversary(param.toJSONString());
            logger.info("Honor5Controller initInfo ----> return:" + resultStr);

            JSONObject result = JSON.parseObject(resultStr);
            returnJson.put("resultCode", String.valueOf(result.get("resultCode")));
            returnJson.put("msg", result.getString("desc"));
            if (result.get("data") != null) {
                returnJson.put("data", result.get("data"));
            }

        } catch (Exception e) {
            logger.error("Honor5Controller initInfo ----> encounter exception:");
            e.printStackTrace();
            returnJson.clear();
            returnJson.put("resultCode", -3);
            returnJson.put("msg", "五周年活动最后一天抽奖发生异常");
        }

        logger.info("Honor5Controller initInfo ----> return to page:" + returnJson.toJSONString());
        return TokenUtil.jsonpCallback(request, returnJson.toJSONString());
    }

    /**
     * 我的中奖列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/myPrize", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String myPrize(HttpServletRequest request, HttpServletResponse response) {
        JSONObject returnJson = new JSONObject();

        try {
            //webapp登录判断 or app登录判断&sign校验
            returnJson = ActivityController.checkLoginStatus(request);
            if (returnJson.getIntValue("resultCode") != 0) {
                return TokenUtil.jsonpCallback(request, returnJson.toJSONString());
            }

            String userId = String.valueOf(returnJson.get("userId"));
            String currentPage = request.getParameter("currentPage");
            String pageSize = request.getParameter("pageSize");

            JSONObject param = new JSONObject();
            param.put("userId", userId);
            param.put("userId", userId);
            param.put("currentPage", StringUtil.isBlank(currentPage) ? 1 : currentPage);
            param.put("pageSize", StringUtil.isBlank(pageSize) ? 5 : pageSize);
            logger.info("Honor5Controller myPrize ----> params:" + param.toJSONString());
            String resultStr = activityUserCXFService.queryUserPrizeRecsList4Honor5(param.toJSONString());
            logger.info("Honor5Controller myPrize ----> return:" + resultStr);
            JSONObject resultJson = JSONObject.parseObject(resultStr);
            if (resultJson != null && resultJson.getIntValue("resultCode") == 0) {
                JSONArray resultList = resultJson.getJSONArray("data");
                JSONArray prizeList = new JSONArray();
                if (resultList != null && resultList.size() > 0) {
                    for (int i = 0; i < resultList.size(); i++) {
                        JSONObject obj = resultList.getJSONObject(i);
                        JSONObject temp = new JSONObject();
                        if (obj.getDate("addTime") != null) {
                            String gameTime = DateUtil.format(obj.getDate("addTime"), DateUtil.LongDateFormat);
                            temp.put("gameTime", gameTime);
                        } else {
                            temp.put("gameTime", "");
                        }
                        temp.put("prizeName", obj.getString("prizeName"));
                        prizeList.add(temp);
                    }
                    returnJson.put("resultCode", resultJson.getIntValue("resultCode"));
                } else {
                    returnJson.put("resultCode", 1);
                }
                JSONObject dataJson = new JSONObject();
                dataJson.put("totalPage", resultJson.getString("totalPage"));
                dataJson.put("prizeList", prizeList);
                returnJson.put("msg", "操作成功！");
                returnJson.put("data", dataJson);
            } else {
                returnJson.put("resultCode", resultJson.getIntValue("resultCode"));
                returnJson.put("msg", resultJson.getIntValue("desc"));
            }
        } catch (Exception e) {
            logger.error("Honor5Controller myPrize ----> arise exception:");
            e.printStackTrace();
            returnJson.clear();
            returnJson.put("resultCode", -3);
            returnJson.put("msg", "我的奖品列表展示失败");
        }

        logger.info("Honor5Controller myPrize ----> return to page:" + returnJson.toJSONString());
        return TokenUtil.jsonpCallback(request, returnJson.toJSONString());
    }

    private JSONObject getActivityStatus() throws ParseException {
        JSONObject returnJson = new JSONObject();

        JSONObject obj = new JSONObject();
        obj.put("activityCode", "honor-5-aniversary");
        logger.info("Honor5Controller getActivityStatus ----> params:" + obj.toJSONString());
        String resp = activityCXFService.getActivityInfoByCode(obj.toJSONString());
        logger.info("Honor5Controller getActivityStatus ----> return:" + resp);
        WSModelResponse response = JsonUtil.jsonToBean(resp, WSModelResponse.class);
        if (response.getResultCode() == 0 && response.getData() != null) {
            //添加活动所处阶段
            JSONObject data = JSONObject.parseObject(response.getData().toString());
            String starttime = data.getString("starttime");
            String endtime = data.getString("endtime");
            if (new Date().compareTo(DateUtil.parseDate(starttime, "yyyy-MM-dd HH:mm:ss")) >= 0 && new Date().compareTo(DateUtil.parseDate(endtime, "yyyy-MM-dd HH:mm:ss")) <= 0) {
                returnJson.put("period", 0);
            } else if (new Date().compareTo(DateUtil.parseDate(starttime, "yyyy-MM-dd HH:mm:ss")) < 0) {
                returnJson.put("period", 1);
            } else {
                returnJson.put("period", 2);
            }
            //添加活动详情属性
            returnJson.put("id", data.getIntValue("id"));
            returnJson.put("activitycode", data.getString("activitycode"));
            returnJson.put("activityname", data.getString("activityname"));
            returnJson.put("starttime", data.getString("starttime"));
            returnJson.put("endtime", data.getString("endtime"));
            returnJson.put("addtime", data.getString("addtime"));
        }else{}
        return returnJson;
    }
}
