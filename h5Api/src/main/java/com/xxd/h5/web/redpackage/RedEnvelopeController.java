package com.xxd.h5.web.redpackage;

import com.xxd.common.bo.Message;
import com.xxd.common.remoting.http.parameters.Headers;
import com.xxd.common.util.HttpHelper;
import com.xxd.h5.service.redpackage.RedEnvelopeService;
import com.xxd.h5.web.H5Rest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * created by xiguoding on 2018/4/27 下午3:32
 */
@RestController
@Path("/red-envelope")
@Api(value = "新手红包接口")
public class RedEnvelopeController extends H5Rest {
    @Autowired
    private RedEnvelopeService redEnvelopeService;

    private Headers getHeaders(String clientId, String token, HttpServletRequest request) {
        return Headers.createHeaders()
                .addHeader("token", token)
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("User-Agent", HttpHelper.getUserAgent(request));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "新手红包", httpMethod = "GET")
    public Message getRedEnvelopes(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "token", format = "String", required = true)
            @HeaderParam(value = "token") @NotNull String token,
            @ApiParam(name = "status", value = "0.全部,1.可使用,2.已使用,3.已过期", format = "int", defaultValue = "0")
            @QueryParam(value = "status") int status,
            @ApiParam(name = "page", format = "int", required = true)
            @QueryParam(value = "page") @NotNull int currentPage,
            @ApiParam(name = "pageSize", format = "int", required = true)
            @QueryParam(value = "pageSize") @NotNull int pageSize) {
        return redEnvelopeService.getRedEnvelopes(getHeaders(clientId, token, request), status, currentPage, pageSize);
    }

}
