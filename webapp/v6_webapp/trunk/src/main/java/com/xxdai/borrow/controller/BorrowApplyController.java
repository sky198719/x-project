/**
 * Copyright (c) 2014, www.xxdai.com All Rights Reserved. 
 */
package com.xxdai.borrow.controller;

import com.alibaba.fastjson.JSONObject;
import com.xxdai.client.CXF_Factory;
import com.xxdai.core.util.http.WSResponse;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.external.borrowApply.ws.BorrowApplyCXFService;
import com.xxdai.util.*;
import com.xxdai.ws.util.WSModelResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;

/**
 * BorrowApplyController
 *
 * @version $Id: BorrowController.java 9854 2015-01-27 02:50:31Z aiden $
 * @since jdk1.6
 */
@Controller
@RequestMapping(value = "/borrowApply")
public class BorrowApplyController {

    /**
     * 日志记录器
     */
    private static final Logger log = Logger.getLogger(BorrowApplyController.class);
    private BorrowApplyCXFService borrowApplyService = (BorrowApplyCXFService) CXF_Factory.getFactory(BorrowApplyCXFService.class, Configuration.getInstance().getValue("webService_url") + "/borrowApplyWebService").create();

    //保存新业贷用户基础信息（跨域） @author aiden at 2016-10-27 17:54:50
    @RequestMapping(value = "/saveXydBaseInfo", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    String saveXydBaseInfo(HttpServletRequest request, HttpServletResponse response) {
        JSONObject paramsJson = new JSONObject();
        JSONObject resultJson = new JSONObject();
        //安全考虑，session中获取手机号
        String mobileNo = String.valueOf(request.getSession().getAttribute("xydRegistedMobile"));

        if(StringUtils.isBlank(mobileNo) || "null".equals(mobileNo)){
            resultJson.put("resultCode", -4);
            resultJson.put("msg", "登录过期，请重新登录！");
            return TokenUtil.jsonpCallback(request, resultJson.toString());
        }

        paramsJson.put("mobileNo", mobileNo);
        paramsJson.put("name", request.getParameter("name"));
        paramsJson.put("identity", request.getParameter("identity"));
        paramsJson.put("cityId", request.getParameter("cityId"));
        paramsJson.put("areaId", request.getParameter("areaId"));
        paramsJson.put("houseProperty", request.getParameter("houseProperty"));
        paramsJson.put("marriage", request.getParameter("marriage"));
        paramsJson.put("idCode", request.getParameter("idCode"));
        paramsJson.put("houseType", request.getParameter("houseType"));
        paramsJson.put("income", new BigDecimal(request.getParameter("income")));

        log.info(MessageFormat.format("invoke borrowApplyService.saveBorrowApplyBaseInfoXyd,params:{0}", paramsJson.toJSONString()));
        String resultStr = borrowApplyService.saveBorrowApplyBaseInfoXyd(paramsJson.toJSONString());
        log.info(MessageFormat.format("borrowApplyService.saveBorrowApplyBaseInfoXyd,response:{0}", resultStr));

        WSResponse wsResponse = JsonUtil.jsonToBean(resultStr, WSResponse.class);
        int resultCode = wsResponse.getResultCode();
        if (0 == resultCode) {
            resultJson.put("resultCode", 0);
            resultJson.put("msg", "保存成功！");
        } else {
            resultJson.put("resultCode", resultCode);
            resultJson.put("msg", "系统内部错误。");
        }

        return TokenUtil.jsonpCallback(request, resultJson.toString());
    }

    //查询新业贷用户基础信息（跨域） @author aiden at 2016-10-27 17:54:50
    @RequestMapping(value = "/queryXydBaseInfo", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    String queryXydBaseInfo(HttpServletRequest request, HttpServletResponse response) {
        JSONObject paramsJson = new JSONObject();
        JSONObject resultJson = new JSONObject();
        //安全考虑，session中获取手机号
        String mobileNo = String.valueOf(request.getSession().getAttribute("xydRegistedMobile"));
        paramsJson.put("mobileNo", mobileNo);

        if(StringUtils.isBlank(mobileNo)  || "null".equals(mobileNo)){
            resultJson.put("resultCode", -4);
            resultJson.put("msg", "登录过期，请重新登录！");
            return TokenUtil.jsonpCallback(request, resultJson.toString());
        }

        log.info(MessageFormat.format("invoke borrowApplyService.queryBorrowApplyBaseInfoXyd,params:{0}", paramsJson.toJSONString()));
        String resultStr = borrowApplyService.queryBorrowApplyBaseInfoXyd(paramsJson.toJSONString());
        log.info(MessageFormat.format("borrowApplyService.queryBorrowApplyBaseInfoXyd,response:{0}", resultStr));

        WSModelResponse wsModelResponse = JsonUtil.jsonToBean(resultStr, WSModelResponse.class);
        int resultCode = wsModelResponse.getResultCode();
        if (0 == resultCode) {
            resultJson.put("resultCode", 0);
            resultJson.put("data", wsModelResponse.getData());
            resultJson.put("msg", "查询成功！");
        } else {
            resultJson.put("resultCode", resultCode);
            resultJson.put("msg", "系统内部错误。");
        }

        return TokenUtil.jsonpCallback(request, resultJson.toString());
    }

    //保存新业贷贷款信息（跨域） @author aiden at 2016-10-27 17:54:50
    @RequestMapping(value = "/saveXydBorrowInfo", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    String saveXydBorrowInfo(HttpServletRequest request, HttpServletResponse response) {
        JSONObject paramsJson = new JSONObject();
        JSONObject resultJson = new JSONObject();
        //安全考虑，session中获取手机号
        String mobileNo = String.valueOf(request.getSession().getAttribute("xydRegistedMobile"));

        if(StringUtils.isBlank(mobileNo) || "null".equals(mobileNo)){
            resultJson.put("resultCode", -4);
            resultJson.put("msg", "登录过期，请重新登录！");
            return TokenUtil.jsonpCallback(request, resultJson.toString());
        }

        paramsJson.put("mobileNo", mobileNo);
        paramsJson.put("loanAmount", request.getParameter("loanAmount"));
        paramsJson.put("loanTerm", request.getParameter("loanTerm"));
        paramsJson.put("houseAddress", request.getParameter("houseAddress"));
        paramsJson.put("houseElectricityfees", request.getParameter("houseElectricityfees"));
        paramsJson.put("relativesName", request.getParameter("relativesName"));
        paramsJson.put("relativesMobile", request.getParameter("relativesMobile"));
        paramsJson.put("colleagueName", request.getParameter("colleagueName"));
        paramsJson.put("colleagueMobile", request.getParameter("colleagueMobile"));
        paramsJson.put("companyAddress", request.getParameter("companyAddress"));
        paramsJson.put("managementAddress", request.getParameter("managementAddress"));
        paramsJson.put("managementElectricityfees", request.getParameter("managementElectricityfees"));

        log.info(MessageFormat.format("invoke borrowApplyService.saveBorrowApplyBorrowInfoXyd,params:{0}", paramsJson.toJSONString()));
        String resultStr = borrowApplyService.saveBorrowApplyBorrowInfoXyd(paramsJson.toJSONString());
        log.info(MessageFormat.format("borrowApplyService.saveBorrowApplyBorrowInfoXyd,response:{0}", resultStr));

        WSResponse wsResponse = JsonUtil.jsonToBean(resultStr, WSResponse.class);
        int resultCode = wsResponse.getResultCode();
        if (0 == resultCode) {
            resultJson.put("resultCode", 0);
            resultJson.put("msg", "保存成功！");
        } else {
            resultJson.put("resultCode", resultCode);
            resultJson.put("msg", "系统内部错误。");
        }

        return TokenUtil.jsonpCallback(request, resultJson.toString());
    }

    //查询新业贷贷款信息（跨域） @author aiden at 2016-10-27 17:54:50
    @RequestMapping(value = "/queryXydBorrowInfo", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    String queryXydBorrowInfo(HttpServletRequest request, HttpServletResponse response) {
        JSONObject paramsJson = new JSONObject();
        JSONObject resultJson = new JSONObject();
        //安全考虑，session中获取手机号
        String mobileNo = String.valueOf(request.getSession().getAttribute("xydRegistedMobile"));

        if(StringUtils.isBlank(mobileNo) || "null".equals(mobileNo)){
            resultJson.put("resultCode", -4);
            resultJson.put("msg", "登录过期，请重新登录！");
            return TokenUtil.jsonpCallback(request, resultJson.toString());
        }

        paramsJson.put("mobileNo", mobileNo);

        log.info(MessageFormat.format("invoke borrowApplyService.queryBorrowApplyBorrowInfoXyd,params:{0}", paramsJson.toJSONString()));
        String resultStr = borrowApplyService.queryBorrowApplyBorrowInfoXyd(paramsJson.toJSONString());
        log.info(MessageFormat.format("borrowApplyService.queryBorrowApplyBorrowInfoXyd,response:{0}", resultStr));

        WSModelResponse wsModelResponse = JsonUtil.jsonToBean(resultStr, WSModelResponse.class);
        int resultCode = wsModelResponse.getResultCode();
        if (0 == resultCode) {
            resultJson.put("resultCode", 0);
            resultJson.put("data", wsModelResponse.getData());
            resultJson.put("msg", "查询成功！");
        } else {
            resultJson.put("resultCode", resultCode);
            resultJson.put("msg", "系统内部错误。");
        }

        return TokenUtil.jsonpCallback(request, resultJson.toString());
    }

    //上传新业贷贷款图片 @author aiden at 2016-10-31 11:52:32
    @RequestMapping(value = "/uploadPicXyd", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    String uploadPicXyd(HttpServletRequest request,HttpServletResponse response) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("resultCode", -5);
        resultJson.put("msg", "系统内部错误！");

        //安全考虑，session中获取手机号
        String mobileNo = String.valueOf(request.getSession().getAttribute("xydRegistedMobile"));
        String picType = request.getParameter("picType");

        if(StringUtils.isBlank(mobileNo) || "null".equals(mobileNo)){
            resultJson.put("resultCode", -4);
            resultJson.put("msg", "登录过期，请重新登录！");
            return TokenUtil.jsonpCallback(request, resultJson.toString());
        }

        if(StringUtils.isBlank(picType)){
            resultJson.put("resultCode", -3);
            resultJson.put("msg", "参数错误！");
            return TokenUtil.jsonpCallback(request, resultJson.toString());
        }

        //拼接上传文件的文件名
        String newFileName = this.getPicTypeName(picType) + "_" + mobileNo;
        try {
            //上传文件
            JSONObject upLoadJson = new UpLoadUtil().uploadFile(request, newFileName);
            if(upLoadJson.getIntValue("resultCode") == 0){
                List<String> pathList = (List<String>)upLoadJson.get("data");
                if(pathList != null && pathList.size() >0 ){
                    String fileName = pathList.get(0);
                    //压缩图片&上传
                    JSONObject imageJson = new ImageUtil().scaleImage(request, fileName);
                    if(imageJson.getIntValue("resultCode") == 0){
                        String originPath = "/static/image/uploadFile" + System.getProperty("file.separator") + fileName;
                        String scaledPath = "/static/image/uploadFile" + System.getProperty("file.separator") + imageJson.get("data");
                        //保存detail记录(压缩前后的图片)
                        Map<String, String> map = new HashMap<String, String>();
                        map.put(picType, originPath);
                        map.put(picType+"Scaled", scaledPath);
                        Iterator<String> setIter =  map.keySet().iterator();
                        while(setIter.hasNext()){
                            String key = setIter.next();
                            String value = map.get(key);
                            JSONObject paramsJson = new JSONObject();
                            paramsJson.put("mobileNo", mobileNo);
                            paramsJson.put("picType", key);
                            paramsJson.put("keyValue", value);
                            borrowApplyService.saveUploadPicPathXyd(paramsJson.toJSONString());
                        }

                        resultJson.put("resultCode", 0);
                        resultJson.put("msg", "图片上传成功！");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.info(MessageFormat.format("新业贷图片（压缩前后）上传失败！, mobileNo:{0}", mobileNo));
        }

        return resultJson.toJSONString();
    }

    //查询上传图片路径 @author aiden at 2016-10-29 10:56:23
    @RequestMapping(value = "/queryUploadPicPathXyd", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    String queryUploadPicPathXyd(HttpServletRequest request, HttpServletResponse response) {
        JSONObject paramsJson = new JSONObject();
        JSONObject resultJson = new JSONObject();
        //安全考虑，session中获取手机号
        String mobileNo = String.valueOf(request.getSession().getAttribute("xydRegistedMobile"));
        String picType = request.getParameter("picType");

        if(StringUtils.isBlank(mobileNo) || "null".equals(mobileNo)){
            resultJson.put("resultCode", -4);
            resultJson.put("msg", "登录过期，请重新登录！");
            return TokenUtil.jsonpCallback(request, resultJson.toString());
        }

        if(StringUtils.isBlank(picType)){
            resultJson.put("resultCode", -3);
            resultJson.put("msg", "参数错误！");
            return TokenUtil.jsonpCallback(request, resultJson.toString());
        }

        paramsJson.put("mobileNo", mobileNo);
        paramsJson.put("picType", picType);

        log.info(MessageFormat.format("invoke borrowApplyService.queryUploadPicPathXyd,params:{0}", paramsJson.toJSONString()));
        String resultStr = borrowApplyService.queryUploadPicPathXyd(paramsJson.toJSONString());
        log.info(MessageFormat.format("borrowApplyService.queryUploadPicPathXyd,response:{0}", resultStr));

        WSModelResponse wsModelResponse = JsonUtil.jsonToBean(resultStr, WSModelResponse.class);
        int resultCode = wsModelResponse.getResultCode();
        if (0 == resultCode) {
            resultJson.put("resultCode", 0);
            resultJson.put("data", Configuration.getInstance().getValue("front_url") +  System.getProperty("file.separator") + ((JSONObject)wsModelResponse.getData()).getString(picType)); //全路径
            resultJson.put("msg", "查询成功！");
        } else {
            resultJson.put("resultCode", resultCode);
            resultJson.put("msg", "系统内部错误。");
        }

        return TokenUtil.jsonpCallback(request, resultJson.toString());
    }

    //查询上传图片路径 @author aiden at 2016-10-29 10:56:23
    @RequestMapping(value = "/queryAllUploadPicPathXyd", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    String queryAllUploadPicPathXyd(HttpServletRequest request, HttpServletResponse response) {
        JSONObject paramsJson = new JSONObject();
        JSONObject resultJson = new JSONObject();
        //安全考虑，session中获取手机号
        String mobileNo = String.valueOf(request.getSession().getAttribute("xydRegistedMobile"));

        if(StringUtils.isBlank(mobileNo) || "null".equals(mobileNo)){
            resultJson.put("resultCode", -4);
            resultJson.put("msg", "登录过期，请重新登录！");
            return TokenUtil.jsonpCallback(request, resultJson.toString());
        }

        paramsJson.put("mobileNo", mobileNo);
        paramsJson.put("notScaled", false);

        log.info(MessageFormat.format("invoke borrowApplyService.queryAllUploadPicPathXyd,params:{0}", paramsJson.toJSONString()));
        String resultStr = borrowApplyService.queryAllUploadPicPathXyd(paramsJson.toJSONString());
        log.info(MessageFormat.format("borrowApplyService.queryAllUploadPicPathXyd,response:{0}", resultStr));

        WSModelResponse wsModelResponse = JsonUtil.jsonToBean(resultStr, WSModelResponse.class);
        int resultCode = wsModelResponse.getResultCode();
        if (0 == resultCode) {
            resultJson.put("resultCode", 0);
            //根据身份&房屋类型，返回数据
            JSONObject catedJson = this.catResultJson((JSONObject) wsModelResponse.getData());
            resultJson.put("data", catedJson);
            resultJson.put("msg", "查询成功！");
        } else {
            resultJson.put("resultCode", resultCode);
            resultJson.put("msg", "系统内部错误。");
        }

        return TokenUtil.jsonpCallback(request, resultJson.toString());
    }

    //根据身份&房屋类型，拼接返回数据
    private JSONObject catResultJson(JSONObject jsonObject){
        JSONObject resultJson = new JSONObject();
        int identity = jsonObject.getIntValue("identity");
        int houseType = jsonObject.getIntValue("houseType");

        //身份证图片
        List<String> idcardUrlList = new ArrayList<String>();
        idcardUrlList.add(this.dealWithBlank(jsonObject.getString("idcardFrontUrlScaled")));
        idcardUrlList.add(this.dealWithBlank(jsonObject.getString("idcardBackUrlScaled")));
        idcardUrlList.add(this.dealWithBlank(jsonObject.getString("idcardInHandUrlScaled")));
        resultJson.put("idcardUrl", idcardUrlList);

        //征信图片
        List<String> creditUrlList = new ArrayList<String>();
        creditUrlList.add(this.dealWithBlank(jsonObject.getString("creditUrl1Scaled")));
        creditUrlList.add(this.dealWithBlank(jsonObject.getString("creditUrl2Scaled")));
        creditUrlList.add(this.dealWithBlank(jsonObject.getString("creditUrl3Scaled")));
        creditUrlList.add(this.dealWithBlank(jsonObject.getString("creditUrl4Scaled")));
        creditUrlList.add(this.dealWithBlank(jsonObject.getString("creditUrl5Scaled")));
        creditUrlList.add(this.dealWithBlank(jsonObject.getString("creditUrl6Scaled")));
        resultJson.put("creditUrl", creditUrlList);

        if(identity == 1){//工薪
            if(houseType == 1){//清房
                List<String> propertyUrlList = new ArrayList<String>();
                propertyUrlList.add(this.dealWithBlank(jsonObject.getString("propertyUrl1Scaled")));
                propertyUrlList.add(this.dealWithBlank(jsonObject.getString("propertyUrl2Scaled")));
                propertyUrlList.add(this.dealWithBlank(jsonObject.getString("propertyUrl3Scaled")));
                resultJson.put("propertyUrl", propertyUrlList);
            }else if(houseType == 2){//按揭房
                List<String> loanUrlList = new ArrayList<String>();
                loanUrlList.add(this.dealWithBlank(jsonObject.getString("loanUrl1Scaled")));
                loanUrlList.add(this.dealWithBlank(jsonObject.getString("loanUrl2Scaled")));
                loanUrlList.add(this.dealWithBlank(jsonObject.getString("loanUrl3Scaled")));
                resultJson.put("loanUrl", loanUrlList);
            }else if(houseType == 3){//抵押给银行
                List<String> mortgageUrlList = new ArrayList<String>();
                mortgageUrlList.add(this.dealWithBlank(jsonObject.getString("mortgageUrl1Scaled")));
                mortgageUrlList.add(this.dealWithBlank(jsonObject.getString("mortgageUrl2Scaled")));
                mortgageUrlList.add(this.dealWithBlank(jsonObject.getString("mortgageUrl3Scaled")));
                resultJson.put("mortgageUrl", mortgageUrlList);
            }else{}

        }else if(identity == 2){//法人
            List<String> licenseUrlList = new ArrayList<String>();
            licenseUrlList.add(this.dealWithBlank(jsonObject.getString("licenseUrl1Scaled")));
            licenseUrlList.add(this.dealWithBlank(jsonObject.getString("rentUrl1Scaled")));
            licenseUrlList.add(this.dealWithBlank(jsonObject.getString("rentUrl2Scaled")));
            resultJson.put("licenseUrl", licenseUrlList);

            List<String> managementUrlList = new ArrayList<String>();
            managementUrlList.add(this.dealWithBlank(jsonObject.getString("houseNumUrlScaled")));
            managementUrlList.add(this.dealWithBlank(jsonObject.getString("companyLogoUrlScaled")));
            managementUrlList.add(this.dealWithBlank(jsonObject.getString("operateProdUrlScaled")));
            managementUrlList.add(this.dealWithBlank(jsonObject.getString("workPlaceUrlScaled")));
            resultJson.put("managementUrl", managementUrlList);

            if(houseType == 1){//清房
                List<String> propertyUrlList = new ArrayList<String>();
                propertyUrlList.add(this.dealWithBlank(jsonObject.getString("propertyUrl1Scaled")));
                propertyUrlList.add(this.dealWithBlank(jsonObject.getString("propertyUrl2Scaled")));
                propertyUrlList.add(this.dealWithBlank(jsonObject.getString("propertyUrl3Scaled")));
                resultJson.put("propertyUrl", propertyUrlList);
            }else if(houseType == 2){//按揭房
                List<String> loanUrlList = new ArrayList<String>();
                loanUrlList.add(this.dealWithBlank(jsonObject.getString("loanUrl1Scaled")));
                loanUrlList.add(this.dealWithBlank(jsonObject.getString("loanUrl2Scaled")));
                loanUrlList.add(this.dealWithBlank(jsonObject.getString("loanUrl3Scaled")));
                resultJson.put("loanUrl", loanUrlList);
            }else if(houseType == 3){//抵押给银行
                List<String> mortgageUrlList = new ArrayList<String>();
                mortgageUrlList.add(this.dealWithBlank(jsonObject.getString("mortgageUrl1Scaled")));
                mortgageUrlList.add(this.dealWithBlank(jsonObject.getString("mortgageUrl2Scaled")));
                mortgageUrlList.add(this.dealWithBlank(jsonObject.getString("mortgageUrl3Scaled")));
                resultJson.put("mortgageUrl", mortgageUrlList);
            }else{}

        }else{}

        return resultJson;
    }

    private String dealWithBlank(String str){
        return StringUtils.isNotBlank(str) ? Configuration.getInstance().getValue("front_url") +  System.getProperty("file.separator") + str : "absent";
    }

    /*    private String getPicTypeName(String picType){
        if("idcardFrontUrl".equals(picType)){
            return "身份证正面";
        }else if("idcardBackUrl".equals(picType)){
            return "身份证反面";
        }else if("idcardInHandUrl".equals(picType)){
            return "手持身份证";
        }else if("propertyUrl1".equals(picType)){
            return "房产证原件1";
        }else if("propertyUrl2".equals(picType)){
            return "房产证原件2";
        }else if("propertyUrl3".equals(picType)){
            return "房产证原件3";
        }else if("creditUrl1".equals(picType)){
            return "征信信息1";
        }else if("creditUrl2".equals(picType)){
            return "征信信息2";
        }else if("creditUrl3".equals(picType)){
            return "征信信息3";
        }else if("creditUrl4".equals(picType)){
            return "征信信息4";
        }else if("creditUrl5".equals(picType)){
            return "征信信息5";
        }else if("creditUrl6".equals(picType)){
            return "征信信息6";
        }else if("loanUrl1".equals(picType)){
            return "借款合同1";
        }else if("loanUrl2".equals(picType)){
            return "借款合同2";
        }else if("loanUrl3".equals(picType)){
            return "借款合同3";
        }else if("mortgageUrl1".equals(picType)){
            return "抵押合同1";
        }else if("mortgageUrl2".equals(picType)){
            return "抵押合同2";
        }else if("mortgageUrl3".equals(picType)){
            return "抵押合同3";
        }else if("licenseUrl1".equals(picType)){
            return "营业执照";
        }else if("rentUrl1".equals(picType)){
            return "租赁合同1";
        }else if("rentUrl2".equals(picType)){
            return "租赁合同2";
        }else if("houseNumUrl".equals(picType)){
            return "门牌号";
        }else if("companyLogoUrl".equals(picType)){
            return "公司logo";
        }else if("operateProdUrl".equals(picType)){
            return "经营产品";
        }else if("workPlaceUrl".equals(picType)){
            return "办公场地";
        }else{}
        return null;
    }*/

    private String getPicTypeName(String picType){
        if("idcardFrontUrl".equals(picType)){
            return "shenfenzheng_zhengmian";
        }else if("idcardBackUrl".equals(picType)){
            return "shenfenzheng_fanmian";
        }else if("idcardInHandUrl".equals(picType)){
            return "shenfenzheng_shouchi";
        }else if("propertyUrl1".equals(picType)){
            return "fangchanzhengyuanjian1";
        }else if("propertyUrl2".equals(picType)){
            return "fangchanzhengyuanjian2";
        }else if("propertyUrl3".equals(picType)){
            return "fangchanzhengyuanjian3";
        }else if("creditUrl1".equals(picType)){
            return "zhengxinxinxi1";
        }else if("creditUrl2".equals(picType)){
            return "zhengxinxinxi2";
        }else if("creditUrl3".equals(picType)){
            return "zhengxinxinxi3";
        }else if("creditUrl4".equals(picType)){
            return "zhengxinxinxi4";
        }else if("creditUrl5".equals(picType)){
            return "zhengxinxinxi5";
        }else if("creditUrl6".equals(picType)){
            return "zhengxinxinxi6";
        }else if("loanUrl1".equals(picType)){
            return "jiekuanhetong1";
        }else if("loanUrl2".equals(picType)){
            return "jiekuanhetong2";
        }else if("loanUrl3".equals(picType)){
            return "jiekuanhetong3";
        }else if("mortgageUrl1".equals(picType)){
            return "diyahetong1";
        }else if("mortgageUrl2".equals(picType)){
            return "diyahetong2";
        }else if("mortgageUrl3".equals(picType)){
            return "diyahetong3";
        }else if("licenseUrl1".equals(picType)){
            return "yingyezhizhao";
        }else if("rentUrl1".equals(picType)){
            return "zulinhetong1";
        }else if("rentUrl2".equals(picType)){
            return "zulinhetong2";
        }else if("houseNumUrl".equals(picType)){
            return "menpaihao";
        }else if("companyLogoUrl".equals(picType)){
            return "gongsi_logo";
        }else if("operateProdUrl".equals(picType)){
            return "jingyingchanpin";
        }else if("workPlaceUrl".equals(picType)){
            return "bangongchangdi";
        }else{}
        return null;
    }

    //查询渠道配置表信息
    @RequestMapping(value = "/queryChannel", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String queryChannel(HttpServletRequest request, HttpServletResponse response) {
        JSONObject resultJson = new JSONObject();
        String resultStr = borrowApplyService.getChannels();
        log.info(MessageFormat.format("borrowApplyService.queryChannel,response:{0}", resultStr));

        WSModelResponse wsModelResponse = JsonUtil.jsonToBean(resultStr, WSModelResponse.class);
        int resultCode = wsModelResponse.getResultCode();
        if (0 == resultCode) {
            resultJson.put("resultCode", 0);
            resultJson.put("data", wsModelResponse.getData());
            resultJson.put("msg", "查询成功！");
        } else {
            resultJson.put("resultCode", resultCode);
            resultJson.put("msg", "系统内部错误。");
        }

        return TokenUtil.jsonpCallback(request, resultJson.toString());
    }

}
