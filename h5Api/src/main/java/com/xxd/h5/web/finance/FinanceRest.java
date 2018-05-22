package com.xxd.h5.web.finance;

import com.alibaba.fastjson.JSONObject;
import com.xxd.common.bo.ErrorMessage;
import com.xxd.common.bo.Message;
import com.xxd.common.bo.SuccessMessage;
import com.xxd.common.enums.ApiEnum;
import com.xxd.common.enums.MessageStatus;
import com.xxd.common.exception.CoreException;
import com.xxd.common.exception.ServiceException;
import com.xxd.common.remoting.http.parameters.Headers;
import com.xxd.common.remoting.http.parameters.QueryStrings;
import com.xxd.common.util.CookieUtil;
import com.xxd.common.util.HttpHelper;
import com.xxd.common.util.JsonUtil;
import com.xxd.h5.service.H5BaseService;
import com.xxd.h5.service.finance.FinanceService;
import com.xxd.h5.service.user.UserService;
import com.xxd.h5.vo.finance.Borrows;
import com.xxd.h5.vo.finance.ProductDetailVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * 理财相关接口.
 *
 * @author zhangshengwen
 */
@Component
@Path("/finances")
@Api(value = "理财接口")
public class FinanceRest extends H5BaseService {

    @Autowired
    private FinanceService financeService;
    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(FinanceRest.class);


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/hot")
    @ApiOperation(value = "获取热门理财产品列表", httpMethod = "GET")
    public Message hotProductList(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", type = "String")
            @HeaderParam(value = "token") String token) {
        try {
            return financeService.getHotProducts(clientId, token, HttpHelper.getUserAgent(request));
        } catch (CoreException | ServiceException exception) {
            logger.error("获取热门列表失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取热门列表失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{productId}")
    @ApiOperation(value = "理财产品详情", httpMethod = "GET")
    public Message productDetail(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token")
            @HeaderParam(value = "token") String token,
            @ApiParam(name = "productType", value = "产品类型(2-步步高升,3-七天大胜,4-月进斗金,5-新元宝,6-月月派,7-散标,8-债券,10-新手产品30天))", required = true)
            @QueryParam(value = "productType") @NotNull int productType,
            @ApiParam(name = "productId", value = "理财产品Id ", required = true)
            @PathParam(value = "productId") @NotNull String productId) {
        try {
            return financeService.financeDetail(clientId, token, productId, productType, HttpHelper.getUserAgent(request));
        } catch (CoreException | ServiceException exception) {
            logger.error("获取理财产品详情出错", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取理财产品详情出错", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/join-record/{productId}")
    @ApiOperation(value = "理财产品加入记录", httpMethod = "GET")
    public Message joinRecord(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "productId", value = "理财产品Id ", required = true)
            @PathParam(value = "productId") @NotNull String productId,
            @ApiParam(name = "page", value = "page", required = true)
            @QueryParam(value = "page") @NotNull int currentPage,
            @ApiParam(name = "pageSize", value = "默认20条")
            @DefaultValue(value = "20") @QueryParam(value = "pageSize") @NotNull int pageSize,
            @ApiParam(name = "productType", value = "产品类型(2-步步高升,3-七天大胜,4-月进斗金,5-新元宝,6-月月派,7-散标,8-债券,10-新手产品30天))", required = true)
            @QueryParam(value = "productType") @NotNull int productType) {

        String productCode = transformService.convertType2PinYin(productType);
        QueryStrings queryStrings = QueryStrings.create()
                .setPath("/").addPath(productId)
                .addParameter("currentPage", currentPage)
                .addParameter("pageSize", pageSize)
                .addParameter("productCode", productCode);
        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis());
        try {
            return apiUtil.get(ApiEnum.API_INVESTMENT_FINANCES_JOIN_RECORD, headers, queryStrings);
        } catch (CoreException | ServiceException exception) {
            logger.error("获取理财产品加入记录失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取理财产品加入记录失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/bids")
    @ApiOperation(value = "获取散标直投列表", httpMethod = "GET")
    public Message bidsList(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "page", type = "int", required = true)
            @QueryParam(value = "page") @NotNull int currentPage,
            @ApiParam(name = "pageSize", type = "int", required = true)
            @QueryParam(value = "pageSize") @NotNull int pageSize) {
        try {
            return financeService.getBids(clientId, currentPage, pageSize, HttpHelper.getUserAgent(request));
        } catch (CoreException | ServiceException exception) {
            logger.error("获取散标列表失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取散标列表失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/bids/calculator")
    @ApiOperation(value = "获取散标预期收益", httpMethod = "GET")
    public Message bidsCalculator(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "money", value = "金额", required = true)
            @QueryParam(value = "money") @NotNull double money,
            @ApiParam(name = "apr", value = "年利率", required = true)
            @QueryParam(value = "apr") @NotNull double apr,
            @ApiParam(name = "times", value = "期限", required = true)
            @QueryParam(value = "times") @NotNull int times,
            @ApiParam(name = "paymentMethod", value = "还款方式", required = true)
            @QueryParam(value = "paymentMethod") @NotNull int paymentMethod) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", clientId)
                    .addHeader("clientTime", System.currentTimeMillis());
            QueryStrings queryStrings = QueryStrings.create()
                    .addParameter("money", money)
                    .addParameter("apr", apr)
                    .addParameter("times", times)
                    .addParameter("paymentMethod", paymentMethod);
            return apiUtil.get(ApiEnum.API_TRADECENTER_BID_CALCULATOR, headers, queryStrings);
        } catch (CoreException | ServiceException exception) {
            logger.error("获取散标预期收益失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取散标预期收益失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    /**
     * 1信用标 2推荐标 3净值标 4秒还标 5调剂标 6抵押标
     * 7新新宝 8新生贷 9新商贷 10新房贷 11菁英贷
     * 12信网贷 13票小宝 14新车贷 15分期贷 16新手标
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/borrow")
    @ApiOperation(value = "产品债权列表-属于标的-7", httpMethod = "GET")
    public Message borrowList(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "page", type = "int", required = true)
            @QueryParam(value = "page") @NotNull int currentPage,
            @ApiParam(name = "pageSize", type = "int", required = true)
            @QueryParam(value = "pageSize") @NotNull int pageSize) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", clientId)
                    .addHeader("clientTime", System.currentTimeMillis());
            QueryStrings queryStrings = QueryStrings.create()
                    .addParameter("currentPage", currentPage)
                    .addParameter("pageSize", pageSize);
            Message borrowListMessage = apiUtil.get(ApiEnum.API_INVESTMENT_FINANCES_BORROW_LIST, headers, queryStrings);
            if (borrowListMessage != null && borrowListMessage.getCode() == MessageStatus.SUCCESS.getStatus()) {
                JSONObject jsonObject = JsonUtil.toJSONObject(borrowListMessage.getData());
                Borrows borrows = JsonUtil.toObject(jsonObject, Borrows.class);
                borrows = financeService.fillContractH5UrlFieldOfBorrow(borrows);
                return new SuccessMessage(borrows);
            }
            return new SuccessMessage(new Borrows());
        } catch (CoreException | ServiceException exception) {
            logger.error("获取产品债权列表失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取产品债权列表失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/creditors")
    @ApiOperation(value = "获取债券转让列表", httpMethod = "GET")
    public Message creditorsList(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "page", type = "int", required = true)
            @QueryParam(value = "page") @NotNull int currentPage,
            @ApiParam(name = "pageSize", type = "int", required = true)
            @QueryParam(value = "pageSize") @NotNull int pageSize) {
        try {
            return financeService.getCreditors(clientId, currentPage, pageSize, HttpHelper.getUserAgent(request));
        } catch (CoreException | ServiceException exception) {
            logger.error("获取债券转让列表失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取债券转让列表失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/new-tender")
    @ApiOperation(value = "新手标", httpMethod = "GET")
    public Message newTender(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token")
            @HeaderParam(value = "token") String token,
            @ApiParam(name = "month", type = "int", value = "1(一个月),3(三个月)", required = true)
            @QueryParam(value = "month") @NotNull int month) {

        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", clientId)
                    .addHeader("clientTime", System.currentTimeMillis());


            QueryStrings queryStrings = QueryStrings.create()
                    .addParameter("prcode", "XSB" + month);

            if (StringUtils.isNotBlank(token)) {
                headers.addHeader("token", token)
                        .addHeader("User-Agent", HttpHelper.getUserAgent(request));
                if (userService.isLogin(headers)) {
                    return apiUtil.get(ApiEnum.API_TRADECENTER_DETAIL_XSB_LOGINED, headers, queryStrings);
                } else {
                    headers.removeHeader("token").removeHeader("User-Agent");
                    return apiUtil.get(ApiEnum.API_TRADECENTER_DETAIL_XSB, headers, queryStrings);
                }
            } else {
                return apiUtil.get(ApiEnum.API_TRADECENTER_DETAIL_XSB, headers, queryStrings);
            }
        } catch (CoreException | ServiceException exception) {
            logger.error("获取新手标失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取新手标失败", e);
            return new ErrorMessage(e.getMessage());
        }

    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/new-tender-record")
    @ApiOperation(value = "新手标投资记录", httpMethod = "GET")
    public Message newTender(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "reglintstId", value = "reglintstId", required = true)
            @QueryParam(value = "reglintstId") @NotNull String reglintstId,
            @ApiParam(name = "currentPage", type = "int", required = true)
            @QueryParam(value = "currentPage") @NotNull int currentPage,
            @ApiParam(name = "pageSize", type = "int", required = true)
            @QueryParam(value = "pageSize") @NotNull int pageSize) {

        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", clientId)
                    .addHeader("clientTime", System.currentTimeMillis());


            QueryStrings queryStrings = QueryStrings.create()
                    .addParameter("reglintstId", reglintstId)
                    .addParameter("currentPage", currentPage)
                    .addParameter("pageSize", pageSize);

           return apiUtil.get(ApiEnum.API_TRADECENTER_XSB_RECORD, headers, queryStrings);
        } catch (CoreException | ServiceException exception) {
            logger.error("获取新手标失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取新手标失败", e);
            return new ErrorMessage(e.getMessage());
        }

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/bid/borrower/{bidCode}")
    @ApiOperation(value = "标的借款人信息", httpMethod = "GET")
    public Message bidBorrowerInfo(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "bidCode", value = "标的code", required = true)
            @PathParam(value = "bidCode") @NotNull String bidCode) {

        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", clientId)
                    .addHeader("clientTime", System.currentTimeMillis());


            QueryStrings queryStrings = QueryStrings.create()
                    .addPath("/").addPath(bidCode).addPath("/borrower");

            return apiUtil.get(ApiEnum.API_USERCENTER_BORROWER_INFO, headers, queryStrings);
        } catch (CoreException | ServiceException exception) {
            logger.error("标的借款人信息", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("标的借款人信息", e);
            return new ErrorMessage(e.getMessage());
        }

    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/bid/loan-info/{bidCode}")
    @ApiOperation(value = "标的借款人征信信息", httpMethod = "GET")
    public Message loanInfo(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token")
            @HeaderParam(value = "token") String token,
            @ApiParam(name = "bidCode", value = "标的code", required = true)
            @PathParam(value = "bidCode") @NotNull String bidCode) {

        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("token", token)
                    .addHeader("clientId", clientId)
                    .addHeader("clientTime", System.currentTimeMillis())
                    .addHeader("User-Agent", HttpHelper.getUserAgent(request));

            return financeService.getLoanInfo(headers, bidCode);
        } catch (CoreException | ServiceException exception) {
            logger.error("标的借款人征信信息", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("标的借款人征信信息", e);
            return new ErrorMessage(e.getMessage());
        }

    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/hot-xyb-yyp")
    @ApiOperation(value = "获取热门理财产品列表-新元宝-月月派利息最高的", httpMethod = "GET")
    public Message hotProductList(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "productType", value = "产品类型5-新元宝,6-月月派)", required = true)
            @QueryParam(value = "productType") @NotNull int productType) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", clientId)
                    .addHeader("clientTime", System.currentTimeMillis());

            int productCode = transformService.transformProductType2DataType(productType, null);

            QueryStrings queryStrings = QueryStrings.create()
                    .addParameter("productCode", productCode);
            return apiUtil.get(ApiEnum.API_TRADECENTER_XYB_YYP_HOTLIST, headers, queryStrings);
        } catch (CoreException | ServiceException exception) {
            logger.error("获取热门理财产品列表-新元宝-月月派利息最高的", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取热门理财产品列表-新元宝-月月派利息最高的", e);
            return new ErrorMessage(e.getMessage());
        }
    }



    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/agreement")
    @ApiOperation(value = "查询通用产品协议内容", httpMethod = "GET")
    public Message getAgreement(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token", required = true)
            @HeaderParam(value = "token") @NotNull String token,
            @ApiParam(name = "productType", value = "产品类型(2-步步高升,3-七天大胜,4-月进斗金,5-新元宝,6-月月派,7-散标,8-债券,10-新手产品30天)", required = true)
            @QueryParam(value = "productType") @NotNull int productType,
            @ApiParam(name = "productId", value = "产品Id", required = true)
            @QueryParam(value = "productId") @NotNull int productId,
            @ApiParam(name = "productJoinId", value = "产品加入Id", required = true)
            @QueryParam(value = "productJoinId") @NotNull int productJoinId) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("token", token)
                    .addHeader("User-Agent", HttpHelper.getUserAgent(request))
                    .addHeader("clientId", clientId)
                    .addHeader("clientTime", System.currentTimeMillis());

            String productCode = transformService.convertType2PinYin(productType);

            QueryStrings queryStrings = QueryStrings.create()
                    .addParameter("productId", productId)
                    .addParameter("productJoinId", productJoinId)
                    .addParameter("productCode", productCode);

            return apiUtil.get(ApiEnum.API_TRADECENTER_INVEST_AGREEMENT, headers, queryStrings);
        } catch (CoreException | ServiceException exception) {
            logger.error("查询通用产品协议内容", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("查询通用产品协议内容", e);
            return new ErrorMessage(e.getMessage());
        }
    }



    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/max-apr-xyb")
    @ApiOperation(value = "查询新元宝每期最大利率", httpMethod = "GET")
    public Message getXYBMaxApr(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", clientId)
                    .addHeader("clientTime", System.currentTimeMillis());

            return apiUtil.get(ApiEnum.API_INVESTMENT_XYB_MAXAPR, headers);
        } catch (CoreException | ServiceException exception) {
            logger.error("查询新元宝每期最大利率失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("查询新元宝每期最大利率失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }



    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/xyd-detail/{bidCode}")
    @ApiOperation(value = "新宜贷产品详情", httpMethod = "GET")
    public Message xydInfo(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "bidCode", value = "产品Id", required = true)
            @PathParam(value = "bidCode") @NotNull String bidCode
            ) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", clientId)
                    .addHeader("clientTime", System.currentTimeMillis());

            return financeService.getXydInfo(headers, bidCode);
        } catch (CoreException | ServiceException exception) {
            logger.error("查询新宜贷产品详情失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("查询新宜贷产品详情失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }



    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/invest-record/{bidCode}")
    @ApiOperation(value = "新宜贷投资记录", httpMethod = "GET")
    public Message xydInvestRecord(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "bidCode", value = "产品Id", required = true)
            @PathParam(value = "bidCode") @NotNull String bidCode,
            @ApiParam(name = "currentPage", type = "int", required = true)
            @QueryParam(value = "currentPage") @NotNull int currentPage,
            @ApiParam(name = "pageSize", type = "int", required = true)
            @QueryParam(value = "pageSize") @NotNull int pageSize) {
        try {

            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", clientId)
                    .addHeader("clientTime", System.currentTimeMillis());


            QueryStrings queryStrings = QueryStrings.create()
                    .setPath("/").addPath(bidCode).addPath("/investments")
                    .addParameter("currentPage", currentPage)
                    .addParameter("pageSize", pageSize);

            return apiUtil.get(ApiEnum.API_INTEGRATION_BIDS_BORROWER, headers, queryStrings);
        } catch (CoreException | ServiceException exception) {
            logger.error("查询新宜贷投资记录失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("查询新宜贷投资记录失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/xyd-repayment/{bidCode}")
    @ApiOperation(value = "新宜贷还款记录", httpMethod = "GET")
    public Message xydRepayment(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "bidCode", value = "产品Id", required = true)
            @PathParam(value = "bidCode") @NotNull String bidCode) {
        try {

            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", clientId)
                    .addHeader("clientTime", System.currentTimeMillis());


            QueryStrings queryStrings = QueryStrings.create()
                    .setPath("/").addPath(bidCode).addPath("/repayments");

            return apiUtil.get(ApiEnum.API_INTEGRATION_BIDS_BORROWER, headers, queryStrings);
        } catch (CoreException | ServiceException exception) {
            logger.error("查询新宜贷还款记录失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("查询新宜贷还款记录失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }
}