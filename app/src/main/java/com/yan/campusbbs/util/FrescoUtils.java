package com.yan.campusbbs.util;

import android.net.Uri;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;

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

}
