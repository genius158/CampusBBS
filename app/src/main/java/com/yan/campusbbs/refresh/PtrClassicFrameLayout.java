package com.yan.campusbbs.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import in.srain.cube.views.ptr.PtrClassicDefaultFooter;
import in.srain.cube.views.ptr.PtrDefaultHandler2;

public class PtrClassicFrameLayout extends PtrFrameLayout {
    private int mPagingTouchSlop;
    private PullToRefreshHeader mPtrClassicHeader;
     private PtrClassicDefaultFooter mPtrClassicFooter;

    public PtrClassicFrameLayout(Context context) {
        super(context);
        initViews();
    }


    public PtrClassicFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public PtrClassicFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }

    public void setRefreshBarMarginTop(int marginTop) {
        mPtrClassicHeader.setRefreshBarMarginTop(marginTop);
    }

    private void initViews() {
        mPtrClassicHeader = new PullToRefreshHeader(getContext());
        setHeaderView(mPtrClassicHeader);
        addPtrUIHandler(mPtrClassicHeader);
     /*   mPtrClassicFooter = new PtrClassicDefaultFooter(getContext());
        setFooterView(mPtrClassicFooter);
        addPtrUIHandler(mPtrClassicFooter);*/
    }

    public void setCanLoadMore(boolean canLoadMore){
        if(canLoadMore){
            mPtrClassicFooter = new PtrClassicDefaultFooter(getContext());
            setFooterView(mPtrClassicFooter);
            addPtrUIHandler(mPtrClassicFooter);
        }
    }


    public PullToRefreshHeader getHeader() {
        return mPtrClassicHeader;
    }

    /**
     * Specify the last update time by this key string
     *
     * @param key
     */
    public void setLastUpdateTimeKey(String key) {
        setLastUpdateTimeHeaderKey(key);
        setLastUpdateTimeFooterKey(key);
    }

    public void setLastUpdateTimeHeaderKey(String key) {
        if (mPtrClassicHeader != null) {
            mPtrClassicHeader.setLastUpdateTimeKey(key);
        }
    }

    public void setLastUpdateTimeFooterKey(String key) {
        if (mPtrClassicFooter != null) {
            mPtrClassicFooter.setLastUpdateTimeKey(key);
        }
    }

    /**
     * Using an object to specify the last update time.
     *
     * @param object
     */
    public void setLastUpdateTimeRelateObject(Object object) {
        setLastUpdateTimeHeaderRelateObject(object);
        setLastUpdateTimeFooterRelateObject(object);
    }

    public void setLastUpdateTimeHeaderRelateObject(Object object) {
        if (mPtrClassicHeader != null) {
            mPtrClassicHeader.setLastUpdateTimeRelateObject(object);
        }
    }

    public void setLastUpdateTimeFooterRelateObject(Object object) {
        if (mPtrClassicFooter != null) {
            mPtrClassicFooter.setLastUpdateTimeRelateObject(object);
        }
    }

    public static abstract class PtrDefaultHandler extends PtrDefaultHandler2 {

        @Override
        public void onLoadMoreBegin(in.srain.cube.views.ptr.PtrFrameLayout frame) {

        }

    }
    private boolean canRefresh = true;

    public void setCanRefresh(boolean canRefresh) {
        this.canRefresh = canRefresh;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        if (e.getActionMasked() == MotionEvent.ACTION_UP ||
                e.getActionMasked() == MotionEvent.ACTION_CANCEL) {
            canRefresh = true;
        }
        if (canRefresh)
            return super.dispatchTouchEvent(e);
        else {
            return super.dispatchTouchEventSupper(e);
        }
    }

}
