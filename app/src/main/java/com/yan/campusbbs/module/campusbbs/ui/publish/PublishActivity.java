package com.yan.campusbbs.module.campusbbs.ui.publish;

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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseActivity;
import com.yan.campusbbs.config.CampusLabels;
import com.yan.campusbbs.module.campusbbs.ui.publish.pic.MultiImageSelector;
import com.yan.campusbbs.module.campusbbs.ui.publish.pic.PicActivity;
import com.yan.campusbbs.module.setting.ImageControl;
import com.yan.campusbbs.module.setting.SettingHelper;
import com.yan.campusbbs.module.setting.SettingModule;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.util.SPUtils;
import com.yan.campusbbs.util.ToastUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
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
    ToastUtils toastUtils;
    @Inject
    ImageControl imageControl;
    @Inject
    PublishPresenter presenter;

    @BindView(R.id.common_app_bar)
    CardView commonAppBar;
    @BindView(R.id.title)
    TextView title;

    public static final String SUB_TITLE = "subTitle";
    @BindView(R.id.sdv_img)
    SimpleDraweeView sdvImg;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.sp_types)
    Spinner spTypes;
    @BindView(R.id.et_content)
    EditText etContent;

    private String subTitle;
    List<String> spDatas = new ArrayList<>();

    private int mode = 1;

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
                .publishModule(new PublishModule(this))
                .build().inject(this);
    }

    private void init() {
        subTitle = getIntent().getStringExtra(SUB_TITLE);
        switch (subTitle) {
            case "学习":
                mode = 1;
                break;
            case "生活":
                mode = 2;
                break;
            case "工作":
                mode = 3;
                break;
            default:
                break;
        }
        for (int i = 0; i < CampusLabels.LEAN_LABELS.length; i++) {
            spDatas.add(CampusLabels.LEAN_LABELS[i]);
        }
        spTypes.setAdapter(new ArrayAdapter<>(getBaseContext()
                , android.R.layout.simple_list_item_1
                , android.R.id.text1
                , spDatas));
    }

    @Override
    protected SPUtils sPUtils() {
        return spUtils;
    }

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        super.changeSkin(actionChangeSkin);
        title.setText(String.valueOf("发布" + subTitle));

        ViewCompat.setBackgroundTintList(etTitle, ColorStateList.valueOf(
                ContextCompat.getColor(getBaseContext(), actionChangeSkin.getColorPrimaryId())
        ));
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                List<String> path = data.getStringArrayListExtra(PicActivity.EXTRA_RESULT);
                Log.e("path", path + "");
                if (path != null && path.get(0) != null) {
                    Log.e("path2", path + "");
                    sdvImg.setImageURI(Uri.parse("file://" + path.get(0)));
                }
            }
        }
    }

    @OnClick({R.id.tv_img_pic, R.id.sdv_img, R.id.arrow_back, R.id.iv_publish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_img_pic:
            case R.id.sdv_img:
                selectImg();
                break;
            case R.id.arrow_back:
                finish();
                break;
            case R.id.iv_publish:
                publish();
                break;
        }
    }

    private void selectImg() {
        MultiImageSelector.create()
                .showCamera(false)
                .single()
                .start(this, REQUEST_IMAGE);
    }

    private void publish() {
        if (TextUtils.isEmpty(etTitle.getText())
                || TextUtils.isEmpty(etContent.getText())) {
            toastUtils.showShort("标题和正文不能为空");
            return;
        }

        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();
        presenter.publish(title
                , content
                , String.valueOf(mode)
                , spDatas.get(spTypes.getSelectedItemPosition())
                , String.valueOf(1)
        );
    }


    @Override
    public void stateSuccess() {
        toastUtils.showShort("发布成功");
        finish();
    }

    @Override
    public void stateNetError() {
        toastUtils.showShort("网络错误");
    }

    @Override
    public void stateError() {
        toastUtils.showShort("发布失败");
    }
}
