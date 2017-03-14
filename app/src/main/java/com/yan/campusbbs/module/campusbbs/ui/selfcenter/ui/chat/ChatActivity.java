package com.yan.campusbbs.module.campusbbs.ui.selfcenter.ui.chat;

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
import com.yan.campusbbs.module.campusbbs.ui.selfcenter.adapter.ChatAdapter;
import com.yan.campusbbs.module.campusbbs.ui.selfcenter.data.ChatOtherData;
import com.yan.campusbbs.module.campusbbs.ui.selfcenter.data.ChatSelfData;
import com.yan.campusbbs.module.search.adapter.SearchAdapter;
import com.yan.campusbbs.module.setting.ImageControl;
import com.yan.campusbbs.module.setting.SettingHelper;
import com.yan.campusbbs.module.setting.SettingModule;
import com.yan.campusbbs.repository.entity.DataMultiItem;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.util.SPUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by yan on 2017/2/15.
 */

public class ChatActivity extends BaseActivity implements ChatContract.View {

    @Inject
    SettingHelper changeSkinHelper;
    @Inject
    SPUtils spUtils;
    @Inject
    ImageControl imageControl;
    @Inject
    ChatAdapter chatAdapter;

    @Inject
    List<DataMultiItem> dataMultiItems;

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
        init();
    }

    private void daggerInject() {
        DaggerChatComponent.builder().applicationComponent(
                ((ApplicationCampusBBS) getApplication()).getApplicationComponent()
        ).chatModule(new ChatModule(this))
                .settingModule(new SettingModule(this, compositeDisposable))
                .build().inject(this);
    }

    private void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setAdapter(chatAdapter);
        dataMultiItems.add(new ChatSelfData(""));
        dataMultiItems.add(new ChatSelfData(""));
        dataMultiItems.add(new ChatSelfData(""));
        dataMultiItems.add(new ChatOtherData(""));
        dataMultiItems.add(new ChatOtherData(""));
        dataMultiItems.add(new ChatOtherData(""));
        dataMultiItems.add(new ChatOtherData(""));
        chatAdapter.notifyDataSetChanged();
    }

    @Override
    protected SPUtils sPUtils() {
        return spUtils;
    }

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        super.changeSkin(actionChangeSkin);
        title.setText("聊天中...");
        commonAppBar.setCardBackgroundColor(
                ContextCompat.getColor(this, actionChangeSkin.getColorPrimaryId())
        );
    }


    @OnClick(R.id.arrow_back)
    public void onClick() {
        finish();
    }
}
