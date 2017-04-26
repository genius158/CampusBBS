package com.yan.campusbbs.module.campusbbs.data;

/**
 * Created by Administrator on 2017/4/27 0027.
 */

public class PublishBackData {

    /**
     * data : null
     * jsessionId : 6D7845E05EF501A53059CAD1AAD6EC67
     * resultCode : 500
     * message :
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
