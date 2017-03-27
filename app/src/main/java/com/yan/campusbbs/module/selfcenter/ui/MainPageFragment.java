package com.yan.campusbbs.module.selfcenter.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
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
        init();
        initRxAction();
        return view;
    }

    private void initRxAction() {
        addDisposable(rxBus.getEvent(LogInAction.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(logInAction -> {
                    if (logInAction.isLogIn) {
                        getFragmentManager().beginTransaction()
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .replace(R.id.fl_fragment_container, new SelfCenterFragment())
                                .commit();
                    } else {
                        getFragmentManager().beginTransaction()
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .replace(R.id.fl_fragment_container, LogInFragment.newInstance())
                                .commit();
                    }
                }, Throwable::printStackTrace));
    }

    public static MainPageFragment newInstance() {
        MainPageFragment mainPageFragment = new MainPageFragment();
        mainPageFragment.setArguments(new Bundle());
        return mainPageFragment;
    }

    private void init() {
        if (TextUtils.isEmpty(ApplicationCampusBBS.getApplication().getSessionId())) {
            getChildFragmentManager().beginTransaction().add(R.id.fl_fragment_container, LogInFragment.newInstance()).commit();
        } else {
            getChildFragmentManager().beginTransaction().add(R.id.fl_fragment_container, new SelfCenterFragment()).commit();
        }
    }
}
