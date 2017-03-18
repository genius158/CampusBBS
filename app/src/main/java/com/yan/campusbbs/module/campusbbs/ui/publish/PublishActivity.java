package com.yan.campusbbs.module.campusbbs.ui.publish;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseActivity;
import com.yan.campusbbs.module.campusbbs.ui.publish.pic.MultiImageSelector;
import com.yan.campusbbs.module.campusbbs.ui.publish.pic.PicActivity;
import com.yan.campusbbs.module.setting.ImageControl;
import com.yan.campusbbs.module.setting.SettingHelper;
import com.yan.campusbbs.module.setting.SettingModule;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.util.FrescoUtils;
import com.yan.campusbbs.util.SPUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by yan on 2017/2/15.
 */

public class PublishActivity extends BaseActivity implements PublishContract.View {

    private static final int REQUEST_IMAGE = 200;
    @Inject
    SettingHelper changeSkinHelper;
    @Inject
    SPUtils spUtils;
    @Inject
    ImageControl imageControl;

    @BindView(R.id.common_app_bar)
    CardView commonAppBar;
    @BindView(R.id.title)
    TextView title;

    public static final String SUB_TITLE = "subTitle";
    @BindView(R.id.sdv_img)
    SimpleDraweeView sdvImg;
    private String subTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbs_self_center_publish);
        ButterKnife.bind(this);
        daggerInject();
        imageControl.frescoInit();
        init();
    }

    private void daggerInject() {
        DaggerPublishComponent.builder().applicationComponent(
                ((ApplicationCampusBBS) getApplication()).getApplicationComponent())
                .settingModule(new SettingModule(this, compositeDisposable))
                .build().inject(this);
    }

    private void init() {
        subTitle = getIntent().getStringExtra(SUB_TITLE);
    }

    @Override
    protected SPUtils sPUtils() {
        return spUtils;
    }

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        super.changeSkin(actionChangeSkin);
        title.setText(String.valueOf("发布" + subTitle));
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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                List<String> path = data.getStringArrayListExtra(PicActivity.EXTRA_RESULT);
                Log.e("path", path + "");
                if (path != null && path.get(0) != null) {
                    Log.e("path2", path + "");
                    sdvImg.setImageURI(Uri.parse("file://"+path.get(0)));
                }
            }
        }
    }

    @OnClick({R.id.tv_img_pic, R.id.sdv_img, R.id.arrow_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_img_pic:
            case R.id.sdv_img:
                MultiImageSelector.create()
                        .showCamera(false)
                        .single()
                        .start(this, REQUEST_IMAGE);
                break;
            case R.id.arrow_back:
                finish();
                break;
        }
    }
}
