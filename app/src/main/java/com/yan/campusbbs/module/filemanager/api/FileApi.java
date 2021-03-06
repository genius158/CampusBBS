package com.yan.campusbbs.module.filemanager.api;

import com.yan.campusbbs.module.filemanager.data.FileData;
import com.yan.campusbbs.repository.DataAddress;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by yan on 2017/5/19.
 */

public interface FileApi {
    @GET(DataAddress.URL_VIDEO_FILE)
    Observable<FileData> getVideo();
    @GET(DataAddress.URL_IMAGE_FILE)
    Observable<FileData> getImages(@Query("pageNum")int pageNum);
}
