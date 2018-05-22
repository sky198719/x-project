package com.xxdai.account.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xxdai.account.bo.AccountLog;


/**
 * 资金日志 查询器
 * 
 * @author liuhuan
 * @date 2013-11-18下午07:20:13
 */
public class LogsFilter {
	private static Map<Integer, String> types = new HashMap<Integer, String>();
	private static Map<Integer, String> limits = new HashMap<Integer, String>();

	static {
		types.put(1, "投标");
		types.put(2, "借款");
		types.put(3, "还款");
		types.put(4, "充值");
		types.put(5, "提现");
		types.put(6, "奖励");
		types.put(7, "收款");
		types.put(8, "冻结");
		types.put(9, "其他");

		limits.put(0, "最近一周");
		limits.put(1, "1个月");
		limits.put(2, "2个月");
		limits.put(3, "3个月");
		limits.put(6, "6个月");
		limits.put(12, "12个月");
		limits.put(13, "12个月以上");
	}

	private Integer type;
	private Integer limit;
	private List<AccountLog> accountLogs;
	private int getCount;

	public Map<Integer, String> getTypes() {
		return types;
	}

	public void setTypes(Map<Integer, String> types) {
		LogsFilter.types = types;
	}

	public Map<Integer, String> getLimits() {
		return limits;
	}

	public void setLimits(Map<Integer, String> limits) {
		LogsFilter.limits = limits;
	}

	public List<AccountLog> getAccountLogs() {
		return accountLogs;
	}

	public void setAccountLogs(List<AccountLog> accountLogs) {
		this.accountLogs = accountLogs;
	}

	public int getGetCount() {
		return getCount;
	}

	public void setGetCount(int getCount) {
		this.getCount = getCount;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}
}
