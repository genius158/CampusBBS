package com.yan.campusbbs.module.selfcenter.api;

import com.yan.campusbbs.module.selfcenter.data.PublishData;
import com.yan.campusbbs.module.selfcenter.data.UserInfoData;
import com.yan.campusbbs.repository.DataAddress;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/3/28.
 */

public interface MainPage {
    @GET(DataAddress.URL_HOME_PAGE_DATA)
    Observable<PublishData> getMainPageData(
            @Query("pageNum") String pageNum
    );
    @GET(DataAddress.URL_SELF_TOPIC3)
    Observable<PublishData> getMainPageSelfData(
    );

    @GET(DataAddress.URL_USER_DETAIL)
    Observable<UserInfoData> getUserInfo(
            @Query("userId") String userId
    );

    @GET(DataAddress.URL_SELF_TOPIC_MORE)
    Observable<PublishData> getSelfTopicMore(
            @Query("pageNum") String pageNum
    );

    @GET(DataAddress.URL_SELF_TOPIC_MORE)
    Observable<PublishData> getMainPageData(
            @Query("pageNum") String pageNum
            ,   @Query("userId") String userId
    );
}
