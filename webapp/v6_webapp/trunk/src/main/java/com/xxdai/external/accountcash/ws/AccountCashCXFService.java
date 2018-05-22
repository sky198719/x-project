package com.xxdai.external.accountcash.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.12
 * 2017-03-06T17:27:11.288+08:00
 * Generated source version: 2.7.12
 * 
 */
@WebService(targetNamespace = "http://webservice.account.xxdai.com/", name = "AccountCashCXFService")
@XmlSeeAlso({ObjectFactory.class})
public interface AccountCashCXFService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "cashAccount", targetNamespace = "http://webservice.account.xxdai.com/", className = "com.xxdai.external.accountcash.ws.CashAccount")
    @WebMethod
    @ResponseWrapper(localName = "cashAccountResponse", targetNamespace = "http://webservice.account.xxdai.com/", className = "com.xxdai.external.accountcash.ws.CashAccountResponse")
    public java.lang.String cashAccount(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "remitMultiAccountCash", targetNamespace = "http://webservice.account.xxdai.com/", className = "com.xxdai.external.accountcash.ws.RemitMultiAccountCash")
    @WebMethod
    @ResponseWrapper(localName = "remitMultiAccountCashResponse", targetNamespace = "http://webservice.account.xxdai.com/", className = "com.xxdai.external.accountcash.ws.RemitMultiAccountCashResponse")
    public java.lang.String remitMultiAccountCash(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getDrawMoneyCountMonthly", targetNamespace = "http://webservice.account.xxdai.com/", className = "com.xxdai.external.accountcash.ws.GetDrawMoneyCountMonthly")
    @WebMethod
    @ResponseWrapper(localName = "getDrawMoneyCountMonthlyResponse", targetNamespace = "http://webservice.account.xxdai.com/", className = "com.xxdai.external.accountcash.ws.GetDrawMoneyCountMonthlyResponse")
    public java.lang.String getDrawMoneyCountMonthly(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "selectRemitAccountCashWithPaging", targetNamespace = "http://webservice.account.xxdai.com/", className = "com.xxdai.external.accountcash.ws.SelectRemitAccountCashWithPaging")
    @WebMethod
    @ResponseWrapper(localName = "selectRemitAccountCashWithPagingResponse", targetNamespace = "http://webservice.account.xxdai.com/", className = "com.xxdai.external.accountcash.ws.SelectRemitAccountCashWithPagingResponse")
    public java.lang.String selectRemitAccountCashWithPaging(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "selectRefundCashById", targetNamespace = "http://webservice.account.xxdai.com/", className = "com.xxdai.external.accountcash.ws.SelectRefundCashById")
    @WebMethod
    @ResponseWrapper(localName = "selectRefundCashByIdResponse", targetNamespace = "http://webservice.account.xxdai.com/", className = "com.xxdai.external.accountcash.ws.SelectRefundCashByIdResponse")
    public java.lang.String selectRefundCashById(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "refundCash", targetNamespace = "http://webservice.account.xxdai.com/", className = "com.xxdai.external.accountcash.ws.RefundCash")
    @WebMethod
    @ResponseWrapper(localName = "refundCashResponse", targetNamespace = "http://webservice.account.xxdai.com/", className = "com.xxdai.external.accountcash.ws.RefundCashResponse")
    public java.lang.String refundCash(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "selectApprovedAccountCashs", targetNamespace = "http://webservice.account.xxdai.com/", className = "com.xxdai.external.accountcash.ws.SelectApprovedAccountCashs")
    @WebMethod
    @ResponseWrapper(localName = "selectApprovedAccountCashsResponse", targetNamespace = "http://webservice.account.xxdai.com/", className = "com.xxdai.external.accountcash.ws.SelectApprovedAccountCashsResponse")
    public java.lang.String selectApprovedAccountCashs(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getSumcashAmount", targetNamespace = "http://webservice.account.xxdai.com/", className = "com.xxdai.external.accountcash.ws.GetSumcashAmount")
    @WebMethod
    @ResponseWrapper(localName = "getSumcashAmountResponse", targetNamespace = "http://webservice.account.xxdai.com/", className = "com.xxdai.external.accountcash.ws.GetSumcashAmountResponse")
    public java.lang.String getSumcashAmount(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "verifyMultiAccountCash", targetNamespace = "http://webservice.account.xxdai.com/", className = "com.xxdai.external.accountcash.ws.VerifyMultiAccountCash")
    @WebMethod
    @ResponseWrapper(localName = "verifyMultiAccountCashResponse", targetNamespace = "http://webservice.account.xxdai.com/", className = "com.xxdai.external.accountcash.ws.VerifyMultiAccountCashResponse")
    public java.lang.String verifyMultiAccountCash(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "selectAccountCashByUser", targetNamespace = "http://webservice.account.xxdai.com/", className = "com.xxdai.external.accountcash.ws.SelectAccountCashByUser")
    @WebMethod
    @ResponseWrapper(localName = "selectAccountCashByUserResponse", targetNamespace = "http://webservice.account.xxdai.com/", className = "com.xxdai.external.accountcash.ws.SelectAccountCashByUserResponse")
    public java.lang.String selectAccountCashByUser(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "selectAccountCashById", targetNamespace = "http://webservice.account.xxdai.com/", className = "com.xxdai.external.accountcash.ws.SelectAccountCashById")
    @WebMethod
    @ResponseWrapper(localName = "selectAccountCashByIdResponse", targetNamespace = "http://webservice.account.xxdai.com/", className = "com.xxdai.external.accountcash.ws.SelectAccountCashByIdResponse")
    public java.lang.String selectAccountCashById(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "selectVerifyAccountCashWithPaging", targetNamespace = "http://webservice.account.xxdai.com/", className = "com.xxdai.external.accountcash.ws.SelectVerifyAccountCashWithPaging")
    @WebMethod
    @ResponseWrapper(localName = "selectVerifyAccountCashWithPagingResponse", targetNamespace = "http://webservice.account.xxdai.com/", className = "com.xxdai.external.accountcash.ws.SelectVerifyAccountCashWithPagingResponse")
    public java.lang.String selectVerifyAccountCashWithPaging(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getAccountCost", targetNamespace = "http://webservice.account.xxdai.com/", className = "com.xxdai.external.accountcash.ws.GetAccountCost")
    @WebMethod
    @ResponseWrapper(localName = "getAccountCostResponse", targetNamespace = "http://webservice.account.xxdai.com/", className = "com.xxdai.external.accountcash.ws.GetAccountCostResponse")
    public java.lang.String getAccountCost(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getMaxcashAmount", targetNamespace = "http://webservice.account.xxdai.com/", className = "com.xxdai.external.accountcash.ws.GetMaxcashAmount")
    @WebMethod
    @ResponseWrapper(localName = "getMaxcashAmountResponse", targetNamespace = "http://webservice.account.xxdai.com/", className = "com.xxdai.external.accountcash.ws.GetMaxcashAmountResponse")
    public java.lang.String getMaxcashAmount(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "selectFailedAccountCashWithPaging", targetNamespace = "http://webservice.account.xxdai.com/", className = "com.xxdai.external.accountcash.ws.SelectFailedAccountCashWithPaging")
    @WebMethod
    @ResponseWrapper(localName = "selectFailedAccountCashWithPagingResponse", targetNamespace = "http://webservice.account.xxdai.com/", className = "com.xxdai.external.accountcash.ws.SelectFailedAccountCashWithPagingResponse")
    public java.lang.String selectFailedAccountCashWithPaging(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "verifyAccountCash", targetNamespace = "http://webservice.account.xxdai.com/", className = "com.xxdai.external.accountcash.ws.VerifyAccountCash")
    @WebMethod
    @ResponseWrapper(localName = "verifyAccountCashResponse", targetNamespace = "http://webservice.account.xxdai.com/", className = "com.xxdai.external.accountcash.ws.VerifyAccountCashResponse")
    public java.lang.String verifyAccountCash(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "canGetCash", targetNamespace = "http://webservice.account.xxdai.com/", className = "com.xxdai.external.accountcash.ws.CanGetCash")
    @WebMethod
    @ResponseWrapper(localName = "canGetCashResponse", targetNamespace = "http://webservice.account.xxdai.com/", className = "com.xxdai.external.accountcash.ws.CanGetCashResponse")
    public java.lang.String canGetCash(
        @WebParam(name = "userIdJson", targetNamespace = "")
        java.lang.String userIdJson
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "remitAccountCash", targetNamespace = "http://webservice.account.xxdai.com/", className = "com.xxdai.external.accountcash.ws.RemitAccountCash")
    @WebMethod
    @ResponseWrapper(localName = "remitAccountCashResponse", targetNamespace = "http://webservice.account.xxdai.com/", className = "com.xxdai.external.accountcash.ws.RemitAccountCashResponse")
    public java.lang.String remitAccountCash(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );
}