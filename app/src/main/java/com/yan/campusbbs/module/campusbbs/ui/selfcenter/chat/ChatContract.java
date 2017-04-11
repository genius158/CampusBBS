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

package com.yan.campusbbs.module.campusbbs.ui.selfcenter.chat;

import com.tencent.TIMMessage;
import com.tencent.TIMUserProfile;
import com.yan.campusbbs.base.BasePresenter;
import com.yan.campusbbs.base.BaseView;
import com.yan.campusbbs.module.campusbbs.ui.selfcenter.friend.FriendContract;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface ChatContract {


    interface View extends BaseView<FriendContract.Presenter> {
        void setData(List<TIMMessage> data);

        void setLoadMoreData(List<TIMMessage> data);

        void getDataError();

        void addLatestData(TIMMessage timMessage);

        void setTitle(String title);
    }

    interface Presenter extends BasePresenter {

        void initData();

        void getMoreChatData();

        void getLatestData();

        TIMUserProfile getSelfProfile();

        void getOtherProfile();
    }
}
