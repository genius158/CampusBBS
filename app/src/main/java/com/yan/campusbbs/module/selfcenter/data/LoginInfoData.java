package com.yan.campusbbs.module.selfcenter.data;

import java.io.Serializable;

/**
 * Created by yan on 2017/3/27.
 */

public class LoginInfoData implements Serializable {

    /**
     * data : {"userInfo":{"userId":"A1","userAccount":"admin","userPassword":null,"cookiePassword":"4QrcOUm6Wau+VuBX8g+IPg==","userNickname":"會飛的青蛙","userRealName":"張三","userRank":99,"userEmail":"A1@gmqil.com","userCreateTime":"16-12-25 06:12:03"}}
     * jsessionId : 8C445A4DCCED15EBAC21EBD55997680D
     * resultCode : 200
     * message : 登录成功
     */

    private DataBean data;
    private String jsessionId;
    private int resultCode;
    private String message;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
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

    public static class DataBean implements Serializable {
        /**
         * userInfo : {"userId":"A1","userAccount":"admin","userPassword":null,"cookiePassword":"4QrcOUm6Wau+VuBX8g+IPg==","userNickname":"會飛的青蛙","userRealName":"張三","userRank":99,"userEmail":"A1@gmqil.com","userCreateTime":"16-12-25 06:12:03"}
         */

        private UserInfoBean userInfo;

        public UserInfoBean getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfoBean userInfo) {
            this.userInfo = userInfo;
        }

        public static class UserInfoBean implements Serializable {
            /**
             * userId : A1
             * userAccount : admin
             * userPassword : null
             * cookiePassword : 4QrcOUm6Wau+VuBX8g+IPg==
             * userNickname : 會飛的青蛙
             * userRealName : 張三
             * userRank : 99
             * userEmail : A1@gmqil.com
             * userCreateTime : 16-12-25 06:12:03
             */

            private String userId;
            private String userAccount;
            private Object userPassword;
            private String cookiePassword;
            private String userNickname;
            private String userRealName;
            private int userRank;
            private String userEmail;
            private String userCreateTime;

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getUserAccount() {
                return userAccount;
            }

            public void setUserAccount(String userAccount) {
                this.userAccount = userAccount;
            }

            public Object getUserPassword() {
                return userPassword;
            }

            public void setUserPassword(Object userPassword) {
                this.userPassword = userPassword;
            }

            public String getCookiePassword() {
                return cookiePassword;
            }

            public void setCookiePassword(String cookiePassword) {
                this.cookiePassword = cookiePassword;
            }

            public String getUserNickname() {
                return userNickname;
            }

            public void setUserNickname(String userNickname) {
                this.userNickname = userNickname;
            }

            public String getUserRealName() {
                return userRealName;
            }

            public void setUserRealName(String userRealName) {
                this.userRealName = userRealName;
            }

            public int getUserRank() {
                return userRank;
            }

            public void setUserRank(int userRank) {
                this.userRank = userRank;
            }

            public String getUserEmail() {
                return userEmail;
            }

            public void setUserEmail(String userEmail) {
                this.userEmail = userEmail;
            }

            public String getUserCreateTime() {
                return userCreateTime;
            }

            public void setUserCreateTime(String userCreateTime) {
                this.userCreateTime = userCreateTime;
            }
        }
    }
}
