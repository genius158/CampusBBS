package com.yan.campusbbs.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public abstract class StatedFragment extends Fragment {
    private static final String STATED_FRAGMENT = "stated_fragment";
    private Bundle savedState;
    private Bundle mainBundle;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!reStated(savedInstanceState)) {
            onFirstTimeLaunched();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        saveStatedArguments();
    }

    public StatedFragment() {
        super();
    }

    private boolean reStated(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mainBundle = savedInstanceState;
            savedState = savedInstanceState.getBundle(STATED_FRAGMENT);
            if (savedState != null) {
                onReloadArguments(savedState);
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
        if (savedState != null) {
            onSaveArguments(savedState);
        }
        if (mainBundle != null)
            mainBundle.putBundle(STATED_FRAGMENT, savedState);
    }

    protected void onReloadArguments(Bundle bundle) {

    }

    protected void onSaveArguments(Bundle bundle) {

    }

    protected void onFirstTimeLaunched() {

    }

}
