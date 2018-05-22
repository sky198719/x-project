package com.xxdai.hotActivities.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxdai.core.util.lang.DateUtil;
import com.xxdai.hotActivities.model.HotActivities;
import com.xxdai.hotActivities.model.SemResponse;
import com.xxdai.client.CXF_Factory;
import com.xxdai.constant.AppConsts;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.external.hotActivities.ws.HotActivitiesCXFService;
import com.xxdai.util.Configuration;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by chaihuangqi on 2015/5/20.
 */
@Controller
@RequestMapping(value = "/hotActivities")
public class HotActivitiesController {

    /**
     * 日志记录器
     */
    private static final Logger log = Logger.getLogger(HotActivitiesController.class);
    private HotActivitiesCXFService hotActivitiesCXFService = (HotActivitiesCXFService) CXF_Factory.getFactory(HotActivitiesCXFService.class,
            Configuration.getInstance().getValue("webService_url") + "/hotActivitiesWebService").create();

    /**
     * WebApp showHotActivities
     * activity列表集合
     */
    @RequestMapping(value = "/search/list", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String showHotActivities(HttpServletRequest request) {
        log.info("enter showHotActivities in HotActivitiesController .");
        JSONObject result = new JSONObject();
        //接收页面参数
        String currentPage = request.getParameter("currentPage");
        String pageSize = request.getParameter("pageSize");

        try {
            //设置查询状态数组,将数据封装成json,传入webservice进行查询
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("currentPage", currentPage == null || currentPage.equals("") ? 1 : Integer.valueOf(currentPage));
            jsonObject.put("pageSize", pageSize == null || pageSize.equals("") ? 10 : Integer.valueOf(pageSize));
            jsonObject.put("pageIndex", currentPage == null || currentPage.equals("") ? 1 : Integer.valueOf(currentPage));

            jsonObject.put("topic", "");
            jsonObject.put("platform", "1");
            jsonObject.put("status", "1");

            jsonArray.add(jsonObject);

            //查询信息
            log.info("select activity request param：" + jsonObject.toJSONString());
            String jsonStr = hotActivitiesCXFService.getHotActivitiesList(jsonArray.toString());
            SemResponse semResponse = JsonUtil.jsonToBean(jsonStr, SemResponse.class);
            List<HotActivities> hotActivitiesList = new ArrayList<HotActivities>();
            if(semResponse.getData()!=null) {
                hotActivitiesList = JsonUtil.jsonToList(semResponse.getData().toString(), HotActivities.class);
            }
            result.put("currentDate", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            result.put("hotActivitiesList", hotActivitiesList);
            result.put("pageSize", semResponse.getPageSize());
            result.put("totalSize", semResponse.getTotalSize());
            result.put("pageIndex", semResponse.getPageIndex());
            result.put("totalPages", semResponse.getTotalPages());
            result.put("resultCode", 0);
            result.put("resultCode", AppConsts.WS_RETURN_SUCC);
            result.put("msg", "获取热门活动成功");
        } catch (Exception e) {
            log.error("获取热门活动失败", e);
            result.put("resultCode", 200);
            result.put("msg", "获取热门活动失败");
        }
        result.put("imageUrl",Configuration.getInstance().getValue("image_back_url"));
        return result.toJSONString();
    }
}

