package com.xxdai.external.inforDiscloure.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.12
 * 2016-06-03T09:50:25.422+08:00
 * Generated source version: 2.7.12
 * 
 */
@WebService(targetNamespace = "http://webservice.statistics.xxdai.com/", name = "InforDiscloureCXFService")
@XmlSeeAlso({ObjectFactory.class})
public interface InforDiscloureCXFService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryInforList", targetNamespace = "http://webservice.statistics.xxdai.com/", className = "com.xxdai.external.inforDiscloure.ws.QueryInforList")
    @WebMethod
    @ResponseWrapper(localName = "queryInforListResponse", targetNamespace = "http://webservice.statistics.xxdai.com/", className = "com.xxdai.external.inforDiscloure.ws.QueryInforListResponse")
    public java.lang.String queryInforList();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "updateInforStatus", targetNamespace = "http://webservice.statistics.xxdai.com/", className = "com.xxdai.external.inforDiscloure.ws.UpdateInforStatus")
    @WebMethod
    @ResponseWrapper(localName = "updateInforStatusResponse", targetNamespace = "http://webservice.statistics.xxdai.com/", className = "com.xxdai.external.inforDiscloure.ws.UpdateInforStatusResponse")
    public java.lang.String updateInforStatus(
        @WebParam(name = "updInforDiscStatusStr", targetNamespace = "")
        java.lang.String updInforDiscStatusStr
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "addInforDiscloure", targetNamespace = "http://webservice.statistics.xxdai.com/", className = "com.xxdai.external.inforDiscloure.ws.AddInforDiscloure")
    @WebMethod
    @ResponseWrapper(localName = "addInforDiscloureResponse", targetNamespace = "http://webservice.statistics.xxdai.com/", className = "com.xxdai.external.inforDiscloure.ws.AddInforDiscloureResponse")
    public java.lang.String addInforDiscloure(
        @WebParam(name = "addInforDiscloureStr", targetNamespace = "")
        java.lang.String addInforDiscloureStr
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getInforDiscList", targetNamespace = "http://webservice.statistics.xxdai.com/", className = "com.xxdai.external.inforDiscloure.ws.GetInforDiscList")
    @WebMethod
    @ResponseWrapper(localName = "getInforDiscListResponse", targetNamespace = "http://webservice.statistics.xxdai.com/", className = "com.xxdai.external.inforDiscloure.ws.GetInforDiscListResponse")
    public java.lang.String getInforDiscList(
        @WebParam(name = "jsonstring", targetNamespace = "")
        java.lang.String jsonstring
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "updateInforDiscloure", targetNamespace = "http://webservice.statistics.xxdai.com/", className = "com.xxdai.external.inforDiscloure.ws.UpdateInforDiscloure")
    @WebMethod
    @ResponseWrapper(localName = "updateInforDiscloureResponse", targetNamespace = "http://webservice.statistics.xxdai.com/", className = "com.xxdai.external.inforDiscloure.ws.UpdateInforDiscloureResponse")
    public java.lang.String updateInforDiscloure(
        @WebParam(name = "updateInforDiscloure", targetNamespace = "")
        java.lang.String updateInforDiscloure
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getInforDiscloure", targetNamespace = "http://webservice.statistics.xxdai.com/", className = "com.xxdai.external.inforDiscloure.ws.GetInforDiscloure")
    @WebMethod
    @ResponseWrapper(localName = "getInforDiscloureResponse", targetNamespace = "http://webservice.statistics.xxdai.com/", className = "com.xxdai.external.inforDiscloure.ws.GetInforDiscloureResponse")
    public java.lang.String getInforDiscloure(
        @WebParam(name = "jsonstring", targetNamespace = "")
        java.lang.String jsonstring
    );
}
