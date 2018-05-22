package com.xxd.h5.web.account;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Maps;
import com.xxd.common.bo.ErrorMessage;
import com.xxd.common.bo.Message;
import com.xxd.common.bo.SuccessMessage;
import com.xxd.common.enums.ApiEnum;
import com.xxd.common.enums.MessageStatus;
import com.xxd.common.exception.CoreException;
import com.xxd.common.exception.ServiceException;
import com.xxd.common.remoting.http.parameters.Headers;
import com.xxd.common.remoting.http.parameters.QueryStrings;
import com.xxd.common.util.HttpHelper;
import com.xxd.common.util.JsonUtil;
import com.xxd.h5.vo.home.HomeDataVo;
import com.xxd.h5.web.H5Rest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zhangshengwen
 * @date 2017/11/28
 */
@Component
@Path("/profits")
@Api(value = "用户收益", description = "用户收益相关接口")
public class ProfitsRest extends H5Rest{

    private static final Logger logger = LoggerFactory.getLogger(AccountRest.class);


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/total")
    @ApiOperation(value = "累计收益列表", httpMethod = "GET")
    public Message accumulatedIncomes(
            @ApiParam(name = "clientId", value = "clientId", format = "String", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token", format = "String", required = true)
            @HeaderParam(value = "token") @NotNull String token,
            @ApiParam(name = "page", value = "页码", required = true)
            @QueryParam(value = "page") @NotNull String page,
            @ApiParam(name = "pageSize", value = "每页显示大小", required = true)
            @QueryParam(value = "pageSize") @NotNull String pageSize,
            @ApiParam(name = "productType", value = "产品类型", required = true)
            @QueryParam(value = "productType") @NotNull String productType) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", clientId)
                    .addHeader("clientTime", System.currentTimeMillis())
                    .addHeader("token", token)
                    .addHeader("User-Agent", HttpHelper.getUserAgent(request));
            QueryStrings queryStrings = QueryStrings.create()
                    .addParameter("currentPage", page)
                    .addParameter("pageSize", pageSize)
                    .addParameter("type", productType);
            return apiUtil.get(ApiEnum.API_INVESTMENT_PROFITS_ACCUMULATE_DINCOMES, headers, queryStrings);
        } catch (CoreException | ServiceException exception) {
            logger.error("获取累计收益列表失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取累计收益列表失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/total-overview")
    @ApiOperation(value = "累计收益概览", httpMethod = "GET")
    public Message accumulatedIncome(
            @ApiParam(name = "clientId", value = "clientId", format = "String", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token", format = "String", required = true)
            @HeaderParam(value = "token") @NotNull String token) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", clientId)
                    .addHeader("clientTime", System.currentTimeMillis())
                    .addHeader("token", token)
                    .addHeader("User-Agent", HttpHelper.getUserAgent(request));
            return apiUtil.get(ApiEnum.API_INVESTMENT_PROFITS_ACCUMULATED_OVERVIEW, headers);
        } catch (CoreException | ServiceException exception) {
            logger.error("获取累计收益概览失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取累计收益概览失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/pending")
    @ApiOperation(value = "待收益列表", httpMethod = "GET")
    public Message dueInIncomes(
            @ApiParam(name = "clientId", value = "clientId", format = "String", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token", format = "String", required = true)
            @HeaderParam(value = "token") @NotNull String token,
            @ApiParam(name = "page", value = "页码", required = true)
            @QueryParam(value = "page") @NotNull String page,
            @ApiParam(name = "pageSize", value = "每页显示大小", required = true)
            @QueryParam(value = "pageSize") @NotNull String pageSize,
            @ApiParam(name = "productType", value = "产品类型", required = true)
            @QueryParam(value = "productType") @NotNull String productType,
            @ApiParam(name = "type", value = "类型:1.待收收益 2.回款计划", required = true)
            @QueryParam(value = "type") @NotNull int type) {


        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("token", token)
                .addHeader("User-Agent", HttpHelper.getUserAgent(request));
        QueryStrings queryStrings = QueryStrings.create()
                .addParameter("currentPage", page)
                .addParameter("pageSize", pageSize)
                .addParameter("productType", productType).addParameter("type", type);
        try {

            Message profitList = apiUtil.get(ApiEnum.API_INVESTMENT_PROFITS_PENDING_LIST, headers, queryStrings);
            if (profitList == null || profitList.getCode() != MessageStatus.SUCCESS.getStatus()) {
                return new SuccessMessage(new Object());
            }
            return profitList;
        } catch (CoreException | ServiceException exception) {
            logger.error("获取待收益列表失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取待收益列表失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/pending-overview")
    @ApiOperation(value = "待收收益概览", httpMethod = "GET")
    public Message dueInIncome(
            @ApiParam(name = "clientId", value = "clientId", format = "String", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token", format = "String", required = true)
            @HeaderParam(value = "token") @NotNull String token,
            @ApiParam(name = "type", value = "类型:1.待收收益 2.回款计划", required = true)
            @QueryParam(value = "type") @NotNull int type) {
        try {
            Headers headers = Headers.createHeaders()
                    .addHeader("clientId", clientId)
                    .addHeader("clientTime", System.currentTimeMillis())
                    .addHeader("token", token)
                    .addHeader("User-Agent", HttpHelper.getUserAgent(request));
            QueryStrings queryStrings = QueryStrings.create().addParameter("type", type);

            return apiUtil.get(ApiEnum.API_INVESTMENT_PROFITS_PENDING, headers, queryStrings);
        } catch (CoreException | ServiceException exception) {
            logger.error("获取待收收益概览", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取待收收益概览", e);
            return new ErrorMessage(e.getMessage());
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/current")
    @ApiOperation(value = "获取当前收益", httpMethod = "GET")
    public Message profits(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "String", required = true)
            @HeaderParam(value = "token") @NotNull String token) {

        Message dueProfitMessage = null;
        // 请求消息头
        Headers headers = Headers.createHeaders()
                .addHeader("token", token)
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("User-Agent", HttpHelper.getUserAgent(request));
        try {
            // 到账收益
            dueProfitMessage = apiUtil.post(ApiEnum.API_INVESTMENT_PROFITS_NOW, headers);
            if (dueProfitMessage != null && dueProfitMessage.getCode() == MessageStatus.SUCCESS.getStatus()) {
                JSONArray dueProfitArray = (JsonUtil.toJSONObject(dueProfitMessage.getData())).getJSONArray("items");
                List<HomeDataVo.ProfitProduct> list = JsonUtil.toList(dueProfitArray, HomeDataVo.ProfitProduct.class);
                Map<String, Object> data = Maps.newHashMap();
                data.put("profits", list);
                return new SuccessMessage(data);
            }
            return new SuccessMessage(new Object());
        } catch (CoreException | ServiceException exception) {
            logger.error("获取当前收益失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取当前收益失败", e);
            return new ErrorMessage(e.getMessage());
        }
    }
}
