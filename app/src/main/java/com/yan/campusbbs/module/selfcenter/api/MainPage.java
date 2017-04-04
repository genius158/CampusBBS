package com.yan.campusbbs.module.selfcenter.api;

import com.yan.campusbbs.module.selfcenter.data.MainPageData;
import com.yan.campusbbs.repository.DataAddress;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/3/28.
 */

public interface MainPage {
    @GET(DataAddress.URL_HOME_PAGE_DATA)
    Observable<MainPageData> getMainPageData(
            @Query("pageNum") String pageNum
    );

    @GET(DataAddress.URL_SELF_TOPIC3)
    Observable<MainPageData> getMainPageSelfData(
    );
}
