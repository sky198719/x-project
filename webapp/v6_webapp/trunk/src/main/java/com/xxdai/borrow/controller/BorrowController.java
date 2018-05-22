/**
 * Copyright (c) 2014, www.xxdai.com All Rights Reserved. 
 */
package com.xxdai.borrow.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxdai.borrow.bo.CompanyInfo;
import com.xxdai.borrow.constants.BorrowConsts;
import com.xxdai.borrow.constants.RedEnvelopeLimitTypeConsts;
import com.xxdai.borrow.model.BorrowQuery;
import com.xxdai.borrow.model.BorrowTenderDetail;
import com.xxdai.borrow.service.BorrowService;
import com.xxdai.borrow.webservice.entity.BorrowQueryWsResponse;
import com.xxdai.client.CXF_Factory;
import com.xxdai.common.DataResponse;
import com.xxdai.constant.AppConsts;
import com.xxdai.constant.Constant;
import com.xxdai.core.util.cipher.CipherException;
import com.xxdai.core.util.cipher.DigestUtil;
import com.xxdai.core.util.http.HttpUtil;
import com.xxdai.core.util.http.WSMapResponse;
import com.xxdai.core.util.http.WSResponse;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.core.util.lang.DateUtil;
import com.xxdai.core.util.lang.StringUtil;
import com.xxdai.encrypt.EscapeCode;
import com.xxdai.external.account.ws.AccountQueryCXFService;
import com.xxdai.external.accountcash.ws.AccountCashCXFService;
import com.xxdai.external.accountov.ws.AccountOVQueryCXFService;
import com.xxdai.external.borrow.ws.BorrowCXFService;
import com.xxdai.external.borrowApply.ws.BorrowApplyCXFService;
import com.xxdai.external.borrowBillingbond.ws.BorrowBillingbondCXFService;
import com.xxdai.external.borrowFee.ws.BorrowFeeCXFService;
import com.xxdai.external.borrowQuery.ws.BorrowQueryCXFService;
import com.xxdai.external.borrowTrade.ws.BorrowTradeCXFService;
import com.xxdai.external.fadada.ws.FaDaDaCXFService;
import com.xxdai.external.lendInfo.ws.LendInfoCXFService;
import com.xxdai.external.person.ws.PersonCXFService;
import com.xxdai.external.user.ws.User;
import com.xxdai.external.user.ws.UserCXFService;
import com.xxdai.http.Message;
import com.xxdai.person.bo.BaseInfo;
import com.xxdai.person.model.Appro;
import com.xxdai.person.model.PersonResponse;
import com.xxdai.product.service.ProductService;
import com.xxdai.redpacket.model.RedenvelopeRecord;
import com.xxdai.redpacket.model.RpWsResponse;
import com.xxdai.redpacket.webservice.interfaces.RedpacketCXFService;
import com.xxdai.user.model.UserResponse;
import com.xxdai.util.Configuration;
import com.xxdai.util.DicCommonUtils;
import com.xxdai.util.HttpTookit;
import com.xxdai.util.TokenUtil;
import com.xxdai.ws.util.WSModelResponse;
import com.xxdai.ws.util.WSPageResponse;
import net.sf.ehcache.transaction.xa.EhcacheXAException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * BorrowController
 *
 * @version $Id: BorrowController.java 9854 2015-01-27 02:50:31Z pufei $
 * @since jdk1.6
 */
@Controller
@RequestMapping(value = "/borrow")
public class BorrowController {

    /**
     * 日志记录器
     */
    private static final Logger log = Logger.getLogger(BorrowController.class);
    private BorrowQueryCXFService borrowQueryService = (BorrowQueryCXFService) CXF_Factory.getFactory(BorrowQueryCXFService.class, com.xxdai.util.Configuration.getInstance().getValue("webService_url") + "/borrowQueryWebService").create();

    private BorrowCXFService borrowCXFService = (BorrowCXFService) CXF_Factory.getFactory(BorrowCXFService.class, Configuration.getInstance().getValue("webService_url") + "/borrowWebService").create();
    private AccountQueryCXFService accountQueryCXFService = (AccountQueryCXFService) CXF_Factory.getFactory(AccountQueryCXFService.class, com.xxdai.util.Configuration.getInstance().getValue("trade_url") + "/accountQueryWebService").create();
    private BorrowTradeCXFService borrowTradeCXFService = (BorrowTradeCXFService) CXF_Factory.getFactory(
            BorrowTradeCXFService.class, Configuration.getInstance().getValue("trade_url") + "/borrowTradeWebService").create();
    private RedpacketCXFService redpacketCXFService = (RedpacketCXFService) CXF_Factory.getFactory(RedpacketCXFService.class, Configuration.getInstance().getValue("webService_url") + "/redpacketWebService").create();
    /**
     * 用户操作接口
     */
    private UserCXFService userCXFService = (UserCXFService) CXF_Factory.getFactory(UserCXFService.class, Configuration.getInstance().getValue("webService_url") + "/userWebService").create();
    private BorrowApplyCXFService borrowApplyCXFService = (BorrowApplyCXFService) CXF_Factory.getFactory(BorrowApplyCXFService.class, Configuration.getInstance().getValue("webService_url") + "/borrowApplyWebService").create();
    private AccountCashCXFService accountCashCXFService = (AccountCashCXFService) CXF_Factory.getFactory(AccountCashCXFService.class, Configuration.getInstance().getValue("webService_url") + "/accountCashWebService").create();
    private LendInfoCXFService lendManagerCXFService = (LendInfoCXFService) CXF_Factory.getFactory(LendInfoCXFService.class, Configuration.getInstance().getValue("webService_url") + "/lendInfoWebService").create();
    private BorrowFeeCXFService borrowFeeCXFService = (BorrowFeeCXFService) CXF_Factory.getFactory(BorrowFeeCXFService.class, Configuration.getInstance().getValue("webService_url") + "/borrowFeeWebService").create();

    protected AccountOVQueryCXFService accountOVQueryCXFService = (AccountOVQueryCXFService) CXF_Factory.getFactory(AccountOVQueryCXFService.class, Configuration.getInstance().getValue("webService_url") + "/accountOVQueryWebService").create();
    private BorrowBillingbondCXFService borrowBillingbondCXFService = (BorrowBillingbondCXFService) CXF_Factory.getFactory(BorrowBillingbondCXFService.class, com.xxdai.util.Configuration.getInstance().getValue("webService_url") + "/borrowBillingbondWebService").create();
    private PersonCXFService personalService = (PersonCXFService) CXF_Factory.getFactory(PersonCXFService.class, Configuration.getInstance().getValue("webService_url") + "/personWebService").create();

    private FaDaDaCXFService faDaDaCXFService = (FaDaDaCXFService) CXF_Factory.getFactory(FaDaDaCXFService.class, Configuration.getInstance().getValue("webService_url") + "/fadadaWebService").create();

    @Autowired
    protected ProductService productService;

    @Autowired
    private BorrowService borrowService;
    /**
     * WebApp showBorrow
     * 标的列表集合
     */
    @RequestMapping(value = "/search/list", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String showBorrow(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        //接收页面参数
        String currentPage = request.getParameter("currentPage");
        String pageSize = request.getParameter("pageSize");

        try {
            //设置查询状态数组
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("currentPage", currentPage == null || currentPage.equals("") ? 1 : Integer.valueOf(currentPage));
            jsonObject.put("pageSize", pageSize == null || pageSize.equals("") ? 10 : Integer.valueOf(pageSize));

            //查询标的信息
            //log.info("select borrowList request param：" + jsonObject.toJSONString());
            String resultStr = borrowCXFService.searchFrontBorrows(JsonUtil.beanToJson(jsonObject));

            //结果集转对象
            WSMapResponse resp = JsonUtil.jsonToBean(resultStr, WSMapResponse.class);
            //log.info("response resultCode =" + resp.getResultCode());

            if (resp.getResultCode() != AppConsts.WS_RETURN_SUCC) {
                log.info("select borrowList fail，resultCode= " + resp.getResultCode() + ", Desc=" + resp.getDesc());
                result.put("resultCode", 100);
                result.put("msg", "获取散标列表失败");
                return result.toJSONString();
            }


            Map borrowVO = resp.getMap();
            List<Map<String, Object>> borrowList = (List<Map<String, Object>>) borrowVO.get("borrows");
            if (borrowList == null || borrowList.size() == 0) {
                log.info("select borrowList is size 0 ，currentPage = " + jsonObject.get("currentPage") + ",pageSize = " + jsonObject.get("pageSize"));
                result.put("resultCode", 101);
                result.put("msg", "当前没有散标列表");
                return result.toJSONString();
            }

            //log.info("borrowList size = " + borrowList.size());
            for (Map<String, Object> borrow : borrowList) {
                BigDecimal accont = new BigDecimal(borrow.get("ACCOUNT").toString());
                BigDecimal accountyes = new BigDecimal(borrow.get("ACCOUNTYES").toString());
                if (accountyes.compareTo(accont) == -1) {
                    borrow.put("isTender", true);
                } else {
                    borrow.put("isTender", false);
                }
                int borrowType = Integer.parseInt(borrow.get("BORROWTYPE").toString());
                int paymentmethod = Integer.parseInt(borrow.get("PAYMENTMETHOD").toString());
                if (borrowType == 13) {
                    borrow.put("dayType", "天");
                } else if (paymentmethod == 6) {
                    borrow.put("dayType", "个季度");
                } else {
                    borrow.put("dayType", "个月");
                }

                borrow.remove("USERNAME");
            }
            result.put("borrowsList", borrowVO);
            result.put("resultCode", 0);
            result.put("resultCode", AppConsts.WS_RETURN_SUCC);
            result.put("msg", "获取散标列表成功");
        } catch (Exception e) {
            log.error("获取散标列表失败", e);
            result.put("resultCode", 200);
            result.put("msg", "获取散标列表失败");
        }

        return result.toJSONString();
    }

    @RequestMapping(value = "/borrowcontract/{borrowId}", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String borrowcontract(HttpServletRequest request,@PathVariable("borrowId") String borrowId){
        JSONObject result = new JSONObject();
        try {

            Object userObj = request.getSession().getAttribute("loginUser");
            if(userObj == null) {
                result.put("resultCode",401);
                result.put("msg","未登陆");
                return result.toJSONString();
            }

            User user = (User)userObj;
            result.put("user", user);

            try {
                //出借人，认证信息
                JSONObject userParam = new JSONObject();
                userParam.put("userId",user.getUserId());
                String userStr = accountOVQueryCXFService.queryRealNameByUserId(userParam.toString());
                PersonResponse userres = JsonUtil.jsonToBean(userStr, PersonResponse.class);
                if(userres.getData()!=null){
                    result.put("userRn", userres.getData());
                }

                Message approMobile = productService.queryMobileApproInfoByUserId(String.valueOf(user.getUserId()));
                if(approMobile != null) {
                    result.put("userApproMobile",approMobile);
                }
            }catch (Exception e) {
                log.error("borrowcontract queryRealNameByUserId error",e);
            }


            boolean isCompany = false;

            JSONObject param = new JSONObject();
            param.put("borrowId",borrowId);

            // 借款人，发标信息
            String str = borrowQueryService.selectBorrowDetail(JsonUtil.beanToJson(borrowId));
            PersonResponse personResponse = JsonUtil.jsonToBean(str, PersonResponse.class);
            if(personResponse.getData() != null) {
                JSONObject borrow = JSONObject.parseObject(personResponse.getData().toString());
                result.put("borrow",borrow );

                if(null != borrow.get("companyId") && !"".equals(borrow.getString("companyId"))){
                    isCompany = true;
                }

                String successTime = borrow.getString("successTime");
                if(successTime != null) {
                    Date date = DateUtil.parseDate(successTime,"yyyy-MM-dd HH:mm:ss");
                    result.put("successTime", DateUtils.formatDate(date,"yyyy年MM月dd日"));
                }


                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("userId", borrow.getString("userId"));
                    String userRespStr = userCXFService.getUserById(jsonObject.toJSONString());
                    WSModelResponse resultRes = JsonUtil.jsonToBean(userRespStr,WSModelResponse.class);

                    if(resultRes.getResultCode() == 200 && resultRes.getData() != null){
                        JSONObject borrowUser = JSONObject.parseObject(resultRes.getData().toString());

                        //脱敏处理
                        String mobile = borrowUser.getString("mobile");
                        if(StringUtil.isNotBlank(mobile) && mobile.length()>=11){
                            borrowUser.put("mobile",mobile.substring(0,3).concat("****").concat(mobile.substring(7,11)));
                        }
                        result.put("borrowUser",borrowUser);
                    }
                }catch (Exception e) {
                    log.error("borrowcontract getUserById error",e);
                }

                try {
                    JSONObject jsonParam = new JSONObject();
                    //jsonParam.put("id", StringUtil.isNotBlank(companyId) ? Integer.parseInt(companyId) : null);
                    jsonParam.put("userId", borrow.getString("userId"));
                    String response = personalService.selectCompanyInfoByUserId(jsonParam.toJSONString());
                    WSModelResponse wsModelResponse = JsonUtil.jsonToBean(response, WSModelResponse.class);
                    if (wsModelResponse.getResultCode() == AppConsts.WS_RETURN_SUCC && wsModelResponse.getData() != null) {
                        List<JSONObject> companys = JsonUtil.jsonToList(wsModelResponse.getData().toString(), JSONObject.class);
                        if (companys != null && companys.size() > 0) {
                            JSONObject company = companys.get(0);

                            //脱敏处理
                            String comReCardId = company.getString("comReCardId");
                            if(StringUtil.isNotBlank(comReCardId) && comReCardId.length() >= 15){
                                company.put("comReCardId",comReCardId.substring(0,6).concat("**********"));
                            }
                            String comRepMobileNo = company.getString("comRepMobileNo");
                            if(StringUtil.isNotBlank(comRepMobileNo) && comRepMobileNo.length()>=11){
                                company.put("comRepMobileNo",comRepMobileNo.substring(0,3).concat("****").concat(comRepMobileNo.substring(7,11)));
                            }
                            result.put("company", company);
                        }
                    }
                }catch (Exception e) {
                    log.error("borrowcontract selectCompanyInfoByUserId error ",e);
                }

                try {
                    // 逾期利率：车贷20160921版本为千分之3，以前都是千分之8
                    BigDecimal laterRate = new BigDecimal("8");
                    if (borrow.getIntValue("type") == BorrowConsts.BORROW_CAR_TYPE
                            && StringUtil.isNotBlank(borrow.getString("version")) && StringUtil.isNaturalNum(borrow.getString("version"))
                            && Integer.parseInt(borrow.getString("version")) >= Integer.parseInt(BorrowConsts.BORROW_DOC_VERSION_20160921)) {
                        laterRate = new BigDecimal("3");
                    }
                    result.put("laterRate", laterRate);
                }catch (Exception e) {
                    log.error("laterRate error",e);
                }

                try {
                    // 认证 借款人
                    JSONObject userParam = new JSONObject();
                    userParam.put("userId",borrow.getString("userId"));
                    str = accountOVQueryCXFService.queryRealNameByUserId(userParam.toString());
                    PersonResponse userres = JsonUtil.jsonToBean(str, PersonResponse.class);
                    if(userres.getData()!=null){
                        result.put("rn", userres.getData());
                    }
                }catch (Exception e) {
                    log.error("queryRealNameByUserId error",e);
                }

                try {
                    //标的投资人信息
                    JSONObject paramInvest = new JSONObject();
                    paramInvest.put("borrowId", borrowId);
                    String  investtr = borrowQueryService.selectBorrowTenderList(paramInvest.toJSONString());
                    WSModelResponse investResponse = JsonUtil.jsonToBean(investtr, WSModelResponse.class);
                    if (investResponse.getData() != null) {
                        result.put("tenderList",investResponse.getData());
                    }
                }catch (Exception e) {
                    log.error("selectBorrowTenderList error",e);
                }

                try {
                    JSONObject req = new JSONObject();
                    req.put("borrowId",borrowId);
                    List<String> prioritys = new ArrayList<String>() {{
                        add("1");
                        add("2");
                        add("3");
                        add("4");
                        add("99");
                    }};

                    req.put("priority",prioritys);
                    String str4 = faDaDaCXFService.getExtBorrowGuarantorByBorrowId(req.toJSONString());
                    result.put("guarantorList",JSONObject.parseObject(str4));
                }catch (Exception e) {
                    log.error("getExtBorrowGuarantorByBorrowId error",e);
                }

                try {
                    // 借款人，借款标审核信息
                    str = borrowQueryService.selectByBorrowId(JsonUtil.beanToJson(borrowId));
                    personResponse = JsonUtil.jsonToBean(str, PersonResponse.class);
                    if (personResponse.getData() != null) {
                        JSONObject approved = JSONObject.parseObject(personResponse.getData().toString());
                        Calendar cal = Calendar.getInstance();
                        result.put("borrowApproved", approved);
                        String approveTime4 = approved.getString("approveTime4");


                        Date approvedStartDate = DateUtil.parseDate(approveTime4,"yyyy-MM-dd HH:mm:ss");
                        result.put("approvedStart", DateUtils.formatDate(approvedStartDate,"yyyy年MM月dd日"));
                        result.put("guarantorSignTime", DateUtils.formatDate(approvedStartDate,"yyyy年MM月dd日"));

                        Date borrowSignTimeDate = DateUtil.parseDate(approveTime4,"yyyy-MM-dd HH:mm:ss");
                        result.put("borrowSignTime", DateUtils.formatDate(borrowSignTimeDate,"yyyy-MM-dd"));

                        int type = borrow.getIntValue("type");
                        int paymentmethod = borrow.getInteger("paymentmethod");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                        if(approveTime4 != null && type != 13 && paymentmethod !=6){
                            Date approveTime4Date = sdf.parse(approveTime4);
                            cal.setTime(approveTime4Date);
                            int day = cal.get(Calendar.DAY_OF_MONTH);
                            cal.add(Calendar.MONTH, borrow.getIntValue("period"));
                            String b = Long.toString(cal.getTime().getTime() / 1000);
                            SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
                            String dateStr = "";
                            dateStr = df.format(cal.getTime());
                            result.put("borrowEndTime", dateStr);

                            // 每月还款日
                            result.put("day", day);
                        } else if (approveTime4 != null && type != 13 && paymentmethod == 6) {
                            Date approveTime4Date = sdf.parse(approveTime4);
                            cal.setTime(approveTime4Date);
                            int day = cal.get(Calendar.DAY_OF_MONTH);
                            cal.add(Calendar.MONTH, borrow.getIntValue("timeLimit") * 3);
                            String b = Long.toString(cal.getTime().getTime() / 1000);
                            SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
                            String dateStr = "";
                            dateStr = df.format(cal.getTime());
                            result.put("borrowEndTime", dateStr);
                            // 每月还款日
                            result.put("day", day);
                        } else if(approveTime4 != null && type ==13) {
                            Date approveTime4Date = sdf.parse(approveTime4);
                            cal.setTime(approveTime4Date);
                            cal.add(Calendar.DAY_OF_MONTH, borrow.getIntValue("timeLimit"));
                            String b = Long.toString(cal.getTime().getTime() / 1000);
                            SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
                            String dateStr = "";
                            dateStr = df.format(cal.getTime());
                            request.setAttribute("borrowEndTime", dateStr);
                            request.setAttribute("day", "");
                        }
                    }
                }catch (Exception e) {
                    log.error("selectByBorrowId error",e);
                }
            }


            try{
                //查询标的费用信息
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("borrowId", borrowId);
                //类型：1线下纸质合同编号2财务结算编号
                jsonObject.put("type", 1);
                String bresultStr = borrowBillingbondCXFService.getBorrowBillingbond(jsonObject.toJSONString());
                WSModelResponse resultRes = JsonUtil.jsonToBean(bresultStr, WSModelResponse.class);
                if(resultRes.getResultCode() == AppConsts.WS_RETURN_SUCC && resultRes.getData() != null){
                    JSONObject obj = JSONObject.parseObject(resultRes.getData().toString());
                    result.put("billingbondCode",obj.getString("code"));
                }
            }catch (Exception e){
                log.error("getBorrowBillingbond error",e);
            }

            try {
                //查询标的信息
                String resultStr = borrowCXFService.initialTenderPage(param.toJSONString());
                //log.info("initialTenderPage resp " + resultStr);
                //结果集转对象
                WSMapResponse wsResponse = JsonUtil.jsonToBean(resultStr, WSMapResponse.class);
                if(wsResponse.getMap() != null) {
                    result.put("borrow1",wsResponse.getMap());
                }

            }catch (Exception e){
                log.error("initialTenderPage error",e);
            }

            try {
                //银行资料
                String investBank = lendManagerCXFService.investBankContract(param.toJSONString());
                WSModelResponse resp = JsonUtil.jsonToBean(investBank,WSModelResponse.class);
                if(resp.getData() != null) {
                    JSONObject bank = JSONObject.parseObject(resp.getData().toString());
                    //脱敏处理
                    String bankAccount = bank.getString("BANKACCOUNT");
                    if(StringUtil.isNotBlank(bankAccount) && bankAccount.length() >= 2){
                        bank.put("BANKACCOUNT",bankAccount.substring(0,2).concat("************")
                                .concat(bankAccount.substring(bankAccount.length()-2,bankAccount.length())));
                    }
                    result.put("bank", bank);

                }

            }catch (Exception e) {
                log.error("investBankContract error",e);
            }

            try {
                //担保人信息
                String guarantor = lendManagerCXFService.investBorrowGuarantorContract(param.toJSONString());
                WSModelResponse investGResponse = JsonUtil.jsonToBean(guarantor, WSModelResponse.class);
                if (investGResponse.getData() != null) {
                    result.put("guarantor", guarantorEncryption(JSONObject.parseObject(investGResponse.getData().toString())));
                }
            }catch (Exception e) {
                log.error("investBorrowGuarantorContract error",e);
            }

            try {
                String   resultStrbank = borrowQueryService.searchBorrowDetailForTender(param.toJSONString());
                // 结果集转对象
                BorrowQueryWsResponse bqWsResponse = JsonUtil.jsonToBean(resultStrbank, BorrowQueryWsResponse.class);
                if(bqWsResponse.getBaseInfo() != null) {
                    BaseInfo baseInfo = bqWsResponse.getBaseInfo();
                    result.put("baseInfo",baseInfo);


                    Message borrowGuarantee = productService.getBorrowGuaranteeByBorrowId(borrowId);
                    result.put("borrowGuarantee",borrowGuarantee);

                    Message msg = productService.getFddContractVersion(borrowId);
                    result.put("FddContractVersion",msg);



                    if(isCompany) {

                        JSONObject pram = new JSONObject();
                        pram.put("borrowId",borrowId);
                        String companyStr = faDaDaCXFService.getCompanyInfoByBorrowId(pram.toJSONString());
                        WSModelResponse resultRes = JsonUtil.jsonToBean(companyStr, WSModelResponse.class);
                        if(resultRes.getResultCode() == 0) {
                            JSONObject company = (JSONObject)resultRes.getData();
                            //脱敏处理
                            String comReCardId = company.getString("comReCardId");
                            if(StringUtil.isNotBlank(comReCardId) && comReCardId.length() >= 15){
                                company.put("comReCardId",comReCardId.substring(0,6).concat("**********"));
                            }
                            String comRepMobileNo = company.getString("comRepMobileNo");
                            if(StringUtil.isNotBlank(comRepMobileNo) && comRepMobileNo.length()>=11){
                                company.put("comRepMobileNo",comRepMobileNo.substring(0,3).concat("****").concat(comRepMobileNo.substring(7,11)));
                            }

                            result.put("company1",company);
                        }
                    }
                }
            }catch (Exception e) {
                log.error("searchBorrowDetailForTender error",e);
            }

            try {
                //查询标的费用信息
                String borrowFeeRepStr = borrowFeeCXFService.selectBorrowFeesMapByBorrowId(param.toJSONString());
                WSMapResponse borrowFeeRResponse = JsonUtil.jsonToBean(borrowFeeRepStr, WSMapResponse.class);
                result.put("borrowFee", borrowFeeRResponse.getMap());

            }catch (Exception e) {
                log.error("selectBorrowFeesMapByBorrowId error",e);
            }
            /*String respStr = postLoanCXFService.getLoanDetailByBorrowId(param.toJSONString());
            WSMapResponse loanResp = JsonUtil.jsonToBean(respStr,WSMapResponse.class);*/
            result.put("resultCode",0);
           // result.put("loan",loanResp.getMap());
        }catch (Exception e) {
            log.error("loanDetail error",e);
            result.put("resultCode",400);
            result.put("msg","异常");
        }finally {
            return result.toJSONString();
        }
    }
    //担保人信息脱敏
    private JSONObject guarantorEncryption(JSONObject guarantor){
        if(guarantor == null){return null;}
        //身份证隐藏
        if(StringUtil.isNotBlank(guarantor.getString("idCardNo")) && guarantor.getString("idCardNo").length() >= 15){
            guarantor.put("idCardNo",guarantor.getString("idCardNo").substring(0,6).concat("**********"));
        }
        //联系地址隐藏
        if(StringUtil.isNotBlank(guarantor.getString("address"))){
            guarantor.put("address",getAddress(guarantor.getString("address")));
        }
        //固定电话
        if(StringUtil.isNotBlank(guarantor.getString("telephone")) && guarantor.getString("telephone").length() >= 4){
            guarantor.put("telephone",guarantor.getString("telephone").substring(0,4).concat("******"));
        }
        //手机号码
        if(StringUtil.isNotBlank(guarantor.getString("mobile")) && guarantor.getString("mobile").length()>=11){
            guarantor.put("mobile",guarantor.getString("mobile").substring(0,3).concat("****").concat(guarantor.getString("mobile").substring(7,11)));
        }
        //银行账号
        String bankAccount = guarantor.getString("bankAccount");
        if(StringUtil.isNotBlank(bankAccount) && bankAccount.length() >= 2){
            guarantor.put("bankAccount",bankAccount.substring(0,2).concat("************")
                    .concat(bankAccount.substring(bankAccount.length()-2,bankAccount.length())));
        }
        return guarantor;
    }
    /**
     * 标的详情
     */
    @RequestMapping(value = "/detail/{id}", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String borrowDetail(HttpServletRequest request, @PathVariable("id") String id) {
        JSONObject result = new JSONObject();
        //log.info("select borrowdetail borrowid = "+id);
        try {
            JSONObject param = new JSONObject();
            param.put("borrowId", id);
            //查询标的信息
            String resultStr = borrowCXFService.initialTenderPage(param.toJSONString());
            //log.info("initialTenderPage resp " + resultStr);
            //结果集转对象
            WSMapResponse wsResponse = JsonUtil.jsonToBean(resultStr, WSMapResponse.class);
            if(wsResponse.getResultCode() != 0) {
                result.put("resultCode", wsResponse.getResultCode());
                result.put("msg", "获取标的详情失败");
                return result.toJSONString();
            }
            Map borrow = wsResponse.getMap();
            borrow.remove("REALNAME");
            borrow.remove("USERNAME");
            result.put("borrow", borrow);


            boolean isCompany = false;
            // 借款人，发标信息
            String str = borrowQueryService.selectBorrowDetail(JsonUtil.beanToJson(id));
            PersonResponse personResponse = JsonUtil.jsonToBean(str, PersonResponse.class);
            if(personResponse.getData() != null) {
                JSONObject borrow1 = JSONObject.parseObject(personResponse.getData().toString());
                if(null != borrow1.get("companyId") && !"".equals(borrow1.getString("companyId"))){
                    isCompany = true;
                }
                result.put("borrow1", borrow1);
            }

            // 逾期利率：车贷20160921版本为千分之3，以前都是千分之8
            BigDecimal laterRate = new BigDecimal("8");
            String version = borrow.get("VERSION") != null ? borrow.get("VERSION").toString() : "";
            if (Integer.parseInt(borrow.get("TYPE").toString()) == BorrowConsts.BORROW_CAR_TYPE
                    && StringUtil.isNotBlank(version) && StringUtil.isNaturalNum(version)
                    && Integer.parseInt(version) >= Integer.parseInt(BorrowConsts.BORROW_DOC_VERSION_20160921)) {
                laterRate = new BigDecimal("3");
            }
            result.put("laterRate", laterRate);

            //查询标的费用信息
            String borrowFeeRepStr = borrowFeeCXFService.selectBorrowFeesMapByBorrowId(param.toJSONString());
            WSMapResponse borrowFeeRResponse = JsonUtil.jsonToBean(borrowFeeRepStr, WSMapResponse.class);
            result.put("borrowFee", borrowFeeRResponse.getMap());

            String accountCostStr = accountCashCXFService.getAccountCost("");
           // log.info("getAccountCost response = " + accountCostStr);
            WSModelResponse res=JsonUtil.jsonToBean(accountCostStr.toString(),WSModelResponse.class);
            if(res.getResultCode()>=0 && res.getData()!=null){
                JSONObject feeInfo = JsonUtil.jsonToBean(res.getData().toString(),JSONObject.class);
                result.put("feeInfo", feeInfo);
            }
            //获取原始标的详细信息
            resultStr = borrowQueryService.searchBorrowDetailForTender(JsonUtil.beanToJson(param));

            //结果集转对象
            BorrowQueryWsResponse bWsResponse = JsonUtil.jsonToBean(resultStr, BorrowQueryWsResponse.class);

            //贷款信息
            BorrowQuery detail = bWsResponse.getBorrowQuery();
            detail.setUserName("");
            result.put("borrowDetail", detail);

            Message msg = productService.getFddContractVersion(id);
            if(msg != null) {
                result.put("FddContractVersion",msg);
            }

            //基本信息
            BaseInfo baseInfo = bWsResponse.getBaseInfo();
            //如果BaseInfo userType==2借款人为企业，获取企业信息
            if(isCompany) {

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
                result.put("companyInfo", companyInfo);
            }
            String realName = baseInfo.getRealName();
            if(realName != null && !"".equals(realName)){
                realName = realName.substring(0,1) + "**";
                baseInfo.setRealName(realName);
            }
            String idCardNo = baseInfo.getIdCardNo();
            if(idCardNo != null && !"".equals(idCardNo)) {
                idCardNo = idCardNo.substring(0,6) + "************" + idCardNo.substring(idCardNo.length()-1,idCardNo.length());
                baseInfo.setIdCardNo(idCardNo);
            }

            baseInfo.setHomeTel("");
            baseInfo.setCompanyTel("");
            result.put("baseInfo", baseInfo);

            if (baseInfo != null) {
                //性别 GENDER_TYPE
                result.put("baseInfoGender", DicCommonUtils.getDicValue("GENDER_TYPE", baseInfo.getGender()));
            }
            //信用等级
            if (detail != null) {
                //信用等级 CREDIT_LEVELS
                result.put("borrowCreditLevels", DicCommonUtils.getDicValue("CREDIT_LEVELS", detail.getCreditLevel()));
            }
            //逾期次数
            int overdue = bWsResponse.getOverdue();
            result.put("overdue", overdue);
            //成功借款次数
            int borrowCount = bWsResponse.getBorrowCount();
            result.put("borrowCount", borrowCount);
            BigDecimal overdueAccount = bWsResponse.getOverdueAccount();
            overdueAccount = overdueAccount == null ? new BigDecimal(0) : overdueAccount;
            result.put("overdueAccount", overdueAccount);
            result.put("isHousePurchase",bWsResponse.getIsHousePurchase());
            result.put("isCarPurchase",bWsResponse.getIsCarPurchase());
            //所在地
            String provinceCity = "", proince = "", city = "";
            if (baseInfo == null || baseInfo.getProvince() == null || "".equals(baseInfo.getProvince().trim()) || "null".equals(baseInfo.getProvince().trim().toLowerCase()) || baseInfo.getProvince().trim().toLowerCase().indexOf("null") != -1 || baseInfo.getProvince().trim().toLowerCase().indexOf("\"") != -1) {
            } else {
                proince = baseInfo.getProvince();
            }
            if (baseInfo == null || baseInfo.getCity() == null || "".equals(baseInfo.getCity().trim()) || "null".equals(baseInfo.getCity().trim().toLowerCase()) || baseInfo.getCity().trim().toLowerCase().indexOf("null") != -1 || baseInfo.getCity().trim().toLowerCase().indexOf("\"") != -1) {
            } else {
                city = baseInfo.getCity();
            }
            if ("".equals(proince) && "".equals(city)) {
                provinceCity = "未知";
            } else {
                provinceCity = proince + "  " + city;
            }
            result.put("provinceCity", provinceCity);
            //待还总额
            BigDecimal repaymentSum = bWsResponse.getRepaymentSum();
            result.put("repaymentSum", repaymentSum);


            //担保人信息
            /*String guarantor = lendManagerCXFService.investBorrowGuarantorContract(param.toJSONString());
            WSModelResponse investGResponse = JsonUtil.jsonToBean(guarantor, WSModelResponse.class);

            if (investGResponse.getData() != null) {
                JSONObject obj = JSONObject.parseObject(investGResponse.getData().toString());
                result.put("postLoan", obj);
            }

            //银行资料
            String investBank = lendManagerCXFService.investBankContract(param.toJSONString());


            WSModelResponse resp = JsonUtil.jsonToBean(investBank,WSModelResponse.class);

            JSONObject jsonObj = JSONObject.parseObject(resp.getData().toString());
            result.put("borrowBank", jsonObj);

            String borrowFeeRepStr = borrowFeeCXFService.selectBorrowFeesMapByBorrowId(param.toJSONString());
            WSMapResponse borrowFeeRResponse = JsonUtil.jsonToBean(borrowFeeRepStr, WSMapResponse.class);
            result.put("borrowFee", borrowFeeRResponse.getMap());

            Object userObj = request.getSession().getAttribute("loginUser");
            if(userObj != null) {
                result.put("user", (User)userObj);
            }

            String tenderListStr = borrowCXFService.queryBorrowTenderByBorrowIdWithoutPage(param.toJSONString());
            WSModelResponse tenderListResp = JsonUtil.jsonToBean(tenderListStr, WSModelResponse.class);
            result.put("tenderList", tenderListResp.getData());*/

            //查询贷款记录
            //  param.put("userId", ((User) userObj).getUserId());
            // log.info("select account req param = " + param.toJSONString());
            // resultStr = accountQueryCXFService.selectUserAccountAndCoupon(param.toJSONString());
            // log.info("account resp = " + resultStr);
            //账户信息
            result.put("userId", borrow.get("USERID"));
            resultStr = accountQueryCXFService.selectUserAccountAndCoupon(result.toJSONString());
            WSMapResponse wsMapResponse = JsonUtil.jsonToBean(resultStr, WSMapResponse.class);
            result.put("account", wsMapResponse.getMap().get("defaultAccount"));

            //正常还清贷款笔数
            int accountNomalPay = bWsResponse.getAccountNomalPay();
            result.put("accountNomalPay", accountNomalPay);
            result.put("resultCode", 0);
            result.put("msg", "获取详情成功");
        } catch (Exception e) {
            log.error("获取借款标详情失败，borrowId=" + id, e);
            result.put("resultCode", 200);
            result.put("msg", "获取详情失败");
        }

        return result.toJSONString();
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

                /*for (int i = 4; i < companyName.length(); i++){
                    x = x + "*";
                }
                */
                //companyName = companyName.substring(companyName.length()- 4);//一个参数表示截取传递的序号之后的部分
                companyInfo.setCompanyName(companyName.substring(0,2) + "***"+companyName.substring(companyName.length()- 2));
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
    public String  getAddress(String oldAddress) {
        if (StringUtil.isBlank(oldAddress)) {
            return null;
        } else {
            String[] allAddress = new String[]{"省", "市", "区", "县", "路", "街道", "街", "段", "弄", "号", "栋", "幢", "楼", "F", "室"};
            int num= oldAddress.indexOf("市");
            String strStart = oldAddress.substring(0,num+1);
            String strEnd = oldAddress.substring(num+1,oldAddress.length());
            String address = "";
            for (int i = 2; i < allAddress.length; i++) {
                //截取省份
                int addressIndex = strEnd.indexOf(allAddress[i]);
                for (int j = 1; j <= addressIndex; j++) {
                    if (j == addressIndex) {
                        address = address + "**" + allAddress[i];
                        strEnd = strEnd.substring(addressIndex + 1, strEnd.length());
                    }
                }
            }
            if (!"".equals(strEnd) && !"".equals(address)) {
                address = address + "**";
            }
            if ("".equals(address)) {
                address = "**";
            }
            return strStart + address;
        }
    }

    /**
     * 投标记录
     */
    @RequestMapping(value = "/borrowTenderList", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String borrowTenderList(HttpServletRequest request) {
        JSONObject result = new JSONObject();

        //获取页面参数
        String borrowId = request.getParameter("borrowId");
        String currentPage = request.getParameter("currentPage");
        if (StringUtils.isBlank(currentPage)) {
            currentPage = "1";
        }
        try {
            JSONObject param = new JSONObject();
            param.put("currentPage", Integer.parseInt(currentPage));
            param.put("borrowId", borrowId);
            //log.info("select borrowTenderList request param = " + param.toJSONString());
            String resultStr = borrowCXFService.queryBorrowTenderByBorrowId(param.toJSONString());
            //结果集转对象
            WSPageResponse wsResponse = JsonUtil.jsonToBean(resultStr, WSPageResponse.class);
            //log.info("resultCode = " + wsResponse.getResultCode());
            if (wsResponse.getResultCode() != 0) {
                result.put("resultCode", wsResponse.getResultCode());
                result.put("msg", "查询数据失败");
                return result.toJSONString();
            }
            List<BorrowTenderDetail> btdList = JsonUtil.jsonToList(wsResponse.getResultList().toString(), BorrowTenderDetail.class);
            for (BorrowTenderDetail bd : btdList) {
                bd.setUserName("");
                bd.setCurUserName("");
            }

            result.put("resultData", btdList);
            result.put("resultCode", 0);
            result.put("msg", "查询数据成功");
        } catch (Exception e) {
            log.error("查询借款标的投标记录失败", e);
            result.put("resultCode", 100);
            result.put("msg", "查询数据失败");
        }
        return result.toString();
    }

    /**
     * 投标
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "/tender/{id}", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String tender(HttpServletRequest request, @PathVariable("id") String id) {
        WSResponse wsResponse = new WSResponse();
        //判断当前token是否有效
        if (!TokenUtil.validToken(request)) {
            JSONObject json = new JSONObject();
            json.put("resultCode", Constant.TOKEN_INVALID_ERROR);
            json.put("desc", "页面已过期，请重新尝试");
            return json.toString();
        }
        // 销毁token
        TokenUtil.removeToken(request);
        //检测用户
        Object userObj = request.getSession().getAttribute("loginUser");
        if (userObj == null) {
            wsResponse.setDesc("您的会话失效，请重新登录");
            wsResponse.setResultCode(-404);
            return wsResponse.toJson();
        }
        User user = (User) userObj;

        //交易密码
        String payPwd = request.getParameter("payPwd");
        if (payPwd == null || payPwd.equals("")) {
            log.info("tender borrow check payPwd is null or empty");
            wsResponse.setResultCode(-1);
            wsResponse.setDesc("请填写支付密码!");
            return wsResponse.toJson();
        }

        try {
            //校验交易密码
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("userId", user.getUserId());
            jsonObj.put("password", EscapeCode.Encryption(payPwd));
            jsonObj.put("ip", HttpUtil.getRealIpAddr(request));
            String browser = request.getHeader("User-Agent");
            browser = browser.length() > 200 ? browser.substring(0, 200) : browser;
            jsonObj.put("browser", browser);
            log.info("tender check payPwd request param = " + jsonObj.toJSONString());
            String resultStr = userCXFService.checkPayPassword(JsonUtil.beanToJson(jsonObj));
            log.info("tender check payPwd response = " + resultStr);
            DataResponse userResponse = JsonUtil.jsonToBean(resultStr, DataResponse.class);
            int resultCode = 0;
            String msg = "";
            if (userResponse != null) {
                switch (userResponse.getResultCode()) {
                    case -1:
                        resultCode = -30;
                        msg = "获取支付密码异常！";
                        break;
                    case -2:
                        resultCode = -31;
                        msg = "支付密码错误，请重新输入！";
                        break;
                    case 220:
                        resultCode = -32;
                        msg = "您还未设置支付密码，请设置！";
                        break;
                    case 230:
                        resultCode = -33;
                        msg = "支付密码与登录密码一致！";
                        break;
                    default:
                }
            }
            //是否实名认证 begin
            boolean realNameFlag = false;
            JSONObject inputObj = new JSONObject();
            inputObj.put("userId", user.getUserId());
            String str = borrowQueryService.queryAppro(inputObj.toString());
            PersonResponse res = JsonUtil.jsonToBean(str, PersonResponse.class);
            Appro appro = null;
            if (res.getData() != null) {
                String dataStr = String.valueOf(res.getData());
                appro = JsonUtil.jsonToBean(dataStr, Appro.class);
            }
            if (null != appro && "1".equals(appro.getRealName())) {
                realNameFlag = true;
            }
            if (!realNameFlag) {
                //未实名认证 先进行实名认证
                resultCode = -34;
                msg = "您的实名认证尚未完成！";
            }
            //end
            if (resultCode != 0) {
                wsResponse = new WSMapResponse();
                wsResponse.setResultCode(-1);
                wsResponse.setDesc(msg);
                return wsResponse.toJson();
            }

            String tenderMoney = request.getParameter("tendermoney"); //投标金额
            String couponCode = request.getParameter("couponCode");//抵用券code
            List<String> couponCodes = null;
            if (couponCode != null && !couponCode.equals("")) {
                couponCodes = new ArrayList<String>();
                couponCodes.add(couponCode);
            }
            //封装投标数据
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", user.getUserId());
            jsonObject.put("borrowId", id);
            jsonObject.put("tenderMoney", tenderMoney);
            jsonObject.put("couponCodes", couponCodes);
            jsonObject.put("ip", HttpUtil.getRealIpAddr(request));
            jsonObject.put("terminalVer", HttpTookit.getRequestTerminal(request));

            //投标
            log.info("tender request param= " + jsonObject.toJSONString());
            resultStr = borrowTradeCXFService.tenderBorrow(JsonUtil.beanToJson(jsonObject));
            log.info("tender response = " + resultStr);
            //返还结果
            wsResponse = JsonUtil.jsonToBean(resultStr, WSMapResponse.class);
        } catch (Exception e) {
            log.error("投标失败，异常", e);
            wsResponse.setResultCode(600);
            wsResponse.setDesc("投标失败，请重新尝试");
        }
        return wsResponse.toJson();
    }


    /**
     * 投标详情展示
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/tender/quick", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String quick(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        //检测用户
        Object userObj = request.getSession().getAttribute("loginUser");
        if (userObj == null) {
            result.put("resultCode", -505);
            result.put("msg", "会话失效，请重新登录");
            return result.toJSONString();
        }
        try {
            String borrowId = request.getParameter("borrowId");
            JSONObject param = new JSONObject();
            param.put("borrowId", borrowId);
            //查询标的信息
            String resultStr = borrowCXFService.initialTenderPage(param.toJSONString());
            //结果集转对象
            WSMapResponse wsResponse = JsonUtil.jsonToBean(resultStr, WSMapResponse.class);
            Map borrow = wsResponse.getMap();
            borrow.remove("REALNAME");
            borrow.remove("USERNAME");
            result.put("borrow", borrow);
            //查询贷款记录
            param.put("userId", ((User) userObj).getUserId());
            resultStr = accountQueryCXFService.selectUserAccountAndCoupon(param.toJSONString());
            WSMapResponse wsMapResponse = JsonUtil.jsonToBean(resultStr, WSMapResponse.class);
            //账户信息
            result.put("account", wsMapResponse.getMap().get("defaultAccount"));

            //设置新手红包信息
            setRedPacketInfo(((User) userObj), borrow, request, result);

            result.put("resultCode", 0);
            result.put("msg", "查询投标详情成功");
        } catch (Exception e) {
            log.error("查询投标详情失败", e);
            result.put("resultCode", 100);
            result.put("msg", "查询投标详情失败");
        }
        return result.toJSONString();
    }

    /**
     * 快捷投标校验
     */
    @RequestMapping(value = "/quickTender", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String quickTender(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        Object userObj = request.getSession().getAttribute("loginUser");
        if (userObj == null) {
            result.put("resultCode", -505);
            result.put("msg", "会话失效，请重新登录");
            return result.toJSONString();
        }
        boolean realNameFlag = false;
        JSONObject inputObj = new JSONObject();
        inputObj.put("userId", ((User) userObj).getUserId());
        String str = borrowQueryService.queryAppro(inputObj.toString());
        PersonResponse res = JsonUtil.jsonToBean(str, PersonResponse.class);
        Appro appro = null;
        if (res.getData() != null) {
            String dataStr = String.valueOf(res.getData());
            appro = JsonUtil.jsonToBean(dataStr, Appro.class);
        }
        if (null != appro && "1".equals(appro.getRealName())) {
            realNameFlag = true;
        }
        if (!realNameFlag) {
            //未实名认证 先进行实名认证
            result.put("quicktCode", -34);
            result.put("quicktmsg", "您的实名认证尚未完成！");
            return result.toString();
        }
        //是否设置支付密码
        String userid = null;
        try {
            userid = DigestUtil.md5ToHex(((User) userObj).getUserId().toString());
        } catch (CipherException e) {
            e.printStackTrace();
        }
        JSONObject obj = new JSONObject();
        obj.put("userId", userid);
        obj.put("password", "");
        obj.put("pswtype", 2);
        obj.put("ip", HttpUtil.getRealIpAddr(request));
        str = userCXFService.checkPsw(obj.toString());
        UserResponse ures = JsonUtil.jsonToBean(str, UserResponse.class);
        String paypwd = "1";//有支付密码 ;
        if (ures.getResultCode() == 220) {
            paypwd = "0";//没支付密码
        }
        result.put("paypwd", paypwd);
        return result.toString();
    }

    private void setRedPacketInfo(User user, Map borrow, HttpServletRequest request, JSONObject result) {
        if (user == null) {
            return;
        }
        //新手红包
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("pageIndex", 1);
        jsonObject1.put("pageSize", 999);
        jsonObject1.put("userId", (user).getUserId());
        jsonObject1.put("status", 1);
        jsonObject1.put("btype", borrow.get("TYPE"));
        jsonObject1.put("prodType", RedEnvelopeLimitTypeConsts.TENDER_USE_PRODUCT_TYPE);
        jsonObject1.put("terminalVer", HttpTookit.getRequestTerminal(request));
        jsonArray.add(jsonObject1);

        try {
            String str = redpacketCXFService.getRedpacketListByUseCodition(jsonArray.toString());
            RpWsResponse res = JsonUtil.jsonToBean(str, RpWsResponse.class);
            if (res.getResultCode() < 0) {
                log.error("红包获取失败：" + res.getDesc());
            } else {
                if (res.getData() != null) {
                    List<RedenvelopeRecord> list = JsonUtil.jsonToList(res.getData().toString(), RedenvelopeRecord.class);
                    result.put("redpacketList", list);
                }
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


    @RequestMapping(value = "/saveBorrowApply", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String saveBorrowApply(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        try {
            Object imgCodeObj = request.getSession().getAttribute("imgCode");
            String imgCode = request.getParameter("randCode");
            if (!(imgCodeObj != null && imgCode != null && imgCode.equalsIgnoreCase(String.valueOf(imgCodeObj)))) {
                json.put("resultCode", 100);
                json.put("msg", "请输入正确的验证码");
                return json.toJSONString();
            }


            String borrowType = request.getParameter("borrowType");
            String province = request.getParameter("province");
            String city = request.getParameter("city");
            String realName = request.getParameter("realName");
            String mobileNo = request.getParameter("mobileNo");
            String gender = request.getParameter("gender");
            String birthday = request.getParameter("birthday");
            String amount = request.getParameter("amount");
            String timeLimit = request.getParameter("timeLimit");
            String monthlyIncome = request.getParameter("monthlyIncome");
            String identity = request.getParameter("identity");
            String ownhouse = request.getParameter("ownhouse");
            String owncar = request.getParameter("owncar");

            json.put("borrowType", borrowType);
            json.put("province", province);
            json.put("city", city);
            json.put("realName", realName);
            json.put("mobileNo", mobileNo);
            json.put("gender", gender);
            json.put("birthday", birthday);
            json.put("amount", amount);
            json.put("timeLimit", timeLimit);
            json.put("monthlyIncome", monthlyIncome);
            json.put("identity",identity);
            json.put("ownhouse",ownhouse);
            json.put("owncar",owncar);
            json.put("ip", HttpUtil.getRealIpAddr(request));
            String channel = request.getParameter("channel");
            if (channel != null && !"".equals(channel)) {
                json.put("channel", channel);
            }
            log.info("saveBorrowApply req = " + json.toJSONString());
            String str = borrowApplyCXFService.saveBorrowApply(JsonUtil.beanToJson(json));
            log.info("saveBorrowApply resp = " + str);
            WSResponse response = JsonUtil.jsonToBean(str, WSResponse.class);
            json.clear();
            if (response != null && response.getResultCode() == AppConsts.WS_RETURN_SUCC) {
                json.put("resultCode", response.getResultCode());
                json.put("msg", response.getDesc());
            } else {
                json.put("resultCode", -1);
                json.put("msg", "保存失败！");
            }
        } catch (Exception e) {
            json.put("resultCode", 400);
            json.put("msg", "保存失败");
            log.error(e);
        }
        return json.toJSONString();
    }

    @RequestMapping(value = "/queryBorrowList", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String queryBorrowList(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            String cpStr = request.getParameter("currentPage");
            int currentPage = cpStr == null ? 1 : Integer.parseInt(cpStr);
            String psStr = request.getParameter("pageSize");
            int pageSize = psStr == null ? 20 : Integer.parseInt(psStr);
            Message msg = borrowService.queryBorrowList(currentPage,pageSize);
            if(msg != null) {
                result.put("code",msg.getCode());
                result.put("msg",msg.getMessage());
                result.put("data",msg.getData());
            } else {
                result.put("code",404);
                result.put("msg","查询不到");
            }
        }catch (Exception e) {
            log.error("investOrderList error",e);
            result.put("code",500);
            result.put("msg","未知异常");
        }finally {
            return result.toJSONString();
        }
    }

}
