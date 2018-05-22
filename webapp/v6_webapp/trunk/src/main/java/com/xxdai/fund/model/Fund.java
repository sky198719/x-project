/**
 * Copyright (c) 2015, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.fund.model;

import java.math.BigDecimal;
import java.util.Date;

public class Fund {

    private String fcode;
    private String fname;
    private BigDecimal account;
    private BigDecimal remAccount;
    private BigDecimal lowestTender;
    private BigDecimal userMostTender;
    private Date openDate;
    private Date closeDate;
    private Integer status;
    private Integer creator;
    private Date createDate;
    private String createIp;
    private Integer  lastModify;
    private Date modifyDate;
    private String modifyIp;
    private Integer isBalance;
    private String lastBalance;

    public String getModifyIp() {
        return modifyIp;
    }

    public void setModifyIp(String modifyIp) {
        this.modifyIp = modifyIp;
    }

    public String getFcode() {
        return fcode;
    }

    public void setFcode(String fcode) {
        this.fcode = fcode;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    public BigDecimal getRemAccount() {
        return remAccount;
    }

    public void setRemAccount(BigDecimal remAccount) {
        this.remAccount = remAccount;
    }

    public BigDecimal getLowestTender() {
        return lowestTender;
    }

    public void setLowestTender(BigDecimal lowestTender) {
        this.lowestTender = lowestTender;
    }

    public BigDecimal getUserMostTender() {
        return userMostTender;
    }

    public void setUserMostTender(BigDecimal userMostTender) {
        this.userMostTender = userMostTender;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date craateDate) {
        this.createDate = craateDate;
    }

    public String getCreateIp() {
        return createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp;
    }

    public Integer getLastModify() {
        return lastModify;
    }

    public void setLastModify(Integer lastModify) {
        this.lastModify = lastModify;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Integer getIsBalance() {
        return isBalance;
    }

    public void setIsBalance(Integer isBalance) {
        this.isBalance = isBalance;
    }

    public String getLastBalance() {
        return lastBalance;
    }

    public void setLastBalance(String lastBalance) {
        this.lastBalance = lastBalance;
    }
}
