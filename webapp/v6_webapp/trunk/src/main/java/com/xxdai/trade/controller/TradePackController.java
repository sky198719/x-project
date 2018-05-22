/**
 * <p>Title: TradeRequestController.java</p>
 * <p>Description: </p>
 * <p>Copyright (c) 2014, www.xinxindai.com All Rights Reserved. </p>
 * <p>Company: www.xinxindai.com</p>
 * @author huna
 * @date 2014年9月16日
 * @version 1.0
 */
package com.xxdai.trade.controller;

import com.alibaba.fastjson.JSONObject;
import com.xxdai.client.CXF_Factory;
import com.xxdai.constant.AppConsts;
import com.xxdai.constant.Constant;
import com.xxdai.core.util.http.HttpUtil;
import com.xxdai.core.util.http.WSMapResponse;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.encrypt.EscapeCode;
import com.xxdai.external.account.ws.AccountQueryCXFService;
import com.xxdai.external.user.ws.User;
import com.xxdai.external.user.ws.UserCXFService;
import com.xxdai.trade.webservice.TradeCXFWebService;
import com.xxdai.trade.webservice.entity.TradeResponse;
import com.xxdai.user.model.UserResponse;
import com.xxdai.util.Configuration;
import com.xxdai.util.DicCommonUtils;
import com.xxdai.util.HttpTookit;
import com.xxdai.util.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @author huna
 * @version $Id: TradePackController.java 16243 2015-03-16 01:47:30Z huna $
 * @since jdk1.6
 */

@Controller
@RequestMapping(value = "/tradepack")
public class TradePackController {
    Log logger = LogFactory.getLog(TradePackController.class);

    private TradeCXFWebService tradeCXFWebService = (TradeCXFWebService) CXF_Factory.getFactory(TradeCXFWebService.class, Configuration.getInstance().getValue("webService_url") + "/tradeWebService").create();

    private AccountQueryCXFService accountQueryCXFService = (AccountQueryCXFService) CXF_Factory.getFactory(AccountQueryCXFService.class, Configuration.getInstance().getValue("trade_url") + "/accountQueryWebService").create();

    private UserCXFService userCXFService = (UserCXFService) CXF_Factory.getFactory(UserCXFService.class, Configuration.getInstance().getValue("webService_url") + "/userWebService").create();

    /**
     * 购买债权前校验支付密码、验证码、可用体验金额、可使用的红包、账户可用金额等信息
     *
     * @param request
     * @param response
     * @param session
     * @return
     */
    @RequestMapping(value = "/checkBeforeBuyTrade", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String checkBeforeBuyTrade(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        JSONObject json = new JSONObject();
        //判断当前token是否有效
        if (!TokenUtil.validToken(request)) {
            json.put("resultCode", Constant.TOKEN_INVALID_ERROR);
            json.put("msg", "页面已过期，请重新尝试");
            return json.toString();
        }
        // 销毁token
        TokenUtil.removeToken(request);

        //获取当前登陆用户
        Object userObj = request.getSession().getAttribute("loginUser");
        if (userObj == null) {
            json.put("resultCode", -99);
            json.put("msg", "您的会话已过期，请重新登录");
            return json.toJSONString();
        }

        //检查系统开关 - 债权转让开关是否为打开状态 1打开 0关闭  update by huna20141216
        String trade = DicCommonUtils.getDicValue("SYS_CONTROL_SWITCH", "TRADE");
        if ("0".equals(trade)) {
            json.put("resultCode", -505);
            json.put("msg", "抱歉，暂时不能购买债权！");
            return json.toString();
        }

        User user = (User) userObj;

        //转让人ID
        Long requestUserId = Long.parseLong(request.getParameter("requestUserId"));
        if (user.getUserId().longValue() == requestUserId.longValue()) {
            json.put("resultCode", -100);
            json.put("msg", "不能购买自己发布的债权转让标！");
            return json.toString();
        }

        //发标人ID
        Long borrowUserId = Long.parseLong(request.getParameter("borrowUserId"));
        if (user.getUserId().longValue() == borrowUserId.longValue()) {
            json.put("resultCode", -101);
            json.put("msg", "不能购买发标人为自己的债权转让标！");
            return json.toString();
        }

        //校验验证码
        String verifyCode = request.getParameter("verifyCode");
        if (StringUtils.isNotBlank(verifyCode)) {
            String randImg = (String) request.getSession().getAttribute("yxRand");
            if (randImg == null || verifyCode == null || !randImg.toLowerCase().equals(verifyCode.toLowerCase())) {
                json.put("resultCode", -102);
                json.put("msg", "verifyCode:验证码错误！");
                return json.toString();
            }
        }

        //校验支付密码
        String md5pwd = request.getParameter("payPassword");
        if (StringUtils.isNotBlank(md5pwd)) {
            try {
                //校验支付密码
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("userId", user.getUserId());
                jsonObj.put("password", EscapeCode.Encryption(md5pwd));
                jsonObj.put("ip", HttpUtil.getRealIpAddr(request));
                String browser = request.getHeader("User-Agent");
                browser = browser.length() > 200 ? browser.substring(0,200):browser;
                jsonObj.put("browser", browser);
                String resultStr = userCXFService.checkPayPassword(JsonUtil.beanToJson(jsonObj));
                UserResponse wsResponse = JsonUtil.jsonToBean(resultStr, UserResponse.class);
                int resultCode = 0;
                String msg = "";
                if (wsResponse != null) {
                    switch (wsResponse.getResultCode()) {
                        case -1:
                            resultCode = -30;
                            msg = "payPassword:获取支付密码异常！";
                            break;
                        case -2:
                            resultCode = -31;
                            msg = "payPassword:支付密码错误，请重新输入！";
                            break;
                        case 220:
                            resultCode = -32;
                            msg = "payPassword:您还未设置支付密码！";
                            break;
                        case 230:
                            resultCode = -33;
                            msg = "payPassword:支付密码和登录密码相同，为保证您的资金安全，请修改支付密码！";
                            break;
                        default:
                    }

                    json.put("msg", msg);
                    json.put("resultCode", resultCode);
                    return json.toString();
                }
            } catch (Exception e) {
                logger.error(e);
                json.put("resultCode", -4);
                json.put("msg", "payPassword:支付密码错误，请重新输入！");
                return json.toString();
            }
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", user.getUserId());
        String resultStr = accountQueryCXFService.selectUserAccountAndCoupon(jsonObject.toJSONString());
        WSMapResponse wsMapResponse = JsonUtil.jsonToBean(resultStr, WSMapResponse.class);

        //获取体验金及抵用券(即红包)
        if (StringUtils.isNotBlank(request.getParameter("experienceAccountUsable"))) {
            //验证可用体验金额
            Map experienceAccountMap = (Map) wsMapResponse.getMap().get("experienceAccount");
            Double experienceAccountUsable = Double.parseDouble(request.getParameter("experienceAccountUsable"));
            if (experienceAccountUsable > 0) {
                if (experienceAccountMap != null && experienceAccountMap.get("USABLE") != null) {
                    BigDecimal usable = BigDecimal.valueOf(new Double(experienceAccountMap.get("USABLE").toString()));
                    if (usable.doubleValue() < experienceAccountUsable) {
                        json.put("resultCode", -5);
                        json.put("msg", "您账户可使用的体验金为【" + usable.doubleValue() + "】元，请确认后重新输入！");
                        return json.toString();
                    }
                }
            }
        }

        //可使用的红包
        //Map couponMap = (Map)wsMapResponse.getMap().get("coupon");

        //账户可用金额
        if (StringUtils.isNotBlank(request.getParameter("repayYesAccount"))) {
            Map accountMap = (Map) wsMapResponse.getMap().get("defaultAccount");
            Double repayYesAccount = Double.parseDouble(request.getParameter("repayYesAccount"));
            if (accountMap != null && accountMap.get("USABLE") != null) {
                BigDecimal usable = BigDecimal.valueOf(Double.valueOf(accountMap.get("USABLE").toString()));
                if (usable.doubleValue() < repayYesAccount) {
                    json.put("resultCode", -6);
                    json.put("msg", "您账户可使用的金额为【" + usable.doubleValue() + "】元，少于支付金额，请充值后重新购买！");
                    return json.toString();
                }
            } else {
                json.put("resultCode", -6);
                json.put("msg", "您账户资金不足，请充值后再购买！");
                return json.toString();
            }
        }

        //买入时剩余期数
        int terms = 0;
        if (StringUtils.isNotBlank(request.getParameter("terms"))) {
            terms = Integer.parseInt(request.getParameter("terms"));
        }
        if (terms == 0) {
            json.put("resultCode", -7);
            json.put("msg", "该债权标剩余期数为0，无法购买！");
            return json.toString();
        }
        return json.toString();
    }

    /**
     * 购买债权转让标
     */
    @RequestMapping(value = "/buyTradePack", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String buyTradePack(HttpServletRequest request) {
        JSONObject json = new JSONObject();

        //获取当前登陆用户
        Object userObj = request.getSession().getAttribute("loginUser");
        if (userObj == null) {
            json.put("resultCode", -404);
            json.put("msg", "您的会话失效，请重新登录");
            return json.toJSONString();
        }

        try {
            //获取页面参数
            String requestId = request.getParameter("requestId"); //债权转让编号
            String tenderId = request.getParameter("tenderId");   //标的投标编号
            Double experienceAccountUsable = Double.parseDouble(request.getParameter("experienceAccountUsable"));  //使用的体验金金额
            Long requestUserId = Long.parseLong(request.getParameter("requestUserId"));   //债权转让人ID

            //获取债权转让详情
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("requestId", requestId);

            //封装参数
            User user = (User) userObj;
            long userId = user.getUserId();
            jsonObject.put("userId", userId);
            jsonObject.put("requestId", requestId);
            jsonObject.put("tenderId", tenderId);
            jsonObject.put("experienceAccountUsable", experienceAccountUsable);  //体验金账户支出金额

            String realIpAddr = HttpTookit.getRealIpAddr(request);
            String clientIp = "";
            if (StringUtils.isNotBlank(realIpAddr)) {
                clientIp = realIpAddr.split("@")[0];
            }
            jsonObject.put("clientIp", clientIp);

            jsonObject.put("terminalVer",HttpTookit.getRequestTerminal(request));

            //查询可转让的债权
            logger.info("buyTradePack req = " + JsonUtil.beanToJson(jsonObject));
            String resultStr = tradeCXFWebService.buyTradePack(JsonUtil.beanToJson(jsonObject));
            logger.info("buyTradePack resp = " + resultStr);
            //结果集转对象
            TradeResponse wsResponse = JsonUtil.jsonToBean(resultStr, TradeResponse.class);
            if (wsResponse.getResultCode() == AppConsts.WS_RETURN_SUCC) {
                json.put("resultCode", wsResponse.getResultCode());
                json.put("msg", "债权购买成功！");
            } else {
                json.put("resultCode", wsResponse.getResultCode());
                json.put("msg", wsResponse.getDesc());
            }
        } catch (Exception e) {
            logger.error("购买债权转让异常", e);
            json.put("resultCode", 505);
            json.put("msg", "购买转让失败，请重新尝试");
        }
        return json.toString();
    }

    /**
     * 查看债权转让合同
     */
    @RequestMapping(value = "/showContract")
    public ModelAndView showContract(HttpServletRequest request) {
        //获取当前登陆用户
        Object userObj = request.getSession().getAttribute("loginUser");

        //获取页面参数
        String requestId = request.getParameter("requestId");

        ModelAndView mv = new ModelAndView("introduce/agreement_credittrainsfer");
        if (userObj != null) {
            User user = (User) userObj;
            long userId = user.getUserId();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("requestId", requestId);

            //根据转让申请编号查询转让合同信息
            String resultStr = tradeCXFWebService.getTransferContract(JsonUtil.beanToJson(jsonObject));
            WSMapResponse wsMap = JsonUtil.jsonToBean(resultStr, WSMapResponse.class);
            mv.addObject("contract", wsMap.getMap());
        } else {
            return new ModelAndView("redirect:/user/ilogin.html");
        }

        return mv;
    }

}
