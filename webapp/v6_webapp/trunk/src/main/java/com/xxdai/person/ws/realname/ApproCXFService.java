package com.xxdai.person.ws.realname;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.12
 * 2014-09-03T09:35:49.293+08:00
 * Generated source version: 2.7.12
 * 
 */
@WebService(targetNamespace = "http://appro.interfaces.webService.xxdai.com/", name = "ApproCXFService")
@XmlSeeAlso({ObjectFactory.class})
public interface ApproCXFService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "checkRealName", targetNamespace = "http://appro.interfaces.webService.xxdai.com/", className = "com.xxdai.person.ws.realname.CheckRealName")
    @WebMethod
    @ResponseWrapper(localName = "checkRealNameResponse", targetNamespace = "http://appro.interfaces.webService.xxdai.com/", className = "com.xxdai.person.ws.realname.CheckRealNameResponse")
    public java.lang.String checkRealName(
        @WebParam(name = "jsonstring", targetNamespace = "")
        java.lang.String jsonstring
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "checkSmsMsg", targetNamespace = "http://appro.interfaces.webService.xxdai.com/", className = "com.xxdai.person.ws.realname.CheckSmsMsg")
    @WebMethod
    @ResponseWrapper(localName = "checkSmsMsgResponse", targetNamespace = "http://appro.interfaces.webService.xxdai.com/", className = "com.xxdai.person.ws.realname.CheckSmsMsgResponse")
    public java.lang.String checkSmsMsg(
        @WebParam(name = "jsonstring", targetNamespace = "")
        java.lang.String jsonstring
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "sendEmailMsg", targetNamespace = "http://appro.interfaces.webService.xxdai.com/", className = "com.xxdai.person.ws.realname.SendEmailMsg")
    @WebMethod
    @ResponseWrapper(localName = "sendEmailMsgResponse", targetNamespace = "http://appro.interfaces.webService.xxdai.com/", className = "com.xxdai.person.ws.realname.SendEmailMsgResponse")
    public java.lang.String sendEmailMsg(
        @WebParam(name = "jsonstring", targetNamespace = "")
        java.lang.String jsonstring
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "sendSmsMsgFromReg", targetNamespace = "http://appro.interfaces.webService.xxdai.com/", className = "com.xxdai.person.ws.realname.SendSmsMsgFromReg")
    @WebMethod
    @ResponseWrapper(localName = "sendSmsMsgFromRegResponse", targetNamespace = "http://appro.interfaces.webService.xxdai.com/", className = "com.xxdai.person.ws.realname.SendSmsMsgFromRegResponse")
    public java.lang.String sendSmsMsgFromReg(
        @WebParam(name = "jsonstring", targetNamespace = "")
        java.lang.String jsonstring
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "checkVip", targetNamespace = "http://appro.interfaces.webService.xxdai.com/", className = "com.xxdai.person.ws.realname.CheckVip")
    @WebMethod
    @ResponseWrapper(localName = "checkVipResponse", targetNamespace = "http://appro.interfaces.webService.xxdai.com/", className = "com.xxdai.person.ws.realname.CheckVipResponse")
    public java.lang.String checkVip(
        @WebParam(name = "jsonstring", targetNamespace = "")
        java.lang.String jsonstring
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "auditVip", targetNamespace = "http://appro.interfaces.webService.xxdai.com/", className = "com.xxdai.person.ws.realname.AuditVip")
    @WebMethod
    @ResponseWrapper(localName = "auditVipResponse", targetNamespace = "http://appro.interfaces.webService.xxdai.com/", className = "com.xxdai.person.ws.realname.AuditVipResponse")
    public java.lang.String auditVip(
        @WebParam(name = "jsonstring", targetNamespace = "")
        java.lang.String jsonstring
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "sendSmsMsg", targetNamespace = "http://appro.interfaces.webService.xxdai.com/", className = "com.xxdai.person.ws.realname.SendSmsMsg")
    @WebMethod
    @ResponseWrapper(localName = "sendSmsMsgResponse", targetNamespace = "http://appro.interfaces.webService.xxdai.com/", className = "com.xxdai.person.ws.realname.SendSmsMsgResponse")
    public java.lang.String sendSmsMsg(
        @WebParam(name = "jsonstring", targetNamespace = "")
        java.lang.String jsonstring
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "auditRealName", targetNamespace = "http://appro.interfaces.webService.xxdai.com/", className = "com.xxdai.person.ws.realname.AuditRealName")
    @WebMethod
    @ResponseWrapper(localName = "auditRealNameResponse", targetNamespace = "http://appro.interfaces.webService.xxdai.com/", className = "com.xxdai.person.ws.realname.AuditRealNameResponse")
    public java.lang.String auditRealName(
        @WebParam(name = "jsonstring", targetNamespace = "")
        java.lang.String jsonstring
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "checkEmailMsg", targetNamespace = "http://appro.interfaces.webService.xxdai.com/", className = "com.xxdai.person.ws.realname.CheckEmailMsg")
    @WebMethod
    @ResponseWrapper(localName = "checkEmailMsgResponse", targetNamespace = "http://appro.interfaces.webService.xxdai.com/", className = "com.xxdai.person.ws.realname.CheckEmailMsgResponse")
    public java.lang.String checkEmailMsg(
        @WebParam(name = "jsonstring", targetNamespace = "")
        java.lang.String jsonstring
    );
}