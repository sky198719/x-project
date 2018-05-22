package com.xxdai.product.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxdai.constant.Constant;
import com.xxdai.core.util.lang.DateUtil;
import com.xxdai.external.user.ws.User;
import com.xxdai.http.Message;
import com.xxdai.product.service.ProductService;
import com.xxdai.product.util.StepUtils;
import com.xxdai.user.service.UserService;
import com.xxdai.util.Configuration;
import com.xxdai.util.HttpTookit;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;

@Controller
@RequestMapping(value = "/product")
public class ProductController {
    private Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    /**
     * 查询产品详情
     * @param request
     * @return
     */
    @RequestMapping(value = "/getProduct", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    String getProduct(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            String pId = request.getParameter("pId");
            if(pId == null || "".equalsIgnoreCase(pId)) {
                result.put("code",400);
                result.put("message","pId为空");
                return result.toJSONString();
            }

            String pCode = request.getParameter("pCode");
            if(pCode == null || "".equalsIgnoreCase(pCode)) {
                result.put("code",401);
                result.put("message","pCode为空");
                return result.toJSONString();
            }

            Object user = request.getSession().getAttribute("loginUser");
            String userId = null;
            if(user != null) {
                userId = String.valueOf(((User)user).getUserId());
            }

            Message msg = productService.getProduct(pCode,pId,userId);
            if(msg != null) {
                result = JSONObject.parseObject(JSONObject.toJSONString(msg));
                result.put("serverTime", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));

            } else {
                result.put("code",404);
                result.put("message","查询不到");
            }
        }catch (Exception e) {
            log.error("getBBGS error",e);
            result.put("code",500);
            result.put("message","未知异常");
        }finally {
            return result.toJSONString();
        }
    }

    private JSONObject setStepJoin(JSONObject stepJoin){
        int day = StepUtils.diffDays(stepJoin.getDate("addDate"), new Date());
        BigDecimal currentApr = StepUtils.calcCurrentApr(stepJoin.getBigDecimal("minApr"), stepJoin.getBigDecimal("stepApr"), stepJoin.getBigDecimal("maxApr"), day);
        BigDecimal interest = StepUtils.calcInterest(stepJoin.getBigDecimal("remaCapital"), day, currentApr);
        stepJoin.put("currentApr",currentApr);
        stepJoin.put("day",day);

        if(stepJoin.getDate("outDate") != null) {
            int outDay =  StepUtils.diffDays(stepJoin.getDate("addDate"), stepJoin.getDate("outDate"));
            stepJoin.put("outDay",outDay);
        }

        stepJoin.put("collectInterest",interest);
        stepJoin.put("isInLocked",StepUtils.isInLocked(day));
        return stepJoin;
    }

    /**
     * 查询产品详情
     * @param request
     * @return
     */
    @RequestMapping(value = "/getWebappProduct", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    String getWebappProduct(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            String pCode = request.getParameter("pCode");
            if(pCode == null || "".equalsIgnoreCase(pCode)) {
                result.put("code",401);
                result.put("message","pCode为空");
                return result.toJSONString();
            }

            String cp = request.getParameter("currentPage");
            int currentPage = cp == null ? 1 : Integer.parseInt(cp);
            String ps = request.getParameter("pageSize");
            int pageSize = ps == null ? 2 : Integer.parseInt(ps);
            String token = HttpTookit.getCookieValue(request,Constant.USERTOKEN);
            Message msg = productService.getWebappProduct(token,HttpTookit.getUserAgent(request),pCode,currentPage,pageSize);
            if(msg != null) {
                result = JSONObject.parseObject(JSONObject.toJSONString(msg));
                result.put("serverTime", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));

            } else {
                result.put("code",404);
                result.put("message","查询不到");
            }
        }catch (Exception e) {
            log.error("getWebappProduct error",e);
            result.put("code",500);
            result.put("message","未知异常");
        }finally {
            return result.toJSONString();
        }
    }

    @RequestMapping(value = "/getNewProduct", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    String getNewProduct(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            String pCode = request.getParameter("pCode");
            if(pCode == null || "".equalsIgnoreCase(pCode)) {
                result.put("code",401);
                result.put("message","pCode为空");
                return result.toJSONString();
            }

            String cp = request.getParameter("currentPage");
            int currentPage = cp == null ? 1 : Integer.parseInt(cp);
            String ps = request.getParameter("pageSize");
            int pageSize = ps == null ? 2 : Integer.parseInt(ps);

            Message msg = productService.getNewProduct(pCode,currentPage,pageSize);
            if(msg != null) {
                result = JSONObject.parseObject(JSONObject.toJSONString(msg));
                result.put("serverTime", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));

            } else {
                result.put("code",404);
                result.put("message","查询不到");
            }
        }catch (Exception e) {
            log.error("getWebappProduct error",e);
            result.put("code",500);
            result.put("message","未知异常");
        }finally {
            return result.toJSONString();
        }
    }

    @RequestMapping(value = "/productDetail", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    String productDetail(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            String pCode = request.getParameter("pCode");
            if(pCode == null || "".equalsIgnoreCase(pCode)) {
                result.put("code",401);
                result.put("message","pCode为空");
                return result.toJSONString();
            }

            String pid = request.getParameter("pid");
            if(pCode == null || "".equalsIgnoreCase(pCode)) {
                result.put("code",402);
                result.put("message","pid为空");
                return result.toJSONString();
            }

            Message msg = productService.productDetail(pCode,pid);
            if(msg != null) {
                result = JSONObject.parseObject(JSONObject.toJSONString(msg));
                result.put("serverTime", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));

            } else {
                result.put("code",404);
                result.put("message","查询不到");
            }
        }catch (Exception e) {
            log.error("productDetail error",e);
            result.put("code",500);
            result.put("message","未知异常");
        }finally {
            return result.toJSONString();
        }
    }


    @RequestMapping(value = "/loanRelationshipRecord", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String loanRelationshipRecord(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            String pId = request.getParameter("pId");
            if(pId == null || "".equalsIgnoreCase(pId)) {
                result.put("code",400);
                result.put("message","pId为空");
                return result.toJSONString();
            }

            String cpStr = request.getParameter("currentPage");
            int currentPage = cpStr == null ? 1 : Integer.parseInt(cpStr);
            String psStr = request.getParameter("pageSize");
            int pageSize = psStr == null ? 20 : Integer.parseInt(psStr);
            Message msg = productService.loanRelationshipRecord(pId,currentPage,pageSize);
            if(msg != null) {
                result = JSONObject.parseObject(JSONObject.toJSONString(msg));
            } else {
                result.put("code",404);
                result.put("message","查询不到");
            }
        }catch (Exception e) {
            log.error("loanRelationshipRecord error",e);
            result.put("code",500);
            result.put("message","未知异常");
        }finally {
            return result.toJSONString();
        }
    }


    @RequestMapping(value = "/bondsList", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String bondsList(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            String joinid = request.getParameter("joinId");
            if(joinid == null || "".equalsIgnoreCase(joinid)) {
                result.put("code",400);
                result.put("message","joinid为空");
                return result.toJSONString();
            }

            String cpStr = request.getParameter("currentPage");
            int currentPage = cpStr == null ? 1 : Integer.parseInt(cpStr);
            String psStr = request.getParameter("pageSize");
            int pageSize = psStr == null ? 20 : Integer.parseInt(psStr);
            Message msg = productService.bondsList(joinid,currentPage,pageSize);
            if(msg != null) {
                result = JSONObject.parseObject(JSONObject.toJSONString(msg));
            } else {
                result.put("code",404);
                result.put("message","查询不到");
            }
            result.put("activity_url",Configuration.getInstance().getValue("activity_url"));
        }catch (Exception e) {
            log.error("bondsList error",e);
            result.put("code",500);
            result.put("message","未知异常");
        }finally {
            return result.toJSONString();
        }
    }

    @RequestMapping(value = "/queryFinanceBorrowList", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String queryFinanceBorrowList(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            String cpStr = request.getParameter("currentPage");
            int currentPage = cpStr == null ? 1 : Integer.parseInt(cpStr);
            String psStr = request.getParameter("pageSize");
            int pageSize = psStr == null ? 20 : Integer.parseInt(psStr);
            Message msg = productService.queryFinanceBorrowList(currentPage,pageSize);
            if(msg != null) {
                result = JSONObject.parseObject(JSONObject.toJSONString(msg));
            } else {
                result.put("code",404);
                result.put("msg","查询不到");
            }
        }catch (Exception e) {
            log.error("queryFinanceBorrowList error",e);
            result.put("code",500);
            result.put("message","未知异常");
        }finally {
            return result.toJSONString();
        }
    }

    @RequestMapping(value = "/userLoanRelationshipRecord", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String userLoanRelationshipRecord(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            String joinId = request.getParameter("joinId");
            if(joinId == null || "".equalsIgnoreCase(joinId)) {
                result.put("code",400);
                result.put("message","joinId为空");
                return result.toJSONString();
            }
            String cpStr = request.getParameter("currentPage");
            int currentPage = cpStr == null ? 1 : Integer.parseInt(cpStr);
            String psStr = request.getParameter("pageSize");
            int pageSize = psStr == null ? 20 : Integer.parseInt(psStr);
            String token = HttpTookit.getCookieValue(request,Constant.USERTOKEN);
            String userAgent = HttpTookit.getUserAgent(request);
            Message msg = productService.userLoanRelationshipRecord(token,userAgent,joinId,currentPage,pageSize);
            if(msg != null) {
                result = JSONObject.parseObject(JSONObject.toJSONString(msg));
            } else {
                result.put("code",404);
                result.put("message","查询不到");
            }
            result.put("activity_url",Configuration.getInstance().getValue("activity_url"));
        }catch (Exception e) {
            log.error("userLoanRelationshipRecord error",e);
            result.put("code",500);
            result.put("message","未知异常");
        }finally {
            return result.toJSONString();
        }
    }


    /**
     * 查询产品加入记录
     * @param request
     * @return
     */
    @RequestMapping(value = "/investOrderList", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String investOrderList(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            String pId = request.getParameter("pId");
            if(pId == null || "".equalsIgnoreCase(pId)) {
                result.put("code",400);
                result.put("message","pId为空");
                return result.toJSONString();
            }

            String cpStr = request.getParameter("currentPage");
            int currentPage = cpStr == null ? 1 : Integer.parseInt(cpStr);
            String psStr = request.getParameter("pageSize");
            int pageSize = psStr == null ? 20 : Integer.parseInt(psStr);
            Message msg = productService.investOrderList(pId,currentPage,pageSize);
            if(msg != null) {
                result = JSONObject.parseObject(JSONObject.toJSONString(msg));
            } else {
                result.put("code",404);
                result.put("message","查询不到");
            }
        }catch (Exception e) {
            log.error("investOrderList error",e);
            result.put("code",500);
            result.put("message","未知异常");
        }finally {
            return result.toJSONString();
        }
    }

    @RequestMapping(value = "/productJoinRecords",produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String productJoinRecords(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            String pId = request.getParameter("pId");
            if(pId == null || "".equalsIgnoreCase(pId)) {
                result.put("code",400);
                result.put("message","pId为空");
                return result.toJSONString();
            }

            String pCode = request.getParameter("pCode");
            if(pCode == null || "".equalsIgnoreCase(pCode)) {
                result.put("code",401);
                result.put("message","pCode为空");
                return result.toJSONString();
            }

            String cpStr = request.getParameter("currentPage");
            int currentPage = cpStr == null ? 1 : Integer.parseInt(cpStr);
            String psStr = request.getParameter("pageSize");
            int pageSize = psStr == null ? 20 : Integer.parseInt(psStr);
            Message msg = productService.productJoinRecords(pId,pCode,currentPage,pageSize);
            if(msg != null) {
                result = JSONObject.parseObject(JSONObject.toJSONString(msg));
            } else {
                result.put("code",404);
                result.put("message","查询不到");
            }
        }catch (Exception e) {
            log.error("productJoinRecords error",e);
            result.put("code",500);
            result.put("message","未知异常");
        }
        finally {
            return result.toJSONString();
        }
    }

    @RequestMapping(value = "/investmentDetail", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String investmentDetail(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            String joinId = request.getParameter("joinId");
            if(joinId == null || "".equalsIgnoreCase(joinId)) {
                result.put("code",400);
                result.put("message","joinId为空");
                return result.toJSONString();
            }
            Message msg = productService.investmentDetail(joinId);
            if(msg != null) {
                result = JSONObject.parseObject(JSONObject.toJSONString(msg));
            } else {
                result.put("code",404);
                result.put("message","查询不到");
            }
        }catch (Exception e) {
            log.error("investmentDetail error",e);
            result.put("code",500);
            result.put("message","未知异常");
        }finally {
            return result.toJSONString();
        }
    }

    @RequestMapping(value = "/optimizeSchemeInvestmentDetail", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String optimizeSchemeInvestmentDetail(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            String joinId = request.getParameter("joinId");
            if(joinId == null || "".equalsIgnoreCase(joinId)) {
                result.put("code",400);
                result.put("message","joinId为空");
                return result.toJSONString();
            }
            Message msg = productService.optimizeSchemeInvestmentDetail(joinId);
            if(msg != null) {
                result = JSONObject.parseObject(JSONObject.toJSONString(msg));
            } else {
                result.put("code",404);
                result.put("message","查询不到");
            }
        }catch (Exception e) {
            log.error("optimizeSchemeInvestmentDetail error",e);
            result.put("code",500);
            result.put("message","未知异常");
        }finally {
            return result.toJSONString();
        }
    }


    @RequestMapping(value = "/investOrder", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String investOrder(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {

            Object user = request.getSession().getAttribute("loginUser");
            if(user == null) {
                result.put("code",300);
                result.put("message","请先登录");
                return result.toJSONString();
            }


            String productCategory = request.getParameter("pCategory");
            if(productCategory == null || "".equalsIgnoreCase(productCategory)) {
                result.put("code",400);
                result.put("message","pCategory为空");
                return result.toJSONString();
            }

            String pId = request.getParameter("pId");
            if(pId == null || "".equalsIgnoreCase(pId)) {
                result.put("code",401);
                result.put("message","pId为空");
                return result.toJSONString();
            }
            String pType = request.getParameter("pType");
            if(pType == null || "".equalsIgnoreCase(pType)) {
                result.put("code",402);
                result.put("message","pType为空");
                return result.toJSONString();
            }

            String payPwd = request.getParameter("payPwd");
            if(payPwd == null || "".equalsIgnoreCase(payPwd)) {
                result.put("code",403);
                result.put("message","payPwd为空");
                return result.toJSONString();
            }

            String token = HttpTookit.getCookieValue(request,Constant.USERTOKEN);
            String userAgent = HttpTookit.getUserAgent(request);
            log.info("validatePayPwdByToken req = \n token = " + token + "\nuserAgent="+userAgent);
            JSONObject res = userService.validatePayPwdByToken(token,payPwd,userAgent);
            log.info("validatePayPwdByToken resp " + res.toJSONString());
            JSONObject resData = res.getJSONObject("data");
            if(Constant.SUCCESS.equals(res.getLong("code")) && 0 != resData.getIntValue("code")) {
                result.put("code",resData.getString("code"));
                result.put("message",resData.getString("message"));
                return result.toJSONString();
            }

            String tenderAmount = request.getParameter("tenderAmount");
            BigDecimal tAmount = new BigDecimal(tenderAmount);
            String redEnvelopeCode = request.getParameter("redEnvelopeCode");
            String couponId = request.getParameter("couponId");

            result =  productService.investOrder(token,productCategory,pId,Integer.parseInt(pType),redEnvelopeCode,tAmount,userAgent,couponId);
        }catch (Exception e) {
            log.error("investOrder error",e);
            result.put("code",500);
            result.put("message","未知异常");
        }finally {
            result.put("activity_url", Configuration.getInstance().getValue("activity_url"));
            return result.toJSONString();
        }
    }


    @RequestMapping(value = "/myInvestmentProducts", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String myInvestmentProducts(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            String pCode = request.getParameter("pCode");
            if(pCode == null || "".equalsIgnoreCase(pCode)) {
                result.put("code",401);
                result.put("message","pCode为空");
                return result.toJSONString();
            }

            Object user = request.getSession().getAttribute("loginUser");
            if(user == null) {
                result.put("code",402);
                result.put("message","请先登录");
                return result.toJSONString();
            }

            String cpStr = request.getParameter("currentPage");
            int currentPage = cpStr == null ? 1 : Integer.parseInt(cpStr);
            String psStr = request.getParameter("pageSize");
            int pageSize = psStr == null ? 20 : Integer.parseInt(psStr);
            String ts = request.getParameter("type");
            int type = ts == null ? 1 : Integer.parseInt(ts);

            String userId = String.valueOf(((User)user).getUserId());

            Message msg = productService.myInvestmentProducts(userId,pCode,currentPage,pageSize,type);
            if(msg != null) {
                result.put("code",msg.getCode());
                result.put("message",msg.getMessage());

                if("BBGS".equalsIgnoreCase(pCode)){
                    JSONObject data = (JSONObject)msg.getData();
                    JSONArray array = data.getJSONArray("list");
                    for(int i = 0; i < array.size();i++) {
                        JSONObject item = (JSONObject)array.get(i);
                        setStepJoin(item);
                    }
                    result.put("data",data);
                } else {
                    result.put("data",msg.getData());
                }

            } else {
                result.put("code",404);
                result.put("message","查询不到");
            }
        }catch (Exception e) {
            log.error("myInvestmentProducts error",e);
            result.put("code",500);
            result.put("message","未知异常");
        }finally {
            return result.toJSONString();
        }
    }

    @RequestMapping(value = "/quit", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String quit(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            String pId = request.getParameter("pId");
            if(pId == null || "".equalsIgnoreCase(pId)) {
                result.put("code",401);
                result.put("message","pId为空");
                return result.toJSONString();
            }
            String pType = request.getParameter("pType");
            if(pType == null || "".equalsIgnoreCase(pType)) {
                result.put("code",402);
                result.put("message","pType为空");
                return result.toJSONString();
            }

            String payPwd = request.getParameter("payPwd");
            if(payPwd == null || "".equalsIgnoreCase(payPwd)) {
                result.put("code",403);
                result.put("message","payPwd为空");
                return result.toJSONString();
            }

            String joinId = request.getParameter("joinId");
            if(joinId == null || "".equalsIgnoreCase(joinId)) {
                result.put("code",404);
                result.put("message","joinId为空");
                return result.toJSONString();
            }

            String quitAmount = request.getParameter("quitAmount");
            if(quitAmount == null || "".equalsIgnoreCase(quitAmount)) {
                result.put("code",405);
                result.put("message","quitAmount为空");
                return result.toJSONString();
            }

            String token = HttpTookit.getCookieValue(request,Constant.USERTOKEN);
            String userAgent = HttpTookit.getUserAgent(request);
            log.info("validatePayPwdByToken req = \ntoken =" + token + "\nuserAgent="+userAgent);
            JSONObject res = userService.validatePayPwdByToken(token,payPwd,userAgent);
            log.info("validatePayPwdByToken resp " + res.toJSONString());
            JSONObject resData = res.getJSONObject("data");
            if(Constant.SUCCESS.equals(res.getLong("code")) && 0 != resData.getIntValue("code")) {
                result.put("code",resData.getString("code"));
                result.put("message",resData.getString("message"));
                return result.toJSONString();
            }

            result =  productService.quit(token,joinId,pId,Integer.parseInt(pType),new BigDecimal(quitAmount),"",userAgent);
        }catch (Exception e) {
            log.error("investOrderList error",e);
            result.put("code",500);
            result.put("message","未知异常");
        }finally {
            return result.toJSONString();
        }
    }

    @RequestMapping(value = "/collectingInterestPlan", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String collectingInterestPlan(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {

            String type = request.getParameter("type");
            if(type == null || "".equalsIgnoreCase(type)) {
                result.put("code",401);
                result.put("message","type为空");
                return result.toJSONString();
            }

            String productType = request.getParameter("productType");
            if(productType == null || "".equalsIgnoreCase(productType)) {
                result.put("code",402);
                result.put("message","productType为空");
                return result.toJSONString();
            }


            Object userObj = request.getSession().getAttribute("loginUser");
            if(userObj == null) {
                result.put("code",403);
                result.put("message","请先登录");
                return result.toJSONString();
            }

            User user = (User)userObj;

            String cpStr = request.getParameter("currentPage");
            int currentPage = cpStr == null ? 1 : Integer.parseInt(cpStr);
            String psStr = request.getParameter("pageSize");
            int pageSize = psStr == null ? 20 : Integer.parseInt(psStr);

            Message msg  = productService.collectingInterestPlan(user.getUserId(),currentPage,pageSize,Integer.parseInt(type),Integer.parseInt(productType));

            if(msg != null) {
                result = JSONObject.parseObject(JSONObject.toJSONString(msg));
            } else {
                result.put("code",400);
                result.put("message","查询不到数据");
            }
        }catch (Exception e) {
            log.error("collectingInterestPlan error",e);
        }finally {
            return result.toJSONString();
        }
    }

    @RequestMapping(value = "/tradingRecord", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String tradingRecord(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {

            String type = request.getParameter("type");
            if(type == null || "".equalsIgnoreCase(type)) {
                result.put("code",401);
                result.put("message","type为空");
                return result.toJSONString();
            }

            String token = HttpTookit.getCookieValue(request,Constant.USERTOKEN);


            String cpStr = request.getParameter("currentPage");
            int currentPage = cpStr == null ? 1 : Integer.parseInt(cpStr);
            String psStr = request.getParameter("pageSize");
            int pageSize = psStr == null ? 20 : Integer.parseInt(psStr);

            Message msg = productService.tradingRecord(HttpTookit.getUserAgent(request),token,currentPage,pageSize,Integer.parseInt(type));

            if(msg != null) {
                result = JSONObject.parseObject(JSONObject.toJSONString(msg));
            } else {
                result.put("code",400);
                result.put("message","找不到记录");
            }
        }catch (Exception e){
            log.error("tradingRecord error ",e);
            result.put("code",404);
            result.put("message","异常");
        } finally {
            return result.toJSONString();
        }
    }

    @RequestMapping(value = "/withdrawCashTrial", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String withdrawCashTrial(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            String token = HttpTookit.getCookieValue(request,Constant.USERTOKEN);

            String withdrawCashAmount = request.getParameter("withdrawCashAmount");

            Message msg = productService.withdrawCashTrial(token,HttpTookit.getUserAgent(request),withdrawCashAmount);

            if(msg != null) {
                result = JSONObject.parseObject(JSONObject.toJSONString(msg));
            } else {
                result.put("code",400);
                result.put("message","找不到记录");
            }
        }catch (Exception e) {
            log.error("withdrawCashTrial error ",e);
            result.put("code",404);
            result.put("message","异常");
        } finally {
            return result.toJSONString();
        }
    }

	@RequestMapping(value = "/conversionCouponRecord", produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
    public @ResponseBody String conversionCouponRecord(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            String token = HttpTookit.getCookieValue(request,Constant.USERTOKEN);

            String code = request.getParameter("code");

            Message msg = productService.conversionCouponRecord(code,token,HttpTookit.getUserAgent(request));

            if(msg != null) {
                result = JSONObject.parseObject(JSONObject.toJSONString(msg));
            } else {
                result.put("code",400);
                result.put("message","找不到记录");
            }
        }catch (Exception e) {
            log.error("conversionCouponRecord error ",e);
            result.put("code",404);
            result.put("message","异常");
        } finally {
            return result.toJSONString();
        }
    }
	
    @RequestMapping(value = "/activityLabel", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String activityLabel(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {

            String productId = request.getParameter("productId");

            Message msg = productService.activityLabel(productId,2);

            if(msg != null) {
                result = JSONObject.parseObject(JSONObject.toJSONString(msg));
            } else {
                result.put("code",400);
                result.put("message","找不到记录");
            }
        }catch (Exception e) {
            log.error("activityLabel error ",e);
            result.put("code",404);
            result.put("message","异常");
        } finally {
            return result.toJSONString();
        }
    }

    @RequestMapping(value = "/fridayActivityBaseGenChance", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String fridayActivityBaseGenChance(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {

            Message status = productService.fridayActivityBaseGetActivityStatus();
            if(status != null && status.getCode() == 200000) {
                String activityStatus = JSONObject.parseObject(status.getData().toString()).getJSONObject("data").getString("activityStatus");
                if("0".equals(activityStatus)) {
                    String token = HttpTookit.getCookieValue(request,Constant.USERTOKEN);

                    Message msg = productService.fridayActivityBaseGenChance(token,HttpTookit.getUserAgent(request));
                    if(msg != null) {
                        result = JSONObject.parseObject(JSONObject.toJSONString(msg));
                    } else {
                        result.put("code",400);
                        result.put("message","找不到记录");
                    }
                } else {
                    result.put("code",activityStatus);
                    result.put("message","不在活动中");
                }
            } else {
                result.put("code",400);
                result.put("message","找不到记录");
            }


        }catch (Exception e) {
            log.error("fridayActivityBaseGenChance error ",e);
            result.put("code",404);
            result.put("message","异常");
        } finally {
            return result.toJSONString();
        }
    }

    @RequestMapping(value = "/loanInfoDisclosure", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String loanInfoDisclosure(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {

            String borrowId = request.getParameter("borrowId");

            Message msg = productService.loanInfoDisclosure(borrowId,HttpTookit.getUserAgent(request));

            if(msg != null) {
                result = JSONObject.parseObject(JSONObject.toJSONString(msg));
            } else {
                result.put("code",400);
                result.put("message","找不到记录");
            }
        }catch (Exception e) {
            log.error("loanInfoDisclosure error ",e);
            result.put("code",404);
            result.put("message","异常");
        } finally {
            return result.toJSONString();
        }
    }

    @RequestMapping(value = "/bidBorrow", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String bidBorrow(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {

            String borrowId = request.getParameter("borrowId");

            Message msg = productService.bidBorrow(borrowId);

            if(msg != null) {
                result = JSONObject.parseObject(JSONObject.toJSONString(msg));
            } else {
                result.put("code",400);
                result.put("message","找不到记录");
            }
        }catch (Exception e) {
            log.error("bidBorrow error ",e);
            result.put("code",404);
            result.put("message","异常");
        } finally {
            return result.toJSONString();
        }
    }

    @RequestMapping(value = "/userApproRealname", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody String userApproRealname(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            String token = HttpTookit.getCookieValue(request,Constant.USERTOKEN);
            Message msg = productService.userApproRealname(token, HttpTookit.getUserAgent(request));

            if(msg != null) {
                result = JSONObject.parseObject(JSONObject.toJSONString(msg));
            } else {
                result.put("code",400);
                result.put("message","找不到记录");
            }
        }catch (Exception e) {
            log.error("bidBorrow error ",e);
            result.put("code",404);
            result.put("message","异常");
        } finally {
            return result.toJSONString();
        }
    }



}
