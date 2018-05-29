package com.xxdai.account.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.xxdai.account.model.*;
import com.xxdai.account.service.AccountService;
import com.xxdai.appro.bo.MobileAppro;
import com.xxdai.appro.bo.RealNameAppro;
import com.xxdai.borrow.constants.BorrowConsts;
import com.xxdai.borrow.constants.RedEnvelopeLimitTypeConsts;
import com.xxdai.client.CXF_Factory;
import com.xxdai.common.BaseController;
import com.xxdai.common.DataResponse;
import com.xxdai.constant.AccountConsts;
import com.xxdai.constant.AppConsts;
import com.xxdai.constant.Constant;
import com.xxdai.constant.SmsConsts;
import com.xxdai.core.util.http.HttpUtil;
import com.xxdai.core.util.http.WSMapResponse;
import com.xxdai.core.util.http.WSResponse;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.core.util.lang.StringUtil;
import com.xxdai.encrypt.EscapeCode;
import com.xxdai.external.account.ws.account.CashWhiteListCXFService;
import com.xxdai.external.feedback.ws.FeedbackCXFService;
import com.xxdai.external.user.ws.User;
import com.xxdai.external.yyp.ws.ReglIntstCXFService;
import com.xxdai.fund.constants.FundConstant;
import com.xxdai.http.Message;
import com.xxdai.market.bo.Levels;
import com.xxdai.person.bo.BankInfo;
import com.xxdai.person.bo.BaseInfo;
import com.xxdai.person.constants.BankConsts;
import com.xxdai.person.model.Appro;
import com.xxdai.person.model.PersonResponse;
import com.xxdai.person.ws.userquery.UserQueryCXFService;
import com.xxdai.product.service.ProductService;
import com.xxdai.redpacket.model.RedenvelopeRecord;
import com.xxdai.redpacket.model.RpWsResponse;
import com.xxdai.redpacket.webservice.interfaces.RedpacketCXFService;
import com.xxdai.system.bo.DicCommonVo;
import com.xxdai.system.bo.FeedbackItemsBo;
import com.xxdai.user.model.UserResponse;
import com.xxdai.util.*;
import com.xxdai.util.CacheUtil;
import com.xxdai.weixin.util.*;
import com.xxdai.ws.util.WSModelResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/account")
public class AccountController extends BaseController {

    private static final Log log = LogFactory.getLog(AccountController.class);
    private FeedbackCXFService feedbackService = (FeedbackCXFService) CXF_Factory.getFactory(FeedbackCXFService.class, com.xxdai.util.Configuration.getInstance().getValue("webService_url") + "/feedbackWebService").create();
    private RedpacketCXFService redpacketCXFService = (RedpacketCXFService) CXF_Factory.getFactory(RedpacketCXFService.class, Configuration.getInstance().getValue("webService_url") + "/redpacketWebService").create();
    UserQueryCXFService userQueryService = (UserQueryCXFService) CXF_Factory.getFactory(UserQueryCXFService.class, Configuration.getInstance().getValue("webService_url") + "/userQueryWebService").create();
    /**
     * _接口
     */
    private ReglIntstCXFService reglIntstCXFService = (ReglIntstCXFService) CXF_Factory.getFactory(ReglIntstCXFService.class, Configuration.getInstance().getValue("webService_url") + "/yypCXFService").create();
    private CashWhiteListCXFService cashWhiteListService = (CashWhiteListCXFService) CXF_Factory.getFactory(CashWhiteListCXFService.class, com.xxdai.util.Configuration.getInstance().getValue("webService_url") + "/cashWhiteListCXFService").create();

    @Autowired
    private AccountService accountService;
    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/cost", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String accountCost(HttpServletRequest request) {
        JSONObject resultJson = new JSONObject();
        String accountCostStr = accountCashCXFService.getAccountCost("");
        WSModelResponse res=JsonUtil.jsonToBean(accountCostStr.toString(),WSModelResponse.class);
        if(res.getResultCode()>=0 && res.getData()!=null){
            JSONObject accountCost = JsonUtil.jsonToBean(res.getData().toString(),JSONObject.class);
            resultJson.put("accountCost",accountCost);
       }
        return resultJson.toJSONString();
    }
    /**
     * 资金账户-->提现
     */
   /* @RequestMapping(value = "/drawmoney", produces = {"application/json;charset=UTF-8"})
    public synchronized
    @ResponseBody
    String drawmoney(HttpServletRequest request) {
        JSONObject resultJson = new JSONObject();
        //验证是否登录状态
        Object userObj = request.getSession().getAttribute("loginUser");
        if (null == userObj) {
            //会话过期
            resultJson.put("resultCode", 400);
            resultJson.put("msg", "页面错误，请刷新页面或重新登录!");
            return resultJson.toString();
        }
        User user = (User) userObj;
        //支付密码
        String payPW = request.getParameter("payPW");
        payPW = EscapeCode.Encryption(payPW);
        DataResponse ures = this.checkPayPsw(user.getUserId(), payPW, request);
        if (ures.getResultCode() != 0) {
            if (ures.getResultCode() == 220) {
                resultJson.put("resultCode", 5);
                resultJson.put("msg", "您还未设置支付密码，前往设置！");
                return resultJson.toString();
            } else if (ures.getResultCode() == 230) {
                resultJson.put("resultCode", 6);
                resultJson.put("msg", "支付密码与登录密码一致，为了资金安全，请进行修改!");
                return resultJson.toString();
            } else {
                resultJson.put("resultCode", 3);
                resultJson.put("msg", "支付密码错误，请重新输入!");
                return resultJson.toString();
            }
        }

        //证件识别码
        String idCardCode = request.getParameter("idCardCode");
        JSONObject obj = new JSONObject();
        obj.put("userId", user.getUserId());
        String str = accountOVQueryCXFService.queryRealNameByUserId(obj.toJSONString());
        PersonResponse res = JsonUtil.jsonToBean(str, PersonResponse.class);
        String idCardNo = "";
        if (res.getData() != null) {
            RealNameAppro realNameAppro = JsonUtil.jsonToBean(res.getData().toString(), RealNameAppro.class);
            idCardNo = realNameAppro.getIdCardNo();
        } else {
            resultJson.put("resultCode", 308);
            resultJson.put("msg", "用户未实名认证，请先实名认证!");
            return resultJson.toString();
        }
        if (!(StringUtils.isNotBlank(idCardNo) && idCardCode != null && idCardNo.toLowerCase().endsWith(idCardCode.toLowerCase()))) {
            //验证码不正确
            resultJson.put("resultCode", 309);
            resultJson.put("msg", "证件识别码错误，请重新输入!");
            return resultJson.toString();
        }

        //验证码
        String verifyCode = request.getParameter("verifyCode");
        Object randCode = request.getSession().getAttribute(SmsConsts.BUSICODE_CASH_MONEY);
        if (!(randCode != null && verifyCode != null && randCode.toString().toLowerCase().equals(verifyCode.toLowerCase()))) {
            //验证码不正确
            resultJson.put("resultCode", 300);
            resultJson.put("msg", "验证码错误，请重新输入!");
            return resultJson.toString();
        }

        //校验银行卡信息，
        String bankId = request.getParameter("bankId");
        JSONObject bankObj = new JSONObject();
        bankObj.put("bankId", bankId);
        bankObj.put("userId", user.getUserId());
        bankObj.put("terminalver", HttpTookit.getRequestTerminal(request));
        //根据当前登录用户id与银行卡id查询数据信息
        String bankCheckStr = personalService.getBankById(bankObj.toJSONString());
        WSModelResponse bankCheckWS = JsonUtil.jsonToBean(bankCheckStr, WSModelResponse.class);
        if (bankCheckWS.getResultCode() == BankConsts.CASH_BANK_DELETED_CODE) {
            //银行卡被删除
            resultJson.put("resultCode", 500);
            resultJson.put("msg", "该银行已被删除，请重新选择银行卡!");
            return resultJson.toString();
        } else if (bankCheckWS.getResultCode() == BankConsts.CASH_BANK_EMPTY_BRANCH_CODE) {
            //银行卡没有支行信息
            resultJson.put("resultCode", 501);
            resultJson.put("msg", "当前提现银行卡未设置支行信息，请先设置支行信息!");
            return resultJson.toString();
        } else if (bankCheckWS.getResultCode() == BankConsts.CASH_BANK_UNMATCH_CODE) {
            //当前登录账户与银行卡不匹配
            resultJson.put("resultCode", 502);
            resultJson.put("msg", "当前登录账户与银行卡不匹配，请选择您绑定的银行卡进行取现!");
            return resultJson.toString();
        } else if (bankCheckWS.getResultCode() == BankConsts.CASH_BANK_EMPTY_CITY_CODE) {
            //银行卡未设置开户行城市
            resultJson.put("resultCode", 503);
            resultJson.put("msg", "当前提现银行卡未设置开户行省市，请先设置开户行省市信息!");
            return resultJson.toString();
        }
        //获取用户银行卡省市信息
        String province = "";
        String city = "";
        String bankInfoStr = personalCXFService.getUserBankById(JsonUtil.beanToJson(bankId));
        WSModelResponse ws = JsonUtil.jsonToBean(bankInfoStr, WSModelResponse.class);
        if (ws.getData() != null) {
            BankInfo userBank = JsonUtil.jsonToBean(ws.getData().toString(), BankInfo.class);
            province = userBank.getProvince();
            city = userBank.getCity();
        }

        //用户状态 ： 0-正常状态；1-账户冻结； 2-禁止提现； 3-黑名单账户；4-账户已注销;  9-白名单账户；
        //todo -----------------------------------------------
        Map oneMap = new HashMap();
        oneMap.put("userId", user.getUserId());
        log.info("canGetCash req = " + JsonUtil.beanToJson(oneMap));
        String oneStr = accountCashCXFService.canGetCash(JsonUtil.beanToJson(oneMap));
        log.info("canGetCash resp = " + oneStr);
        WSMapResponse resResponse = JsonUtil.jsonToBean(oneStr, WSMapResponse.class);
        int ret = resResponse.getResultCode();

        // 如果webService查询是否禁止提现出现异常或就是禁止提现
        if (ret == -1 || ret == 1) {
            String msg = "您的账户禁止提现，请联系客服！";
            log.info(msg);
            //您的账户存在异常,暂时被冻结,请联系客服!
            resultJson.put("resultCode", 301);
            resultJson.put("msg", msg);
            return resultJson.toString();
        }
        //提现金额
        String drawmoney = request.getParameter("drawmoney");

        //封装参数
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", user.getUserId());
        jsonObject.put("userBankId", Long.parseLong(bankId));
        log.info("getMaxcashAmount req = " + jsonObject.toJSONString());
        str = accountCashCXFService.getMaxcashAmount(JsonUtil.beanToJson(jsonObject));
        log.info("getMaxcashAmount resp = " + str);
        ws = JsonUtil.jsonToBean(str, WSModelResponse.class);
        BigDecimal couldCashMoney = BigDecimal.ZERO;
        if (ws.getData() != null) {
            couldCashMoney = new BigDecimal(ws.getData().toString());
        }

        if (drawmoney.equals("")) {
            //提现金额不能为空！
            resultJson.put("resultCode", 305);
            resultJson.put("msg", "提现金额不能为空，请重新输入!");
            return resultJson.toString();
        } else if (new BigDecimal(drawmoney).compareTo(BigDecimal.valueOf(0)) == -1) {
            //提现金额不能小于0
            resultJson.put("resultCode", 306);
            resultJson.put("msg", "提现金额不能小于0，请重新输入！");
            return resultJson.toString();
        } else if (new BigDecimal(drawmoney).compareTo(couldCashMoney) > 0) {
            //提现金额大于账户最大可提现金额，请重新操作!
            resultJson.put("resultCode", 307);
            resultJson.put("msg", "提现金额大于账户最大可提现金额，请重新输入！");
            return resultJson.toString();
        }
        //清空session中的验证码
        request.getSession().removeAttribute(SmsConsts.BUSICODE_CASH_MONEY);

        //调用提现接口
        Map mmap = new HashMap();
        mmap.put("bankId", bankId);
        mmap.put("cashAmount", new BigDecimal(drawmoney));
        mmap.put("userId", user.getUserId());
        mmap.put("ip", HttpUtil.getRealIpAddr(request));
        mmap.put("payPassword", payPW);
        mmap.put("terminalver", HttpTookit.getRequestTerminal(request));
        mmap.put("province", province);
        mmap.put("city", city);
        log.info("cashAccount req = " + JsonUtil.beanToJson(mmap));
        String rstr = accountCashCXFService.cashAccount(JsonUtil.beanToJson(mmap));
        log.info("cashAccount resp = " + rstr);
        WSMapResponse wsResponse = JsonUtil.jsonToBean(rstr, WSMapResponse.class);
        resultJson.put("resultCode", wsResponse.getResultCode());
        resultJson.put("msg", wsResponse.getDesc());
        return resultJson.toString();
    }*/

    public String path(HttpServletRequest request) {
        String path = request.getContextPath();
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    }

    /**
     * 支付密码是否正确
     */
    public DataResponse checkPayPsw(Long userId, String payPW, HttpServletRequest request) {
        JSONObject obj = new JSONObject();
        obj.put("userId", userId);
        obj.put("password", payPW);
        obj.put("ip", HttpUtil.getRealIpAddr(request));
        String browser = request.getHeader("User-Agent");
        browser = browser.length() > 200 ? browser.substring(0, 200) : browser;
        obj.put("browser", browser);
        String str = userCXFService.checkPayPassword(obj.toString());
        DataResponse ures = JsonUtil.jsonToBean(str, DataResponse.class);
        return ures;
    }

    /**
     * 账户概览
     */
    @RequestMapping(value = "/myaccount", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String myaccount(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {

            Object userObj = request.getSession().getAttribute("loginUser");
            if(userObj == null) {
                result.put("code",100);
                result.put("info","未登录");
                return result.toJSONString();
            }

            User user = (User) userObj;
            Map userAcc = userAccount(user.getUserId());
            result.put("usemoney", userAcc.get("usemoney"));//可用额
            result.put("frozen", userAcc.get("frozen"));//冻结

            /*BigDecimal totalIncome = new BigDecimal(0);
            Message accumulatedIncome = productService.accumulatedIncome(user.getUserId());
            if(accumulatedIncome != null && accumulatedIncome.getCode() == Constant.SUCCESS) {
                totalIncome = JSONObject.parseObject(accumulatedIncome.getData().toString()).getBigDecimal("accumulatedIncome");
            }
            result.put("totalIncome", totalIncome);//累计收益

            BigDecimal collectingIncome = new BigDecimal(0);
            Message collectingIncomeMsg = productService.dueInIncome(user.getUserId(),1);
            if(collectingIncomeMsg != null && collectingIncomeMsg.getCode() == Constant.SUCCESS) {
                JSONObject collectingInterest = JSONObject.parseObject(collectingIncomeMsg.getData().toString()).getJSONObject("collectingInterest");
                collectingIncome = collectingInterest.getBigDecimal("dueInInterest");
            }
            result.put("collectingIncome", collectingIncome);//待收收益
            */
            String token = HttpTookit.getCookieValue(request,Constant.USERTOKEN);
            String userAgent = HttpTookit.getUserAgent(request);
            Message overview = productService.overview(token,userAgent);
            result.put("overview",overview);


            Message statistics = productService.statistics(user.getUserId());
            result.put("statistics",statistics);

            result.put("code",0);
        } catch (Exception e) {
            result.put("code",400);
            result.put("info","异常");
            log.error("myaccount error",e);
        }
        return result.toString();
    }

    /**
     * 获取年龄
     * @param idcardNoType
     * @param idcardNo
     * @param birthday
     * @return
     * @throws ParseException
     */
    public String getAgeSub(String idcardNoType,String idcardNo,String birthday) throws ParseException{

        String ageStr = "";
        String date="";
        boolean flag =idcardNoType!=null && !"".equals(idcardNoType) && !"null".equalsIgnoreCase(idcardNoType) &&idcardNoType.equals("1");
        boolean idcardNoFlag = StringUtils.isNotEmpty(idcardNo) && !StringUtils.isEmpty(idcardNo) && !"null".equalsIgnoreCase(idcardNo);
        boolean idcardNoFlag1 = flag && idcardNoFlag && idcardNo.length() ==15;
        boolean idcardNoFlag2 = flag && idcardNoFlag && idcardNo.length() >15;
        boolean birthdayFlag = StringUtils.isNotEmpty(birthday) && !StringUtils.isEmpty(birthday) && !"null".equalsIgnoreCase(birthday);
        boolean birthdayFlag1 = flag && idcardNoFlag==false && birthdayFlag;
        boolean birthdayFlag2 = !flag && birthdayFlag;
        if(idcardNoFlag1){
            date = "19"+idcardNo.substring(6, 8);
            ageStr = this.getAge(date);
        }else if(idcardNoFlag2){
            date = idcardNo.substring(6, 10);
            ageStr = this.getAge(date);
        }else if(birthdayFlag1){
            date = birthday.substring(0, 4);
            ageStr = this.getAge(date);
        }else if(birthdayFlag2){
            date = birthday.substring(0, 4);
            ageStr = this.getAge(date);
        }else{
            ageStr="";
        }

        return ageStr;
    }

    /**
     * 年龄 = 当前时间-出生日期
     * @param date
     * @return
     * @throws ParseException
     */
    public String getAge(String date) throws ParseException{
        String ageStr = "";
        int age = 0;
        try{
            Integer ages = Integer.parseInt(date);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            Integer dates =Integer.parseInt(sdf.format(new Date()));
            age = dates-ages;
        }catch (Exception e) {
            age = 0;
        }finally{
            if(age <=18){
                ageStr = "18岁及以下";
            }else if(age >=19 && age<=25 ){
                ageStr = "19-25岁";
            }else if(age >=26 && age<=35 ){
                ageStr = "26-35岁";
            }else if(age >=36 && age<=49 ){
                ageStr = "36-49岁";
            }else if(age >=50 ){
                ageStr = "50岁以上";
            }
        }
        return ageStr;
    }

    /**
     * 得到注册天数
     * @param beginDateStr
     * @param endDateStr
     * @return long
     * @author jinzhiguang
     */
    private  String getDaySub(String beginDateStr,String endDateStr){
        String regDateStr = "";
        long day=0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate;
        Date endDate;
        try{
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
            day = (endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
        } catch (ParseException e){
            log.info("得到注册天数，请求参数：开始时间："+beginDateStr+"结束世间："+endDateStr);
        }finally{
            if(day < 7){
                regDateStr = "注册时间小于7天";
            }else if(day>=7 && day<=30){
                regDateStr = "注册时间7-30天";
            }else if(day>=31 && day<=90){
                regDateStr = "注册时间31-90天";
            }else if(day >=91 && day <= 365){
                regDateStr = "注册时间91-365天";
            }else if(day>365){
                regDateStr = "注册时间大于365天";
            }
        }

        return regDateStr;
    }

    /**
     * 我的_总资产的计算（可用余额+冻结金额+产品本金+产品待收益）
     * @param userId
     * @return
     */
    public Map getAccountTotal(long userId){
        Map returnMap = null;

        try{
            JSONObject json=new JSONObject();
            json.put("userId", userId);
            String str = borrowQueryCXFService.querySumAccountTotal(json.toJSONString());
            WSModelResponse resp = JsonUtil.jsonToBean(str, WSModelResponse.class);
            if(resp.getData() != null) {
                returnMap = JsonUtil.jsonToBean(resp.getData().toString(),Map.class);
            }

        } catch (Exception e) {
            log.error("[AccountViewController.getAccountTotal]我的_：资产总额,查询异常", e);
        }
        return returnMap;
    }

    /**
     * 用户信息认证
     * @param userId
     * @return
     */
    private Map<String,String> getUserApproInfo(Long userId) {
        String mobileAppro = "0";
        String realnameAppro = "0";
        String vipAppro = "0";
        String emailAppro = "0";

        // 认证信息
        JSONObject  obj = new JSONObject();
        obj.put("userId", userId);
        String str = borrowQueryService.queryAppro(obj.toString());
        PersonResponse res =JsonUtil.jsonToBean(str, PersonResponse.class);

        if(res.getData()!=null){
            String dataStr = String.valueOf(res.getData());
            Appro appro =JsonUtil.jsonToBean(dataStr, Appro.class);
            if(appro!=null){
                mobileAppro = appro.getMobile();
                realnameAppro = appro.getRealName();
                vipAppro = appro.getVip();
                emailAppro = appro.getEmail();
            }
        }

        Map<String, String> approMap = new HashMap<String, String>();
        approMap.put("mobileAppro", mobileAppro);
        approMap.put("realnameAppro", realnameAppro);
        approMap.put("vipAppro", vipAppro);
        approMap.put("emailAppro", emailAppro);

        return approMap;
    }

    /**
     * 账户收益
     */
    @RequestMapping(value = "/myincome", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String myincome(HttpServletRequest request) {
        JSONObject obj = new JSONObject();
        try {
            Object userObj = request.getSession().getAttribute("loginUser");
            if (userObj == null) {
                obj.put("resultCode", -400);
                return obj.toJSONString();
            }

            User user = (User) userObj;
            String str = "";
            Double collintest = 0.00;
            String interestSum = "0.00";//累计收益
            String tenderSum = "0.00";//累计投资
            String accounttotal = "0.00";   //账户总资金
            //累计收益
            collintest = collintest(user.getUserId());
            obj.put("collintest", collintest);
            //获取用户出借概览信息
            obj.put("loanMap", loanMap(user.getUserId(), request));

            //累计收益
            str = borrowQueryService.getInterestSum(user.getUserId());
            WSModelResponse res = JsonUtil.jsonToBean(str, WSModelResponse.class);
            interestSum = res.getData().toString();
            obj.put("interestSum", Double.parseDouble(interestSum));
            //累计投资
            tenderSum = tenderSum(user.getUserId());
            obj.put("tenderSum", Double.parseDouble(tenderSum));
            //累计账户总资产
            JSONObject accountObj = new JSONObject();
            accountObj.put("userId", user.getUserId());
            str = accountQueryCXFService.getAccountSum(JsonUtil.beanToJson(accountObj));
            res = JsonUtil.jsonToBean(str, WSModelResponse.class);
            accounttotal = res.getData().toString();
            obj.put("accounttotal", accounttotal);

            Map map = new HashMap();
            map.put("useId", user.getUserId());
            map.put("accountType", AccountConsts.ACCOUNT_FUND_TYPE_ACCOUNT);
            String fundAccountStr = accountQueryCXFService.selectAccountByIdAndType(JsonUtil.beanToJson(map));
            AccountResponse fundAccountResp = JsonUtil.jsonToBean(fundAccountStr, AccountResponse.class);
            BigDecimal fundAccountTotal = new BigDecimal(0);
            if (fundAccountResp.getData() != null) {
                String dataStr = String.valueOf(fundAccountResp.getData());
                Account fundAccount = JsonUtil.jsonToBean(dataStr, Account.class);
                fundAccountTotal = fundAccount.getAccountTotal();
            }
            obj.put("fundAccountTotal", fundAccountTotal);
            obj.put("resultCode", 0);
        } catch (Exception e) {
            obj.put("resultCode", -1);
        }
        return obj.toJSONString();
    }

    /**
     * 累计收益
     */
    public Double collintest(long userid) {
        Double collintest = 0.00;
        try {
            JSONObject object3 = new JSONObject();
            object3.put("userId", userid);
            String str = tenderService.selectCollectInterest(object3.toString());
            AccountResponse aresp = JsonUtil.jsonToBean(str, AccountResponse.class);
            if (aresp.getData() != null) {
                collintest = Double.parseDouble(aresp.getData().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("累計待收益--------");
            log.error(e.getMessage());
        }
        return collintest;
    }

    /**
     * 获取用户出借概览信息
     */
    public Map loanMap(long userId, HttpServletRequest request) {
        Map<String, Object> loanMap = new HashMap<String, Object>();
        try {
            JSONObject object = new JSONObject();
            object.put("userId", userId);
            object.put("ip", HttpUtil.getRealIpAddr(request));
            String str = borrowCountCXFWebService.queryBorrowCountByMyAccountPages(object.toString());
            JSONObject jobj = JSONObject.parseObject(str);
            String loancountstr = "";
            if (jobj != null) {
                loancountstr = jobj.getString("borrowCountInfos");
            }
            List<BorrowCountInfo> list = new ArrayList<BorrowCountInfo>();
            if (loancountstr != null) {
                list = JsonUtil.jsonToList(loancountstr, BorrowCountInfo.class);
            }
            for (BorrowCountInfo borrowCountInfo : list) {
                loanMap.put("loan" + borrowCountInfo.getType(), borrowCountInfo);
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("出借概览--------");
            log.error(e.getMessage());
        }
        return loanMap;
    }

    /**
     * 累计投资
     */
    public String tenderSum(long userId) {
        String tenderSum = "0";
        try {
            Account account = getAccountByUserId(userId);
            if (account != null) {
                tenderSum = StringUtil.toString(account.getTenderSum());
            }
        } catch (Exception e) {
            log.error("tenderSum ====" + e.getMessage());
        }
        return tenderSum;
    }

    /**
     * 投标记录
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/tenderQuery", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String tenderQuery(HttpServletRequest request) {
        JSONObject resultJson = new JSONObject();
        //验证是否登录状态
        Object userObj = request.getSession().getAttribute("loginUser");
        User user = (User) userObj;

        //分页信息
        String currentPage = request.getParameter("currentPage");// 当前页
        String pageSize = request.getParameter("pageSize");// 页面加载量

        Map map = new HashMap();
        map.put("curPage", currentPage == null || currentPage.equals("") ? 1 : Integer.valueOf(currentPage));
        map.put("pageSize", pageSize == null || pageSize.equals("") ? 10 : Integer.valueOf(pageSize));
        map.put("userId", user.getUserId());
        try {
            //log.info("select tenderQuery req = " + JsonUtil.beanToJson(map));
            String str = tenderService.queryTenderQueryNew(JsonUtil.beanToJson(map));
            AccountResponse resp = JsonUtil.jsonToBean(str, AccountResponse.class);
            PageUtils pageUtils = null;
            if (resp.getData() != null) {
                String datastr = String.valueOf(resp.getData());
                pageUtils = JsonUtil.jsonToBean(datastr, PageUtils.class);
            }
            if (pageUtils != null) {
                resultJson.put("resultCode", 1);
                JSONObject json = new JSONObject();
                for (int i = 0; i < pageUtils.getResultList().size(); i++) {
                    boolean statusDouble = false;
                    json = JSON.parseObject(pageUtils.getResultList().get(i).toString());
                    String status = json.get("status").toString();
                    if (Integer.parseInt(status) == 4) {
                        statusDouble = true;
                    }
                    json.put("statusNumber",status);
                    json.put("statusDouble", statusDouble);
                    json.put("status", boroowSta(Integer.parseInt(status)));
                    pageUtils.getResultList().set(i, json);
                }
            } else {
                resultJson.put("resultCode", 0);
            }
            resultJson.put("curPage", currentPage);
            resultJson.put("pageSize", pageSize);
            resultJson.put("tenderQuery", pageUtils);
        } catch (Exception e) {
            log.error("tenderQuery ====" + e.getMessage(), e);
        }

        //累计投资
        String tenderSum = "0.00";
        tenderSum = tenderSum(user.getUserId());
        resultJson.put("tenderSum", Double.parseDouble(tenderSum));
        return resultJson.toString();
    }

    /**
     * 投资记录 标的状态
     */
    public String boroowSta(int index) {
        String result = "";
        switch (index) {
            case 1:
                result = "审核中";
                break;
            case 2:
                result = "投标中";
                break;
            case 3:
                result = "满标复审";
                break;
            case 4:
                result = "还款中";
                break;
            case 5:
                result = "还款结束";
                break;
            case -1:
                result = "流标";
                break;
            case -2:
                result = "被撤销";
                break;
            case -3:
                result = "审核失败";
                break;
            case 6:
                result = "待签约";
                break;
            case 7:
                result = "准备发布";
                break;
            case -4:
                result = "签约失败";
                break;
            case 44:
                result = "已转让";
                break;
        }
        return result;
    }

    /**
     * 用户资金日志查询
     *
     * @param request
     * @param session
     * @param filters
     * @return
     */
    @RequestMapping(value = "/moneyRecord", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String moneyRecord(HttpServletRequest request, HttpSession session, LogsFilter filters) {

        User user = (User) session.getAttribute("loginUser");
        int pageSize = Integer.parseInt(request.getParameter("pageSize") == null ? "10" : request.getParameter("pageSize")); // 页面加载量
        int currentPage = Integer.parseInt(request.getParameter("currentPage") == null ? "1" : request.getParameter("currentPage")); // 当前页
        int xxb = 0;     //新新币
        JSONObject obj = new JSONObject();
        try {
            Levels levels = getLevels(user.getUserId());
            if (levels != null && levels.getCoins() != null) {
                xxb = levels.getCoins().intValue();
            }
            filters = filters == null ? new LogsFilter() : filters;
            Map map = new HashMap();
            map.put("type", filters.getType());
            map.put("limit", filters.getLimit());
            map.put("currentPage", currentPage);
            map.put("pageSize", pageSize);
            map.put("userId", user.getUserId());
            //log.info("select accout record req = " + JsonUtil.beanToJson(map));
            String jstr = accountQueryCXFService.selectPageAccountMoneyRecord(JsonUtil.beanToJson(map));
            AccountResponse resp = JsonUtil.jsonToBean(jstr, AccountResponse.class);
            PageUtils pageUtils = new PageUtils();
            String dataStr = "";
            if (resp.getData() != null) {
                dataStr = String.valueOf(resp.getData());
                pageUtils = JsonUtil.jsonToBean(dataStr, PageUtils.class);
            }
            filters.setGetCount(pageUtils.getTotalSize());
            String str = dicCXFService.queryCommonValue("OPERATETYPE");
            PersonResponse res = JsonUtil.jsonToBean(str, PersonResponse.class);
            List<DicCommonVo> degrees = null;
            if (res.getData() != null) {
                dataStr = String.valueOf(res.getData());
                degrees = JsonUtil.jsonToList(dataStr, DicCommonVo.class);
            }
            Map configMap = new HashMap();
            if (degrees != null) {
                for (DicCommonVo config : degrees) {
                    configMap.put(config.getPkey(), config.getPvalue());
                }
            }
            JSONObject json = new JSONObject();
            for (int i = 0; i < pageUtils.getResultList().size(); i++) {
                boolean moneyTypeDouble = true;
                json = JSON.parseObject(pageUtils.getResultList().get(i).toString());
                String status = json.get("moneyType").toString();
                String operatorType = json.get("operatorType").toString();
                if (Integer.parseInt(status) == 1 || Integer.parseInt(status) == 3) {
                    moneyTypeDouble = false;
                }
                json.put("operatorType", configMap.get(operatorType));
                json.put("moneyTypeDouble", moneyTypeDouble);
                pageUtils.getResultList().set(i, json);
            }
            obj.put("configMap", configMap);
            obj.put("creditList", pageUtils);
            obj.put("currentPage", currentPage);
            obj.put("pageData", pageSize);
            int maxPage = maxPage(filters.getGetCount(), pageSize);
            obj.put("maxPage", maxPage);
            obj.put("xxb", xxb);
            Map userAcc = userAccount(user.getUserId());
            obj.put("usemoney", userAcc.get("usemoney"));//可用额
            obj.put("frozen", userAcc.get("frozen"));//冻结
        } catch (Exception e) {
            log.error("moneyRecord :" + e.getMessage());
        }
        return obj.toString();
    }

    //分页总页数
    public int maxPage(int count, int pageSize) {
        if (count % pageSize == 0) {
            return (int) Math.ceil(count / pageSize);
        } else {
            return (int) Math.ceil(count / pageSize) + 1;
        }
    }

    //新新比
    public Levels getLevels(Long userId) {
        Levels levels = null;
        //log.info("select user Level req = " + userId);
        String str = marketCXFService.selectLevelsByUserId(JsonUtil.beanToJson(userId));
       // log.info("select user Level resp = " + str);
        MarketWsResponse mres = JsonUtil.jsonToBean(str, MarketWsResponse.class);
        if (mres.getData() != null) {
            levels = JsonUtil.jsonToBean(mres.getData().toString(), Levels.class);
        }
        return levels;
    }

    //可用余额 冻结金额
    public Map userAccount(long userID) {
        String str = "";
        String dataStr = "";
        String usemoney = "0.00";
        String frozen = "0.00";
        Map<String, Object> map = new HashMap<String, Object>();
        Account account = getAccountByUserId(userID);
        if (account != null) {
            usemoney = StringUtil.toString(account.getUsable());
            frozen = StringUtil.toString(account.getFrozen());
        }
        map.clear();
        map.put("usemoney", Double.parseDouble(usemoney));//可用额
        map.put("frozen", Double.parseDouble(frozen));//冻结
        return map;
    }

    /**
     * 汇款记录/收款记录查询
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/tenderDuein", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String tenderDuein(HttpServletRequest request) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("resultCode", "-1");
        resultJson.put("desc", "获取回款计划失败，请稍后重试...");
        try {
            Object userObj = request.getSession().getAttribute("loginUser");
            int pageSize = Integer.parseInt(request.getParameter("pageSize") == null ? "10" : request.getParameter("pageSize")); // 页面加载量
            int currentPage = Integer.parseInt(request.getParameter("currentPage") == null ? "1" : request.getParameter("currentPage")); // 当前页
            User user = (User) userObj;

            Map map = new HashMap();
            map.put("currentPage", currentPage);
            map.put("pageSize", pageSize);
            map.put("userId", user.getUserId());
            log.info("AccountController tenderDuein ----> params:" + JsonUtil.beanToJson(map));
            String str = tenderService.queryTenderDueinV2(JsonUtil.beanToJson(map));
            log.info("AccountController tenderDuein ----> return:" + str);
            if (StringUtil.isNotBlank(str)) {
                JSONObject result = JSONObject.parseObject(str);
                if ("0".equals(String.valueOf(result.get("resultCode")))) {
                    JSONObject json = new JSONObject();
                    JSONObject pageUtils = result.getJSONObject("pageUtils");
                    resultJson.put("tenderDuein", pageUtils);
                    resultJson.put("resultCode", "0");
                    resultJson.put("desc", "获取回款计划成功");
                }
            }
        } catch (Exception e) {
            log.error("AccountController tenderDuein ----> arise exception:", e);
            resultJson.put("resultCode", -1);
            resultJson.put("desc", "操作失败，请稍后重试...");
        }
        log.info("AccountController tenderDuein ----> return to page:" + resultJson.toJSONString());
        return resultJson.toJSONString();
    }

    /**
     * 资金账户-->提现-->初始化
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/initDrawmoney", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String initDrawmoney(HttpServletRequest request) {
        //验证是否登录状态
        Object userObj = request.getSession().getAttribute("loginUser");
        JSONObject jsonObject = new JSONObject();
        if (null == userObj) {
            jsonObject.put("resultCode", -505);
            jsonObject.put("msg", "会话失效，请重新登录");
            return jsonObject.toJSONString();
        }
        User user = (User) userObj;
        try {
            jsonObject.put("userId", user.getUserId());
            //认证信息
            log.info("queryAppro req = " + jsonObject.toJSONString());
            String str = borrowQueryService.queryAppro(jsonObject.toString());
            log.info("queryAppro resp = " + str);
            PersonResponse res = JsonUtil.jsonToBean(str, PersonResponse.class);
            Appro appro = null;
            String dataStr = "";
            if (res.getData() != null) {
                dataStr = String.valueOf(res.getData());
                appro = JsonUtil.jsonToBean(dataStr, Appro.class);
            }
            jsonObject.put("appro", appro);

            if (null != appro && appro.getRealName().equals("1")) {
                log.info("queryRealNameByUserId req = " + jsonObject.toJSONString());
                str = accountOVService.queryRealNameByUserId(jsonObject.toString());
                log.info("queryRealNameByUserId resp = " + str);
                res = JsonUtil.jsonToBean(str, PersonResponse.class);
                RealNameAppro rnAppro = null;
                if (res.getData() != null) {
                    dataStr = String.valueOf(res.getData());
                    rnAppro = JsonUtil.jsonToBean(dataStr, RealNameAppro.class);
                }
                jsonObject.put("rnAppro", rnAppro);
            } else {
                jsonObject.put("msg", "您未申请实名认证，现在就去");
                jsonObject.put("resultCode", -101);
                return jsonObject.toJSONString();
            }

            if (!appro.getMobile().equals("1")) {
                jsonObject.put("msg", "您未进行手机认证，现在就去");
                jsonObject.put("resultCode", -102);
                return jsonObject.toJSONString();
            }

            //查询用户所有的银行卡信息
            log.info("queryBankInfoByUserId req = " + jsonObject.toJSONString());
            str = personalService.queryBankInfoByUserId(JsonUtil.beanToJson(jsonObject));
            log.info("queryBankInfoByUserId resp =" + str);
            res = JsonUtil.jsonToBean(str, PersonResponse.class);
            List<BankInfo> bankList = null;

            if (res.getData() != null) {
                dataStr = String.valueOf(res.getData());
                bankList = JsonUtil.jsonToList(dataStr, BankInfo.class);
            }
            //如果当前用户没有绑定过任何的银行卡，需要自动跳转到银行卡设置页面
            if (null == bankList || bankList.size() == 0) {
                jsonObject.put("msg", "您还没有添加银行卡，请先添加一张银行卡才能提现哦！");
                jsonObject.put("resultCode", -100);
                return jsonObject.toJSONString();
            }

            jsonObject.put("payBankDic", DicCommonUtils.getCommonList("PAY_BANK"));
            jsonObject.put("bankList", bankList);
            jsonObject.put("resultCode", 0);
        } catch (Exception e) {
            log.error("获取提现信息异常", e);
            jsonObject.put("msg", "获取您的提现信息失败，请尝试重新进入提现页面");
            jsonObject.put("resultCode", -200);
        }
        return jsonObject.toString();
    }

    /**
     * 加载提现页面数据
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/loadDrawmoney", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String loadDrawmoney(HttpServletRequest request) {
        JSONObject resultJson = new JSONObject();
        //验证是否登录状态
        Object userObj = request.getSession().getAttribute("loginUser");
        if (null == userObj) {
            //会话过期
            resultJson.put("resultCode", -505);
            resultJson.put("msg", "会话失效，请重新登录");
            return resultJson.toString();
        }
        User user = (User) userObj;
        //资金账户
        Account account = getAccountByUserId(user.getUserId());
        resultJson.put("account", account);

        //页面选择的提现银行卡ID
        String bankId = request.getParameter("bankId");

        if (StringUtils.isNotBlank(bankId)) {
            //封装参数
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", user.getUserId());
            jsonObject.put("userBankId", Long.parseLong(bankId));
            log.info("getMaxcashAmount req = " + jsonObject.toJSONString());
            String str = accountCashCXFService.getMaxcashAmount(JsonUtil.beanToJson(jsonObject));
            log.info("getMaxcashAmount resp = " + str);
            WSModelResponse ws = JsonUtil.jsonToBean(str, WSModelResponse.class);
            if (ws.getData() != null) {
                BigDecimal couldCashMoney = new BigDecimal(ws.getData().toString());
                resultJson.put("couldCashMoney", couldCashMoney);
            }
        }
        return resultJson.toString();
    }

    public Account getAccountByUserId(Long userId) {
        Map map = new HashMap();
        map.put("useId", userId);
        map.put("accountType", AccountConsts.ACCOUNT_ONLINE_DEFAULT_TYPE_ACCOUNT);
        String str = accountQueryCXFService.selectAccountByIdAndType(JsonUtil.beanToJson(map));
        AccountResponse res = JsonUtil.jsonToBean(str, AccountResponse.class);
        Account account = null;
        if (res.getData() != null) {
            String dataStr = String.valueOf(res.getData());
            account = JsonUtil.jsonToBean(dataStr, Account.class);
        }
        return account;
    }

    /**
     * 发送手机验证码短信
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/sendSMS", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String sendSMS(HttpServletRequest request, HttpServletResponse response) {
        JSONObject resultJson = new JSONObject();
        //session中的用户对象
        Object userObj = request.getSession().getAttribute("loginUser");
        if (null == userObj) {
            resultJson.put("resultCode", -505);
            resultJson.put("msg", "会话失效，请重新登陆");
            return resultJson.toString();
        }

        User user = (User) userObj;
        try {
            //获取用户认证手机号
            JSONObject obj = new JSONObject();
            obj.put("userId", user.getUserId());
            log.info("drawmoney sendSMS queryMobileByUserId req = " + obj.toJSONString());
            String str = accountOVService.queryMobileByUserId(obj.toString());
            log.info("resp = " + str);
            PersonResponse res = JsonUtil.jsonToBean(str, PersonResponse.class);
            MobileAppro mobileAppro = null;
            if (res.getData() != null) {
                mobileAppro = JsonUtil.jsonToBean(res.getData().toString(), MobileAppro.class);
            }
            if (!(mobileAppro != null && "1".equals(mobileAppro.getStatus()))) {
                resultJson.put("msg", "您还没有认证您的手机号码，前往认证");
                resultJson.put("resultCode", -1);
                return resultJson.toString();
            }

            //校验手机验证码发送次数限制
//            if(!MessageUtils.checkSendSMSCount(SmsConsts.BUSICODE_CASH_MONEY + user.getMobile(), request, response)) {
//                resultJson.put("msg", "您短信发送太频繁，如果无法收到短信请联系客服或使用语音短信！");
//                resultJson.put("resultCode", -30);
//                return resultJson.toString();
//            }
            switch (MessageUtils.checkVerifyCodeTimeLimit(SmsConsts.BUSICODE_CASH_MONEY + user.getMobile(), MessageUtils.SENDTYPE_SMS, request, response)) {
                case 0:
                    break;
                case 1:
                    resultJson.put("msg", "一小时内最多可获取" + MessageUtils.SENDLIMIT_SMS_TIMES_1HOUR + "次短信验证码！");
                    resultJson.put("resultCode", -31);
                    return resultJson.toString();
                case 2:
                    resultJson.put("msg", "一天内最多可获取" + MessageUtils.SENDLIMIT_SMS_TIMES_24HOUR + "次短信验证码！");
                    resultJson.put("resultCode", -31);
                    return resultJson.toString();
                default:
                    break;
            }

            int randCode = (int) Math.round(Math.random() * 8999) + 1000;
            String content = "尊敬的用户：你正在提现操作，验证码是：" + randCode + "，工作人员不会索取，请勿泄漏，若有问题可致电客服：4000 169 521";

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("content", content);
            jsonObject.put("userId", user.getUserId());
            jsonObject.put("phone", user.getMobile());
            jsonObject.put("sendIp", HttpUtil.getRealIpAddr(request));
            log.info("drawmoney sendSMS req = " + jsonObject.toJSONString());
            String jsonstr = smsCXFService.sendSMS(jsonObject.toJSONString());
            log.info("resp = " + jsonstr);
            WSResponse wsr = JsonUtil.jsonToBean(jsonstr, WSResponse.class);
            String msg = wsr.getDesc();
            //session保存手机的手机验证码
            if (wsr.getResultCode() == 0) {
                request.getSession().setAttribute(SmsConsts.BUSICODE_CASH_MONEY, randCode);
                msg = "验证码已发送到您尾号为" + user.getMobile().substring(7, 11) + "的手机号";
                log.info(content);
                log.info(msg);
            }
            resultJson.put("msg", msg);
            resultJson.put("resultCode", wsr.getResultCode());
        } catch (Exception e) {
            log.error("sendSMS ERROR:" + e.getMessage(), e);
            resultJson.put("msg", "验证码发送异常，请刷新页面重发");
            resultJson.put("resultCode", -100);
        }
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
        log.info("drawmoney sendSMS queryMobileByUserId req = " + obj.toJSONString());
        String str1 = accountOVService.queryMobileByUserId(obj.toString());
        log.info("resp = " + str1);
        PersonResponse res = JsonUtil.jsonToBean(str1, PersonResponse.class);
        MobileAppro mobileAppro = null;
        if (res.getData() != null) {
            mobileAppro = JsonUtil.jsonToBean(res.getData().toString(), MobileAppro.class);
        }
        if (!(mobileAppro != null && "1".equals(mobileAppro.getStatus()))) {
            resultJson.put("msg", "您还没有认证您的手机号码，前往认证");
            resultJson.put("resultCode", -1);
            return resultJson.toString();
        }

        //校验手机验证码发送次数限制
//        if(!MessageUtils.checkSendSMSCount(SmsConsts.BUSICODE_CASH_MONEY_VOICE + user.getMobile(), request, response)) {
//            resultJson.put("msg", "您语音发送太频繁，请稍后重试！");
//            resultJson.put("resultCode", -2);
//            return resultJson.toString();
//        }
        switch (MessageUtils.checkVerifyCodeTimeLimit(SmsConsts.BUSICODE_CASH_MONEY_VOICE + user.getMobile(), MessageUtils.SENDTYPE_VOICE, request, response)) {
            case 0:
                break;
            case 1:
                resultJson.put("msg", "一小时内最多可获取" + MessageUtils.SENDLIMIT_VOICE_TIMES_1HOUR + "次语音验证码！");
                resultJson.put("resultCode", -31);
                return resultJson.toString();
            case 2:
                resultJson.put("msg", "一天内最多可获取" + MessageUtils.SENDLIMIT_VOICE_TIMES_24HOUR + "次语音验证码！");
                resultJson.put("resultCode", -31);
                return resultJson.toString();
            default:
                break;
        }

        int randCode = (int) Math.round(Math.random() * 8999) + 1000;
        log.info("语音验证码：" + randCode);
        // 功能编号
        String busiCode = SmsConsts.BUSICODE_MOBILE_APPRO;
        String msg = "";
        int resultCode = 0;
        try {
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
                request.getSession().setAttribute(SmsConsts.BUSICODE_CASH_MONEY, randCode);
                log.info("语音验证码发送成功...");
            } else {
                resultCode = -2;
                msg = ws.getDesc();
                log.info("语音验证码发送失败：" + msg);
            }
        } catch (Exception e) {
            msg = "获取语音验证码失败，请刷新重试";
            resultCode = -3;
            log.error("sendVoiceSMSLogin ERROR:" + e.getMessage(), e);
        }
        resultJson.put("msg", msg);
        resultJson.put("resultCode", resultCode);
        return resultJson.toString();
    }

    /**
     * 本月收益
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/earningsMonth", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String earningsMonth(HttpServletRequest request) {
        JSONObject resultJson = new JSONObject();
        try {
            Object userObj = request.getSession().getAttribute("loginUser");
            if (null == userObj) {
                resultJson.put("code", -505);
                resultJson.put("msg", "会话失效，请重新登陆");
                return resultJson.toString();
            }
            User user = (User) userObj;

            String month = "yyyy-mm";//本月(sql查询格式)
            BigDecimal interestEarnSum_month = BigDecimal.ZERO;//投标收益

            JSONObject paramsJson = new JSONObject();
            paramsJson.put("userId", user.getUserId());
            paramsJson.put("format", month);

            //投标收益：散标(七天大胜、_、_、新新宝、新手标、散标)+_ (本月)
            //log.info("AccountController earningsMonth ----> params:" + paramsJson.toJSONString());
            String str = borrowQueryCXFService.getInterestEarnSum(paramsJson.toString());
            //log.info("AccountController earningsMonth ----> return:" + str);
            WSModelResponse interestEarnResponse_month = JsonUtil.jsonToBean(str, WSModelResponse.class);

            interestEarnSum_month = new BigDecimal(interestEarnResponse_month.getData().toString());

            BigDecimal sumEarnedThisMonth = BigDecimal.ZERO;//本月已赚取
            sumEarnedThisMonth = sumEarnedThisMonth.add(interestEarnSum_month);

            resultJson.put("curmonintest", sumEarnedThisMonth);
        } catch (Exception e) {
            log.error("AccountController earningsMonth ----> arise exception:", e);
            resultJson.put("code", -100);
            resultJson.put("msg", "获取本月收益异常，请刷新页面重试");
        }
        //log.info("AccountController earningsMonth ----> return to page:" + resultJson.toJSONString());
        return resultJson.toString();
    }

    /**
     * 意见反馈获取
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getFeedBackInfo", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getFeedBackInfo(HttpServletRequest request) {
        JSONObject resultJson = new JSONObject();
        Object userObj = request.getSession().getAttribute("loginUser");
        if (null == userObj) {
            resultJson.put("resultCode", -505);
            resultJson.put("msg", "会话失效，请重新登陆");
            return resultJson.toString();
        }
        try {
            JSONObject object = new JSONObject();
            object.put("terminalver", "webapp");
           // log.info("AccountCotroller getFeedBackInfo ----> params:" + object.toJSONString());
            String str = feedbackService.queryFeedbackValue(object.toString());
           //log.info("AccountCotroller getFeedBackInfo ----> result:" + str);
            WSModelResponse res = JsonUtil.jsonToBean(str, WSModelResponse.class);
            List<FeedbackItemsBo> degrees = null;
            String dataStr = "";
            if (res.getData() != null) {
                dataStr = String.valueOf(res.getData());
                degrees = JsonUtil.jsonToList(dataStr, FeedbackItemsBo.class);

            }
            resultJson.put("degrees", degrees);
            resultJson.put("resultCode", res.getResultCode());
        } catch (Exception e) {
            log.error("get feedback info ERROR:" + e.getMessage(), e);
            resultJson.put("resultCode", -100);
            resultJson.put("msg", "获取意见反馈异常，请刷新页面重试");
        }
        return resultJson.toString();
    }

    /**
     * 意见反馈
     *
     * @param request
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/addFeedback", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String addFeedback(HttpServletRequest request) throws ParseException {
        JSONObject resultJson = new JSONObject();
        User user = (User) request.getSession().getAttribute("loginUser");
        if (null == user) {
            resultJson.put("resultCode", -505);
            resultJson.put("msg", "会话失效，请重新登陆");
            return resultJson.toString();
        }
        try {
            String userName = user.getUserName();
            JSONObject json = new JSONObject();
            json.put("statuscode", 300);
            String content = request.getParameter("content");
            String items = request.getParameter("items");
            json.put("contact", user.getMobile());
            json.put("content", content);
            json.put("sendIp", HttpUtil.getRealIpAddr(request));
            /*JSONObject terminalVer = new JSONObject();
            terminalVer.put("user-agent", request.getHeader("user-agent"));
            terminalVer.put("type", "webapp"); */
            json.put("terminalver", "webapp");
            json.put("userName", userName);
            json.put("items", items);
            log.info("AccountCotroller addFeedBackInfo ----> params:" + json.toJSONString());
            String addFeedbackInfo = feedbackService.addFeedback(json.toJSONString());
            log.info("AccountCotroller addFeedBackInfo ----> result:" + addFeedbackInfo);
            WSModelResponse mecResponse = JsonUtil.jsonToBean(addFeedbackInfo,
                    WSModelResponse.class);
            resultJson.put("resultCode", mecResponse.getResultCode());
            resultJson.put("msg", mecResponse.getDesc());
        } catch (Exception e) {
            log.error("add feedback info ERROR:" + e.getMessage(), e);
            resultJson.put("resultCode", -100);
            resultJson.put("msg", "提交意见反馈异常，请刷新页面重试");
        }
        return resultJson.toString();
    }

    /**
     * 我的__我的投资(累计投资、累计收益、待收收益)
     *
     * @param userId
     * @return
     */
    private Map<String, Map> getMyInvest(long userId) {
        Map sanbiaoMap = null;//散标
        Map xinyuanbaoMap = null;//_
        Map ririyingMap = null;//_
        Map xinxinbaoMap = null;//新新宝
        Map xinshoubiaoMap = null;//新手标
        Map yuejindoujinMap = null; //_
        Map qitiandashengMap = null;//七天大胜
        Map stepUpwardMap = null;//步步高升
        Map yueyuepaiMap = null;//_

        Map<String, Map> investMap = null;

        try {
            /******散标******/
            //标的类型1信用标2推荐标3净值标4秒还标5调剂标6抵押标7新新宝8新生贷9新商贷10新房贷11菁英贷12信网贷13票小宝14新车贷15分期贷16新手标
            List<Integer> borrowTypeList_sanbiao = new ArrayList<Integer>();
            borrowTypeList_sanbiao.add(BorrowConsts.BORROW_CREDIT_TYPE);//1
            borrowTypeList_sanbiao.add(BorrowConsts.BORROW_RECOMMEND_TYPE);//2
            borrowTypeList_sanbiao.add(BorrowConsts.BORROW_NETWORTH_TYPE);//3
            borrowTypeList_sanbiao.add(BorrowConsts.BORROW_MORTGAGE_TYPE);//6
            borrowTypeList_sanbiao.add(BorrowConsts.BORROW_STUDENT_TYPE);//8
            borrowTypeList_sanbiao.add(BorrowConsts.BORROW_BUSINESS_TYPE);//9
            borrowTypeList_sanbiao.add(BorrowConsts.BORROW_HOUSE_TYPE);//10
            borrowTypeList_sanbiao.add(BorrowConsts.BORROW_ELITE_TYPE);//11
            borrowTypeList_sanbiao.add(BorrowConsts.BORROW_NET_TYPE);//12
            borrowTypeList_sanbiao.add(BorrowConsts.BORROW_BILL_TYPE);//13
            borrowTypeList_sanbiao.add(BorrowConsts.BORROW_CAR_TYPE);//14
            borrowTypeList_sanbiao.add(BorrowConsts.BORROW_NEWMALL_TYPE);//15

            /******散标******/
            //累计投资、累计收益、待收收益
            JSONObject mySanBiaoJson = new JSONObject();
            mySanBiaoJson.put("userId", userId);
            mySanBiaoJson.put("borrowTypeList", borrowTypeList_sanbiao);
            String myReturnSanBiaoStr = borrowQueryCXFService.querySanBiaoInvest(mySanBiaoJson.toString());
            WSModelResponse mySanBiaoResponse = JsonUtil.jsonToBean(myReturnSanBiaoStr, WSModelResponse.class);
            if (null != mySanBiaoResponse.getData()) {
                Map resultMap = JsonUtil.jsonToBean(mySanBiaoResponse.getData().toString(), Map.class);
                //如果累计投资大于0
                if (null != resultMap && resultMap.get("EFFECTIVEMONEY") != null && BigDecimal.ZERO.compareTo(new BigDecimal(resultMap.get("EFFECTIVEMONEY").toString())) != 0) {
                    sanbiaoMap = resultMap;
                }
            }

            /******_******/
            //根据账户ID和账号类型查询用户账户
            Account account1004 = this.getAccountByUserIdAndType(userId, AccountConsts.ACCOUNT_X_PLAN_ACCOUNT);
            if (null != account1004) {
                //累计投资、累计收益、待收收益
                JSONObject myXinyuanbaoJson = new JSONObject();
                myXinyuanbaoJson.put("userId", userId);
                String myXinyuanbaoStr = borrowQueryCXFService.queryXinyuanbaoInvest(myXinyuanbaoJson.toString());
                WSModelResponse myXinyuanbaoResponse = JsonUtil.jsonToBean(myXinyuanbaoStr, WSModelResponse.class);
                if (null != myXinyuanbaoResponse.getData()) {
                    List xinyuanbaoList = JsonUtil.jsonToList(myXinyuanbaoResponse.getData().toString(), Map.class);
                    if (null != xinyuanbaoList && xinyuanbaoList.size() > 0) {
                        Map map = (Map) xinyuanbaoList.get(0);
                        if (map != null && BigDecimal.ZERO.compareTo(new BigDecimal(map.get("EFFECTIVEMONEY").toString())) != 0) {
                            xinyuanbaoMap = new HashMap();
                            xinyuanbaoMap.put("EFFECTIVEMONEY", map.get("EFFECTIVEMONEY"));//累计投资
                            xinyuanbaoMap.put("COLLECTEDINTEREST", map.get("COLLECTEDINTEREST"));//累计收益
                            xinyuanbaoMap.put("COLLECTINTEREST", map.get("COLLECTINTEREST"));//待收收益
                        }
                    }
                }
            }

            /******_******/
            Account account1005 = this.getAccountByUserIdAndType(userId, AccountConsts.ACCOUNT_FUND_TYPE_ACCOUNT);
            if (null != account1005) {
                ririyingMap = new HashMap();
                //累计投资：累计转入的钱
                ririyingMap.put("EFFECTIVEMONEY", account1005.getInnerInSum());

                BigDecimal fundEarnTotal = BigDecimal.ZERO;//累计利息

                //查询_的累计利息
                JSONObject jsonTotalObj = new JSONObject();
                jsonTotalObj.put("userId", userId);
                jsonTotalObj.put("type", FundConstant.FUND_TRADE_TYPE_PURCHASE);
                jsonTotalObj.put("initiator", FundConstant.FUND_TRADE_INITIATOR_SYSTEM);
                String totalStr = userFundTradeCXFService.selectTotalTradeNum(jsonTotalObj.toJSONString());
                WSModelResponse fundResp = JsonUtil.jsonToBean(totalStr, WSModelResponse.class);
                if (fundResp.getData() != null) {
                    fundEarnTotal = new BigDecimal(fundResp.getData().toString());
                }
                ririyingMap.put("COLLECTEDINTEREST", fundEarnTotal);//累计收益
            }

            /******新新宝******/
            //累计投资、投资收益、待收收益
            xinxinbaoMap = this.getXinxinbaoOrXinshoubiaoInvest(userId, BorrowConsts.BORROW_XXBAO_TYPE);//新新宝7

            /******新手标******/
            //累计投资、投资收益、待收收益
            xinshoubiaoMap = this.getXinxinbaoOrXinshoubiaoInvest(userId, BorrowConsts.BORROW_XSB_TYPE);//新手标16

            /******_******/
            //获取_:待收总额、累计投资、累计收益、待收收益
            Map yuejindoujin_map = this.getYuejindoujinOrQitiandasheng(userId, Constant.MONTH_GOLD_PRODUCT_TYPE);

            //累计投资、累计已收益、待收收益
            if (yuejindoujin_map != null && BigDecimal.ZERO.compareTo(new BigDecimal(yuejindoujin_map.get("EFFECTIVEMONEY").toString())) != 0) {
                yuejindoujinMap = new HashMap();
                yuejindoujinMap.put("EFFECTIVEMONEY", yuejindoujin_map != null ? yuejindoujin_map.get("EFFECTIVEMONEY") : BigDecimal.ZERO);//累计投资
                yuejindoujinMap.put("COLLECTEDINTEREST", yuejindoujin_map != null ? yuejindoujin_map.get("COLLECTEDINTEREST") : BigDecimal.ZERO);//累计已收益
                yuejindoujinMap.put("COLLECTINTEREST", yuejindoujin_map != null ? yuejindoujin_map.get("COLLECTINTEREST") : BigDecimal.ZERO);//待收收益
            }

            /******七天大胜******/
            //获取七天大胜:待收总额、累计投资、累计收益、待收收益
            Map qitiandasheng_map = this.getYuejindoujinOrQitiandasheng(userId, Constant.SEVEN_GOLD_PRODUCT_TYPE);
            //累计投资、投资收益、待收收益
            if (qitiandasheng_map != null && BigDecimal.ZERO.compareTo(new BigDecimal(qitiandasheng_map.get("EFFECTIVEMONEY").toString())) != 0) {
                qitiandashengMap = new HashMap();
                qitiandashengMap.put("EFFECTIVEMONEY", qitiandasheng_map != null ? qitiandasheng_map.get("EFFECTIVEMONEY") : BigDecimal.ZERO);//累计投资
                qitiandashengMap.put("COLLECTEDINTEREST", qitiandasheng_map != null ? qitiandasheng_map.get("COLLECTEDINTEREST") : BigDecimal.ZERO);//投资收益
                qitiandashengMap.put("COLLECTINTEREST", qitiandasheng_map != null ? qitiandasheng_map.get("COLLECTINTEREST") : BigDecimal.ZERO);//待收收益
            }

            /******步步高升******/
            ////累计投资总额:accountTotal, 当前持有总额: remaCapitalTotal：累计收益总额:interestTotal：待收收益collectInterest
            Map stepUpward_map = this.getStepUpward(userId);
            if (stepUpward_map != null) {
                stepUpwardMap = new HashMap();
                stepUpwardMap.put("EFFECTIVEMONEY", stepUpward_map != null ? stepUpward_map.get("accountTotal") : BigDecimal.ZERO);//累计投资
                stepUpwardMap.put("COLLECTEDINTEREST", stepUpward_map != null ? stepUpward_map.get("interestTotal") : BigDecimal.ZERO);//投资收益
                stepUpwardMap.put("COLLECTINTEREST", stepUpward_map != null ? stepUpward_map.get("collectInterest") : BigDecimal.ZERO);//待收收益
                //stepUpwardMap.put("COLLECTINTEREST", BigDecimal.ZERO);//待收收益(不统计)
            }
            /******_******/
            ////累计投资总额:accountTotal, 当前持有总额: remaCapitalTotal：累计收益总额:interestTotal：待收收益collectInterest
            Map yueyuepai_map = this.getYyp(userId);
            if (yueyuepai_map != null) {
                yueyuepaiMap = new HashMap();
                yueyuepaiMap.put("EFFECTIVEMONEY", yueyuepai_map != null ? yueyuepai_map.get("EFFECTIVEMONEY") : BigDecimal.ZERO);//累计投资
                yueyuepaiMap.put("COLLECTEDINTEREST", yueyuepai_map != null ? yueyuepai_map.get("REALINTEREST") : BigDecimal.ZERO);//投资收益
                yueyuepaiMap.put("COLLECTINTEREST", yueyuepai_map != null ? yueyuepai_map.get("WAITINTEREST") : BigDecimal.ZERO);//待收收益
            }

            investMap = new HashMap<String, Map>();
            investMap.put("sanbiaoMap", sanbiaoMap);
            investMap.put("xinyuanbaoMap", xinyuanbaoMap);
            investMap.put("ririyingMap", ririyingMap);
            investMap.put("xinxinbaoMap", xinxinbaoMap);
            investMap.put("xinshoubiaoMap", xinshoubiaoMap);
            investMap.put("yuejindoujinMap", yuejindoujinMap);
            investMap.put("qitiandashengMap", qitiandashengMap);
            investMap.put("stepUpwardMap", stepUpwardMap);
            investMap.put("yueyuepaiMap", yueyuepaiMap);
        } catch (Exception e) {
            log.error("[AccountViewController.getMyInvest]我的_：我的投资 查询异常", e);
        }
        return investMap;
    }

    /**
     * 根据用户id和账户类型获取用户账户信息
     *
     * @param userId 用户id
     * @param type   账户类型
     * @return
     */
    public Account getAccountByUserIdAndType(long userId, String type) {
        Account account = null;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("useId", userId);//用户id
        map.put("accountType", type);//账户类型

        String returnStr = accountQueryCXFService.selectAccountByIdAndType(JsonUtil.beanToJson(map));
        AccountResponse response = JsonUtil.jsonToBean(returnStr, AccountResponse.class);
        if (response.getData() != null) {
            String dataStr = String.valueOf(response.getData());
            if (StringUtil.isNotEmpty(dataStr)) {
                account = JsonUtil.jsonToBean(dataStr, Account.class);
            }
        }
        return account;
    }

    /**
     * 我的__我的投资_新新宝 或 新手标:累计投资、累计收益、待收收益
     *
     * @param userId
     * @param borrowType
     * @return
     */
    public Map getXinxinbaoOrXinshoubiaoInvest(long userId, Integer borrowType) {
        Map returnMap = null;
        try {
            JSONObject json = new JSONObject();
            json.put("userId", userId);
            json.put("borrowType", borrowType);
            String returnStr = borrowQueryCXFService.queryXinxinbaoOrXinshoubiaoInvest(json.toString());
            WSModelResponse response = JsonUtil.jsonToBean(returnStr, WSModelResponse.class);
            if (null != response.getData()) {
                Map resultMap = JsonUtil.jsonToBean(response.getData().toString(), Map.class);
                //如果累计投资大于0
                if (null != resultMap && resultMap.get("EFFECTIVEMONEY") != null && BigDecimal.ZERO.compareTo(new BigDecimal(resultMap.get("EFFECTIVEMONEY").toString())) != 0) {
                    returnMap = resultMap;
                }
            }
        } catch (Exception e) {
            log.error("[AccountViewController.getXinxinbaoOrXinshoubiaoInvest]我的_：我的投资__或新手标,查询异常", e);
        }
        return returnMap;
    }

    /**
     * 我的__我的投资__ 或 七天大胜:待收总额
     * 我的__我的投资__ 或 七天大胜:累计投资、累计收益、待收收益
     *
     * @param userId
     * @return
     */
    private Map getYuejindoujinOrQitiandasheng(long userId, String prodspell) {
        Map returnmap = null;
        try {
            //_的待收总额、累计投资、累计收益、待收收益
            JSONObject json = new JSONObject();
            json.put("userId", userId);
            json.put("prodspell", prodspell);
            String returnStr = borrowQueryCXFService.queryProductAcountByProdspellAndUserId(json.toString());
            WSModelResponse response = JsonUtil.jsonToBean(returnStr, WSModelResponse.class);
            if (null != response.getData()) {
                List list = JsonUtil.jsonToList(response.getData().toString(), Map.class);
                if (null != list && list.size() > 0) {
                    Map temp = (Map) list.get(0);
                    returnmap = temp;
                }
            }
        } catch (Exception e) {
            log.error("[AccountViewController.getYuejindoujinOrQitiandasheng]我的_：_ 或 七天大胜,查询异常", e);
        }
        return returnmap;
    }

    /**
     * 我的__我的投资_步步高升
     * 我的__我的投资_步步高升:累计投资、累计收益、待收收益
     *
     * @param userId
     * @return
     */
    private Map getStepUpward(long userId) {
        Map stepMap = null;
        try {
            /******步步高升******/
            JSONObject stepJson = new JSONObject();
            stepJson.put("userId", userId);
            //累计投资总额:accountTotal, 当前持有总额: remaCapitalTotal：累计收益总额:interestTotal：待收收益collectInterest
            String stepStr = borrowQueryCXFService.queryStepInvest(stepJson.toString());
            stepMap = JsonUtil.jsonToBean(stepStr, Map.class);
        } catch (Exception e) {
            log.error("[AccountViewController.getYuejindoujinOrQitiandasheng]我的_：_ 或 七天大胜,查询异常", e);
        }
        return stepMap;
    }

    /**
     * 我的__我的投资__
     * 我的__我的投资__:累计投资、累计收益、待收收益
     *
     * @param userId
     * @return
     */
    private Map getYyp(long userId) {
        Map yypMap = null;
        try {
            /******_******/
            JSONObject yypJson = new JSONObject();
            yypJson.put("userId", userId);
            //累计投资总额:EFFECTIVEMONEY, 累计收益总额:COLLECTEDINTEREST：待收收益COLLECTINTEREST
            String str = reglIntstCXFService.queryInvestInfo(yypJson.toString());
            //log.info("我的__我的投资__:" + str);
            JSONObject jo = JSONObject.parseObject(str);
            yypMap = JsonUtil.jsonToBean(jo.get("data").toString(), Map.class);
        } catch (Exception e) {
            log.error("[AccountViewController.getYuejindoujinOrQitiandasheng]我的_：_ 或 七天大胜,查询异常", e);
        }
        return yypMap;
    }

    @RequestMapping(value = "/selectAccount", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String selectAccount(HttpServletRequest request) {
        JSONObject resultJson = new JSONObject();
        try {
            Object obj = request.getSession().getAttribute("loginUser");
            if (null == obj) {
                resultJson.put("resultCode", -505);
                resultJson.put("msg", "会话失效，请重新登陆");
                return resultJson.toString();
            }

            User user = (User) obj;
            //查询默认账户信息
            JSONObject accountJson = new JSONObject();
            accountJson.put("useId", user.getUserId());
            accountJson.put("accountType", com.xxdai.plan.constants.AccountConsts.ACCOUNT_ONLINE_DEFAULT_TYPE_ACCOUNT);
            String resultAccountStr = accountQueryCXFService.selectAccountByIdAndType(accountJson.toJSONString());
            WSModelResponse accountResponse = JsonUtil.jsonToBean(resultAccountStr, WSModelResponse.class);
            if (accountResponse.getData() != null) {
                JSONObject defaultAccount = JSONObject.parseObject(accountResponse.getData().toString());
                resultJson.put("defaultAccount", defaultAccount);
                resultJson.put("resultCode", 0);
            } else {
                resultJson.put("resultCode", 100);
            }
        } catch (Exception e) {
            log.error("selectAccount error", e);
            resultJson.put("resultCode", 100);
        }
        return resultJson.toJSONString();
    }

    @RequestMapping(value = "/getRedpacketList", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getRedpacketList(HttpServletRequest request) {
        JSONObject resultJson = new JSONObject();
        try {
            Object obj = request.getSession().getAttribute("loginUser");
            if (null == obj) {
                resultJson.put("resultCode", -505);
                resultJson.put("msg", "会话失效，请重新登陆");
                return resultJson.toString();
            }
            User user = (User) obj;

            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("pageIndex", 1);
            jsonObject1.put("pageSize", 999);
            jsonObject1.put("userId", user.getUserId());
            jsonObject1.put("status", 1);
            jsonObject1.put("btype", 0);
            jsonObject1.put("prodType", RedEnvelopeLimitTypeConsts.XPLAN_USED_PRODUCT_TYPE);
            jsonObject1.put("terminalVer", HttpTookit.getRequestTerminal(request));
            jsonArray.add(jsonObject1);
            String str = redpacketCXFService.getRedpacketListByUseCodition(jsonArray.toString());

            RpWsResponse res = JsonUtil.jsonToBean(str, RpWsResponse.class);
            if (res.getResultCode() < 0) {
                log.info("红包获取失败：" + res.getDesc());
                resultJson.put("resultCode", 100);
            } else {
                if (res.getData() != null) {
                    List<RedenvelopeRecord> list = JsonUtil.jsonToList(res.getData().toString(), RedenvelopeRecord.class);
                    resultJson.put("redpacketList", list);
                    resultJson.put("resultCode", 0);
                }
            }
        } catch (Exception e) {
            log.error("selectAccount error", e);
            resultJson.put("resultCode", 100);
        }
        return resultJson.toJSONString();
    }

    /**
     * 查询用户账户信息（余额等，用于购买页面）
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getUserAccountInfo", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getUserAccountInfo(HttpServletRequest request, HttpServletRequest response) {
        JSONObject paramJson = new JSONObject();
        JSONObject resultJson = new JSONObject();
        try {
            // 用户信息
            User user = (User) request.getSession().getAttribute("loginUser");
            // 是否是登录状态
            if (null != user) {
                // 查询默认账户信息
                JSONObject accountJson = new JSONObject();
                accountJson.put("useId", user.getUserId());
                accountJson.put("accountType", AccountConsts.ACCOUNT_ONLINE_DEFAULT_TYPE_ACCOUNT);
                //log.info("AccountController getUserAccountInfo ----> params:" + accountJson.toJSONString());
                String resultAccountStr = accountQueryCXFService.selectAccountByIdAndType(accountJson.toJSONString());
                //log.info("AccountController getUserAccountInfo ----> return:" + resultAccountStr);
                WSModelResponse accountResponse = JsonUtil.jsonToBean(resultAccountStr, WSModelResponse.class);
                if (accountResponse.getData() != null) {
                    JSONObject defaultAccount = JSONObject.parseObject(accountResponse.getData().toString());
                    resultJson.put("defaultAccount", defaultAccount);
                }
                resultJson.put("resultCode", 0);

            } else {
                resultJson.put("resultCode", -2);
                resultJson.put("desc", "用户未登录");
            }

        } catch (Exception e) {
            log.error("AccountController getUserAccountInfo ----> arise exception:", e);
            //返回页面
            resultJson.put("resultCode", -1);
            resultJson.put("desc", "操作失败，请稍后重试...");
        }
       // log.info("AccountController getUserAccountInfo ----> return to page:" + resultJson.toJSONString());
        return resultJson.toJSONString();
    }

    /**
     * 获取红包
     */
    @RequestMapping(value = "/getRedPackets", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getRedPackets(HttpServletRequest request, HttpServletRequest response) throws Exception {
        JSONObject paramJson = new JSONObject();
        JSONObject resultJson = new JSONObject();
        try {
            // 用户信息
            User user = (User) request.getSession().getAttribute("loginUser");
            // 是否是登录状态
            if (null != user) {
                //1:散标		2:债权转让	3:_		4:步步高升	5:_
                String prodType = request.getParameter("prodType");
                if (StringUtils.isNotEmpty(prodType)) {
                    Integer pt = Integer.parseInt(prodType);
                    JSONArray jsonArray = new JSONArray();
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("pageIndex", 1);
                    jsonObject1.put("pageSize", 999);
                    jsonObject1.put("userId", (user).getUserId());
                    jsonObject1.put("status", 1);
                    jsonObject1.put("btype", 0);
                    jsonObject1.put("prodType", pt);
                    JSONObject terminalVer = new JSONObject();
                    terminalVer.put("user-agent", request.getHeader("user-agent"));
                    terminalVer.put("type", "WEB-APP");
                    jsonObject1.put("terminalVer", terminalVer);
                    jsonArray.add(jsonObject1);
                    //log.info("AccountController getRedPackets ----> params:" + jsonArray.toJSONString());
                    String str = redpacketCXFService.getRedpacketListByUseCodition(jsonArray.toString());
                    //log.info("AccountController getRedPackets ----> return:" + str);
                    RpWsResponse res = JsonUtil.jsonToBean(str, RpWsResponse.class);
                    if (res.getResultCode() < 0) {
                        resultJson.put("resultCode", -1);
                        resultJson.put("desc", "获取红包失败");
                    } else {
                        resultJson.put("resultCode", 0);
                        resultJson.put("desc", "获取红包成功");
                        if (res.getData() != null) {
                            List<RedenvelopeRecord> list = JsonUtil.jsonToList(res.getData().toString(), RedenvelopeRecord.class);
                            resultJson.put("redPacketList", list);
                        } else {
                            resultJson.put("redPacketList", new JSONArray());
                        }
                    }
                } else {
                    resultJson.put("resultCode", -1);
                    resultJson.put("desc", "产品类别不能为空");
                }

            } else {
                resultJson.put("resultCode", -2);
                resultJson.put("desc", "用户未登录");
            }

        } catch (Exception e) {
            log.error("AccountController getRedPackets ----> arise exception:", e);
            //返回页面
            resultJson.put("resultCode", -1);
            resultJson.put("desc", "操作失败，请稍后重试...");
        }
        //log.info("AccountController getRedPackets ----> return to page:" + resultJson.toJSONString());
        return resultJson.toJSONString();
    }

    @RequestMapping(value = "/getDrawMoneyCountMonthly", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getDrawMoneyCountMonthly(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            Object obj = request.getSession().getAttribute("loginUser");
            if (null == obj) {
                result.put("resultCode", -505);
                result.put("msg", "会话失效，请重新登陆");
                return result.toString();
            }
            User user = (User) obj;

            JSONObject param = new JSONObject();
            param.put("userId", user.getUserId());
            log.info("getDrawMoneyCountMonthly req = " + param.toJSONString());
            String respStr = accountCashCXFService.getDrawMoneyCountMonthly(param.toJSONString());
            log.info("getDrawMoneyCountMonthly resp =" + respStr);
            WSModelResponse wsResponse = JsonUtil.jsonToBean(respStr, WSModelResponse.class);
            String drawCountMonthly = new String("0");
            if (wsResponse.getResultCode() == AppConsts.WS_RETURN_SUCC) {
                drawCountMonthly = wsResponse.getData().toString();
            }
            String accountCashRate = CacheUtil.getCacheValue(AccountConsts.ACCOUNT_CASH_RATE);
            log.info("cache ACCOUNT_CASH_RATE " + accountCashRate);
            String accountCashFer = CacheUtil.getCacheValue(AccountConsts.ACCOUNT_CASH_FER);
            log.info("cache ACCOUNT_CASH_FER " + accountCashFer);
            String accountCashMinfee = com.xxdai.weixin.util.CacheUtil.getCacheValue(AccountConsts.ACCOUNT_CASH_MINFEE);
            log.info("cache ACCOUNT_CASH_MINFEE " + accountCashMinfee);
            String accountCashMaxfee = com.xxdai.weixin.util.CacheUtil.getCacheValue(AccountConsts.ACCOUNT_CASH_MAXFEE);
            log.info("cache ACCOUNT_CASH_MAXFEE " + accountCashMaxfee);
            result.put("resultCode", 0);
            result.put("accountCashRate", accountCashRate);
            result.put("accountCashFer", accountCashFer);
            result.put("drawCountMonthly", drawCountMonthly);
            result.put("accountCashMinfee", accountCashMinfee);
            result.put("accountCashMaxfee", accountCashMaxfee);
        } catch (Exception e) {
            log.error(e);
            result.put("resultCode", 400);
            result.put("msg", "异常");
        }
        return result.toJSONString();
    }

    @RequestMapping(value = "/getCacheCash", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getCacheCash(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            String accountCashRate = CacheUtil.getCacheValue(AccountConsts.ACCOUNT_CASH_RATE);
            log.info("cache ACCOUNT_CASH_RATE " + accountCashRate);
            String accountCashFer = CacheUtil.getCacheValue(AccountConsts.ACCOUNT_CASH_FER);
            log.info("cache ACCOUNT_CASH_FER " + accountCashFer);
            String accountCashMinfee = com.xxdai.weixin.util.CacheUtil.getCacheValue(AccountConsts.ACCOUNT_CASH_MINFEE);
            log.info("cache ACCOUNT_CASH_MINFEE " + accountCashMinfee);
            String accountCashMaxfee = com.xxdai.weixin.util.CacheUtil.getCacheValue(AccountConsts.ACCOUNT_CASH_MAXFEE);
            log.info("cache ACCOUNT_CASH_MAXFEE " + accountCashMaxfee);
            result.put("accountCashRate", accountCashRate);
            result.put("accountCashFer", accountCashFer);
            result.put("accountCashMinfee", accountCashMinfee);
            result.put("accountCashMaxfee", accountCashMaxfee);
            result.put("resultCode", 0);
        } catch (Exception e) {
            result.put("resultCode", 400);
            result.put("msg", "异常");
        }
        return result.toJSONString();
    }

    /**
     * 检查用户是否在白名单内
     * @param request
     * @return
     */
    @RequestMapping(value = "/checkCashWhiteList", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String checkCashWhiteList(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            Object obj = request.getSession().getAttribute("loginUser");
            if (null == obj) {
                result.put("resultCode", -505);
                result.put("msg", "会话失效，请重新登陆");
                return result.toString();
            }
            User user = (User) obj;

            JSONObject param = new JSONObject();
            param.put("userId", user.getUserId());
            log.info("checkCashWhiteList req = " + param.toJSONString());
            String respStr = cashWhiteListService.checkCashWhiteList(param.toJSONString());
            log.info("checkCashWhiteList resp =" + respStr);
            WSModelResponse wsResponse = JsonUtil.jsonToBean(respStr, WSModelResponse.class);
            result.put("resultCode", wsResponse.getResultCode());
            result.put("data", wsResponse.getData());
        } catch (Exception e) {
            log.error(e);
            result.put("resultCode", 400);
            result.put("msg", "异常");
        }
        return result.toJSONString();
    }

    @RequestMapping(value = "/initWithdraw", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String initWithdraw(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            String token = HttpTookit.getCookieValue(request,Constant.USERTOKEN);
            JSONObject data = accountService.initWithdraw(token,HttpTookit.getUserAgent(request));
            result.put("data",data);
            result.put("resultCode",0);
        }catch (Exception e) {
            log.error("initWithdraw error",e);
            result.put("resultCode",1);
        }finally {
            return result.toJSONString();
        }
    }

    @RequestMapping(value = "/doWithdrawCash")
    public ModelAndView doWithdrawCash(@RequestParam(value = "drawmoneyNum", required = true) String drawmoneyNum,  // 传入参数交易金额（money）
                                  HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        String token = HttpTookit.getCookieValue(request,Constant.USERTOKEN);
        JSONObject resultData = accountService.doWithdrawCash(token,drawmoneyNum,HttpTookit.getUserAgent(request));
        log.info("doWithdrawCash resp "+resultData.toJSONString());
        if(resultData.getIntValue("code") == 0) {
            JSONObject result = resultData.getJSONObject("data");
            modelAndView.setViewName("pay/fuiou/newPay_submit");
            modelAndView.addObject("mchnt_cd", result.getString("mchnt_cd"));
            modelAndView.addObject("mchnt_txn_ssn", result.getString("mchnt_txn_ssn"));
            modelAndView.addObject("login_id", result.getString("login_id"));
            modelAndView.addObject("amt", result.getString("amt"));
            modelAndView.addObject("page_notify_url", result.getString("page_notify_url"));
            modelAndView.addObject("signature",result.getString("signature"));            // 支付地址
            modelAndView.addObject("payurl", result.getString("requestUrl"));
            modelAndView.addObject("back_notify_url", result.getString("back_notify_url"));
        } else {
            if(resultData.getIntValue("code") == 200305) {
                request.getSession().invalidate();
            }
            modelAndView.setViewName("drawmoney/fail2");
            modelAndView.addObject("errorTitle","提现失败");
            modelAndView.addObject("errorMsg",resultData.getString("message"));
        }

        return modelAndView;
    }
}
