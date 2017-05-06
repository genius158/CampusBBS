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

package com.yan.campusbbs.module.campusbbs.ui.common.topic;

import com.yan.campusbbs.base.BasePresenter;
import com.yan.campusbbs.base.BaseView;
import com.yan.campusbbs.module.campusbbs.data.TopicData;
import com.yan.campusbbs.repository.entity.DataMultiItem;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface TopicContract {

    interface View extends BaseView<Presenter> {
        void setTopicData(List<DataMultiItem> topicData);

        void setTopicTagData(TopicData topicData);

        void netError();
    }

    interface Presenter extends BasePresenter {
        void getTopicList(
                String pageNum
                , int typeDiv
        );

        void getTopicList(
                String pageNum
                , int typeDiv
                , String topicLabel
        );
    }
}
