package com.yan.campusbbs.setting;

import android.content.Context;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.yan.campusbbs.config.SharedPreferenceConfig;
import com.yan.campusbbs.rxbusaction.ActionImageControl;
import com.yan.campusbbs.util.SPUtils;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by yan on 2017/2/9.
 */

public class ImageControl {
    private static ImageControl imageControl;
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
