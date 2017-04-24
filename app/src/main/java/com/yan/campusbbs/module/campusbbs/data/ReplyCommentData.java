package com.yan.campusbbs.module.campusbbs.data;

/**
 * Created by yan on 2017/4/24.
 */

public class ReplyCommentData {

    /**
     * data : null
     * jsessionId : 0DC29D9C3FEC91D29CBE4366247C9CD2
     * resultCode : 200
     * message : 评论成功
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
