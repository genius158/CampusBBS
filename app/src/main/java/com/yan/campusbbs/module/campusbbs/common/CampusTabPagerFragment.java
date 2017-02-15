package com.yan.campusbbs.module.campusbbs.common;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View; 
import android.view.animation.LinearInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.OvershootInterpolator;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.nineoldandroids.animation.ValueAnimator;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseRefreshFragment;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.rxbusaction.ActionPagerTabClose;
import com.yan.campusbbs.util.AnimationHelper;
import com.yan.campusbbs.util.RxBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by yan on 2017/2/8.
 */

public abstract class CampusTabPagerFragment extends BaseRefreshFragment {
    private static final String BUNDLE_TAB_SELECT_POSITION = "tabSelectPosition";
    private static final String BUNDLE_APP_BAR_Y = "appBarY";

    private List<CampusPagerTabAdapter.PagerTabItem> pagerTabItems;
    private CampusPagerTabAdapter campusPagerTabAdapter;
    private RecyclerView pagerBarRecycler;
    private LinearLayoutManager linearLayoutManager;
    private BaseQuickAdapter.OnRecyclerViewItemClickListener pagerTabItemOnClick;
    private View appBar;
    private RxBus rxBus;
    protected int tabSelectPosition = 0;
    private CampusAppHelperAdd campusAppHelperAdd;

    protected CampusTabPagerFragment() {
        pagerTabItems = new ArrayList<>();
    }

    public void attach(RecyclerView recyclerView
            , List<CampusPagerTabAdapter.PagerTabItem> pagerTabItems
            , RecyclerView pagerBarRecycler
            , CampusPagerTabAdapter campusPagerTabAdapter
            , View appBar
            , RxBus rxBus) {
        this.pagerTabItems = pagerTabItems;
        this.pagerBarRecycler = pagerBarRecycler;
        this.campusPagerTabAdapter = campusPagerTabAdapter;
        this.rxBus = rxBus;
        this.appBar = appBar;
        initRxAction();
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        this.pagerBarRecycler.setLayoutManager(linearLayoutManager);
        this.pagerBarRecycler.setAdapter(this.campusPagerTabAdapter);
        this.campusPagerTabAdapter.setOnRecyclerViewItemClickListener(getItemClickListener());
        campusAppHelperAdd.appHelperAdd(appBar);
    }

    private BaseQuickAdapter.OnRecyclerViewItemClickListener getItemClickListener() {
        return (view, position) -> {
            tabSelectPosition = position;
            if (pagerTabItemOnClick != null) {
                pagerTabItemOnClick.onItemClick(view, position);
            }
            for (int i = 0; i < pagerTabItems.size(); i++) {
                if (i == position) {
                    pagerTabItems.get(i).isSelect = true;
                } else {
                    pagerTabItems.get(i).isSelect = false;
                }
            }
            campusPagerTabAdapter.notifyDataSetChanged();
        };
    }

    private void initRxAction() {
        addDisposable(rxBus.getEvent(ActionPagerTabClose.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(actionPagerTabClose -> {
                    tabAnimationHide();
                }));
    }

    public void setPagerTabItemOnClick(BaseQuickAdapter.OnRecyclerViewItemClickListener pagerTabItemOnClick) {
        this.pagerTabItemOnClick = pagerTabItemOnClick;
    }

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        super.changeSkin(actionChangeSkin);
        campusPagerTabAdapter.changeSkin(actionChangeSkin);
    }

    @Override
    protected void onSaveArguments(Bundle bundle) {
        super.onSaveArguments(bundle);
        bundle.putInt(BUNDLE_TAB_SELECT_POSITION, tabSelectPosition);
        bundle.putFloat(BUNDLE_APP_BAR_Y, appBar.getY());
    }

    @Override
    protected void onReloadArguments(Bundle bundle) {
        super.onReloadArguments(bundle);

        tabSelectPosition = bundle.getInt(BUNDLE_TAB_SELECT_POSITION, 0);
        pagerBarRecycler.scrollToPosition(tabSelectPosition);


        pagerTabItems.get(tabSelectPosition).isSelect = true;
        campusPagerTabAdapter.notifyDataSetChanged();

        appBar.setY(bundle.getFloat(BUNDLE_APP_BAR_Y, 0));
    }


    public void setCampusAppHelperAdd(CampusAppHelperAdd campusAppHelperAdd) {
        this.campusAppHelperAdd = campusAppHelperAdd;
    }


    private boolean isPagerMoreShow;
    private int pagerBarMoreHeight;

    public void onArrowClick() {
        if (pagerBarMoreHeight == 0) {
            pagerBarMoreLayout().measure(
                    View.MeasureSpec.makeMeasureSpec(0,
                            View.MeasureSpec.UNSPECIFIED)
                    , View.MeasureSpec.makeMeasureSpec(0,
                            View.MeasureSpec.UNSPECIFIED)
            );
            pagerBarMoreHeight = pagerBarMoreLayout().getMeasuredHeight();
        }

        if (!isPagerMoreShow) {
            tabAnimationShow();
        } else {
            tabAnimationHide();
        }
    }

    private ValueAnimator animationHide;
    private ValueAnimator animationShow;
    private float[] heightValue = new float[1];

    protected abstract AnimationHelper animationHelper();

    protected abstract View pagerBarMore();

    protected abstract View pagerBarMoreArrow();

    protected abstract View pagerBarMoreLayout();


    private void tabAnimationShow() {
        if (isPagerMoreShow) {
            return;
        }
        isPagerMoreShow = true;
        pagerBarMore().setVisibility(View.VISIBLE);

        //---------------
        if (animationHide != null && animationHide.isRunning()) {
            animationHide.cancel();
        }
        if (animationShow == null) {
            animationShow = getAnimatorShow();
        } else {
            animationShow.setFloatValues(heightValue[0], 0);
        }
        animationShow.start();

        //--------------------------

        if (animationHideArrow != null && animationHideArrow.isRunning()) {
            animationHideArrow.cancel();
        }
        if (animationShowArrow == null) {
            animationShowArrow = getAnimatorShowArrow();
        } else {
            animationShowArrow.setFloatValues(rotationValueArrow[0], 180);
        }
        animationShowArrow.start();
    }

    private void tabAnimationHide() {
        if (!isPagerMoreShow) {
            return;
        }
        isPagerMoreShow = false;
        if (animationShow != null && animationShow.isRunning()) {
            animationShow.cancel();
        }
        if (animationHide == null) {
            animationHide = getAnimatorHide();
        } else {
            animationHide.setFloatValues(heightValue[0]
                    , -pagerBarMoreHeight + getResources().getDimension(R.dimen.action_bar_height_double)*3);
        }
        animationHide.start();

        //--------------------------

        if (animationShowArrow != null && animationShowArrow.isRunning()) {
            animationShowArrow.cancel();
        }
        if (animationHideArrow == null) {
            animationHideArrow = getAnimatorHideArrow();
        } else {
            animationHideArrow.setFloatValues(rotationValueArrow[0], 0);
        }
        animationHideArrow.start();

    }

    private ValueAnimator getAnimatorShow() {
        return animationHelper().createAnimation(1, pagerBarMore()
                , AnimationHelper.AnimationType.TRANSLATEY
                , 350
                , new OvershootInterpolator(0.5f)
                , null
                , heightValue
                , -pagerBarMoreHeight + getResources().getDimension(R.dimen.action_bar_height_double)*3
                , 0);
    }

    private ValueAnimator getAnimatorHide() {
        return animationHelper().createAnimation(2, pagerBarMore()
                , AnimationHelper.AnimationType.TRANSLATEY
                , 500
                , new AnticipateOvershootInterpolator(0.6f)
                , null
                , heightValue
                , 0
                , -pagerBarMoreHeight + getResources().getDimension(R.dimen.action_bar_height_double)*3
        );
    }

    private ValueAnimator animationHideArrow;
    private ValueAnimator animationShowArrow;
    private float[] rotationValueArrow = new float[1];

    private ValueAnimator getAnimatorShowArrow() {
        return animationHelper().createAnimation(3, pagerBarMoreArrow()
                , AnimationHelper.AnimationType.ROTATION
                , 350
                , new OvershootInterpolator()
                , null
                , rotationValueArrow
                , 0
                , 180);
    }

    private ValueAnimator getAnimatorHideArrow() {
        return animationHelper().createAnimation(4, pagerBarMoreArrow()
                , AnimationHelper.AnimationType.ROTATION
                , 500
                , new AnticipateOvershootInterpolator()
                , null
                , rotationValueArrow
                , 180
                , 0
        );
    }

}
