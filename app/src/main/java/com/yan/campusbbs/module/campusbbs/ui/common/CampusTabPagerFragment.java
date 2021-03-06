package com.yan.campusbbs.module.campusbbs.ui.common;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.OvershootInterpolator;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.nineoldandroids.animation.ValueAnimator;
import com.yan.campusbbs.base.BaseRefreshFragment;
import com.yan.campusbbs.module.campusbbs.adapter.CampusDataAdapter;
import com.yan.campusbbs.module.campusbbs.adapter.CampusPagerTabAdapter;
import com.yan.campusbbs.module.campusbbs.data.BannerImgs;
import com.yan.campusbbs.module.campusbbs.data.PostAll;
import com.yan.campusbbs.module.campusbbs.data.PostTag;
import com.yan.campusbbs.module.campusbbs.data.TopicData;
import com.yan.campusbbs.repository.entity.DataMultiItem;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.rxbusaction.ActionFloatingButton;
import com.yan.campusbbs.rxbusaction.ActionPagerTabClose;
import com.yan.campusbbs.utils.AnimationUtils;
import com.yan.campusbbs.utils.RxBus;
import com.yan.campusbbs.utils.SizeUtils;
import com.yan.campusbbs.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by yan on 2017/2/8.
 */

public abstract class CampusTabPagerFragment extends BaseRefreshFragment {
    private static final String BUNDLE_TAB_SELECT_POSITION = "tabSelectPosition";
    private static final String BUNDLE_RECYCLER_Y = "recyclerY";
    private static final String BUNDLE_APP_BAR_Y = "appBarY";

    protected int tabSelectPosition = 0;
    private CampusAppHelperAdd campusAppHelperAdd;

    private ValueAnimator animationHide;
    private ValueAnimator animationShow;
    private float[] heightValue = new float[1];
    private ValueAnimator animationHideArrow;
    private ValueAnimator animationShowArrow;
    private float[] rotationValueArrow = new float[1];

    private boolean isPagerMoreShow;
    private int pagerBarMoreHeight;

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
        recyclerView().addOnScrollListener(onScrollListener);
        campusPagerTabAdapter().setOnItemClickListener(onItemClickListener);
        campusPagerTabMoreAdapter().setOnItemClickListener(onItemClickListener);
        campusAppHelperAdd.appHelperAdd(appBar());
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


    private OnItemClickListener onItemClickListener = new OnItemClickListener() {

        @Override
        public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
            tabSelectPosition = position;
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

            CampusTabPagerFragment.this.onItemClick(position);
        }
    };

    protected void onItemClick(int position) {
    }

    private void initRxAction() {
        addDisposable(rxBus().getEvent(ActionPagerTabClose.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(actionPagerTabClose -> {
                    tabAnimationHide();
                }));
    }

    public void setCampusAppHelperAdd(CampusAppHelperAdd campusAppHelperAdd) {
        this.campusAppHelperAdd = campusAppHelperAdd;
    }

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
                , AnimationUtils.AnimationType.TRANSLATEY
                , 350
                , new OvershootInterpolator(0.5f)
                , null
                , heightValue
                , -pagerBarMoreHeight
                , 0);
    }

    private ValueAnimator getAnimatorHide() {
        return animationHelper().createAnimation(pagerBarMore()
                , AnimationUtils.AnimationType.TRANSLATEY
                , 500
                , new AnticipateOvershootInterpolator(0.6f)
                , null
                , heightValue
                , 0
                , -pagerBarMoreHeight
        );
    }


    private ValueAnimator getAnimatorShowArrow() {
        return animationHelper().createAnimation(pagerBarMoreArrow()
                , AnimationUtils.AnimationType.ROTATION
                , 350
                , new OvershootInterpolator()
                , null
                , rotationValueArrow
                , 0
                , 180);
    }

    private ValueAnimator getAnimatorHideArrow() {
        return animationHelper().createAnimation(pagerBarMoreArrow()
                , AnimationUtils.AnimationType.ROTATION
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
                .setScrollingEnabled(true)
                .build();
    }


    public float getScrollYDistance() {
        return getScrollDistance(recyclerView(), false);
    }

    private ActionFloatingButton actionFloating;
    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (actionFloating == null) {
                actionFloating = new ActionFloatingButton();
            }
            if (dy < 0 && !actionFloating.isScrollDown) {
                actionFloating.isScrollDown = true;
                rxBus().post(actionFloating);
            } else if (dy > 0 && actionFloating.isScrollDown) {
                actionFloating.isScrollDown = false;
                rxBus().post(actionFloating);
            }
        }
    };

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

    public void setTopicData(List<DataMultiItem> topicData) {
        if (topicData.isEmpty()) {
            swipeRefreshLayout().setRefreshing(false);
            campusDataAdapter().loadMoreComplete();
            campusDataAdapter().setEnableLoadMore(false);
            return;
        }
        if (pageNo() == 1) {
            dataMultiItems().clear();
            swipeRefreshLayout().setRefreshing(false);
            dataMultiItems().addAll(topicData);
            campusDataAdapter().notifyDataSetChanged();
            if (dataMultiItems().size() >= 4) {
                campusDataAdapter().setEnableLoadMore(true);
            } else {
                campusDataAdapter().setEnableLoadMore(false);
            }
        } else {
            int tempSize = dataMultiItems().size();
            dataMultiItems().addAll(topicData);
            campusDataAdapter().loadMoreComplete();
            campusDataAdapter().notifyItemRangeInserted(tempSize, dataMultiItems().size() - tempSize);

            if (topicData.isEmpty()) {
                campusDataAdapter().setEnableLoadMore(false);
            }
        }
    }

    public void setTopicTagData(TopicData topicData) {
        if (topicData.getData() == null
                || topicData.getData().getTopicInfoList() == null
                || topicData.getData().getTopicInfoList().getTopicList() == null
                ) {
            swipeRefreshLayout().setRefreshing(false);
            campusDataAdapter().setEnableLoadMore(false);
            toastUtils().showUIShort(topicData.getMessage());
            return;
        }
        if (pageNo() == 1) {
            dataMultiItems().clear();
            swipeRefreshLayout().setRefreshing(false);
            for (int i = 0; i < topicData.getData().getTopicInfoList().getTopicList().size(); i++) {
                dataMultiItems().add(new PostTag(topicData.getData().getTopicInfoList().getTopicList().get(i)));
            }
            campusDataAdapter().notifyDataSetChanged();
            if (dataMultiItems().size() >= 4) {
                campusDataAdapter().setEnableLoadMore(true);
            } else {
                campusDataAdapter().setEnableLoadMore(false);
            }
        } else {
            int tempSize = dataMultiItems().size();
            for (int i = 0; i < topicData.getData().getTopicInfoList().getTopicList().size(); i++) {
                dataMultiItems().add(new PostTag(topicData.getData().getTopicInfoList().getTopicList().get(i)));
            }
            campusDataAdapter().loadMoreComplete();
            campusDataAdapter().notifyItemRangeInserted(tempSize, dataMultiItems().size() - tempSize);

            if (topicData.getData().getTopicInfoList().getTopicList().isEmpty()) {
                campusDataAdapter().setEnableLoadMore(false);
            }
        }
    }


    protected abstract AnimationUtils animationHelper();

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

    protected abstract CampusDataAdapter campusDataAdapter();

    protected abstract List<DataMultiItem> dataMultiItems();

    protected abstract int pageNo();

    protected abstract ToastUtils toastUtils();

}
