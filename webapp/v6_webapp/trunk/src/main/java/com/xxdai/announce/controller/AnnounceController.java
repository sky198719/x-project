package com.xxdai.announce.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxdai.client.CXF_Factory;
import com.xxdai.external.hotActivities.ws.HotActivitiesCXFService;
import com.xxdai.news.webservice.interfaces.NewsCXFService;
import com.xxdai.util.Configuration;

@Controller
@RequestMapping(value = "/announce")
public class AnnounceController {
	
	NewsCXFService newsCXFService = (NewsCXFService) CXF_Factory.getFactory(NewsCXFService.class, Configuration.getInstance().getValue("webService_url") + "/newsWebService").create();
	HotActivitiesCXFService hotActivitiesCXFService = (HotActivitiesCXFService) CXF_Factory.getFactory(HotActivitiesCXFService.class, Configuration.getInstance().getValue("webService_url") + "/hotActivitiesWebService").create();
	
	private static final Logger logger = Logger.getLogger(AnnounceController.class);

	@RequestMapping(value = "/getList", produces = {"application/json;charset=UTF-8"})
	public @ResponseBody String getList(HttpServletRequest request, HttpServletResponse response) {
		JSONObject resultJson = new JSONObject();
		try{
			/* 
			 * 新闻公告:news_notices
			 * 近期活动:news_activity
			 */
			JSONArray jsonArray = new JSONArray();
			JSONObject paramJson = new JSONObject();
	
			String currentPage = request.getParameter("currentPage");
			String pageSize = request.getParameter("pageSize");
			String tab = request.getParameter("type");
			
			paramJson.put("pageSize", StringUtils.isBlank(pageSize)?"10":pageSize);
			paramJson.put("pageIndex", StringUtils.isBlank(currentPage)?"1":currentPage);
			
			String regEx_html="<[^>]+>";
			Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf_splitByDot = new SimpleDateFormat("yyyy.MM.dd");
			SimpleDateFormat sdf_HHmm = new SimpleDateFormat("HH:mm");
			
			if ("1".equals(tab)) {
				paramJson.put("terminalver", "webapp");//webapp
				paramJson.put("typeName", "news_notices");
				jsonArray.add(paramJson);
				//logger.info("AnnounceController getList ----> params:" + jsonArray.toJSONString());
				String resultStr = newsCXFService.queryNewsNoticesListForFront(jsonArray.toJSONString());
				//logger.info("AnnounceController getList ----> return:" + resultStr);
				resultJson = JSON.parseObject(resultStr);
				JSONArray ja = resultJson.getJSONArray("data");
				
				for(int i = 0; i < ja.size(); i++){
					JSONObject jo = ja.getJSONObject(i);
					//状态STATUS  0:正常状态  1:隐藏不显示
                    if (1 == jo.getIntValue("status")) {
                    	continue;
                    }
					String time = "";
					String timeStr = sdf.format(jo.getDate("updateTime"));
					Date today = new Date();
					String todayStr = sdf.format(today);
                    if(todayStr.equals(timeStr)){
                    	time = sdf_HHmm.format(jo.getDate("updateTime"));
                    }else{
                    	time = timeStr.substring(2);
                    }
                    jo.put("time", time);
					
					String context = jo.getString("context");
					String subTitle = "";
					if(StringUtils.isNotBlank(context)){
						Matcher m_html=p_html.matcher(context);
						subTitle =  m_html.replaceAll("").replaceAll(" ", "").replaceAll("&nbsp;", "").replaceAll("\t", "");
					}
					jo.put("subTitle", subTitle);
					
					ja.set(i, jo);
				}
				resultJson.put("data", ja);
				
            } else {
            	paramJson.put("platform", "1");//PLATFORM	使用平台：1、webapp 2、iosapp 3、androidapp 4、pc
				paramJson.put("status", "1");//状态STATUS  1:正常状态  0:隐藏不显示
				
				//近期活动展示：进行中活动都要展示，预热展示近一个月，已结束展示近3个月
				Calendar c_startDate = Calendar.getInstance();
				Calendar c_endDate = Calendar.getInstance();
				c_startDate.add(Calendar.MONTH, -3);
				c_endDate.add(Calendar.MONTH, 1);
				paramJson.put("startDate", sdf.format(c_startDate.getTime()));
				paramJson.put("endDate", sdf.format(c_endDate.getTime()));
				
				jsonArray.add(paramJson);
				//logger.info("AnnounceController getList ----> params:" + jsonArray.toJSONString());
				String resultStr = hotActivitiesCXFService.getHotActivitiesList(jsonArray.toJSONString());
				//logger.info("AnnounceController getList ----> return:" + resultStr);
				resultJson = JSON.parseObject(resultStr);
				JSONArray ja = resultJson.getJSONArray("data");
				JSONArray jaResult = new JSONArray();
				
				//新接口已对数据进行排序，无需排序处理
				for(int i = 0; i < ja.size(); i++){
					JSONObject jo = ja.getJSONObject(i);
					
					String picPath = Configuration.getInstance().getValue("image_back_url") + jo.getString("picPath");
					jo.put("picPath", picPath);
					
					jo.put("activityStart", sdf_splitByDot.format(jo.getDate("beginDate")));
					jo.put("activityEnd", sdf_splitByDot.format(jo.getDate("endDate")));

					boolean external = false;
                    String jumpUrl = jo.getString("jumpUrl");
                    if(jumpUrl != null && jumpUrl.indexOf("http") == 0){
						external = true;
	                }
					jo.put("external", external);
					
					//curstatus：活动状态 1：进行中2：预热中3:已结束4：其它状态
					int curstatus = jo.getIntValue("curstatus");
					if (1 == curstatus) {
						jo.put("avtivityStatus1", true); //进行中
					} else if (2 == curstatus) {
						jo.put("avtivityStatus2", true); //预热中
	                } else if (3 == curstatus){
	                	jo.put("avtivityStatus3", true); //已结束
	                }else{
	                	continue; //状态不明确的活动不展示
	                }
					jaResult.add(jo);
				}

				resultJson.put("data", jaResult);
            }
			
		}catch(Exception e){
    		logger.error("AnnounceController getList ----> arise exception:" , e);
    		resultJson.clear();
	    	resultJson.put("resultCode", -1);
	    	resultJson.put("desc", "操作失败，请稍后重试...");
    	}
    	//logger.info("AnnounceController getList ----> return to page:" + resultJson.toJSONString());
		return resultJson.toJSONString();
	}
	
	@RequestMapping(value = "/getDetail", produces = {"application/json;charset=UTF-8"})
	public @ResponseBody String getDetail(HttpServletRequest request, HttpServletResponse response) {
		JSONObject resultJson = new JSONObject();
		try{
			JSONArray jsonArray = new JSONArray();
			JSONObject paramJson = new JSONObject();
			
			paramJson.put("newsNoticesId", request.getParameter("announceId"));
			jsonArray.add(paramJson);
			//logger.info("AnnounceController getDetail ----> params:" + jsonArray.toJSONString());
			String resultStr = newsCXFService.queryNewsNoticesById(jsonArray.toString());
			//logger.info("AnnounceController getDetail ----> return:" + resultStr);
			resultJson = JSON.parseObject(resultStr);
		}catch(Exception e){
			logger.error("AnnounceController getDetail ----> arise exception:" , e);
	    	resultJson.put("resultCode", -1);
	    	resultJson.put("desc", "操作失败，请稍后重试...");
		}
		//logger.info("AnnounceController getDetail ----> return to page:" + resultJson.toJSONString());
		return resultJson.toJSONString();
	}
}
