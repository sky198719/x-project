package com.xxdai.borrow.bo;

import java.util.Date;

/**
 * Created by chaihuangqi on 2017/3/7.
 */
public class CompanyInfo implements java.io.Serializable {
    /**
    经营区域
     */
    private String managementArea;
    /**
    股东类型 1：自然人股东；2：法人股东；3：自然人股东和法人股东
     */
    private String shareHolderType;
    /**
    股东名称
     */
    private String shareHolderName;
    /**
    股东证件类型
     */
    private String certificatesType;
    /**
    注册时间
     */
    private Date registrationTime;
    /**
    注册资本
     */
    private Double registeredCapital;
    /**
    实缴资本
     */
    private String actualPayment;
    /**
    注册地址
     */
    private String registeredAddress;

    /**
     * 企业名称
     */
    private String  companyName;

    /**
     *企业地址
     */
    private String companyAddress;

    /**
     * 法人代表
     */
    private String comRepName;

    public Double getRegisteredCapital() {
        return registeredCapital;
    }

    public void setRegisteredCapital(Double registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    public String getComRepName() {
        return comRepName;
    }

    public void setComRepName(String comRepName) {
        this.comRepName = comRepName;
    }



    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getManagementArea() {
        return managementArea;
    }

    public void setManagementArea(String managementArea) {
        this.managementArea = managementArea;
    }

    public String getShareHolderType() {
        return shareHolderType;
    }

    public void setShareHolderType(String shareHolderType) {
        this.shareHolderType = shareHolderType;
    }

    public String getShareHolderName() {
        return shareHolderName;
    }

    public void setShareHolderName(String shareHolderName) {
        this.shareHolderName = shareHolderName;
    }

    public String getCertificatesType() {
        return certificatesType;
    }

    public void setCertificatesType(String certificatesType) {
        this.certificatesType = certificatesType;
    }

    public Date getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Date registrationTime) {
        this.registrationTime = registrationTime;
    }

    public String getActualPayment() {
        return actualPayment;
    }

    public void setActualPayment(String actualPayment) {
        this.actualPayment = actualPayment;
    }

    public String getRegisteredAddress() {
        return registeredAddress;
    }

    public void setRegisteredAddress(String registeredAddress) {
        this.registeredAddress = registeredAddress;
    }
}
