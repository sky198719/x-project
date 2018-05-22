/**
* <p>Title: TradeTransfer.java</p>
* <p>Description: </p>
* <p>Copyright (c) 2014, www.xinxindai.com All Rights Reserved. </p>
* <p>Company: www.xinxindai.com</p>
* @author huna
* @date 2014年9月17日
* @version 1.0
*/
package com.xxdai.trade.webservice.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.xxdai.core.util.http.WSResponse;
import com.xxdai.trade.model.TradeTransfer;

/**
 * @author huna
 * @since jdk1.6
 * @version $Id: TradeResponse.java 9464 2014-11-07 12:23:02Z xiaoying $
 */
public class TradeResponse extends WSResponse{
	private TradeTransfer tradeTransfer;

    private List<TradeTransfer> tradeTransfers = new ArrayList<TradeTransfer>();

    private int pageIndex; //当前页
	
	private int pageSize;  //每页记录数
	
	private int totalSize;  //总页数
    
	public TradeTransfer getTradeTransfer() {
		return tradeTransfer;
	}

	public void setTradeTransfer(TradeTransfer tradeTransfer) {
		this.tradeTransfer = tradeTransfer;
	}

	public List<TradeTransfer> getTradeTransfers() {
		return tradeTransfers;
	}

	public void setTradeTransfers(List<TradeTransfer> tradeTransfers) {
		this.tradeTransfers = tradeTransfers;
	}
	
	public int getPageIndex() {
		if(pageIndex<=0){
			pageIndex=1;
		}
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		if(pageSize<=0){
			pageIndex=20;
		}
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
}
