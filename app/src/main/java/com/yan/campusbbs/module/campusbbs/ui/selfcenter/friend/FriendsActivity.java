package com.yan.campusbbs.module.campusbbs.ui.selfcenter.friend;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseActivity;
import com.yan.campusbbs.module.campusbbs.adapter.SelfCenterFriendAdapter;
import com.yan.campusbbs.module.campusbbs.data.SelfCenterFriendData;
import com.yan.campusbbs.module.setting.ImageControl;
import com.yan.campusbbs.module.setting.SettingHelper;
import com.yan.campusbbs.module.setting.SettingModule;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.util.SPUtils;
import com.yan.campusbbs.util.ToastUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    }

    private void daggerInject() {
        DaggerFriendsComponent.builder().applicationComponent(
                ((ApplicationCampusBBS) getApplication()).getApplicationComponent()
        ).friendModule(new FriendModule(this))
                .settingModule(new SettingModule(this, compositeDisposable))
                .build().inject(this);
    }

    private void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setAdapter(friendAdapter);
        presenter.getConversation();
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
        for (SelfCenterFriendData friendData : friendDatas) {
            if (friendData.timUserProfile.getIdentifier().equals(timMessage.timUserProfile.getIdentifier())) {
                friendDatas.remove(friendData);
                friendDatas.add(0, timMessage);
                return;
            }
        }
    }

    @Override
    public void addFriends(SelfCenterFriendData timMessage) {
        friendDatas.add(timMessage);
    }

    @Override
    public synchronized void update() {
        Collections.sort(friendDatas,
                new Comparator<SelfCenterFriendData>() {
                    @Override
                    public int compare(SelfCenterFriendData o1, SelfCenterFriendData o2) {
                        if (o1.timestamp > o2.timestamp) {
                            return -1;
                        } else if (o1.timestamp < o2.timestamp) {
                            return 1;
                        }
                        return 0;
                    }
                });
        friendAdapter.notifyDataSetChanged();
    }

    @Override
    public void error(String s) {

    }
}
