package com.yan.campusbbs.module.selfcenter.ui.friendpage;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseActivity;
import com.yan.campusbbs.module.ImManager;
import com.yan.campusbbs.module.common.data.TopicCacheData;
import com.yan.campusbbs.module.common.data.VisitorsCacheData;
import com.yan.campusbbs.module.selfcenter.adapter.SelfCenterMultiItemAdapter;
import com.yan.campusbbs.module.selfcenter.data.OtherCenterHeader;
import com.yan.campusbbs.module.selfcenter.data.PublishData;
import com.yan.campusbbs.module.selfcenter.data.UserInfoData;
import com.yan.campusbbs.module.selfcenter.ui.mainpage.DaggerSelfCenterOtherComponent;
import com.yan.campusbbs.module.selfcenter.ui.mainpage.SelfCenterContract;
import com.yan.campusbbs.module.selfcenter.ui.mainpage.SelfCenterModule;
import com.yan.campusbbs.module.selfcenter.ui.mainpage.SelfCenterPresenter;
import com.yan.campusbbs.module.setting.AdapterImageControl;
import com.yan.campusbbs.module.setting.SettingHelper;
import com.yan.campusbbs.module.setting.SettingModule;
import com.yan.campusbbs.repository.entity.DataMultiItem;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.util.ACache;
import com.yan.campusbbs.util.RxBus;
import com.yan.campusbbs.util.SPUtils;
import com.yan.campusbbs.util.ToastUtils;

import java.util.LinkedHashSet;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Administrator on 2017/4/6 0006.
 */

public class FriendPageActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SelfCenterContract.View {

    @BindView(R.id.recycler_view)
    protected RecyclerView recyclerView;
    @BindView(R.id.store_house_ptr_frame)
    protected SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.fl_container)
    protected View container;
    @BindView(R.id.common_app_bar)
    protected CardView commonAppBar;
    @BindView(R.id.title)
    protected TextView title;

    @Inject
    protected List<DataMultiItem> dataMultiItems;
    @Inject
    protected SettingHelper changeSkinHelper;
    @Inject
    protected RxBus rxBus;
    @Inject
    protected AdapterImageControl adapterImageControl;
    @Inject
    protected SPUtils spUtils;
    @Inject
    protected ToastUtils toastUtils;
    @Inject
    protected SelfCenterPresenter mPresenter;
    @Inject
    protected SelfCenterMultiItemAdapter adapter;

    protected String nickName;
    protected PublishData.DataBean.TopicInfoListBean.TopicListBean topicListBean;
    protected String userId;
    protected int pageNo = 1;

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null)
            mPresenter.start();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_page);
        ButterKnife.bind(this);
        daggerInject();
        init();
        initRxAction();
        dataInit();
    }

    protected void initRxAction() {

        addDisposable(rxBus.getEvent(ImManager.Action.AddFriend.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(addFriend -> {
                    mPresenter.getFriendData(1, userId);
                    toastUtils.showShort("添加好友成功");
                }));
    }

    protected void daggerInject() {
        DaggerSelfCenterOtherComponent.builder()
                .applicationComponent(((ApplicationCampusBBS) getApplication()).getApplicationComponent())
                .settingModule(new SettingModule(this, compositeDisposable))
                .selfCenterModule(new SelfCenterModule(this, container, compositeDisposable))
                .build().inject(this);
    }

    protected void init() {
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.clearOnScrollListeners();
        adapterImageControl.attachRecyclerView(recyclerView);
    }

    protected void dataInit() {
        userId = getIntent().getStringExtra("userId");
        nickName = getIntent().getStringExtra("nickName");
        if (TextUtils.isEmpty(nickName)
                && getIntent().getSerializableExtra("otherBean") != null) {
            topicListBean = (PublishData.DataBean.TopicInfoListBean.TopicListBean) getIntent()
                    .getSerializableExtra("otherBean");
        }
        mPresenter.getFriendData(pageNo, userId);
        swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(true));
    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        mPresenter.getFriendData(pageNo, userId);
    }

    @Override
    protected SPUtils sPUtils() {
        return spUtils;
    }

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        super.changeSkin(actionChangeSkin);
        if (!TextUtils.isEmpty(nickName)) {
            title.setText(String.valueOf(nickName + "的个人主页"));
        } else if (topicListBean != null) {
            title.setText(String.valueOf(topicListBean.getUserNickname() + "的个人主页"));
        }
        commonAppBar.setCardBackgroundColor(
                ContextCompat.getColor(this, actionChangeSkin.getColorPrimaryId())
        );
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            commonAppBar.setBackgroundColor(
                    ContextCompat.getColor(this, actionChangeSkin.getColorPrimaryId())
            );
        }

        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(this, R.color.crFEFEFE)
        );
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(ContextCompat
                .getColor(this, actionChangeSkin.getColorAccentId()));
    }

    @Override
    public void dataSuccess(List<DataMultiItem> dataMultiItems) {
        if (pageNo == 1) {
            if (dataMultiItems.size() > 3) {
                adapter.setEnableLoadMore(true);
                adapter.setOnLoadMoreListener(
                        () -> {
                            mPresenter.getFriendData(++pageNo, userId);
                        });
                if (dataMultiItems.size() > 0
                        && dataMultiItems.get(0) instanceof OtherCenterHeader
                        && ((OtherCenterHeader) dataMultiItems.get(0)).data instanceof UserInfoData) {
                    OtherCenterHeader otherCenterHeader = (OtherCenterHeader) dataMultiItems.get(0);
                    UserInfoData.DataBean.UserDetailInfoBean userInfoData = ((UserInfoData) otherCenterHeader.data).getData().getUserDetailInfo();
                    title.setText(TextUtils.isEmpty(userInfoData.getUserNickname())
                            ? userInfoData.getUserAccount()
                            : userInfoData.getUserNickname());
                }
            }

            swipeRefreshLayout.setRefreshing(false);
            this.dataMultiItems.clear();
            this.dataMultiItems.addAll(dataMultiItems);
            adapter.notifyDataSetChanged();
            return;
        }
        adapter.loadMoreComplete();
        if (dataMultiItems == null || dataMultiItems.isEmpty()) {
            adapter.setEnableLoadMore(false);
        }
        int tempSize = this.dataMultiItems.size();
        this.dataMultiItems.addAll(dataMultiItems);
        adapter.notifyItemRangeInserted(tempSize, this.dataMultiItems.size() - tempSize);
    }

    @Override
    public void dataError() {
        swipeRefreshLayout.setRefreshing(false);
        toastUtils.showShort("网络错误");
    }

    @OnClick(R.id.arrow_back)
    public void onViewClicked() {
        finish();
    }
}
