package com.yan.campusbbs.module.setting;

import android.support.v7.widget.RecyclerView;


/**
 * Created by yan on 2017/2/11.
 */

public class AdapterImageControl {
    private ImageControl imageControl;


    public AdapterImageControl(ImageControl imageControl) {
        this.imageControl = imageControl;
    }

    public void attachRecyclerView(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(onScrollListener);
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                imageControl.imageResume();
            } else {
                imageControl.imagePause();
            }
        }
    };
}
