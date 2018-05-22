/**
 * Copyright (c) 2016, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.activity.controller;

import com.alibaba.fastjson.JSONObject;
import com.xxdai.client.CXF_Factory;
import com.xxdai.constant.AppConsts;
import com.xxdai.core.util.http.HttpUtil;
import com.xxdai.core.util.http.WSResponse;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.core.util.lang.StringUtil;
import com.xxdai.external.activityUser.ws.ActivityUserCXFService;
import com.xxdai.external.user.ws.User;
import com.xxdai.util.Configuration;
import com.xxdai.ws.util.WSModelResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/valentineFestival")
public class ValentineFestivalController {
    Logger log = Logger.getLogger(ValentineFestivalController.class);
    private ActivityUserCXFService activityUserCXFService = (ActivityUserCXFService) CXF_Factory.getFactory(ActivityUserCXFService.class, Configuration.getInstance().getValue("webService_url") + "/activityUser").create();


    @RequestMapping(value = "/getUserActivityInfo", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getUserActivityInfo(HttpServletRequest request, HttpServletRequest response) {
        JSONObject resultJson = new JSONObject();
        try {
            //验证是否登录状态
            Object userObj = request.getSession().getAttribute("loginUser");
            String userId = "-1";
            if (null != userObj) {
                User user = (User) userObj;
                userId = user.getUserId().toString();
            }

            //查询活动信息&用户个人活动投资信息&总体活动投资信息
            log.info("queryUserActivityInfo param userid= " + userId);
            String jsonStr = activityUserCXFService.queryUserActivityInfo(userId);
            resultJson.put("resultCode", 0);
            resultJson.put("data", jsonStr);
        } catch (Exception e) {
            log.error("store getSumData arise exception:", e);
            resultJson.put("resultCode", -1);
            resultJson.put("resultDesc", "获取数据异常，请稍后重试");
        }
        return resultJson.toJSONString();
    }


    // 选择队伍
        @RequestMapping("/chooseTeam")
        @ResponseBody
        public WSResponse chooseTeam(String choiceId, HttpSession session, HttpServletRequest request) throws Exception {
            WSResponse wsResponse = new WSResponse();
            try {
                Object userObj = session.getAttribute("loginUser");
                if (userObj == null) {
                    wsResponse.setResultCode(-3);
                    wsResponse.setDesc("您还没有登录，请登录后参加活动！");
                    return wsResponse;
                }
                User user = (User)userObj;
                /*String jsonStr = activityUserCXFService.queryUserActivityInfo(user.getUserId().toString());
                if (StringUtil.isNotBlank(jsonStr)) {
                    WSModelResponse response = JsonUtil.jsonToBean(jsonStr, WSModelResponse.class);
                    if(response.getResultCode() == AppConsts.WS_RETURN_SUCC &&  null != response.getData()) {
                        JSONObject jsonObj = JSONObject.parseObject(response.getData().toString());
                        if (null != jsonObj && -3 != jsonObj.getIntValue("activityPhase")) {

                        }
                    }
                }   */

                JSONObject params = new JSONObject();
                params.put("choiceId", choiceId);
                params.put("userId", user.getUserId());
                params.put("terminalver", request.getHeader("user-agent"));
                params.put("createIp", HttpUtil.getRealIpAddr(request));
                String str = activityUserCXFService.joinActivity(JsonUtil.beanToJson(params));
                wsResponse = JsonUtil.jsonToBean(str, WSResponse.class);
            } catch (Exception e) {
                log.error("选择队伍异常！", e);
                wsResponse.setResultCode(-1);
                wsResponse.setDesc("选择队伍失败，请重新尝试");
            }
            return wsResponse;
        }
}
