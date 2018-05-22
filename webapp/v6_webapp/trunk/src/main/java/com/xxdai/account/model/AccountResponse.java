package com.xxdai.account.model;

import com.xxdai.core.util.http.WSResponse;
public class AccountResponse extends WSResponse {
	 
		private Object data;		

		public Object getData() {
			return data;
		}

		public void setData(Object data) {
			this.data = data;
		}
		
		
	}

