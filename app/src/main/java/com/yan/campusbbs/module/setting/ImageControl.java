package com.yan.campusbbs.module.setting;

import android.content.Context;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.yan.campusbbs.config.SharedPreferenceConfig;
import com.yan.campusbbs.util.SPUtils;

import javax.inject.Inject;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by yan on 2017/2/9.
 */

public class ImageControl {
    private boolean imageShowAble;
    private final SPUtils spUtils;
    private final Context context;
    private boolean isInit = false;

    public void setImageShowAble(boolean imageShowAble) {
        this.imageShowAble = imageShowAble;
        if (!imageShowAble && !Fresco.getImagePipeline().isPaused()) {
            Fresco.getImagePipeline().pause();
        } else if (Fresco.getImagePipeline().isPaused()) {
            Fresco.getImagePipeline().resume();
        }
        spUtils.putBoolean(MODE_PRIVATE, SharedPreferenceConfig.SHARED_PREFERENCE
                , SharedPreferenceConfig.IMAGE_SHOW_ABLE, imageShowAble);
    }

    @Inject
    public ImageControl(SPUtils spUtils, Context context) {
        this.spUtils = spUtils;
        this.context = context;
    }

    public void imagePause() {
        if (imageShowAble && !Fresco.getImagePipeline().isPaused()) {
            Fresco.getImagePipeline().pause();
        }
    }

    public void imageResume() {
        if (imageShowAble) {
            Fresco.getImagePipeline().resume();
        }
    }

    public void frescoInit() {
        if (!isInit) {
            isInit = true;
            Fresco.initialize(context);

            imageShowAble = spUtils.getBoolean(Context.MODE_PRIVATE
                    , SharedPreferenceConfig.SHARED_PREFERENCE
                    , SharedPreferenceConfig.IMAGE_SHOW_ABLE, true);

            if (!imageShowAble && !Fresco.getImagePipeline().isPaused()) {
                Fresco.getImagePipeline().pause();
            }
        }
    }
}
