package com.xxdai.pay.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xxdai.appro.bo.RealNameAppro;
import com.xxdai.client.CXF_Factory;
import com.xxdai.common.BaseController;
import com.xxdai.core.util.http.WSMapResponse;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.external.partner.ws.PartnerCXFServcie;
import com.xxdai.external.payrate.ws.PayRateWebService;
import com.xxdai.external.user.ws.User;
import com.xxdai.external.userquery.ws.UserQueryCXFService;
import com.xxdai.fuiou.service.PayAPIService;
import com.xxdai.lianlian.share.security.Md5Algorithm;
import com.xxdai.lianlian.share.security.RSAUtil;
import com.xxdai.lianlian.share.util.FuncUtils;
import com.xxdai.lianlian.wap.client.HttpRequestSimple;
import com.xxdai.lianlian.wap.service.LianLianPayAPIService;
import com.xxdai.lianlian.wap.util.YinTongConfigUtil;
import com.xxdai.partner.bo.Partner;
import com.xxdai.pay.bo.PayRate;
import com.xxdai.person.bo.BankInfo;
import com.xxdai.person.model.PersonResponse;
import com.xxdai.util.Configuration;
import com.xxdai.ws.util.WSModelResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

/**
 * 手机支付相关配置参数
 */
@Controller
@RequestMapping(value = "/mobilePay")
public class MobilePaySwitchController extends BaseController {

    private static final Log log = LogFactory.getLog(MobilePaySwitchController.class);
    private PayRateWebService payrateWebService = (PayRateWebService) CXF_Factory.getFactory(PayRateWebService.class, com.xxdai.util.Configuration.getInstance().getValue("webService_url") + "/payrateWebService").create();
    private PartnerCXFServcie partnerWebService = (PartnerCXFServcie) CXF_Factory.getFactory(PartnerCXFServcie.class, com.xxdai.util.Configuration.getInstance().getValue("webService_url") + "/partnerWebService").create();
    private UserQueryCXFService userQueryCXFService = (UserQueryCXFService) CXF_Factory.getFactory(UserQueryCXFService.class, Configuration.getInstance().getValue("webService_url") + "/userQueryWebService").create();
    @Resource(name = "payAPIService")
    private PayAPIService payAPIService;
    @Resource(name = "lianLianPayAPIService")
    private LianLianPayAPIService lianLianPayAPIService;

    /**
     * 获取当前支付通道id---38:连连50:富友
     */
    @RequestMapping(value = "/getMobilePayChannel", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getMobilePayChannel(HttpServletRequest request) {
        JSONObject returnJson = new JSONObject();
        try {
            Object object = request.getSession().getAttribute("loginUser");
            if (object == null) {
                returnJson.put("code", "-1");
                returnJson.put("data", "");
                returnJson.put("info", "您的会话失效，请重新登录");
                return returnJson.toString();
            }

            String payChannel = "";
            JSONObject input = new JSONObject();
            input.put("bankCode", request.getParameter("bankCode"));
            input.put("amount", request.getParameter("amount"));
            log.info("getPayPartnerForApp req param = " + input.toJSONString());
            String re = payrateWebService.getPayPartnerForApp(input.toString());
            WSMapResponse wsResponse = JsonUtil.jsonToBean(re, WSMapResponse.class);
            if (wsResponse.getResultCode() == 0) {
                payChannel = wsResponse.getMap().get("PARTNERID").toString();
                log.info("current payChannel : " + payChannel);
                returnJson.put("code", "0");
                returnJson.put("data", payChannel);
                returnJson.put("info", "成功");
            } else {
                returnJson.put("code", wsResponse.getResultCode());
                returnJson.put("info", wsResponse.getDesc());
            }
        } catch (Exception e) {
            returnJson.put("code", "-2");
            returnJson.put("info", "获取当前支付通道失败");
        }
        return returnJson.toString();
    }


    /**
     * 银行卡信息验证接口
     */
    @RequestMapping(value = "/bankCardCheck", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String bankCardCheck(@RequestParam(value = "bankId", required = false) Long bankId,
                                @RequestParam(value = "cardNo", required = false) String cardNo,HttpServletRequest request) throws Exception {
        JSONObject returnJson = new JSONObject();

        Object object = request.getSession().getAttribute("loginUser");
       if (object == null) {
           returnJson.put("code", "404");
           returnJson.put("data", "");
           returnJson.put("info", "您的会话失效，请重新登录");
           return returnJson.toString();
       }
       User user = (User)object;

        // 查询实名认证信息
        JSONObject object1 = new JSONObject();
        object1.put("userId", user.getUserId());
        log.info("select realNameInfo req param = " + object1.toJSONString());
        String resultStr1 = accountOVQueryCXFService.queryRealNameByUserId(object1.toJSONString());
        log.info("resp = " + resultStr1);
        PersonResponse dataResponse1 = JsonUtil.jsonToBean(resultStr1, PersonResponse.class);
        RealNameAppro realName = null;
        if (dataResponse1.getData() != null) {
            realName = JsonUtil.jsonToBean(dataResponse1.getData().toString(), RealNameAppro.class);
        }
        if (realName == null || !"1".equals(realName.getStatus())) {
            returnJson.put("code", "-1");
            returnJson.put("data", "");
            returnJson.put("info", "您还未实名认证，请先去认证");
            log.info(returnJson.toJSONString());
            return returnJson.toString();
        }

        if (StringUtils.isBlank(cardNo)) {
            WSModelResponse bankResp = null;
            BankInfo bankInfo = null;
            try {
                String str = personalCXFService.getUserBankById(String.valueOf(bankId));
                bankResp = JsonUtil.jsonToBean(str, WSModelResponse.class);
                bankInfo = (BankInfo) bankResp.getData();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                returnJson.put("code", "-2");
                returnJson.put("data", "");
                returnJson.put("info", "查询用户银行卡失败");
                log.info(returnJson.toJSONString());
                return returnJson.toString();
            }
            if (bankInfo != null) {
                cardNo = bankInfo.getBankAccount();
            } else {
                returnJson.put("code", "-4");
                returnJson.put("data", "");
                returnJson.put("info", "用户银行卡不存在");
                log.info(returnJson.toJSONString());
                return returnJson.toString();
            }
        }

        Partner partner = selectPartnerById(38);
        Partner partner2 = selectPartnerById(50);

        String fuiou = "";
        String lianlian = "";
        if (partner2 != null && partner2.getStatus() == 1) {
            fuiou = "1";
        } else if (partner != null && partner.getStatus() == 1) {
            lianlian = "1";
        } else {
            returnJson.put("code", "-4");
            returnJson.put("data", "");
            returnJson.put("info", "通道已经关闭");
            log.info(returnJson.toJSONString());
            return returnJson.toString();
        }
        ResourceBundle resb = ResourceBundle.getBundle("fuiou");
        String mobileFuiouVerify = resb.getString("mobile_fuiou_verify");
        if (fuiou.equals("1")) {
            lianlian = "";
            if (mobileFuiouVerify.equals("1")) {
                Map<String, String> resMap;
                try {
                    resMap = payAPIService.bankCardCheck(cardNo, "1".equals(realName.getIdCardType()) ? "0" : "1", realName.getIdCardNo(), realName.getRealName());

                    if (resMap != null) {
                        JSONObject dateJson = new JSONObject();
                        for (Object o : resMap.entrySet()) {
                            Map.Entry entry = (Map.Entry) o;
                            dateJson.put(entry.getKey().toString(), entry.getValue());
                        }
                        String rec = dateJson.getString("Rcd");
                        if (rec.equals("0000")) {
                        } else {
                            lianlian = "1";
                        }
                    } else {
                        lianlian = "1";
                    }
                } catch (Exception e) {
                    lianlian = "1";
                    log.error("银行卡信息验证接口错误", e);
                }
            }
            Map<String, String> resMap2;
            try {
                resMap2 = payAPIService.cardBinQuery(cardNo);

                if (resMap2 != null) {
                    JSONObject dateJson = new JSONObject();
                    for (Object o : resMap2.entrySet()) {
                        Map.Entry entry = (Map.Entry) o;
                        dateJson.put(entry.getKey().toString(), entry.getValue());
                    }
                    String rec = dateJson.getString("Rcd");
                    if (rec.equals("0000")) {
                        String ctp = dateJson.getString("Ctp");
                        if (ctp.equals("01")) {
                            lianlian = "";
                            PayRate payRate = queryPayRateByPidAndPayCode(50,dateJson.getString("InsCd"));
                            String bank_code = "";
                            if(payRate != null) {
                                bank_code = payRate.getBankCode();
                            }

                            if (bank_code == null || bank_code.equals("")) {
                                lianlian = "1";
                            } else {
                                JSONObject bankJson = new JSONObject();
                                bankJson.put("card_no", cardNo);
                                bankJson.put("bank_code", bank_code);
                                bankJson.put("bank_name", dateJson.getString("Cnm"));
                                bankJson.put("card_type", Integer.parseInt(ctp.substring(1, ctp.length())) + 1 + "");
                                returnJson.put("code", "0");
                                returnJson.put("data", bankJson);
                                returnJson.put("info", "成功");
                                log.info(returnJson.toJSONString());
                                return returnJson.toString();
                            }

                        } else {
                            returnJson.put("code", "-1");
                            returnJson.put("data", "");
                            returnJson.put("info", "请使用储蓄卡");
                            log.info(returnJson.toJSONString());
                            return returnJson.toString();
                        }
                    } else if (rec.equals("1014")) {
                        returnJson.put("code", "-1");
                        returnJson.put("data", "");
                        returnJson.put("info", "无效卡号");
                        log.info(returnJson.toJSONString());
                        return returnJson.toString();
                    } else if (rec.equals("5505")) {
                        lianlian = "1";
                    } else if (rec.equals("100001")) {
                        returnJson.put("code", "-1");
                        returnJson.put("data", "");
                        returnJson.put("info", "不支持的卡类型");
                        log.info(returnJson.toJSONString());
                        return returnJson.toString();
                    }
                } else {
                    lianlian = "1";
                }
            } catch (Exception e) {
                log.error("查询银行卡Bin信息错误", e);
                lianlian = "1";
            }
        }
        if (lianlian.equals("1")) {
            TreeMap<String, Object> map = new TreeMap<String, Object>();
            if (!FuncUtils.isNull(cardNo)) {
                map.put("card_no", cardNo);
            }
            map.put("oid_partner", YinTongConfigUtil.getInstance().getValue("oid_partner"));
            map.put("sign_type", YinTongConfigUtil.getInstance().getValue("sign_type"));
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                sb.append("&");
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue());
            }
            String sign_src = sb.toString();
            if (sign_src.startsWith("&")) {
                sign_src = sign_src.substring(1);
            }
            log.info("原始参数字符串:" + sign_src);
            String sign = "";
            if ("RSA".equals(YinTongConfigUtil.getInstance().getValue("sign_type"))) {
                sign = RSAUtil.sign(YinTongConfigUtil.getInstance().getValue("trader_pri_key"), sign_src);
            } else {
                sign_src += "&key=" + YinTongConfigUtil.getInstance().getValue("md5_key");
                try {
                    sign = Md5Algorithm.getInstance().md5Digest(sign_src.getBytes("UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    log.error("MD5加密出错",e);
                }
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("card_no", cardNo);
            jsonObject.put("oid_partner", YinTongConfigUtil.getInstance().getValue("oid_partner"));
            jsonObject.put("sign_type", YinTongConfigUtil.getInstance().getValue("sign_type"));
            jsonObject.put("sign", sign);
            String req_data = JSON.toJSONString(jsonObject);
            log.info("请求参数JSON串:req_data=" + req_data);

            HttpRequestSimple httpClient = new HttpRequestSimple();
            /**
             * 卡bin查询接口 旧：https://yintong.com.cn/traderapi/bankCheck.htm
             * https://yintong.com.cn/traderapi/bankcardbin.htm
             * https://yintong.com.cn/traderapi/bankcardquery.htm
             * 新：https://queryapi.lianlianpay.com/bankcardbin.htm
             */
            String resJson = httpClient.postSendHttp("https://queryapi.lianlianpay.com/bankcardbin.htm", req_data);
            log.info("响应JSON串:" + resJson);

            JSONObject callbackJson = JSON.parseObject(resJson);
            String ret_code = callbackJson.getString("ret_code");
            String card_no = callbackJson.getString("card_no");
            String bank_code = callbackJson.getString("bank_code");
            String bank_name = callbackJson.getString("bank_name");
            String card_type = callbackJson.getString("card_type"); // 2-储蓄卡 3-信用卡

            if (Integer.parseInt(ret_code) == 0) {
                if ("3".equals(card_type)) {
                    returnJson.put("code", "-3");
                    returnJson.put("data", "");
                    returnJson.put("info", "请使用储蓄卡");
                    log.info(returnJson.toJSONString());
                    return returnJson.toString();
                }
                PayRate payRate = queryPayRateByPidAndPayCode(38,bank_code);
                String lianLianbank_code = "";
                if(payRate != null) {
                    lianLianbank_code = payRate.getBankCode();
                }

                if (lianLianbank_code == null || lianLianbank_code.equals("")) {
                    returnJson.put("code", "-2");
                    returnJson.put("data", "");
                    returnJson.put("info", "不支持的银行卡");
                    log.info(returnJson.toJSONString());
                    return returnJson.toString();
                } else {
                    JSONObject bankJson = new JSONObject();
                    bankJson.put("card_no", card_no);
                    bankJson.put("bank_code", lianLianbank_code);
                    bankJson.put("bank_name", bank_name);
                    bankJson.put("card_type", card_type);

                    returnJson.put("code", "0");
                    returnJson.put("data", bankJson);
                    returnJson.put("info", "银行卡号正确");
                    log.info(returnJson.toJSONString());
                    return returnJson.toString();
                }
            }
        }
        returnJson.put("code", "-2");
        returnJson.put("data", "");
        returnJson.put("info", "抱歉银行验证失败，请重新尝试或联系客服");
        log.info(returnJson.toJSONString());
        return returnJson.toString();
    }

    /**
     * 通过cardNo获取BankCode
     */
    @RequestMapping(value = "/getBankCodeByCardNo", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getBankCodeByCardNo(HttpServletRequest request) {
        JSONObject returnJson = new JSONObject();
        try {
            Object object = request.getSession().getAttribute("loginUser");
            if (object == null) {
                returnJson.put("code", "-1");
                returnJson.put("data", "");
                returnJson.put("info", "您的会话失效，请重新登录");
                return returnJson.toString();
            }

            String bankCode = "";
            String card_no = request.getParameter("card_no");
            Partner partner = selectPartnerById(38);
            Partner partner2 = selectPartnerById(50);;
            String fuiou = "";
            String lianlian = "";
            if (partner2 != null && partner2.getStatus() == 1) {
                fuiou = "1";
            } else if (partner != null && partner.getStatus() == 1) {
                lianlian = "1";
            } else {
                log.info("通道已经关闭");
            }
            if (fuiou.equals("1")) {
                Map<String, String> resMap2;

                resMap2 = payAPIService.cardBinQuery(card_no);

                if (resMap2 != null) {
                    JSONObject dateJson = new JSONObject();
                    for (Object o : resMap2.entrySet()) {
                        Map.Entry entry = (Map.Entry) o;
                        dateJson.put(entry.getKey().toString(), entry.getValue());
                    }
                    String rec = dateJson.getString("Rcd");
                    if (rec.equals("0000")) {
                        String ctp = dateJson.getString("Ctp");
                        if (ctp.equals("01")) {
                            PayRate payRate = queryPayRateByPidAndPayCode(50, dateJson.getString("InsCd"));
                            if (payRate == null) {
                                lianlian = "1";
                                fuiou = "";
                            } else
                                bankCode = payRate.getBankCode();
                            //bankCode=bankService.selectBankCodeByPayCode("50",dateJson.getString("InsCd"));  ---wait for the webservice interface(queryPayRateByPidAndPayCode)
                            lianlian = "";
                            if (bankCode == null || bankCode.equals("")) {
                                lianlian = "1";
                                fuiou = "";
                            }
                        } else {
                            lianlian = "1";
                            fuiou = "";
                        }
                    } else {
                        lianlian = "1";
                        fuiou = "";

                    }
                } else {
                    lianlian = "1";
                    fuiou = "";
                }
            }

            if (lianlian.equals("1")) {
                JSONObject callbackJson = JSON.parseObject(lianLianPayAPIService.cardBinQuery(card_no));
                String ret_code = callbackJson.getString("ret_code");
                String bank_code = callbackJson.getString("bank_code");
                String bank_name = callbackJson.getString("bank_name");
                String card_type = callbackJson.getString("card_type"); // 2-储蓄卡 3-信用卡

                if (Integer.parseInt(ret_code) == 0) {
                    PayRate payRate = queryPayRateByPidAndPayCode(38,bank_code);
                    if(payRate != null) {
                        bankCode = payRate.getBankCode();
                    }
                    //bankCode = bankService.selectBankCodeByPayCode("38", bank_code);  ---wait for the webservice interface(queryPayRateByPidAndPayCode)
                }
            }
            log.info("bankCode : " + bankCode);

            returnJson.put("code", "0");
            returnJson.put("data", bankCode);
            returnJson.put("info", "成功");
        } catch (Exception e) {
            returnJson.put("code", "-2");
            returnJson.put("info", "获取当前支付通道失败");
        }
        return returnJson.toString();
    }


    public PayRate queryPayRateByPidAndPayCode(int partnerId, String payCode) {
        JSONObject payRateParam = new JSONObject();
        payRateParam.put("partnerId", partnerId);
        payRateParam.put("payCode", payCode);
        log.info("queryPayRateByPidAndPayCode req = " + payRateParam.toJSONString());
        String jsonpayRate = payrateWebService.queryPayRateByPidAndPayCode(payRateParam.toJSONString());
        log.info("queryPayRateByPidAndPayCode resp = " + payRateParam.toJSONString());
        JSONObject resultpayRateJson = JSONObject.parseObject(jsonpayRate);
        String payRateStr = resultpayRateJson.getString("payRate");
        PayRate payRate = JsonUtil.jsonToBean(payRateStr, PayRate.class);
        return payRate;
    }

    public Partner selectPartnerById(int id) {
        JSONObject param = new JSONObject();
        param.put("partnerId", id);
        log.info("selectPartnerById req = " + param.toJSONString());
        String jsonPartner = partnerWebService.queryPartnerById(param.toJSONString());
        log.info("selectPartnerById resp = " + jsonPartner);
        JSONObject resultPartnerJson = JSONObject.parseObject(jsonPartner);
        String partnerStr = resultPartnerJson.getString("partner");
        Partner partner = JsonUtil.jsonToBean(partnerStr, Partner.class);
        return partner;
    }
    
    /**
     * 银行限额查询
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getBankLimit", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getBankLimit(HttpServletRequest request,HttpServletRequest response) {
    	JSONObject resultJson = new JSONObject();
    	try{
    		String type = request.getParameter("type");
    		if (StringUtils.isBlank(type)) {
				throw new Exception("getBankLimit: type is required, but it is blank...");
			}
    		String resultStr = "";
    		if ("list".equals(type)) {
    			//webservice返回结果
    			resultStr = userQueryCXFService.selectBankLimitList();
			}else{
				resultStr = userQueryCXFService.selectBankLimitListMap();
			}
	    	
	    	log.info("getBankLimit: type["+type+"],resp:" + resultStr);

	    	//返回页面
//	    	resultJson.put("resultCode", 0);
//	    	resultJson.put("data", resultStr);
	    	
	    	return resultStr;
	    	
    	}catch(Exception e){
    		log.error("getBankLimit arise exception:" , e);
    		//返回页面
	    	resultJson.put("resultCode", -1);
	    	resultJson.put("resultDesc", "操作失败，请稍后重试...");
	    	return resultJson.toJSONString();
    	}
    }
}
