package com.yan.campusbbs.module.campusbbs.ui.selfcenter.chat;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tencent.TIMElem;
import com.tencent.TIMElemType;
import com.tencent.TIMMessage;
import com.tencent.TIMTextElem;
import com.tencent.TIMUserProfile;
import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseActivity;
import com.yan.campusbbs.config.CacheConfig;
import com.yan.campusbbs.module.ImManager;
import com.yan.campusbbs.module.campusbbs.action.ActonCloseChatActivity;
import com.yan.campusbbs.module.campusbbs.adapter.SelfCenterChatAdapter;
import com.yan.campusbbs.module.campusbbs.data.SelfCenterChatData;
import com.yan.campusbbs.module.campusbbs.data.SelfCenterChatOtherData;
import com.yan.campusbbs.module.campusbbs.data.SelfCenterChatSelfData;
import com.yan.campusbbs.module.common.data.UserProfile;
import com.yan.campusbbs.module.setting.ImageControl;
import com.yan.campusbbs.module.setting.SettingHelper;
import com.yan.campusbbs.module.setting.SettingModule;
import com.yan.campusbbs.repository.entity.DataMultiItem;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.util.ACache;
import com.yan.campusbbs.util.RegExpUtils;
import com.yan.campusbbs.util.RxBus;
import com.yan.campusbbs.util.SPUtils;
import com.yan.campusbbs.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    RxBus rxBus;
    @Inject
    List<DataMultiItem> dataMultiItems;
    @Inject
    ToastUtils toastUtils;
    @Inject
    ChatPresenter presenter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.et_send_text)
    EditText editText;
    @BindView(R.id.common_app_bar)
    CardView commonAppBar;
    @BindView(R.id.title)
    TextView title;

    protected String identifier;
    private boolean canLoadMore = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbs_self_center_chat);
        ButterKnife.bind(this);
        initIntentData();
        daggerInject();
        imageControl.frescoInit();
        init();
        initRxAction();
    }


    protected void initIntentData() {
        identifier = getIntent().getStringExtra("identifier");

        if (!identifier.startsWith("86-")) {
            if (RegExpUtils.isChinaPhoneLegal(identifier)) {
                identifier = "86-" + identifier;
            }
        }
    }

    private void initRxAction() {
        addDisposable(rxBus.getEvent(ImManager.Action.ActionGetChatMessage.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(actionGetChatMessage -> {
                    if (actionGetChatMessage.identifer.equals(identifier)) {
                        presenter.getLatestData();
                    }
                }));
        addDisposable(rxBus.getEvent(ActonCloseChatActivity.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(actionGetChatMessage ->
                        finish()
                ));
    }

    private void daggerInject() {
        DaggerChatComponent.builder().applicationComponent(
                ((ApplicationCampusBBS) getApplication()).getApplicationComponent()
        ).chatModule(new ChatModule(this, identifier))
                .settingModule(new SettingModule(this, compositeDisposable))
                .build().inject(this);
    }

    protected void init() {

        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setAdapter(chatAdapter);

        chatAdapter.setEnableLoadMore(true);
        chatAdapter.setOnLoadMoreListener(requestLoadMoreListener);

        presenter.initData();
    }

    private BaseQuickAdapter.RequestLoadMoreListener requestLoadMoreListener = () -> {
        if (canLoadMore) {
            presenter.getMoreChatData();
        }
    };

    private void addMessage(List<TIMMessage> data) {
        for (TIMMessage msg : data) {
            TIMUserProfile senderProfile = msg.getSenderProfile();
            long timestamp = msg.timestamp();
            for (int i = 0; i < msg.getElementCount(); ++i) {
                TIMElem elem = msg.getElement(i);
                TIMElemType elemType = elem.getType();
                if (elemType == TIMElemType.Text) {
                    TIMTextElem textElem = (TIMTextElem) elem;

                    if (msg.isSelf()) {
                        dataMultiItems.add(new SelfCenterChatSelfData(
                                new SelfCenterChatData(textElem.getText()
                                        , timestamp * 1000
                                ).setUserProfile(new UserProfile(presenter.getSelfProfile()))));
                    } else {
                        dataMultiItems.add(new SelfCenterChatOtherData(
                                new SelfCenterChatData(textElem.getText()
                                        , timestamp * 1000
                                ).setUserProfile(new UserProfile(senderProfile))));
                    }
                }
            }
        }
    }

    @Override
    public void setData(List<TIMMessage> data) {
        if (data == null
                || data.isEmpty()) {
            canLoadMore = false;
            chatAdapter.setEnableLoadMore(false);
            return;
        }
        if (dataMultiItems.size() > 5) {
            chatAdapter.setEnableLoadMore(true);
        }
        canLoadMore = true;
        dataMultiItems.clear();
        addMessage(data);
        presenter.setReadMessage();
        chatAdapter.notifyDataSetChanged();
    }

    @Override
    public void setLoadMoreData(List<TIMMessage> data) {
        if (data == null
                || data.isEmpty()) {
            canLoadMore = false;
            chatAdapter.loadMoreComplete();
            chatAdapter.setEnableLoadMore(false);
            return;
        }
        int tempSize = dataMultiItems.size();
        addMessage(data);
        chatAdapter.loadMoreComplete();

        chatAdapter.notifyItemRangeInserted(tempSize,dataMultiItems.size()-tempSize);
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
            ImManager.getImManager().sendText(identifier, editText.getText().toString());
        }
    }

    @Override
    public void getDataError() {
        toastUtils.showShort("获取数据失败");
    }

    @Override
    public void addLatestData(TIMMessage timMessage) {
        TIMUserProfile senderProfile = timMessage.getSenderProfile();
        long timestamp = timMessage.timestamp();
        for (int i = 0; i < timMessage.getElementCount(); ++i) {
            TIMElem elem = timMessage.getElement(i);
            TIMElemType elemType = elem.getType();
            TIMTextElem textElem = (TIMTextElem) elem;
            if (elemType == TIMElemType.Text) {
                if (timMessage.isSelf()) {
                    dataMultiItems.add(0, new SelfCenterChatSelfData(
                            new SelfCenterChatData(textElem.getText()
                                    , timestamp * 1000
                            ).setUserProfile(new UserProfile(presenter.getSelfProfile()))));
                } else {
                    dataMultiItems.add(0, new SelfCenterChatOtherData(
                            new SelfCenterChatData(textElem.getText()
                                    , timestamp * 1000
                            ).setUserProfile(new UserProfile(senderProfile))));
                }
                chatAdapter.notifyItemInserted(0);
                Observable.timer(100, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(aLong -> {
                            recyclerView.smoothScrollToPosition(0);
                        });
            }
        }
    }

    @Override
    public void setTitle(String title) {
        this.title.setText("与 " + title + " 聊天中...");

    }
}
