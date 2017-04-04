package com.yan.campusbbs.module.common;

import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.ImageView;

import com.yan.campusbbs.R;
import com.yan.campusbbs.util.SizeUtils;
import com.yan.campusbbs.widget.CommonPopupWindow;

import javax.inject.Inject;

import me.relex.photodraweeview.PhotoDraweeView;

/**
 * Created by Administrator on 2017/4/4 0004.
 */

public class PopPhotoView extends CommonPopupWindow implements View.OnClickListener {

    private PhotoDraweeView photoDraweeView;

    @Inject
    public PopPhotoView(View parent) {
        super(parent, R.layout.pop_photo_view);
    }

    public void setImageUrl(String url) {
        photoDraweeView.setPhotoUri(Uri.parse(url));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_container:
            case R.id.iv_pop_close:
                dismiss();
                break;
        }
    }

    @Override
    public void viewInit() {
        setOnClickListener(R.id.ll_container, PopPhotoView.this);
        ImageView imageView = (ImageView) findViewById(R.id.iv_pop_close);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams.topMargin = SizeUtils.dp2px(imageView.getContext(), 10) + SizeUtils.getStatusBarHeight();
        imageView.setLayoutParams(layoutParams);
        setOnClickListener(R.id.iv_pop_close, PopPhotoView.this);
        photoDraweeView = (PhotoDraweeView) findViewById(R.id.pdv_photo_view);
    }
}
