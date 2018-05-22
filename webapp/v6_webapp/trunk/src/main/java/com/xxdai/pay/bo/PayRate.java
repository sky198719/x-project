package com.xxdai.pay.bo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by chaihuangqi on 2015/10/14.
 */
public class PayRate {
    private Long payRateId;
    /** 银行标识码 */
    private String bankCode;
    /** 支付方式 */
    private Long partnerId;
    /** 银行支付码 */
    private String payCode;
    /** 费率 */
    private BigDecimal rate;
    /** 费率权重 defult:0 */
    private BigDecimal rightRate;
    /** 接通率权重 */
    private BigDecimal rightConn;
    private Date createDate;
    private  Long creator;
    private String createIp;
    private Date modifyDate;
    private Long lastModify;

    public Long getPayRateId() {
        return payRateId;
    }

    public void setPayRateId(Long payRateId) {
        this.payRateId = payRateId;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getRightRate() {
        return rightRate;
    }

    public void setRightRate(BigDecimal rightRate) {
        this.rightRate = rightRate;
    }

    public BigDecimal getRightConn() {
        return rightConn;
    }

    public void setRightConn(BigDecimal rightConn) {
        this.rightConn = rightConn;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public String getCreateIp() {
        return createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Long getLastModify() {
        return lastModify;
    }

    public void setLastModify(Long lastModify) {
        this.lastModify = lastModify;
    }
}
