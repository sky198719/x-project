package com.xxdai.external.feedback.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.12
 * 2016-06-02T17:24:32.950+08:00
 * Generated source version: 2.7.12
 * 
 */
@WebService(targetNamespace = "http://webservice.feedback.xxdai.com/", name = "FeedbackCXFService")
@XmlSeeAlso({ObjectFactory.class})
public interface FeedbackCXFService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "addFeedback", targetNamespace = "http://webservice.feedback.xxdai.com/", className = "com.xxdai.external.feedback.ws.AddFeedback")
    @WebMethod
    @ResponseWrapper(localName = "addFeedbackResponse", targetNamespace = "http://webservice.feedback.xxdai.com/", className = "com.xxdai.external.feedback.ws.AddFeedbackResponse")
    public java.lang.String addFeedback(
        @WebParam(name = "jsonString", targetNamespace = "")
        java.lang.String jsonString
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getFeedbackSearch", targetNamespace = "http://webservice.feedback.xxdai.com/", className = "com.xxdai.external.feedback.ws.GetFeedbackSearch")
    @WebMethod
    @ResponseWrapper(localName = "getFeedbackSearchResponse", targetNamespace = "http://webservice.feedback.xxdai.com/", className = "com.xxdai.external.feedback.ws.GetFeedbackSearchResponse")
    public java.lang.String getFeedbackSearch(
        @WebParam(name = "jsonString", targetNamespace = "")
        java.lang.String jsonString
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryFeedbackValue", targetNamespace = "http://webservice.feedback.xxdai.com/", className = "com.xxdai.external.feedback.ws.QueryFeedbackValue")
    @WebMethod
    @ResponseWrapper(localName = "queryFeedbackValueResponse", targetNamespace = "http://webservice.feedback.xxdai.com/", className = "com.xxdai.external.feedback.ws.QueryFeedbackValueResponse")
    public java.lang.String queryFeedbackValue(
        @WebParam(name = "code", targetNamespace = "")
        java.lang.String code
    );
}