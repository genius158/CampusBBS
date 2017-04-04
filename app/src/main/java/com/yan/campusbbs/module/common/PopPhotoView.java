package com.yan.campusbbs.module.common;

import android.net.Uri;
import android.view.View;

import com.yan.campusbbs.R;
import com.yan.campusbbs.widget.CommonPopupWindow;

import javax.inject.Inject;

import me.relex.photodraweeview.PhotoDraweeView;

/**
 * Created by Administrator on 2017/4/4 0004.
 */

public class PopPhotoView extends CommonPopupWindow implements View.OnClickListener {

    private CommonPopupWindow commonPopupWindow;
    private PhotoDraweeView photoDraweeView;

    @Inject
    public PopPhotoView(View parent) {
        super(parent,R.layout.pop_photo_view);
    }

    public void setImageUrl(String url){
        photoDraweeView.setPhotoUri(Uri.parse(url));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_container:
                commonPopupWindow.dismiss();
                break;
        }
    }

    @Override
    public void viewInit() {
        setOnClickListener(R.id.ll_container, PopPhotoView.this);
        photoDraweeView= (PhotoDraweeView) findViewById(R.id.pdv_photo_view);
    }
}
