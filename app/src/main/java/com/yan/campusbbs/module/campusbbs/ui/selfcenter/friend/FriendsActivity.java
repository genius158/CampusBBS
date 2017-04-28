package com.yan.campusbbs.module.campusbbs.ui.selfcenter.friend;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;

import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseActivity;
import com.yan.campusbbs.module.ImManager;
import com.yan.campusbbs.module.campusbbs.adapter.SelfCenterFriendAdapter;
import com.yan.campusbbs.module.campusbbs.adapter.SelfCenterFriendDiffCallBack;
import com.yan.campusbbs.module.campusbbs.data.SelfCenterFriendData;
import com.yan.campusbbs.module.setting.ImageControl;
import com.yan.campusbbs.module.setting.SettingHelper;
import com.yan.campusbbs.module.setting.SettingModule;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.utils.RxBus;
import com.yan.campusbbs.utils.SPUtils;
import com.yan.campusbbs.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yan on 2017/2/15.
 */

public class FriendsActivity extends BaseActivity implements FriendContract.View {
    private static final String TAG = "FriendsActivity";
    @Inject
    SettingHelper changeSkinHelper;
    @Inject
    SPUtils spUtils;
    @Inject
    ToastUtils toastUtils;
    @Inject
    FriendPresenter presenter;
    @Inject
    RxBus rxBus;
    @Inject
    ImageControl imageControl;
    @Inject
    SelfCenterFriendAdapter friendAdapter;

    @Inject
    List<SelfCenterFriendData> friendDatas;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.common_app_bar)
    CardView commonAppBar;
    @BindView(R.id.title)
    TextView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbs_self_center_sub);
        ButterKnife.bind(this);
        daggerInject();
        imageControl.frescoInit();
        init();
        initRxAction();
        presenter.getConversation();
    }

    private void initRxAction() {
        addDisposable(rxBus.getEvent(ImManager.Action.ActionGetChatMessage.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(actionGetChatMessage -> {
                    presenter.getConversation();
                }));
    }

    private void daggerInject() {
        DaggerFriendsComponent.builder().applicationComponent(
                ((ApplicationCampusBBS) getApplication()).getApplicationComponent()
        ).friendModule(new FriendModule(this))
                .settingModule(new SettingModule(this, compositeDisposable))
                .build().inject(this);
    }

    private void init() {
        if (TextUtils.isEmpty(ImManager.getImManager().getTIM().getLoginUser())) {
            toastUtils.showShort("请先登录");
            return;
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setAdapter(friendAdapter);
    }

    @Override
    protected SPUtils sPUtils() {
        return spUtils;
    }

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        super.changeSkin(actionChangeSkin);
        title.setText(R.string.self_center_friend);
        commonAppBar.setCardBackgroundColor(
                ContextCompat.getColor(this, actionChangeSkin.getColorPrimaryId())
        );
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            commonAppBar.setBackgroundColor(
                    ContextCompat.getColor(this, actionChangeSkin.getColorPrimaryId())
            );
        }
    }

    @OnClick(R.id.arrow_back)
    public void onClick() {
        finish();
    }


    @Override
    public synchronized void addConversationData(SelfCenterFriendData timMessage) {
        List<SelfCenterFriendData> oldDatas = new ArrayList<>();
        oldDatas.addAll(friendDatas);

        for (int j = 0; j < friendDatas.size(); j++) {
            if (timMessage.timUserProfile.getIdentifier()
                    .equals(friendDatas.get(j).timUserProfile.getIdentifier())
                    && friendDatas.get(j).timestamp != timMessage.timestamp
                    ) {
                friendDatas.remove(j);
                friendDatas.add(0, timMessage);
                Collections.sort(friendDatas);
                break;
            }
        }
        DiffUtil.DiffResult diffResult = DiffUtil
                .calculateDiff(new SelfCenterFriendDiffCallBack(oldDatas, friendDatas), true);
        diffResult.dispatchUpdatesTo(friendAdapter);

        if (friendDatas.size() >= 1) {
            recyclerView.scrollToPosition(0);
        }
    }

    @Override
    public synchronized void addFriends(List<SelfCenterFriendData> timMessages) {
        List<SelfCenterFriendData> oldDatas = new ArrayList<>();
        oldDatas.addAll(friendDatas);
        for (int i = 0; i < timMessages.size(); i++) {
            boolean isNeedAdd = true;
            for (int j = 0; j < friendDatas.size(); j++) {
                if (timMessages.get(i).timUserProfile.getIdentifier()
                        .equals(friendDatas.get(j).timUserProfile.getIdentifier())) {
                    isNeedAdd = false;
                    break;
                }
            }
            if (isNeedAdd) {
                friendDatas.add(timMessages.get(i));
            }
        }
        DiffUtil.DiffResult diffResult = DiffUtil
                .calculateDiff(new SelfCenterFriendDiffCallBack(oldDatas, friendDatas), true);
        diffResult.dispatchUpdatesTo(friendAdapter);
        if (friendDatas.size() >= 1) {
            recyclerView.scrollToPosition(0);
        }
    }

    @Override
    public void error(String s) {

    }
}
