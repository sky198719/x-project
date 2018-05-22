package com.xxdai.user.model;

import com.xxdai.core.util.http.WSResponse;

public class UserResponse  extends WSResponse {
private Object data;
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
