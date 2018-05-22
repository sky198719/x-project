package com.xxdai.stepupward.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.xxdai.constant.AppConsts;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.xxdai.borrow.constants.RedEnvelopeLimitTypeConsts;
import com.xxdai.client.CXF_Factory;
import com.xxdai.client.ClientUtil;
import com.xxdai.common.DataResponse;
import com.xxdai.constant.AccountConsts;
import com.xxdai.constant.Constant;
import com.xxdai.core.util.http.HttpUtil;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.core.util.lang.DateUtil;
import com.xxdai.core.util.lang.StringUtil;
import com.xxdai.encrypt.EscapeCode;
import com.xxdai.external.account.ws.AccountQueryCXFService;
import com.xxdai.external.redpacket.ws.RedpacketCXFService;
import com.xxdai.external.stepUpward.ws.StepCXFService;
import com.xxdai.external.user.ws.User;
import com.xxdai.external.user.ws.UserCXFService;
import com.xxdai.redpacket.model.RedenvelopeRecord;
import com.xxdai.redpacket.model.RpWsResponse;
import com.xxdai.system.bo.SysConfig;
import com.xxdai.system.ws.SysConfigCXFService;
import com.xxdai.util.Configuration;
import com.xxdai.util.HttpTookit;
import com.xxdai.util.TokenUtil;
import com.xxdai.ws.util.WSModelResponse;



@Controller
@RequestMapping(value = "/stepUpward")
public class StepUpwardController {
	private static final Logger log = Logger.getLogger(StepUpwardController.class);
	
	private final StepCXFService stepCXFService = (StepCXFService) ClientUtil.getWebService(StepCXFService.class,"stepWebService");
	//账户查询
    private AccountQueryCXFService accountQueryCXFService = (AccountQueryCXFService) CXF_Factory.getFactory(AccountQueryCXFService.class, Configuration.getInstance().getValue("trade_url") + "/accountQueryWebService").create();

    //用户操作接口
    private UserCXFService userCXFService = (UserCXFService) CXF_Factory.getFactory(UserCXFService.class, Configuration.getInstance().getValue("webService_url") + "/userWebService").create();
    //获取新手红包
    private RedpacketCXFService redpacketCXFService = (RedpacketCXFService) CXF_Factory.getFactory(RedpacketCXFService.class, Configuration.getInstance().getValue("webService_url") + "/redpacketWebService").create();	
    
    private SysConfigCXFService sysConfigCXFService = (SysConfigCXFService) CXF_Factory.getFactory(SysConfigCXFService.class, Configuration.getInstance().getValue("webService_url") + "/sysConfigWebService").create();	
	/**
     * 步步高升
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getStepUpwardInfo", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getStepInfo(HttpServletRequest request,HttpServletRequest response) {
    	JSONObject paramJson = new JSONObject();
    	JSONObject resultJson = new JSONObject();
    	try{
			//log.info("StepUpwardController getStepUpwardInfo ----> params:" + paramJson.toJSONString());
			String resultStr = stepCXFService.getStepForIndex();
			if (StringUtil.isNotBlank(resultStr)) {
					resultJson = JSON.parseObject(resultStr);
					resultJson.put("serverTime", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			}else{
				resultJson.put("resultCode", "-1");
				resultJson.put("desc", "获取步步高升产品基本信息失败，请稍后重试...");
			}
    	}catch(Exception e){
    		log.error("StepUpwardController getStepUpwardInfo ----> arise exception:" , e);
    		//返回页面
	    	resultJson.put("resultCode", -1);
	    	resultJson.put("desc", "操作失败，请稍后重试...");
    	}
		return resultJson.toJSONString();
	}
    /**
     * 查询用户信息（用于购买页面）
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getUserAccountInfo", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getUserAccountInfo(HttpServletRequest request,HttpServletRequest response) {
    	JSONObject paramJson = new JSONObject();
    	JSONObject resultJson = new JSONObject();
    	try{
			// 用户信息
	        User user = (User) request.getSession().getAttribute("loginUser");
	        // 是否是登录状态
	        if (null != user) {
	            // 查询默认账户信息
	            JSONObject accountJson = new JSONObject();
	            accountJson.put("useId", user.getUserId());
	            accountJson.put("accountType", AccountConsts.ACCOUNT_ONLINE_DEFAULT_TYPE_ACCOUNT);
	           // log.info("StepUpwardController getUserAccountInfo ----> params:" + accountJson.toJSONString());
	            String resultAccountStr = accountQueryCXFService.selectAccountByIdAndType(accountJson.toJSONString());
	           // log.info("StepUpwardController getUserAccountInfo ----> return:" + resultAccountStr);
	            WSModelResponse accountResponse = JsonUtil.jsonToBean(resultAccountStr, WSModelResponse.class);
	            if (accountResponse.getData() != null) {
	                JSONObject defaultAccount = JSONObject.parseObject(accountResponse.getData().toString());
	                resultJson.put("defaultAccount", defaultAccount);
	            }
	            //查询用户已持有的步步高升金额
	            //累计投资总额:accountTotal, 当前持有总额: remaCapitalTotal：累计收益总额:interestTotal
//	            log.info("StepUpwardController getUserInfo statisticStepJoin ----> params:" + String.valueOf(user.getUserId()));
//	            String str2 = stepCXFService.statisticStepJoin(String.valueOf(user.getUserId()));
//	            log.info("StepUpwardController getUserInfo statisticStepJoin ----> return:" + str2);
//	            if(StringUtils.isNotBlank(str2)){
//	                resultJson.put("userStepAccount", str2);
//	            }
	            // 新手红包
	            List<RedenvelopeRecord> redList = getRedPackets(user, request);
	            resultJson.put("redList", redList);
	            
	            resultJson.put("resultCode", 0);
	    		
	    	}else{
	    		resultJson.put("resultCode", -2);
	    		resultJson.put("desc", "用户未登录");
	    	}
	    	
    	}catch(Exception e){
    		log.error("StepUpwardController getUserAccountInfo ----> arise exception:" , e);
    		//返回页面
	    	resultJson.put("resultCode", -1);
	    	resultJson.put("desc", "操作失败，请稍后重试...");
    	}
    	//log.info("StepUpwardController getUserAccountInfo ----> return to page:" + resultJson.toJSONString());
    	return resultJson.toJSONString();
    }
    /**
     * 查询用户持有步步高升信息（用于判断用户额度是否已满）
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getUserStepUpwardInfo", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getUserStepUpwardInfo(HttpServletRequest request,HttpServletRequest response) {
    	JSONObject paramJson = new JSONObject();
    	JSONObject resultJson = new JSONObject();
    	try{
    		// 用户信息
    		User user = (User) request.getSession().getAttribute("loginUser");
    		// 是否是登录状态
    		if (null != user) {
    			//查询用户已持有的步步高升金额
    			//累计投资总额:accountTotal, 当前持有总额: remaCapitalTotal：累计收益总额:interestTotal
    			//log.info("StepUpwardController getUserStepUpwardInfo ----> params:" + String.valueOf(user.getUserId()));
    			String str2 = stepCXFService.statisticStepJoin(String.valueOf(user.getUserId()));
    			//log.info("StepUpwardController getUserStepUpwardInfo ----> return:" + str2);
    			if(StringUtils.isNotBlank(str2)){
    				resultJson.put("resultCode", 0);
    				resultJson.put("userStepAccount", JSONObject.parse(str2));
    			}else{
    				resultJson.put("resultCode", -1);
        			resultJson.put("desc", "未查询到用户持有步步高升");
    			}
    		}else{
    			resultJson.put("resultCode", -2);
    			resultJson.put("desc", "用户未登录");
    		}
    		
    	}catch(Exception e){
    		log.error("StepUpwardController getUserStepUpwardInfo ----> arise exception:" , e);
    		//返回页面
    		resultJson.put("resultCode", -1);
    		resultJson.put("desc", "操作失败，请稍后重试...");
    	}
    	//log.info("StepUpwardController getUserStepUpwardInfo ----> return to page:" + resultJson.toJSONString());
    	return resultJson.toJSONString();
    }
    /**
     * 查询理财产品加入记录
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/stepUpwardJoinHistory", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String stepUpwardJoinHistory(HttpServletRequest request,HttpServletRequest response) {
    	JSONObject paramJson = new JSONObject();
    	JSONObject resultJson = new JSONObject();
    	try{
	    	String stepUpwardId = request.getParameter("stepUpwardId");
	    	String pageSize = request.getParameter("pageSize");
	    	String currentPage = request.getParameter("currentPage");
	    	
	    	paramJson.put("stepId", stepUpwardId);
	    	paramJson.put("pageSize", pageSize);
	    	paramJson.put("currentPage", currentPage);
	    	//log.info("StepUpwardController stepUpwardJoinHistory ----> params:" + paramJson.toJSONString());
	    	String resultStr = stepCXFService.getJoinRecord(paramJson.toJSONString());
	    	//log.info("StepUpwardController stepUpwardJoinHistory ----> return:" + resultStr);
	    	if (StringUtil.isNotBlank(resultStr)) {
	    		resultJson = JSON.parseObject(resultStr);
	    	}else{
	    		resultJson.put("resultCode", "-1");
	    		resultJson.put("desc", "获取步步高升产品加入记录失败，请稍后重试...");
	    	}
    	}catch(Exception e){
    		log.error("StepUpwardController stepUpwardJoinHistory ----> arise exception:" , e);
    		//返回页面
	    	resultJson.put("resultCode", -1);
	    	resultJson.put("desc", "操作失败，请稍后重试...");
    	}
    	//log.info("StepUpwardController stepUpwardJoinHistory ----> return to page:" + resultJson.toJSONString());
    	return resultJson.toJSONString();
    	
    }
    /**
     * 投标
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/stepUpwardTender", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String stepUpwardTender(HttpServletRequest request,HttpServletRequest response) {
    	JSONObject paramJson = new JSONObject();
    	JSONObject resultJson = new JSONObject();
    	try{
	    	//判断当前token是否有效
	        if (!TokenUtil.validToken(request)) {
	            JSONObject json = new JSONObject();
	            json.put("resultCode", Constant.TOKEN_INVALID_ERROR);
	            json.put("desc", "页面已过期，请重新尝试");
	            return json.toString();
	        }
	        TokenUtil.removeToken(request);
	        
	        Object userObj = request.getSession().getAttribute("loginUser");
	        if (userObj == null) {
	            resultJson.put("resultCode", -2);
				resultJson.put("desc", "您的会话失效，请重新登录");
				return resultJson.toJSONString();
	        }
	    	User user = (User)userObj;
	    	long userId = user.getUserId();
	    	String payPwd=request.getParameter("payPwd");  				// 支付密码
	    	String userIp= HttpUtil.getRealIpAddr(request);
	    	
	    	//验证支付密码
	    	resultJson = checkPayPwd(request,userId, payPwd);
	    	if (resultJson.getIntValue("resultCode") != 0) {
	            return resultJson.toJSONString();
	        }
	    	
	        //投资
	    	String stepUpwardId = request.getParameter("stepUpwardId");
	    	String tenderAmount=request.getParameter("realPayAmount");  //实际支付金额
			paramJson.put("userId", userId);
			paramJson.put("stepId", stepUpwardId);
			paramJson.put("account", tenderAmount);
			paramJson.put("addip", userIp);
	        JSONObject terminalVer = new JSONObject();
	        terminalVer.put("user-agent", request.getHeader("user-agent"));
	        terminalVer.put("type", "WEB-APP");
	        paramJson.put("terminalver", terminalVer.toJSONString());
			paramJson.put("channel", "3");
			
			//红包
	        List<String> redCodes = new ArrayList<String>();
	        String redCode = request.getParameter("redCode");
	        if(redCode != null && StringUtil.isNotBlank(redCode)){
	            redCodes.add(redCode);
	            paramJson.put("redCodes", redCodes);
	        }
			
	    	log.info("StepUpwardController stepUpwardTender ----> params:" + paramJson.toJSONString());
	    	String resultStr= stepCXFService.buyStep(paramJson.toJSONString());
	    	log.info("StepUpwardController stepUpwardTender ----> return:" + resultStr);
	    	if (StringUtil.isNotBlank(resultStr)) {
	    		resultJson = JSON.parseObject(resultStr);
	    		resultJson.put("tenderTime", new Date().getTime());
	    	}else{
	    		resultJson.put("resultCode", "-1");
	    		resultJson.put("desc", "系统异常，请稍后重试...");
	    	}

			resultJson.put("activity_url",Configuration.getInstance().getValue("activity_url"));
    	}catch(Exception e){
    		log.error("StepUpwardController stepUpwardTender ----> arise exception:" , e);
	    	resultJson.put("resultCode", -1);
	    	resultJson.put("desc", "操作失败，请稍后重试...");
    	}
    	log.info("StepUpwardController stepUpwardTender ----> return to page:" + resultJson.toJSONString());
    	return resultJson.toJSONString();
    }
    /**
     * 查询理财产品投资记录
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/stepUpwardHistory", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String stepUpwardHistory(HttpServletRequest request,HttpServletRequest response) {
    	JSONObject paramJson = new JSONObject();
    	JSONObject resultJson = new JSONObject();
    	try{
	    	//从session取的当前登录用户
	        Object userObj = request.getSession().getAttribute("loginUser");
	        if (userObj == null) {
	            resultJson.put("resultCode", "-1");
				resultJson.put("desc", "您的会话失效，请重新登录");
				return resultJson.toJSONString();
	        }
	        User user = (User)userObj;
	        
	        String userId = String.valueOf(user.getUserId());
	    	String pageSize = request.getParameter("pageSize");
	    	String currentPage = request.getParameter("currentPage");
	    	String currentTab = request.getParameter("currentTab");
	    	
	    	paramJson.put("userId", userId);
	    	paramJson.put("pageSize", pageSize);
	    	paramJson.put("currentPage", currentPage);
	    	
	    	//log.info("StepUpwardController stepUpwardHistory ----> params:" + paramJson.toJSONString());
	    	String resultStr = "";
	    	if ("1".equals(currentTab)) { //收益中
	    		//log.info("StepUpwardController stepUpwardHistory hodding----> params:" + paramJson.toJSONString());
	    		resultStr = stepCXFService.queryTendingStepJoinList(paramJson.toJSONString());
	    		//log.info("StepUpwardController stepUpwardHistory hodding----> return:" + resultStr);
			}else {//已完成
				//log.info("StepUpwardController stepUpwardHistory finished----> params:" + paramJson.toJSONString());
				resultStr = stepCXFService.queryEndStepJoinList(paramJson.toJSONString());
				//log.info("StepUpwardController stepUpwardHistory finished----> return:" + resultStr);
			}
	    	
	    	if (StringUtil.isNotBlank(resultStr)) {
	    		resultJson = JSON.parseObject(resultStr);
	    	}else{
	    		resultJson.put("resultCode", "-1");
	    		resultJson.put("desc", "获取步步高升产品投资记录失败，请稍后重试...");
	    	}
    	}catch(Exception e){
    		log.error("StepUpwardController stepUpwardHistory ----> arise exception:" , e);
    		//返回页面
	    	resultJson.put("resultCode", -1);
	    	resultJson.put("desc", "操作失败，请稍后重试...");
    	}
    	//log.info("StepUpwardController stepUpwardHistory ----> return to page:" + resultJson.toJSONString());
    	return resultJson.toJSONString();
    	
    }
    /**
     * 查询理财产品退出记录
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/stepUpwardQuitHistory", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String stepUpwardQuitHistory(HttpServletRequest request,HttpServletRequest response) {
    	JSONObject paramJson = new JSONObject();
    	JSONObject resultJson = new JSONObject();
    	try{
    		//从session取的当前登录用户
//    		Object userObj = request.getSession().getAttribute("loginUser");
//    		if (userObj == null) {
//    			resultJson.put("resultCode", "-1");
//    			resultJson.put("desc", "您的会话失效，请重新登录");
//    			return resultJson.toJSONString();
//    		}
//    		User user = (User)userObj;
    		
//    		String userId = String.valueOf(user.getUserId());
    		String pageSize = request.getParameter("pageSize");
    		String currentPage = request.getParameter("currentPage");
    		String stepJoinId = request.getParameter("stepJoinId");
    		
    		paramJson.put("stepJoinId", stepJoinId);
    		paramJson.put("pageSize", pageSize);
    		paramJson.put("currentPage", currentPage);
    		
    		//log.info("StepUpwardController stepUpwardQuitHistory ----> params:" + paramJson.toJSONString());
    		String resultStr = stepCXFService.queryStepQuitList(paramJson.toJSONString());
    		//log.info("StepUpwardController stepUpwardQuitHistory ----> return:" + resultStr);
    		
    		if (StringUtil.isNotBlank(resultStr)) {
    			resultJson = JSON.parseObject(resultStr);
    		}else{
    			resultJson.put("resultCode", "-1");
    			resultJson.put("desc", "获取步步高升产品投资记录失败，请稍后重试...");
    		}
    	}catch(Exception e){
    		log.error("StepUpwardController stepUpwardQuitHistory ----> arise exception:" , e);
    		//返回页面
    		resultJson.put("resultCode", -1);
    		resultJson.put("desc", "操作失败，请稍后重试...");
    	}
    	//log.info("StepUpwardController stepUpwardQuitHistory ----> return to page:" + resultJson.toJSONString());
    	return resultJson.toJSONString();
    	
    }
    /**
     * 退出
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/quitStepUpward", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String quitStepUpward(HttpServletRequest request,HttpServletRequest response) {
    	JSONObject paramJson = new JSONObject();
    	JSONObject resultJson = new JSONObject();
    	try{
	        Object userObj = request.getSession().getAttribute("loginUser");
	        if (userObj == null) {
	            resultJson.put("resultCode", -2);
				resultJson.put("desc", "您的会话失效，请重新登录");
				return resultJson.toJSONString();
	        }
	    	User user = (User)userObj;
	    	long userId = user.getUserId();
	    	String payPwd=request.getParameter("payPwd");  				// 支付密码
	    	
	    	//验证支付密码
	    	resultJson = checkPayPwd(request,userId, payPwd);
	    	if (resultJson.getIntValue("resultCode") != 0) {
	            return resultJson.toJSONString();
	        }
    		
	    	String userIp= HttpUtil.getRealIpAddr(request);
    		String stepJoinId = request.getParameter("stepJoinId");
    		String quitAmount = request.getParameter("quitAmount");
    		
    		paramJson.put("userId", userId);
    		paramJson.put("stepJoinId", stepJoinId);
    		paramJson.put("quitAccount", quitAmount);
    		paramJson.put("addip", userIp);
    		
    		log.info("StepUpwardController quitStepUpward ----> params:" + paramJson.toJSONString());
    		String resultStr = stepCXFService.quit(paramJson.toJSONString());
    		log.info("StepUpwardController quitStepUpward ----> return:" + resultStr);
    		
    		if (StringUtil.isNotBlank(resultStr)) {
    			resultJson = JSON.parseObject(resultStr);

				WSModelResponse wsResponse = JsonUtil.jsonToBean(resultStr, WSModelResponse.class);
				if(wsResponse.getResultCode() == AppConsts.WS_RETURN_SUCC) {
					/******************步步高升GA部署所需参数start******************/
					JSONObject gaJson = new JSONObject();
					gaJson.put("stepJoinId", stepJoinId);
					gaJson.put("userId", user.getUserId());
					String gaJsonStr = stepCXFService.getStepGAData(gaJson.toString());
					WSModelResponse gaRes = JsonUtil.jsonToBean(gaJsonStr, WSModelResponse.class);
					if (gaRes.getData() != null) {
						String dataStr = String.valueOf(gaRes.getData());
						log.info("步步高升ga布码结果：" + dataStr);
						Map map = JsonUtil.jsonToBean(dataStr, Map.class);
						String serviceNum = "";
						if (null != map) {
							resultJson.put("productId", map.get("STEPID"));
							resultJson.put("apr", map.get("APR"));
							resultJson.put("terms", map.get("HOLDDAY"));
							resultJson.put("price", map.get("ACCOUNT"));
							resultJson.put("tradeid", map.get("STEPQUITID"));
							if (null != map.get("SERVICENUM") && !"".equals(map.get("SERVICENUM"))) {
								serviceNum = map.get("SERVICENUM").toString();
							} else {
								serviceNum = "nonFD";
							}
							resultJson.put("servicenum", serviceNum);
						}
					}
					log.info("步步高升退出结果：" + resultJson.toJSONString());
					/******************步步高升GA部署所需参数end******************/
				}
    		}else{
    			resultJson.put("resultCode", "-1");
    			resultJson.put("desc", "步步高升退出失败，请稍后重试...");
    		}
    	}catch(Exception e){
    		log.error("StepUpwardController quitStepUpward ----> arise exception:" , e);
    		//返回页面
    		resultJson.put("resultCode", -1);
    		resultJson.put("desc", "操作失败，请稍后重试...");
    	}
    	log.info("StepUpwardController quitStepUpward ----> return to page:" + resultJson.toJSONString());
    	return resultJson.toJSONString();
    	
    }
    
	private JSONObject checkPayPwd(HttpServletRequest request, long userId, String payPwd) {
		JSONObject resultJson = new JSONObject();
		if(StringUtil.isBlank(payPwd)){
			resultJson.put("resultCode", "-1");
			resultJson.put("desc", "支付密码为空！");
			return resultJson;
		}
		String browser = request.getHeader("User-Agent");
		browser = browser.length() > 200 ? browser.substring(0,200):browser;
		String authPayPwd=EscapeCode.Encryption(payPwd);
		JSONObject authPwd=new JSONObject();
		authPwd.put("userId", userId);
		authPwd.put("password", authPayPwd);
		authPwd.put("ip", HttpUtil.getRealIpAddr(request));
		authPwd.put("browser", browser);
		log.info("StepUpwardController checkPayPassword ----> params:" + authPwd.toJSONString());
		String authStr= userCXFService.checkPayPassword(authPwd.toJSONString());
		log.info("StepUpwardController checkPayPassword ----> return:" + authStr);
		DataResponse userResponse = JsonUtil.jsonToBean(authStr, DataResponse.class);
		int resultCode = 0;
        String desc = "支付密码正确";
        if (userResponse != null) {
            switch (userResponse.getResultCode()) {
                case -1:
                    resultCode = -30;
                    desc = "获取支付密码异常！";
                    break;
                case -2:
                    resultCode = -31;
                    desc = "支付密码错误，请重新输入！";
                    break;
                case 220:
                    resultCode = -32;
                    desc = "您还未设置支付密码，请设置！";
                    break;
                case 230:
                    resultCode = -33;
                    desc = "支付密码与登录密码一致！";
                    break;
                default:
            }
        }
    	resultJson.put("resultCode", resultCode);
		resultJson.put("desc", desc);
		return resultJson;
	}
	
    /**
     * 获取退出收益数据
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getQuitInfo", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getQuitInfo(HttpServletRequest request,HttpServletRequest response) {
    	JSONObject paramJson = new JSONObject();
    	JSONObject resultJson = new JSONObject();
    	try{
    		//从session取的当前登录用户
    		Object userObj = request.getSession().getAttribute("loginUser");
    		if (userObj == null) {
    			resultJson.put("resultCode", "-1");
    			resultJson.put("desc", "您的会话失效，请重新登录");
    			return resultJson.toJSONString();
    		}
    		User user = (User)userObj;
    		
    		long userId = user.getUserId();
    		String stepJoinId = request.getParameter("stepJoinId");
    		String quitAmount = request.getParameter("quitAmount");
    		
    		paramJson.put("userId", userId);
    		paramJson.put("stepJoinId", stepJoinId);
    		paramJson.put("quitAccount", quitAmount);
    		
    	//	log.info("StepUpwardController getQuitInfo ----> params:" + paramJson.toJSONString());
    		String resultStr = stepCXFService.getQuitDesc(paramJson.toJSONString());
    	//	log.info("StepUpwardController getQuitInfo ----> return:" + resultStr);
    		
    		if (StringUtil.isNotBlank(resultStr)) {
    			resultJson = JSON.parseObject(resultStr);
    		}else{
    			resultJson.put("resultCode", "-1");
    			resultJson.put("desc", "获取步步高升退出数据失败，请稍后重试...");
    		}
    	}catch(Exception e){
    		log.error("StepUpwardController getQuitInfo ----> arise exception:" , e);
    		//返回页面
    		resultJson.put("resultCode", -1);
    		resultJson.put("desc", "操作失败，请稍后重试...");
    	}
    	//log.info("StepUpwardController getQuitInfo ----> return to page:" + resultJson.toJSONString());
    	return resultJson.toJSONString();
    	
    }
    /**
     * 获取红包
     */
    private List<RedenvelopeRecord> getRedPackets(User user, HttpServletRequest request) throws Exception{
        if (user == null) {
            return null;
        }
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("pageIndex", 1);
        jsonObject1.put("pageSize", 999);
        jsonObject1.put("userId", (user).getUserId());
        jsonObject1.put("status", 1);
        jsonObject1.put("btype", 0);
        jsonObject1.put("prodType", RedEnvelopeLimitTypeConsts.STEP_USED_PRODUCT_TYPE);
        JSONObject terminalVer = new JSONObject();
        terminalVer.put("user-agent", request.getHeader("user-agent"));
        terminalVer.put("type", "WEB-APP");
        jsonObject1.put("terminalVer", terminalVer);
        jsonArray.add(jsonObject1);
       // log.info("StepUpwardController getRedPackets ----> params:" + jsonArray.toJSONString());
		String str = redpacketCXFService.getRedpacketListByUseCodition(jsonArray.toString());
		//log.info("StepUpwardController getRedPackets ----> return:" + str);
        RpWsResponse res = JsonUtil.jsonToBean(str, RpWsResponse.class);
        if (res.getResultCode() < 0) {
            log.error("红包获取失败：" + res.getDesc());
            return null;
        } else {
            if (res.getData() != null) {
                List<RedenvelopeRecord> list = JsonUtil.jsonToList(res.getData().toString(), RedenvelopeRecord.class);
                return list;
            }else{
            	return null;
            }
        }
    }

    /**
     * 查询是否展示步步高升
     */
    @RequestMapping(value = "/stepUpwardShowOrNot", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String  stepUpwardShowOrNot(User user, HttpServletRequest request) throws Exception{
    	JSONObject paramJson = new JSONObject();
    	JSONObject resultJson = new JSONObject();
		try{
    		paramJson.put("syskey", "SHOW_STEP");
    		//log.info("StepUpwardController stepUpwardShowOrNot ----> params:" + paramJson.toJSONString());
    		String rs = sysConfigCXFService.querySysConfigByKey(paramJson.toJSONString());
    	//	log.info("StepUpwardController stepUpwardShowOrNot ----> return:" + rs);
    		if (rs != null && StringUtil.isNotEmpty(rs)) {
    			JSONObject resObject = JSONObject.parseObject(rs);
    			JSONObject res = JSONObject.parseObject(String.valueOf(resObject.get("response")));
                if ("0".equals(String.valueOf(res.get("resultCode")))) {
                	SysConfig sysConfig = resObject.getObject("sysConfig", SysConfig.class);
                    if (sysConfig != null) {
                    	String value = sysConfig.getSysKeyValue();
                    	if ("true".equals(value)) {
                    		resultJson.put("resultCode", "Y");
            				resultJson.put("desc", "系统配置展示步步高升产品");
						}else{
							resultJson.put("resultCode", "N");
            				resultJson.put("desc", "系统配置不展示步步高升产品");
						}
                    }
				}
                
            }
    	}catch(Exception e){
    		log.error("StepUpwardController stepUpwardShowOrNot ----> arise exception:" , e);
    		//返回页面
	    	resultJson.put("resultCode", -1);
	    	resultJson.put("desc", "操作失败，请稍后重试...");
    	}
    //	log.info("StepUpwardController stepUpwardShowOrNot ----> return to page:" + resultJson.toJSONString());
		return resultJson.toJSONString();
    }
    
}
