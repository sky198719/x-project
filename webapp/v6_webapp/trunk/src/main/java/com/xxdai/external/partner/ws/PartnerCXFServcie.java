package com.xxdai.external.partner.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.12
 * 2015-10-10T15:14:32.631+08:00
 * Generated source version: 2.7.12
 * 
 */
@WebService(targetNamespace = "http://webservice.partners.xxdai.com/", name = "PartnerCXFServcie")
@XmlSeeAlso({ObjectFactory.class})
public interface PartnerCXFServcie {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryCsaiInvestInfo", targetNamespace = "http://webservice.partners.xxdai.com/", className = "com.xxdai.external.partner.ws.QueryCsaiInvestInfo")
    @WebMethod
    @ResponseWrapper(localName = "queryCsaiInvestInfoResponse", targetNamespace = "http://webservice.partners.xxdai.com/", className = "com.xxdai.external.partner.ws.QueryCsaiInvestInfoResponse")
    public java.lang.String queryCsaiInvestInfo(
        @WebParam(name = "jsonstring", targetNamespace = "")
        java.lang.String jsonstring
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "judgeCjdUserType", targetNamespace = "http://webservice.partners.xxdai.com/", className = "com.xxdai.external.partner.ws.JudgeCjdUserType")
    @WebMethod
    @ResponseWrapper(localName = "judgeCjdUserTypeResponse", targetNamespace = "http://webservice.partners.xxdai.com/", className = "com.xxdai.external.partner.ws.JudgeCjdUserTypeResponse")
    public java.lang.String judgeCjdUserType(
        @WebParam(name = "jsonstring", targetNamespace = "")
        java.lang.String jsonstring
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryPartnerById", targetNamespace = "http://webservice.partners.xxdai.com/", className = "com.xxdai.external.partner.ws.QueryPartnerById")
    @WebMethod
    @ResponseWrapper(localName = "queryPartnerByIdResponse", targetNamespace = "http://webservice.partners.xxdai.com/", className = "com.xxdai.external.partner.ws.QueryPartnerByIdResponse")
    public java.lang.String queryPartnerById(
        @WebParam(name = "jsonstring", targetNamespace = "")
        java.lang.String jsonstring
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryThirdPartyPayment", targetNamespace = "http://webservice.partners.xxdai.com/", className = "com.xxdai.external.partner.ws.QueryThirdPartyPayment")
    @WebMethod
    @ResponseWrapper(localName = "queryThirdPartyPaymentResponse", targetNamespace = "http://webservice.partners.xxdai.com/", className = "com.xxdai.external.partner.ws.QueryThirdPartyPaymentResponse")
    public java.lang.String queryThirdPartyPayment(
        @WebParam(name = "jsonstring", targetNamespace = "")
        java.lang.String jsonstring
    );

    @RequestWrapper(localName = "updatePartnerUserInfo", targetNamespace = "http://webservice.partners.xxdai.com/", className = "com.xxdai.external.partner.ws.UpdatePartnerUserInfo")
    @WebMethod
    @ResponseWrapper(localName = "updatePartnerUserInfoResponse", targetNamespace = "http://webservice.partners.xxdai.com/", className = "com.xxdai.external.partner.ws.UpdatePartnerUserInfoResponse")
    public void updatePartnerUserInfo(
        @WebParam(name = "name", targetNamespace = "")
        java.lang.String name,
        @WebParam(name = "partnerId", targetNamespace = "")
        java.lang.Integer partnerId,
        @WebParam(name = "code", targetNamespace = "")
        java.lang.String code,
        @WebParam(name = "msg", targetNamespace = "")
        java.lang.String msg
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryPartnerByUserName", targetNamespace = "http://webservice.partners.xxdai.com/", className = "com.xxdai.external.partner.ws.QueryPartnerByUserName")
    @WebMethod
    @ResponseWrapper(localName = "queryPartnerByUserNameResponse", targetNamespace = "http://webservice.partners.xxdai.com/", className = "com.xxdai.external.partner.ws.QueryPartnerByUserNameResponse")
    public java.lang.String queryPartnerByUserName(
        @WebParam(name = "jsonstring", targetNamespace = "")
        java.lang.String jsonstring
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryPartnerByPhone", targetNamespace = "http://webservice.partners.xxdai.com/", className = "com.xxdai.external.partner.ws.QueryPartnerByPhone")
    @WebMethod
    @ResponseWrapper(localName = "queryPartnerByPhoneResponse", targetNamespace = "http://webservice.partners.xxdai.com/", className = "com.xxdai.external.partner.ws.QueryPartnerByPhoneResponse")
    public java.lang.String queryPartnerByPhone(
        @WebParam(name = "jsonstring", targetNamespace = "")
        java.lang.String jsonstring
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryPartnerByUserId", targetNamespace = "http://webservice.partners.xxdai.com/", className = "com.xxdai.external.partner.ws.QueryPartnerByUserId")
    @WebMethod
    @ResponseWrapper(localName = "queryPartnerByUserIdResponse", targetNamespace = "http://webservice.partners.xxdai.com/", className = "com.xxdai.external.partner.ws.QueryPartnerByUserIdResponse")
    public java.lang.String queryPartnerByUserId(
        @WebParam(name = "jsonstring", targetNamespace = "")
        java.lang.String jsonstring
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryCsaiInvestUser", targetNamespace = "http://webservice.partners.xxdai.com/", className = "com.xxdai.external.partner.ws.QueryCsaiInvestUser")
    @WebMethod
    @ResponseWrapper(localName = "queryCsaiInvestUserResponse", targetNamespace = "http://webservice.partners.xxdai.com/", className = "com.xxdai.external.partner.ws.QueryCsaiInvestUserResponse")
    public java.lang.String queryCsaiInvestUser(
        @WebParam(name = "jsonstring", targetNamespace = "")
        java.lang.String jsonstring
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "savePartnerUserInfo", targetNamespace = "http://webservice.partners.xxdai.com/", className = "com.xxdai.external.partner.ws.SavePartnerUserInfo")
    @WebMethod
    @ResponseWrapper(localName = "savePartnerUserInfoResponse", targetNamespace = "http://webservice.partners.xxdai.com/", className = "com.xxdai.external.partner.ws.SavePartnerUserInfoResponse")
    public java.lang.String savePartnerUserInfo(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );
}
