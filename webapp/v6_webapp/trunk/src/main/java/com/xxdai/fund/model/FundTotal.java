package com.xxdai.fund.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by chaihuangqi on 2015/8/26.
 */
public class FundTotal {
    private Integer userId;
    private String fcode;
    private BigDecimal account;
    private BigDecimal accountAmount;
    private BigDecimal totalPurchase;
    private BigDecimal totalRedeem;
    private BigDecimal totalEarnings;
    private Integer status;
    private Date createDate;
    private String createIp;

    public FundTotal() {
    }

    public FundTotal(Integer userId, String fcode, BigDecimal account, BigDecimal accountAmount, BigDecimal totalPurchase, BigDecimal totalRedeem, BigDecimal totalEarnings, Integer status, Date createDate, String createIp) {
        this.userId = userId;
        this.fcode = fcode;
        this.account = account;
        this.accountAmount = accountAmount;
        this.totalPurchase = totalPurchase;
        this.totalRedeem = totalRedeem;
        this.totalEarnings = totalEarnings;
        this.status = status;
        this.createDate = createDate;
        this.createIp = createIp;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFcode() {
        return fcode;
    }

    public void setFcode(String fcode) {
        this.fcode = fcode;
    }

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    public BigDecimal getAccountAmount() {
        return accountAmount;
    }

    public void setAccountAmount(BigDecimal accountAmount) {
        this.accountAmount = accountAmount;
    }

    public BigDecimal getTotalPurchase() {
        return totalPurchase;
    }

    public void setTotalPurchase(BigDecimal totalPurchase) {
        this.totalPurchase = totalPurchase;
    }

    public BigDecimal getTotalRedeem() {
        return totalRedeem;
    }

    public void setTotalRedeem(BigDecimal totalRedeem) {
        this.totalRedeem = totalRedeem;
    }

    public BigDecimal getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(BigDecimal totalEarnings) {
        this.totalEarnings = totalEarnings;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateIp() {
        return createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp;
    }
}
