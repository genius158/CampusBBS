package com.yan.campusbbs.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yan on 2017/2/8.
 */

public abstract class BaseFragment extends StatedFragment {

    protected CompositeDisposable compositeDisposable= new CompositeDisposable();
    private View rootView;
    private boolean isRootViewSet;
    private boolean isLazyLoad;
    protected boolean isCreateActivity = false;
    private Bundle reLoadBundle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = createView(inflater, container, savedInstanceState);
            isRootViewSet = false;

        } else {
            if (rootView.getParent() != null) {
                ((ViewGroup) rootView.getParent()).removeView(rootView);
            }
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (!isRootViewSet) {
            isRootViewSet = true;
            activityCreated();
        }
        isCreateActivity = true;

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    public void activityCreated() {

    }

    @Override
    protected void onReloadArguments(Bundle bundle) {
        super.onReloadArguments(bundle);
        reLoadBundle = bundle;
        isLazyLoad = bundle.getBoolean("isLazyLoad", true);

        if (isLazyLoad) {
            reloadData();
        }
    }

    private void reloadData() {
    }

    @Override
    protected void onSaveArguments(Bundle bundle) {
        super.onSaveArguments(bundle);
        bundle.putBoolean("isLazyLoad", isLazyLoad);
    }

    protected abstract View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    public void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !isLazyLoad) {
            isLazyLoad = true;
            getDisposable();
        }
    }

    private void getDisposable() {
        compositeDisposable.add(Observable.timer(100, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                            if (isCreateActivity) {
                                onLoadLazy(reLoadBundle);
                                return;
                            }
                            getDisposable();
                        }
                ));
    }

    protected void onLoadLazy(Bundle reLoadBundle) {

    }
}
