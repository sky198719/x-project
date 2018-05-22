package com.xxdai.fuiou.controller;

import com.alibaba.fastjson.JSONObject;
import com.xxdai.account.bo.AccountRecharge;
import com.xxdai.appro.bo.RealNameAppro;
import com.xxdai.common.BaseController;
import com.xxdai.common.DataResponse;
import com.xxdai.core.util.http.HttpUtil;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.external.user.ws.User;
import com.xxdai.fuiou.URIList.PayAPIURIList;
import com.xxdai.fuiou.service.PayAPIService;
import com.xxdai.fuiou.util.encrypt.Digest;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * wap认证支付接口
 */
@Controller(value = "FuiouWapPayController")
@RequestMapping("/fuiou")
public class WapPayController extends BaseController {

    private static final Log log = LogFactory.getLog(WapPayController.class);

    private ResourceBundle resb = ResourceBundle.getBundle("fuiou");
    // 从配置文件读取富友分配的商户秘钥
    private String md5Key = resb.getString("fuiou.md5_key");
    // 商户账户编号
    private String merchantAccount = resb.getString("fuiou.merchantaccount");
    // 从配置文件读取支付API接口URL前缀
    private String urlPrefix = resb.getString("fuiou.pay_urlprefix");
    // 从配置文件读取支付成功页面 URL
    private String homeUrl = resb.getString("fuiou.homeurl");
    // 从配置文件读取重新支付页面 URL
    private String reUrl = resb.getString("fuiou.reurl");
    // 从配置文件读取后台通知 URL
    private String backUrl = resb.getString("fuiou.backurl");

    @Resource(name = "payAPIService")
    private PayAPIService payAPIService;

    @RequestMapping(value = "/paySubmit")
    public ModelAndView paySubmit(@RequestParam(value = "moneyOrder", required = true) BigDecimal moneyOrder,  // 传入参数交易金额（moneyOrder）
                            @RequestParam(value = "cardNo", required = false) String cardNo, // 传入参数银行卡号（cardNo）
                            @RequestParam(value = "bankId", required = false) String bankId, // 传入参数用户银行卡Id（bankId）
                            @RequestParam(value = "bankCode", required = true) String bankCode, // 传入参数用户银行编号（bankCode）
                            HttpServletRequest request,
                            HttpSession session) {
        log.info(String.format("moneyOrder=%s,cardNo=%s,bankId=%s,bankCode=%s",moneyOrder,cardNo,bankId,bankCode));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pay/fuiou/pay_submit");
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
            if (moneyOrder == null) {
                msg = "请输入金额";
                log.info(msg);
                modelAndView.addObject("msg", msg);
                return modelAndView;
            }
            if (moneyOrder.compareTo(new BigDecimal("0.01")) < 0) {
                msg = "您的充值金额需要大于0.01元，请返回重新尝试";
                log.info(msg);
                modelAndView.addObject("msg", msg);
                return modelAndView;
            }
            moneyOrder = moneyOrder.setScale(2, BigDecimal.ROUND_FLOOR);

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
                modelAndView.addObject("code", -1);// 未进行实名认证
                return modelAndView;
            }

            // 如果未传入银行卡号，则查询用户银行卡的卡号
            if (StringUtils.isBlank(cardNo)) {
                if (StringUtils.isBlank(bankId)) {
                    msg = "银行卡号不能为空";
                    log.info(msg);
                    modelAndView.addObject("msg", msg);
                    return modelAndView;
                }

                log.info("getUserBankById req =" + bankId);
                String resultStr2 = personalCXFService.getUserBankById(JsonUtil.beanToJson(bankId));
                log.info("getUserBankById resp =" + resultStr2);
                DataResponse dataResponse2 = JsonUtil.jsonToBean(resultStr2, DataResponse.class);
                BankInfo bankInfo = null;
                if (dataResponse2.getData() != null) {
                    bankInfo = JsonUtil.jsonToBean(dataResponse2.getData().toString(), BankInfo.class);
                }
                if (bankInfo != null) {
                    if (StringUtils.isNotBlank(bankInfo.getBankAccount())) {
                        cardNo = bankInfo.getBankAccount();
                    }
                } else {
                    msg = "用户银行卡不存在";
                    log.info(msg);
                    modelAndView.addObject("msg", msg);
                    return modelAndView;
                }
            }

            if (StringUtils.isBlank(cardNo)) {
                msg = "银行卡号不能为空";
                log.info(msg);
                modelAndView.addObject("msg", msg);
                return modelAndView;
            }

            JSONObject object3 = new JSONObject();
            object3.put("partnerId", "50");
            String resultStr3 = partnerCXFServcie.queryPartnerById(object3.toJSONString());
            Partner partner = null;
            if (resultStr3 != null && StringUtils.isNotBlank(resultStr3)) {
                JSONObject resObject = JSONObject.parseObject(resultStr3);
                if (resObject != null && resObject.size() > 1) {
                    partner = resObject.getObject("partner", Partner.class);
                }
            }
            if (partner == null) {
                msg = "富友合作伙伴不存在";
                log.info(msg);
                modelAndView.addObject("msg", msg);
                return modelAndView;
            }

            // 创建富友订单
            String orderId = payAPIService.createOrder(moneyOrder);
            if (StringUtils.isBlank(orderId)) {
                msg = "创建富友订单失败";
                log.info(msg);
                modelAndView.addObject("msg", msg);
                return modelAndView;
            }

            log.info("----充值订单号：" + orderId);
            // 调用线上充值入库接口
            AccountRecharge accountRecharge = new AccountRecharge(); // 生成充值记录
            accountRecharge.setUserId(user.getUserId()); // 用户UserId
            accountRecharge.setType("1");
            accountRecharge.setAmount(moneyOrder); // 充值金额
            accountRecharge.setPartnerId(50L);
            accountRecharge.setBankCode(bankCode);// 充值银行编号
            accountRecharge.setOrderNo(orderId);
            accountRecharge.setRechargeIp(HttpUtil.getRealIpAddr(request));
            accountRecharge.setTerminalver(HttpTookit.getRequestTerminal(request));
            accountRecharge.setIsiocard(1); // 是否同卡进出
            accountRecharge.setAccount(cardNo);

            log.info("accountRecharge req param = "  + JsonUtil.beanToJson(accountRecharge));
            String resultStr4 = accountRechargeCXFService.dealRecharge(JsonUtil.beanToJson(accountRecharge));
            log.info("resp = " + resultStr4);
            DataResponse accountResponse4 = JsonUtil.jsonToBean(resultStr4, DataResponse.class);
            if (accountResponse4.getResultCode() != 0) {
                msg += "充值记录失败";
                log.info(msg);
                modelAndView.addObject("msg", msg);
                return modelAndView;
            }

            modelAndView.addObject("mchntCd", merchantAccount);
            modelAndView.addObject("orderid", orderId);
            modelAndView.addObject("ono", cardNo);
            modelAndView.addObject("backurl", backUrl);
            modelAndView.addObject("reurl", reUrl);
            modelAndView.addObject("homeurl", homeUrl);
            modelAndView.addObject("name", realName.getRealName());
            modelAndView.addObject("sfz", realName.getIdCardNo());
            String md5 = merchantAccount + "|" + orderId + "|" + realName.getRealName() + "|" + cardNo + "|" + realName.getIdCardNo() + "|" + md5Key;
            modelAndView.addObject("md5", Digest.signMD5(md5));
            // 支付地址
            modelAndView.addObject("payurl", urlPrefix + PayAPIURIList.FUIOU_PAY_URL.getValue());
            msg = null;

            Map<String,Object> map = modelAndView.getModel();
            StringBuffer logBuf = new StringBuffer();
            logBuf.append("\nFuiou paySubmit param :\n");
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                logBuf.append(entry.getKey()).append("=").append(entry.getValue()).append("\n");
             }
             log.info(logBuf);
            return modelAndView;
        }catch (Exception e) {
            log.error("Fuiou paySubmit error. " + msg, e);
            modelAndView.addObject("msg", msg);
            return modelAndView;
        }
    }

}
