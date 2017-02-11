package com.yan.campusbbs.setting;

import android.content.Context;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.yan.campusbbs.config.SharedPreferenceConfig;
import com.yan.campusbbs.rxbusaction.ActionImageShowAble;
import com.yan.campusbbs.util.SPUtils;

import javax.inject.Inject;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by yan on 2017/2/9.
 */

public class ImageControl {
    private boolean imageShowAble;

    public void setImageShowAble(Context context, boolean imageShowAble) {
        this.imageShowAble = imageShowAble;
        if (!imageShowAble) {
            Fresco.getImagePipeline().pause();
        } else {
            Fresco.getImagePipeline().resume();
        }
        SPUtils.putBoolean(context, MODE_PRIVATE, SharedPreferenceConfig.SHARED_PREFERENCE
                , SharedPreferenceConfig.IMAGE_SHOW_ABLE, imageShowAble);
    }

    @Inject
    public ImageControl() {

    }

    public void imagePause() {
        if (imageShowAble) {
            Fresco.getImagePipeline().pause();
        }
    }

    public void imageResume() {
        if (imageShowAble) {
            Fresco.getImagePipeline().resume();
        }
    }

    public void frescoInit(Context context) {
        Fresco.initialize(context);

        imageShowAble = SPUtils.getBoolean(context
                , Context.MODE_PRIVATE
                , SharedPreferenceConfig.SHARED_PREFERENCE
                , SharedPreferenceConfig.IMAGE_SHOW_ABLE, true);

        Log.e("imageShowAble", imageShowAble + "");
        if (!imageShowAble) {
            Fresco.getImagePipeline().pause();
        }
    }
}
