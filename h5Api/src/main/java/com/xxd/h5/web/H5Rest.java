package com.xxd.h5.web;

import com.xxd.common.util.ApiUtil;
import com.xxd.h5.service.transform.CouponTransformService;
import com.xxd.h5.service.transform.TransformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

/**
 * the base restful base class for app client.
 * @author zhangshengwen
 */
@Component
public class H5Rest {

    @Context
    public HttpServletRequest request;

    @Context
    public HttpServletResponse response;

    @Autowired
    public ApiUtil apiUtil;

    @Autowired
    protected TransformService transformService;

    @Autowired
    protected CouponTransformService couponTransformService;

}
