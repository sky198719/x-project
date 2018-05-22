package com.xxdai.http;


public class SuccessMessage extends Message {

    public SuccessMessage(Object data) {
        super.code = MessageStatus.SUCCESS.getStatus();
        super.message = "";
        super.data = data;
    }

}
