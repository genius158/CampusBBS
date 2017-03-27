package com.yan.campusbbs.module.selfcenter.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseFragment;
import com.yan.campusbbs.module.selfcenter.action.LogInAction;
import com.yan.campusbbs.module.selfcenter.ui.login.LogInFragment;
import com.yan.campusbbs.module.selfcenter.ui.mainpage.SelfCenterFragment;
import com.yan.campusbbs.util.RxBus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yan on 2017/3/27.
 */

public class MainPageFragment extends BaseFragment {
    @Inject
    RxBus rxBus;

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_self_center_main, container, false);
        ButterKnife.bind(this, view);
        daggerInject();
        init();
        initRxAction();
        return view;
    }

    private void daggerInject() {
        DaggerMainPageComponent.builder()
                .applicationComponent(((ApplicationCampusBBS) getContext().getApplicationContext())
                        .getApplicationComponent())
                .build().inject(this);
    }

    private void initRxAction() {
        addDisposable(rxBus.getEvent(LogInAction.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(logInAction -> {
                    if (logInAction.isLogIn) {
                        getChildFragmentManager().beginTransaction()
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .replace(R.id.fl_fragment_container, new SelfCenterFragment(),"SELF_CENTER")
                                .commit();
                    } else {
                        getChildFragmentManager().beginTransaction()
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .replace(R.id.fl_fragment_container, LogInFragment.newInstance(),"LOGIN")
                                .commit();
                    }
                }, Throwable::printStackTrace));
    }

    public static MainPageFragment newInstance() {
        MainPageFragment mainPageFragment = new MainPageFragment();
        mainPageFragment.setArguments(new Bundle());
        return mainPageFragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: ");
    }

    private static final String TAG = "MainPageFragment";

    private void init() {
        if (TextUtils.isEmpty(ApplicationCampusBBS.getApplication().getSessionId())) {
            Fragment fragment = getChildFragmentManager().findFragmentByTag("LOGIN");
            if (fragment == null || !fragment.isAdded()) {
                fragment = LogInFragment.newInstance();
            }
            getChildFragmentManager().beginTransaction().add(R.id.fl_fragment_container, fragment, "LOGIN").commit();
        } else {
            Fragment fragment = getChildFragmentManager().findFragmentByTag("SELF_CENTER");
            if (fragment == null || !fragment.isAdded()) {
                fragment = new SelfCenterFragment();
            }
            getChildFragmentManager().beginTransaction().add(R.id.fl_fragment_container, fragment, "SELF_CENTER").commit();
        }
    }
}
