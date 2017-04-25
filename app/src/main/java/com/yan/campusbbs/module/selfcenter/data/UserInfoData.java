package com.yan.campusbbs.module.selfcenter.data;

import java.io.Serializable;

/**
 * Created by yan on 2017/4/14.
 */

public class UserInfoData implements Serializable {

    /**
     * data : {"userDetailInfo":{"userId":"A1","friendCode":"F1","userAccount":"admin","userNickname":"會飛的青蛙","userRealName":null,"userRank":99,"userHeadImg":"http:                                                                \t\t\t1551863228&fm=23&gp=0.jpg","userMood":null,"userEmail":null,"userCreateTime":"2016-12-24 22:12:03","userAge":0,"userGender":0,"userBirth":null,"userMajor":null,"userSchool":null,"userAddress":null}}
     * jsessionId : 5C3DD84B0DE9134680A24AF903D6F566
     * resultCode : 200
     * message :
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
         * userDetailInfo : {"userId":"A1","friendCode":"F1","userAccount":"admin","userNickname":"會飛的青蛙","userRealName":null,"userRank":99,"userHeadImg":"http:                                                                \t\t\t1551863228&fm=23&gp=0.jpg","userMood":null,"userEmail":null,"userCreateTime":"2016-12-24 22:12:03","userAge":0,"userGender":0,"userBirth":null,"userMajor":null,"userSchool":null,"userAddress":null}
         */

        private UserDetailInfoBean userDetailInfo;

        public UserDetailInfoBean getUserDetailInfo() {
            return userDetailInfo;
        }

        public void setUserDetailInfo(UserDetailInfoBean userDetailInfo) {
            this.userDetailInfo = userDetailInfo;
        }

        public static class UserDetailInfoBean implements Serializable {
            /**
             * userId : A1
             * friendCode : F1
             * userAccount : admin
             * userNickname : 會飛的青蛙
             * userRealName : null
             * userRank : 99
             * userHeadImg : http:                                                                			1551863228&fm=23&gp=0.jpg
             * userMood : null
             * userEmail : null
             * userCreateTime : 2016-12-24 22:12:03
             * userAge : 0
             * userGender : 0
             * userBirth : null
             * userMajor : null
             * userSchool : null
             * userAddress : null
             */

            private String userId;
            private String friendCode;
            private String userAccount;
            private String userNickname;
            private Object userRealName;
            private int userRank;
            private String userHeadImg;
            private Object userMood;
            private Object userEmail;
            private String userCreateTime;
            private int userAge;
            private int userGender;
            private Object userBirth;
            private Object userMajor;
            private Object userSchool;
            private Object userAddress;

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

            public Object getUserMood() {
                return userMood;
            }

            public void setUserMood(Object userMood) {
                this.userMood = userMood;
            }

            public Object getUserEmail() {
                return userEmail;
            }

            public void setUserEmail(Object userEmail) {
                this.userEmail = userEmail;
            }

            public String getUserCreateTime() {
                return userCreateTime;
            }

            public void setUserCreateTime(String userCreateTime) {
                this.userCreateTime = userCreateTime;
            }

            public int getUserAge() {
                return userAge;
            }

            public void setUserAge(int userAge) {
                this.userAge = userAge;
            }

            public int getUserGender() {
                return userGender;
            }

            public void setUserGender(int userGender) {
                this.userGender = userGender;
            }

            public Object getUserBirth() {
                return userBirth;
            }

            public void setUserBirth(Object userBirth) {
                this.userBirth = userBirth;
            }

            public Object getUserMajor() {
                return userMajor;
            }

            public void setUserMajor(Object userMajor) {
                this.userMajor = userMajor;
            }

            public Object getUserSchool() {
                return userSchool;
            }

            public void setUserSchool(Object userSchool) {
                this.userSchool = userSchool;
            }

            public Object getUserAddress() {
                return userAddress;
            }

            public void setUserAddress(Object userAddress) {
                this.userAddress = userAddress;
            }

            @Override
            public int hashCode() {
                return userId.hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                if (obj instanceof UserDetailInfoBean) {
                    return ((UserDetailInfoBean) obj).userId.equals(userId);
                }
                return super.equals(obj);
            }
        }
    }
}
