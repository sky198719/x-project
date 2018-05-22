/** 
 * Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
 */
package com.xxdai.person.bo;

// default package

import java.math.BigDecimal;
import java.util.Date;


/**
 * 银行model类
 * 
 * @since jdk1.6
 * @version $Id: BankInfo.java 14510 2015-01-19 08:04:29Z huna $
 */

public class BankInfo implements java.io.Serializable {
	// Fields
	private Long id;
	private Long userId;
	private String bankCode;
	private Integer banded;

	private Date addTime;
	private String addIp;
	private Long lastModify;
	private Date modifyDate;
	private String branch;
	private String bankAccount;
	private Long partnerId;          //合作伙伴
    private String signNo;           //签约号（同卡进出第三方编号）
    private BigDecimal cashSum;      //提现累计（同卡进出）
    private BigDecimal rechargeSum;  //充值累计（同卡进出）
    private Integer status;          //银行卡状态 1在用-1删除'

    
    private String province;
    private String city;

    private String terminalver;

    public String getTerminalver() {
        return this.terminalver;
    }

    public void setTerminalver(String terminalver) {
        this.terminalver = terminalver;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public Integer getBanded() {
		return banded;
	}

	public void setBanded(Integer banded) {
		this.banded = banded;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getAddIp() {
		return addIp;
	}

	public void setAddIp(String addIp) {
		this.addIp = addIp;
	}

	public Long getLastModify() {
		return lastModify;
	}

	public void setLastModify(Long lastModify) {
		this.lastModify = lastModify;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	public String getSignNo() {
		return signNo;
	}

	public void setSignNo(String signNo) {
		this.signNo = signNo;
	}

	public BigDecimal getCashSum() {
		return cashSum;
	}

	public void setCashSum(BigDecimal cashSum) {
		this.cashSum = cashSum;
	}

	public BigDecimal getRechargeSum() {
		return rechargeSum;
	}

	public void setRechargeSum(BigDecimal rechargeSum) {
		this.rechargeSum = rechargeSum;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	
	
}