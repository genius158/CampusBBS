package com.yan.campusbbs.module.campusbbs.ui.selfcenter.ui.chat;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseActivity;
import com.yan.campusbbs.config.CacheConfig;
import com.yan.campusbbs.module.ImManager;
import com.yan.campusbbs.module.campusbbs.ui.selfcenter.adapter.ChatAdapter;
import com.yan.campusbbs.module.campusbbs.ui.selfcenter.data.ChatData;
import com.yan.campusbbs.module.campusbbs.ui.selfcenter.data.ChatOtherData;
import com.yan.campusbbs.module.campusbbs.ui.selfcenter.data.ChatSelfData;
import com.yan.campusbbs.module.selfcenter.data.LoginInfoData;
import com.yan.campusbbs.module.setting.ImageControl;
import com.yan.campusbbs.module.setting.SettingHelper;
import com.yan.campusbbs.module.setting.SettingModule;
import com.yan.campusbbs.repository.entity.DataMultiItem;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.util.ACache;
import com.yan.campusbbs.util.SPUtils;
import com.yan.campusbbs.util.ToastUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import imsdk.data.IMMyself;


/**
 * Created by yan on 2017/2/15.
 */

public class ChatActivity extends BaseActivity implements ChatContract.View {
    private static final String TAG = "ChatActivity";

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
    @Inject
    ToastUtils toastUtils;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.et_send_text)
    EditText editText;
    @BindView(R.id.common_app_bar)
    CardView commonAppBar;
    @BindView(R.id.title)
    TextView title;

    private LoginInfoData.DataBean.UserInfoBean chatUserInfo;
    private LoginInfoData loginInfoData;
    private String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbs_self_center_chat);
        ButterKnife.bind(this);
        daggerInject();
        imageControl.frescoInit();
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
        userId = getIntent().getStringExtra("userId");
        chatUserInfo = (LoginInfoData.DataBean.UserInfoBean) getIntent()
                .getSerializableExtra("chatUserInfo");
        loginInfoData = (LoginInfoData) ACache.get(getBaseContext()).getAsObject(CacheConfig.USER_INFO);

        ImManager.getImManager().setCurrentChatId(userId);
        ImManager.getImManager().setChatViewListener(messageListener);

        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setAdapter(chatAdapter);
    }

    IMMyself.OnReceivedMessageListener messageListener = new IMMyself.OnReceivedMessageListener() {
        @Override
        public void onReceivedText(String s, String s1, String s2, long l) {
            long time=System.currentTimeMillis();
            dataMultiItems.add(new ChatOtherData(new ChatData(
                    null
                    , s1
                    , System.currentTimeMillis()
            )));
            chatAdapter.notifyDataSetChanged();
        }

        @Override
        public void onReceivedBitmap(String s, String s1, long l) {

        }

        @Override
        public void onReceivedBitmapProgress(double v, String s, String s1, long l) {

        }

        @Override
        public void onReceivedBitmapFinish(Uri uri, String s, String s1, long l) {

        }

        @Override
        public void onReceivedAudio(String s, String s1, long l) {

        }

        @Override
        public void onReceivedAudioProgress(double v, String s, String s1, long l) {

        }

        @Override
        public void onReceivedAudioFinish(Uri uri, String s, String s1, long l) {

        }
    };

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
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            commonAppBar.setBackgroundColor(
                    ContextCompat.getColor(this, actionChangeSkin.getColorPrimaryId())
            );
        }
    }

    @Override
    protected void onDestroy() {
        ImManager.getImManager().setCurrentChatId(null);
        ImManager.getImManager().setChatViewListener(null);
        super.onDestroy();
    }

    @OnClick({R.id.tv_btn_chat, R.id.arrow_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_btn_chat:
                sendMessage();
                break;
            case R.id.arrow_back:
                finish();
                break;
        }
    }

    private void sendMessage() {
        if (!TextUtils.isEmpty(editText.getText())) {
            dataMultiItems.add(new ChatSelfData(new ChatData(
                    null
                    , editText.getText().toString()
                    , System.currentTimeMillis()
            )));
            chatAdapter.notifyDataSetChanged();

            ImManager.getImManager().sendText(editText.getText().toString(), userId);
        }
    }
}
