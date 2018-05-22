package com.xxdai.external.weixin.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.12
 * 2015-03-20T16:27:32.225+08:00
 * Generated source version: 2.7.12
 * 
 */
@WebService(targetNamespace = "http://webservice.weixin.xxdai.com/", name = "WeixinCXFService")
@XmlSeeAlso({ObjectFactory.class})
public interface WeixinCXFService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getWeixinUserBind", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.weixin.ws.GetWeixinUserBind")
    @WebMethod
    @ResponseWrapper(localName = "getWeixinUserBindResponse", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.weixin.ws.GetWeixinUserBindResponse")
    public java.lang.String getWeixinUserBind(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "isActivityEnd", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.weixin.ws.IsActivityEnd")
    @WebMethod
    @ResponseWrapper(localName = "isActivityEndResponse", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.weixin.ws.IsActivityEndResponse")
    public boolean isActivityEnd(
        @WebParam(name = "activityCode", targetNamespace = "")
        java.lang.String activityCode
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "weixinUserBind", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.weixin.ws.WeixinUserBind")
    @WebMethod
    @ResponseWrapper(localName = "weixinUserBindResponse", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.weixin.ws.WeixinUserBindResponse")
    public java.lang.String weixinUserBind(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "saveWeixinUser", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.weixin.ws.SaveWeixinUser")
    @WebMethod
    @ResponseWrapper(localName = "saveWeixinUserResponse", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.weixin.ws.SaveWeixinUserResponse")
    public java.lang.String saveWeixinUser(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "subscribeWeixinServiceNo", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.weixin.ws.SubscribeWeixinServiceNo")
    @WebMethod
    @ResponseWrapper(localName = "subscribeWeixinServiceNoResponse", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.weixin.ws.SubscribeWeixinServiceNoResponse")
    public java.lang.String subscribeWeixinServiceNo(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "unSubscribeWeixinServiceNo", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.weixin.ws.UnSubscribeWeixinServiceNo")
    @WebMethod
    @ResponseWrapper(localName = "unSubscribeWeixinServiceNoResponse", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.weixin.ws.UnSubscribeWeixinServiceNoResponse")
    public java.lang.String unSubscribeWeixinServiceNo(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getAccessToken", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.weixin.ws.GetAccessToken")
    @WebMethod
    @ResponseWrapper(localName = "getAccessTokenResponse", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.weixin.ws.GetAccessTokenResponse")
    public java.lang.String getAccessToken(
        @WebParam(name = "param", targetNamespace = "")
        java.lang.String param
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "updateWeixinUser", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.weixin.ws.UpdateWeixinUser")
    @WebMethod
    @ResponseWrapper(localName = "updateWeixinUserResponse", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.weixin.ws.UpdateWeixinUserResponse")
    public java.lang.String updateWeixinUser(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "checkMobile", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.weixin.ws.CheckMobile")
    @WebMethod
    @ResponseWrapper(localName = "checkMobileResponse", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.weixin.ws.CheckMobileResponse")
    public java.lang.String checkMobile(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "goAcquireGift", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.weixin.ws.GoAcquireGift")
    @WebMethod
    @ResponseWrapper(localName = "goAcquireGiftResponse", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.weixin.ws.GoAcquireGiftResponse")
    public java.lang.String goAcquireGift(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "isFollPacks", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.weixin.ws.IsFollPacks")
    @WebMethod
    @ResponseWrapper(localName = "isFollPacksResponse", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.weixin.ws.IsFollPacksResponse")
    public boolean isFollPacks(
        @WebParam(name = "userId", targetNamespace = "")
        java.lang.Long userId
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getWeixinAccoiunt", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.weixin.ws.GetWeixinAccoiunt")
    @WebMethod
    @ResponseWrapper(localName = "getWeixinAccoiuntResponse", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.weixin.ws.GetWeixinAccoiuntResponse")
    public java.lang.String getWeixinAccoiunt(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getWeixinUser", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.weixin.ws.GetWeixinUser")
    @WebMethod
    @ResponseWrapper(localName = "getWeixinUserResponse", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.weixin.ws.GetWeixinUserResponse")
    public java.lang.String getWeixinUser(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "refreshAccessToken", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.weixin.ws.RefreshAccessToken")
    @WebMethod
    @ResponseWrapper(localName = "refreshAccessTokenResponse", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.weixin.ws.RefreshAccessTokenResponse")
    public java.lang.String refreshAccessToken(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );
}