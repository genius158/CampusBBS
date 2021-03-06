package com.yan.campusbbs.module.campusbbs.ui.selfcenter.message;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;

import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseActivity;
import com.yan.campusbbs.config.CacheConfig;
import com.yan.campusbbs.module.ImManager;
import com.yan.campusbbs.module.campusbbs.adapter.SelfCenterMessageAdapter;
import com.yan.campusbbs.module.campusbbs.data.SelfCenterMessageCacheData;
import com.yan.campusbbs.module.campusbbs.data.SelfCenterMessageData;
import com.yan.campusbbs.module.setting.ImageControl;
import com.yan.campusbbs.module.setting.SettingHelper;
import com.yan.campusbbs.module.setting.SettingModule;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.utils.ACache;
import com.yan.campusbbs.utils.RxBus;
import com.yan.campusbbs.utils.SPUtils;
import com.yan.campusbbs.utils.ToastUtils;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by yan on 2017/2/15.
 */

public class MessageActivity extends BaseActivity implements MessageContract.View {

    @Inject
    SettingHelper changeSkinHelper;
    @Inject
    ToastUtils toastUtils;
    @Inject
    SPUtils spUtils;
    @Inject
    RxBus rxBus;
    @Inject
    ImageControl imageControl;
    @Inject
    SelfCenterMessageAdapter messageAdapter;
    @Inject
    List<SelfCenterMessageData> messageDatas;

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
        initRxAction();
        init();
    }

    private void initRxAction() {
        addDisposable(rxBus.getEvent(ImManager.Action.ActionGetMessage.class)
                .subscribeOn(Schedulers.io())
                .subscribe(actionGetMessage -> {
                    getMessage();
                }, Throwable::printStackTrace));
    }

    private void daggerInject() {
        DaggerMessageComponent.builder().applicationComponent(
                ((ApplicationCampusBBS) getApplication()).getApplicationComponent())
                .messageModule(new MessageModule(this))
                .settingModule(new SettingModule(this, compositeDisposable))
                .build().inject(this);
    }

    private void init() {
        if (TextUtils.isEmpty(ImManager.getImManager().getTIM().getLoginUser())) {
            toastUtils.showShort("请先登录");
            return;
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setAdapter(messageAdapter);
        getMessage();
    }

    private void getMessage() {
        if (ACache.get(getBaseContext()).getAsObject(CacheConfig.MESSAGE_INFO+ImManager.getImManager().getIdentifier()) != null) {
            SelfCenterMessageCacheData messageCacheData =
                    (SelfCenterMessageCacheData) ACache.get(getBaseContext()).getAsObject(CacheConfig.MESSAGE_INFO+ImManager.getImManager().getIdentifier());
            messageDatas.clear();
            messageDatas.addAll(messageCacheData.getCenterMessageDatas());
            Collections.reverse(messageDatas);
            messageAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected SPUtils sPUtils() {
        return spUtils;
    }

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        super.changeSkin(actionChangeSkin);
        title.setText("消息");
        commonAppBar.setCardBackgroundColor(
                ContextCompat.getColor(this, actionChangeSkin.getColorPrimaryId())
        );
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            commonAppBar.setBackgroundColor(
                    ContextCompat.getColor(this, actionChangeSkin.getColorPrimaryId())
            );
        }
        messageAdapter.changeSkin(actionChangeSkin);
    }

    @OnClick(R.id.arrow_back)
    public void onClick() {
        finish();
    }
}
