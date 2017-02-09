package com.yan.campusbbs.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yan.campusbbs.R;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2017/2/8.
 */

public abstract class BaseFragment extends Fragment {
    protected CompositeDisposable compositeDisposable;
    private View root;


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

    protected abstract View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    public void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

}
