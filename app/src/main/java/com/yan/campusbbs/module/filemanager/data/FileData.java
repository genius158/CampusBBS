package com.yan.campusbbs.module.filemanager.data;

import java.util.List;

/**
 * Created by yan on 2017/5/19.
 */

public class FileData {

    /**
     * data : {"fileInfoList":{"totalCount":0,"totalPages":0,"pageNum":0,"previous":false,"next":false,"pageList":null,"fileList":[{"fileId":"f15BE7E29521GV68D","fileImage":"","fileVideo":"1494472617115.mp4","fileTypeDiv":2,"createTime":"2017-05-11 12:14:42","userAccount":null}]}}
     * jsessionId : 26B576A0DA7B5E774E88F7A9BB053AA6
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
         * fileInfoList : {"totalCount":0,"totalPages":0,"pageNum":0,"previous":false,"next":false,"pageList":null,"fileList":[{"fileId":"f15BE7E29521GV68D","fileImage":"","fileVideo":"1494472617115.mp4","fileTypeDiv":2,"createTime":"2017-05-11 12:14:42","userAccount":null}]}
         */

        private FileInfoListBean fileInfoList;

        public FileInfoListBean getFileInfoList() {
            return fileInfoList;
        }

        public void setFileInfoList(FileInfoListBean fileInfoList) {
            this.fileInfoList = fileInfoList;
        }

        public static class FileInfoListBean {
            /**
             * totalCount : 0
             * totalPages : 0
             * pageNum : 0
             * previous : false
             * next : false
             * pageList : null
             * fileList : [{"fileId":"f15BE7E29521GV68D","fileImage":"","fileVideo":"1494472617115.mp4","fileTypeDiv":2,"createTime":"2017-05-11 12:14:42","userAccount":null}]
             */

            private int totalCount;
            private int totalPages;
            private int pageNum;
            private boolean previous;
            private boolean next;
            private Object pageList;
            private List<FileListBean> fileList;

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

            public List<FileListBean> getFileList() {
                return fileList;
            }

            public void setFileList(List<FileListBean> fileList) {
                this.fileList = fileList;
            }

            public static class FileListBean {
                /**
                 * fileId : f15BE7E29521GV68D
                 * fileImage :
                 * fileVideo : 1494472617115.mp4
                 * fileTypeDiv : 2
                 * createTime : 2017-05-11 12:14:42
                 * userAccount : null
                 */

                private String fileId;
                private String fileImage;
                private String fileVideo;
                private int fileTypeDiv;
                private String createTime;
                private Object userAccount;

                public String getFileId() {
                    return fileId;
                }

                public void setFileId(String fileId) {
                    this.fileId = fileId;
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

                public int getFileTypeDiv() {
                    return fileTypeDiv;
                }

                public void setFileTypeDiv(int fileTypeDiv) {
                    this.fileTypeDiv = fileTypeDiv;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }

                public Object getUserAccount() {
                    return userAccount;
                }

                public void setUserAccount(Object userAccount) {
                    this.userAccount = userAccount;
                }
            }
        }
    }
}
