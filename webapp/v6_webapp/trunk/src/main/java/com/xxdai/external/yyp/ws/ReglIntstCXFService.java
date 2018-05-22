package com.xxdai.external.yyp.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.12
 * 2016-12-01T15:10:24.457+08:00
 * Generated source version: 2.7.12
 * 
 */
@WebService(targetNamespace = "http://webservice.yyp.xxdai.com/", name = "ReglIntstCXFService")
@XmlSeeAlso({ObjectFactory.class})
public interface ReglIntstCXFService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryYypJoinList", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.QueryYypJoinList")
    @WebMethod
    @ResponseWrapper(localName = "queryYypJoinListResponse", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.QueryYypJoinListResponse")
    public java.lang.String queryYypJoinList(
        @WebParam(name = "jsonParam", targetNamespace = "")
        java.lang.String jsonParam
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryReglintstStaticInfo", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.QueryReglintstStaticInfo")
    @WebMethod
    @ResponseWrapper(localName = "queryReglintstStaticInfoResponse", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.QueryReglintstStaticInfoResponse")
    public java.lang.String queryReglintstStaticInfo();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "updateReglintst", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.UpdateReglintst")
    @WebMethod
    @ResponseWrapper(localName = "updateReglintstResponse", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.UpdateReglintstResponse")
    public java.lang.String updateReglintst(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getReglintstById", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.GetReglintstById")
    @WebMethod
    @ResponseWrapper(localName = "getReglintstByIdResponse", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.GetReglintstByIdResponse")
    public java.lang.String getReglintstById(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryYYPLicaiLogs", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.QueryYYPLicaiLogs")
    @WebMethod
    @ResponseWrapper(localName = "queryYYPLicaiLogsResponse", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.QueryYYPLicaiLogsResponse")
    public java.lang.String queryYYPLicaiLogs(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "repealReglintst", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.RepealReglintst")
    @WebMethod
    @ResponseWrapper(localName = "repealReglintstResponse", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.RepealReglintstResponse")
    public java.lang.String repealReglintst(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getReglintstAccount", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.GetReglintstAccount")
    @WebMethod
    @ResponseWrapper(localName = "getReglintstAccountResponse", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.GetReglintstAccountResponse")
    public java.lang.String getReglintstAccount();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryInvestInfo", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.QueryInvestInfo")
    @WebMethod
    @ResponseWrapper(localName = "queryInvestInfoResponse", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.QueryInvestInfoResponse")
    public java.lang.String queryInvestInfo(
        @WebParam(name = "jsonParam", targetNamespace = "")
        java.lang.String jsonParam
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryReglintstTerms", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.QueryReglintstTerms")
    @WebMethod
    @ResponseWrapper(localName = "queryReglintstTermsResponse", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.QueryReglintstTermsResponse")
    public java.lang.String queryReglintstTerms();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryReglintstTotalPersonTime", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.QueryReglintstTotalPersonTime")
    @WebMethod
    @ResponseWrapper(localName = "queryReglintstTotalPersonTimeResponse", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.QueryReglintstTotalPersonTimeResponse")
    public java.lang.String queryReglintstTotalPersonTime(
        @WebParam(name = "jsonParam", targetNamespace = "")
        java.lang.String jsonParam
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "addReglintst", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.AddReglintst")
    @WebMethod
    @ResponseWrapper(localName = "addReglintstResponse", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.AddReglintstResponse")
    public java.lang.String addReglintst(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryInterestList", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.QueryInterestList")
    @WebMethod
    @ResponseWrapper(localName = "queryInterestListResponse", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.QueryInterestListResponse")
    public java.lang.String queryInterestList(
        @WebParam(name = "jsonParam", targetNamespace = "")
        java.lang.String jsonParam
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getReglintstJoinRecordList", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.GetReglintstJoinRecordList")
    @WebMethod
    @ResponseWrapper(localName = "getReglintstJoinRecordListResponse", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.GetReglintstJoinRecordListResponse")
    public java.lang.String getReglintstJoinRecordList(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getMyYypList", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.GetMyYypList")
    @WebMethod
    @ResponseWrapper(localName = "getMyYypListResponse", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.GetMyYypListResponse")
    public java.lang.String getMyYypList(
        @WebParam(name = "jsonParam", targetNamespace = "")
        java.lang.String jsonParam
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getYYPGAData", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.GetYYPGAData")
    @WebMethod
    @ResponseWrapper(localName = "getYYPGADataResponse", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.GetYYPGADataResponse")
    public java.lang.String getYYPGAData(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "selectReglintstList", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.SelectReglintstList")
    @WebMethod
    @ResponseWrapper(localName = "selectReglintstListResponse", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.SelectReglintstListResponse")
    public java.lang.String selectReglintstList(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryInterestInfoByJoinId", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.QueryInterestInfoByJoinId")
    @WebMethod
    @ResponseWrapper(localName = "queryInterestInfoByJoinIdResponse", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.QueryInterestInfoByJoinIdResponse")
    public java.lang.String queryInterestInfoByJoinId(
        @WebParam(name = "jsonParam", targetNamespace = "")
        java.lang.String jsonParam
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryJoinInfoByUserIdAndJoinId", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.QueryJoinInfoByUserIdAndJoinId")
    @WebMethod
    @ResponseWrapper(localName = "queryJoinInfoByUserIdAndJoinIdResponse", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.QueryJoinInfoByUserIdAndJoinIdResponse")
    public java.lang.String queryJoinInfoByUserIdAndJoinId(
        @WebParam(name = "jsonParam", targetNamespace = "")
        java.lang.String jsonParam
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryReglIntsById", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.QueryReglIntsById")
    @WebMethod
    @ResponseWrapper(localName = "queryReglIntsByIdResponse", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.QueryReglIntsByIdResponse")
    public java.lang.String queryReglIntsById(
        @WebParam(name = "jsonParam", targetNamespace = "")
        java.lang.String jsonParam
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryReglintstInfoById", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.QueryReglintstInfoById")
    @WebMethod
    @ResponseWrapper(localName = "queryReglintstInfoByIdResponse", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.QueryReglintstInfoByIdResponse")
    public java.lang.String queryReglintstInfoById(
        @WebParam(name = "jsonParam", targetNamespace = "")
        java.lang.String jsonParam
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryReglintstJoinInfo", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.QueryReglintstJoinInfo")
    @WebMethod
    @ResponseWrapper(localName = "queryReglintstJoinInfoResponse", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.QueryReglintstJoinInfoResponse")
    public java.lang.String queryReglintstJoinInfo(
        @WebParam(name = "jsonParam", targetNamespace = "")
        java.lang.String jsonParam
    );
}
