package com.xxdai.lianlian.wap.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xxdai.account.bo.AccountRecharge;
import com.xxdai.appro.bo.RealNameAppro;
import com.xxdai.common.BaseController;
import com.xxdai.common.DataResponse;
import com.xxdai.core.util.http.HttpUtil;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.external.user.ws.User;
import com.xxdai.lianlian.share.security.Md5Algorithm;
import com.xxdai.lianlian.share.security.RSAUtil;
import com.xxdai.lianlian.share.util.DateUtil;
import com.xxdai.lianlian.wap.domain.PaymentInfo;
import com.xxdai.lianlian.wap.util.YinTongConfigUtil;
import com.xxdai.partner.bo.Partner;
import com.xxdai.person.bo.BankInfo;
import com.xxdai.util.HttpTookit;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * wap认证支付接口
 */
@Controller
@RequestMapping("/lianlian")
public class WapPayController extends BaseController {

    private static final Log log = LogFactory.getLog(WapPayController.class);

    @RequestMapping(value = "/paySubmit")
    public ModelAndView paySubmit(@RequestParam(value = "moneyOrder", required = true) BigDecimal money_order,  // 传入参数交易金额（moneyOrder）
                            @RequestParam(value = "cardNo", required = false) String card_no, // 传入参数银行卡号（cardNo）
                            @RequestParam(value = "bankId", required = false) String bank_id, // 传入参数用户银行卡Id（bankId）
                            @RequestParam(value = "bankCode", required = true) String bankCode, // 传入参数用户银行编号（bankCode）
                            HttpServletRequest request,
                            HttpSession session) {
        log.info("paySubmit req param [money_order =" + money_order + ",card_no=" + card_no + ",bank_id="+bank_id+"]");
        log.info("req user-agent = " + HttpTookit.getRequestTerminal(request));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pay/lianlian/pay_submit");
        String msg = "抱歉，您的充值申请失败，请重新尝试或者联系客服。";
        try{
            Object object = session.getAttribute("loginUser");
            if(object == null) {
                msg = "您的会话失效，请重新登录";
                log.info(msg);
                modelAndView.addObject("msg", msg);
                return modelAndView;
            }
            User user = (User) object;


            // 校验输入的金额
            if (money_order == null) {
                msg += "CODE[501]";
                log.info(msg + "，未输入金额");
                modelAndView.addObject("msg", msg);
                return modelAndView;
            }
            if (money_order.compareTo(new BigDecimal("0.01")) < 0) {
                msg = "您的充值金额需要大于0.01元，请返回重新尝试";
                log.info(msg);
                modelAndView.addObject("msg", msg);
                return modelAndView;
            }
            money_order = money_order.setScale(2, BigDecimal.ROUND_FLOOR);

            // 查询实名认证信息
            JSONObject object1 = new JSONObject();
            object1.put("userId", user.getUserId());
            log.info("select realNameInfo req param = "  + object1.toJSONString());
            String resultStr1 = accountOVQueryCXFService.queryRealNameByUserId(object1.toJSONString());
            log.info("resp = " + resultStr1);
            DataResponse dataResponse1 = JsonUtil.jsonToBean(resultStr1, DataResponse.class);
            RealNameAppro realName = null;
            if (dataResponse1.getData() != null) {
                realName = JsonUtil.jsonToBean(dataResponse1.getData().toString(), RealNameAppro.class);
            }
            if (realName == null || !"1".equals(realName.getStatus())) {
                msg = "您还未实名认证，请先去认证";
                log.info(msg);
                modelAndView.addObject("msg", msg);
                modelAndView.addObject("code", -1);//未进行实名认证
                return modelAndView;
            }

            // 如果未传入银行卡号，则查询用户银行卡的卡号或者签约协议号
            String no_agree = null; // 签约协议号（noAgree）
            if (StringUtils.isBlank(card_no)) {
                log.info("card_no is null.........");

                if(StringUtils.isBlank(bank_id)){
                    msg += "CODE[505]";
                    log.info(msg + "，bank_id is null,bank_id = " + bank_id);
                    modelAndView.addObject("msg", msg);
                    return modelAndView;
                }

                String resultStr2 = personalCXFService.getUserBankById(JsonUtil.beanToJson(bank_id));
                DataResponse dataResponse2 = JsonUtil.jsonToBean(resultStr2, DataResponse.class);
                BankInfo bankInfo = null;
                if (dataResponse2.getData() != null) {
                    bankInfo = JsonUtil.jsonToBean(dataResponse2.getData().toString(), BankInfo.class);
                }
                if (bankInfo != null) {
                    if (StringUtils.isNotBlank(bankInfo.getSignNo())) {
                        no_agree = bankInfo.getSignNo();
                    }
                    if (StringUtils.isNotBlank(bankInfo.getBankAccount())) {
                        card_no = bankInfo.getBankAccount();
                    }
                } else {
                    msg += "CODE[502]";
                    log.info(msg + "，用户银行卡不存在");
                    modelAndView.addObject("msg", msg);
                    return modelAndView;
                }
            } else {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("userId", user.getUserId());
                map.put("bankAccount", card_no);
                log.info("select bankinfo req = " + JsonUtil.beanToJson(map));
                String resultStr3 = personalCXFService.queryByBankCodeOrBankName(JsonUtil.beanToJson(map));
                log.info("resp = "  + resultStr3);
                DataResponse dataResponse3 = JsonUtil.jsonToBean(resultStr3, DataResponse.class);
                BankInfo bank = null;
                if (dataResponse3.getData() != null) {
                    bank = JsonUtil.jsonToBean(String.valueOf(dataResponse3.getData()), BankInfo.class);
                }
                if (bank != null) {
                    if (StringUtils.isNotBlank(bank.getSignNo())) {
                        no_agree = bank.getSignNo();
                        log.info("set no_agree = " + no_agree);
                    }
                }
            }

            if (StringUtils.isBlank(card_no)) {
                msg += "CODE[500]";
                log.info(msg + ",card_no = "+ card_no);
                modelAndView.addObject("msg", msg);
                return modelAndView;
            }

            JSONObject object3 = new JSONObject();
            object3.put("partnerId", "38");
            String resultStr3 = partnerCXFServcie.queryPartnerById(object3.toJSONString());
            Partner partner = null;
            if (resultStr3 != null && StringUtils.isNotBlank(resultStr3)) {
                JSONObject resObject = JSONObject.parseObject(resultStr3);
                if (resObject != null && resObject.size() > 1) {
                    partner = resObject.getObject("partner", Partner.class);
                }
            }
            if (partner == null) {
                msg += "CODE[503]";
                log.info(msg + ",合作伙伴不存在");
                modelAndView.addObject("msg", msg);
                return modelAndView;
            }

            String str_order = "YX_" + partner.getPartnerAuth() + "_";
            String no_order = str_order + (int) (Math.random() * 100) + (int) (Math.random() * 100) + String.valueOf(System.currentTimeMillis() / 1000L).substring(0, 9);
            log.info("----充值订单号：" + no_order);
            // 调用线上充值入库接口
            AccountRecharge accountRecharge = new AccountRecharge(); // 生成充值记录
            accountRecharge.setUserId(user.getUserId()); // 用户UserId
            accountRecharge.setType("1");
            accountRecharge.setAmount(money_order); // 充值金额
            accountRecharge.setPartnerId(38L);
            accountRecharge.setBankCode(bankCode);// 充值银行编号
            accountRecharge.setOrderNo(no_order);
            accountRecharge.setRechargeIp(HttpUtil.getRealIpAddr(request));
            accountRecharge.setTerminalver(HttpTookit.getRequestTerminal(request));
            accountRecharge.setIsiocard(1); // 是否同卡进出
            if (StringUtils.isNotBlank(card_no)) {
                accountRecharge.setAccount(card_no);
            }

            log.info("accountRecharge req param = "  + JsonUtil.beanToJson(accountRecharge));
            String resultStr4 = accountRechargeCXFService.dealRecharge(JsonUtil.beanToJson(accountRecharge));
            log.info("resp = " + resultStr4);
            DataResponse accountResponse4 = JsonUtil.jsonToBean(resultStr4, DataResponse.class);
            String req_data;
            if (accountResponse4.getResultCode() != 0) {
                msg += "CODE[504]";
                log.info(msg + ",充值记录失败");
                modelAndView.addObject("msg", msg);
                return modelAndView;
            }

            StringBuilder sb = new StringBuilder();
            // 银行账号姓名
            String acct_name = realName.getRealName();
            // 请求应用标识
            if (StringUtils.isNotBlank(acct_name)) {
                sb.append("acct_name=");
                sb.append(acct_name);
                sb.append("&app_request=3");
            } else {
                sb.append("app_request=3");
            }
            // 商户业务类型
            sb.append("&busi_partner=");
            sb.append(YinTongConfigUtil.getInstance().getValue("busi_partner"));
            // 传入参数银行卡号（card_no）
            if (StringUtils.isNotBlank(card_no)) {
                sb.append("&card_no=");
                sb.append(card_no);
            }
            // 商户订单时间
            String dt_order = DateUtil.getCurrentDateTimeStr1();
            sb.append("&dt_order=");
            sb.append(dt_order);
            // 证件号码
            String id_no = realName.getIdCardNo();
            if (StringUtils.isNotBlank(id_no)) {
                sb.append("&id_no=");
                sb.append(id_no);
            }
            // 订单描述
            String info_order = "新新贷线上充值";
            sb.append("&info_order=");
            sb.append(info_order);
            // 传入参数交易金额（money_order）
            sb.append("&money_order=");
            sb.append(money_order);
            // 商品名称
            String name_goods = "新新贷线上充值";
            sb.append("&name_goods=");
            sb.append(name_goods);
            // 传入参数签约协议号（no_agree）
            /*if (StringUtils.isNotBlank(no_agree)) {
                sb.append("&no_agree=");
                sb.append(no_agree);
            }   */
            // 订单号
            sb.append("&no_order=");
            sb.append(no_order);
            // 服务器异步通知地址
            sb.append("&notify_url=");
            sb.append(YinTongConfigUtil.getInstance().getValue("notify_url"));
            // 商户号
            sb.append("&oid_partner=");
            sb.append(YinTongConfigUtil.getInstance().getValue("oid_partner"));
            // 风险控制参数
            String risk_item;
            JSONObject risk_item_JSON = new JSONObject();
            risk_item_JSON.put("frms_ware_category", "2009"); // 商品类目
            risk_item_JSON.put("user_info_mercht_userno", user.getUserId()); // 商户用户唯一标识
            risk_item_JSON.put("user_info_bind_phone", user.getMobile()); // 绑定手机号im
            String regTime = "";
            if(user.getAddTime() != null) {
                regTime = com.xxdai.core.util.lang.DateUtil.format(user.getAddTime(),"yyyyMMddHHmmss");
            }
            risk_item_JSON.put("user_info_dt_register", regTime); // 注册时间
            risk_item_JSON.put("user_info_full_name", realName.getRealName()); // 用户注册姓名

            String user_info_id_type = "";
            if ("1".equals(realName.getIdCardType())) {
                user_info_id_type = "0";
            } else if ("2".equals(realName.getIdCardType()) || "4".equals(realName.getIdCardType())) {
                user_info_id_type = "5";
            } else if ("3".equals(realName.getIdCardType())) {
                user_info_id_type = "6";
            }
            risk_item_JSON.put("user_info_id_type", user_info_id_type); // 用户注册证件类型

            risk_item_JSON.put("user_info_id_no", realName.getIdCardNo()); // 用户注册证件号码
            risk_item_JSON.put("user_info_identify_state", "1"); // 是否实名认证
            risk_item_JSON.put("user_info_identify_type", "3"); // 实名认证方式
            risk_item = JSON.toJSONString(risk_item_JSON);
            sb.append("&risk_item=");
            sb.append(risk_item);
            // 签名方式
            sb.append("&sign_type=");
            sb.append(YinTongConfigUtil.getInstance().getValue("sign_type"));
            // 支付结束回显url
            sb.append("&url_return=");
            sb.append(YinTongConfigUtil.getInstance().getValue("url_return"));
            // 商户用户唯一编号
            sb.append("&user_id=");
            sb.append(user.getUserId());

            // 订单有效时间
            sb.append("&valid_order=");
            sb.append(YinTongConfigUtil.getInstance().getValue("valid_order"));
            String sign_src = sb.toString();
            if (sign_src.startsWith("&")) {
                sign_src = sign_src.substring(1);
            }
            String sign = null;
            if ("RSA".equals(request.getParameter("sign_type"))) {
                sign = RSAUtil.sign(YinTongConfigUtil.getInstance().getValue("trader_pri_key"), sign_src);
            } else {
                sign_src += "&key=" + YinTongConfigUtil.getInstance().getValue("md5_key");
                try {
                    sign = Md5Algorithm.getInstance().md5Digest(sign_src.getBytes("utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            PaymentInfo payInfo = new PaymentInfo();
            payInfo.setApp_request("3");
            payInfo.setBusi_partner(YinTongConfigUtil.getInstance().getValue("busi_partner"));
            payInfo.setCard_no(card_no);
            payInfo.setDt_order(dt_order);
            payInfo.setId_no(id_no);
            payInfo.setInfo_order(info_order);
            payInfo.setMoney_order(money_order.toString());
            payInfo.setName_goods(name_goods);
           // payInfo.setNo_agree(no_agree);
            payInfo.setNo_order(no_order);
            payInfo.setNotify_url(YinTongConfigUtil.getInstance().getValue("notify_url"));
            payInfo.setOid_partner(YinTongConfigUtil.getInstance().getValue("oid_partner"));
            payInfo.setAcct_name(acct_name);
            payInfo.setRisk_item(risk_item);
            payInfo.setSign_type(YinTongConfigUtil.getInstance().getValue("sign_type"));
            payInfo.setUrl_return(YinTongConfigUtil.getInstance().getValue("url_return"));
            payInfo.setUser_id(user.getUserId().toString());
            payInfo.setValid_order(YinTongConfigUtil.getInstance().getValue("valid_order"));
            payInfo.setSign(sign);
            req_data = JSON.toJSONString(payInfo);
            log.info("goto lianlian pay submit req data :" + req_data);
            modelAndView.addObject("req_data", req_data);
            msg = null;
        }catch (Exception e) {
            msg += "CODE[504]";
            log.error("lianlian paySubmit error. " + msg, e);
            modelAndView.addObject("msg", msg);
            return modelAndView;
        }
        return modelAndView;
    }

}
