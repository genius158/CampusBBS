package com.yan.campusbbs.module.selfcenter.data;

/**
 * Created by yan on 2017/4/11.
 */

public class RegisterData {

    /**
     * data : null
     * jsessionId : DB5F1280B6B571FF053754C760FAD97B
     * resultCode : 200
     * message : 用户注册成功
     */

    private Object data;
    private String jsessionId;
    private int resultCode;
    private String message;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getJsessionId() {
        return jsessionId;
    }

    public void setJsessionId(String jsessionId) {
        this.jsessionId = jsessionId;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
