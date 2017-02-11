package com.yan.campusbbs.module.campusbbs.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseFragment;
import com.yan.campusbbs.util.sort.Sort;

import butterknife.ButterKnife;

/**
 * Main UI for the add task screen. Users can enter a task title and description.
 */
public class Others extends BaseFragment implements Sort {

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_campus_bbs_others, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        init();
    }


    private void init() {

    }

    public static Others newInstance() {
        return new Others();
    }

    public Others() {
    }

    @Override
    public int getIndex() {
        return 3;
    }
}
