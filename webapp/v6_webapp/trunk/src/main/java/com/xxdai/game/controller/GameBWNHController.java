package com.xxdai.game.controller;

import com.xxdai.util.CacheUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xxdai.client.ClientUtil;
import com.xxdai.core.util.http.HttpUtil;
import com.xxdai.core.util.lang.StringUtil;
import com.xxdai.external.gamebwnh.ws.WeixinDFJGameCXFService;
import com.xxdai.external.user.ws.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ResourceBundle;

@Controller
@RequestMapping(value = "/gameBWNH")
public class GameBWNHController {
    private static final Logger log = Logger.getLogger(GameBWNHController.class);

    private final WeixinDFJGameCXFService weixinDFJGameCXFService = (WeixinDFJGameCXFService) ClientUtil.getWebService(WeixinDFJGameCXFService.class, "weixinGameWebService");
    private ResourceBundle resb = ResourceBundle.getBundle("wxMpConfig");
    // 从配置文件读取URL前缀
    private String urlPrefix = resb.getString("wxmp.urlprefix");

    @RequestMapping(value = "/ifCanPlayGame", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String ifCanPlayGame(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        JSONObject paramJson = new JSONObject();
        JSONObject resultJson = new JSONObject();
        try {
            String wechatId = String.valueOf(session.getAttribute("wechatId") == null ? "" : session.getAttribute("wechatId"));

            if (StringUtils.isBlank(wechatId)) {
                response.sendError(404);
                resultJson.put("resultCode", -1);
                resultJson.put("resultDesc", "请从微信访问");
                return resultJson.toJSONString();
            }

            String friendWechatId = String.valueOf(session.getAttribute("friendWechatId") == null ? "" : session.getAttribute("friendWechatId"));
            paramJson.put("wechatId", wechatId);
            if (StringUtils.isNotEmpty(friendWechatId) && !friendWechatId.equals(wechatId)) {
                paramJson.put("friendWechatId", friendWechatId);
                String result = weixinDFJGameCXFService.shareThenGetChange(paramJson.toJSONString());
                JSONObject json = JSON.parseObject(result);
                if ("0".equals(String.valueOf(json.get("resultCode")))) {
                    log.info("微信id:" + friendWechatId + ",分享" + wechatId + "成功");
                } else {
                    log.info("微信id:" + friendWechatId + ",分享" + wechatId + "失败");
                }
            }

            paramJson.put("ip", HttpUtil.getRealIpAddr(request));
            log.info("GameBWNHController ifCanPlayGame ----> params:" + paramJson.toJSONString());
            String resultStr = weixinDFJGameCXFService.ifCanPlayDfjGanme(paramJson.toJSONString());
            if (StringUtil.isNotBlank(resultStr)) {
                JSONObject resultStrJson = new JSONObject();
                resultStrJson = JSON.parseObject(resultStr);
                if ("0".equals(String.valueOf(resultStrJson.get("resultCode")))) {
                    resultJson.put("canPlayGame", resultStrJson.get("gameChances"));
                } else {
                    resultJson.put("canPlayGame", 0);
                }
                //剩余可兑换的分数
                String res = weixinDFJGameCXFService.queryGoalDetails(paramJson.toJSONString());
                JSONObject resJson = JSON.parseObject(res);
                long unExchangeScore = 0;
                if ("0".equals(String.valueOf(resJson.get("resultCode")))) {
                    JSONObject resData = JSON.parseObject(String.valueOf(resJson.get("resultData")));
                    if (resData != null) {
                        long dTotalPoint = resData.getLongValue("totalPoint");
                        long dRedeemPoint = resData.getLongValue("redeemPoint");
                        unExchangeScore = dTotalPoint - dRedeemPoint;
                        unExchangeScore = unExchangeScore < 0 ? 0 : unExchangeScore;
                    } else {
                        unExchangeScore = 0;
                    }
                } else {
                    unExchangeScore = 0;
                }
                resultJson.put("unExchangeScore", unExchangeScore);
                resultJson.put("resultCode", 0);

            } else {
                resultJson.put("resultCode", "-1");
                resultJson.put("resultDesc", "获取参与游戏信息失败，请稍后重试...");
            }
        } catch (Exception e) {
            log.error("GameBWNHController ifCanPlayGame ----> arise exception:", e);
            //返回页面
            resultJson.put("resultCode", -1);
            resultJson.put("resultDesc", "操作失败，请稍后重试...");
        }
        //log.info("GameBWNHController ifCanPlayGame ----> return to page:" + resultJson.toJSONString());
        return resultJson.toJSONString();
    }

    @RequestMapping(value = "/saveGameScore", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String saveGameScore(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        JSONObject paramJson = new JSONObject();
        JSONObject resultJson = new JSONObject();
        try {
            String wechatId = String.valueOf(session.getAttribute("wechatId") == null ? "" : session.getAttribute("wechatId"));
            String score = String.valueOf(request.getParameter("score") == null ? "" : request.getParameter("score"));
            if (StringUtils.isBlank(wechatId) || StringUtils.isBlank(score)) {
                response.sendError(404);
                resultJson.put("resultCode", -1);
                resultJson.put("resultDesc", "操作失败，请稍后重试...");
                return resultJson.toJSONString();
            }
            paramJson.put("wechatId", wechatId);
            paramJson.put("gamePoint", score);
            paramJson.put("ip", HttpUtil.getRealIpAddr(request));
            log.info("GameBWNHController saveGameScore ----> params:" + paramJson.toJSONString());
            String resultStr = weixinDFJGameCXFService.recordGameGoals(paramJson.toJSONString());
            if (StringUtil.isNotBlank(resultStr)) {
                log.info("GameBWNHController saveGameScore ----> return:" + resultStr);
                resultJson = JSON.parseObject(resultStr);
            } else {
                resultJson.put("resultCode", "-1");
                resultJson.put("resultDesc", "获取参与游戏信息失败，请稍后重试...");
            }
        } catch (Exception e) {
            log.error("GameBWNHController saveGameScore ----> arise exception:", e);
            //返回页面
            resultJson.put("resultCode", -1);
            resultJson.put("resultDesc", "操作失败，请稍后重试...");
        }
        log.info("GameBWNHController saveGameScore ----> return to page:" + resultJson.toJSONString());
        return resultJson.toJSONString();
    }

    @RequestMapping(value = "/scoreExchange", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String scoreExchange(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        JSONObject paramJson = new JSONObject();
        JSONObject resultJson = new JSONObject();
        try {

            //用户信息
            User user = (User) request.getSession().getAttribute("loginUser");
            if (user == null) {
                resultJson.put("resultCode", -2);
                resultJson.put("resultDesc", "用户未登录");
            } else {
                String wechatId = String.valueOf(session.getAttribute("wechatId") == null ? "" : session.getAttribute("wechatId"));
                if (StringUtils.isBlank(wechatId)) {
                    response.sendError(404);
                    resultJson.put("resultCode", -1);
                    resultJson.put("resultDesc", "操作失败，请稍后重试...");
                    return resultJson.toJSONString();
                }
//            	String score = String.valueOf(request.getParameter("score"));
//            	Double dbScore = Double.valueOf(score);
//            	BigDecimal bdScore = BigDecimal.valueOf(dbScore);
//            	BigDecimal bdXXCoin = bdScore.divide(GameBWNHController.SCORE_EXCHANGE_RATE).setScale(0, BigDecimal.ROUND_FLOOR);
                paramJson.put("userId", user.getUserId());
                paramJson.put("wechatId", wechatId);
//            	paramJson.put("exchangePoint", bdScore);
//            	paramJson.put("xxMoney", bdXXCoin);
                paramJson.put("ip", HttpUtil.getRealIpAddr(request));
                log.info("GameBWNHController scoreExchange ----> params:" + paramJson.toJSONString());
                String resultStr = weixinDFJGameCXFService.goalExchangeCoin(paramJson.toJSONString());
                if (StringUtil.isNotBlank(resultStr)) {
                    log.info("GameBWNHController scoreExchange ----> return:" + resultStr);
                    resultJson = JSON.parseObject(resultStr);
//            		if("0".equals(String.valueOf(resultJson.get("resultCode")))){
//            			resultJson.put("resultCode", "0");
//                		resultJson.put("resultDesc", "兑换成功");
//                		resultJson.put("exchangedXXCoin", bdXXCoin);
//            		}else{
//            			resultJson.put("resultCode", "-1");
//                		resultJson.put("resultDesc", "兑换失败，请稍后重试...");
//            		}
                } else {
                    resultJson.put("resultCode", "-1");
                    resultJson.put("resultDesc", "兑换失败，请稍后重试...");
                }
            }

        } catch (Exception e) {
            log.error("GameBWNHController scoreExchange ----> arise exception:", e);
            //返回页面
            resultJson.put("resultCode", -1);
            resultJson.put("resultDesc", "操作失败，请稍后重试...");
        }
        log.info("GameBWNHController scoreExchange ----> return to page:" + resultJson.toJSONString());
        return resultJson.toJSONString();
    }

    /**
     * 获取JS-SDK权限验证配置参数
     */
    @RequestMapping(value = "/getJsapiSignature", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getJsapiSignature(HttpSession session) {
        JSONObject resultJson = new JSONObject();

        resultJson.put("appid", session.getAttribute("appid"));
        resultJson.put("timestamp", session.getAttribute("timestamp"));
        resultJson.put("noncestr", session.getAttribute("noncestr"));
        resultJson.put("signature", session.getAttribute("signature"));

        resultJson.put("wechatId", session.getAttribute("wechatId"));

        resultJson.put("urlprefix", urlPrefix);

        resultJson.put("resultCode", 0);

        return resultJson.toString();
    }

    /**
     * 减少游戏次数
     *
     * @param request
     * @param response
     * @param session
     * @return
     */
    @RequestMapping(value = "/substractGameChances", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String substractGameChances(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String wechatId = String.valueOf(session.getAttribute("wechatId") == null ? "" : session.getAttribute("wechatId"));
        if (StringUtils.isBlank(wechatId)) {
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("wechatId", wechatId);
        weixinDFJGameCXFService.substractGameChances(jsonObject.toJSONString());
        return null;
    }

    @RequestMapping(value = "/getXMoneyPercent", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getXMoneyPercent(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("xMoneyPercent", CacheUtil.getCacheValue("WX_POINT_XMONET"));
        return jsonObject.toJSONString();
    }



    @RequestMapping(value = "/addGameChances", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String addGameChances(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        JSONObject jsonObject = new JSONObject();
        try {
            JSONObject param = new JSONObject();
            String wechatId = String.valueOf(session.getAttribute("wechatId") == null ? "" : session.getAttribute("wechatId"));
            if (StringUtils.isBlank(wechatId)) {
                jsonObject.put("resultCode",404);
                jsonObject.put("msg","获取不到微信ID");
                return null;
            }
            param.put("wechatId",wechatId);
            String num = request.getParameter("num");
            param.put("num",num);
            log.info("addGameChances req = " + param.toJSONString());
            String res = weixinDFJGameCXFService.addGameChances(param.toJSONString());
            log.info("addGameChances resp = "  + res);
        } catch (Exception e) {
             log.error("addGameChances error ",e);
            jsonObject.put("resultCode",400);
            jsonObject.put("msg","给用户添加游戏次数异常");
        }
        return jsonObject.toJSONString();
    }

}
