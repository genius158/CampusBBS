package com.yan.campusbbs.module.campusbbs.ui.selfcenter.chat;

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

import com.tencent.TIMElem;
import com.tencent.TIMElemType;
import com.tencent.TIMMessage;
import com.tencent.TIMMessageListener;
import com.tencent.TIMTextElem;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;
import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseActivity;
import com.yan.campusbbs.module.ImManager;
import com.yan.campusbbs.module.campusbbs.adapter.SelfCenterChatAdapter;
import com.yan.campusbbs.module.campusbbs.data.SelfCenterChatData;
import com.yan.campusbbs.module.campusbbs.data.SelfCenterChatOtherData;
import com.yan.campusbbs.module.campusbbs.data.SelfCenterChatSelfData;
import com.yan.campusbbs.module.campusbbs.ui.selfcenter.ui.chat.ChatContract;
import com.yan.campusbbs.module.setting.ImageControl;
import com.yan.campusbbs.module.setting.SettingHelper;
import com.yan.campusbbs.module.setting.SettingModule;
import com.yan.campusbbs.repository.entity.DataMultiItem;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.util.SPUtils;
import com.yan.campusbbs.util.ToastUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    SelfCenterChatAdapter chatAdapter;
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

    private TIMUserProfile selfProfile;
    private String identifier;
    private TIMUserProfile otherProfile;

    private int identifierType;

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
        identifier = getIntent().getStringExtra("identifier");
        identifierType = getIntent().getIntExtra("identifierType", 0);
        if (identifierType == 0) {
            identifier = "86-" + identifier;
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setAdapter(chatAdapter);

        ImManager.getImManager().getSelfProfile(selfInfoCallBack);
        ImManager.getImManager().getTIM().addMessageListener(messageListener);
        ImManager.getImManager().getUsersProfile(otherInfoCallBack, identifier);
    }

    private TIMValueCallBack<TIMUserProfile> selfInfoCallBack = new TIMValueCallBack<TIMUserProfile>() {
        @Override
        public void onError(int i, String s) {
            Log.e(TAG, "onError: " + s);
        }

        @Override
        public void onSuccess(TIMUserProfile timUserProfile) {
            selfProfile = timUserProfile;
        }
    };

    private TIMValueCallBack<List<TIMUserProfile>> otherInfoCallBack = new TIMValueCallBack<List<TIMUserProfile>>() {
        @Override
        public void onError(int i, String s) {
            Log.e(TAG, "onError: " + s);
        }

        @Override
        public void onSuccess(List<TIMUserProfile> timUserProfiles) {
            for (TIMUserProfile userProfile : timUserProfiles) {
                otherProfile = userProfile;
            }
        }
    };
    private TIMMessageListener messageListener = list -> {
        for (TIMMessage msg : list) {
            long timestamp = msg.timestamp();
            for (int i = 0; i < msg.getElementCount(); ++i) {
                TIMElem elem = msg.getElement(i);
                //获取当前元素的类型
                TIMElemType elemType = elem.getType();
                Log.d(TAG, "elem type: " + elemType.name());
                if (elemType == TIMElemType.Text) {
                    TIMTextElem textElem = (TIMTextElem) elem;
                    dataMultiItems.add(new SelfCenterChatOtherData(new SelfCenterChatData(
                            otherProfile.getFaceUrl()
                            , textElem.getText()
                            , timestamp * 1000
                    )));
                    chatAdapter.notifyDataSetChanged();
                }
            }
        }
        return false;
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
            dataMultiItems.add(new SelfCenterChatSelfData(new SelfCenterChatData(
                    selfProfile.getFaceUrl()
                    , editText.getText().toString()
                    , System.currentTimeMillis()
            )));
            chatAdapter.notifyDataSetChanged();
            ImManager.getImManager().sendText(identifier, editText.getText().toString());
        }
    }
}
