package com.xxdai.http;

/**
 * 统一的返回的消息结构体.
 */
public abstract class Message {

    public int code;

    public String message;

    public Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Message(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Message() {
    }
}
