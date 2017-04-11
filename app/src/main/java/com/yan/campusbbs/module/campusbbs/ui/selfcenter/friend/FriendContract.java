package com.yan.campusbbs.module.campusbbs.ui.selfcenter.friend;

import com.tencent.TIMMessage;
import com.yan.campusbbs.base.BasePresenter;
import com.yan.campusbbs.base.BaseView;
import com.yan.campusbbs.module.campusbbs.data.SelfCenterFriendData;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface FriendContract {

    interface View extends BaseView<Presenter> {
        void addConversationData(SelfCenterFriendData timMessage);

        void update();

        void error(String s);
    }

    interface Presenter extends BasePresenter {
        void getConversation();
    }
}
