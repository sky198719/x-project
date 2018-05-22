/**
 * Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.plan.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxdai.borrow.constants.RedEnvelopeLimitTypeConsts;
import com.xxdai.client.CXF_Factory;
import com.xxdai.constant.AppConsts;
import com.xxdai.constant.Constant;
import com.xxdai.core.util.http.HttpUtil;
import com.xxdai.core.util.http.WSMapResponse;
import com.xxdai.core.util.http.WSResponse;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.core.util.lang.DateUtil;
import com.xxdai.encrypt.EscapeCode;
import com.xxdai.external.account.ws.AccountQueryCXFService;
import com.xxdai.external.accounttrade.ws.AccountTradeCXFService;
import com.xxdai.external.lottery.ws.LotteryCXFService;
import com.xxdai.external.plan.ws.PlanCXFService;
import com.xxdai.external.user.ws.User;
import com.xxdai.external.user.ws.UserCXFService;
import com.xxdai.person.model.PersonResponse;
import com.xxdai.person.ws.approquery.ApproQueryCXFService;
import com.xxdai.plan.constants.AccountConsts;
import com.xxdai.plan.model.SchemeVO;
import com.xxdai.plan.util.PlanUtils;
import com.xxdai.plan.vo.Scheme;
import com.xxdai.product.service.ProductService;
import com.xxdai.redpacket.model.RedenvelopeRecord;
import com.xxdai.redpacket.model.RpWsResponse;
import com.xxdai.redpacket.webservice.interfaces.RedpacketCXFService;
import com.xxdai.seo.model.CategoryInfo;
import com.xxdai.seo.model.SeoResponse;
import com.xxdai.seo.ws.category.SeoCategoryCXFService;
import com.xxdai.user.model.UserResponse;
import com.xxdai.user.service.UserService;
import com.xxdai.util.Configuration;
import com.xxdai.util.HttpTookit;
import com.xxdai.util.TokenUtil;
import com.xxdai.ws.util.WSModelResponse;
import com.xxdai.ws.util.WSPageResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 新元宝相关操作
 *
 * @version $Id: PlanCotroller.java 18533 2015-04-30 05:24:57Z wangyuxiang $
 * @since jdk1.6
 */
@Controller
@RequestMapping(value = "/xplan")
public class PlanCotroller {
    /**
     * 日志记录器
     */
    Logger log = LoggerFactory.getLogger(PlanCotroller.class);

    /**
     * 新元宝接口
     */
    private PlanCXFService planCXFService = (PlanCXFService) CXF_Factory.getFactory(PlanCXFService.class, Configuration.getInstance().getValue("webService_url") + "/planWebService").create();

    /**
     * 账户查询接口
     */
    private AccountQueryCXFService accountQueryCXFService = (AccountQueryCXFService) CXF_Factory.getFactory(AccountQueryCXFService.class, Configuration.getInstance().getValue("trade_url") + "/accountQueryWebService").create();

    /**
     * 用户操作接口
     */
    private UserCXFService userCXFService = (UserCXFService) CXF_Factory.getFactory(UserCXFService.class, Configuration.getInstance().getValue("webService_url") + "/userWebService").create();

    /**
     * 账户操作接口
     */
    private AccountTradeCXFService accountTradeCXFService = (AccountTradeCXFService) CXF_Factory.getFactory(AccountTradeCXFService.class, Configuration.getInstance().getValue("trade_url") + "/accountTradeWebService").create();

    /**
     * 认证信息查询接口
     */
    private ApproQueryCXFService approQueryCXFService = (ApproQueryCXFService) CXF_Factory.getFactory(ApproQueryCXFService.class, Configuration.getInstance().getValue("webService_url") + "/approQueryWebService").create();


    private RedpacketCXFService redpacketCXFService = (RedpacketCXFService) CXF_Factory.getFactory(RedpacketCXFService.class, Configuration.getInstance().getValue("webService_url") + "/redpacketWebService").create();

    private SeoCategoryCXFService seoCategoryCXFService = (SeoCategoryCXFService) CXF_Factory.getFactory(SeoCategoryCXFService.class, com.xxdai.util.Configuration.getInstance().getValue("webService_url") + "/seoCategoryWebService").create();

    private LotteryCXFService lotteryCXFService = (LotteryCXFService) CXF_Factory.getFactory(LotteryCXFService.class, Configuration.getInstance().getValue("webService_url") + "/lotteryWebService").create();

    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    /**
     * 加载新元宝列表页面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/search/list")
    public String borrowSearch(HttpServletRequest request) {

    	//相关专题
    	List relSubjList=this.getRelSeoSubject();
    	//热门文章
    	List newList=this.getNewSeoInfo();    	
    	request.setAttribute("relSubjList", relSubjList);
    	request.setAttribute("newList", newList);
    	
        return "/plan/xplanList";
    }

    /**散标列表
     * 相关专题  产品页与资讯页入口展现
     * @return
     */
    public List getRelSeoSubject(){
    	 JSONObject resultJson = new JSONObject();
         JSONObject obj = new JSONObject();
         List  list = new ArrayList();
        String jsonstr= seoCategoryCXFService.getThridSeoCategoryByFirstCate(obj.toString());
        SeoResponse seoResponse = JsonUtil.jsonToBean(jsonstr, SeoResponse.class);
        
        if (seoResponse.getResultCode() == 0) {
     	   list=JsonUtil.jsonToList(seoResponse.getData().toString(), List.class);    	   
        } else {
            log.error(seoResponse.getDesc());
        }
        return list;
    }
    
    /**散标列表
     * 热门文章  产品页与资讯页入口展现
     * @return
     */
    public List  getNewSeoInfo(){
    	List  list = new ArrayList();
        JSONObject resultJson = new JSONObject();
        String jsonstr= seoCategoryCXFService.getFourNewInfo();
        SeoResponse seoResponse = JsonUtil.jsonToBean(jsonstr, SeoResponse.class);
        if (seoResponse.getResultCode() == 0) {
     	      list = JsonUtil.jsonToList(seoResponse.getData().toString(), CategoryInfo.class);
        } else {
            log.error(seoResponse.getDesc());
        }
        return list;
    }
    
    /**
     * 获取新元宝列表
     *
     * @param serviceId
     * @param request
     * @return
     */
	@RequestMapping(value = "/schemeList", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String schemeList(String serviceId, HttpServletRequest request) {

		JSONObject resultJson = new JSONObject();
		
        //当前页
        String currentPage = request.getParameter("currentPage");
        //每页大小
        String pageSize = request.getParameter("pageSize");
        //理财列表状态
        String status = request.getParameter("status");
        //封闭期数
        String closeTerm = request.getParameter("closeTerm");

        if (StringUtils.isBlank(currentPage)) {
            currentPage = "1";
        }
        if (StringUtils.isBlank(pageSize)) {
            pageSize = "20";
        }
        
        //构造JSON请求参数
        JSONObject json = new JSONObject();
        json.put("currentPage", Integer.parseInt(currentPage));
        json.put("pageSize", Integer.parseInt(pageSize));
        json.put("status", status);
        json.put("closeTerm", closeTerm);

        //调用远程查询新元宝接口
        //log.info("PlanCotroller schemeList ----> params:" + json.toJSONString());
        String resultStr = planCXFService.getSchemeList(json.toJSONString());
        //log.info("PlanCotroller schemeList ----> return:" + resultStr);
        WSPageResponse response = JsonUtil.jsonToBean(resultStr, WSPageResponse.class);
        List list = response.getResultList();

        resultJson.put("listData", list);
        resultJson.put("currentPage", Integer.parseInt(currentPage));
        resultJson.put("pageNums", response.getTotalPage());
        resultJson.put("currentDate", DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        return resultJson.toJSONString();
    }

    /**
     * 获取新元宝
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/detail/{planId}")
    @ResponseBody
    public String preferPlan(@PathVariable("planId") String planId, HttpServletRequest request) {
        JSONObject resultJson = new JSONObject();
        //log.info("查看新元宝，planId = " + planId);
        resultJson.put("planId", planId);
        JSONObject json = new JSONObject();
        json.put("schemeId", planId);
        String respStr = planCXFService.getSchemeInfo(json.toJSONString());
        WSModelResponse resp = JsonUtil.jsonToBean(respStr, WSModelResponse.class);
        if (resp.getResultCode() != 0) {
            log.info("新元宝【" + planId + "】不存在");
            resultJson.put("msg", "查询不到，新元宝【" + planId + "】");
            resultJson.put("bankUrl", "/index");
            return "model";
        }

        try {
            SchemeVO scheme = JSONObject.parseObject(resp.getData().toString(), SchemeVO.class);

            resultJson.put("scheme", scheme);
            resultJson.put("isHappyHour", scheme.getIsHappyHour());
            if (scheme != null) {
                //计算新元宝结束时间
                Date endTime = new Date();
                Calendar cal = Calendar.getInstance();
                Date opensaleEndDate = scheme.getOpensaleEnd();
                cal.setTime(opensaleEndDate);
                cal.add(Calendar.MONTH, scheme.getCloseterm().intValue());
                endTime = cal.getTime();
                //log.info("计划结束日期：" + endTime);
                resultJson.put("endTime", endTime);

                //推算计划当前所处状态
                PlanUtils planUtils = new PlanUtils();
                int status = planUtils.schemeStatus(scheme);
                //log.info("新元宝，当前状态为：" + status);
                resultJson.put("status", status);

                //计算计划进度
                BigDecimal progress = new BigDecimal(0);
                if (scheme.getAccount().subtract(scheme.getRemacount()).compareTo(new BigDecimal(0)) >= 0) {
                    progress = scheme.getAccount().subtract(scheme.getRemacount());
                    progress = progress.divide(scheme.getAccount(), 10, BigDecimal.ROUND_HALF_UP);
                    progress = progress.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                }
                resultJson.put("progress", progress.doubleValue());
            }

            //用户信息
            User user = (User) request.getSession().getAttribute("loginUser");
            //是否是登录状态
            if (null != user) {

                //查询新元宝账户信息
                /*JSONObject useMoneyParam = new JSONObject();
                useMoneyParam.put("userId", user.getUserId());
                useMoneyParam.put("accountType", AccountConsts.ACCOUNT_ONLINE_DEFAULT_TYPE_ACCOUNT);
                String useMoneyParamStr = planCXFService.getUserMoney(useMoneyParam.toJSONString());
                WSModelResponse useMoneyResponse = JsonUtil.jsonToBean(useMoneyParamStr, WSModelResponse.class);

                if (useMoneyResponse.getData() != null) {
                    JSONObject useMoney = JSONObject.parseObject(useMoneyResponse.getData().toString());
                    resultJson.put("useMoney", useMoney.getBigDecimal("useMoney"));
                } */

                //查询默认账户信息
                JSONObject accountJson = new JSONObject();
                accountJson.put("useId", user.getUserId());
                accountJson.put("accountType", AccountConsts.ACCOUNT_ONLINE_DEFAULT_TYPE_ACCOUNT);
                String resultAccountStr = accountQueryCXFService.selectAccountByIdAndType(accountJson.toJSONString());
                WSModelResponse accountResponse = JsonUtil.jsonToBean(resultAccountStr, WSModelResponse.class);
                if (accountResponse.getData() != null) {
                    JSONObject defaultAccount = JSONObject.parseObject(accountResponse.getData().toString());
                    resultJson.put("defaultAccount", defaultAccount);
                }

                //查询当前用户加入新元宝的相关信息
                JSONObject userSchemeInfoJson = new JSONObject();
                userSchemeInfoJson.put("schemeId", planId);
                userSchemeInfoJson.put("userId", user.getUserId());
                String userSchemeInfoStr = planCXFService.getUserSchemeInfo(userSchemeInfoJson.toJSONString());
                WSModelResponse userSchemeInfoResponse = JsonUtil.jsonToBean(userSchemeInfoStr, WSModelResponse.class);
                if (userSchemeInfoResponse.getData() != null) {
                    JSONObject userSchemeInfo = JSONObject.parseObject(userSchemeInfoResponse.getData().toString());
                    resultJson.put("userSchemeInfo", userSchemeInfo);
                }
            }


            //新手红包
            setRedPacketInfo(user, request, resultJson);


        } catch (Exception e) {
            log.error("查看新元宝异常：" + e.getMessage(), e);
        }

        resultJson.put("activity_url", Configuration.getInstance().getValue("activity_url"));
        return resultJson.toJSONString();
    }

    /**
     * 确认购买新元宝
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/requestQuick")
    @ResponseBody
    public String requestQuick(HttpServletRequest request, @RequestParam(required = true, value = "planId") String planId) {
        JSONObject resultJson = new JSONObject();
        //log.info("查看新元宝，planId = " + planId);
        resultJson.put("planId", planId);
        JSONObject json = new JSONObject();
        json.put("schemeId", planId);
        String respStr = planCXFService.getSchemeInfo(json.toJSONString());
        WSModelResponse resp = JsonUtil.jsonToBean(respStr, WSModelResponse.class);
        if (resp.getResultCode() != 0) {
            log.info("新元宝【" + planId + "】不存在");
            resultJson.put("msg", "查询不到，新元宝【" + planId + "】");
            resultJson.put("bankUrl", "/index");
            return "model";
        }

        try {
            SchemeVO scheme = JSONObject.parseObject(resp.getData().toString(), SchemeVO.class);

            resultJson.put("scheme", scheme);
            resultJson.put("isHappyHour", scheme.getIsHappyHour());
            if (scheme != null) {
                //计算新元宝结束时间
                Date endTime = new Date();
                Calendar cal = Calendar.getInstance();
                Date opensaleEndDate = scheme.getOpensaleEnd();
                cal.setTime(opensaleEndDate);
                cal.add(Calendar.MONTH, scheme.getCloseterm().intValue());
                endTime = cal.getTime();
                //log.info("计划结束日期：" + endTime);
                resultJson.put("endTime", endTime);

                //推算计划当前所处状态
                PlanUtils planUtils = new PlanUtils();
                int status = planUtils.schemeStatus(scheme);
                //log.info("新元宝，当前状态为：" + status);
                resultJson.put("status", status);

                //计算计划进度
                BigDecimal progress = new BigDecimal(0);
                if (scheme.getAccount().subtract(scheme.getRemacount()).compareTo(new BigDecimal(0)) >= 0) {
                    progress = scheme.getAccount().subtract(scheme.getRemacount());
                    progress = progress.divide(scheme.getAccount(), 10, BigDecimal.ROUND_HALF_UP);
                    progress = progress.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                }
                resultJson.put("progress", progress.doubleValue());
            }

            //用户信息
            User user = (User) request.getSession().getAttribute("loginUser");
            //是否是登录状态
            if (null != user) {

                //查询新元宝账户信息
                /*JSONObject useMoneyParam = new JSONObject();
                useMoneyParam.put("userId", user.getUserId());
                useMoneyParam.put("accountType", AccountConsts.ACCOUNT_ONLINE_DEFAULT_TYPE_ACCOUNT);
                String useMoneyParamStr = planCXFService.getUserMoney(useMoneyParam.toJSONString());
                WSModelResponse useMoneyResponse = JsonUtil.jsonToBean(useMoneyParamStr, WSModelResponse.class);

                if (useMoneyResponse.getData() != null) {
                    JSONObject useMoney = JSONObject.parseObject(useMoneyResponse.getData().toString());
                    resultJson.put("useMoney", useMoney.getBigDecimal("useMoney"));
                } */

                //查询默认账户信息
                JSONObject accountJson = new JSONObject();
                accountJson.put("useId", user.getUserId());
                accountJson.put("accountType", AccountConsts.ACCOUNT_ONLINE_DEFAULT_TYPE_ACCOUNT);
                String resultAccountStr = accountQueryCXFService.selectAccountByIdAndType(accountJson.toJSONString());
                WSModelResponse accountResponse = JsonUtil.jsonToBean(resultAccountStr, WSModelResponse.class);
                if (accountResponse.getData() != null) {
                    JSONObject defaultAccount = JSONObject.parseObject(accountResponse.getData().toString());
                    resultJson.put("defaultAccount", defaultAccount);
                }

                //查询当前用户加入新元宝的相关信息
                JSONObject userSchemeInfoJson = new JSONObject();
                userSchemeInfoJson.put("schemeId", planId);
                userSchemeInfoJson.put("userId", user.getUserId());
                String userSchemeInfoStr = planCXFService.getUserSchemeInfo(userSchemeInfoJson.toJSONString());
                WSModelResponse userSchemeInfoResponse = JsonUtil.jsonToBean(userSchemeInfoStr, WSModelResponse.class);
                if (userSchemeInfoResponse.getData() != null) {
                    JSONObject userSchemeInfo = JSONObject.parseObject(userSchemeInfoResponse.getData().toString());
                    resultJson.put("userSchemeInfo", userSchemeInfo);
                }
            }


            //新手红包
            setRedPacketInfo(user, request, resultJson);


        } catch (Exception e) {
            log.error("查看新元宝异常：" + e.getMessage(), e);
        }

        return resultJson.toJSONString();
    }

    /**
     * 获取新元宝信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/schemeInfo", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String schemeInfo(String serviceId, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        json.put("schemeId", serviceId);
        String respStr = planCXFService.getSchemeInfo(json.toJSONString());
        WSModelResponse resp = JsonUtil.jsonToBean(respStr, WSModelResponse.class);

        return resp.getData().toString();
    }

    /**
     * 获取新元宝信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/userSchemeList", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String userSchemeList(String schemeId, HttpServletRequest request) {
        //当前页
        String currentPage = request.getParameter("currentPage");
        currentPage = currentPage == null ? "1" : currentPage;

        //每页大小
        String pageSize = request.getParameter("pageSize");
        pageSize = pageSize == null ? "10" : pageSize;

        //构造JSON请求参数
        JSONObject json = new JSONObject();
        json.put("currentPage", currentPage);
        json.put("pageSize", pageSize);
        json.put("schemeId", schemeId);
        json.put("queryType", request.getParameter("queryType"));

        //调用远程查询新元宝接口
        String resultStr = planCXFService.getUserSchemeList(json.toJSONString());
        WSPageResponse response = JsonUtil.jsonToBean(resultStr, WSPageResponse.class);
        List<JSONObject> dataList = response.getResultList();
        JSONObject resultJson = new JSONObject();
        if (dataList != null) {
            resultJson.put("resultList", dataList);
        }
        return resultJson.toJSONString();
    }


    /**
     * 加入计划
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/buyScheme", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String buyScheme(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            //判断当前token是否有效
            if (!TokenUtil.validToken(request)) {
                result.put("result", Constant.TOKEN_INVALID_ERROR);
                result.put("desc", "页面已过期，请重新尝试");
                return result.toString();
            }
            // 销毁token
            TokenUtil.removeToken(request);
            //用户信息
            User user = (User) request.getSession().getAttribute("loginUser");
            if (user == null) {
                log.info("当前加入计划的用户，未登录，");
                result.put("desc", "您未登录，不能加入新元宝");
                result.put("result", 200);
                return result.toString();
            }

            //参数验证
            String productId = request.getParameter("schemeId");
            if(productId == null || "".equalsIgnoreCase(productId)) {
                result.put("code",401);
                result.put("msg","pId为空");
                return result.toJSONString();
            }

            String productCategory = "0";//0封装化产品，1散标，2债权转让
            int productType = 98;//98代表新元宝
            String tenderAmount = request.getParameter("account");
            BigDecimal tAmount = new BigDecimal(tenderAmount);
            String redEnvelopeCode = request.getParameter("redCodes");

            String payPwd = request.getParameter("payPwd");
            if(payPwd == null || "".equalsIgnoreCase(payPwd)) {
                result.put("code",403);
                result.put("msg","payPwd为空");
                return result.toJSONString();
            }

            //验证支付密码
            /*JSONObject pswJson = new JSONObject();
            pswJson.put("userId", user.getUserId());
            pswJson.put("password", EscapeCode.Encryption(payPwd));
            pswJson.put("ip", HttpTookit.getRealIpAddr(request));
            String browser = request.getHeader("User-Agent");
            browser = browser.length() > 200 ? browser.substring(0,200):browser;
            pswJson.put("browser", browser);
            log.info("plan buyScheme check paypw req :" + pswJson.toJSONString());
            String resultPsw = userCXFService.checkPayPassword(pswJson.toString());
            log.info("plan buyScheme check paypw resp:" + resultPsw);
            WSModelResponse pswResp = JsonUtil.jsonToBean(resultPsw, WSModelResponse.class);
            if (pswResp.getResultCode() == -2) {
                result.put("desc", "支付密码错误，请重新输入");
                result.put("paypwResultCode", pswResp.getResultCode());
                return result.toString();
            } else if (pswResp.getResultCode() == 220) {
                result.put("desc", "支付密码为空，请去设置您的支付密码");
                result.put("paypwResultCode", pswResp.getResultCode());
                return result.toString();
            } else if (pswResp.getResultCode() == 230) {
                result.put("desc", "支付密码与登录密码一致，请重设您的支付密码");
                result.put("paypwResultCode", pswResp.getResultCode());
                return result.toString();
            } else if (pswResp.getResultCode() == -1) {
                result.put("desc", "验证支付密码异常，请重新尝试或联系客服");
                result.put("paypwResultCode", pswResp.getResultCode());
                return result.toString();
            }*/

            String token = HttpTookit.getCookieValue(request,Constant.USERTOKEN);
            String userAgent = HttpTookit.getUserAgent(request);
            //验证支付密码(新)
            JSONObject res = userService.validatePayPwdByToken(token,payPwd,userAgent);
            log.info("------------验证支付密码返回结果：" + res);
            JSONObject resData = res.getJSONObject("data");
            if(Constant.SUCCESS.equals(res.getLong("code")) && 0 != resData.getIntValue("code")) {
                result.put("paypwResultCode",resData.getString("code"));
                result.put("desc",resData.getString("message"));
                return result.toJSONString();
            }
            result.put("activity_url",Configuration.getInstance().getValue("activity_url"));
            String couponId = request.getParameter("couponId");
            JSONObject resultStr = productService.investOrder(token,productCategory,productId,productType,redEnvelopeCode,tAmount,userAgent,couponId);
            log.debug("XYB 新购买逻辑 result返回 ：" + resultStr.toString() + " user.getUserId()：" + user.getUserId() + " amount：" + tAmount);
            if (Constant.SUCCESS.equals(resultStr.getLong("code"))) {
                result = JSONObject.parseObject(resultStr.get("data").toString());
            } else {
                result.put("code", resultStr.getString("code"));
            }

            /*
            JSONObject schemeJson = new JSONObject();
            //新元宝编号
            String schemeId = request.getParameter("schemeId");
            schemeJson.put("schemeId", schemeId);
            schemeJson.put("userId", user.getUserId());

            //加入金额
            String account = request.getParameter("account");
            schemeJson.put("account", account);

            schemeJson.put("addip", HttpTookit.getRealIpAddr(request));
            //终端信息
            /* JSONObject terminalVer = new JSONObject();
            terminalVer.put("user-agent", request.getHeader("user-agent"));
            terminalVer.put("type", "webapp");  *//*
            schemeJson.put("terminalver", HttpTookit.getRequestTerminal(request));

            // 渠道。1：PC  2：mobile，3：app，4：webapp
            schemeJson.put("channel", "4");

            //红包
            List<String> redCodes = new ArrayList<String>();
            redCodes.add(request.getParameter("redCodes"));
            schemeJson.put("redCodes", redCodes);

            //收益处理方式
            String collectiontype = request.getParameter("collectiontype");
            schemeJson.put("collectiontype", collectiontype);
            schemeJson.put("operationType", request.getParameter("operationType"));
            schemeJson.put("ip", HttpTookit.getRealIpAddr(request));
            log.info("plan buyScheme  req:" + schemeJson.toJSONString());
            String resultBuyScheme = planCXFService.buyScheme(schemeJson.toJSONString());
            log.info("plan buyScheme resp :" + resultBuyScheme);
            WSModelResponse resp = JsonUtil.jsonToBean(resultBuyScheme, WSModelResponse.class);
            log.info(resp.toJson());
            result.put("data", resp.getData());
            result.put("desc", resp.getDesc());
            result.put("result", resp.getResultCode());
            result.put("tenderTime", new Date().getTime());

            result.put("activity_url",Configuration.getInstance().getValue("activity_url"));*/
        } catch (Exception e) {
            log.error("plan buyScheme error : " + e.getMessage(), e);
            result.put("desc", "申请加入新元宝异常，请重新重试或者联系客服");
            result.put("result", 404);
        }
        log.info("plan buyScheme result : " + result.toJSONString());
        return result.toJSONString();
    }

    /**
     * 加载我的新元宝
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/myXplan")
    public String myXplan(HttpServletRequest request) {

        return "/plan/myXplan";
    }

    /**
     * 查询我所参与的新元宝
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/mySchemeList", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String mySchemeList(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        //用户信息
        User user = (User) request.getSession().getAttribute("loginUser");
        if (user == null) {
            log.info("当前加入计划的用户，未登录，");
            result.put("desc", "您未登录，不能加入新元宝");
            result.put("result", 200);
            return result.toString();
        }
        //查询我所参与的新元宝
        JSONObject mySchemeJson = new JSONObject();
        mySchemeJson.put("userId", user.getUserId());
        String mySchemeListStr = planCXFService.getMySchemeList(mySchemeJson.toJSONString());
        WSPageResponse resp = JsonUtil.jsonToBean(mySchemeListStr, WSPageResponse.class);

        List<Map<String, Object>> schemeList = resp.getResultList();
        if (schemeList != null && schemeList.size() > 0) {
            for (Map<String, Object> map : schemeList) {
                // 计算新元宝结束时间
                Date endTime;
                Calendar cal = Calendar.getInstance();
                Date opensaleEndDate = new Date();
                try {
                    opensaleEndDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(map.get("OPENSALEEND").toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                cal.setTime(opensaleEndDate);
                cal.add(Calendar.MONTH, Integer.parseInt(map.get("CLOSETERM").toString()));
                endTime = cal.getTime();
                map.put("endTime", endTime);

                // 查询当前用户加入新元宝的相关信息
                JSONObject userSchemeInfoJson = new JSONObject();
                userSchemeInfoJson.put("schemeId", map.get("SCHEMEID"));
                userSchemeInfoJson.put("userId", user.getUserId());
                //log.info("xplan mySchemeList req param ="+userSchemeInfoJson.toJSONString());
                String userSchemeInfoStr = planCXFService.getUserSchemeInfo(userSchemeInfoJson.toJSONString());
                //log.info("xplan mySchemeList resp ="+userSchemeInfoStr);
                WSModelResponse userSchemeInfoResponse = JsonUtil.jsonToBean(userSchemeInfoStr, WSModelResponse.class);
                if (userSchemeInfoResponse.getData() != null) {
                    JSONObject userSchemeInfo = JSONObject.parseObject(userSchemeInfoResponse.getData().toString());
                    map.put("userSchemeInfo", userSchemeInfo);
                }
            }
        }

        result.put("resultList", schemeList);
        result.put("pageNums", resp.getTotalPage());

        //查询默认账户信息
        JSONObject accountJson = new JSONObject();
        accountJson.put("useId", user.getUserId());
        accountJson.put("accountType", AccountConsts.ACCOUNT_ONLINE_DEFAULT_TYPE_ACCOUNT);
        String resultAccountStr = accountQueryCXFService.selectAccountByIdAndType(accountJson.toJSONString());
        WSModelResponse accountResponse = JsonUtil.jsonToBean(resultAccountStr, WSModelResponse.class);
        if (accountResponse.getData() != null) {
            JSONObject defaultAccount = JSONObject.parseObject(accountResponse.getData().toString());
            result.put("defaultAccount", defaultAccount);
        }
        return result.toJSONString();
    }

    /**
     * 转账
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/transferAccounts", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String transferAccounts(HttpServletRequest request) {
        JSONObject resultJson = new JSONObject();
        //用户信息
        User user = (User) request.getSession().getAttribute("loginUser");
        if (user == null) {
            log.info("当前加入计划的用户，未登录，");
            resultJson.put("resultCode", 200);
            resultJson.put("message", "您未登录，不能加入新元宝");
            return resultJson.toString();
        }

        //验证支付密码
        String pwd = request.getParameter("pwd");
        if (!StringUtils.isNotBlank(pwd)) {
            resultJson.put("resultCode", 201);
            resultJson.put("message", "支付密码不能为空");
            return resultJson.toString();
        }
        //校验交易密码
        try {
            //验证支付密码
            JSONObject pswJson = new JSONObject();
            pswJson.put("userId", user.getUserId());
            pswJson.put("password", EscapeCode.Encryption(pwd));
            pswJson.put("ip", HttpTookit.getRealIpAddr(request));
            String browser = request.getHeader("User-Agent");
            browser = browser.length() > 200 ? browser.substring(0,200):browser;
            pswJson.put("browser", browser);
            String resultPsw = userCXFService.checkPayPassword(pswJson.toString());
            WSModelResponse pswResp = JsonUtil.jsonToBean(resultPsw, WSModelResponse.class);
            if (pswResp.getResultCode() == -2) {
                resultJson.put("desc", "支付密码错误，请重新输入");
                resultJson.put("result", pswResp.getResultCode());
                return resultJson.toString();
            } else if (pswResp.getResultCode() == 220) {
                resultJson.put("desc", "支付密码为空，请去设置您的支付密码");
                resultJson.put("result", pswResp.getResultCode());
                return resultJson.toString();
            } else if (pswResp.getResultCode() == 230) {
                resultJson.put("desc", "支付密码与登录密码一致，请重设您的支付密码");
                resultJson.put("result", pswResp.getResultCode());
                return resultJson.toString();
            } else if (pswResp.getResultCode() == -1) {
                resultJson.put("desc", "验证支付密码异常，请重新尝试或联系客服");
                resultJson.put("result", pswResp.getResultCode());
                return resultJson.toString();
            }
        } catch (Exception e) {
            log.error("支付密码错误", e);
            resultJson.put("resultCode", 203);
            resultJson.put("message", "支付密码错误！");
            return resultJson.toString();
        }

        String tradeMoney = request.getParameter("tradeMoney");
        String tradeType = request.getParameter("tradeType");
        //调用交易中心转账接口
        JSONObject tradeJson = new JSONObject();
        tradeJson.put("userId", user.getUserId());
        tradeJson.put("busiId", 0);
        tradeJson.put("operateMoney", tradeMoney);
        if ("out".equals(tradeType)) {
            log.info("从新元宝账户转账到默认账户，金额=" + tradeMoney);
            tradeJson.put("originalPcode", AccountConsts.ACCOUNT_X_PLAN_ACCOUNT);
            tradeJson.put("targetPcode", AccountConsts.ACCOUNT_ONLINE_DEFAULT_TYPE_ACCOUNT);
        } else {
            log.info("从默认账户转账到新元宝账户，金额=" + tradeMoney);
            tradeJson.put("originalPcode", AccountConsts.ACCOUNT_ONLINE_DEFAULT_TYPE_ACCOUNT);
            tradeJson.put("targetPcode", AccountConsts.ACCOUNT_X_PLAN_ACCOUNT);
        }
        tradeJson.put("ip", HttpTookit.getRealIpAddr(request));
        String respStr = accountTradeCXFService.transferToXplanAccount(tradeJson.toJSONString());
        WSResponse resp = JsonUtil.jsonToBean(respStr, WSResponse.class);
        if (resp.getResultCode() == AppConsts.WS_RETURN_SUCC) {
            log.info("转账成功");
            resultJson.put("resultCode", resp.getResultCode());
            resultJson.put("message", "转账成功");
        } else {
            log.info("转账失败");
            resultJson.put("resultCode", -1);
            resultJson.put("message", "转账失败，请重新尝试或者联系客服");
        }
        return resultJson.toJSONString();
    }


    /**
     * 查看用户加入新元宝详情
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/myXplanDetail/{planId}")
    public String myXplanDetail(@PathVariable("planId") String planId, HttpServletRequest request) {
        request.setAttribute("planId", planId);
        return "/plan/myXplanDetail";
    }

    /**
     * 加载用户加入新元宝详情数据
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/loadMyXplanDetail", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String loadMyXplanDetail(HttpServletRequest request) {
        JSONObject resultJson = new JSONObject();

        //用户信息
        User user = (User) request.getSession().getAttribute("loginUser");
        if (user == null) {
            log.info("当前加入计划的用户，未登录，");
            resultJson.put("desc", "您未登录，不能加入新元宝");
            resultJson.put("result", 200);
            return resultJson.toString();
        }

        //查询新元宝信息
        String planId = request.getParameter("planId");
        JSONObject json = new JSONObject();
        json.put("schemeId", planId);
        String respStr = planCXFService.getSchemeInfo(json.toJSONString());
        WSModelResponse resp = JsonUtil.jsonToBean(respStr, WSModelResponse.class);
        if (resp.getResultCode() == 0) {
            Scheme scheme = JSONObject.parseObject(resp.getData().toString(), Scheme.class);
            //推算计划所处阶段
            PlanUtils utils = new PlanUtils();
            resultJson.put("scheme", scheme);
            resultJson.put("schemeStatus", utils.schemeStatus(scheme));
        }

        //查询当前用户加入新元宝的相关信息
        JSONObject userSchemeInfoJson = new JSONObject();
        userSchemeInfoJson.put("schemeId", planId);
        userSchemeInfoJson.put("userId", user.getUserId());
        String userSchemeInfoStr = planCXFService.getUserSchemeInfo(userSchemeInfoJson.toJSONString());
        WSModelResponse userSchemeInfoResponse = JsonUtil.jsonToBean(userSchemeInfoStr, WSModelResponse.class);
        if (userSchemeInfoResponse.getData() != null) {
            JSONObject userSchemeInfo = JSONObject.parseObject(userSchemeInfoResponse.getData().toString());
            resultJson.put("userSchemeInfo", userSchemeInfo);
        }

        return resultJson.toJSONString();
    }

    /**
     * 跳转
     *
     * @param request
     * @return
     */
    @RequestMapping("/result")
    public String toSuccessPage(HttpServletRequest request) {
        String msg = request.getParameter("msgType");
        String schemeId = request.getParameter("schemeId");
        request.setAttribute("messageType", "success");
        request.setAttribute("success_content", "加入新元宝成功");
        request.setAttribute("returnUrl", "../xplan/detail/" + schemeId + ".html");
        request.setAttribute("returnName", "返回查看新元宝");
        return "plan/model";
    }

    /**
     * 用户加入新元宝，持有债权
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/schemeByBorrowList", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String schemeByBorrowList(HttpServletRequest request) {
        JSONObject resultJson = new JSONObject();
        //用户信息
        User user = (User) request.getSession().getAttribute("loginUser");
        if (user == null) {
            log.info("当前加入计划的用户，未登录，");
            resultJson.put("desc", "您未登录，请先登录");
            resultJson.put("result", 200);
            return resultJson.toString();
        }
        JSONObject paramJson = new JSONObject();
        String schemeId = request.getParameter("schemeId");
        paramJson.put("schemeId", schemeId);
        paramJson.put("userId", user.getUserId());
        String currentPage = request.getParameter("currentPage");
        paramJson.put("currentPage", currentPage);
        String pageSize = request.getParameter("pageSize");
        paramJson.put("pageSize", pageSize);
        String schemeByBorrowStr = planCXFService.getBorrowListOfMyScheme(paramJson.toJSONString());
        WSPageResponse pageResp = JsonUtil.jsonToBean(schemeByBorrowStr, WSPageResponse.class);
        resultJson.put("schemeByBorrowList", pageResp.getResultList());
        return resultJson.toJSONString();
    }

    /**
     * 用户加入新元宝理财流水
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/schemeByAccounLogs", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String schemeByAccounLogs(HttpServletRequest request) {
        JSONObject resultJson = new JSONObject();
        //用户信息
        User user = (User) request.getSession().getAttribute("loginUser");
        if (user == null) {
            log.info("当前加入计划的用户，未登录，");
            resultJson.put("desc", "您未登录，请先登录");
            resultJson.put("result", 200);
            return resultJson.toString();
        }
        JSONObject paramJson = new JSONObject();
        String schemeId = request.getParameter("schemeId");
        paramJson.put("schemeId", schemeId);
        paramJson.put("userId", user.getUserId());
        String currentPage = request.getParameter("currentPage");
        paramJson.put("currentPage", currentPage);
        String pageSize = request.getParameter("pageSize");
        paramJson.put("pageSize", pageSize);
        String schemeByAccounLogsStr = planCXFService.getAccounLogsOfMyScheme(paramJson.toJSONString());
        WSPageResponse pageResp = JsonUtil.jsonToBean(schemeByAccounLogsStr, WSPageResponse.class);
        resultJson.put("schemeByAccounLogs", pageResp.getResultList());
        return resultJson.toJSONString();
    }


    /**
     * 新元宝协议
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/agreement/{schemeId}", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String agreement(@PathVariable("schemeId") String schemeId, HttpServletRequest request) {
        JSONObject resultJson = new JSONObject();
        String noShow = request.getParameter("noShow");
        if (noShow != null && "true".equalsIgnoreCase(noShow)) {
            //用户信息
            User user = (User) request.getSession().getAttribute("loginUser");
            if (user != null) {
                //查询当前用户加入新元宝的相关信息
                JSONObject userSchemeInfoJson = new JSONObject();
                userSchemeInfoJson.put("schemeId", schemeId);
                userSchemeInfoJson.put("userId", user.getUserId());
                String userSchemeInfoStr = planCXFService.getUserSchemeInfo(userSchemeInfoJson.toJSONString());
                WSModelResponse userSchemeInfoResponse = JsonUtil.jsonToBean(userSchemeInfoStr, WSModelResponse.class);
                if (userSchemeInfoResponse.getData() != null) {
                    JSONObject userSchemeInfo = JSONObject.parseObject(userSchemeInfoResponse.getData().toString());
                    resultJson.put("userSchemeInfo", userSchemeInfo);

                     //投资金额
                    JSONObject agreementJson = new JSONObject();
                    agreementJson.put("userId",user.getUserId());
                    agreementJson.put("schemeId", schemeId);
                    log.info("queryAgreementVO req = " + agreementJson.toJSONString());
                    String stragreement = planCXFService.queryAgreementVO(agreementJson.toJSONString());
                    log.info("queryAgreementVO resp = " + stragreement);
                    resultJson.put("stragreement",JSONObject.parseObject(stragreement));
                }

                resultJson.put("user", user);

                //查询实名认证信息
                JSONObject params = new JSONObject();
                params.put("userId", user.getUserId());
                String approStr = approQueryCXFService.queryRealNameApproByUserId(params.toJSONString());
                PersonResponse resp = JsonUtil.jsonToBean(approStr, PersonResponse.class);
                if (resp.getData() != null) {
                    JSONObject realName = JSONObject.parseObject(resp.getData().toString());
                    resultJson.put("realName", realName);
                }
            }
        }

        //查询新元宝信息
        JSONObject json = new JSONObject();
        json.put("schemeId", schemeId);
        String respStr = planCXFService.getSchemeInfo(json.toJSONString());
        WSModelResponse resp = JsonUtil.jsonToBean(respStr, WSModelResponse.class);
        if (resp.getResultCode() == 0) {
            JSONObject scheme = JSONObject.parseObject(resp.getData().toString(), JSONObject.class);
            //推算计划所处阶段
            resultJson.put("scheme", scheme);

            //计算新元宝结束时间
            Date endTime = new Date();
            try {
                if (scheme != null) {
                    Calendar cal = Calendar.getInstance();
                    Date opensaleEndDate = scheme.getDate("opensaleEnd");
                    cal.setTime(opensaleEndDate);
                    cal.add(Calendar.MONTH, scheme.getIntValue("closeterm"));
                    endTime = cal.getTime();
                }
            } catch (Exception e) {
                log.error("计算新元宝结束时间异常", e);
                e.printStackTrace();
            }
            log.info("计划结束日期：" + endTime);
            resultJson.put("endTime", endTime);
        }
        return resultJson.toJSONString();
    }

    /**
     * 提前退出新元宝
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/quitScheme", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String quitScheme(HttpServletRequest request) {
        JSONObject result = new JSONObject();

        User user = (User) request.getSession().getAttribute("loginUser");
        if (user == null) {
            result.put("resultCode", -500);
            result.put("msg", "会话超时，请重新登录");
            return result.toJSONString();
        }

        try {

            //交易密码
            String payPwd = request.getParameter("paypwd");
            if (payPwd == null || payPwd.equals("")) {
                result.put("resultCode", 100);
                result.put("msg", "请填写支付密码!");
                return result.toJSONString();
            }

            //校验交易密码
            /*JSONObject jsonObj = new JSONObject();
            jsonObj.put("userId", user.getUserId());
            jsonObj.put("password", EscapeCode.Encryption(payPwd));
            jsonObj.put("ip", HttpUtil.getRealIpAddr(request));
            String browser = request.getHeader("User-Agent");
            browser = browser.length() > 200 ? browser.substring(0,200):browser;
            jsonObj.put("browser", browser);
            log.info("checkPayPwd req param : " + jsonObj.toJSONString());
            String resultStr = userCXFService.checkPayPassword(JsonUtil.beanToJson(jsonObj));
            log.info("checkPayPwd resp :" + resultStr);
            UserResponse userResponse = JsonUtil.jsonToBean(resultStr, UserResponse.class);

            if (userResponse != null) {
                if (userResponse.getResultCode() != 0) {
                    result.put("resultCode", userResponse.getResultCode());
                    result.put("msg", "");
                    return result.toJSONString();
                }
            }*/
            String token = HttpTookit.getCookieValue(request,Constant.USERTOKEN);
            String userAgent = HttpTookit.getUserAgent(request);
            JSONObject res = userService.validatePayPwdByToken(token,payPwd,userAgent);
            log.info("------------验证支付密码返回结果：" + res);
            JSONObject resData = res.getJSONObject("data");
            if(Constant.SUCCESS.equals(res.getLong("code")) && 0 != resData.getIntValue("code")) {
                result.put("resultCode",resData.getString("code"));
                result.put("msg",resData.getString("message"));
                return result.toJSONString();
            }

            String schemeId = request.getParameter("schemeId");
            String joinId = request.getParameter("joinid");
            /*JSONObject param = new JSONObject();
            param.put("schemeId", schemeId);
            param.put("userId", user.getUserId());
            param.put("ip", HttpTookit.getRealIpAddr(request));
            log.info("quit scheme req param :" + param.toJSONString());
            String respStr = planCXFService.quitScheme(param.toJSONString());*/
            result = productService.quit(token,joinId,schemeId,98,new BigDecimal(11.11).setScale(3,BigDecimal.ROUND_HALF_UP),"remark",userAgent);//目前此方法的参数productId，remark，quitAmount对新元宝没什么意义
            log.info("quit scheme resp :" + result);
            WSResponse resp = JsonUtil.jsonToBean(result.getString("data"), WSResponse.class);
            /*WSResponse resp = JsonUtil.jsonToBean(respStr, WSResponse.class);
            if (resp.getResultCode() == AppConsts.WS_RETURN_SUCC) {*/
            if (Constant.SUCCESS.equals(result.getLong("code")) && resp.getResultCode() == 0) {
                result.put("resultCode", 0);
                result.put("msg", "成功");
                log.info("quit ok");

                /******************新元宝GA部署所需参数start******************/
                JSONObject gaJson = new JSONObject();
                gaJson.put("schemeid", joinId);//schemeId);
                gaJson.put("userId", user.getUserId());
                String gaJsonStr = planCXFService.getPlanGAData(gaJson.toString());
                WSModelResponse gaRes = JsonUtil.jsonToBean(gaJsonStr, WSModelResponse.class);
                if(gaRes.getData()!=null) {
                    String dataStr = String.valueOf(gaRes.getData());
                    log.info("新元宝ga布码结果：" + dataStr);
                    Map map = JsonUtil.jsonToBean(dataStr, Map.class);
                    String serviceNum ="";
                    if(null != map){
                        result.put("productId",map.get("SCHEMEID"));
                        result.put("apr",map.get("MINAPR"));
                        result.put("terms",map.get("CLOSETERM"));
                        result.put("price",map.get("ACCOUNT"));
                        result.put("tradeid",map.get("USERSCHEMEID"));
                        if (null != map.get("SERVICENUM") && !"".equals(map.get("SERVICENUM"))) {
                            serviceNum = map.get("SERVICENUM").toString();
                        } else {
                            serviceNum = "nonFD";
                        }
                        result.put("servicenum",serviceNum);
                    }
                }
                log.info("新元宝退出结果：" + result.toJSONString());
                /******************新元宝GA部署所需参数end******************/

            } else {
                result.put("resultCode", resp.getResultCode());
                result.put("msg", resp.getDesc());
                log.info("--------------------- quit fail ----------------- schemaId:{},userId:{}",joinId,user.getUserId());
                log.info("quit fail");
            }
        } catch (Exception e) {
            log.error("退出新元宝失败", e);
            result.put("resultCode", 300);
            result.put("msg", "退出新元宝失败");
            e.printStackTrace();
        }
        return result.toJSONString();
    }


    private void setRedPacketInfo(User user, HttpServletRequest request, JSONObject resultJson) {
        if (user == null) {
            return;
        }
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("pageIndex", 1);
        jsonObject1.put("pageSize", 999);
        jsonObject1.put("userId", ( user).getUserId());
        jsonObject1.put("status", 1);
        jsonObject1.put("btype", 0);
        jsonObject1.put("prodType", RedEnvelopeLimitTypeConsts.XPLAN_USED_PRODUCT_TYPE);
        /*JSONObject terminalVer = new JSONObject();
        terminalVer.put("user-agent", request.getHeader("user-agent"));
        terminalVer.put("type", "webapp");   */
        jsonObject1.put("terminalVer", HttpTookit.getRequestTerminal(request));
        jsonArray.add(jsonObject1);
        String str = redpacketCXFService.getRedpacketListByUseCodition(jsonArray.toString());

        RpWsResponse res = JsonUtil.jsonToBean(str, RpWsResponse.class);
        if (res.getResultCode() < 0) {
            log.error("红包获取失败：" + res.getDesc());
        } else {
            if (res.getData() != null) {
                List<RedenvelopeRecord> list = JsonUtil.jsonToList(res.getData().toString(), RedenvelopeRecord.class);
                resultJson.put("redpacketList", list);
            }
        }
    }
    /**
     * 首页展示新元宝
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/indexXyb", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String indexXyb(HttpServletRequest request) {
        //构造JSON请求参数
        JSONObject json = new JSONObject();
        json.put("currentPage", 1);
        json.put("pageSize", 6);
        JSONObject resultJson = new JSONObject();
        //调用远程查询新元宝接口
        try{
            String resultStr = planCXFService.getSchemeList(json.toJSONString());
            WSPageResponse response = JsonUtil.jsonToBean(resultStr, WSPageResponse.class);
            List list = response.getResultList();
            //根据新元宝类型倒序排序
            Collections.sort(list, new Comparator() {
                @Override
                public int compare(Object o1, Object o2) {
                    int type1 = Integer.parseInt(JSON.parseObject(o1.toString()).get("TYPE").toString());
                    int type2 = Integer.parseInt(JSON.parseObject(o2.toString()).get("TYPE").toString());
                    if (type1 > type2) {
                        return -1;
                    } else if (type1 == type2) {
                        return 0;
                    }
                    return 1;
                }
            });
            int schemeType = 3;
            boolean boolType = true;
            for (int i = 0; i < list.size(); i++) {
                json = JSON.parseObject(list.get(i).toString());
                int type = Integer.parseInt(json.get("TYPE").toString());
                int schemeStatus = Integer.parseInt(json.get("schemeStatus").toString());
                int status = Integer.parseInt(json.get("STATUS").toString());
                if(schemeStatus < 4 && status != 5){
                    schemeType = type;
                    boolType = false;
                    break;
                }
            }
            int schemeSta = 4;//开放期新元宝
            if(boolType){
                schemeSta = 5;//显示锁定期新元宝
            }
            JSONObject result = new JSONObject();
            result = indexShow_scheme(schemeType,list,schemeSta);
            resultJson.put("scheme", result.get("scheme"));
            resultJson.put("schemeStatus", result.get("schemeStatus"));
            return resultJson.toString();
        }catch (Exception e){
            log.error("indexXyb ERROR:"+e.getMessage(),e);
        }
        return resultJson.toString();
    }

    /**
     * 从新元宝list里面遍历出一条最新数据显示
     * @param type
     * @param list
     * @return
     */
    public JSONObject indexShow_scheme(int type,List list,int schemeSta){
        JSONObject jsonOBJ = new JSONObject();
        Scheme scheme = new Scheme();
        for (int i = 0; i < list.size(); i++) {
            JSONObject json = JSON.parseObject(list.get(i).toString());
            int type1 = Integer.parseInt(json.get("TYPE").toString());
            int schemeStatus = Integer.parseInt(json.get("schemeStatus").toString());
            int status = Integer.parseInt(json.get("STATUS").toString());
            if(schemeStatus < schemeSta && status != 5){
                if (type1 == type) {
                    Long closeterm = (long) Integer.parseInt(json.get("CLOSETERM").toString());
                    scheme.setMaxApr(new BigDecimal(json.get("MAXAPR").toString()));
                    scheme.setType(type1);
                    scheme.setPname(json.get("PNAME").toString());
                    scheme.setCloseterm(closeterm);
                    scheme.setRemacount(BigDecimal.valueOf(Long.parseLong(json.get("REMACOUNT").toString())));
                    scheme.setSchemeId(json.getString("SCHEMEID"));
                    jsonOBJ.put("scheme",scheme);
                    jsonOBJ.put("schemeStatus",schemeStatus);
                    return jsonOBJ;
                }
            }
        }
        return jsonOBJ;
    }
    
    
    /**
     * 获取新首页展示-->12个月的新元宝
     *
     * @param serviceId
     * @param request
     * @return
     */
	@RequestMapping(value = "/getSchemeForNewIndex", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getSchemeForNewIndex(String serviceId, HttpServletRequest request) {
		JSONObject resultJson = new JSONObject();
		try{
	        //当前页
	        String currentPage = request.getParameter("currentPage");
	        //每页大小
	        String pageSize = request.getParameter("pageSize");
	        //封闭期数
	        //String closeTerm = request.getParameter("closeTerm");
	
	        if (StringUtils.isBlank(currentPage)) {
	            currentPage = "1";
	        }
	        if (StringUtils.isBlank(pageSize)) {
	            pageSize = "10"; //支持十天数据
	        }
	        
	        //构造JSON请求参数
	        JSONObject paramJson = new JSONObject();
	        paramJson.put("currentPage", Integer.parseInt(currentPage));
	        paramJson.put("pageSize", Integer.parseInt(pageSize));
	        
			String[] closeTerms = {"1","3","6","12"};
			List list = null;
			JSONObject resultScheme = new JSONObject();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			boolean findFlag = false;
			for (int i = 0; i < closeTerms.length; i++) {
				findFlag = false;
				list = null;
				paramJson.put("closeTerm", closeTerms[i]);
				//调用远程查询新元宝接口
			   // log.info("PlanCotroller getSchemeForNewIndex ----> params:" + paramJson.toJSONString());
			    String resultStr = planCXFService.getSchemeList(paramJson.toJSONString());
			   // log.info("PlanCotroller getSchemeForNewIndex ----> return:" + resultStr);
			    WSPageResponse response = JsonUtil.jsonToBean(resultStr, WSPageResponse.class);
			    list = response.getResultList();
			    if (list.size() <= 0) {
			    	continue;
				}else{
					resultScheme = JSONObject.parseObject(String.valueOf(list.get(0)));
					
					for (int j = 0; j < list.size(); j++) {
						JSONObject scheme = JSONObject.parseObject(String.valueOf(list.get(j)));
						String date = String.valueOf(scheme.get("OPENSALEBEGIN")).substring(0,10);
						String today = sdf.format(new Date());
						BigDecimal remaccount = scheme.getBigDecimal("REMACOUNT");
						int status = scheme.getIntValue("schemeStatus");// 0:待发布,1:预定期,2:支付期,3:开放期,4:锁定期,5:退出,6:撤销,7:等待公开加入
						if (today.equals(date) && remaccount.compareTo(BigDecimal.ZERO) > 0 && (status == 0 || status == 1 || status == 2 || status == 3 || status == 7) ) {
							resultScheme = scheme;
							findFlag = true;
						}else{
							continue;
						}
					}
					
					if (findFlag == true) {
						break;
					}
				}
			}
	        
	        if (resultScheme == null) {
	        	throw new Exception("can't find any Scheme to display...");
	        }
	        resultJson.put("resultCode", "0");
	        resultJson.put("schemeInfo", resultScheme);
	        resultJson.put("currentDate", DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
		}catch(Exception e){
    		log.error("PlanCotroller getSchemeForNewIndex ----> arise exception:" , e);
    		//返回页面
	    	resultJson.put("resultCode", "-1");
	    	resultJson.put("desc", "操作失败，请稍后重试...");
    	}
		//log.info("PlanCotroller getSchemeForNewIndex ----> return to page:" + resultJson.toJSONString());
        return resultJson.toJSONString();
    }
	/**
	 * 获取指定期限最新一期的新元宝
	 *
	 * @param serviceId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getLatestSchemeId", produces = {"application/json;charset=UTF-8"})
	public
	@ResponseBody
	String getLatestSchemeId(String serviceId, HttpServletRequest request) {
		JSONObject resultJson = new JSONObject();
		try{
			//当前页
			String currentPage = request.getParameter("currentPage");
			//每页大小
			String pageSize = request.getParameter("pageSize");
			//封闭期数
			String closeTerm = request.getParameter("closeTerm");
			
			if (StringUtils.isBlank(currentPage)) {
				currentPage = "1";
			}
			if (StringUtils.isBlank(pageSize)) {
				pageSize = "10";
			}
			//构造JSON请求参数
			JSONObject paramJson = new JSONObject();
			paramJson.put("currentPage", Integer.parseInt(currentPage));
			paramJson.put("pageSize", Integer.parseInt(pageSize));
			paramJson.put("closeTerm", closeTerm);

            paramJson.put("exceptStatus", 5);
            paramJson.put("opensale_level", 0);
            paramJson.put("forsale_level", 1);
            paramJson.put("lock_level", 2);
            paramJson.put("opensaleBeginLte", DateUtil.format(new Date(), "yyyy-MM-dd")+" 23:59:59");
            paramJson.put("orderByColumnIn", "0,1,2");
			
			//调用远程查询新元宝接口
		//	log.info("PlanCotroller getLatestSchemeId ----> params:" + paramJson.toJSONString());
			String resultStr = planCXFService.getSchemeList(paramJson.toJSONString());
		//	log.info("PlanCotroller getLatestSchemeId ----> return:" + resultStr);
			WSPageResponse response = JsonUtil.jsonToBean(resultStr, WSPageResponse.class);
			List list = response.getResultList();
			if (list.size() <= 0) {
				throw new Exception("can't find any Scheme to display...");
			}
			resultJson.put("resultCode", "0");
			resultJson.put("schemeInfo", list.get(0));
			resultJson.put("currentDate", DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
		}catch(Exception e){
			log.error("PlanCotroller getLatestSchemeId ----> arise exception:" , e);
			//返回页面
			resultJson.put("resultCode", "-1");
			resultJson.put("desc", "操作失败，请稍后重试...");
		}
		//log.info("PlanCotroller getLatestSchemeId ----> return to page:" + resultJson.toJSONString());
		return TokenUtil.jsonpCallback(request,resultJson.toJSONString());
	}

    @RequestMapping(value = "/new/{closeTerm}")
    public ModelAndView newPlan(@PathVariable("closeTerm") String closeTerm) {
        log.info("enter newPlan.closeTerm="+closeTerm);
        ModelAndView view = new ModelAndView("newPlan");

        //构造JSON请求参数
        JSONObject paramJson = new JSONObject();
        paramJson.put("currentPage", 1);
        paramJson.put("pageSize", 1);
        paramJson.put("closeTerm", closeTerm);

        //调用远程查询新元宝接口
       // log.info("PlanCotroller getSchemeForNewIndex ----> params:" + paramJson.toJSONString());
        String resultStr = planCXFService.getSchemeList(paramJson.toJSONString());
       // log.info("PlanCotroller getSchemeForNewIndex ----> return:" + resultStr);
        WSPageResponse response = JsonUtil.jsonToBean(resultStr, WSPageResponse.class);
        List list = response.getResultList();

        JSONObject json = (JSONObject)list.get(0);
        String schemeid = json.getString("SCHEMEID");
        view.addObject("schemeid",schemeid);
        return view;
    }

    @RequestMapping(value = "/toNewDay/{closeTerm}")
    public ModelAndView toNewDay(@PathVariable("closeTerm") String closeTerm) {
        log.info("enter toNewDay.closeTerm="+closeTerm);
        ModelAndView view = new ModelAndView("newPlan");

        try {
            //构造JSON请求参数
            JSONObject paramJson = new JSONObject();
            paramJson.put("currentPage", 1);
            paramJson.put("pageSize", 50);
            paramJson.put("closeTerm", closeTerm);

            //调用远程查询新元宝接口
            // log.info("PlanCotroller getSchemeForNewIndex ----> params:" + paramJson.toJSONString());
            String resultStr = planCXFService.getSchemeList(paramJson.toJSONString());
            // log.info("PlanCotroller getSchemeForNewIndex ----> return:" + resultStr);
            WSPageResponse response = JsonUtil.jsonToBean(resultStr, WSPageResponse.class);
            List list = response.getResultList();

            if(list != null && list.size() > 0) {
                JSONObject obj;
                String par = "yyyy-MM-dd";
                String toDayStr = DateUtil.format(new Date(),par);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String schemeid = null;
                String schemeid_jiaxi = null;
                for(int i = 0 ; i < list.size(); i++) {
                    obj = (JSONObject)list.get(i);
                    if(toDayStr.equals(DateUtil.format(sdf.parse(obj.getString("OPENSALEBEGIN")),par))){

                        if(obj.get("STATUS") != null && obj.getIntValue("STATUS") == 2
                                && obj.get("REMACOUNT") !=null && obj.getBigDecimal("REMACOUNT").compareTo(new BigDecimal(0)) == 1){
                            if(obj.get("WEBAPP") != null && obj.getFloatValue("WEBAPP") > 0){
                                schemeid_jiaxi = obj.getString("SCHEMEID");
                                break;
                            } else {
                                schemeid = obj.getString("SCHEMEID");
                                break;
                            }
                        }

                    }
                }

                if(schemeid_jiaxi != null) {
                    view.addObject("schemeid",schemeid_jiaxi);
                } else {
                    view.addObject("schemeid",schemeid);
                }

            }

        }catch (Exception e) {
            log.error("error toNewDay",e);
        }
        return view;
    }


    @RequestMapping(value = "/toDay/{closeTerm}")
    public ModelAndView toDay(@PathVariable("closeTerm") String closeTerm) {
        log.info("enter toDay.closeTerm="+closeTerm);
        ModelAndView view = new ModelAndView("newPlan");

        try {
            //构造JSON请求参数
            JSONObject paramJson = new JSONObject();
            paramJson.put("currentPage", 1);
            paramJson.put("pageSize", 50);
            paramJson.put("closeTerm", closeTerm);

            //调用远程查询新元宝接口
           // log.info("PlanCotroller getSchemeForNewIndex ----> params:" + paramJson.toJSONString());
            String resultStr = planCXFService.getSchemeList(paramJson.toJSONString());
           // log.info("PlanCotroller getSchemeForNewIndex ----> return:" + resultStr);
            WSPageResponse response = JsonUtil.jsonToBean(resultStr, WSPageResponse.class);
            List list = response.getResultList();

            if(list != null && list.size() > 0) {
                JSONObject obj;
                String par = "yyyy-MM-dd";
                String toDayStr = DateUtil.format(new Date(),par);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for(int i = 0 ; i < list.size(); i++) {
                    obj = (JSONObject)list.get(i);
                    if(toDayStr.equals(DateUtil.format(sdf.parse(obj.getString("OPENSALEBEGIN")),par))){
                        String schemeid = obj.getString("SCHEMEID");
                        view.addObject("schemeid",schemeid);
                      //  log.info("toDay.schemeid="+schemeid);
                        break;
                    }
                }
            }

        }catch (Exception e) {
             log.error("error toDay",e);
        }
        return view;
    }

    /**
     * 获取top10投资用户
     *
     * @param serviceId
     * @param request
     * @return
     */
    @RequestMapping(value = "/getSumTop10UserTender", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getSumTop10UserTender(String serviceId, HttpServletRequest request) {
        JSONObject resultJson = new JSONObject();
        try{
            //构造JSON请求参数
            JSONObject paramJson = new JSONObject();
            paramJson.put("source","monthly");
            //调用远程查询用户投资top10接口
           // log.info("PlanCotroller getSumUserTenderByDate ----> params:" + paramJson.toJSONString());
            String resultStr = lotteryCXFService.getSumUserTenderByDate(paramJson.toJSONString());
           // log.info("PlanCotroller getSumUserTenderByDate ----> return:" + resultStr);
            WSMapResponse response = JsonUtil.jsonToBean(resultStr, WSMapResponse.class);
            List<Map> investTop10s = response.getMapList();
            resultJson.put("resultCode", "0");
            resultJson.put("investTop10s", investTop10s);
        }catch(Exception e){
            log.error("PlanCotroller getSumUserTenderByDate ----> arise exception:" , e);
            //返回页面
            resultJson.put("resultCode", "-1");
            resultJson.put("desc", "操作失败，请稍后重试...");
        }
        //log.info("PlanCotroller getSumUserTenderByDate ----> return to page:" + resultJson.toJSONString());
        return resultJson.toJSONString();
    }

    @RequestMapping(value="/queryUserDetailPage", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String queryUserDetailPage(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            JSONObject param = new JSONObject();
            String userSchemeId = request.getParameter("userSchemeId");
            param.put("userSchemeId",userSchemeId);
            String str = planCXFService.queryUserDetailPage(param.toJSONString());

            JSONObject resp = JSONObject.parseObject(str);
            if(resp.getInteger("resultCode") == 0) {
                JSONObject data = resp.getJSONObject("data");
                JSONArray list = data.getJSONArray("resultList");
                if(list != null && list.size() > 0) {
                   result.put("resultCode",0);
                   result.put("list",list);
               } else {
                   result.put("resultCode",1);
               }
            } else {
                result.put("resultCode",1);
            }
        } catch (Exception e) {
            log.error("PlanCotroller queryUserDetailPage ----> arise exception:" , e);
            //返回页面
            result.put("resultCode", "-1");
            result.put("desc", "操作失败，请稍后重试...");
        }
        return result.toJSONString();
    }

    @RequestMapping(value = "/getUserSchemeInfo",produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String getUserSchemeInfo(HttpServletRequest request){
         JSONObject result = new JSONObject();
        try {
            Object userObj = request.getSession().getAttribute("loginUser");
            if(userObj == null) {
                result.put("resultCode",300);
                result.put("desc", "请先登录");
                return result.toJSONString();
            }
            User user = (User)userObj;
            JSONObject param = new JSONObject();
            String schemeId = request.getParameter("schemeId");
            param.put("userId",user.getUserId());
            param.put("schemeId",schemeId);
            String str = planCXFService.getUserSchemeInfo(param.toJSONString());
            JSONObject resp = JSONObject.parseObject(str);
            result = resp;
        } catch (Exception e) {
            log.error("PlanCotroller.getUserSchemeInfo error",e);
            result.put("resultCode", "-1");
            result.put("desc", "操作失败，请稍后重试...");
        }
        return result.toJSONString();
    }
}
