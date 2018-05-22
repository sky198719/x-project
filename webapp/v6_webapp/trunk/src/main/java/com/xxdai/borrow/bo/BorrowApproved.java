/**
 * Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.borrow.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述
 *
 * @version $Id: BorrowApproved.java 9464 2014-11-07 12:23:02Z xiaoying $
 * @since jdk1.6
 */
public class BorrowApproved implements Serializable {

    private String approveId; //	APPROVEID	N	INTEGER	N			审核id
    private String borrowId; //	BORROWID	N	INTEGER	N			标的id
    private String status; //	STATUS	N	CHAR(1)	N			审核进度:0.待审核;1.初审过;2.反欺诈过;3.终审过;5. 满标待复审（3与4的中间状态,仅给信用标，抵押标使用）4.复审过;-1.初审不通过;-2.反欺诈不通过;-3.终审不通过;-4.复审不通过;-5.撤销借款标;
    private Long approveUser1; //	APPROVEUSER1	N	INTEGER	Y	0		初审人员ID
    private Date approveTime1; //	APPROVETIME1	N	DATE	Y			初审处理时间
    private String approveRemark1; //	APPROVEREMARK1	N	VARCHAR2(300)	Y			初审处理意见
    private String approveIp1; //	APPROVEIP1	N	VARCHAR2(50)	Y			初审处理IP地址
    private Long approveUser2; //	APPROVEUSER2	N	INTEGER	Y	0		反欺诈人员ID
    private Date approveTime2; //	APPROVETIME2	N	DATE	Y			反欺诈处理时间
    private String approveRemark2; //	APPROVEREMARK2	N	VARCHAR2(300)	Y			反欺诈处理意见
    private String approveIp2; //	APPROVEIP2	N	VARCHAR2(50)	Y			反欺诈处理IP地址
    private Long approveUser3; //	APPROVEUSER3	N	INTEGER	Y	0		终审人员ID
    private Date approveTime3; //	APPROVETIME3	N	DATE	Y			终审处理时间
    private String approveRemark3; //	APPROVEREMARK3	N	VARCHAR2(300)	Y			终审处理意见
    private String approveIp3; //	APPROVEIP3	N	VARCHAR2(50)	Y			终审处理IP地址
    private Long approveUser4; //	APPROVEUSER4	N	INTEGER	Y	0		初审人员ID
    private Date approveTime4; //	APPROVETIME4	N	DATE	Y			复审处理时间
    private String approveRemark4; //	APPROVEREMARK4	N	VARCHAR2(300)	Y			复审处理意见
    private String approveIp4; //	APPROVEIP4	N	VARCHAR2(50)	Y			复审处理IP地址
    private Long cancelUser; //	CANCELUSER	N	INTEGER	Y	0		撤销人员ID
    private Date cancelTime; //	CANCELTIME	N	INTEGER	Y			撤销处理时间
    private String cancelRemark; //	CANCELREMARK	N	VARCHAR2(300)	Y			撤销处理缘由
    private String cancelIp; // 	CANCELIP	N	VARCHAR2(32)	Y			撤销处理IP地址

  
  
  
    public String getApproveId() {
		return approveId;
	}

	public void setApproveId(String approveId) {
		this.approveId = approveId;
	}

	public String getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}

	public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

  
    public Long getApproveUser1() {
        return approveUser1;
    }

    public void setApproveUser1(Long approveUser1) {
        this.approveUser1 = approveUser1;
    }

    
    public Date getApproveTime1() {
        return approveTime1;
    }

    public void setApproveTime1(Date approveTime1) {
        this.approveTime1 = approveTime1;
    }

 
    public String getApproveRemark1() {
        return approveRemark1;
    }

    public void setApproveRemark1(String approveRemark1) {
        this.approveRemark1 = approveRemark1;
    }

  
    public String getApproveIp1() {
        return approveIp1;
    }

    public void setApproveIp1(String approveIp1) {
        this.approveIp1 = approveIp1;
    }

  
    public Long getApproveUser2() {
        return approveUser2;
    }

    public void setApproveUser2(Long approveUser2) {
        this.approveUser2 = approveUser2;
    }

   
    public Date getApproveTime2() {
        return approveTime2;
    }

    public void setApproveTime2(Date approveTime2) {
        this.approveTime2 = approveTime2;
    }

    
    public String getApproveRemark2() {
        return approveRemark2;
    }

    public void setApproveRemark2(String approveRemark2) {
        this.approveRemark2 = approveRemark2;
    }

   
    public String getApproveIp2() {
        return approveIp2;
    }

    public void setApproveIp2(String approveIp2) {
        this.approveIp2 = approveIp2;
    }

   
    public Long getApproveUser3() {
        return approveUser3;
    }

    public void setApproveUser3(Long approveUser3) {
        this.approveUser3 = approveUser3;
    }

    
    public Date getApproveTime3() {
        return approveTime3;
    }

    public void setApproveTime3(Date approveTime3) {
        this.approveTime3 = approveTime3;
    }

   
    public String getApproveRemark3() {
        return approveRemark3;
    }

    public void setApproveRemark3(String approveRemark3) {
        this.approveRemark3 = approveRemark3;
    }

   
    public String getApproveIp3() {
        return approveIp3;
    }

    public void setApproveIp3(String approveIp3) {
        this.approveIp3 = approveIp3;
    }

   
    public Long getApproveUser4() {
        return approveUser4;
    }

    public void setApproveUser4(Long approveUser4) {
        this.approveUser4 = approveUser4;
    }

    
    public Date getApproveTime4() {
        return approveTime4;
    }

    public void setApproveTime4(Date approveTime4) {
        this.approveTime4 = approveTime4;
    }

   
    public String getApproveRemark4() {
        return approveRemark4;
    }

    public void setApproveRemark4(String approveRemark4) {
        this.approveRemark4 = approveRemark4;
    }

   
    public String getApproveIp4() {
        return approveIp4;
    }

    public void setApproveIp4(String approveIp4) {
        this.approveIp4 = approveIp4;
    }

   
    public Long getCancelUser() {
        return cancelUser;
    }

    public void setCancelUser(Long cancelUser) {
        this.cancelUser = cancelUser;
    }

 
    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }


    public String getCancelRemark() {
        return cancelRemark;
    }

    public void setCancelRemark(String cancelRemark) {
        this.cancelRemark = cancelRemark;
    }

 
    public String getCancelIp() {
        return cancelIp;
    }

    public void setCancelIp(String cancelIp) {
        this.cancelIp = cancelIp;
    }
}
