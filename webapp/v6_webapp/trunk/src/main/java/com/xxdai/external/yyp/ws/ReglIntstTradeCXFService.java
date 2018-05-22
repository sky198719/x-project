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
 * 2016-12-01T15:10:43.429+08:00
 * Generated source version: 2.7.12
 * 
 */
@WebService(targetNamespace = "http://webservice.yyp.xxdai.com/", name = "ReglIntstTradeCXFService")
@XmlSeeAlso({ObjectFactory.class})
public interface ReglIntstTradeCXFService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "buyYyp", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.BuyYyp")
    @WebMethod
    @ResponseWrapper(localName = "buyYypResponse", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.BuyYypResponse")
    public java.lang.String buyYyp(
        @WebParam(name = "jsonParam", targetNamespace = "")
        java.lang.String jsonParam
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "quitYyp", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.QuitYyp")
    @WebMethod
    @ResponseWrapper(localName = "quitYypResponse", targetNamespace = "http://webservice.yyp.xxdai.com/", className = "com.xxdai.external.yyp.ws.QuitYypResponse")
    public java.lang.String quitYyp(
        @WebParam(name = "jsonParam", targetNamespace = "")
        java.lang.String jsonParam
    );
}