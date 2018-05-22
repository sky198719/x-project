package com.xxdai.activity.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxdai.client.CXF_Factory;
import com.xxdai.client.ClientUtil;
import com.xxdai.core.util.http.HttpUtil;
import com.xxdai.core.util.lang.DateUtil;
import com.xxdai.core.util.lang.StringUtil;
import com.xxdai.external.activityUser.ws.ActivityUserCXFService;
import com.xxdai.external.popularize.ws.PopularizeCFXService;
import com.xxdai.util.Configuration;
import com.xxdai.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;

/**
 * 圣诞节活动 [2016-12-23 00:00:00 - 2016-12-28 23:59:59 ]
 */
@Controller
@RequestMapping(value = "/christmas")
public class ChristmasController {
    private static final Logger logger = LoggerFactory.getLogger(ChristmasController.class);

    private final ActivityUserCXFService activityUserCXFService = (ActivityUserCXFService) ClientUtil.getWebService(ActivityUserCXFService.class, "activityUser");
    protected PopularizeCFXService popularizeCFXService=(PopularizeCFXService) CXF_Factory.getFactory(PopularizeCFXService.class, Configuration.getInstance().getValue("webService_url") + "/popularizeCFXService").create();

    /**
     * 预生成摇树抽金币记录
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/shake", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String shake(HttpServletRequest request, HttpServletResponse response) {
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
            logger.info("ChristmasController shake ----> params:" + param.toJSONString());
            String resultStr = activityUserCXFService.saveShackAdvancePrizeRec4Christmas(param.toJSONString());
            logger.info("ChristmasController shake ----> return:" + resultStr);

            JSONObject result = JSON.parseObject(resultStr);
            if (result.getInteger("resultCode") >= 0 ){
                returnJson.put("resultCode",String.valueOf(result.get("resultCode")));
                returnJson.put("msg", result.getString("desc"));
                if(result.get("data") != null){
                    returnJson.put("data", result.get("data"));
                }
            } else {
                returnJson.put("resultCode",-2);
                returnJson.put("msg",result.getString("desc"));
            }
        } catch (Exception e) {
            logger.error("ChristmasController shake ----> arise exception:");
            e.printStackTrace();
            returnJson.clear();
            returnJson.put("resultCode",-3);
            returnJson.put("msg","预生成摇树抽金币记录异常");
        }

        logger.info("ChristmasController shake ----> return to page:" + returnJson.toJSONString());
        return TokenUtil.jsonpCallback(request, returnJson.toJSONString());
    }

    /**
     * 预生成摇树抽金币记录确认
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/shakeConfirm", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String shakeConfirm(HttpServletRequest request, HttpServletResponse response) {
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
            logger.info("ChristmasController shakeConfirm ----> params:" + param.toJSONString());
            String resultStr = activityUserCXFService.saveConfirmShakePrizeRec4Christmas(param.toJSONString());
            logger.info("ChristmasController shakeConfirm ----> return:" + resultStr);

            if (StringUtil.isNotBlank(resultStr)) {
            	JSONObject result = JSON.parseObject(resultStr);
            	int resultCode = result.getIntValue("resultCode");
            	if (resultCode == 0 && result.get("data") != null) {
                    returnJson.put("data", result.get("data"));
            		returnJson.put("resultCode", 0);
            		returnJson.put("msg", "操作成功！");
            		logger.info("ChristmasController shakeConfirm ----> return to page:" + resultStr);
            		return TokenUtil.jsonpCallback(request,returnJson.toJSONString());
            	}else{
                    returnJson.put("resultCode", -2);
                    returnJson.put("msg", result.getString("desc"));
                    logger.info("ChristmasController shakeConfirm ----> return to page:" + resultStr);
                    return TokenUtil.jsonpCallback(request,returnJson.toJSONString());
                }
            }else{
            	returnJson.clear();
                returnJson.put("resultCode", -3);
                returnJson.put("msg", "调用webservice返回接口为空！");
            }
        	
        } catch (Exception e) {
            logger.error("ChristmasController shakeConfirm ----> arise exception:");
            e.printStackTrace();
            returnJson.clear();
            returnJson.put("resultCode", -4);
            returnJson.put("msg", "确认预生成摇树抽金币记录异常");
        }

        logger.info("ChristmasController shakeConfirm ----> return to page:" + returnJson.toJSONString());
        return TokenUtil.jsonpCallback(request, returnJson.toJSONString());
    }

    /**
     * 预生成翻倍抽奖记录
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/multiplier", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String multiplier(HttpServletRequest request, HttpServletResponse response) {
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
            logger.info("ChristmasController multiplier ----> params:" + param.toJSONString());
            String resultStr = activityUserCXFService.saveMultiplierAdvancePrizeRec4Christmas(param.toJSONString());
            logger.info("ChristmasController multiplier ----> return:" + resultStr);

            JSONObject result = JSON.parseObject(resultStr);
            if (result.getInteger("resultCode") >= 0){
                returnJson.put("resultCode", String.valueOf(result.get("resultCode")));
                returnJson.put("msg", result.getString("desc"));
                if(result.get("data") != null){
                    returnJson.put("data", result.get("data"));
                }
            } else {
                returnJson.put("resultCode",-2);
                returnJson.put("msg",result.getString("desc"));
            }
        } catch (Exception e) {
            logger.error("ChristmasController multiplier ----> arise exception:");
            e.printStackTrace();
            returnJson.clear();
            returnJson.put("resultCode",-3);
            returnJson.put("msg","预生成翻倍记录异常");
        }

        logger.info("ChristmasController multiplier ----> return to page:" + returnJson.toJSONString());
        return TokenUtil.jsonpCallback(request, returnJson.toJSONString());
    }

    /**
     * 预生成翻倍记录确认
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/multiplierConfirm", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String multiplierConfirm(HttpServletRequest request, HttpServletResponse response) {
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
            logger.info("ChristmasController multiplierConfirm ----> params:" + param.toJSONString());
            String resultStr = activityUserCXFService.saveConfirmMultiplierPrizeRec4Christmas(param.toJSONString());
            logger.info("ChristmasController multiplierConfirm ----> return:" + resultStr);

            if (StringUtil.isNotBlank(resultStr)) {
                JSONObject result = JSON.parseObject(resultStr);
                int resultCode = result.getIntValue("resultCode");
                if (resultCode == 0 && result.get("data") != null) {
                    returnJson.put("data", result.get("data"));
                    returnJson.put("resultCode", 0);
                    returnJson.put("msg", "操作成功！");
                    logger.info("ChristmasController multiplierConfirm ----> return to page:" + resultStr);
                    return TokenUtil.jsonpCallback(request,returnJson.toJSONString());
                }else{
                    returnJson.put("resultCode", -2);
                    returnJson.put("msg", result.getString("desc"));
                    logger.info("ChristmasController multiplierConfirm ----> return to page:" + resultStr);
                    return TokenUtil.jsonpCallback(request,returnJson.toJSONString());
                }
            }else{
                returnJson.clear();
                returnJson.put("resultCode", -3);
                returnJson.put("msg", "调用webservice返回接口为空！");
            }

        } catch (Exception e) {
            logger.error("ChristmasController multiplierConfirm ----> arise exception:");
            e.printStackTrace();
            returnJson.clear();
            returnJson.put("resultCode", -4);
            returnJson.put("msg", "确认预生成翻倍记录异常");
        }

        logger.info("ChristmasController multiplierConfirm ----> return to page:" + returnJson.toJSONString());
        return TokenUtil.jsonpCallback(request, returnJson.toJSONString());
    }

    /**
     * 初始化信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/initialize", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String initialize(HttpServletRequest request, HttpServletResponse response) {
        JSONObject returnJson = new JSONObject();
        String activityCode = request.getParameter("activityCode");
        if (StringUtil.isBlank(activityCode)) {
            returnJson.put("resultCode", -1);
            returnJson.put("msg", "活动参数不能为空");
            return TokenUtil.jsonpCallback(request, returnJson.toJSONString());
        }

        try {
            //webapp登录判断 or app登录判断&sign校验
            returnJson = ActivityController.checkLoginStatus(request);
            if (returnJson.getIntValue("resultCode") != 0) {
                return TokenUtil.jsonpCallback(request, returnJson.toJSONString());
            }

            //查询初始化数据
            String userId = String.valueOf(returnJson.get("userId"));
            JSONObject param = new JSONObject();
            param.put("userId", userId);
            logger.info("ChristmasController initialize ----> params:" + param.toJSONString());
            String resultStr = activityUserCXFService.queryChristmasInfo(param.toJSONString());
            logger.info("ChristmasController initialize ----> return:" + resultStr);
            returnJson = JSONObject.parseObject(resultStr);

            JSONObject dataJson =  returnJson.getJSONObject("data");
            //查询用户推广码
            JSONObject paramsObj = new JSONObject();
            paramsObj.put("userId", userId);
            paramsObj.put("activityCode", activityCode);
            String inviteStr = popularizeCFXService.queryUserInviteCode(paramsObj.toJSONString());
            JSONObject inviteObj = JSONObject.parseObject(inviteStr);
            if (null != inviteObj && inviteObj.getIntValue("resultCode") >= 0) {
                JSONObject data = inviteObj.getJSONObject("resultData");
                if(dataJson == null) {
                    dataJson = new JSONObject();
                }
                dataJson.put("nickName", data.getString("NICKNAME"));
                dataJson.put("invitation", data.getString("UUID"));
            }

        } catch (Exception e) {
            logger.error("ChristmasController initialize ----> arise exception:",e);
            returnJson.clear();
            returnJson.put("resultCode",-3);
            returnJson.put("msg","信息初始化失败");
        }

        logger.info("ChristmasController initialize ----> return to page:" + returnJson.toJSONString());
        return TokenUtil.jsonpCallback(request, returnJson.toJSONString());
    }

    /**
     * 翻倍中奖列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/showPrizeRecs", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String showPrizeRecs(HttpServletRequest request, HttpServletResponse response) {
        JSONObject returnJson = new JSONObject();

        try {
            JSONObject param = new JSONObject();
            param.put("recordNum", 15);//查询用户中奖记录条数
            logger.info("ChristmasController showPrizeRecs ----> params:" + param.toJSONString());
            String resultStr = activityUserCXFService.queryChristmasPrizeRecs(param.toJSONString());
            logger.info("ChristmasController showPrizeRecs ----> return:" + resultStr);
            returnJson = JSONObject.parseObject(resultStr);
        } catch (Exception e) {
            logger.error("ChristmasController showPrizeRecs ----> arise exception:");
            returnJson.clear();
            returnJson.put("resultCode",-3);
            returnJson.put("msg","活动中奖列表展示失败");
        }

        logger.info("ChristmasController showPrizeRecs ----> return to page:" + returnJson.toJSONString());
        return TokenUtil.jsonpCallback(request, returnJson.toJSONString());
    }

    /**
     * 投资排名
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/investTop3", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String investTop3(HttpServletRequest request, HttpServletResponse response) {
        JSONObject returnJson = new JSONObject();

        try {
            JSONObject param = new JSONObject();
            param.put("topNum", 3);//查询投资排名列表条数
            logger.info("ChristmasController investTop3 ----> params:" + param.toJSONString());
            String resultStr = activityUserCXFService.queryTopByInvest(param.toJSONString());
            logger.info("ChristmasController investTop3 ----> return:" + resultStr);
            returnJson = JSONObject.parseObject(resultStr);
        } catch (Exception e) {
            logger.error("ChristmasController investTop3 ----> arise exception:");
            returnJson.clear();
            returnJson.put("resultCode",-3);
            returnJson.put("msg","投资排名列表展示失败");
        }

        logger.info("ChristmasController investTop3 ----> return to page:" + returnJson.toJSONString());
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
            param.put("currentPage", StringUtil.isBlank(currentPage) ? 1 : currentPage);
            param.put("pageSize", StringUtil.isBlank(pageSize) ? 7 : pageSize);
            logger.info("ChristmasController myPrize ----> params:" + param.toJSONString());
            String resultStr = activityUserCXFService.queryUserPrizeRecsList(param.toJSONString());
            logger.info("ChristmasController myPrize ----> return:" + resultStr);
            returnJson = JSONObject.parseObject(resultStr);
            if(returnJson != null && returnJson.getIntValue("resultCode") == 0){
                JSONArray resultList = returnJson.getJSONArray("data");
                JSONArray dataArr = new JSONArray();
                if (resultList != null && resultList.size() > 0) {
                    for (int i = 0; i < resultList.size(); i++) {
                        JSONObject obj = resultList.getJSONObject(i);
                        JSONObject temp = new JSONObject();
                        if(obj.getDate("addTime") != null){
                            String gameTime = DateUtil.format(obj.getDate("addTime"),DateUtil.LongDateFormat);
                            gameTime = gameTime.substring(5,gameTime.length());
                            temp.put("gameTime", gameTime);
                        }else{
                            temp.put("gameTime", "");
                        }
                        String gold = obj.getString("remark");
                        String rate = obj.getString("prizeName");
                        if(obj.getInteger("chanceType") != null && obj.getInteger("chanceType") == 2){
                            //翻倍机会2
                            temp.put("rewardInfo", gold.concat("个爱心").concat("  ").concat("翻").concat(rate));
                        }else{
                            //摇树机会1
                            temp.put("rewardInfo", "获得爱心".concat(gold).concat("个"));
                        }
                        dataArr.add(temp);
                    }
                    returnJson.put("resultCode", returnJson.getIntValue("resultCode"));
                }else{
                    //未查到金币记录
                    returnJson.put("resultCode", 1);
                }
                returnJson.put("totalPage", returnJson.getString("totalPage"));
                returnJson.put("data", dataArr);
            }
        } catch (Exception e) {
            logger.error("ChristmasController myPrize ----> arise exception:");
            e.printStackTrace();
            returnJson.clear();
            returnJson.put("resultCode",-3);
            returnJson.put("msg","我的奖品列表展示失败");
        }

        logger.info("ChristmasController myPrize ----> return to page:" + returnJson.toJSONString());
        return TokenUtil.jsonpCallback(request, returnJson.toJSONString());
    }
}
