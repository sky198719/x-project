package com.xxdai.external.gamebwnh.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.12
 * 2016-07-29T13:58:21.904+08:00
 * Generated source version: 2.7.12
 * 
 */
@WebService(targetNamespace = "http://webservice.weixin.xxdai.com/", name = "WeixinDFJGameCXFService")
@XmlSeeAlso({ObjectFactory.class})
public interface WeixinDFJGameCXFService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "goalExchangeCoin", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.gamebwnh.ws.GoalExchangeCoin")
    @WebMethod
    @ResponseWrapper(localName = "goalExchangeCoinResponse", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.gamebwnh.ws.GoalExchangeCoinResponse")
    public java.lang.String goalExchangeCoin(
        @WebParam(name = "param", targetNamespace = "")
        java.lang.String param
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "shareThenGetChange", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.gamebwnh.ws.ShareThenGetChange")
    @WebMethod
    @ResponseWrapper(localName = "shareThenGetChangeResponse", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.gamebwnh.ws.ShareThenGetChangeResponse")
    public java.lang.String shareThenGetChange(
        @WebParam(name = "param", targetNamespace = "")
        java.lang.String param
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "addGameChances", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.gamebwnh.ws.AddGameChances")
    @WebMethod
    @ResponseWrapper(localName = "addGameChancesResponse", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.gamebwnh.ws.AddGameChancesResponse")
    public java.lang.String addGameChances(
        @WebParam(name = "param", targetNamespace = "")
        java.lang.String param
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "ifCanPlayDfjGanme", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.gamebwnh.ws.IfCanPlayDfjGanme")
    @WebMethod
    @ResponseWrapper(localName = "ifCanPlayDfjGanmeResponse", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.gamebwnh.ws.IfCanPlayDfjGanmeResponse")
    public java.lang.String ifCanPlayDfjGanme(
        @WebParam(name = "param", targetNamespace = "")
        java.lang.String param
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryGoalDetails", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.gamebwnh.ws.QueryGoalDetails")
    @WebMethod
    @ResponseWrapper(localName = "queryGoalDetailsResponse", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.gamebwnh.ws.QueryGoalDetailsResponse")
    public java.lang.String queryGoalDetails(
        @WebParam(name = "param", targetNamespace = "")
        java.lang.String param
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "recordGameGoals", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.gamebwnh.ws.RecordGameGoals")
    @WebMethod
    @ResponseWrapper(localName = "recordGameGoalsResponse", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.gamebwnh.ws.RecordGameGoalsResponse")
    public java.lang.String recordGameGoals(
        @WebParam(name = "param", targetNamespace = "")
        java.lang.String param
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "substractGameChances", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.gamebwnh.ws.SubstractGameChances")
    @WebMethod
    @ResponseWrapper(localName = "substractGameChancesResponse", targetNamespace = "http://webservice.weixin.xxdai.com/", className = "com.xxdai.external.gamebwnh.ws.SubstractGameChancesResponse")
    public java.lang.String substractGameChances(
        @WebParam(name = "param", targetNamespace = "")
        java.lang.String param
    );
}