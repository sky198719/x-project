package com.xxdai.activity.controller;

import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xxdai.util.TokenUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxdai.client.ClientUtil;
import com.xxdai.core.util.http.HttpUtil;
import com.xxdai.core.util.lang.StringUtil;
import com.xxdai.external.activityUser.ws.ActivityUserCXFService;

/**
 * 2016年10月1日：国庆活动
 */
@Controller
@RequestMapping(value = "/nationalDay")
public class NationalDayController {
    private static final Logger log = Logger.getLogger(NationalDayController.class);

    private final ActivityUserCXFService activityUserCXFService = (ActivityUserCXFService) ClientUtil.getWebService(ActivityUserCXFService.class, "activityUser");

    /**
     * 用户活动信息（抽奖次数，点亮地区信息）
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getUserGameInfo", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getCanPlayTimes(HttpServletRequest request, HttpServletResponse response) {
        JSONObject returnJson = new JSONObject();
        try {
        	returnJson = ActivityController.checkLoginStatus(request);
        	if (returnJson.getIntValue("resultCode") != 0) {
        		return TokenUtil.jsonpCallback(request, returnJson.toJSONString());
			}
            String userId = String.valueOf(returnJson.get("userId"));
	        JSONObject param = new JSONObject();
	        param.put("userId",userId);
	        //游戏次数
	        log.info("NationalDayController getUserGameInfo playTimes ----> params:" + param.toJSONString());
	        String resultStr = activityUserCXFService.queryLotteryChanceInfo4NationalDay(param.toJSONString());
	        log.info("NationalDayController getUserGameInfo playTimes ----> return:" + resultStr);
            JSONObject result = JSON.parseObject(resultStr);
            JSONObject temp = new JSONObject();
            if ("0".equals(String.valueOf(result.get("resultCode")))) {
                temp.put("playtime", String.valueOf(result.get("data")));
            } else {
            	returnJson.clear();
                returnJson.put("resultCode",10);
                returnJson.put("msg","查询失败");
                return TokenUtil.jsonpCallback(request, returnJson.toJSONString());
            }
            
            //用户点亮地区状态
            log.info("NationalDayController getUserGameInfo gameInfo ----> params:" + param.toJSONString());
            String resultStr1 = activityUserCXFService.queryNationDayCompletedInfo4User(param.toJSONString());
            log.info("NationalDayController getUserGameInfo gameInfo ----> return:" + resultStr1);
            JSONObject result1 = JSON.parseObject(resultStr1);
            String info = "";
            if ("0".equals(String.valueOf(result1.get("resultCode")))) {
            	JSONObject result1_data = result1.getJSONObject("data");
            	SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
            	info = String.format("%s第%s名点亮<br><span style='font-size:0.6rem;color:yellow;'>(每隔一小时更新)</span>", sdf.format(result1_data.getDate("LIGNTENTIME")),String.valueOf(result1_data.get("LIGHTENRANK")));
            }else if ("-2".equals(String.valueOf(result1.get("resultCode")))) {
            	info = "还未全部点亮";
            }else {
            	returnJson.clear();
            	returnJson.put("resultCode",10);
            	returnJson.put("msg","查询失败");
            	return TokenUtil.jsonpCallback(request, returnJson.toJSONString());
            }
            temp.put("info", info);
            JSONArray dataArr = new JSONArray();
            dataArr.add(temp);
            returnJson.put("data",dataArr);
            
            //获取用户点亮的地区列表
            log.info("NationalDayController getUserGameInfo lightenArea ----> params:" + param.toJSONString());
	        String resultStr2 = activityUserCXFService.queryNationDayList4User(param.toJSONString());
	        log.info("NationalDayController getUserGameInfo lightenArea ----> return:" + resultStr2);
            JSONObject result2 = JSON.parseObject(resultStr2);
            JSONArray areaArray = new JSONArray();
            if ("0".equals(String.valueOf(result2.get("resultCode")))) {
            	JSONArray ja = result2.getJSONArray("data");
            	if (ja != null && ja.size() > 0) {
            		for (int i = 0; i < ja.size(); i++) {
            			JSONObject jo = ja.getJSONObject(i);
            			String region = String.valueOf(jo.get("regionCode"));
            			areaArray.add(region);
            		}
				}
            }
            returnJson.put("area", areaArray);
            
            returnJson.put("resultCode",0);
            returnJson.put("msg","查询成功");

        } catch (Exception e) {
            log.error("NationalDayController getUserGameInfo ----> arise exception:", e);
            returnJson.clear();
            returnJson.put("resultCode",11);
            returnJson.put("msg","查询异常");
        }

        log.info("NationalDayController getUserGameInfo ----> return to page:" + returnJson.toJSONString());
        return TokenUtil.jsonpCallback(request, returnJson.toJSONString());
    }

    /**
     * 预生成抽奖记录
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/playStart", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String playStart(HttpServletRequest request, HttpServletResponse response) {
        JSONObject returnJson = new JSONObject();
        try {
        	returnJson = ActivityController.checkLoginStatus(request);
        	if (returnJson.getIntValue("resultCode") != 0) {
        		return TokenUtil.jsonpCallback(request, returnJson.toJSONString());
			}
        	String userId = String.valueOf(returnJson.get("userId"));
            JSONObject param = new JSONObject();
            param.put("userId", userId);
            param.put("ip", HttpUtil.getRealIpAddr(request));
            log.info("NationalDayController playStart ----> params:" + param.toJSONString());
            String resultStr = activityUserCXFService.saveAdvancePrizeRec4NationalDay(param.toJSONString());
            log.info("NationalDayController playStart ----> return:" + resultStr);
            JSONObject result = JSON.parseObject(resultStr);
            if ("0".equals(String.valueOf(result.get("resultCode")))) {
                JSONObject dataJson = result.getJSONObject("data");
                JSONObject returnItem = new JSONObject();
                returnItem.put("itemId", String.valueOf(dataJson.get("prizeId")));
                returnItem.put("itemName", dataJson.get("prizeName"));
                JSONArray dataArr = new JSONArray();
                dataArr.add(returnItem);
                returnJson.put("data",dataArr);
                returnJson.put("resultCode",0);
                returnJson.put("msg","成功");
            } else {
                returnJson.put("resultCode",10);
                returnJson.put("msg",result.getString("desc"));
            }
        } catch (Exception e) {
            log.error("NationalDayController playStart ----> arise exception:", e);
            returnJson.clear();
            returnJson.put("resultCode",11);
            returnJson.put("msg","预生成抽奖记录异常");
        }

        log.info("NationalDayController playStart ----> return to page:" + returnJson.toJSONString());
        return TokenUtil.jsonpCallback(request, returnJson.toJSONString());
    }

    /**
     * 预生成抽奖记录确认
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/playEnd", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String playEnd(HttpServletRequest request, HttpServletResponse response) {
        JSONObject returnJson = new JSONObject();
        try {
        	returnJson = ActivityController.checkLoginStatus(request);
        	if (returnJson.getIntValue("resultCode") != 0) {
        		return TokenUtil.jsonpCallback(request, returnJson.toJSONString());
			}
        	String userId = String.valueOf(returnJson.get("userId"));
            JSONObject param = new JSONObject();
            param.put("userId", userId);
            log.info("NationalDayController playEnd ----> params:" + param.toJSONString());
            String resultStr = activityUserCXFService.saveConfirmPrizeRec4NationalDay(param.toJSONString());
            log.info("NationalDayController playEnd ----> return:" + resultStr);

            if (StringUtil.isNotBlank(resultStr)) {
            	JSONObject result = JSON.parseObject(resultStr);
            	int resultCode = result.getIntValue("resultCode");
            	if (resultCode != 0) {
            		returnJson.put("resultCode", 9);
            		returnJson.put("msg", result.getString("desc"));
            		log.info("NationalDayController playEnd ----> return to page:" + resultStr);
            		return TokenUtil.jsonpCallback(request,returnJson.toJSONString());
            	}
            	
            	log.info("NationalDayController playEnd ----> go to getUserGameInfo...");
            	RequestDispatcher dispatcher = request.getRequestDispatcher("/nationalDay/getUserGameInfo.do");
            	dispatcher.forward(request, response);
            	return null;
            	
            }else{
            	returnJson.clear();
                returnJson.put("resultCode", 10);
                returnJson.put("msg", "抽奖失败");
            }
        	
        } catch (Exception e) {
            log.error("NationalDayController playEnd ----> arise exception:", e);
            returnJson.clear();
            returnJson.put("resultCode", 10);
            returnJson.put("msg", "抽奖失败");
        }

        log.info("NationalDayController playEnd ----> return to page:" + returnJson.toJSONString());
        return TokenUtil.jsonpCallback(request, returnJson.toJSONString());
    }

    /**
     * 幸运奖
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/luckyList", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String luckyList(HttpServletRequest request, HttpServletResponse response) {
        JSONArray returnArray = new JSONArray();
        JSONObject returnItem = null;
        try {
            String resultStr = activityUserCXFService.queryLast15PrizeRecsAll4NationalDay();
            log.info("NationalDayController luckyList ----> return:" + resultStr);
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
            if (StringUtil.isNotBlank(resultStr)) {
                JSONObject returnJson = JSON.parseObject(resultStr);
                if ("0".equals(String.valueOf(returnJson.get("resultCode")))) {
                    JSONArray jaData = returnJson.getJSONArray("data");
                    if (jaData != null && jaData.size() > 0) {
                        for (int i = 0; i < jaData.size(); i++) {
                            JSONObject jo = jaData.getJSONObject(i);
                            returnItem = new JSONObject();
                            returnItem.put("user", jo.get("nickName"));
                            returnItem.put("time", sdf.format(jo.getDate("addTime")));
                            returnItem.put("reward", String.valueOf(jo.get("prizeName")));
                            returnArray.add(returnItem);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("NationalDayController luckyList ----> arise exception:", e);
            returnArray = new JSONArray();
        }
        log.info("NationalDayController luckyList ----> return to page:" + returnArray.toJSONString());

        return TokenUtil.jsonpCallback(request, returnArray.toJSONString());
    }

    /**
     * 土豪奖
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/tuhaoList", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String tuhaoList(HttpServletRequest request, HttpServletResponse response) {
        JSONArray returnArray = new JSONArray();
        JSONObject returnItem = null;
        try {
            String resultStr = activityUserCXFService.queryLast15PrizeRecs4NationalDay();
            log.info("NationalDayController tuhaoList ----> return:" + resultStr);
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
            if (StringUtil.isNotBlank(resultStr)) {
                JSONObject returnJson = JSON.parseObject(resultStr);
                if ("0".equals(String.valueOf(returnJson.get("resultCode")))) {
                    JSONArray jaData = returnJson.getJSONArray("data");
                    if (jaData != null && jaData.size() > 0) {
                        for (int i = 0; i < jaData.size(); i++) {
                            JSONObject jo = jaData.getJSONObject(i);
                            returnItem = new JSONObject();
                            returnItem.put("user", jo.get("nickName"));
                            returnItem.put("time", sdf.format(jo.getDate("addTime")));
                            returnItem.put("reward", String.valueOf(jo.get("prizeName")));
                            returnArray.add(returnItem);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("NationalDayController tuhaoList ----> arise exception:", e);
            returnArray = new JSONArray();
        }
        log.info("NationalDayController tuhaoList ----> return to page:" + returnArray.toJSONString());
        return TokenUtil.jsonpCallback(request, returnArray.toJSONString());
    }
    
    /**
     * 获取当日点亮全部地区的人数
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/totalCountToday", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String totalCountToday(HttpServletRequest request, HttpServletResponse response) {
    	JSONArray returnArray = new JSONArray();
    	JSONObject returnItem = null;
    	try {
    		String resultStr = activityUserCXFService.getNationDayCompletedCountByToday();
    		log.info("NationalDayController totalCountToday ----> return:" + resultStr);
    		if (StringUtil.isNotBlank(resultStr)) {
    			JSONObject returnJson = JSON.parseObject(resultStr);
    			returnItem = new JSONObject();
    			String totalCountToday = "0";
    			if ("0".equals(String.valueOf(returnJson.get("resultCode")))) {
    				totalCountToday = String.valueOf(returnJson.get("data"));
    			}
    			returnItem.put("totalCountToday", totalCountToday);
    			returnArray.add(returnItem);
    		}
    	} catch (Exception e) {
    		log.error("NationalDayController totalCountToday ----> arise exception:", e);
    	}
    	log.info("NationalDayController totalCountToday ----> return to page:" + returnArray.toJSONString());
    	return TokenUtil.jsonpCallback(request, returnArray.toJSONString());
    }
}
