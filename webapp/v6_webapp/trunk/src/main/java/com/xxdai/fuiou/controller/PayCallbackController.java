package com.xxdai.fuiou.controller;

import com.alibaba.fastjson.JSONObject;
import com.xxdai.account.bo.AccountRecharge;
import com.xxdai.account.model.AccountResponse;
import com.xxdai.account.model.RechargeCallbackResultReq;
import com.xxdai.client.CXF_Factory;
import com.xxdai.common.BaseController;
import com.xxdai.core.util.http.HttpUtil;
import com.xxdai.core.util.http.WSResponse;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.external.payrate.ws.PayRateWebService;
import com.xxdai.fuiou.constant.FuiouConstant;
import com.xxdai.fuiou.service.PayAPIService;
import com.xxdai.fuiou.util.encrypt.Digest;
import com.xxdai.lianlian.wap.constant.LianlianConstant;
import com.xxdai.pay.bo.PayRate;
import com.xxdai.util.Convert;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

@Controller(value = "FuiouPayCallbackController")
@RequestMapping(value = "/fuiou")
public class PayCallbackController extends BaseController {

    private static final Log log = LogFactory.getLog(PayCallbackController.class);

    @Resource(name = "payAPIService")
    private PayAPIService payAPIService;

    private ResourceBundle resb = ResourceBundle.getBundle("fuiou");
    // 从配置文件读取富友分配的商户秘钥
    private String md5Key = resb.getString("fuiou.md5_key");

    private PayRateWebService payrateWebService = (PayRateWebService) CXF_Factory.getFactory(PayRateWebService.class, com.xxdai.util.Configuration.getInstance().getValue("webService_url") + "/payrateWebService").create();

    /**
     * wap支付结束后返回地址
     */
    @RequestMapping(value = "/fCallback", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ModelAndView fCallback(@RequestParam(value = "rcd", required = true) String rcd,
                                  @RequestParam(value = "rdesc", required = true) String rdesc,
                                  @RequestParam(value = "orderid", required = true) String orderId,
                                  @RequestParam(value = "md5", required = true) String md5,
                                  HttpServletRequest request) {
        log.info("Fuiou fCallback param : rcd=[" + rcd + "]rdesc=[" + rdesc + "]orderId=[" + orderId + "]md5=[" + md5 + "]");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pay/fuiou/return_merchant");

        List<String> resList = new ArrayList<String>();
        resList.add(rcd);
        resList.add(orderId);

        StringBuilder resStr = new StringBuilder();
        for (String s : resList) {
            resStr.append(s).append("|");
        }
        resStr.append(md5Key);

        // 生成MD5签名
        String resSign = Digest.signMD5(resStr.toString());
        if (md5.equals(resSign)) {
            log.info("验签通过,Fuiou check ok");
            if ("0000".equals(rcd)) {
                String str = accQueryCXFService.selectAccountRechargeByOrderNo(JsonUtil.beanToJson(orderId));
                AccountResponse res = JsonUtil.jsonToBean(str, AccountResponse.class);
                AccountRecharge accountRecharge = null;
                if (res.getData() != null && !"".equals(res.getData()) && !"null".equals(res.getData())) {
                    accountRecharge = JsonUtil.jsonToBean(String.valueOf(res.getData()), AccountRecharge.class);
                }

                if (accountRecharge != null) {
                    String bankCode = "";
                    Map<String, String> resMap;
                    try {
                        resMap = payAPIService.cardBinQuery(accountRecharge.getAccount());
                        if (resMap != null) {
                            bankCode = Convert.fuiouToV6BankCode(resMap.get("InsCd"));
                            if(bankCode == null || "".equals(bankCode)){
                                 /**
                                  * 20161018-使用SOAP接口方式获取bankCode，胡娜
                                  */
                                  JSONObject obj = new JSONObject();
                                  obj.put("partnerId", FuiouConstant.SYS_PANTAERID_FUIOU);
                                  obj.put("payCode", resMap.get("InsCd"));
                                  String payRateStr = payrateWebService.queryPayRateByPidAndPayCode(obj.toJSONString());
                                  obj = JsonUtil.jsonToBean(payRateStr,JSONObject.class);
                                  PayRate payRate = obj.getObject("payRate",PayRate.class);
                                  if(payRate != null){
                                      bankCode = payRate.getBankCode();
                                  }
                            }
                        }
                    } catch (Exception e) {
                        log.error("银行编号由fuiou转为V6发生错误", e);
                        e.printStackTrace();
                    }

                    RechargeCallbackResultReq req = new RechargeCallbackResultReq();
                    req.setAmt(accountRecharge.getAmount().toString());
                    req.setBankId("Fuiou");
                    req.setFlag("1");
                    req.setIp(HttpUtil.getRealIpAddr(request));
                    req.setOrderNo(orderId);
                    req.setTrxId(orderId);
                    req.setPayPlatfrom("富友");
                    req.setPartnerId(50L);
                    req.setSignNo("Fuiou" + accountRecharge.getAccount());
                    req.setBankCode(bankCode);
                    log.info("rechargeCallback req param = " + JsonUtil.beanToJson(req));
                    String resultStr = accountRechargeCXFService.rechargeCallbackResult(JsonUtil.beanToJson(req));
                    log.info("rechargeCallback resp = " + resultStr);
                    WSResponse resultRes = JsonUtil.jsonToBean(resultStr, WSResponse.class);
                    if (resultRes.getResultCode() == 0) {
                        log.info("成功入库");
                        DecimalFormat df = new DecimalFormat("###,##0.00");
                        modelAndView.addObject("pay_amount", df.format(new BigDecimal(accountRecharge.getAmount().toString())));
                        modelAndView.addObject("status", "0");
                        return modelAndView;
                    } else {
                        log.info("入库失败");
                    }
                } else {
                    log.info("无此订单");
                }
            } else {
                log.info("充值失败");
            }
        } else {
            log.info("验签未通过,Fuiou check fail");
        }
        modelAndView.addObject("status", "-1");
        return modelAndView;
    }

    /**
     * wap支付异步通知接口
     */
    @RequestMapping(value = "/callback", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String callback(@RequestParam(value = "rcd", required = true) String rcd,
                           @RequestParam(value = "rdesc", required = true) String rdesc,
                           @RequestParam(value = "orderid", required = true) String orderId,
                           @RequestParam(value = "md5", required = true) String md5,
                           HttpServletRequest request) {
        log.info("Fuiou callback param : rcd=[" + rcd + "]rdesc=[" + rdesc + "]orderId=[" + orderId + "]md5=[" + md5 + "]");

        List<String> resList = new ArrayList<String>();
        resList.add(rcd);
        resList.add(orderId);

        StringBuilder resStr = new StringBuilder();
        for (String s : resList) {
            resStr.append(s).append("|");
        }
        resStr.append(md5Key);

        // 生成MD5签名
        String resSign = Digest.signMD5(resStr.toString());
        if (md5.equals(resSign)) {
            log.info("验签通过,Fuiou check ok");
            if ("0000".equals(rcd)) {
                String str = accQueryCXFService.selectAccountRechargeByOrderNo(JsonUtil.beanToJson(orderId));
                AccountResponse res = JsonUtil.jsonToBean(str, AccountResponse.class);
                AccountRecharge accountRecharge = null;
                if (res.getData() != null && !"".equals(res.getData()) && !"null".equals(res.getData())) {
                    accountRecharge = JsonUtil.jsonToBean(String.valueOf(res.getData()), AccountRecharge.class);
                }

                if (accountRecharge != null) {
                    String bankCode = "";
                    Map<String, String> resMap;
                    try {
                        resMap = payAPIService.cardBinQuery(accountRecharge.getAccount());
                        if (resMap != null) {
                            bankCode = Convert.fuiouToV6BankCode(resMap.get("InsCd"));
                            if(bankCode == null || "".equals(bankCode)){
                                 /**
                                  * 20161018-使用SOAP接口方式获取bankCode，胡娜
                                  */
                                  JSONObject obj = new JSONObject();
                                  obj.put("partnerId", FuiouConstant.SYS_PANTAERID_FUIOU);
                                  obj.put("payCode", resMap.get("InsCd"));
                                  String payRateStr = payrateWebService.queryPayRateByPidAndPayCode(obj.toJSONString());
                                  obj = JsonUtil.jsonToBean(payRateStr,JSONObject.class);
                                  PayRate payRate = obj.getObject("payRate",PayRate.class);
                                  if(payRate != null){
                                      bankCode = payRate.getBankCode();
                                  }
                            }
                        }
                    } catch (Exception e) {
                        log.error("银行编号由fuiou转为V6发生错误", e);
                        e.printStackTrace();
                    }

                    RechargeCallbackResultReq req = new RechargeCallbackResultReq();
                    req.setAmt(accountRecharge.getAmount().toString());
                    req.setBankId("Fuiou");
                    req.setFlag("1");
                    req.setIp(HttpUtil.getRealIpAddr(request));
                    req.setOrderNo(orderId);
                    req.setTrxId(orderId);
                    req.setPayPlatfrom("富友");
                    req.setPartnerId(50L);
                    req.setSignNo("Fuiou" + accountRecharge.getAccount());
                    req.setBankCode(bankCode);
                    log.info("rechargeCallback req param = " + JsonUtil.beanToJson(req));
                    String resultStr = accountRechargeCXFService.rechargeCallbackResult(JsonUtil.beanToJson(req));
                    log.info("rechargeCallback resp = " + resultStr);
                    WSResponse resultRes = JsonUtil.jsonToBean(resultStr, WSResponse.class);
                    if (resultRes.getResultCode() == 0) {
                        log.info("成功入库");
                    } else {
                        log.info("入库失败");
                    }
                } else {
                    log.info("无此订单");
                }
            } else {
                log.info("充值失败");
            }
        } else {
            log.info("验签未通过,Fuiou check fail");
        }
        return null;
    }

}
