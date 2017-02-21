package com.yan.campusbbs.util;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;

/**
 * Created by yan on 2016/11/9.
 */

public class FrescoUtils {
    public static void display(SimpleDraweeView simpleDraweeView, int resId) {
        simpleDraweeView.setImageURI(Uri.parse("res:// /" + resId));
    }

    public static void display(SimpleDraweeView simpleDraweeView, String url) {
        simpleDraweeView.setImageURI(Uri.parse(url));
    }

    public static void clearImgCache(String url) {
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        Uri uri = Uri.parse(url);
        imagePipeline.evictFromMemoryCache(uri);
        imagePipeline.evictFromDiskCache(uri);
        imagePipeline.evictFromCache(uri);
    }

    public static void adjustViewOnImage(Context context, SimpleDraweeView simpleDraweeView, String url) {
        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable anim) {
                if (imageInfo == null) {
                    return;
                }
                QualityInfo qualityInfo = imageInfo.getQualityInfo();
                if (qualityInfo.isOfGoodEnoughQuality()) {
                    ViewGroup.LayoutParams layoutParams = simpleDraweeView.getLayoutParams();
                    if (imageInfo.getWidth() > SizeUtils.getFullScreenWidth(context)) {
                        layoutParams.height = (int) (SizeUtils.getFullScreenWidth(context) / imageInfo.getWidth() * imageInfo.getHeight());
                    } else {
                        layoutParams.width = imageInfo.getWidth();
                        layoutParams.height = imageInfo.getHeight();
                    }
                    simpleDraweeView.setLayoutParams(layoutParams);
                }
            }

            @Override
            public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
            }
        };
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(url))
                .setControllerListener(controllerListener)
                .setTapToRetryEnabled(true)
                .build();

        simpleDraweeView.setController(controller);
    }

}
