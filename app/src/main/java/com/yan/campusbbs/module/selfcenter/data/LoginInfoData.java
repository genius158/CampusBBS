package com.yan.campusbbs.module.selfcenter.data;

/**
 * Created by yan on 2017/3/27.
 */

public class LoginInfoData {

    /**
     * data : null
     * resultCode : 500
     * message : 请输入账号
     */

    private Object data;
    private int resultCode;
    private String message;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
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
