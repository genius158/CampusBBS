package com.yan.campusbbs.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2017/2/8.
 */

public abstract class BaseFragment extends StatedFragment {
    private static final String BUNDLE_IS_LAZY_LOAD ="isLazyLoad";

    protected CompositeDisposable compositeDisposable;
    private View root;
    private boolean isLazyLoad;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compositeDisposable = new CompositeDisposable();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (root == null) {
            root = createView(inflater, container, savedInstanceState);
        } else {
            if (root.getParent() != null) {
                ((ViewGroup) root.getParent()).removeView(root);
            }
        }
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onReloadArguments(Bundle bundle) {
        super.onReloadArguments(bundle);
        isLazyLoad = bundle.getBoolean(BUNDLE_IS_LAZY_LOAD);
    }

    @Override
    protected void onSaveArguments(Bundle bundle) {
        super.onSaveArguments(bundle);
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
