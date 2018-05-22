/**
 * Copyright (c) 2016, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.yyp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxdai.client.CXF_Factory;
import com.xxdai.constant.Constant;
import com.xxdai.core.util.http.HttpUtil;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.core.util.lang.DateUtil;
import com.xxdai.core.util.lang.StringUtil;
import com.xxdai.external.user.ws.User;
import com.xxdai.external.yyp.ws.ReglIntstCXFService;
import com.xxdai.external.yyp.ws.ReglIntstTradeCXFService;
import com.xxdai.person.model.PersonResponse;
import com.xxdai.person.ws.approquery.ApproQueryCXFService;
import com.xxdai.util.AccountUtil;
import com.xxdai.util.Configuration;
import com.xxdai.util.TokenUtil;
import com.xxdai.ws.util.WSModelResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/yyp")
public class YypController {
    /**
     * 日志记录器
     */
    Logger log = LoggerFactory.getLogger(YypController.class);

    /**
     * 月月派交易接口
     */
    private ReglIntstTradeCXFService reglIntstTradeCXFService = (ReglIntstTradeCXFService) CXF_Factory.getFactory(ReglIntstTradeCXFService.class, Configuration.getInstance().getValue("webService_url") + "/yypTradeCXFService").create();

    /**
     * 月月派接口
     */
    private ReglIntstCXFService reglIntstCXFService = (ReglIntstCXFService) CXF_Factory.getFactory(ReglIntstCXFService.class, Configuration.getInstance().getValue("webService_url") + "/yypCXFService").create();

    private ApproQueryCXFService approQueryCXFService = (ApproQueryCXFService) CXF_Factory.getFactory(ApproQueryCXFService.class, Configuration.getInstance().getValue("webService_url") + "/approQueryWebService").create();

    /**
     * 获取月月派列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/list", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String list(HttpServletRequest request) {

        JSONObject resultJson = new JSONObject();

        try {
            //当前页
            String currentPage = request.getParameter("currentPage");
            //每页大小
            String pageSize = request.getParameter("pageSize");

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

            //log.info("YypController list ----> params:" + json.toJSONString());
            String resultStr = reglIntstCXFService.selectReglintstList(json.toJSONString());
            //log.info("YypController list ----> return:" + resultStr);
            JSONObject respJson = JsonUtil.jsonToBean(resultStr, JSONObject.class);
            if (respJson == null || respJson.getIntValue("resultCode") != 0) {
                resultJson.put("resultCode", 100);
                return resultJson.toJSONString();
            }
            resultJson.put("resultList", respJson.get("resultList"));
            resultJson.put("currentDate", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            resultJson.put("resultCode", 0);
        } catch (Exception e) {
            log.error("YypController list ----> arise exception:", e);
            resultJson.put("resultCode", 1);
        }
        //log.info("YypController list ----> return to page:" + resultJson.toJSONString());
        return resultJson.toJSONString();
    }

    /**
     * 获取月月派
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/detail/{yypId}")
    @ResponseBody
    public String detail(@PathVariable("yypId") String yypId, HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            JSONObject param = new JSONObject();
            param.put("yypId", yypId);
            log.info("YypController detail ----> params:" + param.toJSONString());
            String resp = reglIntstCXFService.queryReglIntsById(param.toJSONString());
            //log.info("YypController detail ----> return:" + resp);
            WSModelResponse response = JsonUtil.jsonToBean(resp, WSModelResponse.class);
            if (response == null || response.getResultCode() != 0) {
                result.put("resultCode", 100);
                return result.toJSONString();
            }

            result.put("data", response.getData());
            result.put("resultCode", 0);
        } catch (Exception e) {
            log.error("YypController detail ----> arise exception:", e);
            result.put("resultCode", 1);
        }
        //log.info("YypController detail ----> return to page:" + result.toJSONString());
        return result.toJSONString();
    }

    /**
     * 首页展示
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/index", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String index(HttpServletRequest request) {
        JSONObject result = new JSONObject();

        try {
            String terms = request.getParameter("terms");
            //当前页
            String currentPage = request.getParameter("currentPage");
            //每页大小
            String pageSize = request.getParameter("pageSize");


            if (StringUtils.isBlank(currentPage)) {
                currentPage = "1";
            }
            if (StringUtils.isBlank(pageSize)) {
                pageSize = "10";
            }
            JSONObject param = new JSONObject();
            param.put("currentPage", Integer.parseInt(currentPage));
            param.put("pageSize", Integer.parseInt(pageSize));
            param.put("terms", terms);
            //查当天
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            param.put("openDate", sdf.format(new Date()));
            //调用远程查询新元宝接口
           // log.info("selectYypTerms req:" + param.toJSONString());
            String resultStr = reglIntstCXFService.selectReglintstList(param.toJSONString());
            //log.info("selectYypTerms resp =" + resultStr);
            JSONObject response = JsonUtil.jsonToBean(resultStr, JSONObject.class);
            if (response.getIntValue("resultCode") != 0) {
                result.put("resultCode", response.getIntValue("resultCode"));
                result.put("msg", response.getString("desc"));
                return result.toJSONString();
            }
            result = response;
        } catch (Exception e) {
            log.error("", e);
            result.put("resultCode", 100);
            result.put("msg", "查询异常");
        }
        return result.toJSONString();
    }

    /**
     * 查询理财产品加入记录
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/joinHistory", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String joinHistory(HttpServletRequest request, HttpServletRequest response) {
        JSONObject paramJson = new JSONObject();
        JSONObject resultJson = new JSONObject();
        try {
            String yypId = request.getParameter("yypId");
            String pageSize = request.getParameter("pageSize");
            String currentPage = request.getParameter("currentPage");

            paramJson.put("id", yypId);
            paramJson.put("pageSize", pageSize);
            paramJson.put("currentPage", currentPage);
            //log.info("YypController joinHistory ----> params:" + paramJson.toJSONString());
            String resultStr = reglIntstCXFService.queryYypJoinList(paramJson.toJSONString());
            //log.info("YypController joinHistory ----> return:" + resultStr);
            if (StringUtil.isNotBlank(resultStr)) {
                resultJson = JSON.parseObject(resultStr);
            } else {
                resultJson.put("resultCode", "-1");
                resultJson.put("desc", "获取月月派加入记录失败，请稍后重试...");
            }
        } catch (Exception e) {
            log.error("YypController joinHistory ----> arise exception:", e);
            //返回页面
            resultJson.put("resultCode", -1);
            resultJson.put("desc", "操作失败，请稍后重试...");
        }
        //log.info("YypController joinHistory ----> return to page:" + resultJson.toJSONString());
        return resultJson.toJSONString();

    }

    /**
     * 投标
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/tender", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String tender(HttpServletRequest request, HttpServletRequest response) {
        JSONObject paramJson = new JSONObject();
        JSONObject resultJson = new JSONObject();
        try {
            //判断当前token是否有效
            if (!TokenUtil.validToken(request)) {
                JSONObject json = new JSONObject();
                json.put("resultCode", Constant.TOKEN_INVALID_ERROR);
                json.put("desc", "页面已过期，请重新尝试");
                return json.toString();
            }
            TokenUtil.removeToken(request);

            Object userObj = request.getSession().getAttribute("loginUser");
            if (userObj == null) {
                resultJson.put("resultCode", -2);
                resultJson.put("desc", "您的会话失效，请重新登录");
                return resultJson.toJSONString();
            }
            User user = (User) userObj;
            long userId = user.getUserId();
            String payPwd = request.getParameter("payPwd");                // 支付密码
            String userIp = HttpUtil.getRealIpAddr(request);

            //验证支付密码
            resultJson = JSONObject.parseObject(AccountUtil.checkPayPwd(request, userId, payPwd));
            if (resultJson.getIntValue("resultCode") != 0) {
                return resultJson.toJSONString();
            }

            //投资参数
            String yypId = request.getParameter("yypId");
            String tenderAmount = request.getParameter("realPayAmount");  //实际支付金额
            paramJson.put("userId", userId);
            paramJson.put("yypId", yypId);
            paramJson.put("operateMoney", tenderAmount);
            paramJson.put("ip", userIp);
            JSONObject terminalVer = new JSONObject();
            terminalVer.put("user-agent", request.getHeader("user-agent"));
            terminalVer.put("type", "WEB-APP");
            paramJson.put("terminalver", terminalVer.toJSONString());

            //红包
            List<String> redCodes = new ArrayList<String>();
            String redCode = request.getParameter("redCode");
            if (redCode != null && StringUtil.isNotBlank(redCode)) {
                redCodes.add(redCode);
                paramJson.put("redCodes", redCodes);
            }

            log.info("YypController tender ----> params:" + paramJson.toJSONString());
            String resultStr = reglIntstTradeCXFService.buyYyp(paramJson.toJSONString());
            log.info("YypController tender ----> return:" + resultStr);
            if (StringUtil.isNotBlank(resultStr)) {
                resultJson = JSON.parseObject(resultStr);
                resultJson.put("tenderTime", new Date().getTime());
                resultJson.put("desc", resultJson.get("resultDesc"));
            } else {
                resultJson.put("resultCode", "-1");
                resultJson.put("desc", "系统异常，请稍后重试...");
            }
            resultJson.put("activity_url",Configuration.getInstance().getValue("activity_url"));
        } catch (Exception e) {
            log.error("YypController tender ----> arise exception:", e);
            resultJson.put("resultCode", -1);
            resultJson.put("desc", "操作失败，请稍后重试...");
        }
        log.info("YypController tender ----> return to page:" + resultJson.toJSONString());
        return resultJson.toJSONString();
    }

    /**
     * 查询理财产品投资记录
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/history", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String history(HttpServletRequest request, HttpServletRequest response) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("resultCode", "-1");
        resultJson.put("desc", "获取月月派投资记录失败，请稍后重试...");
        try {
            //从session取的当前登录用户
            Object userObj = request.getSession().getAttribute("loginUser");
            if (userObj == null) {
                resultJson.put("resultCode", "-1");
                resultJson.put("desc", "您的会话失效，请重新登录");
                return resultJson.toJSONString();
            }
            User user = (User) userObj;

            String userId = String.valueOf(user.getUserId());
            String pageSize = request.getParameter("pageSize");
            String currentPage = request.getParameter("currentPage");

            JSONObject paramJson = new JSONObject();
            paramJson.put("userId", userId);
            paramJson.put("pageSize", pageSize);
            paramJson.put("currentPage", currentPage);

            //log.info("YypController history ----> params:" + paramJson.toJSONString());
            String resultStr = reglIntstCXFService.getMyYypList(paramJson.toJSONString());
            //log.info("YypController history ----> return:" + resultStr);

            if (StringUtil.isNotBlank(resultStr)) {
                JSONObject rj = JSON.parseObject(resultStr);
                if ("0".equals(String.valueOf(rj.get("resultCode")))) {
                    resultJson.put("resultCode", "0");
                    resultJson.put("desc", "获取月月派投资记录成功");
                    resultJson.put("resultList", rj.getJSONArray("resultList"));
                    resultJson.put("totalPages", rj.get("totalPages"));
                }
            }
        } catch (Exception e) {
            log.error("YypController history ----> arise exception:", e);
            //返回页面
            resultJson.put("resultCode", -1);
            resultJson.put("desc", "操作失败，请稍后重试...");
        }
        //log.info("YypController history ----> return to page:" + resultJson.toJSONString());
        return resultJson.toJSONString();

    }

    /**
     * 查询用户本期月月派投资信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getUserYypInfo", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getUserYypInfo(HttpServletRequest request, HttpServletRequest response) {
        JSONObject resultJson = new JSONObject();
        try {
            //从session取的当前登录用户
            Object userObj = request.getSession().getAttribute("loginUser");
            if (userObj == null) {
                resultJson.put("resultCode", "-1");
                resultJson.put("desc", "您的会话失效，请重新登录");
                return resultJson.toJSONString();
            }
            User user = (User) userObj;

            String yypId = request.getParameter("yypId");

            JSONObject paramJson = new JSONObject();
            paramJson.put("userId", user.getUserId());
            paramJson.put("pid", yypId);

           // log.info("YypController getUserYypInfo ----> params:" + paramJson.toJSONString());
            String resultStr = reglIntstCXFService.queryInvestInfo(paramJson.toJSONString());
            //log.info("YypController getUserYypInfo ----> return:" + resultStr);

            if (StringUtil.isNotBlank(resultStr)) {
                resultJson = JSON.parseObject(resultStr);
            } else {
                resultJson.put("resultCode", "-1");
                resultJson.put("desc", "获取用户月月派信息失败，请稍后重试...");
            }
        } catch (Exception e) {
            log.error("YypController getUserYypInfo ----> arise exception:", e);
            //返回页面
            resultJson.put("resultCode", -1);
            resultJson.put("desc", "操作失败，请稍后重试...");
        }
        //log.info("YypController getUserYypInfo ----> return to page:" + resultJson.toJSONString());
        return resultJson.toJSONString();

    }

    /**
     * 查询协议数据
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/agreementData/{joinId}", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String agreementData(@PathVariable("joinId") String joinId, HttpServletRequest request, HttpServletRequest response) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("resultCode", "-1");
        resultJson.put("desc", "获取协议数据失败，请稍后重试...");
        try {
            //从session取的当前登录用户
            Object userObj = request.getSession().getAttribute("loginUser");
            if (userObj == null) {
                resultJson.put("resultCode", "-1");
                resultJson.put("desc", "您的会话失效，请重新登录");
                return resultJson.toJSONString();
            }
            User user = (User) userObj;

            JSONObject paramJson = new JSONObject();
            paramJson.put("userId", user.getUserId());
            paramJson.put("joinId", joinId);

           // log.info("YypController readAgreement userJoinInfo ----> params:" + paramJson.toJSONString());
            String userJoinInfo = reglIntstCXFService.queryJoinInfoByUserIdAndJoinId(paramJson.toJSONString());
            //log.info("YypController readAgreement userJoinInfo ----> return:" + userJoinInfo);
            if (StringUtil.isNotBlank(userJoinInfo)) {
                JSONObject userJoinJson = JSON.parseObject(userJoinInfo);
                if ("0".equals(String.valueOf(userJoinJson.get("resultCode")))) {
                    JSONObject userJoinData = userJoinJson.getJSONObject("data");
                    resultJson.put("userJoinInfo", userJoinData);

                    String pid = userJoinData.getString("pid");
                    paramJson.clear();
                    paramJson.put("yypId", pid);
                    //log.info("YypController readAgreement yypInfo ----> params:" + paramJson.toJSONString());
                    String yypInfo = reglIntstCXFService.queryReglIntsById(paramJson.toJSONString());
                    //log.info("YypController readAgreement yypInfo ----> return:" + yypInfo);
                    if (StringUtil.isNotBlank(yypInfo)) {
                        JSONObject yypJson = JSON.parseObject(yypInfo);
                        if ("0".equals(String.valueOf(yypJson.get("resultCode")))) {
                            JSONObject yypData = yypJson.getJSONObject("data");
                            resultJson.put("yypInfo", yypData);
                            resultJson.put("userInfo", user);

                            //查询实名认证信息
                            paramJson.clear();
                            paramJson.put("userId", user.getUserId());
                           // log.info("YypController readAgreement userInfo ----> params:" + paramJson.toJSONString());
                            String approStr = approQueryCXFService.queryRealNameApproByUserId(paramJson.toJSONString());
                          //  log.info("YypController readAgreement userInfo ----> return:" + yypInfo);
                            PersonResponse resp = JsonUtil.jsonToBean(approStr, PersonResponse.class);
                            if (resp.getData() != null) {
                                JSONObject realName = JSONObject.parseObject(resp.getData().toString());
                                JSONObject userInfo = resultJson.getJSONObject("userInfo");
                                userInfo.put("realName", realName);
                                resultJson.put("userInfo", userInfo);
                            }

                            resultJson.put("resultCode", 0);
                            resultJson.put("desc", "获取协议数据成功");
                        }
                    }
                }
            }

        } catch (Exception e) {
            log.error("YypController readAgreement ----> arise exception:", e);
            //返回页面
            resultJson.put("resultCode", -1);
            resultJson.put("desc", "操作失败，请稍后重试...");
        }
        //log.info("YypController readAgreement ----> return to page:" + resultJson.toJSONString());
        return resultJson.toJSONString();

    }

    /**
     * 退出
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/quit", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String quit(HttpServletRequest request, HttpServletRequest response) {
        JSONObject paramJson = new JSONObject();
        JSONObject resultJson = new JSONObject();
        try {
            Object userObj = request.getSession().getAttribute("loginUser");
            if (userObj == null) {
                resultJson.put("resultCode", -2);
                resultJson.put("desc", "您的会话失效，请重新登录");
                return resultJson.toJSONString();
            }
            User user = (User) userObj;
            long userId = user.getUserId();
            String payPwd = request.getParameter("payPwd");                // 支付密码

            //验证支付密码
            resultJson = JSONObject.parseObject(AccountUtil.checkPayPwd(request, userId, payPwd));
            if (resultJson.getIntValue("resultCode") != 0) {
                return resultJson.toJSONString();
            }

            String yypJoinId = request.getParameter("joinId");

            paramJson.put("userId", userId);
            paramJson.put("yypJoinId", yypJoinId);

            //log.info("YypController quit ----> params:" + paramJson.toJSONString());
            String resultStr = reglIntstTradeCXFService.quitYyp(paramJson.toJSONString());
            //log.info("YypController quit ----> return:" + resultStr);

            if (StringUtil.isNotBlank(resultStr)) {
                resultJson = JSON.parseObject(resultStr);
                resultJson.put("desc", resultJson.get("resultDesc"));

                /******************月月派GA部署所需参数start******************/
                JSONObject gaJson = new JSONObject();
                gaJson.put("joinId", yypJoinId);
                gaJson.put("userId", userId);
                String gaJsonStr = reglIntstCXFService.getYYPGAData(gaJson.toString());
                WSModelResponse gaRes = JsonUtil.jsonToBean(gaJsonStr, WSModelResponse.class);
                if(gaRes.getData()!=null) {
                    String dataStr = String.valueOf(gaRes.getData());
                  //  log.info("月月派ga布码结果：" + dataStr);
                    Map map = JsonUtil.jsonToBean(dataStr, Map.class);
                    String serviceNum ="";
                    if(null != map){
                        resultJson.put("productId",map.get("ID"));
                        resultJson.put("apr",map.get("APR"));
                        resultJson.put("terms",map.get("TERMS"));
                        resultJson.put("price",map.get("ACCOUNT"));
                        resultJson.put("tradeid",map.get("JOINID"));
                        if (null != map.get("SERVICENUM") && !"".equals(map.get("SERVICENUM"))) {
                            serviceNum = map.get("SERVICENUM").toString();
                        } else {
                            serviceNum = "nonFD";
                        }
                        resultJson.put("servicenum",serviceNum);
                    }
                }
                //log.info("月月派退出结果：" + resultJson.toJSONString());
                /******************月月派GA部署所需参数end******************/
            } else {
                resultJson.put("resultCode", "-1");
                resultJson.put("desc", "月月派退出失败，请稍后重试...");
            }
        } catch (Exception e) {
            log.error("YypController quit ----> arise exception:", e);
            //返回页面
            resultJson.put("resultCode", -1);
            resultJson.put("desc", "操作失败，请稍后重试...");
        }
        log.info("YypController quit ----> return to page:" + resultJson.toJSONString());
        return resultJson.toJSONString();
    }

    /**
     * 锁定期限和对应的利率，以及起投金额，递增金额等
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/commonInfo", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String commonInfo(HttpServletRequest request, HttpServletRequest response) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("resultCode", "-1");
        resultJson.put("desc", "获取月月派基本信息失败，请稍后重试...");
        try {
            JSONObject paramJson = new JSONObject();
            String openDate = request.getParameter("openDate");
            paramJson.put("currentPage", 1);
            paramJson.put("pageSize", 20);
            paramJson.put("openDate", openDate);
            //log.info("YypController commonInfo ----> params:" + paramJson);
            String resultStr = reglIntstCXFService.selectReglintstList(paramJson.toJSONString());
            //log.info("YypController commonInfo ----> return:" + resultStr);

            if (StringUtil.isNotBlank(resultStr)) {
                JSONObject result = JSON.parseObject(resultStr);
                if ("0".equals(String.valueOf(result.get("resultCode")))) {
                    JSONArray ja = result.getJSONArray("resultList");
                    if (ja != null) {
                        List closeTerms = new ArrayList();
                        double minAmount = 0;        //最小投资金额
                        double increaseRadix = 0;    //递增金额
                        ArrayList<HashMap<Double, Double>> termAndAprs = new ArrayList<HashMap<Double, Double>>();
                        for (int i = 0; i < ja.size(); i++) {
                            JSONObject jo = ja.getJSONObject(i);
                            double apr = jo.getDoubleValue("APR");
                            double term = jo.getDoubleValue("TERMS");
                            double minA = jo.getDoubleValue("LOWESTTENDER");
                            double step = jo.getDoubleValue("STEP");
                            if (closeTerms.contains(term)) {
                                continue;
                            } else {
                                if (i == 0) {
                                    minAmount = minA;
                                    increaseRadix = step;
                                }
                                closeTerms.add(term);
                                HashMap hm = new HashMap();
                                hm.put("term", term);
                                hm.put("apr", apr);
                                termAndAprs.add(hm);
                                if (minAmount > minA) {
                                    minAmount = minA;
                                }
                                if (increaseRadix > step) {
                                    increaseRadix = step;
                                }
                            }
                        }
                        Collections.sort(termAndAprs, new Comparator<HashMap<Double, Double>>() {
                            @Override
                            public int compare(HashMap<Double, Double> o1, HashMap<Double, Double> o2) {
                                if (o1.get("term") > o2.get("term")) {
                                    return 1;
                                } else if (o1.get("term") < o2.get("term")) {
                                    return -1;
                                } else {
                                    return 0;
                                }
                            }
                        });
                        resultJson.put("resultCode", "0");
                        resultJson.put("desc", "获取月月派基本信息成功");
                        resultJson.put("termAndAprs", termAndAprs);
                        resultJson.put("minAmount", minAmount);
                        resultJson.put("increaseRadix", increaseRadix);
                    }
                }
            }
        } catch (Exception e) {
            log.error("YypController commonInfo ----> arise exception:", e);
            //返回页面
            resultJson.put("resultCode", -1);
            resultJson.put("desc", "操作失败，请稍后重试...");
        }
        //log.info("YypController commonInfo ----> return to page:" + resultJson.toJSONString());
        return resultJson.toJSONString();
    }

    @RequestMapping(value = "/queryInterestList", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String queryInterestList(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            Object userObj = request.getSession().getAttribute("loginUser");
            if (userObj == null) {
                result.put("resultCode", -2);
                result.put("desc", "您的会话失效，请重新登录");
                return result.toJSONString();
            }
            User user = (User) userObj;
            JSONObject param = new JSONObject();
            String joinId = request.getParameter("joinId");
            param.put("joinId", joinId);
            param.put("userId", user.getUserId());
           // log.info("yyp queryInterestList req = " + param.toJSONString());
            String respStr = reglIntstCXFService.queryInterestList(param.toJSONString());
            //log.info("yyp queryInterestList resp = " + respStr);
            JSONObject respObj = JsonUtil.jsonToBean(respStr, JSONObject.class);
            if (respObj.getIntValue("resultCode") == 0) {
                result = respObj;
            } else {
                result.put("resultCode", respObj.getIntValue("resultCode"));
                result.put("desc", respObj.getString("desc"));
            }
        } catch (Exception e) {
            log.error("", e);
            result.put("resultCode", 100);
            result.put("desc", "查询异常");
        }
        return result.toJSONString();
    }

    @RequestMapping(value = "/queryInvestInfo", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String queryInvestInfo(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            Object userObj = request.getSession().getAttribute("loginUser");
            if (userObj == null) {
                result.put("resultCode", -2);
                result.put("desc", "您的会话失效，请重新登录");
                return result.toJSONString();
            }
            User user = (User) userObj;
            JSONObject param = new JSONObject();
            param.put("userId", user.getUserId());
            String joinId = request.getParameter("joinId");
            param.put("joinId", joinId);
            String respStr = reglIntstCXFService.queryInvestInfo(param.toJSONString());
            JSONObject respObj = JsonUtil.jsonToBean(respStr, JSONObject.class);
            int resultCode = respObj.getIntValue("resultCode");
            if (resultCode == 0) {
                result = respObj;
            } else {
                result.put("resultCode", respObj.getIntValue("resultCode"));
                result.put("desc", respObj.getString("desc"));
            }
        } catch (Exception e) {
            log.error("", e);
            result.put("resultCode", 100);
            result.put("desc", "查询异常");
        }
        return result.toJSONString();
    }

    @RequestMapping(value = "/queryJoinInfoByUserIdAndJoinId", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String queryJoinInfoByUserIdAndJoinId(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            Object userObj = request.getSession().getAttribute("loginUser");
            if (userObj == null) {
                result.put("resultCode", -2);
                result.put("desc", "您的会话失效，请重新登录");
                return result.toJSONString();
            }
            User user = (User) userObj;
            JSONObject param = new JSONObject();
            param.put("userId", user.getUserId());
            String joinId = request.getParameter("joinId");
            param.put("joinId", joinId);
            String respStr = reglIntstCXFService.queryJoinInfoByUserIdAndJoinId(param.toJSONString());
            JSONObject respObj = JsonUtil.jsonToBean(respStr,JSONObject.class);
            int resultCode = respObj.getIntValue("resultCode");
            if(resultCode == 0) {
                 result = respObj;
            } else {
                result.put("resultCode", resultCode);
                result.put("desc", respObj.getString("desc"));
            }
        } catch (Exception e) {
            log.error("", e);
            result.put("resultCode", 100);
            result.put("desc", "查询异常");
        }
        return result.toJSONString();
    }

}
