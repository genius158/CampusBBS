package com.yan.campusbbs.module.campusbbs.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/4/22 0022.
 */

public class TopicDetailData implements Serializable {

    /**
     * data : {"topicDetailInfo":{"userTopicInfo":{"userId":"U15B524C6A3DU01AH","userAccount":"17780701147","userNickname":"北纬34点8度","userHeadImg":"https:                                                               \t\t\t\t4291398784&fm=23&gp=0.jpg","topicId":"T15BAD6372BCNRVC8","topicTitle":"近距离咯","topicContent":"吐了就","typeDiv":1,"topicLabel":"高考冲刺","contentDiv":0,"likeCount":null,"browseCount":2,"topicReleaseTime":"2017-04-27 11:10:27"},"fileList":[{"fileId":"f15BAD6372C46FR6K","fileTypeDiv":1,"fileImage":"20170427111026264.png","fileVideo":null,"fileDesc":null}]}}
     * jsessionId : 19304311105F58BE704E4C9634C0245C
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

    public static class DataBean implements Serializable{
        /**
         * topicDetailInfo : {"userTopicInfo":{"userId":"U15B524C6A3DU01AH","userAccount":"17780701147","userNickname":"北纬34点8度","userHeadImg":"https:                                                               \t\t\t\t4291398784&fm=23&gp=0.jpg","topicId":"T15BAD6372BCNRVC8","topicTitle":"近距离咯","topicContent":"吐了就","typeDiv":1,"topicLabel":"高考冲刺","contentDiv":0,"likeCount":null,"browseCount":2,"topicReleaseTime":"2017-04-27 11:10:27"},"fileList":[{"fileId":"f15BAD6372C46FR6K","fileTypeDiv":1,"fileImage":"20170427111026264.png","fileVideo":null,"fileDesc":null}]}
         */

        private TopicDetailInfoBean topicDetailInfo;

        public TopicDetailInfoBean getTopicDetailInfo() {
            return topicDetailInfo;
        }

        public void setTopicDetailInfo(TopicDetailInfoBean topicDetailInfo) {
            this.topicDetailInfo = topicDetailInfo;
        }

        public static class TopicDetailInfoBean implements Serializable{
            /**
             * userTopicInfo : {"userId":"U15B524C6A3DU01AH","userAccount":"17780701147","userNickname":"北纬34点8度","userHeadImg":"https:                                                               \t\t\t\t4291398784&fm=23&gp=0.jpg","topicId":"T15BAD6372BCNRVC8","topicTitle":"近距离咯","topicContent":"吐了就","typeDiv":1,"topicLabel":"高考冲刺","contentDiv":0,"likeCount":null,"browseCount":2,"topicReleaseTime":"2017-04-27 11:10:27"}
             * fileList : [{"fileId":"f15BAD6372C46FR6K","fileTypeDiv":1,"fileImage":"20170427111026264.png","fileVideo":null,"fileDesc":null}]
             */

            private UserTopicInfoBean userTopicInfo;
            private List<FileListBean> fileList;

            public UserTopicInfoBean getUserTopicInfo() {
                return userTopicInfo;
            }

            public void setUserTopicInfo(UserTopicInfoBean userTopicInfo) {
                this.userTopicInfo = userTopicInfo;
            }

            public List<FileListBean> getFileList() {
                return fileList;
            }

            public void setFileList(List<FileListBean> fileList) {
                this.fileList = fileList;
            }

            public static class UserTopicInfoBean implements Serializable{
                /**
                 * userId : U15B524C6A3DU01AH
                 * userAccount : 17780701147
                 * userNickname : 北纬34点8度
                 * userHeadImg : https:                                                               				4291398784&fm=23&gp=0.jpg
                 * topicId : T15BAD6372BCNRVC8
                 * topicTitle : 近距离咯
                 * topicContent : 吐了就
                 * typeDiv : 1
                 * topicLabel : 高考冲刺
                 * contentDiv : 0
                 * likeCount : null
                 * browseCount : 2
                 * topicReleaseTime : 2017-04-27 11:10:27
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
                private int browseCount;
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

                public int getBrowseCount() {
                    return browseCount;
                }

                public void setBrowseCount(int browseCount) {
                    this.browseCount = browseCount;
                }

                public String getTopicReleaseTime() {
                    return topicReleaseTime;
                }

                public void setTopicReleaseTime(String topicReleaseTime) {
                    this.topicReleaseTime = topicReleaseTime;
                }
            }

            public static class FileListBean implements Serializable{
                /**
                 * fileId : f15BAD6372C46FR6K
                 * fileTypeDiv : 1
                 * fileImage : 20170427111026264.png
                 * fileVideo : null
                 * fileDesc : null
                 */

                private String fileId;
                private int fileTypeDiv;
                private String fileImage;
                private Object fileVideo;
                private Object fileDesc;

                public String getFileId() {
                    return fileId;
                }

                public void setFileId(String fileId) {
                    this.fileId = fileId;
                }

                public int getFileTypeDiv() {
                    return fileTypeDiv;
                }

                public void setFileTypeDiv(int fileTypeDiv) {
                    this.fileTypeDiv = fileTypeDiv;
                }

                public String getFileImage() {
                    return fileImage;
                }

                public void setFileImage(String fileImage) {
                    this.fileImage = fileImage;
                }

                public Object getFileVideo() {
                    return fileVideo;
                }

                public void setFileVideo(Object fileVideo) {
                    this.fileVideo = fileVideo;
                }

                public Object getFileDesc() {
                    return fileDesc;
                }

                public void setFileDesc(Object fileDesc) {
                    this.fileDesc = fileDesc;
                }
            }
        }
    }
}
