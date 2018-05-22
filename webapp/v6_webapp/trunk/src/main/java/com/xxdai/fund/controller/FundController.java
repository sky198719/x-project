package com.xxdai.fund.controller;

import com.alibaba.fastjson.JSONObject;
import com.xxdai.client.CXF_Factory;
import com.xxdai.common.WebException;
import com.xxdai.constant.AppConsts;
import com.xxdai.constant.Constant;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.core.util.lang.DateUtil;
import com.xxdai.encrypt.EscapeCode;
import com.xxdai.external.account.ws.AccountQueryCXFService;
import com.xxdai.external.fund.ws.FundCXFService;
import com.xxdai.external.fundTrade.ws.FundTradeCXFService;
import com.xxdai.external.fundTrade.ws.UserFundTradeCXFService;
import com.xxdai.external.systemHoliday.ws.SystemHolidayCXFService;
import com.xxdai.external.user.ws.User;
import com.xxdai.external.user.ws.UserCXFService;
import com.xxdai.fund.constants.FundConstant;
import com.xxdai.fund.model.Fund;
import com.xxdai.fund.model.FundTotal;
import com.xxdai.fund.model.FundTotalVO;
import com.xxdai.fund.util.FundUtil;
import com.xxdai.plan.constants.AccountConsts;
import com.xxdai.util.CacheUtil;
import com.xxdai.util.Configuration;
import com.xxdai.util.HttpTookit;
import com.xxdai.util.TokenUtil;
import com.xxdai.ws.util.WSModelResponse;
import com.xxdai.ws.util.WSPageResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * Created by chaihuangqi on 2015/8/19.
 */
@Controller
@RequestMapping(value = "/fund")
public class FundController {

    /**
     * 日志记录器
     */
    private static final Logger log = Logger.getLogger(FundController.class);
    /**
     * 账户查询接口
     */
    private AccountQueryCXFService accountQueryCXFService = (AccountQueryCXFService) CXF_Factory.getFactory(AccountQueryCXFService.class, Configuration.getInstance().getValue("trade_url") + "/accountQueryWebService").create();
    /**
     * 用户操作接口
     */
    private UserCXFService userCXFService = (UserCXFService) CXF_Factory.getFactory(UserCXFService.class, Configuration.getInstance().getValue("webService_url") + "/userWebService").create();
    /**
     * 用户操作接口
     */
    private UserFundTradeCXFService userFundTradeCXFService = (UserFundTradeCXFService) CXF_Factory.getFactory(UserFundTradeCXFService.class, Configuration.getInstance().getValue("webService_url") + "/userFundTradeWebService").create();
    /**
     * 节假日接口
     */
    private SystemHolidayCXFService systemHolidayCXFService = (SystemHolidayCXFService) CXF_Factory.getFactory(SystemHolidayCXFService.class, Configuration.getInstance().getValue("trade_url") + "/systemHolidayWebService").create();

    /**
     * 日日盈操作接口
     */
    private FundTradeCXFService fundTradeCXFService = (FundTradeCXFService) CXF_Factory.getFactory(FundTradeCXFService.class, Configuration.getInstance().getValue("trade_url") + "/fundTradeWebService").create();

    private FundCXFService fundCXFService = (FundCXFService)CXF_Factory.getFactory(FundCXFService.class, Configuration.getInstance().getValue("webService_url") + "/fundWebService").create();

    /**
     * 初始化已投资界面
     */
    @RequestMapping(value = "/initInvestedData", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String initInvestedData(HttpServletRequest request) {
        log.info("enter initInvestedData in FundController .");
        JSONObject result = new JSONObject();
        try {
            //用户信息
            User user = (User) request.getSession().getAttribute("loginUser");
            if (user == null) {
                result.put("msg", "会话失效，请重新登录");
                result.put("resultCode", -400);
                return result.toJSONString();
            }

            Fund fund = selectFund();

            //设置查询状态数组,将数据封装成json,传入webservice进行查询
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("fcode", fund.getFcode());
            jsonObject.put("userId", user.getUserId());
            String resultJson = userFundTradeCXFService.selectInvestAmount(jsonObject.toJSONString());
            JSONObject jsonFund = JSONObject.parseObject(resultJson);
            String fundTotalStr = jsonFund.getString("data");
            JSONObject fundTotalJson = JSONObject.parseObject(fundTotalStr);
            result.put("fundTotal", fundTotalJson);

            String resultJson2 = userFundTradeCXFService.selectYesterdayEarnings(jsonObject.toJSONString());
            JSONObject jsonFund2 = JSONObject.parseObject(resultJson2);
            String fundTradeStr = jsonFund2.getString("data");
            JSONObject fundTradeJson = JSONObject.parseObject(fundTradeStr);
            result.put("fundTrade", fundTradeJson);
            result.put("msg", "获取日日盈用户投资信息成功");
        } catch (Exception e) {
            log.error("获取日日盈用户投资信息失败", e);
            result.put("resultCode", 200);
            result.put("msg", "获取日日盈用户投资信息失败");
        }
        return result.toJSONString();
    }

    /**
     * 获取日日盈基金产品信息
     * @return
     * @throws WebException
     */
    public Fund selectFund() throws WebException {
        try {
            //获取当前基金产品
            JSONObject jsonFundObject = new JSONObject();
            String resultFundJson = userFundTradeCXFService.selectFundByStatus(jsonFundObject.toJSONString());
            JSONObject jsonFund = JSONObject.parseObject(resultFundJson);
            if (jsonFund.getIntValue("resultCode") != 0) {
                throw new WebException(-100, "获取日日盈基金产品失败");
            }

            String fundStr = jsonFund.getString("data");
            return JsonUtil.jsonToBean(fundStr, Fund.class);
        } catch (WebException we) {
            throw we;
        } catch (Exception e) {
            log.error("获取日日盈基金产品异常",e);
            throw new WebException(-101,"获取日日盈基金产品异常");
        }
    }

    /**
     * 初始化转入(申购)界面
     */
    @RequestMapping(value = "/initTransferInData", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String initTransferInData(HttpServletRequest request) {
        log.info("enter initTransferInData in FundController .");
        JSONObject result = new JSONObject();
        try {
            //用户信息
            User user = (User) request.getSession().getAttribute("loginUser");
            if (user == null) {
                result.put("msg", "会话失效，请重新登录");
                result.put("resultCode", -400);
                return result.toJSONString();
            }

            Fund fund = selectFund();

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

            String fcode = fund.getFcode();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", user.getUserId());
            jsonObject.put("fcode", fcode);
            String resultJson = userFundTradeCXFService.selectInvestAmount(jsonObject.toJSONString());
            JSONObject jsonFund = JSONObject.parseObject(resultJson);
            String fundTotalStr = jsonFund.getString("data");
            FundTotal fundTotal = JsonUtil.jsonToBean(fundTotalStr, FundTotal.class);
            BigDecimal totalPurchase = new BigDecimal("0.00");
            BigDecimal totalRedeem = new BigDecimal("0.00");
            BigDecimal investAmount = totalPurchase.subtract(totalRedeem);
            //查询交易累计金额(累计用户申购、累计用户赎回)交易类型1申购2赎回---交易发起人：1用户2系统
            jsonObject.put("type", "1");
            jsonObject.put("initiator", "1");
            String purchaseJson = userFundTradeCXFService.selectTotalTradeNum(jsonObject.toJSONString());
            JSONObject jsonPurchase = JSONObject.parseObject(purchaseJson);
            totalPurchase = jsonPurchase.getBigDecimal("data");
            jsonObject.put("type", "2");
            String redeemJson = userFundTradeCXFService.selectTotalTradeNum(jsonObject.toJSONString());
            JSONObject jsonRedeem = JSONObject.parseObject(redeemJson);
            totalRedeem = jsonRedeem.getBigDecimal("data");
            if (fundTotal != null) {
                if (totalPurchase.compareTo(totalRedeem) == 1) {
                    investAmount = totalPurchase.subtract(totalRedeem);
                }
                if (fund.getRemAccount().compareTo(fund.getUserMostTender().subtract(investAmount)) == 1) {
                    result.put("investUsable", fund.getUserMostTender().subtract(investAmount));
                } else {
                    result.put("investUsable", fund.getRemAccount());
                }
            } else {
                result.put("investUsable", fund.getUserMostTender());
            }
            result.put("leastAmount", fund.getLowestTender());
            result.put("mostAmount", fund.getUserMostTender());
            JSONObject sysHolidayJson = new JSONObject();
            sysHolidayJson.put("buyDate", DateUtil.format(new Date(), "yyyyMMdd HH:mm:ss"));
            String resultValueDate = systemHolidayCXFService.getValueDate(sysHolidayJson.toJSONString());
            JSONObject jsonDate = JSONObject.parseObject(resultValueDate);
            String valueDate = jsonDate.getString("valueDate");
            String arrivalDate = jsonDate.getString("arrivalDate");
            result.put("valueDate", valueDate);
            result.put("arrivalDate", arrivalDate);
            result.put("msg", "获取日日盈账户信息成功");
            
            /*查询日日盈初始信息*/
            JSONObject param = new JSONObject();
            param.put("date", DateUtil.format(new Date(), "yyyyMMdd"));
            param.put("fcode", fund.getFcode());
            String resultJsons = userFundTradeCXFService.selectFundApr(param.toJSONString());
            JSONObject jsonFunds = JSONObject.parseObject(resultJsons);
            if (jsonFunds.getIntValue("resultCode") != 0) {
                result.put("resultCode", jsonFunds.getString("resultCode"));
                return result.toJSONString();
            }
            JSONObject fundAprStr = jsonFunds.getJSONObject("data");
            JSONObject fundAprJson = fundAprStr.getJSONObject("fundApr");
            result.put("fundApr", fundAprJson);
        } catch (Exception e) {
            log.error("获取日日盈账户信息失败", e);
            result.put("resultCode", 200);
            result.put("msg", "获取日日盈账户信息失败");
        }
        return result.toJSONString();
    }

    /**
     * 初始化转出(赎回)界面
     */
    @RequestMapping(value = "/initTransferOutData", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String initTransferOutData(HttpServletRequest request) {
        log.info("enter initTransferOutData in FundController .");
        JSONObject result = new JSONObject();
        try {
            //用户信息
            User user = (User) request.getSession().getAttribute("loginUser");
            if (user == null) {
                result.put("msg", "会话失效，请重新登录");
                result.put("resultCode", -400);
                return result.toJSONString();
            }
            Fund fund = selectFund();
            //查询可转出金额
            JSONObject jsonObject = new JSONObject();
            String fcode = fund.getFcode();
            jsonObject.put("userId", user.getUserId());
            jsonObject.put("fcode", fcode);
            String resultJson = userFundTradeCXFService.selectInvestAmount(jsonObject.toJSONString());
            JSONObject jsonFund = JSONObject.parseObject(resultJson);
            String fundTotalStr = jsonFund.getString("data");
            FundTotalVO fundTotalVO = JsonUtil.jsonToBean(fundTotalStr, FundTotalVO.class);
            result.put("fundTotal", fundTotalVO);
            result.put("msg", "获取日日盈账户信息成功");
        } catch (Exception e) {
            log.error("获取日日盈账户信息失败", e);
            result.put("resultCode", 200);
            result.put("msg", "获取日日盈账户信息失败");
        }
        return result.toJSONString();
    }

    /**
     * 查询交易列表、收益列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/selectTrade", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String selectTrade(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            //用户信息
            User user = (User) request.getSession().getAttribute("loginUser");
            if (user == null) {
                result.put("msg", "会话失效，请重新登录");
                result.put("resultCode", -400);
                return result.toJSONString();
            }

            String currentPage = request.getParameter("currentPage");
            String pageSize = request.getParameter("pageSize");
            String type = request.getParameter("type");
            String initiator = request.getParameter("initiator");
            String fcode = request.getParameter("fcode");

            JSONObject param = new JSONObject();
            param.put("currentPage", currentPage);
            param.put("pageSize", pageSize);
            param.put("type", type);
            param.put("initiator", initiator);
            param.put("userId", user.getUserId());
            param.put("fcode", fcode);
            log.info("getFundTradeList req param = " + param.toJSONString());
            String smsRespStr = userFundTradeCXFService.getFundTradeList(param.toJSONString());
            WSPageResponse resp = JsonUtil.jsonToBean(smsRespStr, WSPageResponse.class);
            if (resp.getResultCode() != Constant.RETURN_SUCC) {
                result.put("resultCode", -1);
                result.put("msg", "查询请求失败，请重新尝试或者联系客服");
                return result.toJSONString();
            }
            result.put("totalPage", resp.getTotalPage());
            result.put("dataList", resp.getResultList());
            result.put("resultCode", Constant.RETURN_SUCC);
        } catch (Exception e) {
            log.error("查询收益异常", e);
            result.put("resultCode", Constant.RETURN_ERROR);
        }
        return result.toJSONString();
    }

    /**
     * 查询交易详细信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/selectTradeInfo", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String selectTradeInfo(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            //用户信息
            User user = (User) request.getSession().getAttribute("loginUser");
            if (user == null) {
                result.put("msg", "会话失效，请重新登录");
                result.put("resultCode", -400);
                return result.toJSONString();
            }
            String tradeId = request.getParameter("tradeId");
            JSONObject param = new JSONObject();
            param.put("tradeId", tradeId);
            String fundTradeInfo = userFundTradeCXFService.getFundTradeInfo(param.toJSONString());
            WSPageResponse resp = JsonUtil.jsonToBean(fundTradeInfo, WSPageResponse.class);
            JSONObject fundTrade = JSONObject.parseObject(resp.getData().toString());
            param.put("buyDate", DateUtil.format(DateUtil.parseDate(fundTrade.getString("createDate"), "yyyy-MM-dd HH:mm:ss"), "yyyyMMdd HH:mm:ss"));
            String resultValueDate = systemHolidayCXFService.getValueDate(param.toJSONString());
            JSONObject jsonDate = JSONObject.parseObject(resultValueDate);
            String valueDate = jsonDate.getString("valueDate");
            String arrivalDate = jsonDate.getString("arrivalDate");
            result.put("currentDate", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            result.put("valueDate", valueDate);
            result.put("arrivalDate", arrivalDate);
            result.put("data", resp.getData());
            result.put("resultCode", Constant.RETURN_SUCC);
        } catch (Exception e) {
            log.error("查询收益详情异常", e);
            result.put("resultCode", Constant.RETURN_ERROR);
        }
        return result.toJSONString();
    }

    /**
     * 日日盈产品限制条件
     * @param reqeust
     * @return
     */
    @RequestMapping(value = "/fundLimit", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String fundLimit(HttpServletRequest reqeust) {
        JSONObject result = new JSONObject();

        try {
            //设置查询状态数组,将数据封装成json,传入webservice进行查询
            JSONObject jsonObject = new JSONObject();
            String resultJson = userFundTradeCXFService.selectFundByStatus(jsonObject.toJSONString());
            JSONObject jsonFund = JSONObject.parseObject(resultJson);
            String fundStr = jsonFund.getString("data");
            Fund fund = JsonUtil.jsonToBean(fundStr, Fund.class);
            Integer isLastBalance = 0;
            String lastBalance = fund.getLastBalance();
            if (lastBalance != null && !lastBalance.equals(DateUtil.format(new Date(), "yyyyMMdd"))) {
                isLastBalance = 1;
            }
            result.put("isBalance",fund.getIsBalance());
            result.put("isLastBalance",isLastBalance);

            //日日盈申购开关
            String purchaseSwitch = CacheUtil.getCacheValue(FundConstant.FUND_PURCHASE_SWITCH);
            result.put("purchaseSwitch", purchaseSwitch);

            //日日盈赎回开关
            String ransomSwitch = CacheUtil.getCacheValue(FundConstant.FUND_RANSOM_SWITCH);
            result.put("ransomSwitch", ransomSwitch);
            result.put("resultCode",Constant.RETURN_SUCC);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            result.put("purchaseSwitch", 1);
            result.put("ransomSwitch", 1);
            result.put("isLastBalance",1);
            result.put("resultCode",Constant.RETURN_ERROR);
        }
        return result.toJSONString();
    }

    /**
     * 查询日日盈信息
     */
    @RequestMapping(value = "/selectFundInfo", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String selectFundInfo(HttpServletRequest request) {
        log.info("enter selectFund in FundController .");
        JSONObject result = new JSONObject();
        try {
            Fund fund = selectFund();

            JSONObject param = new JSONObject();
            param.put("date", DateUtil.format(new Date(), "yyyyMMdd"));
            param.put("fcode", fund.getFcode());
            String resultJson = userFundTradeCXFService.selectFundApr(param.toJSONString());
            JSONObject jsonFund = JSONObject.parseObject(resultJson);
            if (jsonFund.getIntValue("resultCode") != 0) {
                result.put("resultCode", jsonFund.getString("resultCode"));
                return result.toJSONString();
            }
            JSONObject fundAprStr = jsonFund.getJSONObject("data");
            JSONObject fundAprJson = fundAprStr.getJSONObject("fundApr");
            JSONObject activityInfo = fundAprStr.getJSONObject("activityInfo");

            Date startTime = DateUtil.parseDate(activityInfo.getString("startTime"),"yyyy-MM-dd hh:mm:ss");
            Date endTime = DateUtil.parseDate(activityInfo.getString("endTime"),"yyyy-MM-dd hh:mm:ss");
            int isActivity = 0;
            Date currentTime = new Date();
            if(currentTime.compareTo(startTime) >=0 && currentTime.compareTo(endTime) <=0) {
                isActivity = 1;
            }
            result.put("currentDate", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            activityInfo.put("isActivity",isActivity);
            result.put("fundApr", fundAprJson);
            result.put("activityInfo",activityInfo);
            result.put("fund", fund);
            result.put("msg", "获取日日盈信息成功");
            result.put("resultCode", Constant.RETURN_SUCC);
        } catch (Exception e) {
            log.error("获取日日盈信息失败", e);
            result.put("resultCode", 200);
            result.put("msg", "获取日日盈信息失败");
        }
        return TokenUtil.jsonpCallback(request, result.toJSONString());
    }

    /**
     * 查询用户是否投资过日日盈
     */
    @RequestMapping(value = "/selectIsInvested", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String selectIsInvested(HttpServletRequest request) {
        log.info("enter selectIsInvested in FundController .");
        JSONObject result = new JSONObject();
        try {
            //用户信息
            User user = (User) request.getSession().getAttribute("loginUser");
            if (user == null) {
                result.put("msg", "会话失效，请重新登录");
                result.put("resultCode", -400);
                return result.toJSONString();
            }

            Fund fund = selectFund();

            //设置查询状态数组,将数据封装成json,传入webservice进行查询
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("fcode", fund.getFcode());
            jsonObject.put("userId", user.getUserId());
            String resultJson = userFundTradeCXFService.isInvested(jsonObject.toJSONString());
            JSONObject jsonFund = JSONObject.parseObject(resultJson);
            String isInvested = jsonFund.getString("data");
            result.put("isInvested", isInvested);
            result.put("msg", "获取日日盈投资信息成功");
            result.put("resultCode", Constant.RETURN_SUCC);
        } catch (Exception e) {
            log.error("获取日日盈投资信息失败", e);
            result.put("resultCode", 200);
            result.put("msg", "获取日日盈投资信息失败");
        }
        return result.toJSONString();
    }

    /**
     * 查询是否已满额以及是否超过个人限额
     */
    @RequestMapping(value = "/selectFundLimitation", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String selectFundLimitation(HttpServletRequest request) {
        log.info("enter selectFundLimitation in FundController .");
        JSONObject result = new JSONObject();
        try {
            //用户信息
            User user = (User) request.getSession().getAttribute("loginUser");
            if (user == null) {
                result.put("msg", "会话失效，请重新登录");
                result.put("resultCode", -400);
                return result.toJSONString();
            }

            Fund fund = selectFund();

            String fcode = fund.getFcode();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", user.getUserId());
            jsonObject.put("fcode", fcode);
            String resultJson = userFundTradeCXFService.selectInvestAmount(jsonObject.toJSONString());
            JSONObject jsonFund = JSONObject.parseObject(resultJson);
            String fundTotalStr = jsonFund.getString("data");
            FundTotal fundTotal = JsonUtil.jsonToBean(fundTotalStr, FundTotal.class);
            result.put("isTotalFull", "false");
            result.put("isPersonFull", "false");
            BigDecimal totalPurchase = new BigDecimal("0.00");
            BigDecimal totalRedeem = new BigDecimal("0.00");
            BigDecimal investAmount = totalPurchase.subtract(totalRedeem);
            //查询交易累计金额(累计用户申购、累计用户赎回)交易类型1申购2赎回---交易发起人：1用户2系统
            jsonObject.put("type", "1");
            jsonObject.put("initiator", "1");
            String purchaseJson = userFundTradeCXFService.selectTotalTradeNum(jsonObject.toJSONString());
            JSONObject jsonPurchase = JSONObject.parseObject(purchaseJson);
            totalPurchase = jsonPurchase.getBigDecimal("data");
            jsonObject.put("type", "2");
            String redeemJson = userFundTradeCXFService.selectTotalTradeNum(jsonObject.toJSONString());
            JSONObject jsonRedeem = JSONObject.parseObject(redeemJson);
            totalRedeem = jsonRedeem.getBigDecimal("data");
            //log.info(String.format("totalRedeem=%s,totalPurchase=%s",totalRedeem,totalPurchase));
            if (fundTotal != null) {
                if (totalPurchase.compareTo(totalRedeem) == 1) {
                    investAmount = totalPurchase.subtract(totalRedeem);
                }
                if (fund.getLowestTender().compareTo(fund.getRemAccount()) == 1) {
                    result.put("isTotalFull", "true");
                    log.info(String.format("日日盈剩余投资金额小于日日盈最低可投资金额,LowestTender=%s,fundRemAccount=%s",fund.getLowestTender(),fund.getRemAccount()));
                }
                if (investAmount.add(fund.getLowestTender()).compareTo(fund.getUserMostTender()) == 1) {
                    result.put("isPersonFull", "true");
                    log.info(String.format("个人可投资金额小于日日盈最低可投资金额,investAmount=%s,LowestTender = %s,fundUserMostTender=%s",investAmount,fund.getLowestTender(),fund.getUserMostTender()));
                }
            }

            if(fund.getLowestTender().compareTo(fund.getRemAccount()) == 1) {
                result.put("isTotalFull", "true");
                log.info("fund RemAccount is less than lowest tender amount.");
            }

            result.put("fund",fund);
            result.put("msg", "获取日日盈信息成功");
        } catch (Exception e) {
            log.error("获取日日盈信息失败", e);
            result.put("resultCode", 200);
            result.put("msg", "获取日日盈信息失败");
        }
        return result.toJSONString();
    }


    /**
     * 转入(用户申购)
     * 转出(用户赎回)
     */
    @RequestMapping(value = "/fundTrade", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String fundTrade(HttpServletRequest request) {
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
                log.info("日日盈转入转出，未登录，");
                result.put("desc", "您未登录，不能进行下一步操作");
                result.put("result", 200);
                return result.toString();
            }

            //验证支付密码
            String payPwd = request.getParameter("payPwd");
            JSONObject pswJson = new JSONObject();
            pswJson.put("userId", user.getUserId());
            pswJson.put("password", EscapeCode.Encryption(payPwd));
            pswJson.put("ip", HttpTookit.getRealIpAddr(request));
            String browser = request.getHeader("User-Agent");
            browser = browser.length() > 200 ? browser.substring(0,200):browser;
            pswJson.put("browser", browser);
            log.info("用户申购/赎回日日盈验证支付密码，请求参数：" + pswJson.toJSONString());
            String resultPsw = userCXFService.checkPayPassword(pswJson.toString());
            log.info("用户申购/赎回日日盈验证支付密码，响应内容：" + resultPsw);
            WSModelResponse pswResp = JsonUtil.jsonToBean(resultPsw, WSModelResponse.class);
            if (pswResp.getResultCode() == -2) {
                result.put("desc", "支付密码错误，请重新输入");
                result.put("result", pswResp.getResultCode());
                return result.toString();
            } else if (pswResp.getResultCode() == 220) {
                result.put("desc", "支付密码为空，请去设置您的支付密码");
                result.put("result", pswResp.getResultCode());
                return result.toString();
            } else if (pswResp.getResultCode() == 230) {
                result.put("desc", "支付密码与登录密码一致，请重设您的支付密码");
                result.put("result", pswResp.getResultCode());
                return result.toString();
            } else if (pswResp.getResultCode() == -1) {
                result.put("desc", "验证支付密码异常，请重新尝试或联系客服");
                result.put("result", pswResp.getResultCode());
                return result.toString();
            }
            Fund fund = selectFund();
            JSONObject fundTradeJson = new JSONObject();
            //基金编号
            String fcode = fund.getFcode();
            fundTradeJson.put("fcode", fcode);
            fundTradeJson.put("userId", user.getUserId());

            //加入金额
            String tradeAccount = request.getParameter("tradeAccount");
            fundTradeJson.put("tradeAccount", tradeAccount);

            //交易类型
            String type = request.getParameter("opType");
            fundTradeJson.put("opType", type);

            //交易发起人
            fundTradeJson.put("initiator", FundConstant.FUND_USERTRADE_INITIATOR_USER);
            fundTradeJson.put("ip", HttpTookit.getRealIpAddr(request));
            //终端信息
            fundTradeJson.put("terminalver", HttpTookit.getRequestTerminal(request));

            fundTradeJson.put("buyDate", DateUtil.format(new Date(), "yyyyMMdd HH:mm:ss"));
           // log.info("用户申购/赎回日日盈，请求参数：" + fundTradeJson.toJSONString());
            String resultBuyScheme = fundTradeCXFService.fundTrade(fundTradeJson.toJSONString());
           // log.info("用户申购/赎回日日盈，响应内容：" + resultBuyScheme);
            WSModelResponse resp = JsonUtil.jsonToBean(resultBuyScheme, WSModelResponse.class);
            log.info(resp.toJson());
            result.put("tradeId", resp.getData());
            result.put("desc", resp.getDesc());
            result.put("result", resp.getResultCode());

            if(resp.getResultCode() == AppConsts.WS_RETURN_SUCC){
                /******************日日盈转出GA部署所需参数start******************/
                JSONObject gaJson = new JSONObject();
                gaJson.put("fcode", fcode);
                gaJson.put("userId", user.getUserId());
                gaJson.put("tradeId",resp.getData());
                String gaJsonStr = fundCXFService.getFundGAData(gaJson.toString());
                WSModelResponse gaRes = JsonUtil.jsonToBean(gaJsonStr, WSModelResponse.class);
                if(gaRes.getData()!=null) {
                    String dataStr = String.valueOf(gaRes.getData());
                    log.info("日日盈ga布码结果：" + dataStr);
                    Map map = JsonUtil.jsonToBean(dataStr, Map.class);
                    String serviceNum ="";
                    if(null != map){
                        result.put("productId",map.get("FCODE"));
                        result.put("apr",map.get("APR"));
                        result.put("price",map.get("TRADENUM"));
                        result.put("tradeid",map.get("TRADEID"));
                        if (null != map.get("SERVICENUM") && !"".equals(map.get("SERVICENUM"))) {
                            serviceNum = map.get("SERVICENUM").toString();
                        } else {
                            serviceNum = "nonFD";
                        }
                        result.put("servicenum",serviceNum);
                    }
                }
                log.info("日日盈退出结果：" + result.toJSONString());
                /******************日日盈转出GA部署所需参数end********************/
            }
            result.put("activity_url",Configuration.getInstance().getValue("activity_url"));
        } catch (Exception e) {
            result.put("desc", "操作异常，请重新尝试或者联系客服");
            result.put("result", -100);
            log.error("用户申购/赎回日日盈失败，" + e.getMessage(), e);
        }
        return result.toJSONString();
    }

    /**
     * 计算起息日及到账日
     */
    @RequestMapping(value = "/getValueDate", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getValueDate(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            JSONObject sysHolidayJson = new JSONObject();
            sysHolidayJson.put("buyDate", DateUtil.format(new Date(), "yyyyMMdd HH:mm:ss"));
            String resultValueDate = systemHolidayCXFService.getValueDate(sysHolidayJson.toJSONString());
            JSONObject jsonDate = JSONObject.parseObject(resultValueDate);
            String valueDate = jsonDate.getString("valueDate");
            String arrivalDate = jsonDate.getString("arrivalDate");
            result.put("valueDate", valueDate);
            result.put("arrivalDate", arrivalDate);
            result.put("currentDate", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            result.put("msg", "获取起息日成功");
        } catch (Exception e) {
            log.error("获取起息日失败" + e.getMessage(), e);
        }
        return result.toString();
    }

    @RequestMapping(value = "/calculationIncome", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String calculationIncome(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            BigDecimal money = new BigDecimal(request.getParameter("money"));
            BigDecimal apr = new BigDecimal(request.getParameter("apr"));
            int dayNum = Integer.parseInt(request.getParameter("dayNum"));
            BigDecimal resultMoney = FundUtil.calculationIncome(money,apr,dayNum);
            StringBuffer logStr = new StringBuffer();
            logStr.append("\n===============================================\n");
            logStr.append("money       = ").append(money).append("\n");
            logStr.append("apr         = ").append(apr).append("\n");
            logStr.append("dayNum      = ").append(dayNum).append("\n");
            logStr.append("resultMoney = ").append(resultMoney).append("\n");
            logStr.append("===============================================");
            log.info(logStr.toString());
            result.put("resultMoney",resultMoney);
            result.put("resultCode",0);
        } catch (Exception e) {
            result.put("resultCode",1);
            log.error("计算理论收益失败" + e.getMessage(), e);
        }
        return result.toJSONString();
    }
}
