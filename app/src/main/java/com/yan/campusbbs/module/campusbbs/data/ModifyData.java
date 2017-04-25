package com.yan.campusbbs.module.campusbbs.data;

/**
 * Created by yan on 2017/4/25.
 */

public class ModifyData {

    /**
     * data : null
     * jsessionId : 476D9E559520447AE2BA989D301438B5
     * resultCode : 200
     * message : 修改成功
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
