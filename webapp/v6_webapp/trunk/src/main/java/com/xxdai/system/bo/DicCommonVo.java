/** 
* Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
*/
package com.xxdai.system.bo;

/**
 * 返回通用字典实体类
 * @since jdk1.6
 * date: 2014年10月8日
 * @version $Id: DicCommonVo.java 9464 2014-11-07 12:23:02Z xiaoying $
 */
public  class DicCommonVo {

	// Fields
	private String typeCode;
	private String pkey;
	private String pvalue;
	private String pvalue2;
	private String note;
	private String status;
	private Long porder;
	private Long porder2;
	
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getPkey() {
		return pkey;
	}
	public void setPkey(String pkey) {
		this.pkey = pkey;
	}
	public String getPvalue() {
		return pvalue;
	}
	public void setPvalue(String pvalue) {
		this.pvalue = pvalue;
	}
	public String getPvalue2() {
		return pvalue2;
	}
	public void setPvalue2(String pvalue2) {
		this.pvalue2 = pvalue2;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getPorder() {
		return porder;
	}
	public void setPorder(Long porder) {
		this.porder = porder;
	}
	public Long getPorder2() {
		return porder2;
	}
	public void setPorder2(Long porder2) {
		this.porder2 = porder2;
	}
}