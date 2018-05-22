package com.xxdai.monthfinance.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.xxdai.external.borrowQuery.ws.BorrowQueryCXFService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xxdai.client.CXF_Factory;
import com.xxdai.client.ClientUtil;
import com.xxdai.common.DataResponse;
import com.xxdai.constant.AccountConsts;
import com.xxdai.constant.Constant;
import com.xxdai.core.util.http.HttpUtil;
import com.xxdai.core.util.http.WSMapResponse;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.core.util.lang.DateUtil;
import com.xxdai.core.util.lang.StringUtil;
import com.xxdai.encrypt.EscapeCode;
import com.xxdai.external.account.ws.AccountQueryCXFService;
import com.xxdai.external.borrowTrade.ws.BorrowTradeCXFService;
import com.xxdai.external.prod.ws.deploy.CommProdDeployCXFService;
import com.xxdai.external.user.ws.User;
import com.xxdai.external.user.ws.UserCXFService;
import com.xxdai.person.model.Appro;
import com.xxdai.person.model.PersonResponse;
import com.xxdai.util.HttpTookit;
import com.xxdai.util.TokenUtil;

@Controller
@RequestMapping(value = "/monthFinance")
public class MonthFinanceController {
	private static final Logger log = Logger.getLogger(MonthFinanceController.class);
	
	/**
	 * 理财产品发布
	 */
	private final CommProdDeployCXFService commProdDeployCXFService = (CommProdDeployCXFService) ClientUtil.getWebService(CommProdDeployCXFService.class,"commProdDeployWebservice");
	/**
	 * 账户webservice接口
	 */
	private final AccountQueryCXFService accountQueryCXFService = (AccountQueryCXFService) ClientUtil.getTradeWebService(AccountQueryCXFService.class,ClientUtil.accountQueryWebService);
	/**
	 * 用户密码验证
	 */
	private UserCXFService userCXFService = (UserCXFService) ClientUtil.getWebService(UserCXFService.class, ClientUtil.userWebService);
	/**
	 * 投标webservice接口
	 */
	private final BorrowTradeCXFService borrowTradeCXFService = (BorrowTradeCXFService) ClientUtil.getTradeWebService(BorrowTradeCXFService.class,ClientUtil.borrowTradeWebService);

	private BorrowQueryCXFService borrowQueryService = (BorrowQueryCXFService) CXF_Factory.getFactory(BorrowQueryCXFService.class, com.xxdai.util.Configuration.getInstance().getValue("webService_url") + "/borrowQueryWebService").create();
	
	/**
     * 查询理财产品基本信息（用于热门理财列表展示）
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getProudctBaseInfo", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getProudctBaseInfo(HttpServletRequest request,HttpServletRequest response) {
    	JSONObject paramJson = new JSONObject();
    	JSONObject resultJson = new JSONObject();
    	try{
	    	paramJson.put("busiType", "monthgold");	//月进斗金
			//log.info("MonthFinanceController getProudctBaseInfo ----> params:" + paramJson.toJSONString());
			String resultStr = commProdDeployCXFService.queryCommProdDeployFrontInfo(paramJson.toJSONString());
			//log.info("MonthFinanceController getProudctBaseInfo ----> return:" + resultStr);
			if (StringUtil.isNotBlank(resultStr)) {
					resultJson = JSON.parseObject(resultStr);
					resultJson.put("serverTime", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			}else{
				resultJson.put("resultCode", "-1");
				resultJson.put("desc", "获取月进斗金产品基本信息失败，请稍后重试...");
			}
    	}catch(Exception e){
    		log.error("MonthFinanceController getProudctBaseInfo ----> arise exception:" , e);
    		//返回页面
	    	resultJson.put("resultCode", -1);
	    	resultJson.put("desc", "操作失败，请稍后重试...");
    	}
    	//log.info("MonthFinanceController getProudctBaseInfo ----> return to page:" + resultJson.toJSONString());
		return resultJson.toJSONString();
	}
    /**
     * 查询理财产品详情信息（用于展示产品详情页面）
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getProudctDetail", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getProudctDetail(HttpServletRequest request,HttpServletRequest response) {
    	JSONObject paramJson = new JSONObject();
    	JSONObject resultJson = new JSONObject();
    	try{
	    	// 从session取的当前登录用户
	    	Object userObj = request.getSession().getAttribute("loginUser");
	    	if(null != userObj){
	    		User user = (User)userObj;
	    		paramJson.put("curUserId", user.getUserId());
	    	}
	    	
	    	paramJson.put("busiType", "monthgold");	//月进斗金
	    	//log.info("MonthFinanceController getProudctDetail ----> params:" + paramJson.toJSONString());
	    	String resultStr = commProdDeployCXFService.queryCommProdDeployFrontInfo(paramJson.toJSONString());
	    	//log.info("MonthFinanceController getProudctDetail ----> return:" + resultStr);
	    	if (StringUtil.isNotBlank(resultStr)) {
	    		resultJson = JSON.parseObject(resultStr);
	    		resultJson.put("serverTime", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
	    	}else{
	    		resultJson.put("resultCode", "-1");
	    		resultJson.put("desc", "获取月进斗金产品信息失败，请稍后重试...");
	    	}
    	}catch(Exception e){
    		log.error("MonthFinanceController getProudctDetail ----> arise exception:" , e);
    		//返回页面
	    	resultJson.put("resultCode", -1);
	    	resultJson.put("desc", "操作失败，请稍后重试...");
    	}
    	//log.info("MonthFinanceController getProudctDetail ----> return to page:" + resultJson.toJSONString());
    	return resultJson.toJSONString();
    }
    /**
     * 查询理财产品基本信息（用于购买页面）
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getProudctAndUserInfo", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getProudctAndUserInfo(HttpServletRequest request,HttpServletRequest response) {
    	JSONObject paramJson = new JSONObject();
    	JSONObject resultJson = new JSONObject();
    	try{
	    	// 从session取的当前登录用户
	    	Object userObj = request.getSession().getAttribute("loginUser");
	    	if(null != userObj){
	    		User user = (User)userObj;
	    		paramJson.put("curUserId", user.getUserId());
	    		
	    		String accountType = AccountConsts.ACCOUNT_ONLINE_DEFAULT_TYPE_ACCOUNT; // 账户资金类型，投资账户 1001
	    		Map<String, Object> paraMaps = new HashMap<String, Object>();
	    		paraMaps.put("userId", user.getUserId()); 
	    		paraMaps.put("accountType", accountType);
	    		String paramStr = JsonUtil.beanToJson(paraMaps);
	    		//log.info("MonthFinanceController getProudctAndUserInfo selectUserAccountAndCoupon ----> params:" + paramStr);
	    		String accountStr = accountQueryCXFService.selectUserAccountAndCoupon(paramStr);
	    		//log.info("MonthFinanceController getProudctAndUserInfo selectUserAccountAndCoupon ----> return:" + accountStr);
	    		if (StringUtil.isNotBlank(accountStr)) {
	    			WSMapResponse modelResponse = JsonUtil.jsonToBean(
	    					accountStr, WSMapResponse.class);
	    			if (modelResponse.getResultCode() == 0) {
	    				Map accountMap = modelResponse.getMap();
	    				// 判断用户是否有投资账户
	    				if (accountMap != null && accountMap.containsKey("defaultAccount")) {
	    					resultJson.put("defaultAccount",accountMap.get("defaultAccount")); // 投资账户
	    				}
	    			}
	    		}
	    		
	    	}else{
	    		JSONObject defaultJson = new JSONObject(8);
	    		defaultJson.put("USERID", 0);
	    		defaultJson.put("USABLE", 0);
	    		defaultJson.put("FROZEN", 0); // 冻结金额
	    		
	    		resultJson.put("defaultAccount", defaultJson); // 投资账户
	    	}
	    	
	    	paramJson.put("busiType", "monthgold");	//月进斗金
	    	//log.info("MonthFinanceController getProudctAndUserInfo ----> params:" + paramJson.toJSONString());
	    	String resultStr = commProdDeployCXFService.queryCommProdDeployFrontInfo(paramJson.toJSONString());
	    	//log.info("MonthFinanceController getProudctAndUserInfo ----> return:" + resultStr);
	    	if (StringUtil.isNotBlank(resultStr)) {
	    		resultJson.put("productInfo",JSON.parseObject(resultStr));
	    		resultJson.put("serverTime", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
	    	}else{
	    		resultJson.put("resultCode", "-1");
	    		resultJson.put("desc", "获取月进斗金产品信息失败，请稍后重试...");
	    	}
    	}catch(Exception e){
    		log.error("MonthFinanceController getProudctAndUserInfo ----> arise exception:" , e);
    		//返回页面
	    	resultJson.put("resultCode", -1);
	    	resultJson.put("desc", "操作失败，请稍后重试...");
    	}
    	//log.info("MonthFinanceController getProudctAndUserInfo ----> return to page:" + resultJson.toJSONString());
    	return resultJson.toJSONString();
    }
    /**
     * 查询理财产品加入记录
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/monthFinanceJoinHistory", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String monthFinanceJoinHistory(HttpServletRequest request,HttpServletRequest response) {
    	JSONObject paramJson = new JSONObject();
    	JSONObject resultJson = new JSONObject();
    	try{
	    	String deployId = request.getParameter("monthFinanceId");
	    	String pageSize = request.getParameter("pageSize");
	    	String currentPage = request.getParameter("currentPage");
	    	
	    	paramJson.put("deployId", deployId);
	    	paramJson.put("pageSize", pageSize);
	    	paramJson.put("currentPage", currentPage);
	    	//log.info("MonthFinanceController monthFinanceJoinHistory ----> params:" + paramJson.toJSONString());
	    	String resultStr= commProdDeployCXFService.queryCommProdBorrowTender(paramJson.toJSONString());
	    	//log.info("MonthFinanceController monthFinanceJoinHistory ----> return:" + resultStr);
	    	if (StringUtil.isNotBlank(resultStr)) {
	    		resultJson = JSON.parseObject(resultStr);
	    	}else{
	    		resultJson.put("resultCode", "-1");
	    		resultJson.put("desc", "获取月进斗金产品加入记录失败，请稍后重试...");
	    	}
    	}catch(Exception e){
    		log.error("MonthFinanceController monthFinanceJoinHistory ----> arise exception:" , e);
    		//返回页面
	    	resultJson.put("resultCode", -1);
	    	resultJson.put("desc", "操作失败，请稍后重试...");
    	}
    	//log.info("MonthFinanceController monthFinanceJoinHistory ----> return to page:" + resultJson.toJSONString());
    	return resultJson.toJSONString();
    	
    }
    /**
     * 投标
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/monthFinanceTender", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String monthFinanceTender(HttpServletRequest request,HttpServletRequest response) {
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
	        // 销毁token
	        TokenUtil.removeToken(request);
	        
	        //从session取的当前登录用户
	        Object userObj = request.getSession().getAttribute("loginUser");
	        if (userObj == null) {
	            resultJson.put("resultCode", "-1");
				resultJson.put("desc", "您的会话失效，请重新登录");
				return resultJson.toJSONString();
	        }
	    	
	    	User user = (User)userObj;
	    	long userId = user.getUserId();
	    	
	    	String deployId = request.getParameter("monthFinanceId");
	    	String payPwd=request.getParameter("payPwd");  				// 支付密码
	    	String tenderAmount=request.getParameter("realPayAmount");  //实际支付金额
	    	String userIp= HttpUtil.getRealIpAddr(request);
	    	
	    	//投资金额不正确
	    	if (!isNumeric(tenderAmount)) {
	    		resultJson.put("resultCode", "-1");
	        	resultJson.put("desc", "请输入正确的投资金额...");
	        	return resultJson.toJSONString();
			}
	    	//投资金额不能为零
	    	BigDecimal bdTenderAmount = new BigDecimal(tenderAmount);
    		if (bdTenderAmount.compareTo(BigDecimal.ZERO) <= 0) {
	        	resultJson.put("resultCode", "-1");
	        	resultJson.put("desc", "投资金额须大于零...");
	        	return resultJson.toJSONString();
	        }
	    	
	    	//判断是否已过可投资时间及金额是否有问题
    		String result = getProudctAndUserInfo(request,response);
    		//log.info("MonthFinanceController monthFinanceTender ----> getProudctAndUserInfo return:" + result);
    		JSONObject resultInfo = JSONObject.parseObject(result);
    		JSONObject productInfoData = JSONObject.parseObject(resultInfo.getString("productInfo"));
    		JSONObject productInfo = JSONObject.parseObject(productInfoData.getString("data"));
    		JSONObject accountInfo = JSONObject.parseObject(resultInfo.getString("defaultAccount"));
    		//校验产品和投资产品ID不同
    		if (!productInfo.getString("deployId").equals(deployId)) {
	        	resultJson.put("resultCode", "-1");
	        	resultJson.put("desc", "操作失败，请稍后重试...");
	        	return resultJson.toJSONString();
	        }
    		//尚未开始
	        if (DateUtil.parseDate(productInfo.get("currTime").toString(),"yyyy-MM-dd HH:mm:ss").before(DateUtil.parseDate(productInfo.get("startDate").toString(),"yyyy-MM-dd HH:mm:ss"))) {
	        	resultJson.put("resultCode", "-1");
				resultJson.put("desc", "抢购尚未开始！");
				return resultJson.toJSONString();
			}
	        //已经结束
	        if (DateUtil.parseDate(productInfo.get("currTime").toString(),"yyyy-MM-dd HH:mm:ss").after(DateUtil.parseDate(productInfo.get("endDate").toString(),"yyyy-MM-dd HH:mm:ss"))) {
	        	resultJson.put("resultCode", "-1");
	        	resultJson.put("desc", "抢购已经结束！");
	        	return resultJson.toJSONString();
	        }
	        //金额大于本人账户余额
	        if (bdTenderAmount.compareTo(accountInfo.getBigDecimal("USABLE")) > 0 ) {
	        	resultJson.put("resultCode", "-1");
	        	resultJson.put("desc", "投资金额大于您的可用余额，请充值后再试...");
	        	return resultJson.toJSONString();
	        }
	        //金额大于本人的剩余可投额度
	        if (bdTenderAmount.compareTo(productInfo.getBigDecimal("maxMount")) > 0 ) {
	        	resultJson.put("resultCode", "-1");
	        	resultJson.put("desc", "投资金额大于您的剩余可投额度，请修改金额...");
	        	return resultJson.toJSONString();
	        }
	        //金额小于产品的最小投资金额
	        if (bdTenderAmount.compareTo(productInfo.getBigDecimal("lowestTender")) < 0 ) {
	        	resultJson.put("resultCode", "-1");
	        	resultJson.put("desc", "投资金额小于该产品的最小投资金额("+productInfo.getBigDecimal("lowestTender").toString()+"元)，请修改金额...");
	        	return resultJson.toJSONString();
	        }
	        //金额大于产品的剩余可投额度
	        if (bdTenderAmount.compareTo(productInfo.getBigDecimal("untendSum")) > 0 ) {
	        	resultJson.put("resultCode", "-1");
	        	resultJson.put("desc", "投资金额大于该产品的剩余可投额度，请修改金额...");
	        	return resultJson.toJSONString();
	        }
	        //金额必须为* 的整数倍
	        BigDecimal bdTempTenderAmount = bdTenderAmount.subtract(productInfo.getBigDecimal("lowestTender"));
	        if (bdTempTenderAmount.remainder(productInfo.getBigDecimal("stepSum")).compareTo(new BigDecimal("0")) > 0) {
	        	resultJson.put("resultCode", "-1");
	        	resultJson.put("desc", "投资金额必须为"+productInfo.getDouble("stepSum")+"的整数倍，请修改金额...");
	        	return resultJson.toJSONString();
	        }
	        
	    	//验证密码
			if(StringUtil.isBlank(payPwd)){
				resultJson.put("resultCode", "-1");
				resultJson.put("desc", "支付密码为空！");
				return resultJson.toJSONString();
			}
			String browser = request.getHeader("User-Agent");
	        browser = browser.length() > 200 ? browser.substring(0,200):browser;
			String authPayPwd=EscapeCode.Encryption(payPwd);
			JSONObject authPwd=new JSONObject();
			authPwd.put("userId", user.getUserId());
			authPwd.put("password", authPayPwd);
			authPwd.put("ip", userIp);
			authPwd.put("browser", browser);
			log.info("MonthFinanceController monthFinanceTender ----> checkPayPassword params:" + authPwd.toJSONString());
			String authStr= userCXFService.checkPayPassword(authPwd.toJSONString());
			log.info("MonthFinanceController monthFinanceTender ----> checkPayPassword return:" + authStr);
			DataResponse userResponse = JsonUtil.jsonToBean(authStr, DataResponse.class);
	        int resultCode = 0;
	        String desc = "";
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
	        if (resultCode != 0) {
	        	resultJson.put("resultCode", resultCode);
				resultJson.put("desc", desc);
	            return resultJson.toJSONString();
	        }
	        
	        //是否实名认证
//	        boolean realNameFlag = false;
//	        JSONObject inputObj = new JSONObject();
//	        inputObj.put("userId", user.getUserId());
//	        log.info("MonthFinanceController monthFinanceTender ----> queryAppro params:" + inputObj.toJSONString());
//	        String str = borrowQueryService.queryAppro(inputObj.toString());
//	        log.info("MonthFinanceController monthFinanceTender ----> queryAppro return:" + str);
//	        PersonResponse res = JsonUtil.jsonToBean(str, PersonResponse.class);
//	        Appro appro = null;
//	        if (res.getData() != null) {
//	            String dataStr = String.valueOf(res.getData());
//	            appro = JsonUtil.jsonToBean(dataStr, Appro.class);
//	        }
//	        if (null != appro && "1".equals(appro.getRealName())) {
//	            realNameFlag = true;
//	        }
//	        if (!realNameFlag) {
	            //未实名认证 先进行实名认证
//	        	resultJson.put("resultCode", "-1");
//				resultJson.put("desc", "您的实名认证尚未完成！");
//	            return resultJson.toJSONString();
//	        }
	    	
			paramJson.put("userId", userId);
			paramJson.put("deployId", deployId);
			paramJson.put("tenderMoney", tenderAmount);
			paramJson.put("ip", userIp);
			paramJson.put("terminalVer", HttpTookit.getRequestTerminal(request));
	    	log.info("MonthFinanceController monthFinanceTender ----> params:" + paramJson.toJSONString());
	    	String resultStr= borrowTradeCXFService.tenderCommProdBorrow(paramJson.toJSONString());
	    	log.info("MonthFinanceController monthFinanceTender ----> return:" + resultStr);
	    	if (StringUtil.isNotBlank(resultStr)) {
	    		resultJson = JSON.parseObject(resultStr);
	    		resultJson.put("tenderTime", new Date().getTime());
	    	}else{
	    		resultJson.put("resultCode", "-1");
	    		resultJson.put("desc", "系统异常，请稍后重试...");
	    	}
    	}catch(Exception e){
    		log.error("MonthFinanceController monthFinanceTender ----> arise exception:" , e);
    		//返回页面
	    	resultJson.put("resultCode", -1);
	    	resultJson.put("desc", "操作失败，请稍后重试...");
    	}
    	//log.info("MonthFinanceController monthFinanceTender ----> return to page:" + resultJson.toJSONString());
    	return resultJson.toJSONString();
    }
    /**
     * 查询理财产品投资记录
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/monthFinanceHistory", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String monthFinanceHistory(HttpServletRequest request,HttpServletRequest response) {
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
	    	
	    	String status = "";
	    	if ("1".equals(currentTab)) { //收益中
	    		status = "4";
			}else {//已完成
				status = "5";
			}
	    	
	    	paramJson.put("userId", userId);
	    	paramJson.put("busiType", "monthgold");	//月进斗金
	    	paramJson.put("pageSize", pageSize);
	    	paramJson.put("currentPage", currentPage);
	    	paramJson.put("status", status);
	    	//log.info("MonthFinanceController monthFinanceHistory ---->params:" + paramJson.toJSONString());
	    	String resultStr= commProdDeployCXFService.queryMyCommProdTender(paramJson.toJSONString());
	    	//log.info("MonthFinanceController monthFinanceHistory ----> return:" + resultStr);
	    	if (StringUtil.isNotBlank(resultStr)) {
	    		resultJson = JSON.parseObject(resultStr);
	    	}else{
	    		resultJson.put("resultCode", "-1");
	    		resultJson.put("desc", "获取月进斗金产品投资记录失败，请稍后重试...");
	    	}
    	}catch(Exception e){
    		log.error("MonthFinanceController monthFinanceHistory ----> arise exception:" , e);
    		//返回页面
	    	resultJson.put("resultCode", -1);
	    	resultJson.put("desc", "操作失败，请稍后重试...");
    	}
    	//log.info("MonthFinanceController monthFinanceHistory ----> return to page:" + resultJson.toJSONString());
    	return resultJson.toJSONString();
    	
    }
    public boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");
    	   Matcher isNum = pattern.matcher(str);
    	   if( !isNum.matches() ){
    	       return false; 
    	   } 
    	   return true; 
    	}
}
