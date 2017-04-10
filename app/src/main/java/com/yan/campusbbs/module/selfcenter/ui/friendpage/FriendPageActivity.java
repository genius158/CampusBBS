package com.yan.campusbbs.module.selfcenter.ui.friendpage;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseActivity;
import com.yan.campusbbs.config.CacheConfig;
import com.yan.campusbbs.module.selfcenter.adapter.SelfCenterMultiItemAdapter;
import com.yan.campusbbs.module.selfcenter.data.MainPageData;
import com.yan.campusbbs.module.selfcenter.data.SelfCenterHeader;
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

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/6 0006.
 */

public class FriendPageActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SelfCenterContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.store_house_ptr_frame)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.fl_container)
    View container;
    @BindView(R.id.common_app_bar)
    CardView commonAppBar;
    @BindView(R.id.title)
    TextView title;

    @Inject
    List<DataMultiItem> dataMultiItems;
    @Inject
    SettingHelper changeSkinHelper;
    @Inject
    RxBus rxBus;
    @Inject
    AdapterImageControl adapterImageControl;
    @Inject
    SPUtils spUtils;
    @Inject
    ToastUtils toastUtils;
    @Inject
    SelfCenterPresenter mPresenter;
    @Inject
    SelfCenterMultiItemAdapter adapter;


    private MainPageData.DataBean.TopicInfoListBean.TopicListBean topicListBean;
    private String userId;
    int pageNo = 0;

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

    private void initRxAction() {
    }


    private void daggerInject() {
        DaggerSelfCenterOtherComponent.builder()
                .applicationComponent(((ApplicationCampusBBS) getApplication()).getApplicationComponent())
                .settingModule(new SettingModule(this, compositeDisposable))
                .selfCenterModule(new SelfCenterModule(this, container,compositeDisposable ))
                .build().inject(this);

    }

    private void init() {
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.clearOnScrollListeners();
        adapterImageControl.attachRecyclerView(recyclerView);
        adapter.setEnableLoadMore(true);
        adapter.setOnLoadMoreListener(
                () -> {
                    recyclerView.postDelayed(
                            () -> {
                                adapter.loadMoreComplete();
                            }, 100);
                });
    }

    private void dataInit() {
        userId = getIntent().getStringExtra("userId");
        topicListBean = (MainPageData.DataBean.TopicInfoListBean.TopicListBean) getIntent()
                .getSerializableExtra("otherBean");

        mPresenter.getFriendData(pageNo, userId);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onRefresh() {
        mPresenter.getFriendData(pageNo, userId);
    }


    @Override
    protected SPUtils sPUtils() {
        return spUtils;
    }


    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        super.changeSkin(actionChangeSkin);
        title.setText(topicListBean.getUserNickname() + "的个人主页");
        commonAppBar.setCardBackgroundColor(
                ContextCompat.getColor(this, actionChangeSkin.getColorPrimaryId())
        );
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            commonAppBar.setBackgroundColor(
                    ContextCompat.getColor(this, actionChangeSkin.getColorPrimaryId())
            );
        }
    }

    @Override
    public void dataSuccess(List<DataMultiItem> dataMultiItems) {
        swipeRefreshLayout.setRefreshing(false);
        this.dataMultiItems.clear();
        this.dataMultiItems.addAll(dataMultiItems);
        adapter.notifyDataSetChanged();
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
