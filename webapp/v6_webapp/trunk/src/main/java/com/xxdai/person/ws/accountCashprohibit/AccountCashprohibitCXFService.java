package com.xxdai.person.ws.accountCashprohibit;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.12
 * 2016-09-27T10:17:43.431+08:00
 * Generated source version: 2.7.12
 * 
 */
@WebService(targetNamespace = "http://interfaces.webService.xxdai.com/", name = "AccountCashprohibitCXFService")
@XmlSeeAlso({ObjectFactory.class})
public interface AccountCashprohibitCXFService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "selectByUserIdIsFS", targetNamespace = "http://interfaces.webService.xxdai.com/", className = "com.xxdai.person.ws.accountCashprohibit.SelectByUserIdIsFS")
    @WebMethod
    @ResponseWrapper(localName = "selectByUserIdIsFSResponse", targetNamespace = "http://interfaces.webService.xxdai.com/", className = "com.xxdai.person.ws.accountCashprohibit.SelectByUserIdIsFSResponse")
    public java.lang.String selectByUserIdIsFS(
        @WebParam(name = "jsonStr", targetNamespace = "")
        java.lang.String jsonStr
    );
}