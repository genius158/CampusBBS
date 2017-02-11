package com.yan.campusbbs.setting;

import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.yan.campusbbs.config.SharedPreferenceConfig;
import com.yan.campusbbs.rxbusaction.ActionImageControl;
import com.yan.campusbbs.util.SPUtils;

/**
 * Created by yan on 2017/2/9.
 */

public class ImageControl {
    private static ImageControl imageControl;
    private boolean imageShowAble = false;

    public void setImageShowAble(boolean imageShowAble) {
        this.imageShowAble = imageShowAble;
    }

    public static ImageControl getInstance() {
        if (imageControl == null) {
            synchronized (ActionImageControl.class) {
                if (imageControl == null) {
                    imageControl = new ImageControl();
                }
            }
        }
        return imageControl;
    }

    private ImageControl() {

    }

    public void imagePause() {
        Fresco.getImagePipeline().pause();
    }

    public void imageResume() {
        if (imageShowAble) {
            Fresco.getImagePipeline().resume();
        }
    }

    public void frescoInit(Context context) {
        Fresco.initialize(context);
        if (SPUtils.getBoolean(context
                , Context.MODE_PRIVATE
                , SharedPreferenceConfig.SHARED_PREFERENCE
                , SharedPreferenceConfig.IMAGE_SHOW_ABLE, false)) {
            Fresco.getImagePipeline().pause();
        }
    }
}
