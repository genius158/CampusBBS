package com.yan.campusbbs.base;

import android.support.v4.app.Fragment;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2017/2/8.
 */

public class BaseFragment extends Fragment {
    private CompositeDisposable compositeDisposable;

    public synchronized void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
        super.onDestroy();
    }
}
