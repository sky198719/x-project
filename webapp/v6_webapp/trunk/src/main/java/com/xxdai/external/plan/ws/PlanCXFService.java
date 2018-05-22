package com.xxdai.external.plan.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.12
 * 2016-12-01T15:07:54.728+08:00
 * Generated source version: 2.7.12
 * 
 */
@WebService(targetNamespace = "http://webservice.plan.xxdai.com/", name = "PlanCXFService")
@XmlSeeAlso({ObjectFactory.class})
public interface PlanCXFService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "generateNextPname", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.GenerateNextPname")
    @WebMethod
    @ResponseWrapper(localName = "generateNextPnameResponse", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.GenerateNextPnameResponse")
    public java.lang.String generateNextPname();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "repealScheme", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.RepealScheme")
    @WebMethod
    @ResponseWrapper(localName = "repealSchemeResponse", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.RepealSchemeResponse")
    public java.lang.String repealScheme(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getAccounLogsOfMyScheme", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.GetAccounLogsOfMyScheme")
    @WebMethod
    @ResponseWrapper(localName = "getAccounLogsOfMySchemeResponse", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.GetAccounLogsOfMySchemeResponse")
    public java.lang.String getAccounLogsOfMyScheme(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getPlanGAData", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.GetPlanGAData")
    @WebMethod
    @ResponseWrapper(localName = "getPlanGADataResponse", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.GetPlanGADataResponse")
    public java.lang.String getPlanGAData(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getUserMoney", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.GetUserMoney")
    @WebMethod
    @ResponseWrapper(localName = "getUserMoneyResponse", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.GetUserMoneyResponse")
    public java.lang.String getUserMoney(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "quitScheme", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.QuitScheme")
    @WebMethod
    @ResponseWrapper(localName = "quitSchemeResponse", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.QuitSchemeResponse")
    public java.lang.String quitScheme(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getSumUserTenderByDate", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.GetSumUserTenderByDate")
    @WebMethod
    @ResponseWrapper(localName = "getSumUserTenderByDateResponse", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.GetSumUserTenderByDateResponse")
    public java.lang.String getSumUserTenderByDate(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getMySchemeList", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.GetMySchemeList")
    @WebMethod
    @ResponseWrapper(localName = "getMySchemeListResponse", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.GetMySchemeListResponse")
    public java.lang.String getMySchemeList(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "addScheme", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.AddScheme")
    @WebMethod
    @ResponseWrapper(localName = "addSchemeResponse", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.AddSchemeResponse")
    public java.lang.String addScheme(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "expireQuitScheme", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.ExpireQuitScheme")
    @WebMethod
    @ResponseWrapper(localName = "expireQuitSchemeResponse", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.ExpireQuitSchemeResponse")
    public java.lang.String expireQuitScheme(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getTransferListOfMyScheme", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.GetTransferListOfMyScheme")
    @WebMethod
    @ResponseWrapper(localName = "getTransferListOfMySchemeResponse", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.GetTransferListOfMySchemeResponse")
    public java.lang.String getTransferListOfMyScheme(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryUserDetailPage", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.QueryUserDetailPage")
    @WebMethod
    @ResponseWrapper(localName = "queryUserDetailPageResponse", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.QueryUserDetailPageResponse")
    public java.lang.String queryUserDetailPage(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "buyScheme", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.BuyScheme")
    @WebMethod
    @ResponseWrapper(localName = "buySchemeResponse", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.BuySchemeResponse")
    public java.lang.String buyScheme(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "planExpireQuitProfitNotEnough", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.PlanExpireQuitProfitNotEnough")
    @WebMethod
    @ResponseWrapper(localName = "planExpireQuitProfitNotEnoughResponse", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.PlanExpireQuitProfitNotEnoughResponse")
    public java.lang.String planExpireQuitProfitNotEnough(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getUserSchemeList", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.GetUserSchemeList")
    @WebMethod
    @ResponseWrapper(localName = "getUserSchemeListResponse", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.GetUserSchemeListResponse")
    public java.lang.String getUserSchemeList(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "selectUserSchemeByUserIdSchemeId", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.SelectUserSchemeByUserIdSchemeId")
    @WebMethod
    @ResponseWrapper(localName = "selectUserSchemeByUserIdSchemeIdResponse", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.SelectUserSchemeByUserIdSchemeIdResponse")
    public java.lang.String selectUserSchemeByUserIdSchemeId(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getAccounLogsOfYyp", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.GetAccounLogsOfYyp")
    @WebMethod
    @ResponseWrapper(localName = "getAccounLogsOfYypResponse", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.GetAccounLogsOfYypResponse")
    public java.lang.String getAccounLogsOfYyp(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getYypBorrowList", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.GetYypBorrowList")
    @WebMethod
    @ResponseWrapper(localName = "getYypBorrowListResponse", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.GetYypBorrowListResponse")
    public java.lang.String getYypBorrowList(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryUserDetailList", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.QueryUserDetailList")
    @WebMethod
    @ResponseWrapper(localName = "queryUserDetailListResponse", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.QueryUserDetailListResponse")
    public java.lang.String queryUserDetailList(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "updateScheme", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.UpdateScheme")
    @WebMethod
    @ResponseWrapper(localName = "updateSchemeResponse", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.UpdateSchemeResponse")
    public java.lang.String updateScheme(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getUserSchemeInfo", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.GetUserSchemeInfo")
    @WebMethod
    @ResponseWrapper(localName = "getUserSchemeInfoResponse", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.GetUserSchemeInfoResponse")
    public java.lang.String getUserSchemeInfo(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getTotalScheme", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.GetTotalScheme")
    @WebMethod
    @ResponseWrapper(localName = "getTotalSchemeResponse", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.GetTotalSchemeResponse")
    public java.lang.String getTotalScheme(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryAgreementVO", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.QueryAgreementVO")
    @WebMethod
    @ResponseWrapper(localName = "queryAgreementVOResponse", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.QueryAgreementVOResponse")
    public java.lang.String queryAgreementVO(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getSchemeInfo", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.GetSchemeInfo")
    @WebMethod
    @ResponseWrapper(localName = "getSchemeInfoResponse", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.GetSchemeInfoResponse")
    public java.lang.String getSchemeInfo(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getSchemeList", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.GetSchemeList")
    @WebMethod
    @ResponseWrapper(localName = "getSchemeListResponse", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.GetSchemeListResponse")
    public java.lang.String getSchemeList(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getBorrowListOfMyScheme", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.GetBorrowListOfMyScheme")
    @WebMethod
    @ResponseWrapper(localName = "getBorrowListOfMySchemeResponse", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.GetBorrowListOfMySchemeResponse")
    public java.lang.String getBorrowListOfMyScheme(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "planJoinNums", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.PlanJoinNums")
    @WebMethod
    @ResponseWrapper(localName = "planJoinNumsResponse", targetNamespace = "http://webservice.plan.xxdai.com/", className = "com.xxdai.external.plan.ws.PlanJoinNumsResponse")
    public java.lang.String planJoinNums();
}
