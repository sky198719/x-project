package com.xxd.h5.web.investement;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxd.common.bo.ErrorMessage;
import com.xxd.common.bo.Message;
import com.xxd.common.bo.SuccessMessage;
import com.xxd.common.dto.RequestDTO;
import com.xxd.common.enums.ApiEnum;
import com.xxd.common.enums.MessageStatus;
import com.xxd.common.exception.CoreException;
import com.xxd.common.exception.ServiceException;
import com.xxd.common.remoting.http.parameters.FormUrlEncoded;
import com.xxd.common.remoting.http.parameters.Headers;
import com.xxd.common.remoting.http.parameters.QueryStrings;
import com.xxd.common.util.HttpHelper;
import com.xxd.common.util.JsonUtil;
import com.xxd.h5.common.enums.ProductTypeEnum;
import com.xxd.h5.service.H5UrlService;
import com.xxd.h5.service.invest.InvestService;
import com.xxd.h5.service.transform.InvestmentTranformService;
import com.xxd.h5.vo.invest.InvestInitVO;
import com.xxd.h5.web.H5Rest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Iterator;

/**
 * 用户投资相关restful接口.
 *
 * @author zhangshengwen
 */
@Component
@Path("/investments/")
@Api(value = "投资接口")
public class InvestmentRest extends H5Rest {

    private static final Logger logger = LoggerFactory.getLogger(InvestmentRest.class);


    @Autowired
    H5UrlService h5UrlService;

    @Autowired
    InvestmentTranformService investmentTranformService;

    @Autowired
    private InvestService investService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "产品投资记录", httpMethod = "GET")
    public Message investHistory(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token", required = true)
            @HeaderParam(value = "token") @NotNull String token,
            @ApiParam(name = "type", value = "类型 1.当前投资 2.历史投资", required = true)
            @QueryParam(value = "type") @NotNull int type) {
        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("token", token)
                .addHeader("User-Agent", HttpHelper.getUserAgent(request));
        QueryStrings queryStrings = QueryStrings.create().addParameter("type", type);
        try {
            return apiUtil.get(ApiEnum.API_INVESTMENT_INVESTS_CURRENT, headers, queryStrings);
        } catch (CoreException | ServiceException exception) {
            logger.error("获取产品投资记录失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取产品投资记录失败!", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{joinId}")
    @ApiOperation(value = "产品投资详情", httpMethod = "GET")
    public Message getPtdInvestDetail(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token", required = true)
            @HeaderParam(value = "token") @NotNull String token,
            @ApiParam(name = "joinId", value = "joinId", required = true)
            @PathParam(value = "joinId") @NotNull String joinId,
            @ApiParam(name = "productType", value = "产品类型(2-步步高升,3-七天大胜,4-月进斗金,5-新元宝,6-月月派,7-散标债券,10-新手产品30天)", required = true)
            @QueryParam(value = "productType") @NotNull int productType) {
        try {
            return investmentTranformService.productInvestDetail(clientId, joinId, productType, token, HttpHelper.getUserAgent(request));
        } catch (CoreException | ServiceException exception) {
            logger.error("获取产品投资详情出错", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取产品投资详情出错", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("product")
    @ApiOperation(value = "单个产品投资记录(当前/历史)")
    public Message assetProduct(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token", required = true)
            @HeaderParam(value = "token") @NotNull String token,
            @ApiParam(name = "page", value = "page", required = true)
            @QueryParam(value = "page") @NotNull int page,
            @ApiParam(name = "pageSize", value = "pageSize", required = true)
            @QueryParam(value = "pageSize") @NotNull int pageSize,
            @ApiParam(name = "type", value = "类型:1.当前投资 2.历史投资", required = true)
            @QueryParam(value = "type") @NotNull String type,
            @ApiParam(name = "productType", value = "产品类型(2-步步高升,3-七天大胜,4-月进斗金,5-新元宝,6-月月派,7-散标和债券,10-新手产品30天)", required = true)
            @QueryParam(value = "productType") @NotNull int productType) {

        String productPinYin = "";
        if (productType == 7) {
            productPinYin = "SBZQ";
        } else {
            productPinYin = transformService.convertType2PinYin(productType);
        }
        if (StringUtils.isBlank(productPinYin)) {
            return new ErrorMessage("错误的产品类型");
        }
        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("token", token)
                .addHeader("User-Agent", HttpHelper.getUserAgent(request));
        QueryStrings queryStrings = QueryStrings.create()
                .addParameter("currentPage", page)
                .addParameter("pageSize", pageSize)
                .addParameter("type", type)
                .setPath("/" + productPinYin);
        try {
            Message message = apiUtil.get(ApiEnum.API_INVESTMENT_INVESTS_PRODUCT, headers, queryStrings);
            JSONObject jsonObject = JsonUtil.toJSONObject(message.getData());
            JSONArray array = jsonObject.getJSONArray("items");
            for (int i = 0; i < array.size(); i++) {
                JSONObject itemObj = array.getJSONObject(i);
                itemObj.put("productType", productType);
            }
            return new SuccessMessage(jsonObject);
        } catch (CoreException | ServiceException exception) {
            logger.error("获取单个产品投资记录失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取单个产品投资记录失败!", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("init")
    @ApiOperation(value = "获取投资初始化参数", httpMethod = "GET")
    public Message init(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token", required = true)
            @HeaderParam(value = "token") @NotNull String token,
            @ApiParam(name = "productId", value = "产品id", format = "String", required = true)
            @QueryParam(value = "productId") @NotNull String productId,
            @ApiParam(name = "productType", value = "产品类型(2-步步高升,3-七天大胜,4-月进斗金,5-新元宝,6-月月派,7-散标,8-债券,10-新手产品30天)", format = "int", required = true)
            @QueryParam(value = "productType") @NotNull int productType) {

        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("token", token)
                .addHeader("User-Agent", HttpHelper.getUserAgent(request));

        String productCode = transformService.convertType2PinYin(productType);
        if (StringUtils.isBlank(productCode)) {
            return new ErrorMessage("error parameter");
        }
        if (StringUtils.isBlank(productId)) {
            return new ErrorMessage("error parameter");
        }
        // 产品详情
        QueryStrings queryStrings = QueryStrings.create()
                .addPath("/").addPath(productCode).addPath("/").addPath(productId);
        Message productDetailMessage = apiUtil.get(ApiEnum.API_INVESTMENT_FINANCES_PRODUCT_DETAIL_LOGIN, headers, queryStrings);
        if (productDetailMessage == null || productDetailMessage.getCode() != MessageStatus.SUCCESS.getStatus()) {
            return new ErrorMessage("请求异常");
        }
        // 资产总览
        Message assetMessage = apiUtil.get(ApiEnum.API_INVESTMENT_ASSET_OVERVIEW, headers);
        if (assetMessage.getCode() != MessageStatus.SUCCESS.getStatus()) {
            return new ErrorMessage("请求异常");
        }

        try {
            JSONObject productJsonObject = JsonUtil.toJSONObject(productDetailMessage.getData());
            JSONObject assetJsonObject = JsonUtil.toJSONObject(assetMessage.getData());
            InvestInitVO vo = new InvestInitVO();
            vo.setProductName(productJsonObject.getString("name"));
            vo.setProductType(productJsonObject.getString("productType"));
            vo.setPlannedAnnualRate(productJsonObject.getString("plannedAnnualRate"));
            vo.setAvailableBalance(assetJsonObject.getString("availableBalance"));
            vo.setDueInPrincipalAndInterest(productJsonObject.getString("dueInPrincipalAndInterest"));
            vo.setLeastPeriod(productJsonObject.getString("leastPeriod"));
            vo.setLeastPeriodUnit(productJsonObject.getString("leastPeriodUnit"));
            vo.setLeftAmount(productJsonObject.getString("leftAmount"));
            vo.setNextRepaymentDay(productJsonObject.getString("nextRepaymentDay"));
            vo.setAllowanceAmount(productJsonObject.getString("allowanceAmount"));
            vo.setProductNo(productJsonObject.getString("productNo"));
            vo.setTransferPrice(productJsonObject.getString("transferPrice"));
            vo.setLabel(productJsonObject.getString("label"));
            vo.setLabelName(transformService.transformLabel2LabelName(vo.getLabel()));
            vo.setInvestableAmount(productJsonObject.getString("investableAmount"));
            vo.setPeriodNo(productJsonObject.getString("periodNo"));
            vo.setLeastInvestAmount(productJsonObject.getString("leastInvestAmount"));
            vo.setRemainingPeriod(vo.getLeastPeriod() + vo.getLeastPeriodUnit()); // 剩余期限
            vo.setMostInvestAmount(productJsonObject.getString("mostInvestAmount"));
            vo.setFloatingRate(productJsonObject.getString("floatingRate"));
            vo.setProductProtocolH5url(h5UrlService.getProductProtocol(productType, token, productId));
            vo.setProductQAH5url(h5UrlService.getProductQA(productType, productId));
            vo.setDangerTipH5url(h5UrlService.getDangerTipH5url());
            vo.setAllowBuy(productJsonObject.getString("allowBuy"));
            vo.setPlannedAnnualRateFrom(productJsonObject.getString("plannedAnnualRateFrom"));
            vo.setPlannedAnnualRateTo(productJsonObject.getString("plannedAnnualRateTo"));
            vo.setPeriodName(productJsonObject.getString("periodName"));
            String activitedType = productJsonObject.getString("activitedType");
            String activityRemarks = productJsonObject.getString("activityRemarks");
            if (activitedType != null && activitedType.equals("2") && StringUtils.isNotBlank(activityRemarks)) {
                vo.setActivitedType("2");
            } else {
                vo.setActivitedType("1");
            }
            vo.setActivityRemarks(activityRemarks);
            vo.setProxyUrl(h5UrlService.getProxyUrl(token));
            return new SuccessMessage(vo);
        } catch (CoreException | ServiceException exception) {
            logger.error("获取投资初始化参数失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取投资初始化参数失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "投资产品", httpMethod = "POST")
    public Message investProduct(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token", required = true)
            @HeaderParam(value = "token") @NotNull String token,
            @ApiParam(name = "productId", value = "产品id", format = "String", required = true)
            @FormParam(value = "productId") @NotNull String productId,
            @ApiParam(name = "productType", value = "产品类型(2-步步高升,3-七天大胜,4-月进斗金,5-新元宝,6-月月派,7-散标,8-债券,10-新手产品30天)", format = "int", required = true)
            @FormParam(value = "productType") @NotNull int productType,
            @ApiParam(name = "label", value = "只有散标、债券才需要传", format = "String")
            @FormParam(value = "label") String label,
            @ApiParam(name = "redPackageCode", value = "红包code", format = "String")
            @FormParam(value = "redPackageCode") String redPackageCode,
            @ApiParam(name = "couponId", value = "优惠券ID(新版优惠券才有，旧版新手红包传0)", format = "String")
            @FormParam(value = "couponId") String couponId,
            @ApiParam(name = "investAmount", value = "投资金额", required = true)
            @FormParam(value = "investAmount") @NotNull double investAmount) {

        if (investAmount <= 0) {
            return new ErrorMessage(MessageStatus.ERROR_PARAM.getStatus(), "投资金额必须大于0");
        }

        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("token", token)
                .addHeader("User-Agent", HttpHelper.getUserAgent(request));

        int productCategory = transformService.transformProductType2ProductCategory(productType);
        int productTypeInDataBase = transformService.transformProductType2DataType(productType, label);
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.addParameter("productCategory", productCategory);
        requestDTO.addParameter("productId", productId);
        requestDTO.addParameter("productType", productTypeInDataBase);
        if (StringUtils.isNotBlank(redPackageCode)) {
            requestDTO.addParameter("redEnvelopeCode", redPackageCode);
        }

        if (StringUtils.isNotBlank(couponId)) {
            requestDTO.addParameter("couponId", couponId);
        } else {
            requestDTO.addParameter("couponId", "0");
        }

        requestDTO.addParameter("tenderAmount", investAmount);
        FormUrlEncoded formUrlEncoded = FormUrlEncoded.create()
                .setContentType(ContentType.APPLICATION_JSON)
                .setBody(requestDTO.transform2JsonString());
        try {

            Message orderMessage = apiUtil.put(ApiEnum.API_TRADECENTER_INVESTORDER, headers, formUrlEncoded);
            if (orderMessage == null || orderMessage.getCode() != MessageStatus.SUCCESS.getStatus()) {
                return new ErrorMessage("投资失败");
            }
            JSONObject orderJsonObject = JsonUtil.toJSONObject(orderMessage.getData());
            JSONObject bizStatus = orderJsonObject.getJSONObject("bizStatus");
            String code = bizStatus.getString("code");
            String message = bizStatus.getString("message");
            if (!StringUtils.equals("SUCCESS", code)) {
                return new SuccessMessage(new ErrorMessage(-1, message));
            }
            JSONObject data = new JSONObject();
            data.put("createTime", orderJsonObject.getString("createTime")); // 订单创建时间
            data.put("orderNo", orderJsonObject.getString("orderNo")); // 订单号
            data.put("startDate", orderJsonObject.getString("startDate")); // 开始计息日期
            data.put("endDate", orderJsonObject.getString("endDate")); // 结束计息日
            data.put("expireDate", orderJsonObject.getString("expireDate")); // 收益到账日期
            data.put("amount", orderJsonObject.getString("amount")); // 加入金额
            data.put("plannedInterest", orderJsonObject.getString("plannedInterest")); // 预期到账收益
            data.put("productName", ProductTypeEnum.getNameByType(productType));


            // 产品投资加入记录id
            headers.removeHeader("token");
            QueryStrings queryStrings = QueryStrings.create().setPath("/").addPath(data.getString("orderNo"));
            Message investDetailMessage = apiUtil.get(ApiEnum.API_TRADECENTER_INVESTORDER_DETAIL, headers, queryStrings);
            if (investDetailMessage != null && investDetailMessage.getCode() == MessageStatus.SUCCESS.getStatus()) {
                data.put("joinId", JsonUtil.toJSONObject(investDetailMessage.getData()).getString("joinId"));
            }

            return new SuccessMessage(new SuccessMessage(0, message, data));
        } catch (CoreException | ServiceException exception) {
            logger.error("投资产品失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("投资产品失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @POST
    @Path("quit")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "退出投资", httpMethod = "POST")
    public Message quitInvest(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token", required = true)
            @HeaderParam(value = "token") @NotNull String token,
            @ApiParam(name = "productId", value = "产品id", format = "String", required = true)
            @FormParam(value = "productId") @NotNull String productId,
            @ApiParam(name = "productType", value = "产品类型(2-步步高升,3-七天大胜,4-月进斗金,5-新元宝,6-月月派,7-散标,8-债券,10-新手产品30天)", format = "int", required = true)
            @FormParam(value = "productType") @NotNull int productType,
            @ApiParam(name = "label", value = "只有散标、债券才需要传", format = "String")
            @FormParam(value = "label") String label,
            @ApiParam(name = "joinId", value = "joinId", format = "String", required = true)
            @FormParam(value = "joinId") @NotNull String joinId,
            @ApiParam(name = "quitAmount", value = "退出金额", required = true)
            @FormParam(value = "quitAmount") @NotNull double quitAmount,
            @ApiParam(name = "payPwd", value = "支付密码", required = true)
            @FormParam(value = "payPwd") @NotNull String payPwd) {

        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("token", token)
                .addHeader("User-Agent", HttpHelper.getUserAgent(request));

        // 验证支付密码
        if (StringUtils.isBlank(payPwd)) {
            return new ErrorMessage(MessageStatus.ERROR_PARAM.getStatus(), "支付密码不能为空");
        }
        RequestDTO payPasswordDto = new RequestDTO();
        payPasswordDto.addParameter("payPassword", payPwd);
        FormUrlEncoded payPasswordValidate = FormUrlEncoded.create()
                .setBody(payPasswordDto.transform2JsonString());
        Message payPasswordMessage = apiUtil.post(ApiEnum.API_USERCENTER_VALIDATE_PAY_PASSWORD, headers, payPasswordValidate);
        if (payPasswordMessage == null || payPasswordMessage.getCode() != MessageStatus.SUCCESS.getStatus()) {
            return new ErrorMessage("请求异常");
        }
        JSONObject jsonObject = JsonUtil.toJSONObject(payPasswordMessage.getData());
        if (jsonObject == null) {
            return new ErrorMessage("请求异常");
        }
        if (jsonObject.getInteger("code") != 0) {
            return new SuccessMessage(new ErrorMessage(-1, jsonObject.getString("message")));
        }

        // 退出投资
        if (quitAmount <= 0) {
            return new ErrorMessage(MessageStatus.ERROR_PARAM.getStatus(), "退出金额必须大于0");
        }
        int productTypeInDataBase = transformService.transformProductType2DataType(productType, label);
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.addParameter("joinId", joinId);
        requestDTO.addParameter("productId", productId);
        requestDTO.addParameter("productType", productTypeInDataBase);
        requestDTO.addParameter("quitAmount", quitAmount);
        requestDTO.addParameter("remark", "");
        FormUrlEncoded formUrlEncoded = FormUrlEncoded.create()
                .setBody(requestDTO.transform2JsonString());
        try {
            Message message = apiUtil.put(ApiEnum.API_TRADECENTER_QUIT_INVESTORDER, headers, formUrlEncoded);
            if (message != null && MessageStatus.SUCCESS.getStatus() == message.getCode()) {
                // 解析返回code和message
                JSONObject data = JsonUtil.toJSONObject(message.getData());
                int code = data.getInteger("resultCode");
                Object objectMessage = data.get("desc");
                String messageStr = "";
                if (null != objectMessage) {
                    messageStr = objectMessage.toString();
                }
                // 转换义务状态
                if (code == 0) {
                    return new SuccessMessage(new SuccessMessage(0, messageStr));
                } else {
                    return new SuccessMessage(new ErrorMessage(-1, messageStr));
                }
            }
            return message;
        } catch (CoreException | ServiceException exception) {
            logger.error("退出投资失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("退出投资失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }


    @POST
    @Path("bond")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "投资债券", httpMethod = "POST")
    public Message investBond(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token", required = true)
            @HeaderParam(value = "token") @NotNull String token,
            @ApiParam(name = "id", value = "债券id", format = "String", required = true)
            @FormParam(value = "id") @NotNull String id) {
        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("token", token)
                .addHeader("User-Agent", HttpHelper.getUserAgent(request));
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.addParameter("requestId", id);
        FormUrlEncoded formUrlEncoded = FormUrlEncoded.create()
                .setBody(requestDTO.transform2JsonString());
        try {
            Message orderMessage = apiUtil.put(ApiEnum.API_TRADECENTER_INVEST_BOND, headers, formUrlEncoded);
            JSONObject orderJsonObject = JsonUtil.toJSONObject(orderMessage.getData());
            JSONObject bizStatus = orderJsonObject.getJSONObject("bizStatus");
            String code = bizStatus.getString("code");
            String message = bizStatus.getString("message");
            if (!StringUtils.equals("SUCCESS", code)) {
                return new SuccessMessage(new ErrorMessage(-1, message));
            }
            JSONObject data = JsonUtil.toJSONObject(bizStatus.get("data"));
            return new SuccessMessage(new SuccessMessage(0, message, data));
        } catch (CoreException | ServiceException exception) {
            logger.error("投资债券失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("投资债券失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("step/quit-interest")
    @ApiOperation(value = "计算步步高升退出利息", httpMethod = "GET")
    public Message calculateStepQuitInterest(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "joinId", value = "joinId", format = "String", required = true)
            @QueryParam(value = "joinId") @NotNull String joinId,
            @ApiParam(name = "quitAmount", value = "quitAmount", format = "int", required = true)
            @QueryParam(value = "quitAmount") @NotNull String quitAmount) {

        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis());
        QueryStrings queryStrings = QueryStrings.create().addPath("/").addPath(joinId)
                .addParameter("quitAmount", quitAmount);
        try {
            return apiUtil.get(ApiEnum.API_TRADECENTER_STEP_QUIT_INTEREST, headers, queryStrings);
        } catch (CoreException | ServiceException exception) {
            logger.error("计算步步高升退出利息失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("计算步步高升退出利息失败", e);
            return new ErrorMessage(e.getMessage());
        }

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("match-record/{joinId}")
    @ApiOperation(value = "债券匹配记录", httpMethod = "GET")
    public Message matchingRecord(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token")
            @HeaderParam(value = "token") String token,
            @ApiParam(name = "page", value = "page", format = "String", required = true)
            @QueryParam(value = "page") @NotNull int page,
            @ApiParam(name = "pageSize", value = "pageSize", format = "int", required = true)
            @QueryParam(value = "pageSize") @NotNull int pageSize,
            @ApiParam(name = "joinId", value = "joinId", format = "String", required = true)
            @PathParam(value = "joinId") @NotNull String joinId) {

        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis());
        // 产品详情
        QueryStrings queryStrings = QueryStrings.create()
                .addPath("/").addPath(joinId)
                .addParameter("currentPage", page)
                .addParameter("pageSize", pageSize);
        try {
            Message message = null;
            if (StringUtils.isNotBlank(token)) {
                headers.addHeader("token", token)
                        .addHeader("User-Agent", HttpHelper.getUserAgent(request));
                // 已登录和未登陆的传参方式不同
                QueryStrings queryStrings2 = QueryStrings.create()
                        .addParameter("joinId", joinId)
                        .addParameter("currentPage", page)
                        .addParameter("pageSize", pageSize);
                message = apiUtil.get(ApiEnum.API_TRADECENTER_MATCHING_BOND_RECORD_LOGIN, headers, queryStrings2);
            } else {
                message = apiUtil.get(ApiEnum.API_INVESTMENT_INVESTS_MATCHING_BOND_RECORD, headers, queryStrings);
            }

            if (message != null && MessageStatus.SUCCESS.getStatus() == message.getCode()) {
                JSONArray jsonArray = JsonUtil.toJSONObject(message.getData()).getJSONArray("item");
                for (Iterator iterator = jsonArray.iterator(); iterator.hasNext(); ) {
                    JSONObject object = (JSONObject) iterator.next();
                    object.put("productContractH5url", h5UrlService.getProductContractUrl());
                }
            }
            return message;
        } catch (CoreException | ServiceException exception) {
            logger.error("获取债券匹配记录失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取债券匹配记录失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("bid")
    @ApiOperation(value = "散标转让", httpMethod = "PUT")
    public Message bidQuit(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token", required = true)
            @HeaderParam(value = "token") @NotNull String token,
            @ApiParam(name = "amount", value = "转让金额", required = true)
            @FormParam(value = "amount") @NotNull double amount,
            @ApiParam(name = "id", value = "加入Id", required = true)
            @FormParam(value = "id") @NotNull String id,
            @ApiParam(name = "payPwd", value = "支付密码", required = true)
            @FormParam(value = "payPwd") @NotNull String payPwd) {

        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("token", token)
                .addHeader("User-Agent", HttpHelper.getUserAgent(request));

        RequestDTO requestDTO = new RequestDTO();
        requestDTO.addParameter("requestAmount", amount);
        requestDTO.addParameter("tenderId", id);
        requestDTO.addParameter("payPassword", payPwd);
        FormUrlEncoded formUrlEncoded = FormUrlEncoded.create()
                .setBody(requestDTO.transform2JsonString());
        try {
            Message bidQuitMessage = apiUtil.put(ApiEnum.API_TRADECENTER_BID_QUIT, headers, formUrlEncoded);
            if (bidQuitMessage == null || bidQuitMessage.getCode() != MessageStatus.SUCCESS.getStatus()) {
                return new ErrorMessage("退出散标失败");
            }
            JSONObject orderJsonObject = JsonUtil.toJSONObject(bidQuitMessage.getData());
            JSONObject bizStatus = orderJsonObject.getJSONObject("bizStatus");
            String code = bizStatus.getString("code");
            String message = bizStatus.getString("message");
            if (!StringUtils.equals("SUCCESS", code)) {
                return new SuccessMessage(new ErrorMessage(-1, message));
            }
            return new SuccessMessage(new SuccessMessage(0, message));
        } catch (CoreException | ServiceException exception) {
            logger.error("退出散标失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("退出散标失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("bid/undo")
    @ApiOperation(value = "撤销散标转让", httpMethod = "PUT")
    public Message bidUndo(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token", required = true)
            @HeaderParam(value = "token") @NotNull String token,
            @ApiParam(name = "id", value = "Id", required = true)
            @FormParam(value = "id") @NotNull String id,
            @ApiParam(name = "payPwd", value = "支付密码", required = true)
            @FormParam(value = "payPwd") @NotNull String payPwd) {

        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("token", token)
                .addHeader("User-Agent", HttpHelper.getUserAgent(request));

        RequestDTO requestDTO = RequestDTO.create()
                .addParameter("requestId", id)
                .addParameter("payPassword", payPwd);
        FormUrlEncoded formUrlEncoded = FormUrlEncoded.create()
                .setBody(requestDTO.transform2JsonString());
        try {
            Message bidUndoQuitMessage = apiUtil.put(ApiEnum.API_TRADECENTER_BID_UNDO_QUIT, headers, formUrlEncoded);
            if (bidUndoQuitMessage == null || bidUndoQuitMessage.getCode() != MessageStatus.SUCCESS.getStatus()) {
                return new ErrorMessage("撤销转让失败");
            }
            JSONObject orderJsonObject = JsonUtil.toJSONObject(bidUndoQuitMessage.getData());
            JSONObject bizStatus = orderJsonObject.getJSONObject("bizStatus");
            String code = bizStatus.getString("code");
            String message = bizStatus.getString("message");
            if (!StringUtils.equals("SUCCESS", code)) {
                return new SuccessMessage(new ErrorMessage(-1, message));
            }
            return new SuccessMessage(new SuccessMessage(0, message));
        } catch (CoreException | ServiceException exception) {
            logger.error("撤销转让失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("撤销转让失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/my-products")
    @ApiOperation(value = "查询我的投资产品", httpMethod = "GET")
    public Message bidUndo(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token", required = true)
            @HeaderParam(value = "token") @NotNull String token,
            @ApiParam(name = "page", value = "page", required = true)
            @QueryParam(value = "page") @NotNull int page,
            @ApiParam(name = "pageSize", value = "pageSize", required = true)
            @QueryParam(value = "pageSize") @NotNull int pageSize,
            @ApiParam(name = "type", value = "类型，1.当前投资 2.历史投资", required = true)
            @QueryParam(value = "type") @NotNull String type,
            @ApiParam(name = "code", value = "1:返回一个购买记录表,2:返回购买记录详细信息", required = true)
            @QueryParam(value = "code") @NotNull int code,
            @ApiParam(name = "productCode", value = "0默认所有,1=日日盈(只有历史),2=步步高升,3=七天大胜(只有历史),4=月进斗金,5=新元宝,6=月月派,7=散标债权,10=新手专属30天,16=新手标", required = true)
            @QueryParam(value = "productCode") @NotNull int productCode) {

        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("token", token)
                .addHeader("User-Agent", HttpHelper.getUserAgent(request));

        QueryStrings queryStrings = QueryStrings.create()
                .addParameter("type", type)
                .addParameter("code", code)
                .addParameter("productCode", productCode)
                .addParameter("currentPage", page)
                .addParameter("pageSize", pageSize);

        try {
            return apiUtil.get(ApiEnum.API_TRADECENTER_MYINVESTMENT_PRODUCTS, headers, queryStrings);
        } catch (CoreException | ServiceException exception) {
            logger.error("查询我的投资产品失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("查询我的投资产品失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("rry")
    @ApiOperation(value = "日日盈历史投资记录")
    public Message recordRRY(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token", required = true)
            @HeaderParam(value = "token") @NotNull String token,
            @ApiParam(name = "page", value = "page", required = true)
            @QueryParam(value = "page") @NotNull int page,
            @ApiParam(name = "status", value = "转入ROLL_IN 转出ROLL_OUT 收益EARNING 全部ALL", required = true)
            @QueryParam(value = "status") @NotNull String status,
            @ApiParam(name = "pageSize", value = "pageSize", required = true)
            @QueryParam(value = "pageSize") @NotNull int pageSize) {
        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("token", token)
                .addHeader("User-Agent", HttpHelper.getUserAgent(request));
        QueryStrings queryStrings = QueryStrings.create()
                .addParameter("currentPage", page)
                .addParameter("pageSize", pageSize);
        queryStrings.setPath("/" + status);
        try {
            return apiUtil.get(ApiEnum.API_INVESTMENT_INVESTS_RRYRECORD, headers, queryStrings);
        } catch (CoreException | ServiceException exception) {
            logger.error("获取日日盈历史投资记录失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取日日盈历史投资记录失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("step/detail/{joinId}")
    @ApiOperation(value = "步步高升投资详情")
    public Message step(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "joinId", value = "joinId", required = true)
            @PathParam(value = "joinId") @NotNull String joinId) {
        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis());

        QueryStrings queryStrings = QueryStrings.create()
                .setPath("/").addPath(joinId);
        try {
            return apiUtil.get(ApiEnum.API_TRADECENTER_STEP_INVESTMENTDETAIL, headers, queryStrings);
        } catch (CoreException | ServiceException exception) {
            logger.error("获取步步高升投资详情失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取步步高升投资详情失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user-tender-detail/{joinId}")
    @ApiOperation(value = "查询用户投资记录")
    public Message userTenderDetail(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "joinId", value = "joinId", required = true)
            @PathParam(value = "joinId") @NotNull String joinId,
            @ApiParam(name = "productType", value = "产品类型(2-步步高升,3-七天大胜,4-月进斗金,5-新元宝,6-月月派,7-散标,8-债券,10-新手产品30天)",format = "int", required = true)
            @QueryParam(value = "productType") @NotNull int productType,
            @ApiParam(name = "page", value = "page", required = true)
            @QueryParam(value = "page") @NotNull int page,
            @ApiParam(name = "pageSize", value = "pageSize", required = true)
            @QueryParam(value = "pageSize") @NotNull int pageSize) {

        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", clientId)
                    .addHeader("clientTime", System.currentTimeMillis());

            int dataType = transformService.transformProductType2DataType(productType, null);

            QueryStrings queryStrings = QueryStrings.create()
                    .setPath("/").addPath(dataType+"/"+joinId)
                    .addParameter("currentPage", page)
                    .addParameter("pageSize", pageSize);

            return apiUtil.get(ApiEnum.API_TRADECENTER_USERTENDERDETAIL, headers, queryStrings);
        } catch (CoreException | ServiceException exception) {
            logger.error("获取查询用户投资记录失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取查询用户投资记录失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }
}
