package com.yan.campusbbs.module.campusbbs.ui.selfcenter.friend;

import com.yan.campusbbs.base.BasePresenter;
import com.yan.campusbbs.base.BaseView;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface FriendContract {

    interface View extends BaseView<Presenter> {
    }

    interface Presenter extends BasePresenter {

    }
}
