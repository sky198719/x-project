package com.xxdai.market.bo;

// default package

import java.math.BigDecimal;
import java.util.Date;


public class MarketOrder implements java.io.Serializable {

	private Long id;
	private String orderNum;
	private Integer orderType;
	private String goodsNum;
	private Integer buyCount;
	private BigDecimal payMoney;
	private Integer status;
	private Long userId;
	private Integer payType;
	private BigDecimal costPrice;
	private String postCode;
	private BigDecimal expressFee;
	private String addressAccount;
	private String userName;
	private String number;
	private Date addTime;
	private String addIp;
	private Date affirmTime;
	private String affirmName;
	private Long affirmId;
	private String expressOrderNum;
	private Date shipmentsTime;
	private String shipmentsName;
	private Long shipmentsId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(String goodsNum) {
		this.goodsNum = goodsNum;
	}

	public Integer getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}

	public BigDecimal getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(BigDecimal payMoney) {
		this.payMoney = payMoney;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public BigDecimal getExpressFee() {
		return expressFee;
	}

	public void setExpressFee(BigDecimal expressFee) {
		this.expressFee = expressFee;
	}

	public String getAddressAccount() {
		return addressAccount;
	}

	public void setAddressAccount(String addressAccount) {
		this.addressAccount = addressAccount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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

	public Date getAffirmTime() {
		return affirmTime;
	}

	public void setAffirmTime(Date affirmTime) {
		this.affirmTime = affirmTime;
	}

	public String getAffirmName() {
		return affirmName;
	}

	public void setAffirmName(String affirmName) {
		this.affirmName = affirmName;
	}

	public Long getAffirmId() {
		return affirmId;
	}

	public void setAffirmId(Long affirmId) {
		this.affirmId = affirmId;
	}

	public String getExpressOrderNum() {
		return expressOrderNum;
	}

	public void setExpressOrderNum(String expressOrderNum) {
		this.expressOrderNum = expressOrderNum;
	}

	public Date getShipmentsTime() {
		return shipmentsTime;
	}

	public void setShipmentsTime(Date shipmentsTime) {
		this.shipmentsTime = shipmentsTime;
	}

	public String getShipmentsName() {
		return shipmentsName;
	}

	public void setShipmentsName(String shipmentsName) {
		this.shipmentsName = shipmentsName;
	}

	public Long getShipmentsId() {
		return shipmentsId;
	}

	public void setShipmentsId(Long shipmentsId) {
		this.shipmentsId = shipmentsId;
	}
}