/**
 * Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.borrow.model;

import java.math.BigDecimal;
import java.util.Date;
public class BorrowTenderDetail {
	/** 主键 */
	private String tenderId;
	/** 借款标ID */
	private String borrowId;
	/** 用户的ID */
	private Long userId;
	/** 用户的ID */
	private String nickName;
	/** 当前持有者用户的ID */
	private Long curUserId;
	/** 当前持有者用户的姓名 */
	private String curUserName;
	/** 此标实际投标金额 */
	private BigDecimal effectiveMoney;
	/** 投标时间 */
	private Date addTime;
	/** 投标结束时间 */
	private Date endTime;
	/** 投标的IP地址 */
	private String addIP;
	/** 投标状态:-1、投标失败;0、投标中;1、还款中;2、还款结束 */
	private Integer status;
	/** 待收总额 */
	private BigDecimal collectAmount;
	
	public String getTenderId() {
		return tenderId;
	}
	public void setTenderId(String tenderId) {
		this.tenderId = tenderId;
	}
	public String getBorrowId() {
		return borrowId;
	}
	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getCurUserId() {
		return curUserId;
	}
	public void setCurUserId(Long curUserId) {
		this.curUserId = curUserId;
	}
	public String getCurUserName() {
		return curUserName;
	}
	public void setCurUserName(String curUserName) {
		this.curUserName = curUserName;
	}
	public BigDecimal getEffectiveMoney() {
		return effectiveMoney;
	}
	public void setEffectiveMoney(BigDecimal effectiveMoney) {
		this.effectiveMoney = effectiveMoney;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getAddIP() {
		return addIP;
	}
	public void setAddIP(String addIP) {
		this.addIP = addIP;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public BigDecimal getCollectAmount() {
		return collectAmount;
	}
	public void setCollectAmount(BigDecimal collectAmount) {
		this.collectAmount = collectAmount;
	}
	public BigDecimal getCollectInterest() {
		return collectInterest;
	}
	public void setCollectInterest(BigDecimal collectInterest) {
		this.collectInterest = collectInterest;
	}
	public BigDecimal getCollectedAccount() {
		return collectedAccount;
	}
	public void setCollectedAccount(BigDecimal collectedAccount) {
		this.collectedAccount = collectedAccount;
	}
	public BigDecimal getCollectedInterest() {
		return collectedInterest;
	}
	public void setCollectedInterest(BigDecimal collectedInterest) {
		this.collectedInterest = collectedInterest;
	}
	public BigDecimal getCollectedFine() {
		return collectedFine;
	}
	public void setCollectedFine(BigDecimal collectedFine) {
		this.collectedFine = collectedFine;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getIsOptimize() {
		return isOptimize;
	}
	public void setIsOptimize(String isOptimize) {
		this.isOptimize = isOptimize;
	}
	public String getSchemeId() {
		return schemeId;
	}
	public void setSchemeId(String schemeId) {
		this.schemeId = schemeId;
	}
	/** 待收利息总额 */
	private BigDecimal collectInterest;
	/** 实收金额，范围[0,待收总额] */
	private BigDecimal collectedAccount;
	/** 实收利息，范围：[0,待收利息总额] */
	private BigDecimal collectedInterest;
	private BigDecimal collectedFine;//罚息、违约金

	private String userName;
	private String isOptimize;
	private String schemeId;

	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


	
}
