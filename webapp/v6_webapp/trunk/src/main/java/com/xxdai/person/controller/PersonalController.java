package com.xxdai.person.controller;

import com.alibaba.fastjson.JSONObject;
import com.xxdai.appro.bo.EmailAppro;
import com.xxdai.appro.bo.MobileAppro;
import com.xxdai.appro.bo.RealNameAppro;
import com.xxdai.appro.bo.VIPAppro;
import com.xxdai.common.BaseController;
import com.xxdai.common.DataResponse;
import com.xxdai.constant.AppConsts;
import com.xxdai.constant.Constant;
import com.xxdai.core.util.cipher.CipherException;
import com.xxdai.core.util.cipher.DigestUtil;
import com.xxdai.core.util.http.HttpUtil;
import com.xxdai.core.util.http.WSResponse;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.core.util.lang.DateUtil;
import com.xxdai.core.util.lang.StringUtil;
import com.xxdai.encrypt.EscapeCode;
import com.xxdai.external.user.ws.User;
import com.xxdai.http.Message;
import com.xxdai.person.bo.BankInfo;
import com.xxdai.person.bo.Employee;
import com.xxdai.person.model.Appro;
import com.xxdai.person.model.PersonResponse;
import com.xxdai.person.service.PersonService;
import com.xxdai.system.bo.DicCommonVo;
import com.xxdai.system.bo.SysConfig;
import com.xxdai.user.model.UserResponse;
import com.xxdai.user.service.UserService;
import com.xxdai.util.Configuration;
import com.xxdai.util.DicCommonUtils;
import com.xxdai.util.HttpTookit;
import com.xxdai.util.TokenUtil;
import com.xxdai.ws.util.WSModelResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping(value = "/personal")
public class PersonalController extends BaseController {

    private static final Log log = LogFactory.getLog(PersonalController.class);

    @Autowired
    private PersonService personService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/userBandedBankOutSideUse", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String userBandedBankOutSideUse(HttpServletRequest request){
        JSONObject returnJson = new JSONObject();
        try {
            String token = HttpTookit.getCookieValue(request,Constant.USERTOKEN);
            log.info("userBandedBankOutSideUse req "+ token);
            returnJson = personService.userBandedBankOutSideUse(token,HttpTookit.getUserAgent(request));
            log.info("userBandedBankOutSideUse resp "+ returnJson);
        }catch (Exception e) {
            log.error("userBandedBankOutSideUse error",e);
            returnJson.put("code",404);
            returnJson.put("message","查询异常");
        } finally {
            return returnJson.toJSONString();
        }
    }

    @RequestMapping(value = "/userCheckingBankCardInfo", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String userCheckingBankCardInfo(HttpServletRequest request){
        JSONObject returnJson = new JSONObject();
        try {
            String token = HttpTookit.getCookieValue(request,Constant.USERTOKEN);
            log.info("userBandedBankOutSideUse req "+ token);
            returnJson = personService.userCheckingBankCardInfo(token,HttpTookit.getUserAgent(request));
            log.info("userBandedBankOutSideUse resp "+ returnJson);


        }catch (Exception e) {
            log.error("userBandedBankOutSideUse error",e);
            returnJson.put("code",404);
            returnJson.put("message","查询异常");
        } finally {
            return returnJson.toJSONString();
        }
    }


    /**
     * 用户所有银行卡列表
     */
    @RequestMapping(value = "/userBankList", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String userBankList(HttpSession session) {
        JSONObject returnJson = new JSONObject();

        // 验证是否登录状态
        Object userObj = session.getAttribute("loginUser");
        if (null == userObj) {
            returnJson.put("resultCode", "-1");
            returnJson.put("resultData", "");
            returnJson.put("info", "未登录");
            return returnJson.toString();
        }

        User user = (User) userObj;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", user.getUserId());
        String resultStr = personalCXFService.queryBankInfoByUserId(jsonObject.toString());
        DataResponse resultRes = JsonUtil.jsonToBean(resultStr, DataResponse.class);
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        List<BankInfo> bankList = null;
        if (resultRes.getData() != null) {
            bankList = JsonUtil.jsonToList(resultRes.getData().toString(), BankInfo.class);
        }

        if (bankList != null && !bankList.isEmpty()) {
            // 查询银行字典配置
            List<DicCommonVo> payBankList = DicCommonUtils.getCommonList("PAY_BANK");
            HashMap<String, String> payBankHashMap = new HashMap<String, String>();
            for (DicCommonVo dic : payBankList) {
                payBankHashMap.put(dic.getPkey(), dic.getPvalue());
            }
            for (BankInfo bankInfo : bankList) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("bankId", String.valueOf(bankInfo.getId()));
                map.put("bankName", payBankHashMap.get(bankInfo.getBankCode()));
                map.put("bankCode", bankInfo.getBankCode());
                String bankAccount = bankInfo.getBankAccount();
                bankInfo.setBankAccount((bankAccount.length() > 4 ? bankAccount.substring(bankAccount.length() - 4, bankAccount.length())
                        : bankAccount));
                map.put("bankAccount", bankInfo.getBankAccount());
                map.put("banded", bankInfo.getBanded().toString());

                list.add(map);
            }

            returnJson.put("resultCode", "0");
            returnJson.put("resultData", list);
            returnJson.put("info", "查询成功");
            return returnJson.toString();
        } else {
            returnJson.put("resultCode", "-2");
            returnJson.put("resultData", "");
            returnJson.put("info", "列表为空");
            return returnJson.toString();
        }

    }

    /**
     * 用户实名认证
     */
    @RequestMapping(value = "/getRealName", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getRealName(HttpSession session) {
        JSONObject returnJson = new JSONObject();

        // 验证是否登录状态
        Object userObj = session.getAttribute("loginUser");
        if (null == userObj) {
            returnJson.put("resultCode", "-1");
            returnJson.put("resultData", "");
            returnJson.put("info", "未登录");
            return returnJson.toString();
        }

        User user = (User) userObj;
        Object RealNameObj = super.queryInfoByUserId(user.getUserId(), 1);
        RealNameAppro realname = null;
        if (RealNameObj != null) {
            realname = (RealNameAppro) RealNameObj;
            String idCardNo = realname.getIdCardNo();
            realname.setIdCardNo(idCardNo.substring(0, 6) + "******");
            log.info(this.getClass() + "......获取实名认证信息" + realname.getRealName());
        }
        returnJson.put("resultCode", "0");
        returnJson.put("resultData", realname != null ? realname.getRealName() : "");
        returnJson.put("resultStatus", realname != null ? realname.getStatus() : "");
        returnJson.put("info", "查询成功");
        return returnJson.toString();
    }

    @RequestMapping(value = "/getUserInfo", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getUserInfo(HttpSession session) {
        JSONObject returnJson = new JSONObject();

        // 验证是否登录状态
        Object userObj = session.getAttribute("loginUser");
        if (null == userObj) {
            returnJson.put("resultCode", "-1");
            returnJson.put("resultData", "");
            returnJson.put("info", "未登录");
            return returnJson.toString();
        }

        User user = (User) userObj;
        Object RealNameObj = super.queryInfoByUserId(user.getUserId(), 1);
        if (RealNameObj != null) {
            returnJson.put("realInfo", RealNameObj);
            returnJson.put("resultCode", "0");
            returnJson.put("info", "查询成功");
        }
        returnJson.put("user", userObj);
        return returnJson.toString();
    }

    /**
     * 个人资料-->文字资料-->基本信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/info", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String info(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        try {
            // 验证是否登录状态
            Object userObj = request.getSession().getAttribute("loginUser");
            if (null == userObj) {
                jsonObject.put("code",400);
                jsonObject.put("msg","请先登录");
                return jsonObject.toJSONString();
            }

            User user = (User) userObj;
            //当前用户信息
            jsonObject.put("userId", user.getUserId());
            String str = userCXFService.getUserById(jsonObject.toJSONString());
            UserResponse res = JsonUtil.jsonToBean(str, UserResponse.class);
            User nuwUser = null;
            if (res.getData() != null) {
                String dataStr = String.valueOf(res.getData());
                nuwUser = JsonUtil.jsonToBean(dataStr, User.class);
            }
            jsonObject.put("user", nuwUser);

            List<DicCommonVo> pwdPro = getDicCommonTypeList("MIBAO");
            jsonObject.put("pwdPro", pwdPro);

            DicCommonVo config = null;
            if (user.getSecurityQid() != null) {
                config = getDicCommonVo("MIBAO", user.getSecurityQid().toString());
            }
            if (null == config || config.equals("")) {
                jsonObject.put("cfgname", "");
            } else {
                jsonObject.put("cfgname", config.getPvalue());
            }

            // 认证信息
            JSONObject obj = new JSONObject();
            obj.put("userId", user.getUserId());
            str = borrowQueryService.queryAppro(obj.toString());
            //log.info(this.getClass() + "......认证信息" + str);
            PersonResponse pre = JsonUtil.jsonToBean(str, PersonResponse.class);
            String mobilestatus = "0";
            String emailstatus = "0";
            String vipstatus = "0";
            String namestatus = "0";
            if (pre.getData() != null) {
                String dataStr = String.valueOf(pre.getData());
                Appro appro = JsonUtil.jsonToBean(dataStr, Appro.class);
                if (appro != null) {
                    mobilestatus = appro.getMobile();
                    emailstatus = appro.getEmail();
                    vipstatus = appro.getVip();
                    namestatus = appro.getRealName();
                }
            }
            jsonObject.put("mobilestatus", mobilestatus);
            jsonObject.put("emailstatus", emailstatus);
            jsonObject.put("vipstatus", vipstatus);
            jsonObject.put("namestatus", namestatus);

            //获取实名认证信息
            Object RealNameObj = queryInfoByUserId(user.getUserId(), 1);
            RealNameAppro realname = null;
            if (RealNameObj != null) {
                realname = (RealNameAppro) RealNameObj;
                String idCardNo = realname.getIdCardNo();
                if(idCardNo != null && idCardNo.length() > 6) {
                    realname.setIdCardNo(idCardNo.substring(0, 6) + "******");
                } else {
                    realname.setIdCardNo(idCardNo);
                }

                //log.info(this.getClass() + "......获取实名认证信息" + realname.getRealName());
            }
            jsonObject.put("realname", realname);

            /** VIP认证客服信息查询 */
            List<DicCommonVo> vipCustomerList = DicCommonUtils.getCommonList("FRONT_CUSTOM_SERVICE");
            jsonObject.put("customServices", vipCustomerList);

            //获取vip认证信息
            Object vipObj = queryInfoByUserId(user.getUserId(), 2);
            VIPAppro vip = null;
            if (vipObj != null) {
                vip = (VIPAppro) vipObj;
                //log.info(this.getClass() + "......获取vip认证信息" + vip.getId());
            }
            jsonObject.put("vip", vip);
            jsonObject.put("expireDate", null);
            jsonObject.put("inday", null);
            if (vip != null) {
                //获取vip认证的到期时间
                if (vip.getIndate() != null) {
                    Date inDate = vip.getIndate();
                    int inday = DateUtil.diffDays(new Date(), inDate);
                    if (inday <= 15) { // VIP认证 15天后即将到期，重新申请认证
                        jsonObject.put("expireDate", DateUtil.format(inDate, "yyyy-MM-dd"));
                        jsonObject.put("inday", inday);
                    }
                }
                //获取vip认证的客户信息
                if (StringUtils.isNotBlank(vip.getServiceNum())) {
                    String jsonStr = approQueryService.queryVipApproEmployeeNum(JsonUtil.beanToJson(vip.getServiceNum()));
                    PersonResponse res1 = JsonUtil.jsonToBean(jsonStr, PersonResponse.class);
                    Employee emp = null;
                    if (res1.getData() != null) {
                        String dataStr = String.valueOf(res1.getData());
                        emp = JsonUtil.jsonToBean(dataStr, Employee.class);
                    }
                    jsonObject.put("employee", emp);

                    //获取专属客户名称，如果该用户已配置到字典表专属客户中，则显示专属客服名
                    String employeeName = "";
                    if (emp != null) {
                        employeeName = emp.getName();
                        if (vipCustomerList != null && vipCustomerList.size() > 0) {
                            for (DicCommonVo dic : vipCustomerList) {
                                if (dic.getPvalue().equals(emp.getJobNum())) {
                                    employeeName = dic.getPkey();
                                }
                            }
                        }
                    }
                    jsonObject.put("employeeName", employeeName);
                   // log.info(this.getClass() + "......获取vip客服姓名" + employeeName);
                }
            }

            //获取手机绑定信息
            Object mobileObj = queryInfoByUserId(user.getUserId(), 3);
            MobileAppro mobile = null;
            if (mobileObj != null) {
                mobile = (MobileAppro) mobileObj;
                mobile.setMobileNo(mobile.getMobileNo().substring(0, 3) + "****" + mobile.getMobileNo().substring(mobile.getMobileNo().length() - 4));
                //log.info(this.getClass() + "......获取手机绑定信息" + mobile.getMobileNo());
            }
            jsonObject.put("mobile", mobile);

            //获取邮箱绑定信息
            Object emailObj = queryInfoByUserId(user.getUserId(), 4);
            EmailAppro email = null;
            if (emailObj != null) {
                email = (EmailAppro) emailObj;
               // log.info(this.getClass() + "......获取邮箱绑定信息" + email.getEmail());
            }
            jsonObject.put("email", email);


            // 银行信息
            String bstr = personalService.queryBankBanded(JsonUtil.beanToJson(user.getUserId()));
            PersonResponse pres = JsonUtil.jsonToBean(bstr, PersonResponse.class);
            BankInfo bank = null;
            if (pres.getData() != null) {
                String dataStr = String.valueOf(pres.getData());
                bank = JsonUtil.jsonToBean(dataStr, BankInfo.class);
            }
            if (null != bank) {
                bank.setBankAccount(bank.getBankAccount().substring(bank.getBankAccount().length() - 4, bank.getBankAccount().length()));
                jsonObject.put("bankInfo", bank);
                //log.info(this.getClass() + "......获取银行信息" + bank.getBankCode());
                //查询银行字典配置
                List<DicCommonVo> payBankDic = DicCommonUtils.getCommonList("PAY_BANK");
                String bankName = "";
                for (int i = 0; i < payBankDic.size(); i++) {
                    if (bank.getBankCode().equals(payBankDic.get(i).getPkey())) {
                        bankName = payBankDic.get(i).getPvalue();
                    }
                }
                jsonObject.put("bankName", bankName);
            }
            //是否设置支付密码
            String paypwd = "1";//有支付密码
            UserResponse ures = this.checkPsw(user.getUserId(), "", 2, HttpUtil.getRealIpAddr(request));
            if (ures.getResultCode() == 220) {
                paypwd = "0";//没支付密码
            }
            jsonObject.put("paypwd", paypwd);
            //log.info(this.getClass() + "......用户资料完善页面...end");



            String token = HttpTookit.getCookieValue(request, Constant.USERTOKEN);

            Message message = userService.isOpenOnAccount(token, HttpTookit.getUserAgent(request), user.getUserId());


            //log.info("isOpenOnAccount req url = " + JSONObject.toJSONString(message));

            // 处理接口异常情况,转换json报错的情况
            if (message != null && message.getCode() == 200000) {
                JSONObject data = (JSONObject) message.getData();
                if (data.getIntValue("code") == 0) {
                    JSONObject dataOjb = data.getJSONObject("data");

                    int isopenaccount = dataOjb.getIntValue("isopenaccount");
                    jsonObject.put("isopenaccount", isopenaccount);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }finally {
            jsonObject.put("activity_url", Configuration.getInstance().getValue("activity_url"));
            return jsonObject.toJSONString();
        }
    }

    public DicCommonVo getDicCommonVo(String typeCode, String pkey) {
        Map map = new HashMap();
        map.put("typeCode", typeCode);
        map.put("pkey", pkey);
        String str = dicCXFService.queryDicCommonByTypeCodeAndPkey(JsonUtil.beanToJson(map));
        WSModelResponse res = JsonUtil.jsonToBean(str, WSModelResponse.class);
        DicCommonVo vo = null;
        if (res.getData() != null) {
            String dataStr = String.valueOf(res.getData());
            vo = JsonUtil.jsonToBean(dataStr, DicCommonVo.class);
        }
        return vo;
    }

    public UserResponse checkPsw(Long userId, String loginPW, int type, String ip) {
        String userid = null;
        try {
            userid = DigestUtil.md5ToHex(userId.toString());
        } catch (CipherException e) {
            e.printStackTrace();
        }
        JSONObject obj = new JSONObject();
        obj.put("userId", userid);
        obj.put("password", loginPW);
        obj.put("pswtype", type);
        obj.put("ip", ip);
        String str = userCXFService.checkPsw(obj.toString());
        UserResponse ures = JsonUtil.jsonToBean(str, UserResponse.class);
        return ures;
    }

    /**
     * vip客服信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/vipCertified", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String vipCertified(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        /** VIP认证客服信息查询 */
        List<DicCommonVo> vipCustomerList = DicCommonUtils.getCommonList("FRONT_CUSTOM_SERVICE");
        jsonObject.put("customServices", vipCustomerList);
        return jsonObject.toString();
    }

    /**
     * 个人资料-->文字资料-->银行设置
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/banksetup", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String banksetup(HttpServletRequest request) {
        JSONObject resultJson = new JSONObject();
        // session中的用户对象
        Object userObj = request.getSession().getAttribute("loginUser");
        if (null == userObj) {
            //会话过期
            resultJson.put("resultCode", 400);
            resultJson.put("msg", "会话过期，请重新登录!");
            return resultJson.toString();
        }
        try {
            User user = (User) userObj;
            resultJson.put("bankList", bankInfo(user.getUserId()));

            //查询银行字典配置
            List<DicCommonVo> payBankDic = DicCommonUtils.getCommonList("PAY_BANK");
            resultJson.put("payBankDic", payBankDic);
        } catch (Exception e) {
            log.error("bankBindRelieveERROR ====" + e.getMessage(), e);
            resultJson.put("resultCode", -505);
            resultJson.put("msg", "系统异常，请刷新重试或联系客服！");
        }
        return resultJson.toString();
    }

    /**
     * 设置默认银行卡
     */
    @RequestMapping(value = "/bankBindRelieve", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String bankBindRelieve(HttpServletRequest request) {
        JSONObject resultJson = new JSONObject();
        int resultCode = 0;
        String msg = "";
        //判断当前token是否有效
        if (!TokenUtil.validToken(request)) {
            resultJson.put("resultCode", Constant.TOKEN_INVALID_ERROR);
            resultJson.put("msg", "页面已过期，请重新尝试");
            return resultJson.toString();
        }
        // 销毁token
        TokenUtil.removeToken(request);

        // session中的用户对象
        Object userObj = request.getSession().getAttribute("loginUser");
        if (userObj == null) {
            resultCode = -3;
            msg = "您的会话已过期，请重新登陆！";
        } else {
            try {
                User user = (User) userObj;
                String type = request.getParameter("type");
                String bankAccount = request.getParameter("bankId");
                String payPW = request.getParameter("payPW");

                //校验密码
                UserResponse ures = this.checkPayPsw(user.getUserId(), payPW, request);
                switch (ures.getResultCode()) {
                    case -1:
                        resultCode = -20;
                        msg = "获取支付密码异常！";
                        break;
                    case -2:
                        resultCode = -21;
                        msg = "支付密码错误，请重新输入！";
                        break;
                    case 220:
                        resultCode = -22;
                        msg = "您还未设置支付密码，设置！";
                        break;
                    case 230:
                        resultCode = -23;
                        msg = "支付密码与登录密码一致，为了资金安全，请进行修改。";
                        break;
                    default:
                }

                //校验通过，更新银行绑定信息
                if (resultCode == 0) {
                    Map map = new HashMap();
                    map.put("userId", user.getUserId());
                    map.put("banded", type.equalsIgnoreCase("bind") ? 1 : 0);
                    map.put("bankAccount", bankAccount);
                    String str = personalService.alterBankNew(JsonUtil.beanToJson(map));
                    PersonResponse personResponse = JsonUtil.jsonToBean(str, PersonResponse.class);
                    resultCode = personResponse.getResultCode();
                    if (resultCode != 0) {
                        if ("bind".equals(type)) {
                            msg = "银行卡绑定失败！";
                        } else {
                            msg = "银行卡解绑失败！";
                        }
                    }
                }
            } catch (Exception e) {
                log.error("bankBindRelieveERROR ====" + e.getMessage(), e);
                resultCode = -505;
                msg = "系统异常，请刷新重试或联系客服！";
            }
        }
        resultJson.put("resultCode", resultCode);
        resultJson.put("msg", msg);
        return resultJson.toString();

    }

    /**
     * 删除银行卡
     */
    @RequestMapping(value = "/bankDelete", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String bankDelete(HttpServletRequest request) {
        JSONObject resultJson = new JSONObject();
        int resultCode = 0;
        String msg = "";
        //判断当前token是否有效
        if (!TokenUtil.validToken(request)) {
            resultJson.put("resultCode", Constant.TOKEN_INVALID_ERROR);
            resultJson.put("msg", "页面已过期，请重新尝试");
            return resultJson.toString();
        }
        // 销毁token
        TokenUtil.removeToken(request);

        // session中的用户对象
        Object userObj = request.getSession().getAttribute("loginUser");
        if (null != userObj) {
            try {
                User user = (User) userObj;
                String id = request.getParameter("bankId");
                String payPW = request.getParameter("payPW");

                //校验支付密码
                UserResponse ures = this.checkPayPsw(user.getUserId(), payPW, request);
                switch (ures.getResultCode()) {
                    case -1:
                        resultCode = -20;
                        msg = "获取支付密码异常！";
                        break;
                    case -2:
                        resultCode = -21;
                        msg = "支付密码错误，请重新输入！";
                        break;
                    case 220:
                        resultCode = -22;
                        msg = "您还未设置支付密码，请先设置支付密码！";
                        break;
                    case 230:
                        resultCode = -23;
                        msg = "支付密码与登录密码一致，为了资金安全，请进行修改。";
                        break;
                    default:
                }

                //校验通过，删除绑定的银行卡
                if (resultCode == 0) {
                    resultJson.put("bankId", id);
                    resultJson.put("userId", user.getUserId());
                    String str = personalService.cancelBankNew(JsonUtil.beanToJson(resultJson));
                    PersonResponse personResponse = JsonUtil.jsonToBean(str, PersonResponse.class);
                    resultCode = personResponse.getResultCode();
                    if (resultCode != 0) {
                        msg = personResponse.getDesc();
                    }
                }

            } catch (Exception e) {
                log.error("bankDeleteERROR ====" + e.getMessage(), e);
                resultCode = -505;
                msg = "系统异常，请刷新重试或联系客服！";
            }
        } else {
            resultCode = -3;
            msg = "您的会话已过期，请重新登陆！";
        }
        resultJson.put("resultCode", resultCode);
        resultJson.put("msg", msg);

        return resultJson.toString();
    }

    /**
     * 银行卡修改
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/bankEdit", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String bankEdit(HttpServletRequest request) {
        JSONObject resultJson = new JSONObject();
        // session中的用户对象
        Object userObj = request.getSession().getAttribute("loginUser");
        if (null != userObj) {
            User user = (User) userObj;

            String bankId = request.getParameter("bankId");
            String branch = request.getParameter("branch");
            String province = request.getParameter("province");
            String city = request.getParameter("city");
            JSONObject json = new JSONObject();
            json.put("bankId", bankId);
            json.put("branch", branch);
            json.put("userId", user.getUserId());
            json.put("province", province);
            json.put("city", city);
            json.put("terminalver", "WEBAPP");
            log.info("bankEdit req=" + json.toJSONString());
            String str = personalService.updateBankBranch(JsonUtil.beanToJson(json));
            log.info("bankEdit resp=" + str);
            PersonResponse personResponse = JsonUtil.jsonToBean(str, PersonResponse.class);
            if (personResponse.getResultCode() == 0) {
                resultJson.put("resultCode", 0);
            } else {
                resultJson.put("resultCode", personResponse.getResultCode());
                resultJson.put("msg", "修改银行卡失败!");
            }
        } else {
            resultJson.put("resultCode", -3);
            resultJson.put("msg", "您的会话已过期，请重新登陆！");
        }
        return resultJson.toString();
    }

    /**
     * 点击修改银行卡获取相对应的参数
     */
    @RequestMapping(value = "/bankinfo", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String bankinfo(HttpServletRequest request) {
        JSONObject obj = new JSONObject();
        String bankId = request.getParameter("bankId");
        Object userObj = request.getSession().getAttribute("loginUser");
        if (bankId != null && userObj != null) {
            User user = (User) userObj;

            //实名认证
            RealNameAppro realName = realName(user.getUserId());
            //获取相对应银行卡信息
            String bankAccount = "";
            String bankCode = "";
            String branch = "";
            String province = "";
            String city = "";
            int id = Integer.parseInt(bankId);
            List<BankInfo> bank = bankInfo(user.getUserId());
            for (int i = 0; i < bank.size(); i++) {
                if (id == bank.get(i).getId()) {
                    bankAccount = bank.get(i).getBankAccount();
                    bankCode = bank.get(i).getBankCode();
                    branch = bank.get(i).getBranch();
                    province = bank.get(i).getProvince();
                    city = bank.get(i).getCity();
                }
            }
            //查询银行字典配置
            List<DicCommonVo> payBankDic = DicCommonUtils.getCommonList("PAY_BANK");
            obj.put("payBankDic", payBankDic);
            obj.put("userName", realName.getRealName());
            obj.put("bankAccount", bankAccount);
            obj.put("bankCode", bankCode);
            obj.put("branch", branch);
            obj.put("province", province);
            obj.put("city", city);

            //查询开户省份、城市
            List provinceList = queryArea("000000");
            obj.put("provinceList", provinceList);

        }
        return obj.toString();
    }

    /**
     * 支付密码是否正确
     *
     * @param userId
     * @param payPW
     * @param request
     * @return
     */
    public UserResponse checkPayPsw(Long userId, String payPW, HttpServletRequest request) {
        String userid = null;
        JSONObject obj = new JSONObject();
        obj.put("userId", userId);
        obj.put("password", EscapeCode.Encryption(payPW));
        obj.put("ip", HttpUtil.getRealIpAddr(request));
        String browser = request.getHeader("User-Agent");
        browser = browser.length() > 200 ? browser.substring(0, 200) : browser;
        obj.put("browser", browser);
        String str = userCXFService.checkPayPassword(obj.toString());
        UserResponse ures = JsonUtil.jsonToBean(str, UserResponse.class);
        return ures;
    }

    /**
     * 银行卡信息
     */
    public List<BankInfo> bankInfo(Long userid) {
        JSONObject json = new JSONObject();
        json.put("userId", userid);
        String str = personalService.queryBankInfoByUserId(json.toString());
        PersonResponse res = JsonUtil.jsonToBean(str, PersonResponse.class);
        List<BankInfo> bankList = null;
        String dataStr = "";
        if (res.getData() != null) {
            dataStr = String.valueOf(res.getData());
            bankList = JsonUtil.jsonToList(dataStr, BankInfo.class);
        }
        return bankList;
    }

    /**
     * 获取银行列表
     */
    @RequestMapping(value = "/bankList", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String bankList(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute("loginUser");
        User user = (User) userObj;
        JSONObject obj = new JSONObject();
        //查询银行字典配置
        List<DicCommonVo> payBankDic = DicCommonUtils.getCommonList("PAY_BANK");
        obj.put("payBankDic", payBankDic);
        //实名认证
        RealNameAppro realName = realName(user.getUserId());
        if (realName != null) {
            obj.put("userName", realName.getRealName());
        }

        //查询开户省份、城市
        List provinceList = queryArea("000000");
        obj.put("provinceList", provinceList);

        return obj.toString();
    }

    /**
     * 获取用户真实姓名
     */
    public RealNameAppro realName(long userId) {
        // 实名认证信息
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        String userIdJson = JsonUtil.beanToJson(params);
        String str = approQueryService.queryRealNameApproByUserId(userIdJson);
        PersonResponse res = JsonUtil.jsonToBean(str, PersonResponse.class);
        RealNameAppro realName = null;
        if (res.getData() != null) {
            String dataStr = String.valueOf(res.getData());
            realName = JsonUtil.jsonToBean(dataStr, RealNameAppro.class);
        }
        return realName;
    }

    /**
     * 银行卡添加
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/bankAdd", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String bankAdd(HttpServletRequest request) {
        JSONObject resultJson = new JSONObject();
        //判断当前token是否有效
        if (!TokenUtil.validToken(request)) {
            resultJson.put("resultCode", Constant.TOKEN_INVALID_ERROR);
            resultJson.put("msg", "页面已过期，请重新尝试");
            return resultJson.toString();
        }
        // 销毁token
        TokenUtil.removeToken(request);

        // session中的用户对象
        Object userObj = request.getSession().getAttribute("loginUser");
        if (null == userObj) {
            resultJson.put("resultCode", -404);
            resultJson.put("msg", "您的回话失效，请重新登录");
            return resultJson.toJSONString();
        }

        User user = (User) userObj;

        // 验证交易密码
        String payPW = request.getParameter("payPW");
        UserResponse ures = this.checkPayPsw(user.getUserId(), payPW, request);
        log.info("bankAdd checkPayPsw resp = " + ures.toJson());
        if (ures.getResultCode() != 0) {
            if (ures.getResultCode() == 220) {
                resultJson.put("resultCode", 5);
            } else if (ures.getResultCode() == 230) {
                resultJson.put("resultCode", 6);
            } else {
                resultJson.put("resultCode", 3);
            }
            return resultJson.toString();
        }

        String isBind = request.getParameter("isBind");
        String bankInfoJson = request.getParameter("bankInfo");
        log.info("bankAdd param isBind =" + isBind + ",bankInfo=" + bankInfoJson);
        if (null != bankInfoJson && !"".equals(bankInfoJson)) {
            BankInfo bankInfo = JsonUtil.jsonToBean(bankInfoJson, BankInfo.class);
            Map map = new HashMap();
            map.put("userId", user.getUserId());
            map.put("bankAccount", bankInfo.getBankAccount());
            String str = personalService.queryByBankCodeOrBankName(JsonUtil.beanToJson(map));
            PersonResponse res = JsonUtil.jsonToBean(str, PersonResponse.class);
            BankInfo bank = null;
            if (res.getData() != null) {
                bank = JsonUtil.jsonToBean(String.valueOf(res.getData()), BankInfo.class);
            }
            if (null != bank) {
                resultJson.put("resultCode", 2);
                return resultJson.toString();
            }

            bankInfo.setBanded("true".equalsIgnoreCase(isBind) ? 1 : 0);
            bankInfo.setAddTime(new Date());
            bankInfo.setAddIp(HttpUtil.getRealIpAddr(request));
            bankInfo.setUserId(user.getUserId());
            bankInfo.setTerminalver("WEBAPP");
            log.info("bankAdd saveBank req = " + JsonUtil.beanToJson(bankInfo));
            String pstr = personalService.saveBank(JsonUtil.beanToJson(bankInfo));
            log.info("bankAdd saveBank resp = " + pstr);
            PersonResponse personResponse = JsonUtil.jsonToBean(pstr, PersonResponse.class);
            if (personResponse.getResultCode() == 0) {
                resultJson.put("resultCode", 0);
            } else {
                resultJson.put("resultCode", personResponse.getResultCode());
                resultJson.put("desc", personResponse.getDesc());
            }
        }
        return resultJson.toString();
    }

    public List queryArea(String area) {
        String resString = areaQueryCXFService.queryLowerArea(area);
        if (resString != null && StringUtil.isNotEmpty(resString)) {
            JSONObject resObj = JSONObject.parseObject(resString);
            if (resObj != null && resObj.size() > 0) {
                WSResponse response = resObj.getObject("response", WSResponse.class);
                if (response != null && response.getResultCode() == AppConsts.WS_RETURN_SUCC) {
                    List areas = resObj.getObject("areas", List.class);
                    return areas;
                }
            }
        }
        return null;
    }

    @RequestMapping(value = "/lowerArea", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String querySchoolFromLand(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        String province = request.getParameter("province");
        List result = queryArea(province);
        json.put("cityList", result);
        return json.toString();
    }

    @RequestMapping(value = "/getProvinceList", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String queryProvinceList(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        //查询开户省份、城市
        List provinceList = queryArea("000000");
        json.put("provinceList", provinceList);
        return json.toString();
    }
}
