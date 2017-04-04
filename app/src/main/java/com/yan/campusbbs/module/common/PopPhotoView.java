package com.yan.campusbbs.module.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.ImageView;

import com.yan.campusbbs.R;
import com.yan.campusbbs.util.SizeUtils;
import com.yan.campusbbs.util.ToastUtils;
import com.yan.campusbbs.widget.CommonPopupWindow;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.inject.Inject;

import me.relex.photodraweeview.PhotoDraweeView;

/**
 * Created by Administrator on 2017/4/4 0004.
 */

public class PopPhotoView extends CommonPopupWindow implements View.OnClickListener {

    private PhotoDraweeView photoDraweeView;
    private String url;
    private ToastUtils toastUtils;

    @Inject
    public PopPhotoView(View parent, ToastUtils toastUtils) {
        super(parent, R.layout.pop_photo_view);
        this.toastUtils = toastUtils;
    }

    public void setImageUrl(String url) {
        this.url = url;
        photoDraweeView.setPhotoUri(Uri.parse(url));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_container:
            case R.id.iv_pop_close:
                dismiss();
                break;
            case R.id.iv_pop_save:
                try {
                    if (context.getExternalFilesDir("image") == null) return;
                    String imgPath = photoDraweeView.getContext().getExternalFilesDir("image")
                            .getAbsolutePath() + getNameByUrl(url);
                    getViewBitmap(photoDraweeView).compress(Bitmap.CompressFormat.JPEG, 100
                            , new FileOutputStream(new File(imgPath)));
                    toastUtils.showUIShort("图片已保存至：" + imgPath);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    private String getNameByUrl(String url) {
        return String.valueOf("/"+System.currentTimeMillis()+".jpg");
    }

    private Bitmap getViewBitmap(View view) {
        if (view == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    @Override
    public void viewInit() {
        photoDraweeView = (PhotoDraweeView) findViewById(R.id.pdv_photo_view);

        setOnClickListener(R.id.ll_container, PopPhotoView.this);
        ImageView imgClose = (ImageView) findViewById(R.id.iv_pop_close);
        CoordinatorLayout.LayoutParams closeLayoutParams = (CoordinatorLayout.LayoutParams) imgClose.getLayoutParams();
        closeLayoutParams.topMargin = SizeUtils.dp2px(context, 10) + SizeUtils.getStatusBarHeight();
        imgClose.setLayoutParams(closeLayoutParams);
        setOnClickListener(R.id.iv_pop_close, PopPhotoView.this);
        ImageView imgSave = (ImageView) findViewById(R.id.iv_pop_save);
        CoordinatorLayout.LayoutParams saveLayoutParams = (CoordinatorLayout.LayoutParams) imgSave.getLayoutParams();
        saveLayoutParams.topMargin = SizeUtils.dp2px(context, 10) + SizeUtils.getStatusBarHeight();
        imgSave.setLayoutParams(saveLayoutParams);
        setOnClickListener(R.id.iv_pop_save, PopPhotoView.this);
    }
}
