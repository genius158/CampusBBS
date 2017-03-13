package com.yan.campusbbs.module.campusbbs.ui.selfcenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseActivity;
import com.yan.campusbbs.module.campusbbs.ui.selfcenter.ui.friend.FriendsActivity;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.util.SPUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by yan on 2017/2/15.
 */

public class SelfCenterActivity extends BaseActivity {
    @Inject
    SPUtils spUtils;
    @BindView(R.id.common_app_bar)
    CardView commonAppBar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.ll_control_layout)
    LinearLayout llControlLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbs_self_center);
        ButterKnife.bind(this);

        daggerInject();
        init();
    }

    private void daggerInject() {
        DaggerSelfCenterComponent.builder().applicationComponent(
                ((ApplicationCampusBBS) getApplication()).getApplicationComponent()
        ).build().inject(this);
    }

    private void init() {
        for (int i = 0; i < llControlLayout.getChildCount(); i++) {
            View view = llControlLayout.getChildAt(i);
            view.setTag(i);
            view.setOnClickListener(onClickListener);
        }

    }

    private View.OnClickListener onClickListener = v -> {
        int position = (int) v.getTag();
        switch (position) {
            case 0:
                startActivity(new Intent(getBaseContext(), FriendsActivity.class));
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
        }
    };

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
    }


    @OnClick(R.id.arrow_back)
    public void onClick() {
        finish();
    }
}
