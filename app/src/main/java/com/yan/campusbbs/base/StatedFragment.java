package com.yan.campusbbs.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public abstract class StatedFragment extends Fragment {

    private Bundle savedState;
    private Bundle mainBundle;

    public StatedFragment() {
        super();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!reStated(savedInstanceState)) {
            onFirstTimeLaunched();
        }
    }

    private boolean reStated(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mainBundle = savedInstanceState;
            savedState = savedInstanceState.getBundle("STATEDFRAGMENT");
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
    public final void onSaveInstanceState(Bundle outState) {
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

    protected void onReloadArguments(Bundle bundle) {

    }

    protected void onSaveArguments(Bundle bundle) {

    }

    protected void onFirstTimeLaunched() {

    }

}
