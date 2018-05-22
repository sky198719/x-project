package com.xxdai.market.bo;

// default package

public class Levels implements java.io.Serializable {
	private Long userId;
	private Integer coins;
	private String creditLevel;
	private Integer creditIntegral;
	private String investLevel;
	private Integer investIntegral;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getCoins() {
		return coins;
	}

	public void setCoins(Integer coins) {
		this.coins = coins;
	}

	public String getCreditLevel() {
		return creditLevel;
	}

	public void setCreditLevel(String creditLevel) {
		this.creditLevel = creditLevel;
	}

	public Integer getCreditIntegral() {
		return creditIntegral;
	}

	public void setCreditIntegral(Integer creditIntegral) {
		this.creditIntegral = creditIntegral;
	}

	public String getInvestLevel() {
		return investLevel;
	}

	public void setInvestLevel(String investLevel) {
		this.investLevel = investLevel;
	}

	public Integer getInvestIntegral() {
		return investIntegral;
	}

	public void setInvestIntegral(Integer investIntegral) {
		this.investIntegral = investIntegral;
	}
}