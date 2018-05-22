/**
 * Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.borrow.bo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 票据 描述
 * @version $Id:$
 * @since jdk1.6
 */

public class BorrowBillextend {
	
	private Long billId;
	private String borrowId;
	private BigDecimal faceAmount; //票面金额
	private Date endDate;	//票据到期日期
	private Date investDate; //票据开始计息日
	private Integer term; //票据计息周期/期限（天，10-180天）
	private Date issueDate; //发行起始日（即终审审核通过日期）
	private Date issueEndDate; //发行截止日
	private String acceptanceBank; //承兑银行名称
	private String billPic; //票据图片文件
	private String billSource; //票据来源（01鉴丰金融09其它）
	private String type; //票据类型（01银行承兑汇票09其它）
	private String description; //票据描述
	private String remark; //备注
	private Long creator;
	private Date createDate;
	private String createIp;
	private Long lastModify;
	private Date modifyDate;
	public Long getBillId() {
		return billId;
	}
	public void setBillId(Long billId) {
		this.billId = billId;
	}
	public String getBorrowId() {
		return borrowId;
	}
	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}
	public BigDecimal getFaceAmount() {
		return faceAmount;
	}
	public void setFaceAmount(BigDecimal faceAmount) {
		this.faceAmount = faceAmount;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getInvestDate() {
		return investDate;
	}
	public void setInvestDate(Date investDate) {
		this.investDate = investDate;
	}
	public Integer getTerm() {
		return term;
	}
	public void setTerm(Integer term) {
		this.term = term;
	}
	public Date getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	public Date getIssueEndDate() {
		return issueEndDate;
	}
	public void setIssueEndDate(Date issueEndDate) {
		this.issueEndDate = issueEndDate;
	}
	public String getAcceptanceBank() {
		return acceptanceBank;
	}
	public void setAcceptanceBank(String acceptanceBank) {
		this.acceptanceBank = acceptanceBank;
	}
	public String getBillPic() {
		return billPic;
	}
	public void setBillPic(String billPic) {
		this.billPic = billPic;
	}
	public String getBillSource() {
		return billSource;
	}
	public void setBillSource(String billSource) {
		this.billSource = billSource;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getCreator() {
		return creator;
	}
	public void setCreator(Long creator) {
		this.creator = creator;
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
}