package com.yan.campusbbs.module.campusbbs.ui.userinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseActivity;
import com.yan.campusbbs.util.SPUtils;

/**
 * Created by yan on 2017/4/1.
 */

public class UserInfoActivity extends BaseActivity implements UserInfoContract.View {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
    }

    @Override
    protected SPUtils sPUtils() {
        return null;
    }

    @Override
    public void stateSuccess() {

    }

    @Override
    public void stateNetError() {

    }

    @Override
    public void stateError() {

    }
}
