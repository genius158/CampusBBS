package com.yan.campusbbs.module.campusbbs.data;

import java.util.List;

/**
 * Created by Administrator on 2017/4/22 0022.
 */

public class TopicDetailData {

    /**
     * data : {"topicDetailInfo":{"userTopicInfo":{"userId":"U15B524C6A3DU01AH","userAccount":"17780701147","userNickname":"U17780701147","userHeadImg":"http://www.youlanw.com/static/images/man.jpg","topicId":"T15B8F32ABFEJEE9H","topicTitle":"扣扣拉库里","topicContent":"送礼物","typeDiv":1,"topicLabel":"高考冲刺","contentDiv":1,"likeCount":null,"cmtCount":null,"topicReleaseTime":"2017-04-21 22:28:34"},"fileList":[]}}
     * jsessionId : E8AF334015708C4D1BB021FF3DB13B77
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

    public static class DataBean {
        /**
         * topicDetailInfo : {"userTopicInfo":{"userId":"U15B524C6A3DU01AH","userAccount":"17780701147","userNickname":"U17780701147","userHeadImg":"http://www.youlanw.com/static/images/man.jpg","topicId":"T15B8F32ABFEJEE9H","topicTitle":"扣扣拉库里","topicContent":"送礼物","typeDiv":1,"topicLabel":"高考冲刺","contentDiv":1,"likeCount":null,"cmtCount":null,"topicReleaseTime":"2017-04-21 22:28:34"},"fileList":[]}
         */

        private TopicDetailInfoBean topicDetailInfo;

        public TopicDetailInfoBean getTopicDetailInfo() {
            return topicDetailInfo;
        }

        public void setTopicDetailInfo(TopicDetailInfoBean topicDetailInfo) {
            this.topicDetailInfo = topicDetailInfo;
        }

        public static class TopicDetailInfoBean {
            /**
             * userTopicInfo : {"userId":"U15B524C6A3DU01AH","userAccount":"17780701147","userNickname":"U17780701147","userHeadImg":"http://www.youlanw.com/static/images/man.jpg","topicId":"T15B8F32ABFEJEE9H","topicTitle":"扣扣拉库里","topicContent":"送礼物","typeDiv":1,"topicLabel":"高考冲刺","contentDiv":1,"likeCount":null,"cmtCount":null,"topicReleaseTime":"2017-04-21 22:28:34"}
             * fileList : []
             */

            private UserTopicInfoBean userTopicInfo;
            private List<?> fileList;

            public UserTopicInfoBean getUserTopicInfo() {
                return userTopicInfo;
            }

            public void setUserTopicInfo(UserTopicInfoBean userTopicInfo) {
                this.userTopicInfo = userTopicInfo;
            }

            public List<?> getFileList() {
                return fileList;
            }

            public void setFileList(List<?> fileList) {
                this.fileList = fileList;
            }

            public static class UserTopicInfoBean {
                /**
                 * userId : U15B524C6A3DU01AH
                 * userAccount : 17780701147
                 * userNickname : U17780701147
                 * userHeadImg : http://www.youlanw.com/static/images/man.jpg
                 * topicId : T15B8F32ABFEJEE9H
                 * topicTitle : 扣扣拉库里
                 * topicContent : 送礼物
                 * typeDiv : 1
                 * topicLabel : 高考冲刺
                 * contentDiv : 1
                 * likeCount : null
                 * cmtCount : null
                 * topicReleaseTime : 2017-04-21 22:28:34
                 */

                private String userId;
                private String userAccount;
                private String userNickname;
                private String userHeadImg;
                private String topicId;
                private String topicTitle;
                private String topicContent;
                private int typeDiv;
                private String topicLabel;
                private int contentDiv;
                private Object likeCount;
                private Object cmtCount;
                private String topicReleaseTime;

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

                public String getUserNickname() {
                    return userNickname;
                }

                public void setUserNickname(String userNickname) {
                    this.userNickname = userNickname;
                }

                public String getUserHeadImg() {
                    return userHeadImg;
                }

                public void setUserHeadImg(String userHeadImg) {
                    this.userHeadImg = userHeadImg;
                }

                public String getTopicId() {
                    return topicId;
                }

                public void setTopicId(String topicId) {
                    this.topicId = topicId;
                }

                public String getTopicTitle() {
                    return topicTitle;
                }

                public void setTopicTitle(String topicTitle) {
                    this.topicTitle = topicTitle;
                }

                public String getTopicContent() {
                    return topicContent;
                }

                public void setTopicContent(String topicContent) {
                    this.topicContent = topicContent;
                }

                public int getTypeDiv() {
                    return typeDiv;
                }

                public void setTypeDiv(int typeDiv) {
                    this.typeDiv = typeDiv;
                }

                public String getTopicLabel() {
                    return topicLabel;
                }

                public void setTopicLabel(String topicLabel) {
                    this.topicLabel = topicLabel;
                }

                public int getContentDiv() {
                    return contentDiv;
                }

                public void setContentDiv(int contentDiv) {
                    this.contentDiv = contentDiv;
                }

                public Object getLikeCount() {
                    return likeCount;
                }

                public void setLikeCount(Object likeCount) {
                    this.likeCount = likeCount;
                }

                public Object getCmtCount() {
                    return cmtCount;
                }

                public void setCmtCount(Object cmtCount) {
                    this.cmtCount = cmtCount;
                }

                public String getTopicReleaseTime() {
                    return topicReleaseTime;
                }

                public void setTopicReleaseTime(String topicReleaseTime) {
                    this.topicReleaseTime = topicReleaseTime;
                }
            }
        }
    }
}
