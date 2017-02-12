package com.yan.campusbbs.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by yan on 2017/2/8.
 */

public abstract class BaseFragment extends StatedFragment {
    private static final String BUNDLE_IS_LAZY_LOAD = "isLazyLoad";

    protected CompositeDisposable compositeDisposable;
    private View rootView;
    private boolean isRootViewSet;
    private boolean isLazyLoad;
    protected boolean isCreateActivity = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compositeDisposable = new CompositeDisposable();
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
        super.onActivityCreated(savedInstanceState);
        if (!isRootViewSet) {
            isRootViewSet = true;
            activityCreated();
        }
        isCreateActivity = true;
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
        Log.e("reloadbundle", bundle + "");
        isLazyLoad = bundle.getBoolean(BUNDLE_IS_LAZY_LOAD);
    }

    @Override
    protected void onSaveArguments(Bundle bundle) {
        super.onSaveArguments(bundle);
        Log.e("onSavebundle", bundle + "");

        bundle.putBoolean(BUNDLE_IS_LAZY_LOAD, isLazyLoad);

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
            onLoadLazy();
        }
    }

    protected void onLoadLazy() {

    }
}
