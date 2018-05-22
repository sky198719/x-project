package com.xxdai.product.service;

import com.alibaba.fastjson.JSONObject;
import com.xxdai.constant.Constant;
import com.xxdai.http.*;
import com.xxdai.user.service.UserService;
import com.xxdai.util.Configuration;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;

@Service
public class ProductService {
    private Log log = LogFactory.getLog(UserService.class);

    @Autowired
    private ApiUtil apiUtil;

    public Message getProduct(String productCode, String productId, String userId) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", Constant.XXD_INTEGRATION_PLATFORM)
                    .addHeader("clientTime", System.currentTimeMillis());
            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("tradeCenter"));
            url.append("/investBiz/").append(productCode).append("/").append(productId);
            if (userId != null) {
                url.append("?userId=").append(userId);
            }
            return apiUtil.get(url.toString(), headers);
        } catch (Exception e) {
            log.error("getProduct error", e);
            return new ErrorMessage("未知异常");
        }
    }

    public Message getWebappProduct(String token, String userAgent, String pCode, int currentPage, int pageSize) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", Constant.XXD_INTEGRATION_PLATFORM)
                    .addHeader("token", token)
                    .addHeader("User-Agent", userAgent)
                    .addHeader("clientTime", System.currentTimeMillis());
            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("tradeCenter"));
            url.append("/investBiz/");
            if (token != null && !"".equalsIgnoreCase(token)) {
                url.append("getUserWebappIndexProduct/");
            } else {
                url.append("getWebappIndexProduct/");
            }
            url.append(pCode).append("?currentPage=").append(currentPage);
            url.append("&pageSize=").append(pageSize);
            return apiUtil.get(url.toString(), headers);
        } catch (Exception e) {
            log.error("getUserWebappIndexProduct error", e);
            return new ErrorMessage("未知异常");
        }
    }

    public Message getNewProduct(String pCode, int currentPage, int pageSize) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", Constant.XXD_INTEGRATION_PLATFORM)
                    .addHeader("clientTime", System.currentTimeMillis());
            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("tradeCenter"));
            url.append("/investBiz/");
            url.append("getWebappIndexProduct/");
            url.append(pCode).append("?currentPage=").append(currentPage);
            url.append("&pageSize=").append(pageSize);
            return apiUtil.get(url.toString(), headers);
        } catch (Exception e) {
            log.error("getNewProduct error", e);
            return new ErrorMessage("未知异常");
        }
    }

    /**
     * 产品加入记录
     *
     * @return
     */
    public Message investOrderList(String productId, int currentPage, int pageSize) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", Constant.XXD_INTEGRATION_PLATFORM)
                    .addHeader("clientTime", System.currentTimeMillis());
            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("tradeCenter"));
            url.append("/InvestOrderList");
            QueryStrings qs = new QueryStrings();
            qs.addParameter("productId", productId);
            qs.addParameter("currentPage", currentPage);
            qs.addParameter("pageSize", pageSize);
            return apiUtil.get(url.toString(), headers, qs);
        } catch (Exception e) {
            log.error("investOrderList error", e);
            return new ErrorMessage("未知异常");
        }
    }

    public Message productJoinRecords(String productId, String productCode, int currentPage, int pageSize) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", Constant.XXD_INTEGRATION_PLATFORM)
                    .addHeader("clientTime", System.currentTimeMillis());
            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("tradeCenter"));
            url.append("/investBiz/productJoinRecords/").append(productId);
            QueryStrings qs = new QueryStrings();
            qs.addParameter("productCode", productCode);
            qs.addParameter("currentPage", currentPage);
            qs.addParameter("pageSize", pageSize);
            return apiUtil.get(url.toString(), headers, qs);
        } catch (Exception e) {
            log.error("productJoinRecords error", e);
            return new ErrorMessage("未知异常");
        }
    }

    public Message loanRelationshipRecord(String productId, int currentPage, int pageSize) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", Constant.XXD_INTEGRATION_PLATFORM)
                    .addHeader("clientTime", System.currentTimeMillis());
            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("tradeCenter"));
            url.append("/borrowTender/loanRelationshipRecord/").append(productId);
            QueryStrings qs = new QueryStrings();
            qs.addParameter("currentPage", currentPage);
            qs.addParameter("pageSize", pageSize);
            return apiUtil.get(url.toString(), headers, qs);
        } catch (Exception e) {
            log.error("loanRelationshipRecord error", e);
            return new ErrorMessage("未知异常");
        }
    }

    public Message queryFinanceBorrowList(int currentPage, int pageSize) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", Constant.XXD_INTEGRATION_PLATFORM)
                    .addHeader("clientTime", System.currentTimeMillis());
            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("tradeCenter"));
            url.append("/investBiz/queryFinanceBorrowList");
            QueryStrings qs = new QueryStrings();
            qs.addParameter("currentPage", currentPage);
            qs.addParameter("pageSize", pageSize);
            return apiUtil.get(url.toString(), headers, qs);
        } catch (Exception e) {
            log.error("queryFinanceBorrowList error", e);
            return new ErrorMessage("未知异常");
        }
    }

    public Message myInvestmentProducts(String userId, String pCode, int currentPage, int pageSize, int type) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", Constant.XXD_INTEGRATION_PLATFORM)
                    .addHeader("clientTime", System.currentTimeMillis());
            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("tradeCenter"));
            url.append("/investBiz/myInvestmentProducts/").append(pCode);
            url.append("?userId=").append(userId);
            url.append("&type=").append(type);
            url.append("&currentPage=").append(currentPage);
            url.append("&pageSize=").append(pageSize);
            return apiUtil.get(url.toString(), headers, null);
        } catch (Exception e) {
            log.error("myInvestmentProducts error", e);
            return new ErrorMessage("未知异常");
        }
    }

    public Message userLoanRelationshipRecord(String token,String userAgent,String joinId, int currentPage, int pageSize) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", Constant.XXD_INTEGRATION_PLATFORM)
                    .addHeader("clientTime", System.currentTimeMillis())
                    .addHeader("token",token)
                    .addHeader("User-Agent",userAgent);

            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("tradeCenter"));
            url.append("/borrowTender/userLoanRelationshipRecord");

            QueryStrings qs = new QueryStrings();
            qs.addParameter("joinId",joinId);
            qs.addParameter("currentPage", currentPage);
            qs.addParameter("pageSize", pageSize);
            return apiUtil.get(url.toString(), headers, qs);
        } catch (Exception e) {
            log.error("myInvestmentProducts error", e);
            return new ErrorMessage("未知异常");
        }
    }

    public Message bondsList(String joinId, int currentPage, int pageSize) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", Constant.XXD_INTEGRATION_PLATFORM)
                    .addHeader("clientTime", System.currentTimeMillis());

            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("tradeCenter"));
            url.append("/investBiz/bondsList");

            QueryStrings qs = new QueryStrings();
            qs.addParameter("joinId",joinId);
            qs.addParameter("currentPage", currentPage);
            qs.addParameter("pageSize", pageSize);
            return apiUtil.get(url.toString(), headers, qs);
        } catch (Exception e) {
            log.error("bondsList error", e);
            return new ErrorMessage("未知异常");
        }
    }


    public Message accumulatedIncome(long userId) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", Constant.XXD_INTEGRATION_PLATFORM)
                    .addHeader("clientTime", System.currentTimeMillis());
            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("tradeCenter"));
            url.append("/asset/accumulatedIncome/").append(userId);
            return apiUtil.get(url.toString(), headers, null);
        } catch (Exception e) {
            log.error("myInvestmentProducts error", e);
            return new ErrorMessage("未知异常");
        }
    }

    public Message dueInIncome(long userId,int type) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", Constant.XXD_INTEGRATION_PLATFORM)
                    .addHeader("clientTime", System.currentTimeMillis());
            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("tradeCenter"));
            url.append("/asset/dueInIncome/").append(userId);

            QueryStrings qs = new QueryStrings();
            qs.addParameter("type", type);
            return apiUtil.get(url.toString(), headers, qs);
        } catch (Exception e) {
            log.error("dueInIncome error", e);
            return new ErrorMessage("未知异常");
        }
    }


    public Message overview(String token,String userAgent) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", "XXD_FRONT_END")
                    .addHeader("token",token)
                    .addHeader("User-Agent",userAgent)
                    .addHeader("clientTime", System.currentTimeMillis());
            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("investmentAPI"));
            url.append("/asset/overview");

            return apiUtil.get(url.toString(), headers, null);
        } catch (Exception e) {
            log.error("overview error", e);
            return new ErrorMessage("未知异常");
        }
    }


    public Message investmentDetail(String joinId) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", Constant.XXD_INTEGRATION_PLATFORM)
                    .addHeader("clientTime", System.currentTimeMillis());
            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("tradeCenter"));
            url.append("/step/investmentDetail/").append(joinId);

            return apiUtil.get(url.toString(), headers, null);
        } catch (Exception e) {
            log.error("investmentDetail error", e);
            return new ErrorMessage("未知异常");
        }
    }

    public Message statistics(long userId) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", Constant.XXD_INTEGRATION_PLATFORM)
                    .addHeader("clientTime", System.currentTimeMillis());
            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("tradeCenter"));
            url.append("/asset/statistics/").append(userId);

            return apiUtil.get(url.toString(), headers, null);
        } catch (Exception e) {
            log.error("statistics error", e);
            return new ErrorMessage("未知异常");
        }
    }

    public Message productDetail(String productCode,String productId) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", Constant.XXD_INTEGRATION_PLATFORM)
                    .addHeader("clientTime", System.currentTimeMillis());
            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("tradeCenter"));
            url.append("/investBiz/productDetail/");

            QueryStrings qs = new QueryStrings();
            qs.addParameter("productCode", productCode);
            qs.addParameter("productId",productId);

            return apiUtil.get(url.toString(), headers, qs);
        } catch (Exception e) {
            log.error("investmentDetail error", e);
            return new ErrorMessage("未知异常");
        }
    }
	
    public Message optimizeSchemeInvestmentDetail(String joinId) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", Constant.XXD_INTEGRATION_PLATFORM)
                    .addHeader("clientTime", System.currentTimeMillis());
            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("tradeCenter"));
            url.append("/optimizeScheme/investmentDetail/").append(joinId);

            return apiUtil.get(url.toString(), headers, null);
        } catch (Exception e) {
            log.error("optimizeSchemeInvestmentDetail error", e);
            return new ErrorMessage("未知异常");
        }
    }


    public JSONObject quit(String token, String joinId, String productId, int productType, BigDecimal quitAmount, String remark, String userAgent) {
        JSONObject result = new JSONObject();
        try {
            log.info("product quit userAgent=" + userAgent + ",token=" + token);
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", Constant.XXD_INTEGRATION_PLATFORM)
                    .addHeader("token", token)
                    .addHeader("Accept", "application/json;charset=utf-8")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("User-Agent", userAgent)
                    .addHeader("clientTime", System.currentTimeMillis());

            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("tradeCenter"));
            url.append("/InvestOrder/quit");

            JSONObject reqData = new JSONObject();
            reqData.put("joinId", joinId);
            reqData.put("schemeId",joinId);
            reqData.put("productId", productId);
            reqData.put("productType", productType);
            reqData.put("quitAmount", quitAmount);
            reqData.put("remark", remark);
            JSONObject json = new JSONObject();
            json.put("data", reqData);
            StringForm se = new StringForm(json.toJSONString());
            se.build();
            log.info("tradeCenter quit req " + json.toJSONString());
            Message msg = apiUtil.put(url.toString(), headers, se);

            log.info("tradeCenter quit resp = " + JSONObject.toJSONString(msg));
            // 用户登录信息
            result = JSONObject.parseObject(JSONObject.toJSONString(msg));
        } catch (Exception e) {
            log.error("tradeCenter quit error", e);
            result.put("code", 400);
            result.put("message", "退出异常，请重新尝试");
        } finally {
            return result;
        }
    }


    /**
     * 购买理财产品
     *
     * @param productCategory 产品种类
     * @param productId       产品ID
     * @param productType     产品类型
     * @param redEnvelopeCode 红包Code
     * @param tenderAmount    投资金额
     * @return
     */
    public JSONObject investOrder(String token, String productCategory, String productId, int productType, String redEnvelopeCode, BigDecimal tenderAmount, String userAgent,String couponId) {
        JSONObject result = new JSONObject();
        try {
            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("tradeCenter"));
            url.append("/InvestOrder");

            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", Constant.XXD_FRONT_END_H5)
                    .addHeader("token", token)
                    .addHeader("Accept", "application/json;charset=utf-8")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("User-Agent", userAgent)
                    .addHeader("clientTime", System.currentTimeMillis());

            JSONObject reqData = new JSONObject();
            reqData.put("productCategory", productCategory);
            reqData.put("productId", productId);
            reqData.put("productType", productType);
            if(null  != redEnvelopeCode && !"".equals(redEnvelopeCode)) {
                reqData.put("redEnvelopeCode", redEnvelopeCode);
                reqData.put("couponId",couponId);
            }

            reqData.put("tenderAmount", tenderAmount);
            JSONObject json = new JSONObject();
            json.put("data", reqData);
            StringForm sf = new StringForm(json.toJSONString());
            sf.build();
            log.info("tradeCenter InvestOrder req = " + json.toJSONString());
            Message msg = apiUtil.put(url.toString(), headers, sf);

            log.info("tradeCenter InvestOrder resp = " + JSONObject.toJSONString(msg));
            // 用户登录信息
            result = JSONObject.parseObject(JSONObject.toJSONString(msg));
        } catch (Exception e) {
            log.error("InvestOrder error", e);
            result.put("code", 400);
            result.put("message", "购买异常，请重新尝试");
        } finally {
            return result;
        }
    }

    public Message collectingInterestPlan(Long userId, int currentPage, int pageSize, int type, int productType) {
        try {

            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", Constant.XXD_INTEGRATION_PLATFORM)
                    .addHeader("Accept", "application/json;charset=utf-8")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("clientTime", System.currentTimeMillis());

            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("tradeCenter"));
            url.append("/asset/collectingInterestPlan/").append(userId);

            QueryStrings qs = new QueryStrings();
            qs.addParameter("currentPage", currentPage);
            qs.addParameter("pageSize", pageSize);
            qs.addParameter("type", type);
            qs.addParameter("productType", productType);
            return apiUtil.get(url.toString(), headers, qs);

        } catch (Exception e) {
            log.error("collectingInterestPlan error", e);
        }
        return null;
    }

    public Message getFddContractVersion(String borrowId) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", Constant.XXD_INTEGRATION_PLATFORM)
                    .addHeader("Accept", "application/json;charset=utf-8")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("clientTime", System.currentTimeMillis());

            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("tradeCenter"));
            url.append("/fdd/getFddContractVersion");

            QueryStrings qs = new QueryStrings();
            qs.addParameter("borrowId", borrowId);
            return apiUtil.get(url.toString(), headers, qs);
        }catch (Exception e) {
            log.error("getFddContractVersion error",e);
        }
        return null;
    }

    public Message getBorrowGuaranteeByBorrowId(String borrowId) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", Constant.XXD_INTEGRATION_PLATFORM)
                    .addHeader("Accept", "application/json;charset=utf-8")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("clientTime", System.currentTimeMillis());

            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("tradeCenter"));
            url.append("/fdd/getBorrowGuaranteeByBorrowId");

            QueryStrings qs = new QueryStrings();
            qs.addParameter("borrowId", borrowId);
            return apiUtil.get(url.toString(), headers, qs);
        }catch (Exception e) {
            log.error("getBorrowGuaranteeByBorrowId error",e);
        }
        return null;
    }



    public Message tradingRecord(String userAgent, String token, int currentPage, int pageSize, int type) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", "XXD_FRONT_END")
                    .addHeader("Accept", "application/json;charset=utf-8")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("User-Agent", userAgent)
                    .addHeader("token", token)
                    .addHeader("clientTime", System.currentTimeMillis());


            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("investmentAPI"));
            url.append("/asset/tradingRecord");

            QueryStrings qs = new QueryStrings();
            qs.addParameter("currentPage", currentPage);
            qs.addParameter("pageSize", pageSize);
            qs.addParameter("type", type);

            return apiUtil.get(url.toString(), headers, qs);

        } catch (Exception e) {
            log.error("tradingRecord error", e);
        }
        return null;
    }

    public Message withdrawCashTrial(String token,String userAgent,String withdrawCashAmount) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", "XXD_FRONT_END")
                    .addHeader("Accept", "application/json;charset=utf-8")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("User-Agent", userAgent)
                    .addHeader("token", token)
                    .addHeader("clientTime", System.currentTimeMillis());


            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("accountCenter"));
            url.append("/account/withdraw/withdrawCashTrial?withdrawCashAmount="+withdrawCashAmount);

            return apiUtil.get(url.toString(), headers, null);

        } catch (Exception e) {
            log.error("tradingRecord error", e);
        }
        return null;
    }

    public Message fridayActivityBaseGenChance(String token, String userAgent) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", Constant.XXD_ACTIVITY_H5_PAGE)
                    .addHeader("clientTime", System.currentTimeMillis())
                    .addHeader("Accept", "application/json;charset=utf-8")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("User-Agent", userAgent)
                    .addHeader("token", token);

            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("activityCenter"));
            url.append("/fridayActivityBase/genChance");

            QueryStrings qs = new QueryStrings();
            return apiUtil.post(url.toString(),headers,qs);
        } catch (Exception e) {
            log.error("activityBase/genChance error", e);
        }
        return null;
    }

    public Message fridayActivityBaseGetActivityStatus() {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", Constant.XXD_ACTIVITY_H5_PAGE)
                    .addHeader("clientTime", System.currentTimeMillis())
                    .addHeader("Accept", "application/json;charset=utf-8")
                    .addHeader("Content-Type", "application/json");

            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("activityCenter"));
            url.append("/fridayActivityBase/getActivityStatus");

            return apiUtil.get(url.toString(),headers,null);
        } catch (Exception e) {
            log.error("activityBase/genChance error", e);
        }
        return null;
    }

    public Message activityBaseGenChance(String token, String userAgent,String createIp,BigDecimal tenderAmount,String tenderId,String tenderType,String userId) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", Constant.XXD_ACTIVITY_H5_PAGE)
                    .addHeader("clientTime", System.currentTimeMillis())
                    .addHeader("Accept", "application/json;charset=utf-8")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("User-Agent", userAgent)
                    .addHeader("token", token);

            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("activityCenter"));

            JSONObject reqData = new JSONObject();
            reqData.put("createIp", createIp);
            reqData.put("haveTransaction", true);
            reqData.put("tenderAmount", tenderAmount);
            reqData.put("tenderId",tenderId);
            reqData.put("tenderType", tenderType);
            reqData.put("terminal", "H5");
            reqData.put("userId",userId);
            JSONObject json = new JSONObject();
            json.put("data", reqData);
            StringForm sf = new StringForm(json.toJSONString());
            sf.build();

            log.info("activityCenter activityBaseGenChance req " + json.toJSONString());
            Message msg = apiUtil.post(url.toString(), headers, sf);
            if(msg != null) {
                log.info("activityCenter activityBaseGenChance resp " + JSONObject.toJSONString(msg));
            }
            return msg;
        } catch (Exception e) {
            log.error("activityBase/genChance error", e);
        }
        return null;
    }

	public Message conversionCouponRecord(String code,String token,String userAgent) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", Constant.XXD_INTEGRATION_PLATFORM)
                    .addHeader("Accept", "application/json;charset=utf-8")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("User-Agent", userAgent)
                    .addHeader("token", token)
                    .addHeader("clientTime", System.currentTimeMillis());


            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("tradeCenter"));
            url.append("/coupon/conversionCouponRecord");

            QueryStrings qs = new QueryStrings();
            qs.addParameter("code", code);
            return apiUtil.post(url.toString(), headers, qs);

        } catch (Exception e) {
            log.error("conversionCouponRecord error", e);
        }
        return null;
    }

    public Message queryCouponRecordsByUserId(String status,String token,String userAgent) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", Constant.XXD_INTEGRATION_PLATFORM)
                    .addHeader("Accept", "application/json;charset=utf-8")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("User-Agent", userAgent)
                    .addHeader("token", token)
                    .addHeader("clientTime", System.currentTimeMillis());


            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("tradeCenter"));
            url.append("/coupon/queryCouponRecordsByUserId");

            QueryStrings qs = new QueryStrings();
            qs.addParameter("status", status);
            return apiUtil.get(url.toString(), headers, qs);

        } catch (Exception e) {
            log.error("queryCouponRecordsByUserId error", e);
        }
        return null;
    }


    public Message activityLabel(String productId,int platform) {
        try {

            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", Constant.XXD_FRONT_END_H5)
                    .addHeader("Accept", "application/json;charset=utf-8")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("clientTime", System.currentTimeMillis());

            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("activityCenter"));
            url.append("/activityLabel/label");

            QueryStrings qs = new QueryStrings();
            qs.addParameter("productId", productId);
            qs.addParameter("platform", platform);
            return apiUtil.get(url.toString(), headers, qs);

        } catch (Exception e) {
            log.error("activityLabel error", e);
        }
        return null;
    }

    public Message loanInfoDisclosure(String borrowId,String userAgent) {
        try {

            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", Constant.XXD_INTEGRATION_PLATFORM)
                    .addHeader("Accept", "application/json;charset=utf-8")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("User-Agent", userAgent)
                    .addHeader("clientTime", System.currentTimeMillis());

            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("tradeCenter"));
            url.append("/bid/").append(borrowId).append("/loanInfoDisclosure");

            return apiUtil.get(url.toString(), headers, null);

        } catch (Exception e) {
            log.error("loanInfoDisclosure error", e);
        }
        return null;
    }

    public Message bidBorrow(String borrowId) {
        try {

            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", Constant.XXD_INTEGRATION_PLATFORM)
                    .addHeader("Accept", "application/json;charset=utf-8")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("clientTime", System.currentTimeMillis());

            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("tradeCenter"));
            url.append("/bid/").append(borrowId);

            return apiUtil.get(url.toString(), headers, null);

        } catch (Exception e) {
            log.error("bidBorrow error", e);
        }
        return null;
    }

    public Message userApproRealname(String token,String userAgent) {
        try {

            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", Constant.XXD_INTEGRATION_PLATFORM)
                    .addHeader("Accept", "application/json;charset=utf-8")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("User-Agent", userAgent)
                    .addHeader("token", token)
                    .addHeader("clientTime", System.currentTimeMillis());

            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("userCenter"));
            url.append("user/appro/realname");

            return apiUtil.get(url.toString(), headers, null);

        } catch (Exception e) {
            log.error("userApproRealname error", e);
        }
        return null;
    }

    public Message queryMobileApproInfoByUserId(String userId) {
        try {

            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", "XXD_INTEGRATION_PLATFORM")
                    .addHeader("Accept", "application/json;charset=utf-8")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("clientTime", System.currentTimeMillis());

            StringBuffer url = new StringBuffer();
            url.append(Configuration.getInstance().getValue("userCenter"));
            url.append("/user/appro/queryMobileApproInfoByUserId?userId=").append(userId);

            return apiUtil.get(url.toString(), headers, null);

        } catch (Exception e) {
            log.error("queryMobileApproInfoByUserId error", e);
        }
        return null;
    }
}
