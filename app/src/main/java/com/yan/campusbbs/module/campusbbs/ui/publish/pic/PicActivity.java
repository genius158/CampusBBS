package com.yan.campusbbs.module.campusbbs.ui.publish.pic;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.widget.TextView;

import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseActivity;
import com.yan.campusbbs.module.setting.SettingHelper;
import com.yan.campusbbs.module.setting.SettingModule;
import com.yan.campusbbs.module.setting.SkinControl;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.utils.SPUtils;

import java.io.File;
import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelectorFragment;

/**
 * Created by yan on 2017/3/18.
 */

public class PicActivity extends BaseActivity implements MultiImageSelectorFragment.Callback, SkinControl {
    public static final int MODE_SINGLE = 0;
    public static final int MODE_MULTI = 1;
    public static final String EXTRA_SELECT_COUNT = "max_select_count";
    public static final String EXTRA_SELECT_MODE = "select_count_mode";
    public static final String EXTRA_SHOW_CAMERA = "show_camera";
    public static final String EXTRA_RESULT = "select_result";
    public static final String EXTRA_DEFAULT_SELECTED_LIST = "default_list";
    private static final int DEFAULT_IMAGE_SIZE = 9;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.common_app_bar)
    CardView commonAppBar;
    private ArrayList<String> resultList = new ArrayList();
    private int mDefaultCount = 9;

    @Inject
    SettingHelper changeSkinHelper;
    @Inject
    SPUtils spUtils;

    public PicActivity() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.pic_activity_default);
        ButterKnife.bind(this);
        daggerInject();

        Intent intent = this.getIntent();
        this.mDefaultCount = intent.getIntExtra("max_select_count", 9);
        int mode = intent.getIntExtra("select_count_mode", 1);
        boolean isShow = intent.getBooleanExtra("show_camera", true);
        if (mode == 1 && intent.hasExtra("default_list")) {
            this.resultList = intent.getStringArrayListExtra("default_list");
        }

        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putInt("max_select_count", this.mDefaultCount);
            bundle.putInt("select_count_mode", mode);
            bundle.putBoolean("show_camera", isShow);
            bundle.putStringArrayList("default_list", this.resultList);
            this.getSupportFragmentManager().beginTransaction().add(R.id.image_grid
                    , Fragment.instantiate(this, MultiImageSelectorFragment.class.getName(), bundle)).commit();
        }
    }

    private void daggerInject() {
        DaggerPicComponent.builder().applicationComponent(
                ((ApplicationCampusBBS) getApplication()).getApplicationComponent())
                .settingModule(new SettingModule(this, compositeDisposable))
                .build().inject(this);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                this.setResult(0);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onSingleImageSelected(String path) {
        Intent data = new Intent();
        this.resultList.add(path);
        data.putStringArrayListExtra("select_result", this.resultList);
        this.setResult(-1, data);
        this.finish();
    }

    public void onImageSelected(String path) {
        if (!this.resultList.contains(path)) {
            this.resultList.add(path);
        }
    }

    public void onImageUnselected(String path) {
        if (this.resultList.contains(path)) {
            this.resultList.remove(path);
        }
    }

    public void onCameraShot(File imageFile) {
        if (imageFile != null) {
            this.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(imageFile)));
            Intent data = new Intent();
            this.resultList.add(imageFile.getAbsolutePath());
            data.putStringArrayListExtra("select_result", this.resultList);
            this.setResult(-1, data);
            this.finish();
        }

    }


    @Override
    protected SPUtils sPUtils() {
        return spUtils;
    }

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        super.changeSkin(actionChangeSkin);
        title.setText(String.valueOf("图片选择"));
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
}
