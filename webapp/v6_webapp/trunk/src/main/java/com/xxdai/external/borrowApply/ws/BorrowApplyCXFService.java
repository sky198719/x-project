package com.xxdai.external.borrowApply.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.12
 * 2016-12-15T16:58:30.795+08:00
 * Generated source version: 2.7.12
 * 
 */
@WebService(targetNamespace = "http://webservice.borrow.xxdai.com/", name = "BorrowApplyCXFService")
@XmlSeeAlso({ObjectFactory.class})
public interface BorrowApplyCXFService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "saveBorrowApplyBaseInfoXyd", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowApply.ws.SaveBorrowApplyBaseInfoXyd")
    @WebMethod
    @ResponseWrapper(localName = "saveBorrowApplyBaseInfoXydResponse", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowApply.ws.SaveBorrowApplyBaseInfoXydResponse")
    public java.lang.String saveBorrowApplyBaseInfoXyd(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryUploadPicPathXyd", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowApply.ws.QueryUploadPicPathXyd")
    @WebMethod
    @ResponseWrapper(localName = "queryUploadPicPathXydResponse", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowApply.ws.QueryUploadPicPathXydResponse")
    public java.lang.String queryUploadPicPathXyd(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "approveBorrowApply", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowApply.ws.ApproveBorrowApply")
    @WebMethod
    @ResponseWrapper(localName = "approveBorrowApplyResponse", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowApply.ws.ApproveBorrowApplyResponse")
    public java.lang.String approveBorrowApply(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryAllUploadPicPathXyd", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowApply.ws.QueryAllUploadPicPathXyd")
    @WebMethod
    @ResponseWrapper(localName = "queryAllUploadPicPathXydResponse", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowApply.ws.QueryAllUploadPicPathXydResponse")
    public java.lang.String queryAllUploadPicPathXyd(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryBorrowApplyBaseInfoXyd", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowApply.ws.QueryBorrowApplyBaseInfoXyd")
    @WebMethod
    @ResponseWrapper(localName = "queryBorrowApplyBaseInfoXydResponse", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowApply.ws.QueryBorrowApplyBaseInfoXydResponse")
    public java.lang.String queryBorrowApplyBaseInfoXyd(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryBorrowApplyBorrowInfoXyd", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowApply.ws.QueryBorrowApplyBorrowInfoXyd")
    @WebMethod
    @ResponseWrapper(localName = "queryBorrowApplyBorrowInfoXydResponse", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowApply.ws.QueryBorrowApplyBorrowInfoXydResponse")
    public java.lang.String queryBorrowApplyBorrowInfoXyd(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryXYBorrowApplyList", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowApply.ws.QueryXYBorrowApplyList")
    @WebMethod
    @ResponseWrapper(localName = "queryXYBorrowApplyListResponse", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowApply.ws.QueryXYBorrowApplyListResponse")
    public java.lang.String queryXYBorrowApplyList(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "saveBorrowApplyBorrowInfoXyd", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowApply.ws.SaveBorrowApplyBorrowInfoXyd")
    @WebMethod
    @ResponseWrapper(localName = "saveBorrowApplyBorrowInfoXydResponse", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowApply.ws.SaveBorrowApplyBorrowInfoXydResponse")
    public java.lang.String saveBorrowApplyBorrowInfoXyd(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryXYBorrowApplyDetail", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowApply.ws.QueryXYBorrowApplyDetail")
    @WebMethod
    @ResponseWrapper(localName = "queryXYBorrowApplyDetailResponse", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowApply.ws.QueryXYBorrowApplyDetailResponse")
    public java.lang.String queryXYBorrowApplyDetail(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "updateBorrowApply", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowApply.ws.UpdateBorrowApply")
    @WebMethod
    @ResponseWrapper(localName = "updateBorrowApplyResponse", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowApply.ws.UpdateBorrowApplyResponse")
    public java.lang.String updateBorrowApply(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getChannels", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowApply.ws.GetChannels")
    @WebMethod
    @ResponseWrapper(localName = "getChannelsResponse", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowApply.ws.GetChannelsResponse")
    public java.lang.String getChannels();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "saveBorrowApply", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowApply.ws.SaveBorrowApply")
    @WebMethod
    @ResponseWrapper(localName = "saveBorrowApplyResponse", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowApply.ws.SaveBorrowApplyResponse")
    public java.lang.String saveBorrowApply(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryBorrowApplyById", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowApply.ws.QueryBorrowApplyById")
    @WebMethod
    @ResponseWrapper(localName = "queryBorrowApplyByIdResponse", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowApply.ws.QueryBorrowApplyByIdResponse")
    public java.lang.String queryBorrowApplyById(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "saveUploadPicPathXyd", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowApply.ws.SaveUploadPicPathXyd")
    @WebMethod
    @ResponseWrapper(localName = "saveUploadPicPathXydResponse", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowApply.ws.SaveUploadPicPathXydResponse")
    public java.lang.String saveUploadPicPathXyd(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "saveBorrowApplyXyd", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowApply.ws.SaveBorrowApplyXyd")
    @WebMethod
    @ResponseWrapper(localName = "saveBorrowApplyXydResponse", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowApply.ws.SaveBorrowApplyXydResponse")
    public java.lang.String saveBorrowApplyXyd(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryBorrowApplyList", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowApply.ws.QueryBorrowApplyList")
    @WebMethod
    @ResponseWrapper(localName = "queryBorrowApplyListResponse", targetNamespace = "http://webservice.borrow.xxdai.com/", className = "com.xxdai.external.borrowApply.ws.QueryBorrowApplyListResponse")
    public java.lang.String queryBorrowApplyList(
        @WebParam(name = "params", targetNamespace = "")
        java.lang.String params
    );
}