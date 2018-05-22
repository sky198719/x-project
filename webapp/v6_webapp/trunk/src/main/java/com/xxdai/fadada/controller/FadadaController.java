/**
 * Copyright (c) 2016, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.fadada.controller;

import com.alibaba.fastjson.JSONObject;
import com.xxdai.client.CXF_Factory;
import com.xxdai.core.util.http.HttpUtil;
import com.xxdai.external.fadada.ws.FaDaDaCXFService;
import com.xxdai.util.Configuration;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 法大大
 */
@Controller
public class FadadaController {
    /**
     * 日志记录器
     */
    Logger log = LoggerFactory.getLogger(FadadaController.class);

    /**
     * 法大大接口
     */
    private FaDaDaCXFService faDaDaCXFService = (FaDaDaCXFService) CXF_Factory.getFactory(FaDaDaCXFService.class, Configuration.getInstance().getValue("webService_url") + "/fadadaWebService").create();


    /**
     * 签约法大大
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/signFdd", produces = {"application/json;charset=UTF-8"})
    public ModelAndView signContract(HttpServletRequest request,HttpServletResponse response) {
        
    	try{
    		response.setCharacterEncoding("utf-8");
	    	String borrowId = request.getParameter("bid");
	        String userType = request.getParameter("ut");
			String customerId = request.getParameter("cusId");
			String transactionId = request.getParameter("transactionId");


	        //构造JSON请求参数
	        JSONObject params = new JSONObject();
	        params.put("borrowId", borrowId);
	        params.put("userType", userType);
	        params.put("userId", 0l);
	        params.put("ip", HttpUtil.getRealIpAddr(request) );
			params.put("customerId",customerId);
			params.put("transactionId",transactionId);

	        //查询该borrowId的签约状态
	        log.info("get Fadada sign status param:" + params.toJSONString());
	        String status = faDaDaCXFService.getGuarantorSignStatusByborrowId(params.toJSONString());
	        log.info("get Fadada sign status response:" + status);
	        JSONObject statusJson = JSONObject.parseObject(status);
	        if (!"0".equals(String.valueOf(statusJson.get("resultCode")))) {
	        	response.sendError(500);
			}else{
				//-1签约失败，1签约成功，0未签约
		        String signStatus = String.valueOf(statusJson.get("data"));
		        log.info("borrowId:" + borrowId + "，签约状态：" +   ("0".equals(signStatus)?"未签约":
		        													("1".equals(signStatus)?"签约成功":
		        													("-1".equals(signStatus)?"签约失败":
		        													"签约状态未知"))));
		        if ("1".equals(signStatus)) {
					userType = userType == null ? "" : userType;
					customerId = customerId == null ? "" : customerId;
					transactionId = transactionId == null ? "" : transactionId;

		        	RequestDispatcher dispatcher = request.getRequestDispatcher(String.format("/viewFddContract.do?transactionId=%s&userType=%s&customerId=%s",transactionId,userType,customerId));
		        	dispatcher.forward(request, response);
		        	return null;
				}
			}
	        
	        log.info("sign Fadada param:" + params.toJSONString());
	        String result = faDaDaCXFService.doSigningContract(params.toJSONString());
	        log.info("sign Fadada data:" + result);
	        JSONObject resultJson = JSONObject.parseObject(result);
	        if ("0".equals(String.valueOf(resultJson.get("resultCode")))) {
	        	String resultData = resultJson.getString("data");
				if (StringUtils.isBlank(resultData)) {
					log.info("sign Fadada url is blank...");
					response.sendError(500);
				}else{
					response.sendRedirect(resultData);
				}
			}else if ("-4".equals(String.valueOf(resultJson.get("resultCode")))) {
				ModelAndView mav = new ModelAndView("/fadada/signResult");
		    	mav.addObject("signFlag", "nosign");
		    	mav.addObject("info", resultJson.get("desc"));
		    	return mav;
			}else{
				response.sendError(500);
			}
    	}catch(Exception e){
    		log.error("FddController signContract ----> arise exception:" , e);
    		try {
				response.sendError(500);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
    	}
    	return null;
    }

    /**
	 * 签署合同后法大大返回的请求地址
	 * @param request
	 * @return 
	 */
	@RequestMapping(value = "/guarantorSigningContractReturn", produces = {"application/json;charset=UTF-8"})
	public ModelAndView guarantorSigningContractReturn(HttpServletRequest request, HttpServletResponse response){
		try {
			response.setCharacterEncoding("utf-8");
			String transaction_id = request.getParameter("transaction_id");//交易号
			String timestamp = request.getParameter("timestamp"); //请求时间
			String result_code = request.getParameter("result_code");//签章结果代码
			String result_desc = request.getParameter("result_desc");//签章结果描述
			String msg_digest = request.getParameter("msg_digest");//消息摘要
			String download_url = request.getParameter("download_url");//签章后文档下载地址
			String viewpdf_url = request.getParameter("viewpdf_url"); //在线查看已签文档的地址
			
			String resultDesc = StringUtils.isBlank(result_desc) ? "" : new String(result_desc.getBytes("iso8859-1"),"UTF-8");
			
			log.info(String.format("guarantorSigningContractReturn data:{transaction_id:%s,timestamp:%s," +
	                "result_code:%s,result_desc:%s,msg_digest:%s,download_url:%s,viewpdf_url:%s}",
	                transaction_id, timestamp, result_code,result_desc,msg_digest,download_url,viewpdf_url));
			
			JSONObject params = new JSONObject();
			params.put("transactionId", transaction_id);
			params.put("timestamp", timestamp);
			params.put("resultCode", result_code);
			params.put("resultDesc", resultDesc);
			params.put("msgDigest", msg_digest);
			params.put("downloadUrl", download_url);
			params.put("viewpdfUrl", viewpdf_url);
	        
	        log.info("signFddContractResult param:" + params.toJSONString());
	        String result = faDaDaCXFService.doSigningContractReturn(params.toJSONString());
	        log.info("signFddContractResult result:" + result);
	        JSONObject resultJson = JSONObject.parseObject(result);
	        String signFlag = "";
	    	if("1".equals(String.valueOf(resultJson.get("resultCode")))){
	    		signFlag = "success";
	        }else{
	        	signFlag = "fail";
	        }
       
	    	ModelAndView mav = new ModelAndView("/fadada/signResult");
	    	mav.addObject("borrowId", transaction_id.substring(0, transaction_id.length()-13));
	    	mav.addObject("signFlag", signFlag);
			mav.addObject("transactionId", transaction_id);
	    	return mav;
		}catch(Exception e){
    		log.error("FddController guarantorSigningContractReturn ----> arise exception:" , e);
    		try {
				response.sendError(500);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
    	}
		return null;
	}
	/**
	 * 签署合同后法大大返回的请求地址(自测使用)
	 * @param request
	 * @return 
	 */
	@RequestMapping(value = "/signFddContractResult1", produces = {"application/json;charset=UTF-8"})
	public ModelAndView signFddContractResult1(HttpServletRequest request, HttpServletResponse response){
		response.setCharacterEncoding("utf-8");
		String signFlag = "";
		if(!"1".equals("1")){
			signFlag = "nosign";
        }else{
        	signFlag = "fail";
        }
		
		ModelAndView mav = new ModelAndView("/fadada/signResult");
		mav.addObject("borrowId", "BW201605189402");
		mav.addObject("signFlag", signFlag);
		mav.addObject("info", "该合同签约已失效，可能因审核失败、流标等其他原因导致。<br>详情请咨询借款人或信贷经理");
		return mav;
	}
	
	/**
	 * 查看合同
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/viewFddContract", produces = {"application/json;charset=UTF-8"})
	public void viewFddContract(HttpServletRequest request,HttpServletResponse response){
		try {
			response.setCharacterEncoding("utf-8");
			String borrowId = request.getParameter("bid");
			
			JSONObject params = new JSONObject();
			params.put("borrowId", borrowId);

			params.put("transactionId",request.getParameter("transactionId"));
			params.put("customerId",request.getParameter("customerId"));
			params.put("userType",request.getParameter("userType"));
			log.info("viewFddContract param:" + params.toJSONString());
	        String result = faDaDaCXFService.doViewContract(params.toJSONString());
	        log.info("viewFddContract result:" + result);
	        JSONObject resultJson = JSONObject.parseObject(result);
	        if (!"0".equals(String.valueOf(resultJson.get("resultCode")))) {
	        	response.sendError(500);
			}else{
				String resultData = resultJson.getString("data");
				if (StringUtils.isBlank(resultData)) {
					log.info("viewFddContract url is blank...");
					response.sendError(500);
				}else{
					response.sendRedirect(resultData);
				}
			}
	        
        }catch(Exception e){
    		log.error("FddController viewFddContract ----> arise exception:" , e);
    		try {
				response.sendError(500);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
    	}
	}
}
