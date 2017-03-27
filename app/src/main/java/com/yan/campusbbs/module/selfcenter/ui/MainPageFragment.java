package com.yan.campusbbs.module.selfcenter.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseFragment;
import com.yan.campusbbs.module.selfcenter.ui.login.LogInFragment;
import com.yan.campusbbs.module.selfcenter.ui.mainpage.SelfCenterFragment;

import butterknife.ButterKnife;

/**
 * Created by yan on 2017/3/27.
 */

public class MainPageFragment extends BaseFragment {

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_self_center_main, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    public static MainPageFragment newInstance() {
        MainPageFragment mainPageFragment = new MainPageFragment();
        mainPageFragment.setArguments(new Bundle());
        return mainPageFragment;
    }

    private void init() {
        if (TextUtils.isEmpty(ApplicationCampusBBS.getApplication().getSessionId()) ) {
            getChildFragmentManager().beginTransaction().add(R.id.fl_fragment_container, LogInFragment.newInstance()).commit();
        } else {
            getChildFragmentManager().beginTransaction().add(R.id.fl_fragment_container, new SelfCenterFragment()).commit();
        }
    }
}
