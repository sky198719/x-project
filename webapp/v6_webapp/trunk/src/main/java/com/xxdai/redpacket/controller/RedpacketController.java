/** 
 * Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
 */
package com.xxdai.redpacket.controller;

import javax.servlet.http.HttpServletRequest;

import com.xxdai.constant.Constant;
import com.xxdai.external.user.ws.User;
import com.xxdai.http.Message;
import com.xxdai.product.service.ProductService;
import com.xxdai.util.HttpTookit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxdai.client.CXF_Factory;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.redpacket.model.RpWsResponse;
import com.xxdai.redpacket.webservice.interfaces.RedpacketCXFService;
import com.xxdai.util.Configuration;

import java.util.concurrent.Executors;

@Controller
@RequestMapping(value = "/redpacket")
public class RedpacketController {
	private static final Log log = LogFactory.getLog(RedpacketController.class);
	RedpacketCXFService redpacketCXFService=(RedpacketCXFService) CXF_Factory.getFactory(RedpacketCXFService.class, Configuration.getInstance().getValue("webService_url") + "/redpacketWebService").create();
	@Autowired
	private ProductService productService;

	
	@RequestMapping(value = "/redpacketlist", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String redpacketList(HttpServletRequest request) {
		if(request.getSession().getAttribute("loginUser")==null)
			return "";
		User user = (User) request.getSession().getAttribute("loginUser");
		
		String status="1";
		status=request.getParameter("status");
		String result="";
		JSONArray  jsonArray=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("pageIndex", 1);
		jsonObject.put("pageSize", 20);
		jsonObject.put("userId", user.getUserId());
		jsonObject.put("status", status);
		jsonObject.put("btype", null);
		jsonObject.put("limitType", 1);
		jsonObject.put("prodType", 0);
		
		jsonArray.add(jsonObject);
		
		String redpacketStr=redpacketCXFService.getRedpacketList(jsonArray.toString());
		RpWsResponse rpWsResponse=JsonUtil.jsonToBean(redpacketStr, RpWsResponse.class);
		
		if(rpWsResponse.getResultCode()<0 && rpWsResponse.getResultCode()!=-99){
			log.error("红包查询失败："+rpWsResponse.getDesc());
		}else{
			result=rpWsResponse.toJson();
		}
		
		return result;
	}

	@RequestMapping(value = "/list", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String list(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try {
			if(request.getSession().getAttribute("loginUser") == null) {
				result.put("code",100);
				result.put("info","未登录");
				return result.toJSONString();
			}


			User user = (User) request.getSession().getAttribute("loginUser");
			String redStatus = request.getParameter("redStatus");

			JSONArray  jsonArray=new JSONArray();
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("pageIndex", 1);
			jsonObject.put("pageSize", 20);
			jsonObject.put("userId", user.getUserId());
			jsonObject.put("status", redStatus);
			jsonObject.put("btype", null);
			jsonObject.put("limitType", 1);
			jsonObject.put("prodType", 0);

			jsonArray.add(jsonObject);

			String redpacketStr = redpacketCXFService.getRedpacketList(jsonArray.toString());

			RpWsResponse rpWsResponse = JsonUtil.jsonToBean(redpacketStr, RpWsResponse.class);
			if(rpWsResponse.getResultCode() == 0) {
				result.put("redpacket",rpWsResponse);
			}

			String token = HttpTookit.getCookieValue(request, Constant.USERTOKEN);
			String couponStatus = request.getParameter("couponStatus");

			Message msg = productService.queryCouponRecordsByUserId(couponStatus,token,HttpTookit.getUserAgent(request));
			if(msg != null && msg.getCode() == Constant.SUCCESS) {
				result.put("coupon",msg.getData());
			}
			result.put("code",0);
		}catch (Exception e) {
			log.error("redpacket list error",e);
			result.put("code",400);
			result.put("info","查询异常");
		} finally {
			return result.toJSONString();
		}
	}
	
}
