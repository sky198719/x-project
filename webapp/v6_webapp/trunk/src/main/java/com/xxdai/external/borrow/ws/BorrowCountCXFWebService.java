package com.xxdai.external.borrow.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.0.1
 * 2014-10-30T13:13:53.676+08:00
 * Generated source version: 3.0.1
 * 
 */
@WebService(targetNamespace = "http://webservice.borrow.xxdai.com/", name = "BorrowCountCXFWebService")
@XmlSeeAlso({ObjectFactory.class})
public interface BorrowCountCXFWebService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryBorrowCountByMyAccountPages", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.borrow.ws.QueryBorrowCountByMyAccountPages")
    @WebMethod
    @ResponseWrapper(localName = "queryBorrowCountByMyAccountPagesResponse", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.borrow.ws.QueryBorrowCountByMyAccountPagesResponse")
    public java.lang.String queryBorrowCountByMyAccountPages(
        @WebParam(name = "jsonstring", targetNamespace = "")
        java.lang.String jsonstring
    );
}