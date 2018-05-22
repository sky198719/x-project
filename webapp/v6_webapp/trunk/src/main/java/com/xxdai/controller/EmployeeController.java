/**
 * Copyright (c) 2016, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.controller;

import com.alibaba.fastjson.JSONObject;
import com.xxdai.client.CXF_Factory;
import com.xxdai.external.activityUser.ws.ActivityUserCXFService;
import com.xxdai.external.employee.ws.EmployeeCXFService;
import com.xxdai.external.user.ws.User;
import com.xxdai.util.Configuration;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述
 *
 * @version $Id: EmployeeController.java 2016/8/2 18:14 $
 * @since jdk1.6
 */
@Controller
@RequestMapping(value = "/employee")
public class EmployeeController {

    Logger log = Logger.getLogger(EmployeeController.class);
     private EmployeeCXFService employeeCXFService = (EmployeeCXFService) CXF_Factory.getFactory(EmployeeCXFService.class, Configuration.getInstance().getValue("webService_url") + "/employeeWebService").create();

    @RequestMapping(value = "/getEmployee", produces = {"application/json;charset=UTF-8"})
        public
        @ResponseBody
        String getEmployee(@RequestParam(value = "jobNum", required = true) String jobNum,HttpServletRequest request, HttpServletRequest response) {
            JSONObject resultJson = new JSONObject();
            try {
                JSONObject param = new JSONObject();
                param.put("jobNum",jobNum);
                String resp = employeeCXFService.getEmployee(param.toJSONString());
                resultJson.put("resultCode", 0);
                resultJson.put("data", JSONObject.parse(resp));
            } catch (Exception e) {
                log.error("getEmployee arise exception:", e);
                resultJson.put("resultCode", -1);
                resultJson.put("resultDesc", "获取数据异常，请稍后重试");
            }
            return resultJson.toJSONString();
        }

}
