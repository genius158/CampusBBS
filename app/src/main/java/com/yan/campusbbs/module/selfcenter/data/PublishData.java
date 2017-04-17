package com.yan.campusbbs.module.selfcenter.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yan on 2017/3/30.
 */

public class PublishData implements Serializable {

    /**
     * data : {"topicInfoList":{"totalCount":0,"totalPages":0,"pageNum":0,"previous":false,"next":false,"pageList":null,"topicList":[{"userId":"A1","userNickname":"會飛的青蛙","userHeadImg":"123.jpg","topicId":"T2","topicTitle":"天天向上","topicContent":"好生学习，以后当校长！","typeDiv":1,"contentDiv":0,"likeCount":"0","cmtCount":"0","topicReleaseTime":"2017-02-26 02:27:14"},{"userId":"U1","userNickname":"逃課的中隊長","userHeadImg":"124.jpg","topicId":"T2","topicTitle":"天天向上","topicContent":"好生学习，以后当校长！","typeDiv":1,"contentDiv":0,"likeCount":"0","cmtCount":"0","topicReleaseTime":"2017-02-26 02:27:14"},{"userId":"A1","userNickname":"會飛的青蛙","userHeadImg":"123.jpg","topicId":"T1","topicTitle":"膜蛤大法好","topicContent":"这是一个生气的膜法师世界！","typeDiv":3,"contentDiv":0,"likeCount":"0","cmtCount":"1","topicReleaseTime":"2017-02-26 02:27:12"}]}}
     * jsessionId : 1C138F890434BCE49F30409030D06A46
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
         * topicInfoList : {"totalCount":0,"totalPages":0,"pageNum":0,"previous":false,"next":false,"pageList":null,"topicList":[{"userId":"A1","userNickname":"會飛的青蛙","userHeadImg":"123.jpg","topicId":"T2","topicTitle":"天天向上","topicContent":"好生学习，以后当校长！","typeDiv":1,"contentDiv":0,"likeCount":"0","cmtCount":"0","topicReleaseTime":"2017-02-26 02:27:14"},{"userId":"U1","userNickname":"逃課的中隊長","userHeadImg":"124.jpg","topicId":"T2","topicTitle":"天天向上","topicContent":"好生学习，以后当校长！","typeDiv":1,"contentDiv":0,"likeCount":"0","cmtCount":"0","topicReleaseTime":"2017-02-26 02:27:14"},{"userId":"A1","userNickname":"會飛的青蛙","userHeadImg":"123.jpg","topicId":"T1","topicTitle":"膜蛤大法好","topicContent":"这是一个生气的膜法师世界！","typeDiv":3,"contentDiv":0,"likeCount":"0","cmtCount":"1","topicReleaseTime":"2017-02-26 02:27:12"}]}
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
             * topicList : [{"userId":"A1","userNickname":"會飛的青蛙","userHeadImg":"123.jpg","topicId":"T2","topicTitle":"天天向上","topicContent":"好生学习，以后当校长！","typeDiv":1,"contentDiv":0,"likeCount":"0","cmtCount":"0","topicReleaseTime":"2017-02-26 02:27:14"},{"userId":"U1","userNickname":"逃課的中隊長","userHeadImg":"124.jpg","topicId":"T2","topicTitle":"天天向上","topicContent":"好生学习，以后当校长！","typeDiv":1,"contentDiv":0,"likeCount":"0","cmtCount":"0","topicReleaseTime":"2017-02-26 02:27:14"},{"userId":"A1","userNickname":"會飛的青蛙","userHeadImg":"123.jpg","topicId":"T1","topicTitle":"膜蛤大法好","topicContent":"这是一个生气的膜法师世界！","typeDiv":3,"contentDiv":0,"likeCount":"0","cmtCount":"1","topicReleaseTime":"2017-02-26 02:27:12"}]
             */

            private int totalCount;
            private int totalPages;
            private int pageNum;
            private boolean previous;
            private boolean next;
            private Object pageList;
            private List<TopicListBean> topicList;

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
                 * userId : A1
                 * userNickname : 會飛的青蛙
                 * userHeadImg : 123.jpg
                 * topicId : T2
                 * topicTitle : 天天向上
                 * topicContent : 好生学习，以后当校长！
                 * typeDiv : 1
                 * contentDiv : 0
                 * likeCount : 0
                 * cmtCount : 0
                 * topicReleaseTime : 2017-02-26 02:27:14
                 */

                private String userId;
                private String userNickname;
                private String userHeadImg;
                private String topicId;
                private String topicTitle;
                private String topicContent;
                private int typeDiv;
                private int contentDiv;
                private String likeCount;
                private String cmtCount;
                private String topicReleaseTime;

                public String getUserId() {
                    return userId;
                }

                public void setUserId(String userId) {
                    this.userId = userId;
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

                public int getContentDiv() {
                    return contentDiv;
                }

                public void setContentDiv(int contentDiv) {
                    this.contentDiv = contentDiv;
                }

                public String getLikeCount() {
                    return likeCount;
                }

                public void setLikeCount(String likeCount) {
                    this.likeCount = likeCount;
                }

                public String getCmtCount() {
                    return cmtCount;
                }

                public void setCmtCount(String cmtCount) {
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
