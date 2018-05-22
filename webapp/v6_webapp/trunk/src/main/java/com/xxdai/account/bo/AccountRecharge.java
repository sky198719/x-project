/**
 * Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.account.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 描述
 *
 * @version $Id: AccountRecharge.java 15350 2015-02-03 10:20:52Z yangzhe $
 * @since jdk1.6
 */

public class AccountRecharge implements Serializable {

    private String rechargeId; //	RECHARGEID	N	VARCHAR2(30)	N			充值记录编号
    private Long userId; //	USERID	N	INTEGER	N			用户
    private String type; //	TYPE	N	CHAR(1)	N			充值类型字典（1现在充值2体验金充值3奖励4）
    private String bankCode; //	BANKCODE	N	CHAR(6)	N			银行编号
    private String orderNo; //	ORDERNO	N	VARCHAR2(50)	N			充值单号
    private BigDecimal amount; //	AMOUNT	N	NUMBER(16,2)	N			充值金额
    private BigDecimal fee; //	FEE	N	NUMBER(8,2)	Y			充值手续费
    private Date addTime; //	ADDTIME	N	DATE	N			充值时间
    private String rechargeIp; //	RECHARGEIP	N	VARCHAR2(50)	Y			充值ip
    private Long verifyUserId; //	VERIFYUSERID	N	INTEGER	Y			确认用户
    private Date verifyDate; //	VERIFYDATE	N	DATE	Y			确认时间
    private String verifyIp; //	VERIFYIP	N	VARCHAR2(50)	Y			确认ip
    private Integer status; //	STATUS	N	INTEGER	N			充值结果0待确认1成功2失败
    private String remark; //	REMARK	N	VARCHAR2(100)	Y			确认说明
    private String terminalver;
    private Long partnerId;
    private String  partnerName;
    private Integer isiocard; //default 0  是否同卡进出0否1是
    private String account; //充值的银行卡号
    
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public String getRechargeId() {
		return rechargeId;
	}
	public void setRechargeId(String rechargeId) {
		this.rechargeId = rechargeId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getFee() {
		return fee;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public String getRechargeIp() {
		return rechargeIp;
	}
	public void setRechargeIp(String rechargeIp) {
		this.rechargeIp = rechargeIp;
	}
	public Long getVerifyUserId() {
		return verifyUserId;
	}
	public void setVerifyUserId(Long verifyUserId) {
		this.verifyUserId = verifyUserId;
	}
	public Date getVerifyDate() {
		return verifyDate;
	}
	public void setVerifyDate(Date verifyDate) {
		this.verifyDate = verifyDate;
	}
	public String getVerifyIp() {
		return verifyIp;
	}
	public void setVerifyIp(String verifyIp) {
		this.verifyIp = verifyIp;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTerminalver() {
		return terminalver;
	}
	public void setTerminalver(String terminalver) {
		this.terminalver = terminalver;
	}
	public Long getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}
	public Integer getIsiocard() {
		return isiocard;
	}
	public void setIsiocard(Integer isiocard) {
		this.isiocard = isiocard;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
}
