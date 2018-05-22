package com.xxdai.market.bo;

import java.math.BigDecimal;

public class MarketGoodsOV implements java.io.Serializable {
	
	private Long id;
    private String goodsNum;
    private String goodsName;
    private BigDecimal price;
    private String standard;
    private Integer goodsType;
    private BigDecimal expressfee;
    private Integer xxbExpressFee;
    private BigDecimal xinxinCoin;
    private BigDecimal useMoney;
    private Integer limitCount;
    private Integer count;
    private Integer sellCount;
    private String imgPath;
    private String remark;
    private BigDecimal accountPayPrice;
    private BigDecimal xinxinCoinPayPrice;
    private BigDecimal accountTotalPay;
    private BigDecimal xinxinCoinTotalPay;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(String goodsNum) {
		this.goodsNum = goodsNum;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public Integer getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(Integer goodsType) {
		this.goodsType = goodsType;
	}
	public BigDecimal getExpressfee() {
		return expressfee;
	}
	public void setExpressfee(BigDecimal expressfee) {
		this.expressfee = expressfee;
	}
	public Integer getXxbExpressFee() {
		return xxbExpressFee;
	}
	public void setXxbExpressFee(Integer xxbExpressFee) {
		this.xxbExpressFee = xxbExpressFee;
	}
	public BigDecimal getXinxinCoin() {
		return xinxinCoin;
	}
	public void setXinxinCoin(BigDecimal xinxinCoin) {
		this.xinxinCoin = xinxinCoin;
	}
	public BigDecimal getUseMoney() {
		return useMoney;
	}
	public void setUseMoney(BigDecimal useMoney) {
		this.useMoney = useMoney;
	}
	public Integer getLimitCount() {
		return limitCount;
	}
	public void setLimitCount(Integer limitCount) {
		this.limitCount = limitCount;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getSellCount() {
		return sellCount;
	}
	public void setSellCount(Integer sellCount) {
		this.sellCount = sellCount;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public BigDecimal getAccountPayPrice() {
		return accountPayPrice;
	}
	public void setAccountPayPrice(BigDecimal accountPayPrice) {
		this.accountPayPrice = accountPayPrice;
	}
	public BigDecimal getXinxinCoinPayPrice() {
		return xinxinCoinPayPrice;
	}
	public void setXinxinCoinPayPrice(BigDecimal xinxinCoinPayPrice) {
		this.xinxinCoinPayPrice = xinxinCoinPayPrice;
	}
	public BigDecimal getAccountTotalPay() {
		return accountTotalPay;
	}
	public void setAccountTotalPay(BigDecimal accountTotalPay) {
		this.accountTotalPay = accountTotalPay;
	}
	public BigDecimal getXinxinCoinTotalPay() {
		return xinxinCoinTotalPay;
	}
	public void setXinxinCoinTotalPay(BigDecimal xinxinCoinTotalPay) {
		this.xinxinCoinTotalPay = xinxinCoinTotalPay;
	}
    
}
