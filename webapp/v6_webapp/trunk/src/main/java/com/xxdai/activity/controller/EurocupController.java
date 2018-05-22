package com.xxdai.activity.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ctc.wstx.util.StringUtil;
import com.xxdai.client.CXF_Factory;
import com.xxdai.core.util.http.HttpUtil;
import com.xxdai.core.util.http.WSMapResponse;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.external.eurocup.ws.EuroCupCXFService;
import com.xxdai.external.partner.ws.PartnerPromotionCXFService;
import com.xxdai.external.user.ws.User;
import com.xxdai.util.Configuration;
import com.xxdai.util.HttpTookit;
import com.xxdai.ws.util.WSPageResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * EurocupController
 *
 * @since jdk1.6
 */
@Controller
@RequestMapping(value = "/eurocup")
public class EurocupController {

    private static final Logger log = Logger.getLogger(EurocupController.class);
    private EuroCupCXFService euroCupCXFService = (EuroCupCXFService) CXF_Factory.getFactory(EuroCupCXFService.class, Configuration.getInstance().getValue("webService_url") + "/euroCup").create();
    /**
     * 活动状态
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/activityStatus", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String activityStatus(HttpServletRequest request,HttpServletRequest response) {
    	JSONObject resultJson = new JSONObject();
    	try{
	    	String resultStr = euroCupCXFService.isInActivity();
	    	log.info("EurocupController activityStatus ----> return:" + resultStr);
	    	return resultStr;
    	}catch(Exception e){
    		log.error("store getSumData arise exception:" , e);
	    	resultJson.put("resultCode", -1);
	    	resultJson.put("resultDesc", "系统异常，请稍后重试");
	    	return resultJson.toJSONString();
    	}
    }
    
    /**
     * 我的投票
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/myVote", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String myVote(HttpServletRequest request,HttpServletRequest response) {
    	JSONObject resultJson = new JSONObject();
    	try{
    		
    		//webservice查询参数
	    	Object userObj = request.getSession().getAttribute("loginUser");
	    	User user = null;
	    	if(null != userObj){
	    		user = (User)userObj;
	    	}else{
		    	resultJson.put("resultCode", 400);
		    	resultJson.put("resultDesc", "用户未登录");
		    	return resultJson.toJSONString();
	    	}
	    	log.info("EurocupController myVote ----> params:" + user.getUserId());
	    	
	    	//webservice返回结果
	    	String resultStr = euroCupCXFService.findGuessRecordByUserId(String.valueOf(user.getUserId()));
	    	log.info("EurocupController myVote ----> return:" + resultStr);
            if (StringUtils.isNotBlank(resultStr)) {
            	resultJson = JSON.parseObject(resultStr);
			}
            resultJson.put("resultCode", 0);
	    	resultJson.put("resultDesc", "查询竞猜数据成功");
    	}catch(Exception e){
    		log.error("EurocupController myVote ----> arise exception:" , e);
    		//返回页面
	    	resultJson.put("resultCode", -1);
	    	resultJson.put("resultDesc", "系统异常，请稍后重试");
    	}
        //返回页面
    	log.info("EurocupController myVote ----> return to page:" + resultJson.toJSONString());
        return resultJson.toJSONString();
    }

    /**
     * 球队支持数据
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/teamVoteData", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String teamVoteData(HttpServletRequest request,HttpServletRequest response) {
    	JSONObject resultJson = new JSONObject();
    	try{
	    	//webservice返回结果
	    	String resultStr = euroCupCXFService.queryAllTeams();
	    	log.info("EurocupController teamVoteData ----> return:" + resultStr);
	    	
	    	JSONArray teamData = JSON.parseArray(resultStr);
	    	resultJson.put("teamData", teamData);
	    	resultJson.put("resultCode", 0);
	    	resultJson.put("resultDesc", "获取球队数据成功");
	    	
    	}catch(Exception e){
    		log.error("EurocupController teamVoteData ----> arise exception:" , e);
	    	resultJson.put("resultCode", -1);
	    	resultJson.put("resultDesc", "系统异常，请稍后重试");
    	}
    	//返回页面
    	log.info("EurocupController teamVoteData ----> return to page:" + resultJson.toJSONString());
        return resultJson.toJSONString();
    }
    
    /**
     * 投票
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/vote", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String vote(HttpServletRequest request,HttpServletRequest response) {
    	JSONObject resultJson = new JSONObject();
    	try{
	    	//webservice查询参数
	    	JSONObject paramJson = new JSONObject();
	    	Object userObj = request.getSession().getAttribute("loginUser");
	    	User user = null;
	    	if(null != userObj){
	    		user = (User)userObj;
	    		paramJson.put("userId", user.getUserId());
	    	}else{
		    	resultJson.put("resultCode", 400);
		    	resultJson.put("desc", "用户未登录");
		    	return resultJson.toJSONString();
	    	}
	    	paramJson.put("championId", request.getParameter("choosedNo1"));
	    	paramJson.put("runnerId", request.getParameter("choosedNo2"));
	    	paramJson.put("createIp", HttpUtil.getRealIpAddr(request));
	    	paramJson.put("terminalver", HttpTookit.getRequestTerminal(request));
	    	log.info("EurocupController vote ----> params:" + paramJson.toString());
	    	
	    	//webservice返回结果
	    	String resultStr = euroCupCXFService.saveGuess(paramJson.toString());
	    	log.info("EurocupController vote ----> return:" + resultStr);
	    	
	    	resultJson = JSON.parseObject(resultStr);
	    	
    	}catch(Exception e){
    		log.error("EurocupController vote ----> arise exception:" , e);
	    	resultJson.put("resultCode", -1);
	    	resultJson.put("desc", "系统异常，请稍后重试");
    	}
    	//返回页面
    	log.info("EurocupController vote ----> return to page:" + resultJson.toJSONString());
    	return resultJson.toJSONString();
    }
}
