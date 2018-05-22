/**
 * Copyright (c) 2015, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.controller;

import com.alibaba.fastjson.JSONObject;
import com.xxdai.client.CXF_Factory;
import com.xxdai.constant.AppConsts;
import com.xxdai.constant.Constant;
import com.xxdai.core.util.http.WSResponse;
import com.xxdai.core.util.lang.StringUtil;
import com.xxdai.external.dic.area.ws.AreaQueryCXFService;
import com.xxdai.util.Configuration;
import com.xxdai.util.RandCodeUtils;
import com.xxdai.util.TokenUtil;
import com.xxdai.ws.util.WSModelResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.util.List;


@Controller
@RequestMapping(value = "/dic")
public class DicController {

    protected AreaQueryCXFService areaQueryCXFService = (AreaQueryCXFService) CXF_Factory.getFactory(AreaQueryCXFService.class, Configuration.getInstance().getValue("webService_url") + "/areaQueryWebService").create();

    private static final Log log = LogFactory.getLog(DicController.class);

    //查询所有省 @author aiden at 2016-10-29 15:25:37
    @RequestMapping(value = "/getAllSuppProvinces", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    String getAllSupportProvince(HttpServletRequest request) {
        JSONObject resultJson = new JSONObject();
        JSONObject paramsJson = new JSONObject();
        paramsJson.put("pcode", Constant.TOP_NODE_CODE_PRO_CITY_DIC);
        paramsJson.put("type", Constant.XINYEDAI_DIC_SPECIALAREA_CODE);

        String resString = areaQueryCXFService.queryTypeAllChildrenAreaByPcode(paramsJson.toJSONString());
        if (resString != null && StringUtil.isNotEmpty(resString)) {
            WSModelResponse wSModelResponse = JSONObject.parseObject(resString, WSModelResponse.class);
            if (wSModelResponse != null && wSModelResponse.getResultCode() == 0 && wSModelResponse.getData() != null) {
                resultJson.put("resultCode", 0);
                resultJson.put("msg", "操作成功！");
                resultJson.put("data", wSModelResponse.getData());
                return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
            }
        }

        resultJson.put("resultCode", -1);
        resultJson.put("msg", "操作异常！");
        return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
    }

    //查询pcode所有直接子节点 @author aiden at 2016-10-29 15:25:37
    @RequestMapping(value = "/getAllAreaByPcode", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    String getAllAreaByPcode(@RequestParam(value = "pcode", required = true) String pcode, HttpServletRequest request) {
        JSONObject resultJson = new JSONObject();
        JSONObject paramsJson = new JSONObject();
        paramsJson.put("pcode", pcode);
        paramsJson.put("type", Constant.XINYEDAI_DIC_SPECIALAREA_CODE);

        String resString = areaQueryCXFService.queryTypeAllChildrenAreaByPcode(paramsJson.toJSONString());
        if (resString != null && StringUtil.isNotEmpty(resString)) {
            WSModelResponse wSModelResponse = JSONObject.parseObject(resString, WSModelResponse.class);
            if (wSModelResponse != null && wSModelResponse.getResultCode() == 0 && wSModelResponse.getData() != null) {
                resultJson.put("resultCode", 0);
                resultJson.put("msg", "操作成功！");
                resultJson.put("data", wSModelResponse.getData());
                return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
            }
        }

        resultJson.put("resultCode", -1);
        resultJson.put("msg", "操作异常！");
        return TokenUtil.jsonpCallback(request, resultJson.toJSONString());
    }

}
