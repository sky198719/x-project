package com.xxdai.activity.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xxdai.client.CXF_Factory;
import com.xxdai.common.DataResponse;
import com.xxdai.constant.AppConsts;
import com.xxdai.constant.Constant;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.exception.ServiceException;
import com.xxdai.external.activity.ws.ActivityCXFService;
import com.xxdai.external.user.ws.UserCXFService;
import com.xxdai.util.Configuration;
import com.xxdai.util.CookieUtil;
import com.xxdai.util.MD5Utils;
import com.xxdai.util.TokenUtil;
import com.xxdai.ws.util.WSModelResponse;
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
import com.xxdai.external.user.ws.User;

/**
 * 2016年9月22日：会员日活动
 */
@Controller
@RequestMapping(value = "/memberDay922")
public class MemberDay922Controller {
    private static final Logger log = Logger.getLogger(MemberDay922Controller.class);

    private final ActivityUserCXFService activityUserCXFService = (ActivityUserCXFService) ClientUtil.getWebService(ActivityUserCXFService.class, "activityUser");

    protected UserCXFService userCXFService = (UserCXFService) CXF_Factory.getFactory(UserCXFService.class, Configuration.getInstance().getValue("webService_url") + "/userWebService").create();

    /**
     * 抽奖次数
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getCanPlayTimes", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getCanPlayTimes(HttpServletRequest request, HttpServletRequest response) {
        JSONObject resultJson = new JSONObject();
        try {
            String uid = request.getParameter("uid");
            String sign = request.getParameter("sign");
            String userId = uid;
            if (sign != null && !"".equals(sign)) {
                log.info("playEnd checkSign uid = " + uid + ",sign=" + sign);
                JSONObject result = checkSign(uid, sign);
                log.info("playEnd checkSing resp = " + result.toJSONString());
                int code = result.getIntValue("resultCode");
                if (code != 0) {
                    resultJson = result;
                    log.info("playEnd checkSing result = " + resultJson.toJSONString());
                    return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
                }

                if (uid == null || "".equals(uid)) {
                    resultJson.put("resultCode", 7);
                    resultJson.put("msg", "uid未空");
                    log.info("playEnd uid is null");
                    return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
                }
            } else {
                Object obj = request.getSession().getAttribute("loginUser");
                if (obj == null) {
                    resultJson.put("resultCode", 1);
                    resultJson.put("msg", "未登录");
                    log.info("playEnd loginUser is null ");
                    return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
                }
                User user = (User) obj;
                userId = String.valueOf(user.getUserId());
            }

            JSONObject param = new JSONObject();
            param.put("userId", userId);

            log.info("MemberDay922Controller getCanPlayTimes ----> params:" + param.toJSONString());
            String resultStr = activityUserCXFService.queryLotteryChanceInfo(param.toJSONString());
            log.info("MemberDay922Controller getCanPlayTimes ----> return:" + resultStr);
            JSONObject result = JSON.parseObject(resultStr);
            JSONObject dataJson = new JSONObject();
            if ("0".equals(String.valueOf(result.get("resultCode")))) {
                int times = Integer.parseInt(result.get("data").toString());
                if(times > 0){
                    dataJson.put("playtime", String.valueOf(result.get("data")));
                    resultJson.put("resultCode", 0);
                    resultJson.put("msg", "可抽奖: "+times+" 次");
                }else{
                    resultJson.put("resultCode", 2);
                    resultJson.put("msg", "无抽奖次数");
                }
            } else {
                resultJson.put("resultCode", 10);
                resultJson.put("msg", "查询失败");
            }

            //获取活动期间我的_年化投资总额
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId",userId);
            String userResultStr = activityUserCXFService.queryUserXYBamount(jsonObject.toJSONString());
            //log.info("MemberDay922Controller getCanPlayTimes[queryUserXYBamount] ----> return:" + userResultStr);
            WSModelResponse wsResponse = JsonUtil.jsonToBean(userResultStr, WSModelResponse.class);
            if (wsResponse.getResultCode() == AppConsts.WS_RETURN_SUCC && null != wsResponse.getData()) {
                Map<String, Object> map = JsonUtil.jsonToBean(wsResponse.getData().toString(), Map.class);
                if(null != map){
                    dataJson.put("userXYBAmount", map.get("APRSUM"));
                }
            }else{
                dataJson.put("userXYBAmount","0.00");
            }
            resultJson.put("data", dataJson);
        } catch (Exception e) {
            log.error("MemberDay922Controller getCanPlayTimes ----> arise exception:", e);
            resultJson.put("resultCode", 11);
            resultJson.put("msg", "查询异常");
        }

        //log.info("MemberDay922Controller getCanPlayTimes ----> return to page:" + resultJson.toJSONString());
        return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
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
    String playStart(HttpServletRequest request, HttpServletRequest response) {
        JSONObject resultJson = new JSONObject();
        try {
            String uid = request.getParameter("uid");
            String sign = request.getParameter("sign");
            String userId = uid;
            if (sign != null && !"".equals(sign)) {
                log.info("playEnd checkSign uid = " + uid + ",sign=" + sign);
                JSONObject result = checkSign(uid, sign);
                log.info("playEnd checkSing resp = " + result.toJSONString());
                int code = result.getIntValue("resultCode");
                if (code != 0) {
                    resultJson = result;
                    log.info("playEnd checkSing result = " + resultJson.toJSONString());
                    return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
                }

                if (uid == null || "".equals(uid)) {
                    resultJson.put("resultCode", 7);
                    resultJson.put("msg", "uid未空");
                    log.info("playEnd uid is null");
                    return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
                }
            } else {
                Object obj = request.getSession().getAttribute("loginUser");
                if (obj == null) {
                    resultJson.put("resultCode", 5);
                    resultJson.put("msg", "未登录");
                    log.info("playEnd loginUser is null ");
                    return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
                }
                User user = (User) obj;
                userId = String.valueOf(user.getUserId());
            }
            JSONObject param = new JSONObject();
            param.put("userId", userId);
            param.put("ip", HttpUtil.getRealIpAddr(request));
           // log.info("MemberDay922Controller playStart ----> params:" + param.toJSONString());
            String resultStr = activityUserCXFService.saveAdvancePrizeRec(param.toJSONString());
           // log.info("MemberDay922Controller playStart ----> return:" + resultStr);
            JSONObject result = JSON.parseObject(resultStr);
            if ("0".equals(String.valueOf(result.get("resultCode")))) {
                JSONObject dataJson = result.getJSONObject("data");
                JSONObject resultItem = new JSONObject();
                resultItem.put("itemId", String.valueOf(dataJson.get("prizeId")));
                resultItem.put("itemName", dataJson.get("prizeName"));
                JSONArray dataArr = new JSONArray();
                dataArr.add(resultItem);
                resultJson.put("data", dataArr);
                resultJson.put("resultCode", 0);
                resultJson.put("msg", "成功");
            } else {
                resultJson.put("resultCode", 10);
                resultJson.put("msg", result.getString("desc"));
            }
        } catch (Exception e) {
            log.error("MemberDay922Controller playStart ----> arise exception:", e);
            resultJson.put("resultCode", 11);
            resultJson.put("msg", "预生成抽奖记录异常");
        }

        //log.info("MemberDay922Controller playStart ----> return to page:" + resultJson.toJSONString());
        return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
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
    String playEnd(HttpServletRequest request, HttpServletRequest response) {
        JSONObject resultJson = new JSONObject();
        try {
            String uid = request.getParameter("uid");
            String sign = request.getParameter("sign");
            String userId = uid;
            if (sign != null && !"".equals(sign)) {
                log.info("playEnd checkSign uid = " + uid + ",sign=" + sign);
                JSONObject result = checkSign(uid, sign);
                log.info("playEnd checkSing resp = " + result.toJSONString());
                int code = result.getIntValue("resultCode");
                if (code != 0) {
                    resultJson = result;
                    log.info("playEnd checkSing result = " + resultJson.toJSONString());
                    return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
                }

                if (uid == null || "".equals(uid)) {
                    resultJson.put("resultCode", 7);
                    resultJson.put("msg", "uid未空");
                    log.info("playEnd uid is null");
                    return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
                }
            } else {
                Object obj = request.getSession().getAttribute("loginUser");
                if (obj == null) {
                    resultJson.put("resultCode", 5);
                    resultJson.put("msg", "未登录");
                    log.info("playEnd loginUser is null ");
                    return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
                }
                User user = (User) obj;
                userId = String.valueOf(user.getUserId());
            }

            JSONObject param = new JSONObject();
            param.put("userId", userId);
           // log.info("MemberDay922Controller playEnd ----> params:" + param.toJSONString());
            String resultStr = activityUserCXFService.saveConfirmPrizeRec(param.toJSONString());
            //log.info("MemberDay922Controller playEnd ----> return:" + resultStr);

            if (!StringUtil.isNotBlank(resultStr)) {
                resultJson.put("resultCode", 8);
                resultJson.put("msg", "抽奖失败");
                log.info("playEnd saveConfirmPrizeRec is null");
                return resultJson.toJSONString();
            }
            JSONObject result = JSON.parseObject(resultStr);
            int resultCode = result.getIntValue("resultCode");
            if (resultCode != 0) {
                resultJson.put("resultCode", 9);
                resultJson.put("msg", result.getString("desc"));
                log.info("playEnd saveConfirmPrizeRec result = " + resultStr);
                return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
            }

           // log.info("MemberDay922Controller getCanPlayTimes ----> params:" + param.toJSONString());
            String resultString = activityUserCXFService.queryLotteryChanceInfo(param.toJSONString());
          //  log.info("MemberDay922Controller getCanPlayTimes ----> return:" + resultString);
            JSONObject resJson = JSON.parseObject(resultString);
            if ("0".equals(String.valueOf(resJson.get("resultCode")))) {
                JSONObject resultItem = new JSONObject();
                resultItem.put("playtime", String.valueOf(resJson.get("data")));
                JSONArray dataArr = new JSONArray();
                dataArr.add(resultItem);
                resultJson.put("data", dataArr);
                resultJson.put("resultCode", 0);
            }
        } catch (Exception e) {
            log.error("MemberDay922Controller playEnd ----> arise exception:", e);
            resultJson.put("resultCode", 10);
            resultJson.put("msg", "抽奖失败");
            log.info("playEnd saveConfirmPrizeRec error");
        }

       // log.info("MemberDay922Controller playEnd ----> return to page:" + resultJson.toJSONString());
        return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
    }

    /**
     * 查询最新的15条用户中奖信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/luckyList", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String luckyList(HttpServletRequest request, HttpServletRequest response) {
        JSONObject paramJson = new JSONObject();
        JSONArray resultArray = new JSONArray();
        JSONObject resultItem = new JSONObject();
        try {
           // log.info("MemberDay922Controller luckyList ----> params:" + paramJson.toJSONString());
            String resultStr = activityUserCXFService.queryLast15PrizeRecs();
           // log.info("MemberDay922Controller luckyList ----> return:" + resultStr);
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
            if (StringUtil.isNotBlank(resultStr)) {
                JSONObject resultJson = JSON.parseObject(resultStr);
                if ("0".equals(String.valueOf(resultJson.get("resultCode")))) {
                    JSONArray jaData = resultJson.getJSONArray("data");
                    if (jaData != null && jaData.size() > 0) {
                        for (int i = 0; i < jaData.size(); i++) {
                            JSONObject jo = jaData.getJSONObject(i);
                            resultItem = new JSONObject();
                            resultItem.put("user", jo.get("nickName"));
                            resultItem.put("time", sdf2.format(sdf1.parse(String.valueOf(jo.get("addTime")))));
                            resultItem.put("reward", String.valueOf(jo.get("prizeName")));
                            resultArray.add(resultItem);
                        }
                    } else {
                        resultArray = new JSONArray();
                    }
                } else {
                    resultArray = new JSONArray();
                }
            } else {
                resultArray = new JSONArray();
            }
        } catch (Exception e) {
            log.error("MemberDay922Controller luckyList ----> arise exception:", e);
            resultArray = new JSONArray();
        }
        //log.info("MemberDay922Controller luckyList ----> return to page:" + resultArray.toJSONString());

        return TokenUtil.jsonpCallback(request, resultArray.toJSONString());
    }


    /**
     * 获取活动期间_年化投资总额前三的信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/topList", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String topList(HttpServletRequest request, HttpServletRequest response) {
        JSONObject paramJson = new JSONObject();
        JSONArray resultArray = new JSONArray();
        JSONObject resultItem = new JSONObject();
        try {
            //log.info("MemberDay922Controller topList ----> params:" + paramJson.toJSONString());
            String resultStr = activityUserCXFService.queryTop3AprSAmount();
            //log.info("MemberDay922Controller topList ----> return:" + resultStr);
            if (StringUtil.isNotBlank(resultStr)) {
                JSONObject resultJson = JSON.parseObject(resultStr);
                if ("0".equals(String.valueOf(resultJson.get("resultCode")))) {
                    JSONArray jaData = resultJson.getJSONArray("data");
                    if (jaData != null && jaData.size() > 0) {
                        for (int i = 0; i < jaData.size(); i++) {
                            JSONObject jo = jaData.getJSONObject(i);
                            resultItem = new JSONObject();
                            resultItem.put("user", jo.get("nickName"));
                            resultItem.put("amount", fmtMicrometer(String.valueOf(jo.get("aprSum")), 2));
                            resultArray.add(resultItem);
                        }
                    } else {
                        resultArray = new JSONArray();
                    }
                } else {
                    resultArray = new JSONArray();
                }
            } else {
                resultArray = new JSONArray();
            }
        } catch (Exception e) {
            log.error("MemberDay922Controller topList ----> arise exception:", e);
            resultArray = new JSONArray();
        }
        //log.info("MemberDay922Controller topList ----> return to page:" + resultArray.toJSONString());
        return TokenUtil.jsonpCallback(request, resultArray.toJSONString());
    }

    /**
     * 格式化数字为千分位显示；
     *
     * @param text     要格式化的数字；
     * @param decimals 保留小数位数（四舍五入）；
     * @return
     */
    public static String fmtMicrometer(String text, int decimals) {
        String result = "0";
        try {
            DecimalFormat df = null;
            StringBuffer pattern = new StringBuffer("###,##0");
            if (decimals > 0) {
                pattern.append(".");
                for (int i = 0; i < decimals; i++) {
                    pattern.append("0");
                }
            }
            df = new DecimalFormat(pattern.toString());
            double number = 0.0;
            number = Double.parseDouble(text);
            result = df.format(number);
        } catch (Exception e) {
            log.error("fmtMicrometer ----> arise exception:", e);
            result = "error";
        }
        return result;
    }


    public JSONObject checkSign(String uid, String sign) {
        JSONObject result = new JSONObject();
        try {
            JSONObject param = new JSONObject();
            param.put("userId", uid);
            log.info("getSignByUserId req param = " + param.toJSONString());
            String resp = activityUserCXFService.getSignByUserId(param.toJSONString());
            log.info("getSignByUserId resp = " + resp);
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
            log.info("tempSign=" + tempSign + ",sign=" + sign);
            if (tempSign.equalsIgnoreCase(sign)) {
                result.put("resultCode", 0);
                result.put("msg", "签名合法");
            } else {
                result.put("resultCode", 3);
                result.put("msg", "签名非法");
            }
        } catch (Exception e) {
            log.error(e);
            result.put("msg", "验证Sign异常");
            result.put("resultCode", 1);
        }
        return result;
    }

    @RequestMapping(value = "/genLoginSignInRedis", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String genLoginSignInRedis(HttpServletRequest request) {
        JSONObject param = new JSONObject();
        boolean flag = false;
        if (flag) {
            return param.toJSONString();
        }
        String userId = request.getParameter("userId");
        param.put("userId", userId);
        String resp = activityUserCXFService.genLoginSignInRedis(param.toJSONString());
        JSONObject resJson = JSON.parseObject(resp);

        param.put("resp", MD5Utils.MD5Encoder(userId + resJson.getString("data"), "UTF-8"));
        return param.toJSONString();
    }

    @RequestMapping(value = "/queryUserPrizeRecs", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String queryUserPrizeRecs(HttpServletRequest request) {
        JSONObject resultJson = new JSONObject();
        try {

            String uid = request.getParameter("uid");
            String sign = request.getParameter("sign");
            String userId = uid;

            if (sign != null && !"".equals(sign)) {
                log.info("queryUserPrizeRecs checkSign uid = " + uid + ",sign=" + sign);
                JSONObject result = checkSign(uid, sign);
                log.info("queryUserPrizeRecs checkSing resp = " + result.toJSONString());
                int code = result.getIntValue("resultCode");
                if (code != 0) {
                    resultJson = result;
                    log.info("queryUserPrizeRecs checkSing result = " + resultJson.toJSONString());
                    return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
                }

                if (uid == null || "".equals(uid)) {
                    resultJson.put("resultCode", 7);
                    resultJson.put("msg", "uid未空");
                    log.info("queryUserPrizeRecs uid is null");
                    return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
                }
            } else {
                Object obj = request.getSession().getAttribute("loginUser");
                if (obj == null) {
                    resultJson.put("resultCode", 5);
                    resultJson.put("msg", "未登录");
                    log.info("queryUserPrizeRecs loginUser is null ");
                    return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
                }
                User user = (User) obj;
                userId = String.valueOf(user.getUserId());
            }

            JSONObject param = new JSONObject();
            param.put("userId", userId);
            String currentPage = request.getParameter("currentPage");
            currentPage = currentPage == null ? "1" : currentPage;
            String pageSiz = request.getParameter("pageSize");
            pageSiz = pageSiz == null ? "10" : pageSiz;
            param.put("currentPage", currentPage);
            param.put("pageSize", pageSiz);
           // log.info("queryUserPrizeRecs req " + param.toJSONString());
            String respStr = activityUserCXFService.queryUserPrizeRecs(param.toJSONString());
           // log.info("queryUserPrizeRecs resp " + respStr);
            JSONObject result = JSONObject.parseObject(respStr);
            resultJson.put("resultCode", result.getString("resultCode"));
            JSONObject data = result.getJSONObject("data");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
            if (data != null) {
                JSONArray dataArr = new JSONArray();
                JSONArray resultList = data.getJSONArray("resultList");
                if (resultList != null) {
                    JSONObject jsonObj;
                    for (int i = 0; i < resultList.size(); i++) {
                        jsonObj = resultList.getJSONObject(i);
                        JSONObject temp = new JSONObject();
                        temp.put("time1", sdf3.format(sdf1.parse(jsonObj.getString("addTime"))));
                        temp.put("time", sdf2.format(sdf1.parse(jsonObj.getString("addTime"))));
                        temp.put("reward", jsonObj.getString("prizeName"));
                        dataArr.add(temp);
                    }
                }
                resultJson.put("data", dataArr);

                JSONObject pageInfo = new JSONObject();
                pageInfo.put("currentpage", data.getString("currentPage"));
                pageInfo.put("totalpage", data.getString("totalPages"));
                resultJson.put("pageInfo", pageInfo);
            }
        } catch (Exception e) {
            log.error("queryUserPrizeRecs error", e);
            resultJson.put("resultCode", 10);
            resultJson.put("msg", "error");
        }
        return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
    }


    @RequestMapping(value = "/getSign", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String getSign(HttpServletRequest request) {
        String uid = request.getParameter("uid");
        JSONObject param = new JSONObject();
        param.put("userId", uid);
        log.info("getSign req param = " + param.toJSONString());
        String resp = activityUserCXFService.getSignByUserId(param.toJSONString());
        log.info("getSign resp = " + resp);
        WSModelResponse response = JsonUtil.jsonToBean(resp, WSModelResponse.class);
        String tempSign = MD5Utils.MD5Encoder(uid + response.getData().toString(), "UTF-8");
        JSONObject result = new JSONObject();
        result.put("sign",tempSign);
        result.put("flg",response.getData().toString());
        return result.toJSONString();
    }

    @RequestMapping(value = "/checkAppSign", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String checkAppSign(HttpServletRequest request, HttpServletResponse response) {
        JSONObject resultJson = new JSONObject();
        try {

           /* if (uid == null || "".equals(uid)) {
                resultJson.put("resultCode", 7);
                resultJson.put("msg", "uid未空");
                log.info("checkAppSign.queryUserPrizeRecs uid is null");
                return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
            }

            if (sign == null || "".equals(sign)) {
                resultJson.put("resultCode", 8);
                resultJson.put("msg", "sign未空");
                log.info("checkAppSign.queryUserPrizeRecs sign is null");
                return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
            } */

            String uid = request.getParameter("uid");
            String sign = request.getParameter("sign");
            log.info("checkAppSign uid="+uid+",sign="+sign);

            Object userObj = request.getSession().getAttribute("loginUser");
            if(uid == null || "".equals(uid) || sign == null || "".equals(sign)) {
                if(userObj != null) {
                    request.getSession().removeAttribute("loginUser");
                }
                resultJson.put("resultCode", 400);
                resultJson.put("msg", "uid或sign未空");
                log.info("checkAppSign.uid or sign is null");
                return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
            }

            if (userObj == null) {
               // log.info("checkAppSign.queryUserPrizeRecs checkSign uid = " + uid + ",sign=" + sign);
                JSONObject result = checkSign(uid, sign);
                //log.info("checkAppSign.queryUserPrizeRecs checkSing resp = " + result.toJSONString());
                int code = result.getIntValue("resultCode");
                if (code != 0) {
                    resultJson = result;
                    log.info("checkAppSign.queryUserPrizeRecs checkSing result = " + resultJson.toJSONString());
                    return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
                }

                JSONObject param = new JSONObject();
                param.put("userId", uid);
               // log.info("checkAppSign.getUserById req =" + param.toJSONString());
                String userStr = userCXFService.getUserById(param.toJSONString());
               // log.info("checkAppSign.getUserById resp =" + userStr);
                DataResponse dataResponse = JsonUtil.jsonToBean(userStr, DataResponse.class);
                if (dataResponse.getResultCode() < 0) {
                    resultJson.put("resultCode", 10);
                    resultJson.put("msg", "验证APP签名失败");
                    return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
                }
                User user = JsonUtil.jsonToBean(dataResponse.getData().toString(), User.class);
                request.getSession().setAttribute("loginUser", user);
            } else {
                User u = (User) userObj;
              //  log.info("checkAppSign.queryUserPrizeRecs checkSign uid = " + uid + ",sign=" + sign);
                JSONObject result = checkSign(uid, sign);
              //  log.info("checkAppSign.queryUserPrizeRecs checkSing resp = " + result.toJSONString());
                int code = result.getIntValue("resultCode");
                if (code != 0) {
                    resultJson = result;
                    log.info("checkAppSign.queryUserPrizeRecs checkSing result = " + resultJson.toJSONString());
                    return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
                }
                if (u.getUserId() != Long.parseLong(uid)) {

                    JSONObject param = new JSONObject();
                    param.put("userId", uid);
                 //   log.info("checkAppSign.getUserById req =" + param.toJSONString());
                    String userStr = userCXFService.getUserById(param.toJSONString());
                 //   log.info("checkAppSign.getUserById resp =" + userStr);
                    DataResponse dataResponse = JsonUtil.jsonToBean(userStr, DataResponse.class);
                    if (dataResponse.getResultCode() < 0) {
                        resultJson.put("resultCode", 10);
                        resultJson.put("msg", "验证APP签名失败");
                        return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
                    }
                    User user = JsonUtil.jsonToBean(dataResponse.getData().toString(), User.class);
                    request.getSession().removeAttribute("loginUser");
                    request.getSession().setAttribute("loginUser", user);
                }
            }

            resultJson.put("resultCode", 0);
        } catch (Exception e) {
            resultJson.put("resultCode", 400);
            resultJson.put("msg", "验证APP签名异常");
            log.error("checkAppSign error", e);
        } finally {
            return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
        }
    }
}
