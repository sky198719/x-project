package com.xxdai.person.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.payment.baofoo.util.Md5;
import com.xxdai.appro.bo.RealNameAppro;
import com.xxdai.common.BaseController;
import com.xxdai.constant.Constant;
import com.xxdai.constant.SmsConsts;
import com.xxdai.core.util.cipher.CipherException;
import com.xxdai.core.util.cipher.DigestUtil;
import com.xxdai.core.util.http.HttpUtil;
import com.xxdai.core.util.http.WSResponse;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.core.util.lang.StringUtil;
import com.xxdai.encrypt.EscapeCode;
import com.xxdai.external.user.ws.User;
import com.xxdai.person.bo.Employee;
import com.xxdai.person.model.Appro;
import com.xxdai.person.model.PersonResponse;
import com.xxdai.person.model.client.Client;
import com.xxdai.system.bo.DicCommonVo;
import com.xxdai.system.bo.SysConfig;
import com.xxdai.user.model.UserResponse;
import com.xxdai.util.Configuration;
import com.xxdai.util.MessageUtils;
import com.xxdai.util.TokenUtil;
import com.xxdai.ws.util.WSModelResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(value = "/approve")
public class ApproveController extends BaseController {
    private Log logger = LogFactory.getLog(ApproveController.class);

    /**
     * 实名认证
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/realNameApr", produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET, RequestMethod.POST})
    public
    @ResponseBody
    String realNameApr(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String returnMsg = "";
        Object userObj = request.getSession().getAttribute("loginUser");
        String realName = request.getParameter("realname");
        String idCardNo = request.getParameter("idCardNo");
        String cardType = request.getParameter("cardType");
        String uploadIdCardPicFlag = request.getParameter("uploadIdCardPicFlag");
        String picUp = request.getParameter("picUp");
        String picDown = request.getParameter("picDown");
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        if (null == userObj) {
            jsonObject.put("msg", "尚未登录，请登录重试!");
            jsonObject.put("resultCode", -1);
            return jsonObject.toString();
        }
        try {
            User user = (User) userObj;
            /**调用身份认证接口 start**/
            jsonObject.put("userId", user.getUserId());
            jsonObject.put("realname", realName);
            jsonObject.put("idCardNo", idCardNo);
            jsonObject.put("idCardType", 1);
            jsonObject.put("picUp", picUp);
            jsonObject.put("picDown", picDown);
            jsonObject.put("sendIp", HttpUtil.getRealIpAddr(request));
            jsonArray.add(jsonObject);
            logger.info("checkRealName req param = " + jsonArray.toJSONString());
            String jsonstr = approCXFService.checkRealName(jsonArray.toString());
            logger.info("checkRealName resp= " + jsonstr);
            jsonObject.clear();
            /**调用身份认证接口 over**/
            /**接口返回值处理 start*/
            WSResponse wsr = JsonUtil.jsonToBean(jsonstr, WSResponse.class);
            int resultCode = wsr.getResultCode();
            String msg = "";
            switch (resultCode) {
                case -6:
                    jsonObject.put("msg", "用户不存在，请重新登录。");
                    jsonObject.put("resultCode", -1);
                    break;
                case -7:
                    jsonObject.put("msg", "您当前使用的IP地址不合法，请勿攻击。");
                    jsonObject.put("resultCode", -1);
                    break;
                case -19:
                    jsonObject.put("msg", "已经认证通过，无需再次提交。");
                    jsonObject.put("resultCode", -1);
                    break;
                case -20:
                    jsonObject.put("msg", "身份证号码填写有误，请重新填写。");
                    jsonObject.put("resultCode", -1);
                    break;
                case -21:
                    jsonObject.put("msg", "身份证号码填写有误，请重新填写。");
                    jsonObject.put("resultCode", -1);
                    break;
                case -22:
                    jsonObject.put("msg", "身份证号码填写有误，请重新填写。");
                    jsonObject.put("resultCode", -1);
                    break;
                case -23:
                    jsonObject.put("msg", "证件号码已存在，请重新填写或与客服联系。");
                    jsonObject.put("resultCode", -1);
                    break;
                case -99:
                    jsonObject.put("msg", "系统繁忙，请与客服联系。");
                    jsonObject.put("resultCode", -1);
                    break;
                case 0:
                    jsonObject.put("msg", "已提交认证信息,请等待审核");
                    jsonObject.put("resultCode", 0);
                    break;
                case 1:
                    jsonObject.put("msg", "认证已通过");
                    jsonObject.put("resultCode", 1);
                    break;
                case 2:
                    jsonObject.put("msg", "认证不通过：身份证号码与姓名不一致，请重新输入。");
                    jsonObject.put("resultCode", 2);
                    break;
                case 3:
                    jsonObject.put("msg", "您的身份证信息审核未通过，请重新提交资料认证。");
                    jsonObject.put("resultCode", 3);
                    break;
                default:
                    jsonObject.put("msg", "系统繁忙，请与客服联系。");
                    jsonObject.put("resultCode", -1);
                    break;
            }
            /**接口返回值处理 over*/
        } catch (Exception e) {
            logger.error("实名认证申请异常：" + e.getMessage());
            e.printStackTrace();
            returnMsg = "系统繁忙，请与客服联系。";
            jsonObject.put("msg", URLDecoder.decode(returnMsg, "UTF-8"));
            jsonObject.put("resultCode", -1);
        }
        return jsonObject.toString();
    }

    /**
     * 支付密码
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/payPwd", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String payPwd(HttpServletRequest request) {
        JSONObject resultJson = new JSONObject();
        //判断当前token是否有效
        if (!TokenUtil.validToken(request)) {
            resultJson.put("resultCode", Constant.TOKEN_INVALID_ERROR);
            resultJson.put("msg", "页面已过期，请重新尝试");
            return resultJson.toString();
        }
        // 销毁token
        TokenUtil.removeToken(request);
        Object userObj = request.getSession().getAttribute("loginUser");
        //try{
        if (null == userObj) {
            resultJson.put("msg", "尚未登录，请登录重试!");
            return resultJson.toString();
        }
        String payPassword = request.getParameter("payPassword");
        payPassword = EscapeCode.Encryption(payPassword);
        User user = (User) userObj;
        String userid = null;
        try {
            userid = DigestUtil.md5ToHex(user.getUserId().toString());
        } catch (CipherException e) {
            e.printStackTrace();
        }
        UserResponse ures2 = this.checkPsw(user.getUserId(), payPassword, 1, HttpUtil.getRealIpAddr(request));
        if (ures2.getResultCode() == 0) {
            resultJson.put("resultCode", "400");
            return resultJson.toString();
        }
        JSONObject obj = new JSONObject();
        try {
            obj.put("userId", userid);
            obj.put("password", payPassword);
            obj.put("pswtype", 2);
            obj.put("ip", HttpUtil.getRealIpAddr(request));
            logger.info("payPwd req param= " + obj.toJSONString());
            String str = userCXFService.changPassword(obj.toString());
            logger.info("payPwd resp = " + str);
            UserResponse res = JsonUtil.jsonToBean(str, UserResponse.class);

            resultJson.put("resultCode", res.getResultCode());
            resultJson.put("msg", res.getDesc());
        } catch (Exception e) {
            logger.error("payPwd error：" + e.getMessage());
            e.printStackTrace();
            resultJson.put("msg", "系统繁忙，请与客服联系。");
            resultJson.put("resultCode", -1);
        }

        return resultJson.toString();
    }

    public UserResponse checkPsw(Long userId, String loginPW, int type, String ip) {
        String userid = null;
        try {
            userid = DigestUtil.md5ToHex(userId.toString());
        } catch (CipherException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JSONObject obj = new JSONObject();
        obj.put("userId", userid);
        obj.put("password", loginPW);
        obj.put("pswtype", type);
        obj.put("ip", ip);
        logger.info("checkPsw req param = " + obj.toJSONString());
        String str = userCXFService.checkPsw(obj.toString());
        logger.info("checkPsw resp = " + str);
        UserResponse ures = JsonUtil.jsonToBean(str, UserResponse.class);
        return ures;
    }

    /**
     * 修改支付密码
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/updatePW", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String updatePW(HttpServletRequest request) {
        JSONObject resultJson = new JSONObject();
        try {
            // session中的用户对象
            Object userObj = request.getSession().getAttribute("loginUser");
            if (null == userObj) {
                resultJson.put("resultCode", "500");
                return resultJson.toString();
            }

            User user = (User) userObj;
            String newPw = "";
            int typePW = 0;
            /**********************  修改支付密码  ***********************/

            String oldPayPW = EscapeCode.Encryption(request.getParameter("oldPayPW"));
            //旧密码是否正确
            UserResponse ures = this.checkPsw(user.getUserId(), oldPayPW, 2, HttpUtil.getRealIpAddr(request));
            Md5 md5 = new Md5();
            if (oldPayPW.equals(md5.getMD5ofStr(md5.getMD5ofStr("")))) {  //用户输入的支付密码为空
                resultJson.put("resultCode", "101"); //请输入旧的支付密码
                return resultJson.toString();
            }

            if (ures.getResultCode() < 0) {
                resultJson.put("resultCode", "100");//当前支付密码错误，请重新输入
                return resultJson.toString();
            }

            //新密码与旧密码是否相同
            String newPayPW = request.getParameter("newPayPW");
            newPayPW = EscapeCode.Encryption(newPayPW);
            UserResponse ures1 = this.checkPsw(user.getUserId(), newPayPW, 2, HttpUtil.getRealIpAddr(request));
            if (ures1.getResultCode() == 0) {
                resultJson.put("resultCode", "200");
                return resultJson.toString();
            }

            //登陆密码是否和支付密码相同
            //newPayPW = EscapeCode.Encryption(newPayPW);
            UserResponse ures2 = this.checkPsw(user.getUserId(), newPayPW, 1, HttpUtil.getRealIpAddr(request));
            if (ures2.getResultCode() == 0) {
                resultJson.put("resultCode", "400");
                return resultJson.toString();
            }

            if (null != newPayPW && !"".equals(newPayPW)) {
                newPw = newPayPW;
                typePW = 2;
            }
            JSONObject obj = new JSONObject();
            String userid = userid = DigestUtil.md5ToHex(user.getUserId().toString());

            obj.put("userId", userid);
            obj.put("password", newPw);
            obj.put("pswtype", typePW);
            obj.put("ip", HttpUtil.getRealIpAddr(request));
            logger.info("update paypw req param = " + obj.toJSONString());
            String str = userCXFService.changPassword(obj.toString());
            logger.info("update paypw resp = " + str);
            //String str = pwOperateService.updatePW(namePWTypeJson);
            UserResponse res = JsonUtil.jsonToBean(str, UserResponse.class);


            int code = res.getResultCode();
            resultJson.put("resultCode", "" + code);

            // 查询最新的用户信息，重置session
            user = queryUserById(user.getUserId());
            request.getSession().setAttribute("loginUser", user);

        } catch (Exception e) {
            resultJson.put("returnMsg", "后台异常，请重新尝试或者联系客服");
            resultJson.put("resultCode", "-200");
            logger.error("updatePW,error = ", e);
        }
        return resultJson.toString();
    }

    /**
     * 检查客服编号是否存在
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/checkServiceNum", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String checkServiceNum(HttpServletRequest request) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("resultCode", "0");
        String serviceNum = request.getParameter("serviceNum");
        String returnMsg = "";
        String jsonstr = approQueryService.queryVipApproEmployeeNum(JsonUtil.beanToJson(serviceNum));
        PersonResponse personResponse = JsonUtil.jsonToBean(jsonstr, PersonResponse.class);
        Employee employee = null;
        if (personResponse.getData() != null) {
            String dataStr = String.valueOf(personResponse.getData());
            employee = (Employee) JsonUtil.jsonToBean(dataStr, Employee.class);
        }
        if (null == employee) {
            returnMsg = "输入的客服编号不存在，请重新尝试";
            resultJson.put("resultCode", "404");
            resultJson.put("msg", returnMsg);
        }
        return resultJson.toString();
    }

    /**
     * VIP认证
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/vipApply", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String vipApply(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute("loginUser");
        String returnMsg = "";
        JSONObject resultJson = new JSONObject();
        //判断当前token是否有效
        if (!TokenUtil.validToken(request)) {
            resultJson.put("resultCode", Constant.TOKEN_INVALID_ERROR);
            resultJson.put("msg", "页面已过期，请重新尝试");
            return resultJson.toString();
        }
        // 销毁token
        TokenUtil.removeToken(request);
        if (null == userObj) {
            returnMsg = "尚未登录，请登录重试!";
            resultJson.put("msg", returnMsg);
            return resultJson.toString();
        }
        User user = (User) userObj;
        String serviceNum = request.getParameter("serviceNum");

       /* String jsonstr = approQueryService.queryVipApproEmployeeNum(JsonUtil.beanToJson(serviceNum));
        PersonResponse personResponse=JsonUtil.jsonToBean(jsonstr, PersonResponse.class);
        Employee employee=null;
        if(personResponse.getData()!=null){
            String dataStr = String.valueOf(personResponse.getData());
            employee=(Employee) JsonUtil.jsonToBean(dataStr,Employee.class);
        }
        if(null == employee){
            returnMsg = "输入的客服编号不存在，请重新尝试";
            resultJson.put("resultCode", "404");
            resultJson.put("msg",  returnMsg);
         return resultJson.toString();
        } */
        try {
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", user.getUserId());
            jsonObject.put("serviceNum", serviceNum);
            jsonObject.put("sendIp", HttpUtil.getRealIpAddr(request));
            jsonArray.add(jsonObject);
            logger.info("vip apply and auto audit req param=" + jsonArray.toJSONString());
//            String json = approCXFService.checkVip(jsonArray.toString());
            String json = approCXFService.applyAndAuditVip(jsonArray.toString());
            logger.info("vip apply and auto audit resp = " + json);
            WSResponse wsr = JsonUtil.jsonToBean(json, WSResponse.class);
            request.setAttribute("msg", wsr.getDesc());
            resultJson.put("resultCode", wsr.getResultCode());
            resultJson.put("msg", wsr.getDesc());
        } catch (Exception e) {
            resultJson.put("msg", "后台异常，请重新尝试");
            resultJson.put("resultCode", "-200");
            logger.error("vipApply,error = ", e);
        }

        return resultJson.toString();
    }
    
    @RequestMapping(value = "/getEmpNoAndName", produces = { "application/json;charset=UTF-8" })
	public @ResponseBody
	String getEmpNoAndName() {
        JSONObject result = new JSONObject();
		String jsonstr = approQueryService.queryVipApproEmployee();
		PersonResponse res = JsonUtil.jsonToBean(jsonstr, PersonResponse.class);
		if (res.getData() != null) {
			String dataStr = String.valueOf(res.getData());
			List<Employee> list = JsonUtil.jsonToList(dataStr,
					Employee.class);
            Employee e = null;
            JSONArray empNo = new JSONArray();
            JSONArray empName = new JSONArray();
			for (int i = 0; i < list.size(); i++) {
				e = list.get(i);
                empNo.add(e.getJobNum());
                empName.add(e.getName());
			}

            result.put("serviceNum",empNo);
            result.put("empName",empName);
		}
		
		//vip认证校验规则
        String checkVipExp = getCheckVipExp("CHECK_VIP_EXP");
        result.put("checkVipExp", checkVipExp);

		return result.toJSONString();
	}
    
    /**
     * 手机认证
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/mobileAppro", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String mobileAppro(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute("loginUser");
        JSONObject resultJson = new JSONObject();
        if (null == userObj) {
            resultJson.put("resultCode", 400);
            return resultJson.toString();
        }

        //检查重新绑定手机时旧手机号的校验是否通过
        Object randCode = request.getSession().getAttribute("oldMobileRandCode");
        if (randCode != null) {
            boolean oldMobileCheckResult = false;
            Object checkResult = request.getSession().getAttribute(randCode.toString() + "checkResult");
            if (checkResult != null) {
                oldMobileCheckResult = (Boolean) checkResult;
            }
            if (!oldMobileCheckResult) {
                resultJson.put("msg", "请先验证原手机号码！");
                resultJson.put("resultCode", -1);
                return resultJson.toString();
            }
            request.getSession().removeAttribute("oldMobileRandCode");
            request.getSession().removeAttribute(randCode.toString() + "checkResult");
        }
        User user = (User) userObj;
        try {
            String mobileSMSCode = request.getParameter("mobileSMSCode");
            String approveMobile = request.getParameter("approveMobile");
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", user.getUserId());
            jsonObject.put("mobile", approveMobile);
            jsonObject.put("randCode", mobileSMSCode);
            jsonObject.put("sendIp", HttpUtil.getRealIpAddr(request));
            jsonArray.add(jsonObject);
            logger.info("mobile Appro req param= " + jsonArray.toJSONString());
            String jsonstr = approCXFService.checkSmsMsg(jsonArray.toString());
            logger.info("mobile Appro resp = " + jsonstr);
            WSResponse wsr = JsonUtil.jsonToBean(jsonstr, WSResponse.class);
            if (wsr.getResultCode() == 2) {
                //更新session中的用户信息
                user.setMobile(approveMobile);
                request.getSession().setAttribute("loginUser", user);

                //清除session中的手机验证码
                if (randCode != null) {
                    request.getSession().removeAttribute("oldMobileRandCode");
                    request.getSession().removeAttribute(randCode.toString() + "checkResult");
                }
            }
            logger.info("---------------------mobileAppro end-----------------------");
            resultJson.put("msg", wsr.getDesc());
            resultJson.put("resultCode", wsr.getResultCode());
        } catch (Exception e) {
            resultJson.put("msg", "后台异常，请重新尝试或者联系客服");
            resultJson.put("resultCode", "-200");
            logger.error("mobileAppro,error = ", e);
        }
        return resultJson.toString();
    }

    /**
     * 校验手机号码并发送手机验证码短信
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/checkMobileAndSendSMS", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String checkMobileAndSendSMS(HttpServletRequest request, HttpServletResponse response) {
        JSONObject resultJson = new JSONObject();
        try {
            Object userObj = request.getSession().getAttribute("loginUser");
            if (null == userObj) {
                resultJson.put("resultCode", 400);
                return resultJson.toString();
            }

            User user = (User) userObj;
            String phone = request.getParameter("phone");

            int newRandCode = (int) Math.round(Math.random() * 8999) + 1000;

            String sendType = request.getParameter("sendType");
            //校验验证码发送次数限制
            String smsConsts = "0".equals(sendType) ? SmsConsts.BUSICODE_MOBILE_APPRO : SmsConsts.BUSICODE_MOBILE_APPRO_VOICE;
            String msg = "0".equals(sendType) ? "您短信发送太频繁，如果无法收到短信请联系客服或使用语音验证码！" : "您语音发送太频繁，请稍后重试！";
            if (!MessageUtils.checkSendSMSCount(smsConsts + phone, request, response)) {
                resultJson.put("msg", msg);
                resultJson.put("resultCode", -30);
                return resultJson.toString();
            }

            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", user.getUserId());
            jsonObject.put("mobile", phone);
            jsonObject.put("sendIp", HttpUtil.getRealIpAddr(request));
            jsonObject.put("sendType", sendType == null ? "0" : sendType);//0：发送短信验证码 1：发送语音验证码
            jsonObject.put("randCode", newRandCode);
            jsonObject.put("msg", "您正在进行_手机认证，短信验证码为：" + newRandCode + "，手机认证为免费认证，不收取任何费用。");
            logger.info("发送手机验证码参数：" + jsonObject);

            jsonArray.add(jsonObject);
            logger.info("------------绑定手机时发送手机验证码：phone=" + phone);
            String jsonstr = approCXFService.sendSmsMsg(jsonArray.toString());
            WSResponse wsr = JsonUtil.jsonToBean(jsonstr, WSResponse.class);
            resultJson.put("msg", wsr.getDesc());
            resultJson.put("resultCode", wsr.getResultCode());
            logger.info("------------绑定手机时发送手机验证码：msg=" + wsr.getDesc());
        } catch (Exception e) {
            resultJson.put("resultCode",-400);
            resultJson.put("msg", "校验手机号码异常，请重新尝试或者联系客服");
        }
        return resultJson.toString();
    }

    /**
     * 邮箱激活
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/emailActivate", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String emailActivate(HttpServletRequest request) {
        JSONObject resultJson = new JSONObject();
        //判断当前token是否有效
        if (!TokenUtil.validToken(request)) {
            resultJson.put("resultCode", Constant.TOKEN_INVALID_ERROR);
            resultJson.put("msg", "页面已过期，请重新尝试");
            return resultJson.toString();
        }
        // 销毁token
        TokenUtil.removeToken(request);
        Object userObj = request.getSession().getAttribute("loginUser");
        String returnMsg = "";
        if (null == userObj) {
            returnMsg = "尚未登录，请登录重试!";
            resultJson.put("msg", returnMsg);
            return resultJson.toString();
        }
        try {
            User user = (User) userObj;
            String email = request.getParameter("email");
            String uuid = UUID.randomUUID().toString();
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", user.getUserId());
            jsonObject.put("email", email);
            String linkage = Configuration.getInstance().getValue("webapp_url") + request.getParameter("approEmailUrl");
            jsonObject.put("basePath", linkage);
            jsonObject.put("uuid", uuid);
            jsonObject.put("sendIp", HttpUtil.getRealIpAddr(request));
            jsonArray.add(jsonObject);
            String jsonstr = approCXFService.sendEmailMsg(jsonArray.toString());
            WSResponse wsr = JsonUtil.jsonToBean(jsonstr, WSResponse.class);
            resultJson.put("resultCode", wsr.getResultCode());
            resultJson.put("msg", wsr.getDesc());

        } catch (Exception e) {
            resultJson.put("msg", "后台异常，请重新尝试或者联系客服");
            resultJson.put("resultCode", "-200");
            logger.error("emailActivate error", e);
        }
        return resultJson.toString();

    }

    /**
     * 邮件确认
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/activateEmail", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String activateUser(HttpServletRequest request) {
        String uid = request.getParameter("uid");
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uuid", uid);
        jsonObject.put("sendIp", HttpUtil.getRealIpAddr(request));
        jsonArray.add(jsonObject);
        String jsonstr = approCXFService.checkEmailMsg(jsonArray.toString());
        WSResponse wsr = JsonUtil.jsonToBean(jsonstr, WSResponse.class);
        jsonObject.put("msg", wsr.getDesc());

        //设置返回连接
        String bankUrl = Configuration.getInstance().getValue("webapp_url");
        jsonObject.put("bankUrl", bankUrl);
        return jsonObject.toString();
    }

    /**
     * 修改登录密码
     */
    @RequestMapping(value = "/changeLoginPW", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String changeLoginPW(@RequestParam(required = true, value = "oldLoginPW") String oldPassword,
                                @RequestParam(required = true, value = "newLoginPW") String password,
                                HttpSession session,
                                HttpServletRequest request) {
        JSONObject returnJson = new JSONObject();
        // 验证是否登录状态
        Object userObj = session.getAttribute("loginUser");
        if (null == userObj) {
            returnJson.put("code", "-1");
            returnJson.put("data", "");
            returnJson.put("info", "未登录");
            return returnJson.toString();
        }
        User user = (User) userObj;

        if (password.indexOf(user.getUserName()) >= 0) {
            returnJson.put("code", "100");
            returnJson.put("data", "");
            returnJson.put("info", "密码不得包括用户名");
            return returnJson.toString();
        }

        String cipherOldPassword = EscapeCode.Encryption(oldPassword);
        String cipherPassword = EscapeCode.Encryption(password);

        //  1：为登录密码
        WSResponse resultRes1 = this.checkPsw(user.getUserId(), cipherOldPassword, 1, HttpUtil.getRealIpAddr(request));
        if (resultRes1.getResultCode() != 0) {
            returnJson.put("code", "-3");
            returnJson.put("data", "");
            returnJson.put("info", "原始登录密码错误");
            return returnJson.toString();
        }
        WSResponse resultRes2 = this.checkPsw(user.getUserId(), cipherPassword, 1, HttpUtil.getRealIpAddr(request));
        if (resultRes2.getResultCode() == 0) {
            returnJson.put("code", "-4");
            returnJson.put("data", "");
            returnJson.put("info", "新登录密码和原始登录密码不能相同");
            return returnJson.toString();
        }
        WSResponse resultRes3 = this.checkPsw(user.getUserId(), cipherPassword, 2, HttpUtil.getRealIpAddr(request));
        if (resultRes3.getResultCode() == 0) {
            returnJson.put("code", "-5");
            returnJson.put("data", "");
            returnJson.put("info", "新登录密码和支付密码不能相同");
            return returnJson.toString();
        }

        JSONObject obj = new JSONObject();
        String cipherUserId = null;
        try {
            cipherUserId = DigestUtil.md5ToHex(user.getUserId().toString());
        } catch (CipherException e) {
            e.printStackTrace();
        }
        obj.put("userId", cipherUserId);
        obj.put("password", cipherPassword);
        obj.put("pswtype", "1");
        obj.put("ip", HttpUtil.getRealIpAddr(request));
        logger.info("changPassword req param= " + obj.toJSONString());
        String resultStr = userCXFService.changPassword(obj.toString());
        logger.info("changPassword resp =" + resultStr);
        WSResponse resultRes = JsonUtil.jsonToBean(resultStr, WSResponse.class);

        //  同步修改BBS密码
        /*if (resultRes.getResultCode() == 0) {
            Client client = new Client();
            logger.info("sync update bbs user pw req begin");
            String result = client.uc_user_edit(user.getUserName(), cipherOldPassword.substring(0, 6), cipherPassword.substring(0, 6), user.getEmail() == null ? "" : user.getEmail(), 1, "0", "");
            logger.info("sync update bbs user pw resp result = " + result);
        }  */

        returnJson.put("code", String.valueOf(resultRes.getResultCode()));
        returnJson.put("data", "");
        returnJson.put("info", "修改成功");
        return returnJson.toString();
    }

    /**
     * 重置登陆密码
     */
    @RequestMapping(value = "/resetLogPW", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String resetLogPW(@RequestParam(required = true, value = "mobileNo") String mobileNo,
                             @RequestParam(required = true, value = "randCode") String randCode,
                             @RequestParam(required = true, value = "newLoginPW") String newLoginPW,
                             HttpServletRequest request,
                             HttpSession session) {
        JSONObject returnJson = new JSONObject();

        String phone = String.valueOf(session.getAttribute("phone"));
        String smsCode = String.valueOf(session.getAttribute("smsCode"));

        String cipherPassword = EscapeCode.Encryption(newLoginPW);

        if (mobileNo.equals(phone) && randCode.equals(smsCode)) {
            String str = userCXFService.getUserByMobileNo(phone);
            UserResponse res = JsonUtil.jsonToBean(str, UserResponse.class);
            User user = null;
            if (res.getData() != null) {
                String dataStr = String.valueOf(res.getData());
                user = JsonUtil.jsonToBean(dataStr, User.class);
            }
            if (user == null) {
                returnJson.put("code", "-2");
                returnJson.put("data", "");
                returnJson.put("info", "用户不存在");
                return returnJson.toString();
            }

            WSResponse resultRes2 = this.checkPsw(user.getUserId(), cipherPassword, 1, HttpUtil.getRealIpAddr(request));
            if (resultRes2.getResultCode() == 0) {
                returnJson.put("code", "-3");
                returnJson.put("data", "");
                returnJson.put("info", "新登录密码和原始登录密码不能相同");
                return returnJson.toString();
            }
            WSResponse resultRes3 = this.checkPsw(user.getUserId(), cipherPassword, 2, HttpUtil.getRealIpAddr(request));
            if (resultRes3.getResultCode() == 0) {
                returnJson.put("code", "-4");
                returnJson.put("data", "");
                returnJson.put("info", "新的登录密码不能与支付密码一致哦，请修改");
                return returnJson.toString();
            }

            JSONObject obj = new JSONObject();
            String cipherUserId = null;
            try {
                cipherUserId = DigestUtil.md5ToHex(user.getUserId().toString());
            } catch (CipherException e) {
                e.printStackTrace();
            }
            obj.put("userId", cipherUserId);
            obj.put("password", cipherPassword);
            obj.put("pswtype", 1);
            obj.put("ip", HttpUtil.getRealIpAddr(request));
            logger.info(" resetLogPW req param = " + obj.toJSONString());
            String resultStr = userCXFService.changPassword(obj.toString());
            logger.info(" resetLogPW resp = " + resultStr);
            WSResponse resultRes = JsonUtil.jsonToBean(resultStr, WSResponse.class);
            //  同步修改BBS密码
            /*if (resultRes.getResultCode() == 0) {
                Client client = new Client();
                logger.info("sync update bbs logpw req begin");
                String result = client.uc_user_edit(user.getUserName(), "", cipherPassword.substring(0, 6), user.getEmail() == null ? "" : user.getEmail(), 1, "0", "");
                logger.info("sync update bbs logpw resp result = " + result);
            } */

            returnJson.put("code", String.valueOf(resultRes.getResultCode()));
            returnJson.put("data", "");
            returnJson.put("info", resultRes.getDesc());
            return returnJson.toString();
        } else {
            returnJson.put("code", "-5");
            returnJson.put("data", "");
            returnJson.put("info", "手机号码或短信验证码错误");
            return returnJson.toString();
        }
    }

    /**
     * 实名认证/上传正反面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/realNameImg", method = {RequestMethod.GET, RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String realNameImg(HttpServletRequest request) {
        JSONObject returnJson = new JSONObject();
        JSONObject obj = new JSONObject();
        Object userObj = request.getSession().getAttribute("loginUser");
        if (null == userObj) {
            returnJson.put("code", -400);
            returnJson.put("info", "会话失效，请重新登录");
            return returnJson.toString(); // 未登录，登录页面
        }
        User user = (User) userObj;
        // 认证信息
        obj = rNameClick(user.getUserId());
        String namestatus = obj.get("namestatus").toString();
        RealNameAppro realname = (RealNameAppro) obj.get("realname");
        if (namestatus.equals("1")) {
            returnJson.put("code", -2);
            returnJson.put("info", "您已经通过了实名认证，请不要重复提交");
            return returnJson.toString(); // 用户已经认证过
        }
        if (realname != null && realname.getStatus().equals("0")) {
            returnJson.put("code", -3);
            returnJson.put("info", "您的实名认证信息正在审核，请不要重复提交");
            return returnJson.toString(); // 用户已经认证过
        }
        /*** 上传图片 start */
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile filesUp = multipartRequest.getFile("frontFile");
            obj.clear();
            obj = rNameImg(filesUp, request);
            returnJson.put("upFileName", obj.get("FileName"));
            returnJson.put("pathUp", obj.get("path"));
            MultipartFile filesBack = multipartRequest.getFile("backFile");
            obj.clear();
            obj = rNameImg(filesBack, request);
            returnJson.put("backFileName", obj.get("FileName"));
            returnJson.put("pathBack", obj.get("path"));
            returnJson.put("code", 1);
            /*** 上传图片 over */
        } catch (Exception e) {
            returnJson.put("code", -1);
            returnJson.put("info", "身份证上传失败，请刷新重试");
            logger.error("上传身份证图片异常", e);
        }
        return returnJson.toJSONString();
    }

    /**
     * 上传图片
     */
    public JSONObject rNameImg(MultipartFile files, HttpServletRequest request) throws IOException {
        JSONObject returnJson = new JSONObject();
        // 重命名文件
        String fName = files.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String fileFix = fName.substring(fName.indexOf('.') + 1, fName.length());
        //支持JPG,GIF,PNG,BMP,JPEG
        if (!fileFix.toUpperCase().equals("JPG") &&
                !fileFix.toUpperCase().equals("GIF") &&
                !fileFix.toUpperCase().equals("PNG") &&
                !fileFix.toUpperCase().equals("BMP") &&
                !fileFix.toUpperCase().equals("JPEG")) {
            fileFix = "png";//如果上传文件不是图片 修改后缀
        }
        String fileName = uuid + "." + fileFix;
        String realPath = request.getSession().getServletContext().getRealPath("/static/image/uploadFile");
        String pathString = "/static/image/uploadFile/";
        File imageFile = new File(realPath);
        if (!imageFile.exists()) {
            imageFile.mkdirs();
        }
        files.transferTo(new File(realPath, fileName));
        returnJson.put("FileName", fName);
        returnJson.put("path", pathString + fileName);
        return returnJson;
    }

    /**
     * 实名认证填写资料页面校验
     */
    public JSONObject rNameClick(Long userId) {
        JSONObject returnJson = new JSONObject();
        JSONObject obj = new JSONObject();
        obj.put("userId", userId);
        String str = borrowQueryService.queryAppro(obj.toString());
        logger.info(this.getClass() + "......认证信息" + str);
        PersonResponse pre = JsonUtil.jsonToBean(str, PersonResponse.class);
        String namestatus = "0";
        if (pre.getData() != null) {
            String dataStr = String.valueOf(pre.getData());
            Appro appro = JsonUtil.jsonToBean(dataStr, Appro.class);
            if (appro != null) {
                namestatus = appro.getRealName();
            }
        }
        //获取实名认证信息
        Object RealNameObj = queryInfoByUserId(userId, 1);
        RealNameAppro realname = null;
        if (RealNameObj != null) {
            realname = (RealNameAppro) RealNameObj;
            logger.info(this.getClass() + "......获取实名认证信息" + realname.getRealName());
        }
        returnJson.put("namestatus", namestatus);
        returnJson.put("realname", realname);
        return returnJson;
    }
    /**
     * 根据key值获取非员工信息表中的vip客户编号校验正则表达式，多个表达式以","隔开
     *
     * @param key
     * @return
     */
    private String getCheckVipExp(String key) {
    	String checkVipExp = "";
    	try{
    		JSONObject inputObj = new JSONObject();
            inputObj.put("syskey", key);
            String resStr = sysConfigCXFService.querySysConfigByKey(inputObj.toJSONString());
            if (resStr != null && StringUtil.isNotEmpty(resStr)) {
                JSONObject resObject = JSONObject.parseObject(resStr);
                SysConfig sysConfig = resObject.getObject("sysConfig", SysConfig.class);
                if (sysConfig != null) {
                	checkVipExp = sysConfig.getSysKeyValue();
                }
            }
    	}catch(Exception e){
    		logger.error("获取vip编号校验规则失败，",e);
    	}
    	return checkVipExp;
    }
}
