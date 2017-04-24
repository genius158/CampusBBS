package com.yan.campusbbs.module.campusbbs.data;

import java.util.List;

/**
 * Created by yan on 2017/4/24.
 */

public class CommentData {


    /**
     * data : {"commentInfoList":{"totalCount":2,"totalPages":0,"pageNum":0,"previous":false,"next":false,"pageList":null,"commentList":[{"cmtId":"C15B9E9B237EZ6DO7","cmtContent":"啥啥啥啥","cmtTime":"2017-04-24 14:16:59","userId":"U15B524C6A3DU01AH","userAccount":"17780701147","userNickname":"U17780701147","userHeadImg":"http://www.youlanw.com/static/images/man.jpg"},{"cmtId":"C15B9E6C0749Y79HD","cmtContent":"ckfjvvjc","cmtTime":"2017-04-24 13:25:32","userId":"U15B524C6A3DU01AH","userAccount":"17780701147","userNickname":"U17780701147","userHeadImg":"http://www.youlanw.com/static/images/man.jpg"}]}}
     * jsessionId : 0DC29D9C3FEC91D29CBE4366247C9CD2
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
         * commentInfoList : {"totalCount":2,"totalPages":0,"pageNum":0,"previous":false,"next":false,"pageList":null,"commentList":[{"cmtId":"C15B9E9B237EZ6DO7","cmtContent":"啥啥啥啥","cmtTime":"2017-04-24 14:16:59","userId":"U15B524C6A3DU01AH","userAccount":"17780701147","userNickname":"U17780701147","userHeadImg":"http://www.youlanw.com/static/images/man.jpg"},{"cmtId":"C15B9E6C0749Y79HD","cmtContent":"ckfjvvjc","cmtTime":"2017-04-24 13:25:32","userId":"U15B524C6A3DU01AH","userAccount":"17780701147","userNickname":"U17780701147","userHeadImg":"http://www.youlanw.com/static/images/man.jpg"}]}
         */

        private CommentInfoListBean commentInfoList;

        public CommentInfoListBean getCommentInfoList() {
            return commentInfoList;
        }

        public void setCommentInfoList(CommentInfoListBean commentInfoList) {
            this.commentInfoList = commentInfoList;
        }

        public static class CommentInfoListBean {
            /**
             * totalCount : 2
             * totalPages : 0
             * pageNum : 0
             * previous : false
             * next : false
             * pageList : null
             * commentList : [{"cmtId":"C15B9E9B237EZ6DO7","cmtContent":"啥啥啥啥","cmtTime":"2017-04-24 14:16:59","userId":"U15B524C6A3DU01AH","userAccount":"17780701147","userNickname":"U17780701147","userHeadImg":"http://www.youlanw.com/static/images/man.jpg"},{"cmtId":"C15B9E6C0749Y79HD","cmtContent":"ckfjvvjc","cmtTime":"2017-04-24 13:25:32","userId":"U15B524C6A3DU01AH","userAccount":"17780701147","userNickname":"U17780701147","userHeadImg":"http://www.youlanw.com/static/images/man.jpg"}]
             */

            private int totalCount;
            private int totalPages;
            private int pageNum;
            private boolean previous;
            private boolean next;
            private Object pageList;
            private List<CommentListBean> commentList;

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

            public List<CommentListBean> getCommentList() {
                return commentList;
            }

            public void setCommentList(List<CommentListBean> commentList) {
                this.commentList = commentList;
            }

            public static class CommentListBean {
                /**
                 * cmtId : C15B9E9B237EZ6DO7
                 * cmtContent : 啥啥啥啥
                 * cmtTime : 2017-04-24 14:16:59
                 * userId : U15B524C6A3DU01AH
                 * userAccount : 17780701147
                 * userNickname : U17780701147
                 * userHeadImg : http://www.youlanw.com/static/images/man.jpg
                 */

                private String cmtId;
                private String cmtContent;
                private String cmtTime;
                private String userId;
                private String userAccount;
                private String userNickname;
                private String userHeadImg;

                public String getCmtId() {
                    return cmtId;
                }

                public void setCmtId(String cmtId) {
                    this.cmtId = cmtId;
                }

                public String getCmtContent() {
                    return cmtContent;
                }

                public void setCmtContent(String cmtContent) {
                    this.cmtContent = cmtContent;
                }

                public String getCmtTime() {
                    return cmtTime;
                }

                public void setCmtTime(String cmtTime) {
                    this.cmtTime = cmtTime;
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
            }
        }
    }
}
