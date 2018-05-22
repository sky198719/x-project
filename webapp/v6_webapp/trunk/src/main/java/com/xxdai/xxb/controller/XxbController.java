/**
 * Copyright (c) 2016, www.xinxindai.com All Rights Reserved.
 */
package com.xxdai.xxb.controller;

import com.alibaba.fastjson.JSONObject;
import com.xxdai.account.model.AccountResponse;
import com.xxdai.account.model.MarketWsResponse;
import com.xxdai.client.CXF_Factory;
import com.xxdai.constant.Constant;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.external.account.ws.AccountQueryCXFService;
import com.xxdai.external.accquery.ws.AccQueryCXFService;
import com.xxdai.external.market.ws.MarketCXFService;
import com.xxdai.external.user.ws.User;
import com.xxdai.external.user.ws.UserCXFService;
import com.xxdai.http.*;
import com.xxdai.market.bo.Levels;
import com.xxdai.util.Configuration;
import com.xxdai.util.HttpTookit;
import com.xxdai.util.PageUtils;
import com.xxdai.xxb.bo.LevelLogs;
import com.xxdai.xxb.constants.XxbConstant;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述
 *
 * @version $Id: xxbController.java 2016/5/6 10:58 $
 * @since jdk1.6
 */
@Controller
@RequestMapping(value = "/xxb")
public class XxbController {
    private static final Logger log = Logger.getLogger(XxbController.class);
    MarketCXFService marketCXFService = (MarketCXFService) CXF_Factory.getFactory(MarketCXFService.class, com.xxdai.util.Configuration.getInstance().getValue("webService_url") + "/marketWebService").create();
    AccountQueryCXFService accountQueryCXFService = (AccountQueryCXFService) CXF_Factory.getFactory(AccountQueryCXFService.class, com.xxdai.util.Configuration.getInstance().getValue("trade_url") + "/accountQueryWebService").create();
    UserCXFService userService = (UserCXFService) CXF_Factory.getFactory(UserCXFService.class, com.xxdai.util.Configuration.getInstance().getValue("webService_url") + "/userWebService").create();
    AccQueryCXFService accQueryCXFService = (AccQueryCXFService) CXF_Factory.getFactory(AccQueryCXFService.class, com.xxdai.util.Configuration.getInstance().getValue("webService_url") + "/accQueryWebService").create();
    @Autowired
    ApiUtil apiUtil;

    private static String XXDAI_LEVEL_LOGS_COIN_CASH_TYPE = "coin";    //兑换新新币

    @RequestMapping(value = "/getXxbInfo", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getXxbInfo(HttpSession session) {
        JSONObject result = new JSONObject();
        User user = (User) session.getAttribute("loginUser");
        String str = marketCXFService.selectLevelsByUserId(user.getUserId().toString());
        MarketWsResponse res = JsonUtil.jsonToBean(str, MarketWsResponse.class);
        String datastr = "";
        Levels levels = null;
        if (res.getData() != null) {
            datastr = String.valueOf(res.getData());
            levels = JsonUtil.jsonToBean(datastr, Levels.class);
        }
        result.put("levels", levels);
        return result.toJSONString();
    }

    /**
     * 新新币兑换
     */
    @RequestMapping(value = "/exchange", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String exchange(HttpServletRequest request, HttpSession session) {
        JSONObject result = new JSONObject();
        try {

            //用户信息
            User user = (User) session.getAttribute("loginUser");
            if (user == null) {
                log.info("新新币兑换，未登录，");
                result.put("message", "您未登录，不能进行下一步操作");
                result.put("code", 200);
                return result.toString();
            }

            String randCode = request.getParameter("randCode");
            String imgCodeObj = (String) request.getSession().getAttribute("imgCode");
            if (!imgCodeObj.equalsIgnoreCase(randCode)) {
                result.put("code", -5);
                result.put("message", XxbConstant.XxbExchangeMsg.get(-5));
                return result.toJSONString();
            }
            String exchangeNum = request.getParameter("exchangeNum");
            String pwd = request.getParameter("pwd");

            String url = Configuration.getInstance().getValue("accountCenter") + "/user/exchangeXXCoinWithOutPicVerifyCode";

            Headers headers = Headers.createHeaders()
                    .addHeader("token", HttpTookit.getCookieValue(request, Constant.USERTOKEN))
                    .addHeader("clientId", Constant.XXD_INTEGRATION_PLATFORM)
                    .addHeader("clientTime", System.currentTimeMillis())
                    .addHeader("User-Agent",HttpTookit.getUserAgent(request));

            RequestDTO requestDTO = RequestDTO.create()
                    .addParameter("payPW", pwd)
                    .addParameter("xxCoinNumber", exchangeNum);
            FormUrlEncoded formUrlEncoded = FormUrlEncoded.create()
                    .setBody(requestDTO.transform2JsonString());
            log.info("exchange req " + JSONObject.toJSONString(requestDTO));
            Message message = apiUtil.post(url, headers, formUrlEncoded);


            if (message != null) {
                //兑换成功
                result = JSONObject.parseObject(JSONObject.toJSONString(message));
            } else {
                result.put("message", "兑换失败");
                result.put("code",400);
            }

            log.info("exchange result " + result.toJSONString());
            return result.toJSONString();
        } catch (Exception e) {
            result.put("message", "操作异常，请重新尝试或者联系客服");
            result.put("code", -100);
            log.error("新新币兑换失败，" + e.getMessage(), e);
            return result.toJSONString();
        }
    }

    /**
     * 信用等级，新新币
     *
     * @param userId
     * @return
     */
    private Levels getLevels(Long userId) {
        Levels levels = null;
        String str = marketCXFService.selectLevelsByUserId(JsonUtil.beanToJson(userId));
        MarketWsResponse mres = JsonUtil.jsonToBean(str, MarketWsResponse.class);
        if (mres.getData() != null) {
            levels = JsonUtil.jsonToBean(mres.getData().toString(), Levels.class);
        }
        return levels;
    }

    private boolean isPositiveDecimal(String orginal) {
        return isMatch("\\+{0,1}[0]\\.[1-9]*|\\+{0,1}[1-9]\\d*\\.\\d*", orginal);
    }

    /**
     * 验证 输入金额是否正确
     *
     * @param regex
     * @param orginal
     * @return
     */
    private boolean isMatch(String regex, String orginal) {
        if (orginal == null || orginal.trim().equals("")) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher isNum = pattern.matcher(orginal);
        return isNum.matches();
    }

    /**
     * 新新币兑换记录
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/exchangeLogs", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String exchangeLogs(HttpServletRequest request, HttpSession session) {
        JSONObject result = new JSONObject();
        try {
            //用户信息
            User user = (User) session.getAttribute("loginUser");
            if (user == null) {
                log.info("新新币兑换，未登录，");
                result.put("msg", "您未登录，不能进行下一步操作");
                result.put("resultCode", 200);
                return result.toString();
            }
            int pageSize = 10; // 页面加载量
            // int currentPage = 1; // 当前页
            String str = marketCXFService.selectLevelsByUserId(JsonUtil.beanToJson(user.getUserId()));
            MarketWsResponse res = JsonUtil.jsonToBean(str, MarketWsResponse.class);
            String datastr = "";
            Levels levels = null;
            if (res.getData() != null) {
                datastr = String.valueOf(res.getData());
                levels = JsonUtil.jsonToBean(datastr, Levels.class);
            }
            String type = request.getParameter("type");
            String temp = request.getParameter("temp");
            if (temp == null) {
                temp = "1";
            }

            //	int pageData = 8; //页面加载量
            //shezhigao
            String current = request.getParameter("currentPage");
            current = (current == null || "".equals(current)) ? "1" : current;

//			int currentPage = 1; //当前页
            int currentPage = Integer.valueOf(current);
            currentPage = (currentPage <= 0) ? 1 : currentPage;

            Map map = new HashMap();
            map.put("type", type);//type 0查询所有记录 1查询奖励 2查询兑换
            map.put("currentPage", currentPage);
            map.put("pageSize", pageSize);
            map.put("userId", user.getUserId());
            log.info("selectLevelLogsByUserId，the request parameters=" + JsonUtil.beanToJson(map));
            str = accQueryCXFService.selectLevelLogsByUserId(JsonUtil.beanToJson(map));
           //log.info("selectLevelLogsByUserId，the response=" + str);
            AccountResponse resp = JsonUtil.jsonToBean(str, AccountResponse.class);
            PageUtils pageUtils = new PageUtils();
            if (resp.getData() != null) {
                datastr = String.valueOf(resp.getData());
                pageUtils = JsonUtil.jsonToBean(datastr, PageUtils.class);
            }
            List<LevelLogs> levelsLog = pageUtils.getResultList();
            int count = pageUtils.getTotalSize();
            result.put("msg", resp.getDesc());
            result.put("resultCode", resp.getResultCode());
            result.put("levelsLog", levelsLog);
            result.put("levels", levels);
            result.put("currentPage", currentPage);
            result.put("pageData", pageSize);
            result.put("count", count);
            result.put("maxPage", pageUtils.getTotalPages());
        } catch (Exception e) {
            result.put("desc", "操作异常，请重新尝试或者联系客服");
            result.put("result", -100);
            log.error("查询新新币兑换记录失败，" + e.getMessage(), e);
        }
        return result.toJSONString();
    }
}
