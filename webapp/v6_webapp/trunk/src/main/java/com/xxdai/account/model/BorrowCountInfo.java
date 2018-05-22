package com.xxdai.account.model;

public class BorrowCountInfo {
	
	private Integer type ; //统计类型
	private String tenderMoneyCount;//出借总额
	private String getGainsMoneyCount ;//已获得收益
	private String haveNumber;// 持有数量
	private String dueInCount ;//待收标的数量
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getTenderMoneyCount() {
		return tenderMoneyCount;
	}
	public void setTenderMoneyCount(String tenderMoneyCount) {
		this.tenderMoneyCount = tenderMoneyCount;
	}
	public String getGetGainsMoneyCount() {
		return getGainsMoneyCount;
	}
	public void setGetGainsMoneyCount(String getGainsMoneyCount) {
		this.getGainsMoneyCount = getGainsMoneyCount;
	}
	public String getHaveNumber() {
		return haveNumber;
	}
	public void setHaveNumber(String haveNumber) {
		this.haveNumber = haveNumber;
	}
	public String getDueInCount() {
		return dueInCount;
	}
	public void setDueInCount(String dueInCount) {
		this.dueInCount = dueInCount;
	}
}