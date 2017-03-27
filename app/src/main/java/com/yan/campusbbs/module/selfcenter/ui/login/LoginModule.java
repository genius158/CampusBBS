package com.yan.campusbbs.module.selfcenter.ui.login;


import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yan on 2017/2/7.
 */
@Module
public class LoginModule {
    private final LoginContract.View mView;

    public LoginModule(LoginContract.View view) {
        this.mView = view;
    }

    @Provides
    LoginPresenter getLoginPresenter(Context context) {
        return new LoginPresenter(context, mView);
    }

}
