package com.xxdai.account.model;

public class RechargeCallbackResultReq {
	// 商户订单号
	private String orderNo;

	// 支付金额
	private String amt;

	private String flag;

	// 易宝支付交易流水号
	private String trxId;

	private Long userId;

	private String ip;

	// 支付银行
	private String bankId;

	// 支付平台
	private String payPlatfrom;
	//合作伙伴ID  参考xxd_partner
	private Long partnerId;
	//银行代码  参考字典RECHARGE_BANK_LIST
	private String bankCode;
	//签约号（同卡进出第三方编号）
	private String signNo;
	
	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getTrxId() {
		return trxId;
	}

	public void setTrxId(String trxId) {
		this.trxId = trxId;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPayPlatfrom() {
		return payPlatfrom;
	}

	public void setPayPlatfrom(String payPlatfrom) {
		this.payPlatfrom = payPlatfrom;
	}
	
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getSignNo() {
		return signNo;
	}

	public void setSignNo(String signNo) {
		this.signNo = signNo;
	}
	
}