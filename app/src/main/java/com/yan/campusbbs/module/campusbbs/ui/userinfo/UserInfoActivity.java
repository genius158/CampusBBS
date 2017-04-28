package com.yan.campusbbs.module.campusbbs.ui.userinfo;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseActivity;
import com.yan.campusbbs.config.CacheConfig;
import com.yan.campusbbs.module.ImManager;
import com.yan.campusbbs.module.campusbbs.ui.publish.pic.MultiImageSelector;
import com.yan.campusbbs.module.campusbbs.ui.publish.pic.PicActivity;
import com.yan.campusbbs.module.selfcenter.data.LoginInfoData;
import com.yan.campusbbs.module.selfcenter.data.UserInfoData;
import com.yan.campusbbs.module.setting.SettingHelper;
import com.yan.campusbbs.module.setting.SettingModule;
import com.yan.campusbbs.repository.DataAddress;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.util.ACache;
import com.yan.campusbbs.util.EmptyUtil;
import com.yan.campusbbs.util.SPUtils;
import com.yan.campusbbs.util.ToastUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yan on 2017/4/1.
 */

public class UserInfoActivity extends BaseActivity implements UserInfoContract.View {
    private static final int REQUEST_IMAGE = 200;

    @Inject
    UserInfoPresenter presenter;
    @Inject
    SettingHelper settingHelper;
    @Inject
    SPUtils spUtils;
    @Inject
    ToastUtils toastUtils;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.common_app_bar)
    CardView commonAppBar;
    @BindView(R.id.et_nike_name)
    EditText etNikeName;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_campus)
    EditText etCampus;
    @BindView(R.id.et_birthday)
    EditText etBirthday;
    @BindView(R.id.et_sex)
    EditText etSex;
    @BindView(R.id.et_like)
    EditText etLike;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_btn_register)
    TextView btnSubmit;
    @BindView(R.id.img_notice)
    TextView imgNotice;
    @BindView(R.id.sdv_img)
    SimpleDraweeView sdvImg;

    private String imgPath;
    String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        daggerInit();
        init();
    }

    private void init() {
        userId = getIntent().getStringExtra("userId");
        if (TextUtils.isEmpty(userId)) {
            presenter.getSelfInfo();
            sdvImg.setVisibility(View.VISIBLE);
            sdvImg.setOnClickListener(v -> selectImg());
        } else {
            presenter.getSelfInfo(userId);
            btnSubmit.setVisibility(View.GONE);
            sdvImg.setVisibility(View.GONE);
            imgNotice.setVisibility(View.GONE);
            etPhone.setEnabled(false);
            etNikeName.setEnabled(false);
            etEmail.setEnabled(false);
            etLike.setEnabled(false);
            etSex.setEnabled(false);
            etBirthday.setEnabled(false);
            etCampus.setEnabled(false);
        }
    }

    private void daggerInit() {

        DaggerUserInfoComponent.builder().applicationComponent(
                ((ApplicationCampusBBS) getApplication()).getApplicationComponent())
                .settingModule(new SettingModule(this, compositeDisposable))
                .userInfoModule(new UserInfoModule(this))
                .build().inject(this);
    }

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        super.changeSkin(actionChangeSkin);
        title.setText(String.valueOf("个人信息"));
        ColorStateList colorStateList = ColorStateList.valueOf(
                ContextCompat.getColor(getBaseContext(), actionChangeSkin.getColorPrimaryId())
        );
        ViewCompat.setBackgroundTintList(etBirthday, colorStateList);
        ViewCompat.setBackgroundTintList(etEmail, colorStateList);
        ViewCompat.setBackgroundTintList(etLike, colorStateList);
        ViewCompat.setBackgroundTintList(etNikeName, colorStateList);
        ViewCompat.setBackgroundTintList(etPhone, colorStateList);
        ViewCompat.setBackgroundTintList(etCampus, colorStateList);
        ViewCompat.setBackgroundTintList(etSex, colorStateList);
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
    protected SPUtils sPUtils() {
        return spUtils;
    }

    @Override
    public void stateSuccess() {
        ImManager.getImManager().setNikeName(etNikeName.getText().toString());
        LoginInfoData loginInfoData = (LoginInfoData) ACache.get(getBaseContext()).getAsObject(CacheConfig.USER_INFO);
        loginInfoData.getData().getUserInfo().setUserNickname(etNikeName.getText().toString());
        ACache.get(getBaseContext()).put(CacheConfig.USER_INFO, loginInfoData);
        toastUtils.showUIShort("更改成功");
    }

    @Override
    public void stateNetError() {
        toastUtils.showUIShort("网络错误");
    }

    @Override
    public void stateError(String msg) {
        toastUtils.showUIShort(msg);
    }

    @Override
    public void setSelfInfo(UserInfoData selfInfo) {
        if (selfInfo.getData() != null
                && selfInfo.getData().getUserDetailInfo() != null) {
            etPhone.setText(selfInfo.getData().getUserDetailInfo().getUserId());
            etNikeName.setText(selfInfo.getData().getUserDetailInfo().getUserNickname());
            etEmail.setText(EmptyUtil.textEmpty(selfInfo.getData().getUserDetailInfo().getUserEmail()));
            etLike.setText(EmptyUtil.textEmpty(selfInfo.getData().getUserDetailInfo().getUserMajor()));
            etBirthday.setText(EmptyUtil.textEmpty(selfInfo.getData().getUserDetailInfo().getUserBirth()));
            etSex.setText(EmptyUtil.textEmpty(selfInfo.getData().getUserDetailInfo().getUserMood()));
            etCampus.setText(EmptyUtil.textEmpty(selfInfo.getData().getUserDetailInfo().getUserSchool()));

            if (TextUtils.isEmpty(userId) && !TextUtils.isEmpty(selfInfo.getData().getUserDetailInfo().getUserHeadImg())) {
                sdvImg.setImageURI(DataAddress.URL_GET_FILE + selfInfo.getData().getUserDetailInfo().getUserHeadImg());
            }
        }
    }

    @OnClick({R.id.arrow_back, R.id.tv_btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.arrow_back:
                finish();
                break;
            case R.id.tv_btn_register:

                modify();

                break;
        }
    }

    private void modify() {
        presenter.modify(etNikeName.getText().toString()
                , ""
                , ""
                , etSex.getText().toString()
                , etEmail.getText().toString()
                , ""
                , ""
                , etBirthday.getText().toString()
                , etLike.getText().toString()
                , etCampus.getText().toString()
                , "");


    }

    private void selectImg() {
        MultiImageSelector.create()
                .showCamera(false)
                .single()
                .start(this, REQUEST_IMAGE);
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
                    imgPath = path.get(0);
                    sdvImg.setImageURI(Uri.parse("file://" + path.get(0)));
                }
            }
        }
    }
}
