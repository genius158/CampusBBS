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

package com.yan.campusbbs.module.campusbbs.ui.userinfo;

import com.yan.campusbbs.base.BasePresenter;
import com.yan.campusbbs.base.BaseView;
import com.yan.campusbbs.module.selfcenter.data.UserInfoData;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface UserInfoContract {

    interface View extends BaseView<Presenter> {
        void stateSuccess();

        void stateNetError();

        void stateError(String msg);
        void setSelfInfo(UserInfoData selfInfo);
    }

    interface Presenter extends BasePresenter {
        void modify(
                String nickname,
                String realName,
                String headImg,
                String mood,
                String email,
                String age,
                String gender,
                String birth,
                String major,
                String school,
                String address
        );
        void getSelfInfo();
    }
}
