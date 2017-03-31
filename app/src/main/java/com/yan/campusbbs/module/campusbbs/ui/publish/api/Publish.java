package com.yan.campusbbs.module.campusbbs.ui.publish.api;

import com.yan.campusbbs.repository.DataAddress;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by yan on 2017/3/31.
 */

public interface Publish {

    @GET(DataAddress.URL_PUBLISH)
    Observable<ResponseBody> publish(
            @Query("topicTitle") String topicTitle,
            @Query("topicContent") String topicContent,
            @Query("typeDiv") String typeDiv,
            @Query("contentDiv") String contentDiv

    );

}
