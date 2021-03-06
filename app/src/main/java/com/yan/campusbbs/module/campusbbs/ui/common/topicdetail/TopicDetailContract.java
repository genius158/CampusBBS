/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yan.campusbbs.module.campusbbs.ui.common.topicdetail;

import com.yan.campusbbs.base.BasePresenter;
import com.yan.campusbbs.base.BaseView;
import com.yan.campusbbs.module.campusbbs.data.CommentData;
import com.yan.campusbbs.module.campusbbs.data.ReplyCommentData;
import com.yan.campusbbs.module.campusbbs.data.TopicDetailData;
import com.yan.campusbbs.module.campusbbs.data.TopicLikeData;

import okhttp3.ResponseBody;
import retrofit2.http.Query;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface TopicDetailContract {

    interface View extends BaseView<Presenter> {
        void netError();

        void setTopicDetail(TopicDetailData topicDetail);

        void setReplyList(CommentData replyList);

        void reply(ReplyCommentData replyCommentData);
        void topicLike(TopicLikeData topicLikeData);
    }

    interface Presenter extends BasePresenter {
        void getTopicDetail(String topicId);

        void getReplyList(String topicId
                , String pageNum);

        void topicLike(
                String topicId
                , String likeCount
        );

        void replyComment(
                String topicId
                , String content
        );
    }
}
