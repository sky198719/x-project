package com.xxd.h5.web.home;

import com.xxd.common.bo.ErrorMessage;
import com.xxd.common.bo.Message;
import com.xxd.common.enums.MessageStatus;
import com.xxd.common.exception.CoreException;
import com.xxd.common.exception.ServiceException;
import com.xxd.common.util.HttpHelper;
import com.xxd.h5.web.H5Rest;
import com.xxd.h5.service.home.H5HomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * app首页相关restful接口.
 * @author zhangshengwen
 */

@Component
@Path("/homes")
@Api(value = "首页", description = "首页相关接口")
public class HomeRest extends H5Rest {

    private static final Logger logger = LoggerFactory.getLogger(HomeRest.class);

    @Autowired
    private H5HomeService homeService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "首页相关信息", httpMethod = "GET")
    public Message homeInfo(
            @ApiParam(name = "clientId", value = "clientId", required = true)
            @HeaderParam(value = "clientId") @NotNull String clientId,
            @ApiParam(name = "token", value = "String")
            @HeaderParam(value = "token") String token) {
        try {
            return homeService.getHomeData(clientId, token, HttpHelper.getUserAgent(request));
        } catch (CoreException | ServiceException exception) {
            logger.error("获取首页信息失败", exception);
            return new ErrorMessage(exception.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("获取首页信息失败", e);
            return new ErrorMessage(MessageStatus.ERROR);
        }
    }
}