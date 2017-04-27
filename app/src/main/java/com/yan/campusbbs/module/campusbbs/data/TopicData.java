package com.yan.campusbbs.module.campusbbs.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yan on 2017/4/21.
 */

public class TopicData implements Serializable{

    /**
     * data : {"topicInfoList":{"totalCount":0,"totalPages":0,"pageNum":0,"previous":false,"next":false,"pageList":null,"topicList":[{"userId":"U15B524C6A3DU01AH","userAccount":"17780701147","userNickname":"U17780701147","userHeadImg":"http://www.youlanw.com/static/images/man.jpg","topicId":"T15B57E9E956KEZDT","topicTitle":"铁路局","topicContent":"兔兔旅途","typeDiv":1,"topicLabel":null,"contentDiv":0,"likeCount":null,"cmtCount":null,"topicReleaseTime":"2017-04-11 04:49:59"},{"userId":"U15B524C6A3DU01AH","userAccount":"17780701147","userNickname":"U17780701147","userHeadImg":"http://www.youlanw.com/static/images/man.jpg","topicId":"T15B537B483CFE0VV","topicTitle":"睡咯扣女","topicContent":"放松LOL咯","typeDiv":1,"topicLabel":null,"contentDiv":0,"likeCount":null,"cmtCount":null,"topicReleaseTime":"2017-04-10 08:10:40"},{"userId":"U15B524C6A3DU01AH","userAccount":"17780701147","userNickname":"U17780701147","userHeadImg":"http://www.youlanw.com/static/images/man.jpg","topicId":"T15B52CA05DFEUUOU","topicTitle":"近距离咯","topicContent":"李连杰","typeDiv":2,"topicLabel":"健身","contentDiv":0,"likeCount":null,"cmtCount":null,"topicReleaseTime":"2017-04-10 04:57:03"},{"userId":"U15B524C6A3DU01AH","userAccount":"17780701147","userNickname":"U17780701147","userHeadImg":"http://www.youlanw.com/static/images/man.jpg","topicId":"T15B52B7BD94IUGH5","topicTitle":"通景路","topicContent":"T恤拒绝了","typeDiv":1,"topicLabel":"文学","contentDiv":0,"likeCount":null,"cmtCount":null,"topicReleaseTime":"2017-04-10 04:37:05"}]}}
     * jsessionId : 4FAA7AFE2B04F86E61F31A9E82D9A26C
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
         * topicInfoList : {"totalCount":0,"totalPages":0,"pageNum":0,"previous":false,"next":false,"pageList":null,"topicList":[{"userId":"U15B524C6A3DU01AH","userAccount":"17780701147","userNickname":"U17780701147","userHeadImg":"http://www.youlanw.com/static/images/man.jpg","topicId":"T15B57E9E956KEZDT","topicTitle":"铁路局","topicContent":"兔兔旅途","typeDiv":1,"topicLabel":null,"contentDiv":0,"likeCount":null,"cmtCount":null,"topicReleaseTime":"2017-04-11 04:49:59"},{"userId":"U15B524C6A3DU01AH","userAccount":"17780701147","userNickname":"U17780701147","userHeadImg":"http://www.youlanw.com/static/images/man.jpg","topicId":"T15B537B483CFE0VV","topicTitle":"睡咯扣女","topicContent":"放松LOL咯","typeDiv":1,"topicLabel":null,"contentDiv":0,"likeCount":null,"cmtCount":null,"topicReleaseTime":"2017-04-10 08:10:40"},{"userId":"U15B524C6A3DU01AH","userAccount":"17780701147","userNickname":"U17780701147","userHeadImg":"http://www.youlanw.com/static/images/man.jpg","topicId":"T15B52CA05DFEUUOU","topicTitle":"近距离咯","topicContent":"李连杰","typeDiv":2,"topicLabel":"健身","contentDiv":0,"likeCount":null,"cmtCount":null,"topicReleaseTime":"2017-04-10 04:57:03"},{"userId":"U15B524C6A3DU01AH","userAccount":"17780701147","userNickname":"U17780701147","userHeadImg":"http://www.youlanw.com/static/images/man.jpg","topicId":"T15B52B7BD94IUGH5","topicTitle":"通景路","topicContent":"T恤拒绝了","typeDiv":1,"topicLabel":"文学","contentDiv":0,"likeCount":null,"cmtCount":null,"topicReleaseTime":"2017-04-10 04:37:05"}]}
         */

        private TopicInfoListBean topicInfoList;

        public TopicInfoListBean getTopicInfoList() {
            return topicInfoList;
        }

        public void setTopicInfoList(TopicInfoListBean topicInfoList) {
            this.topicInfoList = topicInfoList;
        }

        public static class TopicInfoListBean implements Serializable{
            /**
             * totalCount : 0
             * totalPages : 0
             * pageNum : 0
             * previous : false
             * next : false
             * pageList : null
             * topicList : [{"userId":"U15B524C6A3DU01AH","userAccount":"17780701147","userNickname":"U17780701147","userHeadImg":"http://www.youlanw.com/static/images/man.jpg","topicId":"T15B57E9E956KEZDT","topicTitle":"铁路局","topicContent":"兔兔旅途","typeDiv":1,"topicLabel":null,"contentDiv":0,"likeCount":null,"cmtCount":null,"topicReleaseTime":"2017-04-11 04:49:59"},{"userId":"U15B524C6A3DU01AH","userAccount":"17780701147","userNickname":"U17780701147","userHeadImg":"http://www.youlanw.com/static/images/man.jpg","topicId":"T15B537B483CFE0VV","topicTitle":"睡咯扣女","topicContent":"放松LOL咯","typeDiv":1,"topicLabel":null,"contentDiv":0,"likeCount":null,"cmtCount":null,"topicReleaseTime":"2017-04-10 08:10:40"},{"userId":"U15B524C6A3DU01AH","userAccount":"17780701147","userNickname":"U17780701147","userHeadImg":"http://www.youlanw.com/static/images/man.jpg","topicId":"T15B52CA05DFEUUOU","topicTitle":"近距离咯","topicContent":"李连杰","typeDiv":2,"topicLabel":"健身","contentDiv":0,"likeCount":null,"cmtCount":null,"topicReleaseTime":"2017-04-10 04:57:03"},{"userId":"U15B524C6A3DU01AH","userAccount":"17780701147","userNickname":"U17780701147","userHeadImg":"http://www.youlanw.com/static/images/man.jpg","topicId":"T15B52B7BD94IUGH5","topicTitle":"通景路","topicContent":"T恤拒绝了","typeDiv":1,"topicLabel":"文学","contentDiv":0,"likeCount":null,"cmtCount":null,"topicReleaseTime":"2017-04-10 04:37:05"}]
             */

            private int totalCount;
            private int totalPages;
            private int pageNum;
            private boolean previous;
            private boolean next;
            private int browseCount;
            private Object pageList;
            private List<TopicListBean> topicList;

            public int getBrowseCount() {
                return browseCount;
            }

            public void setBrowseCount(int browseCount) {
                this.browseCount = browseCount;
            }

            public int getTotalCount() {
                return totalCount;
            }

            public void setTotalCount(int totalCount) {
                this.totalCount = totalCount;
            }

            public int getTotalPages() {
                return totalPages;
            }

            public void setTotalPages(int totalPages) {
                this.totalPages = totalPages;
            }

            public int getPageNum() {
                return pageNum;
            }

            public void setPageNum(int pageNum) {
                this.pageNum = pageNum;
            }

            public boolean isPrevious() {
                return previous;
            }

            public void setPrevious(boolean previous) {
                this.previous = previous;
            }

            public boolean isNext() {
                return next;
            }

            public void setNext(boolean next) {
                this.next = next;
            }

            public Object getPageList() {
                return pageList;
            }

            public void setPageList(Object pageList) {
                this.pageList = pageList;
            }

            public List<TopicListBean> getTopicList() {
                return topicList;
            }

            public void setTopicList(List<TopicListBean> topicList) {
                this.topicList = topicList;
            }

            public static class TopicListBean implements Serializable{
                /**
                 * userId : U15B524C6A3DU01AH
                 * userAccount : 17780701147
                 * userNickname : U17780701147
                 * userHeadImg : http://www.youlanw.com/static/images/man.jpg
                 * topicId : T15B57E9E956KEZDT
                 * topicTitle : 铁路局
                 * topicContent : 兔兔旅途
                 * typeDiv : 1
                 * topicLabel : null
                 * contentDiv : 0
                 * likeCount : null
                 * cmtCount : null
                 * topicReleaseTime : 2017-04-11 04:49:59
                 */

                private String userId;
                private String userAccount;
                private String userNickname;
                private String userHeadImg;
                private String topicId;
                private String topicTitle;
                private String topicContent;
                private int browseCount;
                private int typeDiv;
                private Object topicLabel;
                private int contentDiv;
                private Object likeCount;
                private Object cmtCount;
                private String topicReleaseTime;
                private String fileTypeDiv;
                private String fileImage;
                private String fileVideo;

                public String getFileTypeDiv() {
                    return fileTypeDiv;
                }

                public void setFileTypeDiv(String fileTypeDiv) {
                    this.fileTypeDiv = fileTypeDiv;
                }

                public String getFileImage() {
                    return fileImage;
                }

                public void setFileImage(String fileImage) {
                    this.fileImage = fileImage;
                }

                public String getFileVideo() {
                    return fileVideo;
                }

                public void setFileVideo(String fileVideo) {
                    this.fileVideo = fileVideo;
                }

                public int getBrowseCount() {
                    return browseCount;
                }

                public void setBrowseCount(int browseCount) {
                    this.browseCount = browseCount;
                }

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

                public Object getTopicLabel() {
                    return topicLabel;
                }

                public void setTopicLabel(Object topicLabel) {
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
