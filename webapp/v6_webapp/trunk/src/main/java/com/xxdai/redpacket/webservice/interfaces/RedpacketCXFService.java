package com.xxdai.redpacket.webservice.interfaces;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.6
 * 2015-04-20T15:37:47.842+08:00
 * Generated source version: 2.7.6
 * 
 */
@WebService(targetNamespace = "http://interfaces.webservice.redpacket.xxdai.com/", name = "RedpacketCXFService")
@XmlSeeAlso({ObjectFactory.class})
public interface RedpacketCXFService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getRedpacketListByUseCodition", targetNamespace = "http://interfaces.webservice.redpacket.xxdai.com/", className = "com.xxdai.redpacket.webservice.interfaces.GetRedpacketListByUseCodition")
    @WebMethod
    @ResponseWrapper(localName = "getRedpacketListByUseCoditionResponse", targetNamespace = "http://interfaces.webservice.redpacket.xxdai.com/", className = "com.xxdai.redpacket.webservice.interfaces.GetRedpacketListByUseCoditionResponse")
    public java.lang.String getRedpacketListByUseCodition(
        @WebParam(name = "redpacketJson", targetNamespace = "")
        java.lang.String redpacketJson
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getRedpacketTotal", targetNamespace = "http://interfaces.webservice.redpacket.xxdai.com/", className = "com.xxdai.redpacket.webservice.interfaces.GetRedpacketTotal")
    @WebMethod
    @ResponseWrapper(localName = "getRedpacketTotalResponse", targetNamespace = "http://interfaces.webservice.redpacket.xxdai.com/", className = "com.xxdai.redpacket.webservice.interfaces.GetRedpacketTotalResponse")
    public java.lang.String getRedpacketTotal(
        @WebParam(name = "redpacketJson", targetNamespace = "")
        java.lang.String redpacketJson
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getRedpacketList", targetNamespace = "http://interfaces.webservice.redpacket.xxdai.com/", className = "com.xxdai.redpacket.webservice.interfaces.GetRedpacketList")
    @WebMethod
    @ResponseWrapper(localName = "getRedpacketListResponse", targetNamespace = "http://interfaces.webservice.redpacket.xxdai.com/", className = "com.xxdai.redpacket.webservice.interfaces.GetRedpacketListResponse")
    public java.lang.String getRedpacketList(
        @WebParam(name = "redpacketJson", targetNamespace = "")
        java.lang.String redpacketJson
    );
}