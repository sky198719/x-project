package com.xxdai.external.borrowTrade.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.12
 * 2015-12-31T17:15:38.845+08:00
 * Generated source version: 2.7.12
 * 
 */
@WebService(targetNamespace = "http://webservice.borrow.xxdai.com/", name = "BorrowTradeCXFService")
@XmlSeeAlso({ObjectFactory.class})
public interface BorrowTradeCXFService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "returnFrozenAccountForInstallment", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowTrade.ws.ReturnFrozenAccountForInstallment")
    @WebMethod
    @ResponseWrapper(localName = "returnFrozenAccountForInstallmentResponse", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowTrade.ws.ReturnFrozenAccountForInstallmentResponse")
    public java.lang.String returnFrozenAccountForInstallment(
        @WebParam(name = "param", targetNamespace = "")
        java.lang.String param
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "tenderXxbaoBorrow", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowTrade.ws.TenderXxbaoBorrow")
    @WebMethod
    @ResponseWrapper(localName = "tenderXxbaoBorrowResponse", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowTrade.ws.TenderXxbaoBorrowResponse")
    public java.lang.String tenderXxbaoBorrow(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "flowBorrow", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowTrade.ws.FlowBorrow")
    @WebMethod
    @ResponseWrapper(localName = "flowBorrowResponse", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowTrade.ws.FlowBorrowResponse")
    public java.lang.String flowBorrow(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "tenderCommProdBorrow", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowTrade.ws.TenderCommProdBorrow")
    @WebMethod
    @ResponseWrapper(localName = "tenderCommProdBorrowResponse", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowTrade.ws.TenderCommProdBorrowResponse")
    public java.lang.String tenderCommProdBorrow(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryUserBorrowTender", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowTrade.ws.QueryUserBorrowTender")
    @WebMethod
    @ResponseWrapper(localName = "queryUserBorrowTenderResponse", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowTrade.ws.QueryUserBorrowTenderResponse")
    public java.lang.String queryUserBorrowTender(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "transferMarketPrizeAccount", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowTrade.ws.TransferMarketPrizeAccount")
    @WebMethod
    @ResponseWrapper(localName = "transferMarketPrizeAccountResponse", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowTrade.ws.TransferMarketPrizeAccountResponse")
    public java.lang.String transferMarketPrizeAccount(
        @WebParam(name = "param", targetNamespace = "")
        java.lang.String param
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryUserCommProdBorrowSum", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowTrade.ws.QueryUserCommProdBorrowSum")
    @WebMethod
    @ResponseWrapper(localName = "queryUserCommProdBorrowSumResponse", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowTrade.ws.QueryUserCommProdBorrowSumResponse")
    public java.lang.String queryUserCommProdBorrowSum(
        @WebParam(name = "param", targetNamespace = "")
        java.lang.String param
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "frozenAccountForInstallment", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowTrade.ws.FrozenAccountForInstallment")
    @WebMethod
    @ResponseWrapper(localName = "frozenAccountForInstallmentResponse", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowTrade.ws.FrozenAccountForInstallmentResponse")
    public java.lang.String frozenAccountForInstallment(
        @WebParam(name = "param", targetNamespace = "")
        java.lang.String param
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryUserCanBorrowSum", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowTrade.ws.QueryUserCanBorrowSum")
    @WebMethod
    @ResponseWrapper(localName = "queryUserCanBorrowSumResponse", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowTrade.ws.QueryUserCanBorrowSumResponse")
    public java.lang.String queryUserCanBorrowSum(
        @WebParam(name = "param", targetNamespace = "")
        java.lang.String param
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "tenderBorrow", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowTrade.ws.TenderBorrow")
    @WebMethod
    @ResponseWrapper(localName = "tenderBorrowResponse", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowTrade.ws.TenderBorrowResponse")
    public java.lang.String tenderBorrow(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );
}