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
import com.xxdai.appro.bo.MobileAppro;
import com.xxdai.borrow.bo.CompanyInfo;
import com.xxdai.borrow.model.BorrowQuery;
import com.xxdai.borrow.webservice.entity.BorrowQueryWsResponse;
import com.xxdai.client.CXF_Factory;
import com.xxdai.constant.AppConsts;
import com.xxdai.constant.Constant;
import com.xxdai.constant.SmsConsts;
import com.xxdai.constant.SmsTemplateConstant;
import com.xxdai.core.util.http.HttpUtil;
import com.xxdai.core.util.http.WSMapResponse;
import com.xxdai.core.util.http.WSResponse;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.core.util.lang.StringUtil;
import com.xxdai.encrypt.EscapeCode;
import com.xxdai.external.account.ws.AccountQueryCXFService;
import com.xxdai.external.accountcash.ws.AccountCashCXFService;
import com.xxdai.external.borrowQuery.ws.BorrowQueryCXFService;
import com.xxdai.external.sms.ws.SMSCXFService;
import com.xxdai.external.user.ws.User;
import com.xxdai.external.user.ws.UserCXFService;
import com.xxdai.person.bo.BaseInfo;
import com.xxdai.person.model.PersonResponse;
import com.xxdai.person.model.UserCreditStuffShowInfo;
import com.xxdai.person.ws.accountov.AccountOVQueryCXFService;
import com.xxdai.system.bo.DicCommonVo;
import com.xxdai.trade.constants.TradeConstants;
import com.xxdai.trade.model.TradeTransfer;
import com.xxdai.trade.webservice.TradeCXFWebService;
import com.xxdai.trade.webservice.entity.TradeResponse;
import com.xxdai.user.model.UserResponse;
import com.xxdai.util.*;
import com.xxdai.ws.util.WSModelResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author huna
 * @version $Id: TradeRequestController.java 16272 2015-03-16 03:35:16Z huna $
 * @since jdk1.6
 */

@Controller
@RequestMapping(value = "/traderequest")
public class TradeRequestController {
    private static final Log log = LogFactory.getLog(TradeRequestController.class);
    private TradeCXFWebService tradeCXFWebService = (TradeCXFWebService) CXF_Factory.getFactory(TradeCXFWebService.class, Configuration.getInstance().getValue("webService_url") + "/tradeWebService").create();

    private BorrowQueryCXFService borrowQueryCXFService = (BorrowQueryCXFService) CXF_Factory.getFactory(BorrowQueryCXFService.class, Configuration.getInstance().getValue("webService_url") + "/borrowQueryWebService").create();

    private AccountQueryCXFService accountQueryCXFService = (AccountQueryCXFService) CXF_Factory.getFactory(AccountQueryCXFService.class, Configuration.getInstance().getValue("trade_url") + "/accountQueryWebService").create();

    private UserCXFService userCXFService = (UserCXFService) CXF_Factory.getFactory(UserCXFService.class, Configuration.getInstance().getValue("webService_url") + "/userWebService").create();

    private SMSCXFService smsCXFService= (SMSCXFService) CXF_Factory.getFactory(SMSCXFService.class,Configuration.getInstance().getValue("webService_url")+"/smsWebService").create();

    private AccountOVQueryCXFService accountOVService = (AccountOVQueryCXFService) CXF_Factory.getFactory(AccountOVQueryCXFService.class, Configuration.getInstance().getValue("webService_url") + "/accountOVQueryWebService").create();
    private AccountCashCXFService accountCashCXFService = (AccountCashCXFService) CXF_Factory.getFactory(AccountCashCXFService.class, Configuration.getInstance().getValue("webService_url") + "/accountCashWebService").create();

    /**
     * 出借管理
     * 查询当前登陆人可转出的债权、转让中的债权、已转出的债权
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/transfer")
    public ModelAndView tradeTransfer(HttpServletRequest request) {
        //获取当前登陆用户
        Object userObj = request.getSession().getAttribute("loginUser");

        //获取页面参数
        String currentTab = request.getParameter("currentTab");
        String dateFrom = request.getParameter("dateFrom");
        String dateTo = request.getParameter("dateTo");

        ModelAndView mv = new ModelAndView("pages/trade/jsp/trade_transfer");
        if (userObj != null) {
            User user = (User) userObj;
            long userId = user.getUserId();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", userId);

            //计算转让中的债权数量
            String resultStr = tradeCXFWebService.getTransferingCount(JsonUtil.beanToJson(jsonObject));
            JSONObject resultObject = JSONObject.parseObject(resultStr);
            BigDecimal transferingCount = resultObject.getBigDecimal("transferingCount");

            mv.addObject("transferingCount", transferingCount);

            //计算当前持有债权数量
            resultStr = tradeCXFWebService.getTotalTradeCount(JsonUtil.beanToJson(jsonObject));
            resultObject = JSONObject.parseObject(resultStr);
            BigDecimal totalTradeCount = resultObject.getBigDecimal("totalTradeCount");

            mv.addObject("totalTradeCount", totalTradeCount);
        } else {
            return new ModelAndView("redirect:/user/ilogin.html");
        }

        if (StringUtils.isNotBlank(currentTab)) {
            mv.addObject("currentTab", Integer.parseInt(currentTab));
        } else {
            mv.addObject("currentTab", TradeConstants.TRADE_PAGE_TAB_IS_HOLD);
        }

        mv.addObject("dateFrom", dateFrom);
        mv.addObject("dateTo", dateTo);
        return mv;
    }

    /**
     * 查询用户当前持有的债权
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryTradeTransferList", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String queryTradeTransferList(HttpServletRequest request) {
        JSONObject resultJson = new JSONObject();
        try {
            //获取当前登陆用户
            Object userObj = request.getSession().getAttribute("loginUser");
            if (userObj == null) {
               resultJson.put("resultCode", TradeConstants.ERROR_CODE_NOT_LOGIN);
               return resultJson.toString(); //未登录
            }

            //获取页面参数
            String currentTab = request.getParameter("currentTab");
            String dateFrom = request.getParameter("dateFrom");
            String dateTo = request.getParameter("dateTo");

            //分页信息
            String currentPage = request.getParameter("currentPage");

            User user = (User) userObj;
            long userId = user.getUserId();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", userId);
            jsonObject.put("dateFrom", dateFrom);
            jsonObject.put("dateTo", dateTo);

            if (currentPage == null || StringUtil.isEmpty(currentPage)) {
                currentPage = "1";
            }
            jsonObject.put("currentPage", Integer.parseInt(currentPage));

            //查询查询用户当前持有的债权
            String resultStr = tradeCXFWebService.searchTradeTransfer(JsonUtil.beanToJson(jsonObject));
            //结果集转对象
            TradeResponse wsResponse = JsonUtil.jsonToBean(resultStr, TradeResponse.class);
            List<TradeTransfer> tradeTransfers = null;
            List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
            if (wsResponse != null) {
                tradeTransfers = wsResponse.getTradeTransfers();

                for (TradeTransfer trade : tradeTransfers) {
                    HashMap<String, Object> p = new HashMap<String, Object>();
                    p.put("tenderId", trade.getTenderId());
                    p.put("borrowId",trade.getBorrowId());
                    p.put("borrowName", trade.getBorrowName());
                    p.put("borrowTenderTime", trade.getBorrowTenderTime());
                    p.put("rateYear", trade.getRateYear());
                    p.put("remainNumber", trade.getRemainNumber());
                    p.put("repayCapital", trade.getRepayCapital());
                    p.put("nextRepaymentTime", trade.getNextRepaymentTime());
                    p.put("effectiveMoney", trade.getEffectiveMoney());
                    p.put("borrowStatus", trade.getBorrowTenderStatus());
                    if (trade.getBorrowTenderStatus() == 1) {
                        p.put("borrowTenderStatusName", "还款中");
                        p.put("borrowTenderStatus", true);
                    } else if (trade.getBorrowTenderStatus() == 2) {
                        p.put("borrowTenderStatusName", "还款结束");
                        p.put("borrowTenderStatus", true);
                    } else if (trade.getBorrowTenderStatus() == -1) {
                        p.put("borrowTenderStatusName", "投标失败");
                        p.put("borrowTenderStatus", false);
                    } else if (trade.getBorrowTenderStatus() == 0) {
                        p.put("borrowTenderStatusName", "Web");
                        p.put("borrowTenderStatus", false);
                    }
                    resultList.add(p);
                }

                //翻页
                resultJson.put("pageNums", Math.ceil(wsResponse.getTotalSize() / new Double(wsResponse.getPageSize())));
                resultJson.put("currentPage", currentPage);
            }
            resultJson.put("resultList", resultList);

            //计算转让中的债权数量
            resultStr = tradeCXFWebService.getTransferingCount(JsonUtil.beanToJson(jsonObject));
            JSONObject resultObject = JSONObject.parseObject(resultStr);
            BigDecimal transferingCount = resultObject.getBigDecimal("transferingCount");

            resultJson.put("transferingCount", transferingCount);

            //计算已转出的债权数量
            resultStr = tradeCXFWebService.getTransferOutCount(JsonUtil.beanToJson(jsonObject));
            resultObject = JSONObject.parseObject(resultStr);
            BigDecimal transferOutCount = resultObject.getBigDecimal("transferOutCount");

            resultJson.put("transferOutCount", transferOutCount);

            //计算当前持有债权数量
            resultStr = tradeCXFWebService.getTotalTradeCount(JsonUtil.beanToJson(jsonObject));
            resultObject = JSONObject.parseObject(resultStr);
            BigDecimal totalTradeCount = resultObject.getBigDecimal("totalTradeCount");

            resultJson.put("totalTradeCount", totalTradeCount);


            if (StringUtils.isNotBlank(currentTab)) {
                resultJson.put("currentTab", Integer.parseInt(currentTab));
            } else {
                resultJson.put("currentTab", TradeConstants.TRADE_PAGE_TAB_IS_HOLD);
            }

            resultJson.put("dateFrom", dateFrom);
            resultJson.put("dateTo", dateTo);

            //查询贷款状态码表  BORROW_TENDER_STATUS
            resultJson.put("borrowTenderStatusDic", DicCommonUtils.getCommonList("BORROW_TENDER_STATUS"));
            resultJson.put("resultCode", 0);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            resultJson.put("resultCode", TradeConstants.ERROR_CODE_QUERY_ERROR);
            resultJson.put("msg", TradeConstants.ERROR_MSG_QUERY_ERROR1);
        }

        return resultJson.toString();
    }

    /**
     * 查询转让中的债权
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryTradeTransferingList", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String queryTradeTransferingList(HttpServletRequest request) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("resultCode", AppConsts.WS_RETURN_SUCC);

        try {
            //获取当前登陆用户
            Object userObj = request.getSession().getAttribute("loginUser");

            //获取页面参数
            String currentTab = request.getParameter("currentTab");
            String dateFrom = request.getParameter("dateFrom");
            String dateTo = request.getParameter("dateTo");

            //分页信息
            String currentPage = request.getParameter("currentPage");

            if (userObj != null) {
                User user = (User) userObj;
                long userId = user.getUserId();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userId", userId);
                jsonObject.put("dateFrom", dateFrom);
                jsonObject.put("dateTo", dateTo);

                //查询转让中的债权
                jsonObject.put("status", 1);

                if (currentPage == null || StringUtil.isEmpty(currentPage)) {
                    currentPage = "1";
                }
                jsonObject.put("currentPage", Integer.parseInt(currentPage));

                String resultStr = tradeCXFWebService.searchTradeRequest(JsonUtil.beanToJson(jsonObject));

                //结果集转对象
                TradeResponse wsResponse = JsonUtil.jsonToBean(resultStr, TradeResponse.class);
                List<TradeTransfer> tradeTransfering = null;
                List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
                if (wsResponse != null) {
                    tradeTransfering = wsResponse.getTradeTransfers();

                    for (TradeTransfer trade : tradeTransfering) {
                        HashMap<String, Object> p = new HashMap<String, Object>();
                        p.put("borrowName", trade.getBorrowName());
                        p.put("tenderId", trade.getTenderId());
                        p.put("requestId", trade.getRequestId());
                        p.put("addTime", trade.getAddTime());
                        p.put("repayCapital_funds", trade.getRepayCapital().subtract(trade.getFunds()));
                        p.put("apr", trade.getApr());
                        p.put("remainNumber", trade.getRemainNumber());
                        if (trade.getStatus() == 1) {
                            p.put("statusName", "转让中");
                            p.put("status", true);
                        } else if (trade.getStatus() == 2) {
                            p.put("statusName", "转让成功");
                            p.put("status", true);
                        } else if (trade.getStatus() == 3) {
                            p.put("statusName", "转让失败");
                            p.put("status", false);
                        } else if (trade.getStatus() == 4) {
                            p.put("statusName", "撤消");
                            p.put("status", false);
                        } else if (trade.getStatus() == 5) {
                            p.put("statusName", "过期");
                            p.put("status", false);
                        }
                        resultList.add(p);
                    }

                    //翻页
                    resultJson.put("pageNums", Math.ceil(wsResponse.getTotalSize() / new Double(wsResponse.getPageSize())));
                    resultJson.put("currentPage", currentPage);
                }
                resultJson.put("resultList", resultList);


                //计算转让中的债权数量
                resultStr = tradeCXFWebService.getTransferingCount(JsonUtil.beanToJson(jsonObject));
                JSONObject resultObject = JSONObject.parseObject(resultStr);
                BigDecimal transferingCount = resultObject.getBigDecimal("transferingCount");

                resultJson.put("transferingCount", transferingCount);

                //计算当前持有债权数量
                resultStr = tradeCXFWebService.getTotalTradeCount(JsonUtil.beanToJson(jsonObject));
                resultObject = JSONObject.parseObject(resultStr);
                BigDecimal totalTradeCount = resultObject.getBigDecimal("totalTradeCount");

                resultJson.put("totalTradeCount", totalTradeCount);
            } else {
                resultJson.put("resultCode", TradeConstants.ERROR_CODE_NOT_LOGIN);
                return resultJson.toString(); //未登录
            }

            if (StringUtils.isNotBlank(currentTab)) {
                resultJson.put("currentTab", Integer.parseInt(currentTab));
            } else {
                resultJson.put("currentTab", TradeConstants.TRADE_PAGE_TAB_TRANSFERING);
            }

            resultJson.put("dateFrom", dateFrom);
            resultJson.put("dateTo", dateTo);

            //查询债权转让申请状态码表  TRADE_STATUS
            resultJson.put("tradeStatusDic", DicCommonUtils.getCommonList("TRADE_STATUS"));
        } catch (Exception e) {
            e.printStackTrace();

            resultJson.put("resultCode", TradeConstants.ERROR_CODE_QUERY_ERROR);
            resultJson.put("msg", TradeConstants.ERROR_MSG_QUERY_ERROR2);
            return resultJson.toString(); //未登录
        }

        return resultJson.toString();
    }

    /**
     * 查询已转出的债权
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryTradeTransferOutList", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String queryTradeTransferOutList(HttpServletRequest request) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("resultCode", 0);

        try {
            //获取当前登陆用户
            Object userObj = request.getSession().getAttribute("loginUser");

            //获取页面参数
            String currentTab = request.getParameter("currentTab");
            String dateFrom = request.getParameter("dateFrom");
            String dateTo = request.getParameter("dateTo");

            //分页信息
            String currentPage = request.getParameter("currentPage");

            if (userObj != null) {
                User user = (User) userObj;
                long userId = user.getUserId();
                JSONObject jsonObject = new JSONObject();
                if (TradeConstants.TRADE_PAGE_TAB_TRANSFERED_OUT.equals(currentTab)) {
                    //查询转出的债权
                    jsonObject.put("userId", userId);
                } else if (TradeConstants.TRADE_PAGE_TAB_TRANSFERED_IN.equals(currentTab)) {
                    //查询转入的债权
                    jsonObject.put("packUserId", userId);
                }

                jsonObject.put("dateFrom", dateFrom);
                jsonObject.put("dateTo", dateTo);

                //查询已转出的债权
                jsonObject.put("status", TradeConstants.TRADE_STATUS_SUCCESS);

                if (currentPage == null || StringUtil.isEmpty(currentPage)) {
                    currentPage = "1";
                }
                jsonObject.put("currentPage", Integer.parseInt(currentPage));

                String resultStr = tradeCXFWebService.searchTradeRequest(JsonUtil.beanToJson(jsonObject));
                //结果集转对象
                TradeResponse wsResponse = JsonUtil.jsonToBean(resultStr, TradeResponse.class);
                List<TradeTransfer> tradeTransferOut = null;
                List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
                if (wsResponse != null) {
                    tradeTransferOut = wsResponse.getTradeTransfers();

                    for (TradeTransfer trade : tradeTransferOut) {
                        HashMap<String, Object> p = new HashMap<String, Object>();
                        p.put("borrowName", trade.getBorrowName());
                        p.put("requestId", trade.getRequestId());
                        p.put("outTime", trade.getOutTime());
                        p.put("amount_funds", trade.getAmount().subtract(trade.getFunds()));
                        p.put("apr", trade.getApr());
                        if (trade.getStatus() == 1) {
                            p.put("statusName", "转让中");
                            p.put("status", true);
                        } else if (trade.getStatus() == 2) {
                            p.put("statusName", "转让成功");
                            p.put("status", true);
                        } else if (trade.getStatus() == 3) {
                            p.put("statusName", "转让失败");
                            p.put("status", false);
                        } else if (trade.getStatus() == 4) {
                            p.put("statusName", "撤消");
                            p.put("status", false);
                        } else if (trade.getStatus() == 5) {
                            p.put("statusName", "过期");
                            p.put("status", false);
                        }
                        resultList.add(p);
                    }

                    //翻页
                    resultJson.put("pageNums", Math.ceil(wsResponse.getTotalSize() / new Double(wsResponse.getPageSize())));
                    resultJson.put("currentPage", currentPage);
                }
                resultJson.put("resultList", resultList);

                //计算转让中的债权数量
                JSONObject json = new JSONObject();
                json.put("userId", userId);
                resultStr = tradeCXFWebService.getTransferingCount(JsonUtil.beanToJson(json));
                JSONObject resultObject = JSONObject.parseObject(resultStr);
                BigDecimal transferingCount = resultObject.getBigDecimal("transferingCount");

                resultJson.put("transferingCount", transferingCount);

                //计算当前持有债权数量
                resultStr = tradeCXFWebService.getTotalTradeCount(JsonUtil.beanToJson(json));
                resultObject = JSONObject.parseObject(resultStr);
                BigDecimal totalTradeCount = resultObject.getBigDecimal("totalTradeCount");

                resultJson.put("totalTradeCount", totalTradeCount);

            } else {
                resultJson.put("resultCode", TradeConstants.ERROR_CODE_NOT_LOGIN);
                return resultJson.toString(); //未登录
            }

            if (StringUtils.isNotBlank(currentTab)) {
                resultJson.put("currentTab", Integer.parseInt(currentTab));
            } else {
                resultJson.put("currentTab", TradeConstants.TRADE_PAGE_TAB_IS_HOLD);
            }

            resultJson.put("dateFrom", dateFrom);
            resultJson.put("dateTo", dateTo);

            //查询债权转让申请状态码表  TRADE_STATUS
            resultJson.put("tradeStatusDic", DicCommonUtils.getCommonList("TRADE_STATUS"));
        } catch (Exception e) {
            e.printStackTrace();

            resultJson.put("resultCode", TradeConstants.ERROR_CODE_QUERY_ERROR);
            resultJson.put("msg", TradeConstants.ERROR_MSG_QUERY_ERROR3);
            return resultJson.toString();
        }

        return resultJson.toString();
    }

    /**
     * 检查当前选择的债权是否可转让、获取标的详情
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/checkTradeRequest", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String checkTradeRequest(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        json.put("resultCode", 0);
        try {
            //获取当前登陆用户
            Object userObj = request.getSession().getAttribute("loginUser");
            if (userObj != null) {
                String requestId = request.getParameter("requestId");
                String tenderId = request.getParameter("tenderId");
                if (StringUtils.isBlank(tenderId)) {
                    json.put("resultCode", -1);
                    json.put("msg", "无法获取债权编号，请重新登陆后再操作！");
                    return json.toString();
                }

                User user = (User) userObj;
                long userId = user.getUserId();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("requestId", requestId);

                String resultStr;
                int resultCode = 1;

                //操作类型 1：申请债权转让  2：撤消债权转让
                String type = request.getParameter("type");
                if (type.equals("1")) {
                    //检查系统开关 - 债权转让开关是否为打开状态 1打开 0关闭  update by huna20141216
                    String trade = DicCommonUtils.getDicValue("SYS_CONTROL_SWITCH", "TRADE");
                    if ("0".equals(trade)) {
                        json.put("resultCode", -1);
                        json.put("msg", "抱歉，暂时不能转让债权！");
                        return json.toString();
                    }

                    //检查当前选择的债权是否可转让
                    jsonObject.put("tenderId", tenderId);
                    jsonObject.put("userId", userId);
                    resultStr = tradeCXFWebService.checkTradeTransfer(JsonUtil.beanToJson(jsonObject));
                    WSResponse ws = JsonUtil.jsonToBean(resultStr, WSResponse.class);
                    resultCode = ws.getResultCode();
                    if (resultCode == 0) {
                        //查询债权详情
                        resultStr = tradeCXFWebService.getTradeDetail(JsonUtil.beanToJson(jsonObject));
                        //结果集转对象
                        TradeResponse wsResponse = JsonUtil.jsonToBean(resultStr, TradeResponse.class);
                        TradeTransfer tradeTransfer = null;
                        if (wsResponse != null) {
                            tradeTransfer = wsResponse.getTradeTransfer();
                        }
                        if (tradeTransfer != null) {
                            json.put("result", tradeTransfer);
                        }
                    } else {
                        json.put("resultCode", resultCode);
                        json.put("msg", ws.getDesc());
                        return json.toString();
                    }
                } else {
                    //查询债权转让申请详情
                    resultStr = tradeCXFWebService.getTradeRequestDetail(JsonUtil.beanToJson(jsonObject));
                    json.put("resultCode", resultCode);

                    //结果集转对象
                    TradeResponse wsResponse = JsonUtil.jsonToBean(resultStr, TradeResponse.class);
                    TradeTransfer tradeTransfer = null;
                    if (wsResponse != null) {
                        tradeTransfer = wsResponse.getTradeTransfer();
                    }
                    if (tradeTransfer != null && tradeTransfer.getStatus().longValue() == 1) {
                        //正在转让中的，允许撤消
                        json.put("result", tradeTransfer);
                    } else {
                        json.put("resultCode", -1);
                        json.put("msg", "该债权已不在转让中，无法撤消！");
                        return json.toString();
                    }
                }
            } else {
                //session过期
                json.put("resultCode", TradeConstants.ERROR_CODE_NOT_LOGIN);
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            json.put("resultCode", TradeConstants.ERROR_CODE_CHECK_ERROR);
            json.put("msg", TradeConstants.ERROR_MSG_CHECK_ERROR);
        }
        return json.toString();
    }

    @RequestMapping(value = "/selectTradeRequest", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String selectTradeRequest(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        try {
            //获取当前登陆用户
            Object userObj = request.getSession().getAttribute("loginUser");
            if (userObj == null) {
                json.put("resultCode", TradeConstants.ERROR_CODE_NOT_LOGIN);
                return json.toJSONString();
            }
            User user = (User) userObj;
            long userId = user.getUserId();
            String tenderId = request.getParameter("tenderId");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", userId);
            jsonObject.put("tenderId", tenderId);
            //查询债权详情
            String resultStr = tradeCXFWebService.getTradeDetail(JsonUtil.beanToJson(jsonObject));
            //结果集转对象
            TradeResponse wsResponse = JsonUtil.jsonToBean(resultStr, TradeResponse.class);
            TradeTransfer tradeTransfer = null;
            if (wsResponse != null) {
                tradeTransfer = wsResponse.getTradeTransfer();
            }
            if (tradeTransfer != null) {
                json.put("result", tradeTransfer);
                json.put("resultCode",0);
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            json.put("resultCode",0);
            json.put("msg","获取债权数据失败，请重新尝试");
        }
        return json.toJSONString();
    }

	/**
	 * 发送手机验证码短信
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/sendSMS", produces = { "application/json;charset=UTF-8" })
	public @ResponseBody String sendSMS(HttpServletRequest request, HttpServletResponse response){
		JSONObject resultJson = new JSONObject();
		//session中的用户对象
		Object userObj = request.getSession().getAttribute("loginUser");
		if(null == userObj){
			resultJson.put("resultCode", TradeConstants.ERROR_CODE_NOT_LOGIN);
			return resultJson.toString();
		}
		
		User user = (User)userObj;
		
		//获取用户认证手机号
		JSONObject obj = new JSONObject();
		obj.put("userId", user.getUserId());
		String str = accountOVService.queryMobileByUserId(obj.toString());
		PersonResponse res = JsonUtil.jsonToBean(str, PersonResponse.class);
		MobileAppro mobileAppro =null;
		if(res.getData()!=null){
			mobileAppro =JsonUtil.jsonToBean(res.getData().toString(), MobileAppro.class);
		}
		if(!(mobileAppro != null && "1".equals(mobileAppro.getStatus()))){
			resultJson.put("msg", "您还没有认证您的手机号码，请认证！");
			resultJson.put("resultCode", -1);
			return resultJson.toString();
		}

        //校验手机验证码发送次数限制
        /*if (!MessageUtils.checkSendSMSCount(SmsConsts.BUSICODE_TRADE_REQUEST + user.getMobile(), request, response)) {
            resultJson.put("msg", "您短信发送太频繁，如果无法收到短信请联系客服或使用语音验证码！");
            resultJson.put("resultCode", -30);
            return resultJson.toString();
        }*/
		
		int randCode = (int) Math.round(Math.random() * 8999) + 1000;
        String content = "尊敬的用户：你正在债权转让，验证码是：" + randCode + "，工作人员不会索取，请勿泄漏，若有问题可致电客服：4000 169 521";
		
		JSONObject jsonObject=new JSONObject();	
		jsonObject.put("content", content);
		jsonObject.put("userId", user.getUserId());
		jsonObject.put("phone", user.getMobile());		
		jsonObject.put("sendIp",  HttpUtil.getRealIpAddr(request));
        log.info("traderequest sendSMS req:" + jsonObject.toJSONString());
		String jsonstr= smsCXFService.sendSMS(jsonObject.toJSONString());
        log.info("traderequest sendSMS resp:" + jsonstr);
		WSResponse wsr=JsonUtil.jsonToBean(jsonstr, WSResponse.class);			
		String msg = wsr.getDesc();
		//session保存手机的手机验证码
		if(wsr.getResultCode() == AppConsts.WS_RETURN_SUCC){
			request.getSession().setAttribute(SmsConsts.BUSICODE_TRADE_REQUEST, randCode);
			msg = "验证码已发送到您尾号为"+user.getMobile().substring(7,11)+"的手机号，";
			log.info(content);
			log.info(msg);
		}
		resultJson.put("msg", msg);
		resultJson.put("resultCode", wsr.getResultCode());
		return resultJson.toString();
	}
	
	/**
     * 登陆状态下发送语音验证码
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/sendVoiceSMSLogin", produces = {"application/json;charset=UTF-8"})
    private
    @ResponseBody
    String sendVoiceSMSLogin(HttpServletRequest request, HttpServletResponse response) {
    	JSONObject resultJson = new JSONObject();

        // session中的用户对象
        Object userObj = request.getSession().getAttribute("loginUser");
        if (null == userObj) {
            resultJson.put("resultCode", 400);
            resultJson.put("msg", "会话失效，请重新登录");
            return resultJson.toString();
        }

        User user = (User) userObj;

        //获取用户认证手机号
  		JSONObject obj = new JSONObject();
  		obj.put("userId", user.getUserId());
  		String str1 = accountOVService.queryMobileByUserId(obj.toString());
  		PersonResponse res = JsonUtil.jsonToBean(str1, PersonResponse.class);
  		MobileAppro mobileAppro =null;
  		if(res.getData()!=null){
  			mobileAppro =JsonUtil.jsonToBean(res.getData().toString(), MobileAppro.class);
  		}
  		if(!(mobileAppro != null && "1".equals(mobileAppro.getStatus()))){
  			resultJson.put("msg", "您还没有认证您的手机号码;<a href=\"../personal/info.html\"  class=\"ic-red\">立即认证</a>");
  			resultJson.put("resultCode", -1);
  			return resultJson.toString();
  		}
  		
  		 if (!MessageUtils.checkSendSMSCount(SmsConsts.BUSICODE_TRADE_REQUEST_VOICE + user.getMobile(), request, response)) {
             resultJson.put("msg", "您语音发送太频繁，请稍后重试！");
             resultJson.put("resultCode", -2);
             return resultJson.toString();
         }
  		
        int randCode = (int) Math.round(Math.random() * 8999) + 1000;

        // 功能编号
        String busiCode = SmsConsts.BUSICODE_MOBILE_APPRO;
        String msg ="";
        int resultCode = 0;
        try{
            obj.put("verifyCode", randCode);
            obj.put("phone", user.getMobile());
            obj.put("sendIp", HttpUtil.getRealIpAddr(request));
            obj.put("userId", user.getUserId());
            obj.put("busiCode", busiCode);
            String str = smsCXFService.sendVoiceSMS(obj.toJSONString());
            WSMapResponse ws = JsonUtil.jsonToBean(str, WSMapResponse.class);
            resultCode = ws.getResultCode();
            msg = ws.getDesc();
            if (ws.getResultCode() == AppConsts.WS_RETURN_SUCC) {
                request.getSession().setAttribute(SmsConsts.BUSICODE_TRADE_REQUEST, randCode);
                log.info("语音验证码发送成功,验证码为："+ randCode);
            } else {
                resultCode = -2;
                msg = ws.getDesc();
                log.info("语音验证码发送失败："+msg);
            }
        }catch (Exception e){
            msg = "获取语音验证码失败，请刷新重试";
            resultCode = -3;
            log.error("sendVoiceSMSLogin ERROR:" + e.getMessage(),e);
        }
        resultJson.put("msg", msg);
        resultJson.put("resultCode", resultCode);
        return resultJson.toString();
    }
	
    /**
     * 保存债权转让请求前校验支付密码及验证码
     *
     * @param request
     * @param response
     * @param session
     * @return
     */
    @RequestMapping(value = "/checkBeforeTradeRequest", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String checkBeforeTradeRequest(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        JSONObject json = new JSONObject();
        int resultCode = AppConsts.WS_RETURN_SUCC;
        String msg = "";
        try {
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
            if (userObj != null) {
                User user = (User) userObj;

                log.info("traderequest checkBeforeTradeRequest verifyCode.");
                //校验验证码
                String verifyCode = request.getParameter("verifyCode");
                Object randCode = request.getSession().getAttribute(SmsConsts.BUSICODE_TRADE_REQUEST);
                if (!(randCode != null && verifyCode != null && randCode.toString().toLowerCase().equals(verifyCode.toLowerCase()))) {
                    json.put("resultCode", -1);
                    json.put("msg", "verifyCode:验证码错误，请重新输入！");
                    return json.toString();
                }

                //校验支付密码
                String md5pwd = request.getParameter("payPassword");
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("userId", user.getUserId());
                jsonObj.put("password", EscapeCode.Encryption(md5pwd));
                jsonObj.put("ip", HttpUtil.getRealIpAddr(request));
                String browser = request.getHeader("User-Agent");
                browser = browser.length() > 200 ? browser.substring(0,200):browser;
                jsonObj.put("browser", browser);
                String resultStr = userCXFService.checkPayPassword(JsonUtil.beanToJson(jsonObj));
                UserResponse userResponse = JsonUtil.jsonToBean(resultStr, UserResponse.class);
                if (userResponse != null) {
                    switch (userResponse.getResultCode()) {
                        case -1:
                            json.put("resultCode", -20);
                            json.put("msg", "payPassword:获取支付密码异常！");
                            return json.toString();
                        case -2:
                            json.put("resultCode", -21);
                            json.put("msg", "payPassword:支付密码错误，请重新输入！");
                            return json.toString();
                        case 220:
                            json.put("resultCode", -22);
                            json.put("msg", "payPassword:您还未设置支付密码，请<a href='../../personal/info.html' target='_black'>设置</a>！");
                            return json.toString();
                        case 230:
                            json.put("resultCode", -23);
                            json.put("msg", "payPassword:支付密码和登录密码相同，为保证您的资金安全，请修改支付密码！");
                            return json.toString();
                        default:
                    }
                }

                //检查当前选择的债权是否可转让
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("tenderId", request.getParameter("tenderId"));
                jsonObject.put("userId", user.getUserId());
                resultStr = tradeCXFWebService.checkTradeTransfer(JsonUtil.beanToJson(jsonObject));
                WSResponse wsResponse = JsonUtil.jsonToBean(resultStr, WSResponse.class);
                resultCode = wsResponse.getResultCode();
                if (resultCode == 0) {
                    //查询债权详情
                    resultStr = tradeCXFWebService.getTradeDetail(JsonUtil.beanToJson(jsonObject));
                    //结果集转对象
                    TradeResponse ws = JsonUtil.jsonToBean(resultStr, TradeResponse.class);
                    TradeTransfer tradeTransfer = null;
                    if (ws != null) {
                        tradeTransfer = ws.getTradeTransfer();
                    }
                    if (tradeTransfer != null) {
                        json.put("result", tradeTransfer);
                    }
                    
                    //清空session中的验证码
                    request.getSession().removeAttribute(SmsConsts.BUSICODE_TRADE_REQUEST);
                } else {
                    json.put("resultCode", resultCode);
                    json.put("msg", wsResponse.getDesc());
                    return json.toString();
                }
            } else {
                resultCode = TradeConstants.ERROR_CODE_NOT_LOGIN;
                msg = TradeConstants.ERROR_MSG_NOT_LOGIN;
            }
        } catch (Exception e) {
            resultCode = TradeConstants.ERROR_CODE_OTHER;
            msg = TradeConstants.ERROR_MSG_OTHER1;
            log.error(e.getMessage(),e);
        }
        json.put("resultCode", resultCode);
        json.put("msg", msg);
        return json.toString();
    }

    /**
     * 保存债权转让申请信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/saveTradeRequest", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String saveTradeRequest(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        int resultCode = 0;
        String msg = "";

        try {
            //获取当前登陆用户
            Object userObj = request.getSession().getAttribute("loginUser");
            if (userObj != null) {
                //债权编号
                String tenderId = request.getParameter("tenderId");
                //附加值金额
                String fundsMoneyStr = request.getParameter("fundsMoney");
                //转让备注
                String note = request.getParameter("note");

                BigDecimal fundsMoney = BigDecimal.ZERO;
                if (StringUtils.isNotBlank(fundsMoneyStr)) {
                    fundsMoney = BigDecimal.valueOf(Double.parseDouble(fundsMoneyStr));
                }

                String realIpAddr = HttpTookit.getRealIpAddr(request);
                String clientIp = "";
                if (StringUtils.isNotBlank(realIpAddr)) {
                    clientIp = realIpAddr.split("@")[0];
                }

                User user = (User) userObj;
                long userId = user.getUserId();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userId", userId);
                jsonObject.put("fundsMoney", fundsMoney);
                jsonObject.put("note", note);
                jsonObject.put("clientIp", clientIp);
                jsonObject.put("tenderId", tenderId);
                jsonObject.put("terminalVer",HttpTookit.getRequestTerminal(request));

                //查询可转让的债权
                String resultStr = tradeCXFWebService.saveTradeRequest(JsonUtil.beanToJson(jsonObject));
                //结果集转对象
                TradeResponse wsResponse = JsonUtil.jsonToBean(resultStr, TradeResponse.class);
                if (wsResponse != null) {
                    if (wsResponse.getResultCode() < 0) {
                        resultCode = wsResponse.getResultCode();
                        msg = wsResponse.getDesc();
                    } else {
                        /****************************************** 发送短信 **************************************/
                        //债权价值
                        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(request.getParameter("repayCapital")));

                        jsonObject.clear();
                        jsonObject.put("tempCode", SmsTemplateConstant.TRADE_REQUEST_MSG); //短信模板
                        jsonObject.put("userId", userId);                                  //用户ID
                        jsonObject.put("amount", amount);                                  //债权价值
                        jsonObject.put("funds", fundsMoney);                               //让利金额
                        jsonObject.put("tradeMoney", amount.subtract(fundsMoney));         //转让价格
                        jsonObject.put("clientIp", clientIp);                              //客户端IP
                        tradeCXFWebService.sendSmsMessage(JsonUtil.beanToJson(jsonObject));
                    }
                }
            } else {
                resultCode = TradeConstants.ERROR_CODE_NOT_LOGIN;
                msg = TradeConstants.ERROR_MSG_NOT_LOGIN;
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            resultCode = TradeConstants.ERROR_CODE_OTHER;
            msg = TradeConstants.ERROR_MSG_OTHER1;
        }
        json.put("resultCode", resultCode);
        json.put("msg", msg);
        return json.toString();
    }

    /**
     * 撤消债权转让申请
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/rollbackRadeRequest", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String rollbackRadeRequest(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("resultCode", AppConsts.WS_RETURN_SUCC);
        try {
            //判断当前token是否有效
            if (!TokenUtil.validToken(request)) {
                jsonObject.put("resultCode", Constant.TOKEN_INVALID_ERROR);
                jsonObject.put("msg", "页面已过期，请重新尝试");
                return jsonObject.toString();
            }
            // 销毁token
            //获取当前登陆用户
            Object userObj = request.getSession().getAttribute("loginUser");
            if (userObj != null) {
                //债权申请编号
                String requestId = request.getParameter("requestId");
                jsonObject.put("requestId", requestId);

                //撤消债权转让申请
                log.info("rollbackRadeRequest req = " + jsonObject.toJSONString());
                String resultStr = tradeCXFWebService.rollbackRadeRequest(JsonUtil.beanToJson(jsonObject));
                log.info("rollbackRadeRequest resp = " + resultStr);
                WSResponse ws = JsonUtil.jsonToBean(resultStr, WSResponse.class);
                jsonObject.put("resultCode", ws.getResultCode());
                jsonObject.put("msg", ws.getDesc());
            } else {
                jsonObject.put("resultCode", TradeConstants.ERROR_CODE_NOT_LOGIN);
                jsonObject.put("msg", TradeConstants.ERROR_MSG_NOT_LOGIN);
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            jsonObject.put("resultCode", TradeConstants.ERROR_CODE_OTHER);
            jsonObject.put("msg", TradeConstants.ERROR_MSG_OTHER1);
        }
        return jsonObject.toString();
    }


    /**
     * 查询首页债权转让列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/tradeRequestSearch", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String tradeRequestSearch(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();

        String pageSize = request.getParameter("pageSize");

        if (StringUtils.isNotBlank(pageSize)) {
            jsonObject.put("pageSize", Integer.parseInt(pageSize));
        }

        //查询转让中的债权申请列表
        String moreStatus = TradeConstants.TRADE_STATUS_TRANSFERING + "," + TradeConstants.TRADE_STATUS_SUCCESS;
        jsonObject.put("moreStatus", moreStatus);
        String resultStr = tradeCXFWebService.queryTradeRequest(JsonUtil.beanToJson(jsonObject));
        //结果集转对象
        TradeResponse wsResponse = JsonUtil.jsonToBean(resultStr, TradeResponse.class);
        List<TradeTransfer> tradeTransfering = null;
        if (wsResponse != null) {
            tradeTransfering = wsResponse.getTradeTransfers();
        }
        jsonObject.put("resultList", tradeTransfering);
        
        //查询债权标的类型，在页面上显示正确类型图标  BORROW_SHOW_LABEL
        List<DicCommonVo> showLabelList = DicCommonUtils.getCommonList("BORROW_SHOW_LABEL");
        jsonObject.put("showLabelList", showLabelList);

        return jsonObject.toString();
    }

    /**
     * 查询所有债权转让列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/tradeRequestMoreSearch")
    public ModelAndView tradeRequestMoreSearch(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("pages/trade/jsp/trade_request_list");

        return mv;
    }


    /**
     * 查询所有债权转让列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryTradeRequestMore", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String queryTradeRequestMore(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();

        String remainNumber = request.getParameter("remainNumber");
        String rateYear = request.getParameter("rateYear");
        String borrowType = request.getParameter("borrowType");
        String currentPage = request.getParameter("currentPage");

        if (StringUtils.isNotBlank(remainNumber)) {
            jsonObject.put("remainNumber", remainNumber);
        }
        if (StringUtils.isNotBlank(rateYear)) {
            jsonObject.put("rateYear", rateYear);
        }
        if (StringUtils.isNotBlank(borrowType)) {
            jsonObject.put("borrowType", Integer.parseInt(borrowType));
        }
        if (currentPage == null || StringUtil.isEmpty(currentPage)) {
            currentPage = "1";
        }
        jsonObject.put("currentPage", Integer.parseInt(currentPage));

        //查询转让中、转让成功的债权申请列表
        String moreStatus = TradeConstants.TRADE_STATUS_TRANSFERING + "," + TradeConstants.TRADE_STATUS_SUCCESS;
        jsonObject.put("moreStatus", moreStatus);
        //默认每页显示10条记录
        jsonObject.put("pageSize", 10);

        String resultStr = tradeCXFWebService.queryTradeRequest(JsonUtil.beanToJson(jsonObject));
        //结果集转对象
        TradeResponse wsResponse = JsonUtil.jsonToBean(resultStr, TradeResponse.class);
        List<TradeTransfer> tradeTransfering = null;
        List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
        if (wsResponse != null) {
            tradeTransfering = wsResponse.getTradeTransfers();

            for(TradeTransfer trade : tradeTransfering){
                HashMap<String, Object> p = new HashMap<String, Object>();
                p.put("requestId", trade.getRequestId());
                p.put("tenderId", trade.getTenderId());
                p.put("amount", trade.getRepayCapital().subtract(trade.getFunds()));
                p.put("collectionAmount", trade.getRepaymentAmount());
                p.put("leftMonth", trade.getRemainNumber()==null?0:trade.getRemainNumber().intValue());
                p.put("apr", trade.getApr());
                p.put("rate", trade.getRate());
                p.put("transFee", trade.getTransFee());
                if (trade.getStatus() == 1) {
                    p.put("status", true);
                } else if (trade.getStatus() == 2) {
                    p.put("status", false);
                }
                p.put("rep_fun", trade.getRepayCapital().subtract(trade.getFunds()));
                p.put("borrowName", trade.getBorrowName());
                p.put("remainNumber", trade.getRemainNumber());

                resultList.add(p);
            }

            //翻页
            jsonObject.put("pageNums", wsResponse.getTotalSize());
            jsonObject.put("currentPage", Integer.parseInt(currentPage));
        }
        jsonObject.put("resultList", resultList);
        jsonObject.put("remainNumber", remainNumber);
        jsonObject.put("rateYear", rateYear);
        jsonObject.put("borrowType", borrowType);

        //查询债权标的类型，在页面上显示正确类型图标  BORROW_SHOW_LABEL
        List<DicCommonVo> showLabelList = DicCommonUtils.getCommonList("BORROW_SHOW_LABEL");
        jsonObject.put("showLabelList", showLabelList);
        
        return jsonObject.toString();
    }

    /**
     * 贷款详情查询
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/requestDetail/{id}", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String requestDetail(HttpServletRequest request, @PathVariable("id") String id) {
        JSONObject returnJson = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        //获取债权详细信息
        jsonObject.put("requestId", id);
        String resultStr = tradeCXFWebService.getTradeRequestDetail(JsonUtil.beanToJson(jsonObject));

        //结果集转对象
        TradeResponse wsResponse = JsonUtil.jsonToBean(resultStr, TradeResponse.class);
        TradeTransfer tradeTransfer = null;
        tradeTransfer = wsResponse.getTradeTransfer();
        if (tradeTransfer != null && (tradeTransfer.getStatus().longValue() == 1 || tradeTransfer.getStatus().longValue() == 2)) {
            returnJson.put("tradeRequestDetail", tradeTransfer);
        } else {
            returnJson.put("msg", "连接已失效！");
            returnJson.put("bankUrl", "/traderequest/tradeRequestMoreSearch.html");
            return "model";
        }

        //获取原始标的详细信息
        if (tradeTransfer != null) {
            //获取费用信息
            String accountCostStr = accountCashCXFService.getAccountCost("");
           // log.info("getAccountCost response = " + accountCostStr);
            WSModelResponse res=JsonUtil.jsonToBean(accountCostStr.toString(),WSModelResponse.class);
            if(res.getResultCode()>=0 && res.getData()!=null){
                JSONObject feeInfo = JsonUtil.jsonToBean(res.getData().toString(),JSONObject.class);
                returnJson.put("feeInfo", feeInfo);
            }
            jsonObject.clear();
            jsonObject.put("borrowId", tradeTransfer.getBorrowId());
            resultStr = borrowQueryCXFService.searchBorrowDetailForTender(JsonUtil.beanToJson(jsonObject));

            //结果集转对象
            BorrowQueryWsResponse bWsResponse = JsonUtil.jsonToBean(resultStr, BorrowQueryWsResponse.class);

            //当前服务器时间戳
            returnJson.put("currentTime", System.currentTimeMillis());

            //计算截止日
            int remainingDays = 0;
            //转让截止日期
            String remainingDate = tradeTransfer.getNextRepaymentTime();
            if (tradeTransfer.getNextRepaymentTime() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(new Date());
                    long time1 = cal.getTimeInMillis();
                    cal.setTime(sdf.parse(tradeTransfer.getNextRepaymentTime()));
                    cal.add(Calendar.DAY_OF_YEAR, -1);
                    remainingDate = sdf.format(cal.getTime());
                    long time2 = cal.getTimeInMillis();
                    long between_days = (time2 - time1) / (1000 * 3600 * 24);
                    remainingDays = Integer.parseInt(String.valueOf(between_days)) + 1;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            returnJson.put("remainingDays", remainingDays);
            returnJson.put("remainingDate", remainingDate);

            //贷款信息
            BorrowQuery detail = bWsResponse.getBorrowQuery();
            detail.setUserName("");
            returnJson.put("borrowDetail", detail);

            // 借款人，发标信息
            String str = borrowQueryCXFService.selectBorrowDetail(JsonUtil.beanToJson(tradeTransfer.getBorrowId()));
            PersonResponse personResponse = JsonUtil.jsonToBean(str, PersonResponse.class);
            if(personResponse.getData() != null) {
                JSONObject borrow1 = JSONObject.parseObject(personResponse.getData().toString());
                returnJson.put("borrow", borrow1);
            }

            //基本信息
            BaseInfo baseInfo = bWsResponse.getBaseInfo();
            //如果BaseInfo userType==2借款人为企业，获取企业信息
            if("2".equals(baseInfo.getUserType())) {
                CompanyInfo companyInfo = new CompanyInfo();
                if(bWsResponse.getCompanyInfo()!=null) {
                    companyInfo = bWsResponse.getCompanyInfo();
                    //企业个别字段过滤
                    companyInfo = filterCompanyInfo(companyInfo);
                    //公司地址
                    companyInfo.setCompanyAddress(getAddress(companyInfo.getCompanyAddress()));
                    //注册地址
                    companyInfo.setRegisteredAddress(getAddress(companyInfo.getRegisteredAddress()));
                    //注册资本
                    Double registeredCapital = companyInfo.getRegisteredCapital();
                    if(registeredCapital != null && registeredCapital > 0 ){
                        //保留两位小数且不用科学计数法
                        NumberFormat nf = NumberFormat.getInstance();
                        nf.setGroupingUsed(false);
                        request.setAttribute("registeredCapital",nf.format(registeredCapital));
                    }else{
                        request.setAttribute("registeredCapital","0");
                    }
                }
                returnJson.put("companyInfo", companyInfo);
            }
            String realName = baseInfo.getRealName();
            if(realName != null && !"".equals(realName)){
                realName = realName.substring(0,1) + "**";
                baseInfo.setRealName(realName);
            } else {
                baseInfo.setRealName("");
            }
            String idCardNo = baseInfo.getIdCardNo();
            if(idCardNo != null && !"".equals(idCardNo)) {
                idCardNo = idCardNo.substring(0,6) + "************";
                baseInfo.setIdCardNo(idCardNo);
            } else {
                baseInfo.setIdCardNo("");
            }
            baseInfo.setHomeTel("");
            baseInfo.setIdCardNo("");
            returnJson.put("baseInfo", baseInfo);

            //逾期次数
            int overdue = bWsResponse.getOverdue();
            returnJson.put("overdue", overdue);
            BigDecimal overdueAccount = bWsResponse.getOverdueAccount();
            overdueAccount = overdueAccount == null ? new BigDecimal(0) : overdueAccount;
            returnJson.put("overdueAccount", overdueAccount);

            //是否购房
            int isHousePurchase = bWsResponse.getIsHousePurchase();
            returnJson.put("isHousePurchase", isHousePurchase);

            //是否购车;
            int isCarPurchase = bWsResponse.getIsCarPurchase();
            returnJson.put("isCarPurchase", isCarPurchase);

            //正常还清贷款笔数
            int accountNomalPay = bWsResponse.getAccountNomalPay();
            returnJson.put("accountNomalPay", accountNomalPay);

            //逾期还清贷款笔数
            int accountLazyPay = bWsResponse.getAccountLazyPay();
            returnJson.put("accountLazyPay", accountLazyPay);

            //首次借款时间
            String borrowFirstTime = bWsResponse.getBorrowFirstTime();
            returnJson.put("borrowFirstTime", borrowFirstTime);

            //成功借款次数
            int borrowCount = bWsResponse.getBorrowCount();
            returnJson.put("borrowCount", borrowCount);

            //显示审批资料信息
            List<UserCreditStuffShowInfo> userCreditStuffDics = bWsResponse.getUserCreditStuffDics();
            returnJson.put("userCreditStuffDics", userCreditStuffDics);

            //待还总额
            BigDecimal repaymentSum = bWsResponse.getRepaymentSum();
            returnJson.put("repaymentSum", repaymentSum);

            //获取体验金及抵用券(即红包)
            jsonObject.put("userId", detail.getUserId());
            //获取借款人的资金信息
            resultStr = accountQueryCXFService.selectUserAccountAndCoupon(jsonObject.toJSONString());
            WSMapResponse wsMapResponse = JsonUtil.jsonToBean(resultStr, WSMapResponse.class);

            //账户信息
            returnJson.put("account", wsMapResponse.getMap().get("defaultAccount"));

            /************************************************ 查询码表 ***************************************************/

            if (detail != null) {
                //查询借款用途码表  BORROW_USE
                returnJson.put("borrowUse", DicCommonUtils.getDicValue("BORROW_USE", detail.getUse()));

                //查询还款方式码表  BORROW_PAYMENTMETHOD
                returnJson.put("borrowPaymentmeyhod", DicCommonUtils.getDicValue("BORROW_PAYMENTMETHOD", detail.getPaymentMethod()));

                //所在地
                if (StringUtils.isBlank(detail.getProvince()) && StringUtils.isBlank(detail.getCity())) {
                    returnJson.put("provinceCity", "未知");
                } else {
                    returnJson.put("provinceCity", baseInfo.getProvince() + " " + baseInfo.getCity());
                }

                //信用等级 CREDIT_LEVELS
                returnJson.put("borrowCreditLevels", DicCommonUtils.getDicValue("CREDIT_LEVELS", detail.getCreditLevel()));
            }

            if (baseInfo != null) {
                //性别 GENDER_TYPE
                returnJson.put("baseInfoGender", DicCommonUtils.getDicValue("GENDER_TYPE", baseInfo.getGender()));

                //婚姻状况 MARITAL_STATUS
                returnJson.put("baseInfoMaritalStatus", DicCommonUtils.getDicValue("MARITAL_STATUS", baseInfo.getMaritalStatus()));

                //文化程度 DEGREE_TYPE
                returnJson.put("baseInfoDegree", DicCommonUtils.getDicValue("DEGREE_TYPE", baseInfo.getDegree()));
            }
        }
        return returnJson.toJSONString();
    }

    /**
     * 企业信息字段过滤
     * @param companyInfo
     * @return
     */
    public CompanyInfo filterCompanyInfo(CompanyInfo companyInfo){
        String x = "";
        //过滤企业名称
        if(null != companyInfo.getCompanyName() && "" != companyInfo.getCompanyName()){
            String companyName = companyInfo.getCompanyName();
            if(companyName.length() > 0 && companyName.length() == 1){
                companyInfo.setCompanyName("*");
            }else if(companyName.length() > 1 && companyName.length() <= 2){
                companyName = companyName.substring(companyName.length()- 1);//一个参数表示截取传递的序号之后的部分
                companyInfo.setCompanyName("*"+companyName);
            }else if(companyName.length() > 2 && companyName.length() <= 4){
                for (int i = 2; i < companyName.length(); i++){
                    x = x + "*";
                }
                companyName = companyName.substring(companyName.length()- 2);//一个参数表示截取传递的序号之后的部分
                companyInfo.setCompanyName(x+companyName);
            }else{
                for (int i = 4; i < companyName.length(); i++){
                    x = x + "*";
                }
                companyName = companyName.substring(companyName.length()- 4);//一个参数表示截取传递的序号之后的部分
                companyInfo.setCompanyName(x+companyName);
            }
        }
        x = "";
        //过滤企业法人
        if(null != companyInfo.getComRepName() && "" != companyInfo.getComRepName()){
            String comRepName = companyInfo.getComRepName();
            if(comRepName.length() > 1){
                for (int i = 1; i < comRepName.length(); i++){
                    x = x + "*";
                }
                companyInfo.setComRepName(comRepName.substring(0,1)+x);
            }
        }
        return companyInfo;
    }

    /**
     * 地址转换
     * @param oldAddress
     * @return
     */
    public String  getAddress(String oldAddress){
        String[] allAddress = new String[]{"省","市","区","县","路","街道","街","段","弄","号","栋","幢","楼","F","室"};
        String address = "";
        for(int i = 0; i < allAddress.length; i++){
            //截取省份
            int addressIndex = oldAddress.indexOf(allAddress[i]);
            for(int j = 1; j <= addressIndex; j++){
                if(j == addressIndex){
                    address = address + "**"+allAddress[i];
                    oldAddress = oldAddress.substring(addressIndex+1,oldAddress.length());
                }
            }
        }
        if(!"".equals(oldAddress) && !"".equals(address)){
            address = address + "**";
        }
        if("".equals(address)){
            address = "**";
        }
        return address;
    }

    /**
     * 确认投标
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/requestQuick", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String requestQuick(HttpServletRequest request, @RequestParam(required = true, value = "requestId") String requestId) {
        JSONObject returnJson = new JSONObject();

        long userId = -1;
        //获取当前登陆用户
        Object userObj = request.getSession().getAttribute("loginUser");
        if (userObj != null) {
            User user = (User) userObj;
            userId = user.getUserId();
            //检测用户账户情况
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", userId);
            String resultStr = accountQueryCXFService.selectUserAccountAndCoupon(jsonObject.toJSONString());
            WSMapResponse wsResponse = JsonUtil.jsonToBean(resultStr, WSMapResponse.class);
            returnJson.put("defaultAccount", wsResponse.getMap().get("defaultAccount"));
            returnJson.put("experienceAccount", wsResponse.getMap().get("experienceAccount"));
            returnJson.put("coupons", wsResponse.getMap().get("coupons"));

        }

        JSONObject jsonObject = new JSONObject();
        //获取债权详细信息
        jsonObject.put("requestId", requestId);
        String resultStr = tradeCXFWebService.getTradeRequestDetail(JsonUtil.beanToJson(jsonObject));


        //结果集转对象
        TradeResponse wsResponse = JsonUtil.jsonToBean(resultStr, TradeResponse.class);
        TradeTransfer tradeTransfer = null;
        tradeTransfer = wsResponse.getTradeTransfer();
        if (tradeTransfer != null && (tradeTransfer.getStatus().longValue() == 1 || tradeTransfer.getStatus().longValue() == 2)) {
            returnJson.put("tradeRequestDetail", tradeTransfer);
        } else {
            returnJson.put("msg", "连接已失效！");
            returnJson.put("bankUrl", "/traderequest/tradeRequestMoreSearch.html");
            return "model";
        }

        //获取原始标的详细信息
        if (tradeTransfer != null) {
            jsonObject.clear();
            jsonObject.put("borrowId", tradeTransfer.getBorrowId());
            resultStr = borrowQueryCXFService.searchBorrowDetailForTender(JsonUtil.beanToJson(jsonObject));

            //结果集转对象
            BorrowQueryWsResponse bWsResponse = JsonUtil.jsonToBean(resultStr, BorrowQueryWsResponse.class);

            //当前服务器时间戳
            returnJson.put("currentTime", System.currentTimeMillis());

            //计算截止日
            int remainingDays = 0;
            //转让截止日期
            String remainingDate = tradeTransfer.getNextRepaymentTime();
            if (tradeTransfer.getNextRepaymentTime() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(new Date());
                    long time1 = cal.getTimeInMillis();
                    cal.setTime(sdf.parse(tradeTransfer.getNextRepaymentTime()));
                    cal.add(Calendar.DAY_OF_YEAR, -1);
                    remainingDate = sdf.format(cal.getTime());
                    long time2 = cal.getTimeInMillis();
                    long between_days = (time2 - time1) / (1000 * 3600 * 24);
                    remainingDays = Integer.parseInt(String.valueOf(between_days)) + 1;
                } catch (ParseException e) {
                    log.error(e.getMessage(),e);
                }
            }
            returnJson.put("remainingDays", remainingDays);
            returnJson.put("remainingDate", remainingDate);

            //贷款信息
            BorrowQuery detail = bWsResponse.getBorrowQuery();
            detail.setUserName("");
            returnJson.put("borrowDetail", detail);

            //基本信息
            BaseInfo baseInfo = bWsResponse.getBaseInfo();
            baseInfo.setRealName("");
            baseInfo.setIdCardNo("");
            baseInfo.setCompanyTel("");
            baseInfo.setHomeTel("");
            returnJson.put("baseInfo", baseInfo);

            //逾期次数
            int overdue = bWsResponse.getOverdue();
            returnJson.put("overdue", overdue);

            //是否购房
            int isHousePurchase = bWsResponse.getIsHousePurchase();
            returnJson.put("isHousePurchase", isHousePurchase);

            //是否购车;
            int isCarPurchase = bWsResponse.getIsCarPurchase();
            returnJson.put("isCarPurchase", isCarPurchase);

            //正常还清贷款笔数
            int accountNomalPay = bWsResponse.getAccountNomalPay();
            returnJson.put("accountNomalPay", accountNomalPay);

            //逾期还清贷款笔数
            int accountLazyPay = bWsResponse.getAccountLazyPay();
            returnJson.put("accountLazyPay", accountLazyPay);

            //首次借款时间
            String borrowFirstTime = bWsResponse.getBorrowFirstTime();
            returnJson.put("borrowFirstTime", borrowFirstTime);

            //成功借款次数
            int borrowCount = bWsResponse.getBorrowCount();
            returnJson.put("borrowCount", borrowCount);

            //显示审批资料信息
            List<UserCreditStuffShowInfo> userCreditStuffDics = bWsResponse.getUserCreditStuffDics();
            returnJson.put("userCreditStuffDics", userCreditStuffDics);

            //待还总额
            BigDecimal repaymentSum = bWsResponse.getRepaymentSum();
            returnJson.put("repaymentSum", repaymentSum);

            //获取体验金及抵用券(即红包)
            jsonObject.put("userId", detail.getUserId());
            //获取借款人的资金信息
            resultStr = accountQueryCXFService.selectUserAccountAndCoupon(jsonObject.toJSONString());
            WSMapResponse wsMapResponse = JsonUtil.jsonToBean(resultStr, WSMapResponse.class);

            //账户信息
            returnJson.put("account", wsMapResponse.getMap().get("defaultAccount"));

            /************************************************ 查询码表 ***************************************************/

            if (detail != null) {
                //查询借款用途码表  BORROW_USE
                returnJson.put("borrowUse", DicCommonUtils.getDicValue("BORROW_USE", detail.getUse()));

                //查询还款方式码表  BORROW_PAYMENTMETHOD
                returnJson.put("borrowPaymentmeyhod", DicCommonUtils.getDicValue("BORROW_PAYMENTMETHOD", detail.getPaymentMethod()));

                //所在地
                if (StringUtils.isBlank(detail.getProvince()) && StringUtils.isBlank(detail.getCity())) {
                    returnJson.put("provinceCity", "未知");
                } else {
                    returnJson.put("provinceCity", baseInfo.getProvince() + " " + baseInfo.getCity());
                }

                //信用等级 CREDIT_LEVELS
                returnJson.put("borrowCreditLevels", DicCommonUtils.getDicValue("CREDIT_LEVELS", detail.getCreditLevel()));
            }

            if (baseInfo != null) {
                //性别 GENDER_TYPE
                returnJson.put("baseInfoGender", DicCommonUtils.getDicValue("GENDER_TYPE", baseInfo.getGender()));

                //婚姻状况 MARITAL_STATUS
                returnJson.put("baseInfoMaritalStatus", DicCommonUtils.getDicValue("MARITAL_STATUS", baseInfo.getMaritalStatus()));

                //文化程度 DEGREE_TYPE
                returnJson.put("baseInfoDegree", DicCommonUtils.getDicValue("DEGREE_TYPE", baseInfo.getDegree()));
            }
        }

        returnJson.put("userId", userId);

        return returnJson.toJSONString();
    }
    
    /**
     * 查询所有债权转让列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/calApr")
    public String calApr(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("currentPage",request.getParameter("currentPage"));

        //查询转让中、转让成功的债权申请列表
        String moreStatus = TradeConstants.TRADE_STATUS_TRANSFERING + "," + TradeConstants.TRADE_STATUS_SUCCESS;
        jsonObject.put("moreStatus", moreStatus);
        //默认每页显示10条记录
        jsonObject.put("pageSize", 100);
        jsonObject.put("isCalApr", request.getParameter("isCalApr"));
        
        String resultStr = tradeCXFWebService.queryTradeRequest(JsonUtil.beanToJson(jsonObject));
        //结果集转对象
        TradeResponse wsResponse = JsonUtil.jsonToBean(resultStr, TradeResponse.class);
        List<TradeTransfer> tradeTransferList = null;
        if (wsResponse != null) {
        	tradeTransferList = wsResponse.getTradeTransfers();
        }
        
        List<HashMap> resultList = new ArrayList<HashMap>();
        for(TradeTransfer trade : tradeTransferList){
        	HashMap p = new HashMap();
        	p.put("requestId", trade.getRequestId());
        	p.put("tenderId", trade.getTenderId());
        	p.put("amount", trade.getRepayCapital().subtract(trade.getFunds()));
        	p.put("collectionAmount", trade.getRepaymentAmount());
        	p.put("leftMonth", trade.getRemainNumber()==null?0:trade.getRemainNumber().intValue());
        	p.put("apr", trade.getApr());
        	p.put("rate", trade.getRate());
        	p.put("transFee", trade.getTransFee());
        	resultList.add(p);
        }
       
        request.setAttribute("resultList", resultList);

        return "pages/trade/jsp/trade_cal_apr";
    }
    
    /**
     * 逐个计算债权转让实际利率及转让手续费
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/calAprOneByOne", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String calAprOneByOne(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();

        String requestId = request.getParameter("requestId");
        jsonObject.put("requestId", requestId);
        
    	String resultStr = tradeCXFWebService.updateTradeRequestAprAndRate(JsonUtil.beanToJson(jsonObject));
    	WSResponse res = JsonUtil.jsonToBean(resultStr, WSResponse.class);
    	int resultCode = res.getResultCode();
    	String msg = res.getDesc();
    	
        jsonObject.put("resultCode", resultCode);
        jsonObject.put("msg", msg);
        return jsonObject.toString();
    }
}
