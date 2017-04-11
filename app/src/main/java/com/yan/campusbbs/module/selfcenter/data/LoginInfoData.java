package com.yan.campusbbs.module.selfcenter.data;

import java.io.Serializable;

/**
 * Created by yan on 2017/3/27.
 */

public class LoginInfoData implements Serializable {

    /**
     * data : {"userInfo":{"userId":"U15B524C6A3DU01AH","friendCode":"F15B524C6A3D3M5MW","userAccount":"17780701147","userPassword":null,"cookiePassword":"XG3G50VGYT","userNickname":"U17780701147","userRealName":null,"userRank":0,"userHeadImg":"http://www.youlanw.com/static/images/man.jpg","userMood":"","userEmail":"","userCreateTime":"2017-04-09 18:39:51"}}
     * jsessionId : 3D82DDBD96908BA3B5DBB8DF8EA8966D
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
         * userInfo : {"userId":"U15B524C6A3DU01AH","friendCode":"F15B524C6A3D3M5MW","userAccount":"17780701147","userPassword":null,"cookiePassword":"XG3G50VGYT","userNickname":"U17780701147","userRealName":null,"userRank":0,"userHeadImg":"http://www.youlanw.com/static/images/man.jpg","userMood":"","userEmail":"","userCreateTime":"2017-04-09 18:39:51"}
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
             * userId : U15B524C6A3DU01AH
             * friendCode : F15B524C6A3D3M5MW
             * userAccount : 17780701147
             * userPassword : null
             * cookiePassword : XG3G50VGYT
             * userNickname : U17780701147
             * userRealName : null
             * userRank : 0
             * userHeadImg : http://www.youlanw.com/static/images/man.jpg
             * userMood :
             * userEmail :
             * userCreateTime : 2017-04-09 18:39:51
             */

            private String userId;
            private String friendCode;
            private String userAccount;
            private Object userPassword;
            private String cookiePassword;
            private String userNickname;
            private Object userRealName;
            private int userRank;
            private String userHeadImg;
            private String userMood;
            private String userEmail;
            private String userCreateTime;

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getFriendCode() {
                return friendCode;
            }

            public void setFriendCode(String friendCode) {
                this.friendCode = friendCode;
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

            public Object getUserRealName() {
                return userRealName;
            }

            public void setUserRealName(Object userRealName) {
                this.userRealName = userRealName;
            }

            public int getUserRank() {
                return userRank;
            }

            public void setUserRank(int userRank) {
                this.userRank = userRank;
            }

            public String getUserHeadImg() {
                return userHeadImg;
            }

            public void setUserHeadImg(String userHeadImg) {
                this.userHeadImg = userHeadImg;
            }

            public String getUserMood() {
                return userMood;
            }

            public void setUserMood(String userMood) {
                this.userMood = userMood;
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
