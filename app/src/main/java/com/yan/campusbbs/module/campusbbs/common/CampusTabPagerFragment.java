package com.yan.campusbbs.module.campusbbs.common;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.OvershootInterpolator;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.nineoldandroids.animation.ValueAnimator;
import com.yan.campusbbs.base.BaseRefreshFragment;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.rxbusaction.ActionPagerTabClose;
import com.yan.campusbbs.util.AnimationHelper;
import com.yan.campusbbs.util.RxBus;
import com.yan.campusbbs.util.SizeUtils;
import com.yan.campusbbs.widget.GravitySnapHelper;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by yan on 2017/2/8.
 */

public abstract class CampusTabPagerFragment extends BaseRefreshFragment {
    private static final String BUNDLE_TAB_SELECT_POSITION = "tabSelectPosition";
    private static final String BUNDLE_RECYCLER_Y = "recyclerY";
    private static final String BUNDLE_APP_BAR_Y = "appBarY";

    private OnItemClickListener pagerTabItemOnClick;
    protected int tabSelectPosition = 0;
    private CampusAppHelperAdd campusAppHelperAdd;

    protected CampusTabPagerFragment() {

    }

    @Override
    public void activityCreated() {
        super.activityCreated();

        initRxAction();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        pagerBarRecycler().setLayoutManager(linearLayoutManager);
        pagerBarRecycler().setAdapter(campusPagerTabAdapter());

        campusPagerTabAdapter().setOnItemClickListener(onItemClickListener);
        campusPagerTabMoreAdapter().setOnItemClickListener(onItemClickListener);
        campusAppHelperAdd.appHelperAdd(appBar());

//        SnapHelper snapHelperStart = new GravitySnapHelper(Gravity.START);
//        snapHelperStart.attachToRecyclerView(pagerBarRecycler());
    }

    private OnItemClickListener onItemClickListener = new OnItemClickListener() {

        @Override
        public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
            tabSelectPosition = position;
            if (pagerTabItemOnClick != null) {
                pagerTabItemOnClick.onItemClick(adapter, view, position);
            }
            for (int i = 0; i < pagerTabItems().size(); i++) {
                if (i == position) {
                    pagerTabItems().get(i).isSelect = true;
                } else {
                    pagerTabItems().get(i).isSelect = false;
                }
            }

            campusPagerTabMoreAdapter().notifyDataSetChanged();
            campusPagerTabAdapter().notifyDataSetChanged();

            pagerBarRecycler().smoothScrollToPosition(position);
            rxBus().post(new ActionPagerTabClose());
        }
    };

    private void initRxAction() {
        addDisposable(rxBus().getEvent(ActionPagerTabClose.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(actionPagerTabClose -> {
                    tabAnimationHide();
                }));
    }

    public void setPagerTabItemOnClick(OnItemClickListener pagerTabItemOnClick) {
        this.pagerTabItemOnClick = pagerTabItemOnClick;
    }

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        super.changeSkin(actionChangeSkin);
        campusPagerTabAdapter().changeSkin(actionChangeSkin);
        campusPagerTabMoreAdapter().changeSkin(actionChangeSkin);
    }

    @Override
    protected void onSaveArguments(Bundle bundle) {
        super.onSaveArguments(bundle);
        bundle.putInt(BUNDLE_TAB_SELECT_POSITION, tabSelectPosition);
        bundle.putFloat(BUNDLE_APP_BAR_Y, appBar().getY());
        bundle.putFloat(BUNDLE_RECYCLER_Y, getScrollYDistance());
    }

    @Override
    protected void onReloadArguments(Bundle bundle) {
        super.onReloadArguments(bundle);
        tabSelectPosition = bundle.getInt(BUNDLE_TAB_SELECT_POSITION, 0);
        pagerBarRecycler().scrollToPosition(tabSelectPosition);
        recyclerView().scrollTo(0, (int) bundle.getFloat(BUNDLE_RECYCLER_Y, 0));

        pagerTabItems().get(tabSelectPosition).isSelect = true;
        campusPagerTabAdapter().notifyDataSetChanged();
        campusPagerTabMoreAdapter().notifyDataSetChanged();

        appBar().setY(bundle.getFloat(BUNDLE_APP_BAR_Y, 0));
    }

    public void setCampusAppHelperAdd(CampusAppHelperAdd campusAppHelperAdd) {
        this.campusAppHelperAdd = campusAppHelperAdd;
    }

    private boolean isPagerMoreShow;
    private int pagerBarMoreHeight;

    public void onArrowClick() {
        if (pagerBarMoreHeight == 0) {
            pagerBarMoreHeight = (int) SizeUtils.getFullScreenHeight(getContext());
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


    private void tabAnimationShow() {
        if (isPagerMoreShow) {
            return;
        }
        isPagerMoreShow = true;
        pagerBarMore().setVisibility(View.VISIBLE);

        /*--------------moreLayout start----------------*/
        if (animationHide != null && animationHide.isRunning()) {
            animationHide.cancel();
        }
        if (animationShow == null) {
            animationShow = getAnimatorShow();
        } else {
            animationShow.setFloatValues(heightValue[0], 0);
        }
        animationShow.start();
        /*--------------moreLayout end----------------*/

        /*----------------arrow start----------------*/
        if (animationHideArrow != null && animationHideArrow.isRunning()) {
            animationHideArrow.cancel();
        }
        if (animationShowArrow == null) {
            animationShowArrow = getAnimatorShowArrow();
        } else {
            animationShowArrow.setFloatValues(rotationValueArrow[0], 180);
        }
        animationShowArrow.start();
        /*----------------arrow end----------------*/
    }

    private void tabAnimationHide() {
        if (!isPagerMoreShow) {
            return;
        }
        isPagerMoreShow = false;
        /*--------------moreLayout start----------------*/
        if (animationShow != null && animationShow.isRunning()) {
            animationShow.cancel();
        }
        if (animationHide == null) {
            animationHide = getAnimatorHide();
        } else {
            animationHide.setFloatValues(heightValue[0]
                    , -pagerBarMoreHeight);
        }
        animationHide.start();
        /*--------------moreLayout end----------------*/

        /*----------------arrow start----------------*/
        if (animationShowArrow != null && animationShowArrow.isRunning()) {
            animationShowArrow.cancel();
        }
        if (animationHideArrow == null) {
            animationHideArrow = getAnimatorHideArrow();
        } else {
            animationHideArrow.setFloatValues(rotationValueArrow[0], 0);
        }
        animationHideArrow.start();
        /*----------------arrow end----------------*/

    }

    private ValueAnimator getAnimatorShow() {
        return animationHelper().createAnimation(pagerBarMore()
                , AnimationHelper.AnimationType.TRANSLATEY
                , 350
                , new OvershootInterpolator(0.5f)
                , null
                , heightValue
                , -pagerBarMoreHeight
                , 0);
    }

    private ValueAnimator getAnimatorHide() {
        return animationHelper().createAnimation(pagerBarMore()
                , AnimationHelper.AnimationType.TRANSLATEY
                , 500
                , new AnticipateOvershootInterpolator(0.6f)
                , null
                , heightValue
                , 0
                , -pagerBarMoreHeight
        );
    }

    private ValueAnimator animationHideArrow;
    private ValueAnimator animationShowArrow;
    private float[] rotationValueArrow = new float[1];

    private ValueAnimator getAnimatorShowArrow() {
        return animationHelper().createAnimation(pagerBarMoreArrow()
                , AnimationHelper.AnimationType.ROTATION
                , 350
                , new OvershootInterpolator()
                , null
                , rotationValueArrow
                , 0
                , 180);
    }

    private ValueAnimator getAnimatorHideArrow() {
        return animationHelper().createAnimation(pagerBarMoreArrow()
                , AnimationHelper.AnimationType.ROTATION
                , 500
                , new AnticipateOvershootInterpolator()
                , null
                , rotationValueArrow
                , 180
                , 0
        );
    }


    protected ChipsLayoutManager getLayoutManager() {
        return ChipsLayoutManager.newBuilder(getContext())
//              //set vertical gravity for all items in a row. Default = Gravity.CENTER_VERTICAL
//              .setChildGravity(Gravity.TOP)
//              //whether RecyclerView can scroll. TRUE by default
                .setScrollingEnabled(true)
//              //set maximum views count in a particular row
//              .setMaxViewsInRow(2)
//              //set gravity resolver where you can determine gravity for item in position.
//              //This method have priority over previous one
//              .setGravityResolver(new IChildGravityResolver() {
//                  @Override
//                  public int getItemGravity(int position) {
//                      return Gravity.CENTER;
//                  }
//              })
//              //you are able to break row due to your conditions. Row breaker should return true for that views
//              .setRowBreaker(new IRowBreaker() {
//                  @Override
//                  public boolean isItemBreakRow(@IntRange(from = 0) int position) {
//                      return position == 6 || position == 11 || position == 2;
//                  }
//              })
//              //a layoutOrientation of layout manager, could be VERTICAL OR HORIZONTAL. HORIZONTAL by default
//              .setOrientation(ChipsLayoutManager.HORIZONTAL)
//              // row strategy for views in completed row, could be STRATEGY_DEFAULT, STRATEGY_FILL_VIEW,
//              //STRATEGY_FILL_SPACE or STRATEGY_CENTER
//              .setRowStrategy(ChipsLayoutManager.STRATEGY_FILL_SPACE)
//              // whether strategy is applied to last row. FALSE by default
//              .withLastRow(true)
                .build();
    }


    public float getScrollYDistance() {
        return getScrollDistance(recyclerView(), false);
    }


    public float getScrollDistance(RecyclerView recyclerView, boolean isHorizontal) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int position = layoutManager.findFirstVisibleItemPosition();
        View firstVisibleChildView = layoutManager.findViewByPosition(position);
        if (firstVisibleChildView != null) {
            if (isHorizontal) {
                float itemWidth = firstVisibleChildView.getWidth();
                return (position) * itemWidth - firstVisibleChildView.getLeft();
            } else {
                float itemHeight = firstVisibleChildView.getHeight();
                return (position) * itemHeight - firstVisibleChildView.getTop();
            }
        }
        return 0;
    }

    protected abstract AnimationHelper animationHelper();

    protected abstract RecyclerView pagerBarMoreRecycler();

    protected abstract View pagerBarMore();

    protected abstract View pagerBarMoreArrow();

    protected abstract View pagerBarMoreLayout();

    protected abstract List<CampusPagerTabAdapter.PagerTabItem> pagerTabItems();

    protected abstract RecyclerView pagerBarRecycler();

    protected abstract CampusPagerTabAdapter campusPagerTabAdapter();

    protected abstract CampusPagerTabAdapter campusPagerTabMoreAdapter();

    protected abstract RxBus rxBus();

    protected abstract View appBar();

    protected abstract RecyclerView recyclerView();

}
