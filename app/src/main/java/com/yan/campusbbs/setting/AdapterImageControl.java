package com.yan.campusbbs.setting;

import android.support.v7.widget.RecyclerView;

import com.yan.campusbbs.rxbusaction.ActionImageControl;

/**
 * Created by Administrator on 2017/2/11.
 */

public class AdapterImageControl {
    private static AdapterImageControl adapterImageControl;

    public static AdapterImageControl getInstance() {
        if (adapterImageControl == null) {
            synchronized (ActionImageControl.class) {
                if (adapterImageControl == null) {
                    adapterImageControl = new AdapterImageControl();
                }
            }
        }
        return adapterImageControl;
    }

    private AdapterImageControl() {

    }

    public void attachRecyclerView(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(onScrollListener);
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                ImageControl.getInstance().imageResume();
            } else {
                ImageControl.getInstance().imagePause();
            }
        }
    };
}
