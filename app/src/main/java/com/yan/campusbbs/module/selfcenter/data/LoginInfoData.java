package com.yan.campusbbs.module.selfcenter.data;

import java.io.Serializable;

/**
 * Created by yan on 2017/3/27.
 */

public class LoginInfoData implements Serializable {

    /**
     * data : null
     * jsessionId : 0D39988690788BB039A059B0BBF5DD7D
     * resultCode : 200
     * message : 登录成功
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
