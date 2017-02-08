package com.yan.campusbbs.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

public class StatedFragment extends BaseFragment {

    private Bundle savedState;
    private Bundle mainBundle;

    public StatedFragment() {
        super();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("savedInstanceState", savedInstanceState + "");
        if (!reStated(savedInstanceState)) {
            onFirstTimeLaunched();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private boolean reStated(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mainBundle = savedInstanceState;
            savedState = savedInstanceState.getBundle("STATEDFRAGMENT");
            if (savedState != null) {
                reLoadArguments(savedState);
                return true;
            }
        } else {
            savedState = new Bundle();
        }
        return false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mainBundle = outState;
        saveStatedArguments();
    }

    private void saveStatedArguments() {
        onSaveArguments(savedState);
        if (mainBundle != null)
            mainBundle.putBundle("STATEDFRAGMENT", savedState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        saveStatedArguments();
    }

    protected void reLoadArguments(Bundle bundle) {

    }

    protected void onSaveArguments(Bundle bundle) {

    }

    protected void onFirstTimeLaunched() {

    }

}
